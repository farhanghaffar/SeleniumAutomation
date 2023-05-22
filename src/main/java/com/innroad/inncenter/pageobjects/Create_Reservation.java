package com.innroad.inncenter.pageobjects;


import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.interfaces.ICreateReservation;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.innroad.inncenter.webelements.WebElements_Create_Reservation;

public class Create_Reservation implements ICreateReservation {

	public static Logger reslogger = Logger.getLogger("CPReservationPage");
	@Override
	public void createReservation(WebDriver driver,String MarketSegment,
			String ReferralDropDown, String FirstName, String LastName,
			String Adults, String SelectRoomClassRoomAssign,
			String SelectRoomNumber) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		WebElements_Create_Reservation CREATE_RESERVATION = new WebElements_Create_Reservation(driver);
		
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		driver.switchTo().frame(0);
		Thread.sleep(5000);
		CREATE_RESERVATION.ReservationIcon.click();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		driver.switchTo().frame("main");
		Thread.sleep(5000);
		CREATE_RESERVATION.Reservationlink.click();
		Thread.sleep(6000);
		CREATE_RESERVATION.NewReservationButton.click();
		Thread.sleep(5000);
		new Select (driver.findElement(By.id(OR.MarketSegment))).selectByVisibleText(MarketSegment);
		Thread.sleep(3000);
		new Select (driver.findElement(By.id(OR.ReferralDropDown))).selectByVisibleText(ReferralDropDown);
		Thread.sleep(3000);
		CREATE_RESERVATION.UnCheckGuestProfile.click();
		CREATE_RESERVATION.GuestFirstName.sendKeys(FirstName);
		CREATE_RESERVATION.GuestLastName.sendKeys(LastName);
		CREATE_RESERVATION.ClickonAssignRooms.click();
		Thread.sleep(5000);
		//d.switchTo().defaultContent();
		Thread.sleep(5000);
		driver.switchTo().frame(driver.findElement(By.id("dialog-body0")));
		Thread.sleep(3000);
		CREATE_RESERVATION.StartDate.click();
		Thread.sleep(3000);
		CREATE_RESERVATION.ClickonToday.click();
		Thread.sleep(3000);
		CREATE_RESERVATION.NumberofAdults.clear();
		CREATE_RESERVATION.NumberofAdults.sendKeys(Adults);
		CREATE_RESERVATION.SearchButton.click();
		Thread.sleep(5000);
	    new Select (driver.findElement(By.id(OR.SelectRoomClassRoomAssign))).selectByVisibleText(SelectRoomClassRoomAssign);
	    Thread.sleep(3000);
	    new Select (driver.findElement(By.id(OR.SelectRoomNumber))).selectByVisibleText(SelectRoomNumber);
	    Thread.sleep(3000);
	    CREATE_RESERVATION.Select.click();
	    Thread.sleep(3000);
	    driver.switchTo().defaultContent();
		Thread.sleep(3000);
		driver.switchTo().frame("main");
		Thread.sleep(3000);
		CREATE_RESERVATION.Save.click();
		Thread.sleep(5000);
		
	}
	
	CPReservationPage reservationPage = new CPReservationPage();
	public String createMRBBasicReservation(WebDriver driver ,ArrayList<String> test_steps, String checkInDate, String checkOutDate, String adult, String child, String ratePlan,
			String roomClassName, String salutation, String guestFirstName,String guestLastName, ArrayList al,
			String paymentType, String cardNumber, String nameOnCard, String marketSegment,String referral) throws Exception {
		String reservationNo=null;
		String randomNum = Utility.GenerateRandomNumber();
		String yearDate = Utility.getFutureMonthAndYearForMasterCard();
		reservationPage.click_NewReservation(driver, test_steps);
		reservationPage.select_Dates(driver, test_steps, checkInDate, checkOutDate, adult, child, ratePlan, "", "");
		reservationPage.clickOnFindRooms(driver, test_steps);
		ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps,
				roomClassName.split("\\|")[0]);
		reservationPage.selectRoomToReserve(driver, test_steps, roomClassName.split("\\|")[0], rooms.get(0));
		Wait.wait15Second();
		ArrayList<String> rooms1 = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName.split("\\|")[1]);
		reservationPage.selectRoomToReserve(driver, test_steps, roomClassName.split("\\|")[1], rooms1.get(0));
		reservationPage.clickNext(driver, test_steps);
		reservationPage.enter_MRB_MailingAddressForMRB(driver, test_steps, salutation, guestFirstName+ randomNum, guestLastName+ randomNum, "No", al);
		reservationPage.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, yearDate);
		reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
		reservationPage.clickBookNow(driver, test_steps);
		reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
		return reservationNo;

	}
	
	
	
