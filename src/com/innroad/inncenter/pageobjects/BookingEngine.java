package com.innroad.inncenter.pageobjects;

import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_BE;
import com.innroad.inncenter.properties.OR_RateGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_BE;

public class BookingEngine {

	public static Logger logger = Logger.getLogger("BookingEngine");

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

	public void selectCheckInCheckOutDate(WebDriver driver, String startDate, String endDate)
			throws InterruptedException, ParseException {

		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		clickOnStartDate(driver);

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

		return testSteps;
	}

	public String getReservationNumber(WebDriver driver) throws InterruptedException {
		Elements_BE BE = new Elements_BE(driver);
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

}