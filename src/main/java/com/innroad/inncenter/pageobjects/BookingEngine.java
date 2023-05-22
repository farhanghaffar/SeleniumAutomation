package com.innroad.inncenter.pageobjects;

import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.pages.RateGridPage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_BE;
import com.innroad.inncenter.properties.OR_RateGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_BE;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.webelements.Elements_VerifyAvailabilityInBookingEngine;

public class BookingEngine {

	public static Logger logger = Logger.getLogger("BookingEngine");
	String lastDateOfElement = "";
	public static boolean blnDaysLessThanMinNights = false;
	public static int minNights = 0;
	public static int checkInDateIndex = 0;
	public ArrayList<String> roomClassDataValuesInAvailability = new ArrayList<String>();
	public static String getMonthFromSecondPart="";
	public ArrayList<String> clickOnStartDate(WebDriver driver) {

		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_BE.startDate);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.startDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_BE.startDate), driver);
		BE.startDate.click();
		testSteps.add("Clicked on start date");
		logger.info("Clicked on start date");
		return testSteps;
	}
	
	public ArrayList<String> clickOnEndDate(WebDriver driver) {

		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_BE.endDate);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.endDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_BE.endDate), driver);
		BE.endDate.click();
		testSteps.add("Clicked on end date");
		logger.info("Clicked on end date");
		return testSteps;
	}

	public String getMonthFromCalendarHeading1(WebDriver driver) throws InterruptedException {

		Elements_BE BE = new Elements_BE(driver);
		// Wait.WaitForElement(driver, ORRateGrid.getMonthHeading);
		Wait.wait1Second();
		System.out.println("in getMonth");
		System.out.println(OR_BE.getMonthHeading);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.getMonthHeading), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_BE.getMonthHeading), driver);
		return BE.getMonthHeading.getText();

	}

	public String getMonthFromCalendarHeadingFromSecondMonth(WebDriver driver) throws InterruptedException {

		Elements_BE BE = new Elements_BE(driver);
		// Wait.WaitForElement(driver, OR_BE.getMonthHeading);
		Wait.wait1Second();
		System.out.println("in getMonth");
		System.out.println(OR_BE.getMonthHeadingFromSecondMonth);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.getMonthHeadingFromSecondMonth), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_BE.getMonthHeadingFromSecondMonth), driver);
		return BE.getMonthHeadingFromSecondMonth.getText();

	}

	public ArrayList<String> clickOnCalendarRightArrow1(WebDriver driver) {

		Elements_BE BE = new Elements_BE(driver);

		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_BE.calendarRightArrow1);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.calendarRightArrow1), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_BE.calendarRightArrow1), driver);
		BE.calendarRightArrow1.get(0).click();
		testSteps.add("Clicked on calendar right arrow");
		logger.info("Clicked on calendar right arrow");
		return testSteps;
	}

	public ArrayList<String> selectCheckInCheckOutDate(WebDriver driver, String startDate, String endDate)
			throws InterruptedException, ParseException {
		//header[text()='Check in']//following-sibling::div/span
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);
		ArrayList<String> testSteps = new ArrayList<>();
		clickOnStartDate(driver);
		testSteps = verifyCheckInCalendarBookingEngineFooterFields(driver);
		

		String getMonth = getMonthFromCalendarHeading1(driver);
		logger.info("getMonth: " + getMonth);
		String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

		boolean isMothEqual = false;
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(startDate, "MM/dd/yyyy", "MMMM yyyy");
		String getStartDate = ESTTimeZone.getDateBaseOnDate(startDate, "MM/dd/yyyy", "EEEE, MMMM d, yyyy");

		logger.info("expectedMonth: " + expectedMonth);
		String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
		System.out.println("path: " + path);
		// this is for start date
		while (!isMothEqual) {
			if (expectedMonth.equals(getMonth)) {
				logger.info("In first part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			}
			if (expectedMonth.equals(getMonthFromSecondPart)) {
				logger.info("in a second part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			} else {
				logger.info("in else");
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);
				getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

				logger.info("getMonth: " + getMonth);
			}

		}

		expectedMonth = ESTTimeZone.getDateBaseOnDate(endDate, "MM/dd/yyyy", "MMMM yyyy");
		String getEndDate = ESTTimeZone.getDateBaseOnDate(endDate, "MM/dd/yyyy", "EEEE, MMMM d, yyyy");
		path = "//td[contains(@aria-label,'" + getEndDate + "')]";
		System.out.println(path);

		isMothEqual = false;
		while (!isMothEqual) {
			if (expectedMonth.equals(getMonth)) {
				logger.info("In first part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			}
			if (expectedMonth.equals(getMonthFromSecondPart)) {
				logger.info("in a second part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			} else {
				logger.info("in else");
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);
				getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

				logger.info("getMonth: " + getMonth);
			}

		}
		return testSteps;

	}

	public ArrayList<String> enterAdultsBE(WebDriver driver, String adults) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Wait.WaitForElement_ID(driver, OR_BE.ADULTS_BE);
		BE.adultsBE.click();
		BE.adultsBE.clear();
		BE.adultsBE.sendKeys(Keys.BACK_SPACE);
		BE.adultsBE.sendKeys(adults);
		testSteps.add("Enter No of adults : " + adults);
		logger.info("Enter No of adults : " + adults);
		return testSteps;
	}

	public ArrayList<String> enterChildrenBE(WebDriver driver, String children) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement_ID(driver, OR_BE.CHILDREN_BE);
		BE.childrenBE.click();
		BE.childrenBE.clear();
		BE.childrenBE.sendKeys(Keys.BACK_SPACE);
		BE.childrenBE.sendKeys(children);
		testSteps.add("Enter No of children : " + children);
		logger.info("Enter No of children : " + children);
		return testSteps;

	}

	public ArrayList<String> enterAdultsBEForSecondRoom(WebDriver driver, String adults) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Wait.WaitForElement_ID(driver, OR_BE.ADULTS_1_BE);
		BE.adults1BE.click();
		BE.adults1BE.clear();
		BE.adults1BE.sendKeys(Keys.BACK_SPACE);
		BE.adults1BE.sendKeys(adults);
		testSteps.add("Enter No of adults : " + adults);
		logger.info("Enter No of adults : " + adults);
		return testSteps;
	}

	public ArrayList<String> enterChildrenBEForSecondRoom(WebDriver driver, String children) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement_ID(driver, OR_BE.CHILDREN_1_BE);
		BE.children1BE.click();
		BE.children1BE.clear();
		BE.children1BE.sendKeys(Keys.BACK_SPACE);
		BE.children1BE.sendKeys(children);
		testSteps.add("Enter No of children : " + children);
		logger.info("Enter No of children : " + children);
		return testSteps;

	}

	public ArrayList<String> enterGuestInfo(WebDriver driver, String guestFirstName,

			String guestLastName, String emailAddress, String phoneNumber, String homeAddress, String homeApt,
			String zipCode, String city, String state) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement_ID(driver, OR_BE.FIRST_NAME_GUEST_INFO_BE);
		BE.firstNameBE.sendKeys(guestFirstName);
		testSteps.add("Guest First Name : <b>" + guestFirstName + "</b>");
		logger.info("Guest First Name : <b>" + guestFirstName + "</b>");

		BE.lastNameBE.sendKeys(guestLastName);
		testSteps.add("Guest Last Name : <b>" + guestLastName + "</b>");
		logger.info("Guest Last Name : <b>" + guestLastName + "</b>");

		BE.emailAddressBE.sendKeys(emailAddress);
		testSteps.add("Guest Email Address : <b>" + emailAddress + "</b>");
		logger.info("Guest Email Address : <b>" + emailAddress + "</b>");

		BE.phoneNumberBE.sendKeys(phoneNumber);
		testSteps.add("Guest Phone Number : <b>" + phoneNumber + "</b>");
		logger.info("Guest Phone Number : <b>" + phoneNumber + "</b>");

		BE.homeAddressBE.sendKeys(homeAddress);
		testSteps.add("Guest Home Address : <b>" + homeAddress + "</b>");
		logger.info("Guest Home Name : <b>" + homeAddress + "</b>");

		BE.homeAptBE.sendKeys(homeApt);
		testSteps.add("Guest Home Aparment Address : <b>" + homeApt + "</b>");
		logger.info("Guest Home Aparment Address : <b>" + homeApt + "</b>");

		BE.zipCodeBE.sendKeys(zipCode);
		testSteps.add("Guest ZipCode : <b>" + zipCode + "</b>");
		logger.info("Guest ZipCode : <b>" + zipCode + "</b>");

		BE.cityBE.sendKeys(city);
		testSteps.add("Guest City : <b>" + city + "</b>");
		logger.info("Guest City : <b>" + city + "</b>");

		BE.homeAptBE.sendKeys(homeApt);
		testSteps.add("Guest Home Aparment Address : <b>" + homeApt + "</b>");
		logger.info("Guest Home Aparment Address : <b>" + homeApt + "</b>");

		BE.stateBE.click();
		Wait.WaitForElement(driver, OR_BE.STATE_INPUT_FIELD_GUEST_INFO_BE);
		BE.stateInputFieldBE.sendKeys(state);
		String stateList = "(//div[@class='bs-searchbox'])[2]/following-sibling::ul/li//span[text()='" + state + "']";
		Wait.WaitForElement(driver, stateList);
		driver.findElement(By.xpath(stateList)).click();
		testSteps.add("Guest State : <b>" + state + "</b>");
		logger.info("Guest State : <b>" + state + "</b>");
		return testSteps;
	}
	
	public ArrayList<String> enterGuestInfo1(WebDriver driver, String guestFirstName,

			String guestLastName, String emailAddress, String phoneNumber, String homeAddress, String homeApt,
			String zipCode,String country, String city, String state) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement_ID(driver, OR_BE.FIRST_NAME_GUEST_INFO_BE);
		BE.firstNameBE.sendKeys(guestFirstName);
		testSteps.add("Guest First Name : <b>" + guestFirstName + "</b>");
		logger.info("Guest First Name : <b>" + guestFirstName + "</b>");

		BE.lastNameBE.sendKeys(guestLastName);
		testSteps.add("Guest Last Name : <b>" + guestLastName + "</b>");
		logger.info("Guest Last Name : <b>" + guestLastName + "</b>");

		BE.emailAddressBE.sendKeys(emailAddress);
		testSteps.add("Guest Email Address : <b>" + emailAddress + "</b>");
		logger.info("Guest Email Address : <b>" + emailAddress + "</b>");

		BE.phoneNumberBE.sendKeys(phoneNumber);
		testSteps.add("Guest Phone Number : <b>" + phoneNumber + "</b>");
		logger.info("Guest Phone Number : <b>" + phoneNumber + "</b>");

		BE.homeAddressBE.sendKeys(homeAddress);
		testSteps.add("Guest Home Address : <b>" + homeAddress + "</b>");
		logger.info("Guest Home Name : <b>" + homeAddress + "</b>");

		BE.homeAptBE.sendKeys(homeApt);
		testSteps.add("Guest Home Aparment Address : <b>" + homeApt + "</b>");
		logger.info("Guest Home Aparment Address : <b>" + homeApt + "</b>");

		BE.zipCodeBE.sendKeys(zipCode);
		testSteps.add("Guest ZipCode : <b>" + zipCode + "</b>");
		logger.info("Guest ZipCode : <b>" + zipCode + "</b>");
		
		BE.COUNTRY_GUEST_INFO_BE.click();
		String countryList = "(//div[@class='bs-searchbox'])[1]/following-sibling::ul/li//span[text()='" + country + "']";
		Wait.WaitForElement(driver, countryList);
		driver.findElement(By.xpath(countryList)).click();
		testSteps.add("Guest State : <b>" + country + "</b>");
		logger.info("Guest State : <b>" + country + "</b>");

		BE.cityBE.sendKeys(city);
		testSteps.add("Guest City : <b>" + city + "</b>");
		logger.info("Guest City : <b>" + city + "</b>");

		BE.homeAptBE.sendKeys(homeApt);
		testSteps.add("Guest Home Aparment Address : <b>" + homeApt + "</b>");
		logger.info("Guest Home Aparment Address : <b>" + homeApt + "</b>");

		BE.stateBE.click();
		Wait.WaitForElement(driver, OR_BE.STATE_INPUT_FIELD_GUEST_INFO_BE);
		BE.stateInputFieldBE.sendKeys(state);
		String stateList = "(//div[@class='bs-searchbox'])[2]/following-sibling::ul/li//span[text()='" + state + "']";
		Wait.WaitForElement(driver, stateList);
		driver.findElement(By.xpath(stateList)).click();
		testSteps.add("Guest State : <b>" + state + "</b>");
		logger.info("Guest State : <b>" + state + "</b>");
		return testSteps;
	}

	public ArrayList<String> enterMcCardInfo(WebDriver driver, String cardNumber, String nameOnCard, String cardExpDate,
			String cvv) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		BE.creditCardNumberBE.sendKeys(cardNumber);
		testSteps.add("Enter CardNumber : " + cardNumber);
		logger.info("Enter CardNumber : " + cardNumber);
		BE.fullNameOnCardBE.sendKeys(nameOnCard);
		testSteps.add("Enter Name On Card : " + nameOnCard);
		logger.info("Enter Name On Card : " + nameOnCard);
		BE.expirationDateBE.sendKeys(cardExpDate);
		testSteps.add("Enter Card ExpDate : " + cardExpDate);
		logger.info("Enter Card ExpDate : " + cardExpDate);
		BE.cvvBE.sendKeys(cvv);
		testSteps.add("Enter CVV : " + cvv);
		logger.info("Enter CVV : " + cvv);
		return testSteps;

	}

	public ArrayList<String> clickBookStay(WebDriver driver) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Utility.ScrollToElement(BE.agreeOnTermBE, driver);
		BE.agreeOnTermBE.click();
		testSteps.add("Agree On Terms Checked");
		logger.info("Agree On Terms Checked");
		BE.bookStayButtonBE.click();
		testSteps.add("Book Stay Button Clicked");
		logger.info("Book Stay Button Clicked");
		Wait.WaitForElement(driver, OR_BE.RESERVATION_SUCCESS_MESSAGE_BE);
		assertEquals(BE.reservationSuccessMessageBE.getText(), "Booking successful!",
				"Failed:Reservation Success Msg didn't verify");
		testSteps.add("Reservation Success Message in BE Verified");
		logger.info("Reservation Success Message in BE Verified");
		return testSteps;

	}

	public ArrayList<String> clickSearchOfRooms(WebDriver driver) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		BE.searchRoomsButtonBE.click();
		testSteps.add("Search Room Button Clicked");
		logger.info("Search Room Button Clicked");

		return testSteps;

	}

	public ArrayList<String> clickAddRoom(WebDriver driver) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		BE.addRoomButtonBE.click();
		testSteps.add("Add Room Button Clicked");
		logger.info("Add Room Button Clicked");

		return testSteps;

	}

	public ArrayList<String> selectAdvanceOption(WebDriver driver, String option) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		BE.advanceOptionsButtonBE.click();
		testSteps.add("Advance Options Button Clicked");
		logger.info("Advance Options  Button Clicked");
		if (option.equalsIgnoreCase("Group")) {
			Wait.WaitForElement(driver, OR_BE.SELECT_WITH_GROUP_NUMBER_OPTION_BUTTON_BE);
			BE.selectGroupNumberButtonBE.click();
			testSteps.add("Group Option Selected");
			logger.info("Group Option Selected");
		} else if (option.equalsIgnoreCase("Reservation")) {
			Wait.WaitForElement(driver, OR_BE.SELECT_WITH_RESERVATION_NUMBER_OPTION_BUTTON_BE);

			BE.selectReservationNumberButtonBE.click();
			testSteps.add("Reservation Option Selected");
			logger.info("Reservation Option Selected");
		}

		return testSteps;

	}

	public ArrayList<String> enterGroupNumber(WebDriver driver, String groupNumber) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Wait.WaitForElement(driver, OR_BE.ENTER_GROUP_NUMBER_PAGE_TITLE_BE);
		assertEquals(BE.enterGroupNumberTitle.isDisplayed(), true, "Group Page didn't display");
		BE.enterGroupNumberFieldBE.sendKeys(groupNumber);
		testSteps.add("Group Number:" + groupNumber + " Added");
		logger.info("Group Number:" + groupNumber + " Added");

		return testSteps;

	}

	public ArrayList<String> goButton(WebDriver driver) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		BE.goButtonBE.click();
		testSteps.add("Go Button Clicked");
		logger.info("Go Button Clicked");

		return testSteps;

	}

	public ArrayList<String> selectRoomClass(WebDriver driver, String roomClass) {
		ArrayList<String> testSteps = new ArrayList<String>();
		String roomClassSelectionButton = "//div[text()='" + roomClass + "']/parent::div/following-sibling::div/a";
		Wait.WaitForElement(driver, roomClassSelectionButton);
		WebElement roomClassSelectionButtonElement = driver.findElement(By.xpath(roomClassSelectionButton));
		roomClassSelectionButtonElement.click();
		testSteps.add("More Info Button of:" + roomClass + " Clicked");
		logger.info("More Info Button of:" + roomClass + " Clicked");

		return testSteps;

	}

	public ArrayList<String> roomDetailsVerification(WebDriver driver, String roomClass, String rateAmount,
			String maxPerson) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String roomClassName = "//div[text()='" + roomClass + "']";
		String roomClassRateAmount = "//div[text()='" + roomClass + "']/parent::div[contains(@class,'RoomDetails')]//a";
		String roomClassMaxGuest = "//div[text()='" + roomClass
				+ "']//parent::div/div[contains(@class,'RoomDetails__guestsAndBeds')]//div";
		Wait.WaitForElement(driver, roomClassName);
		WebElement roomClasNameElement = driver.findElement(By.xpath(roomClassName));
		WebElement roomClassRateAmountElement = driver.findElement(By.xpath(roomClassRateAmount));
		WebElement roomClassMaxGuestElement = driver.findElement(By.xpath(roomClassMaxGuest));
		Utility.ScrollToElement(roomClasNameElement, driver);

		assertEquals(roomClasNameElement.getText(), roomClass, "Failed: room Class Name didn't verify");
		testSteps.add("Room Class Name:" + roomClass + " Verified");
		logger.info("Room Class Name:" + roomClass + " Verified");

		assertEquals(
				Utility.convertDollarToNormalAmount(driver,
						roomClassRateAmountElement.getText().split("/")[0].split("\\.")[0]),
				rateAmount, "Failed: rate didn't verify");
		testSteps.add("Room Class Rate:" + rateAmount + " Verified");
		logger.info("Room Class Rate:" + rateAmount + " Verified");

		assertEquals(roomClassMaxGuestElement.getText().contains(maxPerson), true, "Failed: Max Guest didn't verify");
		testSteps.add("Room Class Max Person:" + maxPerson + " Verified");
		logger.info("Room Class  Max Person:" + maxPerson + " Verified");

		return testSteps;

	}

	public ArrayList<String> ratePlanDetailsVerification(WebDriver driver, String ratePlanName, String rateAmount)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);

		assertEquals(BE.ratePlanNameBE.getText(), ratePlanName, "Failed: RatePlan Name didn't verify");
		testSteps.add("Rate Plan Name:" + ratePlanName + " Verified");
		logger.info("Rate Plan Name:" + ratePlanName + " Verified");

		assertEquals(Utility.convertDollarToNormalAmount(driver, BE.ratePlanChargeBE.getText().split("\\.")[0]),
				rateAmount, "Failed: rate didn't verify");
		testSteps.add("Rate plan  Rate:" + rateAmount + " Verified");
		logger.info("Rate Plan  Rate:" + rateAmount + " Verified");

		return testSteps;
	}

	public ArrayList<String> clickBookRoom(WebDriver driver, String ratePlanName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		String bookRoomButton = "//span[text()='" + ratePlanName + "']/parent::h2/following-sibling::button";
		WebElement bookRoomButtonElement = driver.findElement(By.xpath(bookRoomButton));
		Utility.ScrollToElement(bookRoomButtonElement, driver);
		bookRoomButtonElement.click();
		testSteps.add("Book Room Button of Rate Plan:" + ratePlanName + " Clicked ");
		logger.info("Book Room Button of Rate Plan:" + ratePlanName + " Clicked ");

		try {
			Wait.explicit_wait_visibilityof_webelement(BE.SKIP_THIS_STEP, driver);
			BE.SKIP_THIS_STEP.click();
			testSteps.add("Skip this Step Clicked");
			logger.info("Skip this Step Clicked");
		}catch(Exception e) {
			
		}
		return testSteps;
	}

	public String getReservationNumber(WebDriver driver) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.RESERVATION_NUMBER_BE), driver);
		Utility.ScrollToElement(BE.reservationNumberBE, driver);
		String reservationNumber = "";
		reservationNumber = BE.reservationNumberBE.getText();
		reservationNumber = reservationNumber.split("#")[1];

		return reservationNumber;
	}

	public String getSubTotal(WebDriver driver) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		Utility.ScrollToElement(BE.subTotalBE, driver);
		String subTotal = "";
		subTotal = Utility.convertDollarToNormalAmount(driver, BE.subTotalBE.getText());

		return subTotal;
	}

	public String getTaxApplied(WebDriver driver) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		Utility.ScrollToElement(BE.taxAppliedBE, driver);
		String tax = "";
		tax = Utility.convertDollarToNormalAmount(driver, BE.taxAppliedBE.getText());

		return tax;
	}

	public String createBookingEngineReservation(WebDriver driver, String startDate, String endDate, String roomClass,
			String ratePlan) throws InterruptedException, ParseException {
		Elements_BE BE = new Elements_BE(driver);
		String reservationNumber = "";
		String adults = Integer.toString(Utility.getRandomNumber(1, 2));
		String child = Utility.GenerateRandomNumber(01);
		String firstName = Utility.generateRandomStringWithGivenLength(5);
		String lastName = Utility.generateRandomStringWithGivenLength(5);
		String emailAddress = "innroadautomation@innroad.com";
		String phoneNumber = "0987654321";
		String address = Utility.GenerateRandomNumber(100) + Utility.generateRandomStringWithGivenLength(5);
		String postalCode = Utility.GenerateRandomNumber(999);
		String city = Utility.generateRandomStringWithGivenLength(06);
		String cardNumber = "5454545454545454";
		String cardName = firstName + lastName;
		String cvv = Utility.GenerateRandomNumber(999);
		String cardExpDate = Utility.GetNextDate(500, "MM/yy");
		System.out.println("Card Exp Date:" + cardExpDate);
		selectCheckInCheckOutDate(driver, startDate, endDate);
		enterAdultsBE(driver, adults);
		enterChildrenBE(driver, child);
		clickSearchOfRooms(driver);
		selectRoomClass(driver, roomClass);
		clickBookRoom(driver, ratePlan);
		int randomNumber = Integer.parseInt(Utility.GenerateRandomNumber(BE.statesListBE.size() - 1));
		System.out.println("Ranowm:" + randomNumber);
		BE.stateBE.click();
		Wait.WaitForElement(driver, OR_BE.STATE_INPUT_FIELD_GUEST_INFO_BE);
		String state = BE.statesListBE.get(randomNumber).getText();
		BE.stateBE.click();
		System.out.println("State:" + state);
		enterGuestInfo(driver, firstName, lastName, emailAddress, phoneNumber, address, address, postalCode, city,
				state);
		enterMcCardInfo(driver, cardNumber, cardName, cardExpDate, cvv);
		clickBookStay(driver);
		reservationNumber = getReservationNumber(driver);

		return reservationNumber;

	}

	public ArrayList<String> enterPromoCode(WebDriver driver, String ratePlanPromoCode) {
			Elements_BE BE = new Elements_BE(driver);
			ArrayList<String> testSteps = new ArrayList<String>();
			Wait.WaitForElement(driver, OR_BE.PROMOCODE_BE);
			BE.promocodeBE.click();
			BE.promocodeBE.clear();
			BE.promocodeBE.sendKeys(Keys.BACK_SPACE);
			BE.promocodeBE.sendKeys(ratePlanPromoCode);
			testSteps.add("Enter Promo Code : " + ratePlanPromoCode);
			logger.info("Enter Promo Code : " + ratePlanPromoCode);
			return testSteps;
	}

	public ArrayList<String> verifyRatePlanExist(WebDriver driver, String expectedRatePlanName, boolean exist) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		List<WebElement> ratePlanNameList = driver.findElements(By.xpath(OR_BE.RATEPLAN_NAME_BE));
		
		testSteps.add("Expected Rate plan Name :" + expectedRatePlanName);
		logger.info("Expected Rate plan Name :" + expectedRatePlanName);
		boolean found = false;
		for(WebElement ratePlanName: ratePlanNameList) {
			if(ratePlanName.getText().equalsIgnoreCase(expectedRatePlanName)) {
				found = true;
			}
		}
		testSteps.add("Verified Rate plan Availability : " + exist);
		logger.info("Verified Rate plan Availability : " + exist);
		assertEquals(found,exist, "Failed: rate Plan Availability verification failed");
		

		return testSteps;
	}
	
	public ArrayList<String> ratePlanNightStayAmount(WebDriver driver, String ratePlanName, String rateAmount, int nights)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		String expectedNightStay = "$"+String.format("%.2f",(Float.parseFloat(rateAmount) * nights));
		testSteps.add("Expected Night Stay : " + expectedNightStay);
		logger.info("Expected Night Stay : " + expectedNightStay);
		String xpath = "(//span[contains(@class,'ratePlanName') and text()='"+ratePlanName+"']"
				+ "//ancestor::div[contains(@class,'rateCard')]//div[contains(@class,'RateCharge')]//span[contains(@class,'pretaxTotal')])[1]";
		WebElement nightStay = driver.findElement(By.xpath(xpath));
		String nightStayAmount = nightStay.getText();
		testSteps.add("Night Stay : " + nightStayAmount);
		logger.info("Night Stay : " + nightStayAmount);
		assertEquals(expectedNightStay,
				nightStayAmount, "Failed: rate didn't verify");
		return testSteps;
	}

	public ArrayList<String> verifySeasonPoliciesInBookingEngine(WebDriver driver, HashMap<String, String> getSessionLevelPolicy,HashMap<String, String> getRateLevelPolicy) 
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> seasonPolicies = new ArrayList<String>();
		String policyType = "";
		String policyName = "";
		Elements_BE bookingEngine = new Elements_BE(driver);
		boolean blnPoliciesExistOrNOt = false;
		Wait.WaitForElement(driver, OR_BE.POLICIES);
		testSteps.add("Season Policies : " + seasonPolicies);
		logger.info("Season Policies : " + seasonPolicies);

		if (bookingEngine.POLICIES.isDisplayed()) {
			if (bookingEngine.POLICIES_LIST.size() > 0) {
				for (int index = 1; index <= bookingEngine.POLICIES_LIST.size(); index++) {
					String selectedPolicyText = "(//h4[text()='Policies']//../ul/li)[" + index + "]/button";
					String elementText = driver.findElement(By.xpath(selectedPolicyText)).getText();
					if (getSessionLevelPolicy.size() > 0 && Collections.frequency(getSessionLevelPolicy.values(), "NA") < 4) {
						for (Map.Entry<String, String> entry : getSessionLevelPolicy.entrySet()) {
							policyType = entry.getKey();
							policyName= entry.getValue();
							if(!policyName.equalsIgnoreCase("NA"))
							{
								if(policyType.equalsIgnoreCase(elementText))
								{
									testSteps.add("Verified Policy Type : " +policyType+" : "+ policyName);
									logger.info("Verified Policy Type : " +policyType+" : "+ policyName);
									blnPoliciesExistOrNOt = true ;
								}
							}
						}
					} else {
						if (getSessionLevelPolicy.size() > 0 && Collections.frequency(getRateLevelPolicy.values(), "NA") < 4) {
							for (Map.Entry<String, String> entry : getRateLevelPolicy.entrySet()) {
								policyType = entry.getKey();
								policyName = entry.getValue();
								if (!policyName.equalsIgnoreCase("NA")) {
									if (policyType.equalsIgnoreCase(elementText)) {
										testSteps.add("Verified Policy = " + policyType + " : " + policyName);
										logger.info("Verified Policy = " + policyType + " : " + policyName);
										blnPoliciesExistOrNOt = true;
									}
								}
							}
						}
					}
					if(blnPoliciesExistOrNOt == false)
					{
						testSteps.add("Failed to Verify Policy Type = " +policyType+" : "+ policyName);
						logger.info("Failed to Verify Policy Type = " +policyType+" : "+ policyName);
					}
				}

			}
		}
		return testSteps;
	}
	
	// Added By Adhnan Ghaffar 11/11/2020
	public ArrayList<String> verifyPolicyExistInBookingEngine(WebDriver driver,String policyType, String policyName, boolean exist) 
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE bookingEngine = new Elements_BE(driver);
		Wait.WaitForElement(driver, OR_BE.POLICIES);
		//testSteps.add("Expected "+policyType+" Policy : " + policyName);
		logger.info("Expected "+policyType+" Policy : " + policyName);
		boolean foundPolicy = false;
		if (bookingEngine.POLICIES.isDisplayed()) {
			if (bookingEngine.POLICIES_LIST.size() > 0) {
				for (int index = 1; index <= bookingEngine.POLICIES_LIST.size(); index++) {
					String selectedPolicyText = "(//h4[text()='Policies']//../ul/li)[" + index + "]/button";
					String elementText = driver.findElement(By.xpath(selectedPolicyText)).getText();
					logger.info("Found Policy : " + elementText);
							if(elementText.equalsIgnoreCase(policyName))
							{
									testSteps.add("Verified "+policyType+" policy '" + policyName +"' exist.");
									logger.info("Verified "+policyType+" policy '" + policyName +"' exist.");
									foundPolicy = true ;
									break;
							}
				}
			}
		}
		if(!exist) {
			testSteps.add("Verified "+policyType+" policy '" + policyName +"' not exist.");
			logger.info("Verified "+policyType+" policy '" + policyName +"' not exist.");
		}
		assertEquals(foundPolicy, exist, "Failed: Required Policy not exist");		
		return testSteps;
	}

	public ArrayList<String> verifyPolicyExistNotInBookingEngine(WebDriver driver) throws InterruptedException {
		
		ArrayList<String> testSteps = new ArrayList<>();
		String bookingEnginePoliciesPath = "//h4[text()='Policies']";
		if(driver.findElements(By.xpath(bookingEnginePoliciesPath)).size() == 0 )
		{
			testSteps.add("Successfully verified no season policies are associated to reservation.");
			logger.info("Successfully verified no season policies are associated to reservation.");
		}
		else
		{
			testSteps.add("Successfully verified no season policies are associated to reservation but are showing in Booking Engine.");
			logger.info("Successfully verified no season policies are associated to reservation but are showing in Booking Engine.");
		}
		
		return testSteps;
	}
	
	//Added By Adhnan 11/12/2020
	public String selectCheckInCheckOutDateAndGetCheckInRateValue(WebDriver driver, String startDate, String endDate, ArrayList<String> testSteps)
			throws InterruptedException, ParseException {
		
		Elements_BE BE = new Elements_BE(driver);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		clickOnStartDate(driver);
		testSteps.add("Click On Check-In date");
		logger.info("Click On Check-In date");
		String getMonth = getMonthFromCalendarHeading1(driver);
		logger.info("getMonth: " + getMonth);
		String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

		boolean isMothEqual = false;
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(startDate, "MM/dd/yyyy", "MMMM yyyy");
		String getStartDate = ESTTimeZone.getDateBaseOnDate(startDate, "MM/dd/yyyy", "EEEE, MMMM d, yyyy");
		
		
		logger.info("expectedMonth: " + expectedMonth);
		String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
		System.out.println("path: " + path);
		
		// get tooltip rate Value 
		WebElement hoverElement = driver.findElement(By.xpath(path));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		testSteps.add("Hovers over Check-IN Date '" + startDate+"'");

		Wait.WaitForElement(driver,  OR_BE.startDateToolTipRateValue);
		Wait.waitForElementToBeVisibile(By.xpath( OR_BE.startDateToolTipRateValue), driver);
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath( OR_BE.startDateToolTipRateValue)), driver);
		
		String rateValue = BE.startDateToolTipRateValue.getText();

		testSteps.add("Check-In Rate Value '" + rateValue+"'");
		logger.info("Check-In Rate Value '" + rateValue+"'");
		// this is for start date
		while (!isMothEqual) {
			if (expectedMonth.equals(getMonth)) {
				logger.info("In first part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			}
			if (expectedMonth.equals(getMonthFromSecondPart)) {
				logger.info("in a second part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			} else {
				logger.info("in else");
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);
				getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

				logger.info("getMonth: " + getMonth);
			}

		}

		expectedMonth = ESTTimeZone.getDateBaseOnDate(endDate, "MM/dd/yyyy", "MMMM yyyy");
		String getEndDate = ESTTimeZone.getDateBaseOnDate(endDate, "MM/dd/yyyy", "EEEE, MMMM d, yyyy");
		path = "//td[contains(@aria-label,'" + getEndDate + "')]";
		System.out.println(path);

		isMothEqual = false;
		while (!isMothEqual) {
			if (expectedMonth.equals(getMonth)) {
				logger.info("In first part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			}
			if (expectedMonth.equals(getMonthFromSecondPart)) {
				logger.info("in a second part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			} else {
				logger.info("in else");
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);
				getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

				logger.info("getMonth: " + getMonth);
			}

		}
		return rateValue;

	}

	public ArrayList<String> verifyCheckInCalendarBookingEngineFooterFields(WebDriver driver)
	{
		boolean blnFound = false;
		ArrayList<String> testSteps = new ArrayList<String>();
		HashMap<String, String> hashmap = new HashMap<>();
		hashmap.put("No Availability", "No Availability");
		hashmap.put("Minimum Stay", "Minimum Stay");
		hashmap.put("No Arrivals", "No Arrivals");
		hashmap.put("No Departures", "No Departures");
		
		Elements_BE bookingEngine = new Elements_BE(driver);
		
		testSteps.add("=================== VERIFYING CHECK IN CALENDAR FOOTER FIELDS ======================");
		logger.info("=================== VERIFYING CHECK IN CALENDAR FOOTER FIELDS ======================");

		List<WebElement> lstCheckInCalenderFooterFields = bookingEngine.bookingEngineCheckInCalenderFooterFields;
		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
			String elementText = entry.getValue();
			logger.info("Expected : " + elementText);
			for (WebElement element : lstCheckInCalenderFooterFields) {
				if (elementText.equalsIgnoreCase(element.getText())) {
					logger.info("Found : " + elementText);
					testSteps.add("Verified '" + entry.getValue() + "' exists on check in calendar.");
					logger.info("Verified '" + entry.getValue() + "' exists on check in calendar.");
					blnFound = true;
					break;
				}
			}
		}
		return testSteps;
	}

	public ArrayList<String> calculateAdditionalChargesForTheEnteredAdultsAndChild(WebDriver driver,String ratePlanName, int numberofDays,
			String adults, String child, String roomClass, String baseAmount, String adultCapacity,
			String personCapacity, String maxAdult, String maxPerson, String additionalAdultCharges,
			String additionalChildCharges,
			boolean isAdditionalChargesApplied)
			throws InterruptedException, NumberFormatException, ParseException {

		double base_amount = Double.parseDouble(baseAmount);
		ArrayList<String> testSteps = new ArrayList<String>();
		String selectedRatePlanChargesPath = "(//span[contains(@class,'ratePlanName') and text()='"+ratePlanName+"']"
				+ "//ancestor::div[contains(@class,'rateCard')]//div[contains(@class,'RateCharge')]//span[contains(@class,'pretaxTotal')])[1]";
		Wait.WaitForElement(driver, selectedRatePlanChargesPath);
		String ratePlanRoomClassChargesValue = driver.findElement(By.xpath(selectedRatePlanChargesPath)).getText();

		int adultDifference = 0;
		int childDifference = 0;
		double intial = 0;
		String rateApplied = "";
		logger.info("child: " + child);
		testSteps.add("child: " + child);
		logger.info("adults: " + adults);
		testSteps.add("adults: " + adults);
		int child_adults = Integer.parseInt(child) + Integer.parseInt(adults);
		logger.info("child_adults: " + child_adults);
		testSteps.add("child_adults: " + child_adults);

		if (isAdditionalChargesApplied) {
			logger.info("**********************************Third if:");

			if (Integer.parseInt(adults) <= Integer.parseInt(adultCapacity)) {
				if (Integer.parseInt(adults) > Integer.parseInt(maxAdult))
					adultDifference = Math.abs(Integer.parseInt(maxAdult) - Integer.parseInt(adults));

				child_adults = child_adults - adultDifference;
				int count = 0;
				boolean isTrue = false;
				int intMaxPerson = Integer.parseInt(maxPerson);
				if (child_adults > intMaxPerson) {
					for (int i = 1; i < child_adults; i++) {
						child_adults = child_adults - 1;
						if (child_adults == intMaxPerson) {
							count = i;
							isTrue = true;
							break;
						}
					}

				}

				if (isTrue) {
					childDifference = count;
				}
				logger.info("Additonal Adult: " + adultDifference);
				testSteps.add("Additonal Adult: " + adultDifference);
				logger.info("Additonal Child: " + childDifference);
				testSteps.add("Additonal Child: " + childDifference);

				logger.info("adult: " + adultDifference * (Double.parseDouble(additionalAdultCharges)));
				testSteps.add("adult: " + adultDifference * (Double.parseDouble(additionalAdultCharges)));
				logger.info("child: " + childDifference * (Double.parseDouble(additionalChildCharges)));
				testSteps.add("child: " + childDifference * (Double.parseDouble(additionalChildCharges)));
				logger.info("base_amount: " + base_amount);
				testSteps.add("base_amount: " + base_amount);

				intial = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
						+ (childDifference * (Double.parseDouble(additionalChildCharges)));
				if (numberofDays > 1) {
					intial = intial * numberofDays;
				}

			}
		}
		else
		{
			intial = base_amount * numberofDays;
		}
		
		String getValue = Utility.RemoveDollarandSpaces(driver, ratePlanRoomClassChargesValue).trim();
		rateApplied = String.format("%.2f", intial);
		logger.info("convert: " + getValue);
		logger.info("rateApplied: " + rateApplied);

		testSteps.add("Expected rates: " + rateApplied);
		testSteps.add("Found: " + intial);
		if (getValue.equals(rateApplied)) {
			logger.info("Verified rate plan rate value is matching.");
			testSteps.add("Verified rate plan rate value is matching.");

		} else {
			logger.info("Failed : Interval rate values are not matching.");
			testSteps.add("Failed : Interval rate values are not matching.");

		}

		return testSteps;
	}

	public boolean verifyRoomClassExistInBookingEngine(WebDriver driver,String adults, String child, String roomClass, String adultCapacity,
			String personCapacity) {
		boolean roomClassNotExist = false;
		
		String roomClassPath = "//div[@class='CA__roomClassName RoomDetails__name___Thpw0' and text()='"+roomClass+"']";
		if(Integer.parseInt(adults) > Integer.parseInt(adultCapacity) || (Integer.parseInt(adults) + Integer.parseInt(child)) > Integer.parseInt(personCapacity))
		{
			int sizeOfRoomClassElement  = driver.findElements(By.xpath(roomClassPath)).size();

			logger.info("Expected : " + 0);
			logger.info("Found: " + sizeOfRoomClassElement);
			if(sizeOfRoomClassElement == 0)
			{
				logger.info("Successfully Verified '"+roomClass +"' is not showing in Booking Engine.");
				roomClassNotExist= true;
			}
			Assert.assertEquals(0, sizeOfRoomClassElement,"Failed :"+roomClass +" is showing in Booking Engine.");
		}
		return roomClassNotExist;
	}
	
	public void clickRateDropDown(WebDriver driver, ArrayList<String> test_steps) {
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.SEARCH_DISPLAYED_RATE, driver);
		elements.SEARCH_DISPLAYED_RATE.click();
		test_steps.add("Open displayed rate grid dropdown");
		String ratePlanName = "//span[text()='BE NightlyRatePlan']//parent::a";
		Wait.WaitForElement(driver, ratePlanName);
		test_steps.add("Select '" + ratePlanName + "' from dropdown.");
		logger.info("Select '" + ratePlanName + "' from dropdown.");
		driver.findElement(By.xpath(ratePlanName)).click();
		String saveButtonPath = "//div[@class='Calendar__calendarSaveSection___1IvyD col-md-4']/button";
		Wait.WaitForElement(driver, saveButtonPath);
		driver.findElement(By.xpath(saveButtonPath)).click();
		test_steps.add("Successfully clicked on Save button.");
		logger.info("Successfully clicked on Save button.");
	}
	
	public boolean isRatePlanExist(WebDriver driver, String ratePlanName, ArrayList<String>test_steps) throws InterruptedException {
		boolean flag;
		String rateDisplayedPath = "//div[text()='Rate Displayed']/../../following-sibling::div//span[2]";
		//Wait.waitForElementToBeVisibile(By.xpath(OR_BE.SETTINGS_LINK), driver);
		Wait.WaitForElement(driver, OR_BE.SETTINGS_LINK);
		driver.findElement(By.xpath(OR_BE.SETTINGS_LINK)).click();
		Wait.WaitForElement(driver, rateDisplayedPath);
		test_steps.add("Expected  :"+ratePlanName);
		logger.info("Expected  :"+ratePlanName);
		String elementText = driver.findElement(By.xpath(rateDisplayedPath)).getText();
		test_steps.add("Found  :"+elementText);
		logger.info("Found  :"+elementText);
		if(driver.findElement(By.xpath(rateDisplayedPath)).getText().equalsIgnoreCase(ratePlanName))
		{
			flag = true;
		}
		else
		{
			clickRateDropDown(driver,ratePlanName,test_steps);
			flag = false;
		}
		return flag;
	}

	public ArrayList<String> clickViewOurEntireAvailability(WebDriver driver,String startDate) {

		ArrayList<String> test_steps = new ArrayList<String>();
		if(driver.findElements(By.xpath(OR_BE.ENTIRE_AVALABILITY)).size() > 0)
		{
			Wait.WaitForElement(driver, OR_BE.ENTIRE_AVALABILITY);
			driver.findElement(By.xpath(OR_BE.ENTIRE_AVALABILITY)).click();
			test_steps.add("Click 'View Our Entire Availability' link");
			logger.info("Click 'View Our Entire Availability' link.");
		}
		return test_steps;
	}

	public ArrayList<String> verifyAvailabilityDatesInBookingEngine(WebDriver driver) throws ParseException {
		
		ArrayList<String> test_steps = new ArrayList<>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
		List<WebElement> lstAvailabilityDays = driver.findElements(By.xpath(AVAILABILITY_DAYS));
		Wait.WaitForElement(driver, OR_BE.AVAILABILITY_MONTH);

		Calendar calobj = Calendar.getInstance();
		System.out.println(df.format(calobj.getTime()));
		String todayDate = df.format(calobj.getTime()).split(" ")[0].toString();
		String month = Utility.get_MonthYear(todayDate).split(" ")[0].toString();
		String elementMonth = elements.AVAILABILITY_MONTH.getText();
		String lastDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(todayDate,"dd/MM/yyyy",
				"dd/MM/yyyy", 15, "America/New_York");
		if(elementMonth.equals(month))
		{
			test_steps.add("Expected Month : "+month);
			logger.info("Expected Month : "+month);
			test_steps.add("Found Month : "+elementMonth);
			logger.info("Found Month : "+elementMonth);
			test_steps.add("Successfully verified Month : "+elementMonth);
			logger.info("Successfully verified Month : "+elementMonth);
			logger.info("No of Days : "+lstAvailabilityDays.size());
			String elementStartDate = lstAvailabilityDays.get(0).getText()+"/"+todayDate.split("/")[1]+"/"+todayDate.split("/")[2];
			lastDateOfElement = ESTTimeZone.getNextDateBaseOnPreviouseDate(elementStartDate,"dd/MM/yyyy",
					"dd/MM/yyyy", 15, "America/New_York");
			if(elementStartDate.equalsIgnoreCase(todayDate) && lastDate.equalsIgnoreCase(lastDateOfElement))
			{
				test_steps.add("Successfully verified date range lies between : "+elementStartDate + " to " + lastDateOfElement);
				logger.info("Successfully verified date range lies between : "+elementStartDate + " to " + lastDateOfElement);
			}
			
		}
		
		
		return test_steps;
	}

	public ArrayList<String> clickOnNextButton(WebDriver driver,ArrayList<String> roomClassList) {
		
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_BE.AVAILABILITY_FORWARD);
		elements.AVAILABILITY_FORWARD.click();
		logger.info("Successfully clicked on next button.");
		test_steps.add("Successfully clicked on next button.");
		
		String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
		List<WebElement> lstAvailabilityDays = driver.findElements(By.xpath(AVAILABILITY_DAYS));
		
		if(lstAvailabilityDays.size() == 15)
		{
			test_steps.add("Successfully verified 15 days are showing when clicked on next button. ");
			logger.info("Successfully verified 15 days are showing when clicked on next button. ");
		}
		for(int index = 0; index < roomClassList.size(); index++)
		{
			String roomClassName = roomClassList.get(index);
			verifyRoomClassAndItsDataInBookingEngine(driver,roomClassName,new ArrayList<String>(),false,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
		}
		
		Wait.WaitForElement(driver, OR_BE.AVAILABILITY_BACKWARD);
		elements.AVAILABILITY_BACKWARD.click();
		
		return test_steps;
	}

	public ArrayList<String> verifyRoomClassAndItsDataInBookingEngine(WebDriver driver,String roomClass,ArrayList<String> channelDataValues,boolean verifyDataValueOfRoomClass,ArrayList<String> minStayRuleValues,ArrayList<String> checkInRuleValues,ArrayList<String> checkOutRuleValues) {
		ArrayList<String> test_steps = new ArrayList<>();
		boolean dataValuesMatch = false;
		Boolean[] boolArray =  new Boolean[2];
 		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		Wait.WaitForElement(driver, OR_BE.AVAILABILITY_ROOM_CLASSES);
		List<WebElement> lstRoomClasses = elements.AVAILABILITY_ROOM_CLASSES;
		
			for(WebElement objRoomClass : lstRoomClasses)
			{
				String foundRoomClass = objRoomClass.getText();
				if(roomClass.equalsIgnoreCase(foundRoomClass))
				{
					logger.info("Found : "+ foundRoomClass);
					test_steps.add("Successfully verified '"+foundRoomClass+"' exists.");
					logger.info("Successfully verified '"+foundRoomClass+"' exists.");
					if(verifyDataValueOfRoomClass == true)
					{
						boolArray = verifyRoomClassDataValues(driver,channelDataValues,roomClass,minStayRuleValues,checkInRuleValues,checkOutRuleValues);
						if(boolArray[0])
						{
							test_steps.add("Successfully verified '"+foundRoomClass+"' data values are matching.");
							logger.info("Successfully verified '"+foundRoomClass+"' data values are matching.");
						}
						if(boolArray[1])
						{
							test_steps.add("Successfully verified '"+foundRoomClass+"' min nights values are matching.");
							logger.info("Successfully verified '"+foundRoomClass+"' min nights values are matching.");
						}
						else if(boolArray[0] == false || boolArray[1] == false)
						{
							test_steps.add("Failed to verify values for :  '"+foundRoomClass+"'.");
							logger.info("Failed to verify values for : '"+foundRoomClass+"'.");
						}
					}
				}
			}
		
		return test_steps;
	}

	public Boolean[] verifyRoomClassDataValues(WebDriver driver,ArrayList<String> channelDataValues,String roomClass,ArrayList<String> minStayRuleValues,ArrayList<String> checkInRuleValues,ArrayList<String> checkOutRuleValues){
		
		boolean dataValuesMatch = false;
		boolean midNightStayValuesMatch =  false;
		Boolean[] boolArray = new Boolean[2];
		String roomClassDataValuesInAvailabilityPath="//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button/span";
		Wait.WaitForElement(driver, roomClassDataValuesInAvailabilityPath);
		 ArrayList<String> roomClassMinNightsInAvailability = new ArrayList<>();
		 roomClassDataValuesInAvailability = new ArrayList<String>();
		 List<WebElement> lstOfRoomClassDataValues= driver.findElements(By.xpath(roomClassDataValuesInAvailabilityPath));
		 ArrayList<String> channelDataValuesForComparison = new ArrayList<String>(channelDataValues.subList(0, lstOfRoomClassDataValues.size()));
		 ArrayList<String> channelMinNightStayValuesForComparison = new ArrayList<String>(minStayRuleValues.subList(0, lstOfRoomClassDataValues.size()));
		for(int i=0;i< lstOfRoomClassDataValues.size() ;i++)
		{
			roomClassDataValuesInAvailability.add(Utility.RemoveDollarandSpaces(driver, lstOfRoomClassDataValues.get(i).getText()).split("\\.")[0]);
		}
		for(int i=1;i<= lstOfRoomClassDataValues.size() ;i++)
		{
			String minNightsPath ="//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li["+i+"]/button/div/i[@class='GridRate__xIcon___1JSbn']";
			if(driver.findElements(By.xpath(minNightsPath)).size() > 0)
			{
				String minStayValue = driver.findElement(By.xpath(minNightsPath)).getText();
				roomClassMinNightsInAvailability.add(minStayValue);
				logger.info("minStayValue :" + minStayValue);
			}
			else
			{
				roomClassMinNightsInAvailability.add("");
				logger.info("minStayValue is null");
			}
		}
		logger.info("Expected :" + channelDataValuesForComparison);
		logger.info("Found :" + roomClassDataValuesInAvailability);
		
		logger.info("Expected Min Night Stay Values :" + channelMinNightStayValuesForComparison);
		logger.info("Found Min Night Stay Values :" + roomClassMinNightsInAvailability);
		
		if(channelDataValuesForComparison.equals(roomClassDataValuesInAvailability))
		{
			dataValuesMatch = true;
		}
		if(channelMinNightStayValuesForComparison.equals(roomClassMinNightsInAvailability))
		{
			midNightStayValuesMatch = true;
		}
		boolArray[0] = dataValuesMatch;
		boolArray[1] = midNightStayValuesMatch;
		
		return boolArray;
	}

	public ArrayList<String> verifySelectADateButtonIsDisplaying(WebDriver driver) {
		ArrayList<String> test_steps = new ArrayList<>();
		String selectADateButtonPath = "//button[text()='Select a Date']";
		if(driver.findElements(By.xpath(selectADateButtonPath)).size() > 0)
		{
			test_steps.add("Successfully verified Select a Date button exists.");
			logger.info("Successfully verified Select a Date button exists");
		}
		
		return test_steps;
	}
	
	public ArrayList<String> clickOnRateValueAtCheckInDate(WebDriver driver,String roomClass,int days,String checkInDate,String checkOutDate,String adults,String childs,String basicAmount,String ratePlanName,String dateFormat) throws ParseException {
		ArrayList<String> test_steps = new ArrayList<>();
		boolean noCheckInRuleValue = false;
		boolean checkIndate = false;
		String checkInDateRatePath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button";
		Wait.WaitForElement(driver, checkInDateRatePath);
		List<WebElement> roomClassRates = driver.findElements(By.xpath(checkInDateRatePath));
		if(roomClassRates.size() > 0 )
		{
			String minNightsNumberPath = "(//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button)[1]/div/i[@class='GridRate__xIcon___1JSbn']";
			if(driver.findElements(By.xpath(minNightsNumberPath)).size() > 0)
			{
				minNights = Integer.parseInt(driver.findElement(By.xpath(minNightsNumberPath)).getText());
				if(days<minNights)
				{
					blnDaysLessThanMinNights = true;
				}
			}
			String noCheckInDivPath = "(//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button)[1]/div";
			if(driver.findElements(By.xpath(noCheckInDivPath)).size() > 0)
			{
				String noCheckInElementPath = "(//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button)[1]/div/i[@class='be-icon-No-Arrivals']";
				if(driver.findElements(By.xpath(noCheckInElementPath)).size() > 0)
				{
					noCheckInRuleValue = true ; 
				}
			}
			
			ArrayList<String> datesList = new ArrayList<String>();
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

			String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
			
			List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
			for (WebElement ele : lstDatesElements) {
				datesList.add(ele.getText());
			}

			logger.info("Availability Day's Are: " + datesList);

			for (int i = 0; i < datesList.size(); i++) {
				for (int j = 0; j < dayList.size(); j++) {
					if (datesList.get(i).equals(dayList.get(j))) {
						checkInDateIndex = i;
						checkIndate = true;
					}
				}
				if(checkIndate)
				{
					break;
				}

			}
			
			roomClassRates.get(checkInDateIndex).click();
			test_steps.add("Successfully clicked on Check In Date Rate Value.");
			logger.info("Successfully clicked on Check In Date Rate Value.");
			roomClassRates.get(checkInDateIndex+days).click();
			test_steps.add("Successfully clicked on Check Out Date Rate Value.");
			logger.info("Successfully clicked on Check Out Date Rate Value.");
			
			test_steps.addAll(VerifyToolTipDetails(driver,checkInDate,checkOutDate,adults,childs,basicAmount,days,roomClass,ratePlanName,minNights,noCheckInRuleValue,dateFormat));
		}
		
		return test_steps;
	}

	private ArrayList<String> VerifyToolTipDetails(WebDriver driver, String checkInDate, String checkOutDate, String adults,
			String childs,String basicAmount,int days,String roomClass,String ratePlanName,int minNights,boolean noCheckInRuleValue,String dateFormat) throws ParseException {

		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);

		String reformatCheckInDate = ESTTimeZone.reformatDate(checkInDate, "MM/d/yyyy", "d/MM/yyyy");
		String reformatCheckOutDate = ESTTimeZone.reformatDate(checkOutDate, "MM/d/yyyy", "d/MM/yyyy");
		String formattedCheckInDate  = Utility.get_Month(reformatCheckInDate)+" "+reformatCheckInDate.split("/")[0].toString()+", "+checkInDate.split("/")[2].toString();
		String formattedCheckOutDate =  Utility.get_Month(reformatCheckOutDate)+" "+reformatCheckOutDate.split("/")[0].toString()+", "+checkOutDate.split("/")[2].toString();
		
		ArrayList<String> test_steps = new ArrayList<>();
		HashMap<String, String> roomClassDataValuesAtSelectedDateRange = new HashMap<String,String>();
		ArrayList<String> datesList = new ArrayList<String>();
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

		String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
		
		List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
		for (WebElement ele : lstDatesElements) {
			datesList.add(ele.getText());
		}

		logger.info("Availability Day's Are: " + datesList);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					roomClassDataValuesAtSelectedDateRange.put(dayList.get(j), roomClassDataValuesInAvailability.get(i));
				}
			}

		}
		int calculatedAmount  = 0;
		int count  = 0;
		
		for (Map.Entry<String,String> entry : roomClassDataValuesAtSelectedDateRange
				.entrySet()) {
			count+=1;
			if(count < roomClassDataValuesAtSelectedDateRange.size())
			{
				String value = entry.getValue();
				calculatedAmount+=Integer.parseInt(value);
			}
		}
		
		
		Wait.WaitForElement(driver, OR_BE.CHECK_IN);
		if (elements.CHECK_IN.isDisplayed()) {
			
			String checkInDateFound = elements.CHECK_IN_DATE.getText();
			assertEquals(checkInDateFound, formattedCheckInDate, "Failed: Check In date didn't verify");
			test_steps.add("Check In date :" + checkInDateFound + " Verified");
			logger.info("Check In date :" + checkInDateFound + " Verified");
		}
		Wait.WaitForElement(driver, OR_BE.CHECK_OUT);
		if (elements.CHECK_OUT.isDisplayed()) {
			String checkOutDateText = elements.CHECK_OUT_DATE.getText();
			assertEquals(checkOutDateText, formattedCheckOutDate, "Failed: Check Out date didn't verify");
			test_steps.add("Check Out date :" + checkOutDateText + " Verified");
			logger.info("Check Out date :" + checkOutDateText + " Verified");
		}
		
		String adultsFound = elements.NO_OF_ADULTS.getAttribute("value");
		assertEquals(adults, adultsFound, "Failed: No of adults didn't verify");
		test_steps.add("No of Adults :" + adultsFound + " Verified");
		logger.info("No of Adults :" + adultsFound + " Verified");
		
		String childFound = elements.NO_OF_CHILDREN.getAttribute("value");
		assertEquals(childs, childFound, "Failed: No of childs didn't verify");
		test_steps.add("No of childs :" + childFound + " Verified");
		logger.info("No of childs :" + childFound + " Verified");
		
		String amountFound = elements.VIEW_RATES_FROM.getText().split("\\$")[1].split("\\.")[0].toString();
		int amountFnd = Integer.parseInt(amountFound);
		assertEquals(calculatedAmount, amountFnd, "Failed: Amount didn't verify");
		test_steps.add("Basic Amount :" + amountFnd + " Verified");
		logger.info("Basic Amount :" + amountFnd + " Verified");
		
		test_steps.addAll(navigateToRoomClassAndVerifyItsDetails(driver,roomClass,formattedCheckInDate,formattedCheckOutDate,adults,days,ratePlanName,minNights,noCheckInRuleValue));
		
		return test_steps;
	}
	
	private ArrayList<String> navigateToRoomClassAndVerifyItsDetails(WebDriver driver,String roomClass,String formattedCheckInDate,String formattedCheckOutDate,String adults,int days,String ratePlanName,int minNights,boolean noCheckInRuleValue)
	{
		ArrayList<String> test_steps = new ArrayList<>();
		String roomClassDetailsPagePath="//button[@type='submit']";
		Wait.WaitForElement(driver, roomClassDetailsPagePath);
		WebElement element = driver.findElement(By.xpath(roomClassDetailsPagePath));
		
		if(element.isDisplayed())
		{
			element.click();
			String roomClassPath = "//div[@class='room__header___21eER']";
			if(driver.findElements(By.xpath(roomClassPath)).size() > 0)
			{
				test_steps.add("Successfully navigate to '" + roomClass + " 'details page.");
				logger.info("Successfully navigate to '" + roomClass + " 'details page.");
			}
		}
		
		String reservationDetailsPath = "//h2[text()='Reservation Details']";
		Wait.WaitForElement(driver, reservationDetailsPath);
		WebElement reservationDetailElement = driver.findElement(By.xpath(reservationDetailsPath));
		if(reservationDetailElement.isDisplayed())
		{
			String checkInDatePath = "//label[text()='Check in']/following-sibling::p";
			WebElement checkInDateElement = driver.findElement(By.xpath(checkInDatePath));
			String checkInDateText =checkInDateElement.getText();
			assertEquals(formattedCheckInDate, checkInDateText, "Failed: Check In date didn't verify");
			test_steps.add("Check In date :" + checkInDateText + " Verified");
			logger.info("Check In date :" + checkInDateText + " Verified");
			
			
			String checkOutDatePath = "//label[text()='Check out']/following-sibling::p";
			WebElement checkOutDateElement = driver.findElement(By.xpath(checkOutDatePath));
			String checkOutDateText =checkOutDateElement.getText();
			assertEquals(formattedCheckOutDate, checkOutDateText, "Failed: Check Out date didn't verify");
			test_steps.add("Check Out date :" + checkOutDateText + " Verified");
			logger.info("Check Out date :" + checkOutDateText + " Verified");
			
			String adultsPath = "//span[@class='CA__adults']";
			WebElement adultsPathElement = driver.findElement(By.xpath(adultsPath));
			String adultsPathElementText =adultsPathElement.getText().split(" ")[0].toString();
			assertEquals(adults, adultsPathElementText, "Failed: No of Adults didn't verify");
			test_steps.add("No of Adults :" + adultsPathElementText + " Verified");
			logger.info("No of Adults :" + adultsPathElementText + " Verified");
			
			String checkAvailabilityButtonPth = "//span[text()='Check Availability']";
			if(driver.findElements(By.xpath(checkAvailabilityButtonPth)).size() > 0)
			{
				test_steps.add("Successfully Verified that Check Availability button exists for : "+ roomClass);
				logger.info("Successfully Verified that Check Availability button exists for : "+ roomClass);
			}
			else
			{
				test_steps.add("Successfully Verified that Check Availability button does not exist for : "+ roomClass);
				logger.info("Successfully Verified that Check Availability button does not exist for : "+ roomClass);
			}

		}
		
		test_steps.addAll(verifyRatePlanIsShowingOrNot(driver,ratePlanName,days,minNights,noCheckInRuleValue));
		
		return test_steps;
	}
	
	
	private ArrayList<String> verifyRatePlanIsShowingOrNot(WebDriver driver,String ratePlanName,int days,int minNights,boolean noCheckInRuleValue)
	{
		ArrayList<String> test_steps = new ArrayList<>();
		String ratePlanPath= "//span[@class='CA__ratePlanName'][text()='"+ratePlanName+"']";
		if(noCheckInRuleValue)
		{
			if(driver.findElements(By.xpath(ratePlanPath)).size() == 0)
			{
				test_steps.add("Successfully Verified '"+ratePlanName+"' is not showing.");
				logger.info("Successfully Verified '"+ratePlanName+"' is not showing.");
			}
			assertEquals(0, driver.findElements(By.xpath(ratePlanPath)).size(), "Failed: '"+ratePlanName+"' exists . ");
		}
		else
		{
			if(days<minNights)
			{
				if(driver.findElements(By.xpath(ratePlanPath)).size() == 0)
				{
					test_steps.add("Successfully Verified '"+ratePlanName+"' is not showing.");
					logger.info("Successfully Verified '"+ratePlanName+"' is not showing.");
				}
				assertEquals(0, driver.findElements(By.xpath(ratePlanPath)).size(), "Failed: '"+ratePlanName+"' exists . ");
			}
			else
			{
				if(driver.findElements(By.xpath(ratePlanPath)).size() > 0)
				{
					test_steps.add("Successfully Verified '"+ratePlanName+"' is showing.");
					logger.info("Successfully Verified '"+ratePlanName+"' is showing.");
					String bookroomElementPath = "(//span[@class='CA__ratePlanName'][text()='BE NightlyRatePlan']//..//..//following-sibling::button)[2]/span";
					assertEquals(1, driver.findElements(By.xpath(bookroomElementPath)).size(), "Failed: Book Room button does not exist.");
					test_steps.add("Successfully Verified Book Room Button exists.");
					logger.info("Successfully Verified Book Room Button exists.");
				}
				assertEquals(1, driver.findElements(By.xpath(ratePlanPath)).size(), "Failed: '"+ratePlanName+"' does not exist . ");
			}
		}
		
		
		return test_steps;
	}

	public void navigateBackToAvailabilityPage(WebDriver driver) {
		
		String navigateBackToAvailabilityPagePath = "//span[text()='Back to Availability Chart']";
		Wait.WaitForElement(driver, navigateBackToAvailabilityPagePath);
		driver.findElement(By.xpath(navigateBackToAvailabilityPagePath)).click();
		logger.info("Successfully clicked on Back to Availability Chart link.");
	}

	public ArrayList<String> verifyAmountIsNotShowingWithoutRatePlan(WebDriver driver,String startDate) throws ParseException, InterruptedException
	{
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_BE BE = new Elements_BE(driver);
		
		clickOnStartDate(driver);
		testSteps.add("Click On Check-In date");
		logger.info("Click On Check-In date");
		String getMonth = getMonthFromCalendarHeading1(driver);
		logger.info("getMonth: " + getMonth);
		String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

		boolean isMothEqual = false;
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(startDate, "dd/MMM/yyyy", "MMMM yyyy");
		String getStartDate = ESTTimeZone.getDateBaseOnDate(startDate, "dd/MMM/yyyy", "EEEE, MMMM d, yyyy");
		
		
		logger.info("expectedMonth: " + expectedMonth);
		String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
		System.out.println("path: " + path);
		
		// this is for start date
				while (!isMothEqual) {
					if (expectedMonth.equals(getMonth)) {
						logger.info("In first part");
						isMothEqual = true;
						break;
					}
					if (expectedMonth.equals(getMonthFromSecondPart)) {
						logger.info("in a second part");
						isMothEqual = true;
						break;
					} else {
						logger.info("in else");
						clickOnCalendarRightArrow1(driver);
						getMonth = getMonthFromCalendarHeading1(driver);
						getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

						logger.info("getMonth: " + getMonth);
					}

				}

		
		
		// get tooltip rate Value 
		WebElement hoverElement = driver.findElement(By.xpath(path));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		testSteps.add("Hovers over Check-IN Date '" + startDate+"'");

		Wait.WaitForElement(driver,  OR_BE.startDateToolTipRateValue);
		//Wait.waitForElementToBeVisibile(By.xpath( OR_BE.startDateToolTipRateValue), driver);
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath( OR_BE.startDateToolTipRateValue)), driver);
		
		String rateValue = BE.startDateToolTipRateValue.getText();
		
		
		testSteps.add("Successfully Verified '"+rateValue+"' is showing.");
		logger.info("Successfully Verified '"+rateValue+"' is showing.");
		
		return testSteps;
	}
	
	
	public ArrayList<String> getMinNightsDataInBookingEngine(WebDriver driver,String roomClass)
	{
		String roomClassDataValuesInAvailabilityPath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"
				+ roomClass + "']//..//..//following-sibling::ul/li/button/span";
		Wait.WaitForElement(driver, roomClassDataValuesInAvailabilityPath);
		ArrayList<String> roomClassMinNightsInAvailability = new ArrayList<>();
		List<WebElement> lstOfRoomClassDataValues = driver
				.findElements(By.xpath(roomClassDataValuesInAvailabilityPath));
		for (int i = 1; i <= lstOfRoomClassDataValues.size(); i++) {
			String minNightsPath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='" + roomClass
					+ "']//..//..//following-sibling::ul/li[" + i + "]/button/div/i[@class='GridRate__xIcon___1JSbn']";
			if (driver.findElements(By.xpath(minNightsPath)).size() > 0) {
				String minStayValue = driver.findElement(By.xpath(minNightsPath)).getText();
				roomClassMinNightsInAvailability.add(minStayValue);
			} else {
				roomClassMinNightsInAvailability.add("");
			}
		}
		return roomClassMinNightsInAvailability;
	}

	public ArrayList<String> verifyMinNightsDataInBookingEngine(WebDriver driver, String roomClassName,
			String checkInDate, String checkOutDate,String dateFormat,HashMap<String, String> minNightsStayDataInInnCenter ) throws ParseException {
		
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> minNightsDataValues = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> minNightsDataValuesMap = new HashMap<String, String>();
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
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		
		String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
		
		List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
		for (WebElement ele : lstDatesElements) {
			datesList.add(ele.getText());
		}

		logger.info("Availability Day's Are: " + datesList);
		minNightsDataValues = getMinNightsDataInBookingEngine(driver, roomClassName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					minNightsDataValuesMap.put(dayList.get(j), minNightsDataValues.get(i));
					test_Steps.add("Min Stay data value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" : "+  minNightsDataValues.get(i));
				}
			}

		}
		
		if(minNightsStayDataInInnCenter.equals(minNightsDataValuesMap))
		{
			test_Steps.add("Successfully Verified Min Stay Rule values are matching between "+checkInDate+" and "+ checkOutDate);
			logger.info("Successfully Verified Min Stay Rule values are matching between "+checkInDate+" and "+ checkOutDate);
		}
		else
		{
			test_Steps.add("Failed to verify data between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
			logger.info("Failed to verify data between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
		}
		
		return test_Steps;
	}

	public ArrayList<String> verifyCheckInDataInBookingEngine(WebDriver driver, String roomClassName,
			String checkInDate, String checkOutDate, String dateFormat, HashMap<String, String> noCheckInDataInInnCenter) throws ParseException {
		
		
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> noCheckInDataValuesMap = new HashMap<String, String>();
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
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		
		String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
		
		List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
		for (WebElement ele : lstDatesElements) {
			datesList.add(ele.getText());
		}

		logger.info("Availability Day's Are: " + datesList);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					int index = i+1;
					String checkInElementPath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClassName+"']//..//..//following-sibling::ul/li["+index+"]/button/span//following-sibling::div/i[@class='be-icon-No-Arrivals']";
					String checkInValue = "";
					if(driver.findElements(By.xpath(checkInElementPath)).size() > 0)
					{
						checkInValue = "Yes";
						noCheckInDataValuesMap.put(dayList.get(j), "Yes");
					}
					else
					{
						checkInValue = "No";
						noCheckInDataValuesMap.put(dayList.get(j), "No");
					}
					test_Steps.add("No Check In data value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  checkInValue);
					logger.info("No Check In data value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  checkInValue);
				}
			}

		}
		
		if(noCheckInDataInInnCenter.equals(noCheckInDataValuesMap))
		{
			test_Steps.add("Successfully Verified No Check In Rule values are matching between "+checkInDate+" and "+ checkOutDate);
			logger.info("Successfully Verified No Check In Rule values values are matching between "+checkInDate+" and "+ checkOutDate);
		}
		else
		{
			test_Steps.add("Failed to verify data for No Check In Rule between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
			logger.info("Failed to verify data for No Check In Rule between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
		}
		return test_Steps;
	}

	public ArrayList<String> verifyCheckOutDataInBookingEngine(WebDriver driver, String roomClassName,
			String checkInDate, String checkOutDate, String dateFormat, HashMap<String, String> noCheckOutDataInInnCenter) throws ParseException {
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> noCheckOutDataValuesMap = new HashMap<String, String>();
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
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		
		String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
		
		List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
		for (WebElement ele : lstDatesElements) {
			datesList.add(ele.getText());
		}

		logger.info("Availability Day's Are: " + datesList);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					int index = i+1;
					String checkInElementPath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClassName+"']//..//..//following-sibling::ul/li["+index+"]/button/span//following-sibling::div/i[@class='be-icon-No-Departures']";
					String checkInValue = "";
					if(driver.findElements(By.xpath(checkInElementPath)).size() > 0)
					{
						checkInValue = "Yes";
						noCheckOutDataValuesMap.put(dayList.get(j), "Yes");
					}
					else
					{
						checkInValue = "No";
						noCheckOutDataValuesMap.put(dayList.get(j), "No");
					}
					test_Steps.add("No Check Out data value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  checkInValue);
					logger.info("No Check Out data value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  checkInValue);
				}
			}

		}
		
		if(noCheckOutDataInInnCenter.equals(noCheckOutDataValuesMap))
		{
			test_Steps.add("Successfully Verified No Check Out Rule values are matching between "+checkInDate+" and "+ checkOutDate);
			logger.info("Successfully Verified No Check Out Rule values values are matching between "+checkInDate+" and "+ checkOutDate);
		}
		else
		{
			test_Steps.add("Failed to verify data for No Check Out Rule between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
			logger.info("Failed to verify data for No Check Out Rule between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
		}
		return test_Steps;
	}

	public ArrayList<String> verifyRateValuesInBookingEngine(WebDriver driver, String roomClassName, String checkInDate,
			String checkOutDate, String dateFormat, HashMap<String, String> rateValuesMap) throws ParseException {
		
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> rateValuesMapInBookingEngine = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, checkInDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, checkOutDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);
		
		String roomClassDataValuesInAvailabilityPath="//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClassName+"']//..//..//following-sibling::ul/li/button/span";
		Wait.WaitForElement(driver, roomClassDataValuesInAvailabilityPath);
		 ArrayList<String> roomClassDataValuesInAvailability = new ArrayList<>();
		 List<WebElement> lstOfRoomClassDataValues= driver.findElements(By.xpath(roomClassDataValuesInAvailabilityPath));
		for(int i=0;i< lstOfRoomClassDataValues.size() ;i++)
		{
			roomClassDataValuesInAvailability.add(Utility.RemoveDollarandSpaces(driver, lstOfRoomClassDataValues.get(i).getText()).split("\\.")[0]);
		}

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		
		String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
		
		
		List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
		for (WebElement ele : lstDatesElements) {
			datesList.add(ele.getText());
		}

		logger.info("Availability Day's Are: " + datesList);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					rateValuesMapInBookingEngine.put(dayList.get(j), roomClassDataValuesInAvailability.get(i));
					test_Steps.add("Rate value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  roomClassDataValuesInAvailability.get(i));
					logger.info("Rate value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  roomClassDataValuesInAvailability.get(i));
				}
			}

		}
		
		if(rateValuesMap.equals(rateValuesMapInBookingEngine))
		{
			test_Steps.add("Successfully Verified rate values are matching between "+checkInDate+" and "+ checkOutDate);
			logger.info("Successfully Verified rate values values are matching between "+checkInDate+" and "+ checkOutDate);
		}
		else
		{
			test_Steps.add("Failed to verify data for rate values between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
			logger.info("Failed to verify data for rate values between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
		}
		return test_Steps;
	}
	
	public String clickOnBookingEngineConfigLink(WebDriver driver)
	{
		String bookingEngineConfigElementPath = "//a[@id='MainContent_rptBookingEngineConfig_lbBookingEngineConfig_0']";
		Wait.WaitForElement(driver, bookingEngineConfigElementPath);
		String getURL = "";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			WebElement bookingEngineConfigElement = driver.findElement(By.xpath(bookingEngineConfigElementPath));
			Utility.ScrollToElement(bookingEngineConfigElement, driver);
			if(driver.findElements(By.xpath(bookingEngineConfigElementPath)).size() > 0)
			{
				getURL = bookingEngineConfigElement.getText();
				jse.executeScript("arguments[0].click();", bookingEngineConfigElement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getURL;
	}
	
	public ArrayList<String> verifyDifferentAdultsAndchildrenCombinations(WebDriver driver,String roomClass,int days,String ratePlanAdultCapicity,String ratePlanPersonCapicity,String adults,String childs)
	{
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		String checkInDateRatePath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button";
		Wait.WaitForElement(driver, checkInDateRatePath);
		List<WebElement> roomClassRates = driver.findElements(By.xpath(checkInDateRatePath));
		roomClassRates.get(0).click();
		roomClassRates.get(days).click();
		String xpathOfViewRateFromButton = "//button[@type='submit']/span//parent::button";
		
		int adultsvalue = Integer.parseInt(ratePlanAdultCapicity) + 1;
		String adultsStringValue = String.valueOf(adultsvalue);
		enterAdults(driver,adultsStringValue);
		if(Integer.parseInt(adultsStringValue) > Integer.parseInt(ratePlanAdultCapicity))
		{
			if(elements.NO_OF_ADULTS.getAttribute("class").contains("RatePopup__error___2PkFn"))
			{
				testSteps.add("Successfully verified adults boundry is highlighted. ");
				logger.info("Successfully verified adults boundry is highlighted.");
			}
			Wait.WaitForElement(driver, xpathOfViewRateFromButton);
			if(!driver.findElement(By.xpath(xpathOfViewRateFromButton)).isEnabled())
			{
				testSteps.add("Successfully verified View Rate From link is disabled. ");
				logger.info("Successfully verified View Rate From link is disabled.");
			}
		}
		adultsvalue = Integer.parseInt(ratePlanAdultCapicity) - 1;
		adultsStringValue = String.valueOf(adultsvalue);
		enterAdults(driver,adultsStringValue);
		int childValue = Integer.parseInt(ratePlanPersonCapicity)+1;
		String childStringValue = String.valueOf(childValue);
		enterChildren(driver,childStringValue);
		if((Integer.parseInt(childStringValue)+ Integer.parseInt(adults)) > Integer.parseInt(ratePlanAdultCapicity))
		{
			if(elements.NO_OF_ADULTS.getAttribute("class").contains("RatePopup__error___2PkFn") && elements.NO_OF_CHILDREN.getAttribute("class").contains("RatePopup__error___2PkFn"))
			{
				testSteps.add("Successfully verified adults and childs boundry is highlighted because their sum exceed maximum person capacity. ");
				logger.info("Successfully verified adults and childs boundry is highlighted because their sum exceed maximum person capacity. ");
			}
			Wait.WaitForElement(driver, xpathOfViewRateFromButton);
			if(!driver.findElement(By.xpath(xpathOfViewRateFromButton)).isEnabled())
			{
				testSteps.add("Successfully verified View Rate From link is disabled. ");
				logger.info("Successfully verified View Rate From link is disabled.");
			}
		}
		
		
		return testSteps;
	}

	private ArrayList<String> enterAdults(WebDriver driver, String adults) {
		
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Wait.WaitForElement(driver, OR_BE.NO_OF_ADULTS);
		elements.NO_OF_ADULTS.click();
		elements.NO_OF_ADULTS.clear();
		elements.NO_OF_ADULTS.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		elements.NO_OF_ADULTS.sendKeys(Keys.BACK_SPACE);
		elements.NO_OF_ADULTS.sendKeys(adults);
		testSteps.add("Enter No of adults : " + adults);
		logger.info("Enter No of adults : " + adults);
		return testSteps;
	}
	
private ArrayList<String> enterChildren(WebDriver driver, String adults) {
		
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Wait.WaitForElement(driver, OR_BE.NO_OF_CHILDREN);
		elements.NO_OF_CHILDREN.click();
		elements.NO_OF_CHILDREN.clear();
		elements.NO_OF_CHILDREN.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		elements.NO_OF_CHILDREN.sendKeys(Keys.BACK_SPACE);
		elements.NO_OF_CHILDREN.sendKeys(adults);
		testSteps.add("Enter No of childs : " + adults);
		logger.info("Enter No of childs : " + adults);
		return testSteps;
	}

public ArrayList<String> verifyAvailabilityDataInBookingEngine(WebDriver driver, String roomClassName,
		String checkInDate, String checkOutDate, String dateFormat, HashMap<String, String> avalabilityDataInnCenter) throws ParseException {
	
	
	ArrayList<String> test_Steps = new ArrayList<>();
	ArrayList<String> datesList = new ArrayList<String>();
	HashMap<String, String> availabilityDataValuesMap = new HashMap<String, String>();
	ArrayList<String> dayList = new ArrayList<String>();
	ArrayList<String> dateList = new ArrayList<String>();
	List<Date> dates = new ArrayList<Date>();
	String blackOutDateIsClickableOrNot = "";

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
	test_Steps.add("Day's  from Start date and End Date: " + dayList);
	logger.info("Date List from Start date and End Date: " + dateList);
	test_Steps.add("Date List from Start date and End Date: " + dateList);
	
	String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
	
	List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
	for (WebElement ele : lstDatesElements) {
		datesList.add(ele.getText());
	}

	logger.info("Availability Day's Are: " + datesList);

	for (int i = 0; i < datesList.size(); i++) {
		for (int j = 0; j < dayList.size(); j++) {
			if (datesList.get(i).equals(dayList.get(j))) {
				int index = i+1;
				String availabiltyElementPath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClassName+"']//..//..//following-sibling::ul/li["+index+"]/button/i[@class='GridRate__noAvailabilityIcon___2ShJ6 be-icon-No-Availability']";
				if(driver.findElements(By.xpath(availabiltyElementPath)).size() > 0)
				{
						String availabilityValue = "Blackout";
						availabilityDataValuesMap.put(dayList.get(j), availabilityValue);
						blackOutDateIsClickableOrNot = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClassName+"']//..//..//following-sibling::ul/li["+index+"]/button";
						WebElement hoverElement = driver.findElement(By.xpath(blackOutDateIsClickableOrNot));
						Actions builder = new Actions(driver);
						builder.moveToElement(hoverElement).perform();
						logger.info("Hovers over blackOut Date : " + dayList.get(j));
						test_Steps.add("Hovers over blackOut Date : "+ dayList.get(j));
						if(driver.findElement(By.xpath(blackOutDateIsClickableOrNot)).getAttribute("style").contains("background-color: rgb"))
						{
							logger.info("Failed : BlackOut date '"+dayList.get(j)+"' is clickable .");
							test_Steps.add("Failed : BlackOut date '"+dayList.get(j)+"' is clickable .");
						}
						else
						{
							logger.info("Successfully verified blackOut date '"+dayList.get(j)+"'  is not clickable .");
							test_Steps.add("Successfully verified blackOut date '"+dayList.get(j)+"'  is not clickable .");
						}

						test_Steps.add("Availability value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  availabilityValue);
						logger.info("Availability value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  availabilityValue);
				}
				else
				{
					String availabilityValue = "Available";
					availabilityDataValuesMap.put(dayList.get(j), availabilityValue);
					test_Steps.add("Availability value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  availabilityValue);
					logger.info("Availability value for '"+roomClassName+"' as per  Date  "+dayList.get(j)+" in booking Engine : "+  availabilityValue);
				}
			}
		}

	}
	
	if(avalabilityDataInnCenter.equals(availabilityDataValuesMap))
	{
		test_Steps.add("Successfully Verified Availability values are matching between "+checkInDate+" and "+ checkOutDate);
		logger.info("Successfully Verified Availability values are matching between "+checkInDate+" and "+ checkOutDate);
	}
	else
	{
		test_Steps.add("Failed to verify data for Availability between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
		logger.info("Failed to verify data for Availability  between "+checkInDate+" and "+ checkOutDate+" for '" +roomClassName + "'");
	}
	return test_Steps;
}
	
	public ArrayList<String> verifyNoRoomAvailableMessageDisplayed(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE bookingEngine = new Elements_BE(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.No_ROOMS_AVAILABLE_MESSAGE), driver);
		if(bookingEngine.No_ROOMS_AVAILABLE_MESSAGE.isDisplayed()) {
		testSteps.add("Sorry! no rooms available for selected criteria message displayed");
		logger.info("Sorry! no rooms available for selected criteria message displayed");
		}
		return testSteps;
	}
	
	public ArrayList<String> clickBookRoomWithoutSkip(WebDriver driver, String ratePlanName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		String bookRoomButton = "//span[text()='" + ratePlanName + "']/parent::h2/following-sibling::button";
		WebElement bookRoomButtonElement = driver.findElement(By.xpath(bookRoomButton));
		Utility.ScrollToElement(bookRoomButtonElement, driver);
		bookRoomButtonElement.click();
		testSteps.add("Book Room Button of Rate Plan:" + ratePlanName + " Clicked ");
		logger.info("Book Room Button of Rate Plan:" + ratePlanName + " Clicked ");
		return testSteps;
	}
	
	public ArrayList<String> skipAddProductsPart(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(BE.SKIP_THIS_STEP, driver);
			BE.SKIP_THIS_STEP.click();
			testSteps.add("Skip this Step Clicked");
			logger.info("Skip this Step Clicked");
		}catch(Exception e) {
			
		}
		return testSteps;
	}
	
	public ArrayList<String> verifyProductExist(WebDriver driver, String productName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> productsList = new ArrayList<>();
		String productNames = "//h3[contains(@class,'addOn__title')]";
		Wait.WaitForElement(driver, productNames);
		Wait.waitForElementToBeVisibile(By.xpath(productNames), driver);
		Wait.waitForElementToBeClickable(By.xpath(productNames), driver);
		List<WebElement> BEproductList = driver.findElements(By.xpath(productNames));
		Utility.ScrollToElement_NoWait(BEproductList.get(0), driver);
		boolean found = false;
		for(WebElement product: BEproductList) {
			if(product.getText().equals(productName)) {
				found = true;
			}
		}
		assertTrue(found,"Failed '"+ productName + "' not found in Booking Engine products list");
		logger.info("Successfully verified product '"+ productName + "' found in Booking Engine");
		testSteps.add("Successfully verified product '"+ productName + "' found in Booking Engine");
		return testSteps;
	}
	
	public ArrayList<String> verifyProductAmount(WebDriver driver, String product, String Amount) throws InterruptedException {
		
		if (Amount.contains(Utility.clientCurrencySymbol)) {
			Amount = Amount.split("\\"+Utility.clientCurrencySymbol)[1].trim();
			
		}
		Amount = String.format("%.2f", Double.parseDouble(Amount));
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		String productPricexpath = "//h3[contains(@class,'addOn__title') and text()='"+product+"']//following-sibling::*[contains(@class,'priceText')]";
		WebElement productPrice = driver.findElement(By.xpath(productPricexpath));
		Utility.ScrollToElement(productPrice, driver);
		String foundPrice = productPrice.getText();
		testSteps.add("Expected Product Amount : " + Amount);
		logger.info("Expected Product Amount : " + Amount);
		foundPrice = foundPrice.trim();
		logger.info("Found Product Amount '" + foundPrice + "'");
		foundPrice = foundPrice.split(" ")[0].trim();
		logger.info("Found Product Amount '" + foundPrice + "'");
		foundPrice = Utility.RemoveDollarandSpaces(driver, foundPrice);
		foundPrice = Utility.removeCurrencySignBracketsAndSpaces(foundPrice);
		foundPrice = String.format("%.2f", Double.parseDouble(foundPrice));
		testSteps.add("Found Product Amount '" + foundPrice + "'");
		logger.info("Found Product Amount '" + foundPrice + "'");
		
		assertEquals(foundPrice,Amount,"Failed: Amount missmatched");
		return testSteps;
	}

	public ArrayList<String> clickAddProduct(WebDriver driver, String productName, int count) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		String buttonXpath = "//h3[contains(@class,'addOn__title') and text()='"+productName+"']//following-sibling::ul//i[contains(@class,'plus')]//parent::button[contains(@class,'addOn__counterButton')]";
		WebElement button = driver.findElement(By.xpath(buttonXpath));
		Utility.ScrollToElement(button, driver);
		for(int i = 0 ; i < count ; i++) {
			button.click();
			testSteps.add("Click add product '" + productName + "' button");
			logger.info("Click add product '" + productName + "' button");
		}
		return testSteps;	
	}
	
	public ArrayList<String> clickRemoveProduct(WebDriver driver, String productName, int count) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE BE = new Elements_BE(driver);
		String buttonXpath = "//h3[contains(@class,'addOn__title') and text()='"+productName+"']//following-sibling::ul//i[contains(@class,'minus')]//parent::button[contains(@class,'addOn__counterButton')]";
		WebElement button = driver.findElement(By.xpath(buttonXpath));
		Utility.ScrollToElement(button, driver);
		for(int i = 0 ; i < count ; i++) {
			button.click();
			testSteps.add("Click remove product '" + productName + "' button");
			logger.info("Click remove product '" + productName + "' button");
		}
		return testSteps;	
	}
	
	public String getProductCount(WebDriver driver, String product,ArrayList<String> testSteps) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		String productCountXpath = "//h3[contains(@class,'addOn__title') and text()='"+product+"']//following-sibling::ul//span[contains(@class,'addOn__counter')]";
		WebElement productCount = driver.findElement(By.xpath(productCountXpath));
		Utility.ScrollToElement(productCount, driver);
		String foundProductCount = productCount.getText();
		//testSteps.add("Found Product Count : " + foundProductCount);
		logger.info("Found Product Count : " + foundProductCount);
		foundProductCount = foundProductCount.trim();
		return foundProductCount;
	}

	public ArrayList<String> addProduct(WebDriver driver, String productName, String count) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String currentCounter = null;
		currentCounter = getProductCount(driver, productName,testSteps);
		logger.info("Required Product Count : " + count);
		for (int i = 0; i < Integer.parseInt(count); i++) { 
			currentCounter = getProductCount(driver, productName,testSteps);
			int counterDifference = Integer.parseInt(count) - Integer.parseInt(currentCounter);
			logger.info("Product Count diff: " + counterDifference);
			if(counterDifference < 0) { 
				testSteps.addAll(clickRemoveProduct(driver,productName,counterDifference));
			}else if(counterDifference > 0) {
				testSteps.addAll(clickAddProduct(driver,productName,counterDifference));
			}else {
				break;
			}	
		}
		return testSteps;
		
	}

	public ArrayList<String> clickAddProductToReservation(WebDriver driver, String amount) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String buttonXpath = "//span[contains(text(),'Add $"+amount+" + Tax To My Reservation')]//parent::button";
		WebElement button = driver.findElement(By.xpath(buttonXpath));
		Utility.ScrollToElement(button, driver);
		button.click();
		testSteps.add("Click 'Add $"+amount+" + Tax To My Reservation' button");
		logger.info("Click 'Add $"+amount+" + Tax To My Reservation' button");
		return testSteps;
		
	}
	
	public String getProductAmount(WebDriver driver,String productName) throws InterruptedException {
		String xpath = "//span[text()='"+productName+"']//following-sibling::span[contains(@class,'price')]";
		WebElement productAmountValue = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(productAmountValue, driver);
		String productAmount = "";
		productAmount = Utility.convertDollarToNormalAmount(driver, productAmountValue.getText());
		productAmount = productAmount.replaceAll("[^0-9.]", "");
		return productAmount;
	}


	public ArrayList<String> clickOnViewEntireAvailabilityLinkThroughAdvancedOptions(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<String>();
			String advancedOptionButtonPath ="//button[@class='ADA__dropDownBtn dropdown-toggle btn DropDown__advancedDropdown___34OjK']";
			Wait.WaitForElement(driver, advancedOptionButtonPath);
			if(driver.findElement(By.xpath(advancedOptionButtonPath)).isDisplayed())
			{
				String advancedOptionDropDownPath = "//i[@class='glyphicon glyphicon-menu-down']";
				Wait.WaitForElement(driver, advancedOptionDropDownPath);
				driver.findElement(By.xpath(advancedOptionDropDownPath)).click();
				logger.info("Successfully Clicked on Advanced option dropdown.");
				testSteps.add("Successfully Clicked on Advanced option dropdown.");
				
				String entireAvailabilityLinkPath = "//a[text()='View Entire Availability']";
				Wait.WaitForElement(driver, entireAvailabilityLinkPath);
				driver.findElement(By.xpath(entireAvailabilityLinkPath));
				if(driver.findElements(By.xpath(entireAvailabilityLinkPath)).size() > 0)
				{
					logger.info("Successfully Verified View Entire Availability link exists.");
					testSteps.add("Successfully Verified View Entire Availability link exists.");
					driver.findElement(By.xpath(entireAvailabilityLinkPath)).click();
					logger.info("Successfully Clicked on View Entire Availability link through advanced options.");
					testSteps.add("Successfully Clicked on View Entire Availability link through advanced options.");
				}
				
			}
			
			return testSteps;
		}

		public ArrayList<String> verifyRoomClassesByAdvancedOption(WebDriver driver, String roomClass) {

			Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(
					driver);
			Wait.WaitForElement(driver, OR_BE.AVAILABILITY_ROOM_CLASSES);
			List<WebElement> lstRoomClasses = elements.AVAILABILITY_ROOM_CLASSES;
			ArrayList<String> testSteps = new ArrayList<String>();

			for (WebElement objRoomClass : lstRoomClasses) {
				String foundRoomClass = objRoomClass.getText();
				if (roomClass.equalsIgnoreCase(foundRoomClass)) {
					logger.info("Found : " + foundRoomClass);
					testSteps.add("Successfully verified '" + foundRoomClass + "' exists.");
					logger.info("Successfully verified '" + foundRoomClass + "' exists.");
				}
			}

			return testSteps;
		}

		public ArrayList<String> clickOnCheckInDate(WebDriver driver, String checkInDate, String checkOutDate,
				String roomClass, String dateFormat, int days) throws ParseException {

			ArrayList<String> testSteps = new ArrayList<String>();
			boolean checkIndate = false;
			String checkInDateRatePath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='" + roomClass
					+ "']//..//..//following-sibling::ul/li/button";
			Wait.WaitForElement(driver, checkInDateRatePath);
			List<WebElement> roomClassRates = driver.findElements(By.xpath(checkInDateRatePath));
			ArrayList<String> datesList = new ArrayList<String>();
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

			String AVAILABILITY_DAYS = "//ul[@class='Grid__headerDayList___30Pd2']/li/span";

			List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
			for (WebElement ele : lstDatesElements) {
				datesList.add(ele.getText());
			}

			logger.info("Availability Day's Are: " + datesList);

			for (int i = 0; i < datesList.size(); i++) {
				for (int j = 0; j < dayList.size(); j++) {
					if (datesList.get(i).equals(dayList.get(j))) {
						checkInDateIndex = i;
						checkIndate = true;
					}
				}
				if (checkIndate) {
					break;
				}

			}

			roomClassRates.get(checkInDateIndex).click();
			testSteps.add("Successfully clicked on Check In Date : "+ fromDate);
			logger.info("Successfully clicked on Check In Date: "+ fromDate);
			return testSteps;
		}
		
	public ArrayList<String> clickViewOurEntireAvailability(WebDriver driver) {

		ArrayList<String> test_steps = new ArrayList<String>();
		if(driver.findElements(By.xpath(OR_BE.ENTIRE_AVALABILITY)).size() > 0)
		{
			Wait.WaitForElement(driver, OR_BE.ENTIRE_AVALABILITY);
			driver.findElement(By.xpath(OR_BE.ENTIRE_AVALABILITY)).click();
			test_steps.add("Successfully verified View Our Entire Availability link is available.");
			logger.info("Successfully verified View Our Entire Availability link is available.");
		}
		return test_steps;
	}
	
	public ArrayList<String> verifyRoomClassVisibilityInBookingEngine(WebDriver driver,String roomClass,boolean visibility) {
		ArrayList<String> test_steps = new ArrayList<>();
		boolean roomClassFound = false;
		boolean verifyroomClass = true;
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		try {
			Wait.WaitForElement(driver, OR_BE.AVAILABILITY_ROOM_CLASSES);
			Wait.waitForElementToBeInVisibile(By.xpath(OR_BE.AVAILABILITY_ROOM_CLASSES), driver, 20);
		}catch(Exception e) {
			if(!visibility) {
				int size =  elements.AVAILABILITY_ROOM_CLASSES.size();
				if(size==0) {
					verifyroomClass = false;
				}
			}
		}
		if(verifyroomClass) {
		List<WebElement> lstRoomClasses = elements.AVAILABILITY_ROOM_CLASSES;
		for (WebElement objRoomClass : lstRoomClasses) {
			String foundRoomClass = objRoomClass.getText();
			if (roomClass.equalsIgnoreCase(foundRoomClass)) {
				roomClassFound = true;
				logger.info("Found : " + foundRoomClass);
				test_steps.add("Successfully verified '" + foundRoomClass + "' exists.");
				logger.info("Successfully verified '" + foundRoomClass + "' exists.");
			}
		}
	}
		assertEquals(roomClassFound,visibility,"Failed: Room Class visibility status missmatched");
		if(!visibility) {
			test_steps.add("Successfully verified '" + roomClass + "' not exists.");
			logger.info("Successfully verified '" + roomClass + "' not exists.");
		}
		return test_steps;
	}
	
	//required Status will be true/false in string
		public ArrayList<String> setToggleButtonState(WebDriver driver,String buttonName,boolean fullSection,String sectionName, String requiredState) throws InterruptedException {

			ArrayList<String> steps = new ArrayList<>();
			String xpath = "//h3[text()='"+sectionName+"']//parent::div//p[text()='"+buttonName+"']//parent::div//following-sibling::div/button";
			if(!fullSection) {
				xpath = "//h3[contains(text(),'"+sectionName+"')]//parent::div//p[text()='"+buttonName+"']//parent::div//following-sibling::div/button";	
			}
			WebElement button = driver.findElement(By.xpath(xpath));
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
			String currentState = button.getAttribute("aria-checked");
			steps.add("'"+ buttonName +"' State : " + currentState);
			logger.info("'"+ buttonName +"' State : " + currentState);
			if(!currentState.equalsIgnoreCase(requiredState)) {
				steps.add("'"+ buttonName +"' is not in required state");
				logger.info("'"+ buttonName +"' is not in required state");
				Utility.ScrollToElement(button, driver);
				try{
					button.click();
				}catch(Exception e) {
					Utility.clickThroughJavaScript(driver, button);
				}
				steps.add("Click on '" + buttonName +"' toggle.");
				logger.info("Click on '" + buttonName +"' toggle.");
				
				String saveButtonPath = "//h3[text()='"+sectionName+"']//parent::div//button[text()='Save']";
				if(!fullSection) {
					saveButtonPath = "//h3[contains(text(),'"+sectionName+"')]//parent::div//button[text()='Save']";	
				}
				Wait.WaitForElement(driver, saveButtonPath);
				try{
					driver.findElement(By.xpath(saveButtonPath)).click();
				}catch(Exception e) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(saveButtonPath)));
				}
				steps.add("Successfully clicked on Save button.");
				logger.info("Successfully clicked on Save button.");
				
				currentState = button.getAttribute("aria-checked");
				steps.add("'"+ buttonName +"' State : " + currentState);
				logger.info("'"+ buttonName +"' State : " + currentState);
			}
			assertTrue(currentState.equalsIgnoreCase(requiredState), "Failed '" + buttonName +"' is not in required state");
			return steps;
		}
	
	public void verifyElementVisibility(WebDriver driver, By byLocator,boolean visible) {
		boolean displayed = false;
		if(visible) {
		if(driver.findElements(byLocator).size() > 0)
		{
			Wait.waitUntilElementToBeVisibleByLocator(byLocator, driver, 60);
			displayed = driver.findElement(byLocator).isDisplayed();
			logger.info("Element displayed : " + displayed);
		}
		}else {
			if(driver.findElements(byLocator).size() != 0)
			{
				displayed =  true;
				logger.info("Element displayed : " + displayed);
			}
		}
		assertEquals(displayed,visible,"Failed: Element visibility missmatched");
	}
	
	
	public ArrayList<String> clickCheckInAndCheckOutDateAndVerifyPopupDiaplyed(WebDriver driver,String roomClass,
			int days,String checkInDate,String checkOutDate,String dateFormat) throws ParseException {
		ArrayList<String> test_steps = new ArrayList<>();
		boolean noCheckInRuleValue = false;
		boolean checkIndate = false;
		String checkInDateRatePath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button";
		logger.info("checkInDateRatePath: " + checkInDateRatePath);
		Wait.WaitForElement(driver, checkInDateRatePath);
		List<WebElement> roomClassRates = driver.findElements(By.xpath(checkInDateRatePath));
		logger.info("roomClassRates.size() : " + roomClassRates.size());
		if(roomClassRates.size() > 0 )
		{	
			ArrayList<String> datesList = new ArrayList<String>();
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

			String AVAILABILITY_DAYS = "//ul[@class='Grid__headerDayList___30Pd2']/li/span";

			List<WebElement> lstDatesElements = driver.findElements(By.xpath(AVAILABILITY_DAYS));
			for (WebElement ele : lstDatesElements) {
				datesList.add(ele.getText());
			}

			logger.info("Availability Day's Are: " + datesList);

			logger.info("Availability Day's Are: " + datesList);
			for (int i = 0; i < datesList.size(); i++) {
				for (int j = 0; j < dayList.size(); j++) {
					if (datesList.get(i).equals(dayList.get(j))) {
						checkInDateIndex = i;
						checkIndate = true;
					}
				}
				if (checkIndate) {
					break;
				}

			}
			roomClassRates.get(checkInDateIndex).click();
			test_steps.add("Successfully clicked on Check In Date : "+ fromDate);
			logger.info("Successfully clicked on Check In Date: "+ fromDate);
			roomClassRates.get(checkInDateIndex + days).click();
			test_steps.add("Successfully clicked on Check Out Date : "+checkOutDate);
			logger.info("Successfully clicked on Check Out Date: "+checkOutDate);
			verifyCheckOutTextIsShowing(driver);
		}
		
		
		return test_steps;
	}
		
		public ArrayList<String> clickOnCheckOutDate(WebDriver driver, String checkInDate, String checkOutDate,
				String roomClass, String dateFormat, int days,int checkinDateIndex) throws ParseException {

			ArrayList<String> testSteps = new ArrayList<String>();
			String checkInDateRatePath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='" + roomClass
					+ "']//..//..//following-sibling::ul/li/button";
			Wait.WaitForElement(driver, checkInDateRatePath);
			List<WebElement> roomClassRates = driver.findElements(By.xpath(checkInDateRatePath));

			roomClassRates.get(checkinDateIndex + days).click();
			testSteps.add("Successfully clicked on Check Out Date : "+checkOutDate);
			logger.info("Successfully clicked on Check Out Date: "+checkOutDate);
			return testSteps;
		}

		public ArrayList<String> verifyCheckOutTextIsShowing(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<String>();
			String checkOutTextPath = "//span[text()='Check out']";
			Wait.WaitForElement(driver, checkOutTextPath);
			if(driver.findElements(By.xpath(checkOutTextPath)).size() > 0)
			{
				Assert.assertEquals(driver.findElement(By.xpath(checkOutTextPath)).getText(), "Check out","Failed : Check Out word is not showing.");
				testSteps.add("Successfully verified Check Out word is showing.");
				logger.info("Successfully verified Check Out word is showing.");
			}
			return testSteps;
		}

		public ArrayList<String> clickOnSelectADateButtonAndSelectDate(WebDriver driver,String startDate) throws InterruptedException, ParseException {
			ArrayList<String> testSteps = new ArrayList<String>();
			String selectaDateButtonPath = "//button[text()='Select a Date']";
			
			Wait.WaitForElement(driver, selectaDateButtonPath);
			driver.findElement(By.xpath(selectaDateButtonPath)).click();
			
			testSteps.add("Successfully clicked on Select a Date button.");
			logger.info("Successfully clicked on Select a Date button");
			
			String getMonth = getMonthFromCalendarHeading1(driver);
			logger.info("getMonth: " + getMonth);
			String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

			boolean isMothEqual = false;
			String expectedMonth = ESTTimeZone.getDateBaseOnDate(startDate, "MM/d/yyyy", "MMMM yyyy");
			String getStartDate = ESTTimeZone.getDateBaseOnDate(startDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");

			logger.info("expectedMonth: " + expectedMonth);
			String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
			System.out.println("path: " + path);
			// this is for start date
			while (!isMothEqual) {
				if (expectedMonth.equals(getMonth)) {
					logger.info("In first part");
					isMothEqual = true;
					driver.findElement(By.xpath(path)).click();
					break;
				}
				if (expectedMonth.equals(getMonthFromSecondPart)) {
					logger.info("in a second part");
					isMothEqual = true;
					driver.findElement(By.xpath(path)).click();
					break;
				} else {
					logger.info("in else");
					clickOnCalendarRightArrow1(driver);
					getMonth = getMonthFromCalendarHeading1(driver);
					getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

					logger.info("getMonth: " + getMonth);
				}

			}
			
			return testSteps;
		}

		public ArrayList<String> verifyAvailabilityGridStartDate(WebDriver driver, String newDate) {
			
			ArrayList<String> test_steps = new ArrayList<String>();
			Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
			String AVAILABILITY_DAYS="//ul[@class='Grid__headerDayList___30Pd2']/li/span";
			List<WebElement> lstAvailabilityDays = driver.findElements(By.xpath(AVAILABILITY_DAYS));
			Wait.WaitForElement(driver, OR_BE.AVAILABILITY_MONTH);
			String month = Utility.get_MonthYear(newDate).split(" ")[0].toString();
			String day = Utility.getDay(newDate);
			
			String elementMonth = elements.AVAILABILITY_MONTH.getText();
			test_steps.add("Expected Month : "+month);
			logger.info("Expected Month : "+month);
			if(elementMonth.equals(month))
			{
				test_steps.add("Found Month : "+elementMonth);
				logger.info("Found Month : "+elementMonth);
				test_steps.add("Successfully verified Month : "+elementMonth);
				logger.info("Successfully verified Month : "+elementMonth);
				logger.info("No of Days : "+lstAvailabilityDays.size());
				String elementStartDate = lstAvailabilityDays.get(0).getText();
				test_steps.add("Expected Day : "+day);
				logger.info("Expected Day : "+day);
				test_steps.add("Found Day : "+elementStartDate);
				logger.info("Found Day : "+elementStartDate);
				if(elementStartDate.equalsIgnoreCase(day))
				{
					test_steps.add("Successfully verified Availability grid starts from day : "+elementStartDate);
					logger.info("Successfully verified Availability grid starts from day : "+elementStartDate);
				}
				
			}
			
			return test_steps;
		}

		public ArrayList<String> selectSecondMonthFromCalender(WebDriver driver, String checkInDate) throws ParseException, InterruptedException {
			
			System.out.println("startDate: " + checkInDate);
			ArrayList<String> testSteps = new ArrayList<>();
			clickOnStartDate(driver);

			String getMonth = getMonthFromCalendarHeading1(driver);
			logger.info("getMonth: " + getMonth);
			String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

			String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "MMMM yyyy");
			String getStartDate = getMonthFromSecondPart.split(" ")[0]+" "+Utility.getDay(checkInDate)+", "+getMonthFromSecondPart.split(" ")[1];

			logger.info("expectedMonth: " + expectedMonth);
			String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
			System.out.println("path: " + path);
			
			Wait.WaitForElement(driver, path);
			driver.findElement(By.xpath(path)).click();
			
			testSteps.add("Successfully clicked on check in date : "+getStartDate);
			logger.info("Successfully clicked on check in date : "+getStartDate);
			int checkOutDateDay = Integer.parseInt(Utility.getDay(checkInDate))+2;
			
			String getEndDate = getMonthFromSecondPart.split(" ")[0]+" "+checkOutDateDay+", "+getMonthFromSecondPart.split(" ")[1];
			
			String endDatePath = "//td[contains(@aria-label,'" + getEndDate + "')]";
			System.out.println("path: " + endDatePath);
			
			Wait.WaitForElement(driver, endDatePath);
			driver.findElement(By.xpath(endDatePath)).click();
			testSteps.add("Successfully clicked on check out date : "+getEndDate);
			logger.info("Successfully clicked on check out date : "+getEndDate);
			
			return testSteps;
		}

		public ArrayList<String> verifyCalenderMonthWhenSelectSecondMonthFromCalender(WebDriver driver,
				String expectedMonth) throws InterruptedException {
			
			ArrayList<String> testSteps = new ArrayList<>();
			clickOnStartDate(driver);

			String getMonth = getMonthFromCalendarHeading1(driver);
			logger.info("getMonth: " + getMonth);
			
			if(getMonth.equals(expectedMonth))
			{
				Assert.assertEquals(getMonth,expectedMonth,"Failed : "+expectedMonth+" is not showing on left side." );
				testSteps.add("Successfully verified "+expectedMonth+" is displaying on left side. ");
				logger.info("Successfully verified "+expectedMonth+" is displaying on left side. ");
			}
			
			return testSteps;
		}

		public ArrayList<String> clickOnReservationDetailsEditLink(WebDriver driver) {
			ArrayList<String> testSteps = new ArrayList<>();
			String editLinkPath = "//i[@class='glyphicon glyphicon-pencil']";
			Wait.WaitForElement(driver, editLinkPath);
			
			driver.findElement(By.xpath(editLinkPath)).click();
			
			testSteps.add("Successfully clicked on reservation details edit link. ");
			logger.info("Successfully clicked on reservation details edit link. ");
			
			return testSteps;
			
		}

		public ArrayList<String> verifyCalenderOnBookingEngineHomePage(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			clickOnStartDate(driver);
			String checkInFieldPath = "//div[@class='dateRangePicker__formGroup___3iFJD form-group has-focus']";
			Wait.WaitForElement(driver, checkInFieldPath);
			Assert.assertEquals(driver.findElements(By.xpath(checkInFieldPath)).size(), 1);
			if(driver.findElements(By.xpath(checkInFieldPath)).size() > 0)
			{
				testSteps.add("Successfully verified calender is appearing to select date. ");
				logger.info("Successfully verified calender is appearing to select date. ");
			}
			
			return testSteps;
		}

		public ArrayList<String> clickOnAnotherAreaAndVerifyCalenderOnBookingEngineHomePage(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			String path ="//h2[text()='Please select your dates below']";
			
			Wait.WaitForElement(driver, path);
			driver.findElement(By.xpath(path)).click();
			String checkInFieldPath = "//div[@class='dateRangePicker__formGroup___3iFJD form-group has-focus']";
			Assert.assertEquals(driver.findElements(By.xpath(checkInFieldPath)).size(), 0);
			if(driver.findElements(By.xpath(checkInFieldPath)).size() == 0)
			{
				testSteps.add("Successfully verified calender disappeared when clicked outside of the calendar. ");
				logger.info("Successfully verified calender disappeared when clicked outside of the calendar. ");
			}
			
			return testSteps;
		}

		public ArrayList<String> verifyCheckInDateWhenClickForSecondTime(WebDriver driver, String checkInDate) throws InterruptedException, ParseException {
			
			ArrayList<String> testSteps = new ArrayList<>();
			System.out.println("CheckInDate : " + checkInDate);
			clickOnStartDate(driver);
			boolean isMothEqual = false;
			Elements_BE bookingEngineElements = new Elements_BE(driver);

			String getMonth = getMonthFromCalendarHeading1(driver);
			logger.info("Month : " + getMonth);
			String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

			String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "MMMM yyyy");
			String getStartDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");

			logger.info("expectedMonth : " + expectedMonth);
			String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
			System.out.println("path: " + path);
			
			while (!isMothEqual) {
				if (expectedMonth.equals(getMonth)) {
					logger.info("In first part");
					isMothEqual = true;
					driver.findElement(By.xpath(path)).click();
					break;
				}
				if (expectedMonth.equals(getMonthFromSecondPart)) {
					logger.info("in a second part");
					isMothEqual = true;
					driver.findElement(By.xpath(path)).click();
					break;
				} else {
					logger.info("in else");
					clickOnCalendarRightArrow1(driver);
					getMonth = getMonthFromCalendarHeading1(driver);
					getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

					logger.info("getMonth: " + getMonth);
				}

			}
			
			testSteps.add("Successfully clicked on check in date : "+getStartDate);
			logger.info("Successfully clicked on check in date : "+getStartDate);
			
			
			driver.findElement(By.xpath(path)).click();
			
			testSteps.add("Clicked on check in date again : "+getStartDate);
			logger.info("Clicked on check in date again : "+getStartDate);
			
			Wait.WaitForElement(driver, OR_BE.startDate);
			String checkInDateText = bookingEngineElements.startDate.getAttribute("value");
			String formattedDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "MMM d, yyyy");
			Assert.assertEquals(checkInDateText, formattedDate);
			if(formattedDate.equals(checkInDateText))
			{
				testSteps.add("Successfully verified checkInDate not changed when clicked checkInDate for second time. ");
				logger.info("Successfully verified checkInDate not changed when clicked checkInDate for second time. ");
			}
			
			String checkOutDatePath ="//label[text()='Check out']//parent::div";
			Wait.WaitForElement(driver, checkOutDatePath);
			if(driver.findElement(By.xpath(checkOutDatePath)).getAttribute("class").contains("has-focus"))
			{
				testSteps.add("Successfully verified calender toggled to the checkout box with the checkin date unchanged. ");
				logger.info("Successfully verified calender toggled to the checkout box with the checkin date unchanged. ");
			}
			else
			{
				testSteps.add("Failed to verify calender toggled to the checkout box with the checkin date unchanged. ");
				logger.info("Failed to verify calender toggled to the checkout box with the checkin date unchanged. ");
			}
			return testSteps;
		}

		
		public String getEndDate(WebDriver driver)
		{
			String endDateValue = "";
			Elements_BE BE = new Elements_BE(driver);
			Wait.WaitForElement(driver, OR_BE.endDate);
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.endDate), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_BE.endDate), driver);
			endDateValue = BE.endDate.getAttribute("value");
			return endDateValue;
		}
		
		public String getStartDate(WebDriver driver)
		{
			String startDateValue = "";
			Elements_BE BE = new Elements_BE(driver);
			Wait.WaitForElement(driver, OR_BE.startDate);
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.startDate), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_BE.startDate), driver);
			startDateValue = BE.startDate.getAttribute("value");
			return startDateValue;
		}
		
		public ArrayList<String> clickOnBackToRoomButton(WebDriver driver)
		{
			ArrayList<String> testSteps = new ArrayList<>();
			String backToRoomButtonPath = "//i[@class='glyphicon glyphicon-menu-left']";
			Wait.WaitForElement(driver, backToRoomButtonPath);
			driver.findElement(By.xpath(backToRoomButtonPath)).click();
			testSteps.add("Successfully clicked on Back To Room button. ");
			logger.info("Successfully clicked on Back To Room button. ");
			return testSteps;
		}
		
		
		public ArrayList<String> selectCheckOutDateByDoubleTapOnDatePicker(WebDriver driver, String checkOutDate) throws InterruptedException, ParseException {
			
			ArrayList<String> testSteps = new ArrayList<>();
			boolean isMothEqual = false;
			Actions actions = new Actions(driver);
			System.out.println("CheckOutDate : " + checkOutDate);
			testSteps.addAll(clickOnReservationDetailsEditLink(driver));
			clickOnEndDate(driver);
			
			String getMonth = getMonthFromCalendarHeading1(driver);
			logger.info("Month : " + getMonth);
			String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

			String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkOutDate, "MM/d/yyyy", "MMMM yyyy");
			String getEndDate = ESTTimeZone.getDateBaseOnDate(checkOutDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");

			logger.info("expectedMonth : " + expectedMonth);
			String path = "//td[contains(@aria-label,'" + getEndDate + "')]";
			System.out.println("path: " + path);
			
			while (!isMothEqual) {
				if (expectedMonth.equals(getMonth)) {
					logger.info("In first part");
					isMothEqual = true;
					WebElement elementLocator = driver.findElement(By.xpath(path));
					actions.doubleClick(elementLocator).perform();
					break;
				}
				if (expectedMonth.equals(getMonthFromSecondPart)) {
					logger.info("in a second part");
					isMothEqual = true;
					WebElement elementLocator = driver.findElement(By.xpath(path));
					actions.doubleClick(elementLocator).perform();
					//driver.findElement(By.xpath(path)).click();
					break;
				} else {
					logger.info("in else");
					clickOnCalendarRightArrow1(driver);
					getMonth = getMonthFromCalendarHeading1(driver);
					getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

					logger.info("getMonth: " + getMonth);
				}

			}
			
			return testSteps;
		}

		public ArrayList<String> verifyCheckOutDateAfterUpate(WebDriver driver, String newCheckOutDate) throws ParseException {
			
			ArrayList<String> testSteps = new ArrayList<>();
			String getEndDate = ESTTimeZone.getDateBaseOnDate(newCheckOutDate, "MM/d/yyyy", "MMM d, yyyy");
			String endDateText = getEndDate(driver);
			Assert.assertEquals(endDateText, getEndDate,"Failed : Check Out Date is not updated.");
			testSteps.add("Successfully verified checkOut date" +endDateText+" has been updated.");
			logger.info("Successfully verified checkOut date" +endDateText+" has been updated.");
			
			return testSteps;
		}

		public ArrayList<String> toggleAllowSameDayBooking(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			
			clickOnSettingsLink(driver);
			
			Wait.WaitForElement(driver, OR_BE.sameDayBookingToggleButton);
			if(bookingEngineElements.sameDayBookingToggleButton.getAttribute("aria-checked").equals("true"))
			{
				bookingEngineElements.sameDayBookingToggleButton.click();
				testSteps.add("Successfully clicked on Same-Day Bookings button and toggled off because it was on. ");
				logger.info("Successfully clicked on Same-Day Bookings button and toggled off because it was on. ");
			}
			else
			{
				testSteps.add("Same-Day Bookings button already toggled off. ");
				logger.info("Same-Day Bookings button already toggled off . ");
			}
			return testSteps;
		}

		public ArrayList<String> verifyCurrentDateIsNotDisplayingWhenSameDayBookingToggleIsOff(WebDriver driver,
				String checkInDate) throws ParseException {
			
			ArrayList<String> testSteps = new ArrayList<>();
			clickOnStartDate(driver);
			String getStartDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");
			String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
			if(driver.findElement(By.xpath(path)).getAttribute("aria-label").contains("Not available"))
			{
				testSteps.add("Successfully verified current date is not showing because Same-Day Bookings button toggled off. ");
				logger.info("Successfully verified current date is not showing because Same-Day Bookings button toggled off. ");
			}
			else
			{
				testSteps.add("Failed : Current date is showing although same day booking button is off in booking Engine Configration. ");
				logger.info("Failed : Current date is showing although same day booking button is off in booking Engine Configration. ");
			}
			
			return testSteps;
		}

		public ArrayList<String> verifyCheckOutDateIsDefaultSelected(WebDriver driver, String checkInDate) throws ParseException, InterruptedException {
			
			ArrayList<String> testSteps = new ArrayList<>();
			boolean isMothEqual = false;
			clickOnStartDate(driver);
			
			String getMonth = getMonthFromCalendarHeading1(driver);
			logger.info("Month : " + getMonth);
			String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

			String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "MMMM yyyy");
			String getStartDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");
			String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
			
			while (!isMothEqual) {
				if (expectedMonth.equals(getMonth)) {
					logger.info("In first part");
					isMothEqual = true;
					driver.findElement(By.xpath(path)).click();
					break;
				}
				if (expectedMonth.equals(getMonthFromSecondPart)) {
					logger.info("in a second part");
					isMothEqual = true;
					driver.findElement(By.xpath(path)).click();
					break;
				} else {
					logger.info("in else");
					clickOnCalendarRightArrow1(driver);
					getMonth = getMonthFromCalendarHeading1(driver);
					getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

					logger.info("getMonth: " + getMonth);
				}

			}
			
			String pathOfHighlightedElement = "//td[contains(@aria-label,'Selected as start date')]";
			Assert.assertEquals(driver.findElements(By.xpath(pathOfHighlightedElement)).size(), 1,"Failed : Other then checkInDate element is highlighted.");
			
			String foundDate = driver.findElement(By.xpath(pathOfHighlightedElement)).getAttribute("aria-label").split("Selected as start date. ")[1].toString();
			Assert.assertEquals(foundDate, getStartDate,"Failed : CheckInDate is not highlighted after selecting check in date.");
			
			testSteps.add("Successfully verified only CheckInDate is highlighted. ");
			logger.info("Successfully verified only CheckInDate is highlighted. ");
			
			return testSteps;
		}

		public ArrayList<String> toggleDisplayAgeRangeToggleButton(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			
			clickOnSettingsLink(driver);
			
			Wait.WaitForElement(driver, OR_BE.ageRangeToggleButton);
			if(bookingEngineElements.ageRangeToggleButton.getAttribute("aria-checked").equals("false"))
			{
				bookingEngineElements.ageRangeToggleButton.click();
				testSteps.add("Successfully clicked on Display age range toggle button . ");
				logger.info("Successfully clicked on Display age range toggle button . ");
			}
			else
			{
				testSteps.add("Display age range button already on. ");
				logger.info("Display age range button already on . ");
			}
			return testSteps;
		}

		public ArrayList<String> saveAgeRangeValues(WebDriver driver)
		{
			ArrayList<String> testSteps = new ArrayList<>();
			String ageRangeSaveButtonPath = "//div[@class='AgeRange__inputSection___398bH row']/div[3]/div/button";
			Wait.WaitForElement(driver, ageRangeSaveButtonPath);
			driver.findElement(By.xpath(ageRangeSaveButtonPath)).click();
			testSteps.add("Successfully clicked on save age range values button. ");
			logger.info("Successfully clicked on save age range values button. ");
			
			return testSteps;
		}
		public ArrayList<String> enterAdultAndChildrenValues(WebDriver driver, String adults, String child) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			Wait.WaitForElement(driver, OR_BE.adultInput);
			bookingEngineElements.adultInput.click();
			bookingEngineElements.adultInput.clear();
			bookingEngineElements.adultInput.sendKeys(adults);
			testSteps.add("Enter Adult value : "+adults);
			logger.info("Enter Adult value : "+adults);
			
			Wait.WaitForElement(driver, OR_BE.childernInput);
			bookingEngineElements.childernInput.click();
			bookingEngineElements.childernInput.clear();
			bookingEngineElements.childernInput.sendKeys(child);
			testSteps.add("Enter Children value : "+child);
			logger.info("Enter Children value : "+child);
			
			return testSteps;
		}

		public ArrayList<String> verifyAdultAgeRangeValues(WebDriver driver,String adult) {
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			
			Wait.WaitForElement(driver, OR_BE.adultToolTipPath);
			Assert.assertEquals(driver.findElements(By.xpath(OR_BE.adultToolTipPath)).size(), 1,"Failed : Adult Age Range tooltip is not showing. ");
			testSteps.add("Successfully verified adult age range tooltip is showing.");
			logger.info("Successfully verified adult age range tooltip is showing.");
			
			WebElement hoverElement = driver.findElement(By.xpath(OR_BE.adultToolTipPath));
			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			testSteps.add("Hovers over adult Age Range tooltip.");

			Wait.WaitForElement(driver,  OR_BE.adultToolTipAgeValue);
			Wait.waitForElementToBeVisibile(By.xpath( OR_BE.adultToolTipAgeValue), driver);
			Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath( OR_BE.adultToolTipAgeValue)), driver);
			
			String adultValue = bookingEngineElements.adultToolTipAgeValue.getText();
			Assert.assertEquals(adultValue, adult,"Failed : Adult age values are mismatching.");
			
			testSteps.add("Successfully verified adult age value : "+ adultValue +" is matching." );
			logger.info("Successfully verified adult age value : "+ adultValue +" is matching." );
			return testSteps;
		}
		
		public ArrayList<String> verifyChildrenAgeRangeValues(WebDriver driver,String child) {
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			
			Wait.WaitForElement(driver, OR_BE.childToolTipPath);
			Assert.assertEquals(driver.findElements(By.xpath(OR_BE.childToolTipPath)).size(), 1,"Failed : Children Age Range tooltip is not showing. ");
			testSteps.add("Successfully verified Children age range tooltip is showing.");
			logger.info("Successfully verified Children age range tooltip is showing.");
			
			WebElement hoverElement = driver.findElement(By.xpath(OR_BE.childToolTipPath));
			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			testSteps.add("Hovers over children Age Range tooltip.");

			Wait.WaitForElement(driver,  OR_BE.childrenToolTipAgeValue);
			Wait.waitForElementToBeVisibile(By.xpath( OR_BE.childrenToolTipAgeValue), driver);
			Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath( OR_BE.childrenToolTipAgeValue)), driver);
			
			String childValue = bookingEngineElements.childrenToolTipAgeValue.getText();
			Assert.assertEquals(childValue, child,"Failed : Children age value is mismatching.");
			
			testSteps.add("Successfully verified Children age value : "+ childValue +" is matching." );
			logger.info("Successfully verified Children age value : "+ childValue +" is matching." );
			return testSteps;
		}
		
		public ArrayList<String> clickOnSettingsLink(WebDriver driver)
		{
			ArrayList<String> testSteps = new ArrayList<>();
			
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.SETTINGS_LINK), driver);
			Wait.WaitForElement(driver, OR_BE.SETTINGS_LINK);
			driver.findElement(By.xpath(OR_BE.SETTINGS_LINK)).click();
			testSteps.add("Successfully clicked on Settings link. ");
			logger.info("Successfully clicked on Settings link. ");
			
			return testSteps;
		}

		public ArrayList<String> toggleGroupBookingsToggleButton(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			clickOnSettingsLink(driver);
			Wait.WaitForElement(driver, OR_BE.groupBookingsToggleButton);
			if(bookingEngineElements.groupBookingsToggleButton.getAttribute("aria-checked").equals("false"))
			{
				bookingEngineElements.groupBookingsToggleButton.click();
				testSteps.add("Successfully clicked on Group Bookings toggle button . ");
				logger.info("Successfully clicked on Group Bookings toggle button . ");
			}
			else
			{
				testSteps.add("Group Bookings button already on. ");
				logger.info("Group Bookings button already on . ");
			}
			return testSteps;
		}

		public ArrayList<String> clickOnAdvancedOption(WebDriver driver) {
			ArrayList<String> testSteps = new ArrayList<>();
			
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.advancedOption), driver);
			Wait.WaitForElement(driver, OR_BE.advancedOption);
			driver.findElement(By.xpath(OR_BE.advancedOption)).click();
			testSteps.add("Successfully clicked on Advanced Option link. ");
			logger.info("Successfully clicked on Advanced Option link. ");
			
			return testSteps;
		}

		public ArrayList<String> clickOnHaveaGroupNumberLink(WebDriver driver) {
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.groupNumberLink), driver);
			Wait.WaitForElement(driver, OR_BE.groupNumberLink);
			bookingEngineElements.groupNumberLink.click();
			testSteps.add("Successfully clicked on I have a group Number link. ");
			logger.info("Successfully clicked on I have a group Number link. ");
			
			return testSteps;
		}

		public ArrayList<String> verifyEnteraGroupNumberPopUpIsShowing(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.groupNumberPopUp), driver);
			Wait.WaitForElement(driver, OR_BE.groupNumberPopUp);
			Assert.assertEquals(driver.findElements(By.xpath(OR_BE.groupNumberPopUp)).size(),1,"Failed : Please Enter Your Group Number Pop Up is not showing.");
			testSteps.add("Successfully verified Please Enter Your Group Number Pop Up is not showing. ");
			logger.info("Successfully verified Please Enter Your Group Number Pop Up is not showing. ");
			
			return testSteps;
		}

		public ArrayList<String> enterGroupNumberInBE(WebDriver driver,String groupNumber) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.groupNumberInput), driver);
			Wait.WaitForElement(driver, OR_BE.groupNumberInput);
			bookingEngineElements.groupNumberInput.click();
			bookingEngineElements.groupNumberInput.clear();
			
			testSteps.add("Enter group number : "+groupNumber);
			logger.info("Enter group number : "+groupNumber);
			bookingEngineElements.groupNumberInput.sendKeys(groupNumber);
			
			Wait.waitForElementToBeVisibile(By.xpath(OR_BE.SEARCH_ROOMS_BUTTON_BE), driver);
			Wait.WaitForElement(driver, OR_BE.SEARCH_ROOMS_BUTTON_BE);
			driver.findElement(By.xpath(OR_BE.SEARCH_ROOMS_BUTTON_BE)).click();
			
			testSteps.add("Successfully clicked on Go Button. ");
			logger.info("Successfully clicked on Go Button. ");
			return testSteps;
		}

		public ArrayList<String> verifyCheckInCheckOutDate(WebDriver driver, String checkInDate, String checkOutDate) throws ParseException {
			
			ArrayList<String> testSteps = new ArrayList<>();
			String formattedStartDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "d MMM, yyyy");
			String formattedEndDate = ESTTimeZone.getDateBaseOnDate(checkOutDate, "MM/d/yyyy", "d MMM, yyyy");
			String startDate = getStartDate(driver);
			testSteps.add("Expected check In Date : "+formattedStartDate);
			logger.info("Expected check In Date : "+formattedStartDate);
			testSteps.add("Found check In Date : "+startDate);
			logger.info("Found check In Date : "+startDate);
			Assert.assertEquals(startDate, formattedStartDate,"Failed : Check In Date is mismatching.");
			testSteps.add("Successfully verified check In Date : "+formattedStartDate);
			logger.info("Successfully verified check In Date : "+formattedStartDate);
			
			String endDate = getEndDate(driver);
			testSteps.add("Expected check In Date : "+formattedEndDate);
			logger.info("Expected check In Date : "+formattedEndDate);
			testSteps.add("Found check In Date : "+endDate);
			logger.info("Found check In Date : "+endDate);
			Assert.assertEquals(endDate, formattedEndDate,"Failed : Check Out Date is mismatching.");
			testSteps.add("Successfully verified check Out Date : "+endDate);
			logger.info("Successfully verified check Out Date : "+endDate);
			
			return testSteps;
		}

		public ArrayList<String> verifyDateRangeLiesBetweenBlockDateRange(WebDriver driver,String checkInDate,String checkOutDate) throws ParseException {
			ArrayList<String> testSteps = new ArrayList<>();
			clickOnStartDate(driver);
			String highlightedElementsPath = "//td[contains(@aria-label,'Selected')]";
			Wait.WaitForElement(driver,highlightedElementsPath);
			String getStartDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "MMMM d, yyyy");
			String getEndDate = ESTTimeZone.getDateBaseOnDate(checkOutDate, "MM/d/yyyy", "MMMM d, yyyy");
			List<WebElement> highlightedElements = driver.findElements(By.xpath(highlightedElementsPath));
			int indexOfLastDate = highlightedElements.size()-1;
			String date="";
			String formattedDate="";
			for(int i=0;i<highlightedElements.size();i++)
			{
				WebElement element = highlightedElements.get(i);
				if(i==0)
				{
					date = element.getAttribute("aria-label").split("Selected as start date. ")[1].toString();
					formattedDate = date.split(", ")[1]+", "+date.split(", ")[2];
					
					Assert.assertEquals(formattedDate, getStartDate,"Failed : calender is not starting from : "+getStartDate);
					testSteps.add("Successfully verified calender is starting from : "+getStartDate);
					logger.info("Successfully verified calender is starting from : "+getStartDate);
				}
				else if(i==indexOfLastDate)
				{
					date = element.getAttribute("aria-label").split("Selected as end date. ")[1].toString();
					formattedDate = date.split(", ")[1]+", "+date.split(", ")[2];
					
					Assert.assertEquals(formattedDate, getEndDate,"Failed : calender is not ending from : "+getEndDate);
					testSteps.add("Successfully verified calender is ending from : "+getEndDate);
					logger.info("Successfully verified calender is ending from : "+getEndDate);
				}
				else
				{
					date = element.getAttribute("aria-label").split("Selected. ")[1].toString();
					formattedDate = date.split(", ")[1]+", "+date.split(", ")[2];
				}
				
				testSteps.add("Date at index "+i+" : "+formattedDate);
				logger.info("Date at index "+i+" : "+formattedDate);
			}
			
			testSteps.add("Successfully verified we can modify the date range only in between block date range "+getStartDate+" to "+getEndDate+".");
			logger.info("Successfully verified we can modify the date range only in between block date range "+getStartDate+" to "+getEndDate+".");
			
			return testSteps;
		}

		public ArrayList<String> toggleDisplayRateForCalenderButton(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			clickOnSettingsLink(driver);
			Wait.WaitForElement(driver, OR_BE.displayratesForCalender);
			if(bookingEngineElements.displayratesForCalender.getAttribute("aria-checked").equals("false"))
			{
				testSteps.add("Display Rates for Calendar button already toggled off. ");
				logger.info("Display Rates for Calendar button already toggled off. ");
			}
			else
			{
				bookingEngineElements.displayratesForCalender.click();
				testSteps.add("Successfully clicked on Display Rates for Calendar toggle button .");
				logger.info("Successfully clicked on Display Rates for Calendar toggle button . ");
			}
			return testSteps;
		}
		
		public ArrayList<String> saveDisplayRateForCalenderSettings(WebDriver driver) {
			
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_BE bookingEngineElements = new Elements_BE(driver);
			clickOnSettingsLink(driver);
			Wait.WaitForElement(driver, OR_BE.saveButtonForCalender);
			bookingEngineElements.saveButtonForCalender.click();
			testSteps.add("Successfully clicked save button for calender settings . ");
			logger.info("Successfully clicked save button for calender settings . ");
			return testSteps;
		}

		public ArrayList<String> hoverOnCheckInDateAndVerifyDates(WebDriver driver, String checkInDate) throws InterruptedException, ParseException {
			
			System.out.println("startDate: " + checkInDate);
			ArrayList<String> testSteps = new ArrayList<>();
			clickOnStartDate(driver);
			
			String getStartDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");

			String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
			System.out.println("path: " + path);
			
			WebElement hoverElement = driver.findElement(By.xpath(path));
			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			testSteps.add("Hovers over Check-IN Date '" + getStartDate+"'");

			if(driver.findElements(By.xpath(OR_BE.startDateToolTipRateValue)).size() == 0)
			{
				testSteps.add("Successfully verified no rate is showing on check in date when Display Rates for Calendar toggle button is switched off.");
				logger.info("Successfully verified no rate is showing on check in date  when Display Rates for Calendar toggle button is switched off.");
			}
			else
			{
				testSteps.add("Failed : Rate is showing on check in date even though Display Rates for Calendar toggle button is switched off.");
				logger.info("Failed : Rate is showing on check in date even though Display Rates for Calendar toggle button is switched off.");
			}
			return testSteps;
		}

		public ArrayList<String> hoverOnCheckOutDateAndVerifyDates(WebDriver driver, String checkOutDate) throws ParseException {
			System.out.println("startDate: " + checkOutDate);
			ArrayList<String> testSteps = new ArrayList<>();
			clickOnEndDate(driver);
			
			String getEndDate = ESTTimeZone.getDateBaseOnDate(checkOutDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");

			String path = "//td[contains(@aria-label,'" + getEndDate + "')]";
			System.out.println("path: " + path);
			
			WebElement hoverElement = driver.findElement(By.xpath(path));
			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			testSteps.add("Hovers over Check-Out Date '" + getEndDate+"'");

			if(driver.findElements(By.xpath(OR_BE.startDateToolTipRateValue)).size() == 0)
			{
				testSteps.add("Successfully verified no rate is showing on check out date when Display Rates for Calendar toggle button is switched off.");
				logger.info("Successfully verified no rate is showing on check out date when Display Rates for Calendar toggle button is switched off.");
			}
			else
			{
				testSteps.add("Failed : Rate is showing on check out date even though Display Rates for Calendar toggle button is switched off.");
				logger.info("Failed : Rate is showing on check out date even though Display Rates for Calendar toggle button is switched off.");
			}
			
			return testSteps;
		}
		


	public void clickRateDropDown(WebDriver driver,String ratePlan, ArrayList<String> test_steps) {
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.SEARCH_DISPLAYED_RATE, driver);
		elements.SEARCH_DISPLAYED_RATE.click();
		test_steps.add("Open displayed rate grid dropdown");
		String ratePlanName = "//div[text()='"+ratePlan+"']";
		Wait.WaitForElement(driver, ratePlanName);
		test_steps.add("Select '" + ratePlanName + "' from dropdown.");
		logger.info("Select '" + ratePlanName + "' from dropdown.");
		driver.findElement(By.xpath(ratePlanName)).click();
		String saveButtonPath = "//h3[text()='Calendars']/../..//button[@type='submit']";
		Wait.WaitForElement(driver, saveButtonPath);
		driver.findElement(By.xpath(saveButtonPath)).click();
		test_steps.add("Successfully clicked on Save button.");
		logger.info("Successfully clicked on Save button.");
	}
	
	public ArrayList<String> enterCustomMessageAndSave(WebDriver driver,String message,
			boolean fullSection,String sectionName) throws InterruptedException {

		ArrayList<String> steps = new ArrayList<>();
		String xpath = "//h3[text()='" + sectionName + "']//parent::div//textarea";
		if (!fullSection) {
			xpath = "//h3[contains(text(),'" + sectionName + "')]//parent::div//textarea";
		}
		WebElement textArea = driver.findElement(By.xpath(xpath));
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		textArea.clear();
		textArea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		textArea.sendKeys(Keys.BACK_SPACE);
		textArea.sendKeys(message);
		steps.add("Enter Message '" + message + "'");
		logger.info("Enter Message '" + message + "'");

		String saveButtonPath = "//h3[text()='" + sectionName + "']//parent::div//button[text()='Save']";
		if (!fullSection) {
			saveButtonPath = "//h3[contains(text(),'" + sectionName + "')]//parent::div//button[text()='Save']";
		}
		Wait.WaitForElement(driver, saveButtonPath);
		try {
			driver.findElement(By.xpath(saveButtonPath)).click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(saveButtonPath)));
		}
		steps.add("Successfully clicked on Save button.");
		logger.info("Successfully clicked on Save button.");
		String enteredText = textArea.getText();
		assertEquals(message, enteredText, "Failed entered message missmatched");
		return steps;
	}
	
	public ArrayList<String> clickCheckAvailability(WebDriver driver) {
		Elements_BE BE = new Elements_BE(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		BE.searchRoomsButtonBE.click();
		testSteps.add("Click Check Availability");
		logger.info("Click Check Availability");
		return testSteps;

	}
	

	public ArrayList<String> noRoomAvailableMessagAfterEditSearchDetails(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE bookingEngine = new Elements_BE(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.No_ROOMS_AVAILABLE_MESSAGE_AFTER_EDIT_DETAILS), driver);
		if(bookingEngine.No_ROOMS_AVAILABLE_MESSAGE_AFTER_EDIT_DETAILS.isDisplayed()) {
			String message = bookingEngine.No_ROOMS_AVAILABLE_MESSAGE_AFTER_EDIT_DETAILS.getText();
			testSteps.add(message);
			logger.info(message);
		}
		return testSteps;
	}
	
	public ArrayList<String> verifyRoomClassAvailableInBookingEngine(WebDriver driver, String roomClass, boolean available) {
		boolean roomClassExist = false;
		
		ArrayList<String> steps = new ArrayList<>();
		String roomClassPath = "//div[@class='CA__roomClassName RoomDetails__name___Thpw0' and text()='"+roomClass+"']";
			int sizeOfRoomClassElement  = driver.findElements(By.xpath(roomClassPath)).size();

			logger.info("Expected : " + 0);
			logger.info("Found: " + sizeOfRoomClassElement);
			if(sizeOfRoomClassElement == 0)
			{
				steps.add("Successfully Verified '"+roomClass +"' is not showing in Booking Engine.");
				logger.info("Successfully Verified '"+roomClass +"' is not showing in Booking Engine.");
				
			}else {
				steps.add("Successfully Verified '"+roomClass +"' is showing in Booking Engine.");
				logger.info("Successfully Verified '"+roomClass +"' is showing in Booking Engine.");
				roomClassExist= true;
			}
			Assert.assertEquals(roomClassExist, available,"Failed :"+roomClass +" visibility status missmatched in Booking Engine.");
		
		return steps;
	}
	
	public ArrayList<String> selectCheckInCheckOutDate(WebDriver driver, String startDate, String endDate, boolean verifyFooterDetails)
			throws InterruptedException, ParseException {
		//header[text()='Check in']//following-sibling::div/span
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);
		ArrayList<String> testSteps = new ArrayList<>();
		clickOnStartDate(driver);
		if(verifyFooterDetails) {
		testSteps = verifyCheckInCalendarBookingEngineFooterFields(driver);
		}
		

		String getMonth = getMonthFromCalendarHeading1(driver);
		logger.info("getMonth: " + getMonth);
		String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

		boolean isMothEqual = false;
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(startDate, "dd/MM/yyyy", "MMMM yyyy");
		String getStartDate = ESTTimeZone.getDateBaseOnDate(startDate, "dd/MM/yyyy", "EEEE, MMMM d, yyyy");

		logger.info("expectedMonth: " + expectedMonth);
		String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
		System.out.println("path: " + path);
		// this is for start date
		while (!isMothEqual) {
			if (expectedMonth.equals(getMonth)) {
				logger.info("In first part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			}
			if (expectedMonth.equals(getMonthFromSecondPart)) {
				logger.info("in a second part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			} else {
				logger.info("in else");
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);
				getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

				logger.info("getMonth: " + getMonth);
			}

		}

		expectedMonth = ESTTimeZone.getDateBaseOnDate(endDate, "dd/MM/yyyy", "MMMM yyyy");
		String getEndDate = ESTTimeZone.getDateBaseOnDate(endDate, "dd/MM/yyyy", "EEEE, MMMM d, yyyy");
		path = "//td[contains(@aria-label,'" + getEndDate + "')]";
		System.out.println(path);

		isMothEqual = false;
		while (!isMothEqual) {
			if (expectedMonth.equals(getMonth)) {
				logger.info("In first part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			}
			if (expectedMonth.equals(getMonthFromSecondPart)) {
				logger.info("in a second part");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			} else {
				logger.info("in else");
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);
				getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

				logger.info("getMonth: " + getMonth);
			}

		}
		return testSteps;

	}
	
	public ArrayList<String> noRoomAvailableCustomMessag(WebDriver driver,String customMessage) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_BE bookingEngine = new Elements_BE(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_BE.No_ROOMS_AVAILABLE_CUSTOM_MESSAGE), driver);
		testSteps.add("Custom Message '" + customMessage + "'");
		logger.info("Expected Custom Message '" + customMessage + "'");
		boolean messageDisplayed = false;
		if(bookingEngine.No_ROOMS_AVAILABLE_CUSTOM_MESSAGE.isDisplayed()) {
			String message = bookingEngine.No_ROOMS_AVAILABLE_CUSTOM_MESSAGE.getText();
			//testSteps.add("Found Custom Message '" + message + "'");
			logger.info("Found Custom Message '" + message + "'");
			//assertTrue(message.equalsIgnoreCase(customMessage),"Failed: Custom Message missmatched");
			messageDisplayed = true;
		}
		assertTrue(messageDisplayed,"Failed: Custom Message is not displaying");
		testSteps.add("Successfully verified Custom Message '" + customMessage + "' is Displaying");
		logger.info("Successfully verified Custom Message '" + customMessage + "' is Displaying");
		return testSteps;
	}
	
	public ArrayList<String> verifyMinStayRuleOnDate(WebDriver driver, String checkInDate, String minStayValue) throws InterruptedException, ParseException {
		
		System.out.println("startDate: " + checkInDate);
		ArrayList<String> testSteps = new ArrayList<>();
		clickOnStartDate(driver);
		testSteps.add("Click On Checkin Date");
		logger.info("Click on Checkin Date");
		String getStartDate = ESTTimeZone.getDateBaseOnDate(checkInDate, "MM/d/yyyy", "EEEE, MMMM d, yyyy");

		String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
		System.out.println("path: " + path);
		
		WebElement dateIcon = driver.findElement(By.xpath(path));
		Wait.WaitForElement(driver,  path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.explicit_wait_visibilityof_webelement_120(dateIcon, driver);
		String xpath = path+ "//div[contains(@class,'dateRangePicker')][2]";
		String text = driver.findElement(By.xpath(xpath)).getText();
			testSteps.add("Min Stay Rule Text '" + text+"'");
			logger.info("Min Stay Rule Text '" + text+"'");
			if(text.equals(minStayValue+"n")) {
				
			}else {
				Wait.wait5Second();
				text = driver.findElement(By.xpath(xpath)).getText();
				testSteps.add("Min Stay Rule Text '" + text+"'");
				logger.info("Min Stay Rule Text '" + text+"'");
			}
		assertEquals(text,minStayValue+"n","Failed: minStay value missmatched");
		return testSteps;
	}
	
	//Added By Adhnan 1/21/21
	public String getLabelAmount(WebDriver driver, String label) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
		String xpath = "//span[contains(text(),'"+label+"')]//following-sibling::span[contains(@class,'checkout__price')]";
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		WebElement labelAmount = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(labelAmount, driver);
		String tax = "";
		tax = Utility.convertDollarToNormalAmount(driver, labelAmount.getText());

		return tax;
	}
	
	public void verifyProductExist(WebDriver driver, String productName, boolean exist) throws InterruptedException {
		String productNames = "//h3[contains(@class,'addOn__title')]";
		Wait.WaitForElement(driver, productNames);
		Wait.waitForElementToBeVisibile(By.xpath(productNames), driver);
		Wait.waitForElementToBeClickable(By.xpath(productNames), driver);
		List<WebElement> BEproductList = driver.findElements(By.xpath(productNames));
		Utility.ScrollToElement_NoWait(BEproductList.get(0), driver);
		boolean found = false;
		for(WebElement product: BEproductList) {
			if(product.getText().equals(productName)) {
				found = true;
			}
		}
		assertEquals(found,exist,"Failed "+ productName + "'s visibility status missmatched in Booking Engine products list");
	}
	
	public ArrayList<String> clickAddProductToReservation(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String buttonXpath = "//span[contains(text(),'To My Reservation')]//parent::button";
		WebElement button = driver.findElement(By.xpath(buttonXpath));
		Utility.ScrollToElement(button, driver);
		button.click();
		testSteps.add("Click 'Add $ To My Reservation' button");
		logger.info("Click 'Add $ To My Reservation' button");
		return testSteps;
		
	}

	public void clickWelcomePageLink(WebDriver driver) throws InterruptedException {
		String buttonXpath = "//a[contains(@class,'Header_logo__1AtfL')]";
		WebElement button = driver.findElement(By.xpath(buttonXpath));
		Wait.WaitForElement(driver, buttonXpath);
		Wait.waitForElementToBeVisibile(By.xpath(buttonXpath), driver);
		Wait.waitForElementToBeClickable(By.xpath(buttonXpath), driver);
		Utility.ScrollToElement(button, driver);
		button.click();
		logger.info("Click 'Welcome Page Link' from header");
		
		/*String configLink=driver.getCurrentUrl();
		String[] a = configLink.split("/admin/config/");
		String nbeLink=a[0];
		driver.navigate().to(nbeLink);*/
		
	}
	
	public String getCalculateAdditionalChargesForTheEnteredAdultsAndChild(WebDriver driver,String ratePlanName, int numberofDays,
			String adults, String child, String roomClass, String baseAmount, String adultCapacity,
			String personCapacity, String maxAdult, String maxPerson, String additionalAdultCharges,
			String additionalChildCharges,
			boolean isAdditionalChargesApplied)
			throws InterruptedException, NumberFormatException, ParseException {

		double base_amount = Double.parseDouble(baseAmount);
		ArrayList<String> testSteps = new ArrayList<String>();
		String selectedRatePlanChargesPath = "(//span[contains(@class,'ratePlanName') and text()='"+ratePlanName+"']"
				+ "//ancestor::div[contains(@class,'rateCard')]//div[contains(@class,'RateCharge')]//span[contains(@class,'pretaxTotal')])[1]";
		Wait.WaitForElement(driver, selectedRatePlanChargesPath);
		String ratePlanRoomClassChargesValue = driver.findElement(By.xpath(selectedRatePlanChargesPath)).getText();

		int adultDifference = 0;
		int childDifference = 0;
		double intial = 0;
		String rateApplied = "";
		logger.info("child: " + child);
		testSteps.add("child: " + child);
		logger.info("adults: " + adults);
		testSteps.add("adults: " + adults);
		int child_adults = Integer.parseInt(child) + Integer.parseInt(adults);
		logger.info("child_adults: " + child_adults);
		testSteps.add("child_adults: " + child_adults);

		if (isAdditionalChargesApplied) {
			logger.info("**********************************Third if:");

			if (Integer.parseInt(adults) <= Integer.parseInt(adultCapacity)) {
				if (Integer.parseInt(adults) > Integer.parseInt(maxAdult))
					adultDifference = Math.abs(Integer.parseInt(maxAdult) - Integer.parseInt(adults));

				child_adults = child_adults - adultDifference;
				int count = 0;
				boolean isTrue = false;
				int intMaxPerson = Integer.parseInt(maxPerson);
				if (child_adults > intMaxPerson) {
					for (int i = 1; i < child_adults; i++) {
						child_adults = child_adults - 1;
						if (child_adults == intMaxPerson) {
							count = i;
							isTrue = true;
							break;
						}
					}

				}

				if (isTrue) {
					childDifference = count;
				}
				logger.info("Additonal Adult: " + adultDifference);
				testSteps.add("Additonal Adult: " + adultDifference);
				logger.info("Additonal Child: " + childDifference);
				testSteps.add("Additonal Child: " + childDifference);

				logger.info("adult: " + adultDifference * (Double.parseDouble(additionalAdultCharges)));
				testSteps.add("adult: " + adultDifference * (Double.parseDouble(additionalAdultCharges)));
				logger.info("child: " + childDifference * (Double.parseDouble(additionalChildCharges)));
				testSteps.add("child: " + childDifference * (Double.parseDouble(additionalChildCharges)));
				logger.info("base_amount: " + base_amount);
				testSteps.add("base_amount: " + base_amount);

				intial = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
						+ (childDifference * (Double.parseDouble(additionalChildCharges)));
				if (numberofDays > 1) {
					intial = intial * numberofDays;
				}
			}
		}
		else
		{
			intial = base_amount * numberofDays;
		}
		
		String getValue = Utility.RemoveDollarandSpaces(driver, ratePlanRoomClassChargesValue).trim();
		rateApplied = String.format("%.2f", intial);
		logger.info("convert: " + getValue);
		logger.info("rateApplied: " + rateApplied);

		testSteps.add("Calculated base Rate value : " + getValue);
		logger.info("Calculated base Rate value : " + getValue);
		return getValue;
	}
	
	public String getCalculatedRatePlanAmountForTheEnteredAdultsAndChild(WebDriver driver, int numberofDays,
			String adults, String child, String roomClass, String baseAmount, String adultCapacity,
			String personCapacity, String maxAdult, String maxPerson, String additionalAdultCharges,
			String additionalChildCharges,
			boolean isAdditionalChargesApplied)
			throws InterruptedException, NumberFormatException, ParseException {

		double base_amount = Double.parseDouble(baseAmount);
		ArrayList<String> testSteps = new ArrayList<String>();

		int adultDifference = 0;
		int childDifference = 0;
		double intial = 0;
		String rateApplied = "";
		logger.info("child: " + child);
		testSteps.add("child: " + child);
		logger.info("adults: " + adults);
		testSteps.add("adults: " + adults);
		int child_adults = Integer.parseInt(child) + Integer.parseInt(adults);
		logger.info("child_adults: " + child_adults);
		testSteps.add("child_adults: " + child_adults);

		if (isAdditionalChargesApplied) {
			logger.info("**********************************Third if:");

			if (Integer.parseInt(adults) <= Integer.parseInt(adultCapacity)) {
				if (Integer.parseInt(adults) > Integer.parseInt(maxAdult))
					adultDifference = Math.abs(Integer.parseInt(maxAdult) - Integer.parseInt(adults));

				child_adults = child_adults - adultDifference;
				int count = 0;
				boolean isTrue = false;
				int intMaxPerson = Integer.parseInt(maxPerson);
				if (child_adults > intMaxPerson) {
					for (int i = 1; i < child_adults; i++) {
						child_adults = child_adults - 1;
						if (child_adults == intMaxPerson) {
							count = i;
							isTrue = true;
							break;
						}
					}

				}

				if (isTrue) {
					childDifference = count;
				}
				logger.info("Additonal Adult: " + adultDifference);
				testSteps.add("Additonal Adult: " + adultDifference);
				logger.info("Additonal Child: " + childDifference);
				testSteps.add("Additonal Child: " + childDifference);

				logger.info("adult: " + adultDifference * (Double.parseDouble(additionalAdultCharges)));
				testSteps.add("adult: " + adultDifference * (Double.parseDouble(additionalAdultCharges)));
				logger.info("child: " + childDifference * (Double.parseDouble(additionalChildCharges)));
				testSteps.add("child: " + childDifference * (Double.parseDouble(additionalChildCharges)));
				logger.info("base_amount: " + base_amount);
				testSteps.add("base_amount: " + base_amount);

				intial = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
						+ (childDifference * (Double.parseDouble(additionalChildCharges)));
				if (numberofDays > 1) {
					intial = intial * numberofDays;
				}
			}
		}
		else
		{
			intial = base_amount * numberofDays;
		}
		
		rateApplied = String.format("%.2f", intial);

		testSteps.add("Calculated base Rate value : " + rateApplied);
		logger.info("Calculated base Rate value : " + rateApplied);
		return rateApplied;
	}
	
	public void clickOnBookingEngineConfigLink(WebDriver driver,String partofBookingEngineChannel)
	{
		String bookingEngineConfigElementPath = "//a[contains(text(),'"+partofBookingEngineChannel+"')]";
		Wait.WaitForElement(driver, bookingEngineConfigElementPath);
		Wait.waitForElementToBeVisibile(By.xpath(bookingEngineConfigElementPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(bookingEngineConfigElementPath), driver);
	
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			WebElement bookingEngineConfigElement = driver.findElement(By.xpath(bookingEngineConfigElementPath));
			Utility.ScrollToElement(bookingEngineConfigElement, driver);
			if(driver.findElements(By.xpath(bookingEngineConfigElementPath)).size() > 0)
			{
				jse.executeScript("arguments[0].click();", bookingEngineConfigElement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Click on New Booking Engine link contains : " + partofBookingEngineChannel);
	}
	
	public ArrayList<String> verifyGroupNumberIsShowingInBE(WebDriver driver, String accountNumber) {
		
		ArrayList<String> testSteps = new ArrayList<>();
		String path ="//h3[contains(text(),'Group -')]";
		testSteps.add("Expected Group Number : "+accountNumber);
		logger.info("Expected Group Number : "+accountNumber);
		String foundAccountNumber = driver.findElement(By.xpath(path)).getText().split("Group - ")[1].split("/")[0].trim();
		testSteps.add("Found Group Number : "+foundAccountNumber);
		logger.info("Found Group Number : "+foundAccountNumber);
		
		Assert.assertEquals(foundAccountNumber,accountNumber ,"Failed : Group : "+accountNumber+" is not showing.");
		testSteps.add("Successfully verified Group : "+accountNumber+" is showing. ");
		logger.info("Successfully verified Group : "+accountNumber+" is showing. ");
		
		return testSteps;
	}
	
public ArrayList<String> verifyTaxIsShowingOrNotInBE(WebDriver driver, String taxAmount,String ratePlanName,String pageName) {
		
		ArrayList<String> testSteps = new ArrayList<>();
		String taxAmountPth = "";
		String taxAmountPath = "";
		String foundTaxAmount = "";
		taxAmount = String.format("%.2f", Double.valueOf(taxAmount)).trim();
		if(pageName.equals("roomDetailsPage"))
		{
			taxAmountPth = "//span[text()='"+ratePlanName+"']//..//following-sibling::div/h3[text()='Taxes']";
		}
		else if(pageName.equals("checkOutPage"))
		{
			taxAmountPth = "//span[text()='Taxes']";
		}
		else if(pageName.equals("reservationPage"))
		{
			taxAmountPth = "//div[text()='Taxes']";
		}
		if(driver.findElements(By.xpath(taxAmountPth)).size() > 0)
		{
			
			if(pageName.equals("roomDetailsPage"))
			{
				taxAmountPath = "//span[text()='"+ratePlanName+"']//..//following-sibling::div/h3[text()='Taxes']/span";
			}
			else if(pageName.equals("checkOutPage"))
			{
				taxAmountPath = "//span[text()='Taxes']//following-sibling::span";
			}
			else if(pageName.equals("reservationPage"))
			{
				taxAmountPath = "//div[text()='Taxes']//following-sibling::div";
			}
			Wait.WaitForElement(driver, taxAmountPath);
			if(pageName.equals("reservationPage"))
			{
				foundTaxAmount  = driver.findElement(By.xpath(taxAmountPath)).getText().split("\\$ ")[1].trim();
			}
			else
			{
				foundTaxAmount  = driver.findElement(By.xpath(taxAmountPath)).getText().split("\\$")[1].trim();
			}
			testSteps.add("Expected Tax Amount : "+taxAmount);
			logger.info("Expected Tax Amount : "+taxAmount);
			testSteps.add("Found Tax Amount : "+foundTaxAmount);
			logger.info("Found Tax Amount : "+foundTaxAmount);
			Assert.assertEquals(foundTaxAmount, taxAmount,"Failed : tax amount is mismatching.");
			testSteps.add("Successfully verified Tax Amount is matching. ");
			logger.info("Successfully verified Tax Amount is matching. ");
		}
		else
		{
			testSteps.add("Tax Amount is not showing.");
			logger.info("Tax Amount is not showing.");
		}
		return testSteps;
	}
public String getRatesRulesFromCalendar(WebDriver driver, String startDate, String endDate, ArrayList<String> testSteps,ArrayList<String> RatesInInncenter,ArrayList<String> MinStayRulesInInncenter,ArrayList<String> CheckInRulesInInncenter)
		throws InterruptedException, ParseException {
	
	ArrayList<String> allDates=new ArrayList<>();
	ArrayList<String> rates=new ArrayList();
	ArrayList<String> noCheckIn=new ArrayList();
	ArrayList<String> minStay=new ArrayList();
	allDates=Utility.getAllDatesBetweenCheckInOutDates(startDate, endDate);
	String rateValue=null;
	
	Elements_BE BE = new Elements_BE(driver);
	System.out.println("startDate: " + startDate);
	System.out.println("endDate: " + endDate);

	clickOnStartDate(driver);
	testSteps.add("Click On Check-In date");
	logger.info("Click On Check-In date");
	String getMonth = getMonthFromCalendarHeading1(driver);
	logger.info("getMonth: " + getMonth);
	String getMonthFromSecondPart = getMonthFromCalendarHeadingFromSecondMonth(driver);

	boolean isMothEqual = false;
	for(int i=0;i<allDates.size();i++)
	{
	String expectedMonth = ESTTimeZone.getDateBaseOnDate(allDates.get(i), "dd/MM/yyyy", "MMMM yyyy");
	String getStartDate = ESTTimeZone.getDateBaseOnDate(allDates.get(i), "dd/MM/yyyy", "EEEE, MMMM d, yyyy");
	
	
	logger.info("expectedMonth: " + expectedMonth);
	String path = "//td[contains(@aria-label,'" + getStartDate + "')]";
	System.out.println("path: " + path);
	
	// get tooltip rate Value 
	WebElement hoverElement = driver.findElement(By.xpath(path));
	Actions builder = new Actions(driver);
	builder.moveToElement(hoverElement).perform();
	testSteps.add("Hovers over Check-IN Date '" + allDates.get(i)+"'");
	Wait.wait2Second();
	Wait.WaitForElement(driver,  OR_BE.startDateToolTipRateValue);
	//Wait.waitForElementToBeVisibile(By.xpath( OR_BE.startDateToolTipRateValue), driver);
	Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath( OR_BE.startDateToolTipRateValue)), driver);
	
	rateValue = BE.startDateToolTipRateValue.getText();

	testSteps.add("Check-In Rate Value '" + rateValue+"'");
	logger.info("Check-In Rate Value '" + rateValue+"'");
	
	String[] arr = rateValue.split("\\r?\\n");
	
	if(arr.length==3) {
		String getRate=Utility.RemoveDollarandSpaces(driver, arr[0]).trim();
		rates.add((getRate.replace(".00", "")));
		noCheckIn.add("Yes");
		String[] getMinStay = arr[2].split(" ");
		minStay.add(getMinStay[0]);
	}
	if(arr.length==2) {
		String getRate=Utility.RemoveDollarandSpaces(driver, arr[0]).trim();
		rates.add((getRate.replace(".00", "")));
		if(arr[1].contains("Arrivals")) {
			noCheckIn.add("Yes");
			minStay.add("");
		}else {
			String[] getMinStay = arr[1].split(" ");
			minStay.add(getMinStay[0]);
			noCheckIn.add("No");
		}
		
	}
	if(arr.length==1) {
		String getRate=Utility.RemoveDollarandSpaces(driver, arr[0]).trim();
		rates.add((getRate.replace(".00", "")));
		noCheckIn.add("No");
		minStay.add("");
		
		}
	}
	logger.info("Expected :" + RatesInInncenter);
	logger.info("Found :" + rates);
	
	logger.info("Expected :" + MinStayRulesInInncenter);
	logger.info("Found :" + minStay);
	
	logger.info("Expected :" + CheckInRulesInInncenter);
	logger.info("Found :" + noCheckIn);
	
	if(RatesInInncenter.equals(rates)) {
		logger.info("Rates are matching in Inncenter and New Booking Engine Calendar");
		testSteps.add("Rates are matching in Inncenter and New Booking Engine Calendar");
	}else {
		logger.info("Rates are not matching in Inncenter and New Booking Engine Calendar");
		testSteps.add("Rates are not matching in Inncenter and New Booking Engine Calendar");
	}
	if(MinStayRulesInInncenter.equals(minStay)) {
		logger.info("Min Saty rules are matching in Inncenter and New Booking Engine Calendar");
		testSteps.add("Min Saty rules are matching in Inncenter and New Booking Engine Calendar");
	}else {
		logger.info("Min Saty rules are not matching in Inncenter and New Booking Engine Calendar");
		testSteps.add("Min Saty rules are not matching in Inncenter and New Booking Engine Calendar");
	}
	if(CheckInRulesInInncenter.equals(noCheckIn)) {
		logger.info("CheckIn Rules are matching in Inncenter and New Booking Engine Calendar");
		testSteps.add("CheckIn Rules are matching in Inncenter and New Booking Engine Calendar");
	}else {
		logger.info("CheckIn Rules are not matching in Inncenter and New Booking Engine Calendar");
		testSteps.add("CheckIn Rules are not matching in Inncenter and New Booking Engine Calendar");
	}

	
	return rateValue;
}
public ArrayList<String> verifyRoomClassAndItsDataInBookingEngine1(WebDriver driver,String roomClass,ArrayList<String> channelDataValues,boolean verifyDataValueOfRoomClass,ArrayList<String> minStayRuleValues,ArrayList<String> checkInRuleValues,ArrayList<String> checkOutRuleValues) {
	ArrayList<String> test_steps = new ArrayList<>();
	boolean dataValuesMatch = false;
	Boolean[] boolArray =  new Boolean[2];
		Elements_VerifyAvailabilityInBookingEngine elements = new Elements_VerifyAvailabilityInBookingEngine(driver);
	Wait.WaitForElement(driver, OR_BE.AVAILABILITY_ROOM_CLASSES);
	List<WebElement> lstRoomClasses = elements.AVAILABILITY_ROOM_CLASSES;
	
		for(WebElement objRoomClass : lstRoomClasses)
		{
			String foundRoomClass = objRoomClass.getText();
			if(roomClass.equalsIgnoreCase(foundRoomClass))
			{
				logger.info("Found : "+ foundRoomClass);
				test_steps.add("Successfully verified '"+foundRoomClass+"' exists.");
				logger.info("Successfully verified '"+foundRoomClass+"' exists.");
				if(verifyDataValueOfRoomClass == true)
				{
					boolArray = verifyRoomClassDataValues1(driver,channelDataValues,roomClass,minStayRuleValues,checkInRuleValues,checkOutRuleValues);
					if(boolArray[0])
					{
						test_steps.add("Successfully verified '"+foundRoomClass+"' data values are matching.");
						logger.info("Successfully verified '"+foundRoomClass+"' data values are matching.");
					}
					if(boolArray[1])
					{
						test_steps.add("Successfully verified '"+foundRoomClass+"' min nights values are matching.");
						logger.info("Successfully verified '"+foundRoomClass+"' min nights values are matching.");
					}
					if(boolArray[2])
					{
						test_steps.add("Successfully verified '"+foundRoomClass+"' CheckIn Rules  are matching.");
						logger.info("Successfully verified '"+foundRoomClass+"' CheckIn Rules  are matching.");
					}
					if(boolArray[3])
					{
						test_steps.add("Successfully verified '"+foundRoomClass+"' CheckOut Rules are matching.");
						logger.info("Successfully verified '"+foundRoomClass+"' CheckOut Rules are matching.");
					}
					else if(boolArray[0] == false || boolArray[1] == false || boolArray[2] == false || boolArray[3] == false)
					{
						test_steps.add("Failed to verify values for :  '"+foundRoomClass+"'.");
						logger.info("Failed to verify values for : '"+foundRoomClass+"'.");
					}
				}
			}
		}
	
	return test_steps;
}
public Boolean[] verifyRoomClassDataValues1(WebDriver driver,ArrayList<String> channelDataValues,String roomClass,ArrayList<String> minStayRuleValues,ArrayList<String> checkInRuleValues,ArrayList<String> checkOutRuleValues){
	
	boolean dataValuesMatch = false;
	boolean midNightStayValuesMatch =  false;
	boolean CheckInRuleMatch =  false;
	boolean CheckOutRuleMatch =  false;
	Boolean[] boolArray = new Boolean[4];
	String roomClassDataValuesInAvailabilityPath="//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li/button/span";
	Wait.WaitForElement(driver, roomClassDataValuesInAvailabilityPath);
	 ArrayList<String> roomClassMinNightsInAvailability = new ArrayList<>();
	 ArrayList<String> roomClassCheckInAvailability = new ArrayList<>();
	 ArrayList<String> roomClassCheckOutAvailability = new ArrayList<>();
	 roomClassDataValuesInAvailability = new ArrayList<String>();
	 for(int i=0;i< channelDataValues.size() ;i++)
	{
		roomClassDataValuesInAvailability.add(Utility.RemoveDollarandSpaces(driver, channelDataValues.get(i)).split("\\.")[0]);
	}
	for(int i=1;i<= channelDataValues.size() ;i++)
	{
		String minNightsPath ="//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li["+i+"]/button/div/i[@class='GridRate__xIcon___1JSbn']";
		if(driver.findElements(By.xpath(minNightsPath)).size() > 0)
		{
			String minStayValue = driver.findElement(By.xpath(minNightsPath)).getText();
			roomClassMinNightsInAvailability.add(minStayValue);
		}
		else
		{
			roomClassMinNightsInAvailability.add("");
		}
	}
	for(int i=1;i<= channelDataValues.size() ;i++)
	{
		String checkInElementPath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li["+i+"]/button/span//following-sibling::div/i[@class='be-icon-No-Arrivals']";
		String checkInValue = "";
		if(driver.findElements(By.xpath(checkInElementPath)).size() > 0)
		{
			checkInValue = "Yes";
			roomClassCheckInAvailability.add(checkInValue);
		}
		else
		{
			checkInValue = "No";
			roomClassCheckInAvailability.add(checkInValue);
		}
	}
	for(int i=1;i<= channelDataValues.size() ;i++)
	{
		String checkOutElementPath = "//div[@class='GridRoomInfo__roomName___ruS4C' and text()='"+roomClass+"']//..//..//following-sibling::ul/li["+i+"]/button/span//following-sibling::div/i[@class='be-icon-No-Departures']";
		String checkOutValue = "";
		if(driver.findElements(By.xpath(checkOutElementPath)).size() > 0)
		{
			checkOutValue = "Yes";
			roomClassCheckOutAvailability.add(checkOutValue);
		}
		else
		{
			checkOutValue = "No";
			roomClassCheckOutAvailability.add(checkOutValue);
		}
	
	}
	
	
	logger.info("Expected :" + channelDataValues);
	logger.info("Found :" + roomClassDataValuesInAvailability);
	
	
	logger.info("Expected Min Night Stay Values :" + minStayRuleValues);
	logger.info("Found Min Night Stay Values :" + roomClassMinNightsInAvailability);
	
	logger.info("Expected CheckIn rule Values :" + checkInRuleValues);
	logger.info("Found CheckIn rule Values :" + roomClassCheckInAvailability);
	
	logger.info("Expected CheckOut rule Values :" + checkOutRuleValues);
	logger.info("Found CheckOut rule Values :" + roomClassCheckOutAvailability);
	
	if(channelDataValues.equals(roomClassDataValuesInAvailability))
	{
		dataValuesMatch = true;
	}
	if(minStayRuleValues.equals(roomClassMinNightsInAvailability))
	{
		midNightStayValuesMatch = true;
	}
	if(checkInRuleValues.equals(roomClassCheckInAvailability))
	{
		CheckInRuleMatch = true;
	}
	if(checkOutRuleValues.equals(roomClassCheckOutAvailability))
	{
		CheckOutRuleMatch = true;
	}
	boolArray[0] = dataValuesMatch;
	boolArray[1] = midNightStayValuesMatch;
	boolArray[2] = CheckInRuleMatch;
	boolArray[3] = CheckOutRuleMatch;
	
	return boolArray;
}

public ArrayList<String> verifyRoomClassNotExists(WebDriver driver, String roomClass) {
	ArrayList<String> testSteps = new ArrayList<String>();
	String roomClassSelectionButton = "//div[text()='" + roomClass + "']/parent::div/following-sibling::div/a";
	if(driver.findElements(By.xpath(roomClassSelectionButton)).size()==0) {
		testSteps.add("Sucessfully Verified Room Class:" + roomClass + "is not available");
		logger.info("Sucessfully Verified Room Class:" + roomClass + "is not available");
	}else {
		Assert.assertTrue(false);
	}

	return testSteps;

}

public String getFee(WebDriver driver) throws InterruptedException {
	Elements_BE bookingEngineElements = new Elements_BE(driver);
	Wait.WaitForElement(driver, OR_BE.Fees);
	Wait.waitForElementToBeClickable(By.xpath(OR_BE.Fees), driver);
	Utility.ScrollToElement_NoWait(bookingEngineElements.Fees, driver);
	String getTaxValue = bookingEngineElements.Fees.getText();
	getTaxValue = getTaxValue.split("\\$")[1].trim();
	return getTaxValue;
}

public String geSubTotal(WebDriver driver) throws InterruptedException {
	Elements_BE bookingEngineElements = new Elements_BE(driver);
	Wait.WaitForElement(driver, OR_BE.SubTotal);
	Wait.waitForElementToBeClickable(By.xpath(OR_BE.SubTotal), driver);
	Utility.ScrollToElement_NoWait(bookingEngineElements.SubTotal, driver);
	String getSubTotal = bookingEngineElements.SubTotal.getText();
	getSubTotal = getSubTotal.split("\\$")[1].trim();
	return getSubTotal;
}

public String getTax(WebDriver driver) throws InterruptedException {
	Elements_BE bookingEngineElements = new Elements_BE(driver);
	Wait.WaitForElement(driver, OR_BE.Tax);
	Wait.waitForElementToBeClickable(By.xpath(OR_BE.Tax), driver);
	Utility.ScrollToElement_NoWait(bookingEngineElements.Tax, driver);
	String getTaxValue = bookingEngineElements.Tax.getText();
	getTaxValue = getTaxValue.split("\\$")[1].trim();
	return getTaxValue;
}

public ArrayList<String> toggleGuestProfileToggleButton(WebDriver driver,String switchOnOrOff) {
	
	ArrayList<String> testSteps = new ArrayList<>();
	Elements_BE bookingEngineElements = new Elements_BE(driver);
	Wait.WaitForElement(driver, OR_BE.guestProfileToggleButton);
	if (switchOnOrOff.equals("On")) {
		if (bookingEngineElements.guestProfileToggleButton.getAttribute("aria-checked").equals("false")) {
			bookingEngineElements.guestProfileToggleButton.click();
			testSteps.add("Successfully clicked on Guest Profile toggle button . ");
			logger.info("Successfully clicked on Guest Profile toggle button . ");
		} else {
			testSteps.add("Guest Profile button already on. ");
			logger.info("Guest Profile button already on . ");
		}
	} else if (switchOnOrOff.equals("Off")) {
		if (bookingEngineElements.guestProfileToggleButton.getAttribute("aria-checked").equals("false")) {
			testSteps.add("Guest Profile button already off. ");
			logger.info("Guest Profile button already off. ");
		} else {
			bookingEngineElements.guestProfileToggleButton.click();
			testSteps.add("Successfully clicked on Guest Profile toggle button and gets off . ");
			logger.info("Successfully clicked on Guest Profile toggle button and gets off . ");
		}
	}
	return testSteps;
}
public ArrayList<String> togglePromoCodeToggleButton(WebDriver driver,String switchOnOrOff) {
	
	ArrayList<String> testSteps = new ArrayList<>();
	Elements_BE bookingEngineElements = new Elements_BE(driver);
	Wait.WaitForElement(driver, OR_BE.PromoCodeToggleButton);
	if (switchOnOrOff.equals("On")) {
		if (bookingEngineElements.PromoCodeToggleButton.getAttribute("aria-checked").equals("false")) {
			bookingEngineElements.PromoCodeToggleButton.click();
			testSteps.add("Successfully clicked on Promo Code toggle button . ");
			logger.info("Successfully clicked on Promo Code toggle button . ");
		} else {
			testSteps.add("Promo Code button already on. ");
			logger.info("Promo Code button already on . ");
		}
	} else if (switchOnOrOff.equals("Off")) {
		if (bookingEngineElements.PromoCodeToggleButton.getAttribute("aria-checked").equals("false")) {
			testSteps.add("Promo Code button already off. ");
			logger.info("Promo Code button already off. ");
		} else {
			bookingEngineElements.PromoCodeToggleButton.click();
			testSteps.add("Successfully clicked on Promo Code toggle button and gets off . ");
			logger.info("Successfully clicked on Promo Code toggle button and gets off . ");
		}
	}
	return testSteps;
}
public ArrayList<String> savePromoCodeToggle(WebDriver driver) throws InterruptedException {
	
	ArrayList<String> testSteps = new ArrayList<>();
	Elements_BE bookingEngineElements = new Elements_BE(driver);
	Wait.WaitForElement(driver, OR_BE.savePromoCodeToggle);
	bookingEngineElements.savePromoCodeToggle.click();
	testSteps.add("Successfully clicked save button for promo code settings . ");
	logger.info("Successfully clicked save button for promo code settings . ");
	return testSteps;
}

public ArrayList<String> verifyPromoCodeFieldIsNotAvailable(WebDriver driver) {
	
	ArrayList<String> testSteps = new ArrayList<>();
	if(driver.findElements(By.xpath(OR_BE.PROMOCODE_BE)).size() > 0)
	{
		testSteps.add("Failed Promo Code is displaying in NBE when the toggle is Off ");
	}
	else
	{
		testSteps.add("Successfully verified Promo Code field not exists. ");
		logger.info("Successfully verified Promo Code field not exists. ");
	}
	
	return testSteps;
}
public ArrayList<String> verifyRoomsNotAvailableWithInvalidPromoCode(WebDriver driver) {
	
	ArrayList<String> testSteps = new ArrayList<>();
	if(driver.findElements(By.xpath(OR_BE.NoRoomWithInvalidPromoCode)).size() > 0)
	{
		testSteps.add("Successfully verified no rooms are available with invalid PromoCode");
		logger.info("Successfully verified no rooms are available with invalid PromoCode");
	}
	else
	{
		testSteps.add("Failed to Verify Rooms are available with invalid promo code ");
		logger.info("Failed to Verify Rooms are available with invalid promo code");
	}
	
	return testSteps;
}
public String getPromoCode(WebDriver driver) throws InterruptedException {
	Elements_BE BE = new Elements_BE(driver);
	Utility.ScrollToElement(BE.PromoCode, driver);
	String PromoCode = BE.PromoCode.getText();

	return PromoCode;
}

	
	


}