public void verifyCheckInStayInfoSingleReservation(WebDriver driver,String checkInDate, String checkOutDate, String adults, String children,
		String roomClassName, String ratePlanName,String roomCharges,ArrayList<String>test_steps) throws ParseException {
		
		int nights=0;		
		Date dateObj1 = Utility.convertStringtoDateFormat("dd/MM/yyyy", checkInDate);
		Date dateObj2 = Utility.convertStringtoDateFormat("dd/MM/yyyy", checkOutDate);
		if (Utility.parseDate(checkInDate, "dd/MM/yyyy", "dd").charAt(0) == '0') {
			checkInDate= Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM d, yyyy");			
		}else
		{
			checkInDate= Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");			
		}
		if (Utility.parseDate(checkOutDate, "dd/MM/yyyy", "dd").charAt(0) == '0') {
			checkOutDate=Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MMM d, yyyy");
		}else {
			checkOutDate=Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MMM dd, yyyy");		
		}
		long diff = dateObj2.getTime() - dateObj1.getTime();
		nights = (int) (diff / (24 * 60 * 60 * 1000));
		reslogger.info("difference between days: " + nights);	
		
		String checkInDates = "//div[contains(@class,'ir-checkin-stay-info')]/..//div[@class='checkin']/span[text()='"
				+ checkInDate + "']";
		assertEquals(driver.findElement(By.xpath(checkInDates)).getText(), checkInDate, "Failed to verify checkinDate");
		test_steps.add("Verified CheckInDate " + checkInDate);
		reslogger.info("Verified CheckInDate " + checkInDate);
		String checkoutDates = "//div[contains(@class,'ir-checkin-stay-info')]/..//div[@class='checkout']/span[text()='"
				+ checkOutDate + "']";
		assertEquals(driver.findElement(By.xpath(checkoutDates)).getText(), checkOutDate, "Failed to verify checkOutDate");
		test_steps.add("Verified CheckOutDate " + checkOutDate);
		reslogger.info("Verified CheckInDate " + checkOutDate);
		
		String night = "//div[contains(@class,'ir-checkin-stay-info')]/..//div[@class='noofnights']/span[text()='"
				+ nights + "N']";
		assertEquals(driver.findElement(By.xpath(night)).getText(), nights+"N", "Failed to verify nights");
		test_steps.add("Verified Nights " + nights);
		reslogger.info("Verified Nights " + nights);
		
		String adult = "(//div[contains(@class,'ir-checkin-stay-info')]/..//div//span[contains(@data-bind,'numberOfAdults')])[2]";
		String ad=Utility.getTextThroughJavaScriptInnerText(driver, adult);
		assertEquals(ad.trim(), adults.trim(), "Failed to verify adults");
		test_steps.add("Verified Adults " + adults);
		reslogger.info("Verified Adults " + adults);

		String childrens = "(//div[contains(@class,'ir-guestInfo')]//span[contains(@data-bind,'text: $data.numberOfChildren')])[2]";
		String ch=Utility.getTextThroughJavaScriptInnerText(driver, childrens);
		String child=driver.findElement(By.xpath(childrens)).getText();
		assertTrue(ch.contains(children));
		test_steps.add("Verified Children " + children);
		reslogger.info("Verified Children " + children);
		
		String roomClasss = "//div[contains(@class,'ir-checkin-stay-info')]/..//div//label[contains(@data-bind,'roomClassName')]";
		String roomClassNames=driver.findElement(By.xpath(roomClasss)).getText().replace(":", "");
		assertEquals(roomClassNames.trim(), roomClassName.trim(), "Failed to verify room class");
		test_steps.add("Verified Room Class " + roomClassName);
		reslogger.info("Verified Room Class " + roomClassName);
		
		String ratePlans = "//div[contains(@class,'ir-checkin-stay-info')]/..//span[contains(@data-bind,'ratePlanName')]";
		assertEquals(driver.findElement(By.xpath(ratePlans)).getText().trim(), ratePlanName.trim(), "Failed to verify ratePlan");
		test_steps.add("Verified Rate Plan " + ratePlanName);
		reslogger.info("Verified Rate Plan " + ratePlanName);

		if(Utility.validateString(roomCharges)) {
		String roomCharge = "//div[contains(@class,'ir-checkin-stay-info')]/..//div//label[contains(@data-bind,'roomTotal ')]";
		String charge=driver.findElement(By.xpath(roomCharge)).getText().replace("$", "");
		assertEquals(charge.trim(), roomCharges.trim(), "Failed to verify RoomCharges");
		test_steps.add("Verified Room Charges " + roomCharges);
		reslogger.info("Verified Room Charges " + roomCharges);
		}

	}

