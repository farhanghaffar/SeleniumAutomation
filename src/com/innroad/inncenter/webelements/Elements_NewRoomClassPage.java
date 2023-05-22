package com.innroad.inncenter.webelements;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.innroad.inncenter.properties.OR;


public class Elements_NewRoomClassPage {
	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("devpinoyLogger");

	public Elements_NewRoomClassPage(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(xpath = OR.setUP)
	public WebElement setUP;
	
	@FindBy(xpath = OR.roomClasses)
	public WebElement roomClasses;
	
	@FindBy(xpath = OR.createnewRoomClass)
	public WebElement createnewRoomClass;
	
	@FindBy(xpath = OR.newRoomLabel)
	public WebElement newRoomLabel;
	
	@FindBy(xpath = OR.detailsLabel)
	public WebElement detailsLabel;
	@FindBy(xpath = OR.roomClassNamev2)
	public WebElement roomClassNamev2;
	@FindBy(xpath = OR.roomClassABv2)
	public WebElement roomClassABv2;
	@FindBy(xpath = OR.sortOrderv2)
	public WebElement sortOrderv2;
	@FindBy(xpath = OR.maxAdultv2)
	public WebElement maxAdultv2;
	@FindBy(xpath = OR.maxPersonv2)
	public WebElement maxPersonv2;
	@FindBy(xpath = OR.smokingPolicyv2)
	public WebElement smokingPolicyv2;
	@FindBy(xpath = OR.editDescription)
	public WebElement editDescription;
	@FindBy(xpath = OR.detailsv2)
	public WebElement detailsv2;
	@FindBy(xpath = OR.donev2)
	public WebElement donev2;
	@FindBy(xpath = OR.roomsv2)
	public WebElement roomsv2;
	@FindBy(xpath = OR.roomsquantityv2)
	public WebElement roomsquantityv2;
	@FindBy(xpath = OR.roomquantityButtonv2)
	public WebElement roomquantityButtonv2;
	@FindBy(xpath = OR.roomNamev2)
	public WebElement roomNamev2;
	@FindBy(xpath = OR.stationIdv2)
	public WebElement stationIdv2;
	@FindBy(xpath = OR.sortOrder)
	public WebElement sortOrder;
	@FindBy(xpath = OR.houseKeepingZonev2)
	public WebElement houseKeepingZonev2;
	@FindBy(xpath = OR.doneButtonv2)
	public WebElement doneButtonv2;
	@FindBy(xpath = OR.publishv2)
	public WebElement publishv2;
	@FindBy(xpath = OR.roomClassPublishConfirmedV2)
	public WebElement roomClassPublishConfirmedV2;
	@FindBy(xpath = OR.roomClassPublishConfirmedOKButtonV2)
	public WebElement roomClassPublishConfirmedOKButtonV2;
	@FindBy(xpath = OR.roomClassPagination)
	public WebElement roomClassPagination;
	@FindBy(xpath = OR.roomClassItemsPerPageDropdown)
	public WebElement roomClassItemsPerPageDropdown;
	@FindBy(xpath = OR.noRoomClassesFound)
	public WebElement noRoomClassesFound;
	@FindBy(xpath = OR.roomClassRecordsFound)
	public WebElement roomClassRecordsFound;
	@FindBy(xpath = OR.roomClassAlphabetFilter)
	public WebElement roomClassAlphabetFilter;
	@FindBy(xpath = OR.roomClassTable)
	public WebElement roomClassTable;
	@FindBy(xpath = OR.roomClassPaginationList)
	public WebElement roomClassPaginationList;
	@FindBy(xpath = OR.roomClassStatusList)
	public WebElement roomClassStatusList;
	
}
