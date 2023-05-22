package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.javascript.host.media.webkitMediaStream;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_AdvanceGroups;
import com.innroad.inncenter.webelements.Elements_Groups;
import com.innroad.inncenter.webelements.Elements_OldGroups;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AdvGroups {

	public static Logger advGroupsLogger = Logger.getLogger("AdvGroups");

	public String createGroupaccount(WebDriver driver, String MarketingSegment, String GroupReferral,
			String GroupAccountName, String GroupFirstName, String GroupLastName, String GroupPhn, String GroupAddress,
			String GroupCity, String Groupstate, String GroupPostale, String Groupscountry, ArrayList getTest_Steps)
			throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);

		// AdvGroup.GroupsNewAccount.click();

		String random = Utility.generateRandomString();
		GroupAccountName = GroupAccountName + random;

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement btnNewAccount = AdvGroup.GroupsNewAccount;
		js.executeScript("arguments[0].click();", btnNewAccount);

		Wait.explicit_wait_visibilityof_webelement_600(AdvGroup.MarketingSegment, driver);
		new Select(AdvGroup.MarketingSegment).selectByVisibleText(MarketingSegment);
		getTest_Steps.add("Selecting group marketing segment : " + MarketingSegment);
		new Select(AdvGroup.GroupReferral).selectByVisibleText(GroupReferral);
		getTest_Steps.add("Selecting group referral : " + GroupReferral);
		AdvGroup.AccountFirstName.sendKeys(GroupAccountName);
		AdvGroup.GroupFirstName.sendKeys(GroupFirstName);
		getTest_Steps.add("Selecting group FirstName : " + GroupFirstName);
		AdvGroup.GroupLastName.sendKeys(GroupLastName);
		getTest_Steps.add("Selecting group LastName : " + GroupLastName);
		AdvGroup.GroupPhn.sendKeys(GroupPhn);
		getTest_Steps.add("Selecting group Phone  : " + GroupPhn);
		AdvGroup.GroupAddress.sendKeys(GroupAddress);
		getTest_Steps.add("Selecting group address  : " + GroupAddress);
		AdvGroup.GroupCity.sendKeys(GroupCity);
		getTest_Steps.add("Selecting group city  : " + GroupCity);
		new Select(AdvGroup.Groupstate).selectByVisibleText(Groupstate);
		getTest_Steps.add("Selecting group state  : " + Groupstate);
		AdvGroup.GroupPostale.sendKeys(GroupPostale);
		getTest_Steps.add("Selecting group postal  : " + GroupPostale);
		new Select(AdvGroup.Groupscountry).selectByVisibleText(Groupscountry);
		getTest_Steps.add("Selecting group country  : " + Groupscountry);
		Wait.wait2Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// AdvGroup.Mailinginfo.click();
		WebElement mailingInfoCB = AdvGroup.Mailinginfo;
		jse.executeScript("arguments[0].scrollIntoView(true);", mailingInfoCB);
		jse.executeScript("arguments[0].click();", mailingInfoCB);

		return GroupAccountName;
	}

	public String GetAccountNumbers(WebDriver driver) {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		String Accnum = AdvGroup.GroupAccountNumber.getAttribute("value");
		return Accnum;
	}

	public void SaveAdvGroup(WebDriver driver) {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.GroupSave.click();
		String validateError = "//div[@id='MainContent_dispSumm']";

	}

	public void SaveAdvGroupValidate(WebDriver driver, ArrayList test_steps) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.GroupSave.click();
		String validateError = "//div[@id='MainContent_dispSumm']";
		Wait.wait10Second();
		if (driver.findElements(By.xpath(validateError)).size() > 0) {
			test_steps.add("All fields marked with an * are required fields");
		} else {
			test_steps.add("Saved the group");
		}
	}

	public void EnterBlockName(WebDriver driver, ArrayList getTest_Steps, String BlockName) {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);

		// AdvGroup.Click_New_Block_button.click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement btnNewBlock = AdvGroup.Click_New_Block_button;
		js.executeScript("arguments[0].click();", btnNewBlock);
		getTest_Steps.add("Clicking on new block");

		Wait.explicit_wait_xpath(OR.Verify_Block_Details_Popup, driver);
		AdvGroup.Enter_Block_Name.sendKeys(BlockName);
		getTest_Steps.add("Enter block name : " + BlockName);
		AdvGroup.Click_Ok.click();
		getTest_Steps.add("Clicking on OK");
		Wait.explicit_wait_xpath(OR.Verify_Rate_Quote, driver);

	}

	public void SearchGroupCriteria(WebDriver driver, ArrayList getTest_Steps, String NumberofNights)
			throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.Select_Arrival_Date_Groups.click();
		AdvGroup.Click_Today_Arrival_Groups.click();
		getTest_Steps.add("Clicking on Today");
		AdvGroup.Enter_No_of_Nigts.sendKeys(NumberofNights);
		getTest_Steps.add("Enter Number of Nights : " + NumberofNights);
		AdvGroup.Click_Search_Group.click();
		getTest_Steps.add("Clicking on Search");
		Wait.explicit_wait_xpath(OR.Verify_Room_Class_Grid_Groups, driver);
		assertTrue(driver.findElement(By.xpath(OR.Room_Class_Grid)).isDisplayed(),
				"Failed: Room Class Grid Group is not Displayed");
		Wait.wait2Second();

	}

	// Select Room class for block

	public void BlockRoomForSelectedRoomclass(WebDriver driver, ArrayList getTest_Steps, String EnterBlockedcount,
			String RoomClassName) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		List<WebElement> GetRoomclassNames = AdvGroup.GetRoomclasses;
		advGroupsLogger.info("GetRoomclassNames" + GetRoomclassNames.size());
		Actions act = new Actions(driver);
		for (int i = 0; i < GetRoomclassNames.size(); i++) {
			if (GetRoomclassNames.get(i).getText().equals(RoomClassName)) {
				int index = i + 1;
				//driver.findElement(By.xpath(
				//	"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"))
				//	.sendKeys(Keys.chord(Keys.CONTROL, "a"), EnterBlockedcount);
			WebElement ele =driver.findElement(By.xpath(
					"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"));
					act.doubleClick(ele).perform();
					ele.sendKeys(EnterBlockedcount);
				
				
				getTest_Steps.add("Enter Blockedcount : " + EnterBlockedcount);
				Wait.wait1Second();
				break;
			} else {
				int index = i + 1;
//				driver.findElement(By.xpath(
				//		"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"))
					//	.sendKeys(Keys.chord(Keys.CONTROL, "a"), "0");
				WebElement ele =driver.findElement(By.xpath(
						"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"));
						act.doubleClick(ele).perform();
						ele.sendKeys("0");
				Wait.wait1Second();
			}
		}

	}

	public boolean ClickBookicon(WebDriver driver, ArrayList getTest_Steps, String RoomClassName, boolean flag)
			throws InterruptedException {
		getTest_Steps
				.add("********************************* Booking from blue button ********************************");
		Wait.wait2Second();
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.wait1Second();
		List<WebElement> RoomCount = AdvGroup.CountofRooms;
		advGroupsLogger.info("RoomCount" + RoomCount.size());
		for (int i = 0; i < RoomCount.size(); i++) {
			if (RoomCount.get(i).getText().equals(RoomClassName)) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].scrollIntoView(true);",
						driver.findElement(By.xpath("//div[@id='row" + i + "JQGrid']//div[@class='book']")));
				jse.executeScript("window.scrollBy(0,150);");
				Wait.wait2Second();
				driver.findElement(By.xpath("//div[@id='row" + i + "JQGrid']//div[@class='book']")).click();
				Wait.wait2Second();
				flag = true;
				break;
			} else if (i == (RoomCount.size() - 1)) {
				getTest_Steps.add("NO blue booking buttons");
				break;
			}
		}
		return flag;
	}

	public boolean ClickYellowicon(WebDriver driver, ArrayList getTest_Steps, boolean flag)
			throws InterruptedException {
		getTest_Steps
				.add("********************************* Booking from yellow button ********************************");
		Wait.wait3Second();
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.wait1Second();
		List<WebElement> RoomCount = AdvGroup.CountofRooms;
		advGroupsLogger.info("RoomCount" + RoomCount.size());
		for (int i = 0; i < RoomCount.size(); i++) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,150);");
			if (driver.findElements(By.xpath("//div[@id='row" + i + "JQGrid']//div[@class='bookyellow']")).size() > 0) {
				driver.findElement(By.xpath("//div[@id='row" + i + "JQGrid']//div[@class='bookyellow']")).click();
				Wait.wait2Second();
				flag = true;
				break;
			} else if (i == (RoomCount.size() - 1)) {
				advGroupsLogger.info("NO yellow booking buttons");
				getTest_Steps.add("NO Yellow booking buttons");
				break;
			}
		}
		return flag;
	}

	public boolean Clickredicon(WebDriver driver, ArrayList getTest_Steps, boolean flag) throws InterruptedException {
		getTest_Steps.add("********************************* Booking from red button ********************************");
		Wait.wait3Second();
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.wait1Second();
		List<WebElement> RoomCount = AdvGroup.CountofRooms;
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,150);");
		advGroupsLogger.info("RoomCount" + RoomCount.size());

		/*
		 * for (int i = 0; i < RoomCount.size(); i++) {
		 * jse.executeScript("window.scrollBy(0,20);");
		 * if(driver.findElements(By.xpath("//div[@id='row" + i +
		 * "JQGrid']//div[@class='bookred']")).size()>0){
		 * driver.findElement(By.xpath("//div[@id='row" + i +
		 * "JQGrid']//div[@class='bookred']")).click(); Wait.wait2Second(); flag=true;
		 * break; }else if(i==(RoomCount.size()-1)){
		 * advGroupsLogger.info("NO red booking buttons"); break; } }
		 */

		Wait.wait5Second();

		if (driver.findElements(By.xpath("//div[@class='bookred']")).size() > 0) {
			jse.executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.xpath("//div[@class='bookred']")));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-40)");
			Wait.wait2Second();
			driver.findElement(By.xpath("//div[@class='bookred']")).click();
			Wait.wait2Second();
			flag = true;
		}

		return flag;
	}

	public void NavigateRoomBlock(WebDriver driver, ArrayList getTest_Steps) {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.Navigate_Room_Block.click();
		getTest_Steps.add("Clicking on Room block");
		Wait.explicit_wait_xpath(OR.Verifyroom_block_content, driver);
	}

	public String PickupFromRoomingList(WebDriver driver, String FirstName, String LastName, String RoomClassName,
			String Amount, String PaymentMethod) throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String PickupCount = "//a[contains(text(),'" + RoomClassName
				+ "')]//ancestor::div[@role='gridcell']//preceding-sibling::div[2]/div";
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(PickupCount)));
		String countBefore = driver.findElement(By.xpath(PickupCount)).getText();
		AdvGroup.Rooming_List.click();
		System.out.println("Click Rooming List");
		driver.switchTo().frame("dialog-body0");
		System.out.println("Switch Frame: body0");
		Wait.wait3Second();
		Wait.explicit_wait_xpath(OR.Rooming_List_Popup, driver);
		assertTrue(AdvGroup.Rooming_List_Popup.isDisplayed(), "Failed: Rooming List Popup is not displaying");

		AdvGroup.RoomingList_FirstName.sendKeys(FirstName);
		AdvGroup.RoomingList_LastName.sendKeys(LastName);

		new Select(AdvGroup.RoomingList_RoomClass).selectByVisibleText(RoomClassName);
		// new Select(AdvGroup.RoomingList_RoomNumber).selectByIndex(1);
		// AdvGroup.RoomingList_Amount.sendKeys(Amount);
		AdvGroup.RoomingList_BillingInfo.click();
		driver.switchTo().defaultContent();
		System.out.println("Switch Frame: default");
		driver.switchTo().frame("dialog-body1");
		System.out.println("Switch Frame: body1");
		Wait.waitUntilPresenceOfElementLocated(OR.BillingInfo_Popup, driver);
		new Select(AdvGroup.BillingInfo_PaymentMethod).selectByVisibleText(PaymentMethod);
		Wait.wait2Second();
		AdvGroup.BillingInfo_Save.click();
		driver.switchTo().defaultContent();
		System.out.println("Switch Frame Default");
		driver.switchTo().frame("dialog-body0");
		System.out.println("Switch Frame: body0");
		// assertTrue(AdvGroup.BillingInfo_Tick.isDisplayed(),"Failed: Billing
		// info");
		jse.executeScript("arguments[0].scrollIntoView(true);", AdvGroup.RoomingList_Pickup);
		AdvGroup.RoomingList_Pickup.click();

		driver.switchTo().defaultContent();
		System.out.println("Switch Frame Default");
		driver.switchTo().frame("dialog-body1");
		System.out.println("Switch Frame: body1");
		Wait.explicit_wait_xpath(OR.RoomingListSummary, driver);
		assertTrue(AdvGroup.RoomingListSummary.isDisplayed(), "Failed: Rooming List Summary");
		// assertTrue(false);
		String resNumber = AdvGroup.GeneratedReservationNumber.getText();
		System.out.println("Reservation Number : " + resNumber);
		AdvGroup.RoomingListSummary_Close.click();
		driver.switchTo().defaultContent();
		System.out.println("Switch Frame Default");
		Wait.wait5Second();
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(PickupCount)));
		String countAfter = driver.findElement(By.xpath(PickupCount)).getText();
		System.out.println(countBefore + "  " + countAfter);
		assertTrue(Integer.parseInt(countAfter) == Integer.parseInt(countBefore) + 1,
				"Failed: Pickup Count is not Increased");

		return resNumber;
	}

	public void VerifyCreatedReservation(WebDriver driver, String resNumber, String AccountName, String FirstName,
			String LastName) {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", AdvGroup.Groups_ReservationsTab);
		AdvGroup.Groups_ReservationsTab.click();

		Wait.explicit_wait_visibilityof_webelement_350(AdvGroup.ReservationNumbers, driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Groups_ReservationPage, driver);
		jse.executeScript("arguments[0].scrollIntoView(true);", AdvGroup.ReservationNumbers);
		int size = driver.findElements(By.xpath(OR.ReservationNumbers)).size();
		System.out.println("Reservation list Size is " + size);
		boolean found = false;
		for (int i = 1; i <= size; i++) {
			String ResNumberPath = "(" + OR.ReservationNumbers + ")[" + i + "]";
			String resNUM = driver.findElement(By.xpath(ResNumberPath)).getText();
			if (resNUM.replaceAll("\\s", "").equals(resNumber.replaceAll("\\s", ""))) {
				found = true;
				String Guestname = "(" + OR.ReservationGuest + ")[" + i + "]";
				assertTrue(driver.findElement(By.xpath(Guestname)).getText().contains(FirstName + " " + LastName),
						"Failed: GuestName Missmatched");

				/*
				 * Wait.waitUntilPresenceOfElementLocated(OR. ReservationDetailPage);
				 * assertTrue(AdvGroup.ReservationDetailPage_GuestName.getText()
				 * .contains(GuestName),"Failed: Account name");
				 * assertTrue(AdvGroup.ReservationDetailPage_Account.getText().
				 * contains(AccountName),"Failed: Account name");
				 * AdvGroup.ReservationDetailPage_Close.click();
				 */break;
			}
		}
		assertTrue(found, "Failed: Reservation " + resNumber + " not found");
	}

	/*
	 * public ArrayList<String> verifyCreatedReservation(WebDriver driver, String
	 * resNumber, String AccountName, String FirstName, String LastName) {
	 * ArrayList<String> test_steps = new ArrayList<>();
	 * 
	 * Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	 * Wait.waitUntilPresenceOfElementLocated(OR.Groups_ReservationsTab, driver);
	 * JavascriptExecutor jse = (JavascriptExecutor) driver;
	 * jse.executeScript("arguments[0].scrollIntoView(true);",
	 * AdvGroup.Groups_ReservationsTab);
	 * Wait.explicit_wait_elementToBeClickable(AdvGroup.Groups_ReservationsTab,
	 * driver); AdvGroup.Groups_ReservationsTab.click();
	 * test_steps.add("Click on reservation tab");
	 * advGroupsLogger.info("Click on reservation tab");
	 * Wait.waitUntilPresenceOfElementLocated(OR.ReservationNumbers, driver);
	 * //Wait.waitForElementToBeInvisible(driver, AdvGroup.ReservationNumbers, 120);
	 * String resNumber1 = AdvGroup.ReservationNumbers.getText();
	 * System.out.println("Reservation Number : " + resNumber1);
	 * test_steps.add("Reservation Number : " + resNumber1);
	 * advGroupsLogger.info("Reservation Number : " + resNumber1);
	 * 
	 * //AdvGroup.RoomingListSummary_Close.click();
	 * 
	 * 
	 * Wait.explicit_wait_visibilityof_webelement_350(AdvGroup.ReservationNumbers,
	 * driver); Wait.waitUntilPresenceOfElementLocated(OR.Groups_ReservationPage,
	 * driver); jse.executeScript("arguments[0].scrollIntoView(true);",
	 * AdvGroup.ReservationNumbers);
	 * Wait.waitUntilPresenceOfElementLocated(OR.ReservationNumbers, driver); int
	 * size = driver.findElements(By.xpath(OR.ReservationNumbers)).size();
	 * System.out.println("Reservation list Size is " + size);
	 * test_steps.add("Reservation list Size is " + size);
	 * advGroupsLogger.info("\"Reservation list Size is " + size); boolean found =
	 * false; for (int i = 1; i <= size; i++) { String ResNumberPath = "(" +
	 * OR.ReservationNumbers + ")[" + i + "]"; String resNUM =
	 * driver.findElement(By.xpath(ResNumberPath)).getText(); if
	 * (resNUM.replaceAll("\\s", "").equals(resNumber.replaceAll("\\s", ""))) {
	 * found = true; String Guestname = "(" + OR.ReservationGuest + ")[" + i + "]";
	 * assertTrue(driver.findElement(By.xpath(Guestname)).getText().contains(
	 * FirstName + " " + LastName), "Failed: GuestName Missmatched");
	 * 
	 * 
	 * Wait.waitUntilPresenceOfElementLocated(OR. ReservationDetailPage);
	 * assertTrue(AdvGroup.ReservationDetailPage_GuestName.getText()
	 * .contains(GuestName),"Failed: Account name");
	 * assertTrue(AdvGroup.ReservationDetailPage_Account.getText().
	 * contains(AccountName),"Failed: Account name");
	 * AdvGroup.ReservationDetailPage_Close.click(); break; } }
	 * 
	 * return test_steps; }
	 */

	public void ClickCreateBlock1(WebDriver driver) {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Utility.ElementFinderUntillNotShow(By.xpath(OR.Click_Create_Block1), driver);
		AdvGroup.Click_Create_Block1.click();
		Wait.explicit_wait_xpath(OR.Verify_Block_Details_Popup, driver);

	}

	public void ClickEditBlock(WebDriver driver) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", AdvGroup.RoomDetailsPage_EditButton);
		driver.manage().window().maximize();
		driver.switchTo().frame("MainContent_Iframe_accountpicker");
		assertTrue(AdvGroup.RoomDetailsPage_BlockDetails.isDisplayed(), "Failed:Block Details not displayed");
		assertTrue(AdvGroup.BlockDetailsPage_RoomBlockAttiribute.isDisplayed(),
				"Failed:Room Block Attributes not displayed");

	}

	public void ClickBlockOptionsButton(WebDriver driver) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.BlockDetailsPage_BlockOptionsButton.click();
		assertTrue(AdvGroup.BlockDetailsPage_ChargeRouting.isDisplayed(),
				"Failed:Charge Routing Details not displayed");
	}

	public void ClickPreviewFolioButton(WebDriver driver) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.BlockDetailsPage_PreviewFolioButton.click();
		assertTrue(AdvGroup.BlockDetailsPage_LineItems.isDisplayed(), "Failed:Line item Details not displayed");

	}

	public void ClickYellowBookIcon(WebDriver driver) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Utility.ScrollToElement(AdvGroup.YellowBookIcon.get(0), driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", AdvGroup.YellowBookIcon.get(0));
		Wait.explicit_wait_xpath(OR.New_Reservation_Page_Load_2, driver);

	}

	public void ClickRedBookIcon(WebDriver driver) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Utility.ScrollToElement(AdvGroup.RedBookIcon.get(0), driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", AdvGroup.RedBookIcon.get(0));
		Wait.explicit_wait_xpath(OR.New_Reservation_Page_Load_2, driver);

	}

	public void ClickBlueBookIcon(WebDriver driver) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Utility.ScrollToElement(AdvGroup.BlueBookIcon.get(0), driver);
		jse.executeScript("arguments[0].click();", AdvGroup.BlueBookIcon.get(0));
		Wait.explicit_wait_xpath(OR.New_Reservation_Page_Load_2, driver);

	}

	public void clickBlueBookIcon(WebDriver driver) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Utility.ScrollToElement(AdvGroup.BlueBookIcon.get(0), driver);
		jse.executeScript("arguments[0].click();", AdvGroup.BlueBookIcon.get(0));

	}

	public void ChangeAccountNumber(WebDriver driver, String AccountNumber) throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		String selectedOption = null;
		Wait.wait5Second();
		// try {
		AdvGroup.GroupAccountNumber.clear();
		AdvGroup.GroupAccountNumber.sendKeys(AccountNumber);
		Wait.wait3Second();
		selectedOption = AdvGroup.GroupAccountNumber.getAttribute("value");
		System.out.println(selectedOption);
		Assert.assertEquals(AccountNumber, selectedOption);

	}

	public void groupSearch(WebDriver driver, ArrayList getTest_Steps, String groupName) {
		Elements_AdvanceGroups group = new Elements_AdvanceGroups(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOf(group.Group_Search_GroupName));
		group.Group_Search_GroupName.sendKeys(groupName);

		getTest_Steps.add("Enter Group Name : " + groupName);
		advGroupsLogger.info("Enter Group Name : " + groupName);

		Wait.WaitForElement(driver, OR.Group_Search_Go);
		group.Group_Search_Go.click();
		getTest_Steps.add("Click on Go");
		advGroupsLogger.info("Click on Go");

		String loc = "//a[contains(text(),'" + groupName.trim() + "')]";
		Wait.WaitForElement(driver, loc);
		driver.findElement(By.xpath(loc)).click();
		getTest_Steps.add("Opening the Group");
		advGroupsLogger.info("Opening the Group");

	}

	public void searchGroupReservation(WebDriver driver, ArrayList getTest_Steps, String reservation) {
		Elements_AdvanceGroups group = new Elements_AdvanceGroups(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOf(group.Group_Reservation_Search_Reservation));
		group.Group_Reservation_Search_Reservation.sendKeys(reservation);
		getTest_Steps.add("Enter Reservation id : " + reservation);
		advGroupsLogger.info("Enter Reservation id : " + reservation);

		Wait.WaitForElement(driver, OR.Group_Reservation_Search_Go);
		group.Group_Reservation_Search_Go.click();
		getTest_Steps.add("Click on Go");
		advGroupsLogger.info("Click on Go");

		String loc = "//td[contains(text(),'" + reservation + "')]";
		Wait.WaitForElement(driver, loc);
		if (driver.findElements(By.xpath(loc)).size() > 0) {
			getTest_Steps.add("Reservation found in group reservation tab");
			advGroupsLogger.info("Reservation found in group reservation tab");
		} else {
			getTest_Steps.add("Reservation not found in group reservation tab");
			advGroupsLogger.info("Reservation not found in group reservation tab");
		}
	}

	public void click_GroupsReservationTab(WebDriver driver, ArrayList getTest_Steps) {
		Elements_AdvanceGroups group = new Elements_AdvanceGroups(driver);
		Wait.WaitForElement(driver, OR.Groups_ReservationsTab);
		group.Groups_ReservationsTab.click();
		getTest_Steps.add("Click on New Reservation Tab in Group");
		advGroupsLogger.info("Click on New Reservation Tab in Group");
	}

	public void click_RoomingList(WebDriver driver, ArrayList getTest_Steps) throws InterruptedException {
		getTest_Steps.add("********************************* Rooming list ********************************");
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.wait5Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,200)");
		Wait.WaitForElement(driver, OR.Group_Click_Roominglistbutton);
		AdvGroup.Group_Click_Roominglistbutton.click();
		getTest_Steps.add("Click on Rooming List");
	}

	public void enter_pickupdetails(WebDriver driver, ArrayList getTest_Steps) throws InterruptedException {
		Wait.wait5Second();
		driver.switchTo().frame(driver.findElement(By.id("dialog-body0")));
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.WaitForElement(driver, OR.oldGroup_Verify_Roominglistpopup);

		String loc = "//table[@id='dgRoomingList']/tbody/tr";

		int numberofRooms = driver.findElements(By.xpath(loc)).size();
		String fName, lName, roomclass, room, amount, billinginfobutton;
		String paymentMethod = "//select[@id='drpBilling_TypeID']";
		String paymentMethodSave = "//input[@id='btnSave']";
		String close = "//input[@id='btnClose']";
		for (int i = 2; i <= numberofRooms; i++) {
			driver.switchTo().defaultContent();
			Wait.wait2Second();
			driver.switchTo().frame(driver.findElement(By.id("dialog-body0")));
			fName = "//table[@id='dgRoomingList']/tbody/tr[" + i + "]/td[1]/input";
			lName = "//table[@id='dgRoomingList']/tbody/tr[" + i + "]/td[2]/input";
			roomclass = "//table[@id='dgRoomingList']/tbody/tr[" + i + "]/td[5]/select";
			room = "//table[@id='dgRoomingList']/tbody/tr[" + i + "]/td[6]/select";
			amount = "//table[@id='dgRoomingList']/tbody/tr[" + i + "]/td[7]/input";
			billinginfobutton = "//table[@id='dgRoomingList']/tbody/tr[" + i + "]/td[8]/input";

			Wait.WaitForElement(driver, fName);
			driver.findElement(By.xpath(fName)).sendKeys("Test res Fname" + i);
			getTest_Steps.add("Test res Fname" + i);
			Wait.WaitForElement(driver, lName);
			driver.findElement(By.xpath(lName)).sendKeys("Test res Lname" + i);
			getTest_Steps.add("Test res Lname" + i);
			Wait.WaitForElement(driver, roomclass);
			new Select(driver.findElement(By.xpath(roomclass))).selectByIndex(1);
			getTest_Steps.add("Select the room class");
			Wait.WaitForElement(driver, room);
			new Select(driver.findElement(By.xpath(room))).selectByIndex(i - 1);
			getTest_Steps.add("selecting the room");
			Wait.WaitForElement(driver, amount);
			driver.findElement(By.xpath(amount)).sendKeys(Keys.chord(Keys.CONTROL, "a"), "200");
			getTest_Steps.add("Enter amount 200");
			Wait.WaitForElement(driver, billinginfobutton);
			driver.findElement(By.xpath(billinginfobutton)).click();
			getTest_Steps.add("Click on billing info button");
			Wait.wait2Second();
			driver.switchTo().defaultContent();
			Wait.wait2Second();
			driver.switchTo().frame(driver.findElement(By.id("dialog-body1")));
			Wait.WaitForElement(driver, paymentMethod);
			new Select(driver.findElement(By.xpath(paymentMethod))).selectByVisibleText("Cash");
			getTest_Steps.add("Select payment method as Cash");
			Wait.WaitForElement(driver, paymentMethodSave);
			driver.findElement(By.xpath(paymentMethodSave)).click();
			getTest_Steps.add("Click on Save");
			Wait.wait2Second();
			driver.switchTo().defaultContent();

		}
		driver.switchTo().defaultContent();
		Wait.wait2Second();
		driver.switchTo().frame(driver.findElement(By.id("dialog-body0")));
		Wait.WaitForElement(driver, OR.Groups_Select_Pickupbutton_Roominglistpopup);
		AdvGroup.Groups_Select_Pickupbutton_Roominglistpopup.click();
		getTest_Steps.add("Click on Pickup");
		driver.switchTo().defaultContent();
		ArrayList al = new ArrayList();

		String resLoc = "//table[@id='dgRoomingList']/tbody/tr";
		Wait.wait5Second();

		while (true) {
			if (!driver.findElement(By.xpath("//div[@id='InnerFreezePane']")).isDisplayed()) {
				System.out.println("in if");
				break;
			} else {
				System.out.println("Waiting for frame");
				Wait.wait5Second();
			}
		}
		Wait.wait5Second();
		Wait.WaitForElement_ID(driver, "dialog-body1");
		driver.switchTo().frame(driver.findElement(By.id("dialog-body1")));

		for (int i = 2; i <= numberofRooms; i++) {
			resLoc = "//table[@id='dgRoomingList']/tbody/tr[" + i + "]/td[1]";
			Wait.WaitForElement(driver, resLoc);
			al.add(driver.findElement(By.xpath(resLoc)).getText());
			getTest_Steps.add("Created reservation : " + driver.findElement(By.xpath(resLoc)).getText());
		}

		Wait.WaitForElement(driver, close);
		driver.findElement(By.xpath(close)).click();
		getTest_Steps.add("Click on Close");
		Wait.wait40Second();
		driver.switchTo().defaultContent();
		if (driver.findElements(By.xpath("//span[contains(text(),'Picked up %: 100%')]")).size() > 0) {
			getTest_Steps.add("Pickup from rooming list done successfully");
		}
	}

	public void click_GroupNewReservation(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Groups group = new Elements_Groups(driver);
		Wait.WaitForElement(driver, OR.Verify_New_Reservation_Enabled);
		group.Verify_New_Reservation_Enabled.click();
		test_steps.add("Click on New Reservation in Group");
		advGroupsLogger.info("Click on New Reservation in Group");
	}

	public void navigateFolio(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Groups group = new Elements_Groups(driver);

		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOf(group.Group_Folio));
		Wait.WaitForElement(driver, OR.Group_Folio);
		group.Group_Folio.click();
		test_steps.add("Click on Group Folio");
		advGroupsLogger.info("Click on Group Folio");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <selectRoomInGroupBlock> ' Description: <Select room and find
	 * avilableRooms,blocks,Avarage rate > ' Input parameters: < ',' separated
	 * parameters type> ( int, int, String ) ' Return value: ArrayList<String> '
	 * Created By: <Reddy Ponnolu> ' Created On: <04/05/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public ArrayList<String> selectRoomInGroupBlock(WebDriver driver, String RoomClassName,
			ArrayList<String> test_steps) throws InterruptedException {
		String groupBlockMinAvilableRooms = "//span[@title='" + RoomClassName
				+ "']/../..//following-sibling::span[contains(@data-bind,'A1_avail')]";
		String groupBlockmaxBlock = "//span[@title='" + RoomClassName
				+ "']/../..//following-sibling::input[contains(@data-bind,'A1_block')]";
		String groupBlockAVgRate = "//span[@title='" + RoomClassName
				+ "']/../..//following-sibling::input[contains(@data-bind,'A1_price')]";
		Utility.ScrollToEnd(driver);
		Wait.waitForElementToBeVisibile(By.xpath("//span[@title='" + RoomClassName
				+ "']/../..//following-sibling::span[contains(@data-bind,'A1_avail')]"), driver);
		String avilableRooms = driver.findElement(By.xpath(groupBlockMinAvilableRooms)).getText();
		test_steps.add("group Block MinAvilable Rooms are:" + avilableRooms);
		advGroupsLogger.info("group Block MinAvilable Rooms are:" + avilableRooms);

		Wait.waitForElementToBeVisibile(By.xpath("//span[@title='" + RoomClassName
				+ "']/../..//following-sibling::input[contains(@data-bind,'A1_block')]"), driver);
		driver.findElement(By.xpath(groupBlockmaxBlock)).click();
		Wait.wait2Second();
		String blocks = driver.findElement(By.xpath(groupBlockmaxBlock)).getAttribute("value");
		test_steps.add("group Block Max Block are:" + blocks);
		advGroupsLogger.info("group Block Max Block:" + blocks);

		Wait.waitForElementToBeVisibile(By.xpath("//span[@title='" + RoomClassName
				+ "']/../..//following-sibling::input[contains(@data-bind,'A1_price')]"), driver);
		String blockRate = driver.findElement(By.xpath(groupBlockAVgRate)).getAttribute("value");
		test_steps.add("Group Block average  Rate:" + blockRate);
		advGroupsLogger.info("Group Block average  Rate:" + blockRate);

		return test_steps;

	}

	public ArrayList<String> searchGroupCriteriaWithOutBlackDates(WebDriver driver, String NumberofNights,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.Select_Arrival_Date_Groups.click();
		String onlyDate = Utility.GeetingNextWeek(driver);
		int nextDayDate = Integer.parseInt(onlyDate);
		nextDayDate++;
		advGroupsLogger.info("Next Week Next Day is " + nextDayDate + "th");
		test_steps.add("Next Week Next Day is " + nextDayDate + "th");
		AdvGroup.Select_Arrival_Date_Groups.click();
		// RateQuotePage.Click_Arrive_DatePicker.click();
		test_steps.add("Clicked on DatePicker");
		String cellPath = "//div[@class='datepicker-days']//td[contains(text(),'" + nextDayDate + "')]";
		WebElement Next_Date = driver.findElement(By.xpath(cellPath));
		Next_Date.click();
		advGroupsLogger.info("Next Week Next day " + nextDayDate + "th is Selected");
		test_steps.add("Next Week Next day " + nextDayDate + "th is Selected");
		AdvGroup.Enter_No_of_Nigts.sendKeys(NumberofNights);
		test_steps.add("Enter Number of Nights : " + NumberofNights);
		AdvGroup.Click_Search_Group.click();
		test_steps.add("Clicking on Search");
		// Wait.explicit_wait_xpath(OR.Verify_Room_Class_Grid_Groups, driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Verify_Room_Class_Grid_Groups), driver);
		assertTrue(driver.findElement(By.xpath(OR.Room_Class_Grid)).isDisplayed(),
				"Failed: Room Class Grid Group is not Displayed");
		Wait.wait2Second();
		return test_steps;

	}

	public ArrayList<String> updatedAutomaticallyAssignedRooms(WebDriver driver, String UpdatedBlockedRoom)
			throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		List<WebElement> BlockedRooms = AdvGroup.GetBlockedRowsize;
		advGroupsLogger.info(BlockedRooms.size());

		for (int i = 0; i < BlockedRooms.size(); i++) {
			String BlockedRoomsCount = BlockedRooms.get(i).getAttribute("value");
			int BlockedRoomsvalues = Integer.parseInt(BlockedRoomsCount);
			if (BlockedRoomsvalues > 0) {
				int index = i + 1;
				//driver.findElement(By.xpath(
				//		"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"))
				//		.sendKeys(Keys.chord(Keys.CONTROL, "a"), UpdatedBlockedRoom);
				WebElement ele =driver.findElement(By.xpath(
						"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"));
						act.doubleClick(ele).perform();
						ele.sendKeys(UpdatedBlockedRoom);
				testSteps.add("Enter UpdatedBlockedRoom : " + UpdatedBlockedRoom);
			} else {
				int index = i + 1;
			//	driver.findElement(By.xpath(
			//			"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"))
			//			.sendKeys(Keys.chord(Keys.CONTROL, "a"), "0");
				WebElement ele =driver.findElement(By.xpath(
						"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"));
						act.doubleClick(ele).perform();
						ele.sendKeys("0");
			}
		}
		return testSteps;
	}

	// Select Room class for block

	public ArrayList<String> BlockRoomForSelectedRoomclass(WebDriver driver, String enterBlockedcount,
			String roomClassName) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		List<WebElement> GetRoomclassNames = AdvGroup.GetRoomclasses;
		advGroupsLogger.info("GetRoomclassNames" + GetRoomclassNames.size());
		Actions act = new Actions(driver);
		for (int i = 0; i < GetRoomclassNames.size(); i++) {
			if (GetRoomclassNames.get(i).getText().equals(roomClassName)) {
				int index = i + 1;
			//	driver.findElement(By.xpath(
			//			"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"))
			//			.sendKeys(Keys.chord(Keys.CONTROL, "a"), enterBlockedcount);
				WebElement ele =driver.findElement(By.xpath(
						"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"));
						act.doubleClick(ele).perform();
						ele.sendKeys(enterBlockedcount);
				testSteps.add("Enter Blockedcount : " + enterBlockedcount + " for Room Class : " + roomClassName);
				break;
			} else {
				int index = i + 1;
				WebElement ele =driver.findElement(By.xpath(
						"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"));
						act.doubleClick(ele).perform();
						ele.sendKeys("0");
				//driver.findElement(By.xpath(
				//		"//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr[" + index + "]/td[3]/input"))
				//		.sendKeys(Keys.chord(Keys.CONTROL, "a"), "0");
			}
		}
		return testSteps;

	}

	public ArrayList<String> ClickCreateBlock(WebDriver driver, ArrayList<String> testSteps)
			throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.WaitForElement(driver, OR.Click_Create_Block);
		AdvGroup.Click_Create_Block.click();
		testSteps.add("Clicking on create block");

		if (driver.findElements(By.xpath(OR.Click_Continue_Block_Night)).size() > 0) {
			AdvGroup.Click_Continue_Block_Night.click();
			testSteps.add("Clicking on continue");
		}

		Wait.WaitForElement(driver, OR.Verify_Release_Date_Popup);
		AdvGroup.Click_Ok_On_Rel_Popup.click();
		testSteps.add("Clicking on Release pop up");
		// Wait.WaitForElement(driver, OR.Verifyroom_block_content);
		return testSteps;

	}

	public ArrayList<String> clickOnGroupsReservationTab(WebDriver driver, ArrayList<String> testSteps) {
		Elements_AdvanceGroups group = new Elements_AdvanceGroups(driver);
		Wait.WaitForElement(driver, OR.Groups_ReservationsTab);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Groups_ReservationsTab), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Groups_ReservationsTab), driver);
		group.Groups_ReservationsTab.click();
		testSteps.add("Click on new reservation tab in group");
		advGroupsLogger.info("Click on new reservation tab in group");
		return testSteps;
	}

	// Created By Adhnan 7/20/2020
	public void verifyRoomNumberExistInPickupFromRoomingList(WebDriver driver, String RoomClassName, String roomNumber,
			boolean exist) throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		AdvGroup.Rooming_List.click();
		System.out.println("Click Rooming List");
		driver.switchTo().frame("dialog-body0");
		System.out.println("Switch Frame: body0");
		Wait.explicit_wait_xpath(OR.Rooming_List_Popup, driver);
		assertTrue(AdvGroup.Rooming_List_Popup.isDisplayed(), "Failed: Rooming List Popup is not displaying");
		new Select(AdvGroup.RoomingList_RoomClass).selectByVisibleText(RoomClassName);
		List<WebElement> getRoomNumber = driver.findElements(By.xpath(OR.RoomingList_RoomNumber));

		boolean found = false;
		for (int i = 0; i < getRoomNumber.size(); i++) {
			String tempRN = getRoomNumber.get(i).getText();
			advGroupsLogger.info("Room Number Options : " + tempRN);
			if (tempRN.equals(roomNumber)) {
				found = true;
				System.out.println("Found Room Number");
			}
		}
		// assertEquals(found, exist, "Failed: room Number existance missmatched");

		Wait.WaitForElement(driver, OR.oldGroup_NewBlock_Cancel);
		Wait.waitForElementToBeVisibile(By.xpath(OR.oldGroup_NewBlock_Cancel), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.oldGroup_NewBlock_Cancel), driver);
		driver.findElement(By.xpath(OR.oldGroup_NewBlock_Cancel)).click();
		advGroupsLogger.info("PickUp Cancel Button Clicked");
		driver.switchTo().defaultContent();
		System.out.println("Switch Frame: default");
	}

	public ArrayList<String> verifyMinStayRuleInBlock(WebDriver driver, String roomClassName, String minStayRule)
			throws InterruptedException {
		String rulePath = "//a[text()='" + roomClassName
				+ "']//..//..//following-sibling::div//span[contains(@class,'minimum_stay_2')]";
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, rulePath);
		Wait.waitForElementToBeVisibile(By.xpath(rulePath), driver);
		Wait.waitForElementToBeClickable(By.xpath(rulePath), driver);
		WebElement rule = driver.findElement(By.xpath(rulePath));
		Utility.ScrollToElement_NoWait(rule, driver);
		String getText = rule.getText().trim();
		testSteps.add("Expected : " + minStayRule + "N");
		advGroupsLogger.info("Expected : " + minStayRule + "N");
		testSteps.add("Found : " + getText);
		advGroupsLogger.info("Found : " + getText);

		assertEquals(getText, minStayRule + "N", "Failed min stay rule count didn't match");
		testSteps.add("Verified min stay rule in newly created block");
		advGroupsLogger.info("Verified min stay rule in newly created block");
		return testSteps;
	}

	public String getAvgRate(WebDriver driver, String RoomClassName) throws InterruptedException {
		Wait.wait5Second();
		String path = "//tbody[@data-bind='foreach: serverData.objListRoomClass']//tr/td[1]/span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ RoomClassName.toUpperCase() + "']/../..//td[4]//input";
		try {
			Wait.WaitForElement(driver, path);
		} catch (Exception e) {
			System.out.println("in catch");
		}
		return driver.findElement(By.xpath(path)).getAttribute("value");
	}

	public void verifyAvgRate(WebDriver driver, String delim, String RoomClassNames, String expectedRates,
			ArrayList<String> testSteps) throws NumberFormatException, InterruptedException {
		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(RoomClassNames, delim);
		ArrayList<String> rateList = Utility.convertTokenToArrayList(expectedRates, delim);
		int index = 0;
		for (String roomClass : roomClassList) {
			assertEquals(Double.parseDouble(getAvgRate(driver, roomClass)), Double.parseDouble(rateList.get(index)),
					"Failed to Verify Average Rate ");
			testSteps.add("Successfully Verified Average Rate : " + rateList.get(index) + " where RoomClass is : "
					+ roomClass);
			advGroupsLogger.info("Successfully Verified Average Rate : " + rateList.get(index)
					+ " where RoomClass is : " + roomClass);
			index++;
		}

	}

	/*
	 * <<<<<<<HEAD=======
	 * 
	 * public void EnterBlockName(WebDriver driver, String BlockName,
	 * ArrayList<String> test_steps) throws InterruptedException {
	 * Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	 * 
	 * // Wait.explicit_wait_xpath(OR.Click_New_Block_button, driver);
	 * Wait.explicit_wait_visibilityof_webelement_150(AdvGroup.
	 * Click_New_Block_button, driver); //
	 * Utility.ScrollToElement(AdvGroup.Click_New_Block_button, driver);
	 * Wait.wait1Second();
	 * Wait.explicit_wait_elementToBeClickable(AdvGroup.Click_New_Block_button,
	 * driver); AdvGroup.Click_New_Block_button.click();
	 * test_steps.add("New Block Button Clicked");
	 * advGroupsLogger.info("New Block Button Clicked");
	 * assertTrue(AdvGroup.block_Details.isDisplayed(), "Block details not found");
	 * // Wait.explicit_wait_xpath(OR.Enter_Block_Name, driver);
	 * Wait.explicit_wait_visibilityof_webelement_150(AdvGroup.Enter_Block_Name,
	 * driver); AdvGroup.Enter_Block_Name.sendKeys(BlockName);
	 * test_steps.add("Entered New Block Name : " + BlockName);
	 * advGroupsLogger.info("Entered New Block Name : " + BlockName);
	 * 
	 * // Wait.explicit_wait_xpath(OR.Click_Ok, driver); //
	 * Wait.explicit_wait_visibilityof_webelement_150(AdvGroup.Click_Ok, driver); //
	 * Utility.ScrollToElement(AdvGroup.Click_Ok, driver);
	 * Wait.explicit_wait_elementToBeClickable(AdvGroup.Click_Ok, driver);
	 * AdvGroup.Click_Ok.click(); test_steps.add("OK Button Clicked");
	 * advGroupsLogger.info("OK Button Clicked");
	 * Wait.waitUntilPageIsLoaded(driver);
	 * assertTrue(AdvGroup.Rate_Quote_Tittle.isDisplayed(),
	 * "RateQuote tittle not dispalyed");
	 * 
	 * }
	 * 
	 * >>>>>>>develop
	 */

