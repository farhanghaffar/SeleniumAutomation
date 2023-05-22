package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;

public class Elements_NightAudit {
	

	WebDriver driver ;


	public static Logger app_logs = Logger.getLogger("devpinoyLogger");
		
		public Elements_NightAudit(WebDriver driver2)
		{
			this.driver=driver2;
			PageFactory.initElements(this.driver, this);
			
			 
		}

		@FindBy(id=OR.Go_ButtonClick)
		public WebElement Go_ButtonClick;
		
		
		@FindBy(xpath=OR.AuditDatePicker)
		public WebElement AuditDatePicker;
		
		@FindBy(id=OR.AuditDate)
		public WebElement AuditDate;
		
		
		@FindBy(xpath=OR.ClickToday)
		public WebElement ClickToday;
		
		@FindBy(id=OR.SetNowButton)
		public WebElement SetNowButton;
		
		@FindBy(xpath=OR.UpdateRoomCondition_Popup)
		public WebElement UpdateRoomCondition_Popup;
		
		@FindBy(xpath=OR.UpdateRoomCondition_Yes)
		public WebElement UpdateRoomCondition_Yes;
		
		@FindBy ( xpath = OR.HouseKeepingCount)
		public WebElement HouseKeepingCount;
		
		@FindBy(id=OR.HouseKeepingText)
		public WebElement HouseKeepingText;
		
		@FindBy(id=OR.DailyTransactionButton)
		public WebElement DailyTransactionButton;
		
		@FindBy(id=OR.DailyTransactionPage)
		public WebElement DailyTransactionPage;
		
		@FindBy(id=OR.AllItemStatus)
		public WebElement AllItemStatus;
		
		@FindBy(id=OR.DailyTransactionPage_GoBtn)
		public WebElement DailyTransactionPage_GoBtn;
		
		@FindBy(id=OR.DailyTransactionPage_PostBtn)
		public WebElement DailyTransactionPage_PostBtn;
		
		@FindBy(id=OR.DialyTrans_CheckBoxAll)
		public WebElement DialyTrans_CheckBoxAll;
		
		@FindBy(id=OR.ItemToPost)
		public WebElement ItemToPost;
		
		@FindBy(id=OR.PeriodIsOpenButton)
		public WebElement PeriodIsOpenButton;
		
		@FindBy(id=OR.PeriodIsOpen_LockButton)
		public WebElement PeriodIsOpen_LockButton;
		
		@FindBy(id=OR.PeriodIsOpen_CancelButton)
		public WebElement PeriodIsOpen_CancelButton;
		
		@FindBy(xpath=OR.PeriodStatusPage)
		public WebElement PeriodStatusPage;
		
		@FindBy(id=OR.VerifyLongStay)
		public WebElement VerifyLongStay;
		
		@FindBy(id=OR.LongStay_SetNowBtn)
		public WebElement LongStay_SetNowBtn;
		
		@FindBy(xpath=OR.LongStay_Popup)
		public WebElement LongStay_Popup;
		
		@FindBy(xpath=OR.LongStay_YesButton)
		public WebElement LongStay_YesButton;
		
		@FindBy(xpath=OR.LongStayMessage)
		public WebElement LongStayMessage;
		
		@FindBy(xpath=OR.LongStay_NoButton)
		public WebElement LongStay_NoButtons;

		@FindBy(xpath = OR.folio_Tab)
		public WebElement folio_Tab;
	
		@FindBy(xpath = OR.SelectCurrentDated_PeriodLock)
		public WebElement SelectCurrentDated_PeriodLock;
		@FindBy(xpath = OR.SelectPriorDated_PeriodLock)
		public WebElement SelectPriorDated_PeriodLock;
		@FindBy(xpath = OR.SelectPriorDatedClick_PeriodLock)
		public WebElement SelectPriorDatedClick_PeriodLock;
		@FindBy(xpath = OR.SelectVoidDated_PeriodLock)
		public WebElement SelectVoidDated_PeriodLock;
		@FindBy(xpath = OR.SelectVoidDateClick_PeriodLock)
		public WebElement SelectVoidDateClick_PeriodLock;

		@FindBy(xpath = OR.checkout_ClosePopupButton)
		public WebElement checkout_ClosePopupButton;
		
		@FindBy(xpath = OR.dailyTransactions)
		public WebElement dailyTransactions;
		
		@FindBy(id = OR.priorItems)
		public WebElement priorItems;

		@FindBy(id = OR.currentItems)
		public WebElement currentItems;

		@FindBy(id = OR.voidItems)
		public WebElement voidItems;
		
		@FindBy(id = OR.noRecordFoundMessage)
		public WebElement noRecordFoundMessage;

		@FindBy(id = OR.postButton)
		public WebElement postButton;
		
		@FindBy(xpath = OR.periodStatus)
		public WebElement periodStatus;
		
		@FindBy(id = OR.totalPriorItemsFound)
		public WebElement totalPriorItemsFound;
		
		@FindBy(xpath = OR.periodLockMsg)
		public List<WebElement> periodLockMsg;
		
		@FindBy(xpath = OR.lockButton)
		public WebElement lockButton;
		
		@FindBy(id = OR.lockPeriodFrame)
		public WebElement lockPeriodFrame;
		
		@FindBy(id = OR.checkAllPriorItems)
		public WebElement checkAllPriorItems;

		@FindBy(id = OR.checkAllCurrentItems)
		public WebElement checkAllCurrentItems;
		
		@FindBy(id = OR.checkAllVoidItems)
		public WebElement checkAllVoidItems;

		@FindBy(xpath=OR.PeriodStatusButton)
		public WebElement PeriodStatusButton;
		

		@FindBy(xpath = OR.checkoutButton)
		public WebElement checkoutButton;
		
		@FindBy(xpath = OR.proceedtocheckoutpayment)
		public WebElement proceedtocheckoutpayment;
		
		@FindBy(xpath = OR.selectPaymentMethod_Checkout)
		public WebElement selectPaymentMethod_Checkout;
		
		@FindBy(xpath = OR.selectCardNumber_Checkout)
		public WebElement selectCardNumber_Checkout;
		
		@FindBy(xpath = OR.selectCardHolderName_Checkout)
		public WebElement selectCardHolderName_Checkout;
		
		@FindBy(xpath = OR.selectExpiryDate_Checkout)
		public WebElement selectExpiryDate_Checkout;
		
		@FindBy(xpath = OR.proceedToExternalPayment_Checkout)
		public WebElement proceedToExternalPayment_Checkout;
		
		@FindBy(xpath = OR.payNow_Checkout)
		public WebElement payNow_Checkout;
			
		@FindBy(xpath = OR.paymentMethod_Checkout)
		public WebElement paymentMethod_Checkout;
		
		@FindBy(xpath = OR.reservationBalance)
		public WebElement reservationBalance;
		
		@FindBy(xpath = OR.closeIframe)
		public WebElement closeIframe;
		
		@FindBy(xpath = OR.printIcon)
		public WebElement printIcon;
		
		@FindBy(xpath = OR.weekDay)
		public List<WebElement> weekDay;

		@FindBy(id=OR.houseKeeping)
		public WebElement houseKeeping;

		@FindBy(id=OR.houseKeepingSetNow)
		public WebElement houseKeepingSetNow;

		@FindBy(id=OR.longStay)
		public WebElement longStay;

		@FindBy(id=OR.longStaySetNow)
		public WebElement longStaySetNow;
		
		@FindBy(xpath = OR.aduitDateLabel)
		public WebElement aduitDateLabel;
}