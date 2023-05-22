package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.model.RefundInfo;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_ReservationV2;

public class GuestFolio {
	
	public static Logger folioLogger = Logger.getLogger("GuestFolio");

	public void clickOnFolioTab(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.GUEST_FOLIO_TAB), driver);
		elements.GUEST_FOLIO_TAB.click();
		waitForFolioToLoad(driver);
		folioLogger.info("Navigated to guest folio");
		test_steps.add("Navigated to guest folio");
	}

	private void waitForFolioToLoad(WebDriver driver) {
		try {
			int i = driver.findElements(By.xpath("(//div[contains(@data-bind,'showLoading') and contains(@style,'display: none')])[11]")).size();
			do {
				Wait.wait1Second();
				i = driver.findElements(By.xpath("(//div[contains(@data-bind,'showLoading') and contains(@style,'display: none')])[11]")).size();
				System.out.println(i);
			} while (i==0);			
		} catch (Exception e) {
			folioLogger.info(e);
		}
	}

	public void clickOnAddChargeButton(WebDriver driver, ArrayList<String> test_steps) {
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_CHARGE), driver);
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_CHARGE)));
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_CATEGORY), driver);	
		folioLogger.info("Clicked on Add Charge button");
		test_steps.add("Clicked on Add Charge button");
	}

	public void enterDateForAddCharge(WebDriver driver, ArrayList<String> test_steps, String date) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_DATE), driver);
		elements.GUEST_FOLIO_ADD_NEW_DATE.click();
		String header = driver.findElement(By.xpath("//div[@class='datepicker-days']//th[@class='datepicker-switch']")).getText();
		if (!header.equalsIgnoreCase(Utility.parseDate(date, "dd/MM/yyyy", "MMMM yyyy"))) {
			do {
				header = driver.findElement(By.xpath("//div[@class='datepicker-days']//th[@class='datepicker-switch']")).getText();
				if (header.equalsIgnoreCase(Utility.parseDate(date, "dd/MM/yyyy", "MMMM yyyy"))) {
					break;
				}
			} while (header.equalsIgnoreCase(Utility.parseDate(date, "dd/MM/yyyy", "MMMM yyyy")));
		}
		String day = Utility.parseDate(date, "dd/MM/yyyy", "EEEE, MMM dd, yyyy");
		driver.findElement(By.xpath("//td[@title='"+day+"']")).click();			
		folioLogger.info("Provided date for new charge as : "+date);
		test_steps.add("Provided date for new charge as : <b>"+date+"</b>");
	}
	
	public void selectCategoryForAddCharge(WebDriver driver, ArrayList<String> test_steps, String category) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_CATEGORY), driver);
		elements.GUEST_FOLIO_ADD_NEW_CATEGORY.click();
		String categoryXpath = "//a[contains(@data-bind,'ClickCategory') and contains(text(),'"+category+"')]";
		Wait.waitForElementToBeVisibile(By.xpath(categoryXpath), driver);
		driver.findElement(By.xpath(categoryXpath)).click();
		folioLogger.info("Selected category for new charge as : <b>"+category+"</b>");
		test_steps.add("Selected category for new charge as : <b>"+category+"</b>");
	}

	public void provideDescriptionForAddCharge(WebDriver driver, ArrayList<String> test_steps, String desc) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_DESCRIPTION), driver);
		elements.GUEST_FOLIO_ADD_NEW_DESCRIPTION.clear();
		elements.GUEST_FOLIO_ADD_NEW_DESCRIPTION.sendKeys(desc);
		folioLogger.info("Provided description for new charge as : <b>"+desc+"</b>");
		test_steps.add("Provided description for new charge as : <b>"+desc+"</b>");
	}

	public void provideNotesAddCharge(WebDriver driver, ArrayList<String> test_steps, String note) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_NOTE_ICON), driver);
		elements.GUEST_FOLIO_ADD_NEW_NOTE_ICON.click();
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_NOTE_TEXTFIELD), driver);
		elements.GUEST_FOLIO_ADD_NEW_NOTE_TEXTFIELD.clear();
		elements.GUEST_FOLIO_ADD_NEW_NOTE_TEXTFIELD.sendKeys(note);
		elements.GUEST_FOLIO_ADD_NEW_NOTE_ADD_NOTE_BUTTON.click();
		folioLogger.info("Provided note for new charge as : <b>"+note+"</b>");
		test_steps.add("Provided description for new charge as : <b>"+note+"</b>");
	}

	public void provideQuantityForAddCharge(WebDriver driver, ArrayList<String> test_steps, String quantity) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_QUANTITY), driver);
		elements.GUEST_FOLIO_ADD_NEW_QUANTITY.clear();
		elements.GUEST_FOLIO_ADD_NEW_QUANTITY.sendKeys(quantity);
		folioLogger.info("Provided Quantity for new charge as : <b>"+quantity+"</b>");
		test_steps.add("Provided Quantity for new charge as : <b>"+quantity+"</b>");
	}

	public void provideAmountForAddCharge(WebDriver driver, ArrayList<String> test_steps, String amount) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_ADD_NEW_AMOUNT), driver);
		Utility.clearValue(driver, elements.GUEST_FOLIO_ADD_NEW_AMOUNT);