public ArrayList<String> verifyNewBlockDetails(WebDriver driver,String NumberofNights, ArrayList<String> BlockDetails){
		
		ArrayList<String> blockDetails = new ArrayList<>();
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		BlockDetails.add("verify blockDetails details");
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Arrive_Date, driver);
		String Block_Arrival_Date= AdvGroup.Arrive_Date.getAttribute("value");
		advGroupsLogger.info("Block Arrival Date : " + Block_Arrival_Date);
		BlockDetails.add("Block Arrival Date: " + Block_Arrival_Date);
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Arrive_Date, driver);
		String Block_Depart_Date= AdvGroup.Departure_Date.getAttribute("value");
		advGroupsLogger.info("Block Depart Date : " + Block_Depart_Date);
		BlockDetails.add("Block Depart Date : " + Block_Depart_Date);
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rate_Quote_Room_Ninghts, driver);
		String NoofRoomNinghtsInRate=AdvGroup.Rate_Quote_Room_Ninghts.getText().replace(" room nights", "");
		BlockDetails.add("No of Room Ninghts In Rate  :"+NoofRoomNinghtsInRate);
		advGroupsLogger.info("No of Room Ninghts In Rate  :"+NoofRoomNinghtsInRate);
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Summary_Room_Ninght, driver);
		String NoofRoomNinghtsInsummary=AdvGroup.Rate_Quote_Room_Ninghts.getText().replace(" room nights", "");
		BlockDetails.add("No of Room Ninghts In Summary  :"+NoofRoomNinghtsInsummary);
		advGroupsLogger.info("No of Room Ninghts In summary  :"+NoofRoomNinghtsInsummary);
		int rateRoomNinght=Integer.parseInt(NoofRoomNinghtsInRate);
		int summaryRoomNinght=Integer.parseInt(NoofRoomNinghtsInsummary);
		assertEquals(NoofRoomNinghtsInRate, NoofRoomNinghtsInsummary);
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Summary_Room_Blocks, driver);
		String summaryRoomBlocks=AdvGroup.Summary_Room_Blocks.getText();
		BlockDetails.add("summary RoomB locks"+summaryRoomBlocks);
		advGroupsLogger.info("summary RoomB locks  :"+summaryRoomBlocks);
		
		assertEquals(summaryRoomBlocks,NoofRoomNinghtsInRate);
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Total_Amount, driver);
		String BlockTotal_Amount= AdvGroup.Total_Amount.getText();
		advGroupsLogger.info("total Amount : " + BlockTotal_Amount);
		BlockDetails.add("total Amount : " + BlockTotal_Amount);
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Room_QRg_Amount, driver);
		String BlockQRG_Amount= AdvGroup.Room_QRg_Amount.getText();
		advGroupsLogger.info("total Amount : " + BlockQRG_Amount);
		BlockDetails.add("total Amount : " + BlockQRG_Amount);
		
		Double BlockQrgAmt =Double.parseDouble(BlockQRG_Amount);
		Double No_Of_Blocks=Double.parseDouble(summaryRoomBlocks);
		Double act_amount =BlockQrgAmt*No_Of_Blocks;
		advGroupsLogger.info("based on Blocked Room rate : " + act_amount);
		BlockDetails.add("based on Blocked Room rate : " + act_amount);
		Double total_amount = Double.parseDouble(BlockTotal_Amount);
		assertEquals(act_amount , total_amount,"Total is not matching");

	
		return BlockDetails;

	}


