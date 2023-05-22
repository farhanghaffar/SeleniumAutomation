package com.innroad.inncenter.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class Excel_Writer {
	
	public void excelCreater() throws IOException {
		//creating an instance of Workbook class   
		Workbook wb = new HSSFWorkbook();   
		//creates an excel file at the specified location  
		OutputStream fileOut = new FileOutputStream("C:\\demo\\BankStatement.xlsx");   
		System.out.println("Excel File has been created successfully.");   
		wb.write(fileOut);   
		
	}
	
	

}
