package com.innroad.inncenter.webelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.innroad.inncenter.properties.OR_Airbnb;

public class Elements_Airbnb {

	@FindBy(xpath = OR_Airbnb.ClickContinueWithEmail)
	public WebElement clickContinueWithEmail;
	
	@FindBy(xpath = OR_Airbnb.EnterEmail)
	public WebElement enterEmail;
	
	@FindBy(xpath = OR_Airbnb.EnterPassword)
	public WebElement enterPassword;
	
	@FindBy(xpath = OR_Airbnb.ClickLoginButton)
	public WebElement clickLoginButton;
	
	@FindBy(xpath = OR_Airbnb.AcceptCookiesButtonOK)
	public WebElement acceptCookiesButtonOK;
	

}