public ArrayList<String> verify_RoomBlock_Details(WebDriver driver, String AccountName, String BlockName,String NumberofNights , String EnterBlockedcount) throws InterruptedException {
	ArrayList<String> test_steps = new ArrayList<>();

	Elements_Groups elements_Groups = new Elements_Groups(driver);
	test_steps.add("verify Room Block details");
	advGroupsLogger.info("verify Room Block details");

	/*Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.RoomBlock_GroupName, driver);
	String accountName =elements_Groups.RoomBlock_GroupName.getText();
	test_steps.add("Account Name : " + accountName);
	advGroupsLogger.info("Account Name : " + accountName);
	assertTrue(elements_Groups.RoomBlock_GroupName.isDisplayed() , "GroupAccountName displayed");*/
	
	

	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.RoomBlock_Revenue, driver);

	String Actual_Revenue =elements_Groups.RoomBlock_Revenue.getText().replace("$", "").replace(".00", "");
	advGroupsLogger.info("Total revenue : " + Actual_Revenue);
	test_steps.add("Total revenue : " + Actual_Revenue);
	
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Expected_Revenue, driver);
	String Expected_RoomBlock_Revenue =elements_Groups.Expected_Revenue.getText().replace("$", "").replace(".00", "");
	advGroupsLogger.info("Expected_RoomBlock_Revenue : " + Expected_RoomBlock_Revenue);
	test_steps.add("Expected_RoomBlock_Revenue : " + Expected_RoomBlock_Revenue);
	float act_revenue=Float.parseFloat(Actual_Revenue);
	float exp_Revenue=Float.parseFloat(Expected_RoomBlock_Revenue);
	
	assertEquals(Actual_Revenue , Expected_RoomBlock_Revenue);
	
	
	//test_steps.add("Total revenue : " + AccountNumber);
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Block_name, driver);
	String Expected_BlockName =elements_Groups.Block_name.getText();
	advGroupsLogger.info("Expected_BlockName : " + Expected_BlockName);
	test_steps.add("Expected_BlockName : " + Expected_BlockName);
	assertEquals(Expected_BlockName , BlockName);
	

	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Blocked_Rooms_count, driver);
	String Blocked_Room_Counts =elements_Groups.Blocked_Rooms_count.getText();
	advGroupsLogger.info("Blocked Rooms : " + Blocked_Room_Counts.replace("Rooms Blocked: ", ""));
	test_steps.add("Total Blocked Rooms : " + Blocked_Room_Counts);

	
	//int rbc =Integer.valueOf(RBCount);
	//assertTrue(elements_Groups.Blocked_Rooms_count.isDisplayed(),"No.of room blocks cocunt");
	assertEquals(Blocked_Room_Counts.replace("Rooms Blocked: ", ""),EnterBlockedcount);
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Total_Room_Ninghts, driver);
	String No_of_ninght =elements_Groups.Total_Room_Ninghts.getText();
	advGroupsLogger.info("Blocked No_of_ninght  : " + No_of_ninght);
	test_steps.add("Blocked No_of_ninght: " + No_of_ninght);

	
	assertTrue(elements_Groups.Total_Room_Ninghts.isDisplayed(),"No.of room ninght cocunt");
	assertEquals(No_of_ninght,EnterBlockedcount);
	
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Expected_Revenue, driver);
	String Block_Total_Revenue= elements_Groups.Expected_Revenue.getText().replace("$", "").trim().replace(".00", "").trim();
	advGroupsLogger.info("Expected_Revenue  : " + Block_Total_Revenue);
	test_steps.add("Expected_Revenue : " + Block_Total_Revenue);
	assertEquals(Block_Total_Revenue,Actual_Revenue);
	
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Expected_Revenue, driver);
	String Account_Details_QRG= elements_Groups.Account_Detail_QRG.getText();
	advGroupsLogger.info("Account_Details_QRG  : " + Account_Details_QRG);
	test_steps.add("Account_Details_QRG : " + Account_Details_QRG);
	
	Double actQRGTotal =Double.parseDouble(Account_Details_QRG.replace("$", ""));
	Double Blocks_Count=Double.parseDouble(Blocked_Room_Counts.replace("Rooms Blocked:", ""));
	Double act_amount =actQRGTotal*Blocks_Count;
	advGroupsLogger.info("based on Blocked Room rate : " + act_amount);
	test_steps.add("\"based on Blocked Room rate : " + act_amount);
	Double Account_Detail_Expected_Revenue = Double.parseDouble(Actual_Revenue.replace("$", ""));
	if(Account_Detail_Expected_Revenue>=act_amount) {
		advGroupsLogger.info("Expected revenue greter then actuval amount");
		test_steps.add("Expected revenue greter then actuval amount");
		
	}
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Account_PickedUp_Revenue, driver);
	String Account_PickedUp_Revenue= elements_Groups.Account_PickedUp_Revenue.getText();
	advGroupsLogger.info("PickedUp Revenue : " + Account_PickedUp_Revenue);
	test_steps.add("PickedUp Revenue : " + Account_PickedUp_Revenue);
	
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Account_PickedUp_Percentage, driver);
	String Account_PickedUp_Percentage= elements_Groups.Account_PickedUp_Percentage.getText();
	advGroupsLogger.info("PickedUp_Percentage  : " + Account_PickedUp_Percentage);
	test_steps.add("PickedUp_Percentage : " + Account_PickedUp_Percentage);
	
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Room_Block_PickedUp_Revenue, driver);
	String Room_Block_PickedUp_Revenue= elements_Groups.Room_Block_PickedUp_Revenue.getText();
	advGroupsLogger.info("Room_Block_PickedUp_Revenue  : " + Room_Block_PickedUp_Revenue);
	test_steps.add("Room_Block_PickedUp_Revenue : " + Room_Block_PickedUp_Revenue);
	assertEquals(Room_Block_PickedUp_Revenue,Account_PickedUp_Revenue);
	//Picked up %:
	
	Wait.explicit_wait_visibilityof_webelement_120(elements_Groups.Room_Block_PickedUp_percentage, driver);
	String Room_Block_PickedUp_percentage= elements_Groups.Room_Block_PickedUp_percentage.getText();
	advGroupsLogger.info("Blocked for ninght  : " + Room_Block_PickedUp_percentage);
	test_steps.add("Blocked for ninght : " + Room_Block_PickedUp_percentage);
	assertEquals(Room_Block_PickedUp_percentage.replace("Picked up %: ", ""),Account_PickedUp_Percentage);
	
	

	return test_steps;
}

