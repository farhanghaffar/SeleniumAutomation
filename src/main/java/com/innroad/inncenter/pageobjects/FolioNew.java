package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle.Control;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.innroad.inncenter.model.CheckOutSuccessPaymentPopup;
import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pages.NewFolio;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_FolioNew;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_All_Payments;
import com.innroad.inncenter.webelements.Elements_FolioNew;
import com.innroad.inncenter.webelements.Elements_Reports;
import com.innroad.inncenter.webelements.Elements_ReservationV2;

import bsh.util.Util;

public class FolioNew {
	
	public static Logger folioLogger = Logger.getLogger("NewFolio");
	
	public ArrayList<String> getCount_FolioTypes(WebDriver driver, ArrayList<String> test_steps) {

		List<WebElement> folioTypes=driver.findElements(By.xpath(OR_FolioNew.AllFolios));
		folioLogger.info(folioTypes.size());
		for(WebElement ele:folioTypes)
		{
			folioLogger.info(ele.getText());
		}
	
		test_steps.add("Successfully opened the Folio Tab");
		folioLogger.info("Successfully opened the Folio Tab");
		return test_steps;
	}
	
	public ArrayList<String> getFolioItemValues(WebDriver driver, ArrayList<String> test_steps) {
				
		HashMap<String, String> hmCatgory=new HashMap<String, String>();
		HashMap<String, String> hmAmount=new HashMap<String, String>();
		HashMap<String, String> hmTax=new HashMap<String, String>();
		HashMap<String, String> hmTotal=new HashMap<String, String>();
		List<WebElement> folioTypes=driver.findElements(By.xpath(OR_FolioNew.AllFolios));
		folioLogger.info(folioTypes.size());
		for(WebElement ele:folioTypes)
		{
			folioLogger.info("Folio Type is "+ele.getText());
			test_steps.add("Folio Type is "+ele.getText());
			ele.click();
			try {
					getValuesCharges(driver, test_steps);
					getValuesPayments(driver, test_steps);
					getValuesAuthorization(driver, test_steps);
					getValuesBalance(driver, test_steps);
					int totalRows=driver.findElements(By.xpath("//th[contains(@class,'lineitem-category')]//parent::tr//parent::thead//parent::table//tbody//tr")).size();
					
						for(int i=1;i<=totalRows;i++)
							{
							String xpathCategory="//th[contains(@class,'lineitem-category')]//parent::tr//parent::thead//parent::table//tbody//tr[" + i + "]//td[5]";
							String xpathAmount="//th[contains(@class,'lineitem-amount')]//parent::tr//parent::thead//parent::table//tbody//tr[" + i + "]//td[9]";
							String xpathTax="//th[contains(@class,'lineitem-tax')]//parent::tr//parent::thead//parent::table//tbody//tr[" + i + "]//td[10]";
							String xpathTotal="//th[contains(@class,'lineitem-total')]//parent::tr//parent::thead//parent::table//tbody//tr[" + i + "]//td[11]";
							
							String categoryType=driver.findElement(By.xpath(xpathCategory)).getText();
							String amount=driver.findElement(By.xpath(xpathAmount)).getText();
							String tax=driver.findElement(By.xpath(xpathTax)).getText();
							String total=driver.findElement(By.xpath(xpathTotal)).getText();
							hmCatgory.put("Category_row("+i+")", categoryType);
							hmAmount.put("Amount_row("+i+")", amount);
							hmTax.put("Tax_row("+i+")", tax);
							hmTotal.put("Total_row("+i+")", total);
							test_steps.add("For row "+i+  " Category is "+hmCatgory.get("Category_row("+i+")")+ " ,Amount  is  "+hmAmount.get("Amount_row("+i+")")+" , Tax is  "+
									hmTax.get("Tax_row("+i+")")+" and Total is  "+hmTotal.get("Total_row("+i+")"));		
							folioLogger.info("For row "+i+  " Category is "+hmCatgory.get("Category_row("+i+")")+ " ,Amount  is  "+hmAmount.get("Amount_row("+i+")")+" , Tax is  "+
									hmTax.get("Tax_row("+i+")")+" and Total is  "+hmTotal.get("Total_row("+i+")"));	
							}
		
				
				}catch (Exception e) {
						test_steps.add("No line items.");
						folioLogger.info("No line items.");
					}
	
		}

		test_steps.add("Successfully read all the folio line items for each folio type");
		folioLogger.info("Successfully read all the folio line items for each folio type");
		return test_steps;
	}
	
