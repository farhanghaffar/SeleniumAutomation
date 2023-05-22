package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;

public class AirbnbReservationUI {
	public static Logger reslogger = Logger.getLogger("CPReservationPage");
	
	public void verifySettlementMsgForAirBNB(WebDriver driver, ArrayList test_steps)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		boolean isExist=Utility.isElementPresent(driver, By.xpath(OR_Reservation.CP_Reservation_CheckOutAllButton_ConfirmationMsg));
		if(isExist) {
		Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_CheckOutAllButton_ConfirmationMsg);
		Wait.explicit_wait_visibilityof_webelement(res.CP_Reservation_CheckOutAllButton_ConfirmationMsg, driver);
		Utility.ScrollToElement(res.CP_Reservation_CheckOutAllButton_ConfirmationMsg, driver);
		String Msg = "Do you wish to settle charges against non-Guest Folio(s) before completing check-out?";
		reslogger.info(res.CP_Reservation_CheckOutAllButton_ConfirmationMsg.getText());
		assertEquals(res.CP_Reservation_CheckOutAllButton_ConfirmationMsg.getText().toLowerCase().trim(),
				Msg.toLowerCase().trim(), "Failed: To verify Message");
		test_steps.add("Verified Message : <b>" + Msg + "</b>");
		reslogger.info("Verified Message : " + Msg);
		}
	}
	
	public void clickContinueButton(WebDriver driver, ArrayList test_steps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.continuePaymentButton);
		Utility.scrollAndClick(driver, By.xpath(OR_Reservation.continuePaymentButton));
		test_steps.add("Click on Continue button" );
		reslogger.info("Click on Continue button");

	}

}
