package com.innroad.inncenter.properties;



public class ORRateGrid{

	public static final String ratesTab="//li[@class='active']";
	public static final String dailyOccupancyAndRatesLabel = "//span[(text()='Daily Occupancy & Rates')]";
	public static final String ratePlanArrow="//span[@class='Select-arrow-zone']";
	public static final String ratePlanNamesList="//div[@class='Select-menu-outer']/div[@class='Select-menu']/div";
	public static final String ratePlansColor="//div[@class='Select-menu-outer']/div[@class='Select-menu']/div/div";
	public static final String ratePlanGridLoading="//div[contains(@class,'sweet-loading')]";
	public static final String ratePlanSelectValue = "//div[@class='Select-value']";
	public static final String ratePlanNames = "//div[@class='Select-menu-outer']//div//div//div";
	// calendar xpath
	public static final  String calendarIcon = "//div[@id='daterange-calendar']";
	public static final String calendarMonthHeading = "//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div";
	public static final String StartAndAndDateICon = "//div[@class='DayPickerInput']";
//	public static final String calendarRightArrow = "//button[contains(@class,'arrowButton ')]/span[contains(@class,'right-arrow')]";
	public static final String calendarRightArrow = "//button[contains(@class,'calenderBtn-FloatRight btn btn-link')]";
	public static final String OkButton = "//div[contains(@class,'content-wrap')]//button";
	public static final String BulkUpdateCloseButton = ".close";
	public static final String NoDaysMatchAlert = "//div[contains(@class,'content-wrap')]//p";
	public static final String clickAddSecondSetOfRules = "//button[contains(@class,'second-restrictions')]";
	public static final String SeasonMonth = "//div[@class='DayPicker-Caption']//div";
	public static final String ProrateforeachSeasonCheckbox = "//div[@class='interval-form']//label";
	public static final String ClickProrateforeachSeasonCheckbox = "//span[text()='Pro-rate each season by default']";
	public static final String GetRatesOverRide = ".rulesPopupControls>b";

}