//		elements.GUEST_FOLIO_ADD_NEW_AMOUNT.clear();
		elements.GUEST_FOLIO_ADD_NEW_AMOUNT.sendKeys(amount);
		folioLogger.info("Provided Amount for new charge as : <b>"+amount+"</b>");
		test_steps.add("Provided Amount for new charge as : <b>"+amount+"</b>");
	}
	
	public void clickOnSaveChangesButton(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.GUEST_FOLIO_SAVE_CHANGES), driver);
		elements.GUEST_FOLIO_SAVE_CHANGES.click();
		folioLogger.info("Clicked on Save Changes button");
		test_steps.add("Clicked on Save Changes button");
		Wait.waitForElementToBeVisibile(By.xpath("//div[@class='toast-title' and contains(text(),'Reservation Saved')]"), driver);
		String message = driver.findElement(By.xpath("//div[@class='toast-message']")).getText();
		folioLogger.info("Success message displayed as : "+message);
		test_steps.add("Success message displayed as : <b>"+message+"</b>");
	}
	
	public void addChargeAndPostLineItem(WebDriver driver, ArrayList<String> test_steps, String date, 
			String category, String desc, String note, String quantity, String amount) {
		clickOnAddChargeButton(driver, test_steps);
		if (Utility.validateString(date)) {
			enterDateForAddCharge(driver, test_steps, date);			
		}
		selectCategoryForAddCharge(driver, test_steps, category);
		if (Utility.validateString(desc)) {
			provideDescriptionForAddCharge(driver, test_steps, desc);			
		} if (Utility.validateString(note)) {
			provideNotesAddCharge(driver, test_steps, note);			
		} if (Utility.validateString(quantity)) {
			provideQuantityForAddCharge(driver, test_steps, quantity);			
		} if (Utility.validateString(amount)) {
			provideAmountForAddCharge(driver, test_steps, amount);			
		}
		clickOnSaveChangesButton(driver, test_steps);
	}
	
	public HashMap<String,ArrayList<String>> getAllLineItems(WebDriver driver, ArrayList<String> test_steps, String routing) {
		ArrayList<String> lineItemsToRoute = new ArrayList<>();
		ArrayList<String> lineItemsNotToRoute = new ArrayList<>();
		HashMap<String, ArrayList<String>> lineItemsData = new HashMap<>();
		List<WebElement> dates = driver.findElements(By.xpath("//td[@class='lineitem-date']//span"));
		
		for (int i = 0; i < dates.size(); i++) {
			int j = i+1;
			String status = driver.findElement(By.xpath("(//td[@class='lineitem-status']//span)["+j+"]")).getText();
			String date = driver.findElement(By.xpath("(//td[@class='lineitem-date']//span)["+j+"]")).getText();
			String category = driver.findElement(By.xpath("(//td[@class='lineitem-category']//span)["+j+"]")).getText();
			String description = driver.findElement(By.xpath("(//td[@class='lineitem-description pr']//a)["+j+"]")).getText();
			String quantity = driver.findElement(By.xpath("(//td[@class='lineitem-qty']//span)["+j+"]")).getText();
			String amount = driver.findElement(By.xpath("(//td[@class='lineitem-amount']//span)["+j+"]")).getText();
			String tax = driver.findElement(By.xpath("(//td[@class='lineitem-tax ']//span)["+j+"]")).getText();
			String total = driver.findElement(By.xpath("(//td[@class='lineitem-total']//span)["+j+"]")).getText();		
			String lineItem = category+" for the day "+date+" with amount "+amount+", description : "+description
					+", quantity : "+quantity+", tax : "+tax+", total : "+total+" and status is "+status;
			if (routing.equalsIgnoreCase("All Items")) {
					lineItemsToRoute.add(lineItem);
					test_steps.add("Line Item <b>"+lineItem+"</b> to be routed");
			} else if (routing.equalsIgnoreCase("Room Charges Only")) {
				if (category.equalsIgnoreCase("Room Charge")) {
					lineItemsToRoute.add(lineItem);					
					test_steps.add("Line Item <b>"+lineItem+"</b> to be routed");
				} else {
					lineItemsNotToRoute.add(lineItem);
					test_steps.add("Line Item <b>"+lineItem+"</b> not to be routed");
				}
			} else if (routing.equalsIgnoreCase("Nothing")) {
				lineItemsNotToRoute.add(lineItem);
				test_steps.add("Line Item <b>"+lineItem+"</b> not to be routed");
			}
		}
		lineItemsData.put("Routing Impacted Items", lineItemsToRoute);
		lineItemsData.put("Routing Not Impacted Items", lineItemsNotToRoute);
		return lineItemsData;
	}
		
	public void clickOnAccountFolio(WebDriver driver, ArrayList<String> test_steps, String accountName) {
		driver.findElement(By.xpath("//a[contains(text(),'"+accountName+"') and contains(@data-bind,'handleSelectFolio')]")).click();
		waitForFolioToLoad(driver);
		test_steps.add("Clicked on Account Folio option");
		folioLogger.info("Clicked on Account Folio option");
	}
	
	public void applyRouting(WebDriver driver, ArrayList<String> test_steps, String routingOption) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_DROPDOWN), driver);
		elements.GUEST_FOLIO_APPLY_ROUTING_DROPDOWN.click();
		test_steps.add("Clicked on Apply Routing drop down");
		folioLogger.info("Clicked on Apply Routing drop down");
		if (routingOption.equalsIgnoreCase("Selected Charges Only") || routingOption.equalsIgnoreCase("Selected")) {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_SELECTEDCHARGES), driver);
			elements.GUEST_FOLIO_APPLY_ROUTING_SELECTEDCHARGES.click();
			test_steps.add("Selected <b>Selected Charges Only</b> option from Apply Routing drop down");
			folioLogger.info("Selected <b>Selected Charges Only</b> option from Apply Routing drop down");
		} else if (routingOption.equalsIgnoreCase("All Applicable Charges") || routingOption.equalsIgnoreCase("All")) {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_ALLCHARGES), driver);
			elements.GUEST_FOLIO_APPLY_ROUTING_ALLCHARGES.click();			
			test_steps.add("Selected <b>All Applicable Charges</b> option from Apply Routing drop down");
			folioLogger.info("Selected <b>All Applicable Charges</b> option from Apply Routing drop down");
		}
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_POPUP_HEADER), driver, 30);
			elements.GUEST_FOLIO_APPLY_ROUTING_POPUP_PROCEED_BUTTON.click();
			test_steps.add("Clicked on Proceed button from confirmation popup");
			folioLogger.info("Clicked on Proceed button from confirmation popup");
			Wait.waitForElementToBeVisibile(By.xpath("//div[@class='toast-message']"), driver);
			System.out.println(driver.findElement(By.xpath("//div[@class='toast-message']")).getText());
		} catch (Exception e) {
			System.out.println(e);
			folioLogger.info(e);
		}
		
		
	}

	public void verifyLineItemsDisplayed(WebDriver driver, ArrayList<String> test_steps, 
			ArrayList<String> lineItems, boolean isDisplayed) {	
		List<WebElement> dates = driver.findElements(By.xpath("//td[@class='lineitem-date']//span"));	
		for (String lineItem : lineItems) {
			boolean lineItemFound = false;
			for (int i = 0; i < dates.size(); i++) {
				int j = i+1;
				String status = driver.findElement(By.xpath("(//td[@class='lineitem-status']//span)["+j+"]")).getText();
				String date = driver.findElement(By.xpath("(//td[@class='lineitem-date']//span)["+j+"]")).getText();
				String category = driver.findElement(By.xpath("(//td[@class='lineitem-category']//span)["+j+"]")).getText();
				String description = driver.findElement(By.xpath("(//td[@class='lineitem-description pr']//a)["+j+"]")).getText();
				String quantity = driver.findElement(By.xpath("(//td[@class='lineitem-qty']//span)["+j+"]")).getText();
				String amount = driver.findElement(By.xpath("(//td[@class='lineitem-amount']//span)["+j+"]")).getText().replace(TestCore.propertyCurrency, "").trim();
				String tax = driver.findElement(By.xpath("(//td[@class='lineitem-tax ']//span)["+j+"]")).getText().replace(TestCore.propertyCurrency, "").trim();
				String total = driver.findElement(By.xpath("(//td[@class='lineitem-total']//span)["+j+"]")).getText().replace(TestCore.propertyCurrency, "").trim();		
				String lineItemDisplayed = category+" for the day "+date+" with amount "+amount+", description : "+description
						+", quantity : "+quantity+", tax : "+tax+", total : "+total+" and status is "+status;
//				String  = status+" "+date+" "+category+" "+description+" "+quantity+" "+amount+" "+tax+" "+total; 
				if (lineItem.equalsIgnoreCase(lineItemDisplayed)) {
					lineItemFound = true;
					break;
				}
			}
			if (isDisplayed) {				
				try {
					assertEquals(lineItemFound, true, lineItem+" is not displayed");
					test_steps.add("Successfully verified line item as <b>"+lineItem+"</b>");
					folioLogger.info("Successfully verified line item as <b>"+lineItem+"</b>");
				} catch (Exception e) {
					test_steps.add(e.toString());
					folioLogger.info(e);
				} catch (Error e) {
					test_steps.add(e.toString());
					folioLogger.info(e);
				}			
			} else {				
				try {
					assertEquals(lineItemFound, false, lineItem+" is displayed");
					test_steps.add("Successfully verified line item is not displayed <b>"+lineItem+"</b>");
					folioLogger.info("Successfully verified line item is not displayed <b>"+lineItem+"</b>");
				} catch (Exception e) {
					test_steps.add(e.toString());
					folioLogger.info(e);
				} catch (Error e) {
					test_steps.add(e.toString());
					folioLogger.info(e);
				}			
			}			
		}
		
	}

	public HashMap<String,ArrayList<String>> selectLineItemsForManualRouting(WebDriver driver, ArrayList<String> test_steps, 
			String routing, String category, String date, String amount) {
//		amount = "10.00";
		date = Utility.parseDate(date, "dd/MM/yyyy", "d MMM, yyyy");
		ArrayList<String> lineItemsToRoute = new ArrayList<>();
		ArrayList<String> lineItemsNotToRoute = new ArrayList<>();
		HashMap<String, ArrayList<String>> lineItemsData = new HashMap<>();
		List<WebElement> dates = driver.findElements(By.xpath("//td[@class='lineitem-date']//span"));
		boolean lineItemFound = false;
		for (int i = 0; i < dates.size(); i++) {
			int j = i+1;
			String statusDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-status']//span)["+j+"]")).getText();
			String dateDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-date']//span)["+j+"]")).getText();
			String categoryDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-category']//span)["+j+"]")).getText();
			String descriptionDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-description pr']//a)["+j+"]")).getText();
			String quantityDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-qty']//span)["+j+"]")).getText();
			String amountDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-amount']//span)["+j+"]")).getText().replace(TestCore.propertyCurrency, "").trim();
			String taxDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-tax ']//span)["+j+"]")).getText().replace(TestCore.propertyCurrency, "").trim();
			String totalDisplayed = driver.findElement(By.xpath("(//td[@class='lineitem-total']//span)["+j+"]")).getText().replace(TestCore.propertyCurrency, "").trim();		
			String lineItem = categoryDisplayed+" for the day "+dateDisplayed+" with amount "+amountDisplayed+", description : "+descriptionDisplayed
					+", quantity : "+quantityDisplayed+", tax : "+taxDisplayed+", total : "+totalDisplayed+" and status is "+statusDisplayed;
			if (routing.equalsIgnoreCase("All Items")) {
				if (category.equalsIgnoreCase(categoryDisplayed) && date.equalsIgnoreCase(dateDisplayed) && amount.equalsIgnoreCase(amountDisplayed)) {
					lineItemsToRoute.add(lineItem);
					test_steps.add("Line Item <b>"+lineItem+"</b> can be routed");	
					try {
						driver.findElement(By.xpath("(//input[contains(@data-bind,'selectLineItem')])["+j+"]")).click();						
					} catch (Exception e) {
						driver.findElement(By.xpath("(//input[contains(@data-bind,'selectLineItem')])["+j+"]/../span")).click();						
					}
					lineItemFound = true;
				}else {
					lineItemsNotToRoute.add(lineItem);
					test_steps.add("Line Item <b>"+lineItem+"</b> not to be routed");					
				}
			} else if (routing.equalsIgnoreCase("Room Charges Only")) {
				if (category.equalsIgnoreCase(categoryDisplayed) && date.equalsIgnoreCase(dateDisplayed) && amount.equalsIgnoreCase(amountDisplayed)) {					
					if (category.equalsIgnoreCase("Room Charge")) {
						lineItemsToRoute.add(lineItem);
						test_steps.add("Line Item <b>"+lineItem+"</b> can be routed");	
						driver.findElement(By.xpath("(//input[contains(@data-bind,'selectLineItem')])["+j+"]")).click();
						lineItemFound = true;
					} else {
						lineItemsNotToRoute.add(lineItem);
						test_steps.add("Line Item <b>"+lineItem+"</b> cannot be routed");
					}					
				} else {
					lineItemsNotToRoute.add(lineItem);
					test_steps.add("Line Item <b>"+lineItem+"</b> not to be routed");
				}
			} else if (routing.equalsIgnoreCase("Nothing")) {
				lineItemsNotToRoute.add(lineItem);
				test_steps.add("Line Item <b>"+lineItem+"</b> cannot be routed");
			}
		}
		try {
			assertEquals(lineItemFound, true, "Line item cannobe routed or cannot found");
		} catch (Exception e) {
			// TODO: handle exception
		} catch (Error e) {
			// TODO: handle exception
		}
		lineItemsData.put("Routing Impacted Items", lineItemsToRoute);
		lineItemsData.put("Routing Not Impacted Items", lineItemsNotToRoute);
		return lineItemsData;
	}
	
	public HashMap<String, String> takePaymentFromGuestFolio(WebDriver driver, ArrayList<String> test_steps, String includePayment) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		ReservationV2 reservationPage = new ReservationV2();
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.guestFolioPayButton), driver);
		elements.guestFolioPayButton.click();
		folioLogger.info("Clicked on pay button");
		test_steps.add("Clicked on pay button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.takePaymentAmountInput), driver);
		Wait.wait5Second();
		data.put("Amount", elements.takePaymentAmountInput.getText());
		
		data.put("Payment Type", elements.takePaymentPaymentMethod.getAttribute("title"));
		test_steps.add("Details captured while payment is : <b>"+data+"</b>");
		if (includePayment.equalsIgnoreCase("Capture")) {
			elements.takePaymentTypeButton.click();
			String xpath = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//button/following-sibling::div//span[text()='Capture']/..";
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			driver.findElement(By.xpath(xpath)).click();
			data.put("Transaction Type", elements.takePaymentTypeButton.getAttribute("title"));
			folioLogger.info(data);
		} else if (includePayment.equalsIgnoreCase("Authorization")) {
			elements.takePaymentTypeButton.click();
			String xpath = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//button/following-sibling::div//span[text()='Authorization Only']/..";
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			driver.findElement(By.xpath(xpath)).click();
			data.put("Transaction Type", elements.takePaymentTypeButton.getAttribute("title"));
			folioLogger.info(data);
		}
		reservationPage.clickPayTakePayment(driver, test_steps);
		reservationPage.closePaymentSuccessfullPopup(driver, test_steps);
		Wait.wait2Second();
		return data;
	}

	public HashMap<String, String> refundPaymentFromGuestDetailsTab(WebDriver driver, ArrayList<String> test_steps, String amount) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.guestDetailsRefundButton), driver);
		elements.guestDetailsRefundButton.click();
		folioLogger.info("Clicked on Refund button");
		test_steps.add("Clicked on Refund button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.refundPopupHeader), driver);
		Wait.wait5Second();
		elements.refundPopupAmount.clear();
		elements.refundPopupAmount.sendKeys(amount);
		data.put("Amount", amount);
		String refundDate="//input[contains(@data-bind,'isRefundDateEnable')]";
		Wait.WaitForElement(driver, refundDate);
		driver.findElement(By.xpath(refundDate)).click();
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.refundPopupRefundButton), driver);		
		elements.refundPopupRefundButton.click();
		folioLogger.info("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
		test_steps.add("Provided Refund Amount as : <b>"+amount+"</b> and Clicked on Refund button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
		String toastMessage = elements.TOASTER_MESSAGE.getText();
		if (toastMessage.contains("Refund payment of amount") && toastMessage.contains("Guest Folio folio is success")) {
			folioLogger.info("Refund is successful");
			test_steps.add("Refund is successful");			
		} else {
			folioLogger.info("Refund is not successful");
			test_steps.add("Refund is not successful");						
		}
		return data;
	}
	

	public RefundInfo getRefundInfo(WebDriver driver) throws InterruptedException 
	{
		RefundInfo refundInfo;
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.wait3Second();
		refundInfo = new RefundInfo(
				Utility.getELementText(ele.refundPopupHeader, false, ""),
				Utility.getELementText(ele.refundPopupAmount, false, ""), 
				Utility.getELementText(ele.REFUND_PAYMENT_METHOD, false, ""), 
				Utility.getELementText(ele.REFUND_ADD_NOTES_BUTTON, false, ""), 
				Utility.getELementText(ele.REFUND_NOTES, false, "")
				);
		return refundInfo;
	}
	
		
		
	//new
	public HashMap<String, String> takePaymentFromGuestFolio_RV2(WebDriver driver, ArrayList<String> test_steps, String includePayment,String amount,String paymentType,String cardNumber,String nameOnCard ) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		ReservationV2 reservationPage = new ReservationV2();
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.guestFolioPayButton), driver);
		elements.guestFolioPayButton.click();
		folioLogger.info("Clicked on pay button");
		test_steps.add("Clicked on pay button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.takePaymentAmountInput), driver);
		Wait.wait5Second();
		data.put("Amount", elements.takePaymentAmountInput.getText());
		data.put("Transaction Type", elements.takePaymentTypeButton.getAttribute("title"));
		data.put("Payment Type", elements.takePaymentPaymentMethod.getAttribute("title"));
		test_steps.add("Details captured while payment is : <b>"+data+"</b>");

		if(Utility.validateString(amount)) {
		Utility.ScrollToElement(elements.takePaymentAmountInput, driver);
		elements.takePaymentAmountInput.click();
		elements.takePaymentAmountInput.clear();
		elements.takePaymentAmountInput.clear();
		elements.takePaymentAmountInput.sendKeys(amount);
		elements.takePaymentAmountInput.sendKeys(Keys.TAB);
		folioLogger.info("Entered amount : "+amount);
		test_steps.add("Entered amount : "+amount);
		}
		

		if (paymentType.trim().equalsIgnoreCase("Gift Certificate")) {
			try
			{

			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD), driver);
			elements.TAKE_PAYMENT_PAYMENT_METHOD.click();
			String Option = "(//span[contains(text(),'" + paymentType + "')])[last()]";
			Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
			driver.findElement(By.xpath(Option)).click();
			folioLogger.info("Selected Payment Method "+paymentType );
			test_steps.add("Selected Payment Method "+paymentType);
			}
			catch(Exception e)
			{
			Wait.WaitForElement(driver, OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD);
			Utility.ScrollToElement(elements.TAKE_PAYMENT_PAYMENT_METHOD, driver);
			Wait.wait2Second();
			elements.TAKE_PAYMENT_PAYMENT_METHOD.click();
			String Option = "(//span[contains(text(),'" + paymentType+ "')])[last()]";
			Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
			driver.findElement(By.xpath(Option)).click();
			folioLogger.info("Selected Payment Method "+ paymentType );
			test_steps.add("Selected Payment Method "+ paymentType);
			}
		elements.CP_NewReservation_GiftCertNumber2.sendKeys(cardNumber);
		test_steps.add("Enter Gift Card Number : " + cardNumber);
		Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../.."), driver);
		driver.findElement(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../..")).click();

		}else if (paymentType.trim().equalsIgnoreCase("MC") || paymentType.trim().equalsIgnoreCase("Visa")
		|| paymentType.trim().equalsIgnoreCase("Amex") || paymentType.trim().equalsIgnoreCase("Discover")
		|| paymentType.trim().equalsIgnoreCase("Masterrr") ) {

		String cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
		try
		{

		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD), driver);
		elements.TAKE_PAYMENT_PAYMENT_METHOD.click();
		String Option = "(//span[contains(text(),'" + paymentType + "')])[last()]";
		Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
		driver.findElement(By.xpath(Option)).click();
		folioLogger.info("Selected Payment Method "+paymentType );
		test_steps.add("Selected Payment Method "+paymentType);
		}
		catch(Exception e)
		{
		Wait.WaitForElement(driver, OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD);
		Utility.ScrollToElement(elements.TAKE_PAYMENT_PAYMENT_METHOD, driver);
		Wait.wait2Second();
		elements.TAKE_PAYMENT_PAYMENT_METHOD.click();
		String Option = "(//span[contains(text(),'" + paymentType+ "')])[last()]";
		Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
		driver.findElement(By.xpath(Option)).click();
		folioLogger.info("Selected Payment Method "+ paymentType );
		test_steps.add("Selected Payment Method "+ paymentType);
		}
		elements.CARD_NUMBER.click();
		elements.CARD_NUMBER.clear();
		elements.CARD_NUMBER.sendKeys(cardNumber);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.NAME_ON_CARD), driver);
		test_steps.add("Enter CardNumber : " + cardNumber);
		folioLogger.info("Enter CardNumber : " + cardNumber);
		elements.NAME_ON_CARD.click();
		elements.NAME_ON_CARD.clear();
		elements.NAME_ON_CARD.sendKeys(nameOnCard);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.EXPIRY_DATE), driver);
		test_steps.add("Enter Name On Card : " + nameOnCard);
		folioLogger.info("Enter Name On Card : " + nameOnCard);
		elements.EXPIRY_DATE.click();
		elements.EXPIRY_DATE.clear();
		elements.EXPIRY_DATE.sendKeys(cardExpDate);
		elements.EXPIRY_DATE.sendKeys(Keys.TAB);
		test_steps.add("Enter Card ExpDate : " + cardExpDate);
		folioLogger.info("Enter Card ExpDate : " + cardExpDate);



		if (includePayment.contains("Capture")) {
		elements.takePaymentTypeButton.click();
		String xpath = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//button/following-sibling::div//span[text()='Capture']/..";
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		driver.findElement(By.xpath(xpath)).click();
		test_steps.add("Payment Type : " + includePayment);
		folioLogger.info("Payment Type : " + includePayment);




		} else if (includePayment.contains("Authorization")) {
		elements.takePaymentTypeButton.click();
		String xpath = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//button/following-sibling::div//span[text()='Authorization Only']/..";
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		driver.findElement(By.xpath(xpath)).click();
		test_steps.add("Payment Type : " + includePayment);
		folioLogger.info("Payment Type : " + includePayment);



		}



		}

		reservationPage.clickPayTakePayment(driver, test_steps);
		//Wait.wait10Second();
		reservationPage.closePaymentSuccessfullPopup(driver, test_steps);
		return data;


	}

	//end
	//Added By Riddhi
	//Verify Refund Popup Elements
	public void verifyRefundInfo(RefundInfo actualRefundDetail,
			String expectedRefundPopupHeader , boolean verifyRefundHeader, boolean continueExeRefundHeader,
			String expectedRefundPopupAmount , boolean verifyRefundPopupAmount, boolean continueExeRefundPopupAmount,
			String expectedREFUND_PAYMENT_METHOD , boolean verifyREFUND_PAYMENT_METHOD, boolean continueExeREFUND_PAYMENT_METHOD,
			String expectedREFUND_ADD_NOTES_BUTTON, boolean verifyREFUND_ADD_NOTES_BUTTON, boolean continueExeREFUND_ADD_NOTES_BUTTON,
			String expectedREFUND_NOTES , boolean verifyREFUND_NOTES, boolean continueExeREFUND_NOTES,ArrayList<String> test_steps) {
		
		Utility.customAssert(actualRefundDetail.getrefundPopupHeader(), expectedRefundPopupHeader, 
				verifyRefundHeader,"Successfully Verified Refund Popup Header : " + expectedRefundPopupHeader,
				"Failed To Verify Refund Popup Header" ,continueExeRefundHeader, test_steps);
		
		Utility.customAssert(actualRefundDetail.getrefundPopupAmount(), expectedRefundPopupAmount, 
				verifyRefundHeader,"Successfully Verified Refund Popup Refund Amount : " + expectedRefundPopupAmount,
				"Failed To Verify Refund Popup Refund Amount" ,continueExeRefundPopupAmount, test_steps);
		
		Utility.customAssert(actualRefundDetail.getREFUND_PAYMENT_METHOD(), expectedREFUND_PAYMENT_METHOD, 
				verifyREFUND_PAYMENT_METHOD,"Successfully Verified Refund Payment Method Drop Down : " + expectedREFUND_PAYMENT_METHOD,
				"Failed To Verify Refund Payment Method Drop Down" ,continueExeREFUND_PAYMENT_METHOD, test_steps);
		
		Utility.customAssert(actualRefundDetail.getREFUND_ADD_NOTES_BUTTON(), expectedREFUND_ADD_NOTES_BUTTON, 
				verifyREFUND_ADD_NOTES_BUTTON,"Successfully Verified Refund Add Notes Button : " + expectedREFUND_ADD_NOTES_BUTTON,
				"Failed To Verify Refund Add Notes Button" ,continueExeREFUND_ADD_NOTES_BUTTON, test_steps);
		
		Utility.customAssert(actualRefundDetail.getREFUND_NOTES(), expectedREFUND_NOTES, 
				verifyREFUND_NOTES,"Successfully Verified Refund Notes Text : " + expectedREFUND_NOTES,
				"Failed To Verify Refund Notes" ,continueExeREFUND_NOTES, test_steps);
	}
	
	//Added By Riddhi
	//perform refund with notes
	public void refundPaymentFromResDetailsTabwithRefundNotes(WebDriver driver, ArrayList<String> test_steps, 
			String refundAmount, boolean isRefundNotes, String refundNotes) throws InterruptedException 
	{
		//HashMap<String, String> data = new HashMap<>();
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.guestDetailsRefundButton), driver);
		elements.guestDetailsRefundButton.click();
		folioLogger.info("Clicked on Refund button");
		test_steps.add("Clicked on Refund button");
		
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.refundPopupHeader), driver);
		Wait.wait5Second();
		
		//Verify Refund Popup Displayed or not
		
		Utility.customAssert(elements.refundPopupHeader.isDisplayed()+"", true+"", true, "Successfully Verified Refund Popup Header", "Failed To Verify Refund Popup", true, test_steps);
		Utility.customAssert(elements.refundPopupAmount.isDisplayed()+"", true+"", true, "Successfully Verified Refund Amount Field", "Failed To Verify Refund Popup Amount Field", true, test_steps);
		
		elements.refundPopupAmount.clear();
		elements.refundPopupAmount.sendKeys(refundAmount);
		//data.put("Amount", refundAmount);
		
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.REFUND_PAYMENT_METHOD), driver);
		Utility.customAssert(elements.REFUND_PAYMENT_METHOD.isDisplayed()+"", true+"", true, "Successfully Verified Refund Popup Payment Dropdown", "Failed To Verify Refund Popup Payment Dropdown", true, test_steps);
		Utility.customAssert(elements.REFUND_ADD_NOTES_BUTTON.isDisplayed()+"", true+"", true, "Successfully Verified Refund - Add Notes Button", "Failed To Verify Refund Popup - Add Notes Button", true, test_steps);
		
		if(isRefundNotes)
		{
			folioLogger.info("======================Clicking on Add Notes Button in Refund Popup======================");
			test_steps.add("======================Clicking on Add Notes Button in Refund Popup======================");
			
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.REFUND_ADD_NOTES_BUTTON),driver);
			elements.REFUND_ADD_NOTES_BUTTON.click();
			
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.REFUND_NOTES), driver);
			Utility.customAssert(elements.REFUND_NOTES.isDisplayed()+"", true+"", true, "Successfully Verified Refund - Notes Text", "Failed To Verify Refund Popup - Notes Text", true, test_steps);
			
			elements.REFUND_NOTES.clear();
			elements.REFUND_NOTES.sendKeys(refundNotes);
			//data.put(refundNotes, refundNotes);
		}
		
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.refundPopupRefundButton), driver);		
		elements.refundPopupRefundButton.click();
		
		folioLogger.info("Provided Refund Amount as : <b>"+refundAmount+"</b> and Clicked on Refund button");
		test_steps.add("Provided Refund Amount as : <b>"+refundAmount+"</b> and Clicked on Refund button");
		
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
		String toastMessage = elements.TOASTER_MESSAGE.getText();
		
		if (toastMessage.contains("Refund payment of amount") && toastMessage.contains("Guest Folio folio is success")) {
			folioLogger.info("Refund is successful");
			test_steps.add("Refund is successful");			
		} else {
			folioLogger.info("Refund is not successful");
			test_steps.add("Refund is not successful");						
		}
		
	}

	
	public void cancelPaymentPopUp(WebDriver driver, ArrayList<String> test_steps,String PaymentTransactionType) throws Exception {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

	
		String PAYMENT_LINE_ITEM ="//table[contains(@class,'table-lineItems')]//span[contains(text(),'"+PaymentTransactionType+"')]/../following-sibling::td/a[contains(@data-bind,'description')]";
		Wait.WaitForElement(driver, PAYMENT_LINE_ITEM);
		driver.findElement(By.xpath(PAYMENT_LINE_ITEM)).click();
		Wait.wait5Second();
		String dropDownProcess ="//button[contains(@data-bind,'processPayment')]/../preceding-sibling::td[1]/div";
		Wait.WaitForElement(driver, dropDownProcess);
		driver.findElement(By.xpath(dropDownProcess)).click();
		
		String cancelBtn ="//button[contains(@data-bind,'processPayment')]/../preceding-sibling::td[1]/div//span[text()='Cancel']";
		Wait.WaitForElement(driver, cancelBtn);
		driver.findElement(By.xpath(cancelBtn)).click();

		String addNoteBtn="//button[contains(@data-bind,'processPayment')]/../preceding-sibling::td[4]";
		Wait.WaitForElement(driver, addNoteBtn);
		driver.findElement(By.xpath(addNoteBtn)).click();
		String addNotes=null;
		String addNotesBtn=null;
		if(PaymentTransactionType=="Authoriz")
		{
			 addNotes="(//span[text()='Add Notes']/../../preceding-sibling::div[1]//textarea[contains(@placeholder,'Enter Notes')])[last()]";
			 addNotesBtn="(//span[text()='Add Notes']/..)[last()]";
		}else if(PaymentTransactionType=="Capture")
		{
			 addNotes="(//span[text()='Add Notes']/../../preceding-sibling::div[1]//textarea[contains(@placeholder,'Enter Notes')])[last()-1]";
			 addNotesBtn="(//span[text()='Add Notes']/..)[last()-1]";
		}
		
		Wait.WaitForElement(driver, addNotes);
		driver.findElement(By.xpath(addNotes)).sendKeys("Updated notes");
		
		Wait.WaitForElement(driver, addNotesBtn);
		driver.findElement(By.xpath(addNotesBtn)).click();
		String processButton="//button[contains(@data-bind,'processPayment')]";
		Wait.WaitForElement(driver, processButton);
		driver.findElement(By.xpath(processButton)).click();
		String closeButton="(//button[text()='Close'])[last()]";
		Wait.WaitForElement(driver, closeButton);
		driver.findElement(By.xpath(closeButton)).click();
		folioLogger.info("Navigated to guest folio");
		test_steps.add("Navigated to guest folio");
	}
	
	public HashMap<String, String> takePaymentFromGuestFolio_Failed(WebDriver driver, ArrayList<String> test_steps, String includePayment) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		ReservationV2 reservationPage = new ReservationV2();
		try
		{
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.guestFolioPayButton), driver);
		elements.guestFolioPayButton.click();
		folioLogger.info("Clicked on pay button");
		test_steps.add("Clicked on pay button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.takePaymentAmountInput), driver);
		Wait.wait5Second();
		data.put("Amount", elements.takePaymentAmountInput.getText());
		
		data.put("Payment Type", elements.takePaymentPaymentMethod.getAttribute("title"));
		test_steps.add("Details captured while payment is : <b>"+data+"</b>");
		if (includePayment.equalsIgnoreCase("Capture")) {
			elements.takePaymentTypeButton.click();
			String xpath = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//button/following-sibling::div//span[text()='Capture']/..";
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			driver.findElement(By.xpath(xpath)).click();
			data.put("Transaction Type", elements.takePaymentTypeButton.getAttribute("title"));
			folioLogger.info(data);
		} else if (includePayment.equalsIgnoreCase("Authorization")) {
			elements.takePaymentTypeButton.click();
			String xpath = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//button/following-sibling::div//span[text()='Authorization Only']/..";
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			driver.findElement(By.xpath(xpath)).click();
			data.put("Transaction Type", elements.takePaymentTypeButton.getAttribute("title"));
			folioLogger.info(data);
		}
		reservationPage.clickPayTakePayment(driver, test_steps);
		reservationPage.closePaymentSuccessfullPopup(driver, test_steps);	
		}
		catch (Exception e) {
			folioLogger.info("transactionfailed");
			String closeBtn="//h4[text()='Take Payment']/../button[contains(@data-bind,'click:PaymentCloseButtonClick')]";
			Wait.WaitForElement(driver, closeBtn);
			driver.findElement(By.xpath(closeBtn)).click();
			Wait.wait3Second();
		}
		return data;
	}


	//Added By Riddhi - Method to verify Single Tab/Multiple Tabs on Refund Popup Based on Payment Gateway
	public void verifyTabsonRefundPopupBasedOnAddedGateway(WebDriver driver, ArrayList<String> test_steps, 
			String paymentGateway) throws InterruptedException 
	{
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.guestDetailsRefundButton), driver);
		elements.guestDetailsRefundButton.click();
		folioLogger.info("Clicked on Refund button");
		test_steps.add("Clicked on Refund button");
		
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.refundPopupHeader), driver);
		Wait.wait5Second();
		
		//Verify Refund Popup Displayed or not
		Utility.customAssert(elements.refundPopupHeader.isDisplayed()+"", true+"", true, "Successfully Verified Refund Popup Header", "Failed To Verify Refund Popup", true, test_steps);
		
		if( !paymentGateway.equalsIgnoreCase("Payment Express"))
		{
			Utility.customAssert(elements.refundPopupAmount.isDisplayed()+"", true+"", true, "Successfully Verified Refund Amount Field", "Failed To Verify Refund Popup Amount Field", true, test_steps);
			Utility.customAssert(elements.REFUND_PAYMENT_METHOD.isDisplayed()+"", true+"", true, 
					"Successfully Verified Refund Payment Method Field", "Failed to Verify Refund Payment Method Field", true, test_steps);
			Utility.customAssert(elements.REFUND_LOG_AS_EXTERNAL_CHK.isDisplayed()+"", true+"", true, 
					"Successfully Verified Log As External Payment checkbox Field", "Failed to Verify Log As External Payment Checkbox Field", true, test_steps);
			Utility.customAssert(elements.REFUND_LOG_AS_EXTERNAL_CHK.isDisplayed()+"", true+"", true, 
					"Successfully Verified Set As Main Method checkbox field", "Failed to Verify Set As Main Method Checkbox Field", true, test_steps);
			Utility.customAssert(elements.REFUND_ADD_NOTES_BUTTON.isDisplayed()+"", true+"", true, 
					"Successfully Verified Refund - Add Notes Button", "Failed To Verify Refund Popup - Add Notes Button", true, test_steps);
		}
		try
		{
			Utility.customAssert(elements.REFUND_PE_pathRefundSpecificTab.isDisplayed()+"", true+"", true, 
					"Refund Popup Has Multiple Tab For Payment Gateway - "+paymentGateway, "Refund Popup has Single Tab", true, test_steps);
		}
		catch(Exception e)
		{
			assertTrue(true);
			folioLogger.info("<b>Refund Popup Has Single Tab For Payment Gateway - "+paymentGateway+"</b>");
			test_steps.add("<b>Refund Popup Has Single Tab For Payment Gateway - "+paymentGateway+"</b>");
		} 
	}
	//Added By Riddhi - Close Refund Popup
	public void closeRefundPopup(WebDriver driver, ArrayList<String> test_steps)
	{
		try
		{
			Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.REFUND_CLOSE_ICON), driver);
			folioLogger.info("Closing Refund Popup");
			test_steps.add("Closing Refund Popup");
			elements.REFUND_CLOSE_ICON.click();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