public ArrayList<String> verify_RoomingList_details(WebDriver driver, String BlockName){
	Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	ArrayList<String> test_steps =new ArrayList<>();
    driver.switchTo().frame("dialog-body0");
	
	Wait.waitUntilPresenceOfElementLocated(OR.Rooming_List_Popup, driver);

	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_List_tittle,driver);
	String tittle= AdvGroup.Rooming_List_tittle.getText();
	advGroupsLogger.info("tittle: " + tittle);
	test_steps.add("Reservation folio ACC: : " + tittle);
	assertTrue(AdvGroup.Rooming_List_tittle.isDisplayed(),"Rooming List - Pick Up Rooms");
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Block_Name,driver);
	String Rooming_list_Block_Name= AdvGroup.Rooming_list_Block_Name.getText();
	advGroupsLogger.info("Rooming_list_Block_Name: " + Rooming_list_Block_Name);
	test_steps.add("Rooming_list_Block_Name: : " + Rooming_list_Block_Name);
	assertEquals(Rooming_list_Block_Name,BlockName);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Arrive_Date,driver);
	String Rooming_list_Arrive_Date= AdvGroup.Rooming_list_Arrive_Date.getText();
	advGroupsLogger.info("Rooming_list_Arrive_Date: " + Rooming_list_Arrive_Date);
	test_steps.add("Rooming_list_Arrive_Date: : " + Rooming_list_Arrive_Date);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Depart_Date,driver);
	String Rooming_list_Depart_Date= AdvGroup.Rooming_list_Depart_Date.getText();
	advGroupsLogger.info("Rooming_list_Depart_Date: " + Rooming_list_Depart_Date);
	test_steps.add("Rooming_list_Depart_Date: : " + Rooming_list_Depart_Date);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Statu,driver);
	String Rooming_list_Statu= AdvGroup.Rooming_list_Statu.getText();
	advGroupsLogger.info("Rooming_list_Statu: " + Rooming_list_Statu);
	test_steps.add("Rooming_list_Statu: : " + Rooming_list_Statu);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_QRG_value,driver);
	String Rooming_list_QRG_value= AdvGroup.Rooming_list_QRG_value.getText();
	advGroupsLogger.info("Rooming_list_QRG_value: " + Rooming_list_QRG_value);
	test_steps.add("Rooming_list_QRG_value: : " + Rooming_list_QRG_value);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Expected_Revenue,driver);
	String Rooming_list_Expected_Revenue= AdvGroup.Rooming_list_Expected_Revenue.getText();
	advGroupsLogger.info("Rooming_list_Expected_Revenue: " + Rooming_list_Expected_Revenue);
	test_steps.add("Rooming_list_Expected_Revenue: : " + Rooming_list_Expected_Revenue);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_PickedUp_Revenue,driver);
	String Rooming_list_PickedUp_Revenue= AdvGroup.Rooming_list_PickedUp_Revenue.getText();
	advGroupsLogger.info("Rooming_list_PickedUp_Revenue: " + Rooming_list_PickedUp_Revenue);
	test_steps.add("Rooming_list_PickedUp_Revenue: : " + Rooming_list_PickedUp_Revenue);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_PickedUp_percentage,driver);
	String Rooming_list_PickedUp_percentage= AdvGroup.Rooming_list_PickedUp_percentage.getText();
	advGroupsLogger.info("Rooming_list_PickedUp_percentage: " + Rooming_list_PickedUp_percentage);
	test_steps.add("Rooming_list_PickedUp_percentage: : " + Rooming_list_PickedUp_percentage);
	if(AdvGroup.Rooming_List_grid_chkHideDates.isSelected()) {
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_List_grid_Arrive_date,driver);
		String Rooming_List_grid_Arrive_date= AdvGroup.Rooming_List_grid_Arrive_date.getAttribute("Value");
		advGroupsLogger.info("Rooming_List_grid_Arrive_date: " + Rooming_List_grid_Arrive_date);
		test_steps.add("Rooming_List_grid_Arrive_date: : " + Rooming_List_grid_Arrive_date);
		assertEquals(Rooming_List_grid_Arrive_date,Rooming_list_Arrive_Date);
		
		
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_List_grid_deprt_date,driver);
		String Rooming_List_grid_deprt_date= AdvGroup.Rooming_List_grid_deprt_date.getAttribute("value");
		advGroupsLogger.info("Rooming_List_grid_deprt_date: " + Rooming_List_grid_deprt_date);
		test_steps.add("Rooming_List_grid_deprt_date: : " + Rooming_List_grid_deprt_date);

		
		assertEquals(Rooming_List_grid_deprt_date.trim(),Rooming_list_Depart_Date.trim(),"Block names  are not matching");

		
	}else {
		advGroupsLogger.info("Arrive and departure dates are not avilable on grid ");
		test_steps.add("Arrive and departure dates are not avilable on grid");
		
	}


	return test_steps;

	}



