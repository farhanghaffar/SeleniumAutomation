package com.innroad.inncenter.pageobjects;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.innroad.inncenter.pages.ARegisterUser;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.ARegisterWebElements;

public class ARegister {

	private WebDriver driver;
	private ARegisterWebElements elements;
	
	public ARegister( WebDriver driver) {
		this.driver = driver;
		elements = new ARegisterWebElements(driver);
	}
	
	public void clickOnRegisterBtn() {
		Wait.waitForElementToBeClickable(By.xpath(ARegisterUser.btnAllow), driver);
		elements.btnAllow.click();
		
		Wait.waitForElementToBeClickable(By.xpath(ARegisterUser.btnRegister), driver);
		elements.btnRegister.click();
		System.out.println("Click on Join as participant button");
	}
	
	public ArrayList<String> registerUser(String firstName,String lastName,String email,
			String password,String confirmPassword,String imgPath){
		
		ArrayList<String>  steps = new ArrayList();
		
		Wait.waitForElementToBeClickable(By.xpath(ARegisterUser.firstName), driver);
		elements.firstName.sendKeys(firstName);
		steps.add("Enter first Name: "+firstName);
		
		elements.lastName.sendKeys(lastName);
		steps.add("Enter last Name: "+lastName);
		
		elements.email.sendKeys(email);
		steps.add("Enter email: "+email);

		elements.password.sendKeys(password);
		steps.add("Enter password: "+password);
		
		elements.confirmPassword.sendKeys(confirmPassword);
		steps.add("Enter confirm password Name: "+confirmPassword);
		
		
		elements.agreedTC.click();
		steps.add("Checked term and condiction");
		
		elements.btnSignUp.click();
		steps.add("Click on btn setup");
		
		return steps;
		
	}
	
}
