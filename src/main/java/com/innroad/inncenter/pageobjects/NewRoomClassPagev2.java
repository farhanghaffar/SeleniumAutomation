package com.innroad.inncenter.pageobjects;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClass;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;



public class NewRoomClassPagev2 {
	ArrayList<String> test_steps = new ArrayList<>();
	public static Logger reslogger = Logger.getLogger("NewRoomClassPagev2");
	public ArrayList<String> createRoomClassPageWithDescriptionV2(WebDriver driver, ArrayList<String> test_steps, String roomClassName,String roomClassAB,String policy,String sortOrder,String maxAdults,String maxPerson,String details,String roomQuant,String roomName,String statinId,String sortOrder1,String zone) throws InterruptedException
 {
	 Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
	//	res.roomClassNamev2.clear();
	 String randomNumber= "1234567890";
	 randomNumber= Utility.GenerateRandomNumber();
	 roomClassName=roomClassName+randomNumber;
	 roomClassAB=roomClassAB+randomNumber;
	 details=details +randomNumber;
		res.roomClassNamev2.sendKeys(roomClassName);
		test_steps.add("Enter RoomClassName : " + roomClassName);
		reslogger.info("Enter RoomClassName : " + roomClassName);
		res.roomClassABv2.clear();
		res.roomClassABv2.sendKeys(roomClassAB);
		test_steps.add("Enter RoomClassAB : " + roomClassAB);
		reslogger.info("Enter RoomClassAB : " + roomClassAB);
		res.sortOrderv2.clear();
		res.sortOrderv2.sendKeys(sortOrder);
		test_steps.add("Enter SortOrder : " + sortOrder);
		reslogger.info("Enter SortOrder : " + sortOrder);
		res.maxAdultv2.clear();
		res.maxAdultv2.sendKeys(maxAdults);
		test_steps.add("Enter MaxAdults : " + maxAdults);
		reslogger.info("Enter MaxAdults : " + maxAdults);
		res.maxPersonv2.clear();
		res.maxPersonv2.sendKeys(maxPerson);
		test_steps.add("Enter MaxPerson : " + maxPerson);
		reslogger.info("Enter MaxPerson : " + maxPerson);
		res.smokingPolicyv2.click();
		String smokingpolicy = "//ul[@role='listbox']/li[text()='"+policy+"']";

		reslogger.info(smokingpolicy);
		Wait.WaitForElement(driver, smokingpolicy);
		driver.findElement(By.xpath(smokingpolicy)).click();
		test_steps.add("Selecting the smokingpolicy : " + policy);
		reslogger.info("Selecting the smokingpolicy : " + policy);
		res.editDescription.click();
		test_steps.add("click editdescription");
		reslogger.info("click editdescription");
		res.detailsv2.clear();
		res.detailsv2.sendKeys(details);
		test_steps.add("Enter details : " + details);
		reslogger.info("Enter details : " + details);
        WebDriverWait wait = new WebDriverWait(driver, 90);
		
		Wait.WaitForElement(driver, OR.donev2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.donev2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.donev2), driver);
		
		Utility.ScrollToElement(res.donev2, driver);
		