public ArrayList<String> roomingListPopup_RoomPickup(WebDriver driver,String FirstName, String LastName, String RoomClass, String Amount,String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
		String paymentMethod, String cardNo, String expiryDate) throws InterruptedException{

<<<<<<< HEAD
public ArrayList<String> roomingListPopup_RoomPickup(WebDriver driver,String FirstName, String LastName, String RoomClass, String Amount,String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
		String paymentMethod, String cardNo, String expiryDate) throws InterruptedException{
=======
>>>>>>> develop
	ArrayList<String> test_steps = new ArrayList<>();
	Elements_OldGroups group = new Elements_OldGroups(driver);
	Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	
	
	group.oldGroups_Enter_FirstName_Roominglistpopup.sendKeys(FirstName);
	test_steps.add("Entered First Name in Rooming List Popup : " + FirstName);
	advGroupsLogger.info("Entered First Name in Rooming List Popup : " + FirstName);
	
	
	group.oldGroups_Enter_LastName_Roominglistpoup.sendKeys(LastName);
	test_steps.add("Entered Last Name in Rooming List Popup : " + LastName);
	advGroupsLogger.info("Entered Last Name in Rooming List Popup : " + LastName);
	
	
	new Select(group.oldGroups_Select_Roomclass_Roominglistpoup).selectByVisibleText(RoomClass);
	test_steps.add("Selected RoomClass in Rooming List Popup : " + RoomClass);
	advGroupsLogger.info("Selected RoomClass in Rooming List Popup : " + RoomClass);
	
	String roomnum = OR.oldGroups_Select_RoomNo_Roominglistpopup + "/option";
	System.out.println(roomnum);
	int count = driver.findElements(By.xpath(roomnum)).size();
	Random random = new Random();
	int randomNumber = random.nextInt(count - 1) + 1;
	System.out.println("count : " + count);
	System.out.println("randomNumber : " + randomNumber);
	new Select(group.oldGroups_Select_RoomNo_Roominglistpopup)
			.selectByIndex(randomNumber);
	
	test_steps.add("Selected RoomNo in Rooming List Popup : " + randomNumber);
	advGroupsLogger.info("Selected RoomNo in Rooming List Popup : " + randomNumber);
	

	Utility.ScrollToElement(group.oldGroups_Amount_Roominglistpopup, driver);
	Wait.explicit_wait_elementToBeClickable(group.oldGroups_Amount_Roominglistpopup, driver);
	group.oldGroups_Amount_Roominglistpopup.click();
	group.oldGroups_Amount_Roominglistpopup.clear();
	group.oldGroups_Amount_Roominglistpopup.sendKeys(Amount);
	test_steps.add("Entered Amount in Rooming List Popup : " + Amount);
	advGroupsLogger.info("Entered Amount in Rooming List Popup : " + Amount);

	Utility.ScrollToElement(group.oldGroups_ClickBillingInfo_Roominglistpopup, driver);
	Wait.explicit_wait_elementToBeClickable(group.oldGroups_ClickBillingInfo_Roominglistpopup, driver);
	group.oldGroups_ClickBillingInfo_Roominglistpopup.click();
	test_steps.add("Billing Info Button Clicked");
	advGroupsLogger.info("Billing Info Button Clicked");
	
	driver.switchTo().defaultContent();
	driver.switchTo().frame("dialog-body1");
	Wait.waitUntilPresenceOfElementLocated("//*[@id='frmRoomingListBillingInfo']", driver);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_FristName,driver);
	String Rooming_list_Billing_FristName= AdvGroup.Rooming_list_Billing_FristName.getAttribute("value");
	advGroupsLogger.info("Rooming_list_Billing_FristName: " + Rooming_list_Billing_FristName);
	test_steps.add("Rooming_list_Billing_FristName : " + Rooming_list_Billing_FristName);
	assertEquals(Rooming_list_Billing_FristName,FirstName);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_lastName,driver);
	String Rooming_list_Billing_lastName= AdvGroup.Rooming_list_Billing_lastName.getAttribute("value");
	advGroupsLogger.info("Rooming_list_Billing_lastName: " + Rooming_list_Billing_lastName);
	test_steps.add("Rooming_list_Billing_lastName : " + Rooming_list_Billing_lastName);
	assertEquals(Rooming_list_Billing_lastName,LastName);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_Phone_Number,driver);
	String Rooming_list_Billing_Phone_Number= AdvGroup.Rooming_list_Billing_Phone_Number.getAttribute("value");
	advGroupsLogger.info("Rooming_list_Billing_Phone_Number: " + Rooming_list_Billing_Phone_Number);
	test_steps.add("Rooming_list_Billing_Phone_Number : " + Rooming_list_Billing_Phone_Number);
	assertEquals(Rooming_list_Billing_Phone_Number.replace("(", "").replace(")", "").replace(" ", "").trim().replace("-", "").trim(),Phonenumber);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_Address,driver);
	String Rooming_list_Billing_Address= AdvGroup.Rooming_list_Billing_Address.getAttribute("value");
	advGroupsLogger.info("Rooming_list_Billing_Address: " + Rooming_list_Billing_Address);
	test_steps.add("Rooming_list_Billing_Address : " + Rooming_list_Billing_Address);
	assertEquals(Rooming_list_Billing_Address,Address1);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_city,driver);
	String Rooming_list_Billing_city= AdvGroup.Rooming_list_Billing_city.getAttribute("value");
	advGroupsLogger.info("Rooming_list_Billing_city: " + Rooming_list_Billing_city);
	test_steps.add("Reservation folio ACC: : " + Rooming_list_Billing_city);
	assertEquals(Rooming_list_Billing_city,city);
	
	Select select_State = new Select(driver.findElement(By.xpath("//select[@id='drpBilling_territoryID']")));
	WebElement option_State = select_State.getFirstSelectedOption();
	String Rooming_list_Billing_state = option_State.getText();
	
	//Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_state,driver);
	//String Rooming_list_Billing_state= AdvGroup.Rooming_list_Billing_state.getAttribute("value");
	advGroupsLogger.info("Rooming_list_Billing_state: " + Rooming_list_Billing_state);
	test_steps.add("Rooming_list_Billing_state : " + Rooming_list_Billing_state);
	//assertEquals(Rooming_list_Billing_state,State);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_postal_code,driver);
	String Rooming_list_Billing_postal_code= AdvGroup.Rooming_list_Billing_postal_code.getAttribute("value");
	advGroupsLogger.info(" Rooming_list_Billing_postal_code: " + Rooming_list_Billing_postal_code);
	test_steps.add(" Rooming_list_Billing_postal_code : " + Rooming_list_Billing_postal_code);
	assertEquals(Rooming_list_Billing_postal_code,Postalcode);
	
	
	Select select = new Select(driver.findElement(By.xpath("//select[@id='drpBilling_countryID']")));
	WebElement option = select.getFirstSelectedOption();
	String Rooming_list_Billing_country = option.getText();
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rooming_list_Billing_country,driver);
	//String Rooming_list_Billing_country= AdvGroup.Rooming_list_Billing_country.getAttribute("value");
	advGroupsLogger.info("Rooming_list_Billing_country: " + Rooming_list_Billing_country);
	test_steps.add("Rooming_list_Billing_country: : " + Rooming_list_Billing_country);

	assertEquals(Rooming_list_Billing_country,Country);

	Elements_AdvanceGroups group1= new Elements_AdvanceGroups(driver);
	
	
	Wait.explicit_wait_visibilityof_webelement_120(group1.BillingInfo_PaymentMethod, driver);
<<<<<<< HEAD
	/*new Select(group1.BillingInfo_PaymentMethod).selectByIndex(1);*/
=======

	new Select(group1.BillingInfo_PaymentMethod).selectByIndex(1);

/*	new Select(group1.BillingInfo_PaymentMethod).selectByIndex(1);*/
>>>>>>> develop
	new Select(group1.BillingInfo_PaymentMethod).selectByVisibleText(paymentMethod);
	test_steps.add("Selected Payment Method : " + paymentMethod);
	advGroupsLogger.info("Selected Payment Method : " + paymentMethod);
	if(paymentMethod.equalsIgnoreCase("MC")) {		
		group1.BillingInfo_AccountNo.sendKeys(cardNo);
		test_steps.add("Input Account : " + cardNo);
		advGroupsLogger.info("Input Account: " + cardNo);
		group1.BillingInfo_ExpiryDate.sendKeys(expiryDate);
		test_steps.add("Input Exp Date : " + cardNo);
		advGroupsLogger.info("Input Exp Date : " + cardNo);
	}
