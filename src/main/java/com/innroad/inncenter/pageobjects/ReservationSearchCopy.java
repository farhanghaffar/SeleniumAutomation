package com.innroad.inncenter.pageobjects;

import java.io.BufferedReader;
import static org.testng.Assert.assertEquals;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.IReservationSearchCopy;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.innroad.inncenter.webelements.Elements_Reservation_CopyPage;
import com.innroad.inncenter.webelements.Elements_Reservation_SearchPage;

public class ReservationSearchCopy implements IReservationSearchCopy {

	public static String selectedOption;

	public static String copiedNextRoom;

	public static Logger resunassignedCopyLogger = Logger.getLogger("ReservationSearchCopy");

	public void roomAssignment(WebDriver driver, String PropertyName, String Nights, String Adults, String Children,
			String RatepromoCode, String CheckorUncheckAssign, String RoomClassName, String RoomNumber)
			throws InterruptedException {

		Elements_Reservation ReservationPage = new Elements_Reservation(driver);
		ReservationPage.Click_RoomPicker.click();
		Wait.explicit_wait_xpath(OR.Room_Assignment_PopUp, driver);
		Wait.wait3Second();
		/*
		 * try { new Select(ReservationPage.Select_property_RoomAssign).
		 * selectByVisibleText(PropertyName); } catch(Exception e) { new
		 * Select(ReservationPage.Select_property_RoomAssign2).
		 * selectByVisibleText(PropertyName); }
		 */

		Wait.explicit_wait_visibilityof_webelement(ReservationPage.Click_Arrive_Datepicker, driver);
		// Wait.wait15Second();
		ReservationPage.Click_Arrive_Datepicker.click();
		ReservationPage.Click_Today.click();
		ReservationPage.Enter_Nigts.clear();
		ReservationPage.Enter_Nigts.sendKeys(Nights);
		ReservationPage.Enter_Adults.sendKeys(Adults);
		ReservationPage.Enter_Children.sendKeys(Children);
		ReservationPage.Enter_Rate_Promocode.sendKeys(RatepromoCode);

		if (ReservationPage.Check_Assign_Room.isSelected()) {
			// ReservationPage.Check_Assign_Room.click();
			ReservationPage.Click_Search.click();
		} else {
			if (CheckorUncheckAssign.equals("Yes")) {
				ReservationPage.Check_Assign_Room.click();
				ReservationPage.Click_Search.click();
			} else {
				ReservationPage.Click_Search.click();
			}
		}
		try {
			Wait.wait2Second();
			new Select(ReservationPage.Select_Room_Class).selectByVisibleText(RoomClassName);
			selectedOption = new Select(ReservationPage.Validating_UnAssgined_DDL).getFirstSelectedOption().getText();
			resunassignedCopyLogger.info("selectedOption  " + selectedOption);
			Wait.wait5Second();
			if (selectedOption.equals("--Select--")) {
				// new
				// Select(ReservationPage.Select_Room_Number).selectByVisibleText(RoomNumber);
				// new
				// Select(ReservationPage.Select_Room_Number).selectByIndex(5);
				 new Select(ReservationPage.Select_Room_Number).selectByIndex(1);
			//	resunassignedCopyLogger.info(" Selected Room number is " + selectedRoom);

				Thread.sleep(5000);

				Wait.wait5Second();
			} else {
				resunassignedCopyLogger.info("Reservation is unassigned");
			}
		} catch (Exception e) {
			Wait.explicit_wait_xpath(OR.Validation_Text_NoRooms, driver);
			resunassignedCopyLogger.info("Room Class are not Available for these dates");

		}
		ReservationPage.Click_Select.click();
		try {
			Wait.explicit_wait_xpath(OR.Verify_RulesBroken_Popup, driver);
		} catch (Exception e) {
			resunassignedCopyLogger.info("Verification failed");
		}
		Wait.wait5Second();
		if (ReservationPage.Verify_RulesBroken_Popup.isDisplayed()) {
			ReservationPage.Click_Continue_RuleBroken_Popup.click();
		} else {
			resunassignedCopyLogger.info("Satisfied Rules");
		}
		Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(OR.Reservation_ModuleLoading)), 180);
		if (ReservationPage.Verify_Toaster_Container.isDisplayed()) {
			String getTotasterTitle = ReservationPage.Toaster_Title.getText();
			String getToastermessage = ReservationPage.Toaster_Message.getText();
			resunassignedCopyLogger.info(getTotasterTitle + " " + getToastermessage);
			Assert.assertEquals(getTotasterTitle, "Room assignment has changed.");
			Assert.assertEquals(getToastermessage, "Room assignment has changed.");
		}
		Wait.wait2Second();
		String getPropertyName = ReservationPage.Get_Property_Name.getText();
		String getRoomclassName_status = ReservationPage.Get_RoomClass_Status.getText();
		resunassignedCopyLogger.info(getRoomclassName_status);
		Assert.assertEquals(getPropertyName, PropertyName);
		String getRoomclassName[] = getRoomclassName_status.split(" ");
		// Assert.assertEquals(getRoomclassName[0],RoomClassName );
		if (CheckorUncheckAssign.equals("Yes")) {

		}

	}

	public void copyReservation(WebDriver driver) throws InterruptedException {

		Elements_Reservation_CopyPage res_copy = new Elements_Reservation_CopyPage(driver);

		res_copy.click_Copy_Button.click();

		Thread.sleep(8000);

		res_copy.click_copiedRoomPicker.click();

		Thread.sleep(6000);
		/*
		 * Select getRoomNumber=new Select(res_copy.get_Room_Number);
		 * copiedNextRoom=getRoomNumber.getFirstSelectedOption().getText();
		 */

		copiedNextRoom = new Select(res_copy.Validating_UnAssgined_DDL).getFirstSelectedOption().getText();
		// String copiedNextRoom=res_copy.get_copiedRoom_Number.getText();

		// Assert.assertEquals( nextRoom, copiedNextRoom);
		resunassignedCopyLogger.info(" Copied Next room Number is " + copiedNextRoom);

		// Assert.assertEquals( nextRoom,copiedNextRoom);
		// Assert.assertEquals( nextRoom, nextRoomCopied);
		Assert.assertEquals(selectedOption, copiedNextRoom);

		res_copy.click_cancel_button.click();
		Thread.sleep(8000);

		resunassignedCopyLogger.info(" Reservation Copied ");
		Thread.sleep(8000);
		String copiedGuestName = res_copy.get_Copied_Guest_Name.getText();
		resunassignedCopyLogger.info(" Name of the Copied Guest Name is " + copiedGuestName);
		Thread.sleep(5000);

	}
	
	public void roomAssignment_unassigned(WebDriver driver, String PropertyName, String Nights, String Adults, String Children,
			String RatepromoCode, String CheckorUncheckAssign2, String RoomClassName, String RoomNumber)
			throws InterruptedException {

		Elements_Reservation ReservationPage = new Elements_Reservation(driver);
		ReservationPage.Click_RoomPicker.click();
		Wait.explicit_wait_xpath(OR.Room_Assignment_PopUp, driver);
		Wait.wait3Second();
		/*
		 * try { new Select(ReservationPage.Select_property_RoomAssign).
		 * selectByVisibleText(PropertyName); } catch(Exception e) { new
		 * Select(ReservationPage.Select_property_RoomAssign2).
		 * selectByVisibleText(PropertyName); }
		 */

		Wait.explicit_wait_visibilityof_webelement(ReservationPage.Click_Arrive_Datepicker, driver);
		// Wait.wait15Second();
		ReservationPage.Click_Arrive_Datepicker.click();
		ReservationPage.Click_Today.click();
		ReservationPage.Enter_Nigts.clear();
		ReservationPage.Enter_Nigts.sendKeys(Nights);
		ReservationPage.Enter_Adults.sendKeys(Adults);
		ReservationPage.Enter_Children.sendKeys(Children);
		ReservationPage.Enter_Rate_Promocode.sendKeys(RatepromoCode);

		if (ReservationPage.Check_Assign_Room.isSelected()) {
			 ReservationPage.Check_Assign_Room.click();
			ReservationPage.Click_Search.click();
		} else {
			if (CheckorUncheckAssign2.equals("No")) {
				ReservationPage.Check_Assign_Room.click();
				ReservationPage.Click_Search.click();
			} else {
				ReservationPage.Click_Search.click();
			}
		}
		try {
			Wait.wait2Second();
			new Select(ReservationPage.Select_Room_Class).selectByVisibleText(RoomClassName);
			selectedOption = new Select(ReservationPage.Validating_UnAssgined_DDL).getFirstSelectedOption().getText();
			resunassignedCopyLogger.info("selectedOption  " + selectedOption);
			Wait.wait5Second();
			if (selectedOption.equals("--Select--")) {
				// new
				// Select(ReservationPage.Select_Room_Number).selectByVisibleText(RoomNumber);
				// new
				// Select(ReservationPage.Select_Room_Number).selectByIndex(5);
				 new Select(ReservationPage.Select_Room_Number).selectByIndex(1);
			//	resunassignedCopyLogger.info(" Selected Room number is " + selectedRoom);

				Thread.sleep(5000);

				Wait.wait5Second();
			} else {
				resunassignedCopyLogger.info("Reservation is unassigned");
			}
		} catch (Exception e) {
			Wait.explicit_wait_xpath(OR.Validation_Text_NoRooms, driver);
			resunassignedCopyLogger.info("Room Class are not Available for these dates");

		}
		ReservationPage.Click_Select.click();
		try {
			Wait.explicit_wait_xpath(OR.Verify_RulesBroken_Popup, driver);
		} catch (Exception e) {
			resunassignedCopyLogger.info("Verification failed");
		}
		Wait.wait5Second();
		if (ReservationPage.Verify_RulesBroken_Popup.isDisplayed()) {
			ReservationPage.Click_Continue_RuleBroken_Popup.click();
		} else {
			resunassignedCopyLogger.info("Satisfied Rules");
		}
		Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(OR.Reservation_ModuleLoading)), 180);
		if (ReservationPage.Verify_Toaster_Container.isDisplayed()) {
			String getTotasterTitle = ReservationPage.Toaster_Title.getText();
			String getToastermessage = ReservationPage.Toaster_Message.getText();
			resunassignedCopyLogger.info(getTotasterTitle + " " + getToastermessage);
			Assert.assertEquals(getTotasterTitle, "Room assignment has changed.");
			Assert.assertEquals(getToastermessage, "Room assignment has changed.");
		}
		Wait.wait2Second();
		String getPropertyName = ReservationPage.Get_Property_Name.getText();
		String getRoomclassName_status = ReservationPage.Get_RoomClass_Status.getText();
		resunassignedCopyLogger.info(getRoomclassName_status);
		Assert.assertEquals(getPropertyName, PropertyName);
		String getRoomclassName[] = getRoomclassName_status.split(" ");
		// Assert.assertEquals(getRoomclassName[0],RoomClassName );
		if (CheckorUncheckAssign2.equals("No")) {

		}

	}

	
	
	public ArrayList<String> copyReservation_unassigned(WebDriver driver, ArrayList<String> getTest_Steps) throws InterruptedException {

		Elements_Reservation_CopyPage resCopy_unassigned = new Elements_Reservation_CopyPage(driver);

		// Wait.explicit_wait_visibilityof_webelement(res_copy.click_Copy_Button);
		Wait.WaitForElement(driver, OR.click_Copy_Button);
		resCopy_unassigned.click_Copy_Button.click();
		resunassignedCopyLogger.info(" Reservation Copied ");
		
		Wait.WaitForElement(driver, OR.click_copiedRoomPicker);
		
		Wait.explicit_wait_visibilityof_webelement_120(resCopy_unassigned.click_copiedRoomPicker, driver);
		//Wait.WaitForElement(driver, OR.click_copiedRoomPicker);

		/*Utility.ScrollToElement(res_copy.click_copiedRoomPicker, driver);
		res_copy.click_copiedRoomPicker.click();

		//Thread.sleep(5000);
		Wait.WaitForElement(driver, OR.get_Room_Number);
		Select getRoomNumber = new Select(res_copy.get_Room_Number);
		copiedNextRoom = getRoomNumber.getFirstSelectedOption().getText();
		resunassignedCopyLogger.info(" Copied Next room Number is " + copiedNextRoom);

		// Assert.assertEquals( nextRoom,copiedNextRoom);
		// Assert.assertEquals( nextRoom, nextRoomCopied);
		// Assert.assertEquals( nextRoom, copiedNextRoom);

		res_copy.click_cancel_button.click();
		Thread.sleep(8000);*/
		Wait.WaitForElement(driver, OR.Get_RoomClass_Status);
		String getRoomclassName_status = resCopy_unassigned.Get_RoomClass_Status_CopyUnassigned.getText();
		resunassignedCopyLogger.info(getRoomclassName_status);

		String getRoomclassName[] = getRoomclassName_status.split(":");
		resunassignedCopyLogger.info(" Copied Room of Unassigned Reservation: " +getRoomclassName[1]);
		//roomClassUnassigned
		getTest_Steps.add(" Copied Room of Unassigned Reservation: " +getRoomclassName[1]);
		
		
		
		//Thread.sleep(8000);
		Wait.WaitForElement(driver, OR.get_Copied_Guest_Name);
		String copiedGuestName = resCopy_unassigned.get_Copied_Guest_Name.getText();
		resunassignedCopyLogger.info(" Name of the Copied Guest Name is " + copiedGuestName);
		Thread.sleep(5000);
		return getTest_Steps;

	}
	
	public ArrayList<String> getCopiedGuestProfile_Unassigned(WebDriver driver,String FirstName, String LastName,String Line1, String City,String State, String Country,String Postalcode,String Phonenumber,String Email, ArrayList<String> getTest_Steps ) {
		Elements_Reservation_CopyPage res_copy = new Elements_Reservation_CopyPage(driver);
		//Elements_Reservation ReservationPage = new Elements_Reservation(driver);
		Wait.WaitForElement(driver, OR.Copied_First_Name);
		

		if(	!FirstName.equals(res_copy.Copied_First_Name.getAttribute("value"))&&
			!LastName.equals(res_copy.Copied_Last_Name.getAttribute("value"))&&
			Line1.equals(res_copy.Copied_Line1.getAttribute("value"))&&
			City.equals(res_copy.Copied_City.getAttribute("value"))&&
			State.equals(new Select(res_copy.CopiedSelect_State).getFirstSelectedOption().getText()) &&
			Country.equals(new Select(res_copy.CopiedSelect_Country).getFirstSelectedOption().getText()) &&
			Postalcode.equals(res_copy.Copied_Postal_Code.getAttribute("value")) &&
			Phonenumber.equals(res_copy.Copied_Phone_Number.getAttribute("value")) &&
			Email.equals(res_copy.Copied_email.getAttribute("value"))){

			
			resunassignedCopyLogger.info("****Unassigned Reservation Guest Profile is copied *****");
			
		}
		else {
			resunassignedCopyLogger.info("****Guest profile of copied unassigned reservation is not same*****");
		}
		return getTest_Steps;
	}
	
	public ArrayList<String> verifyContactInformation(WebDriver driver, String FirstName, String LastName, String Line1,
			String City, String Country, String State, String Postalcode, String Phonenumber)
			throws InterruptedException {

		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_Reservation ReservationPage = new Elements_Reservation(driver);

		String contactFirstName = ReservationPage.Enter_Contact_First_Name2.getAttribute("value");
		assertEquals(contactFirstName, FirstName, "Failed First Name Not Matched");
		resunassignedCopyLogger.info("Successfully Verified Contact First Name : " + contactFirstName);
		test_steps.add("Successfully Verified Contact First Name : " + contactFirstName);

		String contactLastName = ReservationPage.Enter_Contact_Last_Name.getAttribute("value");
		assertEquals(contactLastName, LastName, "Failed Last Name Not Matched");
		resunassignedCopyLogger.info("Successfully Verified Contact Last Name : " + contactFirstName);
		test_steps.add("Successfully Verified Contact Last Name : " + contactFirstName);

		String enteredLine1 = ReservationPage.Enter_Line12.getAttribute("value");
		assertEquals(enteredLine1, Line1, "Failed Address Not Matched");
		resunassignedCopyLogger.info("Successfully Verified Contact Address : " + enteredLine1);
		test_steps.add("Successfully Verified Contact Address : " + enteredLine1);

		String enteredCity = ReservationPage.Enter_City2.getAttribute("value");
		assertEquals(enteredCity, City, "Failed City Not Matched");
		resunassignedCopyLogger.info("Successfully Verified Contact City : " + enteredCity);
		test_steps.add("Successfully Verified Contact City : " + enteredCity);

		String enteredPostalCode = ReservationPage.Enter_Postal_Code2.getAttribute("value");
		assertEquals(enteredPostalCode, Postalcode, "Failed Postal Code Not Matched");
		resunassignedCopyLogger.info("Successfully Verified Contact Postal Code : " + enteredPostalCode);
		test_steps.add("Successfully Verified Contact Postal Code : " + enteredPostalCode);

		String enteredPhoneNo = ReservationPage.Enter_Phone_Number2.getAttribute("value");
		enteredPhoneNo = enteredPhoneNo.replace("(", "");
		enteredPhoneNo = enteredPhoneNo.replace(")", "");
		enteredPhoneNo = enteredPhoneNo.replace(" ", "");
		enteredPhoneNo = enteredPhoneNo.replace("-", "");
		assertEquals(enteredPhoneNo, Phonenumber, "Failed Phone No Not Matched");
		resunassignedCopyLogger.info("Successfully Verified Contact Phone No : " + enteredPhoneNo);
		test_steps.add("Successfully Verified Contact Phone No : " + enteredPhoneNo);

//		String selectedCountry = new Select(ReservationPage.Select_Country2).getFirstSelectedOption().getText();
//		assertEquals(selectedCountry, Country, "Failed Country Not Matched");
//		resunassignedCopyLogger.info("Successfully Verified Contact Country : " + selectedCountry);
//		test_steps.add("Successfully Verified Contact Country : " + selectedCountry);
//
//		String selectedState = new Select(ReservationPage.Select_State2).getFirstSelectedOption().getText();
//		assertEquals(selectedState, State, "Failed State Not Matched");
//		resunassignedCopyLogger.info("Successfully Verified Contact State : " + selectedState);
//		test_steps.add("Successfully Verified Contact State : " + selectedState);

		return test_steps;
	}
	
	public ArrayList<String> contactInfoFirstLastName(WebDriver driver, String FirstName, String LastName) {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_Reservation_CopyPage ReservationPage = new Elements_Reservation_CopyPage(driver);

		Wait.explicit_wait_visibilityof_webelement_120(ReservationPage.Copied_First_Name, driver);
		ReservationPage.Copied_First_Name.clear();
		ReservationPage.Copied_First_Name.sendKeys(FirstName);
		resunassignedCopyLogger.info("Entered First Name : " + FirstName);
		test_steps.add("Entered First Name : " + FirstName);
		Wait.explicit_wait_visibilityof_webelement_120(ReservationPage.Copied_Last_Name, driver);
		ReservationPage.Copied_Last_Name.clear();
		ReservationPage.Copied_Last_Name.sendKeys(LastName);
		resunassignedCopyLogger.info("Entered Last Name : " + LastName);
		test_steps.add("Entered Last Name : " + LastName);

		return test_steps;
	}
	
	public void SaveRes_Updated(WebDriver driver) throws InterruptedException {
		Elements_Reservation Elements_Reservations = new Elements_Reservation(driver);
		Wait.WaitForElement(driver, OR.ReservationSaveButton_2);
		Wait.explicit_wait_xpath(OR.ReservationSaveButton_2, driver);
		Utility.ScrollToElement(Elements_Reservations.ReservationSaveButton_2, driver);
		Wait.wait3Second();
		Elements_Reservations.ReservationSaveButton_2.click();
		resunassignedCopyLogger.info("Click on save button");

		Wait.waitForCPReservationLoading(driver);
		
//		if(SaveButtonEnabled(driver)){
//			Elements_Reservations.ReservationSaveButton.click();
//		}
		
		int i=1;
		while (i<31) {
			i++;
			resunassignedCopyLogger.info("Waiting for Module Lodaing : " + i);
			if (!driver.findElement(By.xpath("//div[@id='ReservationDetail']//div[@class='modules_loading']"))
					.isDisplayed()) {
				break;
			} else {
				Wait.wait2Second();
			}
		}

		String res;
		i=1;
		while (i<5) {
			i++;
			resunassignedCopyLogger.info("Waiting for Confirmation Number : " + i);
			res = Elements_Reservations.Get_Reser_Number.getText();
			if (!res.equalsIgnoreCase("Pending")) {
				if (!res.equalsIgnoreCase("")) {
					break;
				}
			} else {
			}
			Wait.wait1Second();
			if (driver.findElements(By.cssSelector(OR.CancelDepositePolicy_Button)).size() > 0) {
				Elements_Reservations.CancelDepositePolicy_Button.get(3).click();
			} else {
			}
		}
		i=1;
		while (i<5) {
			i++;
			resunassignedCopyLogger.info("Waiting for Reservaed Saved : " + i);
			if (driver.findElements(By.xpath("//div[contains(text(),'Reservation Saved')]/preceding-sibling::button"))
					.size() <= 0) {
				break;
			} else {
				Wait.wait2Second();
			}
		}

		resunassignedCopyLogger.info("Reservation Saved");
	}
	
	public String getRoomNoRoomClass(WebDriver driver) {
		Elements_Reservation_CopyPage ReservationPage = new Elements_Reservation_CopyPage(driver);
		Wait.WaitForElement(driver, OR.Get_RoomClass_Status_CopyUnassigned);
		String text = ReservationPage.Get_RoomClass_Status_CopyUnassigned.getText();
		
		return text;
	}
	
	
}