public String createSplitReservation(WebDriver driver ,ArrayList<String> test_steps, String checkInDate, String checkOutDate, String adult, String child, String ratePlan,
		String roomClassName, String salutation, String guestFirstName,String guestLastName,
		String paymentType, String cardNumber, String nameOnCard, String marketSegment,String referral, String isGuesProfile, 
		boolean isReservation,boolean closeReservation) throws Exception {
	String reservationNo=null;
	String randomNum = Utility.GenerateRandomNumber();
	String yearDate = Utility.getFutureMonthAndYearForMasterCard();
	Navigation navigation = new Navigation();
	try {
		if(isReservation) {
	navigation.cpReservation_Backward(driver);}}
	catch(Exception e) {
		if(isReservation) {
		navigation.reservation_Backward_2(driver);
		}
	}
	reservationPage.click_NewReservation(driver, test_steps);
	reservationPage.select_Dates(driver, test_steps, checkInDate, checkOutDate, adult, child, ratePlan, "", "Yes");
	reservationPage.clickOnFindRooms(driver, test_steps);
	String path="//a[contains(text(),'Room for Part')]";
	List<WebElement> elements= driver.findElements(By.xpath(path));
	Utility.ScrollToElement(elements.get(0), driver);
	elements.get(0).click();
	ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps,
			roomClassName.split("\\|")[0]);
	reservationPage.selectRoomToReserve(driver, test_steps, roomClassName.split("\\|")[0], rooms.get(0));
	Wait.wait15Second();
	Utility.ScrollToElement(elements.get(1), driver);
	elements.get(1).click();
	rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName.split("\\|")[1]);
	reservationPage.selectRoomToReserve(driver, test_steps, roomClassName.split("\\|")[1], rooms.get(1));
	Wait.wait5Second();
	Utility.ScrollToUp(driver);
	Utility.ScrollToTillEndOfPage(driver);
	Wait.wait5Second();
	reservationPage.clickNext(driver, test_steps);
	reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation, guestFirstName,
			guestLastName, isGuesProfile);
	reservationPage.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, yearDate);
	reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
	reservationPage.clickBookNow(driver, test_steps);
	reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
	reservationPage.clickCloseReservationSavePopup(driver, test_steps);
	if(closeReservation) {
		reservationPage.closeReservationTab(driver);
	}
	return reservationNo;

}
	
