
package com.innroad.inncenter.properties;

public class OR_NightlyRatePlan {

	public static final String RatePlan_CreateSeason="//span[text()='Create season']";
	public static final String RatePlan_SeasonName="//input[@placeholder='Enter season name']";
	public static final String RatePlan_Season_Sunday="//span[text()='Sun']/following-sibling::span";
	public static final String RatePlan_Season_Monnday="//span[text()='Mon']/following-sibling::span";
	public static final String RatePlan_Season_Tuesday="//span[text()='Tue']/following-sibling::span";
	public static final String RatePlan_Season_Wednesday="//span[text()='Wed']/following-sibling::span";
	public static final String RatePlan_Season_Thursday="//span[text()='Thu']/following-sibling::span";
	public static final String RatePlan_Season_Friday="//span[text()='Fri']/following-sibling::span";
	public static final String RatePlan_Season_Saturday="//span[text()='Sat']/following-sibling::span";
	public static final String RatePlan_Season_CreateSeason="//span[text()='Create season']/..";
	public static final String RatePlan_Season_ColorDropDown="//div[contains(text(),'Select season color')]/span[@class='down-arrow']";
	public static final String RatePlan_Season_ChargesForAdditionalChildAdult="//span[text()='Charge for additional adult/child']/following-sibling::div/button";
	//public static final String RatePlan_Season_AddRoomClass="//span[text()='Add room class']/preceding-sibling::i";
	public static final String RatePlan_Season_AddRoomClass="//span[text()='Add room class']/preceding-sibling::span/span";
	public static final String RatePlan_Season_RulesRestrictionsOnSeason="//a[text()='Rules/Restrictions']";
	public static final String RatePlan_Season_AssignRulesByRoomClass="//span[text()='Assign rules by room class']/following-sibling::button";
	public static final String RatePlan_Season_SeasonRulesSpecificRoomClasses="//span[text()='Choose existing room class(s)']";
	public static final String RatePlan_Season_SeasonMinNightsRule="//span[text()='Min nights']/preceding-sibling::span/input";
	public static final String RatePlan_Season_SeasonMinNightsRuleValue="//span[text()='Min nights']/./../../following-sibling::div//input";
	public static final String RatePlan_Season_SeasonNoCheckIn="//span[text()='No check-in']/preceding-sibling::span/input";
	public static final String RatePlan_Season_SeasonNoCheckOut="//span[text()='No check-out']/preceding-sibling::span/input";
	public static final String RatePlan_Season_SeasonSave="//span[text()='Save']";
	public static final String RatePlan_Season_SeasonPolices="//ul[@class='seasonNavMenu']/li//a[text()='Policies']";
	public static final String RatePlan_Season_CompleteChanges="//span[text()='Complete changes']/..";
	public static final String RatePlan_Season_DefaultRatePlan="//label[contains(text(),'Default Rate Plan')]/../..//button";
    public static final String RatePlan_Season_SaveAsActive="//span[text()='Save as active']/..";
    public static final String RatePlan_Season_Blockout="//span[text()='Blackout']/..";
    public static final String DEFAULT_RATEPLAN_TOGGLE="//label[contains(text(),'Default Rate Plan')]/../..//button";
    public static final String DEFAULT_RATEPLAN_TOOLTIP = "//label[contains(text(),'Default Rate Plan')]/../..//span[@class='pop-data']";
    public static final String RatePlan_Season_Edit_Calender= "//div[contains(text(),'Calendar')]";
    public static final String RatePlan_Season_FillBlanks="//span[contains(text(),'Fill blanks')]/..";
    public static final String RatePlan_Season_SaveRatePlan="//span[contains(text(),'Save rate plan')]/..";
    public static final String RatePlan_Season_Replace= "//span[text()='Replace']/..";
	public static final String ROOMCLASSES   = "//span[@class='ant-checkbox']/following-sibling::span";
    public static final String editThisSeason = "//button/span[text()='Edit this season']";
    public static final String editSeasonTitle = "//h2[@class='content-title']";
    public static final String editSeasonCloseIcon = "//button[@aria-label='Close']/span";
    public static final String editSeasonCloseYesButton = "//button/span[contains(text(),'Yes')]";
	public static final String policiesCheckBox="//div[@class='center-screen']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]";
	public static final String policiesNames="//div[@class='center-screen']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]/following-sibling::span";
	public static final String policiesCheckBoxFromSeason="//div[@class='policy-set-item']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]";
	public static final String policiesNameFromSeason="//div[@class='policy-set-item']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]/following-sibling::span";
	 public static final String assignPoliciesByRoomClass="//span[contains(text(),'Assign Policies by room class')]/..//button";
	 public static final String assignRuleByRoomClass="//span[contains(text(),'Assign rules by room class')]/following-sibling::button";

	public static final String RatePlan_Season_AssignPolicyByRoomClass = "//span[text()='Assign Policies by room class']/following-sibling::button";


}
