//Interface for Login

package com.innroad.inncenter.interfaces;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public interface INightAudit {	

	
	ArrayList<String> EnterAuditDate(WebDriver driver,String Date) throws InterruptedException, ParseException;
	void GoButtonClick(WebDriver driver) throws InterruptedException;
	void VerifyHouseKeepingSection(WebDriver driver) throws InterruptedException;
	void EnterAuditDate(WebDriver driver) throws InterruptedException;
	void DailyTransactionButtonClick(WebDriver driver) throws InterruptedException;
	void SelectAllItemStatus(WebDriver driver) throws InterruptedException;
	void DailyTransaction_PostButtonClick(WebDriver driver) throws InterruptedException;
	void PeriodIsOpenButtonClick(WebDriver driver) throws InterruptedException;
	String GetHouseKeepingCount(WebDriver driver) throws InterruptedException;



	
}