	public void clickAddCharge(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_FolioNew folio = new Elements_FolioNew(driver);	
		String path = "//button/span[text()=' Add ']";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		driver.findElement(By.xpath(path)).click();
		folioLogger.info("Clicked on Add");
		test_steps.add("Clicked on Add");
		Wait.WaitForElement(driver, OR_FolioNew.AddCharge);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.AddCharge), driver);
		Wait.wait5Second();
		folio.AddCharge.click();
		folioLogger.info("Clicked on  Charge button");
		test_steps.add("Clicked on  Charge button");
	}
	
	public void selectCategory(WebDriver driver, ArrayList<String> test_steps, String category) throws InterruptedException {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		String strCategory="//a[contains(text(),'"+category+"')]";
		Wait.waitForElementToBeVisibile(By.xpath(OR_FolioNew.CategorySelect), driver);
		Wait.WaitForElement(driver, OR_FolioNew.CategorySelect);
		folio.CategorySelect.click();
		folioLogger.info("Click on Category Drop Down Box");
		test_steps.add("Click on Category Drop Down Box");
		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_FOLIO_SearchCategoryName);
		Utility.ScrollToElement(res.GUEST_FOLIO_SearchCategoryName, driver);
		res.GUEST_FOLIO_SearchCategoryName.sendKeys(category);
		folioLogger.info("Enter Category Name :" +category);
		test_steps.add("Enter Category Name :" +category);
		Wait.WaitForElement(driver, strCategory);
		Utility.scrollAndClick(driver, By.xpath(strCategory));
		folioLogger.info("Select Category  :" +category);
		test_steps.add("Select Category :" +category);
		}
	
	public void enterAmountFolioLineItem(WebDriver driver, ArrayList<String> test_steps, String amount) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_FolioNew.EnterAmountFolio), driver);
		folio.EnterAmountFolio.sendKeys(amount);
		test_steps.add("Entered amount for the given Folio Category is "+amount);
		folioLogger.info("Entered amount for the Folio Category is "+amount);
	}
	
	public void clickSaveChanges(WebDriver driver, ArrayList<String> test_steps) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.SaveChangesButton), driver);
		folio.SaveChangesButton.click();
		folioLogger.info("Clicked on Save Changes button");
		test_steps.add("Clicked on Save Changes button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 60);
	}
	
	public void addFolioLineItem(WebDriver driver, ArrayList<String> test_steps, String category, String amount) throws InterruptedException {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		try {
			clickAddCharge(driver, test_steps);
			Wait.wait10Second();			
			selectCategory(driver, test_steps, category);
			enterAmountFolioLineItem(driver, test_steps, amount);
			Wait.wait5Second();
			folioClickSave(driver, test_steps);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
			test_steps.add("Successfully added Folio line item");
			folioLogger.info("Successfully added Folio line item");
		}catch (Exception e) {
			folioLogger.info(e.toString());
		}
	}
	
	
	public void addFolioLineItemMRB(WebDriver driver,ArrayList<String> test_steps,String category, String amount) throws InterruptedException {		
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			Wait.wait1Second();
			allFolios.get(i).click();
			clickAddCharge(driver, test_steps);
			try {
			selectCategory(driver, test_steps, category);}
			catch(Exception e) {
				selectCategory(driver, test_steps, category);
			}
			enterAmountFolioLineItem(driver, test_steps, amount);
			folioClickSave(driver, test_steps);
			test_steps.add("Successfully added Folio line item");
			folioLogger.info("Successfully added Folio line item");
		}
	}
	
	public ArrayList<String> getValuesCharges(WebDriver driver, ArrayList<String> test_steps) {

		String charges=driver.findElement(By.xpath("//li[contains(@class,'folio-listHover')]/a")).getText();
		test_steps.add(charges);
		folioLogger.info(charges);
	
		test_steps.add("Charge values are displayed");
		folioLogger.info("Charge values are displayed");
		return test_steps;
	}
	
	public ArrayList<String> getValuesPayments(WebDriver driver, ArrayList<String> test_steps) {

//		driver.findElement(By.xpath(OR.Payments)).click();
		String payments=driver.findElement(By.xpath("//li[contains(@class,'folio-listHover')]//following-sibling::li/a")).getText();
		test_steps.add(payments);
		folioLogger.info(payments);
	
		test_steps.add("Payment values are displayed");
		folioLogger.info("Payment values are displayed");
		return test_steps;
	}
	
	public ArrayList<String> getValuesAuthorization(WebDriver driver, ArrayList<String> test_steps) {

//		driver.findElement(By.xpath(OR.Authorization)).click();
		String authorization=driver.findElement(By.xpath("//li[contains(@class,'folio-listHover')]//following-sibling::li[2]/a")).getText();
		test_steps.add(authorization);
		folioLogger.info(authorization);
	
		test_steps.add("Authorization values are displayed");
		folioLogger.info("Authorization values are displayed");
		return test_steps;
	}
	
	public ArrayList<String> getValuesBalance(WebDriver driver, ArrayList<String> test_steps) {

//		driver.findElement(By.xpath(OR.BalanceGrid)).click();
		String balance=driver.findElement(By.xpath("//li[contains(@class,'folio-listHover')]//following-sibling::li[3]/a")).getText();
		System.out.println(balance);
		test_steps.add(balance);
		folioLogger.info(balance);
	
		test_steps.add("Balance values are displayed");
		folioLogger.info("Balance values are displayed");
		return test_steps;
	}
	
	public ArrayList<String> folioSettings(WebDriver driver, ArrayList<String> test_steps,String FolioName, String FolioDes
			,String isVoidItem, String isPendingItems, String showAuthReport, String PhoneAccess
			, String PosPosting, String MovieRes, String CreditLimit, String moveAccToFolio ) throws InterruptedException {
		 
		Elements_FolioNew FolioPage=new Elements_FolioNew(driver);
		driver.findElement(By.xpath(OR_FolioNew.SettingsIcon)).click();
		Wait.explicit_wait_visibilityof_webelement(FolioPage.EleFolioName, driver);
		Utility.ScrollToElement(FolioPage.EleFolioName, driver);
		
				if(!(FolioName).isEmpty())
				{				
					FolioPage.EleFolioName.clear();
					FolioPage.EleFolioName.sendKeys(FolioName);
					folioLogger.info("Entered Folio Name is  "+FolioName);
					test_steps.add("Entered Folio Name is  "+FolioName);
				}
				else {
					folioLogger.info("Defualt folio name is slelected");
					test_steps.add("Defualt folio name is slelected");
				}
		
				if(!(FolioDes).isEmpty())
				{
					FolioPage.EleFolioDescription.clear();
					FolioPage.EleFolioDescription.sendKeys(FolioDes);
					folioLogger.info("Entered Folio Name is  "+FolioDes);
					test_steps.add("Entered Folio Name is  "+FolioDes);
				}
				else {
					folioLogger.info("Defualt folio description is slelected");
					test_steps.add("Defualt folio description is slelected");
				}
		
				if(isVoidItem.equalsIgnoreCase("No"))
				{
					
					FolioPage.EleToggleDisplayVoidItems.click();
					
					folioLogger.info("Display Voided Items toggle is switched off");
					test_steps.add("Display Voided Items toggle is switched off");
				}
				else {
					folioLogger.info("Display Voided Items toggle is switched on by default");
					test_steps.add("Display Voided Items toggle is switched on by default");
				}
				
				if(isPendingItems.equalsIgnoreCase("No"))
				{
					FolioPage.EleToggleDisplayPendingItems.click();
					
					folioLogger.info("Display Pending Items toggle is switched off");
					test_steps.add("Display Pending Items toggle is switched off");
				}
				else {
					folioLogger.info("Display Pending Items toggle is switched on by default");
					test_steps.add("Display Pending Items toggle is switched on by default");
				}
				
				if(showAuthReport.equalsIgnoreCase("No"))
				{
					FolioPage.EleToggleShowAuthReport.click();
					
					folioLogger.info("Show Authorizations in Report toggle is switched off");
					test_steps.add("Show Authorizations in Report toggle is switched off");
				}
				else {
					folioLogger.info("Show Authorizations in Report toggle is switched on by default");
					test_steps.add("Show Authorizations in Report toggle is switched on by default");
				}
				
				/*if(!(PhoneAccess).isEmpty())
				{
					FolioPage.ElePhoneAccess.click();
					List<WebElement> optionsPhoneAccess=driver.findElements(By.xpath(OR_FolioNew.DdlValuesPhoneAccess));
					for(WebElement ele:optionsPhoneAccess)
					{
						folioLogger.info(ele.getText());
						if(ele.getText().equalsIgnoreCase(PhoneAccess))
						{
							ele.click();
							
							break;
						}
					}
					folioLogger.info("Selected Phone Access option is " +PhoneAccess);
					test_steps.add("Selected Phone Access option is " +PhoneAccess);
					
				}
				else {
					folioLogger.info("Defualt Phone Access option is selected");
					test_steps.add("Defualt Phone Access option is selected");
				}
				
				if(!(PosPosting).isEmpty())
				{
					FolioPage.ElePosPosting.click();
					List<WebElement> optionsPosPosting=driver.findElements(By.xpath(OR_FolioNew.DdlValuesPosPosting));
					for(WebElement ele:optionsPosPosting)
					{
						folioLogger.info(ele.getText());
						if(ele.getText().equalsIgnoreCase(PosPosting))
						{
							ele.click();
							break;
						}
					}
					folioLogger.info("Selected Pos Posting option is " +PosPosting);
					test_steps.add("Selected Pos Posting option is " +PosPosting);
				}
				else {
					folioLogger.info("Defualt Pos Posting  option is selected");
					test_steps.add("Defualt Pos Posting  option is selected");
				}*/
				
				if(!(MovieRes).isEmpty())
				{
					FolioPage.EleMovieRestrictions.click();
					List<WebElement> optionsMovieRes=driver.findElements(By.xpath(OR_FolioNew.DdlValuesMovieRes));
					for(WebElement ele:optionsMovieRes)
					{
						folioLogger.info(ele.getText());
						if(ele.getText().equalsIgnoreCase(MovieRes))
						{
							ele.click();
							break;
						}
					}
					folioLogger.info("Selected Movie Restrictions option is " +MovieRes);
					test_steps.add("Selected Movie Restrictions option is " +MovieRes);
				}
				else {
					folioLogger.info("Defualt Movie Restrictions option is selected");
					test_steps.add("Defualt Movie Restrictions option is selected");
				}
				
				if(!(CreditLimit).isEmpty())
				{
					FolioPage.EleCreditLimit.click();
					FolioPage.EleCreditLimit.clear();
					FolioPage.EleCreditLimit.sendKeys(CreditLimit);
					
					folioLogger.info("Entered Credit Limit value is "+CreditLimit);
					test_steps.add("Entered Credit Limit value is "+CreditLimit);
				}
				else {
					folioLogger.info("Credit limit value is not entered, 0 value will be stored to the field");
					test_steps.add("Credit limit value is not entered, 0 value will be stored to the field");
				}
				
				if(!(moveAccToFolio).isEmpty())
				{
					FolioPage.EleMoveToAccFolio.click();
					List<WebElement> optionsMoveToAccFolio=driver.findElements(By.xpath(OR_FolioNew.DdlValuesMoveToAccFolio));
					for(WebElement ele:optionsMoveToAccFolio)
					{
						folioLogger.info(ele.getText());
						if(ele.getText().equalsIgnoreCase(moveAccToFolio))
						{
							ele.click();
							break;
						}
					}
					folioLogger.info("Selected Move To Account Folio option is " +moveAccToFolio);
					test_steps.add("Selected Move To Account Folio option is " +moveAccToFolio);
				}
				else {
					folioLogger.info("Defualt Move To Account Folio option is selected");
					test_steps.add("Defualt Move To Account Folio option is selected");
				}
		
		driver.findElement(By.xpath(OR_FolioNew.SaveBtn)).click();
		test_steps.add("Folio settings are done and saved sucessfully");
		folioLogger.info("Folio settings are done and saved sucessfully");
		return test_steps;
	}
	
	public List<WebElement> getAllFolioLinks(WebDriver driver){
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		List<WebElement> allFolios = folio.AllFolios;
		return allFolios;
	}
	
	public double getFolioCharges(WebDriver driver, ArrayList<String> test_steps) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		String charges = null;
		double chargesValue = 0.0;
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			allFolios.get(i).click();
			Wait.WaitForElement(driver, OR_FolioNew.ChargesValue);
			charges = folio.ChargesValue.getText().replace("$", "").trim();
			chargesValue = chargesValue + Double.parseDouble(charges);
		}
		test_steps.add("Folio charges: "+chargesValue);
		folioLogger.info("Folio charges: "+chargesValue);
		return chargesValue;
	}
	
	public double getFolioPayments(WebDriver driver, ArrayList<String> test_steps) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.WaitForElement(driver, OR_FolioNew.PaymentValue);
		String payments = null;
		double paymentsValue = 0.0;	
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			allFolios.get(i).click();
			payments = folio.PaymentValue.getText().replaceAll("[-$£ ]", "").trim();
			paymentsValue = paymentsValue + Double.parseDouble(payments);	
		}
		test_steps.add("Payments: "+paymentsValue);
		folioLogger.info("Payments: "+paymentsValue);	
		return paymentsValue;
	}
	
	public double getFolioAuthorization(WebDriver driver, ArrayList<String> test_steps) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.WaitForElement(driver, OR_FolioNew.AuthorizationValue);
		String authorization = null;
		double authorizationValue = 0.0;		
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			allFolios.get(i).click();
			authorization = folio.AuthorizationValue.getText().replace("$", "").trim();
			authorizationValue = authorizationValue + Double.parseDouble(authorization);			
		}		
		test_steps.add("Authorization Value: "+authorizationValue);
		folioLogger.info("Authorization Value: "+authorizationValue);
	
		return authorizationValue;
	}
	
	public double getFolioBalance(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
		String balance = null;
		double balanceValue = 0.0;		
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			allFolios.get(i).click();
			Wait.wait2Second();
			balance = folio.BalanceValue.getText().replace("$", "").trim();
			balanceValue = balanceValue + Double.parseDouble(balance);
		}	
		test_steps.add("Folio Balance: "+balanceValue);
		folioLogger.info("Folio Balance: "+balanceValue);
	
		return balanceValue;
	}
	
	public double getFolioTaxesOnLineItem(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		ReservationV2 res = new ReservationV2();
		Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
		String tax = null;
		double taxValue = 0.0;		
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			allFolios.get(i).click();
			Wait.wait2Second();			
			ArrayList<FolioLineItem> folioLine = res.getAllFolioLineItems(driver);
			for (int j = 0; j < folioLine.size(); j++) {
				tax = folioLine.get(j).getITEM_TAX().replace("$", "").trim();
				taxValue = taxValue + Double.parseDouble(tax);			
			}
		}	
		test_steps.add("Folio Taxes: "+taxValue);
		folioLogger.info("Folio Taxes: "+taxValue);
	
		return taxValue;
	}
	
	public double getFolioFees(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		ReservationV2 res = new ReservationV2();
		Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
		String fee = null;
		double feeValue = 0.0;		
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			allFolios.get(i).click();
			Wait.wait2Second();			
			ArrayList<FolioLineItem> folioLine = res.getAllFolioLineItems(driver);
			for (int j = 0; j < folioLine.size(); j++) {
				if (folioLine.get(j).getITEM_CATEGORY().contains("Fee")) {
					fee = folioLine.get(j).getITEM_AMOUNT().replace("$", "").trim();
					feeValue = feeValue + Double.parseDouble(fee);
				}		
			}
		}	
		test_steps.add("Folio Fees: "+feeValue);
		folioLogger.info("Folio Fees: "+feeValue);
	
		return feeValue;
	}
	
	public double getFolioRoomCharges(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		ReservationV2 res = new ReservationV2();
		Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
		String roomCharge = null;
		double roomChargeValue = 0.0;		
		List<WebElement> allFolios = folio.AllFolios;
		for (int i = 0; i < allFolios.size(); i++) {
			allFolios.get(i).click();
			Wait.wait2Second();			
			ArrayList<FolioLineItem> folioLine = res.getAllFolioLineItems(driver);
			for (int j = 0; j < folioLine.size(); j++) {
				if (folioLine.get(j).getITEM_CATEGORY().contains("Room Charge")) {
					roomCharge = folioLine.get(j).getITEM_AMOUNT().replace("$", "").trim();
					roomChargeValue = roomChargeValue + Double.parseDouble(roomCharge);
				}		
			}
		}	
		test_steps.add("Folio Room Charges: "+roomChargeValue);
		folioLogger.info("Folio Room Charges: "+roomChargeValue);	
		return roomChargeValue;
	}
	
	public boolean validateFolioCharges(WebDriver driver, ArrayList<String> test_steps, TripSummary tripSummary) {
		boolean flag = false;
		double folioCharges = getFolioCharges(driver, test_steps);
		double tripTotal = Double.parseDouble(tripSummary.getTS_TRIP_TOTAL().replace("$", "").trim());
		
		if (folioCharges == tripTotal) {
			folioLogger.info("Folio total charges successfully validated with Trip total");
			test_steps.add("Folio total charges successfully validated with Trip total");
			flag = true;
		}else {
			folioLogger.info("Failed - validating Folio charges with Trip total is failed. Expected: "+tripTotal+" But found: "+folioCharges);
			test_steps.add("AssertionError - Failed - validating Folio charges with Trip total is failed. Expected: "+tripTotal+" But found: "+folioCharges);
		}
		return flag;
	}
	
	public boolean validateFolioBalance(WebDriver driver, ArrayList<String> test_steps, TripSummary tripSummary) throws Exception {
		boolean flag = false;
		double folioBalance = getFolioBalance(driver, test_steps);
		double balance = Double.parseDouble(tripSummary.getTS_BALANCE().replace("$", "").trim());
		
		if (folioBalance == balance) {
			folioLogger.info("Folio Balance successfully validated with Trip Summary Balance");
			test_steps.add("Folio Balance successfully validated with Trip Summary Balance");
			flag = true;
		}else {
			folioLogger.info("Failed - validating Folio Balance with Trip summary Balance is failed. Expected: "+balance+" But found: "+folioBalance);
			test_steps.add("AssertionError - Failed - validating Folio Balance with Trip summary Balance is failed. Expected: "+balance+" But found: "+folioBalance);
		}
		return flag;
	}
	
	public boolean validateFolioPayments(WebDriver driver, ArrayList<String> test_steps, TripSummary tripSummary) {
		boolean flag = false;
		double folioPayments = getFolioPayments(driver, test_steps);
		double paid = Double.parseDouble(tripSummary.getTS_PAID().replace("$", "").trim());
		
		if (folioPayments == paid) {
			folioLogger.info("Folio Payments successfully validated with Trip Summary Paid amount");
			test_steps.add("Folio Payments successfully validated with Trip Summary Paid amount");
			flag = true;
		}else {
			folioLogger.info("Failed - validating Folio Payments with Trip summary Paid amount is failed. Expected: "+paid+" But found: "+folioPayments);
			test_steps.add("AssertionError - Failed - validating Folio charges with Trip summary Paid amount is failed. Expected: "+paid+" But found: "+folioPayments);
		}
		return flag;
	}
	
	public boolean validateFolioTaxes(WebDriver driver, ArrayList<String> test_steps, TripSummary tripSummary) throws Exception {
		boolean flag = false;
		double folioTax = getFolioTaxesOnLineItem(driver, test_steps);
		double tripTax = Double.parseDouble(tripSummary.getTS_TAXES().replace("$", "").trim());
		
		if (folioTax == tripTax) {
			folioLogger.info("Tax value successfully validated in Folio with Trip Summary Tax");
			test_steps.add("Tax value successfully validated in Folio with Trip Summary Tax");
			flag = true;
		}else {
			folioLogger.info("Failed - validating Tax value in Folio with Trip Summary Tax is failed. Expected: "+tripTax+" But found: "+folioTax);
			test_steps.add("AssertionError - Failed - validating Tax value in Folio with Trip Summary Tax is failed. Expected: "+tripTax+" But found: "+folioTax);
		}
		return flag;
	}
	
	public boolean validateFolioFees(WebDriver driver, ArrayList<String> test_steps, TripSummary tripSummary) throws Exception {
		boolean flag = false;
		double folioFee = getFolioFees(driver, test_steps);
		double tripFee = Double.parseDouble(tripSummary.getTS_FEES().replace("$", "").trim());
		
		if (folioFee == tripFee) {
			folioLogger.info("Fee value successfully validated in Folio with Trip Summary Fee");
			test_steps.add("Fee value successfully validated in Folio with Trip Summary Fee");
			flag = true;
		}else {
			folioLogger.info("Failed - validating Fee value in Folio with Trip Summary Fee is failed. Expected: "+tripFee+" But found: "+folioFee);
			test_steps.add("AssertionError - Failed - validating Fee value in Folio with Trip Summary Fee is failed. Expected: "+tripFee+" But found: "+folioFee);
		}
		return flag;
	}
	
	public boolean validateFolioRoomCharges(WebDriver driver, ArrayList<String> test_steps, TripSummary tripSummary) throws Exception {
		boolean flag = false;
		double folioRoomCharges = getFolioRoomCharges(driver, test_steps);
		double tripRoomCharges = Double.parseDouble(tripSummary.getTS_ROOM_CHARGE().replace("$", "").trim());
		
		if (folioRoomCharges == tripRoomCharges) {
			folioLogger.info("Room Charges total successfully validated in Folio with Trip Summary Room charges");
			test_steps.add("Room Charges total successfully validated in Folio with Trip Summary Room charges");
			flag = true;
		}else {
			folioLogger.info("Failed - validating Room charges total in Folio with Trip Summary is failed. Expected: "+tripRoomCharges+" But found: "+folioRoomCharges);
			test_steps.add("AssertionError - Failed - validating Room charges total in Folio with Trip Summary is failed. Expected: "+tripRoomCharges+" But found: "+folioRoomCharges);
		}
		return flag;
	}
	
	public void validateFolioTotals(WebDriver driver, ArrayList<String> test_steps, TripSummary tripSummary) throws Exception {
		
		validateFolioCharges(driver, test_steps, tripSummary);
		validateFolioBalance(driver, test_steps, tripSummary);
		validateFolioPayments(driver, test_steps, tripSummary);
		
		validateFolioTaxes(driver, test_steps, tripSummary);
		validateFolioFees(driver, test_steps, tripSummary);
		validateFolioRoomCharges(driver, test_steps, tripSummary);
	}
	
