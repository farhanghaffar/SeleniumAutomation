package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.INightAudit;
import com.innroad.inncenter.interfaces.IPolicies;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_NightAudit;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;
import com.innroad.inncenter.webelements.WebElements_Policies;

public class NightAudit implements INightAudit {

	public static Logger NightAuditLogger = Logger.getLogger("NigthAuditLogger");
	
	public ArrayList<String> EnterAuditDate(WebDriver driver, String Date) throws InterruptedException, ParseException {

		// TODO Auto-generated method stub
		ArrayList<String> testSteps = new ArrayList<>();
		NightAuditLogger.info("Date : " + Date);
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement(driver, OR.AuditDatePicker);
		Utility.ScrollToElement(NightAudit.AuditDatePicker, driver);
		NightAudit.AuditDatePicker.click();
		NightAuditLogger.info("Click on date picker icon");
		Wait.explicit_wait_visibilityof_webelement(NightAudit.ClickToday, driver);
		NightAudit.ClickToday.click();
		testSteps.add("Selecting The Date");
		NightAuditLogger.info("Selecting The Date");
		NightAudit.AuditDatePicker.click();
		NightAuditLogger.info("Click on date picker icon");
		String monthYear = Utility.get_MonthYear(Date);
		String day = Utility.getDay(Date);
		//reslogger.info(monthYear);
		String header = null, headerText = null, next = null, date;
		for (int i = 1; i <= 24; i++) {
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
						testSteps.add("Selected Date : " + Date);
						NightAuditLogger.info("Selected Date : " + Date);
						//test_steps.add("Selecting checkin date : " + Date);
						//reslogger.info("Selecting checkin date : " + Date);
						break;
					}
				}
				break;
			} else {
				NightAuditLogger.info("Click icon to change month");
				
				if(ESTTimeZone.CompareDates(Date, "dd/MM/yyyy", "US/Eastren")) {
					next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[@class='prev']/i";
					Wait.WaitForElement(driver, next);
					driver.findElement(By.xpath(next)).click();
					NightAuditLogger.info("Click prev icon");
					Wait.wait2Second();					
				}else {
					next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[@class='next']/i";
					Wait.WaitForElement(driver, next);
					driver.findElement(By.xpath(next)).click();
					NightAuditLogger.info("Click next icon");
					Wait.wait2Second();					
					
				}
			}
		}

		return testSteps;
	}

	public ArrayList<String> enterAuditDate(WebDriver driver, String Date) throws InterruptedException, ParseException {
		// TODO Auto-generated method stub
		ArrayList<String> testSteps = new ArrayList<>();
		NightAuditLogger.info("Date : " + Date);
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement(driver, OR.AuditDatePicker);
		Utility.ScrollToElement(NightAudit.AuditDatePicker, driver);
		NightAudit.AuditDatePicker.click();
		NightAuditLogger.info("Click on date picker icon");
		Wait.explicit_wait_visibilityof_webelement(NightAudit.ClickToday, driver);
		NightAudit.ClickToday.click();
		testSteps.add("Selecting The Date");
		NightAuditLogger.info("Selecting The Date");
		NightAudit.AuditDatePicker.click();
		NightAuditLogger.info("Click on date picker icon");
		String monthYear = Utility.get_MonthYear(Date);
		String day = Utility.getDay(Date);
		//reslogger.info(monthYear);
		String header = null, headerText = null, next = null, date;
		for (int i = 1; i <= 24; i++) {
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
						testSteps.add("Selected Date : " + Date);
						NightAuditLogger.info("Selected Date : " + Date);
						//test_steps.add("Selecting checkin date : " + Date);
						//reslogger.info("Selecting checkin date : " + Date);
						break;
					}
				}
				break;
			} else {
				NightAuditLogger.info("Click icon to change month");
				
				if(ESTTimeZone.CompareDates(Date, "dd/MM/yyyy", "US/Eastren")) {
					next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[@class='prev']/i";
					Wait.WaitForElement(driver, next);
					driver.findElement(By.xpath(next)).click();
					NightAuditLogger.info("Click prev icon");
					Wait.wait2Second();					
				}else {
					next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[@class='next']/i";
					Wait.WaitForElement(driver, next);
					driver.findElement(By.xpath(next)).click();
					NightAuditLogger.info("Click next icon");
					Wait.wait2Second();					
					
				}
			}
		}

		return testSteps;
	}

	@Override
	public void GoButtonClick(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.Go_ButtonClick.click();

	}

	public void GoButtonClick(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.Go_ButtonClick.click();
		testSteps.add("Clicked On Go Button");
		NightAuditLogger.info("Clicked On Go Button");

	}



	@Override
	public String GetHouseKeepingCount(WebDriver driver) throws InterruptedException {

		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		String Count;
		Wait.explicit_wait_visibilityof_webelement(NightAudit.HouseKeepingCount, driver);
		Count = NightAudit.HouseKeepingCount.getText();
		Utility.app_logs.info(Count);
		return Count;
	}

	@Override
	public void VerifyHouseKeepingSection(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);

		boolean SetNowButton = NightAudit.SetNowButton.isEnabled();
		assertEquals(SetNowButton, true, "HouseKeepingText isnot Enabled ");

		boolean HouseKeepingText = NightAudit.HouseKeepingText.isDisplayed();
		assertEquals(HouseKeepingText, true, "HouseKeepingText is Clickable");
	}

	public void ClickSetNowButton(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);

		boolean SetNowButton = NightAudit.SetNowButton.isEnabled();
		assertEquals(SetNowButton, true, "HouseKeepingText isnot Enabled ");
		NightAudit.SetNowButton.click();
		Wait.explicit_wait_visibilityof_webelement(NightAudit.UpdateRoomCondition_Popup, driver);
		assertTrue(NightAudit.UpdateRoomCondition_Popup.isDisplayed(),
				"Failed : Update Room Condition Popup is not displayed ");
		NightAudit.UpdateRoomCondition_Yes.click();
	}

	@Override
	public void EnterAuditDate(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.AuditDatePicker.click();
		NightAudit.ClickToday.click();
		String GetDate = NightAudit.AuditDate.getAttribute("value");
		NightAudit.AuditDatePicker.click();
		String CurrrentMonthDateAndDay = driver.findElement(By.cssSelector(".active.day")).getAttribute("title");
		String[] CurrentDateAndYear = CurrrentMonthDateAndDay.split(", ");
		String CurrentMonthAndDate = CurrentDateAndYear[1];
		String CurrentDate = CurrentMonthAndDate.split(" ")[1];
		String CurrentMonthFullName = CurrentMonthAndDate.split(" ")[0];
		String CurrentMonthAbbrevation = CurrentMonthFullName.substring(0, Math.min(CurrentMonthFullName.length(), 3));
		String CurrentYear = CurrentDateAndYear[2];
		int CurrentDateInt = Integer.valueOf(CurrentDate);
		if (CurrentDateInt < 10) {
			CurrentDate = Integer.toString(CurrentDateInt);
			CurrentDate = "0" + CurrentDate;
		}
		assertTrue(GetDate.equals(CurrentMonthAbbrevation + " " + CurrentDate + ", " + CurrentYear),
				"Selcted audit date isn't displayed");

	}

	public String GetSystemDate() {

		DateFormat dateInstance = SimpleDateFormat.getDateInstance();
		String date = dateInstance.format(Calendar.getInstance().getTime());
		String SplitDate[] = date.split(" ");
		String month = SplitDate[0];
		String day = SplitDate[1];
		String d[] = day.split(",");
		String year = SplitDate[2];
		if (Integer.parseInt(d[0]) < 10) {
			day = "0" + d[0];
		}
		String formate = month + " " + day + " " + year;
		return formate;

	}

	@Override
	public void DailyTransactionButtonClick(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.DailyTransactionButton.click();
		assertTrue(NightAudit.DailyTransactionPage.isDisplayed(), "Daily Transaction Page isn't displayed");
		TableRowCountBefore = GetTableRowCount(driver);
	}

	public static int TableRowCountBefore = 0;
	public static int TableRowCountAfter = 0;

	public int GetTableRowCount(WebDriver driver) {

		int i = 0;
		WebElement table = driver.findElement(By.xpath("//*[@id='MainContent_dgCurrentNightAuditList']"));
		List<WebElement> listOfRows1 = table.findElements(By.className("dgItem"));
		List<WebElement> listOfRows2 = table.findElements(By.className("dgItemAlt"));
		i = listOfRows2.size() + listOfRows1.size();
		return i;
	}

	@Override
	public void SelectAllItemStatus(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);

		for (int i = 0; i < 4; i++) {
			WebElement ItemStatus = driver.findElement(By.id("MainContent_chkItemStatus_" + i));

			if (!ItemStatus.isSelected()) {
				ItemStatus.click();
				boolean itemStatusState = ItemStatus.isSelected();
				assertEquals(itemStatusState, true, "itemStatusState isn't true");

			}
		}
		Wait.wait2Second();
		NightAudit.DailyTransactionPage_GoBtn.click();

	}

	@Override
	public void DailyTransaction_PostButtonClick(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.ItemToPost.click();
		Wait.wait2Second();
		NightAudit.DailyTransactionPage_PostBtn.click();
		Wait.wait15Second();
		assertTrue(TableRowCountBefore > TableRowCountAfter, "Post Button isn't Clicked");

	}

	// Period Is Open
	@Override
	public void PeriodIsOpenButtonClick(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.PeriodIsOpenButton.click();
		assertTrue(NightAudit.DailyTransactionPage.isDisplayed(), "Daily Transaction Page isn't displayed");

	}

	public void PeriodIsOpenButtonClick(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.PeriodIsOpenButton.click();
		assertTrue(NightAudit.DailyTransactionPage.isDisplayed(), "Daily Transaction Page isn't displayed");
		testSteps.add("Clicked On Period is open Button");
		NightAuditLogger.info("Clicked On Period is open Button");

	}

	public void PeriodIsOpen_LockButtonClick(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		driver.switchTo().frame("dialog-body0");
		NightAudit.PeriodIsOpen_LockButton.click();
		Wait.wait2Second();
		driver.switchTo().defaultContent();
		assertTrue(NightAudit.PeriodStatusPage.isDisplayed(), "Period Statud Page isn't displayed");

	}

	public void PostAllItemCheckBox(WebDriver driver) throws InterruptedException {
		TableRowCountBefore = GetTableRowCount(driver);
		// TODO Auto-generated method stub
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		NightAudit.DialyTrans_CheckBoxAll.click();
		DailyTransaction_PostButtonClick(driver);

	}

	public String LongStay(WebDriver driver) throws InterruptedException {

		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.VerifyLongStay);
		Wait.explicit_wait_visibilityof_webelement(NightAudit.VerifyLongStay, driver);
		return NightAudit.VerifyLongStay.getText();
	}

	public ArrayList<String> LongStay_SetNow(WebDriver driver,ArrayList<String> test_steps) throws InterruptedException {

		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);
		Wait.explicit_wait_visibilityof_webelement(nightAudit.LongStay_SetNowBtn, driver);
		nightAudit.LongStay_SetNowBtn.click();
		test_steps.add("Click on Set Now button");
		
		assertEquals(nightAudit.LongStay_Popup.getText(), "Long Stay", "Long stay popup is not appearing");
		test_steps.add("verified Long Stay popup appear");
		assertTrue(
				nightAudit.LongStayMessage.getText().contains(
						"  Reservations meet the tax exemption criteria under Long Stay. Do you wish to set Long Stay?"),
				"Long stay popup is not appearing");
		
		assertEquals(nightAudit.LongStay_YesButton.getText(), "Yes", "Yes button is not appear on long stay popup");
		test_steps.add("verified Long Stay popup had Yes button");
		
		assertEquals(nightAudit.LongStay_NoButtons.getText(), "No", "No button is not appear on long stay popup");
		test_steps.add("verified Long Stay popup had No button");
		
		nightAudit.LongStay_YesButton.click();
		test_steps.add("Click on Yes button");
		Wait.wait2Second();
		
		Wait.explicit_wait_visibilityof_webelement(nightAudit.VerifyLongStay, driver);
		assertEquals(nightAudit.VerifyLongStay.getText(), "0", "Long stay value did not set 0");
		test_steps.add("Reset Long Stay value again 0");
		
		return test_steps;

	}
	
	public void Get_NightAuditDate(WebDriver driver,ArrayList<String> test_steps)
	{
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		String GetDate = NightAudit.AuditDate.getAttribute("value");
		test_steps.add("Night Audit Date: <b>"+GetDate+" </b>");
		NightAuditLogger.info("Night Audit Date: "+GetDate+"");
	}
	
	public void Verified_HouseKeeping_No_Increase(WebDriver driver,ArrayList<String> test_steps, int BeforeCheckIn, int AfterCheckInNO, String GetHouseKeepintNO)
	{
		int HouseKeepingNOIncrease = BeforeCheckIn+AfterCheckInNO;
		String HouseKeepingIncreaseNO=String.valueOf(HouseKeepingNOIncrease);
		System.out.println(GetHouseKeepintNO.trim());
		System.out.println((String.valueOf(HouseKeepingNOIncrease)).trim());
		assertEquals(GetHouseKeepintNO.trim(),(HouseKeepingIncreaseNO.trim()) ,"Failed: to Verify HouseKeeping Number");
		test_steps.add("Verified House Keeping No Increase To: <b>"+String.valueOf(HouseKeepingNOIncrease)+" </b>");
		NightAuditLogger.info("Verified House Keeping No Increase To:"+String.valueOf(HouseKeepingNOIncrease));
	}
	
	public void Verified_HouseKeeping_No_Decrease(WebDriver driver,ArrayList<String> test_steps,  int AfterCheckInNO, int AfterCheckOut,String GetHouseKeepintNO)
	{
		int HouseKeepingNODecrease = AfterCheckInNO-AfterCheckOut;
		String HouseKeepingDecreaseNO=String.valueOf(HouseKeepingNODecrease);
		System.out.println(GetHouseKeepintNO.trim());
		System.out.println((String.valueOf(HouseKeepingNODecrease)).trim());
		assertTrue(GetHouseKeepintNO.trim().equals(HouseKeepingDecreaseNO.trim()) ,"Failed: to Verify HouseKeeping Number");
		test_steps.add("Verified House Keeping No Decrease To: <b>"+String.valueOf(HouseKeepingNODecrease)+" </b>");
		NightAuditLogger.info("Verified House Keeping No Decrease To:"+String.valueOf(HouseKeepingNODecrease));
	}

	public void Period_Lock(WebDriver driver,ArrayList<String> test_steps, String reservation)
	{ 
		
		ArrayList<String> matchingstring = new ArrayList<>();
		ArrayList<String> listofrowsmatching = new ArrayList<>();
		Elements_NightAudit nightaud = new Elements_NightAudit(driver);
		nightaud.PeriodIsOpenButton.click();
		test_steps.add("Clicked On Period Open Button");
		NightAuditLogger.info("Clicked On Period Open Button");
		WebElement matchingrow =driver.findElement(By.xpath("//table[@id='MainContent_dgCurrentNightAuditList']/tbody/tr/td[text()='"+reservation+"']"));
		assertTrue(matchingrow.isDisplayed(), "Failed to verify reservation number");
		
		Wait.WaitForElement(driver, OR.SelectCurrentDated_PeriodLock);
		nightaud.SelectCurrentDated_PeriodLock.click();
		test_steps.add("selected all current items");
		NightAuditLogger.info("selected all current items");
		nightaud.DailyTransactionPage_PostBtn.click();
		test_steps.add("Post All Current Items");
		NightAuditLogger.info("Post All Current Items");
		Wait.WaitForElement(driver, OR.SelectPriorDated_PeriodLock);
		nightaud.SelectPriorDated_PeriodLock.click();
		test_steps.add("Clicked On Prior Date Tab");
		NightAuditLogger.info("Clicked On Prior Date Tab");

			int i = 0;
			int j =15;
			WebElement table = driver.findElement(By.xpath("//*[@id='MainContent_dgCurrentNightAuditList']"));
			List<WebElement> listOfRows1 = table.findElements(By.className("dgItem"));
			List<WebElement> listOfRows2 = table.findElements(By.className("dgItemAlt"));
			i = listOfRows2.size() + listOfRows1.size();
			if (i >  j)
			{
				System.out.println("this should be covered in manual testing");
			}
			else 
			{
				assertTrue(matchingrow.isDisplayed(), "Failed to verify reservation number");
				Wait.WaitForElement(driver, OR.SelectPriorDatedClick_PeriodLock);
				nightaud.SelectPriorDatedClick_PeriodLock.click();
				test_steps.add("Select All Prior Items");
				NightAuditLogger.info("Select All Prior Items");
				nightaud.DailyTransactionPage_PostBtn.click();
				test_steps.add("Post Prior Items");
				NightAuditLogger.info("Post Prior Items");
			}
		
		Wait.WaitForElement(driver, OR.SelectVoidDated_PeriodLock);
		nightaud.SelectVoidDated_PeriodLock.click();
		test_steps.add("Clicked On Void Tab");
		NightAuditLogger.info("Clicked On Void Tab");
		assertTrue(matchingrow.isDisplayed(), "Failed to verify reservation number");
		Wait.WaitForElement(driver, OR.SelectVoidDateClick_PeriodLock);
		nightaud.SelectVoidDateClick_PeriodLock.click();
		test_steps.add("Select All Void Items");
		NightAuditLogger.info("Select All Void Items");
		
		nightaud.DailyTransactionPage_PostBtn.click();
		test_steps.add("Post Void Items");
		NightAuditLogger.info("Post Void Items");
		}


	public void enter_PaymentDetailsForCheckout(WebDriver driver, ArrayList<String> test_steps,String CardNumber,String  NameOnCard ,String CardExpDate) throws InterruptedException {
		Elements_NightAudit nightau = new Elements_NightAudit(driver);
		Wait.WaitForElement(driver,OR.selectPaymentMethod_Checkout);
		nightau.selectPaymentMethod_Checkout.click();
		String paymentMethod = "//label[text()='PAYMENT METHOD']/preceding-sibling::div//ul/li//span[contains(text(),'Amex')]";
		Wait.WaitForElement(driver, paymentMethod);
		driver.findElement(By.xpath(paymentMethod)).click();
		//nightau.paymentMethod_Checkout.click();
		//Wait.wait1Second();
		String cardNumber = "(//input[@placeholder='Credit Card Number'])[2]";
		Wait.WaitForElement(driver, cardNumber);
		driver.findElement(By.xpath(cardNumber)).sendKeys(CardNumber);
		String cardname = "(//input[@placeholder='Name On Card'])[3]";
		Wait.WaitForElement(driver, cardname);
		driver.findElement(By.xpath(cardname)).sendKeys(NameOnCard);
		String expirydate = "(//input[@placeholder='Expiry Date'])[2]";
		Wait.WaitForElement(driver, expirydate);
		driver.findElement(By.xpath(expirydate)).sendKeys(CardExpDate);
		
		
}

	public int getPriorItemSize(WebDriver driver)
			throws InterruptedException {
		List<WebElement> priorItemList = driver.findElements(By.id(OR.priorItems));
		int size = priorItemList.size();
		NightAuditLogger.info("'Prior Items' size  : " + size);
		
		return size;
	}
	


	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: clickPriorItems 
	 * Description: This method will click on Prior Items
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: void
	 * String ' Created By: Muhammad bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	
	public void clickPriorItems(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		Wait.WaitForElement_ID(driver, OR.priorItems);
		Wait.waitForElementToBeVisibile(By.id(OR.priorItems), driver);
		Wait.waitForElementToBeClickable(By.id(OR.priorItems), driver);
		nightAudit.priorItems.click();
		NightAuditLogger.info("Clicked On 'Prior Items'");
		testSteps.add("Clicked On 'Prior Items'");
		
	}	


	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: clickCurrentItems 
	 * Description: This method will click on Current Items
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: void
	 * String ' Created By: Muhammad bakar ' Created On: 11-19-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	
	public void clickCurrentItems(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
			Wait.WaitForElement_ID(driver, OR.currentItems);
			Wait.waitForElementToBeVisibile(By.id(OR.currentItems), driver);
			Wait.waitForElementToBeClickable(By.id(OR.currentItems), driver);
			nightAudit.currentItems.click();
			NightAuditLogger.info("Clicked On 'Current Items'");
			testSteps.add("Clicked On 'Current Items'");
		
	}	
		

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: clickVoidItems 
	 * Description: This method will click on void Items
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: void
	 * String ' Created By: Muhammad bakar ' Created On: 11-19-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	
	public void clickVoidItems(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		Wait.WaitForElement_ID(driver, OR.voidItems);
		Wait.waitForElementToBeVisibile(By.id(OR.voidItems), driver);
		Wait.waitForElementToBeClickable(By.id(OR.voidItems), driver);
		nightAudit.voidItems.click();
		NightAuditLogger.info("Clicked On 'Void Items'");
		testSteps.add("Clicked On 'Void Items'");
		
	}	
		

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: verifyNoResultsFound 
	 * Description: This method will verify no record found error message is displaying or not based on boolean passedby user
	 * Input parameters: Webdriver,ArrayList<String> , boolean
	 * Return value: void
	 * String ' Created By: Muhammad bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	
	public void verifyNoResultsFound(WebDriver driver, ArrayList<String> testSteps, boolean isDipslay) throws InterruptedException {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		if(isDipslay) {
			assertTrue(nightAudit.noRecordFoundMessage.isDisplayed(), "Failed : No Record Found is not displaying");
			NightAuditLogger.info("Verified that error message ' No records meet your criteria.  Please change your criteria and search again. ' is displaying");
			testSteps.add("Verified that error message ' No records meet your criteria.  Please change your criteria and search again. ' is displaying");
			
		}else {
			
			assertTrue(!nightAudit.noRecordFoundMessage.isDisplayed(), "Failed : No Record Found is displaying");
			NightAuditLogger.info("Verified that error message 'No records meet your criteria.  Please change your criteria and search again.' is not displaying");
			testSteps.add("Verified that error message 'No records meet your criteria.  Please change your criteria and search again.' is not displaying");
			
		}

	}
	

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: clickPriorItems 
	 * Description: This method will click on Post button
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: void
	 * String ' Created By: Muhammad bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	
	public void clickPostButton(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		Wait.WaitForElement_ID(driver, OR.postButton);
		Wait.waitForElementToBeVisibile(By.id(OR.postButton), driver);
		Wait.waitForElementToBeClickable(By.id(OR.postButton), driver);
		nightAudit.postButton.click();
		NightAuditLogger.info("Clicked On 'Post button'");
		testSteps.add("Clicked On 'Post button'");
		
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: clickDailyTransactions 
	 * Description: This method will click on daily transactions
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: void
	 * String ' Created By: Muhammad bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public void clickDailyTransactions(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		Wait.WaitForElement(driver, OR.dailyTransactions);
		Wait.waitForElementToBeVisibile(By.xpath(OR.dailyTransactions), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.dailyTransactions), driver);
		nightAudit.dailyTransactions.click();
		NightAuditLogger.info("Clicked On 'Daily Transactions'");
		testSteps.add("Clicked On 'Daily Transactions'");
		
	}


	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: selectDateContainsItems 
	 * Description: This method will check for dates by index for prior  items. If prior items exist 
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: int
	 * String ' Created By: Muhammad bakar ' Created On: 11-19-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	
	
	static int i=0;
	public int selectDateContainsItems(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException, ParseException {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);	
		NightAuditLogger.info(i);
		String getCurrentDate = Utility.getNextDate(i, "dd/MM/yyyy");		
		EnterAuditDate(driver, getCurrentDate);
	    GoButtonClick(driver);
	    clickDailyTransactions(driver, testSteps);
	    clickPriorItems(driver, testSteps);
	    
		List<WebElement> noRecordList = driver.findElements(By.xpath(OR.noRecordFoundMessage));
		while(noRecordList.size() > 0) {
			i++;
			selectDateContainsItems(driver, testSteps);
		}
		
		return i;
	}
	
	public void clickPostedStatus(WebDriver driver, ArrayList<String> testSteps) 
	{
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);	
		Wait.WaitForElement(driver, OR.periodStatus);
		Wait.waitForElementToBeVisibile(By.xpath(OR.periodStatus), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.periodStatus), driver);				
		Utility.clickThroughJavaScript(driver, nightAudit.periodStatus);
		NightAuditLogger.info("Click using java script posted status");
		testSteps.add("Click posted status");
		NightAuditLogger.info("Click posted status");
	}

	
	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: getTotalPriorItems 
	 * Description: This method will get prior items
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: String
	 * String ' Created By: Muhammad bakar ' Created On: 11-19-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public String getTotalPriorItems(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		Wait.WaitForElement_ID(driver, OR.totalPriorItemsFound);
		Wait.waitForElementToBeVisibile(By.id(OR.totalPriorItemsFound), driver);
		Wait.waitForElementToBeClickable(By.id(OR.totalPriorItemsFound), driver);
		String getText = nightAudit.totalPriorItemsFound.getText().trim();
		NightAuditLogger.info(getText);
		testSteps.add(getText);
		String getCount = getText.replace("Total ", "");
		NightAuditLogger.info(getCount);
		getCount = getCount.replace(" Prior Dated Record(s) found", "");
		NightAuditLogger.info(getCount);
		
		return getCount;
	}


	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: selectAllPriorItems 
	 * Description: This method will click on select all prior items checkbox
	 * Input parameters: Webdriver,ArrayList<String> 
	 * Return value: void
	 * String ' Created By: Muhammad bakar ' Created On: 11-19-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	
	public void selectAllPriorItems(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		Wait.WaitForElement_ID(driver, OR.checkAllPriorItems);
		Wait.waitForElementToBeVisibile(By.id(OR.checkAllPriorItems), driver);
		Wait.waitForElementToBeClickable(By.id(OR.checkAllPriorItems), driver);
		nightAudit.checkAllPriorItems.click();
		NightAuditLogger.info("Selected all prior items");
		testSteps.add("Selected all prior items");
		
		
	}
	

	public String getPriorItemsDatesBasedOnIndex(WebDriver driver, ArrayList<String> testSteps, int index) throws InterruptedException, ParseException {
		
		String firstPriorItemDetail = "(//tr[@class='dgItem']//td[10])[" + index + "]";
		WebElement element = driver.findElement(By.xpath(firstPriorItemDetail));
		String getString = element.getText().trim();
		NightAuditLogger.info("Date for Prior Item at index("+ index +") : " + getString);
		testSteps.add("Date for Prior Item at index("+ index +") : " + getString);
		return getString;
	}
	static int dateCount = 0;
	
	public void getPriorItemsDates(WebDriver driver,String dateToCompare) throws InterruptedException, ParseException {
		String firstPriorItemDetail = "//tr[@class='dgItem']//td[10]";
		List<WebElement> element = driver.findElements(By.xpath(firstPriorItemDetail));
		
		for(int i=0; i < element.size(); i++) {

			String getText = element.get(i).getText().trim();
			//NigthAuditLogger.info(getText);
		//	testSteps.add(getText);
			if(dateToCompare.equalsIgnoreCase(getText)) {
				dateCount++;
				NightAuditLogger.info("date matched : " + dateCount);

				
			}
			if(dateCount == 100) {
				break;
			}				
		}
		 		
	}

	public int getAllOccurancesOfDate(WebDriver driver, ArrayList<String> testSteps, String dateToCompare) throws InterruptedException, ParseException {

		
		return dateCount;
	}


	public void postItemsBasedOnDate(WebDriver driver, ArrayList<String> testSteps, String dateToCompare) throws InterruptedException, ParseException {
		
		String paginationPath = "//tr[@align='right']//td//a";
		List<WebElement> paginationList = driver.findElements(By.xpath(paginationPath));
		ArrayList<String> getPagination = new ArrayList<>();
		NightAuditLogger.info("paginationList.size() " + paginationList.size());
		for(int k=0; k < paginationList.size(); k++) {
			NightAuditLogger.info("k : " + k + " : " + paginationList.get(k).getText());
			getPagination.add(paginationList.get(k).getText().trim());
		}
		for(int i=0; i < getPagination.size(); i++) {
			String firstPriorItemDetail = "//td[text()='"+ dateToCompare +"']//preceding-sibling::td[last()]";
			 		NightAuditLogger.info(firstPriorItemDetail);
			if(i == getPagination.size()-1) {
				NightAuditLogger.info("At last pagination index " + i);
			}else {
				List<WebElement> itemElement = driver.findElements(By.xpath(firstPriorItemDetail));			
				NightAuditLogger.info("itemElement size : "+ itemElement.size());
				for(int j=0; j < itemElement.size(); j++) {
					
					Utility.ScrollToElement(itemElement.get(j), driver);
					itemElement.get(j).click();
					NightAuditLogger.info("Check item with date("+ dateToCompare +") at index : " + j);
					testSteps.add("Check item with date("+ dateToCompare +") at index : " + j);
				}
				if(itemElement.size() > 0) {					
					clickPostButton(driver, testSteps);
				}
				

				String indexPath = "//tr[@align='right']//td//a[text()='" + getPagination.get(i) + "']";
				NightAuditLogger.info(indexPath);
				WebElement element = driver.findElement(By.xpath(indexPath));
				
				Wait.wait10Second();
				Utility.ScrollToElement(element, driver);
				element.click();
				NightAuditLogger.info("Clicked on pagination at index : " + i);							
				Wait.wait10Second();
				
			}						
		}
	}
	
	public void periodLockHeadingDisplay(WebDriver driver, ArrayList<String> testSteps, boolean isDisplay) {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		if(isDisplay) {
			assertTrue(nightAudit.periodLockMsg.size() > 0, "Failed : Period Lock Heading not displaying");
			NightAuditLogger.info("'The following periods will be locked and no further postings will be allowed:' heading is displaying");
			testSteps.add("'The following periods will be locked and no further postings will be allowed:' heading is displaying");
			
		}else {
			assertTrue(nightAudit.periodLockMsg.size() < 1, "Failed : Period Lock Heading displaying");
			NightAuditLogger.info("'The following periods will be locked and no further postings will be allowed:' heading is not displaying");
			testSteps.add("'The following periods will be locked and no further postings will be allowed:' heading is not displaying");
			
		}
	}
	

	public void switchToLockPeriodPopup(WebDriver driver, ArrayList<String> testSteps) 
	{
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);	
		driver.switchTo().frame(nightAudit.lockPeriodFrame);
		NightAuditLogger.info("Switched to period lock popup");
		testSteps.add("Switched to period lock popup");
		
	}
	
	public void clickLock(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);	
		Wait.WaitForElement(driver, OR.lockButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.lockButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.lockButton), driver);
		Utility.ScrollToElement(nightAudit.lockButton, driver);
		nightAudit.lockButton.click();
		NightAuditLogger.info("Lock button clicked");
		testSteps.add("Lock button clicked");
	}
	

	public void postAllItems(WebDriver driver, ArrayList<String> testSteps, String itemsToPost) throws InterruptedException, ParseException {
		Elements_NightAudit nightAudit = new Elements_NightAudit(driver);		
		String paginationPath = "//tr[@align='right']//td//a";
		List<WebElement> paginationList = driver.findElements(By.xpath(paginationPath));
		ArrayList<String> getPagination = new ArrayList<>();
		for(int k=0; k < paginationList.size(); k++) {
			NightAuditLogger.info("k : " + k + " : " + paginationList.get(k).getText());
			getPagination.add(paginationList.get(k).getText().trim());
		}
		NightAuditLogger.info("paginationList.size() " + paginationList.size());
		if(getPagination.size() > 0) {
			
	/*		for(int i=0; i < getPagination.size(); i++) {
				if((getPagination.size() > 1 && i == getPagination.size()-1) || (getPagination.size() <= 1 && i == getPagination.size())) {
					NightAuditLogger.info("At last pagination index " + i);
				}else {
*/								
						if(itemsToPost.equalsIgnoreCase("CurrentItems")) {
							if(driver.findElements(By.id(OR.currentItems)).size() > 0) {								
								clickCurrentItems(driver, testSteps);
							}
							List<WebElement> allCurrentItems = driver.findElements(By.id(OR.checkAllCurrentItems));
							while(allCurrentItems.size() > 0) {
								NightAuditLogger.info("Current items available");
								Wait.WaitForElement_ID(driver, OR.checkAllCurrentItems);
								Wait.waitForElementToBeVisibile(By.id(OR.checkAllCurrentItems), driver);
								Wait.waitForElementToBeClickable(By.id(OR.checkAllCurrentItems), driver);
								nightAudit.checkAllCurrentItems.click();
							
								clickPostButton(driver, testSteps);
								Wait.wait3Second();							
								allCurrentItems = driver.findElements(By.id(OR.checkAllCurrentItems));
							}
							
						}else if(itemsToPost.equalsIgnoreCase("PriorItems")) {
							if(driver.findElements(By.id(OR.priorItems)).size() > 0) {								
									clickPriorItems(driver, testSteps);
							}
							List<WebElement> allPriorItems = driver.findElements(By.id(OR.checkAllPriorItems));
							while(allPriorItems.size() > 0) {
								NightAuditLogger.info("Prior items available");
								Wait.WaitForElement_ID(driver, OR.checkAllPriorItems);
								Wait.waitForElementToBeVisibile(By.id(OR.checkAllPriorItems), driver);
								Wait.waitForElementToBeClickable(By.id(OR.checkAllPriorItems), driver);
								nightAudit.checkAllPriorItems.click();
							
								clickPostButton(driver, testSteps);
								Wait.wait3Second();		
								allPriorItems = driver.findElements(By.id(OR.checkAllPriorItems));
							}
							
						}else if(itemsToPost.equalsIgnoreCase("VoidItems")) {
							if(driver.findElements(By.id(OR.voidItems)).size() > 0) {								
								clickVoidItems(driver, testSteps);
							}
							List<WebElement> allVoidItems = driver.findElements(By.id(OR.checkAllVoidItems));
							while(allVoidItems.size() > 0) {
								NightAuditLogger.info("Void items available");
								
								  Wait.WaitForElement_ID(driver, OR.checkAllVoidItems);
								  Wait.waitForElementToBeVisibile(By.id(OR.checkAllVoidItems), driver);
								  Wait.waitForElementToBeClickable(By.id(OR.checkAllVoidItems), driver);
								  nightAudit.checkAllVoidItems.click();
								  	 
								  clickPostButton(driver, testSteps);
								  Wait.wait3Second();
								  allVoidItems = driver.findElements(By.id(OR.checkAllVoidItems));
									
							}
							
						}
						/*
						String indexPath = "//tr[@align='right']//td//a[text()='" + getPagination.get(i) + "']";
						NightAuditLogger.info(indexPath);
						WebElement element = driver.findElement(By.xpath(indexPath));
						
						Wait.wait10Second();
						Utility.ScrollToElement(element, driver);
						element.click();
						NightAuditLogger.info("Clicked on pagination at index : " + i);							
						Wait.WaitForElement_ID(driver, OR.totalPriorItemsFound);
						Wait.waitForElementToBeVisibile(By.id(OR.totalPriorItemsFound), driver);
						Wait.waitForElementToBeClickable(By.id(OR.totalPriorItemsFound), driver);
						Wait.wait10Second();
					*/		
		//		}						
	//		}
		}else {
				if(itemsToPost.equalsIgnoreCase("CurrentItems")) {
					if(driver.findElements(By.id(OR.currentItems)).size() > 0) {								
						clickCurrentItems(driver, testSteps);
					}
					List<WebElement> allCurrentItems = driver.findElements(By.id(OR.checkAllCurrentItems));
					if(allCurrentItems.size() > 0) {
						Wait.WaitForElement_ID(driver, OR.checkAllCurrentItems);
						Wait.waitForElementToBeVisibile(By.id(OR.checkAllCurrentItems), driver);
						Wait.waitForElementToBeClickable(By.id(OR.checkAllCurrentItems), driver);
						nightAudit.checkAllCurrentItems.click();
					
						clickPostButton(driver, testSteps);
						
					}
					
				}else if(itemsToPost.equalsIgnoreCase("PriorItems")) {
					if(driver.findElements(By.id(OR.priorItems)).size() > 0) {								
						clickPriorItems(driver, testSteps);
					}
					List<WebElement> allPriorItems = driver.findElements(By.id(OR.checkAllPriorItems));
					if(allPriorItems.size() > 0) {
						Wait.WaitForElement_ID(driver, OR.checkAllPriorItems);
						Wait.waitForElementToBeVisibile(By.id(OR.checkAllPriorItems), driver);
						Wait.waitForElementToBeClickable(By.id(OR.checkAllPriorItems), driver);
						nightAudit.checkAllPriorItems.click();
					
						clickPostButton(driver, testSteps);
						
					}
					
				}else if(itemsToPost.equalsIgnoreCase("VoidItems")) {
					if(driver.findElements(By.id(OR.voidItems)).size() > 0) {								
							clickVoidItems(driver, testSteps);
					}
					List<WebElement> allVoidItems = driver.findElements(By.id(OR.checkAllPriorItems));
					if(allVoidItems.size() > 0) {
						/*
						 * Wait.WaitForElement_ID(driver, OR.checkAllPriorItems);
						 * Wait.waitForElementToBeVisibile(By.id(OR.checkAllPriorItems), driver);
						 * Wait.waitForElementToBeClickable(By.id(OR.checkAllPriorItems), driver);
						 * nightAudit.checkAllPriorItems.click();
						 * 
						 */
						clickPostButton(driver, testSteps);
						
					}
					
				}
	
			}
		
	}
		

	public void postCurrentItems(WebDriver driver, ArrayList<String> testSteps, String dateToCompare) throws InterruptedException, ParseException {
		
			String firstPriorItemDetail = "//td[text()='"+ dateToCompare +"']//preceding-sibling::td[last()]";
	 		NightAuditLogger.info(firstPriorItemDetail);
			List<WebElement> itemElement = driver.findElements(By.xpath(firstPriorItemDetail));			
			NightAuditLogger.info("itemElement size : "+ itemElement.size());
			for(int j=0; j < itemElement.size(); j++) {					
					Utility.ScrollToElement(itemElement.get(j), driver);
					itemElement.get(j).click();
					NightAuditLogger.info("Check item with date("+ dateToCompare +") at index : " + j);
					testSteps.add("Check item with date("+ dateToCompare +") at index : " + j);
				}
				if(itemElement.size() > 0) {					
					clickPostButton(driver, testSteps);
				}
		
	}


	public void clickReservation(WebDriver driver, ArrayList<String> testSteps, String reservationNumber) throws InterruptedException, ParseException {
		
		String firstPriorItemDetail = "(//td[text()='"+ reservationNumber +"']//following-sibling::td//a)[1]";
 		NightAuditLogger.info(firstPriorItemDetail);
		List<WebElement> itemElement = driver.findElements(By.xpath(firstPriorItemDetail));			
		
		/*String paginationPath = "//tr[@align='right']//td//a";
		List<WebElement> paginationList = driver.findElements(By.xpath(paginationPath));
		ArrayList<String> getPagination = new ArrayList<>();
		for(int k=0; k < paginationList.size(); k++) {
			NightAuditLogger.info("k : " + k + " : " + paginationList.get(k).getText());
			getPagination.add(paginationList.get(k).getText().trim());
		}
		
		NightAuditLogger.info("paginationList.size() " + paginationList.size());
		if(getPagination.size() > 0) {
			NightAuditLogger.info("itemElement size : "+ itemElement.size());
			while(itemElement.size() < 1) {
				String indexPath = "//tr[@align='right']//td//a[text()='" + getPagination.get(i) + "']";
				NightAuditLogger.info(indexPath);
				WebElement element = driver.findElement(By.xpath(indexPath));				
				Wait.wait10Second();
				Utility.ScrollToElement(element, driver);
				element.click();
				NightAuditLogger.info("Clicked on pagination at index : " + i);							
				Wait.wait10Second();
				itemElement = driver.findElements(By.xpath(firstPriorItemDetail));
			}
			if(itemElement.size() > 0) {					
				Utility.ScrollToElement(itemElement.get(0), driver);
				itemElement.get(0).click();
				NightAuditLogger.info("Click guest name with reservation number("+ reservationNumber +")");
				testSteps.add("Click guest name with reservation number("+ reservationNumber +")");
			}else {
				NightAuditLogger.info("Guest name with reservation number("+ reservationNumber +") not found");
				testSteps.add("Guest name with reservation number("+ reservationNumber +") not found");				
			}
			
		}else {
		*/
			if(itemElement.size() > 0) {					
				Utility.ScrollToElement(itemElement.get(0), driver);
				itemElement.get(0).click();
				NightAuditLogger.info("Click guest name with reservation number("+ reservationNumber +")");
				testSteps.add("Click guest name with reservation number("+ reservationNumber +")");
			}else {
				NightAuditLogger.info("Guest name with reservation number("+ reservationNumber +") not found");
				testSteps.add("Guest name with reservation number("+ reservationNumber +") not found");
				
			}			
		//}			
	}

	public void verifyCurrencyLabelInReservation(WebDriver driver, String currencyLabel, ArrayList<String> testSteps) throws InterruptedException {
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		
		String iframe = "//iframe[@id='dialog-body0']";
		Wait.WaitForElement(driver, iframe);
		Wait.waitForElementToBeVisibile(By.xpath(iframe), driver);
		
		driver.switchTo().frame("dialog-body0");
		NightAuditLogger.info("Switched to iframe");
		
		String getCurrency = currencyLabel.split("\\(")[1];
		NightAuditLogger.info("getCurrency : " + getCurrency);
		getCurrency = getCurrency.split("\\)")[0];		
		NightAuditLogger.info("getCurrency : " + getCurrency);
		getCurrency = getCurrency.trim();		
		NightAuditLogger.info("getCurrency : " + getCurrency);
	
		Wait.WaitForElement(driver, OR.reservationBalance);
		Wait.waitForElementToBeVisibile(By.xpath(OR.reservationBalance), driver);
		String getBalance = NightAudit.reservationBalance.getText().trim();
		NightAuditLogger.info("Found : " + getBalance);
		testSteps.add("Found : " + getBalance);		
		
		assertTrue(getBalance.contains(getCurrency), "Failed : Currency label didn't match " + getCurrency);
		NightAuditLogger.info("Verified that currency label (" + currencyLabel +") exist in reservation");
		testSteps.add("Verified that currency label (" + currencyLabel +") exist in reservation");

		driver.switchTo().defaultContent();
		NightAuditLogger.info("Switched to default content");
		
		Wait.WaitForElement(driver, OR.closeIframe);
		Wait.waitForElementToBeVisibile(By.xpath(OR.closeIframe), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.closeIframe), driver);
		NightAudit.closeIframe.click();
		NightAuditLogger.info("Close icon clicked");
		testSteps.add("Close icon clicked");		
		
	}

	public void clickPrintIcon(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);		
		Wait.WaitForElement(driver, OR.printIcon);
		Wait.waitForElementToBeVisibile(By.xpath(OR.printIcon), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.printIcon), driver);
		NightAudit.printIcon.click();
		NightAuditLogger.info("print icon clicked");
		testSteps.add("print icon clicked");		

	}

	public void verifyWeekStartDay(WebDriver driver, String Day, ArrayList<String> testSteps) throws InterruptedException, ParseException {

		// TODO Auto-generated method stub
		NightAuditLogger.info("Day : " + Day);
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement(driver, OR.AuditDatePicker);
		Utility.ScrollToElement(NightAudit.AuditDatePicker, driver);
		NightAudit.AuditDatePicker.click();
		NightAuditLogger.info("Click on date picker icon");
		Wait.explicit_wait_visibilityof_webelement(NightAudit.weekDay.get(0), driver);
		String getDay= NightAudit.weekDay.get(0).getText().trim();
		NightAuditLogger.info("Expected : " + Day.substring(0, 2));
		testSteps.add("Expected : " + Day.substring(0, 2));
		
		NightAuditLogger.info("Found : " + getDay);
		testSteps.add("Found : " + getDay);
		assertEquals(getDay, Day.substring(0, 2), "Failed : Day not matched");
		testSteps.add("Verifed week start day is '" + Day + "'");
		NightAuditLogger.info("Verifed week start day is '" + Day + "'");
	}

	
	public void verifyCalenderIcon(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		testSteps.add("===== Verified calender icon is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified calender icon is displaying in night audit====".toUpperCase());
		
		Wait.WaitForElement(driver, OR.AuditDatePicker);
		Wait.waitForElementToBeVisibile(By.xpath(OR.AuditDatePicker), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.AuditDatePicker), driver);
		assertTrue(NightAudit.AuditDatePicker.isDisplayed(), "Failed : Night audit calender icon is not dipslaying");
		
		testSteps.add("Verified calender icon is displaying in night audit");
		NightAuditLogger.info("Verified calender icon is displaying in night audit");
	}

	public void verifyGoButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified Go button is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified Go button is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.Go_ButtonClick);
		Wait.waitForElementToBeVisibile(By.id(OR.Go_ButtonClick), driver);
		Wait.waitForElementToBeClickable(By.id(OR.Go_ButtonClick), driver);
		assertTrue(NightAudit.Go_ButtonClick.isDisplayed(), "Failed : Night audit Go button is not dipslaying");
		
		testSteps.add("Verified Go button is displaying in night audit");
		NightAuditLogger.info("Verified Go button is displaying in night audit");

	}

	public void verifyPeriodIsOpenButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified 'Period is Open' button is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'Period is Open' button is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.PeriodIsOpenButton);
		Wait.waitForElementToBeVisibile(By.id(OR.PeriodIsOpenButton), driver);
		Wait.waitForElementToBeClickable(By.id(OR.PeriodIsOpenButton), driver);
		assertTrue(NightAudit.PeriodIsOpenButton.isDisplayed(), "Failed : Night audit 'Period is Open' button is not dipslaying");
		
		testSteps.add("Verified 'Period is Open' button is displaying in night audit");
		NightAuditLogger.info("Verified 'Period is Open' button is displaying in night audit");

	}
	
	public void verifyDailyTransactionButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified 'Daily Transactions' button is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'Daily Transactions' button is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.DailyTransactionButton);
		Wait.waitForElementToBeVisibile(By.id(OR.DailyTransactionButton), driver);
		Wait.waitForElementToBeClickable(By.id(OR.DailyTransactionButton), driver);
		assertTrue(NightAudit.DailyTransactionButton.isDisplayed(), "Failed : Night audit 'Daily Transactions' button is not dipslaying");
		
		testSteps.add("Verified 'Daily Transactions' button is displaying in night audit");
		NightAuditLogger.info("Verified 'Daily Transactions' button is displaying in night audit");
	}

	public void verifyHouseKeepingButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified 'House Keeping' button is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'House Keeping' button is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.houseKeeping);
		Wait.waitForElementToBeVisibile(By.id(OR.houseKeeping), driver);
		Wait.waitForElementToBeClickable(By.id(OR.houseKeeping), driver);
		assertTrue(NightAudit.houseKeeping.isDisplayed(), "Failed : Night audit 'House Keeping' button is not dipslaying");
		
		testSteps.add("Verified 'House Keeping' button is displaying in night audit");
		NightAuditLogger.info("Verified 'House Keeping' button is displaying in night audit");
	}

	public void verifyHouseKeepingSetNowButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified 'Set Now' button With House Keeping is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'Set Now' button With House Keeping is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.houseKeepingSetNow);
		Wait.waitForElementToBeVisibile(By.id(OR.houseKeepingSetNow), driver);
		Wait.waitForElementToBeClickable(By.id(OR.houseKeepingSetNow), driver);
		assertTrue(NightAudit.houseKeepingSetNow.isDisplayed(), "Failed : Night audit 'Set Now' button With House Keeping is not dipslaying");
		
		testSteps.add("Verified 'Set Now' button With House Keeping is displaying in night audit");
		NightAuditLogger.info("Verified 'Set Now' button With House Keeping is displaying in night audit");
	}

	public void verifyLongStayButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified 'Long Stay' button is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'Long Stay' button is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.longStay);
		Wait.waitForElementToBeVisibile(By.id(OR.longStay), driver);
		Wait.waitForElementToBeClickable(By.id(OR.longStay), driver);
		assertTrue(NightAudit.longStay.isDisplayed(), "Failed : Night audit 'Long Stay' button is not dipslaying");
		
		testSteps.add("Verified 'Long Stay' button is displaying in night audit");
		NightAuditLogger.info("Verified 'Long Stay' button is displaying in night audit");
	}

	public void verifyLongStaySetNowButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified 'Set Now' button With Long Stay is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'Set Now' button With Long Stay is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement_ID(driver, OR.longStaySetNow);
		Wait.waitForElementToBeVisibile(By.id(OR.longStaySetNow), driver);
		assertTrue(NightAudit.longStaySetNow.isDisplayed(), "Failed : Night audit 'Set Now' button With Long Stay is not dipslaying");

		testSteps.add("Verified 'Set Now' button With Long Stay is displaying in night audit");
		NightAuditLogger.info("Verified 'Set Now' button With Long Stay is displaying in night audit");

		testSteps.add("===== Verified 'Set Now' button With Long Stay is not enabled in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'Set Now' button With Long Stay is not enabled in night audit====".toUpperCase());

		assertTrue(!NightAudit.longStaySetNow.isEnabled(), "Failed : Night audit 'Set Now' button With Long Stay is not not enabled");
		testSteps.add("Verified 'Set Now' button With Long Stay is not enabled in night audit");
		NightAuditLogger.info("Verified 'Set Now' button With Long Stay is not enabled in night audit");

}

	public void verifyAuditDateLabel(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("===== Verified 'Audit Date:' label is displaying in night audit =====".toUpperCase());
		NightAuditLogger.info("===== Verified 'Audit Date:' label is displaying in night audit====".toUpperCase());
		
		Elements_NightAudit NightAudit = new Elements_NightAudit(driver);
		Wait.WaitForElement(driver, OR.aduitDateLabel);
		Wait.waitForElementToBeVisibile(By.xpath(OR.aduitDateLabel), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.aduitDateLabel), driver);
		assertTrue(NightAudit.aduitDateLabel.isDisplayed(), "Failed : Night audit 'Audit Date:' Label is not dipslaying");
		
		testSteps.add("Verified 'Audit Date:' Label is displaying in night audit");
		NightAuditLogger.info("Verified 'Audit Date:' Label is displaying in night audit");
	}
	
}
