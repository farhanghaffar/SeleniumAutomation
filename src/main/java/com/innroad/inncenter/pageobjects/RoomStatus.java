package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.innroad.inncenter.interfaces.IRoomStatus;
import com.innroad.inncenter.pages.RoomStatusPage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_GuestServices;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;
import com.innroad.inncenter.webelements.WebElementsRoomStatus;
import com.innroad.inncenter.webelements.WebElements_RoomStatus;

public class RoomStatus implements IRoomStatus {

	public static Logger RoomStatusLogger = Logger.getLogger("RoomStatus");
	public int OccupiedNo = 0, VacantNo = 0;
    public ArrayList<String> obtainedList ;
	@Override
	public void SearchRoomZoonOccupancy(WebDriver driver, String Value) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		System.out.println("heere" + driver.findElement(By.xpath("//li[@class='blue active']//span")).getText());
		if (!driver.findElement(By.xpath("//li[@class='blue active']//span")).getText().equals("0")) {
			driver.findElements(By.cssSelector(".form-control.gs-input.ng-untouched.ng-pristine.ng-valid")).get(1)
					.sendKeys(Value);
			roomstatus.RoomStatus_SearchButon.click();
			assertTrue(driver.findElement(By.cssSelector(".col-sm-12.mobPadLR-0")).isDisplayed(),
					"Failed:Searched didn't work");
		}
	}

	@Override
	public void VerifyQuickStats(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);

		assertTrue(roomstatus.VerifyRoomStats_Dirty.isDisplayed(), "Failed: Dirty Stats not Displayed");
		assertTrue(roomstatus.VerifyRoomStats_Clean.isDisplayed(), "Failed: Clean Stats not Displayed");
		assertTrue(roomstatus.VerifyRoomStats_Inspection.isDisplayed(), "Failed: Inspection Stats not Displayed");
		assertTrue(roomstatus.VerifyRoomStats_OutofOrder.isDisplayed(), "Failed: OutofOrder Stats not Displayed");
		assertTrue(roomstatus.VerifyRoomStats_All.isDisplayed(), "Failed: All Stats not Displayed");

	}

	@Override
	public void VerifySortFuntionality(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton.click();
		assertTrue(roomstatus.VerifySortFunc_RoomNum.isDisplayed(), "Failed: Room# not displayed");
		assertTrue(roomstatus.VerifySortFunc_Zone.isDisplayed(), "Failed: Zone not displayed");
		assertTrue(roomstatus.VerifySortFunc_ArrivalDue.isDisplayed(), "Failed: Arrival Due not displayed");
	}

	
	public void verifySortFuntionality(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.WaitForElement(driver, OR.VerifySortFuncButton1);
		roomstatus.VerifySortFuncButton1.click();
		assertTrue(roomstatus.VerifySortFunc_RoomNum.isDisplayed(), "Failed: Room# not displayed");
		testSteps.add("Displayed Room #");
		RoomStatusLogger.info("Displayed Room #");
		assertTrue(roomstatus.VerifySortFunc_Zone.isDisplayed(), "Failed: Zone not displayed");
		testSteps.add("Displayed Zone");
		RoomStatusLogger.info("Displayed Zone");
		assertTrue(roomstatus.VerifySortFunc_ArrivalDue.isDisplayed(), "Failed: Arrival Due not displayed");
		testSteps.add("Displayed Arrival Due");
		RoomStatusLogger.info("Displayed Arrival Due");
	}

	public void verifySortFuntionality1(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton1.click();
		assertTrue(roomstatus.VerifySortFunc_RoomNum.isDisplayed(), "Failed: Room# not displayed");
		assertTrue(roomstatus.VerifySortFunc_Zone.isDisplayed(), "Failed: Zone not displayed");
		assertTrue(roomstatus.VerifySortFunc_ArrivalDue.isDisplayed(), "Failed: Arrival Due not displayed");
	}

	public void SortByZone(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton.click();
		Thread.sleep(10000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.VerifySortFunc_Zone);

	}

	public void SortByArrivalDue1(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton1.click();
		Thread.sleep(10000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.VerifySortFunc_ArrivalDue);
	}

	public void SortByRoomNum1(WebDriver driver) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton1.click();
		Thread.sleep(10000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.VerifySortFunc_RoomNum);

	}

	public void SortByZone1(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton1.click();
		Thread.sleep(10000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.VerifySortFunc_Zone);

	}

	@Override
	public void SortByArrivalDue(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton.click();
		Thread.sleep(10000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.VerifySortFunc_ArrivalDue);
	}

	@Override
	public void SortByRoomNum(WebDriver driver) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.VerifySortFuncButton.click();
		Thread.sleep(10000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.VerifySortFunc_RoomNum);

	}

	@Override
	public void MouserHoverOverElemet(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);

		WebElement ele = driver.findElement(By.xpath("//div[contains(@data-sortorder,'1')]"));
		// Create object 'action' of an Actions class
		Actions action = new Actions(driver);
		// Mouseover on an element
		action.moveToElement(ele).perform();

		// assertTrue(roomstatus.RoomStatus_RoomDirtyLabel.isDisplayed(),
		// "Failed: Diry/Clean DropDown is not displayed");
		// assertTrue(roomstatus.RoomStatus_RoomTaskLabel.isDisplayed(),
		// "Failed: Task DropDown is not displayed");

		GetUpdateStatus_States(driver, "Dirty");

	}

	@Override
	public void ClickCleanDropDownButton(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.RoomStatus_RoomDirtyLabelDropDownButton.click();
		assertTrue(roomstatus.RoomStatus_RoomDirtyLabelButton_DirtyCate.isDisplayed(),
				"Failed: Dirty DropDown ColorName is not displayed");
		assertTrue(roomstatus.RoomStatus_RoomDirtyLabelButton_CleanCate.isDisplayed(),
				"Failed: Clean DropDown ColorName is not displayed");
		// assertTrue(roomstatus.RoomStatus_RoomDirtyLabelButton_InspectionCate.isDisplayed(),
		// "Failed: Inspection DropDown ColorName is not displayed");

	}

	@Override
	public void SelectOneRoomStatusCategory(WebDriver driver, String ColorName) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		try {
			if (ColorName.equals("Clean") || ColorName.equalsIgnoreCase("clean")) {
				roomstatus.RoomStatus_RoomDirtyLabelButton_CleanCate.click();
				Wait.wait5Second();
				assertTrue(
						roomstatus.RoomStatus_Element.getCssValue("background-color").equals("green")
								|| roomstatus.RoomStatus_Element.getCssValue("background-color").equals("Green"),
						"Failed: Specified  Color is not correct");

			} else if (ColorName.equals("Dirty") || ColorName.equalsIgnoreCase("dirty")) {
				roomstatus.RoomStatus_RoomDirtyLabelButton_DirtyCate.click();
				Wait.wait5Second();
				assertTrue(
						roomstatus.RoomStatus_Element.getCssValue("background-color").equals("red")
								| roomstatus.RoomStatus_Element.getCssValue("background-color").equals("Red"),
						"Failed: Specified  Color is not correct");
			}

			else if (ColorName.equals("Inspection") || ColorName.equalsIgnoreCase("inspection")) {
				roomstatus.RoomStatus_RoomDirtyLabelButton_InspectionCate.click();
				Wait.wait5Second();
				assertTrue(
						roomstatus.RoomStatus_Element.getCssValue("background-color").equals("orange")
								|| roomstatus.RoomStatus_Element.getCssValue("background-color").equals("Orange"),
						"Failed: Specified  Color is not correct");
			}
		} catch (Exception e) {
			Assert.assertTrue(false);

		} catch (Error e) {
			Assert.assertTrue(false);
		}

	}

	@Override
	public void ClickFirstElementRadioButton(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Actions action = new Actions(driver);
		// Mouseover on an element
		action.moveToElement(roomstatus.RoomStatus_Room_RadioButton).perform();
		assertTrue(roomstatus.RoomStatus_Room_RadioButton.isEnabled(), "Failed: Radio Button is not Displayed");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.RoomStatus_Room_RadioButton);

	}

	public void verifyUpdateStatusButtonEnable(WebDriver driver) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Utility.ElementFinderUntillNotShow(By.xpath(OR.UpdateStatusButton), driver);
		assertTrue(roomstatus.UpdateStatusButton.isEnabled(), "Failed: Update Status Button is Enabled");
	}
	@Override
	public void ClickUpdateStatusButton(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		try {

			Utility.ElementFinderUntillNotShow(By.xpath(OR.UpdateStatusButton), driver);
			roomstatus.UpdateStatusButton.click();
		} catch (Exception e) {

			Utility.ElementFinderUntillNotShow(By.xpath(OR.UpdateStatusButton1), driver);
			roomstatus.UpdateStatusButton1.click();
		}
		assertTrue(roomstatus.UpdateStatus_Clean.isDisplayed(), "Failed: Clean Button is not Displayed");
		assertTrue(roomstatus.UpdateStatus_Dirty.isDisplayed(), "Failed: Dirty Button is not Displayed");
		// assertTrue(roomstatus.UpdateStatus_Inspection.isDisplayed(), "Failed:
		// Inspection Button is not Displayed");

	}

	@Override
	public void ClickUpdateStatus_CleanButton(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		// roomstatus.UpdateStatusButton.click();
		// Thread.sleep(10000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.UpdateStatus_Dirty);

		// MouserHoverOverElemet(driver);
		// assertTrue(roomstatus.RoomStatus_RoomDirtyLabelButton_DirtyCate.isDisplayed(),
		// "Failed: Dirty DropDown ColorName is not displayed");
		// assertTrue(roomstatus.UpdateStatus_Dirty.isDisplayed(), "Failed:
		// Clean Button is not Displayed");

	}

	static String StatsBefore = "";
	static String StatsAfter = "";

	public void updateStatusAndCategoryStatsVerification(WebDriver driver) throws InterruptedException {

		StatsBefore = GetUpdateStatus_States(driver, "Dirty");
		Wait.wait10Second();
		StatsAfter = GetUpdateStatus_States(driver, "Dirty");
		assertTrue(StatsBefore != StatsAfter, "Failed Dirty Stats not Updated");

	}

	public String GetUpdateStatus_States(WebDriver driver, String Category) throws InterruptedException {
		if (Category.equals("Dirty")) {
			String DirtyStats = driver.findElement(By.className("color-red")).getText();
			return DirtyStats;
		} else if (Category.equals("Clean")) {
			String CleanStats = driver.findElement(By.className("color-green")).getText();
			return CleanStats;
		} else if (Category.equals("Inspection")) {
			String InspectionStats = driver.findElement(By.className("color-orange")).getText();
			return InspectionStats;
		}

		return "";
	}

	public void Reports(WebDriver driver) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_xpath(OR.Report, driver);
		roomstatus.Report.click();
		assertTrue(driver.findElement(By.xpath(OR.Report_RoomStatus)).isDisplayed(),
				"Failed: Room Status does not exist");
		assertTrue(driver.findElement(By.xpath(OR.Report_RoomStatusTask)).isDisplayed(),
				"Failed: Room Status with Task does not exist");
		RoomStatusLogger.info("Click Reports");

	}

	public void clickreportRoomStatus(WebDriver driver) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_xpath(OR.Report_RoomStatus, driver);
		roomstatus.Report_RoomStatus.click();
		Wait.wait3Second();
		RoomStatusLogger.info("Click Room Status");
	}
	public void Report_RoomStatus(WebDriver driver) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_xpath(OR.Report_RoomStatus, driver);
		roomstatus.Report_RoomStatus.click();
		Wait.wait3Second();
		RoomStatusLogger.info("Click Room Status");
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		RoomStatusLogger.info("Switch to report tab");

		VerifyLoading(driver);

		try {
			Wait.WaitForElement(driver, OR.ReportPage);
			Wait.waitForElementToBeVisibile(By.xpath(OR.ReportPage), driver);

			assertTrue(driver.findElement(By.xpath(OR.ReportPage)).isDisplayed(), "Failed: Room Status Report Page");
			assertTrue(driver.findElement(By.xpath(OR.ReportPage_Table)).isDisplayed(), "Failed: Room Status Report");
			RoomStatusLogger.info("Report: Room Status ");
		} catch (Exception e) {
			// TODO: handle exception
		}

		driver.close();
		RoomStatusLogger.info("Close Tab");
		driver.switchTo().window(tabs2.get(0));
		Wait.explicit_wait_xpath(OR.RoomStatusPage, driver);
		RoomStatusLogger.info("Room Status Page");

	}

	public void switchToNextTab(WebDriver driver) {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		RoomStatusLogger.info("Switch to report tab");
	}
	
	public void closeCurrentWindow(WebDriver driver) {
		driver.close();
		RoomStatusLogger.info("Close Current Window");
	}
	
	public void switchToParentWindowTab(WebDriver driver) {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(0));
		RoomStatusLogger.info("Switch to main tab");
	}
	
	public void reportRoomStatusWithTask(WebDriver driver) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_xpath(OR.Report_RoomStatusTask, driver);
		roomstatus.Report_RoomStatusTask.click();
		Wait.wait2Second();
		RoomStatusLogger.info("Click Room Status with Task");
	}
	public void Report_RoomStatusWithTask(WebDriver driver) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_xpath(OR.Report_RoomStatusTask, driver);
		roomstatus.Report_RoomStatusTask.click();
		Wait.wait2Second();
		RoomStatusLogger.info("Click Room Status with Task");
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		RoomStatusLogger.info("Switch to report tab");
		Wait.wait2Second();

		VerifyLoading(driver);

		Wait.WaitForElement(driver, OR.ReportPage);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ReportPage), driver);

		assertTrue(driver.findElement(By.xpath(OR.ReportPage)).isDisplayed(),
				"Failed: Room Status with Task Report Page");
		assertTrue(driver.findElement(By.xpath(OR.ReportPage_Table)).isDisplayed(),
				"Failed: Room Status with Task Report");
		RoomStatusLogger.info("Report: Room Status with Task");
		driver.close();
		RoomStatusLogger.info("Close Tab");
		driver.switchTo().window(tabs2.get(0));
		Wait.explicit_wait_xpath(OR.RoomStatusPage, driver);
		RoomStatusLogger.info("Room Status Page");

	}

	public void VerifyInspectionQuickStats(WebDriver driver, boolean inspectionstatus) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		if (inspectionstatus)
			assertTrue(roomstatus.VerifyRoomStats_Inspection.isDisplayed(), "Failed: Inspection Stats not Displayed");
		// else
		// assertTrue(!roomstatus.VerifyRoomStats_Inspection.isDisplayed(),
		// "Failed: Inspection Stats Displayed");
	}

	public void ClickStatesRoomStatus(WebDriver driver, String RoomStatus) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		if (RoomStatus.equals("Clean") || RoomStatus.equalsIgnoreCase("clean")) {
			roomstatus.VerifyRoomStats_Clean.click();
			Wait.wait5Second();
			String one = roomstatus.VerifyRoomStats_Clean.getCssValue("color");
			System.out.println("Color" + one);
			String Path = "(//div[contains(@data-sortorder,'1')])[1]/div";
			// System.out.println("Room Color : " +
			// driver.findElement(By.xpath(Path)).getCssValue("background-color"));
			assertTrue(driver.findElement(By.xpath(Path)).getCssValue("background-color").equals(one),
					"Failed: Specified  Color is not correct");

		} else if (RoomStatus.equals("Dirty") || RoomStatus.equalsIgnoreCase("dirty")) {

			roomstatus.VerifyRoomStats_Dirty.click();
			Wait.wait5Second();
			String one = roomstatus.VerifyRoomStats_Dirty.getCssValue("color");
			System.out.println("Color" + one);
			String Path = "(//div[contains(@data-sortorder,'1')])[1]/div";
			// System.out.println("Room Color : " +
			// driver.findElement(By.xpath(Path)).getCssValue("background-color"));
			assertTrue(driver.findElement(By.xpath(Path)).getCssValue("background-color").equals(one),
					"Failed: Specified  Color is not correct");
		}

		else if (RoomStatus.equals("Inspection") || RoomStatus.equalsIgnoreCase("inspection")) {
			roomstatus.VerifyRoomStats_Inspection.click();
			Wait.wait5Second();
			String one = roomstatus.VerifyRoomStats_Inspection.getCssValue("color");
			System.out.println("Color" + one);
			String Path = "(//div[contains(@data-sortorder,'1')])[1]/div";
			// System.out.println("Room Color : " +
			// driver.findElement(By.xpath(Path)).getCssValue("background-color"));
			assertTrue(driver.findElement(By.xpath(Path)).getCssValue("background-color").equals(one),
					"Failed: Specified  Color is not correct");
		}
	}

	public void VerifyRoomStatus(WebDriver driver, String RoomStatus, String RoomClassName, String RoomNumber,
			String RoomClassNumber) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		driver.navigate().refresh();
		Wait.wait3Second();
		String StatusPath = "//span[text()='" + RoomStatus + "']";
		WebElement Status = driver.findElement(By.xpath(StatusPath));
		Utility.ScrollToElement(Status, driver);
		Status.click();
		Wait.wait5Second();
		roomstatus.DirtyTab.click();
		Wait.wait5Second();
		String RoomPath = "";
		WebElement Room = null;
		try {
			RoomPath = "//div[@data-roomclass='" + RoomClassNumber + "'and @data-roomname='"
					+ RoomNumber.replaceAll("\\s+", "") + "' and @data-roomclassname='"
					+ RoomClassName.toLowerCase().replaceAll("\\s+", "") + "']";
			Room = driver.findElement(By.xpath(RoomPath));
		} catch (Exception e) {
			RoomPath = "//div[@data-roomname='" + RoomNumber.replaceAll("\\s+", "") + "' and @data-roomclassname='"
					+ RoomClassName.toLowerCase() + "']";
			Room = driver.findElement(By.xpath(RoomPath));
		}

		Utility.ScrollToElement(Room, driver);
		assertTrue(Room.isDisplayed(), "Failed : Room : " + RoomClassName + " " + RoomNumber + " is not " + RoomStatus);

	}

	public void MovetoRoom_ChangeStatus(WebDriver driver, String RoomStatus, String RoomClassName,
			String RoomClassNumber, String RoomNumber) throws InterruptedException {
		String RoomPath = "//span[text()='vacant']//ancestor::div[@data-roomclass='" + RoomClassNumber
				+ "' and @data-roomname='" + RoomNumber + "' and @data-roomclassname='" + RoomClassName.toLowerCase()
				+ "']";
		System.out.println(RoomPath);
		WebElement Room = driver.findElement(By.xpath(RoomPath));
		Utility.ScrollToElement(Room, driver);
		Actions builder = new Actions(driver);
		builder.moveToElement(Room).build().perform();
		WebDriverWait wait1 = new WebDriverWait(driver, 5);
		String StatusPath = "(//span[text()='vacant']//ancestor::div[@data-roomclass='" + RoomClassNumber
				+ "' and @data-roomname='" + RoomNumber + "' and @data-roomclassname='" + RoomClassName.toLowerCase()
				+ "'])[1]//following-sibling::i[contains(@class,'fa fa-angle')]";
		wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(StatusPath)));
		driver.findElement(By.xpath(StatusPath)).click();
		Wait.wait2Second();
		RoomNumber = Room.getAttribute("data-roomname");
		String Path = "((//span[text()='vacant']//ancestor::div[@data-roomclass='" + RoomClassNumber
				+ "' and @data-roomname='" + RoomNumber + "' and @data-roomclassname='" + RoomClassName.toLowerCase()
				+ "'])[1]//following-sibling::a[contains(text(),'" + RoomStatus + "')])[last()]";
		System.out.println(Path);
		driver.findElement(By.xpath(Path)).click();
		Wait.wait2Second();

	}

	public String MovetoRoom_ChangeStatus(WebDriver driver, String RoomStatus, String RoomClassName,
			String RoomClassNumber) throws InterruptedException {
		String RoomNumber = "";
		String RoomPath = "(//span[text()='vacant']//ancestor::div[@data-roomclass='" + RoomClassNumber
				+ "' and @data-roomclassname='" + RoomClassName.toLowerCase().replaceAll("\\s+", "") + "'])[1]";
		WebElement Room = driver.findElement(By.xpath(RoomPath));
		Actions builder = new Actions(driver);
		builder.moveToElement(Room).build().perform();
		WebDriverWait wait1 = new WebDriverWait(driver, 5);
		String StatusPath = "(//span[text()='vacant']//ancestor::div[@data-roomclassname='"
				+ RoomClassName.toLowerCase().replaceAll("\\s+", "")
				+ "'])[1]//following-sibling::i[contains(@class,'fa fa-angle')]";
		wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(StatusPath)));
		driver.findElement(By.xpath(StatusPath)).click();
		Wait.wait2Second();
		RoomNumber = Room.getAttribute("data-roomname");
		String Path = "(//span[text()='vacant']//ancestor::div[@data-roomclassname='"
				+ RoomClassName.toLowerCase().replaceAll("\\s+", "") + "'])[1]//following-sibling::";
		if (RoomStatus.contains("Clean")) {
			Path = Path + "li[3]/a";
		} else if (RoomStatus.contains("Inspection")) {
			Path = Path + "li[1]/a";
		} else if (RoomStatus.contains("Dirty")) {
			Path = Path + "li[2]/a";
		}
		driver.findElement(By.xpath(Path)).click();
		Wait.wait2Second();
		return RoomNumber;

	}

	public void verifyRoomStatusTabEnabled(WebDriver driver, ArrayList<String> test_steps) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.WaitForElement(driver, OR.RoomStatus);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatusTab, driver);
		assertTrue(roomstatus.RoomStatusTab.isEnabled(), " Failed: to verify RoomStatus Tab");
		test_steps.add("RoomStatus Tab Enabled");
		RoomStatusLogger.info("RoomStatus Tab Enabled");

	}

	public void verifiedGuestServicesDataLoadedOrNot(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.WaitForElement(driver, OR_GuestServices.RoomStatus_AllStatNumber);
		String getNumberofAllQuickStats = roomstatus.RoomStatus_AllQuickStatsNumber.getText();
		boolean isExist = !getNumberofAllQuickStats.equals("0");
		if (isExist) {
			test_steps.add("Successfully Data Loaded on Guest Services Screen");
			RoomStatusLogger.info("Successfully Data Loaded on Guest Services Screen");
		} else {	
			boolean isExist1=false;
			test_steps.add("Failed to Load Data on Guest Services Screen");
			RoomStatusLogger.info("Failed to Load Data on Guest Services Screen");
			Navigation nav = new Navigation();
			do {
			nav.navigateToReservationFromGuestServices(driver, test_steps);
			nav.navigateGuestservice(driver);
			 String getNumberofAllQuickStats1 = roomstatus.RoomStatus_AllQuickStatsNumber.getText();
			 isExist1 = !getNumberofAllQuickStats1.equals("0");
			}while(!isExist1);
			RoomStatusLogger.info("Exist from loop");
		}
	}

	public void verifySearchTestBoxPresented(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		assertTrue(roomstatus.RoomStatus_SearchField.isDisplayed(), "Failed:Search Text Box not displayed");
		// test_steps.add(" Search Text Box Displayed.");
		RoomStatusLogger.info("Search  Text Box Displayed.");
	}

	public void inputSearchAndClickSearchButton(WebDriver driver, String Value, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.WaitForElement(driver, OR.RoomStatus_SearchField);
		roomstatus.RoomStatus_SearchField.clear();
		test_steps.add("Clear Search Field.");
		RoomStatusLogger.info("Clear Search Field.");
		roomstatus.RoomStatus_SearchField.sendKeys(Value);
		test_steps.add("Input Search Value : " + "<b>" + Value + "</b>");
		RoomStatusLogger.info("Input Search Value : " + Value);
		Wait.waitForElementToBeClickable(By.id("searchsubmit"), driver, 5);
		Utility.ScrollToElement(roomstatus.RoomStatus_SearchButon, driver);
		Utility.clickThroughJavaScript(driver, roomstatus.RoomStatus_SearchButon);
		test_steps.add("Click Search Button");
		RoomStatusLogger.info("Click Search Button.");
		Wait.wait60Second();
		String getNumberofAllQuickStats = roomstatus.RoomStatus_AllQuickStatsNumber.getText();
		boolean isExist = !getNumberofAllQuickStats.equals("0");
		if (isExist) {
			test_steps.add("Successfully Data Loaded on Guest Services Screen");
			RoomStatusLogger.info("Successfully Data Loaded on Guest Services Screen");
		} else {	
			boolean isExist1=false;
			test_steps.add("Failed to Load Data on Guest Services Screen");
			RoomStatusLogger.info("Failed to Load Data on Guest Services Screen");
			Navigation nav = new Navigation();
			do {
			nav.navigateToReservationFromGuestServices(driver, test_steps);
			nav.navigateGuestservice(driver);
			 String getNumberofAllQuickStats1 = roomstatus.RoomStatus_AllQuickStatsNumber.getText();
			 isExist1 = !getNumberofAllQuickStats1.equals("0");
			 Wait.WaitForElement(driver, OR.RoomStatus_SearchField);
				roomstatus.RoomStatus_SearchField.clear();
				test_steps.add("Clear Search Field.");
				RoomStatusLogger.info("Clear Search Field.");
				roomstatus.RoomStatus_SearchField.sendKeys(Value);
				test_steps.add("Input Search Value : " + "<b>" + Value + "</b>");
				RoomStatusLogger.info("Input Search Value : " + Value);
				Wait.waitForElementToBeClickable(By.id("searchsubmit"), driver, 5);
				Utility.ScrollToElement(roomstatus.RoomStatus_SearchButon, driver);
				Utility.clickThroughJavaScript(driver, roomstatus.RoomStatus_SearchButon);
				test_steps.add("Click Search Button");
				RoomStatusLogger.info("Click Search Button.");
				Wait.wait60Second();
			}while(!isExist1);
			RoomStatusLogger.info("Exist from loop");
		}
		Wait.wait60Second();
	}

	public void searchByRoomHash(WebDriver driver, String RoomNO, String RoomName, ArrayList<String> test_steps)
			throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, RoomNO, test_steps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
		test_steps.add("Search  working.");
		RoomStatusLogger.info("Search  working.");
		assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
				(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
				"Failed:to Match Room Tile with Quick Stats Number");
		test_steps.add("Total All Quick Stats NO : <b>" + roomstatus.RoomStatus_RoomCardList.size()
				+ "</b> Matched with Total Room Tile NO : <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
				+ "</b>");
		RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
				+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());

		String path = "//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase() + "')][@data-roomname='"
				+ RoomNO + "']";
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed:Room Number Not Matched");
		test_steps.add("Room Number Matched <b>" + RoomNO + "</b>");
		RoomStatusLogger.info("Room Number Matched " + RoomNO);

	}

	public int getRoomCardSize(WebDriver driver) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		int size = roomstatus.RoomStatus_RoomCardList.size();
		System.out.println("Total Room Card Is: " + size);
		return size;
	}

	public void clickONRoomStatusForToday(WebDriver driver) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		roomstatus.RoomStatus_Today.click();
	}

	public void changeStatusMultipleRoom(WebDriver driver, ArrayList<String> test_steps, String status,
			int RoomTileSize, ArrayList<String> roomNo) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);

		for (int i = 0; i < RoomTileSize; i++) {
			WebElement ele = driver.findElement(By.xpath("//div[contains(@data-sortorder,'" + roomNo.get(i) + "')]"));
			// Create object 'action' of an Actions class
			Actions action = new Actions(driver);
			// Mouseover on an element
			action.moveToElement(ele).clickAndHold().perform();
			test_steps.add("Mouse Hover  Room Tile " + roomNo.get(i));
			RoomStatusLogger.info("Mouse Hover  Room Tile " + roomNo.get(i));
			Wait.WaitForElement(driver, OR.RoomStatus_RoomTileDropDownButton);
			roomstatus.RoomStatus_RoomTileDropDownButton.get(i).click();
			test_steps.add("Click on Room Tile Drop Down Box " + roomNo.get(i));
			RoomStatusLogger.info("Click on Room Tile Drop Down Box " + roomNo.get(i));
			String path = "//div[contains(@data-sortorder,'" + roomNo.get(i)
					+ "')]//div[@class='grid-content']//div[contains(@class,'dropdown')]//ul//li/a[contains(text(),'"
					+ status + "')]";
			WebElement element = driver.findElement(By.xpath(path));
			Wait.waitUntilPresenceOfElementLocated(path, driver);
			element.click();
			test_steps.add("Select " + "<b>" + status + "</b>" + " Status from Room Tile Drop Down Box ");
			RoomStatusLogger.info("Select " + status + " Status from Room Tile Drop Down Box ");
			verifyTosterMessageSingleRoom(driver, test_steps, roomNo.get(i));
			Wait.wait5Second();

		}
	}

	public List<String> getStatusofSingleRoomClass(WebDriver driver, ArrayList<String> testSteps, String roomName,
			String roomNO) {
		List<String> statusName = new ArrayList<String>();
		String path = "//room-card/div[(@data-roomclassname='" + roomName.toLowerCase() + "')][@data-roomname='"
				+ roomNO + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		String clickDropDown = "//room-card/div[(@data-roomclassname='" + roomName.toLowerCase()
				+ "')][@data-roomname='" + roomNO
				+ "']//div[@class='grid-content']//div[contains(@class,'dropdown')]/a/i";
		Wait.WaitForElement(driver, clickDropDown);
		WebElement elementDropDownBox = driver.findElement(By.xpath(clickDropDown));
		elementDropDownBox.click();
		String pathStatus = "//room-card/div[(@data-roomclassname='" + roomName.toLowerCase() + "')][@data-roomname='"
				+ roomNO + "']//div[@class='grid-content']//div[contains(@class,'dropdown')]//ul//li/a";
		Wait.waitUntilPresenceOfElementLocated(pathStatus, driver);
		List<WebElement> elementStatus = driver.findElements(By.xpath(pathStatus));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (WebElement str : elementStatus) {
			String value = (String) js.executeScript("return  arguments[0].text", str);
			statusName.add(value.trim());

		}

		return statusName;

	}

	public void searchByRoomHashAndChangeRoomStatus(WebDriver driver, String RoomNO, String RoomName, String Status,
			ArrayList<String> test_steps) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, RoomNO, test_steps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");

		test_steps.add("Search  working.");
		RoomStatusLogger.info("Search  working.");
		assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
				(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
				"Failed:to Match Room Tile with Quick Stats Number");
		test_steps.add("Total All Quick Stats NO : <b>" + roomstatus.RoomStatus_RoomCardList.size()
				+ "</b> Matched with Total Room Tile NO : <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
				+ "</b>");
		RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
				+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());
		String path = "//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase() + "')][@data-roomname='"
				+ RoomNO + "']";
		RoomStatusLogger.info(path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isEnabled(), "Failed:Room Number Not Matched with Status");
		RoomStatusLogger.info("Room Enabled");
		RoomStatusLogger.info("Verified Enable");
		Actions action = new Actions(driver);
		action.moveToElement(element).clickAndHold().perform();
		RoomStatusLogger.info("Click Toggle");
		String ClickDropDown = "//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase()
				+ "')][@data-roomname='" + RoomNO
				+ "']//div[@class='grid-content']//div[contains(@class,'dropdown')]/a/i";
		WebElement elementDropDownBox = driver.findElement(By.xpath(ClickDropDown));
		elementDropDownBox.click();
		RoomStatusLogger.info("Click Drop Down Box");
		String pathStatus = "//div[@class='grid-content']//div[contains(@class,'dropdown')]//ul//li/a[contains(text(),'"
				+ Status + "')]";
		WebElement elementStatus = driver.findElement(By.xpath(pathStatus));
		Wait.wait5Second();
		RoomStatusLogger.info("Waiting");		
		try {
		Utility.ScrollToElement(elementStatus, driver);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", elementStatus);
		}catch(Exception e) {
		Utility.scrollAndClick(driver, By.xpath(pathStatus));
		}
		RoomStatusLogger.info("Select Status");
		// elementStatus.click();
		test_steps.add("Change " + "<b> " + Status + "</b> Status" + "For Room and Room Class <b>" + RoomNO + " - "
				+ RoomName + "</b>");
		RoomStatusLogger.info("Change " + Status + "Status For Room " + RoomNO + " - " + RoomName);
		Utility.ScrollToElement(roomstatus.RoomStatus_Today, driver);
		action.moveToElement(roomstatus.RoomStatus_Today).perform();
		RoomStatusLogger.info("Action Performed");
		Wait.wait5Second();
		
		String path1 = "//room-card/div[(@data-roomclassname='"+RoomName.toLowerCase()+"')][@data-roomname='"+RoomNO+"' and contains(@class,'"+Status+"')]";
		boolean isExist=Utility.isElementPresent(driver, By.xpath(path1));
		if(!isExist) {
			action.moveToElement(element).clickAndHold().perform();
			RoomStatusLogger.info("Click Toggle");
			ClickDropDown = "//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase()
					+ "')][@data-roomname='" + RoomNO
					+ "']//div[@class='grid-content']//div[contains(@class,'dropdown')]/a/i";
			 elementDropDownBox = driver.findElement(By.xpath(ClickDropDown));
			elementDropDownBox.click();
			RoomStatusLogger.info("Click Drop Down Box");
			 pathStatus = "//div[@class='grid-content']//div[contains(@class,'dropdown')]//ul//li/a[contains(text(),'"
					+ Status + "')]";
			 elementStatus = driver.findElement(By.xpath(pathStatus));
			Wait.wait5Second();
			RoomStatusLogger.info("Waiting");		
			try {
			Utility.ScrollToElement(elementStatus, driver);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementStatus);
			}catch(Exception e) {
			Utility.scrollAndClick(driver, By.xpath(pathStatus));
			}
			RoomStatusLogger.info("Select Status");
			// elementStatus.click();
			test_steps.add("Change " + "<b> " + Status + "</b> Status" + "For Room and Room Class <b>" + RoomNO + " - "
					+ RoomName + "</b>");
			RoomStatusLogger.info("Change " + Status + "Status For Room " + RoomNO + " - " + RoomName);
			Utility.ScrollToElement(roomstatus.RoomStatus_Today, driver);
			action.moveToElement(roomstatus.RoomStatus_Today).perform();
			RoomStatusLogger.info("Action Performed");
			Wait.wait5Second();
		}
		Wait.wait5Second();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRoomStatusWithSpecificRoomNo> ' Description:
	 * <Verified Room Status Like Out of Order, Clean, Dirty,Inspection and All
	 * for Specific Room No> ' Input parameters: < String Status like Out of
	 * Order, Clean, Dirty, Inspection and All> ' Return value: <array list> '
	 * Created Room No and Room Class By: <Gangotri Sikheria> ' Created On:
	 * <05/04/2020>
	 * 
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <mm/dd/yyyy>:<Developer name>:
	 * 1.Step modified 2.Step modified
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyRoomStatusWithSpecificRoomNo(WebDriver driver, String RoomNO, String RoomName, String Status,
			ArrayList<String> test_steps) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String OOTpath = "//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase() + "')][@data-roomname='"
				+ RoomNO + "']//span[(text()='" + roomstatus.OOOTab.getText().toLowerCase() + "')]";
		if (roomstatus.OOOTab.getText().toLowerCase().trim().equals(Status.toLowerCase().trim())) {
			Wait.WaitForElement(driver, OOTpath);
			WebElement ooTElement = driver.findElement(By.xpath(OOTpath));
			// Verified Out of Order Quick Stats
			assertTrue(
					roomstatus.RoomStatus_OutOfOrderStatSize.getText()
							.equals(Integer.toString(roomstatus.RoomStatus_OORRoomCard.size())),
					"Failed:to Match Room Tile with Quick Stats Out Of Order");
			test_steps.add("Room Class : <b> " + RoomName + "</b> Room No: <b> " + RoomNO + "</b> is Out of Order :");
			RoomStatusLogger.info("Room Class :  " + RoomName + " Room No: <b> " + RoomNO + " is Out of Order :");
			// Verified All Quick Stats
			assertTrue(
					roomstatus.RoomStatus_AllQuickStatsNumber.getText()
							.equals(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
					"Failed:to Match Room Tile with Quick Stats Out Of Order");
			test_steps.add("Total All No Is: <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
					+ "</b> Total Room Tile No Is : <b>" + roomstatus.RoomStatus_RoomCardList.size() + "</b>");
			RoomStatusLogger.info("Total All No Is: " + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
					+ " Total Room Tile No Is : " + roomstatus.RoomStatus_RoomCardList.size());

			// Verified Out of Order is Display or Not
			assertTrue(ooTElement.isDisplayed(), "Failed:to Verify Out of Order");
			test_steps.add("Verified" + "<b> " + Status + "</b> Status For Room and Room Class <b>" + RoomName + " - "
					+ RoomNO + "</b>");
			RoomStatusLogger.info("Verified " + Status + "Status For Room " + RoomNO + " - " + RoomName);

		}

	}

	public void clickRoomStatusTab(WebDriver driver) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Utility.ScrollToElement(roomstatus.RoomStatusTabOne, driver);
		roomstatus.RoomStatusTabOne.click();
		Wait.WaitForElement(driver, OR.RoomStatus);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatusTab, driver);

	}

	public void searchByRoom(WebDriver driver, String Value, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
		test_steps.add("Passed:Search  working.");
		RoomStatusLogger.info("Passed:Search  working.");
		assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
				(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
				"Failed:to Match Room Tile with Quick Stats Number");
		test_steps.add("Total All Quick Stats NO : <b>" + roomstatus.RoomStatus_RoomCardList.size()
				+ "</b> Matched with Total Room Tile NO : <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
				+ "</b>");
		RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
				+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());

		WebElement element = driver
				.findElement(By.xpath("//room-card/div[contains(@data-roomclassname,'" + Value.toLowerCase() + "')]"));
		assertTrue(element.isDisplayed(), "Rooms not available");
		test_steps.add("Verified Rooms: <b>" + Value + " </b> ");
		RoomStatusLogger.info("  Verified Rooms: " + Value);

	}

	public void roomTileVerifyColor(WebDriver driver, String RoomStatus, ArrayList<String> test_steps) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String ColorName = null, backgroundColor = null;
		if (RoomStatus.equals("Clean") || RoomStatus.equalsIgnoreCase("clean")) {

			ColorName = roomstatus.RoomStatus_CleanStatSize.getCssValue("color");
			RoomStatusLogger.info("Get Color :" + ColorName);
			backgroundColor = verifyRoomColor(roomstatus.RoomStatus_CleanRoomCardS, ColorName);

		} else if (RoomStatus.equals("Dirty") || RoomStatus.equalsIgnoreCase("dirty")) {
			ColorName = roomstatus.RoomStatus_DirtyStatSize.getCssValue("color");
			RoomStatusLogger.info("Get Color :" + ColorName);
			backgroundColor = verifyRoomColor(roomstatus.RoomStatus_DirtyStatTotalRoomCardStatus, ColorName);
		} else if (RoomStatus.equals("Inspection") || RoomStatus.equalsIgnoreCase("inspection")) {
			ColorName = roomstatus.RoomStatus_InspectionStatSize.getCssValue("color");
			RoomStatusLogger.info("Get Color :" + ColorName);
			backgroundColor = verifyRoomColor(roomstatus.RoomStatus_InspectionRoomCardS, ColorName);
		} else if (RoomStatus.equals("Out of Order") || RoomStatus.equalsIgnoreCase("out of order")) {
			ColorName = roomstatus.RoomStatus_OutOfOrderStatSize.getCssValue("color");
			RoomStatusLogger.info("Get Color :" + ColorName);
			backgroundColor = verifyRoomColor(roomstatus.RoomStatus_OORRoomCardS, ColorName);
		}

		test_steps.add(RoomStatus + " Quick Status Color " + "<b>" + ColorName + "</b>" + "Matched With"
				+ " Room Tiles BackGround Color " + "<b>" + backgroundColor + "</b>");
		RoomStatusLogger.info(RoomStatus + " Quick Status Color " + "<b>" + ColorName + "</b>" + "Matched With"
				+ " Room Tiles BackGround Color " + "<b>" + backgroundColor + "</b>");

	}

	public void clickRoomTileRadioButton(WebDriver driver, ArrayList<String> test_steps, int roomTileSize) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		for (int i = 0; i < roomTileSize; i++) {
			Actions action = new Actions(driver);
			// Mouseover on an element
			action.moveToElement(roomstatus.RoomStatus_RoomTile_RadioButtons.get(i)).perform();
			assertTrue(roomstatus.RoomStatus_RoomTile_RadioButtons.get(i).isEnabled(),
					"Failed: Radio Button is not Displayed");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", roomstatus.RoomStatus_RoomTile_RadioButtons.get(i));
			test_steps.add("Click on All Room Tile Radio Button " + i);
			RoomStatusLogger.info("Click on Room Tile Radio Button " + i);
		}

	}

	public String verifyTosterMessageSingleRoom(WebDriver driver, ArrayList<String> test_steps,
			String itemToBeUpdated) {
		String message = null;
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String messageText = itemToBeUpdated + " has been updated.";
		RoomStatusLogger.info(messageText);
		RoomStatusLogger.info(roomstatus.tosterMsg.getText());
		assertEquals(roomstatus.tosterMsg.getText().toLowerCase().trim(), (messageText.toLowerCase().trim()),
				"Failed: To verify Message task to be updated");
		test_steps.add("Verified : <b>" + messageText + "</b>");
		RoomStatusLogger.info("Verified :" + messageText);
		return message;
	}

	public String verifyTosterMessageBulkUpdate(WebDriver driver, ArrayList<String> test_steps,
			String itemToBeUpdated) {
		String message = null;
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String messageText = itemToBeUpdated + " Room(s) have been updated.";
		RoomStatusLogger.info(messageText);
		RoomStatusLogger.info(roomstatus.tosterMsg.getText());
		assertEquals(roomstatus.tosterMsg.getText().toLowerCase().trim(), (messageText.toLowerCase().trim()),
				"Failed: To verify Message task to be updated");
		test_steps.add("Verified : <b>" + messageText + "</b>");
		RoomStatusLogger.info("Verified :" + messageText);
		return message;
	}

	public void searchByZone(WebDriver driver, String Value, ArrayList<String> test_steps) throws InterruptedException {

		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
		test_steps.add("Search  working.");
		RoomStatusLogger.info("Search  working.");

		assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
				(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
				"Failed:to Match Room Tile with Quick Stats Number");
		test_steps.add("Total All Quick Stats NO : <b>" + roomstatus.RoomStatus_RoomCardList.size()

				+ "</b> Matched with Total Room Tile NO : <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
				+ "</b>");
		RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
				+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());

		WebElement element = driver
				.findElement(By.xpath("//room-card/div[contains(@data-zone,'" + Value.toLowerCase() + "')]"));
		assertTrue(element.isDisplayed(), "Rooms not available as per search zone");
		test_steps.add("Verified Zone : <b>" + Value + " </b>");
		RoomStatusLogger.info(" Verified Zone : " + Value);
	}

	public void searchByVacantOccupied(WebDriver driver, String Value, ArrayList<String> test_steps)
			throws InterruptedException {

		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
		test_steps.add("Search  working.");
		RoomStatusLogger.info("Search  working.");

		assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
				(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
				"Failed:to Match Room Tile with Quick Stats Number");
		test_steps.add("Total All Quick Stats NO : <b>" + roomstatus.RoomStatus_RoomCardList.size()

				+ " </b>Matched with Total Room Tile NO : <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
				+ "</b>");
		RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
				+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());

		assertEquals(roomstatus.RoomStatus_Vacant.getText().toString().toLowerCase(), Value.toLowerCase(),
				"Faild : To Search By " + Value);
		test_steps.add(" Searchd by <b>" + Value + " </b>: Verified <b>" + Value + "</b> Rooms.");
		RoomStatusLogger.info("Searchd by " + Value + " : Verified " + Value + " Rooms.");
	}

	public boolean verifiedByArrivalDue(WebDriver driver, String Value, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		String getNumberofAllQuickStats = roomstatus.RoomStatus_AllQuickStatsNumber.getText();
		RoomStatusLogger.info(getNumberofAllQuickStats);
		boolean isExist = !getNumberofAllQuickStats.equals("0");

		if (isExist) {
			test_steps.add("<b>****Verify Arrival Due****</b>");
			Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
			assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
			test_steps.add("Search working.");
			RoomStatusLogger.info("Search working.");

			assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
					(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
					"Failed:to Match Room Tile with Quick Stats Number");
			test_steps.add("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
					+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());
			RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
					+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());

			assertEquals(roomstatus.RoomStatus_ArrivalDue.getText().toString().toLowerCase(), Value.toLowerCase(),
					"Rooms are not Vacant");
			test_steps.add("Searchd by <b>" + Value + "</b> : Verified <b>" + Value + "</b> Rooms.");
			RoomStatusLogger.info("Searchd by " + Value + " : Verified " + Value + " Rooms.");
		}
		return isExist;

	}

	public void searchByArrivalDue(WebDriver driver, String Value, ArrayList<String> test_steps)
			throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_Today, driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");

		test_steps.add("Search working.");
		RoomStatusLogger.info("Search working.");

		assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
				(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
				"Failed:to Match Room Tile with Quick Stats Number");
		test_steps.add("Total All Quick Stats NO : <b>" + roomstatus.RoomStatus_RoomCardList.size()
				+ "</b> Matched with Total Room Tile NO : <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
				+ "</b>");
		RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
				+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());

		assertEquals(roomstatus.RoomStatus_ArrivalDue.getText().toString().toLowerCase(), Value.toLowerCase(),
				"Failed : to Search Arrival Due");
		String dataArrivalDueValue = roomstatus.RoomStatus_RoomCardList.get(0).getAttribute("data-arrivaldue")
				.toString();
		assertTrue(dataArrivalDueValue.equals("0"), "Failed to Search Arrival Due Attribute Value");
		test_steps.add(" Searchd by <b>" + Value + " </b>: Verified <b>" + Value + "</b> Rooms.");
		RoomStatusLogger.info(" Searchd by " + Value + " : Verified " + Value + " Rooms.");

	}

	public boolean verifyByOccupied(WebDriver driver, String Value, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		String getNumberofAllQuickStats = roomstatus.RoomStatus_AllQuickStatsNumber.getText();
		RoomStatusLogger.info(getNumberofAllQuickStats);
		boolean isExist = !getNumberofAllQuickStats.equals("0");
		if (isExist) {
			test_steps.add("<b>****Verify Occupied****</b>");
			Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
			assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
			test_steps.add("Search  working.");
			RoomStatusLogger.info("Search  working.");
			assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
					(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
					"Failed:to Match Room Tile with Quick Stats Number");
			test_steps.add("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
					+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());
			RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
					+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());
			assertEquals(roomstatus.RoomStatus_Vacant.getText().toString().toLowerCase(), Value.toLowerCase(),
					"Rooms are not Vacant");
			test_steps.add(" Searchd by <b>" + Value + " :</b> Verified <b>" + Value + "</b> Rooms.");
			RoomStatusLogger.info(" Searchd by " + Value + " :Verified " + Value + " Rooms.");
		}
		return isExist;
	}

	public String clickQuickStatsAndVerifyColor(WebDriver driver, String RoomStatus, ArrayList<String> test_steps)
			throws InterruptedException {
		String Statscount = null;
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		if (RoomStatus.equals("Clean") || RoomStatus.equalsIgnoreCase("clean")) {

			roomstatus.VerifyRoomStats_Clean.click();
			// Wait.wait5Second();
			Wait.waitforPageLoad(5, driver);
			test_steps.add("Click on Clean Quick Stats");
			RoomStatusLogger.info("Click on Clean Quick Stats");
			Statscount = roomstatus.RoomStatus_CleanStatSize.getText();
			/*
			 * String one =
			 * roomstatus.RoomStatus_CleanStatSize.getCssValue("color");
			 * RoomStatusLogger.info("Get Color :" + one);
			 * VerifyRoomColor(roomstatus.RoomStatus_CleanRoomCardS, one);
			 * test_steps.add("Verified Color");
			 * RoomStatusLogger.info("Verified Color");
			 */

		} else if (RoomStatus.equals("Dirty") || RoomStatus.equalsIgnoreCase("dirty")) {

			roomstatus.VerifyRoomStats_Dirty.click();
			// Wait.wait5Second();
			Wait.waitforPageLoad(5, driver);
			test_steps.add("Click on Dirty Quick Stats");
			RoomStatusLogger.info("Click on Dirty Quick Stats");
			Statscount = roomstatus.RoomStatus_DirtyStatSize.getText();
			String one = roomstatus.RoomStatus_DirtyStatSize.getCssValue("color");
			RoomStatusLogger.info("Get Color :" + one);
			verifyRoomColor(roomstatus.RoomStatus_DirtyStatTotalRoomCardStatus, one);
			test_steps.add("Verified Color<b> " + one + "</b>");
			RoomStatusLogger.info("Verified Color " + one);
		}

		else if (RoomStatus.equals("Inspection") || RoomStatus.equalsIgnoreCase("inspection")) {

			roomstatus.VerifyRoomStats_Inspection.click();
			// Wait.wait5Second();
			Wait.waitforPageLoad(5, driver);
			test_steps.add("Click on Inspection Quick Stats");
			RoomStatusLogger.info("Click on Inspection Quick Stats");
			Statscount = roomstatus.RoomStatus_InspectionStatSize.getText();
			String one = roomstatus.RoomStatus_InspectionStatSize.getCssValue("color");
			RoomStatusLogger.info("Get Color :" + one);
			verifyRoomColor(roomstatus.RoomStatus_InspectionRoomCardS, one);
			test_steps.add("Verified Color<b> " + one + "</b>");
			RoomStatusLogger.info("Verified Color " + one);
		} else if (RoomStatus.equals("Out of Order") || RoomStatus.equalsIgnoreCase("out of order")) {
			roomstatus.VerifyRoomStats_OutofOrder.click();
			// Wait.wait5Second();
			Wait.waitforPageLoad(5, driver);
			test_steps.add("Click on Out of Order Quick Stats");
			RoomStatusLogger.info("Click on Out of Order Quick Stats");
			Statscount = roomstatus.RoomStatus_OutOfOrderStatSize.getText();
			/*
			 * String one =
			 * roomstatus.RoomStatus_OutOfOrderStatSize.getCssValue("color");
			 * RoomStatusLogger.info("Get Color :" + one);
			 * VerifyRoomColor(roomstatus.RoomStatus_OORRoomCardS, one);
			 * test_steps.add("Verified Color");
			 * RoomStatusLogger.info("Verified Color");
			 */
		}
		return Statscount;
	}

	public void verifyTotalRoomCard(WebDriver driver, ArrayList<String> test_steps, String RoomStatus,
			List<WebElement> RoomCardsNo, WebElement QuickStatsNo) throws InterruptedException {
		// TODO Auto-generated method stub
		int Actual = RoomCardsNo.size();
		int Expected = Integer.parseInt(QuickStatsNo.getText());
		assertEquals(Actual, Expected, "Total room card  is not matched with " + RoomStatus + "quick stats number ");
		test_steps.add("Total Room Card : " + RoomCardsNo.size() + " Matched with " + RoomStatus
				+ " Quick stats Number: " + QuickStatsNo.getText());
		RoomStatusLogger.info("Total Room Card : " + RoomCardsNo.size() + " Matched with " + RoomStatus
				+ " Quick stats Number: " + QuickStatsNo.getText());
		test_steps.add("Total " + QuickStatsNo.getText() + " Room Card Enabled");
		RoomStatusLogger.info("Total " + QuickStatsNo.getText() + " Room Card Enabled");
	}

	public void verifyAllStat(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_GuestServices.RoomStatus_AllTab), driver, 5);
		roomstatus.RoomStatus_AllTab.click();
		Wait.waitforPageLoad(5, driver);
		// Wait.wait25Second();
		test_steps.add("Click on All Quick stats");
		RoomStatusLogger.info("Click on All Quick stats");
		int DirtyNumber = Integer.parseInt(roomstatus.RoomStatus_DirtyStatSize.getText());
		RoomStatusLogger.info("Total Dirty Rooms " + DirtyNumber);
		int InspectiomnNumber = Integer.parseInt(roomstatus.RoomStatus_InspectionStatSize.getText());
		RoomStatusLogger.info("Total Inspection Rooms " + InspectiomnNumber);
		int CleanNumber = Integer.parseInt(roomstatus.RoomStatus_CleanStatSize.getText());
		RoomStatusLogger.info("Total Clean Rooms " + CleanNumber);
		int OutOfOrderNumber = Integer.parseInt(roomstatus.RoomStatus_OutOfOrderStatSize.getText());
		RoomStatusLogger.info("Total Out Of Order Rooms " + OutOfOrderNumber);
		int SumofAll = DirtyNumber + InspectiomnNumber + CleanNumber + OutOfOrderNumber;
		RoomStatusLogger.info("Sum of  Quick Stats " + SumofAll);
		int Actual = Integer.parseInt(roomstatus.RoomStatus_AllQuickStatsNumber.getText());
		RoomStatusLogger.info("Actual Quick Stat Number " + Actual);
		assertEquals(Actual, SumofAll, "Sum of Dirty + Inspection + Clean+ Out of Order stats are: " + SumofAll
				+ " should not Equal to All quick stat number " + Actual);
		test_steps.add("Sum of Dirty + Inspection + Clean + Out of Order stats are " + SumofAll
				+ " Equal to All quick stats  " + Actual);
		RoomStatusLogger.info("Sum of Dirty + Inspection + Clean+Out of Order stats number " + SumofAll
				+ " Equal to All quick stat number " + Actual);
		test_steps.add("Total " + roomstatus.RoomStatus_AllQuickStats.getText() + " Room Card Enabled");
		RoomStatusLogger.info("Total " + roomstatus.RoomStatus_AllQuickStats.getText() + " Room Card Enabled");
	}

	public boolean getQuickStatSize(WebDriver driver, String status) {
		boolean isExist = false;
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		if (status.equals("Inspection") || status.equalsIgnoreCase("inspection")) {
			String getNumberofAllQuickStats = roomstatus.RoomStatus_InspectionStatSize.getText();
			isExist = !getNumberofAllQuickStats.equals("0");
		} else if (status.equals("Clean") || status.equalsIgnoreCase("clean")) {
			String getNumberofAllQuickStats = roomstatus.RoomStatus_CleanStatSize.getText();
			isExist = !getNumberofAllQuickStats.equals("0");

		} else if (status.equals("Out of Order") || status.equalsIgnoreCase("out of order")) {
			String getNumberofAllQuickStats = roomstatus.RoomStatus_OutOfOrderStatSize.getText();
			isExist = !getNumberofAllQuickStats.equals("0");

		}

		return isExist;
	}

	private String verifyRoomColor(List<WebElement> element, String Value) {
		String backgroundColor = null;
		for (WebElement e : element) {

			backgroundColor = e.getCssValue("background-color");
			assertEquals(backgroundColor, (Value), "Failed: Specified  Color is not correct");

		}
		RoomStatusLogger.info("Verifid Color: " + backgroundColor);

		return backgroundColor;
	}

	public void searchByRoomHashVerifyRoomStatus(WebDriver driver, String Value, String RoomName, String DirtyStatus,
			String CleanStatus, String VacantStatus, String OccupiedStatus, ArrayList<String> test_steps)
			throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
		test_steps.add("Search  working.");
		RoomStatusLogger.info("Search  working.");
		assertEquals(roomstatus.RoomStatus_AllQuickStatsNumber.getText(),
				(Integer.toString(roomstatus.RoomStatus_RoomCardList.size())),
				"Failed:to Match Room Tile with Quick Stats Number");
		test_steps.add("Total All Quick Stats NO : <b>" + roomstatus.RoomStatus_RoomCardList.size()
				+ "</b> Matched with Total Room Tile NO : <b>" + roomstatus.RoomStatus_AllQuickStatsNumber.getText()
				+ "</b>");
		RoomStatusLogger.info("Total All Quick Stats NO : " + roomstatus.RoomStatus_RoomCardList.size()
				+ " Matched with Total Room Tile NO : " + roomstatus.RoomStatus_AllQuickStatsNumber.getText());
		String path = "//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase() + "')][@data-roomname='" + Value
				+ "']/..//div[contains(@class,'dropdown')]/a[contains(text(),'" + DirtyStatus
				+ "')]|//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase() + "')][@data-roomname='" + Value
				+ "']/..//div[contains(@class,'dropdown')]/a[contains(text(),'" + CleanStatus + "')]";
		System.out.println(path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isEnabled(), "Failed:Room Number Not Matched with Status");
		RoomStatusLogger.info("Enable");
		Actions action = new Actions(driver);
		Action mouseOverHome = action.moveToElement(element).build();
		mouseOverHome.perform();
		RoomStatusLogger.info("Perform Mouse Over");
		String Status = element.getText().toLowerCase();
		System.out.println("Status Is: " + Status);
		if (element.getText().toLowerCase().trim().equals(DirtyStatus.toLowerCase().trim())) {
			test_steps.add(
					"Room Status Of Room <b> " + RoomName + " - " + Value + "</b> is : <b>" + DirtyStatus + " </b>");
			RoomStatusLogger.info("Room Status Of Room " + RoomName + " - " + Value + " is : " + DirtyStatus);

		} else if (element.getText().toLowerCase().trim().equals(CleanStatus.toLowerCase().trim())) {
			test_steps.add(
					"Room Status Of Room <b>" + RoomName + " - " + Value + "</b> is : <b>" + CleanStatus + " </b>");
			RoomStatusLogger.info("Room Status Of Room" + RoomName + " - " + Value + " is : " + CleanStatus);
		}

		String status = "//room-card/div[(@data-roomclassname='" + RoomName.toLowerCase() + "')][@data-roomname='"
				+ Value + "']/..//div/span[contains(@class,'status')][contains(text(),'"
				+ VacantStatus.toLowerCase().trim() + "')]|//room-card/div[(@data-roomclassname='"
				+ RoomName.toLowerCase() + "')][@data-roomname='" + Value
				+ "']/..//div/span[contains(@class,'status')][contains(text(),'" + OccupiedStatus.toLowerCase().trim()
				+ "')]";
		System.out.println(status);
		WebElement elementStatus = driver.findElement(By.xpath(status));
		assertTrue(elementStatus.isDisplayed(), "Failed:Room Number Not Matched with RoomStatus");
		if (elementStatus.getText().toLowerCase().trim().equals(VacantStatus.toLowerCase().trim())) {
			VacantNo = 1;
			test_steps.add("Room  <b>" + RoomName + " - " + Value + "</b> is : <b>" + VacantStatus + " </b>");
			RoomStatusLogger.info("Room " + RoomName + " - " + Value + " is : " + VacantStatus);

		} else if (elementStatus.getText().toLowerCase().trim().equals(OccupiedStatus.toLowerCase().trim())) {
			OccupiedNo = 1;
			test_steps.add("Room  <b>" + RoomName + " - " + Value + "</b> is : <b>" + OccupiedStatus + " </b>");
			RoomStatusLogger.info("Room " + RoomName + " - " + Value + " is : " + OccupiedStatus);
		}

	}

	public void VerifyLoading(WebDriver driver) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		try {
			int count = 0;
			while (count <= 40) {
				if (!elementsRoomStatus.Loading.isDisplayed()) {
					break;
				}
				count++;
				Wait.wait1Second();
				System.out.println("in a loop: " + count);
			}
			// Wait.waitForElementToBeGone(driver,elementsRoomStatus.Loading,
			// 10);
		} catch (Exception e) {
			System.out.println("in catch");
			// Wait.wait2Second();
		}
	}

	public void SearchRoomZoneOccupancyOthers(WebDriver driver, String Input) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		VerifyLoading(driver);
		Wait.WaitForElement(driver, RoomStatusPage.InputSearch);
		Wait.explicit_wait_visibilityof_webelement(elementsRoomStatus.InputSearch, driver);
		Wait.explicit_wait_elementToBeClickable(elementsRoomStatus.InputSearch, driver);
		elementsRoomStatus.InputSearch.sendKeys(Input);
		elementsRoomStatus.ButtonSearch.click();
		VerifyLoading(driver);

	}

	public ArrayList<String> VerifyTask(WebDriver driver, String RoomClassName, String RoomNumber, int ExpectedTask,
			boolean isClick) {

		ArrayList<String> test_step = new ArrayList<>();
		RoomClassName = RoomClassName.toLowerCase();
		System.out.println(RoomClassName);
		String RoomClassSlotPath = "//div[@data-roomname='" + RoomNumber + "' and @data-roomclassname='" + RoomClassName
				+ "']";
		Wait.WaitForElement(driver, RoomClassSlotPath);
		WebElement elementRoomSlot = driver.findElement(By.xpath(RoomClassSlotPath));
		Wait.explicit_wait_visibilityof_webelement_120(elementRoomSlot, driver);
		Wait.explicit_wait_elementToBeClickable(elementRoomSlot, driver);
		Actions actions = new Actions(driver);
		actions.moveToElement(elementRoomSlot).perform();

		String TaskPath = "(" + RoomClassSlotPath + "//a[contains(@class,'btn-link-border')])[2]";
		System.out.println(TaskPath);
		Wait.WaitForElement(driver, TaskPath);
		Wait.waitForElementToBeVisibile(By.xpath(TaskPath), driver);
		WebElement elementTask = driver.findElement(By.xpath(TaskPath));
		Wait.waitForElementToBeClickableUsingClass(By.xpath(TaskPath), driver);
		Wait.explicit_wait_visibilityof_webelement(elementTask, driver);
		Wait.explicit_wait_elementToBeClickable(elementTask, driver);
		String getTotalTask = elementTask.getText();
		System.out.println(getTotalTask);
		String strSplit[] = getTotalTask.split(" ");
		System.out.println(strSplit.length);
		getTotalTask = strSplit[0];
		int convertTaskintoInt = Integer.parseInt(getTotalTask);
		System.out.println("convertTaskintoInt: " + convertTaskintoInt);
		test_step.add("Expected task: " + ExpectedTask);
		test_step.add("Found: " + convertTaskintoInt);
		assertEquals(convertTaskintoInt, ExpectedTask, "Failed: Task is mismatching!");
		if (isClick) {
			elementTask.click();
			test_step.add("Click on task");
		}
		test_step.add("Verified task against room class " + RoomClassName + " and room number " + RoomNumber);
		return test_step;
	}

	public String getTaskImage(WebDriver driver, int index) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		Wait.WaitForElement(driver, RoomStatusPage.GetCategoryImage);
		Wait.explicit_wait_visibilityof_webelement(elementsRoomStatus.GetCategoryImage.get(index), driver);
		Wait.explicit_wait_elementToBeClickable(elementsRoomStatus.GetCategoryImage.get(index), driver);
		String[] strSplit = elementsRoomStatus.GetCategoryImage.get(index).getAttribute("class").split("-");

		String getImage = strSplit[strSplit.length - 1];
		System.out.println(strSplit[strSplit.length - 1]);

		return getImage;

	}

	public String getTaskName(WebDriver driver, int index) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		return elementsRoomStatus.GetCategoryName.get(index).getText();

	}

	public String getTaskDueOnAssignedAndStatus(WebDriver driver, int index) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		return elementsRoomStatus.GetTaskDueOnAssignedAndStatus.get(index).getText();

	}

	public String getRoomStatus(WebDriver driver) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		return elementsRoomStatus.RoomStatus.getText();

	}

	public void ButtonClosPopup(WebDriver driver) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		elementsRoomStatus.ButtonClosPopup.click();
	}

	public String GetRoomStatus(WebDriver driver, String ColorName) throws InterruptedException {

		String Pth = "//li[contains(@class,'" + ColorName + "')]//span[contains(@class,'gs-statsValue')]";
		System.out.println(Pth);
		Wait.WaitForElement(driver, Pth);
		WebElement element = driver.findElement(By.xpath(Pth));
		Wait.explicit_wait_visibilityof_webelement(element, driver);
		Wait.explicit_wait_elementToBeClickable(element, driver);
		return element.getText();

	}

	public ArrayList<String> SelectReports(WebDriver driver, String ReportsType) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.explicit_wait_xpath(OR.Report_RoomStatus, driver);
		roomstatus.Report.click();
		RoomStatusLogger.info("Click on reports");
		testSteps.add("Click on reports");

		String reportTypePath = "(//ul[contains(@class,'dropdown-menu')]//a[contains(text(),'" + ReportsType
				+ "')])[1]";
		Wait.WaitForElement(driver, reportTypePath);
		WebElement element = driver.findElement(By.xpath(reportTypePath));
		Wait.explicit_wait_elementToBeClickable(element, driver);
		element.click();
		testSteps.add("Selet reports type as " + ReportsType);

		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		VerifyLoading(driver);
		RoomStatusLogger.info("Switch to report tab");
		Wait.WaitForElement(driver, OR.ReportPage);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.ReportPage, driver);
		Wait.explicit_wait_elementToBeClickable(roomstatus.ReportPage, driver);

		assertTrue(roomstatus.ReportPage.isDisplayed(), "Failed: Room Status Report Page");
		RoomStatusLogger.info("Report: Room Status ");
		testSteps.add("Verified room status page is displaying");
		VerifyLoading(driver);
		return testSteps;
	}

	public String GetRoomStatusInSecondWindows(WebDriver driver, int index) throws InterruptedException {

		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		return elementsRoomStatus.RoomStatusInSecondWindow.get(index).getText();

	}

	public void CloseSecondWindow(WebDriver driver) throws InterruptedException {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		RoomStatusLogger.info("Close Tab");
		driver.switchTo().window(tabs2.get(0));
		Wait.WaitForElement(driver, OR.RoomStatusPage);
		Wait.explicit_wait_xpath(OR.RoomStatusPage, driver);
		RoomStatusLogger.info("Room Status Page");

	}

	public String GetRoomStatusInSecondWindow(WebDriver driver, String RoomClassName, int Index)
			throws InterruptedException {

		String path = "(//td[text()='" + RoomClassName + "']//..//td)[" + Index + "]";
		System.out.println(path);
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		return element.getText();

	}

	public String GetRoomStatusFor(WebDriver driver) throws InterruptedException {

		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		return elementsRoomStatus.RoomStatusFor.getText();

	}

	public String getTaskImageInRoomStatus(WebDriver driver, int index) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		Wait.WaitForElement(driver, RoomStatusPage.GetTaskImage);
		Wait.explicit_wait_visibilityof_webelement(elementsRoomStatus.GetTaskImage.get(index), driver);
		Wait.explicit_wait_elementToBeClickable(elementsRoomStatus.GetTaskImage.get(index), driver);
		String[] strSplit = elementsRoomStatus.GetTaskImage.get(index).getAttribute("class").split("-");

		String getImage = strSplit[strSplit.length - 1];
		System.out.println(strSplit[strSplit.length - 1]);

		return getImage;

	}

	public String getTaskNameRoomStatus(WebDriver driver, int index) throws InterruptedException {
		WebElementsRoomStatus elementsRoomStatus = new WebElementsRoomStatus(driver);
		return elementsRoomStatus.GetTaskName.get(index).getText();

	}

	public String TaskDueOnAssignedAndStatusInRioomStatus(WebDriver driver, String TaskName, int index)
			throws InterruptedException {
		String Path = "//span[text()='" + TaskName + "']//..//..//following-sibling::div";
		List<WebElement> element = driver.findElements(By.xpath(Path));
		return element.get(index).getText();

	}


	public void verify_QuickStats_Enabled_Disabled(WebDriver driver, String Status, boolean condition,
			ArrayList<String> test_steps) throws InterruptedException {
		// TODO Auto-generated method stub
		String path="//span[contains(text(),'"+Status+"')]";
		boolean isExist=Utility.isElementDisplayed(driver, By.xpath(path));
		if(isExist) {
			test_steps.add("Room Status : Verified " + Status + " Quick Stats Enabled");
			RoomStatusLogger.info("Room Status : Verified " + Status + " Quick Stats Enabled");
		}else {
			test_steps.add("Room Status: Verified " + Status + "  Quick Stats is Disabled");
			RoomStatusLogger.info("Room Status: Verified " + Status + "  Quick Stats is Disabled");
		}
	
	}
	
	public void verifyRoomTile_StatusEnabled_Disabled(WebDriver driver, String Status, boolean Condition,
			ArrayList<String> test_steps) {
		String element = "//div[@class='grid-content']//ul//a[contains(text(),'" + Status + "')]";
		boolean isExist=Utility.isElementEnabled(driver, By.xpath(element));
		if(isExist) {
			test_steps.add("Room Tile : Verified " + Status + "  Status is Enabled");
			RoomStatusLogger.info("Room Tile : Verified " + Status + "  Status is Enabled");
		}else {
			test_steps.add("Room Tile : Verified " + Status + "  Status is Disabled");
			RoomStatusLogger.info("Room Tile : Verified " + Status + "  Status is Disabled");	
		}
	}
	
	public void verifyInspectionQuickStatsRoomTile_OnToggleDisable(WebDriver driver, String Status, String inspectionSize,ArrayList<String> test_steps)
	{
		String inspectionPath = "//room-card/div//a[@data-toggle='dropdown'][contains(text(),'"+Status+"')]";
		List<WebElement> inspectionElement = driver.findElements(By.xpath(inspectionPath));
		assertTrue(String.valueOf(inspectionElement.size()).equals(inspectionSize),
				"Failed:to Verify Inspection Quick Stats on toggle Off");
		test_steps.add("Total Inspection Room Tile Found: " + inspectionElement.size());
		RoomStatusLogger.info("Total Inspection Room Tile Found : " + inspectionElement.size());
	}
	
	public void getSortingOrderINArray(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		// ArrayList<String> obtainedList;
		// Added Verification Here
		obtainedList = new ArrayList<>();
		ArrayList<String> list = new ArrayList();
		List<WebElement> elementList = roomstatus.RoomStatus_RoomCardList;
		for (WebElement ele : elementList) {
			list.add(ele.getAttribute("data-roomname").toString());
		}

		Collections.sort(list);
		for (String str : list) {
			obtainedList.add(str);

		}
		Wait.wait5Second();
		System.out.print("Before Report Generate : " + obtainedList);
		RoomStatusLogger.info("Get Sorting Order Before Generating Report");
	}
	
	public void Report_RoomStatus_Verification(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		 
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_xpath(OR.Report_RoomStatus, driver);
		roomstatus.Report_RoomStatus.click();
		Wait.wait3Second();
		test_steps.add("Click Room Status");
		RoomStatusLogger.info("Click Room Status");
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		test_steps.add("Switch to report tab");
		RoomStatusLogger.info("Switch to report tab");
		Wait.wait2Second();
		assertTrue(driver.findElement(By.xpath(OR.ReportPage)).isDisplayed(), "Failed: Room Status Report Page");
		test_steps.add("Report Page is Displayed");
		RoomStatusLogger.info("Report Page is Displayed");
		test_steps.add("*****Start Verifying Sorting*****");
		// Added Steps

		ArrayList<String> obtainedListAfterGenerateReport = new ArrayList<>();
		ArrayList<String> obtainedListAfterGenerateReportSorted = new ArrayList<>();
		List<WebElement> elementListAfterGenerateReport = roomstatus.RoomStatus_Report_RoomStatus;
		for (WebElement ele : elementListAfterGenerateReport) {
			obtainedListAfterGenerateReport.add(ele.getText());

		}
		Collections.sort(obtainedListAfterGenerateReport);
		for (String str : obtainedListAfterGenerateReport) {
			obtainedListAfterGenerateReportSorted.add(str);
		}

		System.out.print("Report Data After Sorting : " + obtainedListAfterGenerateReportSorted);
		// test_steps.add("Report Data After Sorting : "
		// +obtainedListAfterGenerateReport);
		Assert.assertTrue(obtainedList.equals(obtainedListAfterGenerateReportSorted),
				" Failed: to verify Sort By Room#");

		// assertTrue(driver.findElement(By.xpath(OR.ReportPage_Table)).isDisplayed(),
		// "Failed: Room Status Report");
		RoomStatusLogger.info("Report: Room Status ");
		driver.close();
		RoomStatusLogger.info("Close Tab");
		driver.switchTo().window(tabs2.get(0));
		Wait.explicit_wait_xpath(OR.RoomStatusPage, driver);
		RoomStatusLogger.info("Room Status Page");

	}
	
	
	public void reportRoomStatusVerification(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		 
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.explicit_wait_xpath(OR.Report_RoomStatus, driver);
		roomstatus.Report_RoomStatus.click();
		Wait.wait3Second();
		test_steps.add("Click Room Status");
		RoomStatusLogger.info("Click Room Status");
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		test_steps.add("Switch to report tab");
		RoomStatusLogger.info("Switch to report tab");
		Wait.wait60Second();
		assertTrue(driver.findElement(By.xpath(OR.ReportPage)).isDisplayed(), "Failed: Room Status Report Page");
		test_steps.add("Report Page is Displayed");
		RoomStatusLogger.info("Report Page is Displayed");
		test_steps.add("*****Start Verifying Sorting*****");
		// Added Steps

		ArrayList<String> obtainedListAfterGenerateReport = new ArrayList<>();
		ArrayList<String> obtainedListAfterGenerateReportSorted = new ArrayList<>();
		List<WebElement> elementListAfterGenerateReport = roomstatus.RoomStatus_Report_RoomStatus;
		for (WebElement ele : elementListAfterGenerateReport) {
			obtainedListAfterGenerateReport.add(ele.getText());

		}
		Collections.sort(obtainedListAfterGenerateReport);
		for (String str : obtainedListAfterGenerateReport) {
			obtainedListAfterGenerateReportSorted.add(str);
		}

		System.out.print("Report Data After Sorting : " + obtainedListAfterGenerateReportSorted);
		Assert.assertTrue(obtainedList.equals(obtainedListAfterGenerateReportSorted),
				" Failed: to verify Sort By Room#");
		RoomStatusLogger.info("Report: Room Status ");
		

	}
	
	public void switchToMainWindowOfGSAfterCloseReport(WebDriver driver) {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		RoomStatusLogger.info("Close Tab");
		driver.switchTo().window(tabs2.get(0));
		Wait.explicit_wait_xpath(OR.RoomStatusPage, driver);
		RoomStatusLogger.info("Room Status Page");
	}
	public ArrayList<String> getRoomClassAndZonefromGSDashbaord(WebDriver driver) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		//HashMap <String , String> data= new HashMap<String, String>();
		ArrayList<String> data= new ArrayList<String>();
		for(int i=0;i<roomstatus.RoomStatus_RoomCardList.size();i++) {
			//data.put(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-zone"),roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-roomname"));
			data.add(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-zone"));
		}
		RoomStatusLogger.info("data: " +data);
		return data;
		
	}
	
	public ArrayList<String> getZonefromGSDashbaord(WebDriver driver) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		ArrayList<String> data= new ArrayList<String>();
		for(int i=0;i<roomstatus.RoomStatusZone.size();i++) {
			data.add(roomstatus.RoomStatusZone.get(i).getAttribute("data-zone"));
		}
		return data;
		
	}
	
	public void verifytaskOnReport(WebDriver driver, ArrayList<String> test_Steps, String taskType, String detail, String remark, String status) {
		String path="//table[contains(@class,'gs-veticalAM')]//tr[@class='gs-pdf-child-tbl']//span[contains(text(),'"+taskType+"')]";
		String details="//table[contains(@class,'gs-veticalAM')]//tr[@class='gs-pdf-child-tbl']//div[contains(text(),'"+detail+"')]";
		String remarks="//table[contains(@class,'gs-veticalAM')]//tr[@class='gs-pdf-child-tbl']//div[contains(text(),'"+remark+"')]";
		String statuss="//table[contains(@class,'gs-veticalAM')]//tr[@class='gs-pdf-child-tbl']//div[contains(text(),'"+status+"')]";
		
		Wait.WaitForElement(driver, path);
		WebElement element=driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed());
		test_Steps.add("Verify table task type: " + element.getText());
		RoomStatusLogger.info("Verify table task type: " + element.getText());
		WebElement element1=driver.findElement(By.xpath(details));
		assertTrue(element1.isDisplayed());
		test_Steps.add("Verify table task type: " + element1.getText());
		RoomStatusLogger.info("Verify table task type: " + element1.getText());
		WebElement element2=driver.findElement(By.xpath(remarks));
		assertTrue(element2.isDisplayed());
		test_Steps.add("Verify table task type: " + element2.getText());
		RoomStatusLogger.info("Verify table task type: " + element2.getText());
		WebElement element3=driver.findElement(By.xpath(statuss));
		assertTrue(element3.isDisplayed());
		test_Steps.add("Verify table task type: " + element3.getText());
		RoomStatusLogger.info("Verify table task type: " + element3.getText());
		closeCurrentWindow(driver);
		switchToParentWindowTab(driver);
	}
	

	public ArrayList<String> getDirtyRoomList(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Wait.wait5Second();
		ArrayList<String> result = new ArrayList<>();
		String loaderLocator = "//div[text()='Loading']";
		if(Utility.isElementPresent(driver, By.xpath(loaderLocator))) {
			try {
				WebElement element = driver.findElement(By.xpath(loaderLocator));
				Wait.waitForElementToBeInVisibile(By.xpath(loaderLocator), driver);
				Wait.waitForElementToBeGone(driver, element, 30);
			} catch (Exception ignore) {}
		}

		String locator = "//div[contains(@class, 'portfolio-masonry-item') and contains(@class, 'masonry-item') and contains(@class, 'Dirty')]";
		if(Utility.isElementPresent(driver, By.xpath(locator))) {
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			for(WebElement element: elements) {
				WebElement roomClass = element.findElement(By.xpath(".//h2[1]"));
				String text = roomClass.getText().trim();
				String[] splitText = text.split("\n");
				result.add(splitText[1].trim() + "|" + splitText[0].trim());
			}
		}

		return result;
	}

	public void roomStatusReport_Verification(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {
	WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		assertTrue(driver.findElement(By.xpath(OR.ReportPage)).isDisplayed(), "Failed: Room Status Report Page");
		test_steps.add("Report Page is Displayed");
		RoomStatusLogger.info("Report Page is Displayed");
		System.out.println(java.time.LocalDate.now());  
		Date date = new Date(); 
		DateFormat format = new SimpleDateFormat("d MMM''yyyy");
		format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		String strDate = format.format(date);  
	    System.out.println("Date: " +strDate);
		String reportLabel=roomstatus.RoomStatusReportHeader.getText();
		String getDate=roomstatus.RoomStatusReportHeaderDate.getText();
		assertTrue((reportLabel+" "+getDate).equals(reportLabel+" "+ strDate), "Failed: to verify Room Status Date label");
		test_steps.add("Successfull Verified Title Header -Room Stats with Current date: <b>" +reportLabel+" "+getDate +"</b>");
		RoomStatusLogger.info("Successfull Verified Title Header -Room Stats with Current date");
		// verified Report Page Quick Status Displayed or Not 
		assertTrue(roomstatus.ReportDirtyTab.isDisplayed(), "Failed: To Verify Room Status Report Page Dirty Tab");
		test_steps.add("Report Page <b> Dirty</b> tab is Displayed");
		RoomStatusLogger.info("Report Page Dirty tab is Displayed");
		
		assertTrue(roomstatus.ReportInspectionTab.isDisplayed(), "Failed: To Verify Room Status Report Page Inspection Tab");
		test_steps.add("Report Page Inspection tab is Displayed");
		RoomStatusLogger.info("Report Page <b>Inspection</b> tab is Displayed");
		
		assertTrue(roomstatus.ReportCleanTab.isDisplayed(), "Failed: To Verify Room Status Report Page Clean Tab");
		test_steps.add("Report Page <b>Clean</b> tab is Displayed");
		RoomStatusLogger.info("Report Page Clean tab is Displayed");
		

		assertTrue(roomstatus.ReportOutOfOrderTab.isDisplayed(), "Failed: To Verify Room Status Report Page Out Of Order  Tab");
		test_steps.add("Report Page <b>Out Of Order</b>  tab is Displayed");
		RoomStatusLogger.info("Report Page Out Of Order  tab is Displayed");
		
		assertTrue(roomstatus.ReportAllTab.isDisplayed(), "Failed: To Verify Room Status Report Page All Tab");
		test_steps.add("Report Page <b>All</b> tab is Displayed");
		RoomStatusLogger.info("Report Page All tab is Displayed");
		
		// verified Report Page Headers are diaplayed or not 
		assertTrue(roomstatus.ReportRoomHeader.isDisplayed(), "Failed: To Verify Room Status Report Page Room Header");
		test_steps.add("Report Page <b>ROOM</b> Header is Displayed");
		RoomStatusLogger.info("Report Page Room Header is Displayed");
		
		assertTrue(roomstatus.ReportRoomClassHeader.isDisplayed(), "Failed: To Verify Room Status Report Page RoomClass Header");
		test_steps.add("Report Page <b>ROOM CLASS</b> Header is Displayed");
		RoomStatusLogger.info("Report Page RoomClass Header is Displayed");
		
		assertTrue(roomstatus.ReportZoneHeader.isDisplayed(), "Failed: To Verify Room Status Report Page Zone Header");
		test_steps.add("Report Page <b>ZONE</b> Header is Displayed");
		RoomStatusLogger.info("Report Page Zone Header is Displayed");
		
		assertTrue(roomstatus.ReportConditionHeader.isDisplayed(), "Failed: To Verify Room Status Report Page Condition Header");
		test_steps.add("Report Page <b>CONDITION</b> Header is Displayed");
		RoomStatusLogger.info("Report Page Condition Header is Displayed");
		
		assertTrue(roomstatus.ReportOccupancyHeader.isDisplayed(), "Failed: To Verify Room Status Report Page Occupancy Header");
		test_steps.add("Report Page <b>OCCUPANCY</b> Header is Displayed");
		RoomStatusLogger.info("Report Page Occupancy Header is Displayed");
		
		assertTrue(roomstatus.ReportDepartureDateHeader.isDisplayed(), "Failed: To Verify Room Status Report Page Departure Date Header");
		test_steps.add("Report Page <b>DEPARTURE DATE</b> Header is Displayed");
		RoomStatusLogger.info("Report Page Departure Date Header is Displayed");
		
		assertTrue(roomstatus.ReportArrivalDueHeader.isDisplayed(), "Failed: To Verify Room Status Report Page Arrival Due Header");
		test_steps.add("Report Page <b>ARRIVAL DUE</b> Header is Displayed");
		RoomStatusLogger.info("Report Page Arrival Due Header is Displayed");
		
		assertTrue(roomstatus.ReportTasksHeader.isDisplayed(), "Failed: To Verify Room Status Report Page Tass(s) Header");
		test_steps.add("Report Page <b>TASK(S)</b> Header is Displayed");
		RoomStatusLogger.info("Report Page Task(s) Header is Displayed");
		// Verified Report Contant Dispalyed Or Not
		boolean isExist=roomstatus.ReportContant.size()!=0;
		if(isExist)
		{
			assertTrue(roomstatus.ReportTasksHeader.isEnabled(), "Failed: To Report Contact Page");
			test_steps.add("Report Page Contant is Displayed");
			RoomStatusLogger.info("Report Page <b>Contant</b> is Displayed");
		}
		else
		{
			test_steps.add("<b>Report Page Contant is Not Displayed as Quick Stats has Zero Count</b> ");
			RoomStatusLogger.info("Report Page Contant is Not Displayed as Quick Stats has Zero Count");
		}
}
	
	
	public void Verify_SearchTextBox_PlaceHolder(WebDriver driver, String value, ArrayList<String> test_steps) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String placeHolderValue = roomstatus.RoomStatus_SearchField.getAttribute("placeholder");
		assertTrue(placeHolderValue.toLowerCase().trim().equals(value.toLowerCase().trim()),
				"Failed:To Verify Place Holder");
		test_steps.add("The Place holder for the basic search should display the text: <b>" + placeHolderValue +"</b>");
		RoomStatusLogger.info("The Place holder for the basic search should display the text: " + placeHolderValue);
	}
	
	public void Verify_SearchTestBox_Presented(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		assertTrue(roomstatus.RoomStatus_SearchField.isDisplayed(), "Failed:Search Text Box not displayed");
		test_steps.add(" Search  Text Box Displayed.");
		RoomStatusLogger.info("Search  Text Box Displayed.");
	}
	
	public void searchInvalid(WebDriver driver, String Value, String msg,ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, Value, test_steps);
		assertTrue(roomstatus.RoomStatus_NoResult.getText().toLowerCase().trim().equals(msg.toLowerCase().trim()),
				"Failed: Invalid Search not work");
		test_steps.add("Displayed  Warning Message <b>" + roomstatus.RoomStatus_NoResult.getText() + "</b>");
		RoomStatusLogger.info("Displayed  Warning Message " + roomstatus.RoomStatus_NoResult.getText());
	}
	
	public void reportVerifyQuickStatsCount(WebDriver driver, ArrayList<String> test_steps, String status,String Statscount)
	{
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String path="//li[contains(@class,'"+status.toLowerCase().trim()+"')]//span[@class='gs-statsValue']";
		Wait.WaitForElement(driver, path);
		WebElement element ;
		if(status.equals("Dirty")|| status.equalsIgnoreCase("dirty"))
		{
			element= driver.findElement(By.xpath(path));
			assertTrue(element.getText().toLowerCase().trim().equals(Statscount.toLowerCase().trim()), "Failed: To Verify Dirty Stats Count");
			test_steps.add("Verified Dirty Stats Count: <b>" + Statscount+ "</b>");
			RoomStatusLogger.info("Verified Dirty Stats Count : " + Statscount);
			
			assertTrue(roomstatus.ReportInspectionCount.getText().equals("0"), "Failed: To Verify Inspection Stats Count");
			test_steps.add("Verified Inspection Stats Count: <b>" + roomstatus.ReportInspectionCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Inspection Stats Count : " + roomstatus.ReportInspectionCount.getText());
						
			assertTrue(roomstatus.ReportCleanCount.getText().equals("0"), "Failed: To Verify Clean Stats Count");
			test_steps.add("Verified Clean Stats Count: <b>" + roomstatus.ReportCleanCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Clean Stats Count : " + roomstatus.ReportCleanCount.getText());
			
			assertTrue(roomstatus.ReportOutOfOrderCount.getText().equals("0"), "Failed: To Verify Out Of Order Stats Count");
			test_steps.add("Verified Out Of Order Stats Count: <b>" + roomstatus.ReportOutOfOrderCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Out Of Order Stats Count : " + roomstatus.ReportOutOfOrderCount.getText());
			
			assertTrue(roomstatus.ReportAllCount.getText().equals(Statscount.toLowerCase().trim()), "Failed: To Verify All Stats Count");
			test_steps.add("Verified All Stats Count: <b>" + roomstatus.ReportAllCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified All Stats Count : " + roomstatus.ReportAllCount.getText());
		}
		else if(status.equals("Inspection")|| status.equalsIgnoreCase("inspection"))
		{
			element= driver.findElement(By.xpath(path));
			assertTrue(element.getText().toLowerCase().trim().equals(Statscount.toLowerCase().trim()), "Failed: To Verify Inspection Stats Count");
			test_steps.add("Verified Inspection Stats Count: <b>" + Statscount+ "</b>");
			RoomStatusLogger.info("Verified Inspection Stats Count : " + Statscount);
			
			assertTrue(roomstatus.ReportDirtyCount.getText().equals("0"), "Failed: To Verify Dirty Stats Count");
			test_steps.add("Verified Dirty Stats Count: <b>" + roomstatus.ReportDirtyCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Dirty Stats Count : " + roomstatus.ReportDirtyCount.getText());
			
			assertTrue(roomstatus.ReportCleanCount.getText().equals("0"), "Failed: To Verify Clean Stats Count");
			test_steps.add("Verified Clean Stats Count: <b>" + roomstatus.ReportCleanCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Clean Stats Count : " + roomstatus.ReportCleanCount.getText());
			
			assertTrue(roomstatus.ReportOutOfOrderCount.getText().equals("0"), "Failed: To Verify Out Of Order Stats Count");
			test_steps.add("Verified Out Of Order Stats Count: <b>" + roomstatus.ReportOutOfOrderCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Out Of Order Stats Count : " + roomstatus.ReportOutOfOrderCount.getText());
			
			assertTrue(roomstatus.ReportAllCount.getText().equals(Statscount.toLowerCase().trim()), "Failed: To Verify All Stats Count");
			test_steps.add("Verified All Stats Count: <b>" + roomstatus.ReportAllCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified All Stats Count : " + roomstatus.ReportAllCount.getText());

		}
		else if(status.equals("Clean")|| status.equalsIgnoreCase("clean"))
		{
			element= driver.findElement(By.xpath(path));
			assertTrue(element.getText().toLowerCase().trim().equals(Statscount.toLowerCase().trim()), "Failed: To Verify Clean Stats Count");
			test_steps.add("Verified Clean Stats Count: <b>" + Statscount+ "</b>");
			RoomStatusLogger.info("Verified Clean Stats Count : " + Statscount);
			
			
			assertTrue(roomstatus.ReportDirtyCount.getText().equals("0"), "Failed: To Verify Dirty Stats Count");
			test_steps.add("Verified Dirty Stats Count: <b>" + roomstatus.ReportDirtyCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Dirty Stats Count : " + roomstatus.ReportDirtyCount.getText());
			
			assertTrue(roomstatus.ReportInspectionCount.getText().equals("0"), "Failed: To Verify Inspection Stats Count");
			test_steps.add("Verified Inspection Stats Count: <b>" + roomstatus.ReportInspectionCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Inspection Stats Count : " + roomstatus.ReportInspectionCount.getText());
			
			
			assertTrue(roomstatus.ReportOutOfOrderCount.getText().equals("0"), "Failed: To Verify Out Of Order Stats Count");
			test_steps.add("Verified Out Of Order Stats Count: <b>" + roomstatus.ReportOutOfOrderCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Out Of Order Stats Count : " + roomstatus.ReportOutOfOrderCount.getText());
			
			assertTrue(roomstatus.ReportAllCount.getText().equals(Statscount.toLowerCase().trim()), "Failed: To Verify All Stats Count");
			test_steps.add("Verified All Stats Count: <b>" + roomstatus.ReportAllCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified All Stats Count : " + roomstatus.ReportAllCount.getText());

		}
		else if(status.equals("Out-of-Order")|| status.equalsIgnoreCase("out-of-order"))
		{
			element= driver.findElement(By.xpath(path));
			assertTrue(element.getText().toLowerCase().trim().equals(Statscount.toLowerCase().trim()), "Failed: To Verify Out Of Order Stats Count");
			test_steps.add("Verified Out-of-Order Stats Count: <b>" + Statscount+ "</b>");
			RoomStatusLogger.info("Verified Out-of-Order Stats Count : " + Statscount);
			
			assertTrue(roomstatus.ReportDirtyCount.getText().equals("0"), "Failed: To Verify Dirty Stats Count");
			test_steps.add("Verified Dirty Stats Count: <b>" + roomstatus.ReportDirtyCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Dirty Stats Count : " + roomstatus.ReportDirtyCount.getText());
			
			assertTrue(roomstatus.ReportInspectionCount.getText().equals("0"), "Failed: To Verify Inspection Stats Count");
			test_steps.add("Verified Inspection Stats Count: <b>" + roomstatus.ReportInspectionCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Inspection Stats Count : " + roomstatus.ReportInspectionCount.getText());
			
			assertTrue(roomstatus.ReportCleanCount.getText().equals("0"), "Failed: To Verify Clean Stats Count");
			test_steps.add("Verified Clean Stats Count: <b>" + roomstatus.ReportCleanCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified Clean Stats Count : " + roomstatus.ReportCleanCount.getText());
			
			assertTrue(roomstatus.ReportAllCount.getText().equals(Statscount.toLowerCase().trim()), "Failed: To Verify All Stats Count");
			test_steps.add("Verified All Stats Count: <b>" + roomstatus.ReportAllCount.getText()+ "</b>");
			RoomStatusLogger.info("Verified All Stats Count : " + roomstatus.ReportAllCount.getText());
			
		}
	
	}
	
	public void reportVerifyAllQuickStatsCount(WebDriver driver, ArrayList<String> test_steps, String status,String allcount, String dirtyCount, String cleanCount, String oooCount, String inspectionCount, int oooSize)	
	{
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String path="//li[contains(@class,'"+status.toLowerCase().trim()+"')]//span[@class='gs-statsValue']";
		Wait.WaitForElement(driver, path);
		WebElement element ;
				element= driver.findElement(By.xpath(path));
				assertEquals(element.getText().toLowerCase().trim(),(allcount.toLowerCase().trim()), "Failed: To Verify All Stats Count");
				test_steps.add("Verified All Stats Count: <b>" + allcount+ "</b>");
				RoomStatusLogger.info("Verified All Stats Count : " + allcount);
				if(oooSize>0) {
					assertEquals(roomstatus.ReportOutOfOrderCount.getText().toLowerCase().trim(),(oooCount.toLowerCase().trim()), "Failed: To Verify Out Of Order Stats Count");
				test_steps.add("Verified Out Of Order Stats Count: <b>" + roomstatus.ReportOutOfOrderCount.getText()+ "</b>");
				RoomStatusLogger.info("Verified Out Of Order Stats Count : " + roomstatus.ReportOutOfOrderCount.getText());			
				}
				assertEquals(roomstatus.ReportDirtyCount.getText().toLowerCase().trim(),(dirtyCount.toLowerCase().trim()), "Failed: To Verify Dirty Stats Count");
				test_steps.add("Verified Dirty Stats Count: <b>" + roomstatus.ReportDirtyCount.getText()+ "</b>");
				RoomStatusLogger.info("Verified Dirty Stats Count : " + roomstatus.ReportDirtyCount.getText());
				
				assertEquals(roomstatus.ReportInspectionCount.getText().toLowerCase().trim(),(inspectionCount.toLowerCase().trim()), "Failed: To Verify Inspection Stats Count");
				test_steps.add("Verified Inspection Stats Count: <b>" + roomstatus.ReportInspectionCount.getText()+ "</b>");
				RoomStatusLogger.info("Verified Inspection Stats Count : " + roomstatus.ReportInspectionCount.getText());
				
				assertEquals(roomstatus.ReportCleanCount.getText().toLowerCase().trim(),(cleanCount.toLowerCase().trim()), "Failed: To Verify Clean Stats Count");
				test_steps.add("Verified Clean Stats Count: <b>" + roomstatus.ReportCleanCount.getText()+ "</b>");
				RoomStatusLogger.info("Verified Clean Stats Count : " + roomstatus.ReportCleanCount.getText());
			
		}
	
	public String getQuickStatCount(WebDriver driver, String status) {
		String getCount = null;
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		if (status.equals("Inspection") || status.equalsIgnoreCase("inspection")) {
			 getCount = roomstatus.RoomStatus_InspectionStatSize.getText();
			
		} else if (status.equals("Clean") || status.equalsIgnoreCase("clean")) {
			 getCount = roomstatus.RoomStatus_CleanStatSize.getText();
			

		} else if (status.equals("Out of Order") || status.equalsIgnoreCase("out of order")) {
			 getCount = roomstatus.RoomStatus_OutOfOrderStatSize.getText();			
		}
		else if (status.equals("Dirty") || status.equalsIgnoreCase("dirty")) {
			 getCount = roomstatus.RoomStatus_DirtyStatSize.getText();			
		}
		else if (status.equals("All") || status.equalsIgnoreCase("all")) {
			 getCount = roomstatus.RoomStatus_AllQuickStatsNumber.getText();			
		}

		return getCount;
	}

	
	public HashMap<String, ArrayList<String>> getRoomTileData(WebDriver driver, String status) {
		HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
		ArrayList<String> rooms=new ArrayList<String>();
		ArrayList<String> roomsClass=new ArrayList<String>();
		ArrayList<String> statusList=new ArrayList<String>();
		ArrayList<String> zone=new ArrayList<String>();
		List<WebElement> listOfRooms=driver.findElements(By.xpath("//div[contains(@class,'"+status+"')]"));
		List<WebElement> statusOfRoomElement=driver.findElements(By.xpath("//div[contains(@class,'"+status+"')]/div//div[@class='grid-thumbnail']//span"));
		for(int i=0;i<listOfRooms.size();i++) {
			rooms.add(listOfRooms.get(i).getAttribute("data-roomname").trim());
			roomsClass.add(listOfRooms.get(i).getAttribute("data-roomclassname").trim());
			statusList.add(statusOfRoomElement.get(i).getText().trim());
			zone.add(listOfRooms.get(i).getAttribute("data-zone").trim());
		}
		
		data.put("RoomNo", rooms);
		data.put("RoomClass", roomsClass);
		data.put("StatusOfRoom", statusList);
		data.put("Zone", zone);
		RoomStatusLogger.info("Data Are : " + data);
		return data;
		
	}
	
	
	public void reportVerifyData(WebDriver driver, String roomNO, String roomClass,String zone,String condition,
			String occupancy,String departure,String task, String arrival,ArrayList<String> test_steps,int index) throws java.text.ParseException
	{
		index=index+1;
		String roomElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[1])["+index+"]";
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		assertEquals(driver.findElement(By.xpath(roomElement)).getText(),(roomNO), "Failed: To Verify Room No under Room Header");
		test_steps.add("Verified Room Header Value : <b>" + roomNO+ "</b>");
		RoomStatusLogger.info("Verified Room Header Value : " + roomNO);
		
		String roomClassElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[2])["+index+"]";
		assertEquals(driver.findElement(By.xpath(roomClassElement)).getText().toLowerCase().trim(),(roomClass.toLowerCase().trim()), "Failed: To Verify Room Class under ROOM CLASS Header");
		test_steps.add("Verified Room Class Header Value : <b>" + roomClass+ "</b>");
		RoomStatusLogger.info("Verified Room Class Header Value : " + roomClass);
		
		String zoneElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[3])["+index+"]";
		assertEquals(driver.findElement(By.xpath(zoneElement)).getText().toLowerCase().trim(),(zone.toLowerCase().trim()), "Failed: To Verify zone under ZONE Header");
		test_steps.add("Verified Zone Header Value : <b>" + zone+ "</b>");
		RoomStatusLogger.info("Verified Zone Header Value : " + zone);
		if(Utility.validateString(condition)) {
		String conditionElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[4])["+index+"]";
		assertEquals(driver.findElement(By.xpath(conditionElement)).getText().toLowerCase().trim(),(condition.toLowerCase().trim()), "Failed: To Verify Condition under Condition Header");		
		test_steps.add("Verified Condition Header Value : <b>" + condition+ "</b>");
		RoomStatusLogger.info("Verified Condition Header Value : " + condition);
		}
		String occupancyElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[5])["+index+"]";
		assertEquals(driver.findElement(By.xpath(occupancyElement)).getText().toLowerCase().trim(),(occupancy.toLowerCase().trim()), "Failed: To Verify Condition under Condition Header");		
		test_steps.add("Verified Occupancy Header Value : <b>" + occupancy+ "</b>");
		RoomStatusLogger.info("Verified Occupancy Header Value : " + occupancy);
		
		if(Utility.validateString(departure)) {
		String departureElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[6])["+index+"]";
		assertEquals(driver.findElement(By.xpath(departureElement)).getText().toLowerCase().trim(),(departure.toLowerCase().trim()), "Failed: To Verify Condition under Condition Header");		
		test_steps.add("Verified Departure Date Header Value : <b>" + departure+ "</b>");
		RoomStatusLogger.info("Verified Departure Date Header Value : " + departure);
		}
		if(Utility.validateString(arrival)) {
		String arrivalDueElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[7])["+index+"]";
		assertEquals(driver.findElement(By.xpath(arrivalDueElement)).getText().toLowerCase().trim(),(arrival.toLowerCase().trim()), "Failed: To Verify Condition under Condition Header");		
		test_steps.add("Verified Arrival Due Header Value : <b>" + arrival+ "</b>");
		RoomStatusLogger.info("Verified Arrival Due Header Value : " + arrival);
		}
		if(Utility.validateString(task)) {
		String tasksElement="(//table[contains(@class,'gs-table')]//tbody//tr/td[8])["+index+"]";
		assertEquals(roomstatus.ReportContantData.get(7).getText().toLowerCase().trim(),(task.replaceFirst("\\.0+$", "").toLowerCase().trim()), "Failed: To Verify Tasks under Tasks  Header");
		test_steps.add("Verified Tasks Header Value : <b>" + task+ "</b>");
		RoomStatusLogger.info("Verified Tasks Header Value : " + task);
		}
	}
	
	
	public void clickStatesRoomStatus(WebDriver driver, String RoomStatus) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		if (RoomStatus.equals("Clean") || RoomStatus.equalsIgnoreCase("clean")) {
			roomstatus.VerifyRoomStats_Clean.click();
			Wait.wait5Second();
		
		} else if (RoomStatus.equals("Dirty") || RoomStatus.equalsIgnoreCase("dirty")) {

			roomstatus.VerifyRoomStats_Dirty.click();
			Wait.wait5Second();
			}

		else if (RoomStatus.equals("Inspection") || RoomStatus.equalsIgnoreCase("inspection")) {
			roomstatus.VerifyRoomStats_Inspection.click();
			Wait.wait5Second();
				}
		else if (RoomStatus.equals("All") || RoomStatus.equalsIgnoreCase("all")) {
			roomstatus.VerifyRoomStats_All.click();
			Wait.wait5Second();
				}
	}

	
	
	public void clickUpdateStatus_InspectionButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", roomstatus.UpdateStatus_Inspection);
		testSteps.add("Click on Inspection");
		RoomStatusLogger.info("Click on Inspection");
	}
	
	public HashMap<String, ArrayList<String>> getRoomTileDataForAllQuickStats(WebDriver driver) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
		ArrayList<String> rooms=new ArrayList<String>();
		ArrayList<String> roomsClass=new ArrayList<String>();
		ArrayList<String> statusList=new ArrayList<String>();
		ArrayList<String> zone=new ArrayList<String>();
	//	List<WebElement> listOfRooms=driver.findElements(By.xpath("//div[contains(@class,'"+status+"')]"));
		List<WebElement> statusOfRoomElement=driver.findElements(By.xpath("//room-card/div//div[@class='grid-thumbnail']//span"));
		for(int i=0;i<roomstatus.RoomStatus_RoomCardList.size();i++) {
			rooms.add(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-roomname").trim());
			roomsClass.add(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-roomclassname").trim());
			statusList.add(statusOfRoomElement.get(i).getText().trim());
			zone.add(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-zone").trim());
		}
		
		data.put("RoomNo", rooms);
		data.put("RoomClass", roomsClass);
		data.put("StatusOfRoom", statusList);
		data.put("Zone", zone);
		RoomStatusLogger.info("Data Are : " + data);
		return data;
		
	}
	

	public HashMap<String, ArrayList<String>> getRoomTileDataForSpecificSize(WebDriver driver, int size) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
		ArrayList<String> rooms=new ArrayList<String>();
		ArrayList<String> roomsClass=new ArrayList<String>();
		ArrayList<String> statusList=new ArrayList<String>();
		ArrayList<String> zone=new ArrayList<String>();
		ArrayList<String> condition=new ArrayList<String>();
		List<WebElement> statusOfRoomElement=driver.findElements(By.xpath("//room-card/div//div[@class='grid-thumbnail']//span"));
		List<WebElement> conditionOfRoomElement=driver.findElements(By.xpath("//room-card/div//div[@class='grid-content']//div[contains(@class,'dropdown')]//ul/li[1]/a"));
		for(int i=0;i<size;i++) {
			rooms.add(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-roomname").trim());
			roomsClass.add(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-roomclassname").trim());
			statusList.add(statusOfRoomElement.get(i).getText().trim());
			zone.add(roomstatus.RoomStatus_RoomCardList.get(i).getAttribute("data-zone").trim());		
			Actions action = new Actions(driver);
			// Mouseover on an element
			action.moveToElement(roomstatus.RoomStatus_RoomCardList.get(i)).clickAndHold().perform();
			RoomStatusLogger.info("Mouse Hover  Room Tile " + i);
			Wait.WaitForElement(driver, OR.RoomStatus_RoomTileDropDownButton);
			assertTrue(roomstatus.RoomStatus_RoomTileDropDownButton.get(i).isEnabled(),"Failed to verify Room Tile Drop Down Box");
			assertTrue(driver.findElement(By.tagName("body")).getCssValue("cursor").equals("auto"), "Failed to verify Hand Over");
			roomstatus.RoomStatus_RoomTileDropDownButton.get(i).click();
			RoomStatusLogger.info("Click on Room Tile Drop Down Box " + i);
			condition.add(conditionOfRoomElement.get(i).getText().trim());
		}
		
		data.put("RoomNo", rooms);
		data.put("RoomClass", roomsClass);
		data.put("StatusOfRoom", statusList);
		data.put("Zone", zone);
		data.put("Condition", condition);
		RoomStatusLogger.info("Data Are : " + data);
		return data;
		
	}
	
	
	public void verifyOutOFOrderDisabledUnableToUpdate(WebDriver driver, ArrayList<String> testSteps) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.WaitForElement(driver, OR_GuestServices.RoomStatus_OORRoomCardSize);
		for(int i=0;i<roomstatus.RoomStatus_OORRoomCardS.size();i++) {
		Actions action = new Actions(driver);
		// Mouseover on an element
		//action.moveToElement(roomstatus.RoomStatus_OORRoomCardS.get(i)).clickAndHold().perform();
		action.moveToElement(roomstatus.RoomStatus_OORRoomCardS.get(i)).perform();
		testSteps.add("Mouse Hover  Room Tile " + i);
		RoomStatusLogger.info("Mouse Hover  Room Tile " + i);
		boolean isExist=Utility.isElementPresent(driver, By.xpath(OR.RoomStatus_RoomTileDropDownButton));
		if(!isExist){testSteps.add("Unable to update Out of Order rooms ");
		RoomStatusLogger.info("Unable to update Out of Order rooms ");
		}
		}

	}
	public String getCurrentStatusofRoomTile(WebDriver driver, ArrayList<String> testSteps, String roomName,
			String roomNO) {
		String statusName=null;
		String path = "//room-card/div[(@data-roomclassname='" + roomName.toLowerCase() + "')][@data-roomname='"
				+ roomNO + "']//div[@class='grid-content']//div[contains(@class,'dropdown')]//a";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		JavascriptExecutor js = (JavascriptExecutor) driver;
			String value = (String) js.executeScript("return  arguments[0].text", element);
			statusName=value.trim();

		return statusName;

	}
	public void verifyCurrentStatusofRoomTile(WebDriver driver, ArrayList<String> testSteps, String roomName,
			String roomNO, String status) {
		String statusName=null;
		String path = "//room-card/div[(@data-roomclassname='" + roomName.toLowerCase() + "')][@data-roomname='"
				+ roomNO + "']//div[@class='grid-content']//div[contains(@class,'dropdown')]//a";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		JavascriptExecutor js = (JavascriptExecutor) driver;
			String value = (String) js.executeScript("return  arguments[0].text", element);
			statusName=value.trim();
			assertEquals(statusName.toLowerCase().trim(), status.toLowerCase().trim(), "Failed to verify status of room tile");
			testSteps.add("Verified Status of Room Tile: "+statusName);
			RoomStatusLogger.info("Verified Status of Room Tile: "+statusName);

	}
	
	public void clickSelectAll(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.WaitForElement(driver, OR_GuestServices.GSSelectAll);
		Utility.ScrollToElement(roomstatus.GSSelectAll, driver);
		roomstatus.GSSelectAll.click();
		RoomStatusLogger.info("Click Select All");
		testSteps.add("Click Select All");
	}
	
	public void clickDeSelectAll(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		Wait.WaitForElement(driver, OR_GuestServices.GSDSelectAll);
		Utility.ScrollToElement(roomstatus.GSDSelectAll, driver);
		roomstatus.GSDSelectAll.click();
		RoomStatusLogger.info("Click DeSelect All");
		testSteps.add("Click DeSelect All");
	}
	
	public void verifyAllQuickStatColor(WebDriver driver, ArrayList<String> testSteps) {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		String one = roomstatus.RoomStatus_AllQuickStatsNumber.getCssValue("color");
		RoomStatusLogger.info("Get Color :" + one);
		java.awt.Color myColor = java.awt.Color.decode("#43B7BA");
//		Utility ut= new Utility();
//		String coloeName=ut.getColorNameFromColor(myColor);
//		RoomStatusLogger.info(coloeName);
		
		
	}
	
	public void verifyAllRoomsSelectedDeselected(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		boolean isExist= Utility.isElementPresent(driver,By.xpath(OR_GuestServices.allRoomTileChecked));
		if(isExist) {
		Wait.WaitForElement(driver, OR_GuestServices.allRoomTileChecked);
		assertTrue(roomstatus.allRoomTileChecked.get(0).isEnabled());
		int size= roomstatus.allRoomTileChecked.size();
		RoomStatusLogger.info("Verified all room selected" +size);
		testSteps.add("Verified all room selected" +size);
		}else {
			RoomStatusLogger.info("Verified No room selected");
			testSteps.add("Verified No room selected");
		}
	}
	
	public void verifyArrivalDueofSpecificRoomNoAndRoomClass(WebDriver driver, String roomNo, String roomClass,ArrayList<String> testSteps) throws InterruptedException {
		
		WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
		inputSearchAndClickSearchButton(driver, roomNo, testSteps);
		Wait.explicit_wait_visibilityof_webelement(roomstatus.RoomStatus_RoomCardList.get(0), driver);
		assertTrue(!(roomstatus.RoomStatus_AllQuickStatsNumber.getText().equals("0")), "Failed:Search not work");
		testSteps.add("Search  working.");
		RoomStatusLogger.info("Search  working.");
		String path="//div[@data-roomname='"+roomNo+"' and @data-roomclassname='"+roomClass.toLowerCase()+"']//span[@class='arrivalDue']";
		RoomStatusLogger.info(path);
		Wait.WaitForElement(driver, path);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),"Failed to verify arrival due for room number and room class");
		RoomStatusLogger.info("Verified Arrival Due to searched room and Room Class " +roomNo +" and " +roomClass);
		testSteps.add("Verified Arrival Due to searched room and Room Class " +roomNo +" and " +roomClass);
	}
	
	public void verifyRoomTileStatus(WebDriver driver, String roomNo, String roomClass, String status,ArrayList<String> testSteps) {
		
		String path="//room-card/div[@data-roomname='"+roomNo+"' and @data-roomclassname='"+roomClass.toLowerCase()+"']"
				+ "/div//div[contains(@class,'gs')]/a[contains(text(),'"+status+"')]";
		Wait.WaitForElement(driver, path);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled());
		RoomStatusLogger.info("Verified Status of " +roomNo +" and " +roomClass +" Status is " + status );
		testSteps.add("Verified Status of " +roomNo +" and " +roomClass +" Status is " + status);
		
	}
	
public void verifyArrivalDueStatus(WebDriver driver,String status,ArrayList<String> testSteps) {
		
		String path="//room-card/div//span[@class='arrivalDue']/parent::*/following-sibling::div"
				+ "//div[contains(@class,'gs')]/a[contains(text(),'"+status+"')]";
		Wait.WaitForElement(driver, path);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled());
		RoomStatusLogger.info("Verified Arrival Due Status  " +status);
		testSteps.add("Verified Arrival Due Status  " +status);
		
	}

public void verifyandClickPrintButton(WebDriver driver,ArrayList<String> testSteps) throws InterruptedException {
	WebElements_RoomStatus roomstatus = new WebElements_RoomStatus(driver);
	Wait.WaitForElement(driver, OR.ReportPrintIcon);
	assertTrue(roomstatus.ReportPrintIcon.isDisplayed());
	testSteps.add("Verified Print button Displayed");
	RoomStatusLogger.info("Verified Print button Displayed");
	Utility.ScrollToElement(roomstatus.ReportPrintIcon, driver);
	/*roomstatus.ReportPrintIcon.click();*/
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", roomstatus.ReportPrintIcon);
	testSteps.add("Click Print button");
	RoomStatusLogger.info("Click Print button");
	
}

public void cancelPrintPopup(WebDriver driver,ArrayList<String> testSteps) throws InterruptedException {	
	JavascriptExecutor js = (JavascriptExecutor) driver;
	Set<String> windowHandles = driver.getWindowHandles();
	if (!windowHandles.isEmpty()) {
	    driver.switchTo().window((String) windowHandles.toArray()[windowHandles.size() - 1]);
	}
	WebElement cancelBtn = (WebElement)js.executeScript("return document.querySelector('body>print-preview-app').shadowRoot.querySelector('#sidebar').shadowRoot.querySelector('print-preview-button-strip').shadowRoot.querySelector('cr-button.cancel-button')");
	Wait.wait5Second();
	cancelBtn.click();
	testSteps.add("Click Cancel button of Print Popup");
	RoomStatusLogger.info("Click Cancel button of Print Popup");
}

public void verifyTaskPageAvailable(WebDriver driver,ArrayList<String> testSteps) {
	Wait.explicit_wait_xpath(OR.TaskListPage, driver);
	assertTrue(driver.findElement(By.xpath(OR.TaskListPage)).isEnabled(), "Failed to verify task list page");	
	testSteps.add("Verified Task List Page Enabled");
	RoomStatusLogger.info("Verified Task List Page Enabled");
}
}