<<<<<<< HEAD
=======

	test_steps.add("Selected Payment Method : Account");
	advGroupsLogger.info("Selected Payment Method : Account");
>>>>>>> develop
	
	//Wait.explicit_wait_visibilityof_webelement_120(group1.BillingInfo_Save, driver);
	Utility.ScrollToElement(group1.BillingInfo_Save, driver);
	Wait.explicit_wait_elementToBeClickable(group1.BillingInfo_Save, driver);
	group1.BillingInfo_Save.click();
	test_steps.add("Billing Info Save Button Clicked");
	advGroupsLogger.info("Billing Info Save Button Clicked");
	
	driver.switchTo().defaultContent();
	return test_steps;
}

public ArrayList<String> verify_RoomingList_PickUp_Summary(WebDriver driver, String BlockName) throws Exception{
	Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	ArrayList<String> test_steps =new ArrayList<>();
	driver.switchTo().frame("dialog-body1");	
    
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Block_Name,driver);
	String PickUp_Summary_Block_Name= AdvGroup.PickUp_Summary_Block_Name.getText();
	advGroupsLogger.info("PickUp_Summary_Block_Name: " + PickUp_Summary_Block_Name);
	test_steps.add("PickUp_Summary_Block_Name: : " + PickUp_Summary_Block_Name);
	assertEquals(PickUp_Summary_Block_Name,BlockName);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Arrive_Date,driver);
	String PickUp_Summary_Arrive_Date= AdvGroup.PickUp_Summary_Arrive_Date.getText();
	advGroupsLogger.info("PickUp_Summary_Arrive_Date: " + PickUp_Summary_Arrive_Date);
	test_steps.add("PickUp_Summary_Arrive_Date: : " + PickUp_Summary_Arrive_Date);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Depart_Date,driver);
	String PickUp_Summary_Depart_Date= AdvGroup.PickUp_Summary_Depart_Date.getText();
	advGroupsLogger.info("PickUp_Summary_Depart_Date: " + PickUp_Summary_Depart_Date);
	test_steps.add("PickUp_Summary_Depart_Date: : " + PickUp_Summary_Depart_Date);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_QGR_Value,driver);
	String PickUp_Summary_QGR_Value= AdvGroup.PickUp_Summary_QGR_Value.getText();
	advGroupsLogger.info("PickUp_Summary_QGR_Value: " + PickUp_Summary_QGR_Value);
	test_steps.add("PickUp_Summary_QGR_Value: : " + PickUp_Summary_QGR_Value);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Status,driver);
	String PickUp_Summary_Status= AdvGroup.PickUp_Summary_Status.getText();
	advGroupsLogger.info("PickUp_Summary_Status: " + PickUp_Summary_Status);
	test_steps.add("PickUp_Summary_Status: : " + PickUp_Summary_Status);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Expected_Revenue,driver);
	String PickUp_Summary_Expected_Revenue= AdvGroup.PickUp_Summary_Expected_Revenue.getText();
	advGroupsLogger.info("PickUp_Summary_Expected_Revenue: " + PickUp_Summary_Expected_Revenue);
	test_steps.add("PickUp_Summary_Expected_Revenue: : " + PickUp_Summary_Expected_Revenue);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_PickedUp_Revenue,driver);
	String PickUp_Summary_PickedUp_Revenue= AdvGroup.PickUp_Summary_PickedUp_Revenue.getText();
	advGroupsLogger.info("PickUp_Summary_PickedUp_Revenue: " + PickUp_Summary_PickedUp_Revenue);
	test_steps.add("Reservation_balanct: : " + PickUp_Summary_PickedUp_Revenue);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_PickedUp_percentage,driver);
	String PickUp_Summary_PickedUp_percentage= AdvGroup.PickUp_Summary_PickedUp_percentage.getText();
	advGroupsLogger.info("PickUp_Summary_PickedUp_percentage: " + PickUp_Summary_PickedUp_percentage);
	test_steps.add("PickUp_Summary_PickedUp_percentage: : " + PickUp_Summary_PickedUp_percentage);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Reservation_Number,driver);
	String PickUp_Summary_Reservation_Number= AdvGroup.PickUp_Summary_Reservation_Number.getText();
	advGroupsLogger.info("PickUp_Summary_Reservation_Number: " + PickUp_Summary_Reservation_Number);
	test_steps.add("PickUp_Summary_Reservation_Number: : " + PickUp_Summary_Reservation_Number);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Grid_Arrive_date,driver);
	String PickUp_Summary_Grid_Arrive_date= AdvGroup.PickUp_Summary_Grid_Arrive_date.getText();
	advGroupsLogger.info("PickUp_Summary_Grid_Arrive_date: " + PickUp_Summary_Grid_Arrive_date);
	test_steps.add("PickUp_Summary_Grid_Arrive_date: : " + PickUp_Summary_Grid_Arrive_date);
	assertEquals(PickUp_Summary_Grid_Arrive_date,PickUp_Summary_Arrive_Date);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.PickUp_Summary_Grid_Depart_date,driver);
	String PickUp_Summary_Grid_Depart_date= AdvGroup.PickUp_Summary_Grid_Depart_date.getText();
	advGroupsLogger.info("PickUp_Summary_Grid_Depart_date: " + PickUp_Summary_Grid_Depart_date);
	test_steps.add("PickUp_Summary_Grid_Depart_date: : " + PickUp_Summary_Grid_Depart_date);
	assertEquals(PickUp_Summary_Depart_Date,PickUp_Summary_Grid_Depart_date);
	
	Double Exp_Revenue =Double.parseDouble(PickUp_Summary_Expected_Revenue.replace("$", "").trim());
	Double pick_revenue =Double.parseDouble(PickUp_Summary_PickedUp_Revenue.replace("$", "").trim());
	
	
	if(Exp_Revenue>=pick_revenue) {
		advGroupsLogger.info("PickUp Summary Expected Revenue greterthan to total balance");
		test_steps.add("PickUp Summary Expected Revenue greterthan to total balance");
		
	}else {
		advGroupsLogger.info("PickUp Summary Expected Revenue lessthan to total balance");
		test_steps.add("PickUp Summary Expected Revenue lessthan to total balance");
		
	}
	
	Utility.ScrollToElement(driver.findElement(By.xpath(OR.oldGroup_Click_Close_Roominglistsummary)), driver);
	Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(OR.oldGroup_Click_Close_Roominglistsummary)), driver);
	driver.findElement(By.xpath(OR.oldGroup_Click_Close_Roominglistsummary)).click();
	test_steps.add("Rooming List Summary Close Button Clicked");
	advGroupsLogger.info("Rooming List Summary Close Button Clicked");
	driver.switchTo().defaultContent();
	//Wait.wait3Second();
	
	
	
	driver.switchTo().defaultContent();
	Wait.waitUntilPageIsLoaded(driver);
	Wait.wait2Second();


	
	return test_steps;
	
	}

	public ArrayList<String> verifyCreatedReservation(WebDriver driver, String resNumber, String AccountName,
			String FirstName, String LastName) {
		ArrayList<String> test_steps = new ArrayList<>();

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Groups_ReservationsTab, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", AdvGroup.Groups_ReservationsTab);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Groups_ReservationsTab, driver);
		AdvGroup.Groups_ReservationsTab.click();
		test_steps.add("Click on reservation tab");
		advGroupsLogger.info("Click on reservation tab");
		Wait.waitUntilPresenceOfElementLocated(OR.ReservationNumbers, driver);
		// Wait.waitForElementToBeInvisible(driver, AdvGroup.ReservationNumbers, 120);
		String resNumber1 = AdvGroup.ReservationNumbers.getText();
		System.out.println("Reservation Number : " + resNumber1);
		test_steps.add("Reservation Number : " + resNumber1);
		advGroupsLogger.info("Reservation Number : " + resNumber1);

		// AdvGroup.RoomingListSummary_Close.click();

		Wait.explicit_wait_visibilityof_webelement_350(AdvGroup.ReservationNumbers, driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Groups_ReservationPage, driver);
		jse.executeScript("arguments[0].scrollIntoView(true);", AdvGroup.ReservationNumbers);
		Wait.waitUntilPresenceOfElementLocated(OR.ReservationNumbers, driver);
		int size = driver.findElements(By.xpath(OR.ReservationNumbers)).size();
		System.out.println("Reservation list Size is " + size);
		test_steps.add("Reservation list Size is " + size);
		advGroupsLogger.info("\"Reservation list Size is " + size);
		boolean found = false;
		for (int i = 1; i <= size; i++) {
			String ResNumberPath = "(" + OR.ReservationNumbers + ")[" + i + "]";
			String resNUM = driver.findElement(By.xpath(ResNumberPath)).getText();
			if (resNUM.replaceAll("\\s", "").equals(resNumber.replaceAll("\\s", ""))) {
				found = true;
				String Guestname = "(" + OR.ReservationGuest + ")[" + i + "]";
				assertTrue(driver.findElement(By.xpath(Guestname)).getText().contains(FirstName + " " + LastName),
						"Failed: GuestName Missmatched");

				/*
				 * Wait.waitUntilPresenceOfElementLocated(OR. ReservationDetailPage);
				 * assertTrue(AdvGroup.ReservationDetailPage_GuestName.getText()
				 * .contains(GuestName),"Failed: Account name");
				 * assertTrue(AdvGroup.ReservationDetailPage_Account.getText().
				 * contains(AccountName),"Failed: Account name");
				 * AdvGroup.ReservationDetailPage_Close.click();
				 */break;
			}
		}

		return test_steps;
	}

public void enterFirst_LastName(WebDriver driver, String FirstName, String LastName) {


	Elements_Reservation elements_Reservation = new Elements_Reservation(driver);
	Wait.explicit_wait_visibilityof_webelement_350(elements_Reservation.Enter_First_Name, driver);
	elements_Reservation.Enter_First_Name.clear();
	elements_Reservation.Enter_Last_Name.clear();

	elements_Reservation.Enter_First_Name.sendKeys(FirstName);
	elements_Reservation.Enter_Last_Name.sendKeys(LastName);

	}



public String validatePromoCodeGenerated(WebDriver driver,String Room_Block_PromoCod,String AccountName,ArrayList<String> test_steps) throws InterruptedException {

	Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_ACCNO,driver);
	Wait.wait2Second();
	String Reservation_ACCNO= AdvGroup.Reservation_ACCNO.getText();
	advGroupsLogger.info("ReservationPage Promocode: " + Reservation_ACCNO);
	test_steps.add("PreviewFolio Exp Amount: : " + Reservation_ACCNO);
	assertEquals(Reservation_ACCNO,AccountName);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.ReservationPage_Promocode,driver);
	Wait.wait2Second();
	String ReservationPage_Promocode= AdvGroup.ReservationPage_Promocode.getText();
	advGroupsLogger.info("ReservationPage Promocode: " + ReservationPage_Promocode);
	test_steps.add("PreviewFolio Exp Amount: : " + ReservationPage_Promocode);

	return ReservationPage_Promocode;

}

