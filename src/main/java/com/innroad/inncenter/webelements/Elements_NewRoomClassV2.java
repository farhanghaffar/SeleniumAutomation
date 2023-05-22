package com.innroad.inncenter.webelements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;

public class Elements_NewRoomClassV2 {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("devpinoyLogger");

	public Elements_NewRoomClassV2(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(xpath = OR.newRoomClassButtonV2)
	public WebElement newRoomClassButtonV2;
	
	@FindBy(xpath = OR.roomClassDeleteButtonV2)
	public WebElement roomClassDeleteButtonV2;
	
	@FindBy(xpath = OR.closeNewRoomClassTabV2)
	public WebElement closeNewRoomClassTabV2;

	@FindBy(xpath = OR.roomClassDetailsV2)
	public WebElement roomClassDetailsV2;

	@FindBy(xpath = OR.newRoomClassPageV2)
	public WebElement newRoomClassPageV2;

	@FindBy(xpath = OR.roomClassPublishButtonV2)
	public WebElement roomClassPublishButtonV2;

	@FindBy(xpath = OR.roomClassCancelButtonV2)
	public WebElement roomClassCancelButtonV2;

	@FindBy(xpath = OR.roomClassDetailsTabV2)
	public WebElement roomClassDetailsTabV2;

	@FindBy(xpath = OR.roomClassRoomsTabV2)
	public WebElement roomClassRoomsTabV2;

	@FindBy(xpath = OR.roomClassNameFieldV2)
	public WebElement roomClassNameFieldV2;

	@FindBy(xpath = OR.roomClassAbbreviationFieldV2)
	public WebElement roomClassAbbreviationFieldV2;

	@FindBy(xpath = OR.roomClassStatusDropdownV2)
	public WebElement roomClassStatusDropdownV2;

	@FindBy(xpath = OR.roomClassSmokingPolicyDropdownV2)
	public WebElement roomClassSmokingPolicyDropdownV2;

	@FindBy(xpath = OR.roomClassSortOrderFieledV2)
	public WebElement roomClassSortOrderFieledV2;

	@FindBy(xpath = OR.roomClassMaxAdultsFieldV2)
	public WebElement roomClassMaxAdultsFieldV2;

	@FindBy(xpath = OR.roomClassMaxPersonsFieldV2)
	public WebElement roomClassMaxPersonsFieldV2;

	@FindBy(xpath = OR.roomClassDescriptionEditFieldV2)
	public WebElement roomClassDescriptionEditFieldV2;

	@FindBy(xpath = OR.roomClassQuantityFieldV2)
	public WebElement roomClassQuantityFieldV2;

	@FindBy(xpath = OR.roomClassQuantityAssignButtonV2)
	public WebElement roomClassQuantityAssignButtonV2;

	@FindBy(xpath = OR.roomClassAssignNumbersButtonV2)
	public WebElement roomClassAssignNumbersButtonV2;

	@FindBy(xpath = OR.roomClassAssignNumbersDoneButtonV2)
	public WebElement roomClassAssignNumbersDoneButtonV2;

	@FindBy(xpath = OR.roomClassRoomNumberorNameFirstFieldV2)
	public WebElement roomClassRoomNumberorNameFirstFieldV2;
	
	@FindBy(xpath = OR.roomClassRoomHousekeepingZoneFieldV2)
	public WebElement roomClassRoomHousekeepingZoneFieldV2;


	@FindBy(xpath = OR.New_RoomClass_AssignRoomNumber)
	public WebElement NewRoomClassAssignRoomNumbers;
	
	@FindBy(xpath = OR.noRoomClassesFoundV2)
	public WebElement noRoomClassesFoundV2;
	
	@FindBy(xpath = OR.roomClassRecordsFoundV2)
	public WebElement roomClassRecordsFoundV2;
	
	@FindBy(xpath = OR.roomClassTableV2)
	public WebElement roomClassTableV2;
	
	@FindBy(xpath = OR.roomClassPaginationV2)
	public WebElement roomClassPaginationV2;
	
	@FindBy(xpath = OR.roomClassDeleteConfirmationPopupV2)
	public WebElement roomClassDeleteConfirmationPopupV2;
	
	@FindBy(xpath = OR.roomClassDeleteConfirmationPopupOKButtonV2)
	public WebElement roomClassDeleteConfirmationPopupOKButtonV2;
	
	@FindBy(xpath = OR.roomClassDeleteConfirmationPopupCancelButtonV2)
	public WebElement roomClassDeleteConfirmationPopupCancelButtonV2;
	
	@FindBy(xpath = OR.roomClassItemsPerPageDropdownV2)
	public WebElement roomClassItemsPerPageDropdownV2;
	
	@FindBy(xpath = OR.roomClassPaginationListV2)
	public WebElement roomClassPaginationListV2;
	
	@FindBy(xpath = OR.roomClassIdFieldV2)
	public WebElement roomClassIdFieldV2;
	
	@FindBy(xpath=OR.New_RoomClass_Abbrivation1)
	public WebElement New_RoomClass_Abbrivation1;

	@FindBy(xpath=OR.New_RoomCLass_Max_Persons1)
	public WebElement New_RoomCLass_Max_Persons1;

	@FindBy(xpath=OR.New_RoomClass_Room_Quantity1)
	public WebElement New_RoomClass_Room_Quantity1;
	
	@FindBy(xpath = OR.New_RoomClass_Max_Adults1)
	public WebElement New_RoomClass_Max_Adults1;

	@FindBy(xpath = OR.expandNewRoomClassStatus)
	public WebElement expandNewRoomClassStatus;

	@FindBy(xpath = OR.selectedStatusOfNewRoomClass)
	public WebElement selectedStatusOfNewRoomClass;
	
	@FindBy(xpath = OR.NewRoomText)
	public WebElement newRoomText;
	
	@FindBy(xpath = OR.roomClassRoomTabV2)
	public WebElement roomClassRoomTabV2;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
