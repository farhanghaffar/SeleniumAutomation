package com.innroad.inncenter.pages;

public class NewRoomClassPageObjectV2 {
	public static final String setUP = "(//span[text()='Setup'])[2]";
	public static final String roomClasses ="//ul[@class='sec_nav']//li/a[text()='Room Classes']";
	public static final String createnewRoomClass ="//button[@class='ant-btn ant-btn-primary' and span ='New Room Class']";
	public static final String newRoomLabel ="//h1[text()='New Room']";
	public static final String detailsLabel="(//div[text()='Details'])[1]";
	public static final String roomClassNamev2="//input[@id ='name' and @class='ant-input']";
	public static final String roomClassABv2="//input[@id ='abbreviation' and @class='ant-input']";
	public static final String sortOrderv2="//input[@id ='sortOrder']";
	public static final String maxAdultv2="//input[@id ='maxAdults']";
	public static final String maxPersonv2="//input[@id ='maxPersons']";
	public static final String smokingPolicyv2="//div[@id ='isSmokingAllowed']//span//i";
	public static final String editDescription ="//button[@title ='Edit Description']";
	public static final String detailsv2 = "//div[@class='jodit_wysiwyg']";
	public static final String done_descv2="//span[text()='Done' ]";
	public static final String roomsv2="//div[text()='Rooms']";
	public static final String roomsquantityv2="//div[@class='ant-input-number-input-wrap']//input[@placeholder='Placeholder']";
	public static final String roomquantityButtonv2 ="//div[@class='ant-col-1']//button[@type ='button']";
	public static final String roomNamev2 ="//input[@class='ant-input PhysicalRooms_roomName_L5kZX']";
	public static final String stationIdv2 ="//input[@id='rooms[0].stationId']";
	public static final String sortOrder ="//input[@id='rooms[0].sortOrder']";
	public static final String houseKeepingZonev2="(//div[@id='rooms[0].housekeepingZoneId']/div/div/following::span/i)[1]";
    public static final String doneButtonv2 ="//span[text()='Done']";
    public static final String publishv2 ="//span[text()='PUBLISH']";
    public static final String roomClassPagination ="//ul[contains(@class, 'RoomClasses_tablePagination')]";
    public static final String noRoomClassesFound = "//div[contains(text(),'No Room Classes Found')]";
	public static final String roomClassRecordsFound = "//p[contains(text(),'Record(s) found')]";
	public static final String roomClassAlphabetFilter = "//div[contains(@class,'RoomClasses_alphabetFilter')]";
	public static final String roomClassPageStatusDropdown = "(//span[.='STATUS:']//following::div[@aria-autocomplete='list'])[1]";
	public static final String roomClassItemsPerPageDropdown = "//div[text()='Items Per Page']//following::div[@aria-autocomplete='list']//div[@title]";
    public static final String roomClassTable = "//table//tbody/tr";
    public static final String roomClassPaginationList = "//ul[contains(@class, 'RoomClasses_tablePagination')]/li";


}