public ArrayList<String> verifyReservation_folio_details(WebDriver driver, String AccountNo){
	Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	ArrayList<String> test_steps =new ArrayList<>();
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_folio_Tab,driver);
	AdvGroup.Reservation_folio_Tab.click();
	advGroupsLogger.info("click on folio tab");
	test_steps.add("click on folio tab");
	
	//Reservation_folio_ACC
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_folio_ACC,driver);
	String Reservation_folio_ACC= AdvGroup.Reservation_folio_ACC.getText();
	advGroupsLogger.info("Reservation folio ACC: " + Reservation_folio_ACC);
	test_steps.add("Reservation folio ACC: : " + Reservation_folio_ACC);
	assertEquals(Reservation_folio_ACC,AccountNo);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_start_date,driver);
	String Reservation_start_date= AdvGroup.Reservation_start_date.getText();
	advGroupsLogger.info("Reservation start date: " + Reservation_start_date);
	test_steps.add("Reservation start date: : " + Reservation_start_date);
	
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_end_date,driver);
	String Reservation_end_date= AdvGroup.Reservation_end_date.getText();
	advGroupsLogger.info("Reservation end date: " + Reservation_end_date);
	test_steps.add("Reservation end date: : " + Reservation_end_date);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_Folio_Balence,driver);
	String Reservation_Folio_Balence= AdvGroup.Reservation_Folio_Balence.getText();
	advGroupsLogger.info("Reservation Folio Balence: " + Reservation_Folio_Balence);
	test_steps.add("Reservation Folio Balence: : " + Reservation_Folio_Balence);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_Total,driver);
	String Reservation_Total= AdvGroup.Reservation_Total.getText();
	advGroupsLogger.info("Reservation Total: " + Reservation_Total);
	test_steps.add("Reservation Total: : " + Reservation_Total);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_LineItem_effectivedate,driver);
	String Reservation_LineItem_effectivedate= AdvGroup.Reservation_LineItem_effectivedate.getText();
	advGroupsLogger.info("Reservation_LineItem_effectivedate: " + Reservation_LineItem_effectivedate);
	test_steps.add("Reservation_LineItem_effectivedate: : " + Reservation_LineItem_effectivedate);
	assertEquals(Reservation_LineItem_effectivedate,Reservation_start_date);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_Room_Charges,driver);
	String Reservation_Room_Charges= AdvGroup.Reservation_Room_Charges.getText();
	advGroupsLogger.info("Reservation_Room_Charges: " + Reservation_Room_Charges);
	test_steps.add("Reservation_Room_Charges: : " + Reservation_Room_Charges);
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_incidentals,driver);
	String Reservation_incidentals= AdvGroup.Reservation_incidentals.getText();
	advGroupsLogger.info("Reservation_incidentals: " + Reservation_incidentals);
	test_steps.add("Reservation_incidentals: : " + Reservation_incidentals);
	
	
	Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_balanc,driver);
	String Reservation_balanc= AdvGroup.Reservation_balanc.getText();
	advGroupsLogger.info("Reservation_balanc: " + Reservation_balanc);
	test_steps.add("Reservation_balanct: : " + Reservation_balanc);
	assertEquals(Reservation_balanc,Reservation_Total);
	
	Double actTotal =Double.parseDouble(Reservation_Room_Charges.replace("$", ""));
	Double res_incidetal =Double.parseDouble(Reservation_incidentals.replace("$", ""));
	Double total_amount=actTotal+res_incidetal;
	Double res_bal =Double.parseDouble(Reservation_balanc.replace("$", ""));
	if(res_bal>=total_amount) {
		advGroupsLogger.info("reservation balence greterthan to total balance");
		test_steps.add("reservation balence greterthan to total balance");
		
	}else {
		advGroupsLogger.info("reservation balence lessthan to total balance");
		test_steps.add("reservation balence lessthan to total balance");
		
	}
	
	return test_steps;
	
	}

	public void EnterBlockName(WebDriver driver, String BlockName, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);

		// Wait.explicit_wait_xpath(OR.Click_New_Block_button, driver);
		Wait.explicit_wait_visibilityof_webelement_150(AdvGroup.Click_New_Block_button, driver);
		// Utility.ScrollToElement(AdvGroup.Click_New_Block_button, driver);
		Wait.wait1Second();
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Click_New_Block_button, driver);
		AdvGroup.Click_New_Block_button.click();
		test_steps.add("New Block Button Clicked");
		advGroupsLogger.info("New Block Button Clicked");
		assertTrue(AdvGroup.block_Details.isDisplayed(), "Block details not found");
		// Wait.explicit_wait_xpath(OR.Enter_Block_Name, driver);
		Wait.explicit_wait_visibilityof_webelement_150(AdvGroup.Enter_Block_Name, driver);
		AdvGroup.Enter_Block_Name.sendKeys(BlockName);
		test_steps.add("Entered New Block Name : " + BlockName);
		advGroupsLogger.info("Entered New Block Name : " + BlockName);

		// Wait.explicit_wait_xpath(OR.Click_Ok, driver);
		// Wait.explicit_wait_visibilityof_webelement_150(AdvGroup.Click_Ok, driver);
		// Utility.ScrollToElement(AdvGroup.Click_Ok, driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Click_Ok, driver);
		AdvGroup.Click_Ok.click();
		test_steps.add("OK Button Clicked");
		advGroupsLogger.info("OK Button Clicked");
		Wait.waitUntilPageIsLoaded(driver);
		assertTrue(AdvGroup.Rate_Quote_Tittle.isDisplayed(), "RateQuote tittle not dispalyed");

	}

	public void SearchGroupCriteriawithSchdulers(WebDriver driver, String NumberofNights, String PreScheduler,
			String PostScheduler, ArrayList<String> test_steps) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		// Wait.waitUntilPresenceOfElementLocated(OR.Rate_Quote_Tittle, driver);
		// Wait.explicit_wait_visibilityof_webelement_150(AdvGroup.Select_Arrival_Date_Groups,
		// driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Select_Arrival_Date_Groups, driver);
		AdvGroup.Select_Arrival_Date_Groups.click();
		test_steps.add("Select Arrival Date");
		advGroupsLogger.info("Select Arrival Date");

		test_steps.add("Arrival_Date");
		advGroupsLogger.info("Arrival_Date");
		test_steps.add("SelectArrival date");
		advGroupsLogger.info("SelectArrival date");
		// Wait.waitUntilPresenceOfElementLocated(OR.Click_Today_Arrival_Groups,
		// driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Click_Today_Arrival_Groups, driver);
		AdvGroup.Click_Today_Arrival_Groups.click();

		test_steps.add("Today Clicked as Arrival Date");
		advGroupsLogger.info("Today Clicked as Arrival Date");
		Wait.explicit_wait_visibilityof_webelement_200(AdvGroup.Enter_No_of_Nigts, driver);
		// Wait.explicit_wait_elementToBeClickable(AdvGroup.Enter_No_of_Nigts, driver);
		AdvGroup.Enter_No_of_Nigts.click();
		AdvGroup.Enter_No_of_Nigts.sendKeys(NumberofNights);
		String NoOfNinghts = AdvGroup.Enter_No_of_Nigts.getText();

		test_steps.add("Entered Room Per Night : " + NumberofNights);
		advGroupsLogger.info("Entered Room Per Night : " + NumberofNights);

		int pre = Integer.parseInt(PreScheduler);
		int post = Integer.parseInt(PostScheduler);

		for (int i = 0; i < pre; i++) {
			// Wait.WaitForElement(driver, OR.Group_PreScheduler);
			// Wait.waitUntilPresenceOfElementLocated(OR.Group_PreScheduler, driver);
			Wait.explicit_wait_elementToBeClickable(AdvGroup.Group_PreScheduler, driver);
			AdvGroup.Group_PreScheduler.click();
			Wait.wait1Second();

		}
		test_steps.add("Select prescheduler as : " + pre);
		advGroupsLogger.info("Select prescheduler as : " + pre);

		for (int i = 0; i < post; i++) {
			// Wait.WaitForElement(driver, OR.Group_PostScheduler);
			// Wait.waitUntilPresenceOfElementLocated(OR.Group_PostScheduler, driver);
			Wait.explicit_wait_elementToBeClickable(AdvGroup.Group_PostScheduler, driver);
			AdvGroup.Group_PostScheduler.click();
			Wait.wait1Second();
		}

		test_steps.add("Select postscheduler as : " + post);
		advGroupsLogger.info("Select postscheduler as : " + post);

		Wait.explicit_wait_elementToBeClickable(AdvGroup.Click_Search_Group, driver);
		AdvGroup.Click_Search_Group.click();
		test_steps.add("Search Group Button Clicked");
		advGroupsLogger.info("Search Group Button Clicked");
		Wait.waitUntilPageIsLoaded(driver);
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Rate_Quote_Room_Ninghts, driver);
		String NoofRoomNinghtsInRate = AdvGroup.Rate_Quote_Room_Ninghts.getText();
		test_steps.add("No of Room Ninghts In Rate" + NoofRoomNinghtsInRate);
		advGroupsLogger.info("No of Room Ninghts In Rate" + NoofRoomNinghtsInRate);

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Summary_Room_Ninght, driver);
		String NoofRoomNinghtsInsummary = AdvGroup.Rate_Quote_Room_Ninghts.getText();
		test_steps.add("No of Room Ninghts In Summary" + NoofRoomNinghtsInsummary);
		advGroupsLogger.info("\"No of Room Ninghts In summary" + NoofRoomNinghtsInsummary);
		assertEquals(NoofRoomNinghtsInRate, NoofRoomNinghtsInsummary);

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Summary_Room_Blocks, driver);
		String summaryRoomBlocks = AdvGroup.Summary_Room_Blocks.getText();
		test_steps.add("summary RoomB locks" + summaryRoomBlocks);
		advGroupsLogger.info("summary RoomB locks" + summaryRoomBlocks);
		assertEquals(summaryRoomBlocks, NumberofNights);

	}

	public void click_Select_Rooms(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		driver.navigate().refresh();
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.WaitForElement(driver, OR.Group_select_Rooms);
		Wait.waitUntilPresenceOfElementLocated(OR.Group_select_Rooms, driver);
		Utility.ScrollToElement(AdvGroup.Group_select_Rooms, driver);
		AdvGroup.Group_select_Rooms.click();
		test_steps.add("Click on select room button");
		advGroupsLogger.info("Click on select room button");
		Wait.waitUntilPageIsLoaded(driver);

	}

	public void select_Room_Number(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		// Wait.explicit_wait_xpath("//*[@id='MainContent_Iframe_AssignRooms']");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("MainContent_Iframe_AssignRooms");
		test_steps.add("Move to frame");
		advGroupsLogger.info("Move to frame");
		Wait.waitUntilPresenceOfElementLocated(OR.SelectRoom_context, driver);
		assertTrue(AdvGroup.SelectRoom_context.isDisplayed(), "Failed:Select Room not displayed");
		Wait.WaitForElement(driver, OR.Select_Rooms_Go);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Rooms_Go, driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Select_Rooms_Go, driver);
		Wait.wait2Second();
		AdvGroup.Select_Rooms_Go.click();

		test_steps.add("clik on go button");
		advGroupsLogger.info("clik on go button");
		Wait.WaitForElement(driver, OR.Select_Rooms_Number);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Rooms_Number, driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Select_Rooms_Number, driver);
		Wait.wait1Second();
		AdvGroup.Select_Rooms_Number.click();
		test_steps.add("click on room number");
		advGroupsLogger.info("click on room number");

	}

	public void select_Rooms_save(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);

		Wait.WaitForElement(driver, OR.Select_Rooms_Save_Button);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Rooms_Save_Button, driver);
		Utility.ScrollToElement(AdvGroup.Select_Rooms_Save_Button, driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Select_Rooms_Save_Button, driver);
		AdvGroup.Select_Rooms_Save_Button.click();
		test_steps.add(" click the save button ");
		advGroupsLogger.info("click the save button ");
		Wait.waitUntilPageIsLoaded(driver);
	}

	public void Select_Rooms_done(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		Wait.WaitForElement(driver, OR.Select_Rooms_done_Button);
		Wait.WaitForElement(driver, OR.Select_Rooms_done_Button);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Rooms_done_Button, driver);
		Utility.ScrollToElement(AdvGroup.Select_Rooms_done_Button, driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Select_Rooms_done_Button, driver);
		AdvGroup.Select_Rooms_done_Button.click();
		test_steps.add(" click the done button ");
		advGroupsLogger.info("click the done button ");
		Wait.wait1Second();
		Wait.waitUntilPageIsLoaded(driver);

	}

	public void click_EditBlock(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", AdvGroup.RoomDetailsPage_EditButton);
		// driver.manage().window().maximize();
		driver.switchTo().frame((driver.findElement(By.id("MainContent_Iframe_accountpicker"))));
		Wait.wait5Second();
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.RoomDetailsPage_BlockDetails, driver);

		assertTrue(AdvGroup.RoomDetailsPage_BlockDetails.isDisplayed(), "Failed:Block Details not displayed");
		assertTrue(AdvGroup.BlockDetailsPage_RoomBlockAttiribute.isDisplayed(),
				"Failed:Room Block Attributes not displayed");
		test_steps.add("Click on edit block");
		advGroupsLogger.info("Click on edit block");
		Wait.waitUntilPageIsLoaded(driver);

	}

	public void UpdateSearchGroupCriteriawithSchdulers(WebDriver driver, String NumberofNights, String PreScheduler,
			String PostScheduler, String UpdatedPreScheduler, String UpdatedPostScheduler, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.RoomDetailsPage_BlockDetails, driver);
		Wait.waitUntilPageIsLoaded(driver);
		Wait.wait2Second();

		int pre = Integer.parseInt(UpdatedPreScheduler);
		int post = Integer.parseInt(UpdatedPostScheduler);

		int preslder = Integer.parseInt(PreScheduler);
		int postslder = Integer.parseInt(PostScheduler);

		for (int i = 0; i < preslder; i++) {

			AdvGroup.Group_PreSchedulerMinus.click();
			Wait.wait1Second();
		}

		advGroupsLogger.info("click on preSchedulers");
		test_steps.add("click on preSchedulers");

		for (int i = 0; i < postslder; i++) {
			// Wait.WaitForElement(driver, OR.Group_PostSchedulerMinus);
			AdvGroup.Group_PostSchedulerMinus.click();
			Wait.wait1Second();
		}
		advGroupsLogger.info("click on postSchedulers");
		test_steps.add("click on postSchedulers");

		for (int i = 0; i < pre; i++) {
			// Wait.WaitForElement(driver, OR.Group_PreScheduler);
			AdvGroup.Group_PreScheduler.click();
			Wait.wait1Second();
		}

		advGroupsLogger.info("Select prescheduler as : " + pre);
		test_steps.add("Select prescheduler as : " + pre);
		for (int i = 0; i < post; i++) {
			// Wait.WaitForElement(driver, OR.Group_PostScheduler);
			Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Group_PostScheduler, driver);
			AdvGroup.Group_PostScheduler.click();
			Wait.wait1Second();
		}

		advGroupsLogger.info("Select postscheduler as : " + post);
		test_steps.add("Select postscheduler as: " + post);
		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Click_Search_Group, driver);
		AdvGroup.Click_Search_Group.click();
		Wait.waitUntilPageIsLoaded(driver);
		test_steps.add("Click on Search");
		advGroupsLogger.info("Click on Search");
		// Wait.explicit_wait_xpath(OR.Verify_Room_Class_Grid_Groups);

	}

	public ArrayList<String> verify_EditBlock_Details(WebDriver driver, String BlockName) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<>();
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		test_steps.add("verify Edit Block details");
		advGroupsLogger.info("verify Edit Block details");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_details, driver);
		assertTrue(AdvGroup.Edit_block_details.isDisplayed(), "edit block details find");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Name, driver);
		String Block_Name = AdvGroup.Edit_block_Name.getText();
		advGroupsLogger.info("Edit Block Name : " + Block_Name);
		test_steps.add("Edit Block Name : " + Block_Name);
		assertEquals(AdvGroup.Edit_block_Name.getText(), BlockName, "BlackName is  matching");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Arrive_date, driver);
		String Arrival_Date = AdvGroup.Edit_block_Arrive_date.getText();
		advGroupsLogger.info("Edit Block Arrival Date : " + Arrival_Date);
		test_steps.add("Edit Block Arrival Date : " + Arrival_Date);
		assertTrue(AdvGroup.Edit_block_Arrive_date.isDisplayed(), "Arrival date not found");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Room_Block_ArriveDate, driver);
		String Room_Block_ArriveDate = AdvGroup.Room_Block_ArriveDate.getAttribute("value");
		advGroupsLogger.info("Room_Block Arrival Date : " + Room_Block_ArriveDate);
		test_steps.add("Room_Block Arrival Date : " + Room_Block_ArriveDate);
		assertEquals(Arrival_Date, Room_Block_ArriveDate, "edit block arrival dates are not matching :");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Depart, driver);
		String Depart_Date = AdvGroup.Edit_block_Depart.getText();
		advGroupsLogger.info("Edit Block Depart Date : " + Depart_Date);
		test_steps.add("Edit Block Depart Date : " + Depart_Date);
		assertTrue(AdvGroup.Edit_block_Depart.isDisplayed(), "Depart date not found");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Room_Block_Depart, driver);
		String Room_Block_Depart = AdvGroup.Edit_block_Depart.getText();
		advGroupsLogger.info("Room_Block_Depar : " + Room_Block_Depart);
		test_steps.add("Room_Block_Depar : " + Room_Block_Depart);
		assertTrue(AdvGroup.Edit_block_Depart.isDisplayed(), "Depart date not found");
		assertEquals(Depart_Date, Room_Block_Depart, "edit block depart dates are not m,atching :");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_QGR_Value, driver);
		String QRG_Value = AdvGroup.Edit_block_QGR_Value.getText();
		advGroupsLogger.info("Edit Block QGR Value : " + QRG_Value);
		test_steps.add("Edit Block QGR Value : " + QRG_Value);
		assertTrue(AdvGroup.Edit_block_QGR_Value.isDisplayed(), "QRG Amount not found");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Expected_Revenue, driver);
		String Exp_Revenue = AdvGroup.Edit_block_Expected_Revenue.getText();
		advGroupsLogger.info("Edit Block Expected Revenue : " + Exp_Revenue);
		test_steps.add("Edit Block Expected Revenue  : " + Exp_Revenue);
		assertTrue(AdvGroup.Edit_block_Expected_Revenue.isDisplayed(), "Exp Amount not found");

		// group.Verify_Room_Block_Promo_code(driver);

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_RoomNinght, driver);
		String RoomsforNinght = AdvGroup.Edit_block_RoomNinght.getText();
		advGroupsLogger.info("Updated Room ninghts : " + RoomsforNinght);
		test_steps.add("Updated Room ninghts: " + RoomsforNinght);

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_RoomBlocks, driver);
		String Blocked_Rooms = AdvGroup.Edit_block_RoomBlocks.getText();
		advGroupsLogger.info("Rooms for Block : " + Blocked_Rooms);
		test_steps.add("Rooms for Block : " + Blocked_Rooms);

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_ActuvalQTG_Value, driver);
		String Act_QRGValue = AdvGroup.Edit_block_ActuvalQTG_Value.getText();
		advGroupsLogger.info("ActuvalQTG Value : " + Act_QRGValue);
		test_steps.add("ActuvalQTG Value : " + Act_QRGValue);
		// assertEquals(QRG_Value.replace("$", ""),Act_QRGValue,"QGR values are not
		// matching :");

		Double RoomBlock_QRG = Double.parseDouble(QRG_Value.replace("$", ""));
		Double summary_Qrg = Double.parseDouble(Act_QRGValue);
		if (RoomBlock_QRG >= summary_Qrg) {
			advGroupsLogger.info("Room Block Qrg greterthan or equal the summary block QRG");
			test_steps.add("Room Block Qrg greterthan or equal the summary block QRg");

		} else if (RoomBlock_QRG <= summary_Qrg) {
			advGroupsLogger.info("Room Block Qrg lessthan or equal the summary block QRG");
			test_steps.add("Room Block Qrg lessthan or equal the summary block QRg");

		}

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_total_Amount, driver);
		String Total_Amount = AdvGroup.Edit_block_total_Amount.getText();
		advGroupsLogger.info("total Amount : " + Total_Amount);
		test_steps.add("total Amount : " + Total_Amount);
		Double actTotal = Double.parseDouble(Act_QRGValue);
		Double No_Of_Blocks = Double.parseDouble(Blocked_Rooms);
		Double act_amount = actTotal * No_Of_Blocks;
		advGroupsLogger.info("based on Blocked Room rate : " + act_amount);
		test_steps.add("based on Blocked Room rate : " + act_amount);
		Double total_amount = Double.parseDouble(Total_Amount);
		Double Expt_Revenue = Double.parseDouble(Exp_Revenue.replace("$", ""));
		assertEquals(act_amount, total_amount, "Total is not  matching");
		if (Expt_Revenue >= total_amount) {
			advGroupsLogger.info("Expected Revenue greater than the total amount");
			test_steps.add("Expected Revenue greater than the total amount");

		}
		Utility.ScrollToElement(AdvGroup.Group_Edit_Block_Save, driver);
		// Wait.waitUntilPresenceOfElementLocated(OR.Group_Edit_Block_Save, driver);
		Wait.explicit_wait_elementToBeClickable(AdvGroup.Group_Edit_Block_Save, driver);

		return test_steps;

	}

	public void click_EditBlockSave(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);

		Utility.ScrollToElement(AdvGroup.Group_Edit_Block_Save, driver);

		Wait.explicit_wait_elementToBeClickable(AdvGroup.Group_Edit_Block_Save, driver);

		AdvGroup.Group_Edit_Block_Save.click();
		test_steps.add("Click on block save");
		advGroupsLogger.info("Click on block save");

		try {
			WebElement Releasedate_popup = driver.findElement(By.xpath("//span[contains(text(),'Block Room Nights')]"));
			if (Releasedate_popup.isDisplayed()) {
				assertTrue(driver.findElement(By.xpath("//div[@id='msgRoomBlocksNotMatch']/div")).isDisplayed(),
						"There is a difference in Room Nights blocked(3) v/s the Room Nights(2) in the initial search. Do you want to continue?");
				test_steps.add("Block Room ninght pop displayed");
				advGroupsLogger.info("Block Room ninght pop displayed");
				AdvGroup.Room_Block_Popup.click();
				test_steps.add("Click on continue on Room block pop up");
				advGroupsLogger.info("Click on continue on Room block pop up");

			} else {

				test_steps.add("Block Room ninght pop not displayed");
				advGroupsLogger.info("Block Room ninght pop not displayed");
			}

		} catch (Exception e) {
			test_steps.add("Release date pop not displayed");
			advGroupsLogger.info("Release date not  pop displayed");
		}

		while (true) {
			if (!driver.findElement(By.xpath("//div[@id='InnerFreezePane']/img")).isDisplayed()) {
				System.out.println("in if");
				Wait.wait3Second();
				break;
			} else {
				System.out.println("Waiting for frame");
				Wait.wait5Second();
			}
		}
		Utility.ScrollToElement(AdvGroup.Group_Edit_Block_Done, driver);
		AdvGroup.Group_Edit_Block_Done.click();
		test_steps.add("Click on edit block done");
		advGroupsLogger.info("Click on edit block done");
		assertTrue(AdvGroup.GroupAccountDetails.isDisplayed(), "Group Account Details not found");

	}

	public ArrayList<String> verify_Preview_Folio_Details(WebDriver driver, String BlockName)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<>();
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
		test_steps.add("verify PreviewFolio details");
		advGroupsLogger.info("verify PreviewFolio details");

		/*
		 * driver.switchTo().frame(driver.findElement(By.id(
		 * "MainContent_Iframe_accountpicker"))); Wait.wait1Second();
		 */

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_details, driver);
		assertTrue(AdvGroup.Edit_block_details.isDisplayed(), "edit block details find");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Name, driver);
		String Block_Name = AdvGroup.Edit_block_Name.getText();
		advGroupsLogger.info("PreviewFolio Block Name : " + Block_Name);
		test_steps.add("PreviewFolio Block Name : " + Block_Name);
		assertEquals(AdvGroup.Edit_block_Name.getText(), BlockName, "PreviewFolio Block Name is  matching");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Arrive_date, driver);
		String Arrival_Date = AdvGroup.Edit_block_Arrive_date.getText();
		advGroupsLogger.info("PreviewFolio Arriveldate : " + Arrival_Date);
		test_steps.add("PreviewFolio Arriveldate : " + Arrival_Date);
		assertTrue(AdvGroup.Edit_block_Arrive_date.isDisplayed(), "Arrival date not found");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Depart, driver);
		String Depart_Date = AdvGroup.Edit_block_Depart.getText();
		advGroupsLogger.info("PreviewFolio Departldate : " + Depart_Date);
		test_steps.add("PreviewFolio Departldate: " + Depart_Date);
		assertTrue(AdvGroup.Edit_block_Depart.isDisplayed(), "Depart date not found");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_QGR_Value, driver);
		String QRG_Value = AdvGroup.Edit_block_QGR_Value.getText();
		advGroupsLogger.info("PreviewFolio QRG Amount : " + QRG_Value);
		test_steps.add("PreviewFolio QRG Amount : " + QRG_Value);
		assertTrue(AdvGroup.Edit_block_QGR_Value.isDisplayed(), "QRG Amount not found");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Edit_block_Expected_Revenue, driver);
		String Exp_Revenue = AdvGroup.Edit_block_Expected_Revenue.getText();
		advGroupsLogger.info("PreviewFolio Exp Amount: " + Exp_Revenue);
		test_steps.add("PreviewFolio Exp Amount: : " + Exp_Revenue);
		assertTrue(AdvGroup.Edit_block_Expected_Revenue.isDisplayed(), "Exp Amount not found");

		return test_steps;

	}

	public String validatePromecode(WebDriver driver, String Room_Block_PromoCod, String AccountName,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.Reservation_ACCNO, driver);
		Wait.wait2Second();
		String Reservation_ACCNO = AdvGroup.Reservation_ACCNO.getText();
		advGroupsLogger.info("ReservationPage Promocode: " + Reservation_ACCNO);
		test_steps.add("PreviewFolio Exp Amount: : " + Reservation_ACCNO);
		assertEquals(Reservation_ACCNO, AccountName, "promo codes are not matching");

		Wait.explicit_wait_visibilityof_webelement_120(AdvGroup.ReservationPage_Promocode, driver);
		Wait.wait2Second();
		String ReservationPage_Promocode = AdvGroup.ReservationPage_Promocode.getText();
		advGroupsLogger.info("ReservationPage Promocode: " + ReservationPage_Promocode);
		test_steps.add("PreviewFolio Exp Amount: : " + ReservationPage_Promocode);
		assertEquals(Room_Block_PromoCod, ReservationPage_Promocode, "promo codes are not matching");

		return ReservationPage_Promocode;

	}