/*	public void validateFolioLineItemRoomCharge(WebDriver driver, ArrayList<String> test_steps, StayInfo stayInfo, 
			ArrayList<FolioLineItem> folioItems) throws Exception {
		
		double roomChargeTotal = Double.parseDouble(stayInfo.getSI_ROOM_TOTAL().replace("$", "").trim());
		int numberOfNights = Integer.parseInt(stayInfo.getSI_NUMBER_OF_NIGHTS().replace("N", "").trim());
		double roomChargePerDay = roomChargeTotal/numberOfNights;
		folioLogger.info("Room Total: "+roomChargeTotal);
		folioLogger.info("Nights: "+numberOfNights);
		folioLogger.info("Room charge per day: "+roomChargePerDay);
		
		String checkInDate = stayInfo.getSI_CHECK_IN_DATE();
		String checkOutDate = stayInfo.getSI_CHECK_OUT_DATE();
		String date = null;
		for (int i = 0; i < numberOfNights; i++) {
			boolean flag = false;
			date = Utility.increaseDateAsPerGivenDate("MMM d, yyyy", checkInDate, i);
			folioLogger.info("Date: "+date);
			for (int j = 0; j < folioItems.size(); j++) {
				folioLogger.info("Foilio date: "+folioItems.get(j).getITEM_DATE());	
				if (folioItems.get(j).getITEM_DATE().equalsIgnoreCase(date)) {	
					folioLogger.info("Date found");					
					if (folioItems.get(j).getITEM_CATEGORY().equalsIgnoreCase("Room Charge")) {
						folioLogger.info("Found Room charge line item in Folio for date "+date);
						test_steps.add("Found Room charge line item in Folio for date "+date);
						double actRoomCharge = Double.parseDouble(folioItems.get(j).getITEM_AMOUNT().replace("$", "").trim());
						if (actRoomCharge == roomChargePerDay) {
							folioLogger.info("Room charge amount validated successfully for date "+date);
							test_steps.add("Room charge amount validated successfully for date "+date);
						}else {
							folioLogger.info("Failed - Room charge amount validation failed for date "+date+" Expected: "+roomChargePerDay+" But found: "+actRoomCharge);
							test_steps.add("AssertionError - Failed - Room charge amount validation failed for date "+date+" Expected: "+roomChargePerDay+" But found: "+actRoomCharge);
						}
						flag = true;
					}
				}
			}
			if (!flag) {
				folioLogger.info("Failed - Room charge line item not found in folio for date "+date);
				test_steps.add("AssertionError - Failed - Room charge line item not found in folio for date "+date);
			}
		}		
	}*/
	
	public void validateFolioLineItemRoomCharge(WebDriver driver, ArrayList<String> test_steps, ArrayList<StayInfo> stayInfo) throws Exception {
		ReservationV2 res = new ReservationV2();
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		List<WebElement> allFolios = folio.AllFolios;
		int roomsCount = stayInfo.size();
		ArrayList<FolioLineItem> folioItems = new ArrayList<FolioLineItem>();
		
		for (int k = 0; k < roomsCount; k++) {
			allFolios.get(k).click();
			Wait.wait3Second();
			folioItems = res.getAllFolioLineItems(driver);
			double roomChargeTotal = Double.parseDouble(stayInfo.get(k).getSI_ROOM_TOTAL().replace("$", "").trim());
			int numberOfNights = Integer.parseInt(stayInfo.get(k).getSI_NUMBER_OF_NIGHTS().replace("N", "").trim());
			double roomChargePerDay = roomChargeTotal/numberOfNights;
			folioLogger.info("Room Total: "+roomChargeTotal);
			folioLogger.info("Nights: "+numberOfNights);
			folioLogger.info("Room charge per day: "+roomChargePerDay);
			
			String checkInDate = stayInfo.get(k).getSI_CHECK_IN_DATE();
			String checkOutDate = stayInfo.get(k).getSI_CHECK_OUT_DATE();
			String date = null;
			for (int i = 0; i < numberOfNights; i++) {
				boolean flag = false;
				date = Utility.increaseDateAsPerGivenDate("MMM d, yyyy", checkInDate, i);
				folioLogger.info("Date: "+date);
				for (int j = 0; j < folioItems.size(); j++) {
					folioLogger.info("Foilio date: "+folioItems.get(j).getITEM_DATE());	
					if (folioItems.get(j).getITEM_DATE().equalsIgnoreCase(date)) {	
						folioLogger.info("Date found");					
						if (folioItems.get(j).getITEM_CATEGORY().equalsIgnoreCase("Room Charge") && !folioItems.get(j).getITEM_STATUS().equalsIgnoreCase("Void")) {
							folioLogger.info("Found Room charge line item in Folio for date "+date);
							test_steps.add("Found Room charge line item in Folio for date "+date);
							double actRoomCharge = Double.parseDouble(folioItems.get(j).getITEM_AMOUNT().replace("$", "").trim());
							if (actRoomCharge == roomChargePerDay) {
								folioLogger.info("Room charge amount "+folioItems.get(j).getITEM_AMOUNT()+" validated successfully for date "+date+" for room number "+stayInfo.get(k).getSI_ROOMCLASS_NAME()+" "+stayInfo.get(k).getSI_ROOM_NUMBER());
								test_steps.add("Room charge amount "+folioItems.get(j).getITEM_AMOUNT()+" validated successfully for date "+date+" for room number "+stayInfo.get(k).getSI_ROOMCLASS_NAME()+" "+stayInfo.get(k).getSI_ROOM_NUMBER());
							}else {
								folioLogger.info("Failed - Room charge amount validation failed for date "+date+" for room number "+stayInfo.get(k).getSI_ROOMCLASS_NAME()+"-"+stayInfo.get(k).getSI_ROOM_NUMBER()+" Expected: "+roomChargePerDay+" But found: "+actRoomCharge);
								test_steps.add("AssertionError - Failed - Room charge amount validation failed for date "+date+" for room number "+stayInfo.get(k).getSI_ROOMCLASS_NAME()+"-"+stayInfo.get(k).getSI_ROOM_NUMBER()+" Expected: "+roomChargePerDay+" But found: "+actRoomCharge);
							}
							flag = true;
						}
					}
				}
				if (!flag) {
					folioLogger.info("Failed - Room charge line item not found in folio for date "+date+" for room number "+stayInfo.get(k).getSI_ROOMCLASS_NAME()+"-"+stayInfo.get(k).getSI_ROOM_NUMBER());
					test_steps.add("AssertionError - Failed - Room charge line item not found in folio for date "+date+" for room number "+stayInfo.get(k).getSI_ROOMCLASS_NAME()+"-"+stayInfo.get(k).getSI_ROOM_NUMBER());
				}
			}
		}				
	}
	
	public HashMap<String, String> getGuestLedgerPaymentDetailsInFolio(WebDriver driver) {
		
		String tableHeader = "//div[contains(@data-bind,'guestLedgers')][1]/table//th";
		String tableValues = "//div[contains(@data-bind,'guestLedgers')][2]/table//td";
		List<WebElement> values = driver.findElements(By.xpath(tableValues));
		
		HashMap<String, String> paymentGuestLedger = new HashMap<>();
		
		paymentGuestLedger.put("Status", values.get(0).getText());
		paymentGuestLedger.put("Date", values.get(1).getText());
		paymentGuestLedger.put("Category", values.get(2).getText());
		paymentGuestLedger.put("Description", values.get(3).findElement(By.tagName("span")).getText());
		paymentGuestLedger.put("Notes", values.get(4).findElement(By.xpath("//div[@data-bind='text: note']")).getText());
		paymentGuestLedger.put("Transaction Amount", values.get(5).getText());
		
		return paymentGuestLedger;
	}
	
	
	public void clickFolioChargesTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.ChargesTab), driver);
		folio.ChargesTab.click();
		folioLogger.info("Clicked on Folio Charges tab");
		test_steps.add("Clicked on Folio Charges tab");
	}
	
	public void clickFolioPaymentsTab(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.PaymentsTab), driver);
		for (int i = 0; i < 10; i++) {
			try {
				folio.PaymentsTab.click();
				break;
			}catch (Exception e) {
				folioLogger.info("Iteration "+i+"  "+e.toString());
				Wait.wait1Second();
			}
		}
		folioLogger.info("Clicked on Folio Payments Tab");
		test_steps.add("Clicked on Folio Payments Tab");
	}
	
	public void clickFolioAuthorizationsTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.AuthorizationTab), driver);
		folio.AuthorizationTab.click();
		folioLogger.info("Clicked on Folio Authorizations tab");
		test_steps.add("Clicked on Folio Authorizations tab");
	}
	
	public void clickFolioBalanceTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.BalanceTab), driver);
		folio.BalanceTab.click();
		folioLogger.info("Clicked on Folio Balance tab");
		test_steps.add("Clicked on Folio Balance tab");
	}
	
	public void verifyCheckOutPaymentFolioLineItem(WebDriver driver, ArrayList<String> test_steps, String paymentMethod, CheckOutSuccessPaymentPopup paymentPopup) throws InterruptedException {		
		ReservationV2 res = new ReservationV2();
		//clickFolioPaymentsTab(driver, test_steps);
		ArrayList<FolioLineItem> folioItems = res.getAllFolioLineItems(driver);
		
		for (int i = 0; i < folioItems.size(); i++) {
			if (folioItems.get(i).getITEM_CATEGORY().equalsIgnoreCase(paymentMethod)) {
				double folioAmount = Double.parseDouble(folioItems.get(i).getITEM_AMOUNT().replace("$", "").replace("£", "").trim());
				double checkOutAmount = Double.parseDouble(paymentPopup.getCHECK_OUT_PAYPMENT_AMOUNT().replace("$", "").replace("£", "").trim());
				String chaketOutdate = Utility.parseDate(paymentPopup.getCHECK_OUT_PAYMENT_DATE(), "MM/dd/yyyy", "MMM dd, yyyy");
				if (folioAmount == checkOutAmount && chaketOutdate.equalsIgnoreCase(folioItems.get(i).getITEM_DATE())) {
					folioLogger.info("Verified check-out payment line item in Folio");
					test_steps.add("Verified check-out payment line item in Folio");
				}else {
					folioLogger.info("Failed to validate check-out payment line item in Folio");
					test_steps.add("AssertionError Failed to validate check-out payment line item in Folio");
				}
			}
		}		
	}
	
	public void clickOnGivenFolioLineItemDescription(WebDriver driver, ArrayList<String> test_steps, String category) {
		ReservationV2 res = new ReservationV2();
		
		String folioTable = "(//table[contains(@class,'table-lineItems')])[1]/tbody/tr";
		Wait.WaitForElement(driver, folioTable);
		List<WebElement> folioRows = driver.findElements(By.xpath(folioTable));
		
		for (int i = 0; i < folioRows.size(); i++) {
			List<WebElement> foliocols = folioRows.get(i).findElements(By.tagName("td"));
			if (foliocols.get(4).findElement(By.tagName("span")).getText().equalsIgnoreCase(category)) {
				foliocols.get(5).findElement(By.tagName("a")).click();
				Wait.WaitForElement(driver, OR_FolioNew.folioPaymentDetailsWindow);
				break;
			}
			
//			for (int j = 0; j < foliocols.size(); j++) {
//				if (foliocols.get(j).findElement(By.tagName("span")).getText().equalsIgnoreCase(category)) {
//					foliocols.get(j+1).findElement(By.tagName("a")).click();
//					Wait.WaitForElement(driver, OR_FolioNew.folioPaymentDetailsWindow);
//					break;
//				}
//			}		
		}
	}
	
	public void closePaymentDetailsWindow(WebDriver driver, ArrayList<String> test_steps) {
		String closeButton = "//button[text()='Close'][contains(@data-bind,'handleCloseClick')]";
		WebElement close = driver.findElement(By.xpath(closeButton));
		Utility.clickThroughJavaScript(driver, close);
	}
	
	public void verifyCheckOutPaymentDetailsInFolio(WebDriver driver, ArrayList<String> test_steps, String category, 
			CheckOutSuccessPaymentPopup paymentPopup, String notesCheckOut) throws Exception {
		
		//clickFolioPaymentsTab(driver, test_steps);
		//clickOnGivenFolioLineItemDescription(driver, test_steps, category);
		HashMap<String, String> paymentDetails = getGuestLedgerPaymentDetailsInFolio(driver);
		folioLogger.info("Amont: "+paymentPopup.getCHECK_OUT_PAYPMENT_AMOUNT());
		double checkOutAmount = Double.parseDouble(paymentPopup.getCHECK_OUT_PAYPMENT_AMOUNT().replace("$", "").replace("£", "").trim());
		double folioAmount = Double.parseDouble(paymentDetails.get("Transaction Amount").replace("$", "").replace("£", "").trim());
		if (checkOutAmount == folioAmount) {
			folioLogger.info("Verified Amount in Folio Payment details with Check-out payment popup");
			test_steps.add("Verified Amount in Folio Payment details with Check-out payment popup");
		}else {
			folioLogger.info("Failed - Verification of Amount in Folio Payment details with Check-out payment popup. Expected "+checkOutAmount+" But found: "+folioAmount);
			test_steps.add("AssertionError Failed - Verification of Amount in Folio Payment details with Check-out payment popup. Expected "+checkOutAmount+" But found: "+folioAmount);
		}
		
		if (paymentPopup.getCHECK_OUT_PAYMENT_DATE().equalsIgnoreCase(paymentDetails.get("Date"))) {
			folioLogger.info("Verified Check-out payment Date in Folio Payment details with Check-out payment popup");
			test_steps.add("Verified Check-out payment Date in Folio Payment details with Check-out payment popup");
		}else {
			folioLogger.info("Failed - Verification of Check-out payment Date in Folio Payment details with Check-out payment popup");
			test_steps.add("AssertionError Failed - Verification of Check-out payment Date in Folio Payment details with Check-out payment popup");
		}
		
		if (Utility.validateString(notesCheckOut)) {
			if (notesCheckOut.equalsIgnoreCase(paymentDetails.get("Notes"))) {
				folioLogger.info("Verified Check-out Notes in Folio Payment details with Check-out payment popup");
				test_steps.add("Verified Check-out Notes in Folio Payment details with Check-out payment popup");
			}else {
				folioLogger.info("Failed Verification of Check-out Notes in Folio Payment details with Check-out payment popup");
				test_steps.add("AssertionError Failed Verification of Check-out Notes in Folio Payment details with Check-out payment popup");
			}
		}
	}
	
	
	public void postedLineItem(WebDriver driver, ArrayList<String> test_steps, String item) throws InterruptedException {
		
		String statusChangeButton = "(//span[text()='"+item+"']/../..//button[contains(@data-bind,'handleStatus')])[1]";
		String status = "(//span[text()='"+item+"']/../..//span[contains(@data-bind,'itemStatus')])[1][text()='Posted']";		
		try {
			Wait.waitForElementToBeClickable(By.xpath(statusChangeButton), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(statusChangeButton)), driver);
			driver.findElement(By.xpath(statusChangeButton)).click();
			Wait.WaitForElement(driver, status);
			Utility.verifyTrue(driver.findElement(By.xpath(status)), "Verify Post", test_steps);
			folioLogger.info("Successfully changed the status to Posted");
			test_steps.add("Successfully changed the status to Posted");
		}catch (Exception e) {
			folioLogger.info("Status not changed to Posted");
			test_steps.add("AssertioError Failed to change the status to Posted");
		}
	}
	
	public void voidLineItem(WebDriver driver, ArrayList<String> test_steps, String item, String voidReason) throws Exception {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		String checkbox = "//span[text()='"+item+"']/../..//span[contains(@class,'check-box')]";
		Wait.waitForElementToBeClickable(By.xpath(checkbox), driver);
		for (int i = 0; i < 10; i++) {
			try {
				driver.findElement(By.xpath(checkbox)).click();
				break;
			}catch (Exception e) {
				Wait.wait1Second();
			}
		}
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.bulkActionDropDown), driver);
		folio.bulkActionDropDown.click();
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.voidLineItem), driver);
		folio.voidLineItem.click();
		Wait.waitForElementToBeVisibile(By.xpath(OR_FolioNew.voidReason), driver);
		folio.voidReason.sendKeys(voidReason);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.voidProceedButton), driver);
		folio.voidProceedButton.click();
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_FolioNew.AddCharge), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.AddCharge), driver);
	}
	
	public void displayVoidItems(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_FolioNew folio = new Elements_FolioNew(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.folioSettingsIcon), driver);
		folio.folioSettingsIcon.click();
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.displayVoidItemsCheck), driver);
		//folio.displayVoidItemsCheck.click();
		Utility.clickThroughJavaScript(driver, folio.displayVoidItemsCheck);
		Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.folioSettingsSave), driver);
		folio.folioSettingsSave.click();
		Wait.wait5Second();
	}
	
	
	public ArrayList<HashMap<String, String>> getAdvancedPaymentDetails(WebDriver driver, ArrayList<String> test_steps, String category) {
		ArrayList<HashMap<String, String>> paymentDetails = new ArrayList<>();
		clickOnGivenFolioLineItemDescription(driver, test_steps, category);
		paymentDetails = getPaymentDetailsGuestLedgerInFolio(driver);
		closePaymentDetailsWindow(driver, test_steps);
		return paymentDetails;
	}
	
	ArrayList<HashMap<String, String>> getPaymentDetailsGuestLedgerInFolio(WebDriver driver) {
		
		String tableHeader = "//div[contains(@data-bind,'guestLedgers')][1]/table//th";
		String tableRows = "//div[contains(@data-bind,'guestLedgers')][2]/table//tr";
		
		ArrayList<HashMap<String, String>> paymentGuestLedger = new ArrayList<>();
				
		List<WebElement> rows = driver.findElements(By.xpath(tableRows));
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			HashMap<String, String> guestLedger = new HashMap<>();
			guestLedger.put("Status", cells.get(0).getText());
			guestLedger.put("Date", cells.get(1).getText());
			guestLedger.put("Category", cells.get(2).getText());
			guestLedger.put("Description", cells.get(3).findElement(By.tagName("span")).getText());
			guestLedger.put("Notes", cells.get(4).findElement(By.xpath("//div[@data-bind='text: note']")).getText());
			guestLedger.put("Transaction Amount", cells.get(5).getText());
			
			paymentGuestLedger.add(guestLedger);
		}		
		return paymentGuestLedger;
	}
	
	
	public HashMap<String, String> getFolioBalances(WebDriver driver, HashMap<String, Boolean> reservationTypeMap)
			throws InterruptedException {
		Elements_Reports elements = new Elements_Reports(driver);
		HashMap<String, String> getFolioBalances = new HashMap<>();
		for (Map.Entry<String, Boolean> entry : reservationTypeMap.entrySet()) {
			Utility.printString("Key = " + entry.getKey() + "   Value = " + entry.getValue());
			String reservationType = entry.getKey();
			if (entry.getValue()) {
				String path = "//span[text()='Receivable Balances']/../../parent::thead//following-sibling::tbody//td[@title='"
						+ reservationType + "']//following-sibling::td";
				List<WebElement> element = driver.findElements(By.xpath(path));
				if (element.size() > 0) {
					Utility.ScrollToElement(element.get(0), driver);
					String getBalance = element.get(0).getText().trim();
					// Utility.printString(getBalance);
					getBalance = Utility.removeDollarBracketsAndSpaces(getBalance);
					getFolioBalances.put(reservationType, getBalance);
				} else {
					getFolioBalances.put(reservationType, "0.00");
				}
			}
		}
		return getFolioBalances;
	}
		
		public void clickOnPendingStatus(WebDriver driver, String resStatus, String resType) throws InterruptedException {
			Elements_Reports element = new Elements_Reports(driver);
			try {
				element.Folio.click();
				Wait.wait5Second();
				element.FolioStatusPending.click();
				if (resType.equalsIgnoreCase("MRB")) {
					String SecondRoom = "(//a[contains(text(),'Guest Folio For')])[2]";
					driver.findElement(By.xpath(SecondRoom)).click();
					Wait.wait5Second();
					String SecondFolioPending = "//button[contains(@data-bind,'handleStatusPendingToPost')]";
					Wait.wait5Second();
					driver.findElement(By.xpath(SecondFolioPending)).click();
					Wait.wait5Second();
				}
				Wait.wait5Second();
			} catch (Exception e) {
				if (resStatus.equalsIgnoreCase("Cancelled")) {
					resStatus = "MC";
				} else {
					resStatus = "No Show Fee";
				}
				try {

					Wait.wait5Second();
					String status = "//span[text()='" + resStatus
							+ "']//..//..//button[contains(@data-bind,'handleStatusPendingToPost')]";
					WebElement statusElement = driver.findElement(By.xpath(status));
					Wait.wait2Second();
					Wait.waitForElementToBeClickable(By.xpath(status), driver);
					statusElement.click();
				} catch (Exception ex) {

				}
			}

		}
		
		public void verifyReservation(WebDriver driver, String guestFullName, String guestSecondName, String ReservationNo,
				List<String> roomClass, List<String> RoomNo, String inHouseDate, String DepartureDate,
				List<String> FolioBalance, ArrayList<String> testSteps, String reservationType)
				throws InterruptedException, ParseException {

			inHouseDate = ESTTimeZone.reformatDate(inHouseDate, "dd/MM/yyyy", "MMM dd, yyyy");
			DepartureDate = ESTTimeZone.reformatDate(DepartureDate, "dd/MM/yyyy", "MMM dd, yyyy");
			folioLogger.info(inHouseDate);
			folioLogger.info(DepartureDate);
			if (guestFullName != "") {
				String detailGuestName = "//a[text()='" + ReservationNo
						+ "']//..//..//td[@class='ant-table-cell TableView_greyTitle_3SYL8']//a[text()='" + guestFullName
						+ "']";
				WebElement detailGuestNameelement = driver.findElement(By.xpath(detailGuestName));
				Assert.assertTrue(detailGuestNameelement.isDisplayed(), "Guest Name Not matched");
				testSteps.add("Verified detail view contain guest name :" + guestFullName);

			}
			if (guestSecondName != "") {
				String detailGuestName = "//a[text()='" + ReservationNo + "']//..//..//td//a[text()='" + guestSecondName
						+ "']";
				WebElement detailGuestNameelement = driver.findElement(By.xpath(detailGuestName));
				Assert.assertTrue(detailGuestNameelement.isDisplayed(), "Guest Name Not matched");
				testSteps.add("Verified detail view contain guest second name :" + guestFullName);

			}

			String detailReservationNo = "//a[text()='" + ReservationNo + "']";
			List<WebElement> detailReservationNoelement = driver.findElements(By.xpath(detailReservationNo));
			for (int a = 0; a < detailReservationNoelement.size(); a++) {
				Assert.assertTrue(Utility.isElementDisplayed(driver, By.xpath(detailReservationNo)),
						"Failed : Reservation with number '" + ReservationNo + "' is not displaying");
				testSteps.add("Verified detail view contain reservation no :" + ReservationNo);

			}

			for (int a = 0; a < roomClass.size(); a++) {
				String room = roomClass.get(a).toString() + ": " + RoomNo.get(a).toString();
				folioLogger.info(room);
				String detailRoomNo = "//a[text()='" + ReservationNo + "']//..//..//td[text()='" + room + "']";
				WebElement detailRoomNoelement = driver.findElement(By.xpath(detailRoomNo));
				Assert.assertTrue(detailRoomNoelement.isDisplayed(), "RoomNo not contain");
				testSteps.add("Verified detail view contain first room :" + room);

			}
			String detailInHouse = "//a[text()='" + ReservationNo + "']//..//..//td[4]";
			List<WebElement> detailInHouseelement = driver.findElements(By.xpath(detailInHouse));
			for (int a = 0; a < detailInHouseelement.size(); a++) {
				assertEquals(detailInHouseelement.get(a).getText(), inHouseDate, "Failed to verify in house date");
				testSteps.add("Verified detail view contain in-House date :" + inHouseDate);

			}
			String detailDeparture = "//a[text()='" + ReservationNo + "']//..//..//td[5]";
			List<WebElement> detailDepartureelement = driver.findElements(By.xpath(detailDeparture));
			for (int a = 0; a < detailDepartureelement.size(); a++) {
				assertEquals(detailDepartureelement.get(a).getText(), DepartureDate, "Failed to verify departure date");
				testSteps.add("Verified detail view contain departure date :" + DepartureDate);

			}

			for (int b = 0; b < FolioBalance.size(); b++) {
				String detailFolioBalance = "//a[text()='" + ReservationNo + "']//..//..//td[contains(text(), '"
						+ FolioBalance.get(b).trim() + "')][last()]";
				Assert.assertTrue(Utility.isElementDisplayed(driver, By.xpath(detailFolioBalance)),
						"Failed to verify folio balance");
				testSteps.add("Verified detail view contain folio balance :" + FolioBalance.get(0));
			}

		}
		public void clickSaveChanges_RV2(WebDriver driver, ArrayList<String> test_steps) {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.SaveChangesButton_RV2), driver);
			folio.SaveChangesButton_RV2.click();
			folioLogger.info("Clicked on Save Changes button");
			test_steps.add("Clicked on Save Changes button");
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 60);
			//Wait.explicit_wait_absenceofelement(OR_FolioNew.tosterMsg, driver);
		}
		
		public void addFolioLineItem_RV2(WebDriver driver, ArrayList<String> test_steps, String category, String amount) throws InterruptedException {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			try {
				clickAddCharge(driver, test_steps);
				selectCategory(driver, test_steps, category);
				enterAmountFolioLineItem(driver, test_steps, amount);
				clickSaveChanges_RV2(driver, test_steps);
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
				test_steps.add("Successfully added Folio line item");
				folioLogger.info("Successfully added Folio line item");
			}catch (Exception e) {
				folioLogger.info(e.toString());
			}

			
		}

		
		public double getFolioRoomCharges(WebDriver driver, ArrayList<String> test_steps, String category) throws Exception {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			ReservationV2 res = new ReservationV2();
			Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
			String roomCharge = null;
			double roomChargeValue = 0.0;		
			List<WebElement> allFolios = folio.AllFolios;
			for (int i = 0; i < allFolios.size(); i++) {
				allFolios.get(i).click();
				Wait.wait2Second();			
				ArrayList<FolioLineItem> folioLine = res.getAllFolioLineItems(driver);
				for (int j = 0; j < folioLine.size(); j++) {
					//Catgory should not be empty , it will give total amount as per category;
					if (folioLine.get(j).getITEM_CATEGORY().contains(category)) {
						roomCharge = folioLine.get(j).getITEM_AMOUNT().replace("$", "").trim();
						roomChargeValue = roomChargeValue + Double.parseDouble(roomCharge);
					}	
					test_steps.add("Folio Room Charges: "+roomChargeValue);
					folioLogger.info("Folio Room Charges: "+roomChargeValue);	
				}
			}	
			
			return roomChargeValue;
		}
		
		public void folioClickSave(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			Wait.WaitForElement(driver, OR_FolioNew.folioSaveButton);
			Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.folioSaveButton), driver);
			Wait.wait2Second();
			folio.folioSaveButton.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 60);
			folioLogger.info("Clicked on Save  button");
			test_steps.add("Clicked on Save  button");			
			
			//Wait.explicit_wait_absenceofelement(OR_FolioNew.tosterMsg, driver);
		}
		
		public Multimap<String, String> getFolioRoomChargesDateWise(WebDriver driver, ArrayList<String> test_steps, String category) throws Exception {
			Multimap<String, String> charge= ArrayListMultimap.create();
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			ReservationV2 res = new ReservationV2();
			Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
			String roomCharge = null;
			double roomChargeValue = 0.0;		
			List<WebElement> allFolios = folio.AllFolios;
			for (int i = 0; i < allFolios.size(); i++) {
				allFolios.get(i).click();
				Wait.wait2Second();			
				ArrayList<FolioLineItem> folioLine = res.getAllFolioLineItems(driver);
				for (int j = 0; j < folioLine.size(); j++) {
					if (folioLine.get(j).getITEM_CATEGORY().contains(category)) {
						roomCharge = folioLine.get(j).getITEM_AMOUNT().replace("$", "").trim();
							charge.put(folioLine.get(j).getITEM_DATE(), String.valueOf(roomCharge));
					}		
				}
			}	
			test_steps.add("Folio Room Charges: "+charge);
			folioLogger.info("Folio Room Charges: "+charge);	
			return charge;
		}
		
		
		public HashMap<String, String> getFolioChargesDateWiseAndRoomsWise(WebDriver driver, ArrayList<String> test_steps, String category) throws Exception {
			HashMap<String, String> charge= new HashMap<String, String>();
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			ReservationV2 res = new ReservationV2();
			Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
			String roomCharge = null;
			List<WebElement> allFolios = folio.AllFolios;
			for (int i = 0; i < allFolios.size(); i++) {
				allFolios.get(i).click();
				Wait.wait2Second();	
				ArrayList<FolioLineItem> folioLine = res.getAllFolioLineItems(driver);
				for (int j = 0; j < folioLine.size(); j++) {
					//Catgory should not be empty , it will give total amount as per category;
					if(Utility.validateString(category)) {
					if (folioLine.get(j).getITEM_CATEGORY().contains(category)) {
						roomCharge = folioLine.get(j).getITEM_AMOUNT().replace("$", "").trim();
						String[] abb= allFolios.get(i).getText().split("For");
						charge.put(abb[1] +"="+folioLine.get(j).getITEM_DATE(), String.valueOf(roomCharge));
					}
					test_steps.add("Folio Room Charges: "+charge);
					folioLogger.info("Folio Room Charges: "+charge);	
					}else {
						//give all amount of allcategory						
						roomCharge = folioLine.get(j).getITEM_AMOUNT().replace("$", "").trim();
						String[] abb= allFolios.get(i).getText().split("For");
						String cat= folioLine.get(j).getITEM_CATEGORY();
						charge.put(abb[1] +"="+folioLine.get(j).getITEM_DATE() +"-" +cat, String.valueOf(roomCharge));
					}
				}
			}	
			
			return charge;
		}
		//Added By Riddhi - Get Room Class Number and Room CLass Name for the selected Folio
		public void verifyRoomClassNameNoForSelectedFolio(WebDriver driver, ArrayList<String> test_steps, 
				String expectedRoomClassAbb, String expectedRoomClassNo ) throws InterruptedException 
		{
			try
			{
				String roomXPath = "(//a[contains(text(),'Guest Folio For "+expectedRoomClassAbb+" : "+expectedRoomClassNo+"')])";
				WebElement element= driver.findElement(By.xpath(roomXPath));
				Utility.verifyTrue(element, "Successfully Verify folio tab - Room Class Name & Number", test_steps);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void voidLineItemByIndex(WebDriver driver, ArrayList<String> test_steps, String item, String voidReason, int index) throws Exception {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			Elements_ReservationV2 res = new Elements_ReservationV2(driver);
			String checkbox = "(//span[text()='"+item+"']/../..//span[contains(@class,'check-box')])["+index+"]";
			//String checkbox = "(//span[text()='"+item+"']/../..//input[contains(@type,'checkbox')])["+index+"]";
			Wait.waitForElementToBeClickable(By.xpath(checkbox), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(checkbox)), driver);
			driver.findElement(By.xpath(checkbox)).click();
			try {
			Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.bulkActionDropDown), driver);
			folio.bulkActionDropDown.click();
			}catch(Exception e) {
			 String path="//span[contains(text(),' Bulk Action')]/..";
			 Wait.waitForElementToBeClickable(By.xpath(path), driver);
				driver.findElement(By.xpath(path)).click();
			}
			Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.voidLineItem), driver);
			folio.voidLineItem.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_FolioNew.voidReason), driver);
			folio.voidReason.sendKeys(voidReason);
			Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.voidProceedButton), driver);
			folio.voidProceedButton.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
			Wait.waitForElementToBeVisibile(By.xpath("//button/span[text()=' Add ']"), driver);
			Wait.waitForElementToBeClickable(By.xpath("//button/span[text()=' Add ']"), driver);
		}
		
		
		
		//Creatd by Gangotri
		public void moveFolioByIndex(WebDriver driver, ArrayList<String> test_steps, String item, int index, boolean otherReservation, String reservationNo, String folioName) throws InterruptedException {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			Elements_ReservationV2 res = new Elements_ReservationV2(driver);
			String checkbox = 
					"(//span[text()='"+item+"']/../..//span[contains(@class,'check-box')])["+index+"]"

;
			Wait.waitForElementToBeClickable(By.xpath(checkbox), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(checkbox)), driver);
			driver.findElement(By.xpath(checkbox)).click();
			try {
				Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.bulkActionDropDown), driver);
				folio.bulkActionDropDown.click();
				}catch(Exception e) {
				 String path="//span[contains(text(),' Bulk Action')]/..";
				 Wait.waitForElementToBeClickable(By.xpath(path), driver);
					driver.findElement(By.xpath(path)).click();
					folioLogger.info("Click on Bulk Action");
				}
			Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.moveLineItem), driver);
			folio.moveLineItem.click();
			folioLogger.info("Click on Move");
			//String folioNamePath="//a//span[text()='"+folioName+"']";
			String folioNamePath="//li//a//span[contains(text(),'"+folioName+"')]";
			if(otherReservation){
				Utility.ScrollToElement(folio.moveOtherReservation, driver);
				folio.moveOtherReservation.click();
				test_steps.add("Click on Folio on other reservation option");
				folioLogger.info("Click on Folio on other reservation option");
				folio.moveOtherReservationTextBox.click();
				folio.moveOtherReservationTextBox.sendKeys(reservationNo);
				String path="//h4[text()='Move Folio Line Items']/../following-sibling::div//b[text()='"+reservationNo+"']";
				Utility.clickThroughAction(driver, driver.findElement(By.xpath(path)));
				test_steps.add("Entered Reservation NO: "+reservationNo );
				folioLogger.info("Entered Reservation NO: "+reservationNo);
				Wait.waitForElementToBeVisibile(By.xpath(OR_FolioNew.moveOtherReservationTragetFolio), driver);
				folio.moveOtherReservationTragetFolio.click();
				test_steps.add("Click on Target Folio Drop Down Box");
				folioLogger.info("Click on Target Folio Drop Down Box");				
				driver.findElement(By.xpath(folioNamePath)).click();
				test_steps.add("Select Folio : " + folioName);
				folioLogger.info("Select Folio : " + folioName);
				
			}else {
				try {
				folio.moveOtherReservationTragetFolio.click();
				}catch(Exception e) {
					Utility.scrollAndClick(driver, By.xpath(OR_FolioNew.moveOtherReservationTragetFolio));
				}
				test_steps.add("Click on Target Folio Drop Down Box");
				folioLogger.info("Click on Target"
						+ " Folio Drop Down Box");
				driver.findElement(By.xpath(folioNamePath)).click();
				test_steps.add("Select Folio : " + folioName);
				folioLogger.info("Select Folio : " + folioName);
			}
			Utility.ScrollToElement(folio.movelineitemButton, driver);
			folio.movelineitemButton.click();
			test_steps.add("Click on Move Items Button");
			folioLogger.info("Click on Move Items Button");
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
			Utility.verifyTrue(res.TOASTER_MESSAGE, "Verify Toaster", test_steps);
			test_steps.add("Verify Toaster");
			folioLogger.info("Verify Toaster");
		}
		
		public void addFolioLineItemWithDate(WebDriver driver, ArrayList<String> test_steps, String date,String category, String amount) throws InterruptedException {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			try {
				clickAddCharge(driver, test_steps);
				String path="//td[@class='lineitem-date']//div[contains(@class,'dateField')]";
				Wait.WaitForElement(driver, path);
				Utility.scrollAndClick(driver, By.xpath(path));
				Utility.selectStartDate(driver, test_steps, date);
				selectCategory(driver, test_steps, category);
				enterAmountFolioLineItem(driver, test_steps, amount);
				folioClickSave(driver, test_steps);
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
				test_steps.add("Successfully added Folio line item");
				folioLogger.info("Successfully added Folio line item");
			}catch (Exception e) {
				folioLogger.info(e.toString());
			}
		}
		
		//Added By Riddhi - Perform Refund using cash/check/custom payment method
				public void refundPaymentinGuestFolio(WebDriver driver, ArrayList<String> test_steps, 
						String amount, String paymentMethod, String cardNumber, String nameOnCard, String expiryDate, String CVVcode) throws Exception {
					Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
					String Option ="";
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.refundPopupHeader), driver);
					Wait.wait5Second();
					elementsV2.refundPopupAmount.clear();
					elementsV2.refundPopupAmount.sendKeys(amount);
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.REFUND_PAYMENT_METHOD), driver);
					elementsV2.REFUND_PAYMENT_METHOD.click();
					if(paymentMethod.contains("Account"))
					{						
						String payByAccount="Account - "+cardNumber;
						Option = "(//span[contains(text(),'"+payByAccount+"')])[last()]";
						test_steps.add("Account Name & Number : "+payByAccount);
						folioLogger.info("Account Name & Number : "+payByAccount);
						
					}
					else
					{
						Option = "(//span[contains(text(),'" + paymentMethod + "')])[last()]";
					}
					
					Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
					driver.findElement(By.xpath(Option)).click();
					if(paymentMethod.contains("MC")||paymentMethod.contains("Visa")||paymentMethod.contains("Discover"))
					{
						enterCardNumberinPayRefundpopup(driver,test_steps,cardNumber);
						enterCardHolderNameinPayRefundPopup(driver, test_steps, nameOnCard);
						enterExpiryDateinPayRefundpopup(driver, test_steps, expiryDate);
						elementsV2.EXPIRY_DATE.sendKeys(Keys.TAB);

						enterCVVCodeinPayRefundpopup(driver, test_steps, CVVcode);
					}
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.refundPopupRefundButton), driver);	
					elementsV2.refundPopupRefundButton.click();
					folioLogger.info("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
					test_steps.add("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
					
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
					String toastMessage = elementsV2.TOASTER_MESSAGE.getText();
					if (toastMessage.contains("Refund payment of amount ") && toastMessage.contains("for Guest Folio folio")) {
						folioLogger.info("Refund is successful");
						test_steps.add("Refund is successful");			
					} else {
						folioLogger.info("Refund is not successful");
						test_steps.add("Refund is not successful");						
					}
				}
				
				//Added By Riddhi - Click on Refund Button From Folio Tab
				public void clickRefundButtonFromFolioTab(WebDriver driver, ArrayList<String> test_steps) throws Exception
				{
					Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.FOLIO_REFUND_BTN), driver);
					elementsV2.FOLIO_REFUND_BTN.click();
					folioLogger.info("Clicked on Folio Refund button");
					test_steps.add("Clicked on Folio Refund button");
				}
				//Added By Riddhi - Refund using 'Reservation As Payment' Method
				public void refundPaymentusingReservationMethod(WebDriver driver, ArrayList<String> test_steps, 
						String amount, String paymentMethod, String reservationNumber) throws Exception {
					Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.refundPopupHeader), driver);
					Wait.wait5Second();
					elementsV2.refundPopupAmount.clear();
					elementsV2.refundPopupAmount.sendKeys(amount);
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.REFUND_PAYMENT_METHOD), driver);
					elementsV2.REFUND_PAYMENT_METHOD.click();
					String Option = "(//span[contains(text(),'" + paymentMethod + "')])[last()]";
					Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
					driver.findElement(By.xpath(Option)).click();
					if(paymentMethod.contains("Reservation"))
					{
						Wait.waitForElementByXpath(driver, OR_ReservationV2.ENTER_RES_NO_IN_PAY_REFUND_POPUP);
						elementsV2.ENTER_RES_NO_IN_PAY_REFUND_POPUP.sendKeys(reservationNumber);
						test_steps.add("Entered reservation number : " + reservationNumber);
						Wait.wait2Second();
						String reservationToSelect = "//b[text()='"+ reservationNumber +"']/../../..//parent::li";
						WebElement ele = driver.findElement(By.xpath(reservationToSelect));
						ele.click();
						test_steps.add("Clicked reservation with number : " + reservationNumber + " in searched results");
					}
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.refundPopupRefundButton), driver);	
					elementsV2.refundPopupRefundButton.click();
					folioLogger.info("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
					test_steps.add("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
					
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
					String toastMessage = elementsV2.TOASTER_MESSAGE.getText();
					if (toastMessage.contains("Refund payment of amount ") && toastMessage.contains("for Guest Folio ")) {
						folioLogger.info("Refund is successful using "+paymentMethod+" As Payment Method");
						test_steps.add("Refund is successful using "+paymentMethod+" As Payment Method");
					} else {
						folioLogger.info("Refund is not successful using "+paymentMethod+" As Payment Method");
						test_steps.add("Refund is not successful using "+paymentMethod+" As Payment Method");
					}
				}
				//Added By Riddhi - Refund Using "Log As External" Method
				public void refundusingLogAsExternal(WebDriver driver, ArrayList<String> test_steps, 
						String refundAmount, String paymentMethod, String CCType, String cardNumber, 
						String nameOnCard, String expiryDate, String CVVcode) throws Exception 
				{
					Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.refundPopupHeader), driver);
					Wait.wait5Second();
					elementsV2.refundPopupAmount.clear();
					elementsV2.refundPopupAmount.sendKeys(refundAmount);
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.REFUND_PAYMENT_METHOD), driver);
					elementsV2.REFUND_PAYMENT_METHOD.click();
					String Option = "(//span[contains(text(),'" + CCType + "')])[last()]";
					Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
					driver.findElement(By.xpath(Option)).click();
					
					enterCardNumberinPayRefundpopup(driver, test_steps, cardNumber);
					enterCardHolderNameinPayRefundPopup(driver, test_steps, nameOnCard);
					enterExpiryDateinPayRefundpopup(driver, test_steps, expiryDate);
					enterCVVCodeinPayRefundpopup(driver, test_steps, CVVcode);
					
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.REFUND_LOG_AS_EXTERNAL_CHK), driver);
					elementsV2.REFUND_LOG_AS_EXTERNAL_CHK.click();
					String refundDate="//input[contains(@data-bind,'isRefundDateEnable')]";
					Wait.WaitForElement(driver, refundDate);
					if( driver.findElement(By.xpath(refundDate)).isEnabled() )
					{
						assertTrue( driver.findElement(By.xpath(refundDate)).isDisplayed(), 
								"Refund Date field is enabled, when Log As External is checked");
						folioLogger.info("Refund Date Field is Editable");
						test_steps.add("Refund Date Field is Editable");
					}
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.refundPopupRefundButton), driver);
					String actualPayButtonText = elementsV2.refundPopupRefundButton.getText();
					String expectedPayButtonText = "Log $"+Utility.convertDecimalFormat(refundAmount);
					Utility.customAssert(actualPayButtonText, expectedPayButtonText, true, "Refund Button Text Changed to Log", "Refund Button Text doesn't change", true, test_steps);
					
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.REFUND_NOTE_4_EXTERNAL_PAYMENT), driver);
					assertTrue(driver.findElement(By.xpath(OR_ReservationV2.REFUND_NOTE_4_EXTERNAL_PAYMENT)).isDisplayed(),"Log As External Notes Displayed");
					elementsV2.refundPopupRefundButton.click();
					folioLogger.info("Provided Refund Amount as : <b>"+refundAmount+"</b> and Clicked on Refund button");
					test_steps.add("Provided Refund Amount as : <b>"+refundAmount+"</b> and Clicked on Refund button");
					
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
					String toastMessage = elementsV2.TOASTER_MESSAGE.getText();
					if (toastMessage.contains("Refund payment of amount ") && toastMessage.contains("for Guest Folio ")) {
						folioLogger.info("Refund is successful using "+paymentMethod+" As Payment Method");
						test_steps.add("Refund is successful using "+paymentMethod+" As Payment Method");
					} else {
						folioLogger.info("Refund is not successful using "+paymentMethod+" As Payment Method");
						test_steps.add("Refund is not successful using "+paymentMethod+" As Payment Method");
					}
				}		
				//Added By Riddhi - Create Custom Folio Method
				public void createCustomFolio(WebDriver driver, ArrayList<String> test_steps, String customFolioName) throws Exception
				{
					folioLogger.info("Creating Custom Folio......");
					test_steps.add("Creating Custom Folio.........");
					Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.ADD_CUSTOM_FOLIO_BTN), driver);
					elementsV2.ADD_CUSTOM_FOLIO_BTN.click();			
					folioLogger.info("Enter Custom Folio Name");
					test_steps.add("Enter Custom Folio Name");
					Wait.waitForElementToBeClickable(By.id(OR_ReservationV2.ENTER_CUSTOM_FOLIO_NAME), driver);
					elementsV2.ENTER_CUSTOM_FOLIO_NAME.click();
					elementsV2.ENTER_CUSTOM_FOLIO_NAME.sendKeys(customFolioName);
					folioLogger.info("Clicking On Custom Folio Save Button");
					test_steps.add("Clicking On Custom Folio Save Button");
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.SAVE_CUSTOM_FOLIO_BTN), driver);
					elementsV2.SAVE_CUSTOM_FOLIO_BTN.click();
					folioLogger.info("Custom Folio Created.....");
					test_steps.add("Custom Folio Created.....");
					Wait.wait10Second();
				}
				
				//Added By Riddhi - Verify Refund Payment from Custom Folio
				public void refundPaymentinCustomFolio(WebDriver driver, ArrayList<String> test_steps, String customFolioName,
						String amount, String paymentMethod, String cardNumber, String nameOnCard, String expiryDate, String CVVcode) throws Exception
				{
					Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.refundPopupHeader), driver);
					//enterAmountinPayRefundPopup(driver, test_steps, amount);
					Wait.wait5Second();
					elementsV2.refundPopupAmount.clear();
					elementsV2.refundPopupAmount.sendKeys(amount);
					folioLogger.info("Entered Refund Amount");
					test_steps.add("Entered Refund Amount");
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.REFUND_PAYMENT_METHOD), driver);
					elementsV2.REFUND_PAYMENT_METHOD.click();
					String Option = "(//span[contains(text(),'" + paymentMethod + "')])[last()]";
					Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
					driver.findElement(By.xpath(Option)).click();
					folioLogger.info("Selected Payment Method  "+paymentMethod );
					test_steps.add("Selected Payment Method  "+paymentMethod);
					if(paymentMethod.equals("MC")||paymentMethod.equals("Visa")||paymentMethod.equals("Discover"))
					{
						enterCardNumberinPayRefundpopup(driver,test_steps,cardNumber);
						enterCardHolderNameinPayRefundPopup(driver, test_steps, nameOnCard);
						enterExpiryDateinPayRefundpopup(driver, test_steps, expiryDate);
						enterCVVCodeinPayRefundpopup(driver, test_steps, CVVcode);
				
					}
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.refundPopupRefundButton), driver);	
					elementsV2.refundPopupRefundButton.click();
					folioLogger.info("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
					test_steps.add("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
					Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
					String toastMessage = elementsV2.TOASTER_MESSAGE.getText();
					if (toastMessage.contains("Refund payment of amount ") && toastMessage.contains("for "+customFolioName+" folio is success")) {
						folioLogger.info("Refund is successful using " + paymentMethod + " in Custom Folio");
						test_steps.add("Refund is successful using " + paymentMethod + " in Custom Folio");
					} else {
						folioLogger.info("Refund is not successfull in Custom Folio");
						test_steps.add("Refund is not successful in Custom Folio");						
					}
				}
		//Added By Riddhi
		public void verifyFolioLineItemNote(WebDriver driver, ArrayList<String>testSteps, String date, String paymentType,
				String expectedNote) throws InterruptedException 
		{	
			date=Utility.parseDate(date, "dd/MM/yyyy", "E MMM dd, yyyy");
			testSteps.add("Parsed Date : " + date);
			folioLogger.info("Parsed Date : " + date);
			
			List<WebElement> folioTypes=driver.findElements(By.xpath(OR_FolioNew.AllFolios));
			folioLogger.info(folioTypes.size());
			int totalRows=driver.findElements(By.xpath("//th[contains(@class,'lineitem-category')]//parent::tr//parent::thead//parent::table//tbody//tr")).size();
			totalRows = totalRows-1;
			String xPathNotes = "(//td[@class='lineitem-category']//span[@data-bind='text: category'][text()='"+paymentType+"']"
					+ "/parent::td/following-sibling::td[@class='lineitem-notes']"
					+"//img[contains(@src,'NotesView.svg')]/parent::button)["+totalRows+"]";
			folioLogger.info("xPath : " + xPathNotes);
			driver.findElement(By.xpath(xPathNotes)).click();
			Wait.wait15Second();
			String notesPopupxPath = "(//span[@data-bind='text: category'][text()='"+paymentType+"']/../..//notes//div[contains(@data-bind,'isNoteEditPopover')]/div[@data-bind='text: note'])["+totalRows+"]";
			String actualNotes = driver.findElement(By.xpath(notesPopupxPath)).getText();
			folioLogger.info("Actual Notes from Application : " + actualNotes);
			
			Utility.customAssert(actualNotes, expectedNote, true, "Successfully Verified Refund Notes in Folio Refund Line Item", 
					"Failed to Verify Refund Notes in Folio Refund Line Item", true, testSteps);
		}
		
		
			
		
		public void verifyFolioLineItem4AccountPaymentRefund(WebDriver driver, ArrayList<String> test_steps, String date,
				String paymentMethod, String accountType, String payByGroupAcc, String amount)
				throws InterruptedException
		{
				String desc = payByGroupAcc;
				folioLogger.info("Date Before Parsing - " + date);
				date = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");
				//Account (Group)
				paymentMethod = "Account ("+accountType+")";
				
				folioLogger.info("Description is -" + desc);
				//amount = Utility.convertDecimalFormat(amount);
				//amount = "-$" + amount + "";

				folioLogger.info("Folio Desc Column Value - " + date);
				folioLogger.info("Amount is - " + amount);
				folioLogger.info("Date After Parsing - " + date);

				String path = "//td[@class='lineitem-date']/span[(text()='"+ date + "')]"
				+ "/../following-sibling::td[@class='lineitem-category']/span[(text()='" + paymentMethod
				+ "')]" + "/../following-sibling::td[@class='lineitem-description pr']//a[normalize-space(text()='" + desc
				+ "')]" + "/../following-sibling::td[@class='lineitem-amount']/span[(text()='" + amount + "')]";
				folioLogger.info(path);
				Wait.WaitForElement(driver, path);
				WebElement element = driver.findElement(By.xpath(path));
				assertTrue(element.isDisplayed(), "Failed: To Verify Line Item");
				test_steps.add(" Successfully Verified Date:<b> " + date + "</b>");
				folioLogger.info(" Successfully Verified Date: " + date);
				test_steps.add(" Successfully Verified Category:<b> " + paymentMethod + "</b>");
				folioLogger.info(" Successfully Verified Category: " + paymentMethod);
				test_steps.add("Successfully Verified Description:<b> " + desc + "</b>");
				folioLogger.info(" Successfully Verified Description: " + desc);
				test_steps.add(" Successfully Verified Amount:<b> " + amount + "</b>");
				folioLogger.info(" Successfully Verified Amount: " + amount);
		}	
		
		//Added By Riddhi - Click on 'Pay' Button From Folio Tab
		public void clickPayButtonFromFolioTab(WebDriver driver, ArrayList<String> test_steps) throws Exception
		{

			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.FOLIO_PAY_BTN), driver);
			elementsV2.FOLIO_PAY_BTN.click();
			folioLogger.info("Clicked on Folio Pay button");
			test_steps.add("Clicked on Folio Pay button");
		}

		//Added By Riddhi - Enter Amount in Payment & Refund Popup
		public void enterAmountinPayRefundPopup(WebDriver driver, ArrayList<String> test_steps, String Amount) throws InterruptedException

		{
			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			Wait.wait5Second();
			elementsV2.takePaymentAmountInput.click();
			elementsV2.takePaymentAmountInput.clear();
			elementsV2.takePaymentAmountInput.clear();
			elementsV2.takePaymentAmountInput.sendKeys(Amount);
		}

		
		//Added By Riddhi - Move to specific Folio
		public void moveToFolio(WebDriver driver, ArrayList<String> test_steps, String folioName) throws InterruptedException
		{
			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			//String folioXpath = "//a[contains(text(),'RidsCorp_AkdUN5T65jQNDvHAMMVf(13778107)')]";
			String folioXpath = "//a[contains(text(),'"+folioName+"')]";
			Wait.WaitForElement(driver, folioXpath);
			Wait.waitForElementToBeClickable(By.xpath(folioXpath), driver);
			WebElement eleFolio = driver.findElement(By.xpath(folioXpath));
			assertTrue(eleFolio.isDisplayed(), "Failed : Folio is not displayed");
			eleFolio.click();
			Wait.wait5Second();
			test_steps.add("Navigated to Folio " + folioName);
			folioLogger.info("Navigated to Folio " + folioName);
		}
		
		
		//Added By Riddhi - enter CVV Code in payment/refund popup
		public void enterCVVCodeinPayRefundpopup(WebDriver driver, ArrayList<String> test_steps, String CVVcode)
		{
			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			if( elementsV2.CVV_CODE.isEnabled())
			{
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CVV_CODE), driver);
				elementsV2.CVV_CODE.click();
				elementsV2.CVV_CODE.clear();
				elementsV2.CVV_CODE.sendKeys(CVVcode);
				test_steps.add("Entered CVV Code : " + CVVcode);
				folioLogger.info("Entered CVV Code : "+CVVcode);
			}
		}

		//Added By Riddhi - enter Expiry Date in payment/refund popup
		public void enterExpiryDateinPayRefundpopup(WebDriver driver, ArrayList<String> test_steps, String expiryDate)
		{
			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.EXPIRY_DATE), driver);
			elementsV2.EXPIRY_DATE.click();
			elementsV2.EXPIRY_DATE.clear();
			elementsV2.EXPIRY_DATE.sendKeys(expiryDate);
			test_steps.add("Entered Expiry Date : " + expiryDate);
			folioLogger.info("Entered Expiry Date : "+expiryDate);
		}
				
		//Added By Riddhi - enter Card Number in payment/refund popup
		public void enterCardNumberinPayRefundpopup(WebDriver driver, ArrayList<String> test_steps, String cardNumber)
		{
			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CARD_NUMBER), driver);
			elementsV2.CARD_NUMBER.click();
			elementsV2.CARD_NUMBER.clear();
			elementsV2.CARD_NUMBER.sendKeys(cardNumber);
			test_steps.add("Entered Card Number : " + cardNumber);
			folioLogger.info("Entered Card number : "+cardNumber);
		}
				//Added By Riddhi - enter card holder Name in Payment/refund popup
		public void enterCardHolderNameinPayRefundPopup(WebDriver driver, ArrayList<String> test_steps, String nameOnCard)
		{
			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.NAME_ON_CARD), driver);
			elementsV2.NAME_ON_CARD.click();
			elementsV2.NAME_ON_CARD.clear();
			elementsV2.NAME_ON_CARD.sendKeys(nameOnCard);
			test_steps.add("Entered Name on Card: " + nameOnCard);
			folioLogger.info("Entered Name On Card : "+nameOnCard);
		}
		
		//Added By Riddhi - Verify Line Items in the Folio
				public void verifyFolioLineItems(WebDriver driver, ArrayList<String> test_steps, String date, String paymentMethod,
						String amount, String nameOnCard, String last4Digit, String expiryDate)
						throws InterruptedException 
				{
					test_steps.add("******************* Verifying Folio Line Item **********************");
					folioLogger.info("******************* Verifying Folio Line Item  **********************");
					String desc = null;
					folioLogger.info("Date Before Parsing - " + date);

					date = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");
					if(paymentMethod.contains("MC")||paymentMethod.contains("Visa")||paymentMethod.contains("Discover"))
					{
						desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp. Date: " + expiryDate + "";
					}
					else if(paymentMethod.contains("Reservation"))
					{
						desc = paymentMethod + " - ";
					}
					else
					{
						desc = paymentMethod;
					}
					folioLogger.info("Description is -" + desc);
					folioLogger.info("Folio Desc Column Value - " + date);
					folioLogger.info("Amount is - " + amount);
					folioLogger.info("Date After Parsing - " + date);
					
					String path = "//td[@class='lineitem-date']/span[(text()='"+ date + "')]" 
							+ "/../following-sibling::td[@class='lineitem-category']/span[(text()='" + paymentMethod
							+ "')]" + "/../following-sibling::td[@class='lineitem-description pr']//a[normalize-space(text()='" + desc
							+ "')]" + "/../following-sibling::td[@class='lineitem-amount']/span[contains(text(),'" + amount + "')]";
					folioLogger.info(path);
					Wait.WaitForElement(driver, path);
					WebElement element = driver.findElement(By.xpath(path));
					assertTrue(element.isDisplayed(), "Failed: To Verify Line Item");
					test_steps.add(" Successfully Verified Date:<b>  " + date + "</b>");
					folioLogger.info(" Successfully Verified  Date: " + date);
					test_steps.add(" Successfully Verified  Category:<b>  " + paymentMethod + "</b>");
					folioLogger.info(" Successfully Verified  Category: " + paymentMethod);
					test_steps.add("Successfully Verified  Description:<b>  " + desc + "</b>");
					folioLogger.info(" Successfully Verified Description: " + desc);
					test_steps.add(" Successfully Verified Amount:<b>  " + amount + "</b>");
					folioLogger.info(" Successfully Verified  Amount: " + amount);
				}
			
			
			
			//Added By Riddhi - Verify Payment & Refund Folio Line Items
				//Updated by Sucharitha Nagaraju-- Reservation option updated
				public void verifyFolioLineItems(WebDriver driver, ArrayList<String> test_steps, String date, String paymentMethod,
				String payAccountNumber, String amount, String CCType, String cardNumber,
				String nameOnCard, String last4Digit, String expiryDate)
				throws InterruptedException
				{
				String desc = null;
				folioLogger.info("Date Before Parsing - " + date); date = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");
				if(paymentMethod.contains("MC")||paymentMethod.contains("Visa")||paymentMethod.contains("Discover"))
				{
				desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp. Date: " + expiryDate + "";
				}
				else if(paymentMethod.contains("Reservation"))
				{
				desc = paymentMethod + " - " + payAccountNumber;
				}
				else if(paymentMethod.contains("Account"))
				{
				desc = "Account" + " - " + payAccountNumber;
				}
				else if(paymentMethod.contains("Log As External Payment"))
				{
				String cardFormat = null;
				if(CCType.equals("MC")) {
				cardFormat=""+CCType+" "+last4Digit+" ("+expiryDate+")";
				}else if(CCType.equals("Cash")){
				cardFormat=CCType;
				}
				date = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");
				desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp. Date: " + expiryDate + "";
				paymentMethod = CCType;
				}
				else
				{ desc = paymentMethod; }
				folioLogger.info("Folio Desc Column Value - " + date);
				folioLogger.info("Amount is - " + amount);
				folioLogger.info("Date After Parsing - " + date);
				String path = "//td[@class='lineitem-date']/span[(text()='"+ date + "')]"
				+ "/../following-sibling::td[@class='lineitem-category']/span[(text()='" + paymentMethod
				+ "')]" + "/../following-sibling::td[@class='lineitem-description pr']//a[normalize-space(text()='" + desc
				+ "')]" + "/../following-sibling::td[@class='lineitem-amount']/span[contains(text(),'" + amount + "')]";
				

		     
				folioLogger.info(path);
				Wait.WaitForElement(driver, path);
				WebElement element = driver.findElement(By.xpath(path));
				assertTrue(element.isDisplayed(), "Failed: To Verify Line Item");
				test_steps.add(" Successfully Verified Date:<b> " + date + "</b>");
				folioLogger.info(" Successfully Verified Date: " + date);
				test_steps.add(" Successfully Verified Category:<b> " + paymentMethod + "</b>");
				folioLogger.info(" Successfully Verified Category: " + paymentMethod);
				test_steps.add("Successfully Verified Description:<b> " + desc + "</b>");
				folioLogger.info(" Successfully Verified Description: " + desc);
				test_steps.add(" Successfully Verified Payment Amount:<b> " + amount + "</b>");
				folioLogger.info(" Successfully Verified Payment Amount: " + amount);
				}
				
				
				//Added By Riddhi
				public void makePayment(WebDriver driver, ArrayList<String> test_steps,
						String paymentAmount, String paymentMethod, String payAccountNumber, String CCType,
						String cardNumber, String nameOnCard, String expiryDate, String CVVcode) throws Exception
				
						{
					   Elements_All_Payments payment = new Elements_All_Payments(driver);
					
						ReservationV2 resV2 = new ReservationV2();
						Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
						Wait.WaitForElement(driver, OR.PayButton);
						Wait.waitForElementToBeVisibile(By.xpath(OR.PayButton), driver);
						Utility.ScrollToElement(payment.PayButton, driver);
						Wait.waitForElementToBeClickable(By.xpath(OR.PayButton), driver);
						payment.PayButton.click();
						test_steps.add("Click payment button");
						folioLogger.info("Click payment button");
						
						String payMethod = paymentMethod;
						Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.takePaymentHeader), driver);
						folioLogger.info("Entered Payment : "+paymentAmount);
						test_steps.add("Entered Payment : "+paymentAmount);
						enterAmountinPayRefundPopup(driver, test_steps, paymentAmount);
						if(paymentMethod.contains("Log As External Payment"))
						{
						payMethod = CCType;
						}
						else if(paymentMethod.contains("Group")||paymentMethod.contains("Account"))
						{
						payMethod ="Account - "+payAccountNumber;
						test_steps.add("Account Name & Number : "+payMethod);
						folioLogger.info("Account Name & Number : "+payMethod);
						}
						else if(paymentMethod.contains("HA")||paymentMethod.contains("House Account"))
						{
						payMethod = "House Account";
						}
						try
						{
						Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD), driver);
						elementsV2.TAKE_PAYMENT_PAYMENT_METHOD.click();
						String Option = "(//span[contains(text(),'" + payMethod + "')])[last()]";
						Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
						driver.findElement(By.xpath(Option)).click();
						folioLogger.info("Selected Payment Method "+paymentMethod );
						test_steps.add("Selected Payment Method "+paymentMethod);
						}
						catch(Exception e)
						{
						Wait.WaitForElement(driver, OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD);
						Utility.ScrollToElement(elementsV2.TAKE_PAYMENT_PAYMENT_METHOD, driver);
						Wait.wait2Second();
						elementsV2.TAKE_PAYMENT_PAYMENT_METHOD.click();

						String Option = "(//span[contains(text(),'" + payMethod + "')])[last()]";
						Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
						driver.findElement(By.xpath(Option)).click();
						folioLogger.info("Selected Payment Method "+paymentMethod );
						test_steps.add("Selected Payment Method "+paymentMethod);
						}
						if(paymentMethod.contains("Reservation"))
						{
						Wait.waitForElementByXpath(driver, OR_ReservationV2.ENTER_RES_NO_IN_PAY_REFUND_POPUP);
						elementsV2.ENTER_RES_NO_IN_PAY_REFUND_POPUP.sendKeys(payAccountNumber);
						test_steps.add("Entered Reservation number : " + payAccountNumber);
						folioLogger.info("Entered Reservation number : "+payAccountNumber);
						Wait.wait2Second();
						String reservationToSelect = "//b[text()='"+ payAccountNumber +"']/../../..//parent::li";
						WebElement ele = driver.findElement(By.xpath(reservationToSelect));
						ele.click();
						test_steps.add("Clicked reservation with number : " + payAccountNumber + " in search result");
						folioLogger.info("reservation with number : "+payAccountNumber+" in Search Result");
						}
						else if(paymentMethod.contains("Log As External Payment")|| paymentMethod.contains("MC") ||
						paymentMethod.contains("Visa") || paymentMethod.contains("Discover"))
						{
						enterCardNumberinPayRefundpopup(driver,test_steps,cardNumber);
						enterCardHolderNameinPayRefundPopup(driver, test_steps, nameOnCard);
						enterExpiryDateinPayRefundpopup(driver, test_steps, expiryDate);
						enterCVVCodeinPayRefundpopup(driver, test_steps, CVVcode);



						if(paymentMethod.contains("Log As External Payment"))
						{
						Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.TAKEPAYMENT_LOG_AS_EXTERNAL_CHK), driver);
						elementsV2.TAKEPAYMENT_LOG_AS_EXTERNAL_CHK.click();
						test_steps.add("Log As External Checkbox is checked.....");
						folioLogger.info("Log As External Checkbox is checked.....");
						}
						}
						else if(paymentMethod.contains("HA")||paymentMethod.contains("House Account"))
						{
						Wait.waitForElementByXpath(driver, OR_ReservationV2.ENTER_HA_NO_IN_PAY_REFUND_POPUP);
						elementsV2.ENTER_HA_NO_IN_PAY_REFUND_POPUP.sendKeys(payAccountNumber);
						test_steps.add("Entered House Account number : " + payAccountNumber);
						folioLogger.info("Entered House Account number : " + payAccountNumber);
						Wait.wait2Second();
						String reservationToSelect = "//b[text()='"+ payAccountNumber +"']/../../..//parent::li";
						WebElement ele = driver.findElement(By.xpath(reservationToSelect));
						ele.click();
						test_steps.add("Clicked HA Account with number : " + payAccountNumber + " in search result");
						folioLogger.info("Cicked HA Account with number : "+payAccountNumber+" in Search Result");
						}
						else if(paymentMethod.contains("GC")||paymentMethod.contains("Gift Certificate")||paymentMethod.contains("Gift Card"))
						{
						Wait.waitForElementByXpath(driver, OR_ReservationV2.ENTER_GC_NO_IN_PAY_REFUND_POPUP);
						elementsV2.ENTER_GC_NO_IN_PAY_REFUND_POPUP.sendKeys(payAccountNumber);
						test_steps.add("Entered Gift Certificate Account number : " + payAccountNumber);
						folioLogger.info("Entered Gift Certificate Account number : " + payAccountNumber);
						Wait.wait2Second();
						String gcToSelect = "//b[text()='"+ payAccountNumber +"']/../../..//parent::li";
						WebElement ele = driver.findElement(By.xpath(gcToSelect));
						ele.click();
						test_steps.add("Clicked GC Account with number : " + payAccountNumber + " in search result");
						folioLogger.info("Clicked GC Account with number : "+payAccountNumber+" in Search Result");
						}
						Wait.wait15Second();
						try
						{
						Utility.ScrollToElement(elementsV2.takePaymentPayButton, driver);
						Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.takePaymentPayButton), driver);
						elementsV2.takePaymentPayButton.click();
						//Wait.wait5Second();
						}catch(Exception e)
						{
						String payXpath = "(//span[contains(@data-bind,'PaymentProcessButtonText')]|//a[contains(@data-bind,'IsPaymentFormValid')]/span[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'LOG')]|//a[contains(@data-bind,'PaymentProcessButtonText')][contains(text(),'Pay')]|//a[contains(text(),'Authorize')]|//a[contains(text(),'Refund')])[2]";
						Wait.waitForElementToBeClickable(By.xpath(payXpath), driver);
						Utility.ScrollToElement(By.xpath(payXpath).findElement(driver), driver);
						Utility.clickThroughJavaScript(driver, By.xpath(payXpath).findElement(driver));
						Wait.wait5Second();
						}
						resV2.closePaymentSuccessfullPopup(driver, test_steps);
						folioLogger.info("Click On Continue button, once payment is successfully");
						test_steps.add("Click On Continue button, once payment is successfully");
						}
		
		public void switchGuestfolio2(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			String folioxpath="(//a[contains(text(),'Guest Folio')])[2]";
			Wait.WaitForElement(driver, folioxpath);		
			Wait.wait2Second();
			driver.findElement(By.xpath(folioxpath)).click();
			Wait.wait5Second();			
		}

		//Added By Riddhi
		public void deleteFolioLineItem(WebDriver driver, ArrayList<String> test_steps, String date, 
				String category, String amount, String nameOnCard, String last4Digit, String expiryDate) throws InterruptedException	{
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			String desc = null;
			folioLogger.info("Date Before Parsing - " + date);

			date = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");
			if(category.contains("MC")||category.contains("Visa")||category.contains("Discover"))
			{
				desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp. Date: " + expiryDate + "";
			}
			else if(category.contains("Reservation"))
			{
				desc = category + " - ";
			}
			else
			{
				desc = category;
			}
			folioLogger.info("Description is -" + desc);
			folioLogger.info("Folio Desc Column Value - " + date);
			folioLogger.info("Amount is - " + amount);
			folioLogger.info("Date After Parsing - " + date);
			String deleteButtonxPath= "//td[@class='lineitem-date']/span[(text()='"+ date + "')]"
					+"/../following-sibling::td[@class='lineitem-category']//span[(text() ='"+category + "')]"
					+"/../following-sibling::td[@class='lineitem-description pr']//a[normalize-space(text()='" + desc
					+ "')]" + "/../following-sibling::td[@class='lineitem-amount']//span[contains(text(),'" + amount
					+ "')]" + "//../following-sibling::td[@class='lineitem-delete']//button[contains(@data-bind,'handleSelectVoidFolio')]";
			folioLogger.info(deleteButtonxPath);
			Wait.WaitForElement(driver, deleteButtonxPath);
			WebElement element = driver.findElement(By.xpath(deleteButtonxPath));
			assertTrue(element.isDisplayed(), "Failed: To Verify Delete Button in Folio Line Item");
			element.click();
			addDeleteNotes(driver, test_steps, "delete folio item");			
			clickVoidFolioLineItemButton(driver, test_steps);
		}
		//Added By Riddhi
		public void addDeleteNotes(WebDriver driver, ArrayList<String> test_steps, String voidNotes) throws InterruptedException
		{
			Elements_FolioNew eleFolio = new Elements_FolioNew(driver);
			eleFolio.deleteFolioLineItemsNote.clear();
			eleFolio.deleteFolioLineItemsNote.sendKeys(voidNotes);
		}
		//Added By Riddhi
		public void clickVoidFolioLineItemButton(WebDriver driver, ArrayList<String> test_steps)
		{
			Elements_FolioNew eleFolio = new Elements_FolioNew(driver);			
			Wait.WaitForElement(driver, OR_FolioNew.voidButton);
			Wait.waitForElementToBeClickable(By.xpath(OR_FolioNew.voidButton), driver);
			//Wait.wait2Second();
			eleFolio.voidButton.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_FolioNew.voidItemToasterMessage), driver);			
			folioLogger.info("Deleted Item From Folio");
			test_steps.add("Deleted Item From Folio");
		}
		//Added By Riddhi
		public void clickFolioDesc(WebDriver driver,String status, String category, ArrayList<String> test_steps) throws InterruptedException 
		{
			String path = "//tbody//td[@class='lineitem-status']/span[text()='"+status+"']"
					+ "/../following-sibling::td[@class='lineitem-category']/span[contains(text(),'"+category+"')]"
					+ "/../following-sibling::td[contains(@class,'lineitem-description')]/a";
			folioLogger.info("status "+status);
			test_steps.add("status "+status);
			folioLogger.info("Category "+category);
			test_steps.add("Category "+category);
			folioLogger.info("Clicking on the Line Item Description  "+path);
			test_steps.add("Clicking on the Line Item Description "+path);
			Wait.WaitForElement(driver, path);
			WebElement element = driver.findElement(By.xpath(path));
			Utility.ScrollToElement(element, driver);
			element.click();			
		}
		//Added By Riddhi
		public void adjustFolioLineItems(WebDriver driver, ArrayList<String> test_steps, String lineItemCategory, String amount)
		{
			try
			{
				String adjustBtnXpath = "//button[contains(@data-bind,'$parent.handleClickOnAdjust')]//img";
				Wait.WaitForElement(driver, adjustBtnXpath);
				WebElement element = driver.findElement(By.xpath(adjustBtnXpath));
				Utility.ScrollToElement(element, driver);
				element.click();
				folioLogger.info("Adjust Button Clicked");
				test_steps.add("Adjust Button Clicked");
				
				String downArrow = "//tr[contains(@class,'addRow')]/../../../../../../..//button[@class='btn dropdown-toggle btn-default']/span/i";
				//String downArrow = "//tr[contains(@class,'addRow')]/../../../../../../../div//td//div/button[@class='btn dropdown-toggle btn-default']/span/i";
				folioLogger.info("Category downArrow : " + downArrow);				
				Wait.WaitForElement(driver, downArrow);
				WebElement elementCategory = driver.findElement(By.xpath(downArrow));
				Utility.ScrollToElement(elementCategory, driver);
				elementCategory.click();
				
				String categoryXpath = "//tr[contains(@class,'addRow')]/../../../../../../..//ul//li//a//span[text()='"+lineItemCategory+"']";
				folioLogger.info("Category xpath : " + categoryXpath);
				Wait.WaitForElement(driver, categoryXpath);
				WebElement elementCat = driver.findElement(By.xpath(categoryXpath));
				elementCat.click();
				folioLogger.info("Selected Category"+lineItemCategory);
				test_steps.add("Selected Category"+lineItemCategory);
				
				String amountXpath = "//input[contains(@data-bind,'value: $data.amount, valueUpdate')]";
				folioLogger.info("Amount xpath : " + amountXpath);				
				Wait.WaitForElement(driver, amountXpath);
				WebElement amountElement = driver.findElement(By.xpath(amountXpath));
				amountElement.clear();
				amountElement.sendKeys(amount);
				//amountElement.sendKeys(Keys.TAB);
				folioLogger.info("Entered Amount : "+amount);
				test_steps.add("Entered AMount : "+amount);				
				
				String addBtnxpath = "//button[contains(@data-bind,'$parent.handleAddTransactionItem, enable: $data.enableAddButton')]";
				Wait.WaitForElement(driver, addBtnxpath);				
				Wait.waitForElementToBeClickable(By.xpath(addBtnxpath), driver);
				Utility.ScrollToElement(driver.findElement(By.xpath(addBtnxpath)), driver);
				WebElement addElement = driver.findElement(By.xpath(addBtnxpath));
				addElement.click();				
				folioLogger.info("Add Button Clicked");
				test_steps.add("Add Button Clicked");
				
				String saveChangesBtn = "//button[contains(@data-bind,'click: $parent.handleItemDetailsSave')]//span[text()='Save Changes']";
				Wait.WaitForElement(driver, saveChangesBtn);
				WebElement saveElement = driver.findElement(By.xpath(saveChangesBtn));
				saveElement.click();
				Wait.wait25Second();
				folioLogger.info("Save Changes Button Clicked");
				test_steps.add("Save Changes Button Clicked");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		//Added By Riddhi - This method verifies Whether Checkbox is enabled or disabled in folio line items, 
		public boolean verifyCheckboxisEnabledForFolioLineItems(WebDriver driver, ArrayList<String> test_steps, String date, String status,
				String paymentMethod,String payAccountNumber, String amount, String CCType, String cardNumber, 
				String nameOnCard, String last4Digit, String expiryDate)
				throws InterruptedException 
		{
			String desc = null;
			folioLogger.info("Date Before Parsing - " + date);

			date = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");
			if(paymentMethod.contains("MC")||paymentMethod.contains("Visa")||paymentMethod.contains("Discover"))
			{
				desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp.Date:" + expiryDate + "";
			}
			else if(paymentMethod.contains("Reservation"))
			{
				desc = paymentMethod + " - " + payAccountNumber;
			}
			else if(paymentMethod.contains("Account"))
			{
				desc = "Account" + " - " + payAccountNumber;
			}
			else if(paymentMethod.contains("Log As External Payment"))
			{
				String cardFormat = null;
				if(CCType.equals("MC")) {
					cardFormat=""+CCType+" "+last4Digit+" ("+expiryDate+")";
				}else if(CCType.equals("Cash")){
					cardFormat=CCType;
				}
				date = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");
				desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp. Date: " + expiryDate + "";
				paymentMethod = CCType;
			}
			else
			{ desc = paymentMethod;	}
			folioLogger.info("Folio Desc Column Value - " + date);
			folioLogger.info("Amount is - " + amount);
			folioLogger.info("Date After Parsing - " + date);
			
			//td[@class='lineitem-description pr']//a[normalize-space(text()='Spa')]/..//..//parent::td[@class='lineitem-category']/span[(text()='Spa')]/..//..//parent::td[@class='lineitem-date']/span[(text()='Apr 11, 2022')]/..//..//parent::td[@class='lineitem-status']/span[(text()='Void')]/..//..//parent::td[@class='lineitem-checkbox']//label[contains(@data-bind,'isTableItemDisabled')]//span			
			String xPathForFolioLineItemcheckbox = "//td[@class='lineitem-description pr']//a[normalize-space(text()='"+desc+"')]"
					+ "/..//..//parent::td[@class='lineitem-category']/span[(text()='"+paymentMethod+"')]"
					+ "/..//..//parent::td[@class='lineitem-date']/span[(text()='"+date + "')]"
					+"/..//..//parent::td[@class='lineitem-status']/span[(text()='"+status+"')]";				
			folioLogger.info(xPathForFolioLineItemcheckbox);
			Wait.WaitForElement(driver, xPathForFolioLineItemcheckbox);
			WebElement element = driver.findElement(By.xpath(xPathForFolioLineItemcheckbox));			
			assertTrue(element.isDisplayed(), "Failed: To Verify Checkbox");
			test_steps.add(" Successfully Verified Date:<b>  " + date + "</b>");
			folioLogger.info(" Successfully Verified  Date: " + date);
			test_steps.add(" Successfully Verified  Category:<b>  " + paymentMethod + "</b>");
			folioLogger.info(" Successfully Verified  Category: " + paymentMethod);
			test_steps.add("Successfully Verified  Description:<b>  " + desc + "</b>");
			folioLogger.info(" Successfully Verified Description: " + desc);
			test_steps.add(" Successfully Verified Payment Amount:<b>  " + amount + "</b>");
			folioLogger.info(" Successfully Verified  Payment Amount: " + amount);
			if( element.isEnabled())
			{
				test_steps.add("<b> Folio Line Item Checkbox is enabled </b>");
				folioLogger.info(" <b> Folio Line Item Checkbox is enabled </b>");
				return true;
			}
			else
			{
				test_steps.add("<b> Folio Line Item Checkbox is disabled </b>");
				folioLogger.info(" <b> Folio Line Item Checkbox is disabled</b>");				
				return false;
			}
		}

				
		public void clickFolioDescription(WebDriver driver,String status, String category,String payMode,ArrayList<String> test_steps) throws InterruptedException {
			String path = "//tbody//td[@class='lineitem-status']/span[text()='"+status+"']"
					+ "/../following-sibling::td[@class='lineitem-category']/span[contains(text(),'"+category+"')]"
							+ "/../following-sibling::td[contains(@class,'lineitem-description')]/a";
			folioLogger.info("status "+status);
			test_steps.add("status "+status);
			folioLogger.info("Category "+category);
			test_steps.add("Category "+category);
			folioLogger.info("payMode "+payMode);
			test_steps.add("payMode "+payMode);
			
			try {	
				folioLogger.info("Clicking on the path try block "+path);
				test_steps.add("Clicking on the path try block "+path);
				Wait.WaitForElement(driver, path);
				WebElement element = driver.findElement(By.xpath(path));
				Utility.ScrollToElement(element, driver);
				element.click();
			String path1=null;
			boolean isExist=false;
			
			if(payMode.equalsIgnoreCase("Capture")||payMode.equalsIgnoreCase("Authorization Only"))
			{
			 Wait.WaitForElement(driver,  path1);
			path1 = "//div[contains(@class,'modal-content')]//h4[contains(@class,'modal-title')]/span[contains(text(),'Payment')]";		 		
			folioLogger.info(path1);
			test_steps.add("Displaying the path1 "+path1);
			}else {
			path1 = "//div[@class='modal-content']//h4[contains(@class,'modal-title') and contains(text(),'Item Detail')]";
			folioLogger.info(path1);
			test_steps.add("Displaying the path1 "+path1);
			}
			isExist=Utility.isElementDisplay(driver.findElement(By.xpath(path1)));
			 Utility.verifyBooleanEquals(isExist, true, test_steps);
			 
			}catch(Exception e) {
				try {
					String processButton="//div[@class='scroll-table-body']/..//button[@class='btn folio-btn btn-success folio-btn-success']";
					 Wait.WaitForElement(driver,  processButton);		
						Wait.wait2Second();
						driver.findElement(By.xpath( processButton)).click();
						folioLogger.info("Clicked on the process button "+processButton);
						test_steps.add("Clickedon the process button "+processButton);
				}catch(Exception e1) {
					String processButton="//div[@class='scroll-table-body']/..//button[@class='btn folio-btn btn-success folio-btn-success']";
					 Wait.WaitForElement(driver,  processButton);		
						Wait.wait2Second();
						driver.findElement(By.xpath( processButton)).click();
				}
				
				}
		}

		//Added By Sucharitha- Switch to the  Manual CC/Cash Refund
		public void ClickManualCardCCButton(WebDriver driver, ArrayList<String> test_steps)throws InterruptedException{
        	String RadioButtonPath = "//div[@class='col-md-4 ir-col-middle']//span[@class='ir-radio-btn']";
        	Wait.WaitForElement(driver, RadioButtonPath);

    		driver.findElement(By.xpath(RadioButtonPath)).click();
    		test_steps.add("Successfully switched to Manual CC/Cash Refund");
    		folioLogger.info("Successfully switched to Manual CC/Cash Refund");
        	
     
				try {
					String processButton="//div[@class='scroll-table-body']/..//button[@class='btn folio-btn btn-success folio-btn-success']";
					 Wait.WaitForElement(driver,  processButton);		
						Wait.wait2Second();
						driver.findElement(By.xpath( processButton)).click();
						folioLogger.info("Clicked on the process button "+processButton);
						test_steps.add("Clickedon the process button "+processButton);
				}catch(Exception e1) {
					String processButton="//div[@class='scroll-table-body']/..//button[@class='btn folio-btn btn-success folio-btn-success']";
					 Wait.WaitForElement(driver,  processButton);		
						Wait.wait2Second();
						driver.findElement(By.xpath( processButton)).click();
				}
				
				}
		
					
		public double getFolioLineItemTotalForFutureDate(WebDriver driver, ArrayList<String> test_steps,String checkinDate) throws Exception {			
			
			 DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
			 SimpleDateFormat d3=new SimpleDateFormat("yyyy-MM-dd");
			 Date checkin_Date = df1.parse(checkinDate);			
			HashMap<String, ArrayList<String>> data = new HashMap<>();			
			String strRows = "(//table[contains(@class,'table-lineItems')])[1]/tbody/tr"; 
			   folioLogger.info("strRows: "+strRows);			
			List<WebElement> rows = driver.findElements(By.xpath(strRows));		
			double total=0.00;
			String lineItemTotal="0.00";
			for (int i = 0; i < rows.size(); i++) {
				ArrayList<String> datesData = new ArrayList<>();
				List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
				String dateCell=cells.get(3).getText().trim();
				String categoryCell=cells.get(4).getText().trim();
				folioLogger.info("dateCell: "+dateCell);
				 DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
				 SimpleDateFormat d2=new SimpleDateFormat("yyyy-MM-dd");
				  Date futurDate = df.parse(dateCell);
		           System.out.println("Date: " + dateCell);
		          // folioLogger.info("Date in dd/MM/yyyy format is: "+df.format(dateCell));
		           folioLogger.info("Date in yyyy-MM-dd format is: "+d2.format(futurDate));
		           if(d2.format(futurDate).compareTo(d3.format(checkin_Date))>0)
		           {
		        	   if(categoryCell.contains("MC")||categoryCell.contains("Visa")||categoryCell.contains("Cash")||categoryCell.contains("Check"))
		        	   {
		        		   continue;		        		   
		        	   }else {
		        	  
		        	   lineItemTotal=cells.get(10).getText().trim();
		        	   folioLogger.info("lineItemTotal: "+lineItemTotal);
		        	   total=total+Double.parseDouble(Utility.removeBracketsAndMinusSign(lineItemTotal));
		        	   folioLogger.info("total: "+total);
		        	  
						}
						  
		           }else {
		        	   continue;
		           }			
			}			
			return total;		
		}
		
		public double getFolioBalanceOfFirstFolio(WebDriver driver, ArrayList<String> test_steps) throws Exception {
			Elements_FolioNew folio = new Elements_FolioNew(driver);
			Wait.WaitForElement(driver, OR_FolioNew.BalanceValue);
			String balance = null;
			double balanceValue = 0.0;		
			WebElement firstFolios = folio.FirstFolios;			
			firstFolios.click();
			Wait.wait2Second();
			balance = folio.BalanceValue.getText().replace("$", "").trim();
			balanceValue = balanceValue + Double.parseDouble(balance);				
			test_steps.add("Folio Balance: "+balanceValue);
			folioLogger.info("Folio Balance: "+balanceValue);
		
			return balanceValue;
		}
		
		


}
