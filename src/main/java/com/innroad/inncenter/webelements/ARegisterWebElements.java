package com.innroad.inncenter.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.pages.ARegisterUser;

public class ARegisterWebElements {
	
	WebDriver driver = null;
	public ARegisterWebElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	
	
	@FindBy(xpath = ARegisterUser.btnAllow)
	public WebElement btnAllow;
	
	@FindBy(xpath = ARegisterUser.btnRegister)
	public WebElement btnRegister;
	
	@FindBy(xpath = ARegisterUser.firstName)
	public WebElement firstName;
	
	@FindBy(xpath = ARegisterUser.lastName)
	public WebElement lastName;
	
	@FindBy(xpath = ARegisterUser.email)
	public WebElement email;
	
	@FindBy(xpath = ARegisterUser.password)
	public WebElement password;
	
	@FindBy(xpath = ARegisterUser.confirmPassword)
	public WebElement confirmPassword;
	
	@FindBy(xpath = ARegisterUser.agreedTC)
	public WebElement agreedTC;
	
	@FindBy(xpath = ARegisterUser.btnSignUp)
	public WebElement btnSignUp;
	

}

