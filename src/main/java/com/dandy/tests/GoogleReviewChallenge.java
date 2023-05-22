package com.dandy.tests;

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
	public void contactUsForm(String compnayName, String type, String website, String phone,String address,
			String country, String email, String review1,String review2,String review3,
			String review4,String review5, String badReview) {
				
		driver = getDriver();
		try {
			
			
			 driver.get("https://www.kressleysautoandtruck.com/");
			 scriptName = compnayName;
			 description = "Website: <br>"	
			+ "<a href='"+website+"' target='_blank'>"
					+ "Click here to open website: "+website+"</a>";
			 testcatagory = "Form Filling";

				test = extent.startTest(scriptName, description).assignCategory(testcatagory).assignCategory("Filling Data");

			Wait.wait3Second();
			ContactUsForm contactUsForm = new ContactUsForm();
			boolean isFormShowing = contactUsForm.SubmitForm(driver,website);
			if (!isFormShowing) {
				test.log(LogStatus.FAIL, "Failed to click on contact us form");
				assertFalse(true);
			}
			test.log(LogStatus.PASS, test.addScreenCapture(Utility
					.captureScreenShot(scriptName  +"_" + Utility.getTimeStamp(), driver)));
			
			contactUsForm.GetAttribute(driver,compnayName,badReview, test, scriptName);
			
			test.log(LogStatus.PASS, "Submitted form");
			test.log(LogStatus.PASS, test.addScreenCapture(Utility
							.captureScreenShot(scriptName  +"_" + Utility.getTimeStamp(), driver)));

		} catch (Exception e) {
			Utility.updateReport(e, "Failed to fill form", scriptName, scriptName, driver);
		}catch (Error e) {
			Utility.updateReport(e, "Failed to fill form", scriptName, scriptName, driver);
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
