package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.innroad.inncenter.properties.OR_Setup;
import com.innroad.inncenter.webelements.Elements_NewRoomClass;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.innroad.inncenter.interfaces.INewRoomClassesV2;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;
import com.innroad.inncenter.webelements.Elements_NewRoomClassV2;
import com.relevantcodes.extentreports.ExtentTest;

public class NewRoomClassesV2 implements INewRoomClassesV2{

	public static Logger roomclassLogger = Logger.getLogger("NewRoomClassesV2");
	Elements_NewRoomClassV2 roomClass = null;

	public void RoomClass_Search(WebDriver driver) {
		roomClass = new Elements_NewRoomClassV2(driver);
		Wait.WaitForElement(driver, OR.SearchRoomClass);
		// roomClass.SearchRoomClass.click();
	}

	public ArrayList<String> createRoomClassV2( WebDriver driver, ArrayList<String> test_steps, String roomClassName, String roomClassAbbrivation,
			String maxAdults, String maxPersons, String roomQuantity) throws Exception {

		ArrayList<String> returnRoomNos= new ArrayList<String>();
		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 90);

		Wait.WaitForElement(driver, OR.newRoomClassButtonV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRoomClassButtonV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRoomClassButtonV2), driver);

		Utility.ScrollToElement(roomClass.newRoomClassButtonV2, driver);

		roomClass.newRoomClassButtonV2.click();
		roomclassLogger.info("Clicked on NEW ROOMCLASS button");
		test_steps.add("Clicked on NEW ROOMCLASS button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassNameFieldV2), driver);

		roomClass.roomClassNameFieldV2.sendKeys(roomClassName);
		roomclassLogger.info("Entered the room class name : " + roomClassName);
		test_steps.add("Enter room class name : <b>" + roomClassName + "</b>");

		roomClass.roomClassAbbreviationFieldV2.sendKeys(roomClassAbbrivation);
		roomclassLogger.info("Entered the room class abbreviation : " + roomClassAbbrivation);
		test_steps.add("Enter room class abbreviation : <b>" + roomClassAbbrivation + "</b>");

		roomClass.roomClassMaxAdultsFieldV2.sendKeys(maxAdults);
		roomclassLogger.info("Entered the max audlts : " + maxAdults);
		test_steps.add("Enter max audlts : <b>" + maxAdults + "</b>");

		test_steps.add("Enter max persons : <b>" + maxPersons + "</b>");
		roomClass.roomClassMaxPersonsFieldV2.sendKeys(maxPersons);
		roomclassLogger.info("Entered the max persons : " + maxPersons);

		roomClass.roomClassRoomsTabV2.click();
		roomclassLogger.info("Clicked on Rooms Tab");
		test_steps.add("Click Rooms Tab");

		roomClass.roomClassQuantityFieldV2.sendKeys(roomQuantity);
		roomclassLogger.info("Enterd the rooms quantity : " + roomQuantity);
		test_steps.add("Enter rooms quantity : <b>" + roomQuantity + "</b>");

		roomClass.roomClassQuantityAssignButtonV2.click();
		roomclassLogger.info("Clicked on assign room numbers arrow");
		test_steps.add("Clicked on assign room numbers arrow");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRoomNumberorNameFirstFieldV2), driver);
		roomClass.roomClassRoomNumberorNameFirstFieldV2.clear();

		String RN = Utility.threeDigitgenerateRandomString();
		roomClass.roomClassRoomNumberorNameFirstFieldV2.sendKeys(RN);
		Utility.RoomNo = RN;

		roomclassLogger.info("Entered the room number " + RN);
		test_steps.add("Enter  room number <b>" + RN + "</b>");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.roomClassAssignNumbersButtonV2)));
		roomClass.roomClassAssignNumbersButtonV2.click();
		test_steps.add("Click Assign Numbers button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersDoneButtonV2), driver);
		roomClass.roomClassAssignNumbersDoneButtonV2.click();
		roomclassLogger.info("Clicked on Done button");

		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishButtonV2), driver);
		
		
		HashMap<String, String> rooms= getRoomNoOfRoomClass(driver);
		for(Map.Entry<String, String> entry: rooms.entrySet()) {
			returnRoomNos.add(entry.getValue());
		}
		
		roomclassLogger.info("Rooms Are:" + returnRoomNos);
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			jse.executeScript("arguments[0].click();", roomClass.roomClassPublishButtonV2);

		} catch (Exception e1) {
			roomClass.roomClassPublishButtonV2.click();
		}
		test_steps.add("Clicked on publish button");

		Wait.WaitForElement(driver, OR.roomClassCreatedToaster);
		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassCreatedToaster), driver);
		roomclassLogger.info("Success Toaster dislayed");
		Wait.waitForElementToBeInVisibile(By.xpath(OR.roomClassCreatedToaster), driver);

		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
		Wait.wait3Second();

		WebElement element = driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2));
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			jse.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			element.click();
		}
		test_steps.add("Clicked on Publish Confirmed Dailog: OK button");
		roomclassLogger.info("Clicked on Publish Confirmed Dailog: OK button");

		return returnRoomNos;
	}


	public ArrayList<String> createRoomClassV2( WebDriver driver, ArrayList<String> test_steps, String roomClassName, String roomClassAbbrivation,
			String maxAdults, String maxPersons, String roomQuantity, String startRoomNumber) throws Exception {

		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);

		Wait.WaitForElement(driver, OR.newRoomClassButtonV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRoomClassButtonV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRoomClassButtonV2), driver);

		Utility.ScrollToElement(roomClass.newRoomClassButtonV2, driver);

		roomClass.newRoomClassButtonV2.click();
		roomclassLogger.info("Clicked on NEW ROOMCLASS button");
		test_steps.add("Clicked on NEW ROOMCLASS button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassNameFieldV2), driver);

		roomClass.roomClassNameFieldV2.sendKeys(roomClassName);
		roomclassLogger.info("Entered the room class name : " + roomClassName);
		test_steps.add("Enter room class name : <b>" + roomClassName + "</b>");

		roomClass.roomClassAbbreviationFieldV2.sendKeys(roomClassAbbrivation);
		roomclassLogger.info("Entered the room class abbreviation : " + roomClassAbbrivation);
		test_steps.add("Enter room class abbreviation : <b>" + roomClassAbbrivation + "</b>");

		roomClass.roomClassMaxAdultsFieldV2.sendKeys(maxAdults);
		roomclassLogger.info("Entered the max audlts : " + maxAdults);
		test_steps.add("Enter max audlts : <b>" + maxAdults + "</b>");

		test_steps.add("Enter max persons : <b>" + maxPersons + "</b>");
		roomClass.roomClassMaxPersonsFieldV2.sendKeys(maxPersons);
		roomclassLogger.info("Entered the max persons : " + maxPersons);

		roomClass.roomClassRoomsTabV2.click();
		roomclassLogger.info("Clicked on Rooms Tab");
		test_steps.add("Click Rooms Tab");

		roomClass.roomClassQuantityFieldV2.sendKeys(roomQuantity);
		roomclassLogger.info("Enterd the rooms quantity : " + roomQuantity);
		test_steps.add("Enter rooms quantity : <b>" + roomQuantity + "</b>");

		roomClass.roomClassQuantityAssignButtonV2.click();
		roomclassLogger.info("Clicked on assign room numbers arrow");
		test_steps.add("Clicked on assign room numbers arrow");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRoomNumberorNameFirstFieldV2), driver);
		roomClass.roomClassRoomNumberorNameFirstFieldV2.clear();

		roomClass.roomClassRoomNumberorNameFirstFieldV2.sendKeys(startRoomNumber);
		
		roomclassLogger.info("Entered the room number " + startRoomNumber);
		test_steps.add("Enter  room number <b>" + startRoomNumber + "</b>");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.roomClassAssignNumbersButtonV2)));
		roomClass.roomClassAssignNumbersButtonV2.click();
		test_steps.add("Click Assign Numbers button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersDoneButtonV2), driver);
		roomClass.roomClassAssignNumbersDoneButtonV2.click();
		roomclassLogger.info("Clicked on Done button");

		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishButtonV2), driver);
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			jse.executeScript("arguments[0].click();", roomClass.roomClassPublishButtonV2);

		} catch (Exception e1) {
			roomClass.roomClassPublishButtonV2.click();
		}
		test_steps.add("Clicked on publish button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassCreatedToaster), driver);
		roomclassLogger.info("Success Toaster dislayed");
		Wait.waitForElementToBeInVisibile(By.xpath(OR.roomClassCreatedToaster), driver);

		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
		Wait.wait3Second();

		WebElement element = driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2));
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			jse.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			element.click();
		}
		test_steps.add("Clicked on Publish Confirmed Dailog: OK button");
		roomclassLogger.info("Clicked on Publish Confirmed Dailog: OK button");

		return test_steps;
	}

	public boolean searchRoomClassV2(WebDriver driver, String roomClassName) throws Exception {

		ArrayList<String> test_Steps = new ArrayList<>();
		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);

		boolean isRoomClassFound = false;
		boolean endPagination = false;
		String alphabetFilter = null;
		boolean noRoomClassesFoundMessage = false;

		//Select the room class to delete

		//Get the first letter of roomClassName to filter
		String firstCharOfRoomClass = roomClassName.substring(0,1).trim();
		int unicodeValue = firstCharOfRoomClass.toCharArray()[0];
		if((65<=unicodeValue & unicodeValue<=90) || (97<=unicodeValue & unicodeValue<=122)){
			alphabetFilter = firstCharOfRoomClass.toUpperCase();
		}else if ( 48<=unicodeValue & unicodeValue<=57){
			alphabetFilter = "#";  // default value
		}else {
			alphabetFilter = "All";
		}

		//Filer By clicking on alphabetical symbol
		driver.findElement(By.xpath("//input[@value='" + alphabetFilter +"']//ancestor::label")).click();
		roomclassLogger.info("Clicked on  roomClass alphabet filter: " + alphabetFilter);
		test_Steps.add("Clicked on  roomClass alphabet filter: " + alphabetFilter);

		//Static Wait
		Wait.wait2Second();