		res.donev2.click();
		test_steps.add("Clicking_done");
		reslogger.info("Clicking_done");
		res.roomsv2.click();
		test_steps.add("Clicking New Room Tab");
		reslogger.info("Clicking New Room Tab");
		res.roomsquantityv2.clear();
		res.roomsquantityv2.sendKeys(roomQuant);
		test_steps.add("Enter RoomQuantity : " + roomQuant);
		reslogger.info("Enter RoomQuantity : " + roomQuant);
		res.roomquantityButtonv2.click();
		test_steps.add("Addding quantity");
		reslogger.info("Addding quantity");
		res.roomNamev2.clear();
		res.roomNamev2.sendKeys(roomName);
		test_steps.add("Enter RoomName : " + roomName);
		reslogger.info("Enter RoomName : " + roomName);
		res.stationIdv2.clear();
		res.stationIdv2.sendKeys(statinId);
		test_steps.add("Enter StationId : " + statinId);
		reslogger.info("Enter StationId : " + statinId);
		res.sortOrder.clear();
		res.sortOrder.sendKeys(sortOrder1);
		test_steps.add("Enter Sortorder : " + sortOrder1);
		reslogger.info("Enter Sortorder : " + sortOrder1);
		res.houseKeepingZonev2.click();
		String zonev2 = "//ul[@role='listbox']/li[text()='"+zone+"']";
        reslogger.info(zonev2);
		Wait.WaitForElement(driver, zonev2);
		driver.findElement(By.xpath(zonev2)).click();
		test_steps.add("Enter Zone : " + zonev2);
		reslogger.info("Enter Zone : " + zonev2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.doneButtonv2), driver);
		res.doneButtonv2.click();
		test_steps.add("Clicking on done button");
		reslogger.info("Clicking on done button");
		Wait.waitForElementToBeClickable(By.xpath(OR.publishv2), driver);
		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		reslogger.info("Clicking on publish button");
		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
		//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
		Wait.wait5Second();
		res.roomClassPublishConfirmedOKButtonV2.click();
		return test_steps;
	}
	public ArrayList<String> updateRoomClassAbbrV2(WebDriver driver, ArrayList<String> test_steps,String roomClass,String roomClassAB)throws InterruptedException
	{
		String roomclassname = "//a[@class='roomClassLink' and text()='"+roomClass+"']";

		reslogger.info(roomclassname);
		Wait.WaitForElement(driver, roomclassname);
		driver.findElement(By.xpath(roomclassname)).click();
		test_steps.add("Navigating to room class page : " + roomClass);
		reslogger.info("Navigating to room class page : " + roomClass);
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.roomClassABv2.clear();
		res.roomClassABv2.sendKeys(roomClassAB);
		test_steps.add("update RoomClassAB : " + roomClassAB);
		reslogger.info("update RoomClassAB : " + roomClassAB);
		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		reslogger.info("Clicking on publish button");
		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
		Wait.wait5Second();
		res.roomClassPublishConfirmedOKButtonV2.click();
		return test_steps;
	}
	public ArrayList<String> updateRoomClassSmokeingPolicyV2(WebDriver driver, ArrayList<String> test_steps,String roomClass,String policy)throws InterruptedException
	{
		String roomclassname = "//a[@class='roomClassLink' and text()='"+roomClass+"']";

		reslogger.info(roomclassname);
		Wait.WaitForElement(driver, roomclassname);
		driver.findElement(By.xpath(roomclassname)).click();
		test_steps.add("Navigating to room class page : " + roomClass);
		reslogger.info("Navigating to room class page : " + roomClass);
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.smokingPolicyv2.click();
		String smokingpolicy = "//ul[@role='listbox']/li[text()='"+policy+"']";

		reslogger.info(smokingpolicy);
		Wait.WaitForElement(driver, smokingpolicy);
		driver.findElement(By.xpath(smokingpolicy)).click();
		test_steps.add("Selecting the smokingpolicy : " + policy);
		reslogger.info("Selecting the smokingpolicy : " + policy);
	
		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		reslogger.info("Clicking on publish button");
		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
		//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
		Wait.wait5Second();
		res.roomClassPublishConfirmedOKButtonV2.click();
		return test_steps;
	}
	
		public ArrayList<String> updateRoomClassMaxAdutlsAndPersonsV2(WebDriver driver, ArrayList<String> test_steps,String roomClass,String maxAdults,String maxPerson)throws InterruptedException
		{String roomclassname = "//a[@class='roomClassLink' and text()='"+roomClass+"']";

		reslogger.info(roomclassname);
		Wait.WaitForElement(driver, roomclassname);
		driver.findElement(By.xpath(roomclassname)).click();
		test_steps.add("Navigating to room class page : " + roomClass);
		reslogger.info("Navigating to room class page : " + roomClass);
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.maxAdultv2.clear();
		res.maxAdultv2.sendKeys(maxAdults);
		test_steps.add("update MaxAdults : " + maxAdults);
		reslogger.info("update MaxAdults : " + maxAdults);
		res.maxPersonv2.clear();
		res.maxPersonv2.sendKeys(maxPerson);
		test_steps.add("Enter MaxPerson : " + maxPerson);
		reslogger.info("Enter MaxPerson : " + maxPerson);
			res.publishv2.click();
			test_steps.add("Clicking on publish button");
			reslogger.info("Clicking on publish button");
			Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
			//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
			Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
			Wait.wait5Second();
			res.roomClassPublishConfirmedOKButtonV2.click();
			return test_steps;
		}
		
			public ArrayList<String> updateRoomClassNameV2(WebDriver driver, ArrayList<String> test_steps,String roomClass,String roomClassName)throws InterruptedException
			{
				String roomclassname = "//a[@class='roomClassLink' and text()='"+roomClass+"']";

				reslogger.info(roomclassname);
				Wait.WaitForElement(driver, roomclassname);
				driver.findElement(By.xpath(roomclassname)).click();
				test_steps.add("Navigating to room class page : " + roomClass);
				reslogger.info("Navigating to room class page : " + roomClass);
				Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
				res.roomClassNamev2.clear();
				res.roomClassNamev2.sendKeys(roomClassName);
				test_steps.add("update RoomClassName : " + roomClassName);
				reslogger.info("update RoomClassName : " + roomClassName);
			     res.publishv2.click();
					test_steps.add("Clicking on publish button");
					reslogger.info("Clicking on publish button");
					Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
					//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
					Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
					Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
					Wait.wait5Second();
					res.roomClassPublishConfirmedOKButtonV2.click();
					return test_steps;
			}
			
	public ArrayList<String> getAllRoomClassesListV2(WebDriver driver, ArrayList<String> test_steps,String patialClassName)throws InterruptedException
	{
		ArrayList<String> test_Steps = new ArrayList<>();
		//ArrayList<String> allroomclasseslist = new ArrayList<>();
		ArrayList<String> matchingstring = new ArrayList<>();
		
		List<WebElement> listOfRoomNames = driver.findElements(By.xpath(OR.roomClassTable));
		for(int i = 1; i<=listOfRoomNames.size(); i++){
			String getAllRoomClassesListV2 =driver.findElement(By.xpath("//table//tbody/tr" + "[" + i + "]" + "//a[@class='roomClassLink']")).getText();
		if(getAllRoomClassesListV2.startsWith(patialClassName))
		{
			matchingstring.add(getAllRoomClassesListV2);
			//System.out.println(getAllRoomClassesListV2);
			reslogger.info("All room classes are : "+matchingstring);
			test_Steps.add("All room classes are : "+matchingstring);
		}
		
		}
		return matchingstring;
		
	}
	public ArrayList<String> getAllActiveRoomClassesListV2(WebDriver driver, ArrayList<String> test_steps,String status)throws InterruptedException
	{
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> activeroomclasses = new ArrayList<>();
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.roomClassStatusList.click();
		String statusselect = "//li[text()='"+ status +"']";
         reslogger.info(statusselect);
		Wait.WaitForElement(driver, statusselect);
		driver.findElement(By.xpath(statusselect)).click();
		test_steps.add("Selecting the Status : " + status);
		reslogger.info("Selecting the Status : " + status);
		List<WebElement> listOfRoomNames = driver.findElements(By.xpath(OR.roomClassTable));
		for(int i = 1; i<=listOfRoomNames.size(); i++){
			String getAllRoomClassesListV2 =driver.findElement(By.xpath("//table//tbody/tr" + "[" + i + "]" + "//a[@class='roomClassLink']")).getText();
			activeroomclasses.add(getAllRoomClassesListV2);
			reslogger.info("All active room classes are : "+getAllRoomClassesListV2);
			test_Steps.add("All active room classes are : "+getAllRoomClassesListV2);
			
	}
	return activeroomclasses;
	}
	
	
	
}

	


