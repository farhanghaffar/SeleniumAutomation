package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR_BE;

public class Elements_VerifyAvailabilityInBookingEngine {

	WebDriver driver ;  


	public static Logger app_logs = Logger.getLogger("devpinoyLogger");
		
		public Elements_VerifyAvailabilityInBookingEngine(WebDriver driver2)
		{
			this.driver=driver2;
			PageFactory.initElements(this.driver, this);
		}
		
		@FindBy(xpath = OR_BE.SETTINGS_LINK)
		public WebElement SETTINGS_LINK;
		
		@FindBy(xpath = OR_BE.SEARCH_DISPLAYED_RATE)
		public WebElement SEARCH_DISPLAYED_RATE;
		
		@FindBy(xpath = OR_BE.ENTIRE_AVALABILITY)
		public WebElement ENTIRE_AVALABILITY;
		
		@FindBy(xpath = OR_BE.AVAILABILITY_MONTH)
		public WebElement AVAILABILITY_MONTH;
		
		@FindBy(xpath = OR_BE.AVAILABILITY_BACKWARD)
		public WebElement AVAILABILITY_BACKWARD;
		
		@FindBy(xpath = OR_BE.AVAILABILITY_FORWARD)
		public WebElement AVAILABILITY_FORWARD;
		
		@FindBy(xpath = OR_BE.AVAILABILITY_ROOM_CLASSES)
		public List<WebElement> AVAILABILITY_ROOM_CLASSES;
		
		@FindBy(xpath = OR_BE.CHECK_IN)
		public WebElement CHECK_IN;
		
		@FindBy(xpath = OR_BE.CHECK_IN_DATE)
		public WebElement CHECK_IN_DATE;
		
		@FindBy(xpath = OR_BE.CHECK_OUT)
		public WebElement CHECK_OUT;
		
		@FindBy(xpath = OR_BE.CHECK_OUT_DATE)
		public WebElement CHECK_OUT_DATE;
		
		@FindBy(xpath = OR_BE.ADULTS)
		public WebElement ADULTS;
		
		@FindBy(xpath = OR_BE.NO_OF_ADULTS)
		public WebElement NO_OF_ADULTS;
		
		@FindBy(xpath = OR_BE.CHILDREN)
		public WebElement CHILDREN;
		
		@FindBy(xpath = OR_BE.NO_OF_CHILDREN)
		public WebElement NO_OF_CHILDREN;
		
		@FindBy(xpath = OR_BE.VIEW_RATES_FROM)
		public WebElement VIEW_RATES_FROM;
		
		@FindBy(xpath = OR_BE.DETAILS_POPUP)
		public WebElement DETAILS_POPUP;
		
		
}
