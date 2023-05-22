package com.innroad.inncenter.tests;

import static org.testng.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.ContactUsForm;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.LogStatus;

public class GoogleReviewChallenge extends TestCore {
	
	public WebDriver driver = null;
	ArrayList<String> testSteps = new ArrayList<>();
	String scriptName = "";
	String description = "";
	String testcatagory = "";
	int count = 1;
	
	@Test(dataProvider = "getData")
	public void contactUsForm() {
				
		driver = getDriver();
		try {
			
			 driver.get("https://www.kressleysautoandtruck.com/");
			
		} catch (Exception e) {
		}catch (Error e) {
		}
		}	
			
	@AfterMethod
	public void close() {
		driver.quit();
	}
	
	@DataProvider
	public Object[][] getData() {
		
		// return test data from the sheetname provided
		return Utility.getData("Sheet2", excel);
	}

	

}
