package com.innroad.inncenter.model;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.waits.Wait;

public class FolioLineItem {

	private String ITEM_STATUS;
	private String ITEM_DATE;
	private String ITEM_CATEGORY;
	private String ITEM_DESCRIPTION;
	private String ITEM_QTY;
	private String ITEM_AMOUNT;
	private String ITEM_TAX;
	private String ITEM_TOTAL;
	
	

	@Override
	public String toString() {
		
		return "Folio Line Item : "
				+ "\n Item Status : " + ITEM_STATUS
				+ "\n Item Date : " + ITEM_DATE
				+ "\n Item Category : "  + ITEM_CATEGORY
				+ "\n Item Desc : " + ITEM_DESCRIPTION
				+ "\n Item QTY : " + ITEM_QTY
				+ "\n Item Amount : " + ITEM_AMOUNT
				+ "\n Item Tax : " + ITEM_TAX
				+ "\n Item Total : " + ITEM_TOTAL;
	}

	public FolioLineItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FolioLineItem(String iTEM_STATUS, String iTEM_DATE, String iTEM_CATEGORY, String iTEM_DESCRIPTION,
			String iTEM_QTY, String iTEM_AMOUNT, String iTEM_TAX, String iTEM_TOTAL) {
		super();
		ITEM_STATUS = iTEM_STATUS;
		ITEM_DATE = iTEM_DATE;
		ITEM_CATEGORY = iTEM_CATEGORY;
		ITEM_DESCRIPTION = iTEM_DESCRIPTION;
		ITEM_QTY = iTEM_QTY;
		ITEM_AMOUNT = iTEM_AMOUNT;
		ITEM_TAX = iTEM_TAX;
		ITEM_TOTAL = iTEM_TOTAL;
	}

	public String getITEM_STATUS() {
		return ITEM_STATUS;
	}

	public void setITEM_STATUS(String iTEM_STATUS) {
		ITEM_STATUS = iTEM_STATUS;
	}

	public String getITEM_DATE() {
		return ITEM_DATE;
	}

	public void setITEM_DATE(String iTEM_DATE) {
		ITEM_DATE = iTEM_DATE;
	}

	public String getITEM_CATEGORY() {
		return ITEM_CATEGORY;
	}

	public void setITEM_CATEGORY(String iTEM_CATEGORY) {
		ITEM_CATEGORY = iTEM_CATEGORY;
	}

	public String getITEM_DESCRIPTION() {
		return ITEM_DESCRIPTION;
	}

	public void setITEM_DESCRIPTION(String iTEM_DESCRIPTION) {
		ITEM_DESCRIPTION = iTEM_DESCRIPTION;
	}

	public String getITEM_QTY() {
		return ITEM_QTY;
	}

	public void setITEM_QTY(String iTEM_QTY) {
		ITEM_QTY = iTEM_QTY;
	}

	public String getITEM_AMOUNT() {
		return ITEM_AMOUNT;
	}

	public void setITEM_AMOUNT(String iTEM_AMOUNT) {
		ITEM_AMOUNT = iTEM_AMOUNT;
	}

	public String getITEM_TAX() {
		return ITEM_TAX;
	}

	public void setITEM_TAX(String iTEM_TAX) {
		ITEM_TAX = iTEM_TAX;
	}

	public String getITEM_TOTAL() {
		return ITEM_TOTAL;
	}

	public void setITEM_TOTAL(String iTEM_TOTAL) {
		ITEM_TOTAL = iTEM_TOTAL;
	}

	
	
	
	
}