public String select_Room_Number(WebDriver driver, int rowNo, ArrayList<String> test_steps) throws InterruptedException {

	Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	// Wait.explicit_wait_xpath("//*[@id='MainContent_Iframe_AssignRooms']");
//	driver.switchTo().defaultContent();
//	driver.switchTo().frame("MainContent_Iframe_AssignRooms");
	
	String slotPath = "//div[@id='contenttableAssignRoomsGrid']/div[" + rowNo + "]/div[8]/div";
	String roomNoPath = "//div[@id='contenttableAssignRoomsGrid']/div[" + rowNo + "]/div[3]/div";
	
	Wait.WaitForElement(driver, slotPath);
	Wait.waitUntilPresenceOfElementLocated(slotPath, driver);
	Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(slotPath)), driver);
	Wait.wait1Second();
	driver.findElement(By.xpath(slotPath)).click();
	
	String selectedRoomNo = driver.findElement(By.xpath(roomNoPath)).getText();
	
	test_steps.add("click on room number : " + selectedRoomNo);
	advGroupsLogger.info("click on room number : " + selectedRoomNo);
	
	return selectedRoomNo;

	}

public String getRateFromRoomBlock(WebDriver driver, ArrayList getTest_Steps, String RoomClassName)
		throws InterruptedException {
	Wait.wait2Second();
	String rate="";
	Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
	Wait.wait1Second();
	List<WebElement> RoomCount = AdvGroup.CountofRooms;
	advGroupsLogger.info("RoomCount" + RoomCount.size());
	for (int i = 0; i < RoomCount.size(); i++) {
		if (RoomCount.get(i).getText().equals(RoomClassName)) {
			rate=driver.findElement(By.xpath("//div[@id='row" + i + "JQGrid']//div[11]//label")).getText();
			Wait.wait2Second();
			break;
		} else if (i == (RoomCount.size() - 1)) {
			getTest_Steps.add("NO Rate found for the room class "+RoomClassName);
			break;
		}
	}
	return rate;
}

}