public String createBasicAccountReservation(WebDriver driver,String checkInDate, String checkOutDate, String adults, String children,
		String ratePlanName, String salutation, String guestFirstName, String guestLastName, String isGuesProfile,
		String paymentType, String cardNumber, String nameOnCard, String marketSegment, String referral, 
		String roomClassNames, boolean isReservation,boolean closeReservation, ArrayList<String> test_steps, String accountName)
		throws Exception {
	Navigation navigation = new Navigation();
	String reservationNo=null;
	try {
		if(isReservation) {
	navigation.cpReservation_Backward(driver);}}
	catch(Exception e) {
		if(isReservation) {
		navigation.reservation_Backward_2(driver);
		}
	}
	reservationPage.click_NewReservation(driver, test_steps);
	reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
	reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
	reservationPage.enter_Adults(driver, test_steps, adults);
	reservationPage.enter_Children(driver, test_steps, children);
	reservationPage.select_Rateplan(driver, test_steps, ratePlanName, "");
	reservationPage.clickOnFindRooms(driver, test_steps);
	ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassNames);
	reservationPage.selectRoomToReserve(driver, test_steps, roomClassNames, rooms.get(0));
	reservationPage.clickNext(driver, test_steps);
	String yearDate = Utility.getFutureMonthAndYearForMasterCard();	
	Utility.expiryDate=yearDate;
	reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation, guestFirstName,
			guestLastName, isGuesProfile);
	reservationPage.associateAccountInReservation(driver, test_steps, accountName, "");
	String path="//label[text()='Payment Method']/preceding-sibling::div//button[contains(@title,'Select')]";
	boolean isExist=  Utility.isElementPresent(driver, By.xpath(path));
	if(isExist) {
	reservationPage.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, yearDate);
	}
	String Path2="//label[text()='Referral']//parent::div/div/button[contains(@title,'Select')]";
	boolean isExist2= Utility.isElementPresent(driver, By.xpath(Path2));
	if(isExist2) {
	reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
	}
	reservationPage.clickBookNow(driver, test_steps);
	reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
	reservationPage.clickCloseReservationSavePopup(driver, test_steps);
	if(closeReservation) {
		reservationPage.closeReservationTab(driver);
	}
	return reservationNo;
}

	
public void clickTakePaymentofDeatilsPage(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
	boolean isExist= Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.takePaymentButton));
	if(isExist) {
	Wait.WaitForElement(driver, OR_Reservation.takePaymentButton);
	Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.takePaymentButton), driver);
	Utility.scrollAndClick(driver, By.xpath(OR_Reservation.takePaymentButton));
	test_steps.add("Click on Take Payment of Details Page");
	reslogger.info("Click on Take Payment of Details Page");
	}	
	}

	public void clickTakePaymentPayButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
	Wait.WaitForElement(driver, OR_Reservation.takepaymentPayButton);
	Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.takepaymentPayButton), driver);
	Utility.scrollAndClick(driver, By.xpath(OR_Reservation.takepaymentPayButton));
	test_steps.add("Click on Pay button of Take Payment ");
	reslogger.info("Click on Pay button Take Payment ");
	}
	
	
	public void verifyReservationStatusFromDropdown(WebDriver driver, ArrayList<String> test_steps) {
		String confirmed="//span[text()='Confirmed' and contains(@data-bind,'reservationStatusItem')]";
			Wait.WaitForElement(driver, confirmed);
			assertTrue(driver.findElement(By.xpath(confirmed)).isDisplayed(), "Failed to verify confirmed status");
			test_steps.add("Successfully verified Confirmed button in reservation staus drop down");
			reslogger.info("Successfully verified Confirmed button in reservation staus drop down");
			String onHold="//span[text()='On Hold' and contains(@data-bind,'reservationStatusItem')]";
			Wait.WaitForElement(driver, onHold);
			assertTrue(driver.findElement(By.xpath(onHold)).isDisplayed(), "Failed to verify On Hold status");
			test_steps.add("Successfully verified On Hold button in reservation staus drop down");
			reslogger.info("Successfully verified On Hold button in reservation staus drop down");	
			String noShow="//span[text()='No Show' and contains(@data-bind,'reservationStatusItem')]";
			Wait.WaitForElement(driver, noShow);
			assertTrue(driver.findElement(By.xpath(noShow)).isDisplayed(), "Failed to verify No Show status");
			test_steps.add("Successfully verified No Show button in reservation staus drop down");
			reslogger.info("Successfully verified No Show  button in reservation staus drop down");	
	}
}