//		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPaginationV2), driver);

		//Check whether room classes are displayed or "No Room Classes Found" text displayed
		try{
			if(driver.findElement(By.xpath(OR.noRoomClassesFoundV2)).isDisplayed()){
				noRoomClassesFoundMessage = true;
			}
		}catch(Exception e){

		}

		if(!noRoomClassesFoundMessage) {
			//Get Total matching records displayed text
			String totalRecordsFound = driver.findElement(By.xpath(OR.roomClassRecordsFoundV2)).getText();
			int roomsCount =Integer.parseInt(totalRecordsFound.trim().split(" ")[0]);

			//Get Items per page value
			Utility.ScrollToElement(roomClass.roomClassItemsPerPageDropdownV2, driver);
			String itemsPerPageValue =  driver.findElement(By.xpath(OR.roomClassItemsPerPageDropdownV2)).getText();
			int itemsPerPageVal = Integer.parseInt(itemsPerPageValue.trim());

			//Check that, no of records are more than Items per page value then use pagination and find the room class

			if(roomsCount <= itemsPerPageVal) {
				//No Pagination is required, Get all table rows which represent all room classes data
				List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

				//Run the loop for each row to identify room class
				for(int row = 1; row<=listOfTableRows.size(); row++){
					String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
					if(capturedRoomClassName.equalsIgnoreCase(roomClassName)){
						isRoomClassFound = true;
						roomclassLogger.info("Room class is found");
						test_Steps.add("Room class is found");
					}

					if(isRoomClassFound){
						break;
					}
				}
			}else {
				// Pagination is required and get all the pages
				Utility.ScrollToElement(roomClass.roomClassPaginationV2, driver);
				List<WebElement> paginationValues = driver.findElements(By.xpath(OR.roomClassPaginationListV2));

				// Go page by page ( exclude first and last )
				for(int pageIndex=2; pageIndex<paginationValues.size(); pageIndex++){
					// Click on pagination index
					driver.findElement(By.xpath("//ul[contains(@class, 'RoomClasses_tablePagination')]/li[" + pageIndex + "]")).click();

					//Static Wait
					Wait.wait2Second();

					//Now,Get all table rows which represent all room classes data
					List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

					//Run the loop for each row to identify room class
					for(int row = 1; row<=listOfTableRows.size(); row++){
						String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
						if(capturedRoomClassName.equalsIgnoreCase(roomClassName)){
							isRoomClassFound = true;
							roomclassLogger.info("Room class is found");
							test_Steps.add("Room class is found");
						}

						if(isRoomClassFound){
							break;
						}
					}

					if(endPagination){
						break;
					}
				}
			}
		}else{
			if(isRoomClassFound==false || noRoomClassesFoundMessage == true) {
				roomclassLogger.info("No Room Classes Found with specified room class name: " + roomClassName);
				test_Steps.add("No Room Classes Found with specified room class name: " + roomClassName);
			}
		}

		//Static wait
		Wait.wait2Second();

		return isRoomClassFound;
	}

	public ArrayList<String> deleteRoomClassV2(WebDriver driver, String roomClassName) throws Exception {

		ArrayList<String> test_Steps = new ArrayList<>();
		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);

		boolean isRoomClassFound = false;
		boolean endPagination = false;
		String alphabetFilter = null;
		boolean noRoomClassesFoundMessage = false;
		boolean isRoomClassSeleted = false;

		//Select the room class to delete

		//Get the first letter of roomClassName to filter
		String firstCharOfRoomClass = roomClassName.substring(0,1).trim();
		int unicodeValue = firstCharOfRoomClass.toCharArray()[0];
		if((65<=unicodeValue & unicodeValue<=90) || (97<=unicodeValue & unicodeValue<=122)){
			alphabetFilter = firstCharOfRoomClass.toUpperCase();
		}else if ( 48<=unicodeValue & unicodeValue<=57){
			alphabetFilter = "#";  // default value
		}else {
			alphabetFilter = "All";
		}

		//Filer By clicking on alphabetical symbol
		driver.findElement(By.xpath("//input[@value='" + alphabetFilter +"']//ancestor::label")).click();
		roomclassLogger.info("Clicked on  roomClass alphabet filter: " + alphabetFilter);
		test_Steps.add("Clicked on  roomClass alphabet filter: " + alphabetFilter);

		//Static Wait
		Wait.wait10Second();
	//	Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPaginationV2), driver);

		//Check whether room classes are displayed or "No Room Classes Found" text displayed
		try{
			if(driver.findElement(By.xpath(OR.noRoomClassesFoundV2)).isDisplayed()){
				noRoomClassesFoundMessage = true;
			}
		}catch(Exception e){

		}

		if(!noRoomClassesFoundMessage) {
			//Get Total matching records displayed text
			String totalRecordsFound = driver.findElement(By.xpath(OR.roomClassRecordsFoundV2)).getText();
			int roomsCount =Integer.parseInt(totalRecordsFound.trim().split(" ")[0]);

			//Get Items per page value
			Utility.ScrollToElement(roomClass.roomClassItemsPerPageDropdownV2, driver);
			String itemsPerPageValue =  driver.findElement(By.xpath(OR.roomClassItemsPerPageDropdownV2)).getText();
			int itemsPerPageVal = Integer.parseInt(itemsPerPageValue.trim());

			//Check that, no of records are more than Items per page value then use pagination and find the room class

			if(roomsCount <= itemsPerPageVal) {
				//No Pagination is required, Get all table rows which represent all room classes data
				List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

				//Run the loop for each row to identify room class
				for(int row = 1; row<=listOfTableRows.size(); row++){
					String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
					if(capturedRoomClassName.equalsIgnoreCase(roomClassName)){
						isRoomClassFound = true;
						roomclassLogger.info("Room class is found");
						test_Steps.add("Room class is found");
					}

					if(isRoomClassFound){
						//Once room class is found, select the checkbox
						driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input")).click();
						roomclassLogger.info("Selected the room class to delete");
						test_Steps.add("Selected the room class to delete");

						//Checking whether room class checkbox is checked or not?
						WebElement roomClassCheckbox = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input/.."));
						if(roomClassCheckbox.getAttribute("class").contains("checkbox-checked")){
							isRoomClassSeleted = true;
						}
						break;
					}
				}
			}else {
				// Pagination is required and get all the pages
				Utility.ScrollToElement(roomClass.roomClassPaginationV2, driver);
				List<WebElement> paginationValues = driver.findElements(By.xpath(OR.roomClassPaginationListV2));

				// Go page by page ( exclude first and last )
				for(int pageIndex=2; pageIndex<paginationValues.size(); pageIndex++){
					// Click on pagination index
					driver.findElement(By.xpath("//ul[contains(@class, 'RoomClasses_tablePagination')]/li[" + pageIndex + "]")).click();

					//Static Wait
					Wait.wait2Second();

					//Now,Get all table rows which represent all room classes data
					List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

					//Run the loop for each row to identify room class
					for(int row = 1; row<=listOfTableRows.size(); row++){
						String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
						if(capturedRoomClassName.equalsIgnoreCase(roomClassName)){
							isRoomClassFound = true;
							roomclassLogger.info("Room class is found");
							test_Steps.add("Room class is found");
						}

						if(isRoomClassFound){
							//Once room class is found, select the checkbox
							driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input")).click();
							endPagination = true;
							roomclassLogger.info("Selected the room class to delete");
							test_Steps.add("Selected the room class to delete");

							//Checking whether room class checkbox is checked or not?
							WebElement roomClassCheckbox = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input/.."));
							if(roomClassCheckbox.getAttribute("class").contains("checkbox-checked")){
								isRoomClassSeleted = true;
							}
							break;
						}
					}

					if(endPagination){
						break;
					}
				}
			}

			//If room class is selected and delete button is enabled then click on delete button
			if(isRoomClassSeleted){
				//Click on DELETE button
				if(driver.findElement(By.xpath(OR.roomClassDeleteButtonV2)).isEnabled()) {
					driver.findElement(By.xpath(OR.roomClassDeleteButtonV2)).click();
					roomclassLogger.info("Clicked on DELETE button");
					test_Steps.add("Clicked on DELETE button");
					Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassDeleteConfirmationPopupV2), driver);
				}

				//"Do you want to delete these items?" model pop-up will display
				if(driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupV2)).isDisplayed()){
					roomclassLogger.info("RoomClass deletion confirmation popup is displayed");
					test_Steps.add("RoomClass deletion confirmation popup is displayed");

					//Click on OK button
					Wait.wait2Second();
					//driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)).click();
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					try {
						jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)));
					} catch (Exception e) {
						jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)));
					}

					roomclassLogger.info("Clicked on Deletion Confirmation popup OK button");
					test_Steps.add("Clicked on Deletion Confirmation popup OK button");
				}

				try{

					//Verify the toaster message "Room Classes Deleted Successfully"
					if(driver.findElement(By.xpath("//div[contains(text(),'Room Classes Deleted Successfully')]")).isDisplayed()){
						roomclassLogger.info("Delete confirmation Toaster message is displayed");
						test_Steps.add("Delete confirmation Toaster message is displayed");
					}
				}catch(Exception e){
					roomclassLogger.info("Delete confirmation Toaster message is disappeared");
					test_Steps.add("Delete confirmation Toaster message is disappeared");
				}
			}else{
				roomclassLogger.info("Specified room class does not exist");
				test_Steps.add("Specified room class does not exist");
			}
		}else{
			roomclassLogger.info("No Room Classes Found with specified room class name: " + roomClassName);
			test_Steps.add("No Room Classes Found with specified room class name: " + roomClassName);
		}

		//Static wait
		Wait.wait2Second();
		return test_Steps;
	}

	public ArrayList<String> closeRoomClassTabV2(WebDriver driver, String roomClassName) throws Exception {

		ArrayList<String> test_Steps = new ArrayList<>();

		// Make the room class window as active ( if multiple windows opened also same behaviour )
		driver.findElement(By.xpath("//a[contains(text(),'"+ roomClassName +"')]")).click();
		roomclassLogger.info("Clicked on room class tab and made it as active tab");
		test_Steps.add("Clicked on room class tab and made it as active tab");

		//Close the active room class window
		String path="//a[contains(text(),'"+ roomClassName +"')]//following-sibling::a";
		Wait.WaitForElement(driver, path);
		WebElement element= driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.click();
		//driver.findElement(By.xpath("//a[contains(text(),'"+ roomClassName +"')]//following-sibling::a")).click();
		roomclassLogger.info("Closed the room class tab");
		test_Steps.add("Closed the room class tab");

		//After closing the room class tab, room class main page will display hence wait until
		try{
			Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRecordsFoundV2), driver);
		}catch(Exception e) {
			driver.navigate().refresh();

			Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRecordsFoundV2), driver);
		}

		return test_Steps;
	}

	public ArrayList<String> getAbbrivation(WebDriver driver,String delim, String roomClassNames,ArrayList<String> test_Steps ) throws Exception{


		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClassNames, delim);
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		for(String roomClass : roomClassList) {
			boolean flag = searchRoomClassV2(driver, roomClass);

			if(flag) {
				String path = "//tbody[@class='ant-table-tbody']//tr//td/a[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+roomClass.toUpperCase()+"']/../following::td";
				RoomAbbri.add(driver.findElement(By.xpath(path)).getText());
			}else {
				RoomAbbri.add("NA");
			}

		}
		roomclassLogger.info("Abbrivation for room classes are : "+RoomAbbri);
		test_Steps.add("Abbrivation for room classes are : "+RoomAbbri);
		return RoomAbbri;
	}

	public ArrayList<String> createRoomClassWithDescriptionV2(WebDriver driver, ArrayList<String> test_steps, String roomClassName,String roomClassAB,String policy,String sortOrder,String maxAdults,String maxPerson,String details,String roomQuant,String roomName,String statinId,String sortOrder1,String zone) throws InterruptedException
	{
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		//	res.roomClassNamev2.clear();
		String randomNumber= "1234567890";
		randomNumber= Utility.GenerateRandomNumber();
		//roomClassName=roomClassName+randomNumber;
		//roomClassAB=roomClassAB+randomNumber;
		details=details +randomNumber;
		res.roomClassNamev2.sendKeys(roomClassName);
		test_steps.add("Enter RoomClassName : " + roomClassName);
		roomclassLogger.info("Enter RoomClassName : " + roomClassName);
		res.roomClassABv2.clear();
		res.roomClassABv2.sendKeys(roomClassAB);
		test_steps.add("Enter RoomClassAB : " + roomClassAB);
		roomclassLogger.info("Enter RoomClassAB : " + roomClassAB);
		res.sortOrderv2.clear();
		res.sortOrderv2.sendKeys(sortOrder);
		test_steps.add("Enter SortOrder : " + sortOrder);
		roomclassLogger.info("Enter SortOrder : " + sortOrder);
		res.maxAdultv2.clear();
		res.maxAdultv2.sendKeys(maxAdults);
		test_steps.add("Enter MaxAdults : " + maxAdults);
		roomclassLogger.info("Enter MaxAdults : " + maxAdults);
		res.maxPersonv2.clear();
		res.maxPersonv2.sendKeys(maxPerson);
		test_steps.add("Enter MaxPerson : " + maxPerson);
		roomclassLogger.info("Enter MaxPerson : " + maxPerson);
		res.smokingPolicyv2.click();
		String smokingpolicy = "//ul[@role='listbox']/li[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+policy.toUpperCase()+"']";

		roomclassLogger.info(smokingpolicy);
		Wait.WaitForElement(driver, smokingpolicy);
		driver.findElement(By.xpath(smokingpolicy)).click();
		test_steps.add("Selecting the smokingpolicy : " + policy);
		roomclassLogger.info("Selecting the smokingpolicy : " + policy);
		res.editDescription.click();
		test_steps.add("click editdescription");
		roomclassLogger.info("click editdescription");
		res.detailsv2.clear();
		res.detailsv2.sendKeys(details);
		test_steps.add("Enter details : " + details);
		roomclassLogger.info("Enter details : " + details);
		WebDriverWait wait = new WebDriverWait(driver, 90);

		Wait.WaitForElement(driver, OR.donev2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.donev2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.donev2), driver);

		Utility.ScrollToElement(res.donev2, driver);

		res.donev2.click();
		test_steps.add("Clicking_done");
		roomclassLogger.info("Clicking_done");
		res.roomsv2.click();
		test_steps.add("Clicking New Room Tab");
		roomclassLogger.info("Clicking New Room Tab");
		res.roomsquantityv2.clear();
		res.roomsquantityv2.sendKeys(roomQuant);
		test_steps.add("Enter RoomQuantity : " + roomQuant);
		roomclassLogger.info("Enter RoomQuantity : " + roomQuant);
		res.roomquantityButtonv2.click();
		test_steps.add("Addding quantity");
		roomclassLogger.info("Addding quantity");
		res.roomNamev2.clear();
		res.roomNamev2.sendKeys(roomName);
		test_steps.add("Enter RoomName : " + roomName);
		roomclassLogger.info("Enter RoomName : " + roomName);
		res.stationIdv2.clear();
		res.stationIdv2.sendKeys(statinId);
		test_steps.add("Enter StationId : " + statinId);
		roomclassLogger.info("Enter StationId : " + statinId);
		res.sortOrder.clear();
		res.sortOrder.sendKeys(sortOrder1);
		test_steps.add("Enter Sortorder : " + sortOrder1);
		roomclassLogger.info("Enter Sortorder : " + sortOrder1);
		res.houseKeepingZonev2.click();
		String zonev2 = "//ul[@role='listbox']/li[text()='"+zone+"']";
		roomclassLogger.info(zonev2);
		Wait.WaitForElement(driver, zonev2);
		driver.findElement(By.xpath(zonev2)).click();
		test_steps.add("Enter Zone : " + zonev2);
		roomclassLogger.info("Enter Zone : " + zonev2);
		res.roomClassAssignNumbersButtonV2.click();
		Utility.app_logs.info("clicked on assign room numbers");
		Wait.waitForElementToBeVisibile(By.xpath(OR.doneButtonv2), driver);
		res.doneButtonv2.click();
		test_steps.add("Clicking on done button");
		roomclassLogger.info("Clicking on done button");
		Wait.waitForElementToBeClickable(By.xpath(OR.publishv2), driver);
		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		roomclassLogger.info("Clicking on publish button");
		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
		//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
		Wait.wait5Second();
		res.roomClassPublishConfirmedOKButtonV2.click();
		return test_steps;
	}

	public ArrayList<String> updateRoomClassAbbrV2(WebDriver driver, ArrayList<String> test_steps,
			String roomClass,String roomClassAB)throws InterruptedException {
		String roomclassname = "//a[@class='roomClassLink' and text()='"+roomClass+"']";

		roomclassLogger.info(roomclassname);
		Wait.WaitForElement(driver, roomclassname);
		driver.findElement(By.xpath(roomclassname)).click();
		test_steps.add("Navigating to room class page : " + roomClass);
		roomclassLogger.info("Navigating to room class page : " + roomClass);
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.roomClassABv2.clear();
		res.roomClassABv2.sendKeys(roomClassAB);
		test_steps.add("update RoomClassAB : " + roomClassAB);
		roomclassLogger.info("update RoomClassAB : " + roomClassAB);
		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		roomclassLogger.info("Clicking on publish button");
		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
		Wait.wait5Second();
		res.roomClassPublishConfirmedOKButtonV2.click();
		return test_steps;
	}

	public ArrayList<String> updateRoomClassSmokeingPolicyV2(WebDriver driver, ArrayList<String> test_steps, 
			String roomClass,String policy)throws InterruptedException {
		String roomclassname = "//a[@class='roomClassLink' and text()='"+roomClass+"']";

		roomclassLogger.info(roomclassname);
		Wait.WaitForElement(driver, roomclassname);
		driver.findElement(By.xpath(roomclassname)).click();
		test_steps.add("Navigating to room class page : " + roomClass);
		roomclassLogger.info("Navigating to room class page : " + roomClass);
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.smokingPolicyv2.click();
		String smokingpolicy = "//ul[@role='listbox']/li[text()='"+policy+"']";

		roomclassLogger.info(smokingpolicy);
		Wait.WaitForElement(driver, smokingpolicy);
		driver.findElement(By.xpath(smokingpolicy)).click();
		test_steps.add("Selecting the smokingpolicy : " + policy);
		roomclassLogger.info("Selecting the smokingpolicy : " + policy);

		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		roomclassLogger.info("Clicking on publish button");
		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
		//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
		Wait.wait5Second();
		res.roomClassPublishConfirmedOKButtonV2.click();
		return test_steps;
	}

	public ArrayList<String> updateRoomClassMaxAdutlsAndPersonsV2(WebDriver driver, ArrayList<String> test_steps, 
			String roomClass,String maxAdults,String maxPerson)throws InterruptedException {
		String roomclassname = "//a[@class='roomClassLink' and text()='"+roomClass+"']";
		roomclassLogger.info(roomclassname);
		Wait.WaitForElement(driver, roomclassname);
		driver.findElement(By.xpath(roomclassname)).click();
		test_steps.add("Navigating to room class page : " + roomClass);
		roomclassLogger.info("Navigating to room class page : " + roomClass);
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.maxAdultv2.clear();
		res.maxAdultv2.sendKeys(maxAdults);
		test_steps.add("update MaxAdults : " + maxAdults);
		roomclassLogger.info("update MaxAdults : " + maxAdults);
		res.maxPersonv2.clear();
		res.maxPersonv2.sendKeys(maxPerson);
		test_steps.add("Enter MaxPerson : " + maxPerson);
		roomclassLogger.info("Enter MaxPerson : " + maxPerson);
		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		roomclassLogger.info("Clicking on publish button");
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

		roomclassLogger.info(roomclassname);
		Wait.WaitForElement(driver, roomclassname);
		driver.findElement(By.xpath(roomclassname)).click();
		test_steps.add("Navigating to room class page : " + roomClass);
		roomclassLogger.info("Navigating to room class page : " + roomClass);
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.roomClassNamev2.clear();
		res.roomClassNamev2.sendKeys(roomClassName);
		test_steps.add("update RoomClassName : " + roomClassName);
		roomclassLogger.info("update RoomClassName : " + roomClassName);
		res.publishv2.click();
		test_steps.add("Clicking on publish button");
		roomclassLogger.info("Clicking on publish button");
		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
		//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
		Wait.wait5Second();
		res.roomClassPublishConfirmedOKButtonV2.click();
		return test_steps;
	}

	public ArrayList<String> getAllRoomClassesListV2(WebDriver driver, ArrayList<String> test_steps)throws InterruptedException
	{
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> allroomclasseslist = new ArrayList<>();

		List<WebElement> listOfRoomNames = driver.findElements(By.xpath(OR.roomClassTable));
		for(int i = 1; i<=listOfRoomNames.size(); i++){
			String getAllRoomClassesListV2 =driver.findElement(By.xpath("//table//tbody/tr" + "[" + i + "]" + "//a[@class='roomClassLink']")).getText();
			allroomclasseslist.add(getAllRoomClassesListV2);
			roomclassLogger.info("All room classes are : "+allroomclasseslist);
			test_Steps.add("All room classes are : "+allroomclasseslist);


		}
		return allroomclasseslist;
	}

	public void selectStatus(WebDriver driver, ArrayList<String> test_steps,String status) {
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		Wait.WaitForElement(driver, OR.roomClassStatusList);
		res.roomClassStatusList.click();
		String statusselect = "//li[text()='"+ status +"']";
		roomclassLogger.info(statusselect);
		Wait.WaitForElement(driver, statusselect);
		driver.findElement(By.xpath(statusselect)).click();
		test_steps.add("Selecting the Status : " + status);
		roomclassLogger.info("Selecting the Status : " + status);
	}
	public ArrayList<String> getAllActiveRoomClassesListV2(WebDriver driver, ArrayList<String> test_steps,String status)throws InterruptedException
	{
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> activeroomclasses = new ArrayList<>();
		Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
		res.roomClassStatusList.click();
		String statusselect = "//li[text()='"+ status +"']";
		roomclassLogger.info(statusselect);
		Wait.WaitForElement(driver, statusselect);
		driver.findElement(By.xpath(statusselect)).click();
		test_steps.add("Selecting the Status : " + status);
		roomclassLogger.info("Selecting the Status : " + status);
		List<WebElement> listOfActiveRoomNames = driver.findElements(By.xpath(OR.roomClassTable));
		for(int i = 1; i<=listOfActiveRoomNames.size(); i++){
			String getAllRoomClassesListV2 =driver.findElement(By.xpath("//table//tbody/tr" + "[" + i + "]" + "//a[@class='roomClassLink']")).getText();
			activeroomclasses.add(getAllRoomClassesListV2);
			roomclassLogger.info("All active room classes are : "+getAllRoomClassesListV2);
			test_Steps.add("All active room classes are : "+getAllRoomClassesListV2);

		}
		return activeroomclasses;
	}


	public String getRoomClassZone(WebDriver driver) {
		String zone= null;
		Elements_NewRoomClassV2 res = new Elements_NewRoomClassV2(driver);
		zone=res.roomClassRoomHousekeepingZoneFieldV2.getAttribute("title");
		return zone;
	}


	public ArrayList<String> createRoomClassWithZoneV2(WebDriver driver, String roomClassName,
			String roomClassAbbrivation, String maxAdults, String maxPersons, String roomQuantity, String zone,ExtentTest test,
			ArrayList<String> test_steps) throws Exception {

		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);

		Wait.WaitForElement(driver, OR.newRoomClassButtonV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRoomClassButtonV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRoomClassButtonV2), driver);

		Utility.ScrollToElement(roomClass.newRoomClassButtonV2, driver);

		roomClass.newRoomClassButtonV2.click();
		roomclassLogger.info("Clicked on NEW ROOMCLASS button");
		test_steps.add("Clicked on NEW ROOMCLASS button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassNameFieldV2), driver);

		roomClass.roomClassNameFieldV2.sendKeys(roomClassName);
		roomclassLogger.info("Entered the room class name : " + roomClassName);
		test_steps.add("Enter room class name : <b>" + roomClassName + "</b>");

		roomClass.roomClassAbbreviationFieldV2.sendKeys(roomClassAbbrivation);
		roomclassLogger.info("Entered the room class abbreviation : " + roomClassAbbrivation);
		test_steps.add("Enter room class abbreviation : <b>" + roomClassAbbrivation + "</b>");

		roomClass.roomClassMaxAdultsFieldV2.sendKeys(maxAdults);
		roomclassLogger.info("Entered the max audlts : " + maxAdults);
		test_steps.add("Enter max audlts : <b>" + maxAdults + "</b>");

		test_steps.add("Enter max persons : <b>" + maxPersons + "</b>");
		roomClass.roomClassMaxPersonsFieldV2.sendKeys(maxPersons);
		roomclassLogger.info("Entered the max persons : " + maxPersons);

		roomClass.roomClassRoomsTabV2.click();
		roomclassLogger.info("Clicked on Rooms Tab");
		test_steps.add("Click Rooms Tab");

		roomClass.roomClassQuantityFieldV2.sendKeys(roomQuantity);
		roomclassLogger.info("Enterd the rooms quantity : " + roomQuantity);
		test_steps.add("Enter rooms quantity : <b>" + roomQuantity + "</b>");

		roomClass.roomClassQuantityAssignButtonV2.click();
		roomclassLogger.info("Clicked on assign room numbers arrow");
		test_steps.add("Clicked on assign room numbers arrow");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRoomNumberorNameFirstFieldV2), driver);
		roomClass.roomClassRoomNumberorNameFirstFieldV2.clear();

		String RN = Utility.generateRandomNumber();
		roomClass.roomClassRoomNumberorNameFirstFieldV2.sendKeys(RN);
		Utility.RoomNo = RN;
		roomclassLogger.info("Entered the room number " + RN);
		test_steps.add("Enter  room number <b>" + RN + "</b>");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.roomClassAssignNumbersButtonV2)));
		roomClass.roomClassAssignNumbersButtonV2.click();
		test_steps.add("Click Assign Numbers button");
		roomclassLogger.info("Click Assign Numbers button");

		for(int i=0;i<roomQuantity.length();i++) {
			String zonePath="//div[@id='rooms["+i+"].housekeepingZoneId']/div/div/div";
			Wait.waitForElementToBeVisibile(By.xpath(zonePath), driver);
			WebElement element=driver.findElement(By.xpath(zonePath));
			Utility.ScrollToElement(element, driver);
			element.click();
			test_steps.add("Click On Zone Drop Down Box");
			roomclassLogger.info("Click On Zone Drop Down Box");
			String path="//ul[@role='listbox']//li[text()='"+zone+"']";
			WebElement elements=driver.findElement(By.xpath(path));
			Utility.ScrollToElement(elements, driver);
			Utility.clickThroughJavaScript(driver, elements);
			test_steps.add("Select Zone " +zone);
			roomclassLogger.info("Select Zone " +zone);
		}


		Wait.WaitForElement(driver, OR.roomClassAssignNumbersDoneButtonV2);
		//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersDoneButtonV2), driver);
		Utility.ScrollToUp(driver);
		roomClass.roomClassAssignNumbersDoneButtonV2.click();
		roomclassLogger.info("Clicked on Done button");

		Wait.WaitForElement(driver, OR.roomClassPublishButtonV2);
		Utility.ScrollToElement(driver.findElement(By.xpath(OR.roomClassPublishButtonV2)), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishButtonV2), driver);
		roomClass.roomClassPublishButtonV2.click();
		test_steps.add("Clicked on publish button");
		roomclassLogger.info("Clicked on publish button");

		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedDailogV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedDailogV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
		Wait.wait5Second();
		driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2)).click();
		test_steps.add("Clicked on Publish Confirmed Dailog: OK button");
		roomclassLogger.info("Clicked on Publish Confirmed Dailog: OK button");

		return test_steps;
	}


	/*public HashMap<String, ArrayList<String>> (WebDriver driver, ArrayList<String> test_steps) throws InterruptedException, Exception {
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);
		ArrayList<String> activeRoomClassList = getAllActiveRoomClassesListV2(driver, test_steps, "Active");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (String roomClassName : activeRoomClassList) {
			String roomClassLocator = String.format("//table//tbody/tr/td[3]/a[text()='%s']", roomClassName);
			Wait.WaitForElement(driver, roomClassLocator);
			WebElement element = driver.findElement(By.xpath(roomClassLocator));
			element.click();
			ArrayList<String> roomClassDetailList = new ArrayList<>();

			Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
			String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);
			roomClassDetailList.add(abbreviationTextValue.trim());

			Wait.WaitForElement(driver, OR.roomClassSortOrderFieledV2);
			String sortOrderTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassSortOrderFieledV2);
			roomClassDetailList.add(sortOrderTextValue.trim());

			Wait.WaitForElement(driver, OR.roomClassMaxAdultsFieldV2);
			String maxAdultTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxAdultsFieldV2);
			roomClassDetailList.add(maxAdultTextValue.trim());

			Wait.WaitForElement(driver, OR.New_RoomCLass_Max_Persons1);
			String maxPersonsTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxPersonsFieldV2);
			roomClassDetailList.add(maxPersonsTextValue.trim());

			map.put(roomClassName, roomClassDetailList);
			closeRoomClassTabV2(driver, roomClassName);
		}
		return map;
	}
*/
	/*public ArrayList<String> getSortOrderMap(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		HashMap<Integer, ArrayList<String>> map = new HashMap<>();
		Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);

		ArrayList<String> activeRoomClassList = getAllActiveRoomClassesListV2(driver, test_steps, "Active");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (String roomClassName : activeRoomClassList) {
			String roomClassLocator = String.format("//table//tbody/tr/td[3]/a[text()='%s']", roomClassName);
			Wait.WaitForElement(driver, roomClassLocator);
			WebElement element = driver.findElement(By.xpath(roomClassLocator));
			element.click();

			Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
			String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);

			ele.roomClassRoomsTabV2.click();

			String physicalRoomNameLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@class,'PhysicalRooms_roomName')]";
			String physicalRoomSortOrderLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@id,'sortOrder')]";

			if(Utility.isElementPresent(driver, By.xpath(physicalRoomNameLocator))) {
				List<WebElement> physicalRoomNameList = driver.findElements(By.xpath(physicalRoomNameLocator));
				List<WebElement> physicalRoomSortOrderList = driver.findElements(By.xpath(physicalRoomSortOrderLocator));
				for(int i = 0; i < physicalRoomNameList.size(); i++) {
					int sortOrder = Integer.parseInt(((String) jse.executeScript("return arguments[0].value", physicalRoomSortOrderList.get(i))).trim());
					String physicalRoomName = abbreviationTextValue + "|" + ((String) jse.executeScript("return arguments[0].value", physicalRoomNameList.get(i))).trim();
					if (!map.containsKey(sortOrder)) {
						map.put(sortOrder, new ArrayList<>());
					}
					map.get(sortOrder).add(physicalRoomName);
				}
			}

			String virtualRoomNameLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[2]";
			String virtualRoomSortOrderLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[3]";

			if(Utility.isElementPresent(driver, By.xpath(virtualRoomNameLocator))) {
				List<WebElement> virtualRoomNameList = driver.findElements(By.xpath(virtualRoomNameLocator));
				List<WebElement> virtualRoomSortOrderList = driver.findElements(By.xpath(virtualRoomSortOrderLocator));

				for(int i = 0; i < virtualRoomSortOrderList.size(); i++) {
					int sortOrder = Integer.parseInt(virtualRoomSortOrderList.get(i).getText().trim());
					String virtualRoomName = virtualRoomNameList.get(i).getText().trim();

					if (!map.containsKey(sortOrder)) {
						map.put(sortOrder, new ArrayList<>());
					}
					map.get(sortOrder).add(virtualRoomName);
				}
			}
			closeRoomClassTabV2(driver, roomClassName);
		}

		List<Integer> keys = new ArrayList<>(map.keySet());
		keys.sort(Comparator.comparingInt(o -> o));
		ArrayList<String> sortedList = new ArrayList<>();
		for(int key : keys) {
			sortedList.addAll(map.get(key));
		}

		return sortedList;
	}*/

	/*public HashMap<String, ArrayList<String>> getGroupBySortOrderMap(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		HashMap<String, ArrayList<String>> sortOrderMap = new HashMap<>();
		Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);

		ArrayList<String> activeRoomClassList = getAllActiveRoomClassesListV2(driver, test_steps, "Active");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (String roomClassName : activeRoomClassList) {
			HashMap<Integer, ArrayList<String>> map = new HashMap<>();

			String roomClassLocator = String.format("//table//tbody/tr/td[3]/a[text()='%s']", roomClassName);
			Wait.WaitForElement(driver, roomClassLocator);
			WebElement element = driver.findElement(By.xpath(roomClassLocator));
			element.click();

			Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
			String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);

			ele.roomClassRoomsTabV2.click();

			String physicalRoomNameLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@class,'PhysicalRooms_roomName')]";
			String physicalRoomSortOrderLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@id,'sortOrder')]";

			if(Utility.isElementPresent(driver, By.xpath(physicalRoomNameLocator))) {
				List<WebElement> physicalRoomNameList = driver.findElements(By.xpath(physicalRoomNameLocator));
				List<WebElement> physicalRoomSortOrderList = driver.findElements(By.xpath(physicalRoomSortOrderLocator));

				for(int i = 0; i < physicalRoomNameList.size(); i++) {
					int sortOrder = Integer.parseInt(((String) jse.executeScript("return arguments[0].value", physicalRoomSortOrderList.get(i))).trim());
					String physicalRoomName = ((String) jse.executeScript("return arguments[0].value", physicalRoomNameList.get(i))).trim();
					if (!map.containsKey(sortOrder)) {
						map.put(sortOrder, new ArrayList<>());
					}
					map.get(sortOrder).add(physicalRoomName);
				}
			}

			String virtualRoomNameLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[2]";
			String virtualRoomSortOrderLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[3]";

			if(Utility.isElementPresent(driver, By.xpath(virtualRoomNameLocator))) {
				List<WebElement> virtualRoomNameList = driver.findElements(By.xpath(virtualRoomNameLocator));
				List<WebElement> virtualRoomSortOrderList = driver.findElements(By.xpath(virtualRoomSortOrderLocator));

				for(int i = 0; i < virtualRoomSortOrderList.size(); i++) {
					int sortOrder = Integer.parseInt(virtualRoomSortOrderList.get(i).getText().trim());
					String virtualRoomName = virtualRoomNameList.get(i).getText().trim();

					if (!map.containsKey(sortOrder)) {
						map.put(sortOrder, new ArrayList<>());
					}
					map.get(sortOrder).add(virtualRoomName);
				}
			}
			closeRoomClassTabV2(driver, roomClassName);

			ArrayList<String> resultList = new ArrayList<>();
			List<Integer> sortedList = new ArrayList<>(map.keySet());
			sortedList.sort(Comparator.comparingInt(o -> o));
			for(Integer key : sortedList) {
				ArrayList<String> roomNumberList = map.get(key);
				resultList.addAll(roomNumberList);

			}
			sortOrderMap.put(roomClassName, resultList);
		}
		return sortOrderMap;
	}*/

	public void deleteRoomClassIfExist(WebDriver driver, ArrayList<String> test_steps, String roomClass) throws Exception {
		Elements_NewRoomClassV2 elements = new Elements_NewRoomClassV2(driver);

		String alphabet = "//label[contains(@class,'RoomClasses')]//span[text()='"+Character.toUpperCase(roomClass.charAt(0))+"']";
		Wait.waitForElementToBeVisibile(By.xpath(alphabet), driver);
		driver.findElement(By.xpath(alphabet)).click();
		Wait.waitForElementToBeVisibile(By.xpath("//div[@class='ant-table-column-sorters' and text()='Room Class Name']"), driver);

		List<WebElement> checkBoxes = driver.findElements(
				By.xpath("//a[contains(text(),'"+roomClass+"')]/../..//input[@type='checkbox']"));
		if (checkBoxes.size()>0) {
			for (WebElement checkBox : checkBoxes) {
				Utility.clickThroughJavaScript(driver, checkBox);
			}
			Utility.clickThroughJavaScript(driver, elements.roomClassDeleteButtonV2);
			Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2), driver);
			Utility.clickThroughJavaScript(driver, elements.roomClassDeleteConfirmationPopupOKButtonV2);	
			try {
				Wait.waitForElementToBeVisibile(By.xpath("//div[contains(text(),'Room Classes Deleted Successfully')]"), driver);
			} catch (Exception e) {
				// TODO: handle exception
			}
			test_steps.add("Room class deleted successfully with name as : <b>"+roomClass+"</b>");
		} else {
			test_steps.add("Room class doesn't exist with name as : <b>"+roomClass+"</b>");
		}
	}

	public HashMap<String, String> openRoomClassAndGetDataFromRoomTab(WebDriver driver, ArrayList<String> test_steps, String roomClass) throws Exception{
		Navigation navigation = new Navigation();
		HashMap<String, String> roomDetails = new HashMap<>();
		navigation.Setup(driver);
		navigation.RoomClass(driver);
		if (searchForRoomClassEistsOrNot(driver, test_steps, roomClass)) {
			openRoomClass(driver, test_steps, roomClass);		
			roomDetails = getDataFromRoomTab(driver, test_steps);
		}
		return roomDetails;
	}

	public boolean searchForRoomClassEistsOrNot(WebDriver driver, ArrayList<String> test_steps, String roomClassName)
			throws Exception {
		boolean roomClassExists = false;
		char ch = roomClassName.charAt(0);
		String str = ("" + ch).toUpperCase();
		String searchLetter = "//input[@value='"+str+"']/../..";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Wait.waitForElementToBeClickable(By.xpath(searchLetter), driver);
		WebElement Search = driver.findElement(By.xpath(searchLetter));
		js.executeScript("arguments[0].click(true);", Search);
		//		Wait.waitForElementToBeVisibile(By.xpath(OR.SearchRoomClass), driver);
		if (driver.findElements(By.xpath("//a[contains(text(),'" + roomClassName + "')]")).size() > 0) {
			test_steps.add("newly created class room validated successfully");
			roomClassExists = true;
		}
		return roomClassExists;
	}

	public void openRoomClass(WebDriver driver, ArrayList<String> test_steps, String roomClass) throws Exception{
		String roomClassXpath = "//a[contains(text(),'" + roomClass + "')]";
		Wait.waitForElementToBeClickable(By.xpath(roomClassXpath), driver);
		driver.findElement(By.xpath(roomClassXpath)).click();
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR.NewRoomClassPublish), driver);
			test_steps.add("Opened <b>"+roomClass+"</b> room class");			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public HashMap<String, String> getDataFromRoomTab(WebDriver driver, ArrayList<String> test_steps ) throws Exception{
		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		HashMap<String, String> roomDetails = new HashMap<>();
		String abbrivation = roomClass.New_RoomClass_Abbrivation1.getAttribute("value");
		String maxPersons = roomClass.New_RoomCLass_Max_Persons1.getAttribute("value");
		String maxAdults = roomClass.New_RoomClass_Max_Adults1.getAttribute("value");
		roomDetails.put("Abbreviation", abbrivation);
		roomDetails.put("Max Persons", maxPersons);
		roomDetails.put("Max Adults", maxAdults);
		return roomDetails;
	}

	public ArrayList<String> getAllActiveRoomClassNames(WebDriver driver) throws InterruptedException {

		Elements_NewRoomClass roomClass = new Elements_NewRoomClass(driver);
		ArrayList<String> roomClasses = new ArrayList<>();
		String pathOfTotalRecords = "//tbody[@class='ant-table-tbody']//tr";
		String pathOfStatus = "";
		String getStats = "";
		String pathOfRoomClassName = "";
		String getRoomClassName = "";
		boolean isRoomClassExist = false;

		int pagesSize = roomClass.RoomClassPagesfixed.size();
		roomclassLogger.info("Page size: " + pagesSize);
		if (pagesSize == 1) {
			List<WebElement> getTotalRecord = driver.findElements(By.xpath(pathOfTotalRecords));

			roomclassLogger.info("size: " + getTotalRecord.size());
			for (int i = 1; i <= getTotalRecord.size(); i++) {
				//pathOfStatus = "(//tbody[@class='ant-table-tbody']//tr)[" + i + "]//td[5]";
				pathOfStatus = "//tbody[@class='ant-table-tbody']//tr["+i+"]//td[6]";
				getStats = driver.findElement(By.xpath(pathOfStatus)).getText();
				getStats = getStats.trim();
				if (getStats.equalsIgnoreCase("Active") || getStats.contains("Active")) {
					//pathOfRoomClassName = "(//tbody[@class='ant-table-tbody']//tr)[" + i + "]//td[2]//a";
					pathOfRoomClassName = "//tbody[@class='ant-table-tbody']//tr["+i+"]//td[3]/a";
					WebElement roomClassElement = driver.findElement(By.xpath(pathOfRoomClassName));
					getRoomClassName = roomClassElement.getText();
					getRoomClassName = getRoomClassName.trim();
					roomClasses.add(getRoomClassName);
					isRoomClassExist = true;
				}
			}
		} else {
			for (int j = 0; j < roomClass.RoomClassPagesfixed.size(); j++) {
				Utility.ScrollToElement(roomClass.RoomClassPagesfixed.get(j), driver);
				roomClass.RoomClassPagesfixed.get(j).click();
				roomclassLogger.info("roomClass Page Size : " + roomClass.RoomClassPagesfixed.size());

				List<WebElement> getTotalRecord = driver.findElements(By.xpath(pathOfTotalRecords));

				for (int i = 1; i <= getTotalRecord.size(); i++) {
					//pathOfStatus = "(//tbody[@class='ant-table-tbody']//tr)[" + i + "]//td[5]";
					pathOfStatus = "//tbody[@class='ant-table-tbody']//tr["+i+"]//td[6]";
					getStats = driver.findElement(By.xpath(pathOfStatus)).getText();
					getStats = getStats.trim();
					if (getStats.equalsIgnoreCase("Active") || getStats.contains("Active")) {
						//pathOfRoomClassName = "(//tbody[@class='ant-table-tbody']//tr)[" + i + "]//td[2]//a";
						pathOfRoomClassName = "//tbody[@class='ant-table-tbody']//tr["+i+"]//td[3]/a";
						WebElement roomClassElement = driver.findElement(By.xpath(pathOfRoomClassName));
						getRoomClassName = roomClassElement.getText();
						getRoomClassName = getRoomClassName.trim();
						roomClasses.add(getRoomClassName);
						isRoomClassExist = true;
					}
				}

			}
		}
		if (!isRoomClassExist) {
			roomclassLogger.info("No active room class exist");
		}
		return roomClasses;
	}
    
    public ArrayList<String> createRoomClassV2( WebDriver driver, String roomClassName, String roomClassAbbrivation, 
			String maxAdults, String maxPersons, String roomQuantity, 
			ExtentTest test, ArrayList<String> test_steps) throws Exception {

		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);

		Wait.WaitForElement(driver, OR.newRoomClassButtonV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRoomClassButtonV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRoomClassButtonV2), driver);

		Utility.ScrollToElement(roomClass.newRoomClassButtonV2, driver);

		roomClass.newRoomClassButtonV2.click();
		roomclassLogger.info("Clicked on NEW ROOMCLASS button");
		test_steps.add("Clicked on NEW ROOMCLASS button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassNameFieldV2), driver);

		roomClass.roomClassNameFieldV2.sendKeys(roomClassName);
		roomclassLogger.info("Entered the room class name : " + roomClassName);
		test_steps.add("Enter room class name : <b>" + roomClassName + "</b>");

		roomClass.roomClassAbbreviationFieldV2.sendKeys(roomClassAbbrivation);
		roomclassLogger.info("Entered the room class abbreviation : " + roomClassAbbrivation);
		test_steps.add("Enter room class abbreviation : <b>" + roomClassAbbrivation + "</b>");

		roomClass.roomClassMaxAdultsFieldV2.sendKeys(maxAdults);
		roomclassLogger.info("Entered the max audlts : " + maxAdults);
		test_steps.add("Enter max audlts : <b>" + maxAdults + "</b>");

		test_steps.add("Enter max persons : <b>" + maxPersons + "</b>");
		roomClass.roomClassMaxPersonsFieldV2.sendKeys(maxPersons);
		roomclassLogger.info("Entered the max persons : " + maxPersons);

		roomClass.roomClassRoomsTabV2.click();
		roomclassLogger.info("Clicked on Rooms Tab");
		test_steps.add("Click Rooms Tab");

		roomClass.roomClassQuantityFieldV2.sendKeys(roomQuantity);
		roomclassLogger.info("Enterd the rooms quantity : " + roomQuantity);
		test_steps.add("Enter rooms quantity : <b>" + roomQuantity + "</b>");

		roomClass.roomClassQuantityAssignButtonV2.click();
		roomclassLogger.info("Clicked on assign room numbers arrow");
		test_steps.add("Clicked on assign room numbers arrow");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRoomNumberorNameFirstFieldV2), driver);
		roomClass.roomClassRoomNumberorNameFirstFieldV2.clear();

		String RN = "1";
		roomClass.roomClassRoomNumberorNameFirstFieldV2.sendKeys("1");
		Utility.RoomNo = "1";
		roomclassLogger.info("Entered the room number " + RN);
		test_steps.add("Enter  room number <b>" + RN + "</b>");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.roomClassAssignNumbersButtonV2)));
		roomClass.roomClassAssignNumbersButtonV2.click();
		test_steps.add("Click Assign Numbers button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersDoneButtonV2), driver);
		roomClass.roomClassAssignNumbersDoneButtonV2.click();
		roomclassLogger.info("Clicked on Done button");

		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishButtonV2), driver);
		roomClass.roomClassPublishButtonV2.click();
		roomclassLogger.info("Clicked on publish button");
		test_steps.add("Clicked on publish button");
		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedDailogV2), driver);
		Wait.waitUntilButtonIsEnabledByLocator(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver, 60);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
		roomclassLogger.info("Page Load");
		WebElement element= driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2));
		Utility.ScrollToElement(element, driver);
		roomclassLogger.info("Scroll Done");
		Wait.wait15Second();
		driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2)).click();
		test_steps.add("Clicked on Publish Confirmed Dailog: OK button");
		roomclassLogger.info("Clicked on Publish Confirmed Dailog: OK button");

		return test_steps;
	}




	/*public ArrayList<String> createRoomClassV2( WebDriver driver, String roomClassName, String roomClassAbbrivation, 
			String maxAdults, String maxPersons, String roomQuantity, 
			ExtentTest test, ArrayList<String> test_steps) throws Exception {

		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);

		Wait.WaitForElement(driver, OR.newRoomClassButtonV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRoomClassButtonV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRoomClassButtonV2), driver);

		Utility.ScrollToElement(roomClass.newRoomClassButtonV2, driver);

		try {
			roomClass.newRoomClassButtonV2.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR.NewRoomText), driver);
			
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			jse.executeScript("arguments[0].click();", roomClass.newRoomClassButtonV2);
			Wait.waitForElementToBeVisibile(By.xpath(OR.NewRoomText), driver);
		}
		roomclassLogger.info("Clicked on NEW ROOMCLASS button");
		test_steps.add("Clicked on NEW ROOMCLASS button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassNameFieldV2), driver);

		roomClass.roomClassNameFieldV2.sendKeys(roomClassName);
		roomclassLogger.info("Entered the room class name : " + roomClassName);
		test_steps.add("Enter room class name : <b>" + roomClassName + "</b>");

		roomClass.roomClassAbbreviationFieldV2.sendKeys(roomClassAbbrivation);
		roomclassLogger.info("Entered the room class abbreviation : " + roomClassAbbrivation);
		test_steps.add("Enter room class abbreviation : <b>" + roomClassAbbrivation + "</b>");

		roomClass.roomClassMaxAdultsFieldV2.sendKeys(maxAdults);
		roomclassLogger.info("Entered the max audlts : " + maxAdults);
		test_steps.add("Enter max audlts : <b>" + maxAdults + "</b>");

		test_steps.add("Enter max persons : <b>" + maxPersons + "</b>");
		roomClass.roomClassMaxPersonsFieldV2.sendKeys(maxPersons);
		roomclassLogger.info("Entered the max persons : " + maxPersons);

		roomClass.roomClassRoomsTabV2.click();
		roomclassLogger.info("Clicked on Rooms Tab");
		test_steps.add("Click Rooms Tab");

		roomClass.roomClassQuantityFieldV2.sendKeys(roomQuantity);
		roomclassLogger.info("Enterd the rooms quantity : " + roomQuantity);
		test_steps.add("Enter rooms quantity : <b>" + roomQuantity + "</b>");

		roomClass.roomClassQuantityAssignButtonV2.click();
		roomclassLogger.info("Clicked on assign room numbers arrow");
		test_steps.add("Clicked on assign room numbers arrow");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRoomNumberorNameFirstFieldV2), driver);
		roomClass.roomClassRoomNumberorNameFirstFieldV2.clear();

		String RN = "1";
		roomClass.roomClassRoomNumberorNameFirstFieldV2.sendKeys("1");
		Utility.RoomNo = "1";
		roomclassLogger.info("Entered the room number " + RN);
		test_steps.add("Enter  room number <b>" + RN + "</b>");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.roomClassAssignNumbersButtonV2)));
		roomClass.roomClassAssignNumbersButtonV2.click();
		test_steps.add("Click Assign Numbers button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersDoneButtonV2), driver);
		roomClass.roomClassAssignNumbersDoneButtonV2.click();
		roomclassLogger.info("Clicked on Done button");

		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishButtonV2), driver);
		roomClass.roomClassPublishButtonV2.click();
		roomclassLogger.info("Clicked on publish button");
		test_steps.add("Clicked on publish button");
		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedDailogV2), driver);
		Wait.waitUntilButtonIsEnabledByLocator(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver, 60);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
		roomclassLogger.info("Page Load");
		WebElement element= driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2));
		Utility.ScrollToElement(element, driver);
		roomclassLogger.info("Scroll Done");
		Wait.wait10Second();
		driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2)).click();
		test_steps.add("Clicked on Publish Confirmed Dailog: OK button");
		roomclassLogger.info("Clicked on Publish Confirmed Dailog: OK button");

		return test_steps;
	}*/
	
    
 // Delete a room class
 	public ArrayList<String> deleteAllRoomClassV2(WebDriver driver, String roomClassName) throws Exception {

 			ArrayList<String> test_Steps = new ArrayList<>();
 			Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);

 			boolean isRoomClassFound = false;
 			boolean endPagination = false;
 			String alphabetFilter = null;
 			boolean noRoomClassesFoundMessage = false;
 			boolean isRoomClassSeleted = false;

 			//Select the room class to delete

 			//Get the first letter of roomClassName to filter
 			String firstCharOfRoomClass = roomClassName.substring(0,1).trim();
 			int unicodeValue = firstCharOfRoomClass.toCharArray()[0];
 			if((65<=unicodeValue & unicodeValue<=90) || (97<=unicodeValue & unicodeValue<=122)){
 				alphabetFilter = firstCharOfRoomClass.toUpperCase();
 			}else if ( 48<=unicodeValue & unicodeValue<=57){
 				alphabetFilter = "#";  // default value
 			}else {
 				alphabetFilter = "All";
 			}

 			//Filer By clicking on alphabetical symbol
 			driver.findElement(By.xpath("//input[@value='" + alphabetFilter +"']//ancestor::label")).click();
 			roomclassLogger.info("Clicked on  roomClass alphabet filter: " + alphabetFilter);
 			test_Steps.add("Clicked on  roomClass alphabet filter: " + alphabetFilter);

 			//Static Wait
 			Wait.wait2Second();
 			boolean isExist=Utility.isElementPresent(driver, By.xpath(OR.roomClassPaginationV2));
 			if(isExist) {
 			Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPaginationV2), driver);
 			}

 			//Check whether room classes are displayed or "No Room Classes Found" text displayed
 			try{
 				if(driver.findElement(By.xpath(OR.noRoomClassesFoundV2)).isDisplayed()){
 					noRoomClassesFoundMessage = true;
 				}
 			}catch(Exception e){

 			}

 			if(!noRoomClassesFoundMessage) {
 				//Get Total matching records displayed text
 				String totalRecordsFound = driver.findElement(By.xpath(OR.roomClassRecordsFoundV2)).getText();
 				int roomsCount =Integer.parseInt(totalRecordsFound.trim().split(" ")[0]);

 				//Get Items per page value
 				Utility.ScrollToElement(roomClass.roomClassItemsPerPageDropdownV2, driver);
 				String itemsPerPageValue =  driver.findElement(By.xpath(OR.roomClassItemsPerPageDropdownV2)).getText();
 				int itemsPerPageVal = Integer.parseInt(itemsPerPageValue.trim());

 				//Check that, no of records are more than Items per page value then use pagination and find the room class

 				if(roomsCount <= itemsPerPageVal) {
 					//No Pagination is required, Get all table rows which represent all room classes data
 					List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

 					//Run the loop for each row to identify room class
 					for(int row = 1; row<=listOfTableRows.size(); row++){
 						String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
 						if(capturedRoomClassName.contains(roomClassName)){
 							isRoomClassFound = true;
 							roomclassLogger.info("Room class is found");
 							test_Steps.add("Room class is found");
 						}

 						if(isRoomClassFound){
 							//Once room class is found, select the checkbox
 							driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input")).click();
 							roomclassLogger.info("Selected the room class to delete");
 							test_Steps.add("Selected the room class to delete");

 							//Checking whether room class checkbox is checked or not?
 							WebElement roomClassCheckbox = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input/.."));
 							if(roomClassCheckbox.getAttribute("class").contains("checkbox-checked")){
 								isRoomClassSeleted = true;
 							}
 							break;
 						}
 					}
 				}else {
 					// Pagination is required and get all the pages
 					Utility.ScrollToElement(roomClass.roomClassPaginationV2, driver);
 					List<WebElement> paginationValues = driver.findElements(By.xpath(OR.roomClassPaginationListV2));

 					// Go page by page ( exclude first and last )
 					for(int pageIndex=2; pageIndex<paginationValues.size(); pageIndex++){
 						// Click on pagination index
 						driver.findElement(By.xpath("//ul[contains(@class, 'RoomClasses_tablePagination')]/li[" + pageIndex + "]")).click();

 						//Static Wait
 						Wait.wait2Second();

 						//Now,Get all table rows which represent all room classes data
 						List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

 						//Run the loop for each row to identify room class
 						for(int row = 1; row<=listOfTableRows.size(); row++){
 							String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
 							if(capturedRoomClassName.contains(roomClassName)){
 								isRoomClassFound = true;
 								roomclassLogger.info("Room class is found");
 								test_Steps.add("Room class is found");
 							}

 							if(isRoomClassFound){
 								//Once room class is found, select the checkbox
 								driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input")).click();
 								endPagination = true;
 								roomclassLogger.info("Selected the room class to delete");
 								test_Steps.add("Selected the room class to delete");

 								//Checking whether room class checkbox is checked or not?
 								WebElement roomClassCheckbox = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input/.."));
 								if(roomClassCheckbox.getAttribute("class").contains("checkbox-checked")){
 									isRoomClassSeleted = true;
 								}
 								break;
 							}
 						}

 						if(endPagination){
 							break;
 						}
 					}
 				}

 				//If room class is selected and delete button is enabled then click on delete button
 				if(isRoomClassSeleted){
 					//Click on DELETE button
 					if(driver.findElement(By.xpath(OR.roomClassDeleteButtonV2)).isEnabled()) {
 						driver.findElement(By.xpath(OR.roomClassDeleteButtonV2)).click();
 						roomclassLogger.info("Clicked on DELETE button");
 						test_Steps.add("Clicked on DELETE button");
 						Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassDeleteConfirmationPopupV2), driver);
 					}

 					//"Do you want to delete these items?" model pop-up will display
 					if(driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupV2)).isDisplayed()){
 						roomclassLogger.info("RoomClass deletion confirmation popup is displayed");
 						test_Steps.add("RoomClass deletion confirmation popup is displayed");

 						//Click on OK button
 						Wait.wait2Second();
 						//driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)).click();
 						JavascriptExecutor jse = (JavascriptExecutor) driver;
 						try {
 							jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)));
 						} catch (Exception e) {
 							jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)));
 						}

 						roomclassLogger.info("Clicked on Deletion Confirmation popup OK button");
 						test_Steps.add("Clicked on Deletion Confirmation popup OK button");
 					}

 					try{

 						//Verify the toaster message "Room Classes Deleted Successfully"
 						if(driver.findElement(By.xpath("//div[contains(text(),'Room Classes Deleted Successfully')]")).isDisplayed()){
 							roomclassLogger.info("Delete confirmation Toaster message is displayed");
 							test_Steps.add("Delete confirmation Toaster message is displayed");
 						}
 					}catch(Exception e){
 						roomclassLogger.info("Delete confirmation Toaster message is disappeared");
 						test_Steps.add("Delete confirmation Toaster message is disappeared");
 					}
 				}else{
 					roomclassLogger.info("Specified room class does not exist");
 					test_Steps.add("Specified room class does not exist");
 				}
 			}else{
 				roomclassLogger.info("No Room Classes Found with specified room class name: " + roomClassName);
 				test_Steps.add("No Room Classes Found with specified room class name: " + roomClassName);
 			}

 			//Static wait
 			Wait.wait2Second();
 			return test_Steps;
 	}


    public HashMap<String, ArrayList<String>> getRoomClassDetails(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException, Exception {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);
        ArrayList<String> activeRoomClassList = getAllActiveRoomClassesListV2(driver, test_steps, "Active");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (String roomClassName : activeRoomClassList) {
            String roomClassLocator = String.format("//table//tbody/tr/td[3]/a[text()='%s']", roomClassName);
            Wait.WaitForElement(driver, roomClassLocator);
            WebElement element = driver.findElement(By.xpath(roomClassLocator));
            element.click();
            ArrayList<String> roomClassDetailList = new ArrayList<>();

            Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
            String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);
            roomClassDetailList.add(abbreviationTextValue.trim());

            Wait.WaitForElement(driver, OR.roomClassSortOrderFieledV2);
            String sortOrderTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassSortOrderFieledV2);
            roomClassDetailList.add(sortOrderTextValue.trim());

            Wait.WaitForElement(driver, OR.roomClassMaxAdultsFieldV2);
            String maxAdultTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxAdultsFieldV2);
            roomClassDetailList.add(maxAdultTextValue.trim());

            Wait.WaitForElement(driver, OR.New_RoomCLass_Max_Persons1);
            String maxPersonsTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxPersonsFieldV2);
            roomClassDetailList.add(maxPersonsTextValue.trim());

            map.put(roomClassName, roomClassDetailList);
            closeRoomClassTabV2(driver, roomClassName);
        }
        return map;
    }

    public ArrayList<String> getSortOrderMap(WebDriver driver, ArrayList<String> test_steps) throws Exception {
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);

        ArrayList<String> activeRoomClassList = getAllActiveRoomClassesListV2(driver, test_steps, "Active");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (String roomClassName : activeRoomClassList) {
            String roomClassLocator = String.format("//table//tbody/tr/td[3]/a[text()='%s']", roomClassName);
            Wait.WaitForElement(driver, roomClassLocator);
            WebElement element = driver.findElement(By.xpath(roomClassLocator));
            element.click();

            Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
            String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);

            ele.roomClassRoomsTabV2.click();

            String physicalRoomNameLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@class,'PhysicalRooms_roomName')]";
            String physicalRoomSortOrderLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@id,'sortOrder')]";

            if(Utility.isElementPresent(driver, By.xpath(physicalRoomNameLocator))) {
                List<WebElement> physicalRoomNameList = driver.findElements(By.xpath(physicalRoomNameLocator));
                List<WebElement> physicalRoomSortOrderList = driver.findElements(By.xpath(physicalRoomSortOrderLocator));
                for(int i = 0; i < physicalRoomNameList.size(); i++) {
                    int sortOrder = Integer.parseInt(((String) jse.executeScript("return arguments[0].value", physicalRoomSortOrderList.get(i))).trim());
                    String physicalRoomName = abbreviationTextValue + "|" + ((String) jse.executeScript("return arguments[0].value", physicalRoomNameList.get(i))).trim();
                    if (!map.containsKey(sortOrder)) {
                        map.put(sortOrder, new ArrayList<>());
                    }
                    map.get(sortOrder).add(physicalRoomName);
                }
            }

            String virtualRoomNameLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[2]";
            String virtualRoomSortOrderLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[3]";

            if(Utility.isElementPresent(driver, By.xpath(virtualRoomNameLocator))) {
                List<WebElement> virtualRoomNameList = driver.findElements(By.xpath(virtualRoomNameLocator));
                List<WebElement> virtualRoomSortOrderList = driver.findElements(By.xpath(virtualRoomSortOrderLocator));

                for(int i = 0; i < virtualRoomSortOrderList.size(); i++) {
                    int sortOrder = Integer.parseInt(virtualRoomSortOrderList.get(i).getText().trim());
                    String virtualRoomName = virtualRoomNameList.get(i).getText().trim();

                    if (!map.containsKey(sortOrder)) {
                        map.put(sortOrder, new ArrayList<>());
                    }
                    map.get(sortOrder).add(virtualRoomName);
                }
            }
            closeRoomClassTabV2(driver, roomClassName);
        }

        List<Integer> keys = new ArrayList<>(map.keySet());
        keys.sort(Comparator.comparingInt(o -> o));
        ArrayList<String> sortedList = new ArrayList<>();
        for(int key : keys) {
            sortedList.addAll(map.get(key));
        }

        return sortedList;
    }

    public HashMap<String, ArrayList<String>> getGroupBySortOrderMap(WebDriver driver, ArrayList<String> test_steps) throws Exception {
        HashMap<String, ArrayList<String>> sortOrderMap = new HashMap<>();
        Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);

        ArrayList<String> activeRoomClassList = getAllActiveRoomClassesListV2(driver, test_steps, "Active");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (String roomClassName : activeRoomClassList) {
            HashMap<Integer, ArrayList<String>> map = new HashMap<>();

            String roomClassLocator = String.format("//table//tbody/tr/td[3]/a[text()='%s']", roomClassName);
            Wait.WaitForElement(driver, roomClassLocator);
            WebElement element = driver.findElement(By.xpath(roomClassLocator));
            element.click();

            Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
            String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);

            ele.roomClassRoomsTabV2.click();

            String physicalRoomNameLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@class,'PhysicalRooms_roomName')]";
            String physicalRoomSortOrderLocator = "//tr[contains(@class, 'PhysicalRooms_activeRow')]//input[contains(@id,'sortOrder')]";

            if(Utility.isElementPresent(driver, By.xpath(physicalRoomNameLocator))) {
                List<WebElement> physicalRoomNameList = driver.findElements(By.xpath(physicalRoomNameLocator));
                List<WebElement> physicalRoomSortOrderList = driver.findElements(By.xpath(physicalRoomSortOrderLocator));

                for(int i = 0; i < physicalRoomNameList.size(); i++) {
                    int sortOrder = Integer.parseInt(((String) jse.executeScript("return arguments[0].value", physicalRoomSortOrderList.get(i))).trim());
                    String physicalRoomName = ((String) jse.executeScript("return arguments[0].value", physicalRoomNameList.get(i))).trim();
                    if (!map.containsKey(sortOrder)) {
                        map.put(sortOrder, new ArrayList<>());
                    }
                    map.get(sortOrder).add(physicalRoomName);
                }
            }

            String virtualRoomNameLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[2]";
            String virtualRoomSortOrderLocator = "//div[contains(@class, 'VirtualRoomsTable_roomsTable')]//table//tbody/tr/td[3]";

            if(Utility.isElementPresent(driver, By.xpath(virtualRoomNameLocator))) {
                List<WebElement> virtualRoomNameList = driver.findElements(By.xpath(virtualRoomNameLocator));
                List<WebElement> virtualRoomSortOrderList = driver.findElements(By.xpath(virtualRoomSortOrderLocator));

                for(int i = 0; i < virtualRoomSortOrderList.size(); i++) {
                    int sortOrder = Integer.parseInt(virtualRoomSortOrderList.get(i).getText().trim());
                    String virtualRoomName = virtualRoomNameList.get(i).getText().trim();

                    if (!map.containsKey(sortOrder)) {
                        map.put(sortOrder, new ArrayList<>());
                    }
                    map.get(sortOrder).add(virtualRoomName);
                }
            }
            closeRoomClassTabV2(driver, roomClassName);

            ArrayList<String> resultList = new ArrayList<>();
            List<Integer> sortedList = new ArrayList<>(map.keySet());
            sortedList.sort(Comparator.comparingInt(o -> o));
            for(Integer key : sortedList) {
                ArrayList<String> roomNumberList = map.get(key);
                resultList.addAll(roomNumberList);

            }
            sortOrderMap.put(roomClassName, resultList);
        }
        return sortOrderMap;
    }

    public void applyFilter(WebDriver driver, ArrayList<String> test_steps, char filterChar) {
		String locator = String.format("//label/span[text()='%c']", filterChar);
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		driver.findElement(By.xpath(locator)).click();
	}

    public String getAbbreviation(WebDriver driver, ArrayList<String> test_steps, String roomClass) {
		applyFilter(driver, test_steps, roomClass.charAt(0));
		String locator = String.format("//a[text()='%s']/parent::td/following-sibling::td[1]", roomClass);
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		return driver.findElement(By.xpath(locator)).getTagName().trim();
	}

    public ArrayList<String> createRoomClassWithStatusV2(WebDriver driver, String roomClassName,
			String roomClassAbbrivation, String maxAdults, String maxPersons, String roomQuantity, String status,
			ExtentTest test, ArrayList<String> test_steps) throws Exception {

		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);

		Wait.WaitForElement(driver, OR.newRoomClassButtonV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRoomClassButtonV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRoomClassButtonV2), driver);

		Utility.ScrollToElement(roomClass.newRoomClassButtonV2, driver);

		roomClass.newRoomClassButtonV2.click();
		roomclassLogger.info("Clicked on NEW ROOMCLASS button");
		test_steps.add("Clicked on NEW ROOMCLASS button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassNameFieldV2), driver);

		roomClass.roomClassNameFieldV2.sendKeys(roomClassName);
		roomclassLogger.info("Entered the room class name : " + roomClassName);
		test_steps.add("Enter room class name : <b>" + roomClassName + "</b>");

		roomClass.roomClassAbbreviationFieldV2.sendKeys(roomClassAbbrivation);
		roomclassLogger.info("Entered the room class abbreviation : " + roomClassAbbrivation);
		test_steps.add("Enter room class abbreviation : <b>" + roomClassAbbrivation + "</b>");
		
		roomClass.expandNewRoomClassStatus.click();
		String xpath = "//ul[@role='listbox']/li[text()='"+status+"']";
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		driver.findElement(By.xpath(xpath)).click();
		roomclassLogger.info("Select room class status : " + status);
		test_steps.add("Select room class status : <b>" + status + "</b>");
		String selectedStatus = roomClass.selectedStatusOfNewRoomClass.getText();
		assertEquals(selectedStatus, status,"Failed: Room Class status is not selected");

		roomClass.roomClassMaxAdultsFieldV2.sendKeys(maxAdults);
		roomclassLogger.info("Entered the max audlts : " + maxAdults);
		test_steps.add("Enter max audlts : <b>" + maxAdults + "</b>");

		test_steps.add("Enter max persons : <b>" + maxPersons + "</b>");
		roomClass.roomClassMaxPersonsFieldV2.sendKeys(maxPersons);
		roomclassLogger.info("Entered the max persons : " + maxPersons);

		roomClass.roomClassRoomsTabV2.click();
		roomclassLogger.info("Clicked on Rooms Tab");
		test_steps.add("Click Rooms Tab");

		roomClass.roomClassQuantityFieldV2.sendKeys(roomQuantity);
		roomclassLogger.info("Enterd the rooms quantity : " + roomQuantity);
		test_steps.add("Enter rooms quantity : <b>" + roomQuantity + "</b>");

		roomClass.roomClassQuantityAssignButtonV2.click();
		roomclassLogger.info("Clicked on assign room numbers arrow");
		test_steps.add("Clicked on assign room numbers arrow");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassRoomNumberorNameFirstFieldV2), driver);
		roomClass.roomClassRoomNumberorNameFirstFieldV2.clear();

		String RN = "1";
		roomClass.roomClassRoomNumberorNameFirstFieldV2.sendKeys("1");
		Utility.RoomNo = "1";
		roomclassLogger.info("Entered the room number " + RN);
		test_steps.add("Enter  room number <b>" + RN + "</b>");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.roomClassAssignNumbersButtonV2)));
		roomClass.roomClassAssignNumbersButtonV2.click();
		test_steps.add("Click Assign Numbers button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersDoneButtonV2), driver);
		roomClass.roomClassAssignNumbersDoneButtonV2.click();



		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishButtonV2), driver);
		roomClass.roomClassPublishButtonV2.click();
		roomclassLogger.info("Clicked on publish button");
		test_steps.add("Clicked on publish button");

		try {
		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedDailogOKButtonV2);
		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
		Wait.wait3Second();
		driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2)).click();
		test_steps.add("Clicked on Publish Confirmed Dailog: OK button");
		roomclassLogger.info("Clicked on Publish Confirmed Dailog: OK button");
		}catch(Exception e) {
			Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2), driver);
			Wait.wait3Second();
			driver.findElement(By.xpath(OR.roomClassPublishConfirmedDailogOKButtonV2)).click();
			test_steps.add("Clicked on Publish Confirmed Dailog: OK button");
			roomclassLogger.info("Clicked on Publish Confirmed Dailog: OK button");
		}
		return test_steps;
	}
	

	public String getAbbreviationText(WebDriver driver, ArrayList<String> test_steps, String roomClass) {
		applyFilter(driver, test_steps, roomClass.charAt(0));
		String locator = String.format("//a[text()='%s']/parent::td/following-sibling::td[1]", roomClass);
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		return driver.findElement(By.xpath(locator)).getText().trim();
	}
	
	public void clickRoomClass(WebDriver driver, ArrayList<String> test_steps, String roomClass) {
		applyFilter(driver, test_steps, roomClass.charAt(0));
		String locator = "//a[@class='roomClassLink' and text()='"+ roomClass +"']";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		driver.findElement(By.xpath(locator)).click();
		test_steps.add("Click '"+  roomClass + "' to open");
		roomclassLogger.info("Click '"+  roomClass + "' to open");
	}

	 public HashMap<String, String> getRoomClassDetails(WebDriver driver, String roomClassName, ArrayList<String> testSteps) throws InterruptedException, Exception {
	        HashMap<String, String> map = new HashMap<>();
	        Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);
	        Elements_NewRoomClass ele1 = new Elements_NewRoomClass(driver);
	        JavascriptExecutor jse = (JavascriptExecutor) driver;
	        clickRoomClass(driver, testSteps, roomClassName);
	        Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
	        String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);
	        map.put("roomClassAbbreviation", abbreviationTextValue.trim());
	        testSteps.add("roomClassAbbreviation : " +  abbreviationTextValue.trim());
	        Wait.WaitForElement(driver, OR.roomClassSortOrderFieledV2);
	        String sortOrderTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassSortOrderFieledV2);
	        map.put("sortOrder", sortOrderTextValue.trim());
	        testSteps.add("sortOrder : " +  sortOrderTextValue.trim());
	        
			Utility.ScrollToElement(ele1.ClickVrboTab, driver);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele1.ClickVrboTab);
			String ratePlanNameVrbo = ele1.getRatePlanVrbo.getText();
			map.put("ratePlan", ratePlanNameVrbo.trim());
	               
	        Wait.WaitForElement(driver, OR.roomClassMaxAdultsFieldV2);
	        String maxAdultTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxAdultsFieldV2);
	        map.put("maxAdult", maxAdultTextValue.trim());
	        testSteps.add("maxAdult : " +  maxAdultTextValue.trim());
	        
	        Wait.WaitForElement(driver, OR.New_RoomCLass_Max_Persons1);
	        String maxPersonsTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxPersonsFieldV2);
	        map.put("maxPersons", maxPersonsTextValue.trim());
	        testSteps.add("maxPersons : " +  maxPersonsTextValue.trim());

	        Wait.WaitForElement(driver, OR.roomClassIdFieldV2);
	        String roomClassIdValue = ele.roomClassIdFieldV2.getText().trim();
	        map.put("roomClassId", roomClassIdValue.trim());
	        testSteps.add("roomClassId : " +  roomClassIdValue.trim());
	        
	        return map;
}
	 public void clicmRoomTab(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		 Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);
		 Wait.WaitForElement(driver, OR.roomClassRoomTabV2);
		 ele.roomClassRoomTabV2.click();
		 testSteps.add("Click on Room Tab");
		 roomclassLogger.info("Click on Room Tab");
		 Wait.WaitForElement(driver, OR.roomClassRoomPhysicalRoomsTitleV2);
	 }
	 
	 public HashMap<String,String> getRoomNoOfRoomClass(WebDriver driver) {
		 HashMap<String,String> roomNos= new HashMap<String,String>();
		 String path="//input[contains(@id,'.roomName')]";
		List< WebElement >elements=driver.findElements(By.xpath(path));
		 int size= elements.size();
		 for(int i=0;i<size;i++) {
			 String roomPath="//input[contains(@id,'rooms["+i+"].roomName')]";
			 WebElement element= driver.findElement(By.xpath(roomPath));
			 roomNos.put(String.valueOf(i+1),element.getAttribute("value"));
					 
		 }
		 roomclassLogger.info("Total Rooms Are " + size);
		 roomclassLogger.info("Total Rooms Name Are " + roomNos);
		return roomNos;
	 }

		public String getRatePlanFromVrboTab(WebDriver driver) throws InterruptedException {
			Elements_NewRoomClass ele = new Elements_NewRoomClass(driver);
			Utility.ScrollToElement(ele.ClickVrboTab, driver);
			ele.ClickVrboTab.click();
			String ratePlanName = ele.getRatePlanVrbo.getText();
			return ratePlanName;
			}

	public void clickPublishV2(WebDriver driver, ArrayList<String> test_steps) {
		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishButtonV2), driver);
		try {
			Wait.wait2Second();
			roomClass.roomClassPublishButtonV2.click();
		} catch (Exception e1) {
			try {
				Utility.clickThroughAction(driver, roomClass.roomClassPublishButtonV2);
			}catch (Exception e2) {
				Utility.clickThroughJavaScript(driver, roomClass.roomClassPublishButtonV2);
			}
		}
		test_steps.add("Clicked on publish button");
	}
	
	
	public void updateRoomClassRoomCount(WebDriver driver, int roomCount, ArrayList<String> test_steps) throws Exception {
		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
		openRoomClass(driver, test_steps, "Test2");
		
		roomClass.roomClassRoomsTabV2.click();
		roomclassLogger.info("Clicked on Rooms Tab");
		test_steps.add("Click Rooms Tab");

		int roomCountOld = Integer.parseInt(roomClass.roomClassQuantityFieldV2.getAttribute("value"));
		int roomCountNew = roomCountOld + roomCount;
		roomClass.roomClassQuantityFieldV2.clear();
		roomClass.roomClassQuantityFieldV2.sendKeys(Integer.toString(roomCountNew));
		roomclassLogger.info("Enterd the rooms quantity : " + roomCount);
		test_steps.add("Enter rooms quantity : <b>" + roomCount + "</b>");

		roomClass.roomClassQuantityAssignButtonV2.click();
		roomclassLogger.info("Clicked on assign room numbers arrow");
		test_steps.add("Clicked on assign room numbers arrow");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersButtonV2), driver);
		roomClass.roomClassAssignNumbersButtonV2.click();
		test_steps.add("Click Assign Numbers button");

		Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassAssignNumbersDoneButtonV2), driver);
		roomClass.roomClassAssignNumbersDoneButtonV2.click();
		roomclassLogger.info("Clicked on Done button");
		
		clickPublishV2(driver, test_steps);
	}

	

	
	// Delete a room class
	public ArrayList<String> deleteRoomClassStartWithRoomClassV2(WebDriver driver, String roomClassName) throws Exception {

			ArrayList<String> test_Steps = new ArrayList<>();
			Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);

			boolean isRoomClassFound = false;
			boolean endPagination = false;
			String alphabetFilter = null;
			boolean noRoomClassesFoundMessage = false;
			boolean isRoomClassSeleted = false;

			//Select the room class to delete

			//Get the first letter of roomClassName to filter
			String firstCharOfRoomClass = roomClassName.substring(0,1).trim();
			int unicodeValue = firstCharOfRoomClass.toCharArray()[0];
			if((65<=unicodeValue & unicodeValue<=90) || (97<=unicodeValue & unicodeValue<=122)){
				alphabetFilter = firstCharOfRoomClass.toUpperCase();
			}else if ( 48<=unicodeValue & unicodeValue<=57){
				alphabetFilter = "#";  // default value
			}else {
				alphabetFilter = "All";
			}

			//Filer By clicking on alphabetical symbol
			driver.findElement(By.xpath("//input[@value='" + alphabetFilter +"']//ancestor::label")).click();
			roomclassLogger.info("Clicked on  roomClass alphabet filter: " + alphabetFilter);
			test_Steps.add("Clicked on  roomClass alphabet filter: " + alphabetFilter);

			//Static Wait
			Wait.wait2Second();
			Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPaginationV2), driver);

			//Check whether room classes are displayed or "No Room Classes Found" text displayed
			try{
				if(driver.findElement(By.xpath(OR.noRoomClassesFoundV2)).isDisplayed()){
					noRoomClassesFoundMessage = true;
				}
			}catch(Exception e){

			}

			if(!noRoomClassesFoundMessage) {
				//Get Total matching records displayed text
				String totalRecordsFound = driver.findElement(By.xpath(OR.roomClassRecordsFoundV2)).getText();
				int roomsCount =Integer.parseInt(totalRecordsFound.trim().split(" ")[0]);

				//Get Items per page value
				Utility.ScrollToElement(roomClass.roomClassItemsPerPageDropdownV2, driver);
				String itemsPerPageValue =  driver.findElement(By.xpath(OR.roomClassItemsPerPageDropdownV2)).getText();
				int itemsPerPageVal = Integer.parseInt(itemsPerPageValue.trim());

				//Check that, no of records are more than Items per page value then use pagination and find the room class

				if(roomsCount <= itemsPerPageVal) {
					//No Pagination is required, Get all table rows which represent all room classes data
					List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

					//Run the loop for each row to identify room class
					for(int row = 1; row<=listOfTableRows.size(); row++){
						String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
						if(capturedRoomClassName.startsWith(roomClassName)){
							isRoomClassFound = true;
							roomclassLogger.info("Room class is found");
							test_Steps.add("Room class is found");
						}

						if(isRoomClassFound){
							//Once room class is found, select the checkbox
							driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input")).click();
							roomclassLogger.info("Selected the room class to delete");
							test_Steps.add("Selected the room class to delete");

							//Checking whether room class checkbox is checked or not?
							WebElement roomClassCheckbox = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input/.."));
							if(roomClassCheckbox.getAttribute("class").contains("checkbox-checked")){
								isRoomClassSeleted = true;
							}
						}
					}
				}else {
					// Pagination is required and get all the pages
					Utility.ScrollToElement(roomClass.roomClassPaginationV2, driver);
					List<WebElement> paginationValues = driver.findElements(By.xpath(OR.roomClassPaginationListV2));

					// Go page by page ( exclude first and last )
					for(int pageIndex=2; pageIndex<paginationValues.size(); pageIndex++){
						// Click on pagination index
						driver.findElement(By.xpath("//ul[contains(@class, 'RoomClasses_tablePagination')]/li[" + pageIndex + "]")).click();

						//Static Wait
						Wait.wait2Second();

						//Now,Get all table rows which represent all room classes data
						List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

						//Run the loop for each row to identify room class
						for(int row = 1; row<=listOfTableRows.size(); row++){
							String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
							if(capturedRoomClassName.startsWith(roomClassName)){
								isRoomClassFound = true;
								roomclassLogger.info("Room class is found");
								test_Steps.add("Room class is found");
							}

							if(isRoomClassFound){
								//Once room class is found, select the checkbox
								driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input")).click();
								endPagination = true;
								roomclassLogger.info("Selected the room class to delete");
								test_Steps.add("Selected the room class to delete");

								//Checking whether room class checkbox is checked or not?
								WebElement roomClassCheckbox = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//td[1]//input/.."));
								if(roomClassCheckbox.getAttribute("class").contains("checkbox-checked")){
									isRoomClassSeleted = true;
								}
							}
						}

						if(endPagination){
							break;
						}
					}
				}

				//If room class is selected and delete button is enabled then click on delete button
				if(isRoomClassSeleted){
					//Click on DELETE button
					if(driver.findElement(By.xpath(OR.roomClassDeleteButtonV2)).isEnabled()) {
						driver.findElement(By.xpath(OR.roomClassDeleteButtonV2)).click();
						roomclassLogger.info("Clicked on DELETE button");
						test_Steps.add("Clicked on DELETE button");
						Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassDeleteConfirmationPopupV2), driver);
					}

					//"Do you want to delete these items?" model pop-up will display
					if(driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupV2)).isDisplayed()){
						roomclassLogger.info("RoomClass deletion confirmation popup is displayed");
						test_Steps.add("RoomClass deletion confirmation popup is displayed");

						//Click on OK button
						Wait.wait2Second();
						//driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)).click();
						JavascriptExecutor jse = (JavascriptExecutor) driver;
						try {
							jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)));
						} catch (Exception e) {
							jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(OR.roomClassDeleteConfirmationPopupOKButtonV2)));
						}

						roomclassLogger.info("Clicked on Deletion Confirmation popup OK button");
						test_Steps.add("Clicked on Deletion Confirmation popup OK button");
					}

					try{

						//Verify the toaster message "Room Classes Deleted Successfully"
						if(driver.findElement(By.xpath("//div[contains(text(),'Room Classes Deleted Successfully')]")).isDisplayed()){
							roomclassLogger.info("Delete confirmation Toaster message is displayed");
							test_Steps.add("Delete confirmation Toaster message is displayed");
						}
					}catch(Exception e){
						roomclassLogger.info("Delete confirmation Toaster message is disappeared");
						test_Steps.add("Delete confirmation Toaster message is disappeared");
					}
				}else{
					roomclassLogger.info("Specified room class does not exist");
					test_Steps.add("Specified room class does not exist");
				}
			}else{
				roomclassLogger.info("No Room Classes Found with specified room class name: " + roomClassName);
				test_Steps.add("No Room Classes Found with specified room class name: " + roomClassName);
			}

			//Static wait
			Wait.wait2Second();
			return test_Steps;
	}
	
	// Search a room class
	public boolean searchRoomClassStartWithV2(WebDriver driver, String roomClassName) throws Exception {

		ArrayList<String> test_Steps = new ArrayList<>();
		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);

		boolean isRoomClassFound = false;
		boolean endPagination = false;
		String alphabetFilter = null;
		boolean noRoomClassesFoundMessage = false;

		//Select the room class to delete

		//Get the first letter of roomClassName to filter
		String firstCharOfRoomClass = roomClassName.substring(0,1).trim();
		int unicodeValue = firstCharOfRoomClass.toCharArray()[0];
		if((65<=unicodeValue & unicodeValue<=90) || (97<=unicodeValue & unicodeValue<=122)){
			alphabetFilter = firstCharOfRoomClass.toUpperCase();
		}else if ( 48<=unicodeValue & unicodeValue<=57){
			alphabetFilter = "#";  // default value
		}else {
			alphabetFilter = "All";
		}

		//Filer By clicking on alphabetical symbol
		driver.findElement(By.xpath("//input[@value='" + alphabetFilter +"']//ancestor::label")).click();
		roomclassLogger.info("Clicked on  roomClass alphabet filter: " + alphabetFilter);
		test_Steps.add("Clicked on  roomClass alphabet filter: " + alphabetFilter);

		//Static Wait
		Wait.wait2Second();

		//Check whether room classes are displayed or "No Room Classes Found" text displayed
		try{
			Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPaginationV2), driver);
			if(driver.findElement(By.xpath(OR.noRoomClassesFoundV2)).isDisplayed()){
				noRoomClassesFoundMessage = true;
			}
		}catch(Exception e){

		}

		if(!noRoomClassesFoundMessage) {
			//Get Total matching records displayed text
			String totalRecordsFound = driver.findElement(By.xpath(OR.roomClassRecordsFoundV2)).getText();
			int roomsCount =Integer.parseInt(totalRecordsFound.trim().split(" ")[0]);

			//Get Items per page value
			Utility.ScrollToElement(roomClass.roomClassItemsPerPageDropdownV2, driver);
			String itemsPerPageValue =  driver.findElement(By.xpath(OR.roomClassItemsPerPageDropdownV2)).getText();
			int itemsPerPageVal = Integer.parseInt(itemsPerPageValue.trim());

			//Check that, no of records are more than Items per page value then use pagination and find the room class

			if(roomsCount <= itemsPerPageVal) {
				//No Pagination is required, Get all table rows which represent all room classes data
				List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

				//Run the loop for each row to identify room class
				for(int row = 1; row<=listOfTableRows.size(); row++){
					String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
					if(capturedRoomClassName.startsWith(roomClassName)){
						isRoomClassFound = true;
						roomclassLogger.info("Room class is found");
						test_Steps.add("Room class is found");
					}

					if(isRoomClassFound){
						break;
					}
				}
			}else {
				// Pagination is required and get all the pages
				Utility.ScrollToElement(roomClass.roomClassPaginationV2, driver);
				List<WebElement> paginationValues = driver.findElements(By.xpath(OR.roomClassPaginationListV2));

				// Go page by page ( exclude first and last )
				for(int pageIndex=2; pageIndex<paginationValues.size(); pageIndex++){
					// Click on pagination index
					driver.findElement(By.xpath("//ul[contains(@class, 'RoomClasses_tablePagination')]/li[" + pageIndex + "]")).click();

					//Static Wait
					Wait.wait2Second();

					//Now,Get all table rows which represent all room classes data
					List<WebElement> listOfTableRows = driver.findElements(By.xpath(OR.roomClassTableV2));

					//Run the loop for each row to identify room class
					for(int row = 1; row<=listOfTableRows.size(); row++){
						String capturedRoomClassName  = driver.findElement(By.xpath("//table//tbody/tr" + "[" + row + "]" + "//a[@class='roomClassLink']")).getText();
						if(capturedRoomClassName.startsWith(roomClassName)){
							isRoomClassFound = true;
							roomclassLogger.info("Room class is found");
							test_Steps.add("Room class is found");
						}

						if(isRoomClassFound){
							break;
						}
					}

					if(endPagination){
						break;
					}
				}
			}
		}else{
			if(isRoomClassFound==false || noRoomClassesFoundMessage == true) {
				roomclassLogger.info("No Room Classes Found with specified room class name: " + roomClassName);
				test_Steps.add("No Room Classes Found with specified room class name: " + roomClassName);
			}
		}

		//Static wait
		Wait.wait2Second();

		return isRoomClassFound;
	}

	 public HashMap<String, String> getRoomClassDetails(WebDriver driver, String roomClassName, ArrayList<String> testSteps, boolean isRoomClassClose) throws InterruptedException, Exception {
	        HashMap<String, String> map = new HashMap<>();
	        Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);
	        JavascriptExecutor jse = (JavascriptExecutor) driver;
	        clickRoomClass(driver, testSteps, roomClassName);
	        Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
	        String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);
	        map.put("roomClassAbbreviation", abbreviationTextValue.trim());
	        testSteps.add("roomClassAbbreviation : " +  abbreviationTextValue.trim());
	        Wait.WaitForElement(driver, OR.roomClassSortOrderFieledV2);
	        String sortOrderTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassSortOrderFieledV2);
	        map.put("sortOrder", sortOrderTextValue.trim());
	        testSteps.add("sortOrder : " +  sortOrderTextValue.trim());
	               
	        Wait.WaitForElement(driver, OR.roomClassMaxAdultsFieldV2);
	        String maxAdultTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxAdultsFieldV2);
	        map.put("maxAdult", maxAdultTextValue.trim());
	        testSteps.add("maxAdult : " +  maxAdultTextValue.trim());
	        
	        Wait.WaitForElement(driver, OR.New_RoomCLass_Max_Persons1);
	        String maxPersonsTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassMaxPersonsFieldV2);
	        map.put("maxPersons", maxPersonsTextValue.trim());
	        testSteps.add("maxPersons : " +  maxPersonsTextValue.trim());

	        Wait.WaitForElement(driver, OR.roomClassIdFieldV2);
	        String roomClassIdValue = ele.roomClassIdFieldV2.getText().trim();
	        map.put("roomClassId", roomClassIdValue.trim());
	        testSteps.add("roomClassId : " +  roomClassIdValue.trim());

	        if(isRoomClassClose) {
	        closeRoomClassTabV2(driver, roomClassName);
	        }
	        return map;
	    }

       public String getRoomClassZone(WebDriver driver, String roomClassName, ArrayList<String> testSteps,boolean isRoomClassClose) throws Exception {
    	   String zone=null;
    	   searchRoomClassV2(driver, roomClassName);
    	   clickRoomClass(driver, testSteps, roomClassName);
    	   Wait.waitforPageLoad(30, driver);
    	   clickRoomTab(driver, testSteps);
    	   zone=getRoomClassZone(driver);
    	   testSteps.add("Get Zone: "+zone);
    	   roomclassLogger.info("Get Zone: "+zone);
    	   if(isRoomClassClose) {
   	        closeRoomClassTabV2(driver, roomClassName);
   	        }
		return zone;
    	   
       }
       public void clickRoomTab(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
   		String locator = "//div[@role='tab' and text()='Rooms']";
   		Wait.WaitForElement(driver, locator);
   		driver.findElement(By.xpath(locator)).click();
   		test_steps.add("Click On Room Tab");
   		roomclassLogger.info("Click On Room Tab");
   	}
 
       
       public ArrayList<String> getActiveRoomNumber(WebDriver driver,String delim, String roomClassNames,ArrayList<String> test_Steps ) throws Exception{

			ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClassNames, delim);
			ArrayList<String> getRoomNumber = new ArrayList<String>();
			for(String roomClass : roomClassList) {
				boolean flag = searchRoomClassV2(driver, roomClass);

				if(flag) {
					String path = "(//a[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+roomClass.toUpperCase()+"']/../following::td)[2]";
					getRoomNumber.add(driver.findElement(By.xpath(path)).getText());
				}else {
					getRoomNumber.add("NA");
				}

			}
			roomclassLogger.info("Abbrivation for room classes are : "+getRoomNumber);
			test_Steps.add("Abbrivation for room classes are : "+getRoomNumber);
			return getRoomNumber;
		}
       

       public ArrayList<String> getRoom(WebDriver driver,String delim, String roomClassNames,ArrayList<String> test_Steps ) throws Exception{


   		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClassNames, delim);
   		ArrayList<String> room = new ArrayList<String>();
   		for(String roomClass : roomClassList) {
   			boolean flag = searchRoomClassV2(driver, roomClass);

   			if(flag) {
   				String path = "//tbody[@class='ant-table-tbody']//tr//td/a[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+roomClass.toUpperCase()+"']/../following::td[2]";
   				room.add(driver.findElement(By.xpath(path)).getText());
   			}else {
   				room.add("NA");
   			}

   		}
   		roomclassLogger.info("Room for room classes are : "+room);
   		test_Steps.add("Room for room classes are : "+room);
   		return room;
   	}

       public String modifyRoomClassName(WebDriver driver, String roomClassName, ArrayList<String> test_steps) {
    	   Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
				res.roomClassNamev2.clear();
			
			//roomClassName=roomClassName+randomNumber;
			//roomClassAB=roomClassAB+randomNumber;
//			details=details +randomNumber;
			res.roomClassNamev2.sendKeys(roomClassName);
			test_steps.add("Modify RoomClassName : " + roomClassName);
			roomclassLogger.info("Modify RoomClassName : " + roomClassName);
			
			return (roomClassName);
       }
       
       public void clickOnPublishButton(WebDriver driver,ArrayList<String> test_steps) throws InterruptedException {
    	   Elements_NewRoomClassPage res = new Elements_NewRoomClassPage(driver);
    	   res.publishv2.click();
   		test_steps.add("Clicking on publish button");
   		roomclassLogger.info("Clicking on publish button");
   		Wait.WaitForElement(driver, "//div[contains(text(),'Success')]");
   		//Wait.waitForElementToBeVisibile(By.xpath(OR.roomClassPublishConfirmedV2), driver);
   		Wait.waitForElementToBeClickable(By.xpath(OR.roomClassPublishConfirmedOKButtonV2), driver);
   		Wait.WaitForElement(driver, OR.roomClassPublishConfirmedOKButtonV2);
   		Wait.wait5Second();
   		res.roomClassPublishConfirmedOKButtonV2.click();
       }
       
       public void clickOnNewRoomClasshButton(WebDriver driver,ArrayList<String> test_steps) throws InterruptedException {

   		Elements_NewRoomClassV2 roomClass = new Elements_NewRoomClassV2(driver);
   		WebDriverWait wait = new WebDriverWait(driver, 90);

   		Wait.WaitForElement(driver, OR.newRoomClassButtonV2);
   		Wait.waitForElementToBeVisibile(By.xpath(OR.newRoomClassButtonV2), driver);
   		Wait.waitForElementToBeClickable(By.xpath(OR.newRoomClassButtonV2), driver);

   		Utility.ScrollToElement(roomClass.newRoomClassButtonV2, driver);

   		roomClass.newRoomClassButtonV2.click();
   		roomclassLogger.info("Clicked on NEW ROOMCLASS button");
   		test_steps.add("Clicked on NEW ROOMCLASS button");
       }
       
       //Method added By Riddhi - separate data for multiple room classes and create new room class if it doesn't exist
       public ArrayList<String> createMultipleRoomClassesforV2(WebDriver driver, ArrayList<String> test_steps, 
    		   String roomClassName,String roomClassAB,String policy,String sortOrder,String maxAdults,String maxPerson,
    		   String details,String roomQuant,String roomName,String statinId,String sortOrder1,String zone) throws Exception
       {
    	   Elements_NewRoomClassPage roomClassEle = new Elements_NewRoomClassPage(driver);
    	   NewRoomClassesV2 roomClassObj = new NewRoomClassesV2();
    	   Navigation navigation = new Navigation();
    	   ArrayList<String> getTest_Steps = new ArrayList<>();
			
    	   String[] RoomClassName = roomClassName.split("\\|");
    	   String[] RoomClassAB = roomClassAB.split("\\|");
    	   String[] Policy = policy.split("\\|");
    	   String[] SortOrder = sortOrder.split("\\|");
    	   
    	   String[] MaxAdults = maxAdults.split("\\|");
    	   String[] MaxPerson = maxPerson.split("\\|");
    	   String[] Details = details.split("\\|");
    	   String[] RoomQuant = roomQuant.split("\\|");
    	   
    	   String[] RoomName = roomName.split("\\|");
    	   String[] StatinId = statinId.split("\\|");
    	   String[] SortOrder1 = sortOrder1.split("\\|");
    	   String[] Zone = zone.split("\\|");
    	   
    	   try
    	   {
    		   Wait.WaitForElement(driver, OR.setUP);
    		   roomClassEle.setUP.click();
    		   roomClassEle.roomClasses.click();
    		   
    		   for(int i=0; i<RoomClassName.length; i++)
        	   {
    			   test_steps.add("=================== Verify Room Classs Exist or not ======================" + RoomClassName[i]);
    			   roomclassLogger.info("=================== Verify Room Classs Exist or not ======================" + RoomClassName[i]);
        		   
    			   boolean isRoomClassAvailable = searchRoomClassV2(driver, RoomClassName[i]);
        		   if(!isRoomClassAvailable)
        		   {
        			   test_steps.add("=================== CREATING NEW ROOM CLASS WITH DESCRIPTION ======================");
        			   roomclassLogger.info("=================== CREATING NEW ROOM CLASS WITH DESCRIPTION ======================");
       				
        			   	roomClassEle.createnewRoomClass.click();
       					test_steps.add("Clicking on New Room Class");
       					roomclassLogger.info("Clicking On New Room Class");
       					
       					getTest_Steps.clear();
       					getTest_Steps = createRoomClassWithDescriptionV2(driver, getTest_Steps, RoomClassName[i], RoomClassAB[i], 
       							Policy[i], SortOrder[i], MaxAdults[i], MaxPerson[i], Details[i], 
       							RoomQuant[i], RoomName[i], StatinId[i], SortOrder[i], Zone[i]);
       					test_steps.addAll(getTest_Steps);
       					test_steps.add("New Room Class Created :: " + RoomClassName[i]);
       					roomclassLogger.info("New Room Class Created :: " + RoomClassName[i]);
       					closeRoomClassTabV2(driver,RoomClassName[i]);
        		    }
        		    else
        		    {
	       				test_steps.add(" Room Class Exists : " + RoomClassName[i]);
	       				roomclassLogger.info(" Room Class Exists : " + RoomClassName[i]);
	       			}
        		   if(i+1 != RoomClassName.length)
        		   {
        		    driver.findElement(By.xpath("//input[@value='All']//ancestor::label")).click();
        			roomclassLogger.info("Clicked on  roomClass 'All' filter: ");
        			test_steps.add("Clicked on  roomClass 'All' filter: ");
        			Wait.wait2Second();
        		   }
        	   }
    		   
    	   }
    	   catch(Exception e)
    	   {
    		   e.printStackTrace();
    	   }
    	   return test_steps;
       }
       
       //Added By Riddhi
       public void clickOnVRBOTab4RoomClass(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
			Elements_NewRoomClass ele = new Elements_NewRoomClass(driver);
			Utility.ScrollToElement(ele.ClickVrboTab, driver);
			ele.ClickVrboTab.click();
			test_steps.add("Click on VRBO Tab For Existing Room Class");
			roomclassLogger.info("Click on VRBO Tab For Existing Room Class");			
		}
       
       //Added By Riddhi
       public void updateVrboHeadline(WebDriver driver, ArrayList<String> test_steps, String headLineTxt) throws InterruptedException {
    		Elements_NewRoomClass ele = new Elements_NewRoomClass(driver);
    		Utility.ScrollToElement(ele.VRBO_HEADLINES, driver);
    		Wait.wait10Second();
    		ele.VRBO_HEADLINES.clear();
    		ele.VRBO_HEADLINES.sendKeys(headLineTxt);
    		test_steps.add("Click on VRBO Tab For Existing Room Class");
			roomclassLogger.info("Entered VRBO Headlines : " + headLineTxt);
       }
}


