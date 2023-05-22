package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reports;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;
import com.innroad.inncenter.webelements.Elements_Reports;
import com.innroad.inncenter.webelements.Elements_SetUp_Properties;
import com.innroad.inncenter.webelements.Elements_Users;
import com.relevantcodes.extentreports.ExtentTest;

import freemarker.template.utility.DateUtil.CalendarFieldsToDateConverter;

public class ReportsV2 {

	public static Logger reportLogger = Logger.getLogger("ReportsV2");

	// public static Logger reportLogger = Logger.getLogger("ReportsV2");

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToReports> Description: <This method
	 * navigates to ReportsV2 page> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <07/24/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void navigateToReports(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			jse.executeScript("arguments[0].click();", res.ReportsIcon);
		} catch (Exception e) {
			jse.executeScript("arguments[0].click();", res.Reports);
		}
		Wait.explicit_wait_xpath(OR_Reports.BrowseAllReports, driver);
		reportLogger.info("Navigated to Reports");
		test_steps.add("Navigated to Reports");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <clickHelpButton> Description: <This method
	 * clicks on Help button in ReportsV2 page> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <07/24/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void clickHelpButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.Help);
		Utility.clickThroughJavaScript(driver, res.Help);
		Utility.switchTab(driver, 1);
		Wait.WaitForElement(driver, OR_Reports.ReportsHelpPageHeader);
		reportLogger.info("validated Help button");
		test_steps.add("validated Help button");
		// driver.close();
		// Utility.switchTab(driver, 0);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePerformanceFilter> Description: <This
	 * method validates Performance filter in Reports-V2 page as per options should
	 * be available> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <07/24/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePerformanceFilter(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.explicit_wait_elementToBeClickable(res.PerformanceFilter, driver);
		Utility.clickThroughAction(driver, res.PerformanceFilter);

		boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.PerformanceReportsHeading));
		boolean condition2 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.DailyFlashReport));
		boolean condition3 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.NetSalesReport));
		boolean condition4 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.RoomForecastReport));
		boolean condition5 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.AccountingReportsHeading));
		boolean condition6 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.AdvanceDepositReport));
		boolean condition7 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.AccountBalancesReport));
		boolean condition8 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.LedgerBalancesReport));
		boolean condition9 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Incidentals));
		boolean condition10 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.PaymentMethod));
		boolean condition11 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Taxes));
		boolean condition12 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.TransactionsReport));

		if (condition1 && condition2 && condition3 && condition4 && !condition5 && !condition6 && !condition7
				&& !condition8 && !condition9 && !condition10 && !condition11 && !condition12) {
			reportLogger.info("Sucess - Validaion of Performance filter");
			test_steps.add("Success - Validaion of Performance filter");
		} else {
			Assert.assertTrue(false, "Fail - Validaion of Performance filter");
		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAccountingFilter> Description: <This
	 * method validates Accounting filter in Reports-V2 page as per options should
	 * be available> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <07/24/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateAccountingFilter(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.explicit_wait_elementToBeClickable(res.AccountingFilter, driver);
		Utility.clickThroughAction(driver, res.AccountingFilter);

		boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.PerformanceReportsHeading));
		boolean condition2 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.DailyFlashReport));
		boolean condition3 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.NetSalesReport));
		boolean condition4 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.RoomForecastReport));
		boolean condition5 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.AccountingReportsHeading));
		boolean condition6 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.AdvanceDepositReport));
		boolean condition7 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.AccountBalancesReport));
		boolean condition8 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.LedgerBalancesReport));
		boolean condition9 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Incidentals));
		boolean condition10 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.PaymentMethod));
		boolean condition11 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Taxes));
		boolean condition12 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.TransactionsReport));

		if (!condition1 && !condition2 && !condition3 && !condition4 && condition5 && condition6 && condition7
				&& condition8 && condition9 && condition10 && condition11 && condition12) {

			reportLogger.info("Success - Validaion of Accounting filter");
			test_steps.add("Sucess - Validaion of Accounting filter");
		} else {
			Assert.assertTrue(false, "Fail - Validaion of Accounting filter");
		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToLedgerBalancesReport> Description:
	 * <This method navigates to Ledger Balances Report> Input parameters:
	 * <WebDriver driver> Return value: <void> Created By: <Naveen Kadthala> Created
	 * On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void navigateToLedgerBalancesReport(WebDriver driver) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.explicit_wait_visibilityof_webelement_120(res.LedgerBalancesReport, driver);
		try {
			Wait.wait1Second();
			res.LedgerBalancesReport.click();
			Wait.wait1Second();
			Utility.switchTab(driver, 1);
		}catch(Exception e) {
			Wait.wait1Second();
			Utility.clickThroughAction(driver, res.LedgerBalancesReport);
			Wait.wait1Second();
			Utility.switchTab(driver, 1);
		}
		Wait.WaitForElement(driver, OR_Reports.LedgerBalancesReportHeader);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToDailyFlashReport> Description: <This
	 * method navigates to Daily Flash Report> Input parameters: <WebDriver driver>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void navigateToDailyFlashReport(WebDriver driver) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		res.DailyFlashReport.click();
		Utility.switchTab(driver, 1);
		Wait.WaitForElement(driver, OR_Reports.DailyFlashReportHeader);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToNetSalesReport> Description: <This
	 * method navigates to Daily Net Sales Report> Input parameters: <WebDriver
	 * driver> Return value: <void> Created By: <Naveen Kadthala> Created
	 * On:<07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void navigateToNetSalesReport(WebDriver driver) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		res.NetSalesReport.click();
		Utility.switchTab(driver, 1);
		Wait.WaitForElement(driver, OR_Reports.NetSalesReportHeader);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToRoomForecast> Description: <This
	 * method navigates to Room Forecast Report> Input parameters: <WebDriver
	 * driver> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void navigateToRoomForecastReport(WebDriver driver) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		res.RoomForecastReport.click();
		Utility.switchTab(driver, 1);
		Wait.WaitForElement(driver, OR_Reports.RoomForecastReportHeader);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToAdvanceDepositReport> Description:
	 * <This method navigates to Advance Deposit Report> Input parameters:
	 * <WebDriver driver> Return value: <void> Created By: <Naveen Kadthala> Created
	 * On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void navigateToAdvanceDepositReport(WebDriver driver) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		res.AdvanceDepositReport.click();
		Utility.switchTab(driver, 1);
		Wait.WaitForElement(driver, OR_Reports.AdvanceDepositReportHeader);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToAccountBalancesReport> Description:
	 * <This method navigates to Account Balances Report> Input parameters:
	 * <WebDriver driver> Return value: <void> Created By: <Naveen Kadthala> Created
	 * On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void navigateToAccountBalancesReport(WebDriver driver) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		res.AccountBalancesReport.click();
		Utility.switchTab(driver, 1);
		Wait.WaitForElement(driver, OR_Reports.AccountBalancesReportHeader);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <navigateToTransactionsReport> Description:
	 * <This method navigates to Transactions Report> Input parameters: <WebDriver
	 * driver> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void navigateToTransactionsReport(WebDriver driver) throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		res.TransactionsReport.click();
		Utility.switchTab(driver, 1);
		Wait.WaitForElement(driver, OR_Reports.TransactionsReportHeader);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateInRoadImageinLedgerBalanceReport>
	 * Description: <This method validates InnRoad image in Ledger Balance Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateInRoadImageinLedgerBalanceReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.InnRoadImage);
		boolean condition = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.InnRoadImage));
		if (condition) {
			reportLogger.info("Success - Validation of InnRoad Image");
			test_steps.add("Success - Validation of InnRoad Image");
		} else
			Assert.assertTrue(false, "Fail - Validation of InnRoad Image");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateTooltipOfInnRoadImage> Description:
	 * <This method validates tool tip of InnRoad's image in Ledger Balance Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateTooltipOfInnRoadImage(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.InnRoadImage);
		if ((res.InnRoadImage.getAttribute("title")).equals("innRoad Reports")) {
			reportLogger.info("Success - validation of InnRoad image tooltip");
			test_steps.add("Success - validation of InnRoad image tooltip");
		} else
			Assert.assertTrue(false, "Fail - Validation of InnRoad Image tooltip");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateMainHeaderinLedgerBalanceReport>
	 * Description: <This method validates Main Header in Ledger Balance Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateMainHeaderinLedgerBalanceReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.LedgerBalancesReportMainHeader);
		boolean condition = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.LedgerBalancesReportMainHeader));
		if (condition) {
			reportLogger.info("Success - Validation of Ledger Balances Report's Main Header");
			test_steps.add("Success - Validation of Ledger Balances Report's Main Header");
		} else
			Assert.assertTrue(false, "Fail - Validation of Ledger Balances Report's Main Header");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateMainHeaderToolTipinLedgerBalanceReport> Description: <This method
	 * validates Main Header's tool tip in Ledger Balance Report> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateMainHeaderToolTipinLedgerBalanceReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.LedgerBalancesReportMainHeader);
		if ((res.LedgerBalancesReportMainHeader.getAttribute("title")).equals("Report name")) {
			reportLogger.info("Success - validation of Tool tip of Main Header in Ledger Balances Report");
			test_steps.add("Success - validation of Tool tip of Main Header in Ledger Balances Report");
		} else
			Assert.assertTrue(false, "Fail - validation of Tool tip of Main Header in Ledger Balances Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateCollapseFunctionalityinLedgerBalancesReport> Description: <This
	 * method validates collapse functionality in Ledger Balance Report> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateCollapseFunctionalityinLedgerBalancesReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		while (!Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Collapse)))
			res.Edit.click();
		Wait.explicit_wait_elementToBeClickable(res.Collapse, driver);
		Utility.clickThroughAction(driver, res.Collapse);
		boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.ChoseDateRange));
		boolean condition2 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.SelectInputs));
		boolean condition3 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.CustomizeDetailedView));

		if (!condition1 && !condition2 && !condition3) {

			reportLogger.info("Success - Validaion of Collapse functionlity");
			test_steps.add("Sucess - Validaion of Collapse functionlity");
		} else {
			Assert.assertTrue(false, "Fail - Validaion of Collapse functionlity");
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateEditFunctionalityinLedgerBalancesReport> Description: <This method
	 * validates Edit functionality in Ledger Balance Report> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <07/27/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateEditFunctionalityinLedgerBalancesReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.explicit_wait_elementToBeClickable(res.Edit, driver);
		Utility.clickThroughAction(driver, res.Edit);
		Wait.WaitForElement(driver, OR_Reports.AdvancedInputs);
		boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.ChoseDateRange));
		boolean condition2 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.SelectInputs));
		boolean condition3 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.CustomizeDetailedView));
		if (condition1 && condition2 && condition3) {
			reportLogger.info("Success - Validaion of Edit functionlity");
			test_steps.add("Sucess - Validaion of Edit functionlity");
		} else {
			Assert.assertTrue(false, "Fail - Validaion of Edit functionlity");
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateExcelExportIsDisabledBeforeRunReport>
	 * Description: <This method validates if Excel export is disabled before run
	 * report> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateExcelExportIsDisabledBeforeRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.ExcelExport);
		String s = driver.findElement(By.xpath("//span[text()='Excel']/..")).getAttribute("disabled");
		if (s.equals("true")) {
			reportLogger.info("Success - Excel Export disable validation before Run Report");
			test_steps.add("Sucess - Excel Export disable validation before Run Report");
		} else
			Assert.assertTrue(false, "Fail - Excel Export is disable validation before Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePDFExportIsDisabledBeforeRunReport>
	 * Description: <This method validates if PDF export is disabled before run
	 * report> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePDFExportIsDisabledBeforeRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.PDFExport);
		String s = driver.findElement(By.xpath("//span[text()='PDF']/..")).getAttribute("disabled");
		if (s.equals("true")) {
			reportLogger.info("Success - PDF Export disable validation before Run Report");
			test_steps.add("Sucess - PDF Export disable validation before Run Report");
		} else
			Assert.assertTrue(false, "Fail - PDF Export is disable validation before Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePrinttIsDisabledBeforeRunReport>
	 * Description: <This method validates if Print is disabled before run report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePrinttIsDisabledBeforeRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.Print);
		String s = driver.findElement(By.xpath("//span[text()='Print']/..")).getAttribute("disabled");
		if (s.equals("true")) {
			reportLogger.info("Success - Print disable validation before Run Report");
			test_steps.add("Sucess - Print disable validation before Run Report");
		} else
			Assert.assertTrue(false, "Fail - Print is disable validation before Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateExcelExportToolTipBeforeRunReport>
	 * Description: <This method validates Excel tool tip before run report> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateExcelExportToolTipBeforeRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.ExcelExport);
		Utility.hoverOnElement(driver, res.ExcelExport);
		Wait.WaitForElement(driver, OR_Reports.ExcelExportToolTipBeforeRunReport);
		if ((res.ExcelExportToolTipBeforeRunReport.getText()).equals("Please click \"Run Report\" before exporting.")) {
			reportLogger.info("Success - Excel Export tooltip validation before run report");
			test_steps.add("Success - Excel Export tooltip validation before run report");
		} else
			Assert.assertTrue(false, "Fail - Excel Export tooltip validation before run report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePDFExportToolTipBeforeRunReport>
	 * Description: <This method validates PDF Export tool tip before run report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePDFExportToolTipBeforeRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.PDFExport);
		Utility.hoverOnElement(driver, res.PDFExport);
		Wait.WaitForElement(driver, OR_Reports.PDFExportToolTipBeforeRunReport);
		if ((res.PDFExportToolTipBeforeRunReport.getText()).equals("Please click \"Run Report\" before exporting.")) {
			reportLogger.info("Success - PDF Export tooltip validation before run report");
			test_steps.add("Sucess - PDF Export tooltip validation before run report");
		} else
			Assert.assertTrue(false, "Fail - PDF Export tooltip validation before run report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePrintToolTipBeforeRunReport>
	 * Description: <This method validates Print tool tip before run report> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePrintToolTipBeforeRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.PDFExport);
		Utility.hoverOnElement(driver, res.PDFExport);
		Wait.WaitForElement(driver, OR_Reports.PDFExportToolTipBeforeRunReport);
		if ((res.PDFExportToolTipBeforeRunReport.getText()).equals("Please click \"Run Report\" before exporting.")) {
			reportLogger.info("Success - Print tooltip validation before Run Report");
			test_steps.add("Sucess - Print tooltip  validation before Run Report");
		} else
			Assert.assertTrue(false, "Fail - PDF Export disable validation before Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <clickOnRunReport> Description: <This method
	 * validates clicks on run report> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void clickOnRunReport(WebDriver driver) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.explicit_wait_elementToBeClickable(res.RunReport, driver);
		Utility.clickThroughAction(driver, res.RunReport);
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateExcelExportIsEnabledBAfterRunReport>
	 * Description: <This method validates if Excel Export is enabled after Run
	 * Report> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateExcelExportIsEnabledAfterRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.ExcelExport);
		String s = driver.findElement(By.xpath("//span[text()='Excel']/..")).getAttribute("disabled");
		if (s == null) {
			reportLogger.info("Success - Excel Export validation after Run Report");
			test_steps.add("Success - Excel Export validation after Run Report");
		} else
			Assert.assertTrue(false, "Fail - Excel Export validation after Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePDFExportIsEnabledAfterRunReport>
	 * Description: <This method validates if PDF Export is enabled after Run
	 * Report> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePDFExportIsEnabledAfterRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.PDFExport);
		String s = driver.findElement(By.xpath("//span[text()='PDF']/..")).getAttribute("disabled");
		if (s == null) {
			reportLogger.info("Success - PDF Export validation after Run Report");
			test_steps.add("Success - PDF Export validation after Run Report");
		} else
			Assert.assertTrue(false, "Fail - PDF Export validation after Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePrinttIsEnabledAfterRunReport>
	 * Description: <This method validates if Print is enabled after Run Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/28/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePrinttIsEnabledAfterRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.Print);
		String s = driver.findElement(By.xpath("//span[text()='Print']/..")).getAttribute("disabled");
		if (s == null) {
			reportLogger.info("Success - Print validation after Run Report");
			test_steps.add("Success - Print validation after Run Report");
		} else
			Assert.assertTrue(false, "Fail - Print validation after Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateExcelExportToolTipAfterRunReport>
	 * Description: <This method validates Excel export tool tip after Run Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/29/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateExcelExportToolTipAfterRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.ExcelExport);
		String s = driver.findElement(By.xpath("//span[text()='Excel']/..")).getAttribute("title");
		if (s.equals("Export report to Excel")) {
			reportLogger.info("Success - Excel tool tip validation after Run Report");
			test_steps.add("Sucess - Excel tool tip validation after Run Report");
		} else
			Assert.assertTrue(false, "Fail - Excel tool tip validation after Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePDFExportToolTipAfterRunReport>
	 * Description: <This method validates PDF export tool tip after Run Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/29/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePDFExportToolTipAfterRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.PDFExport);
		String s = driver.findElement(By.xpath("//span[text()='PDF']/..")).getAttribute("title");
		if (s.equals("Export report to PDF")) {
			reportLogger.info("Success - PDF tool tip validation after Run Report");
			test_steps.add("Sucess - PDF tool tip validation after Run Report");
		} else
			Assert.assertTrue(false, "Fail - PDF tool tip validation after Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validatePrintToolTipAfterRunReport>
	 * Description: <This method validates Print export tool tip after Run Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <07/29/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validatePrintToolTipAfterRunReport(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.Print);
		String s = driver.findElement(By.xpath("//span[text()='Print']/..")).getAttribute("title");
		if (s.equals("Print report")) {
			reportLogger.info("Success - Print tool tip validation after Run Report");
			test_steps.add("Sucess - Print tool tip validation after Run Report");
		} else
			Assert.assertTrue(false, "Fail - Print tool tip validation after Run Report");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateSortReportbyToolTip> Description:
	 * <This method validates Sort Report By tool tip> Input parameters: <WebDriver
	 * driver, ArrayList<String> test_steps> Return value: <void> Created By:
	 * <Naveen Kadthala> Created On: <07/29/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateSortReportbyToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.SortReportBy);
		WebElement e = driver.findElement(By.xpath("//*[text()='Sort Report By']/../following-sibling::span"));
		Utility.hoverOnElement(driver, e);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Sort Report By')]"), driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Sort Report By')]"))
				.getText();
		String act2 = driver
				.findElement(By.xpath("//div[@class='ant-popover-inner-content']/p[contains(text(),'Sorts data in the')]"))
				.getText().replaceAll("[\\t\\n\\r]+", " ");
		String exp1 = "Sort Report By";
		String exp2 = "Sorts data in the �Detailed View� section of the report based on the option selected in the drop down.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Sort Report By tooltip validation");
			test_steps.add("Sucess - Sort Report By tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Sort Report By tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateSortReportbyOptions> Description:
	 * <This method validates Sort Report options> Input parameters: <WebDriver
	 * driver, ArrayList<String> test_steps> Return value: <void> Created By:
	 * <Naveen Kadthala> Created On: <07/29/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateSortReportbyOptions(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		String[] actOptions = { "Amount", "Date", "Guest/Account Name", "Item Description", "Processing Method",
				"Reservation Number", "Tax Exempt" };
		Utility.clickThroughAction(driver, res.SortReportByOptionsExpand);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='sortBy_list']/following-sibling::div/div/div/div/div"),
				driver);
		List<WebElement> expoptions = driver
				.findElements(By.xpath("//div[@id='sortBy_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < actOptions.length; i++) {
			Assert.assertEquals(actOptions[i], expoptions.get(i).getText(),
					"FAIL - Sort Report By drop down options validation" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-338'>"
							+ "Click here to open JIRA: RPT-338</a>");
		}
		Utility.clickThroughAction(driver, res.SortReportByOptionsExpand);
		reportLogger.info("Success - Sort Report By drop down options validation");
		test_steps.add("Sucess - Sort Report By drop down options validation" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/RPT-338'>" + "Click here to open JIRA: RPT-338</a>");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateGroupRowsbyToolTip> Description: <This
	 * method validates Group Rows by tool tip> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <07/29/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateGroupRowsbyToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.SortReportBy);
		WebElement e = driver.findElement(By.xpath("//*[text()='Group Rows By']/../following-sibling::span"));
		Utility.hoverOnElement(driver, e);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Group Rows By')]"), driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Group Rows By')]"))
				.getText();
		String act2 = driver.findElement(By
				.xpath("//div[@class='ant-popover-inner-content']/p[contains(text(),'Group rows by a specific time')]"))
				.getText().replaceAll("[\\t\\n\\r]+", " ");
		String exp1 = "Group Rows By";
		String exp2 = "Group rows by a specific time period in the �Detailed View� section of the report. For example, if �Day� is selected, then each row is a day. If �Month� is selected, each row is a Month.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Group Rows By tooltip validation");
			test_steps.add("Sucess - Group Rows By tooltip validation" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/RPT-308'>" + "Click here to open JIRA: RPT-308</a>"
					+ "<br>" + "<a href='https://innroad.atlassian.net/browse/RPT-535'>"
					+ "Click here to open JIRA: RPT-535</a>");
		} else
			Assert.assertTrue(false, "Fail - Group Rows By tooltip validation" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/RPT-308'>" + "Click here to open JIRA: RPT-308</a>"
					+ "<br>" + "<a href='https://innroad.atlassian.net/browse/RPT-535'>"
					+ "Click here to open JIRA: RPT-535</a>" + "<br>");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateGroupRowsbyOptions> Description: <This
	 * method validates Group Rows options> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <07/29/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateGroupRowsbyOptions(WebDriver driver, ArrayList<String> test_steps) {
		String[] actOptions = { "Transaction", "Day", "Month", "Year" };
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.GroupRowsByOptionsExpand);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='groupBy_list']/following-sibling::div/div/div/div/div"),
				driver);
		List<WebElement> expoptions = driver
				.findElements(By.xpath("//div[@id='groupBy_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < actOptions.length; i++) {
			Assert.assertEquals(actOptions[i], expoptions.get(i).getText(),
					"FAIL - Group Rows By drop down options validation");
		}
		Utility.clickThroughAction(driver, res.GroupRowsByOptionsExpand);
		reportLogger.info("Success - Group Rows By drop down options validation");
		test_steps.add("Sucess - Group Rows By drop down options validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAdvancedOptionsToolTip> Description:
	 * <This method validates Advanced Options tool tip> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/03/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateAdvancedOptionsToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_Reports.AdvancedInputs);
		WebElement e = driver.findElement(By.xpath("//*[text()='Advanced Inputs']/../../following-sibling::span"));
		Utility.hoverOnElement(driver, e);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Advanced Inputs')]"), driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Advanced Inputs')]"))
				.getText();
		String act2 = driver.findElement(By.xpath(
				"//div[@class='ant-popover-inner-content']/p[contains(text(),'to view the list of advanced inputs')]"))
				.getText();
		String exp1 = "Advanced Inputs";
		String exp2 = "Expand this section to view the list of advanced inputs you can choose from, to narrow down your search and filter the report data further.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Advanced Inputs tooltip validation");
			test_steps.add("Sucess - Advanced Inputs tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Advanced Options tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateExpandAllfunctionalityofAdvancedOptions> Description: <This method
	 * validates Expand All functionality of advanced options> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/03/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateExpandAllfunctionalityofAdvancedOptions(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.explicit_wait_elementToBeClickable(res.ExpandAll, driver);
		Utility.clickThroughAction(driver, res.ExpandAll);
		Wait.WaitForElement(driver, OR_Reports.AdvancedDeposits);
		boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Pending));
		boolean condition2 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Users));
		boolean condition3 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.ShiftTime));
		boolean condition4 = driver.findElement(By.xpath("//*[text()='Tax Exempt Ledger Items']/../../.."))
				.getAttribute("aria-expanded").equalsIgnoreCase("true");
		boolean condition5 = driver.findElement(By.xpath("//*[text()='Market Segment']/../../.."))
				.getAttribute("aria-expanded").equalsIgnoreCase("true");
		boolean condition6 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.OnHold));
		boolean condition7 = driver.findElement(By.xpath("//*[text()='Referrals']/../../.."))
				.getAttribute("aria-expanded").equalsIgnoreCase("true");
		if (condition1 && condition2 && condition3 && condition4 && condition5 && condition6 && condition7) {
			reportLogger.info("Success - Validaion of ExpandAll functionlity");
			test_steps.add("Sucess - Validaion of ExpandAll functionlity");
		} else {
			Assert.assertTrue(false, "Fail - Validaion of ExpandAll functionlity");
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateCollapseAllfunctionalityofAdvancedOptions> Description: <This method
	 * validates Expand All functionality of advanced options> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/03/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateCollapseAllfunctionalityofAdvancedOptions(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.explicit_wait_elementToBeClickable(res.CollapseAll, driver);
		Utility.clickThroughAction(driver, res.CollapseAll);
		Wait.WaitForElement(driver, OR_Reports.AdvancedDeposits);
		boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Pending));
		boolean condition2 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Users));
		boolean condition3 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Properties));
		boolean condition4 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.ShiftTime));
		boolean condition5 = driver.findElement(By.xpath("//*[text()='Tax Exempt Ledger Items']/../../.."))
				.getAttribute("aria-expanded").equalsIgnoreCase("true");
		boolean condition6 = driver.findElement(By.xpath("//*[text()='Market Segment']/../../.."))
				.getAttribute("aria-expanded").equalsIgnoreCase("true");
		boolean condition7 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.OnHold));
		boolean condition8 = driver.findElement(By.xpath("//*[text()='Referrals']/../../.."))
				.getAttribute("aria-expanded").equalsIgnoreCase("true");
		if (!condition1 && !condition2 && !condition3 && !condition4 && !condition5 && !condition6 && !condition7
				&& !condition8) {
			reportLogger.info("Success - Validaion of Collapse All functionlity");
			test_steps.add("Sucess - Validaion of Collapse All functionlity");
		} else {
			Assert.assertTrue(false, "Fail - Validaion of Collapse All functionlity");
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAccountTypeToolTip> Description: <This
	 * method validates Account Type tool tip> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateAccountTypeToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.AccountType);
		// WebElement e = driver.findElement(By.xpath("//*[text()='Advanced
		// Options']/../following-sibling::span"));
		Utility.hoverOnElement(driver, res.AccountTypeToolTipIcon);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Account Type')]"), driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Account Type')]"))
				.getText();
		String act2 = driver
				.findElement(By.xpath(
						"//div[@class='ant-popover-inner-content']/p[contains(text(),'one or more account types')]"))
				.getText();
		String exp1 = "Account Type";
		String exp2 = "Pull the ledger account data for one or more account types.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Account Type tooltip validation");
			test_steps.add("Sucess - Account Type tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Account Type tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateAccountTypeExpandAndCollapseFunctionality> Description: <This method
	 * validates Market Segment's Expand and Collapse functionality> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateAccountTypeExpandAndCollapseFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.AccountType);
		boolean condition1 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Account Type']/../..//div[text()='All']"));
		Utility.clickThroughAction(driver, res.AccountType);
		boolean condition2 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Account Type']/../..//div[text()='All']"));
		if (condition1 && !condition2) {
			reportLogger.info("Success - Account Type Expand and Collapse functionality");
			test_steps.add("Sucess - Account Type Expand and Collapse functionality");
		} else
			Assert.assertTrue(false, "Fail - Account Type Expand and Collapse functionality");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAccountTypeClearAllfuntionality>
	 * Description: <This method validates Account Type Clear All functionality>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateAccountTypeClearAllfuntionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.AdvancedDeposits), driver);
		Utility.clickThroughAction(driver, res.AccountTypeClearAll);
		List<WebElement> options = driver.findElements(By.xpath(OR_Reports.AccountTypesOptions));
		for (int i = 0; i < options.size(); i++) {
			Assert.assertEquals(options.get(i).getAttribute("class").contains("checked"), false,
					"FAIL - Account Type Clear All functionality validation");
		}
		reportLogger.info("Success - Account Type Clear All functionality validation");
		test_steps.add("Sucess - Account Type Clear All functionality validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAccountTypeSelectAllFunctionality>
	 * Description: <This method validates Account Type Select All functionality>
	 * Pre-requisites: Ledger Balances report should be opened,Account Type should
	 * be expanded and all options should be unchecked. Input parameters: <WebDriver
	 * driver, ArrayList<String> test_steps> Return value: <void> Created By:
	 * <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateAccountTypeSelectAllFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.AccountTypeSelectAll);
		List<WebElement> options = driver.findElements(By.xpath(OR_Reports.AccountTypesOptions));
		for (int i = 0; i < options.size(); i++) {
			Assert.assertEquals(options.get(i).getAttribute("class").contains("checked"), true,
					"FAIL - Account Type Select All functionality validation");
		}
		reportLogger.info("Success - Account Type Select All functionality validation");
		test_steps.add("Sucess - Account Type Select All functionality validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAccountTypeOptions> Description: <This
	 * method validates Account Type options> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <08/03/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateAccountTypeOptions(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		String[] actOptions = { "Advanced Deposits", "House Account", "Group", "Corporate/Member Accounts",
				"Reservations", "Unit Owners", "Gift Certificate", "Travel Agent" };
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.AdvancedDeposits), driver);
		List<WebElement> expoptions = driver.findElements(By.xpath(OR_Reports.AccountTypesOptions));
		for (int i = 0; i < actOptions.length; i++) {
			Assert.assertEquals(actOptions[i], expoptions.get(i).getText(),
					"FAIL - Account Types options validation" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-532'>"
							+ "Click here to open JIRA: RPT-532</a>");
		}
		reportLogger.info("Success - Account Types options validation");
		test_steps.add("Sucess - Account Types options validation" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/RPT-532'>" + "Click here to open JIRA: RPT-532</a>");

	}

	// Item
	// status===================================================================================================

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateItemStatusToolTip> Description: <This
	 * method validates Item Status tool tip> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateItemStatusToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.ItemStatus);
		Utility.hoverOnElement(driver, res.ItemStatusToolTipIcon);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Item Status')]"),
				driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Item Status')]")).getText();
		String act2 = driver
				.findElement(By.xpath(
						"//div[@class='ant-popover-inner-content']/p[contains(text(),'one or more item statuses')]"))
				.getText();
		String exp1 = "Item Status";
		String exp2 = "Pull the ledger account data for one or more item statuses.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Item Status tooltip validation");
			test_steps.add("Sucess - Item Status tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Item Status tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateItemStatusExpandAndCollapseFunctionality> Description: <This method
	 * validates Item Status's Expand and Collapse functionality> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateItemStatusExpandAndCollapseFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.ItemStatus);
		boolean condition1 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Item Status']/../..//div[text()='3/4']"));
		Utility.clickThroughAction(driver, res.ItemStatus);
		boolean condition2 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Item Status']/../..//div[text()='3/4']"));
		if (condition1 && !condition2) {
			reportLogger.info("Success - Item Status Expand and Collapse functionality");
			test_steps.add("Sucess - Item Status Expand and Collapse functionality");
		} else
			Assert.assertTrue(false, "Fail - Item Status Expand and Collapse functionality");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateItemStatusClearAllfuntionality>
	 * Description: <This method validates Item Status Clear All functionality>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateItemStatusClearAllfuntionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.Pending), driver);
		Utility.clickThroughAction(driver, res.ItemStatusClearAll);
		List<WebElement> options = driver.findElements(By.xpath(OR_Reports.ItemStatusOptions));
		for (int i = 0; i < options.size(); i++) {
			Assert.assertEquals(options.get(i).getAttribute("class").contains("checked"), false,
					"FAIL - Item Status Clear All functionality validation");
		}
		reportLogger.info("Success - Item Status Clear All functionality validation");
		test_steps.add("Sucess - Item Status clear All functionality validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateItemStatusSelectAllFunctionality>
	 * Description: <This method validates Item Status Select All functionality>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateItemStatusSelectAllFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.ItemStatusSelectAll);
		List<WebElement> options = driver.findElements(By.xpath(OR_Reports.ItemStatusOptions));
		for (int i = 0; i < options.size(); i++) {
			Assert.assertEquals(options.get(i).getAttribute("class").contains("checked"), true,
					"FAIL - Item Status select All functionality validation");
		}
		reportLogger.info("Success - Item Status select All functionality validation");
		test_steps.add("Sucess - Item Status select All functionality validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateItemStatusOptions> Description: <This
	 * method validates Item Status options> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <08/03/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateItemStatusOptions(WebDriver driver, ArrayList<String> test_steps) {
		String[] actOptions = { "Pending", "Locked", "Void", "Posted" };
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.Pending), driver);
		List<WebElement> expoptions = driver.findElements(By.xpath(OR_Reports.ItemStatusOptions));
		for (int i = 0; i < actOptions.length; i++) {
			Assert.assertEquals(actOptions[i], expoptions.get(i).getText(), "FAIL - Item Status options validation");
		}
		reportLogger.info("Success - Item Status options validation");
		test_steps.add("Sucess - Item Status options validation");

	}

	// Include Data Form
	// ===================================================================================================

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateIncludeDataFromToolTip> Description:
	 * <This method validates Include Data From tool tip> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateIncludeDataFromToolTip(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);

//		String strJavaScript = "var element = arguments[0]; var mouseEventObj = document.createEvent('MouseEvents'); mouseEventObj.initEvent( 'mouseover', true, true ); element.dispatchEvent(mouseEventObj);"; 
//		((JavascriptExecutor) driver).executeScript(strJavaScript, res.IncludeDataFromToolTipIcon); 

		res.IncludeDataFromToolTipIcon.click();

		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Include Data From')]"), driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Include Data From')]"))
				.getText().replaceAll("[\\t\\n\\r]+", " ");
		String act2 = driver
				.findElement(By.xpath("//div[@class='ant-popover-inner-content']/div/b[contains(text(),'Users')]"))
				.getText().replaceAll("[\\t\\n\\r]+", " ");
		String act3 = driver
				.findElement(
						By.xpath("//div[@class='ant-popover-inner-content']/div/p[contains(text(),'entitlement')]"))
				.getText().replaceAll("[\\t\\n\\r]+", " ");
		String act4 = driver
				.findElement(By.xpath("//div[@class='ant-popover-inner-content']//b[contains(text(),'Shift')]"))
				.getText();
		String act5 = driver
				.findElement(By.xpath("//div[@class='ant-popover-inner-content']//p[contains(text(),'slot')]"))
				.getText();

		String exp1 = "Include Data From";
		String exp2 = "Users";
		String exp3 = "Generate the report for other/ all users. In order to run this report as another user, you will need to have the entitlement called �Run Reports as other users� enabled.";
		String exp4 = "Shift Time";
		String exp5 = "Choose this option to run this report for a specific time slot for the date range selected.";

		if ((exp1.equals(act1)) && (exp2.equals(act2)) && (exp3.equals(act3)) && act4.equals(exp4)
				&& act5.equals(exp5)) {
			reportLogger.info("Success - Include Data Form tooltip validation");
			test_steps.add("Sucess - - Include Data Form tooltip validation" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/RPT-385'>"
					+ "Click here to open JIRA: RPT-385</a>");
		} else
			Assert.assertTrue(false,
					"Fail - - Include Data Form tooltip validation" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-385'>"
							+ "Click here to open JIRA: RPT-385</a>");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateIncludeDataFromExpandAndCollapseFunctionality> Description: <This
	 * method validates Market Segment's Expand and Collapse functionality> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateIncludeDataFromExpandAndCollapseFunctionality(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.IncludeDataFrom);
		boolean condition1 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Include Data From']/../..//div[text()='All Users | 00:00 to 00:00']"));

		Wait.wait3Second();
		Utility.clickThroughAction(driver, res.IncludeDataFrom);
		boolean condition2 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Include Data From']/../..//div[text()='All Users | 00:00 to 00:00']"));
		System.out.println("condition1=" + condition1);
		System.out.println("condition2=" + condition2);
		if (!condition1 && condition2) {
			reportLogger.info("Success - Include Data From Expand and Collapse functionality");
			test_steps.add("Sucess - Include Data From Expand and Collapse functionality");
		} else
			Assert.assertTrue(false, "Fail - Include Data From Expand and Collapse functionality");

	}

	// Tax Exempt Ledger Items
	// ===================================================================================================
	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateTaxExemptLedgerItemsToolTip>
	 * Description: <This method validates Tax Exempt Ledger Items tool tip> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateTaxExemptLedgerItemsToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.TaxExemptLedgerItems);
		Utility.hoverOnElement(driver, res.TaxExemptLedgerItemsToolTipIcon);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Tax Exempt Ledger Items')]"), driver);
		String act1 = driver
				.findElement(
						By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Tax Exempt Ledger Items')]"))
				.getText();
		String act2 = driver
				.findElement(By.xpath("//div[@class='ant-popover-inner-content']/p[contains(text(),'tax exempt')]"))
				.getText();
		String exp1 = "Tax Exempt Ledger Items";
		String exp2 = "Pull the report specific to the tax exempt setting on a reservation/ account.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Tax Exempt Ledger Items tooltip validation");
			test_steps.add("Sucess - Tax Exempt Ledger Items tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Tax Exempt Ledger Items tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateTaxExemptLedgerItemsExpandAndCollapseFunctionality> Description:
	 * <This method validates Tax Exempt Ledger Items Expand and Collapse
	 * functionality> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateTaxExemptLedgerItemsExpandAndCollapseFunctionality(WebDriver driver,
			ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.TaxExemptLedgerItems);
		boolean condition1 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Tax Exempt Ledger Items']/../..//div[text()='Tax Exempt & Taxed']"));
		Utility.clickThroughAction(driver, res.TaxExemptLedgerItems);
		boolean condition2 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Tax Exempt Ledger Items']/../..//div[text()='Tax Exempt & Taxed']"));
		System.out.println("condition1=" + condition1);
		System.out.println("condition2=" + condition2);
		if (condition1 && !condition2) {
			reportLogger.info("Success - Tax Exempt Ledger Items Expand and Collapse functionality");
			test_steps.add("Sucess - Tax Exempt Ledger Items Expand and Collapse functionality");
		} else
			Assert.assertTrue(false, "Fail - Tax Exempt Ledger Items Expand and Collapse functionality");

	}

	// Market Segment
	// ===================================================================================================
	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateIncludeDataFromToolTip> Description:
	 * <This method validates Include Data From tool tip> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateMarketSegmentToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.MarketSegment);
		Utility.hoverOnElement(driver, res.MarketSegmentToolTipIcon);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Market Segment')]"), driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Market Segment')]"))
				.getText();
		String act2 = driver
				.findElement(By.xpath(
						"//div[@class='ant-popover-inner-content']/p[contains(text(),'specific to a market segment')]"))
				.getText();
		String exp1 = "Market Segment";
		String exp2 = "Pull the report specific to a market segment.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Market Segment tooltip validation");
			test_steps.add("Sucess - Market Segment tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Market Segment tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateMarketSegmentExpandAndCollapseFunctionality> Description: <This
	 * method validates Market Segment's Expand and Collapse functionality> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateMarketSegmentExpandAndCollapseFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.MarketSegment);
		boolean condition1 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Market Segment']/../..//div[text()='All']"));
		Utility.clickThroughAction(driver, res.MarketSegment);
		boolean condition2 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Market Segment']/../..//div[text()='All']"));
		if (condition1 && !condition2) {
			reportLogger.info("Success - Market Segment Expand and Collapse functionality");
			test_steps.add("Sucess - Market Segment Expand and Collapse functionality");
		} else
			Assert.assertTrue(false, "Fail - Market Segment Expand and Collapse functionality");

	}

	// Reservation Status

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReservationStatusToolTip> Description:
	 * <This method validates Reservation Status tool tip> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateReservationStatusToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.ReservationStatus);
		Utility.hoverOnElement(driver, res.ReservationStatusToolTipIcon);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Reservation Status')]"), driver);
		String act1 = driver
				.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Reservation Status')]"))
				.getText();
		String act2 = driver.findElement(By.xpath(
				"//div[@class='ant-popover-inner-content']/p[contains(text(),'specific to a reservation status')]"))
				.getText();
		String exp1 = "Reservation Status";
		String exp2 = "Generate the report specific to a reservation status.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Reservation Status tooltip validation");
			test_steps.add("Sucess - Reservation Status tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Reservation Status tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateReservationStatusExpandAndCollapseFunctionality> Description: <This
	 * method validates Reservation Status Expand and Collapse functionality> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateReservationStatusExpandAndCollapseFunctionality(WebDriver driver,
			ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.ReservationStatus);
		boolean condition1 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Reservation Status']/../..//div[text()='All']"));
		Utility.clickThroughAction(driver, res.ReservationStatus);
		boolean condition2 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Reservation Status']/../..//div[text()='All']"));
		if (condition1 && !condition2) {
			reportLogger.info("Success - Reservation Status Expand and Collapse functionality");
			test_steps.add("Sucess - Reservation Status Expand and Collapse functionality");
		} else
			Assert.assertTrue(false, "Fail - Reservation Status Expand and Collapse functionality");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReservationStatusClearAllfuntionality>
	 * Description: <This method validates Reservation Status Clear All
	 * functionality> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReservationStatusClearAllfuntionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.ReservationStatus), driver);
		Utility.clickThroughAction(driver, res.ReservationStatusClearAll);
		List<WebElement> options = driver.findElements(By.xpath(OR_Reports.ReservationStatusOptions));
		for (int i = 0; i < options.size(); i++) {
			Assert.assertEquals(options.get(i).getAttribute("class").contains("checked"), false,
					"FAIL - Reservation Status Clear All functionality validation");
		}
		reportLogger.info("Success - Reservation Status Clear All functionality validation");
		test_steps.add("Sucess - Reservation Status Clear All functionality validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateReservationStatusSelectAllFunctionality> Description: <This method
	 * validates Reservation Status Select All functionality> Pre-requisites: Ledger
	 * Balances report should be opened,Account Type should be expanded and all
	 * options should be unchecked. Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateReservationStatusSelectAllFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.ReservationStatusSelectAll);
		List<WebElement> options = driver.findElements(By.xpath(OR_Reports.ReservationStatusOptions));
		for (int i = 0; i < options.size(); i++) {
			Assert.assertEquals(options.get(i).getAttribute("class").contains("checked"), true,
					"FAIL - Reservation Status Select All functionality validation");
		}
		reportLogger.info("Success - Reservation Status Select All functionality validation");
		test_steps.add("Sucess - Reservation Status Select All functionality validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReservationStatusOptions> Description:
	 * <This method validates Reservation Status options> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/03/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateReservationStatusOptions(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		String[] actOptions = { "On Hold", "In-House", "Group Blocked", "No Show", "Reserved", "Departed", "Cancelled",
				"Confirmed", "Quote", "Guaranteed" };
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.ReservationStatus), driver);
		List<WebElement> expoptions = driver.findElements(By.xpath(OR_Reports.ReservationStatusOptions));
		for (int i = 0; i < actOptions.length; i++) {
			Assert.assertEquals(actOptions[i], expoptions.get(i).getText(),
					"FAIL - Reservation Status options validation");
		}
		reportLogger.info("Success - Reservation Status options validation");
		test_steps.add("Sucess - Reservation Status options validation");

	}

	// Referrals
	// ===================================================================================================
	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReferralsToolTip> Description: <This
	 * method validates Referrals From tool tip> Input parameters: <WebDriver
	 * driver, ArrayList<String> test_steps> Return value: <void> Created By:
	 * <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateReferralsToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.Referrals);
		Utility.hoverOnElement(driver, res.ReferralsToolTipIcon);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Referrals')]"),
				driver);
		String act1 = driver.findElement(By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Referrals')]"))
				.getText();
		String act2 = driver
				.findElement(By.xpath("//div[@class='ant-popover-inner-content']/p[contains(text(),'referral type')]"))
				.getText();
		String exp1 = "Referrals";
		String exp2 = "Generate the report specific to a referral type.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Referrals tooltip validation");
			test_steps.add("Sucess - Referrals tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Referrals tooltip validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateReferralsExpandAndCollapseFunctionality> Description: <This method
	 * validates Referrals Expand and Collapse functionality> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/04/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateReferralsExpandAndCollapseFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.Referrals);
		boolean condition1 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Referrals']/../..//div[text()='All']"));
		Utility.clickThroughAction(driver, res.Referrals);
		boolean condition2 = Utility.isElementPresent(driver,
				By.xpath("//*[text()='Referrals']/../..//div[text()='All']"));
		if (condition1 && !condition2) {
			reportLogger.info("Success - Referrals Expand and Collapse functionality");
			test_steps.add("Sucess - Referrals Expand and Collapse functionality");
		} else
			Assert.assertTrue(false, "Fail - Referrals Expand and Collapse functionality");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <ValidateSelectionOfGivenSortReportByOption>
	 * Description: <This method selects given sort report by option> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps, String
	 * sortreportbyoption> Return value: <void> Created By: <Naveen Kadthala>
	 * Created On: <08/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void ValidateSelectionOfGivenSortReportByOption(WebDriver driver, ArrayList<String> test_steps, String s)
			throws InterruptedException {
		int flag = 0;
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.SortReportByOptionsExpand);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='sortBy_list']/following-sibling::div/div/div/div/div"),
				driver);
		List<WebElement> options = driver
				.findElements(By.xpath("//div[@id='sortBy_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < options.size(); i++) {
			if (s.equalsIgnoreCase(options.get(i).getText())) {
				Utility.clickThroughAction(driver, options.get(i));
				reportLogger.info("Success - Selection of given Sort By Option");
				test_steps.add("Sucess - Selection of given Sort By Option");
				flag = 1;
				break;

			}

		}
		if (flag == 0)
			Assert.assertTrue(false, "Fail - Selection of given Sort By Option");

	}

	public void selectGivenSortReportByOption(WebDriver driver, String s, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.SortReportByOptionsExpand);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='sortBy_list']/following-sibling::div/div/div/div/div"),
				driver);
		List<WebElement> options = driver
				.findElements(By.xpath("//div[@id='sortBy_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < options.size(); i++) {
			if (s.equalsIgnoreCase(options.get(i).getText())) {
				Utility.clickThroughAction(driver, options.get(i));
				reportLogger.info("Success - Selection of given Sort By Option");
				// test_steps.add("Sucess - Selection of given Sort By Option");
				break;

			}
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <ValidateSelectionOfGivenGroupRowsByOption>
	 * Description: <This method selects given group rows by option> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps, String
	 * grouprowsbyoption> Return value: <void> Created By: <Naveen Kadthala> Created
	 * On: <08/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void ValidateSelectionOfGivenGroupRowsByOption(WebDriver driver, ArrayList<String> test_steps, String s)
			throws InterruptedException {
		int flag = 0;
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.GroupRowsByOptionsExpand);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='groupBy_list']/following-sibling::div/div/div/div/div"),
				driver);
		List<WebElement> options = driver
				.findElements(By.xpath("//div[@id='groupBy_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < options.size(); i++) {
			if (s.equalsIgnoreCase(options.get(i).getText())) {
				Utility.clickThroughAction(driver, options.get(i));
				reportLogger.info("Success - Selection of given Group Rows By Option");
				test_steps.add("Sucess - Selection of given Group Rows Option");
				flag = 1;
				break;
			}

		}
		if (flag == 0)
			Assert.assertTrue(false, "Fail - Selection of given Group Rows By Option");

	}

	// New Sprint 13
	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <clickExcelInLedgerBalancesReport> Description:
	 * <This method clicks on Excel in Ledger Balances Report> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void clickExcelInLedgerBalancesReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.ExcelExport);
		reportLogger.info("Clicked on Excel");
		test_steps.add("Clicked on Excel");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <clickPDFlInLedgerBalancesReport> Description:
	 * <This method clicks on PDF in Ledger Balances Report> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void clickPDFlInLedgerBalancesReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.PDFExport);
		reportLogger.info("Clicked on PDF");
		test_steps.add("Clicked on PDF");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <clickPrintInLedgerBalancesReport> Description:
	 * <This method clicks on Print in Ledger Balances Report> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void clickPrintInLedgerBalancesReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.Print);
		reportLogger.info("Clicked on Print");
		test_steps.add("Clicked on Print");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <clickRunReportInLedgerBalancesReport>
	 * Description: <This method clicks on RunReport in Ledger Balances Report>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <08/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void clickRunReportInLedgerBalancesReport(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.RunReport);
		reportLogger.info("Clicked on RunReport");
		test_steps.add("Clicked on RunReport");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateRunReporExistenceinMainHeader>
	 * Description: <This method checks if RunReport exists on Main Header in Ledger
	 * Balances Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <08/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateRunReporExistenceinMainHeader(WebDriver driver, ArrayList<String> test_steps) {
		if (Utility.isElementDisplayed(driver, By.xpath(OR_Reports.RunReport))) {
			reportLogger.info("Success - Run Report existence validation in Main header");
			test_steps.add("Success - Run Report existence validation in Main header");
		} else
			Assert.assertTrue(false, "Fail - Run Report existence validation in Main header");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateRunReporExistenceAtPageBottom>
	 * Description: <This method checks if RunReport exists on Main Header in Ledger
	 * Balances Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <08/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateRunReporExistenceAtPageBottom(WebDriver driver, ArrayList<String> test_steps) {
		if (Utility.isElementDisplayed(driver, By.xpath(OR_Reports.RunReportAtBottom))) {
			reportLogger.info("Success - Run Report existence validation at Page Bottom");
			test_steps.add("Success - Run Report existence validation at Page Bottom" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/RPT-279'>"
					+ "Click here to open JIRA: RPT-279</a>");
		} else
			Assert.assertTrue(false,
					"Fail - Run Report existence validation at Page Bottom" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-279'>"
							+ "Click here to open JIRA: RPT-279</a>");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateLedgerBalancesReportExistenceDescriptionAtBottomHeader> Description:
	 * <This method checks Report Name and Description exists in Secodary Header in
	 * Ledger Balances Report> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <08/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateNameAndDescriptioninSecondaryHeader(WebDriver driver, ArrayList<String> test_steps) {
		if ((Utility.isElementDisplayed(driver, By.xpath(OR_Reports.LedgerBalancesReportHeader)))
				&& (Utility.isElementDisplayed(driver, By.xpath(OR_Reports.LedgerBalancesSubTitle)))) {
			reportLogger.info("Success - Report Filter Header along with Description validation");
			test_steps.add("Success - Report Filter Header along with Description validation");
		} else
			Assert.assertTrue(false, "Fail - Report Filter Header along with Description validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <checkRequiredAdvancedOption> Description:
	 * <This method selects required Account Type Option. This method works for
	 * Account Type, Item Status and Reservation status> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps, String OptiontToBeChecked>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void checkRequiredAdvancedOption(WebDriver driver, ArrayList<String> test_steps,
			String... OptiontsToBeChecked) {
		for (int i = 0; i < OptiontsToBeChecked.length; i++) {
			String s = "//*[text()='" + OptiontsToBeChecked[i] + "']/..";
			String s2 = "(//*[text()='Gift Certificate']/..)[2]";
			WebElement e;
			if (OptiontsToBeChecked[i].equalsIgnoreCase("Gift Certificate")) {
				e = driver.findElement(By.xpath(s2));
			}else {
				e = driver.findElement(By.xpath(s));
			}
			
			Wait.explicit_wait_visibilityof_webelement(e, driver);
			if (e.getAttribute("class").contains("checked")) {
				reportLogger.info("Required option already checked");
			} else {

				Utility.clickThroughJavaScript(driver, e);
				reportLogger.info("checked the required option " + OptiontsToBeChecked[i]);
			}
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <unCheckRequiredAdvancedOption> Description:
	 * <This method unchecks required Account Type Option. This method works for
	 * Account Type, Item Status and Reservation status> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps, String OptiontToBeChecked>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void unCheckRequiredAdvancedOption(WebDriver driver, ArrayList<String> test_steps,
			String OptiontToBeUnChecked) {
		String s = "//*[text()='" + OptiontToBeUnChecked + "']/..";
		WebElement e = driver.findElement(By.xpath(s));
		if (e.getAttribute("class").contains("checked")) {
			Utility.clickThroughJavaScript(driver, e);
			reportLogger.info("unchecked the required option " + OptiontToBeUnChecked);
		} else
			reportLogger.info("Required option already unchecked");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getCountOfCheckedAdvancedSubOptions>
	 * Description: <This method gets count of checked advanced options. This method
	 * works for Account Type, Item Status and Reservation status> Input parameters:
	 * <WebDriver driver, String AdvacnedOption> Return value: <int> Created By:
	 * <Naveen Kadthala> Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public int getCountOfCheckedAdvancedSubOptions(WebDriver driver, String AdvacnedOption) {
		// get number of check-boxes
		String s1 = "//*[text()='" + AdvacnedOption + "']/../../../following-sibling::div/div/div/label";
		List<WebElement> options = driver.findElements(By.xpath(s1));
		int countOfCheckedBoxes = 0;
		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getAttribute("class").contains("checked"))
				countOfCheckedBoxes = countOfCheckedBoxes + 1;
		}
		return countOfCheckedBoxes;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getTextOfCheckedAdvancedSubOptionsDisplayed>
	 * Description: <This method gets text of checked advanced options Displayed.
	 * This method works for Account Type, Item Status and Reservation status> Input
	 * parameters: <WebDriver driver, String AdvacnedOption> Return value: <String>
	 * Created By: <Naveen Kadthala> Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public String getTextOfCheckedAdvancedSubOptionsDisplayed(WebDriver driver, String AdvacnedOption) {
		String s2 = "//*[text()='" + AdvacnedOption
				+ "']/../following-sibling::div//div[contains(@class,'textOverflow')]";
		Wait.waitForElementToBeVisibile(By.xpath(s2), driver);
		String text = driver.findElement(By.xpath(s2)).getText();
		return text;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateCountOfSelectedTotalAdvancedOption>
	 * Description: <This method validates count of selected / Total of advanced
	 * options. This method works for Account Type, Item Status and Reservation
	 * status> Input parameters: <WebDriver driver, ArrayList<String> test_steps,
	 * String AdvacnedOption> Return value: <void> Created By: <Naveen Kadthala>
	 * Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateCountOfSelectedTotalAdvancedOption(WebDriver driver, ArrayList<String> test_steps,
			String AdvacnedOption, int countOfCheckedBoxes, String CountTextDisplayed) {

		if (AdvacnedOption.equalsIgnoreCase("Account Type")) {
			boolean condition1 = (countOfCheckedBoxes == 8 && CountTextDisplayed.equals("All"));
			boolean condition2 = (countOfCheckedBoxes == 7 && CountTextDisplayed.equals("7/8"));
			boolean condition3 = (countOfCheckedBoxes == 6 && CountTextDisplayed.equals("6/8"));
			boolean condition4 = (countOfCheckedBoxes == 5 && CountTextDisplayed.equals("5/8"));
			boolean condition5 = (countOfCheckedBoxes == 4 && CountTextDisplayed.equals("4/8"));
			boolean condition6 = (countOfCheckedBoxes == 3 && CountTextDisplayed.equals("3/8"));
			boolean condition7 = (countOfCheckedBoxes == 2 && CountTextDisplayed.equals("2/8"));
			boolean condition8 = (countOfCheckedBoxes == 1 && CountTextDisplayed.equals("1/8"));
			boolean condition9 = (countOfCheckedBoxes == 0 && CountTextDisplayed.equals("None"));
			if (condition1 || condition2 || condition3 || condition4 || condition5 || condition6 || condition7
					|| condition8 || condition9) {
				reportLogger.info("Success - Count of selected and displayed check boxes validation");
			}
		}

		else if (AdvacnedOption.equalsIgnoreCase("Item Status")) {
			boolean condition1 = (countOfCheckedBoxes == 4 && CountTextDisplayed.equals("All"));
			boolean condition2 = (countOfCheckedBoxes == 3 && CountTextDisplayed.equals("3/4"));
			boolean condition3 = (countOfCheckedBoxes == 2 && CountTextDisplayed.equals("2/4"));
			boolean condition4 = (countOfCheckedBoxes == 1 && CountTextDisplayed.equals("1/4"));
			boolean condition5 = (countOfCheckedBoxes == 0 && CountTextDisplayed.equals("None"));
			if (condition1 || condition2 || condition3 || condition4 || condition5) {
				reportLogger.info("Success - Count of selected and displayed check boxes validation");
			}
		}

		else if (AdvacnedOption.equalsIgnoreCase("Reservation Status")) {
			boolean condition1 = (countOfCheckedBoxes == 10 && CountTextDisplayed.equals("All"));
			boolean condition2 = (countOfCheckedBoxes == 7 && CountTextDisplayed.equals("9/10"));
			boolean condition3 = (countOfCheckedBoxes == 7 && CountTextDisplayed.equals("8/10"));
			boolean condition4 = (countOfCheckedBoxes == 7 && CountTextDisplayed.equals("7/10"));
			boolean condition5 = (countOfCheckedBoxes == 6 && CountTextDisplayed.equals("6/10"));
			boolean condition6 = (countOfCheckedBoxes == 5 && CountTextDisplayed.equals("5/10"));
			boolean condition7 = (countOfCheckedBoxes == 4 && CountTextDisplayed.equals("4/10"));
			boolean condition8 = (countOfCheckedBoxes == 3 && CountTextDisplayed.equals("3/10"));
			boolean condition9 = (countOfCheckedBoxes == 2 && CountTextDisplayed.equals("2/10"));
			boolean condition10 = (countOfCheckedBoxes == 1 && CountTextDisplayed.equals("1/10"));
			boolean condition11 = (countOfCheckedBoxes == 0 && CountTextDisplayed.equals("None"));
			if (condition1 || condition2 || condition3 || condition4 || condition5 || condition6 || condition7
					|| condition8 || condition9 || condition10 || condition11) {
				reportLogger.info("Success - Count of selected and displayed check boxes validation");
			}
		}

		else {
			Assert.assertTrue(false, "Fail - Count of selected and displayed check boxes validation");
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getTaxExemptLedgerItemsOptions> Description:
	 * <This method gets text of Tax Exempt Ledger Item Options> Input parameters:
	 * <WebDriver driver> Return value: <ArrayList<String>> Created By: <Naveen
	 * Kadthala> Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> getTaxExemptLedgerItemsOptions(WebDriver driver) {
		ArrayList<String> options = new ArrayList<String>();
		List<WebElement> listedoptions = driver
				.findElements(By.xpath("//div[@id='taxExempt_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < listedoptions.size(); i++) {
			options.add(listedoptions.get(i).getText());
		}
		return options;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateTaxExemptLedgerItemsOptions>
	 * Description: <This method validates Tax Exempt Ledger Items options> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateTaxExemptLedgerItemsOptions(WebDriver driver, ArrayList<String> test_steps) {
		String[] actOptions = { "Tax Exempt & Taxed", "Tax Exempt", "Taxed" };
		Elements_Reports res = new Elements_Reports(driver);
		Utility.clickThroughAction(driver, res.TaxExemptListExpand);
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='taxExempt_list']/following-sibling::div/div/div/div/div"),
				driver);
		List<WebElement> expoptions = driver
				.findElements(By.xpath("//div[@id='taxExempt_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < actOptions.length; i++) {
			Assert.assertEquals(actOptions[i], expoptions.get(i).getText(),
					"FAIL - Tax Exempt Ledger Items options validation");
		}
		Utility.clickThroughAction(driver, res.TaxExemptListExpand);
		reportLogger.info("Success - Tax Exempt Ledger Items options validation");
		test_steps.add("Sucess - Tax Exempt Ledger Items options validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getMarketSegmentOptions> Description: <This
	 * method gets text of Market Segment Options> Input parameters: <WebDriver
	 * driver> Return value: <ArrayList<String>> Created By: <Naveen Kadthala>
	 * Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> getMarketSegmentOptions(WebDriver driver) {
		ArrayList<String> options = new ArrayList<String>();
		List<WebElement> listedoptions = driver.findElements(By.xpath(
				"//div[@id='marketSegment_list']/following-sibling::div/descendant::div[@class='ant-select-item-option-content']"));
		for (WebElement option : listedoptions) {
			options.add(option.getText());
		}
		return options;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateMarketSegmentOptions> Description:
	 * <This method validates options of Market Segment Options> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateMarketSegmentOptions(WebDriver driver, ArrayList<String> test_steps) throws Throwable {
		Elements_Reports res = new Elements_Reports(driver);
		Navigation nav = new Navigation();
		ReportsV2 report = new ReportsV2();
//		 setup(driver);
//		 nav.ListManagemnet(driver);
		driver.findElement(By.xpath("//a[text()='Market Segment']")).click();
		ListManagement ls = new ListManagement();
		ArrayList<String> lsMarketSegments = ls.getActiveMarketSegmentsNames(driver);
		report.navigateToReports(driver, test_steps);
		report.navigateToLedgerBalancesReport(driver);
		res.MarketSegment.click();
		Utility.clickThroughAction(driver, res.MarketSegmentListExpand);
		Wait.wait5Second();
		ArrayList<String> options = report.getMarketSegmentOptions(driver);

		try {
			if (options.get(0).equals("All")) {
				options.remove(0);
				Collections.sort(lsMarketSegments);
				Collections.sort(options);
				if (lsMarketSegments.equals(options)) {
					reportLogger.info("Success - Market Segments options validation");
					test_steps.add("Sucess - Market Segments options validation");
				} else {
					Assert.assertTrue(false, "Fail - Market Segments options validation. Expected: " + lsMarketSegments
							+ " but found: " + options);
				}
			} else {
				Assert.assertTrue(false, "Fail - Market Segments options validation");
			}
		} catch (Exception e) {
			test_steps.add(e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getReferralsOptions> Description: <This method
	 * gets text of Referrals Options> Input parameters: <WebDriver driver> Return
	 * value: <ArrayList<String>> Created By: <Naveen Kadthala> Created On:
	 * <08/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> getReferralsOptions(WebDriver driver) {
		ArrayList<String> options = new ArrayList<String>();
		List<WebElement> listedoptions = driver.findElements(By.xpath(
				"//div[@id='referrals_list']/following-sibling::div/descendant::div[@class='ant-select-item-option-content']"));
		for (WebElement option : listedoptions) {
			options.add(option.getText());
		}
		return options;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReferralsOptions> Description: <This
	 * method validates options of Market Segment Options> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <08/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReferralsOptions(WebDriver driver, ArrayList<String> test_steps) throws Throwable {

		// ********************************************
		Elements_Reports res = new Elements_Reports(driver);
		Navigation nav = new Navigation();
		ReportsV2 report = new ReportsV2();
		// setup(driver);
		// nav.ListManagemnet(driver);
		// driver.findElement(By.xpath("//a[text()='Market Segment']")).click();
		ListManagement ls = new ListManagement();
		ArrayList<String> lsReferrals = ls.getActiveReferralsNames(driver);
		report.navigateToReports(driver, test_steps);
		report.navigateToLedgerBalancesReport(driver);
		res.Referrals.click();
		Utility.clickThroughAction(driver, res.ReferralstListExpand);
		Wait.wait5Second();
		ArrayList<String> options = report.getReferralsOptions(driver);

		try {
			if (options.get(0).equals("All")) {
				options.remove(0);
				Collections.sort(lsReferrals);
				Collections.sort(options);
				if (lsReferrals.equals(options)) {
					reportLogger.info("Success - Referrals options validation");
					test_steps.add("Sucess - Referrals options validation");
				} else {
					Assert.assertTrue(false,
							"Fail - Referrals options validation. Expected: " + lsReferrals + " but found: " + options);
				}
			} else {
				Assert.assertTrue(false, "Fail - Referrals options validation");
			}
		} catch (Exception e) {
			test_steps.add(e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// ***********************************************
		// Elements_Reports res = new Elements_Reports(driver);
		// Navigation nav = new Navigation();
		// ReportsV2 report = new ReportsV2();
		// nav.setup(driver);
		// nav.ListManagemnet(driver);
		// ListManagement ls = new ListManagement();
		// ArrayList<String> lsReferrals = ls.getActiveReferralsNames(driver);
		// report.navigateToReports(driver, test_steps);
		// report.navigateToLedgerBalancesReport(driver);
		// res.Referrals.click();
		// Utility.clickThroughAction(driver, res.ReferralstListExpand);
		// Wait.wait5Second();
		// ArrayList<String> options = report.getReferralsOptions(driver);
		// int flag=0;
		// for(int i=0; i<lsReferrals.size();i++) {
		// Assert.assertEquals(lsReferrals.get(i), options.get(i+1), "Fail - Referrals
		// options validation");
		// }
		// flag=1;
		// if(options.get(0).equals("All") && flag==1) {
		// reportLogger.info("Success - Referrals options validation");
		// test_steps.add("Sucess - Referrals options validation");}
		// else
		// {
		// reportLogger.info("Fail - Referrals options validation");
		// test_steps.add("Fail - Referrals options validation");
		// }
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateTaxExemptLedgerItemsCollapseText>
	 * Description: <This method validates Tax Exempt Ledger Items Collapse Text>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <08/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateTaxExemptLedgerItemsCollapseText(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		List<WebElement> l = driver
				.findElements(By.xpath("//div[@id='taxExempt_list']/following-sibling::div/div/div/div/div"));
		for (WebElement e : l) {
			Utility.clickThroughJavaScript(driver, e);
			String selectedoption = e.getAttribute("textContent");
			Utility.clickThroughJavaScript(driver, res.TaxExemptLedgerItems);
			String displayText = driver.findElement(By.xpath(
					"//*[text()='Tax Exempt Ledger Items']/../following-sibling::div//div[contains(@class,'textOverflow')]"))
					.getText();
			Utility.clickThroughJavaScript(driver, res.TaxExemptLedgerItems);
			Assert.assertEquals(selectedoption, displayText,
					"Fail - Tax Exempt Ledger Items Collapse Text validation" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-536'>"
							+ "Click here to open JIRA: RPT-536</a>");
		}
		reportLogger.info("Success - Tax Exempt Ledger Items Collapse Text validation");
		test_steps.add("Sucess - Tax Exempt Ledger Items Collapse Text validation" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/RPT-536'>" + "Click here to open JIRA: RPT-536</a>");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateMarketSegmentCollapseText>
	 * Description: <This method validates Market Segment Collapse Text> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <08/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateMarketSegmentCollapseText(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		List<WebElement> l = driver
				.findElements(By.xpath("//div[@id='marketSegment_list']/following-sibling::div/div/div/div/div"));
		for (WebElement e : l) {
			Utility.clickThroughJavaScript(driver, e);
			String selectedoption = e.getAttribute("textContent");
			Utility.clickThroughJavaScript(driver, res.MarketSegment);
			String displayText = driver.findElement(By.xpath(
					"//*[text()='Market Segment']/../following-sibling::div//div[contains(@class,'textOverflow')]"))
					.getText();
			Utility.clickThroughJavaScript(driver, res.MarketSegment);
			Assert.assertEquals(selectedoption, displayText, "Fail - Market Segment Collapse Text validation");
		}
		reportLogger.info("Success - Market Segment Collapse Text validation");
		test_steps.add("Sucess - Market Segment Collapse Text validation");

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReferralsCollapseText> Description:
	 * <This method validates Referrals Collapse Text> Input parameters: <WebDriver
	 * driver, ArrayList<String> test_steps> Return value: <void> Created By:
	 * <Naveen Kadthala> Created On: <08/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateReferralsCollapseText(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		List<WebElement> l = driver
				.findElements(By.xpath("//div[@id='referrals_list']/following-sibling::div/div/div/div/div"));
		for (WebElement e : l) {
			Utility.clickThroughJavaScript(driver, e);
			String selectedoption = e.getAttribute("textContent");
			Utility.clickThroughJavaScript(driver, res.Referrals);
			String displayText = driver
					.findElement(By.xpath(
							"//*[text()='Referrals']/../following-sibling::div//div[contains(@class,'textOverflow')]"))
					.getText();
			Utility.clickThroughJavaScript(driver, res.Referrals);
			Assert.assertEquals(selectedoption, displayText, "Fail - Referrals Collapse Text validation");
		}
		reportLogger.info("Success - Market Segment Collapse Text validation");
		test_steps.add("Sucess - Referrals Collapse Text validation");
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <verifyHelpbuttonAvailabilityInAllNonAspxPages>
	 * Description: <This method checks if Help button is available in other react
	 * pages> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <08/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void verifyHelpbuttonAvailabilityInAllNonAspxPages(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Navigation nav = new Navigation();
		driver.findElement(By.xpath("//a[text()='Inventory']")).click();
		// nav.inventory(driver);
		nav.RatesGrid(driver);
		boolean c1 = Utility.isElementPresent(driver, By.xpath("//a[text()='Help']"));

		driver.findElement(By.xpath("//a[text()='Demand Management']")).click();
		boolean c2 = Utility.isElementPresent(driver, By.xpath("//a[text()='Help']"));

		driver.findElement(By.xpath("//a[contains(text(),'Distribution')]")).click();
		boolean c3 = Utility.isElementPresent(driver, By.xpath("/a[text()='Help']"));

		driver.findElement(By.xpath("//span[@class='sn_span3'][contains(text(),'Policies')]")).click();
		boolean c4 = Utility.isElementPresent(driver, By.xpath("/a[text()='Help']"));

		driver.findElement(By.xpath("//a[contains(text(),'Products & Bundles')]")).click();
		boolean c5 = Utility.isElementPresent(driver, By.xpath("/a[text()='Help']"));

		if (!c1 && !c2 && !c3 && !c4 && !c5) {
			reportLogger.info("validated Help button and it is not available in other react pages");
			test_steps.add("validated Help button and it is not available in other react pages" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/RPT-314'>"
					+ "Click here to open JIRA: RPT-314</a>");
		} else
			Assert.assertTrue(false,
					"validated Help button and it is available in other react pages" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-314'>"
							+ "Click here to open JIRA: RPT-314</a>");

	}

	// Navigate to other pages
	// navigate to Admin
	public void admin(WebDriver driver) {
		Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
		try {
			driver.findElement(By.xpath("(//*[text()='Admin'])[2]")).click();
		} catch (Exception e) {
			try {
				Navigate.Admin.click();
			} catch (Exception e1) {
				Navigate.AdminIcon.click();
			}
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR.Admin_Grid), driver);
		reportLogger.info("Navigated to Admin");
	}

	// Navigate to Setup page
	public void setup(WebDriver driver) {
		Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//*[text()='Setup'])[2]")));
			// driver.findElement(By.xpath("(//span[text()='Setup'])[2]")).click();
		} catch (Exception e) {
			try {
				jse.executeScript("arguments[0].click();", Navigate.Setup);
			} catch (Exception e1) {
				try {
					driver.findElement(By.xpath("//*[@data-id='fncMenuSetup']")).click();
				} catch (Exception e2) {
					jse.executeScript("arguments[0].click();", Navigate.SetupIcon);
				}
			}
		}
		try {
			Wait.explicit_wait_xpath(OR.Setup_Grid, driver);
		} catch (Exception e) {
			driver.navigate().refresh();
			Wait.explicit_wait_xpath(OR.Setup_Grid, driver);
		}
		Wait.explicit_wait_xpath(OR.Setup_Menu_Title, driver);
		reportLogger.info("Navigated to Setup");
	}

	// Naresh
	// This method is to verify Select Input options are available in Ledger
	// Balances Report page
	public boolean verifySelectInputAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		boolean flag;
		if (driver.findElements(By.xpath(OR_Reports.txtSelectInputs)).size() > 0) {
			test_steps.add("Select Input option is available on Ledger Balances Report page");
			reportLogger.info("Select Input option is available on Ledger Balances Report page");
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	// This method is to verify Include Ledger Account is available in Ledger
	// Balances Report page
	public boolean verifyIncludeLedgerAccountAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);
		boolean flag;
		if (driver.findElements(By.xpath(OR_Reports.txtIncludeLedgerAccount)).size() > 0) {
			test_steps.add("Include Ledger Account is available on Ledger Balances Report page");
			reportLogger.info("Include Ledger Account is available on Ledger Balances Report page");
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	// This method is to verify Include Ledger Account tooltip
	public void validateTooltipIncludeLedgerAccount(WebDriver driver, String expectedMessage,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		try {
			driver.navigate().refresh();
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.infoIncludeLedgerAccount), driver);
			Utility.mouseOverElement(driver, report.infoIncludeLedgerAccount);
			Wait.wait2Second();
			String actualMessage = readTooltipText(driver);
			reportLogger.info("Expected " + expectedMessage);
			reportLogger.info("Actual " + actualMessage);
			if (expectedMessage.equals(actualMessage)) {
				test_steps.add("Include Ledger Account tooltip message validated susseccfully");
				reportLogger.info("Include Ledger Account tooltip message validated susseccfully");
			} else {
				Assert.assertTrue(false, "Failed to validate Display Custom General Ledger Account # tooltip message");
			}
		} catch (Exception e) {
			// Assert.assertTrue(false, "Failed to validate Include Ledger Account tooltip
			// message");
		}
	}

	// This method is to read the tooltip message
	public String readTooltipText(WebDriver driver) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);
		String text = report.tooltip.getText();
		return text;
	}

	// This method is to verify Incidentals Select input option is available in
	// Ledger Balances Report page
	public void verifyIncidentalsAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		// Wait.WaitForElement(driver, OR_Reports.txtIncludeLedgerAccount);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.selectIncidentals), driver);
			reportLogger.info("Incidentals available under Select inputs in Ledger Balances Repost page");
			test_steps.add("Incidentals available under Select inputs in Ledger Balances Repost page");
		} catch (Exception e) {
			reportLogger.info("Incidentals is not available under Select inputs in Ledger Balances Repost page");
			test_steps.add("Incidentals is not available under Select inputs in Ledger Balances Repost page");
			Assert.assertTrue(false, "");
		}
	}

	// This method is to verify given Select input option is available in Ledger
	// Balances Report page
	public void verifySelectInputOptionAvailability(WebDriver driver, String selectInputName,
			ArrayList<String> test_steps) throws InterruptedException {
		String selectInputLocator = "//span[contains(text(),'" + selectInputName + "')]//parent::button";
		ArrayList<String> types = new ArrayList<>();
		// Wait.WaitForElement(driver, OR_Reports.txtIncludeLedgerAccount);
		try {
			Wait.explicit_wait_visibilityof_webelement_3(driver.findElement(By.xpath(selectInputLocator)), driver);
			Wait.waitForElementToBeVisibile(By.xpath(selectInputLocator), driver);
			reportLogger.info(selectInputName + " available under Select inputs in Ledger Balances Repost page");
			test_steps.add(selectInputName + " available under Select inputs in Ledger Balances Repost page");
		} catch (Exception e) {
			reportLogger.info(selectInputName + " is not available under Select inputs in Ledger Balances Repost page");
			test_steps.add("AssertionError: " + selectInputName
					+ " is not available under Select inputs in Ledger Balances Repost page");
		}
	}

	// This method is to get available types
	public ArrayList<String> getAvailableTypes(WebDriver driver, Set<String> typesAll, ArrayList<String> test_steps)
			throws InterruptedException {

		ArrayList<String> types = new ArrayList<>();

		for (String type : typesAll) {
			String selectInputLocator = "//span[contains(text(),'" + type + "')]//parent::button";

			if (driver.findElements(By.xpath(selectInputLocator)).size() > 0) {
				types.add(type);
			}
		}

		return types;
	}
	
	// This method is to get available types
	public ArrayList<String> getAllAvailableTypes(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		ArrayList<String> types = new ArrayList<>();
		String strType = "//span[contains(@class,'MultiSelectControls')]";
		
		List<WebElement> type = driver.findElements(By.xpath(strType));
		
		for (int i = 0; i < type.size(); i++) {
			types.add(type.get(i).getText());
		}

		return types;
	}

	// This method is to validate Select Inputs Default values
	public void validateSelectInputsDefaultValues(WebDriver driver, Set<String> typesAll, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);
		ArrayList<String> types = new ArrayList<>();

		types = getAvailableTypes(driver, typesAll, test_steps);

		// String selectInputLocator = "//span[contains(text(),'" + selectInputName +
		// "')]//parent::button";
		// String strCheckbox = "//span[contains(text(),'" + selectInputName +
		// "')]//preceding-sibling::label//input";

		// Wait.WaitForElement(driver, OR_Reports.txtIncludeLedgerAccount);

		for (int i = 0; i < types.size(); i++) {

			try {

				String strCheckbox = "//span[contains(text(),'" + types.get(i) + "')]//preceding-sibling::label//input";

				// Wait.waitForElementToBeVisibile(By.xpath(selectInputLocator), driver);
				WebElement checkbox = driver.findElement(By.xpath(strCheckbox));
				if (!checkbox.isEnabled()) {
					reportLogger.info(types.get(i) + " checkbox disabled as expected");
					test_steps.add(types.get(i) + " checkbox disabled as expected");
				} else {
					reportLogger.info(types.get(i) + " checkbox not disabled disabled as expected");
					Assert.assertTrue(false);
				}

			} catch (Exception e) {
				reportLogger.info(types.get(i) + " checkbox validation failed");
				test_steps.add(types.get(i) + " checkbox validation failed");
				Assert.assertTrue(false, types.get(i) + " checkbox validation failed");
			} catch (Error e) {
				reportLogger.info("Failed - " + types.get(i) + " checkbox not disabled as expected");
				test_steps.add("Failed - " + types.get(i) + " checkbox not disabled as expected");
			}

		}

	}

	// This method is to verify popup when we clicked on input options - Incidentals
	public void selectInputsPopupValidationIncidentals(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.selectIncidentals), driver);
		report.selectIncidentals.click();

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.popupMessage), driver);

		if (report.popupMessage.getText().equals("Ledger Account Balances")) {
			Assert.assertTrue(true, "Popup not displayed as expected");
			reportLogger.info("Select inputs popup displayed");
			test_steps.add("Select inputs popup displayed");

			Wait.wait5Second();
			reportLogger.info(report.inputIncidentals.getAttribute("class"));
			if (report.inputIncidentals.getAttribute("class").contains("ant-menu-item-selected")) {
				Assert.assertTrue(true, "Incidentals input is selected as expected");
			} else {
				Assert.assertTrue(false, "Incidentals input is not selected as expected");
			}

		} else {
			Assert.assertTrue(false, "Popup not displayed as expected");
		}
	}

	// This method is to click on given select input
	public void clickSelectInput(WebDriver driver, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {

		String selectInputLocator = "//span[contains(text(),'" + selectInputName + "')]//parent::button";

		Elements_Reports report = new Elements_Reports(driver);
		// Wait.waitForElementToBeVisibile(By.xpath(selectInputLocator), driver);
		try {
			// Wait.explicit_wait_10sec(driver.findElement(By.xpath(selectInputLocator)),
			// driver);
			Wait.explicit_wait_visibilityof_webelement_3(driver.findElement(By.xpath(selectInputLocator)), driver);
			WebElement input = driver.findElement(By.xpath(selectInputLocator));
			input.click();
			Wait.wait1Second();

		} catch (Exception e) {
			test_steps.add("AssertionError " + selectInputName + " is not available in Ledger Balances page");
			Assert.assertTrue(false);
		}

		Wait.wait1Second();
	}

	// This method is to validate click on given select input
	public void validateClickSelectInput(WebDriver driver, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		clickSelectInput(driver, selectInputName, test_steps);

		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.popupMessage), driver);
			if (report.popupMessage.getText().equals("Ledger Account Balances")) {
				reportLogger.info("Select inputs popup displayed");
				test_steps.add("Select inputs popup displayed");
			} else {
				Assert.assertTrue(false, "Popup not displayed as expected");
			}

		} catch (Exception e) {
			test_steps.add("AssertionError " + selectInputName + "popup validation failed");
		}
	}

	// This method is to verify popup when we clicked on input options - All options
	public void validateSelectInputsPopup(WebDriver driver, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {

		String selectInputLocator = "//span[contains(text(),'" + selectInputName + "')]//parent::button";
		String inputLocator = "//li[contains(text(),'" + selectInputName + "')]";

		Elements_Reports report = new Elements_Reports(driver);
		try {
			// Wait.waitForElementToBeVisibile(By.xpath(selectInputLocator), driver);
			Wait.wait2Second();
			// driver.findElement(By.xpath(selectInputLocator)).click();
			clickSelectInput(driver, selectInputName, test_steps);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.popupMessage), driver);

			try {
				if (report.popupMessage.getText().equals("Ledger Account Balances")) {
					test_steps.add("Select inputs popup displayed sucessfully - " + selectInputName);
					reportLogger.info("Select inputs popup displayed sucessfully - " + selectInputName);

					Wait.wait3Second();
					WebElement input = driver.findElement(By.xpath(inputLocator));
					WebElement popup = driver.findElement(By.xpath(
							"//span[contains(text(),'Save')]//parent::button[contains(@class,'ant-btn-success')]"));
					// reportLogger.info(input.getAttribute("class"));
					if (!popup.isEnabled()) {
						test_steps.add("Save button disabled as expected - " + selectInputName);
						reportLogger.info("Save button disabled as expected - " + selectInputName);
					} else {
						reportLogger.info(selectInputName + " Save button not disabled as expected");
						Assert.assertTrue(false, selectInputName + " Save button not disabled as expected");
					}

				} else {
					reportLogger.info(selectInputName + "Select input Popup not displayed as expected");
					Assert.assertTrue(false, selectInputName + "Select input Popup not displayed as expected");
				}

				reportLogger.info(selectInputName + " Select inputs popup validated sucessfully");
				test_steps.add(selectInputName + " Select inputs popup validated sucessfully");

				// closePopupSelectInputs(driver, test_steps);
				Wait.wait3Second();
			} catch (Exception e) {
				test_steps.add(e.toString());
				// test_steps.add("AssertionError "+selectInputName+" Select input Popup
				// validation failed");
			} catch (Error e) {
				// test_steps.add(e.toString());
				test_steps.add(e.toString());
			}

		} catch (Exception e) {
			// test_steps.add(e.toString());
			reportLogger.info(selectInputName + " Select input option not available on Ledger Balances page");
			test_steps.add(
					"AssertionError " + selectInputName + " Select input option not available on Ledger Balances page");
		} catch (Error e) {
			// test_steps.add(e.toString());
			reportLogger.info(selectInputName + " Select input option not available on Ledger Balances page");
			test_steps.add(
					"AssertionError " + selectInputName + " Select input option not available on Ledger Balances page");
		}
	}

	// This method is to close the popup
	public void closePopupSelectInputs(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.buttonClosePopup), driver);
		report.buttonClosePopup.click();
	}

	// This method is to Save the popup
	public void savePopupSelectInputs(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.buttonSavePopup), driver);
		// report.buttonSavePopup.click();
		Utility.clickThroughAction(driver, report.buttonSavePopup);
	}

	// This method is to Cancel the popup
	public void cancelPopupSelectInputs(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.buttonCancelPopup), driver);
		report.buttonCancelPopup.click();
	}

	// This method is to get input values from Selected input
	public ArrayList<String> getInputValuesFromGivenCategory(WebDriver driver, String selectInputName,
			ArrayList<String> test_steps) throws InterruptedException {

		String inputLocator = "//li[contains(text(),'" + selectInputName + "')]";
		String inputValuesLocator = "//span[contains(text(),'" + selectInputName
				+ "')]//following-sibling::a[@role='button']";
		ArrayList<String> inputValues = new ArrayList<>();

		Elements_Reports report = new Elements_Reports(driver);

		// Wait.waitForElementToBeVisibile(By.xpath(inputLocator), driver);
		// driver.findElement(By.xpath(inputLocator)).click();
		Wait.wait1Second();

		List<WebElement> inputValuesElements = driver.findElements(By.xpath(inputValuesLocator));

		for (WebElement input : inputValuesElements) {
			inputValues.add(input.getAttribute("title"));
		}

		return inputValues;
	}

	// This method is to validate Select Input options clear all and checkbox and
	// Input Option availability
	public void validateSelectInputCheckboxAndClearAll(WebDriver driver, String selectInputName,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		// report.buttonClearAll.click();

		String selectInputLocator = "//span[contains(text(),'" + selectInputName + "')]//parent::button";
		String strCheckbox = "//span[contains(text(),'" + selectInputName + "')]//preceding-sibling::label//input";

		// Wait.WaitForElement(driver, OR_Reports.txtIncludeLedgerAccount);
		try {
			try {
				Wait.explicit_wait_10sec(driver.findElement(By.xpath(selectInputLocator)), driver);
				reportLogger.info(selectInputName + " is available");
			} catch (Exception e) {
				reportLogger.info("Failed: " + selectInputName + " is not available on Ledger Balances Report page");
				test_steps.add("Failed: " + selectInputName + " is not available on Ledger Balances Report page");
				Assert.assertTrue(false,
						"Failed: " + selectInputName + " is not available on Ledger Balances Report page");
			}

			// Wait.waitForElementToBeVisibile(By.xpath(selectInputLocator), driver);
			WebElement checkbox = driver.findElement(By.xpath(strCheckbox));
			if (!checkbox.isEnabled()) {
				reportLogger.info("Checkbox disabled");
				// clickSelectInput(driver, selectInputName, test_steps);
				selectAllInputOptions(driver, selectInputName, test_steps);
				Utility.clickThroughAction(driver, report.buttonSavePopup);

				if (checkbox.isEnabled()) {
					test_steps.add(selectInputName + " checkbox is enabled after selecting options");
					reportLogger.info(selectInputName
							+ " available under Select inputs in Ledger Balances Report page and checkbox validated sucessfully");
					test_steps.add(selectInputName
							+ " available under Select inputs in Ledger Balances Report page and checkbox validated sucessfully");
				}
			}

		} catch (Exception e) {
			reportLogger.info(selectInputName + " checkbox validation failed");
			test_steps.add(selectInputName + " checkbox validation failed");
			Assert.assertTrue(false, selectInputName + " checkbox validation failed");
		}

		try {
			Wait.wait1Second();
			clearAllInputOptions(driver, test_steps);
			Wait.wait2Second();
			WebElement checkbox = driver.findElement(By.xpath(strCheckbox));
			if (!checkbox.isEnabled()) {
				test_steps.add(selectInputName + " Clear all validated successfully");
				reportLogger.info(selectInputName + " checkbox is disabled after clear all options");
			} else {
				reportLogger.info(selectInputName + " checkbox is disabled after clear all options");
				test_steps.add("AssertionError - " + selectInputName + " Clear all validated successfully");
			}

		} catch (Exception e) {
			reportLogger.info(selectInputName + " clear all validation failed");
			test_steps.add(selectInputName + " clear all validation failed");
			Assert.assertTrue(false, selectInputName + " clear all validation failed");
		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}

	// This method is to all Select Input items using See All
	public void selectAllInputsUsingSeeAll(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		Utility.clickThroughAction(driver, report.buttonSeeAll);
		Wait.wait1Second();
		Utility.clickThroughAction(driver, report.clickSelectAll);
		Wait.wait1Second();
		Utility.clickThroughAction(driver, report.buttonSavePopup);
		Wait.wait1Second();

	}

	// This method is to validate See all functionality
	public void validateSeeAll(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		String strNames = "//button[contains(@class,'MultiSelectControls_checkBoxButton')]";
		String inputLocator = "//h3[text()='Available']//parent::div//a";
		ArrayList<String> inputValues = new ArrayList<>();
		ArrayList<String> allNames = new ArrayList<>();

		// seeAllInputOptions(driver, test_steps);
		Wait.wait2Second();
		try {
			Utility.clickThroughJavaScript(driver, report.buttonSeeAll);
			Wait.wait3Second();
			List<WebElement> inputValuesElements = driver.findElements(By.xpath(inputLocator));

			for (int i = 0; i < inputValuesElements.size(); i++) {
				inputValues.add(inputValuesElements.get(i).getAttribute("title"));
			}

			reportLogger.info("See All: " + inputValues.size() + "  " + inputValues);
			Wait.wait2Second();
			closePopupSelectInputs(driver, test_steps);
			List<WebElement> names = driver.findElements(By.xpath(strNames));

			for (int i = 0; i < names.size(); i++) {
				// names.get(i).click();
				Utility.clickThroughAction(driver, names.get(i));
				List<WebElement> values = driver.findElements(By.xpath("//a[@role='button']"));
				for (int j = 0; j < values.size() - 2; j++) {
					allNames.add(values.get(j).getAttribute("title"));
				}
				Wait.wait2Second();
				closePopupSelectInputs(driver, test_steps);
			}
			reportLogger.info("All: " + allNames.size() + "  " + allNames);

			Collections.sort(inputValues);
			Collections.sort(allNames);

			if (inputValues.equals(allNames)) {
				reportLogger.info("Select Inputs - See All functionality validated successfully");
				test_steps.add("Select Inputs - See All functionality validated successfully");
			} else {
				reportLogger.info("Failed: Select Inputs - See All functionality validation failed");
				test_steps.add("Failed: Select Inputs - See All functionality validation failed");
				Assert.assertTrue(false, "Failed: Select Inputs - See All functionality validation failed. Expected: "
						+ allNames + " But found: " + inputValues);
			}
		} catch (Exception e) {
			test_steps.add(e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}
	}

	// This method is to validate 0 options
	public void validate_0_0_Functionality(WebDriver driver, ArrayList<String> types, ArrayList<String> test_steps)
			throws InterruptedException {

		for (String type : types) {
			if (getAllInputOptionsCount(driver, type, test_steps) == 0) {
				reportLogger.info(
						"The given " + type + " type has no accounts, but it's still visible in Ledger Balances page");
				test_steps.add(
						"The given " + type + " type has no accounts, but it's still visible in Ledger Balances page");
				Assert.assertTrue(false,
						"The give " + type + " type has no accounts, but it's still visible in Ledger Balances page");
			}
		}
	}

	// This method is to select input option from given category
	public void selectInputOption(WebDriver driver, String selectInputName, String selectInputOption,
			ArrayList<String> test_steps) throws InterruptedException {

		String inputLocator = "//li[contains(text(),'" + selectInputName + "')]";
		String strInputValuesAvailable = "//h3[text()='Available']//parent::div//span[contains(text(),'"
				+ selectInputName + "')]//following-sibling::a[@role='button']";
		// String inputValuesLocator =
		// "//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";

		Wait.waitForElementToBeVisibile(By.xpath(inputLocator), driver);
		driver.findElement(By.xpath(inputLocator)).click();

		List<WebElement> inputValuesAvailable = driver.findElements(By.xpath(strInputValuesAvailable));

		for (WebElement input : inputValuesAvailable) {
			if (input.getText().equalsIgnoreCase(selectInputOption)) {
				input.click();
				break;
			}
		}
	}

	// This method is to verify select input option from given category
	public void verifySelectInputOption(WebDriver driver, String selectInputName, String selectInputOption,
			ArrayList<String> test_steps) throws InterruptedException {

		String strInputValuesAvailable = "//h3[text()='Available']//parent::div//span[contains(text(),'"
				+ selectInputName + "')]//following-sibling::a[@role='button']";
		String strInputValuesSelected = "//h3[text()='Selected']//parent::div//span[contains(text(),'" + selectInputName
				+ "')]//following-sibling::a[@role='button']";
		// String inputValuesLocator =
		// "//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";
		ArrayList<String> inputValuesSelectedList = new ArrayList<>();
		ArrayList<String> inputValuesAvailableList = new ArrayList<>();
		clickSelectInput(driver, selectInputName, test_steps);
		Wait.wait2Second();
		selectInputOption(driver, selectInputName, selectInputOption, test_steps);

		List<WebElement> inputValuesAvailable = driver.findElements(By.xpath(strInputValuesAvailable));
		List<WebElement> inputValuesSelected = driver.findElements(By.xpath(strInputValuesSelected));

		for (WebElement available : inputValuesAvailable) {
			inputValuesAvailableList.add(available.getText());
		}

		for (WebElement selected : inputValuesSelected) {
			inputValuesSelectedList.add(selected.getText());
		}

		if (inputValuesSelectedList.contains(selectInputOption)
				&& !inputValuesAvailableList.contains(selectInputOption)) {
			reportLogger.info(selectInputOption + " input option is selected and it is moved to Selected list");
			test_steps.add(selectInputOption + " input option is selected and it is moved to Selected list");
		} else {
			Assert.assertTrue(false, "Given " + selectInputOption + " option is not selected");
		}

		closePopupSelectInputs(driver, test_steps);
		Wait.wait3Second();
	}

	// This method is to select all input options from given category
	public void selectAllInputOptions(WebDriver driver, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {

		String inputLocator = "//li[contains(text(),'" + selectInputName + "')]";
		String strInputValuesAvailable = "//h3[text()='Available']//parent::div//span[contains(text(),'"
				+ selectInputName + "')]//following-sibling::a[@role='button']";
		// String inputValuesLocator =
		// "//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";

		Elements_Reports report = new Elements_Reports(driver);

		try {
			clickSelectInput(driver, selectInputName, test_steps);
			Wait.wait2Second();
			// Wait.waitForElementToBeVisibile(By.xpath(inputLocator), driver);
			// driver.findElement(By.xpath(inputLocator)).click();
		} catch (Exception e) {
			test_steps.add("AssertionError " + selectInputName + " not available on Ledger Balances page");
		}

		report.clickSelectAll.click();

		if (driver.findElements(By.xpath(strInputValuesAvailable)).size() == 0) {
			reportLogger.info(selectInputName + " All options are moved from Available to Selected");
		} else {
			Assert.assertTrue(false, selectInputName + " Failed to move all options from Available to Selected");
		}

		// closePopupSelectInputs(driver, test_steps);
		Wait.wait1Second();
	}

	// This method is to validate select all and deselect all input options from
	// given category
	public void validateSelectAndDeselectAllInputOptions(WebDriver driver, String selectInputName,
			ArrayList<String> test_steps) throws InterruptedException {

		try {
			selectAllInputOptions(driver, selectInputName, test_steps);
			reportLogger.info("Select Inputs: " + selectInputName + " select all options successfully validated");
			test_steps.add("Select Inputs: " + selectInputName + " select all options successfully validated");

		} catch (Exception e) {
			test_steps.add(e.toString());
			test_steps.add(
					"AssertionError Select Inputs: " + selectInputName + " Select all input options validation failed");
		} catch (Error e) {
			test_steps.add(e.toString());
			test_steps.add(
					"AssertionError Select Inputs: " + selectInputName + " Select all input options validation failed");
		}
		Wait.wait1Second();
		try {
			removeAllInputOptions(driver, selectInputName, test_steps);
			reportLogger.info("Select Inputs: " + selectInputName + " deselect all options successfully validated");
			test_steps.add("Select Inputs: " + selectInputName + " deselect all options successfully validated");
			closePopupSelectInputs(driver, test_steps);
			Wait.wait1Second();

		} catch (Exception e) {
			test_steps.add(e.toString());
			test_steps.add("AssertionError Select Inputs: " + selectInputName
					+ " De-select all input options validation failed");
		} catch (Error e) {
			test_steps.add(e.toString());
			test_steps.add("AssertionError Select Inputs: " + selectInputName
					+ " De-select all input options validation failed");
		}

	}

	// This method is to deselect(remove) all input options from given category
	public void removeAllInputOptions(WebDriver driver, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {

		String inputLocator = "//li[contains(text(),'" + selectInputName + "')]";
		String strInputValuesSelected = "//h3[text()='Selected']//parent::div//span[contains(text(),'" + selectInputName
				+ "')]//following-sibling::a[@role='button']";
		// String inputValuesLocator =
		// "//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";

		Elements_Reports report = new Elements_Reports(driver);
		// clickSelectInput(driver, selectInputName, test_steps);
		// Wait.waitForElementToBeVisibile(By.xpath(inputLocator), driver);
		// driver.findElement(By.xpath(inputLocator)).click();

		report.clickRemoveAll.click();

		if (driver.findElements(By.xpath(strInputValuesSelected)).size() == 0) {
			reportLogger.info(selectInputName + " All options are moved from Selected to Available");
		} else {
			Assert.assertTrue(false, selectInputName + " Failed to move all options from Selected to Available");
		}

		// closePopupSelectInputs(driver, test_steps);
		Wait.wait1Second();
	}

	// This method is to validate Plus(+) and close(x) symbols on Select input
	// options popup
	public void validatePlusAndCloseIconsOnSelectInputOptionsPopup(WebDriver driver, String selectInputName,
			ArrayList<String> test_steps) throws InterruptedException {

		String inputLocator = "//li[contains(text(),'" + selectInputName + "')]";
		String strInputValuesAvailable = "//h3[text()='Available']//parent::div//span[contains(text(),'"
				+ selectInputName + "')]//following-sibling::a[@role='button']//span[@role='img']";
		String strInputValuesSelected = "//h3[text()='Selected']//parent::div//span[contains(text(),'" + selectInputName
				+ "')]//following-sibling::a[@role='button']//span[@role='img']";

		// String strInputValuesAvailable =
		// "//h3[text()='Available']//parent::div//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";
		// String strInputValuesSelected =
		// "//h3[text()='Selected']//parent::div//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";

		try {
			clickSelectInput(driver, selectInputName, test_steps);
			// String inputValuesLocator =
			// "//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";
			Wait.wait3Second();
			Wait.waitForElementToBeVisibile(By.xpath(inputLocator), driver);
			driver.findElement(By.xpath(inputLocator)).click();

			Wait.wait3Second();
			List<WebElement> inputValuesAvailable = driver.findElements(By.xpath(strInputValuesAvailable));
			// reportLogger.info(inputValuesAvailable.size());
			// reportLogger.info(inputValuesAvailable.get(0).getAttribute("type"));
			if (inputValuesAvailable.get(0).getAttribute("type").equals("plus")) {

				reportLogger.info("Plus icon Successfully validated on " + selectInputName + " Select input options");
				test_steps.add("Plus icon Successfully validated on '" + selectInputName + "' Select input options");
			} else {
				Assert.assertTrue(false,
						"Plus icon validation failed on '" + selectInputName + "' Select input options");
			}

			inputValuesAvailable.get(0).click();

			List<WebElement> inputValuesSelected = driver.findElements(By.xpath(strInputValuesSelected));

			if (inputValuesSelected.get(0).getAttribute("type").equals("close")) {

				reportLogger.info("Close icon Successfully validated on " + selectInputName + " Select input options");
				test_steps.add("Close icon Successfully validated on '" + selectInputName + "' Select input options");
			} else {
				Assert.assertTrue(false,
						"Close icon validation failed on '" + selectInputName + "' Select input options");
			}

			closePopupSelectInputs(driver, test_steps);
		} catch (Exception e) {
			test_steps.add("AssertionError " + selectInputName + "is not available in Ledger Balances page");
		} catch (Error e) {
			test_steps.add(e.toString());

		}

	}

	// This method is to clear all input options
	public void clearAllInputOptions(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);
		Wait.wait1Second();
		Utility.clickThroughAction(driver, report.buttonClearAll);
		// Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.buttonClearAll), driver);
		// report.buttonClearAll.click();
	}

	// This method is to See all input options
	public void seeAllInputOptions(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.buttonSeeAll), driver);
		// Utility.ScrollToElement(report.buttonSeeAll, driver);
		// report.buttonSeeAll.click();
		Utility.clickThroughAction(driver, report.buttonSeeAll);

	}

	// This method is to validate Select input options values with Ledger account
	// details
	public void validateSelectInputOptionsWithLedgerAccount(WebDriver driver, ArrayList<String> accountDetailsExpected,
			String selectInputName, ArrayList<String> test_steps) throws InterruptedException {

		LedgerAccount la = new LedgerAccount();

		// ArrayList<String> accountDetailsExpected = la.getLedgerAccountDetails(driver,
		// accountType, Status);
		ArrayList<String> accountDetailsActual = getInputValuesFromGivenCategory(driver, selectInputName, test_steps);

		Collections.sort(accountDetailsExpected);
		Collections.sort(accountDetailsActual);

		if (accountDetailsExpected.equals(accountDetailsActual)) {
			reportLogger.info("Successfully validated " + selectInputName
					+ " Select Input options values with Ledger Account details");
			test_steps.add("Successfully validated " + selectInputName
					+ "Select Input options values with Ledger Account details");
		} else {
			Assert.assertTrue(false,
					"Failed to validate " + selectInputName
							+ " Select Input options values with Ledger Account details, Expected: "
							+ accountDetailsExpected + " Actual: " + accountDetailsActual);
		}
	}

	// This method is to get Selected input options count
	public int getSelectedInputOptionsCount(WebDriver driver, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {

		String strSelectInput = "//span[contains(text(),'" + selectInputName + "')]//following-sibling::span";
		WebElement selectInput = driver.findElement(By.xpath(strSelectInput));

		reportLogger.info(selectInput.getText());
		int selectedCount = Integer.parseInt(selectInput.getText().split("/")[0].replaceAll("[^a-zA-Z0-9]", ""));
		reportLogger.info(selectedCount);

		return selectedCount;
	}

	// This method is to get all input options count
	public int getAllInputOptionsCount(WebDriver driver, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {

		String strSelectInput = "//span[contains(text(),'" + selectInputName + "')]//following-sibling::span";
		WebElement selectInput = driver.findElement(By.xpath(strSelectInput));

		// reportLogger.info(selectInput.getText());
		int totalCount = Integer.parseInt(selectInput.getText().split("/")[1].replaceAll("[^a-zA-Z0-9]", ""));
		reportLogger.info("Total count: " + totalCount);

		return totalCount;
	}

	// This method is to validate Select input options count with Ledger account
	// details
	public void validateSelectInputOptionsCountWithLedgerAccount(WebDriver driver,
			ArrayList<String> accountDetailsExpected, String selectInputName, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);
		LedgerAccount la = new LedgerAccount();
		Navigation nav = new Navigation();

		// ArrayList<String> accountDetailsExpected = la.getLedgerAccountDetails(driver,
		// accountType, Status);
		// nav.ReportsV2(driver);
		// navigateToLedgerBalancesReport(driver);
		// clickSelectInput(driver, selectInputName, test_steps);
		// ArrayList<String> accountDetailsActual =
		// getInputValuesFromGivenCategory(driver, selectInputName, test_steps);
		// closePopupSelectInputs(driver, test_steps);

		int countExpected = accountDetailsExpected.size();
		reportLogger.info(countExpected);
		int countActual = getAllInputOptionsCount(driver, selectInputName, test_steps);
		reportLogger.info(countActual);

		Collections.sort(accountDetailsExpected);
		// Collections.sort(accountDetailsActual);

		// if (accountDetailsExpected.equals(accountDetailsActual)) {
		if (countExpected == countActual) {
			test_steps.add("Successfully validated " + selectInputName
					+ " Select Input options values count with Ledger Account details");
			reportLogger.info("Successfully validated " + selectInputName
					+ " Select Input options values count with Ledger Account details");
		} else {
			Assert.assertTrue(false,
					"Failed to validate " + selectInputName
							+ " Select Input options values count with Ledger Account details, Expected: "
							+ countExpected + " Actual: " + countActual);
		}
	}

	// This method is to validate Exclude Zero Balance Ledger Accounts availability
	public void validateExcludeZeroBalanceLedgerAccountsAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.txtExcludeZeroBalanceLedgerAccounts), driver);
			test_steps.add("Exclude Zero Balance Ledger Accounts is available on Ledger Balances Report page");
			reportLogger.info("Exclude Zero Balance Ledger Accounts is available on Ledger Balances Report page");
		} catch (Exception e) {
			Assert.assertTrue(false,
					"Failed, Exclude Zero Balance Ledger Accounts is not available on Ledger Balances Report page");
		}
	}

	// This method is to validate Exclude Zero Balance Ledger Accounts tooltip
	// message
	public void validateTooltipExcludeZeroBalanceLedgerAccounts(WebDriver driver, String expectedMessage,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		try {
			driver.navigate().refresh();
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.tooltipExcludeZeroBalanceLedgerAccounts), driver);
			Utility.mouseOverElement(driver, report.tooltipExcludeZeroBalanceLedgerAccounts);
			String actualMessage = readTooltipText(driver);
			if (expectedMessage.equals(actualMessage)) {
				test_steps.add("Exclude Zero Balance Ledger Accounts tooltip message validated susseccfully");
				reportLogger.info("Exclude Zero Balance Ledger Accounts tooltip message validated susseccfully");
			} else {
				Assert.assertTrue(false, "Failed to validate Exclude Zero Balance Ledger Accounts tooltip message");
			}
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to validate Exclude Zero Balance Ledger Accounts tooltip message");
		}
	}

	// This method is to validate Display Custom General Ledger Account #
	// availability
	public void validateDisplayCustomGeneralLedgerAccountAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.txtDisplayCustomGeneralLedgerAccount), driver);
			test_steps.add("Display Custom General Ledger Account # is available on Ledger Balances Report page");
			reportLogger.info("Display Custom General Ledger Account # is available on Ledger Balances Report page");
		} catch (Exception e) {
			Assert.assertTrue(false,
					"Failed, Display Custom General Ledger Account # is not available on Ledger Balances Report page");
		}
	}

	// This method is to validate Display Custom General Ledger Account # tooltip
	// message
	public void validateTooltipDisplayCustomGeneralLedgerAccount(WebDriver driver, String expectedMessage,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		try {
			driver.navigate().refresh();
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.tooltipDisplayCustomGeneralLedgerAccount), driver);
			Utility.mouseOverElement(driver, report.tooltipDisplayCustomGeneralLedgerAccount);
			String actualMessage = readTooltipText(driver);
			if (expectedMessage.equals(actualMessage)) {
				test_steps.add("Display Custom General Ledger Account # tooltip message validated susseccfully");
				reportLogger.info("Display Custom General Ledger Account # tooltip message validated susseccfully");
			} else {
				Assert.assertTrue(false, "Failed to validate Display Custom General Ledger Account # tooltip message");
			}
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to validate Display Custom General Ledger Account # tooltip message");
		}
	}

	// This method is to select Yes/No for Exclude Zero Balance Ledger Accounts
	public void selectExcludeZeroBalanceLedgerAccountsOption(WebDriver driver, String option,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		try {
			if (option.equals("Yes")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_Reports.rdoYesExcludeZero), driver);
				if (!report.rdoYesExcludeZero.isSelected()) {
					report.rdoYesExcludeZero.click();
				}
			} else {
				Wait.waitForElementToBeClickable(By.xpath(OR_Reports.rdoNoExcludeZero), driver);
				report.rdoNoExcludeZero.click();
			}

			test_steps.add("Exclude Zero Balance Ledger Accounts - " + option + " selected successfully");
			reportLogger.info("Exclude Zero Balance Ledger Accounts - " + option + " selected successfully");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to select " + option + " for Exclude Zero Balance Ledger Accounts");
		}
	}

	// This method is to select Yes/No for Exclude Zero Balance Ledger Accounts
	public void validateExcludeZeroBalanceLedgerAccountsOption(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);
		// Wait.explicit_wait_10sec(report.rdoYesExcludeZero, driver);

		try {
			if (report.rdoYesExcludeZero.isSelected()) {
				report.rdoYesExcludeZero.click();
				Wait.wait1Second();
				if (!report.rdoYesExcludeZero.isSelected()) {
					reportLogger.info("Exclude Zero Balance Ledger Accounts 'Yes' button validated successfully");
					test_steps.add("Exclude Zero Balance Ledger Accounts 'Yes' button validated successfully");
				}
			}
			if (!report.rdoYesExcludeZero.isSelected()) {
				report.rdoYesExcludeZero.click();
				Wait.wait1Second();
				if (report.rdoYesExcludeZero.isSelected()) {
					reportLogger.info("Exclude Zero Balance Ledger Accounts 'Yes' button validated successfully");
					test_steps.add("Exclude Zero Balance Ledger Accounts 'Yes' button validated successfully");
				}
			}

			if (report.rdoNoExcludeZero.isSelected()) {
				report.rdoNoExcludeZero.click();
				Wait.wait1Second();
				if (!report.rdoNoExcludeZero.isSelected()) {
					reportLogger.info("Exclude Zero Balance Ledger Accounts 'No' button validated successfully");
					test_steps.add("Exclude Zero Balance Ledger Accounts 'No' button validated successfully");
				}
			}

			if (!report.rdoNoExcludeZero.isSelected()) {
				report.rdoNoExcludeZero.click();
				Wait.wait1Second();
				if (report.rdoNoExcludeZero.isSelected()) {
					reportLogger.info("Exclude Zero Balance Ledger Accounts 'No' button validated successfully");
					test_steps.add("Exclude Zero Balance Ledger Accounts 'No' button validated successfully");
				}
			}
			test_steps.add("Exclude Zero Balance Ledger Accounts - Yes/No buttons validated successfully");
			reportLogger.info("Exclude Zero Balance Ledger Accounts - Yes/No buttons validated successfully");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to validate Exclude Zero Balance Ledger Accounts Yes/No buttons");
		}
	}

	// This method is to select Yes/No for Display Custom General Ledger Account #
	public void validateDisplayCustomGeneralLedgerAccount(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);
		Wait.explicit_wait_10sec(report.rdoYesDisplayCustomGeneral, driver);

		try {
			if (report.rdoYesDisplayCustomGeneral.isSelected()) {
				report.rdoYesDisplayCustomGeneral.click();
				Wait.wait1Second();
				if (!report.rdoYesDisplayCustomGeneral.isSelected()) {
					reportLogger.info("Display Custom General Ledger Account 'Yes' button validated successfully");
					test_steps.add("Display Custom General Ledger Account 'Yes' button validated successfully");
				}
			}
			if (!report.rdoYesDisplayCustomGeneral.isSelected()) {
				report.rdoYesDisplayCustomGeneral.click();
				Wait.wait1Second();
				if (report.rdoYesDisplayCustomGeneral.isSelected()) {
					reportLogger.info("Display Custom General Ledger Account 'Yes' button validated successfully");
					test_steps.add("Display Custom General Ledger Account 'Yes' button validated successfully");
				}
			}

			if (report.rdoNoDisplayCustomGeneral.isSelected()) {
				report.rdoNoDisplayCustomGeneral.click();
				Wait.wait1Second();
				if (!report.rdoNoDisplayCustomGeneral.isSelected()) {
					reportLogger.info("Display Custom General Ledger Account 'No' button validated successfully");
					test_steps.add("Display Custom General Ledger Account 'No' button validated successfully");
				}
			}

			if (!report.rdoNoDisplayCustomGeneral.isSelected()) {
				report.rdoNoDisplayCustomGeneral.click();
				Wait.wait1Second();
				if (report.rdoNoDisplayCustomGeneral.isSelected()) {
					reportLogger.info("Exclude Zero Balance Ledger Accounts 'No' button validated successfully");
					test_steps.add("Exclude Zero Balance Ledger Accounts 'No' button validated successfully");
				}
			}
			test_steps.add("Display Custom General Ledger Account - Yes/No buttons validated successfully");
			reportLogger.info("Display Custom General Ledger Account - Yes/No buttons validated successfully");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to validate Display Custom General Ledger Account Yes/No buttons");
		}
	}

	// This method is to Select Yes/No for Display Custom General Ledger Account #
	public void selectDisplayCustomGeneralLedgerAccountsOption(WebDriver driver, String option,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		try {
			if (option.equals("Yes")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_Reports.rdoYesDisplayCustomGeneral), driver);
				if (!report.rdoYesDisplayCustomGeneral.isSelected()) {
					report.rdoYesDisplayCustomGeneral.click();
				}
			} else {
				Wait.waitForElementToBeClickable(By.xpath(OR_Reports.rdoNoDisplayCustomGeneral), driver);
				report.rdoNoDisplayCustomGeneral.click();
			}

			test_steps.add("Display Custom General Ledger Account # - " + option + " selected successfully");
			reportLogger.info("Display Custom General Ledger Account # - " + option + " selected successfully");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to select " + option + " for Display Custom General Ledger Account #");
		}
	}

	// This method is to verify search functionality for available options
	public void validateSearchAvailable(WebDriver driver, String selectInputName, String searchValue,
			ArrayList<String> test_steps) throws InterruptedException {

		String strInputValuesAvailable = "//h3[text()='Available']//parent::div//span[contains(text(),'"
				+ selectInputName + "')]//following-sibling::a[@role='button']";
		String searchNoResults = "//h3[text()='Available']//following-sibling::span/input[@name='searchKey']//parent::span//following-sibling::div/div/i[text()='No results found']";
		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.searchAvailable), driver);
		report.searchAvailable.click();
		report.searchAvailable.sendKeys(searchValue);

		// String strInputValuesSelected =
		// "//h3[text()='Selected']//parent::div//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";
		// String inputValuesLocator =
		// "//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";
		ArrayList<String> inputValuesSelectedList = new ArrayList<>();
		ArrayList<String> inputValuesAvailableList = new ArrayList<>();
		ArrayList<String> inputValuesAvailableListBeforeSort = new ArrayList<>();

		List<WebElement> inputValuesAvailable = driver.findElements(By.xpath(strInputValuesAvailable));
		// List<WebElement> inputValuesSelected =
		// driver.findElements(By.xpath(strInputValuesSelected));

		for (WebElement available : inputValuesAvailable) {
			inputValuesAvailableList.add(available.getText());
		}

		inputValuesAvailableListBeforeSort = inputValuesAvailableList;

		Collections.sort(inputValuesAvailableList);

		if (inputValuesAvailableList.equals(inputValuesAvailableListBeforeSort)) {
			if (inputValuesAvailableList.size() == 0) {
				Wait.waitForElementToBeVisibile(By.xpath(searchNoResults), driver);
				test_steps.add("Search functionality successfully validated in Available");
				reportLogger.info("Search functionality successfully validated in Available");
			}
		} else {
			Assert.assertTrue(false, "Failed to validate search functionality in Available");
		}

	}

	// This method is to verify search functionality for Selected options
	public void validateSearchSelected(WebDriver driver, String selectInputName, String searchValue,
			ArrayList<String> test_steps) throws InterruptedException {

		String strInputValuesSelected = "//h3[text()='Selected']//parent::div//span[contains(text(),'" + selectInputName
				+ "')]//following-sibling::a[@role='button']";
		String searchNoResults = "//h3[text()='Available']//following-sibling::span/input[@name='searchKey']//parent::span//following-sibling::div/div/i[text()='No results found']";
		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.searchSelected), driver);
		report.searchSelected.click();
		report.searchSelected.sendKeys(searchValue);

		// String strInputValuesSelected =
		// "//h3[text()='Selected']//parent::div//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";
		// String inputValuesLocator =
		// "//span[text()='"+selectInputName+"']//following-sibling::a[@role='button']";
		ArrayList<String> inputValuesSelectedList = new ArrayList<>();
		ArrayList<String> inputValuesSelectedListBeforeSort = new ArrayList<>();

		List<WebElement> inputValuesSelected = driver.findElements(By.xpath(strInputValuesSelected));

		for (WebElement available : inputValuesSelected) {
			inputValuesSelectedList.add(available.getText());
		}

		inputValuesSelectedListBeforeSort = inputValuesSelectedList;

		Collections.sort(inputValuesSelectedList);

		if (inputValuesSelectedList.equals(inputValuesSelectedListBeforeSort)) {
			if (inputValuesSelectedList.size() == 0) {
				Wait.waitForElementToBeVisibile(By.xpath(searchNoResults), driver);
				test_steps.add("Search functionality successfully validated in Selected");
				reportLogger.info("Search functionality successfully validated in Selected");
			}
		} else {
			Assert.assertTrue(false, "Failed to validate search functionality in Selected");
		}
	}

	public void validateIncludeLedgerAccountsToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.IncludedLedgerAccounts);
		Utility.hoverOnElement(driver, res.IncludedLedgerAccountsToolTipIcon);
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Included Ledger Accounts')]"), driver);
		String act1 = driver
				.findElement(
						By.xpath("//div[@class='ant-popover-title']/b[contains(text(),'Included Ledger Accounts')]"))
				.getText();
		String act2 = driver.findElement(By.xpath(
				"//div[@class='ant-popover-inner-content']/p[contains(text(),'Define which Ledger Accounts to be included')]"))
				.getText().replaceAll("[\\t\\n\\r]+", " ");
		String exp1 = "Included Ledger Accounts";
		String exp2 = "Define which Ledger Accounts to be included in the report. Click on the category name or click �See All� to select specific accounts.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Included Ledger Accounts tooltip validation");
			test_steps.add("Sucess - Included Ledger Accounts tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Included Ledger Accounts tooltip validation");
	}

	public void validateExcludeZeroBalanceLedgerAccountsToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.ExcludeZeroBalanceLedgerAccounts);
		Utility.hoverOnElement(driver, res.ExcludeZeroBalanceLedgerAccountsToolTipIcon);
		Wait.waitForElementToBeVisibile(
				By.xpath(
						"//div[@class='ant-popover-title']/b[contains(text(),'Exclude Zero Balance Ledger Accounts')]"),
				driver);
		String act1 = driver
				.findElement(By.xpath(
						"//div[@class='ant-popover-title']/b[contains(text(),'Exclude Zero Balance Ledger Accounts')]"))
				.getText();
		String act2 = driver
				.findElement(By.xpath("//div[@class='ant-popover-inner-content']/p[contains(text(),'Exclude all $0')]"))
				.getText();
		String exp1 = "Exclude Zero Balance Ledger Accounts";
		String exp2 = "Exclude all $0 balance Ledger Accounts from the report.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Exclude Zero Balance Ledger Accounts tooltip validation");
			test_steps.add("Sucess - Exclude Zero Balance Ledger Accounts tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Exclude Zero Balance Ledger Accounts tooltip validation");
	}

	public void validateDisplayCustomGeneralLedgerAccountToolTip(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.DisplayCustomGeneralLedgerAccount);
		Utility.hoverOnElement(driver, res.DisplayCustomGeneralLedgerAccountToolTipIcon);
		Wait.waitForElementToBeVisibile(By.xpath(
				"//div[@class='ant-popover-title']/b[contains(text(),'Display Custom General Ledger Account #')]"),
				driver);
		String act1 = driver.findElement(By.xpath(
				"//div[@class='ant-popover-title']/b[contains(text(),'Display Custom General Ledger Account #')]"))
				.getText();
		String act2 = driver.findElement(By
				.xpath("//div[@class='ant-popover-inner-content']/p[contains(text(),'General Ledger Account Number')]"))
				.getText();
		String exp1 = "Display Custom General Ledger Account #";
		String exp2 = "Display the General Ledger Account Number, if any, for each Ledger Account.";

		if ((exp1.equals(act1)) && (exp2.equals(act2))) {
			reportLogger.info("Success - Display Custom General Ledger Account tooltip validation");
			test_steps.add("Sucess - Display Custom General Ledger Account tooltip validation");
		} else
			Assert.assertTrue(false, "Fail - Display Custom General Ledger Account tooltip validation");
	}

	// Validate all account types with Ledger Balances Report options
	public void validateLedgerAccountWithReport(WebDriver driver, HashSet<String> typesUnique, ArrayList<String> names,
			ArrayList<String> types, ArrayList<String> status, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_Reports res = new Elements_Reports(driver);
		LedgerAccount la = new LedgerAccount();

		ArrayList<String> inputValues = new ArrayList<>();
		ArrayList nameList = new ArrayList<>();
		
		if (typesUnique.contains("Package")) {
			typesUnique.remove("Package");
		}
		
		if (typesUnique.contains("Retail")) {
			typesUnique.remove("Retail");
		}

		for (String type : typesUnique) {

			nameList.clear();
			inputValues.clear();

			try {
				clickSelectInput(driver, type, test_steps);
				test_steps.add(type + " is available in Ledger Balances Report page");
				String inputLocator = "//li[contains(text(),'" + type + "')]";
				String inputValuesLocator = "//span[contains(text(),'" + type
						+ "')]//following-sibling::a[@role='button']";

				// Wait.waitForElementToBeVisibile(By.xpath(inputLocator), driver);
				driver.findElement(By.xpath(inputLocator)).click();

				List<WebElement> inputValuesElements = driver.findElements(By.xpath(inputValuesLocator));

				for (WebElement input : inputValuesElements) {
					inputValues.add(input.getAttribute("title").trim());
				}
				closePopupSelectInputs(driver, test_steps);

				int count = 0;
				for (int i = 0; i < types.size(); i++) {
					if (types.get(i).equalsIgnoreCase(type) && status.get(i).equalsIgnoreCase("Active")) {
						nameList.add(names.get(i));
						reportLogger.info(names.get(i) + " Added to list");
						count++;
					}
				}

				if (type.equalsIgnoreCase("Payment Method")) {
					nameList.add("Account (Corp/Member)");
					nameList.add("Account (Group)");
					nameList.add("Account (House Account)");
					nameList.add("Account (Travel Agent)");
					nameList.add("Account (Unit Owner)");
					nameList.remove("Account (Advanced Deposit)");
					count = count + 4;

				}

				Collections.sort(inputValues);
				Collections.sort(nameList);

				try {
					if (inputValues.equals(nameList)) {
						test_steps.add(
								type + " account names successfully matching with LedgerBalances Report Page options");
						reportLogger.info(
								type + " account names successfully matching with LedgerBalances Report Page options");
					} else {
						Assert.assertTrue(false, "Select inputs: " + type
								+ " Failed to match Ledger Account value with Ledger Balances Report value. Expected: "
								+ nameList + " But found: " + inputValues);
						/*
						 * try { Assert.assertTrue(false,
						 * "Failed to match Ledger Account value with Ledger Balances Report value. Expected: "
						 * +nameList+" But found: "+inputValues); }catch (Exception e) {
						 * test_steps.add(e.toString()); } catch (Error e) {
						 * test_steps.add(e.toString()); } for (int i = 0; i < inputValues.size(); i++)
						 * { try { Assert.assertEquals(inputValues.get(i), nameList.get(i),
						 * "Failed to match Ledger Account value with Ledger Balances Report value");
						 * }catch (Exception e) { test_steps.add(e.toString()); } catch (Error e) {
						 * test_steps.add(e.toString()); } }
						 */
					}
				} catch (Exception e) {
					test_steps.add(e.toString());
				} catch (Error e) {
					test_steps.add(e.toString());
				}

				reportLogger.info("Count " + count);
				try {
					if (getAllInputOptionsCount(driver, type, test_steps) == count) {
						test_steps.add(type + " options count is matching with Ledger Balances Select Input count");
						reportLogger.info(type + " options count is matching with Ledger Balances Select Input count");
					} else {
						Assert.assertTrue(false, "Failed - Select Inputs: " + type + " Expected: " + count
								+ ", Actual: " + getAllInputOptionsCount(driver, type, test_steps));
					}

				} catch (Exception e) {
					test_steps.add(e.toString());
				} catch (Error e) {
					test_steps.add(e.toString());
				}

			} catch (Exception e) {
				test_steps.add("AssertionError " + type + " account is not available on Ledger Balances Report page");
			} catch (Error e) {
				test_steps.add("AssertionError " + type + " account is not available on Ledger Balances Report page");
				// test_steps.add(e.toString());
			}
		}

	}

	// This method is to validate Transer input
	public void validateTransfersInput(WebDriver driver, ArrayList<String> expected, ArrayList<String> test_steps)
			throws InterruptedException {

		String selectInputLocator = "//span[contains(text(),'Transfers')]//parent::button";
		String inputLocator = "//li[text()='Transfers']";
		String inputValuesLocator = "//span[text()='Transfers']//following-sibling::a[@role='button']";
		ArrayList<String> actual = new ArrayList<>();

		Elements_Reports report = new Elements_Reports(driver);

		try {
			clickSelectInput(driver, "Transfers", test_steps);

			List<WebElement> inputValuesElements = driver.findElements(By.xpath(inputValuesLocator));

			for (WebElement input : inputValuesElements) {
				actual.add(input.getAttribute("title"));
			}

			if (expected.equals(actual)) {
				reportLogger.info("Transfers input and it's values validated successfully");
				test_steps.add("Transfers input and it's values validated successfully");
			} else {
				reportLogger.info("Transfers input validation failed");
				test_steps.add("Transfers input validation failed");
			}
		} catch (Exception e) {
			reportLogger.info("Transfers input validation failed");
			test_steps.add("AssertionError - Transfers input validation failed");
		} catch (Error e) {
			reportLogger.info("Transfers input validation failed");
			test_steps.add("AssertionError - Transfers input validation failed");
		}
		closePopupSelectInputs(driver, test_steps);

	}

	// This method is to validate Advance deposit input
	public void validateAdvanceDepositAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		String selectInputLocator = "//span[contains(text(),'Advanced Deposit')]//parent::button";

		try {
			Wait.waitForElementToBeVisibile(By.xpath(selectInputLocator), driver, 15);
			reportLogger.info("Advanced Deposit input is available on Ledger Balances page");
			test_steps.add("Advanced Deposit input is available on Ledger Balances page");

		} catch (Exception e) {
			reportLogger.info("Advanced Deposit input is not available on Ledger Balances page");
			test_steps.add("AssertionError - Advanced Deposit input is not available on Ledger Balances page");
			// test_steps.add(e.toString());
		}

	}

	// This method is to validate Select Inputs order in Multiselect model popup
	public void validateSeletInputsOrder(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		String strType = "//*[contains(@class, 'MultiSelectControls_disabled_31Mff')][1]";  //"//*[contains(@class, 'MultiSelectControls_underLine')]"
		String strCategories = "//li[contains(@role,'menuitem') and contains(text(),'All Categories')]//following::li";
		ArrayList<String> allTypes = new ArrayList<>();
		ArrayList<String> allCategories = new ArrayList<>();

		Elements_Reports report = new Elements_Reports(driver);

		try {

			List<WebElement> types = driver.findElements(By.xpath(strType));

		/*	for (int i = 0; i < types.size(); i++) {
				if (i % 2 == 0) {
					allTypes.add(types.get(i).getText());
				}
			}

			for (int i = 0; i < types.size(); i++) {
				if (i % 2 == 1) {
					allTypes.add(types.get(i).getText());
				}
			} */
			
			for (int i = 0; i < types.size(); i++) {
				allTypes.add(types.get(i).getText());
			}

			reportLogger.info("All types: " + allTypes);

			report.selectIncidentals.click();
			Wait.wait1Second();
			List<WebElement> categories = driver.findElements(By.xpath(strCategories));
			reportLogger.info("All Categories size: "+categories.size());
			reportLogger.info("All Categories : "+categories);
			for (int i = 0; i < categories.size(); i++) {
				allCategories.add(categories.get(i).getText());
			}
			reportLogger.info("All Categories: " + allCategories);
			closePopupSelectInputs(driver, test_steps);

			if (allTypes.equals(allCategories)) {
				reportLogger.info("Success - Select inputs order is same as the order in Multiselect model popup");
				test_steps.add("Success - Select inputs order is same as the order in Multiselect model popup");
			} else {
				reportLogger.info("Failed - Select inputs order is not same as the order in Multiselect model popup");
				test_steps.add(
						"AssertionError Failed - Select inputs order is not same as the order in Multiselect model popup. <br>"
								+ "Expected: " + allTypes + " But found: " + allCategories);
			}

		} catch (Exception e) {
			test_steps.add(e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}

	// This method is to expand given advanced options
	public void expandGivenAdvancedOptions(WebDriver driver, String option, ArrayList<String> test_steps)
			throws InterruptedException {

		String strOption = "//*[text()='" + option + "']";

		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.AdvancedInputs);

		WebElement advancedOption = driver.findElement(By.xpath(strOption));
		// Utility.ScrollToElement(advancedOption, driver);
		Utility.clickThroughAction(driver, advancedOption);
		Wait.wait3Second();

	}

	// This method is to get all users list from Include Data form
	public ArrayList<String> getUsersListFromIncludeDataForm(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		String strUsers = "//div[@id='users_list']//following-sibling::div//div//div//div/div";
		// String struser = "//input[@id='users']";

		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.AdvancedInputs);
		ArrayList<String> usersList = new ArrayList<>();

		WebElement user = driver.findElement(By.xpath("//input[@id='users']"));
		// driver.findElement(By.xpath("//input[@id='users']")).click();
		// user.click();
		Utility.clickThroughAction(driver, user);
		Wait.wait2Second();

		List<WebElement> users = driver.findElements(By.xpath(strUsers));

		for (int i = 1; i < users.size(); i++) {
			Utility.ScrollToElement(users.get(i), driver);
			Wait.wait1Second();
			usersList.add(users.get(i).getText());
		}
		// driver.findElement(By.xpath("//input[@id='users']")).click();
		Wait.wait1Second();
		Utility.clickThroughAction(driver, user);
		return usersList;
	}

	// This method is select user from Include Data form
	public void selectUserFromIncludeDataForm(WebDriver driver, String userName, ArrayList<String> test_steps)
			throws InterruptedException {

		String strUsers = "//div[@id='users_list']//following-sibling::div//div//div//div/div";
		// String struser = "//input[@id='users']";

		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.AdvancedInputs);
		ArrayList<String> usersList = new ArrayList<>();

		WebElement user = driver.findElement(By.xpath("//input[@id='users']"));
		// driver.findElement(By.xpath("//input[@id='users']")).click();
		// user.click();
		Utility.clickThroughAction(driver, user);
		Wait.wait2Second();

		List<WebElement> users = driver.findElements(By.xpath(strUsers));

		if (!userName.isEmpty()) {
			for (int i = 1; i < users.size(); i++) {
				// Utility.ScrollToElement(users.get(i), driver);
				// Wait.wait1Second();
				if (users.get(i).getText().equalsIgnoreCase(userName)) {
					users.get(i).click();
					break;
				}
			}
		}

	}

	// This method is to validate Include data Form Collapse text
	public void validateIncludeDataFormCollapseText(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		String text = "//*[text()='Include Data From']/../following-sibling::div//div[contains(@class,'textOverflow')]";

		Elements_Reports report = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.AdvancedInputs);
		String startDate = null, endDate = null, expected = null, actual = null;

		try {
			Wait.wait3Second();
			Utility.clickThroughAction(driver, report.IncludeDataFrom);
			// expandGivenAdvancedOptions(driver, "Include Data From", test_steps);
			// Wait.wait1Second();
			selectUserFromIncludeDataForm(driver, "All Users", test_steps);
			// expandGivenAdvancedOptions(driver, "Include Data From", test_steps);
			Wait.wait1Second();
			Utility.clickThroughAction(driver, report.buttonShiftTime);

			selectShiftTimeStart(driver, "05", "08", "AM", test_steps);
			selectShiftTimeEnd(driver, "07", "10", "AM", test_steps);

			startDate = report.inputStartShiftTime.getAttribute("value");
			endDate = report.inputEndShiftTime.getAttribute("value");

			Wait.wait1Second();
			Utility.clickThroughAction(driver, report.IncludeDataFrom);
			expected = "All Users | 05:08 to 07:10";
			actual = driver.findElement(By.xpath(text)).getText();

			if (expected.equalsIgnoreCase(actual)) {
				reportLogger.info("Include Data From collapse text successfully validated");
				test_steps.add("Include Data From collapse text successfully validated");
			} else {
				reportLogger.info("Failed - Include Data From collapse text validation failed");
				Assert.assertTrue(false, "Failed - Include Data From collapse text validation failed");
			}

			try {
				Wait.wait3Second();
				Utility.clickThroughAction(driver, report.IncludeDataFrom);
				Wait.wait2Second();
				Utility.clickThroughAction(driver, report.buttonShiftTime);
				Wait.wait2Second();
				Utility.clickThroughAction(driver, report.IncludeDataFrom);

				expected = "All Users | 00:00 to 00:00";
				actual = driver.findElement(By.xpath(text)).getText();

				if (expected.equalsIgnoreCase(actual)) {
					reportLogger
							.info("Include Data From Shift time back to default 00:00 after unckeck the Shift time");
					test_steps.add("Include Data From Shift time back to default 00:00 after unckeck the Shift time");
				} else {
					reportLogger.info(
							"Failed - Include Data From Shift time not back to 00:00 after uncheck the Shift time");
					Assert.assertTrue(false,
							"Failed - Include Data From Shift time not back to 00:00 after uncheck the Shift time");
				}

			} catch (Exception e) {
				test_steps.add("AssertionError " + e.toString());
			} catch (Error e) {
				test_steps.add(e.toString());
			}

		} catch (Exception e) {
			test_steps.add("AssertionError " + e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}

	// This method is to get all Properties list from Include Data form
	public ArrayList<String> getPropertiesListFromIncludeDataForm(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		String strProperties = "//div[@id='properties_list']//following-sibling::div//div//div//div/div";

		Elements_Reports res = new Elements_Reports(driver);
		Wait.WaitForElement(driver, OR_Reports.AdvancedInputs);
		ArrayList<String> propertiesList = new ArrayList<>();

		Wait.wait1Second();
		driver.findElement(By.xpath("//input[@id='properties']")).click();
		Wait.wait2Second();

		List<WebElement> properties = driver.findElements(By.xpath(strProperties));

		for (int i = 1; i < properties.size(); i++) {
			propertiesList.add(properties.get(i).getText());
		}
		driver.findElement(By.xpath("//input[@id='properties']")).click();
		return propertiesList;
	}

	// This method is to validate Users availability in Include Data form
	public void validateIncludeDataFormUsersAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		try {
			Wait.WaitForElement(driver, OR_Reports.Users);
			// Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.Users), driver);
			reportLogger.info("Users available under Include Data form in Ledger Balances Repost page");
			test_steps.add("Users available under Include Data form in Ledger Balances Repost page");
		} catch (Exception e) {
			reportLogger.info("Users not available under Include Data form in Ledger Balances Repost page");
			test_steps.add("Users not available under Include Data form in Ledger Balances Repost page");
			Assert.assertTrue(false, "Users not available under Include Data form in Ledger Balances Repost page");
		}
	}

	// This method is to validate Properties availability in Include Data form
	public void validateIncludeDataFormPropertiesAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		try {
			Wait.WaitForElement(driver, OR_Reports.Properties);
			// Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.Properties), driver);
			reportLogger.info("Properties available under Include Data form in Ledger Balances Repost page");
			test_steps.add("Properties available under Include Data form in Ledger Balances Repost page");
		} catch (Exception e) {
			reportLogger.info("Properties not available under Include Data form in Ledger Balances Repost page");
			test_steps.add("Properties not available under Include Data form in Ledger Balances Repost page");
			Assert.assertTrue(false, "Properties not available under Include Data form in Ledger Balances Repost page");
		}
	}

	// This method is to validate Shift Time availability in Include Data form
	public void validateIncludeDataFormShiftTimeAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		try {
			Wait.WaitForElement(driver, OR_Reports.ShiftTime);
			// Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.ShiftTime), driver);
			reportLogger.info("Shift Time available under Include Data form in Ledger Balances Repost page");
			test_steps.add("Shift Time available under Include Data form in Ledger Balances Repost page");
		} catch (Exception e) {
			reportLogger.info("Shift Time not available under Include Data form in Ledger Balances Repost page");
			test_steps.add("Shift Time not available under Include Data form in Ledger Balances Repost page");
			Assert.assertTrue(false, "Shift Time not available under Include Data form in Ledger Balances Repost page");
		}
	}

	// This method is to select Shift Time Start
	public void clickShifTime(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.buttonShiftTime), driver);
		// report.buttonShiftTime.click();
		Utility.clickThroughAction(driver, report.buttonShiftTime);

	}

	// This method is to select Shift Time Start
	public void selectShiftTimeStart(WebDriver driver, String hours, String minutes, String AMorPm,
			ArrayList<String> test_steps) throws InterruptedException {
		String strHours = "(//ul[@class='ant-picker-time-panel-column'])[1]/li/div";
		String strMinutes = "(//ul[@class='ant-picker-time-panel-column'])[2]/li/div";
		String strAMPM = "(//ul[@class='ant-picker-time-panel-column'])[3]/li/div";

		String strHours1 = "((//ul[@class='ant-picker-time-panel-column'])[1]/li/div)[" + (Integer.parseInt(hours) + 1)
				+ "]";
		String strMinutes1 = "((//ul[@class='ant-picker-time-panel-column'])[2]/li/div)["
				+ (Integer.parseInt(minutes) + 1) + "]";

		Elements_Reports report = new Elements_Reports(driver);

		report.inputStartShiftTime.click();

		// Utility.ScrollToElement(driver.findElement(By.xpath("((//ul[@class='ant-picker-time-panel-column'])[1]/li/div)[12]")),
		// driver);
		// Utility.ScrollToElement(driver.findElement(By.xpath("((//ul[@class='ant-picker-time-panel-column'])[2]/li/div)[60]")),
		// driver);

		List<WebElement> hoursList = driver.findElements(By.xpath(strHours));
		List<WebElement> minutesList = driver.findElements(By.xpath(strMinutes));
		List<WebElement> AMorPMList = driver.findElements(By.xpath(strAMPM));

		/*
		 * reportLogger.info("Hours: "+hoursList.size()); for (int i = 0; i <
		 * hoursList.size(); i++) { reportLogger.info(hoursList.get(i).getText()); }
		 * 
		 * reportLogger.info("Minutes: "+minutesList.size()); for (int i = 0; i <
		 * minutesList.size(); i++) { reportLogger.info(minutesList.get(i).getText()); }
		 */

		/*
		 * for (int i = 0; i < hoursList.size(); i++) { if
		 * (hoursList.get(i).getText().equalsIgnoreCase(hours)) {
		 * hoursList.get(i).click(); break; } }
		 * 
		 * for (int i = 0; i < minutesList.size(); i++) { if
		 * (minutesList.get(i).getText().equalsIgnoreCase(minutes)) {
		 * minutesList.get(i).click(); break; } }
		 */
		Utility.ScrollToElement(driver.findElement(By.xpath(strHours1)), driver);
		Utility.clickThroughAction(driver, driver.findElement(By.xpath(strHours1)));
		// driver.findElement(By.xpath(strHours1)).click();

		Utility.ScrollToElement(driver.findElement(By.xpath(strMinutes1)), driver);
		driver.findElement(By.xpath(strMinutes1)).click();

		for (int i = 0; i < AMorPMList.size(); i++) {
			if (AMorPMList.get(i).getText().equalsIgnoreCase(AMorPm)) {
				AMorPMList.get(i).click();
				break;
			}
		}

		Wait.waitForElementToBeClickable(By.xpath(OR_Reports.buttonOkShiftTimeStart), driver);
		report.buttonOkShiftTimeStart.click();

		Wait.wait10Second();

	}

	// This method is to select Shift Time End
	public void selectShiftTimeEnd(WebDriver driver, String hours, String minutes, String AMorPm,
			ArrayList<String> test_steps) throws InterruptedException {
		String strHours = "(//ul[@class='ant-picker-time-panel-column'])[4]/li/div";
		String strMinutes = "(//ul[@class='ant-picker-time-panel-column'])[5]/li/div";
		String strAMPM = "(//ul[@class='ant-picker-time-panel-column'])[6]/li/div";

		String strHours1 = "((//ul[@class='ant-picker-time-panel-column'])[4]/li/div)[" + (Integer.parseInt(hours) + 1)
				+ "]";
		String strMinutes1 = "((//ul[@class='ant-picker-time-panel-column'])[5]/li/div)["
				+ (Integer.parseInt(minutes) + 1) + "]";

		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.inputEndShiftTime), driver);
		Utility.ScrollToElement(report.IncludeDataFrom, driver);
		report.inputEndShiftTime.click();

		List<WebElement> hoursList = driver.findElements(By.xpath(strHours));
		List<WebElement> minutesList = driver.findElements(By.xpath(strMinutes));
		List<WebElement> AMorPMList = driver.findElements(By.xpath(strAMPM));

		/*
		 * for (int i = 0; i < hoursList.size(); i++) { if
		 * (hoursList.get(i).getText().equalsIgnoreCase(hours)) {
		 * hoursList.get(i).click(); break; } }
		 * 
		 * for (int i = 0; i < minutesList.size(); i++) { if
		 * (minutesList.get(i).getText().equalsIgnoreCase(minutes)) {
		 * minutesList.get(i).click(); break; } }
		 */

		Utility.ScrollToElement(driver.findElement(By.xpath(strHours1)), driver);
		driver.findElement(By.xpath(strHours1)).click();

		Utility.ScrollToElement(driver.findElement(By.xpath(strMinutes1)), driver);
		driver.findElement(By.xpath(strMinutes1)).click();

		for (int i = 0; i < AMorPMList.size(); i++) {
			if (AMorPMList.get(i).getText().equalsIgnoreCase(AMorPm)) {
				AMorPMList.get(i).click();
				break;
			}
		}

		Wait.waitForElementToBeClickable(By.xpath(OR_Reports.buttonOkShiftTimeEnd), driver);
		report.buttonOkShiftTimeEnd.click();

	}

	// This method is to select Now Shift Time start
	public void selectNowShiftTimeStart(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.inputStartShiftTime), driver);
		// Utility.ScrollToElement(report.inputStartShiftTime, driver);
		Utility.ScrollToElement(report.IncludeDataFrom, driver);
		report.inputStartShiftTime.click();
		report.buttonNowShiftTimeStart.click();
	}

	// This method is to select Now Shift Time End
	public void selectNowShiftTimeEnd(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.inputEndShiftTime), driver);
		// Utility.ScrollToElement(report.inputEndShiftTime, driver);
		Utility.ScrollToElement(report.IncludeDataFrom, driver);
		report.inputEndShiftTime.click();
		report.buttonNowShiftTimeEnd.click();
	}

	// This method is to validate Shift Time Start
	public void validateShiftTimeStart(WebDriver driver, String hours, String minutes, String AMorPm,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		selectShiftTimeStart(driver, hours, minutes, AMorPm, test_steps);

		try {
			if (AMorPm.equalsIgnoreCase("PM")) {
				if (report.inputStartShiftTime.getAttribute("value")
						.equalsIgnoreCase(Integer.parseInt(hours) + 12 + ":" + minutes)) {
					reportLogger.info("Shift Time - Start time successfully validated");
					test_steps.add("Shift Time - Start time successfully validated");
				} else {
					reportLogger.info("Shift Time - Start time validation failed " + "Expected: "
							+ (Integer.parseInt(hours) + 12) + ":" + minutes + " But found: "
							+ report.inputStartShiftTime.getAttribute("value"));
					test_steps.add("Shift Time - Start time validation failed " + "Expected: " + Integer.parseInt(hours)
							+ 12 + ":" + minutes + " But found: " + report.inputStartShiftTime.getAttribute("value"));
					Assert.assertTrue(false,
							"Shift Time - Start time validation failed " + "Expected: " + Integer.parseInt(hours) + 12
									+ ":" + minutes + " But found: "
									+ report.inputStartShiftTime.getAttribute("value"));
				}
			} else {
				if (report.inputStartShiftTime.getAttribute("value").equalsIgnoreCase(hours + ":" + minutes)) {
					reportLogger.info("Shift Time - Start time successfully validated");
					test_steps.add("Shift Time - Start time successfully validated");
				} else {
					reportLogger.info("Shift Time - Start time validation failed " + "Expected: " + hours + ":"
							+ minutes + " But found: " + report.inputStartShiftTime.getAttribute("value"));
					test_steps.add("Shift Time - Start time validation failed " + "Expected: " + hours + ":" + minutes
							+ " But found: " + report.inputStartShiftTime.getAttribute("value"));
					Assert.assertTrue(false, "Shift Time - Start time validation failed " + "Expected: " + hours + ":"
							+ minutes + " But found: " + report.inputStartShiftTime.getAttribute("value"));
				}
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Shift Time - Start time validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Shift Time - Start time validation failed");
		}

	}

	// This method is to validate Shift Time Start - Now
	public void validateNowShiftTimeStart(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		selectNowShiftTimeStart(driver, test_steps);

		try {
			if (report.inputStartShiftTime.getAttribute("value")
					.equalsIgnoreCase(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE))) {
				reportLogger.info("Shift Time - Now - Start time successfully validated");
				test_steps.add("Shift Time - Now - Start time successfully validated");
			} else {
				reportLogger.info("Shift Time - Now - Start time validation failed " + "Expected: "
						+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " But found: "
						+ report.inputStartShiftTime.getAttribute("value"));
				test_steps.add("Shift Time - Now - Start time validation failed " + "Expected: "
						+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " But found: "
						+ report.inputStartShiftTime.getAttribute("value"));
				Assert.assertTrue(false,
						"Shift Time - Now - Start time validation failed " + "Expected: "
								+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " But found: "
								+ report.inputStartShiftTime.getAttribute("value"));
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Shift Time - Now - Start time validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Shift Time - Now - Start time validation failed");
		}
	}

	// This method is to validate Shift Time End
	public void validateShiftTimeEnd(WebDriver driver, String hours, String minutes, String AMorPm,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reports report = new Elements_Reports(driver);

		selectShiftTimeEnd(driver, hours, minutes, AMorPm, test_steps);

		try {
			if (AMorPm.equalsIgnoreCase("PM")) {
				if (report.inputEndShiftTime.getAttribute("value")
						.equalsIgnoreCase(Integer.parseInt(hours) + 12 + ":" + minutes)) {
					reportLogger.info("Shift Time - End time successfully validated");
					test_steps.add("Shift Time - End time successfully validated");
				} else {
					reportLogger.info(
							"Shift Time - End time validation failed " + "Expected: " + Integer.parseInt(hours) + 12
									+ ":" + minutes + " But found: " + report.inputEndShiftTime.getAttribute("value"));
					test_steps.add("Shift Time - End time validation failed " + "Expected: " + Integer.parseInt(hours)
							+ 12 + ":" + minutes + " But found: " + report.inputEndShiftTime.getAttribute("value"));
					Assert.assertTrue(false,
							"Shift Time - End time validation failed " + "Expected: " + Integer.parseInt(hours) + 12
									+ ":" + minutes + " But found: " + report.inputEndShiftTime.getAttribute("value"));
				}
			} else {
				if (report.inputEndShiftTime.getAttribute("value").equalsIgnoreCase(hours + ":" + minutes)) {
					reportLogger.info("Shift Time - End time successfully validated");
					test_steps.add("Shift Time - End time successfully validated");
				} else {
					reportLogger.info("Shift Time - End time validation failed " + "Expected: " + hours + ":" + minutes
							+ " But found: " + report.inputEndShiftTime.getAttribute("value"));
					test_steps.add("Shift Time - End time validation failed " + "Expected: " + hours + ":" + minutes
							+ " But found: " + report.inputEndShiftTime.getAttribute("value"));
					Assert.assertTrue(false, "Shift Time - End time validation failed " + "Expected: " + hours + ":"
							+ minutes + " But found: " + report.inputEndShiftTime.getAttribute("value"));
				}
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Shift Time - End time validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Shift Time - End time validation failed");
		}

	}

	// This method is to validate Shift Time End - Now
	public void validateNowShiftTimeEnd(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_Reports report = new Elements_Reports(driver);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		selectNowShiftTimeEnd(driver, test_steps);

		try {
			if (report.inputEndShiftTime.getAttribute("value")
					.equalsIgnoreCase(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE))) {
				reportLogger.info("Shift Time - Now - End time successfully validated");
				test_steps.add("Shift Time - Now - End time successfully validated");
			} else {
				reportLogger.info("Shift Time - Now - End time validation failed " + "Expected: "
						+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " But found: "
						+ report.inputEndShiftTime.getAttribute("value"));
				test_steps.add("Shift Time - Now - End time validation failed " + "Expected: "
						+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " But found: "
						+ report.inputEndShiftTime.getAttribute("value"));
				Assert.assertTrue(false,
						"Shift Time - Now - End time validation failed " + "Expected: " + cal.get(Calendar.HOUR_OF_DAY)
								+ ":" + cal.get(Calendar.MINUTE) + " But found: "
								+ report.inputEndShiftTime.getAttribute("value"));
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Shift Time - Now - End time validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Shift Time - Now - End time validation failed");
		}
	}

	// This method is to logout from ReportsV2
	public void logout(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		String CurrentUser = "//span[@class='ant-avatar-string']";
		String Logout = "//a[text()='Logout']";

		Elements_Users user = new Elements_Users(driver);
		Wait.waitForElementToBeClickable(By.xpath(CurrentUser), driver);
		driver.findElement(By.xpath(CurrentUser)).click();
		try {
			Wait.waitForElementToBeClickable(By.xpath(Logout), driver, 10);
			driver.findElement(By.xpath(Logout)).click();
		} catch (Exception e) {
			Wait.waitForElementToBeClickable(By.xpath(OR.logoutLink), driver, 10);
			user.logoutLink.click();
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR.LoginPage), driver);
	}

	// Ledger Balances - Choose Date Range

	// This method is to select Date range
	public void selectDateRange(WebDriver driver, String dateOption, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		Wait.wait2Second();
		// report.dateDropDown.click();
		Utility.clickThroughAction(driver, report.dateDropDown);

		Wait.wait2Second();
		List<WebElement> startDateOption = driver.findElements(By.xpath(OR_Reports.dateOptions));

		for (int i = 0; i < startDateOption.size(); i++) {
			if (dateOption.equalsIgnoreCase(startDateOption.get(i).getText())) {
				startDateOption.get(i).click();
				break;
			}
		}
		Wait.wait2Second();
	}

	// This method is to return Start date
	public void startDate(WebDriver driver, String dateOption, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		Wait.WaitForElement(driver, OR_Reports.dateStart);
		report.dateStart.click();
		List<WebElement> startDateOption = driver.findElements(By.xpath(OR_Reports.dateOptions));

		for (int i = 0; i < startDateOption.size(); i++) {
			if (dateOption.equalsIgnoreCase(startDateOption.get(i).getText())) {
				startDateOption.get(i).click();
				break;
			}
		}
	}

	// This method is to return Start date
	public String getStartDate(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		// startDate(driver, dateOption, test_steps);

		String sdate = report.dateStart.getAttribute("value");
		// Date date = new SimpleDateFormat("E, dd MMM yyyy").parse(sdate);
		// return date;

		return sdate;
	}

	/*
	 * //This method is to return End date public void endDate(WebDriver driver,
	 * String dateOption, ArrayList<String> test_steps) throws InterruptedException,
	 * ParseException {
	 * 
	 * Elements_Reports report = new Elements_Reports(driver);
	 * 
	 * Wait.WaitForElement(driver, OR_Reports.dateEnd); report.dateEnd.click();
	 * List<WebElement> startDateOption =
	 * driver.findElements(By.xpath(OR_Reports.dateOptions));
	 * 
	 * for (int i = 0; i < startDateOption.size(); i++) { if
	 * (dateOption.equalsIgnoreCase(startDateOption.get(i).getText())) {
	 * startDateOption.get(i).click(); break; } } }
	 */

	// This method is to return End date
	public String getEndDate(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		// endDate(driver, dateOption, test_steps);

		String sdate = report.dateEnd.getAttribute("value");
		// Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sdate);
		return sdate;
	}

	// This method is to validate Date range
	public void validateAllDateRanges(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		SimpleDateFormat format = new SimpleDateFormat("MMM dd, YYYY");
		// format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		// Calendar cal = Calendar.getInstance();

		try {
			selectDateRange(driver, "Today", test_steps);
			if (format.format(new Date(report.dateStart.getAttribute("value")))
					.equalsIgnoreCase(Utility.getCurrentDate("MMM dd, YYYY"))
					&& format.format(new Date(report.dateEnd.getAttribute("value")))
							.equalsIgnoreCase(Utility.getCurrentDate("MMM dd, YYYY"))) {
				reportLogger.info("Today date selection validated successfuly");
				test_steps.add("Today date selection validated successfuly");
			} else {
				reportLogger.info("Failed - Today date selection validation failed. Expected Start Date: "
						+ Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateStart.getAttribute("value") + " and Expected End date: "
						+ Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateEnd.getAttribute("value"));
				test_steps.add("Failed - Today date selection validation failed. Expected Start Date: "
						+ Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateStart.getAttribute("value") + " and Expected End date: "
						+ Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateEnd.getAttribute("value"));
				Assert.assertTrue(false, "");
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Today date selection validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Today date selection validation failed");
		}

	/*	try {
			selectDateRange(driver, "Last Month", test_steps);
			// startDate(driver, "Last Month", test_steps);
			Calendar lastMonth = Calendar.getInstance();

			// Date lastMonthFirst = new Date(cal.get(Calendar.MONTH) + "/" +
			// cal.getActualMinimum(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
			// Date lastMonthLast = new Date(cal.get(Calendar.MONTH) + "/" +
			// cal.getActualMinimum(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
			//String lastMonthFirst = format.format(new Date(lastMonth.get(Calendar.MONTH) + "/" + 1 + "/" + lastMonth.get(Calendar.YEAR)));
			String lastMonthFirst = new DateFormatSymbols().getMonths()[lastMonth.get(Calendar.MONTH)-1].substring(0, 3)+" 1, "+lastMonth.get(Calendar.YEAR);

			String lastMonthLast = format.format(new Date(lastMonth.get(Calendar.MONTH) + "/"
					+ lastMonth.getActualMaximum(Calendar.DATE) + "/" + lastMonth.get(Calendar.YEAR)));

			if (format.format(new Date(report.dateStart.getAttribute("value"))).equalsIgnoreCase(lastMonthFirst)
					&& report.dateEnd.getAttribute("value").equalsIgnoreCase(lastMonthLast)) {
				reportLogger.info("Last Month date selection validated successfuly");
				test_steps.add("Last Month date selection validated successfuly");
			} else {
				reportLogger.info(
						"Failed - Last Month date selection validation failed. Expected Start Date: " + lastMonthFirst
								+ " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: "
								+ lastMonthLast + " But found: " + report.dateEnd.getAttribute("value"));
				test_steps.add(
						"Failed - Last Month date selection validation failed. Expected Start Date: " + lastMonthFirst
								+ " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: "
								+ lastMonthLast + " But found: " + report.dateEnd.getAttribute("value"));
				Assert.assertTrue(false, "");
			}

		} catch (Exception e) {
			test_steps.add("AssertionError: Last Month date selection validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Last Month date selection validation failed");
		}

		try {

			selectDateRange(driver, "Month To Date", test_steps);
			Calendar monthToDate = Calendar.getInstance();
			monthToDate.add(Calendar.MONTH, +1);

			//String start = format.format(new Date(monthToDate.get(Calendar.MONTH) + "/"+ 1 + "/" + monthToDate.get(Calendar.YEAR)));   //monthToDate.getActualMinimum(Calendar.DATE)
			String start = new DateFormatSymbols().getMonths()[monthToDate.get(Calendar.MONTH)-1].substring(0, 3) + " 1, "+ monthToDate.get(Calendar.YEAR);   //monthToDate.getActualMinimum(Calendar.DATE)

			// String end = format.format(new Date(cal.get(Calendar.MONTH) + "/" +
			// cal.getActualMinimum(Calendar.DATE) + "/" + cal.get(Calendar.YEAR)));
			// cal.add(Calendar.MONTH, -1);

//			if (Integer.parseInt(Utility.getDayFromDate(Utility.getCurrentDate("MMM dd, YYYY"), "MMM dd, YYYY", "Eastern Standard Time (North America)"))<10) {
//				
//			}
			
			if (format.format(new Date(report.dateStart.getAttribute("value"))).equalsIgnoreCase(start)
					&& report.dateEnd.getAttribute("value").equalsIgnoreCase(format.format(new Date(Utility.getCurrentDate("MMM d, YYYY"))))) {

				reportLogger.info("Month To Date date selection validated successfuly");
				test_steps.add("Month To Date date selection validated successfuly");

			} else {
				reportLogger.info("Failed - Month To Date date selection validation failed. Expected Start Date: "
						+ start + " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: "
						+ Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateEnd.getAttribute("value"));
				test_steps.add("Failed - Month To Date date selection validation failed. Expected Start Date: " + start
						+ " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: "
						+ Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateEnd.getAttribute("value"));
				Assert.assertTrue(false, "");
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Month To Date date selection validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Month To Date date selection validation failed");
		}

		try {
			selectDateRange(driver, "Year To Date", test_steps);
			// startDate(driver, "Year To Date", test_steps);
			Calendar yearToDate = Calendar.getInstance();

			//String startYear = format.format(new Date(1 + "/" + 1 + "/" + yearToDate.get(Calendar.YEAR)));
			String startYear = "Jan " + "1, " + yearToDate.get(Calendar.YEAR);

			if (format.format(new Date(report.dateStart.getAttribute("value"))).equalsIgnoreCase(startYear)
					&& report.dateEnd.getAttribute("value").equalsIgnoreCase(format.format(new Date(Utility.getCurrentDate("MMM d, YYYY"))))) {
				reportLogger.info("Year To Date date selection validated successfuly");
				test_steps.add("Year To Date date selection validated successfuly");
			} else {
				reportLogger.info("Failed - Year To Date date selection validation failed. Expected Start Date: "
						+ startYear + " But found: " + report.dateStart.getAttribute("value")
						+ " and Expected End date: " + Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateEnd.getAttribute("value"));
				test_steps.add("Failed - Year To Date date selection validation failed. Expected Start Date: "
						+ startYear + " But found: " + report.dateStart.getAttribute("value")
						+ " and Expected End date: " + Utility.getCurrentDate("MMM dd, YYYY") + " But found: "
						+ report.dateEnd.getAttribute("value"));
				Assert.assertTrue(false, "");
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Year To Date date selection validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Year To Date date selection validation failed");
		}*/

		try {
			selectDateRange(driver, "Last Week", test_steps);

			GregorianCalendar dayBeforeThisWeek = new GregorianCalendar();
			int dayFromMonday = (dayBeforeThisWeek.get(Calendar.DAY_OF_WEEK) + 7 - Calendar.MONDAY) % 7;
			dayBeforeThisWeek.add(Calendar.DATE, -dayFromMonday - 1);
			String end = new SimpleDateFormat("MMM dd, YYYY").format(dayBeforeThisWeek.getTime());
			System.out.println(end);
			dayBeforeThisWeek.add(Calendar.DATE, -6);
			// String start = new SimpleDateFormat("MMM dd, YYYY").format(new
			// Date(Utility.GetNextDate(-7, end)));

			String start = new SimpleDateFormat("MMM dd, YYYY").format(dayBeforeThisWeek.getTime());
			System.out.println(start);

			if (format.format(new Date(report.dateStart.getAttribute("value"))).equalsIgnoreCase(start)
					&& format.format(new Date(report.dateEnd.getAttribute("value"))).equalsIgnoreCase(end)) {
				reportLogger.info("Last Week date selection validated successfuly");
				test_steps.add("Last Week date selection validated successfuly");
			} else {
				reportLogger.info("Failed - Last Week date selection validation failed. Expected Start Date: " + start
						+ " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: " + end
						+ " But found: " + report.dateEnd.getAttribute("value"));
				test_steps.add("Failed - Last Week date selection validation failed. Expected Start Date: " + start
						+ " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: " + end
						+ " But found: " + report.dateEnd.getAttribute("value"));
				Assert.assertTrue(false, "");
			}
		} catch (Exception e) {
			reportLogger.info("Last Week date selection validation failed");
			test_steps.add("AssertionError: Last Week date selection validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Last Week date selection validation failed");
		}

		try {
			// startDate(driver, "Last 3 Months", test_steps);
			selectDateRange(driver, "Last 3 Months", test_steps);

			Calendar cal3Months = Calendar.getInstance();
			cal3Months.add(Calendar.MONTH, -3);
			cal3Months.set(Calendar.DATE, 1);
			Date firstDateOfLast3Months = cal3Months.getTime();

			cal3Months.add(Calendar.MONTH, 2);

			cal3Months.set(Calendar.DATE, cal3Months.getActualMaximum(Calendar.DATE)); // changed calendar to cal

			Date lastDateOfLast3Months = cal3Months.getTime();

			String expectedStartDate = format.format(firstDateOfLast3Months);
			String expectedEndDate = format.format(lastDateOfLast3Months);

			if (format.format(new Date(report.dateStart.getAttribute("value"))).equalsIgnoreCase(expectedStartDate)
					&& format.format(new Date(report.dateEnd.getAttribute("value")))
							.equalsIgnoreCase(expectedEndDate)) {
				reportLogger.info("Last 3 Months date selection validated successfuly");
				test_steps.add("Last 3 Months date selection validated successfuly");
			} else {
				reportLogger.info("Failed - Last 3 Months date selection validation failed. Expected Start Date: "
						+ expectedStartDate + " But found: "
						+ format.format(new Date(report.dateStart.getAttribute("value"))) + " and Expected End date: "
						+ expectedEndDate + " But found: "
						+ format.format(new Date(report.dateEnd.getAttribute("value"))));
				test_steps.add("Failed - Last 3 Months date selection validation failed. Expected Start Date: "
						+ expectedStartDate + " But found: "
						+ format.format(new Date(report.dateStart.getAttribute("value"))) + " and Expected End date: "
						+ expectedEndDate + " But found: "
						+ format.format(new Date(report.dateEnd.getAttribute("value"))));
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Last 3 Months date selection validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Last 3 Months date selection validation failed");
		}

		try {
			selectDateRange(driver, "Last Year", test_steps);

			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, -1);
			int firstDate = c.getActualMinimum(Calendar.DATE);
			// cal.add(Calendar.MONTH, 1);
			int month = c.getActualMinimum(Calendar.MONTH) + 1;

			int year = c.get(Calendar.YEAR);
			String firstDay = firstDate + "/" + month + "/" + year;
			System.out.println(firstDay);

			//String start = format.format(new Date(c.getActualMinimum(Calendar.MONTH) + 1 + "/" + c.getActualMinimum(Calendar.DATE) + "/" + c.get(Calendar.YEAR)));
			String start = "Jan 01, " + c.get(Calendar.YEAR);

			c.add(Calendar.YEAR, -1);
			String end = format.format(new Date(c.getActualMaximum(Calendar.MONTH) + 1 + "/"
					+ c.getActualMaximum(Calendar.DATE) + "/" + c.get(Calendar.YEAR)));

			if (format.format(new Date(report.dateStart.getAttribute("value"))).equalsIgnoreCase(start)
					&& report.dateEnd.getAttribute("value").equalsIgnoreCase(end)) {
				reportLogger.info("Last Year date selection validated successfuly");
				test_steps.add("Last Year date selection validated successfuly");
			} else {
				reportLogger.info("Failed - Last Year date selection validation failed. Expected Start Date: " + start
						+ " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: " + end
						+ " But found: " + report.dateEnd.getAttribute("value"));
				test_steps.add("Failed - Last Year date selection validation failed. Expected Start Date: " + start
						+ " But found: " + report.dateStart.getAttribute("value") + " and Expected End date: " + end
						+ " But found: " + report.dateEnd.getAttribute("value"));
				Assert.assertTrue(false, "");
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Last Year date selection validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Last Year date selection validation failed");
		}

	}

	// This method is to validate Default date selection
	public void validateCustomRangeDateDefault(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		report.dateStart.click();
		Wait.WaitForElement(driver, OR_Reports.dateTab);

		Calendar c = Calendar.getInstance();
		int today = c.get(Calendar.DAY_OF_MONTH);
		if (report.daySelected.getText().equalsIgnoreCase("" + today + "")) {
			reportLogger.info("Default Date selection validated successfully");
			test_steps.add("Default Date selection validated successfully");
		} else {
			Assert.assertTrue(false,
					"Failed: Default Date selection validation. Expected: "
							+ Utility.getDay(Utility.getCurrentDate("DD/MM/YYYY")) + " But found: "
							+ report.daySelected.getText());
		}
	}

	// This method is to select custom date
	public void selectStartdateOld(WebDriver driver, String dateStart, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		report.dateStart.click();
		Wait.WaitForElement(driver, OR_Reports.dateTab);

		// res.CP_NewReservation_CheckinCal.click();
		String monthYear = Utility.get_MonthYear(dateStart);
		String year = Utility.get_Year(dateStart);
		String month = Utility.get_Month(dateStart);
		String day = Utility.getDay(dateStart);
		reportLogger.info(monthYear);
		String header = null, headerText = null, next = null, date1, date2;
		String monthHeader1 = null, monthHeader2 = null, yearHeader1 = null, yearHeader2 = null;
		String monthHeaderText1 = null, monthHeaderText2 = null, yearHeaderText1 = null, yearHeaderText2 = null;

		for (int i = 1; i <= 12; i++) {

			monthHeader1 = "(//button[@class='ant-picker-month-btn'])[1]";
			monthHeader2 = "(//button[@class='ant-picker-month-btn'])[2]";
			yearHeader1 = "(//button[@class='ant-picker-year-btn'])[1]";
			yearHeader2 = "(//button[@class='ant-picker-year-btn'])[2]";

			monthHeaderText1 = driver.findElement(By.xpath(monthHeader1)).getText();
			monthHeaderText2 = driver.findElement(By.xpath(monthHeader2)).getText();
			yearHeaderText1 = driver.findElement(By.xpath(yearHeader1)).getText();
			yearHeaderText2 = driver.findElement(By.xpath(yearHeader2)).getText();

			// header = "//table[@class='datepicker-table-condensed
			// table-condensed']/thead/tr/th[2]";
			// headerText = driver.findElement(By.xpath(header)).getText();

			date1 = "(//td[contains(@title,'" + day + "')])[1]";
			date2 = "(//td[contains(@title,'" + day + "')])[2]";

			if (monthYear.equalsIgnoreCase(monthHeaderText1 + " " + yearHeaderText1)) {
				Wait.WaitForElement(driver, date1);
				driver.findElement(By.xpath(date1)).click();
				break;
			} else if (monthYear.equalsIgnoreCase(monthHeaderText2 + " " + yearHeaderText2)) {
				Wait.WaitForElement(driver, date2);
				driver.findElement(By.xpath(date2)).click();
				break;
			} else {

				String previousMonth = "(//button[@class='ant-picker-header-prev-btn'])[1]";
				String nextMonth = "(//button[@class='ant-picker-header-next-btn'])[2]";

				String previousYear = "";
				String nextYear = "";

			}

			/*
			 * int size = driver.findElements(By.xpath(date)).size(); for (int k = 1; k <=
			 * size; k++) { date = "(//td[contains(@class,'day') and text()='" + day +
			 * "'])[" + k + "]"; String classText =
			 * driver.findElement(By.xpath(date)).getAttribute("class"); if
			 * (!classText.contains("old")) { driver.findElement(By.xpath(date)).click();
			 * test_steps.add("Selecting checkin date : " + CheckInDate);
			 * reslogger.info("Selecting checkin date : " + CheckInDate); break; } } break;
			 * } else { next =
			 * "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div"
			 * ; Wait.WaitForElement(driver, next);
			 * driver.findElement(By.xpath(next)).click(); Wait.wait2Second(); }
			 */
		}

	}

	// This method is to select custom date
	public void selectStartdate(WebDriver driver, String dateStart, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		// String dateStart = new SimpleDateFormat("DD/MM/YYYY").format(new
		// Date(dateInput));

		String year = Utility.get_Year(dateStart);
		String month = Utility.get_Month(dateStart);
		String day = Utility.getDay(dateStart);

		String years = "//div[@class='ant-picker-cell-inner']";
		String months = "//div[@class='ant-picker-cell-inner']";
		String date = "//td[contains(@title,'" + day + "')]";

		Elements_Reports report = new Elements_Reports(driver);

		report.dateStart.click();
		Wait.WaitForElement(driver, OR_Reports.dateTab);
		report.buttonYear.click();
		List<WebElement> yearsAll = driver.findElements(By.xpath(years));

		for (int i = 0; i < yearsAll.size(); i++) {
			if (yearsAll.get(i).getText().equalsIgnoreCase(year)) {
				yearsAll.get(i).click();
				break;
			}
		}

		Wait.waitForElementToBeVisibile(By.xpath("//button[@class='ant-picker-year-btn']"), driver);

		List<WebElement> monthsAll = driver.findElements(By.xpath(months));

		for (int i = 0; i < monthsAll.size(); i++) {
			if (monthsAll.get(i).getText().equalsIgnoreCase(month)) {
				monthsAll.get(i).click();
				break;
			}
		}
		Wait.wait5Second();
		List<WebElement> dates = driver.findElements(By.xpath(date));

		for (int i = 0; i < dates.size(); i++) {
			if (!dates.get(i).getAttribute("class").contains("disabled")) {
				dates.get(i).click();
				break;
			}
		}
		// driver.findElement(By.xpath(date)).click();
		reportLogger.info("Start date selected " + dateStart);

	}

	// This method is to select custom End date
	public void selectEnddate(WebDriver driver, String dateEnd, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		// String dateStart = new SimpleDateFormat("DD/MM/YYYY").format(new
		// Date(dateInput));

		String year = Utility.get_Year(dateEnd);
		String month = Utility.get_Month(dateEnd);
		String day = Utility.getDay(dateEnd);

		String years = "//div[@class='ant-picker-cell-inner']";
		String months = "//div[@class='ant-picker-cell-inner']";
		String date = "//td[contains(@title,'" + day + "')]";

		Elements_Reports report = new Elements_Reports(driver);

		report.dateEnd.click();
		Wait.WaitForElement(driver, OR_Reports.dateTab);
		report.buttonYear.click();
		List<WebElement> yearsAll = driver.findElements(By.xpath(years));

		for (int i = 0; i < yearsAll.size(); i++) {
			if (yearsAll.get(i).getText().equalsIgnoreCase(year)) {
				yearsAll.get(i).click();
				break;
			}
		}

		Wait.waitForElementToBeVisibile(By.xpath("//button[@class='ant-picker-year-btn']"), driver);

		List<WebElement> monthsAll = driver.findElements(By.xpath(months));

		for (int i = 0; i < monthsAll.size(); i++) {
			if (monthsAll.get(i).getText().equalsIgnoreCase(month)) {
				monthsAll.get(i).click();
				break;
			}
		}
		Wait.wait5Second();

		List<WebElement> dates = driver.findElements(By.xpath(date));

		for (int i = 0; i < dates.size(); i++) {
			if (!dates.get(i).getAttribute("class").contains("disabled")) {
				dates.get(i).click();
				break;
			}
		}

		// driver.findElement(By.xpath(date)).click();
		reportLogger.info("End date selected " + dateEnd);

	}

	// This method is to validate Custom Date Range
	public void validateCustomDateRnge(WebDriver driver, String dateStart, String dateEnd, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {
		Elements_Reports report = new Elements_Reports(driver);
		selectStartdate(driver, dateStart, test_steps);
		selectEnddate(driver, dateEnd, test_steps);

		String dateStartExpected = Utility.get_Month(dateStart) + " " + Utility.getDay(dateStart) + ", "
				+ Utility.get_Year(dateStart);
		String dateEndExpected = Utility.get_Month(dateEnd) + " " + Utility.getDay(dateEnd) + ", "
				+ Utility.get_Year(dateEnd);

		try {
			if (report.dateStart.getAttribute("value").equalsIgnoreCase(dateStartExpected)) {
				reportLogger.info("Custom Start date validated successfully");
				test_steps.add("Custom Start date validated successfully");
			} else {
				reportLogger.info("Failed - Custom Start date validation failed. Expected: " + dateStartExpected
						+ " But found: " + report.dateStart.getAttribute("value"));
				test_steps.add("Failed - Custom Start date validation failed. Expected: " + dateStartExpected
						+ " But found: " + report.dateStart.getAttribute("value"));
				Assert.assertTrue(false, "Failed - Custom Start date validation failed. Expected: " + dateStartExpected
						+ " But found: " + report.dateStart.getAttribute("value"));
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Custom Start date validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Custom Start date validation failed");
		}

		try {
			if (report.dateEnd.getAttribute("value").equalsIgnoreCase(dateEndExpected)) {
				reportLogger.info("Custom End date validated successfully");
				test_steps.add("Custom End date validated successfully");
			} else {
				reportLogger.info("Failed - Custom End date validation failed. Expected: " + dateEndExpected
						+ " But found: " + report.dateEnd.getAttribute("value"));
				test_steps.add("Failed - Custom End date validation failed. Expected: " + dateEndExpected
						+ " But found: " + report.dateEnd.getAttribute("value"));
				Assert.assertTrue(false, "Failed - Custom End date validation failed. Expected: " + dateEndExpected
						+ " But found: " + report.dateEnd.getAttribute("value"));
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Custom End date validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Custom End date validation failed");
		}

	}

	// This method is to validate Custom Date Range
	public void validateDifferentDateFormat(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		String dateStartExpected, dateEndExpected, dateStartActual, dateEndActual;
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, YYYY");
		dateStartExpected = Utility.getCurrentDate("MMM dd, YYYY");
		dateEndExpected = Utility.getCurrentDate("MMM dd, YYYY");

		Elements_Reports report = new Elements_Reports(driver);
		report.dateStart.click();
		Wait.wait3Second();
		report.dateStart.clear();
		Wait.wait3Second();
		report.dateStart.sendKeys("15/06/2020");
		Wait.wait3Second();
		report.dateStart.sendKeys(Keys.ENTER);
		dateStartActual = format.format(new Date(report.dateStart.getAttribute("value")));

		try {
			if (dateStartExpected.equalsIgnoreCase(dateStartActual)) {
				reportLogger.info(
						"Success - Start date - Entered different format date and it's not accepting and selecting current date");
				test_steps.add(
						"Success - Start date - Entered different format date and it's not accepting and selecting current date");
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: Start date - Different format date validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: Start date - Different format date validation failed");
		}

		report.dateEnd.click();
		Wait.wait3Second();
		report.dateEnd.clear();
		Wait.wait3Second();
		report.dateEnd.sendKeys("15/06/2020");
		Wait.wait3Second();
		report.dateEnd.sendKeys(Keys.ENTER);

		dateEndActual = format.format(new Date(report.dateStart.getAttribute("value")));

		try {
			if (dateEndExpected.equalsIgnoreCase(dateEndActual)) {
				reportLogger.info(
						"Success - End date - Entered different format date and it's not accepting and selecting current date");
				test_steps.add(
						"Success - End date - Entered different format date and it's not accepting and selecting current date");
			}
		} catch (Exception e) {
			test_steps.add("AssertionError: End date - Different format date validation failed");
		} catch (Error e) {
			test_steps.add("AssertionError: End date - ifferent format date validation failed");
		}

	}

	// Return to Default
	// This method is to validate Return to Default availability
	public void validateReturnToDefaultAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		try {
			Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);
			reportLogger.info("Return to Default option is available in Ledger Balances Page");
			test_steps.add("Return to Default option is available in Ledger Balances Page");
		} catch (Exception e) {
			test_steps.add("AssertionError: Return to Default option is not available in Ledger Balances Page");
			Assert.assertTrue(false, "Return to Default option is available in Ledger Balances Page");
		} catch (Error e) {
			test_steps.add("AssertionError: Return to Default option is not available in Ledger Balances Page");
			Assert.assertTrue(false, "Return to Default option is not available in Ledger Balances Page");
		}

		try {
			Wait.WaitForElement(driver, OR_Reports.symbolReturnToDefault);
			reportLogger.info("Return to Default symbol is available in Ledger Balances Page");
			test_steps.add("Return to Default symbol is available in Ledger Balances Page");
		} catch (Exception e) {
			test_steps.add("AssertionError: Return to Default symbol is not available in Ledger Balances Page");
			Assert.assertTrue(false, "Return to Default symbol is not available in Ledger Balances Page");
		} catch (Error e) {
			test_steps.add("AssertionError: Return to Default symbol is not available in Ledger Balances Page");
			Assert.assertTrue(false, "Return to Default symbol is not available in Ledger Balances Page");
		}

	}

	// This method is to click Return to Default
	public void clickReturnToDefault(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);

		Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);
		report.buttonReturnToDefault.click();

		// String toastMessage = report.toasterTitle.getText();

	}

	// This method is to get toast message Return to Default
	public String getToastMessageReturnToDefault(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		String message = null;

		if (driver.findElements(By.xpath(OR_Reports.ReturnToDefaultSeccess)).size() > 0) {
			message = report.ReturnToDefaultSeccess.getText();
			reportLogger.info("Return to Defaults Success - " + message);
			test_steps.add("Return to Defaults Success - " + message);
		} else if (driver.findElements(By.xpath(OR_Reports.ReturnToDefaultFailure)).size() > 0) {
			message = report.ReturnToDefaultFailure.getText();
			reportLogger.info("Return to Defaults Failure - " + message);
			test_steps.add("Return to Defaults Failure - " + message);
		}

		return message;
	}

	// This method is to validate Return to Default
	public void validateReturnToDefault(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		// String toast =
		// "//*[@class='ant-notification-notice-message-single-line-auto-margin']";

		Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);
		Utility.clickThroughAction(driver, report.buttonReturnToDefault);
		// report.buttonReturnToDefault.click();
		String toastMessage = getToastMessageReturnToDefault(driver, test_steps);
		String toastDescription = report.ReturnToDefaultDescription.getText();
		System.out.println(toastMessage);

		try {

			if (toastMessage.equalsIgnoreCase("Success")) {
				reportLogger.info("Return to Default message validated successfully - " + toastMessage);
				test_steps.add("Return to Default message validated successfully - " + toastMessage);

				if (toastDescription.equalsIgnoreCase("Report inputs returned to default.")) {
					reportLogger
							.info("Return to Default Success Description validated successfully - " + toastDescription);
					test_steps
							.add("Return to Default Success Description validated successfully - " + toastDescription);
				}

			} else if (toastMessage.contains("No Report Data Available")) {
				reportLogger.info("Return to Default message validated successfully - " + toastMessage);
				test_steps.add("Return to Default message validated successfully - " + toastMessage);

				if (toastDescription
						.equalsIgnoreCase("Please update the report inputs below and try running reports again.")) {
					reportLogger
							.info("Return to Default Failure Description validated successfully - " + toastDescription);
					test_steps
							.add("Return to Default Failure Description validated successfully - " + toastDescription);
				}

			} else {
				Assert.assertTrue(false, "Failed - Return to Default message validation failed");
			}

		} catch (Exception e) {
			test_steps.add(e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}

	// This method is to validate Return to Default in different ways
	public void validateReturnToDefaultAll(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		String toastMessage = null;

		// Clear all Select Inputs then validate Return to Default
		try {
			Utility.clickThroughAction(driver, report.buttonClearAll);

			Utility.clickThroughAction(driver, report.buttonReturnToDefault);

			toastMessage = getToastMessageReturnToDefault(driver, test_steps);

			if (toastMessage.equalsIgnoreCase("Success")) {

				List<WebElement> inputs = driver.findElements(By.xpath(OR_Reports.checkboxSelectInputs));

				for (int i = 0; i < inputs.size(); i++) {
					if (inputs.get(i).isEnabled()) {
						reportLogger.info("Failed to get Default after click on Return to Default");
						Assert.assertTrue(false, "Failed to get Default after click on Return to Default");
					}
				}
				reportLogger.info("Select Input values are back to Default after click on Return to Default");
				test_steps.add("Select Input values are back to Default after click on Return to Default");

			} else if (toastMessage.contains("No Report Data Available")) {
				reportLogger.info("Failed to Return to Default - " + toastMessage);
				test_steps.add("Failed to Return to Default - " + toastMessage);
			}

		} catch (Exception e) {
			test_steps.add("AssertionError " + e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Clear all Advanced Options then validate Return to Default
		try {

			Wait.wait3Second();
			driver.navigate().refresh();
			Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);

			Utility.clickThroughAction(driver, report.AccountType);
			Utility.clickThroughAction(driver, report.AccountTypeClearAll);
			Utility.clickThroughAction(driver, report.AccountType);

			Utility.clickThroughJavaScript(driver, report.ItemStatus);
			Wait.wait1Second();
			Utility.clickThroughJavaScript(driver, report.ItemStatusSelectAll);
			Utility.clickThroughJavaScript(driver, report.ItemStatusClearAll);
			Utility.clickThroughAction(driver, report.ItemStatus);
			// Utility.clickThroughAction(driver, report.ItemStatusClearAll);

			// Utility.clickThroughAction(driver, report.ReservationStatus);
			// Utility.clickThroughJavaScript(driver, report.ReservationStatusClearAll);
			// Utility.clickThroughAction(driver, report.ReservationStatusClearAll);

			Utility.clickThroughAction(driver, report.buttonReturnToDefault);

			toastMessage = getToastMessageReturnToDefault(driver, test_steps);

			if (toastMessage.equalsIgnoreCase("Success")) {
				if (getAdvancedpOptionsCollapseText(driver, test_steps, "Account Type").equalsIgnoreCase("All")
						&& getAdvancedpOptionsCollapseText(driver, test_steps, "Item Status").equalsIgnoreCase("3/4")) {

					reportLogger.info("Advacned Input values are back to Default after click on Return to Default");
					test_steps.add("Advacned Input values are back to Default after click on Return to Default");

				} else {
					assertTrue(false,
							"Failed to get Advacned Input values are back to Default after clicking on Return to Default");
				}

			} else if (toastMessage.contains("No Report Data Available")) {
				reportLogger.info("Failed to Return to Default - " + toastMessage);
				test_steps.add("Failed to Return to Default - " + toastMessage);
			}

		} catch (Exception e) {
			test_steps.add("AssertionError " + e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Select Inputs and clear all Advanced Options then validate Return to Default
		try {

			Wait.wait3Second();
			driver.navigate().refresh();
			Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);
			Utility.clickThroughAction(driver, report.buttonReturnToDefault);

			selectAllInputOptions(driver, "Incidental", test_steps);
			savePopupSelectInputs(driver, test_steps);
			selectAllInputOptions(driver, "Tax", test_steps);
			savePopupSelectInputs(driver, test_steps);

			Utility.clickThroughJavaScript(driver, report.AccountType);
			Utility.clickThroughJavaScript(driver, report.AccountTypeClearAll);
			Utility.clickThroughJavaScript(driver, report.AccountType);

			Utility.clickThroughJavaScript(driver, report.ItemStatus);
			Wait.wait1Second();
			Utility.clickThroughJavaScript(driver, report.ItemStatusSelectAll);
			Utility.clickThroughJavaScript(driver, report.ItemStatusClearAll);
			Utility.clickThroughAction(driver, report.ItemStatus);

			Utility.clickThroughAction(driver, report.buttonReturnToDefault);

			toastMessage = getToastMessageReturnToDefault(driver, test_steps);

			if (toastMessage.equalsIgnoreCase("Success")) {

				if (!report.checkboxIncidentals.isEnabled() && !report.checkboxTaxes.isEnabled()) {
					reportLogger.info("Select Input values are back to Default after click on Return to Default");
					test_steps.add("Select Input values are back to Default after click on Return to Default");
				} else {
					assertTrue(false,
							"Failed to get Select Input values are back to Default after clicking on Return to Default");
				}

				if (getAdvancedpOptionsCollapseText(driver, test_steps, "Account Type").equalsIgnoreCase("All")
						&& getAdvancedpOptionsCollapseText(driver, test_steps, "Item Status").equalsIgnoreCase("3/4")) {

					reportLogger.info("Advacned Input values are back to Default after click on Return to Default");
					test_steps.add("Advacned Input values are back to Default after click on Return to Default");

				} else {
					assertTrue(false,
							"Failed to get Advacned Input values are back to Default after clicking on Return to Default");
				}

			} else if (toastMessage.contains("No Report Data Available")) {
				reportLogger.info(
						"After Clear All Advanced options and Select Inputs, Return to Default message validated successfully - "
								+ toastMessage);
				test_steps.add(
						"After Clear All Advanced options and Select Inputs, Return to Default message validated successfully - "
								+ toastMessage);
			} else {
				Assert.assertTrue(false, "Failed - Failed to return to default");
			}

		} catch (Exception e) {
			test_steps.add("AssertionError " + e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		/*
		 * //Change some other items then validate Return to Default try {
		 * 
		 * Wait.wait3Second(); driver.navigate().refresh(); Wait.WaitForElement(driver,
		 * OR_Reports.buttonReturnToDefault);
		 * 
		 * report.rdoNoExcludeZero.click(); report.rdoNoDisplayCustomGeneral.click();
		 * 
		 * Utility.clickThroughAction(driver, report.buttonReturnToDefault);
		 * 
		 * toastMessage = getToastMessageReturnToDefault(driver, test_steps);
		 * 
		 * if (toastMessage.equalsIgnoreCase("Success")) { reportLogger.
		 * info("After Clear All Advanced options and Select Inputs, Return to Default message validated successfully - "
		 * +toastMessage); test_steps.
		 * add("After Clear All Advanced options and Select Inputs, Return to Default message validated successfully - "
		 * +toastMessage);
		 * 
		 * }else if(toastMessage.contains("No Report Data Available")) { reportLogger.
		 * info("After Clear All Advanced options and Select Inputs, Return to Default message validated successfully - "
		 * +toastMessage); test_steps.
		 * add("After Clear All Advanced options and Select Inputs, Return to Default message validated successfully - "
		 * +toastMessage); }else { Assert.assertTrue(false,
		 * "Failed - After Clear All Advanced options and Select Inputs - Return to Default message validation failed"
		 * ); }
		 * 
		 * }catch(Exception e) { test_steps.add(e.toString()); }catch(Error e) {
		 * test_steps.add(e.toString()); }
		 */

		// click multiple times on Return to Defaults then validate
		try {
			// Click multiple times on Return to Defaults
			driver.navigate().refresh();
			Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);
			Utility.clickThroughAction(driver, report.buttonReturnToDefault);

			toastMessage = getToastMessageReturnToDefault(driver, test_steps);

			if (toastMessage.equalsIgnoreCase("Success")) {
				List<WebElement> inputs = driver.findElements(By.xpath(OR_Reports.checkboxSelectInputs));

				for (int i = 0; i < inputs.size(); i++) {
					if (inputs.get(i).isEnabled()) {
						reportLogger.info("Failed to get Default after click on Return to Default");
						Assert.assertTrue(false, "Failed to get Default after click on Return to Default");
					}
				}
				reportLogger.info("Select Input values are back to Default after click on Return to Default");
				test_steps.add("Select Input values are back to Default after click on Return to Default");

			} else if (toastMessage.contains("No Report Data Available")) {

				reportLogger.info("Failed to Return to Default - " + toastMessage);
				test_steps.add("Failed to Return to Default - " + toastMessage);
			}

			Wait.wait5Second();
			Utility.clickThroughAction(driver, report.buttonReturnToDefault);
			reportLogger.info("Second time clicked on Return to Default");
			test_steps.add("Second time clicked on Return to Default");
			String toastMessage2 = getToastMessageReturnToDefault(driver, test_steps);

			if (toastMessage2.equalsIgnoreCase("Success")) {
				List<WebElement> inputs = driver.findElements(By.xpath(OR_Reports.checkboxSelectInputs));

				for (int i = 0; i < inputs.size(); i++) {
					if (inputs.get(i).isEnabled()) {
						reportLogger.info("Failed to get Default after click on Return to Default");
						Assert.assertTrue(false, "Failed to get Default after click on Return to Default");
					}
				}
				reportLogger.info("Select Input values are back to Default after click on Return to Default");
				test_steps.add("Select Input values are back to Default after click on Return to Default");

			} else if (toastMessage.contains("No Report Data Available")) {

				reportLogger.info("Failed to Return to Default - " + toastMessage);
				test_steps.add("Failed to Return to Default - " + toastMessage);
			}

		} catch (Exception e) {
			test_steps.add("AssertionError " + e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// validate Choose Date Range to Return to Default
		try {

			driver.navigate().refresh();
			Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);

			selectDateRange(driver, "Last Week", test_steps);

			Wait.WaitForElement(driver, OR_Reports.buttonReturnToDefault);
			Utility.clickThroughAction(driver, report.buttonReturnToDefault);

			toastMessage = getToastMessageReturnToDefault(driver, test_steps);

			if (toastMessage.equalsIgnoreCase("Success")) {

				SimpleDateFormat format = new SimpleDateFormat("MMM dd, YYYY");

				if (format.format(new Date(report.dateStart.getAttribute("value")))
						.equalsIgnoreCase(Utility.getCurrentDate("MMM dd, YYYY"))
						&& format.format(new Date(report.dateEnd.getAttribute("value")))
								.equalsIgnoreCase(Utility.getCurrentDate("MMM dd, YYYY"))) {

					reportLogger.info("Date set back to Default after click on Return to Default");
					test_steps.add("Date set back to Default after click on Return to Default");
				} else {
					reportLogger.info("Unable to get date back to Default after click on Return to Default");
					Assert.assertTrue(false, "Failed to get Date back to Default after click on Return to Default");
				}

			} else if (toastMessage.contains("No Report Data Available")) {

				reportLogger.info("Failed to Return to Default - " + toastMessage);
				test_steps.add("Failed to Return to Default - " + toastMessage);
			}

		} catch (Exception e) {
			test_steps.add("AssertionError " + e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getAdvancedpOptionsCollapseText> Description:
	 * <This method reads collapse text of advanced options> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps, String advanceOption> Return
	 * value: <String> Created By: <Naveen Kadthala> Created On: <08/19/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public String getAdvancedpOptionsCollapseText(WebDriver driver, ArrayList<String> test_steps,
			String advanceOption) {
		String displayText = driver.findElement(By.xpath(
				"//*[text()='" + advanceOption + "']/../following-sibling::div//div[contains(@class,'textOverflow')]"))
				.getText();
		return displayText;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateAllAdvancedpOptionsdefaultCollapseText> Description: <This method
	 * validates collapse text of all advanced options> Input parameters: <WebDriver
	 * driver, ArrayList<String> test_steps> Return value: <void> Created By:
	 * <Naveen Kadthala> Created On: <08/19/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateAllAdvancedpOptionsdefaultCollapseText(WebDriver driver, ArrayList<String> test_steps) {
		ReportsV2 report = new ReportsV2();
		String AccountTypeCollapseText = report.getAdvancedpOptionsCollapseText(driver, test_steps, "Account Type");
		String ItemStatusCollapseText = report.getAdvancedpOptionsCollapseText(driver, test_steps, "Item Status");
		String IncludeDataFromCollapseText = report.getAdvancedpOptionsCollapseText(driver, test_steps,
				"Include Data From");
		String TaxExemptLedgerItemsCollapseText = report.getAdvancedpOptionsCollapseText(driver, test_steps,
				"Tax Exempt Ledger Items");
		String MarketSegmentCollapseText = report.getAdvancedpOptionsCollapseText(driver, test_steps, "Market Segment");
		String ReservationStatusCollapseText = report.getAdvancedpOptionsCollapseText(driver, test_steps,
				"Reservation Status");
		String ReferralsCollapseText = report.getAdvancedpOptionsCollapseText(driver, test_steps, "Referrals");

		boolean condition1 = AccountTypeCollapseText.equals("All");
		boolean condition2 = ItemStatusCollapseText.equals("3/4");
		boolean condition3 = IncludeDataFromCollapseText.equals("All Users | 00:00 to 00:00");
		boolean condition4 = TaxExemptLedgerItemsCollapseText.equals("Tax Exempt & Taxed");
		boolean condition5 = MarketSegmentCollapseText.equals("All");
		boolean condition6 = ReservationStatusCollapseText.equals("All");
		boolean condition7 = ReferralsCollapseText.equals("All");

		if (condition1 && condition2 && condition3 && condition4 && condition5 && condition6 && condition7) {
			reportLogger.info("Sucess - Validaion of all Advanced Inputs default collapse text");
			test_steps.add("Success - Validaion of all Advanced Inputs default collapse text");
		} else {
			Assert.assertTrue(false, "Fail - Validaion of all Advanced Inputs default collapse text");
		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateNoDuplicateofReportsNames>
	 * Description: <This method validates there are no duplicates of Reports names
	 * on Reports home page> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <08/19/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void validateNoDuplicateofReportsNames(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Reports res = new Elements_Reports(driver);

		boolean condition1 = (driver.findElements(By.xpath(OR_Reports.DailyFlashReport)).size() == 1);
		boolean condition2 = (driver.findElements(By.xpath(OR_Reports.NetSalesReport)).size() == 1);
		boolean condition3 = (driver.findElements(By.xpath(OR_Reports.RoomForecastReport)).size() == 1);
		boolean condition4 = (driver.findElements(By.xpath(OR_Reports.AdvanceDepositReport)).size() == 1);
		boolean condition5 = (driver.findElements(By.xpath(OR_Reports.AccountBalancesReport)).size() == 1);
		boolean condition6 = (driver.findElements(By.xpath(OR_Reports.LedgerBalancesReport)).size() == 1);
		boolean condition7 = (driver.findElements(By.xpath(OR_Reports.TransactionsReport)).size() == 1);
		boolean condition8 = (driver.findElements(By.xpath(OR_Reports.PerformanceReportsHeading)).size() == 1);
		boolean condition9 = (driver.findElements(By.xpath(OR_Reports.AccountingReportsHeading)).size() == 1);

		if (condition1 && condition2 && condition3 && condition4 && condition5 && condition6 && condition7 && condition8
				&& condition9) {
			reportLogger.info("Sucess - Validaion of duplicate Report names in ReportsV2 home page");
			test_steps.add("Success - Validaion of duplicate Report names in ReportsV2 home page" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/RPT-276'>"
					+ "Click here to open JIRA: RPT-276</a>");
		} else {
			Assert.assertTrue(false,
					"Fail - Validaion of duplicate Report names in ReportsV2 home page" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-276'>"
							+ "Click here to open JIRA: RPT-276</a>");
		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <SelectGivenTaxExemptLedgerItems> Description:
	 * <This method selects given Tax Exempt LedgerItems option> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps, String
	 * TaxExemptLedgerItemsOption> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <20/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void SelectGivenTaxExemptLedgerItems(WebDriver driver, ArrayList<String> test_steps,
			String TaxExemptLedgerItemsOption) throws InterruptedException {
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='taxExempt_list']/following-sibling::div/div/div/div/div"), driver);
		List<WebElement> options = driver
				.findElements(By.xpath("//div[@id='taxExempt_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < options.size(); i++) {
			if (TaxExemptLedgerItemsOption.equalsIgnoreCase(options.get(i).getText())) {
				Utility.clickThroughAction(driver, options.get(i));
			}
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <SelectGivenMarketSegmentOption> Description:
	 * <This method selects given Market Segment option> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps, String MarketSegmentOption>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <20/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void SelectGivenMarketSegmentOption(WebDriver driver, ArrayList<String> test_steps,
			String MarketSegmentOption) throws InterruptedException {
		Wait.waitForElementToBeVisibile(
				By.xpath("//div[@id='marketSegment_list']/following-sibling::div/div/div/div/div"), driver);
		List<WebElement> options = driver
				.findElements(By.xpath("//div[@id='marketSegment_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < options.size(); i++) {
			if (MarketSegmentOption.equalsIgnoreCase(options.get(i).getText())) {
				Utility.clickThroughAction(driver, options.get(i));
			}
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <SelectGivenReferralsOption> Description: <This
	 * method selects given Referrals option> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps, String ReferralsOption> Return value: <void>
	 * Created By: <Naveen Kadthala> Created On: <20/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void SelectGivenReferralsOption(WebDriver driver, ArrayList<String> test_steps, String ReferralsOption)
			throws InterruptedException {
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='referrals_list']/following-sibling::div/div/div/div/div"),
				driver);
		List<WebElement> options = driver
				.findElements(By.xpath("//div[@id='referrals_list']/following-sibling::div/div/div/div/div"));
		for (int i = 0; i < options.size(); i++) {
			if (ReferralsOption.equalsIgnoreCase(options.get(i).getText())) {
				Utility.clickThroughAction(driver, options.get(i));
			}
		}
	}

	// functions for create Run Report
	public void selectSelectInputs(WebDriver driver, String input, String options, ArrayList<String> test_steps)
			throws InterruptedException {
		// reportLogger.info(options);
		reportLogger.info(options + "   " + options.split(",") + "  " + options.split(",").length);

		if (options.equalsIgnoreCase("All")) {
			selectAllInputOptions(driver, input, test_steps);
			savePopupSelectInputs(driver, test_steps);
		} else if (options.isEmpty()) {

		} else if (options.split(",").length == 1) {
			clickSelectInput(driver, input, test_steps);
			selectInputOption(driver, input, options, test_steps);
			Wait.wait1Second();
			savePopupSelectInputs(driver, test_steps);
		} else {
			clickSelectInput(driver, input, test_steps);
			String[] selectOptions = options.split(",");
			// reportLogger.info("Inputs " + selectOptions.length + " " + selectOptions);

			for (int i = 0; i < selectOptions.length; i++) {
				// reportLogger.info(selectOptions[i]);
				selectInputOption(driver, input, selectOptions[i], test_steps);
				// reportLogger.info("Clicked on option");
			}
			Wait.wait1Second();
			savePopupSelectInputs(driver, test_steps);
		}
	}

	// This method is to select Input options
	public void selectSelectInputsAll(WebDriver driver, String[] inputs, String[] options, ArrayList<String> test_steps)
			throws InterruptedException {

		for (int i = 0; i < inputs.length; i++) {
			selectSelectInputs(driver, inputs[i], options[i], test_steps);
		}


	}
	
	// This method is to select Input options
	public void selectSelectInputsAll(WebDriver driver, Set<String> inputs, HashMap<String, String> options, ArrayList<String> test_steps)
			throws InterruptedException {

		for (String input : inputs) {
			selectSelectInputs(driver, input, options.get(input), test_steps);
		}


	}

	// This method is to validate dropdown box size
	public void validateDropdownBoxSize(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		int height, width;
		Elements_Reports report = new Elements_Reports(driver);
		driver.navigate().refresh();
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.AdvancedInputs), driver);

		try {
			width = report.ChoseDateRange.getSize().getWidth();
			height = report.ChoseDateRange.getSize().getHeight();
			reportLogger.info("Width: " + width);
			reportLogger.info("Height: " + height);

			if (report.ChoseDateRange.getSize().getWidth() == 155) {
				reportLogger.info("Choose Date Range dropdown size validated successfully");
				test_steps.add("Choose Date Range dropdown size validated successfully");
			} else {
				reportLogger.info("Failed - Choose Date Range dropdown size validation failed");
				Assert.assertTrue(false, "Failed - Choose Date Range dropdown size validation failed");
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try {
			width = report.SortReportByOptionsExpand.getSize().getWidth();
			height = report.SortReportByOptionsExpand.getSize().getHeight();
			reportLogger.info("Width: " + width);
			reportLogger.info("Height: " + height);

			if (report.SortReportByOptionsExpand.getSize().getWidth() == 514) {
				reportLogger.info("Sort Report By dropdown size validated successfully");
				test_steps.add("Sort Report By dropdown size validated successfully");
			} else {
				reportLogger.info("Failed - Sort Report By dropdown size validation failed");
				Assert.assertTrue(false, "Failed - Sort Report By dropdown size validation failed");
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try {
			width = report.GroupRowsByOptionsExpand.getSize().getWidth();
			height = report.GroupRowsByOptionsExpand.getSize().getHeight();
			reportLogger.info("Width: " + width);
			reportLogger.info("Height: " + height);

			if (report.GroupRowsByOptionsExpand.getSize().getWidth() == 514) {
				reportLogger.info("Group Rows By dropdown size validated successfully");
				test_steps.add("Group Rows By dropdown size validated successfully");
			} else {
				reportLogger.info("Failed - Group Rows By dropdown size validation failed");
				Assert.assertTrue(false, "Failed - Group Rows By dropdown size validation failed");
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try {
			Wait.wait2Second();
			Utility.clickThroughJavaScript(driver, report.IncludeDataFrom);
			Wait.wait2Second();

			width = report.usersExpand.getSize().getWidth();
			height = report.usersExpand.getSize().getHeight();
			reportLogger.info("Width: " + width);
			reportLogger.info("Height: " + height);

			if (report.usersExpand.getSize().getWidth() == 480) {
				reportLogger.info("Include Data Form - Users dropdown size validated successfully");
				test_steps.add("Include Data Form - Users dropdown size validated successfully");
			} else {
				reportLogger.info("Failed - Include Data Form - Users dropdown size validation failed");
				Assert.assertTrue(false, "Failed - Include Data Form - Users dropdown size validation failed");
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try {
			Utility.clickThroughJavaScript(driver, report.TaxExemptLedgerItems);
			Wait.wait2Second();

			width = report.TaxExempt.getSize().getWidth();
			height = report.TaxExempt.getSize().getHeight();
			reportLogger.info("Width: " + width);
			reportLogger.info("Height: " + height);

			if (report.TaxExempt.getSize().getWidth() == 480) {
				reportLogger.info("Tax Exempt Ledger Items dropdown size validated successfully");
				test_steps.add("Tax Exempt Ledger Items dropdown size validated successfully");
			} else {
				reportLogger.info("Failed - Tax Exempt Ledger Items dropdown size validation failed");
				Assert.assertTrue(false, "Failed - Tax Exempt Ledger Items dropdown size validation failed");
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try {
			Utility.clickThroughJavaScript(driver, report.MarketSegment);
			Wait.wait2Second();

			width = report.MarketSegmentListExpand.getSize().getWidth();
			height = report.MarketSegmentListExpand.getSize().getHeight();
			reportLogger.info("Width: " + width);
			reportLogger.info("Height: " + height);

			if (report.MarketSegmentListExpand.getSize().getWidth() == 480) {
				reportLogger.info("Market Segment dropdown size validated successfully");
				test_steps.add("Market Segment dropdown size validated successfully");
			} else {
				reportLogger.info("Failed - Market Segment dropdown size validation failed");
				Assert.assertTrue(false, "Failed - Market Segment dropdown size validation failed");
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try {
			Utility.clickThroughJavaScript(driver, report.Referrals);
			Wait.wait2Second();

			width = report.ReferralstListExpand.getSize().getWidth();
			height = report.ReferralstListExpand.getSize().getHeight();
			reportLogger.info("Width: " + width);
			reportLogger.info("Height: " + height);

			if (report.ReferralstListExpand.getSize().getWidth() == 480) {
				reportLogger.info("Referrals dropdown size validated successfully");
				test_steps.add("Referrals dropdown size validated successfully");
			} else {
				reportLogger.info("Failed - Referrals dropdown size validation failed");
				Assert.assertTrue(false, "Failed - Referrals dropdown size validation failed");
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}

	// Sprint14
	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <splitComaSeperatedValuesfromExcel>
	 * Description: <This method seperates comma seperated values from a given
	 * string and returns string[]> Input parameters: <String options> Return value:
	 * <String[]> Created By: <Naveen Kadthala> Created On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public String[] splitComaSeperatedValuesfromExcel(String options) {
		String[] Options = options.split(",");
		for (int i = 0; i < Options.length; i++) {
			Options[i] = Options[i].trim();
		}
		return Options;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <selectAccountTyepOptionsGiventhroughExcel>
	 * Description: <This method selects Account Type Advanced Inputs given through
	 * Excel> Input parameters: <WebDriver driver, ArrayList<String> test_steps,
	 * String accounttypeoptions> Return value: <void> Created By: <Naveen Kadthala>
	 * Created On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */


    public void selectAccountTyepOptionsGiventhroughExcel(WebDriver driver, ArrayList<String> test_steps, String accounttypeoptions) {
    	
    	
        ReportsV2 reports = new ReportsV2();
        Elements_Reports res = new Elements_Reports(driver);
        Wait.explicit_wait_elementToBeClickable(res.AccountType, driver);
        
        if (!accounttypeoptions.contentEquals("")) {
            try {
            	res.AccountType.click();
            }catch(Exception e) {
            	Utility.clickThroughAction(driver, res.AccountType);
            }
            Utility.clickThroughAction(driver, res.AccountTypeClearAll);
            String[] options = reports.splitComaSeperatedValuesfromExcel(accounttypeoptions);
            reports.checkRequiredAdvancedOption(driver, test_steps, options);
		}
        
    }
        
	

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <selectItemStatusOptionsGiventhroughExcel>
	 * Description: <This method selects Item Status Advanced Inputs given through
	 * Excel> Input parameters: <WebDriver driver, ArrayList<String> test_steps,
	 * String ItemStatusOptions> Return value: <void> Created By: <Naveen Kadthala>
	 * Created On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

    public void selectItemStatusOptionsGiventhroughExcel(WebDriver driver, ArrayList<String> test_steps,
            String ItemStatusOptions) {
        ReportsV2 reports = new ReportsV2();
        Elements_Reports res = new Elements_Reports(driver);
        // res.ItemStatus.click();
        
        //Utility.clickThroughJavaScript(driver, res.ItemStatusSelectAll);
        if (!ItemStatusOptions.contentEquals("")) {
        	Utility.clickThroughJavaScript(driver, res.ItemStatus);
            Utility.clickThroughAction(driver, res.ItemStatusClearAll);
            String[] options = reports.splitComaSeperatedValuesfromExcel(ItemStatusOptions);
            reports.checkRequiredAdvancedOption(driver, test_steps, options);
            res.ItemStatus.click();
        }
        
    }

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <selectReservationStatusOptionsGiventhroughExcel> Description: <This method
	 * selects Reservation Status Advanced Inputs given through Excel> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps, String
	 * reservationStatusOptions> Return value: <void> Created By: <Naveen Kadthala>
	 * Created On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

    public void selectReservationStatusOptionsGiventhroughExcel(WebDriver driver, ArrayList<String> test_steps,
            String reservationStatusOptions) {
        ReportsV2 reports = new ReportsV2();
        Elements_Reports res = new Elements_Reports(driver);
        // res.ReservationStatus.click();
          
        if (!reservationStatusOptions.contentEquals("")) {
        	Utility.clickThroughJavaScript(driver, res.ReservationStatus); 
            Utility.clickThroughAction(driver, res.ReservationStatusClearAll);
            String[] options = reports.splitComaSeperatedValuesfromExcel(reservationStatusOptions);
            reports.checkRequiredAdvancedOption(driver, test_steps, options);
            res.ReservationStatus.click();
        }
        
    }

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <selectTaxExemptLedgerItemsGiventhroughExcel>
	 * Description: <This method selects Tax Exempt Ledger Items given through
	 * Excel> Input parameters: <WebDriver driver, ArrayList<String> test_steps,
	 * String taxExemptLedgerItemOption> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void selectTaxExemptLedgerItemsGiventhroughExcel(WebDriver driver, ArrayList<String> test_steps,
			String TaxExemptLedgerItemsOption) throws InterruptedException {
		ReportsV2 reports = new ReportsV2();
		Elements_Reports res = new Elements_Reports(driver);
		// res.TaxExemptLedgerItems.click();
		Utility.clickThroughJavaScript(driver, res.TaxExemptLedgerItems);
		Wait.wait2Second();
		//Utility.clickThroughJavaScript(driver, res.TaxExemptListExpand);
		Utility.clickThroughAction(driver, res.TaxExemptListExpand);
		//res.TaxExemptListExpand.click();
		reports.SelectGivenTaxExemptLedgerItems(driver, test_steps, TaxExemptLedgerItemsOption);
		res.TaxExemptLedgerItems.click();
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <selectMarketSegmentOptionGiventhroughExcel>
	 * Description: <This method selects Market Segment Option given through Excel>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps, String
	 * marketSegmentOption> Return value: <void> Created By: <Naveen Kadthala>
	 * Created On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void selectMarketSegmentOptionGiventhroughExcel(WebDriver driver, ArrayList<String> test_steps,
			String marketSegmentOption) throws InterruptedException {
		ReportsV2 reports = new ReportsV2();
		Elements_Reports res = new Elements_Reports(driver);
		
		if (!marketSegmentOption.isEmpty()) {
			try {
				res.MarketSegment.click();
			}catch(Exception e) {
				Utility.clickThroughAction(driver, res.MarketSegment);
			}
			
			Wait.wait2Second();
			//res.MarketSegmentListExpand.click();
			//Utility.clickThroughJavaScript(driver, res.MarketSegmentListExpand);
			Utility.clickThroughAction(driver, res.MarketSegmentListExpand);
			reports.SelectGivenMarketSegmentOption(driver, test_steps, marketSegmentOption);
			try {
				res.MarketSegment.click();
			}catch(Exception e) {
				Utility.clickThroughAction(driver, res.MarketSegment);
			}
		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <selectReferralsOptionGiventhroughExcel>
	 * Description: <This method selects Referrals Option given through Excel> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps, String
	 * referralsOption> Return value: <void> Created By: <Naveen Kadthala> Created
	 * On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void selectReferralsOptionGiventhroughExcel(WebDriver driver, ArrayList<String> test_steps,
			String referralsOption) throws InterruptedException {
		ReportsV2 reports = new ReportsV2();
		Elements_Reports res = new Elements_Reports(driver);
		
		if (!referralsOption.isEmpty()) {
			// res.Referrals.click();
			Utility.clickThroughJavaScript(driver, res.Referrals);
			Wait.wait1Second();
			//res.ReferralstListExpand.click();
			Utility.clickThroughAction(driver, res.ReferralstListExpand);
			reports.SelectGivenReferralsOption(driver, test_steps, referralsOption);
			res.Referrals.click();
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <selectIncludeDataFromOptionsGiventhroughExcel>
	 * Description: <This method selects IncludeDataFrom Options given through
	 * Excel> Input parameters: <WebDriver driver, ArrayList<String> test_steps,
	 * String IncludeDataFromUsers, String IncludeDataFromProperties, String
	 * IncludeDataFromShiftTimeStartHours, String
	 * IncludeDataFromShiftTimeStartMinutes,String
	 * IncludeDataFromShiftTimeStartAmPm, String IncludeDataFromShiftTimeEndHours,
	 * String IncludeDataFromShiftTimeEndMinutes, String
	 * IncludeDataFromShiftTimeEndAmPm> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <24/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public void selectIncludeDataFromOptionsGiventhroughExcel(WebDriver driver, ArrayList<String> test_steps,
			String IncludeDataFromUsers, String IncludeDataFromShiftTimeStartHours,
			String IncludeDataFromShiftTimeStartMinutes, String IncludeDataFromShiftTimeStartAmPm,
			String IncludeDataFromShiftTimeEndHours, String IncludeDataFromShiftTimeEndMinutes,
			String IncludeDataFromShiftTimeEndAmPm) throws InterruptedException {
		ReportsV2 reports = new ReportsV2();
		Elements_Reports res = new Elements_Reports(driver);

		Wait.wait1Second();
		res.IncludeDataFrom.click();

		// select users
		reports.selectUserFromIncludeDataForm(driver, IncludeDataFromUsers, test_steps);

		// select shift start time and shift end time
		boolean condition1 = (!IncludeDataFromShiftTimeStartHours.equals(""));
		boolean condition2 = (!IncludeDataFromShiftTimeStartMinutes.equals(""));
		boolean condition3 = (!IncludeDataFromShiftTimeStartAmPm.equals(""));
		boolean condition4 = (!IncludeDataFromShiftTimeEndHours.equals(""));
		boolean condition5 = (!IncludeDataFromShiftTimeEndMinutes.equals(""));
		boolean condition6 = (!IncludeDataFromShiftTimeEndAmPm.equals(""));

		if (condition1 && condition2 && condition3 && condition4 && condition5 && condition6) {
			Utility.clickThroughAction(driver, res.buttonShiftTime);
			reports.selectShiftTimeStart(driver, IncludeDataFromShiftTimeStartHours,
					IncludeDataFromShiftTimeStartMinutes, IncludeDataFromShiftTimeStartAmPm, test_steps);
			reports.selectShiftTimeEnd(driver, IncludeDataFromShiftTimeEndHours, IncludeDataFromShiftTimeEndMinutes,
					IncludeDataFromShiftTimeEndAmPm, test_steps);
		}
		res.IncludeDataFrom.click();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <AccountSave> ' Description: <This method will Save account
	 * when created/Modification: remove unnecessary wait> ' Input
	 * parameters:(WebDriver) ' Return value: void ' Updated By: <Adhnan Ghaffar> '
	 * Updated On: <06/02/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> AccountSave(WebDriver driver, ExtentTest test, String AccountName,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Account_Save_Button), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Account_Save_Button), driver);
		CreateAccount.Account_Save_Button.click();
		test_steps.add("Click Save ");
		reportLogger.info("Click Save");
		Wait.WaitForElementUsingClassName(driver, OR.Toaster_Message);
		Wait.waitForElementToBeVisibile(By.className(OR.Toaster_Message), driver);
		String Toast_Message = CreateAccount.Toaster_Message.getText();

		String dirty = "(//img[@src='scripts/innCenter/V4/server/dirty.png'])[7]";

		if (Utility.isElementDisplayed(driver, By.xpath(dirty))
				|| Toast_Message.contains("Please fill in all the mandatory fields")) {
			reportLogger.info("************* Please fill in all the mandatory fields ************");
			test_steps.add("Please fill in all the mandatory fields");
		} else {
			test_steps.add(Toast_Message);
			reportLogger.info(Toast_Message);
			assertEquals(Toast_Message, "The account " + AccountName + " has been successfully created.",
					"New account does not create");
		}

		return test_steps;
	}

	public Double deposit(WebDriver driver, ArrayList test_steps, String IsDepositOverride,
			String DepositOverrideAmount) throws InterruptedException {
		// Wait.wait5Second();
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_DepositAmount);
		String deposit = res.CP_NewReservation_DepositAmount.getAttribute("textContent");
		test_steps.add("Deposit amount is : " + deposit);
		reportLogger.info("Deposit amount is : " + deposit);
		deposit = deposit.trim();
		char ch = deposit.charAt(0);
		deposit = deposit.replace("$", "");
		deposit = deposit.trim();
		Double d = Double.parseDouble(deposit);

		if (IsDepositOverride.equalsIgnoreCase("Yes") && d > 0) {
			Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_OverrideDeposit);
			res.CP_NewReservation_OverrideDeposit.click();
			test_steps.add("Clicking on override deposit amount");
			reportLogger.info("Clicking on override deposit amount");
			Wait.wait2Second();
			Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_OverrideDepositAmoount);
			res.CP_NewReservation_OverrideDepositAmoount.sendKeys(DepositOverrideAmount);
			test_steps.add("Override deposit amount is : " + ch + " " + DepositOverrideAmount);
			reportLogger.info("Override deposit amount is : " + ch + " " + DepositOverrideAmount);
			d = Double.parseDouble(DepositOverrideAmount.trim());
		}
		return d;
	}

	public void validate_GuestInfo(WebDriver driver, ArrayList test_steps, String Salutation, String GuestFirstName,
			String GuestLastName, String PhoneNumber, String AlternatePhone, String Email, String Country,
			String Account, String Address1, String Address2, String Address3, String State, String City,
			String PostalCode) {
		test_steps.add("******************* Guest Info fields verification **********************");
		reportLogger.info("******************* Guest Info fields verification **********************");

		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_GuestInfo_GuestName);

		String name = Salutation.trim() + " " + GuestFirstName.trim() + " " + GuestLastName.trim();
		String guestname = res.CP_GuestInfo_GuestName.getText().trim();
		reportLogger.info(guestname);
		if (guestname.equalsIgnoreCase(name.trim())) {
			test_steps.add("Reservation Guest Info GuestName field verified : " + name);
			reportLogger.info("Reservation Guest Info GuestName field verified : " + name);
		} else {
			test_steps.add("Reservation Guest Info GuestName field not found : " + name);
			reportLogger.info("Reservation Guest Info GuestName field not found : " + name);
		}

		String contactname = res.CP_GuestInfo_ContactName.getText().trim();
		reportLogger.info(contactname);
		if (contactname.equalsIgnoreCase(name.trim())) {
			test_steps.add("Reservation Guest Info ContactName field verified : " + name);
			reportLogger.info("Reservation Guest Info ContactName field verified : " + name);
		} else {
			test_steps.add("Reservation Guest Info ContactName field not found : " + name);
			reportLogger.info("Reservation Guest Info ContactName field not found : " + name);
		}

		PhoneNumber = PhoneNumber.replace("" + PhoneNumber.trim().charAt(0), "(" + PhoneNumber.trim().charAt(0));
		PhoneNumber = PhoneNumber.replace("" + PhoneNumber.trim().charAt(4), ")" + PhoneNumber.trim().charAt(4));
		PhoneNumber = PhoneNumber.replace("" + PhoneNumber.trim().charAt(5), " " + PhoneNumber.trim().charAt(5));
		PhoneNumber = PhoneNumber.replace("" + PhoneNumber.trim().charAt(9), "-" + PhoneNumber.trim().charAt(9));

		String code = null;
		if (Country.equalsIgnoreCase("United States")) {
			code = "1";
		} else if (Country.equalsIgnoreCase("United Kingdom")) {
			code = "41";
		}
		code = code + "-";
		PhoneNumber = code + PhoneNumber;
		reportLogger.info(PhoneNumber);

		String phone = res.CP_GuestInfo_Phone.getText().trim();
		if (phone.equalsIgnoreCase(PhoneNumber)) {
			test_steps.add("Reservation Guest Info PhoneNumber field verified : " + PhoneNumber);
			reportLogger.info("Reservation Guest Info PhoneNumber field verified : " + PhoneNumber);
		} else {
			test_steps.add("Reservation Guest Info PhoneNumber field not found : " + PhoneNumber);
			reportLogger.info("Reservation Guest Info PhoneNumber field not found : " + PhoneNumber);
		}

		AlternatePhone = AlternatePhone.replace("" + AlternatePhone.trim().charAt(0),
				"(" + AlternatePhone.trim().charAt(0));
		AlternatePhone = AlternatePhone.replace("" + AlternatePhone.trim().charAt(4),
				")" + AlternatePhone.trim().charAt(4));
		AlternatePhone = AlternatePhone.replace("" + AlternatePhone.trim().charAt(5),
				" " + AlternatePhone.trim().charAt(5));
		AlternatePhone = AlternatePhone.replace("" + AlternatePhone.trim().charAt(9),
				"-" + AlternatePhone.trim().charAt(9));

		AlternatePhone = code + AlternatePhone;
		reportLogger.info(AlternatePhone);

		phone = res.CP_GuestInfo_AlternatePhone.getText().trim();
		if (phone.equalsIgnoreCase(AlternatePhone)) {
			test_steps.add("Reservation Guest Info AlternatePhone field verified : " + AlternatePhone);
			reportLogger.info("Reservation Guest Info AlternatePhone field verified : " + AlternatePhone);
		} else {
			test_steps.add("Reservation Guest Info AlternatePhone field not found : " + AlternatePhone);
			reportLogger.info("Reservation Guest Info AlternatePhone field not found : " + AlternatePhone);
		}

		String mail = res.CP_GuestInfo_Email.getText().trim();
		reportLogger.info(mail);
		if (mail.equalsIgnoreCase(Email.trim())) {
			test_steps.add("Reservation Guest Info Email field verified : " + Email);
			reportLogger.info("Reservation Guest Info Email field verified : " + Email);
		} else {
			test_steps.add("Reservation Guest Info Email field not found : " + Email);
			reportLogger.info("Reservation Guest Info Email field not found : " + Email);
		}

		String account = driver
				.findElement(By.xpath(
						"//guest-info//span[contains(text(),'Gues')]/../..//label[text()='Account']/following::span"))
				.getText().trim();
		// res.CP_GuestInfo_Account.getText().trim();
		if (account.equalsIgnoreCase("-") && (Account.equalsIgnoreCase("") || Account.isEmpty())) {
			test_steps.add("Reservation Guest Info Account field verified");
			reportLogger.info("Reservation Guest Info Account field verified");
		} else if (!(account.equalsIgnoreCase("-")) && !(Account.equalsIgnoreCase("") || Account.isEmpty())) {
			if (account.contentEquals(Account.trim())) {
				test_steps.add("Reservation Guest Info Account field verified : " + Account);
				reportLogger.info("Reservation Guest Info Account field verified  : " + Account);
			} else {
				test_steps.add("Reservation Guest Info Account field not found : " + Account);
				reportLogger.info("Reservation Guest Info Account field not found : " + Account);
			}
		} else {
			test_steps.add("Reservation Guest Info Account field not found : " + Account);
			reportLogger.info("Reservation Guest Info Account field not found : " + Account);
		}

		String address1 = res.CP_GuestInfo_Address1.getText().trim();
		reportLogger.info(address1);
		if (address1.equalsIgnoreCase(Address1.trim())) {
			test_steps.add("Reservation Guest Info Address1 field verified : " + Address1);
			reportLogger.info("Reservation Guest Info Address1 field verified : " + Address1);
		} else {
			test_steps.add("Reservation Guest Info Address1 field not found : " + Address1);
			reportLogger.info("Reservation Guest Info Address1 field not found : " + Address1);
		}

		String address2 = res.CP_GuestInfo_Address2.getText().trim();
		reportLogger.info(address2);
		if (address2.equalsIgnoreCase(Address2.trim())) {
			test_steps.add("Reservation Guest Info Address2 field verified : " + Address2);
			reportLogger.info("Reservation Guest Info Address2 field verified : " + Address2);
		} else {
			test_steps.add("Reservation Guest Info Address2 field not found : " + Address2);
			reportLogger.info("Reservation Guest Info Address2 field not found : " + Address2);
		}

		String address3 = res.CP_GuestInfo_Address3.getText().trim();
		reportLogger.info(address3);
		if (address3.equalsIgnoreCase(Address3.trim())) {
			test_steps.add("Reservation Guest Info Address3 field verified : " + Address3);
			reportLogger.info("Reservation Guest Info Address3 field verified : " + Address3);
		} else {
			test_steps.add("Reservation Guest Info Address3 field not found : " + Address3);
			reportLogger.info("Reservation Guest Info Address3 field not found : " + Address3);
		}

		String city = res.CP_GuestInfo_City.getText().trim();
		reportLogger.info(city);
		if (city.equalsIgnoreCase(City.trim())) {
			test_steps.add("Reservation Guest Info City field verified : " + City);
			reportLogger.info("Reservation Guest Info City field verified : " + City);
		} else {
			test_steps.add("Reservation Guest Info City field not found : " + City);
			reportLogger.info("Reservation Guest Info City field not found : " + City);
		}

		String postalcode = res.CP_GuestInfo_PostalCode.getText().trim();
		reportLogger.info(postalcode);
		if (postalcode.equalsIgnoreCase(PostalCode.trim())) {
			test_steps.add("Reservation Guest Info PostalCode field verified : " + PostalCode);
			reportLogger.info("Reservation Guest Info PostalCode field verified : " + PostalCode);
		} else {
			test_steps.add("Reservation Guest Info PostalCode field not found : " + PostalCode);
			reportLogger.info("Reservation Guest Info PostalCode field not found : " + PostalCode);
		}

		String country = res.CP_GuestInfo_Country.getText().trim();
		reportLogger.info(country);
		if (country.equalsIgnoreCase(Country.trim())) {
			test_steps.add("Reservation Guest Info Country field verified : " + Country);
			reportLogger.info("Reservation Guest Info Country field verified : " + Country);
		} else {
			test_steps.add("Reservation Guest Info Country field not found : " + Country);
			reportLogger.info("Reservation Guest Info Country field not found : " + Country);
		}

		String state = res.CP_GuestInfo_State.getText().trim();
		reportLogger.info(state);

		if (!(state.equalsIgnoreCase("") || state.isEmpty())) {
			if (state.equalsIgnoreCase(State.trim())) {
				test_steps.add("Reservation Guest Info State field verified : " + State);
				reportLogger.info("Reservation Guest Info State field verified : " + State);
			} else {
				test_steps.add("Reservation Guest Info State field not found : " + State);
				reportLogger.info("Reservation Guest Info State field not found : " + State);
			}
		}
	}
	// **********************//

	public ArrayList<String> selectRoom(WebDriver driver, ArrayList<String> test_steps, String RoomClass,
			String IsAssign, String Account) throws InterruptedException {
		String room1 = "(//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
				+ "')]//..//..//div[2]//span)[1]";
		String room = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
				+ "')]//..//..//div[2]//span";
		System.out.println("IsAssign: " + IsAssign);

		String rooms;
		try {
			Wait.waitForElementToBeVisibile(By.xpath(room1), driver, 20);   //Wait.explicit_wait_xpath(driver, room1);
			rooms = driver.findElement(By.xpath(room1)).getText();
		}catch(Exception e) {
			Wait.waitForElementToBeVisibile(By.xpath(room), driver, 20);   //Wait.WaitForElement(driver, room);
			rooms = driver.findElement(By.xpath(room)).getText();
		}
		
		reportLogger.info(rooms);
		String[] roomsCount = rooms.split(" ");
		int count = Integer.parseInt(roomsCount[0].trim());
		if (count > 0) {
			String sel = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + RoomClass
					+ "']//..//..//..//following-sibling::div//div//select";

			String rulessize = "//section[@class='ir-roomClassDetails manual-override']//span[text()='"

					+ RoomClass.trim() + "']/following-sibling::span";
			reportLogger.info(rulessize);

			int ruleCount = driver.findElements(By.xpath(rulessize)).size();
			WebElement ele = driver.findElement(By.xpath(sel));
			test_steps.add("Selected room class : " + RoomClass);
			reportLogger.info("Selected room class : " + RoomClass);
			if (IsAssign.equalsIgnoreCase("No")) {
				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[text()='"
						+ RoomClass
						+ "']//..//..//..//following-sibling::div//div//select//following-sibling::div//button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String unAssign = "(//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div//ul//span[text()='Unassigned'])";
				Wait.WaitForElement(driver, unAssign);
				driver.findElement(By.xpath(unAssign)).click();
				test_steps.add("Selected room number : Unassigned");
				reportLogger.info("Selected room number : Unassigned");
			} else if (IsAssign.equalsIgnoreCase("Yes")) {

				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div/button";
				Wait.waitForElementToBeClickable(By.xpath(expand), driver, 20);
				Wait.waitForElementToBeClickable(By.xpath(expand), driver);
				WebElement elementExpand = driver.findElement(By.xpath(expand));
				Utility.ScrollToElement_NoWait(elementExpand, driver);
				elementExpand.click();

				String roomnum = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div//ul//li//span[@class='text']";
				reportLogger.info(roomnum);
				List<WebElement> getRoomNumber = driver.findElements(By.xpath(roomnum));
				System.out.println(getRoomNumber.size());
				String getMinimumNumber = getRoomNumber.get(1).getText();
				System.out.println("getMinimumNumber: " + getMinimumNumber);

				String getMaxmumNumber = getRoomNumber.get(getRoomNumber.size() - 1).getAttribute("textContent");
				System.out.println("getMaxmumNumber: " + getMaxmumNumber);
				getMaxmumNumber = getMaxmumNumber.trim();
				int maxNumber = Integer.parseInt(getMaxmumNumber);
				int minNumber = Integer.parseInt(getMinimumNumber);

				System.out.println("maxNumber : " + maxNumber);
				System.out.println("maxNumber : " + maxNumber);

				Random random = new Random();
				int randomNumber = random.nextInt(maxNumber - minNumber) + minNumber;
				IsAssign = String.valueOf(randomNumber);
				boolean isBreak = false;
				for (int j = 0; j < 5; j++) {
					System.out.println("randomNumber: " + randomNumber);
					for (int i = 1; i < getRoomNumber.size(); i++) {
						// System.out.println("in loop " + i);
						String getRoomNumberFromOptions = getRoomNumber.get(i).getText();
						getRoomNumberFromOptions = getRoomNumberFromOptions.trim();
						if (getRoomNumberFromOptions.equals(IsAssign)) {
							isBreak = true;
							break;
						}

					}
					if (isBreak == false) {
						random = new Random();
						randomNumber = random.nextInt(maxNumber - minNumber) + minNumber;
						IsAssign = String.valueOf(randomNumber);
					}
					if (isBreak) {
						break;
					}

				}
				System.out.print(" Random Number is :" + randomNumber);

				String assignRoomNo = "(//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div//ul//span[text()='"
						+ IsAssign + "'])";
				Wait.waitForElementToBeClickable(By.xpath(assignRoomNo), driver, 10);
				driver.findElement(By.xpath(assignRoomNo)).click();
				test_steps.add("Selecting room <b>" + IsAssign + "</b> from <b>" + RoomClass + "</b> room class");
				reportLogger.info("Selecting room <b>" + IsAssign + "</b> from <b>" + RoomClass + "</b> room class");

			} else {

				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div//button";
				Wait.waitForElementToBeClickable(By.xpath(expand), driver, 20);
				Wait.waitForElementToBeClickable(By.xpath(expand), driver);
				WebElement elementExpand = driver.findElement(By.xpath(expand));
				Utility.ScrollToElement_NoWait(elementExpand, driver);
				elementExpand.click();

				String roomnum = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div//ul//li//span[@class='text']";
				System.out.println(roomnum);
				List<WebElement> getRoomNumber = driver.findElements(By.xpath(roomnum));
				System.out.println(getRoomNumber.size());
				String getMinimumNumber = getRoomNumber.get(1).getText();
				System.out.println("getMinimumNumber: " + getMinimumNumber);

				String getMaxmumNumber = getRoomNumber.get(getRoomNumber.size() - 1).getAttribute("textContent");
				System.out.println("getMaxmumNumber: " + getMaxmumNumber);
				getMaxmumNumber = getMaxmumNumber.trim();
				int maxNumber = Integer.parseInt(getMaxmumNumber);
				int minNumber = Integer.parseInt(getMinimumNumber);

				System.out.println("maxNumber : " + maxNumber);
				System.out.println("maxNumber : " + maxNumber);

				Random random = new Random();
				int randomNumber = random.nextInt(maxNumber - minNumber) + minNumber;
				IsAssign = String.valueOf(randomNumber);
				boolean isBreak = false;
				for (int j = 0; j < 5; j++) {
					System.out.println("randomNumber: " + randomNumber);
					for (int i = 1; i < getRoomNumber.size(); i++) {
						System.out.println("in loop " + i);
						String getRoomNumberFromOptions = getRoomNumber.get(i).getText();
						getRoomNumberFromOptions = getRoomNumberFromOptions.trim();
						if (getRoomNumberFromOptions.equals(IsAssign)) {
							isBreak = true;
							break;
						}

					}
					if (isBreak == false) {
						random = new Random();
						randomNumber = random.nextInt(maxNumber - minNumber) + minNumber;
						IsAssign = String.valueOf(randomNumber);
					}
					if (isBreak) {
						break;
					}

				}
				System.out.print(" Random Number is :" + randomNumber);

				String assignRoomNo = "(//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div//ul//span[text()='"
						+ IsAssign + "'])";
				Wait.waitForElementToBeClickable(By.xpath(assignRoomNo), driver, 10);
				driver.findElement(By.xpath(assignRoomNo)).click();
				test_steps.add("Selecting room <b>" + IsAssign + "</b> from <b>" + RoomClass + "</b> room class");
				reportLogger.info("Selecting room <b>" + IsAssign + "</b> from <b>" + RoomClass + "</b> room class");
			}

			String select = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
					+ RoomClass + "')]//..//..//..//following-sibling::div//span[contains(text(),'SELECT')]";
			Wait.WaitForElement(driver, select);
			driver.findElement(By.xpath(select)).click();

			String loading = "(//div[@class='ir-loader-in'])[2]";
			int counter = 0;
			while (true) {
				if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
					break;
				} else if (counter == 10) {
					break;
				} else {
					counter++;
					Wait.wait2Second();
				}
			}

			reportLogger.info("Waited to loading symbol");

			reportLogger.info("Rule Count : " + ruleCount);

			// System.out.println("Waited to loading symbol");

			// System.out.println("Rule Count : " + ruleCount);

			/*
			 * if (ruleCount > 1) { Wait.wait5Second(); String rules =
			 * "//p[text()='Selecting this room will violate the following rules']/../..//button[text()='Yes']"
			 * ;
			 * 
			 * if (driver.findElements(By.xpath(rules)).size() > 0) { Wait.wait2Second();
			 * driver.findElement(By.xpath(rules)).click(); test_steps.add(
			 * "Selecting this room will violate the following rules : Are you sure you want to proceed? : yes"
			 * ); reslogger.info(
			 * "Selecting this room will violate the following rules : Are you sure you want to proceed? : yes"
			 * );
			 * 
			 * loading = "(//div[@class='ir-loader-in'])[2]"; counter = 0; while (true) { if
			 * (!driver.findElement(By.xpath(loading)).isDisplayed()) { break; } else if
			 * (counter == 4) { break; } else { counter++; Wait.wait2Second(); } } } }
			 */ 
			if (!(Account.isEmpty() || Account.equalsIgnoreCase(""))) {
				String policy = "//p[contains(text(),'Based on the changes made')]/../..//button[text()='Yes']";
				Wait.WaitForElement(driver, policy);
				driver.findElement(By.xpath(policy)).click();
				test_steps.add("Based on the changes made, new policies are applicable.? : yes");
				reportLogger.info("Based on the changes made, new policies are applicable.? : yes");

				loading = "(//div[@class='ir-loader-in'])[2]";
				counter = 0;
				while (true) {
					if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
						break;
					} else if (counter == 3) {
						break;
					} else {
						counter++;
						Wait.wait2Second();
					}
				}
			}

		} else {
			test_steps.add("Rooms Count <=0 for " + RoomClass + " for specified date");
			reportLogger.info("Rooms Count <=0 for " + RoomClass + " for specified date");
		}
		return test_steps;
	}

	public boolean verify_TaskSections(WebDriver driver, ArrayList test_steps) {
		test_steps.add("******************* Verify Task section **********************");
		reportLogger.info("******************* Verify Task section **********************");
		String task = "//button[text()='ADD TASK']";
		if (driver.findElement(By.xpath(task)).isEnabled()) {
			test_steps.add("Task Section is displayed");
			reportLogger.info("Task Section is displayed");
			return true;
		} else {
			test_steps.add("Task Section is not displayed");
			reportLogger.info("Task Section is not displayed");
			return false;
		}
	}

	// Ledger Run Report

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateStandardReportHeaderExistence>
	 * Description: <This method validates existence of Standard Report Header>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateStandardReportHeaderExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			String actual = res.GRHeaderTitle.getText().trim();
			String expected = null;
			if (Utility.isElementDisplayed(driver, By.xpath(
					"//*[@class='ReportHeader_rightContent_BYZVl']/ul/li/div/div/span[@class='ant-select-selection-item']"))) {
				expected = "Ledger Balances Report -" + driver.findElement(By.xpath(
						"//*[@class='ReportHeader_rightContent_BYZVl']/ul/li/div/div/span[@class='ant-select-selection-item']"))
						.getText().trim();
			} else {
				expected = "Ledger Balances Report -" + driver
						.findElement(By.xpath(
								"//*[@class='ReportHeader_rightContent_BYZVl']/ul/li[contains(@class,'property')]/div"))
						.getText().trim();
			}
			reportLogger.info("Success - Standard Report Header existence validation");
			test_steps.add("Success - Standard Report Header existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Standard Report Header existence validation");
			test_steps.add("AssertionError - FAIL - Standard Report Header existence validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Standard Report Header existence validation");
			test_steps.add("AssertionError - FAIL - Standard Report Header existence validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReportTypeCaptionExistence>
	 * Description: <This method validates existence of Report Type> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReportTypeCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRReportTypeCaption.isDisplayed();
			reportLogger.info("Success - Report Type Caption existence validation");
			test_steps.add("Success - Report Type Caption existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Report Type Caption existence validation");
			test_steps.add("AssertionError - Report Type Caption existence validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Report Type Caption existence validation");
			test_steps.add("AssertionError - Report Type Caption existence validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReportTypeValue> Description: <This
	 * method validates value of Report Type> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReportTypeValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			String s = res.GRReportTypeValue.getText();
			if (s.equals("Ledger Balances Report")) {
				reportLogger.info("Success - Report Type value validation");
				test_steps.add("Success - Report Type value validation");
			} else {
				reportLogger.info("FAIL - Report Type value validation");
				test_steps.add("AssertionError - FAIL - Report Type value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Report Type value validation");
			test_steps.add("AssertionError - FAIL - Report Type value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Report Type value validation");
			test_steps.add("AssertionError - FAIL - Report Type value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateDateRangeCaptionExistence>
	 * Description: <This method validates Date Range Captino existence> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateDateRangeCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRDateRangeCaption.isDisplayed();
			reportLogger.info("Success - Date Range Caption validation");
			test_steps.add("Success - Date Range Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Date Range Caption validation");
			test_steps.add("AssertionError - FAIL - Date Range Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Date Range Caption validation");
			test_steps.add("AssertionError - FAIL - Date Range Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateDateRangeValue> Description: <This
	 * method validates value of Date Range in Report generated> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateDateRangeValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String s1 = driver
					.findElement(By
							.xpath("//div[contains(@class,'DateRange')]/div/span[@class='ant-select-selection-item']"))
					.getText().trim();

			String startDate = getStartDate(driver, test_steps);
			Date date1 = new SimpleDateFormat("MMM d, yyy").parse(startDate);
			String s2 = new SimpleDateFormat("MMM dd, yyy").format(date1);

			String endDate = getEndDate(driver, test_steps);
			Date date2 = new SimpleDateFormat("MMM d, yyy").parse(startDate);
			String s3 = new SimpleDateFormat("MMM dd, yyy").format(date2);

			String expValue = s1 + " | " + s2 + " to " + s3;
			String actValue = res.GRDateRangeValue.getText();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Date Range value validation");
				test_steps.add("Success - Date Range value validation");
			} else {
				reportLogger.info("Fail - Date Range value validation");
				test_steps.add("AssertionError - Fail - Date Range value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("Fail - Date Range value validation");
			test_steps.add("AssertionError - Fail - Date Range value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("Fail - Date Range value validation");
			test_steps.add("AssertionError - Fail - Date Range value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAccountTypeCaptionexistence>
	 * Description: <This method validates Account Type Caption existence in Report
	 * generated> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateAccountTypeCaptionexistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRAccountTypeCaption.isDisplayed();
			reportLogger.info("Success - Account Type Caption validation");
			test_steps.add("Success - Account Type Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Account Type Caption validation");
			test_steps.add("AssertionError - FAIL - Account Type Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Account Type Caption validation");
			test_steps.add("AssertionError - FAIL - Account Type Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateAccountTypeValue> Description: <This
	 * method validates value of Account Type in Report generated> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateAccountTypeValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			String actValue = res.GRAccountTypeValue.getText();
			int x = driver.findElements(By.xpath("//div[@id='accountTypes']/label[contains(@class,'checked')]")).size();
			if (x == 0 && actValue.equals("None")) {
				reportLogger.info("Success - Account Type value validation");
				test_steps.add("Success - Account Type value validation");
			} else if (x == 8 && actValue.equals("All")) {
				reportLogger.info("Success - Account Type value validation");
				test_steps.add("Success - Account Type value validation");
			} else if (x > 0 && x < 8) {
				List<WebElement> l = driver
						.findElements(By.xpath("//div[@id='accountTypes']/label[contains(@class,'checked')]"));
				String value = null;
				for (int i = 1; i < l.size(); i++) {
					value = l.get(0).getText() + " | " + l.get(i).getText();
					if (actValue.equals(value)) {
						reportLogger.info("Success - Account Type value validation");
						test_steps.add("Success - Account Type value validation");
					} else {
						reportLogger.info("FAIL - Account Type value validation");
						test_steps.add("AssertionError - FAIL - Account Type value validation-<Br>");
					}
				}
			} else {
				reportLogger.info("FAIL - Account Type value validation");
				test_steps.add("AssertionError - FAIL - Account Type value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Account Type value validation");
			test_steps.add("AssertionError - FAIL - Account Type value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Account Type value validation");
			test_steps.add("AssertionError - FAIL - Account Type value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReservationStatusCaptionexistence>
	 * Description: <This method validates Reservation Status Caption existence in
	 * Report generated> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReservationStatusCaptionexistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRReservationStatusCaption.isDisplayed();
			reportLogger.info("Success - Reservation Status Caption validation");
			test_steps.add("Success - Reservation Status Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Reservation Status Caption validation");
			test_steps.add("AssertionError - FAIL - Reservation Status Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Reservation Status Caption validation");
			test_steps.add("AssertionError - FAIL - Reservation Status Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReservationStatusValue> Description:
	 * <This method validates value of Reservation Status in Report generated> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReservationStatusValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			String actValue = res.GRReservationStatusValue.getText();
			int x = driver.findElements(By.xpath("//div[@id='reservationStatus']/label[contains(@class,'checked')]"))
					.size();
			if (x == 0 && actValue.equals("None")) {
				reportLogger.info("Success - Reservation Status value validation");
				test_steps.add("Success - Reservation Status value validation");
			} else if (x == 10 && actValue.equals("All")) {
				reportLogger.info("Success - Reservation Status value validation");
				test_steps.add("Success - Reservation Status value validation");
			} else if (x > 0 && x < 10) {
				List<WebElement> l = driver
						.findElements(By.xpath("//div[@id='reservationStatus']/label[contains(@class,'checked')]"));
				String value = null;
				for (int i = 1; i < l.size(); i++) {
					value = l.get(0).getText() + " | " + l.get(i).getText();
					if (actValue.equals(value)) {
						reportLogger.info("Success - Reservation Status value validation");
						test_steps.add("Success - Reservation Status value validation");
					} else {
						reportLogger.info("FAIL - Reservation Status value validation");
						test_steps.add("AssertionError - FAIL - Reservation Status value validation-<Br>");
					}
				}
			} else {
				reportLogger.info("FAIL - Reservation Status value validation");
				test_steps.add("AssertionError - FAIL - Reservation Status value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Reservation Status value validation");
			test_steps.add("AssertionError - FAIL - Reservation Status value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Reservation Status value validation");
			test_steps.add("AssertionError - FAIL - Reservation Status value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateItemStatusCaptionexistence>
	 * Description: <This method validates Item Status Caption existence in Report
	 * generated> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateItemStatusCaptionexistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRItemStatusCaption.isDisplayed();
			reportLogger.info("Success - Item Status Caption validation");
			test_steps.add("Success - Item Status Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Item Status Caption validation");
			test_steps.add("AssertionError - FAIL - Item Status Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Item Status Caption validation");
			test_steps.add("AssertionError - FAIL - Item Status Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateItemStatusValue> Description: <This
	 * method validates value of Item Status in Report generated> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateItemStatusValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			String actValue = res.GRItemStatusValue.getText();
			int x = driver.findElements(By.xpath("//div[@id='itemStatus']/label[contains(@class,'checked')]")).size();
			if (x == 0 && actValue.equals("None")) {
				reportLogger.info("Success - Item Status value validation");
				test_steps.add("Success - Item Status value validation");
			} else if (x == 4 && actValue.equals("All")) {
				reportLogger.info("Success - Item Status value validation");
				test_steps.add("Success - Item Status value validation");
			} else if (x > 0 && x < 4) {
				List<WebElement> l = driver
						.findElements(By.xpath("//div[@id='itemStatus']/label[contains(@class,'checked')]"));
				String value = l.get(0).getAttribute("textContent");
				for (int i = 1; i < l.size(); i++) {
					value = value + " | " + l.get(i).getAttribute("textContent");
				}
				if (actValue.equals(value)) {
					reportLogger.info("Success - Item Status value validation");
					test_steps.add("Success - Item Status value validation");
				} else {
					reportLogger.info("FAIL - Item Status value validation");
					test_steps.add("AssertionError - FAIL - Item Status value validation-<Br>");
				}

			} else {
				reportLogger.info("FAIL - Item Status value validation");
				test_steps.add("AssertionError - FAIL - Item Status value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Item Status value validation");
			test_steps.add("AssertionError - FAIL - Item Status value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Item Status value validation");
			test_steps.add("AssertionError - FAIL - Item Status value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateIncludeDataFromCaptionExistence>
	 * Description: <This method validates Include Data From Caption existence>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateIncludeDataFromCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRIncludeDataFromCaption.isDisplayed();
			reportLogger.info("Success - Include Data From Caption validation");
			test_steps.add("Success - Include Data From Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Include Data From Caption validation");
			test_steps.add("AssertionError - FAIL - Include Data From Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Include Data From Caption validation");
			test_steps.add("AssertionError - FAIL - Include Data From Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateIncludeDataFromValue> Description:
	 * <This method validates value of Include Data From in Report generated> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateIncludeDataFromValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String expValue = driver.findElement(By.xpath(
					"//div[@class='CollapseHeader_textOverflow_INIKj'][contains(text(),'All Users | 00:00 to 00:00')]"))
					.getText().trim();
			String actValue = res.GRIncludeDataFromValue.getText().replace("-", "to").trim();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Include Data From value validation");
				test_steps.add("Success - Include Data From value validation");
			} else {
				reportLogger.info("FAIL - Include Data From value validation");
				test_steps.add("AssertionError - FAIL - Include Data From value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Include Data From value validation");
			test_steps.add("AssertionError - FAIL - Include Data From value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Include Data From value validation");
			test_steps.add("AssertionError - FAIL - Include Data From value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateSortReportByCaptionExistence>
	 * Description: <This method validates Sort Report By Caption existence> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateSortReportByCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRSortReportByCaption.isDisplayed();
			reportLogger.info("Success - Sort Report By Caption validation");
			test_steps.add("Success - Sort Report By Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Sort Report By Caption validation");
			test_steps.add("AssertionError - FAIL - Sort Report By Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Sort Report By Caption validation");
			test_steps.add("AssertionError - FAIL - Sort Report By Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateSortReportByValue> Description: <This
	 * method validates value of Sort Report By in Report generated> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateSortReportByValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String expValue = driver.findElement(By.xpath(
					"//b[text()='Sort Report By']/../../following-sibling::div//span[@class='ant-select-selection-item']"))
					.getText().trim();
			String actValue = res.GRSortReportByValue.getText().trim();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Sort Report By value validation");
				test_steps.add("Success - Sort Report By value validation");
			} else {
				reportLogger.info("FAIL - Sort Report By value validation");
				test_steps.add("AssertionError - FAIL - Sort Report By value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Sort Report By value validation");
			test_steps.add("AssertionError - FAIL - Sort Report By value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Sort Report By value validation");
			test_steps.add("AssertionError - FAIL - Sort Report By value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateGroupRowsByCaptionExistence>
	 * Description: <This method validates Group Rows By Caption existence> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateGroupRowstByCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRGroupRowsByCaption.isDisplayed();
			reportLogger.info("Success - Group Rows By Caption validation");
			test_steps.add("Success - Group Rows By Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Group Rows By Caption validation");
			test_steps.add("AssertionError - FAIL - Group Rows By Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Group Rows By Caption validation");
			test_steps.add("AssertionError - FAIL - Group Rows By Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateGroupsBytByValue> Description: <This
	 * method validates value of Sort Report By in Report generated> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateGroupsByValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String expValue = driver.findElement(By.xpath(
					"//b[text()='Group Rows By']/../../following-sibling::div//span[@class='ant-select-selection-item']"))
					.getText().trim();
			String actValue = res.GRGroupRowsByValue.getText().trim();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Group Rows By value validation");
				test_steps.add("Success - Group Rows By value validation");
			} else {
				reportLogger.info("FAIL - Group Rows By value validation");
				test_steps.add("AssertionError - FAIL - Group Rows By value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Group Rows By value validation");
			test_steps.add("AssertionError - FAIL - Group Rows By value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Group Rows By value validation");
			test_steps.add("AssertionError - FAIL - Date Range value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateTaxExemptCaptionExistence>
	 * Description: <This method validates Tax Exempt Caption existence> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateTaxExemptCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRTaxExemptCaption.isDisplayed();
			reportLogger.info("Success - Tax Exempt Caption validation");
			test_steps.add("Success - Tax Exempt Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Tax Exempt Caption validation");
			test_steps.add("AssertionError - FAIL - Tax Exempt Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Tax Exempt Caption validation");
			test_steps.add("AssertionError - FAIL - Tax Exempt Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateTaxExemptmValue> Description: <This
	 * method validates value of Tax Exempt in Report generated> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateTaxExemptmValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String selectedTaxExemptOption = "//span[text()='Tax Exempt Ledger Items']/../../..//following-sibling::div[@class='ant-collapse-content ant-collapse-content-active']//span[@class='ant-select-selection-item']";
			if (!Utility.isElementDisplayed(driver, By.xpath(selectedTaxExemptOption))) {
				res.TaxExemptLedgerItems.click();
			}

			String expValue = driver.findElement(By.xpath(selectedTaxExemptOption)).getText().trim();

			String actValue = res.GRTaxExemptValue.getText().trim();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Tax Exempt value validation");
				test_steps.add("Success - Tax Exempt value validation");
			} else {
				reportLogger.info("FAIL - Tax Exempt value validation");
				test_steps.add("AssertionError - FAIL - Tax Exempt value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Tax Exempt value validation");
			test_steps.add("AssertionError - FAIL - Tax Exempt value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Tax Exempt value validation");
			test_steps.add("AssertionError - FAIL - Tax Exempt value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateMarketSegmentCaptionExistence>
	 * Description: <This method validates Market Segment Caption existence> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateMarketSegmentCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRMarketSegmentCaption.isDisplayed();
			reportLogger.info("Success - Market Segment Caption validation");
			test_steps.add("Success - Market Segment Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Market Segment Caption validation");
			test_steps.add("AssertionError - FAIL - Market Segment Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Market Segment Caption validation");
			test_steps.add("AssertionError - FAIL - Market Segment Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateMarketSegmentValue> Description: <This
	 * method validates value of Market Segment in Report generated> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateMarketSegmentValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String expValue = driver
					.findElement(By.xpath(
							"//span[text()='Market Segment']/../..//div[@class='CollapseHeader_textOverflow_INIKj']"))
					.getText().trim();
			String actValue = res.GRMarketSegmentValue.getText().trim();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Market Segment value validation");
				test_steps.add("Success - Market Segment value validation");
			} else {
				reportLogger.info("FAIL - Market Segment value validation");
				test_steps.add("AssertionError - FAIL - Market Segment value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Market Segment value validation");
			test_steps.add("AssertionError - FAIL - Market Segment value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Market Segment value validation");
			test_steps.add("AssertionError - FAIL - Market Segment value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReferralsCaptionExistence>
	 * Description: <This method validates Referrals Caption existence> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReferralsCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRReferralsCaption.isDisplayed();
			reportLogger.info("Success - Referrals Caption validation");
			test_steps.add("Success - Referrals Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Referrals Caption validation");
			test_steps.add("AssertionError - FAIL - Referrals Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Referrals Caption validation");
			test_steps.add("AssertionError - FAIL - Referrals Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateReferralsValue> Description: <This
	 * method validates value of Referrals in Report generated> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateReferralsValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String expValue = driver
					.findElement(By
							.xpath("//span[text()='Referrals']/../..//div[@class='CollapseHeader_textOverflow_INIKj']"))
					.getText().trim();
			String actValue = res.GRReferralsValue.getText().trim();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Referrals value validation");
				test_steps.add("Success - Referrals value validation");
			} else {
				reportLogger.info("FAIL - Referrals value validation");
				test_steps.add("AssertionError - FAIL - Referrals value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Referrals value validation");
			test_steps.add("AssertionError - FAIL - Referrals value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Referrals value validation");
			test_steps.add("AssertionError - FAIL - Referrals value validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateExcludeZeroBalanceLedgerAccountsCaptionExistence> Description: <This
	 * method validates Exclude Zero Balance Ledger Accounts Caption existence>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateExcludeZeroBalanceLedgerAccountsCaptionExistence(WebDriver driver,
			ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRExcludeZeroBalanceLedgerAccountsCaption.isDisplayed();
			reportLogger.info("Success - Exclude Zero Balance Ledger Accounts Caption validation");
			test_steps.add("Success - Exclude Zero Balance Ledger Accounts Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Exclude Zero Balance Ledger Accounts Caption validation");
			test_steps.add("AssertionError - FAIL - Exclude Zero Balance Ledger Accounts Caption validation-<Br>"
					+ e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Exclude Zero Balance Ledger Accounts Caption validation");
			test_steps.add("AssertionError - FAIL - Exclude Zero Balance Ledger Accounts Caption validation-<Br>"
					+ e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateExcludeZeroBalanceLedgerAccountsValue>
	 * Description: <This method validates value of Exclude Zero Balance Ledger
	 * Accounts in Report generated> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateExcludeZeroBalanceLedgerAccountsValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			String expValue = driver.findElement(By.xpath(
					"//b[text()='Exclude Zero Balance Ledger Accounts']/../../..//label[contains(@class,'checked')]"))
					.getText().trim();
			String actValue = res.GRExcludeZeroBalanceLedgerAccountsValue.getText().trim();

			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Exclude Zero Balance Ledger Accounts value validation");
				test_steps.add("Success - Exclude Zero Balance Ledger Accounts value validation");
			} else {
				reportLogger.info("FAIL - Exclude Zero Balance Ledger Accounts value validation");
				test_steps.add("AssertionError - FAIL - Exclude Zero Balance Ledger Accounts value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Exclude Zero Balance Ledger Accounts value validation");
			test_steps.add("AssertionError - FAIL - Exclude Zero Balance Ledger Accounts value validation-<Br>"
					+ e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Exclude Zero Balance Ledger Accounts value validation");
			test_steps.add("AssertionError - FAIL - Exclude Zero Balance Ledger Accounts value validation-<Br>"
					+ e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateIncludedLedgerAccountsCaptionExistence> Description: <This method
	 * validates Included Ledger Accounts Caption existence> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateIncludedLedgerAccountsCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRIncludedLedgerAccountsCaption.isDisplayed();
			reportLogger.info("Success - Included Ledger Accounts Caption validation");
			test_steps.add("Success - Included Ledger Accounts Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Included Ledger Accounts Caption validation");
			test_steps.add("AssertionError - FAIL - Included Ledger Accounts Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Included Ledger Accounts Caption validation");
			test_steps.add("AssertionError - FAIL - Included Ledger Accounts Caption validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateIncludedLedgerAccountsInStandardView>
	 * Description: <This method validates Included Ledger Accounts in StandardView
	 * in generated Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/01/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateLedgerAccountsInStandardView(WebDriver driver, ArrayList<String> test_steps) {
		try {

			// actual list
			String s = driver
					.findElement(By.xpath("//span[text()='Included Ledger Accounts: ']/following-sibling::span"))
					.getText();
			String s1[] = s.split("-");
//			for (int i = 0; i < s1.length; i++) {
//				System.out.println(i + "=" + s1[i]);
//			}
			ArrayList<String> actuallist = new ArrayList<String>();

			for (int i = 1; i < s1.length - 1; i = i + 2) {

				actuallist.add(s1[i].trim() + " - " + s1[i + 1].trim());
			}

			// expected list
			ArrayList<String> expectedlist = new ArrayList<String>();
			String value = null;
			List<WebElement> l = driver.findElements(By.xpath(
					"//strong[text()='Select Inputs']/../../../following-sibling::div//div[@class='ant-col ant-col-xs-12']"));
			for (int i = 1; i <= l.size(); i++) {
				String s2 = driver.findElement(By.xpath(
						"//strong[text()='Select Inputs']/../../../following-sibling::div//div[@class='ant-col ant-col-xs-12']["
								+ i + "]"))
						.getText();
				String s3[] = s2.replaceAll("[(/)]", "/").split("/");
				String caption = s3[0];
				int selectedCount = Integer.parseInt(s3[1].trim());
				int totalCount = Integer.parseInt(s3[2]);
				if (selectedCount == 0) {
					System.out.println("selected count is zero, not adding to list");
				} else {
					if (selectedCount == totalCount) {
						value = "All";
						expectedlist.add(caption + " - " + value);
					} else if (selectedCount < totalCount) {
						value = "" + selectedCount;
						expectedlist.add(caption + " - " + value);
					}
				}
			}

			Collections.sort(actuallist);
			Collections.sort(expectedlist);

			if (actuallist.equals(expectedlist)) {
				reportLogger.info("Success - Included Ledger Accounts standard view validation");
				test_steps.add("Success - Included Ledger Accounts standard view validation");
			} else {
				reportLogger.info("FAIL - Included Ledger Accounts standard view validation");
				test_steps.add("AssertionError - FAIL - Included Ledger Accounts standard view validation-<Br>");
			}
		}

		catch (Exception e) {
			reportLogger.info("FAIL - Included Ledger Accounts standard view validation");
			test_steps.add(
					"AssertionError - FAIL - Included Ledger Accounts standard view validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Included Ledger Accounts standard view validation");
			test_steps.add(
					"AssertionError - FAIL - Included Ledger Accounts standard view validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getLedgerAccountMainCategoryAmount>
	 * Description: <This method gets Ledger Account Category Amount from Summary
	 * view in generated Report> Input parameters: <WebDriver driver, String
	 * MainCategory> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public int getLedgerAccountMainCategoryAmount(WebDriver driver, String MainCategory) {
		String s = driver.findElement(
				By.xpath("//div[text()='Ledger Account Category']/../../../following-sibling::tbody/tr/td[text()='"
						+ MainCategory + "']/following-sibling::td/span"))
				.getText().replace("$", "").trim();
		int i = (int) Double.parseDouble(s);
		return i;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getLedgerAccountSubCategoryAmount>
	 * Description: <This method gets Ledger Account Category Amount from Summary
	 * view in generated Report> Input parameters: <WebDriver driver, String
	 * MainCategory> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public int getLedgerAccountSubCategoryAmount(WebDriver driver, String MainCategory, String subCategory) {
		String s = driver.findElement(By.xpath("//th[text()='" + MainCategory
				+ "']/../../following-sibling::tbody/tr/td[text()='" + subCategory + "']/following-sibling::td/span"))
				.getText().replace("$", "").trim();
		int i = (int) Double.parseDouble(s);
		return i;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <getCategoryTotalAmount> Description: <This
	 * method gets Ledger Account Category Amount from Summary view in generated
	 * Report> Input parameters: <WebDriver driver, String Category> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/02/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public int getCategoryTotalAmount(WebDriver driver, String Category) {
		String s = driver.findElement(By.xpath("//td[text()='" + Category + "']/following-sibling::td/div/span"))
				.getText().replace("$", "").trim();
		int i = (int) Double.parseDouble(s);
		return i;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateSummaryViewHeaderExistence>
	 * Description: <This method validates Summary view Header existence in
	 * generated Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateSummaryViewHeaderExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRSummaryViewHeader.isDisplayed();
			reportLogger.info("Success - Summary view Header existence validation");
			test_steps.add("Success - Summary view Header existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Summary view Header existence validation");
			test_steps.add("AssertionError - FAIL - Summary view Header existence validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Summary view Header existence validation");
			test_steps.add("AssertionError - FAIL - Summary view Header existence validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateLedgerAccountCategoryExistence>
	 * Description: <This method validates Ledger Account Category existence in
	 * generated Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateLedgerAccountCategoryExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRLedgerAccountCategory.isDisplayed();
			reportLogger.info("Success - Ledger Account Category existence validation");
			test_steps.add("Success - Ledger Account Category existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Ledger Account Category existence validation");
			test_steps.add("AssertionError - FAIL - Ledger Account Category existence validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Ledger Account Category existence validation");
			test_steps.add("AssertionError - FAIL - Ledger Account Category existence validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateLedgerAccountCategoryBalanceExistence>
	 * Description: <This method validates Ledger Account Category Balance existence
	 * in generated Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateLedgerAccountCategoryBalanceExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRLedgerAccountCategoryBalance.isDisplayed();
			reportLogger.info("Success - Ledger Account Category Balance existence validation");
			test_steps.add("Success - Ledger Account Category Balance existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Ledger Account Category Balance existence validation");
			test_steps.add(
					"AssertionError - FAIL - Ledger Account Category Balance existence validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Ledger Account Category Balance existence validation");
			test_steps.add(
					"AssertionError - FAIL - Ledger Account Category Balance existence validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateLedgerAccountCategoryTotalCaptionExistence> Description: <This
	 * method validates Ledger Account Category Total Caption existence in generated
	 * Report> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateLedgerAccountCategoryTotalCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRLedgerAccountCategoryTotalCaption.isDisplayed();
			reportLogger.info("Success - Ledger Account Category Total Caption existence validation");
			test_steps.add("Success - Ledger Account Category Total Caption existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Ledger Account Category Total Caption existence validation");
			test_steps.add("AssertionError - FAIL - Ledger Account Category Total Caption existence validation-<Br>"
					+ e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Ledger Account Category Total Caption existence validation");
			test_steps.add("AssertionError - FAIL - Ledger Account Category Total Caption existence validation-<Br>"
					+ e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateLedgerAccountCategoryToolTip>
	 * Description: <This method validates Ledger Account Category Tool Tip in
	 * generated Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateLedgerAccountCategoryToolTip(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			Utility.hoverOnElement(driver, res.GRLedgerAccountCategoryToolTipIcon);
			Wait.explicit_wait_visibilityof_webelement(res.GRLedgerAccountCategoryToolTipHeader, driver);

			String act1 = res.GRLedgerAccountCategoryToolTipHeader.getText().trim();
			String act2 = res.GRLedgerAccountCategoryToolTipContent.getText().trim();

			String exp1 = "Ledger Account Category";
			String exp2 = "Each ledger account is divided into different categories. The summary section of the report displays the total amount of each ledger account category. The detailed section of the report uses the user inputs given for sorting and grouping of data and displays the total for each individual ledger account while also displaying the total revenue for that ledger account category, for the specified date range.";

			if ((exp1.equals(act1)) && (exp2.equals(act2))) {
				reportLogger.info("Success - Ledger Account Category tooltip validation");
				test_steps.add("Sucess - Ledger Account Category tooltip validation");
			} else {
				reportLogger.info("FAIL - Ledger Account Category tooltip validation");
				test_steps.add("AssertionError - FAIL - Ledger Account Category tooltip validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Ledger Account Category tooltip validation");
			test_steps.add("AssertionError - FAIL - Ledger Account Category tooltip validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Ledger Account Category tooltip validation");
			test_steps.add("AssertionError - FAIL - Ledger Account Category tooltip validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateLedgerScrollTopFunctionality>
	 * Description: <This method validates Scroll Top Functionality> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/07/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateLedgerScrollTopFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			Utility.ScrollToEnd(driver);
			res.ScrollToTop.click();
			boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Edit));
			boolean condition2 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Collapse));
			if (condition1 || condition2) {
				reportLogger.info("Success - Scroll Top Functionality validation");
				test_steps.add("Success - Scroll Top Functionality validation");
			} else {
				reportLogger.info("FAIL - Scroll Top Functionality validation");
				test_steps.add("AssertionError - FAIL - Scroll Top Functionality validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Scroll Top Functionality validation");
			test_steps.add("AssertionError - FAIL - Scroll Top Functionality validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Scroll Top Functionality validation");
			test_steps.add("AssertionError - FAIL - Scroll Top Functionality validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <ValidateAllReservationstooltips> Description:
	 * <This method validates tool tips of all reservations> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void ValidateAllReservationstooltips(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			List<WebElement> l = driver
					.findElements(By.xpath("//div[text()='Reservation']/span/span[@aria-label='info-circle']"));
			for (int i = 1; i <= l.size(); i++) {

				Utility.hoverOnElement(driver, driver.findElement(
						By.xpath("(//div[text()='Reservation']/span/span[@aria-label='info-circle'])[" + i + "]")));
				Wait.explicit_wait_visibilityof_webelement(
						driver.findElement(By.xpath("(//b[text()='Reservation: '])[" + i + "]")), driver);

				String act1 = driver.findElement(By.xpath("(//b[text()='Reservation: '])[" + i + "]")).getText().trim();
				String act2 = driver
						.findElement(By.xpath("//p[contains(text(),'details of the reservation or the account')]"))
						.getAttribute("textContent").replaceAll("[\\t\\n\\r]+", " ");

				String exp1 = "Reservation:";
				String exp2 = "Includes details of the reservation or the account on which the ledger account has been added.Reservation #: Reservation number of which the ledger account has been added.Guest/ Account Name: Name of the guest on the reservation or the account name on which the ledger account has been added.Arrival Date: Arrival date of the reservation.";

				if ((exp1.equals(act1)) && (exp2.equals(act2))) {
					reportLogger.info("Sucess - Reservations tooltips validation");
					test_steps.add("Sucess - Reservation tooltip validation" + "-" + i);
				} else {
					reportLogger.info("FAIL - Reservations tooltips validation");
					test_steps.add("AssertionError - FAIL - All Reservations tooltips validation" + "-" + i);
				}
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Reservations tooltips validation");
			test_steps.add("AssertionError - FAIL - Reservations tooltips validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Reservations tooltips validation");
			test_steps.add("AssertionError - FAIL - Reservations tooltips validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <ValidateAllTransactionstooltips> Description:
	 * <This method validates tool tips of all reservations> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/10/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void ValidateAllTransactionstooltips(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			List<WebElement> l = driver
					.findElements(By.xpath("//div[text()='Transaction']/span/span[@aria-label='info-circle']"));
			for (int i = 1; i <= l.size(); i++) {

				Utility.hoverOnElement(driver, driver.findElement(
						By.xpath("(//div[text()='Transaction']/span/span[@aria-label='info-circle'])[" + i + "]")));
				Wait.explicit_wait_visibilityof_webelement(
						driver.findElement(By.xpath("(//b[text()='Transaction: '])[" + i + "]")), driver);

				String act1 = driver.findElement(By.xpath("//b[text()='Transaction: ']")).getAttribute("textContent")
						.trim();
				String act2 = driver.findElement(By.xpath(
						"(//p[contains(text(),'Includes the below details related to the transaction')])[" + i + "]"))
						.getText().replaceAll("[\\t\\n\\r]+", " ");

				String exp1 = "Transaction:";
				String exp2 = "Includes the below details related to the transaction. Date: Date of the ledger account added.0 Item Description: Description of the charges/payments added on the reservation/account folio. Tax Exempt: Indicates whether or not the reservation/ account is tax exempt. Amount: The amount of payment processed or charges added.";

				if ((exp1.equals(act1)) && (exp2.equals(act2))) {
					reportLogger.info("Success - All Transactions tooltips validation");
					test_steps.add("Success - Transaction tooltip validation" + "-" + i);
				} else {
					reportLogger.info("FAIL - All Transactions tooltips validation");
					test_steps.add("AssertionError - FAIL - Transaction tooltip validation" + "-" + i);
				}
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - All Transactions tooltips validation");
			test_steps.add("AssertionError - FAIL - All Transactions tooltips validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - All Transactions tooltips validation");
			test_steps.add("AssertionError - FAIL - All Transactions tooltips validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateFilterHeaderDateRangeCaptionExistence>
	 * Description: <This method validates Filter Header Date Range Caption
	 * existence> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateFilterHeaderDateRangeCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.FilterHeaderDateRangeCaption.isDisplayed();
			reportLogger.info("Success - Filter Header Date Range Caption existence validation");
			test_steps.add("Success - Filter Header Date Range Caption existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Filter Header Date Range Caption existence validation");
			test_steps.add("AssertionError - FAIL - Filter Header Date Range Caption existence validation-<Br>"
					+ e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Filter Header Date Range Caption existence validation");
			test_steps.add("AssertionError - FAIL - Filter Header Date Range Caption existence validation-<Br>"
					+ e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateFilterHeaderIncludedLedgerAccountsCaptionExistence> Description:
	 * <This method validates Filter Header Included Ledger Accounts Caption
	 * existence> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateFilterHeaderIncludedLedgerAccountsCaptionExistence(WebDriver driver,
			ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.FilterHeaderIncludedLedgerAccountsCaption.isDisplayed();
			reportLogger.info("Success - Filter Header Included Ledger Accounts Caption existence validation");
			test_steps.add("Success - Filter Header Included Ledger Accounts Caption existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Filter Header Included Ledger Accounts Caption existence validation");
			test_steps.add(
					"AssertionError - FAIL - Filter Header Included Ledger Accounts Caption existence validation-<Br>"
							+ e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Filter Header Included Ledger Accounts Caption existence validation");
			test_steps.add(
					"AssertionError - FAIL - Filter Header Included Ledger Accounts Caption existence validation-<Br>"
							+ e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateFilterHeaderTaxExemptCaptionExistence>
	 * Description: <This method validates Filter Header Tax Exempt Caption
	 * existence> Input parameters: <WebDriver driver, ArrayList<String> test_steps>
	 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateFilterHeaderTaxExemptCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.FilterHeaderTaxExemptCaption.isDisplayed();
			reportLogger.info("Success - Filter Header Tax Exempt Caption existence validation");
			test_steps.add("Success - Filter Header Tax Exempt Caption existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Filter Header Tax Exempt Caption existence validation");
			test_steps.add("AssertionError - FAIL - Filter Header Tax Exempt Caption existence validation-<Br>"
					+ e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Filter Header Tax Exempt Caption existence validation");
			test_steps.add("AssertionError - FAIL - Filter Header Tax Exempt Caption existence validation-<Br>"
					+ e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name:
	 * <validateFilterHeaderSortReportByCaptionExistence> Description: <This method
	 * validates Filter Header Sort Report By Caption existence> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateFilterHeaderSortReportByCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.FilterHeaderSortReportByCaption.isDisplayed();
			reportLogger.info("Success - Filter Header Sort Report By Caption existence validation");
			test_steps.add("Success - Filter Header Sort Report By Caption existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Filter Header Sort Report By Caption existence validation");
			test_steps.add("AssertionError - FAIL - Filter Header Sort Report By Caption existence validation-<Br>"
					+ e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Filter Header Sort Report By Caption existence validation");
			test_steps.add("AssertionError - FAIL - Filter Header Sort Report By Caption existence validation-<Br>"
					+ e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateFilterHeaderGroupByCaptionExistence>
	 * Description: <This method validates Filter Header Group By Caption existence>
	 * Input parameters: <WebDriver driver, ArrayList<String> test_steps> Return
	 * value: <void> Created By: <Naveen Kadthala> Created On: <09/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateFilterHeaderGroupByCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.FilterHeaderGroupByCaption.isDisplayed();
			reportLogger.info("Success - Filter Header Group By Caption existence validation");
			test_steps.add("Success - Filter Header Group By Caption existence validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Filter Header Group By Caption existence validation");
			test_steps.add(
					"AssertionError - FAIL - Filter Header Group By Caption existence validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Filter Header Group By Caption existence validation");
			test_steps.add(
					"AssertionError - FAIL - Filter Header Group By Caption existence validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateFilterHeader> Description: <This
	 * method validates Filter Header content> Input parameters: <WebDriver driver,
	 * ArrayList<String> test_steps> Return value: <void> Created By: <Naveen
	 * Kadthala> Created On: <09/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateFilterHeader(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			boolean condition1 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.FilterHeaderDateRangeCaption));
			boolean condition2 = Utility.isElementDisplayed(driver,
					By.xpath(OR_Reports.FilterHeaderIncludedLedgerAccountsCaption));
			boolean condition3 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.FilterHeaderTaxExemptCaption));
			boolean condition4 = Utility.isElementDisplayed(driver,
					By.xpath(OR_Reports.FilterHeaderSortReportByCaption));
			boolean condition5 = Utility.isElementDisplayed(driver, By.xpath(OR_Reports.FilterHeaderGroupByCaption));

			String DateRangeActVal = res.FilterHeaderDateRangeValue.getText();
			String IncludedLedgerAccountsActVal = res.FilterHeaderIncludedLedgerAccountsValue.getText();
			String TaxExemptActVal = res.FilterHeaderTaxExemptValue.getText();
			String SortReportByActVal = res.FilterHeaderSortReportByValue.getText();
			String GroupByActVal = res.FilterHeaderGroupByValue.getText();

			while (!Utility.isElementDisplayed(driver, By.xpath(OR_Reports.Collapse))) {
				res.Edit.click();
			}

			String s1 = driver
					.findElement(By
							.xpath("//div[contains(@class,'DateRange')]/div/span[@class='ant-select-selection-item']"))
					.getText().trim();

			// Date Range

			String startDate = getStartDate(driver, test_steps);
			Date date1 = new SimpleDateFormat("MMM d, yyy").parse(startDate);
			String s2 = new SimpleDateFormat("MMM dd, yyy").format(date1);

			String endDate = getEndDate(driver, test_steps);
			Date date2 = new SimpleDateFormat("MMM d, yyy").parse(startDate);
			String s3 = new SimpleDateFormat("MMM dd, yyy").format(date2);

			String DateRangeExpVal = s1 + " | " + s2 + " to " + s3;

			boolean condition6 = DateRangeActVal.equals(DateRangeExpVal);

			// Included Ledger Accounts
			// expected list
			ArrayList<String> expectedlist = new ArrayList<String>();
			String value = null;
			List<WebElement> l = driver.findElements(By.xpath(
					"//strong[text()='Select Inputs']/../../../following-sibling::div//div[@class='ant-col ant-col-xs-12']"));
			for (int i = 1; i <= l.size(); i++) {
				WebElement e = driver.findElement(By.xpath(
						"//strong[text()='Select Inputs']/../../../following-sibling::div//div[@class='ant-col ant-col-xs-12']["
								+ i + "]"));
				String s4 = e.getText();

				String s5[] = s4.replaceAll("[(/)]", "/").split("/");
				String caption = s5[0];
				int selectedCount = Integer.parseInt(s5[1].trim());
				int totalCount = Integer.parseInt(s5[2].trim());
				if (selectedCount != 0) {
					expectedlist.add(s4.replaceAll("[(/)0-9]", " ").trim());
				}
			}
			String LedgerAccountsCount = null;
			int count = expectedlist.size();
			if (count <= 3) {
				LedgerAccountsCount = null;
			} else {
				LedgerAccountsCount = Integer.toString((count - 3));
			}

			// actual val

			ArrayList<String> actuallist = new ArrayList<String>();
			boolean conditiony = false;
			boolean b = IncludedLedgerAccountsActVal.contains("more");
			if (b) {
				conditiony = IncludedLedgerAccountsActVal.contains("+ " + LedgerAccountsCount + " more");
				String s6[] = IncludedLedgerAccountsActVal.split("[+]")[0].split("[|]");

				for (int i = 0; i < s6.length; i++) {
					actuallist.add(s6[i].trim());
				}
			} else {
				if (IncludedLedgerAccountsActVal.contains("[|]")) {
					conditiony = true;
					String s6[] = IncludedLedgerAccountsActVal.split("[|]");
					for (int i = 0; i < s6.length; i++) {
						actuallist.add(s6[i].trim());
					}
				} else {
					conditiony = true;
					String s6[] = (IncludedLedgerAccountsActVal + "|").split("[|]");
					for (int i = 0; i < s6.length; i++) {
						actuallist.add(s6[i].trim());
					}

				}
			}

			boolean conditionx = expectedlist.containsAll(actuallist);

			boolean condition7 = (conditionx && conditiony);

			// Tax Exempt
			String selectedTaxExemptOption = "//span[text()='Tax Exempt Ledger Items']/../../..//following-sibling::div[@class='ant-collapse-content ant-collapse-content-active']//span[@class='ant-select-selection-item']";
			if (!Utility.isElementDisplayed(driver, By.xpath(selectedTaxExemptOption))) {
				res.TaxExemptLedgerItems.click();
			}
			String taxExemptExpValue = driver.findElement(By.xpath(selectedTaxExemptOption)).getText().trim();

			if (taxExemptExpValue.equals("Tax Exempt")) {
				taxExemptExpValue = "Yes";
			} else if (taxExemptExpValue.equals("Taxed")) {
				taxExemptExpValue = "No";
			}

			boolean condition8 = taxExemptExpValue.equals(TaxExemptActVal);

			// Sort Report By
			String sortReportByexpValue = driver.findElement(By.xpath(
					"//b[text()='Sort Report By']/../../following-sibling::div//span[@class='ant-select-selection-item']"))
					.getText().trim();

			boolean condition9 = sortReportByexpValue.equals(SortReportByActVal);

			// sort Group By
			String sortGroupByexpValue = driver.findElement(By.xpath(
					"//b[text()='Group Rows By']/../../following-sibling::div//span[@class='ant-select-selection-item']"))
					.getText().trim();

			boolean condition10 = sortGroupByexpValue.equals(GroupByActVal);

			if (condition1 && condition2 && condition3 && condition4 && condition5 && condition6 && condition7
					&& condition8 && condition9 && condition10) {
				reportLogger.info("Success - Filter Header validation");
				test_steps.add("Success - Filter Header validation");
			} else {
				reportLogger.info("FAIL - Filter Header validation");
				test_steps.add("AssertionError - FAIL - Filter Header validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Filter Header Date Range Caption existence validation");
			test_steps.add("AssertionError - FAIL - Filter Header validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Filter Header validation");
			test_steps.add("AssertionError - FAIL - Filter Header validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateNoReportDataMessage> Description:
	 * <This method validates No Report Data Message> Input parameters: <WebDriver
	 * driver, ArrayList<String> test_steps> Return value: <void> Created By:
	 * <Naveen Kadthala> Created On: <09/14/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 */
	public void validateNoReportDataMessage(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.NoReportDataAvailableMessageHeading.isDisplayed();
			res.NoReportDataAvailableMessageContent.isDisplayed();
			reportLogger.info("Success - No Data toster validation");
			test_steps.add("Success - No Data toster validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - No Data toster validation");
			test_steps.add("AssertionError - FAIL - No Data toster validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - No Data toster validation");
			test_steps.add("AssertionError - FAIL - No Data toster validation-<Br>" + e.toString());
		}
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateCancelReportFunctionality>
	 * Description: <This method validates Cancel Report functionltiy while
	 * generating Report> Input parameters: <WebDriver driver, ArrayList<String>
	 * test_steps> Return value: <void> Created By: <Naveen Kadthala> Created On:
	 * <09/15/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateCancelReportFunctionality(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			if (Utility.isElementDisplayed(driver, By.xpath(OR_Reports.LoadingReportMessage))) {
				res.LoadingReportCancelButton.click();
				if (Utility.isElementDisplayed(driver, By.xpath(OR_Reports.GRReportTypeCaption))) {
					reportLogger.info("Fail - Loading Report Cancel functionality validation");
					test_steps.add("Fail - Loading Report Cancel functionality validation");
				} else {
					reportLogger.info("Success - Loading Report Cancel functionality validation");
					test_steps.add("Success - Loading Report Cancel functionality validation");
				}
			} else {
				reportLogger.info("Fail - Loading Report Cancel functionality validation");
				test_steps.add("Fail - Loading Report Cancel functionality validation");
			}

		} catch (Exception e) {
			reportLogger.info("FAIL - Loading Report Cancel functionality validation");
			test_steps.add("AssertionError - Loading Report Cancel functionality validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Loading Report Cancel functionality validation");
			test_steps.add("AssertionError - Loading Report Cancel functionality validation-<Br>" + e.toString());
		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateGeneratedOnCaptionExistence>
	 * Description: <This method validates Generated On Caption existence> Input
	 * parameters: <WebDriver driver, ArrayList<String> test_steps> Return value:
	 * <void> Created By: <Naveen Kadthala> Created On: <09/15/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateGeneratedOnCaptionExistence(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);
			res.GRGeneratedOnCaption.isDisplayed();
			reportLogger.info("Success - Generated On Caption validation");
			test_steps.add("Success - Generated On Caption validation");
		} catch (Exception e) {
			reportLogger.info("FAIL - Generated On Caption validation");
			test_steps.add("AssertionError - FAIL - Generated On Caption validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Generated On Caption validation");
			test_steps.add("AssertionError - FAIL - Generated On Caption validation-<Br>" + e.toString());
		}
	}

	// methods for after run report
	// This method is to get all Ledger Account Category details
	public HashMap<String, String> getLedgerCategoryDetails(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> category = new HashMap<>();

		//List<WebElement> rows = report.tableLedgerAccountCategory.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		List<WebElement> rows = driver.findElements(By.xpath("//div[contains(text(),'Ledger Account Category')]//ancestor::table/tbody/tr"));
		// reportLogger.info("Rows: "+rows.size());

		for (int i = 0; i < rows.size(); i++) {

			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			// reportLogger.info("Cells: "+cells.size());
			// reportLogger.info("Data: "+cells.get(0).getText());
			category.put(cells.get(0).getText(), cells.get(1).findElement(By.tagName("span")).getText());
		}

		// List<WebElement> foot =
		// report.tableLedgerAccountCategory.findElement(By.tagName("tfoot")).findElements(By.tagName("td"));
		// category.put(foot.get(0).getText(),
		// foot.get(1).findElement(By.tagName("span")).getText());

		reportLogger.info("Details " + category);

		return category;

	}

	// This method is to get all Ledger Account Category details
	public HashMap<String, String> getDetailsOfGivenCategory(WebDriver driver, String category,
			ArrayList<String> test_steps) throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> details = new HashMap<>();

		String ledger = "//th[contains(@title,'" + category + "')]//ancestor::table";

		List<WebElement> rows = driver.findElements(By.xpath(ledger + "/tbody/tr"));
		// reportLogger.info("Rows: "+rows.size());

		for (int i = 0; i < rows.size(); i++) {

			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			// reportLogger.info("Cells: "+cells.size());
			reportLogger.info("Data: " + cells.get(0).getText());
			details.put(cells.get(0).getText(), cells.get(1).getAttribute("title"));
		}

		List<WebElement> foot = driver.findElements(By.xpath(ledger + "/tfoot/tr/td"));
		details.put(foot.get(0).getText(), foot.get(1).findElement(By.tagName("span")).getText());

		reportLogger.info("Details " + details);
		return details;

	}

	// This method is to get all Ledger Account Category details
	public HashMap<String, String> getDetailsOfAllLedgerCategories(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> details = new HashMap<>();

		HashMap<String, String> category = getLedgerCategoryDetails(driver, test_steps);
		reportLogger.info("Ledger Categories: " + category.size());
		reportLogger.info("Ledger Categories: " + category);

		Set<String> ledgers = category.keySet();

		for (String ledger : ledgers) {
			String strLedger = "(//th[contains(text(),'" + ledger + "')]//ancestor::table)[1]";

			List<WebElement> rows = driver.findElements(By.xpath(strLedger + "/tbody/tr"));
			reportLogger.info("Rows: " + rows.size());

			for (int i = 0; i < rows.size(); i++) {

				List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
				reportLogger.info("Cells: " + cells.size());
				reportLogger.info("Data: " + cells.get(0).getText());
				details.put(cells.get(0).getText(), cells.get(1).findElement(By.tagName("span")).getText());
			}

		}

		reportLogger.info("Details: " + details);
		return details;

	}

	// This method is to get all Ledger Account Category details - Detailed view
	public HashMap<String, ArrayList<String>> getDetailedViewDetails(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> details = new HashMap<>();
		// ArrayList<ArrayList<String>> ledgerDetails = new ArrayList<>();
		HashMap<String, ArrayList<String>> ledgerDetails = new HashMap<>();
		ArrayList<String> accounts = new ArrayList<>();

		HashMap<String, String> category = getDetailsOfAllLedgerCategories(driver, test_steps);

		Set<String> ledgers = category.keySet();

		for (String ledger : ledgers) {
			accounts.clear();
			// String strLedger = "//h1[contains(text(),'Detailed View | Ledger Balances
			// Report')]//following::div[contains(text(),'"+ledger+"')]//parent::div//following::table";
			String strLedger = "(//h1[contains(text(),'Detailed View | Ledger Balances Report')]//following::div[contains(text(),'"
					+ ledger + "')]//parent::div//following::table)[1]";

			List<WebElement> rows = driver.findElements(By.xpath(strLedger + "/tbody/tr"));
			for (int i = 0; i < rows.size(); i++) {
				List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));

				for (int j = 0; j < cells.size(); j++) {
					accounts.add(cells.get(j).getText());
				}
			}
			reportLogger.info("Accounts: " + accounts);
			ledgerDetails.put(ledger, accounts);

		}
		return ledgerDetails;

	}

	// This method is to get all Ledger Account Category details - Latest Data -
	// Detailed view
	public HashMap<String, ArrayList<String>> getDetailedViewDetailsLatest(WebDriver driver,
			ArrayList<String> test_steps) throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> details = new HashMap<>();
		// ArrayList<ArrayList<String>> ledgerDetails = new ArrayList<>();
		HashMap<String, ArrayList<String>> ledgerDetails = new HashMap<>();
		ArrayList<String> accounts = new ArrayList<>();

		HashMap<String, String> category = getDetailsOfAllLedgerCategories(driver, test_steps);

		Set<String> ledgers = category.keySet();

		for (String ledger : ledgers) {
			accounts.clear();
			// String strLedger = "//h1[contains(text(),'Detailed View | Ledger Balances
			// Report')]//following::div[contains(text(),'"+ledger+"')]//parent::div//following::table";
			String strLedger = "(//*[contains(text(),'Detailed View | Ledger Balances Report')]//following::div[contains(text(),'"
					+ ledger + "')]//parent::div//following::table)[1]";

			List<WebElement> rows = driver.findElements(By.xpath(strLedger + "/tbody/tr"));
			reportLogger.info("Rows             " + rows.size());
			// for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cells = rows.get(0).findElements(By.tagName("td"));
			reportLogger.info("Cell Size                 " + cells.size());

			for (int j = 0; j < cells.size(); j++) {
				accounts.add(cells.get(j).getText());
			}
			// }
			reportLogger.info("Accounts: " + accounts);
			ledgerDetails.put(ledger, accounts);

		}
		return ledgerDetails;

	}

	// This method is to get all Ledger Account Category details With headers -
	// Latest Data - Detailed view
	public HashMap<String, HashMap<String, String>> getDetailedViewDetailsLatestWithHeaders(WebDriver driver,
			ArrayList<String> test_steps) throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> details = new HashMap<>();
		// ArrayList<ArrayList<String>> ledgerDetails = new ArrayList<>();
		HashMap<String, HashMap<String, String>> ledgerDetails = new HashMap<>();
		// ArrayList<String> accounts = new ArrayList<>();
		HashMap<String, String> accounts = new HashMap<>();

		HashMap<String, String> category = getDetailsOfAllLedgerCategories(driver, test_steps);

		Set<String> ledgers = category.keySet();

		for (String ledger : ledgers) {
			accounts.clear();
			// String strLedger = "//h1[contains(text(),'Detailed View | Ledger Balances
			// Report')]//following::div[contains(text(),'"+ledger+"')]//parent::div//following::table";
			String strLedger = "(//*[contains(text(),'Detailed View | Ledger Balances Report')]//following::div[contains(text(),'"
					+ ledger + "')]//parent::div//following::table)[1]";

			List<WebElement> rows = driver.findElements(By.xpath(strLedger + "/tbody/tr"));
			// reportLogger.info("Rows: "+rows.size());
			List<WebElement> header = driver.findElements(By.xpath(strLedger + "/thead/tr"));
			// reportLogger.info("Header rows: "+header.size());
			List<WebElement> headerCells = header.get(1).findElements(By.tagName("th"));
			// reportLogger.info("Header cells: "+headerCells.size());
			// for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cells = rows.get(0).findElements(By.tagName("td"));
			// reportLogger.info("Cells: "+cells.size());

			for (int j = 0; j < cells.size(); j++) {
				accounts.put(headerCells.get(j).getText(), cells.get(j).getText());
				// accounts.add(cells.get(j).getText());
			}
			// }
			reportLogger.info("Accounts: " + accounts);
			ledgerDetails.put(ledger, accounts);

		}
		return ledgerDetails;

	}

	// This method is to get all Ledger Account Category details With headers -
	// Latest Data - Detailed view
	public HashMap<String, HashMap<String, String>> getDetailedViewDetailsLatestOfGivenInput(WebDriver driver,
			String input, ArrayList<String> test_steps) throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> details = new HashMap<>();
		// ArrayList<ArrayList<String>> ledgerDetails = new ArrayList<>();
		HashMap<String, HashMap<String, String>> ledgerDetails = new HashMap<>();
		// ArrayList<String> accounts = new ArrayList<>();
		HashMap<String, String> accounts = new HashMap<>();

		HashMap<String, String> category = getDetailsOfAllLedgerCategories(driver, test_steps);

		Set<String> ledgers = category.keySet();

		for (String ledger : ledgers) {

			if (ledger.equalsIgnoreCase("input")) {

				accounts.clear();
				// String strLedger = "//h1[contains(text(),'Detailed View | Ledger Balances
				// Report')]//following::div[contains(text(),'"+ledger+"')]//parent::div//following::table";
				String strLedger = "(//h1[contains(text(),'Detailed View | Ledger Balances Report')]//following::div[contains(text(),'"
						+ ledger + "')]//parent::div//following::table)[1]";

				List<WebElement> rows = driver.findElements(By.xpath(strLedger + "/tbody/tr"));
				// reportLogger.info("Rows: "+rows.size());
				List<WebElement> header = driver.findElements(By.xpath(strLedger + "/thead/tr"));
				// reportLogger.info("Header rows: "+header.size());
				List<WebElement> headerCells = header.get(1).findElements(By.tagName("th"));
				// reportLogger.info("Header cells: "+headerCells.size());
				// for (int i = 0; i < rows.size(); i++) {
				List<WebElement> cells = rows.get(0).findElements(By.tagName("td"));
				// reportLogger.info("Cells: "+cells.size());

				for (int j = 0; j < cells.size(); j++) {
					accounts.put(headerCells.get(j).getText(), cells.get(j).getText());
					// accounts.add(cells.get(j).getText());
				}
				// }
				reportLogger.info("Accounts: " + accounts);
				ledgerDetails.put(ledger, accounts);

				break;

			}

		}
		return ledgerDetails;

	}

	// This method is to validate given reservation number availability
	public void validateGivenReservationNumberAvailability(WebDriver driver, String input, String reservation,
			ArrayList<String> test_steps) throws InterruptedException, ParseException {

		String strInput = "((//div[contains(text(),'" + input + "')])[1]//following::table[1]//td[contains(text(),'"
				+ reservation + "')])[1]";

		try {
			Wait.explicit_wait_10sec(driver.findElement(By.xpath(strInput)), driver);
		} catch (Exception e) {
			reportLogger
					.info("AssertionError - Given reservation number " + reservation + " is not avilable in Report");
			test_steps.add("AssertionError - Given reservation number " + reservation + " is not avilable in Report");
		} catch (Error e) {
			reportLogger
					.info("AssertionError - Given reservation number " + reservation + " is not avilable in Report");
			test_steps.add("AssertionError - Given reservation number " + reservation + " is not avilable in Report");
		}

	}

	// This method is to check given reservation number availability
	public boolean checkGivenReservationNumberAvailability(WebDriver driver, String input, String reservation,
			ArrayList<String> test_steps) throws InterruptedException, ParseException {

		String strInput = "((//div[contains(text(),'" + input + "')])[1]//following::table[1]//td[contains(text(),'"
				+ reservation + "')])[1]";
		boolean flag = false;

		try {
			Wait.explicit_wait_10sec(driver.findElement(By.xpath(strInput)), driver);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// This method is to get transaction details of given reservation number - HashMap<String, String>
	public HashMap<String, String> getTransactionDetails(WebDriver driver, String input, String reservation,
			ArrayList<String> test_steps) throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, String> details = new HashMap<>();

		String strLedger = "(//*[contains(text(),'Detailed View | Ledger Balances Report')]//following::div[contains(text(),'"
				+ input + "')]//parent::div//following::table)[1]";
		List<WebElement> header = driver.findElements(By.xpath(strLedger + "/thead/tr[2]/th"));
		// List<WebElement> header =
		// driver.findElements(By.xpath("(//h1[contains(text(),'Detailed View | Ledger
		// Balances
		// Report')]//following::div[contains(text(),'"+input+"')]//parent::div//following::table)[1]/thead/tr[2]/th"));
		reportLogger.info("header Size " + header.size());

		String strInput = "((//div[contains(text(),'" + input + "')])[1]//following::table[1]//td[contains(text(),'"
				+ reservation + "')])[1]";
		String strItemTable = "(//div[contains(text(),'" + input + "')])[1]//following::table[1]//tr";

		List<WebElement> strRows = driver.findElements(By.xpath(strItemTable));
		reportLogger.info("Transaction rows: " + strRows.size());

		for (int i = 2; i < strRows.size(); i++) {
			List<WebElement> cells = strRows.get(i).findElements(By.tagName("td"));
			reportLogger.info("Cells: " + cells.size());
			reportLogger.info("Reservation number: " + cells.get(0).getText());

			if (cells.get(0).getText().equalsIgnoreCase(reservation)) {
				reportLogger.info("Entered into loop");
				for (int j = 0; j < cells.size(); j++) {
					reportLogger.info("Header: " + header.get(j).getText());
					reportLogger.info("Cell: " + cells.get(j).getText());
					details.put(header.get(j).getText(), cells.get(j).getText());
				}
				break;
			}

		}

		return details;

	}
	
	// This method is to get transaction details of given reservation number - HashMap<String, ArrayList<String>>
	public HashMap<String, ArrayList<String>> getTransactionDetailsList(WebDriver driver, String input, 
			ArrayList<String> test_steps) throws InterruptedException, ParseException {

		Elements_Reports report = new Elements_Reports(driver);
		HashMap<String, ArrayList<String>> details = new HashMap<>();
		//ArrayList<String> data = new ArrayList<>();

		String strLedger = "(//*[contains(text(),'Detailed View | Ledger Balances Report')]//following::div[contains(text(),'"+ input +"')]//parent::div//following::table)[1]";
		List<WebElement> header = driver.findElements(By.xpath(strLedger + "/thead/tr[2]/th"));
		// List<WebElement> header =
		// driver.findElements(By.xpath("(//h1[contains(text(),'Detailed View | Ledger
		// Balances
		// Report')]//following::div[contains(text(),'"+input+"')]//parent::div//following::table)[1]/thead/tr[2]/th"));
		reportLogger.info("header Size " + header.size());

		//String strInput = "((//div[contains(text(),'" + input + "')])[1]//following::table[1]//td[contains(text(),'"
		//		+ reservation + "')])[1]";
		String strItemTable = "(//div[contains(text(),'" + input + "')])[1]//following::table[1]//tr";

		List<WebElement> rowData = driver.findElements(By.xpath(strItemTable));
		reportLogger.info("Transaction rows: " + rowData.size());
		
		try {
		List<WebElement> cellData = rowData.get(2).findElements(By.tagName("td"));
		reportLogger.info("Cell Data size: "+cellData.size());
		
		for (int i = 0; i < cellData.size(); i++) {
			//data.clear();
			ArrayList<String> data = new ArrayList<>();
			for (int j = 2; j < rowData.size(); j++) {
				List<WebElement> cells = rowData.get(j).findElements(By.tagName("td"));
				data.add(cells.get(i).getText());
			}
			reportLogger.info("Data All: "+data);
			details.put(header.get(i).getText(), data);
		}
		}catch(Exception e) {
			reportLogger.info("No data found for "+input);
			test_steps.add("AssertionError - No data found for "+input);
		}

		return details;

	}
	
	// This method is to get all details by given Sort option
	public ArrayList<String> getDetailsByGivenSortOption(WebDriver driver, String input, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		String strHeaders = "//div[text()='Reservation']//following::tr[1]/th";
		ArrayList<String> itemsList = new ArrayList<>();

		List<WebElement> headers = driver.findElements(By.xpath(strHeaders));
		int count = 0;
		for (int i = 0; i < headers.size(); i++) {
			if (headers.get(i).getText().equalsIgnoreCase(input)) {
				// String data = "//th[contains(text(),'"+input+"')][1]//ancestor::table//tr";
				String data = "(//th[contains(text(),'" + input + "')][1]//ancestor::table/tbody)[1]/tr";

				List<WebElement> dataList = driver.findElements(By.xpath(data));

				for (int j = 1; j < dataList.size(); j++) {
					List<WebElement> cells = dataList.get(j).findElements(By.tagName("td"));
					reportLogger.info("Sort Cell size: " + cells.size() + "  " + cells.get(count).getText());
					itemsList.add(cells.get(count).getText());
				}
				break;
			}
			count++;
		}

		return itemsList;

	}

	// This method is to validate sort Report Sort functionality
	public void validateReportSortFunctionality(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		String strHeaders = "//div[text()='Reservation']//following::tr[1]/th";
		ArrayList<String> itemsList = new ArrayList<>();
		ArrayList<String> itemsListAfterSort = new ArrayList<>();

		String[] sortOptions = { "Reservation Number", "Guest/Account Name", "Date", "Item Description", "Tax Exempt",
				"Amount" };
		String[] sort = { "Reservation #", "Guest/Account Name", "Date", "Item Description", "Tax Exempt", "Amount" };

		selectAllInputsUsingSeeAll(driver, test_steps);

		try {

			for (int i = 0; i < sort.length; i++) {

				if (sort[i].equalsIgnoreCase("Reservation #")) {
					selectGivenSortReportByOption(driver, "Reservation Number", test_steps);
				} else {
					selectGivenSortReportByOption(driver, sort[i], test_steps);
				}

				clickOnRunReport(driver);

				itemsList = getDetailsByGivenSortOption(driver, sort[i], test_steps);

				itemsListAfterSort = itemsList;

				Collections.sort(itemsListAfterSort);

				if (itemsList.equals(itemsListAfterSort)) {
					reportLogger.info(sort[i] + " - Report sort functionality validated Successfully");
					test_steps.add(sort[i] + " - Report sort functionality validated Successfully");

				} else {
					reportLogger.info(sort[i] + " - Report sort functionality validation failed");
					Assert.assertTrue(false, "Failed - " + sort[i] + " - Report sort functionality validation failed");
				}

				Utility.clickThroughAction(driver, driver.findElement(By.xpath("//span[contains(text(),'Edit')]")));
				Wait.wait1Second();

			}
		} catch (Exception e) {
			test_steps.add(e.toString());
		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}
	
	
	//Tax
		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <navigateToTaxesAndFees> Description: <This
		 * method navigates to Taxes and fees tab> Input parameters: <WebDriver driver>
		 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public void navigateToTaxesAndFees(WebDriver driver) {
			Elements_SetUp_Properties res = new Elements_SetUp_Properties(driver);
			driver.findElement(By.xpath("//a[@data-id='/setup/taxes']")).click();
		}

		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <checkIfTaxRowExists> Description: <This
		 * method checks if Tax Row exists in Taxes and Fees> Input parameters: <WebDriver driver>
		 * Return value: <boolean> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public boolean checkIfTaxRowExists(WebDriver driver) {
			int count = 0;
			List<WebElement> l = driver.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr"));
			for (int i = 1; i <= l.size(); i++) {
				String s = driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']/tr["+i+"]/td[2]"))
						.getAttribute("textContent");
				if (s.equalsIgnoreCase("Tax")) {
					count = count + 1;
				}
			}
			if (count >= 1) {
				return true;
			} else {
				return false;
			}
		}

		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <getTaxLedgerAccounts> Description: <This
		 * method gets list of Tax ledger accounts> Input parameters: <WebDriver driver>
		 * Return value: <ArrayList<String>> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public ArrayList<String> getTaxLedgerAccounts(WebDriver driver) {
			ArrayList<String> al = new ArrayList<String>();
			List<WebElement> l = driver.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr"));
			for (int i = 1; i <=l.size(); i++) {
				String s = driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']/tr["+i+"]/td[2]"))
						.getAttribute("textContent");
				if (s.equalsIgnoreCase("Tax")) {
					driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']/tr["+i+"]/td[6]/div/button[contains(@class,'edit')]")).click();
					List<WebElement> ls = driver.findElements(By.xpath("//h2[text()='Apply to the following ledger accounts ']/..//div[@class='panel-data-fill']/div/span"));
					for(WebElement e: ls) {
						al.add(e.getText());
					}
					break;	
				}
			}
			return al;
		}	
		
		
		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <getTaxAmountFromTaxLedgerAccount> Description: <This
		 * method gets Tax Amount from Tax Ledger Account> Input parameters: <WebDriver driver>
		 * Return value: <String> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public String getTaxAmountFromTaxLedgerAccount(WebDriver driver) {
			String amount = driver.findElement(By.xpath("//input[@name='amount']")).getAttribute("value");
			String caption = driver.findElement(By.xpath("//label[text()='Flat/Percent']/following-sibling::div//div[@class='ant-select-selection-selected-value']")).getAttribute("textContent");
			String TaxAmount = amount +"|"+ caption;
			return TaxAmount;
		}	
		
		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <getTaxLedgerAccountSelectedOption> Description: <This
		 * method gets selected Ledger Account> Input parameters: <WebDriver driver>
		 * Return value: <String> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public String getTaxLedgerAccountSelectedOption(WebDriver driver) {
			String selectedLedgerAccount = driver.findElement(By.xpath("//label[text()='Ledger Account']/following-sibling::div//div[@class='ant-select-selection-selected-value']")).getAttribute("textContent");
			return selectedLedgerAccount;
		}	
		
		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <createTaxItem> Description: <This
		 * method creates Tax Item> Input parameters: <WebDriver driver>
		 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public void createTaxItem(WebDriver driver, String taxname, String displayname, String description, String ledgerAccount, String USDorPercentage, String amount, String addLedgerAccount ) throws InterruptedException {
			Utility.clickThroughAction(driver, driver.findElement(By.xpath("//span[text()='Create ']")));
			driver.findElement(By.xpath("//a[text()='Tax']")).click();
			driver.findElement(By.xpath("//input[@name='name']")).sendKeys(taxname);
			driver.findElement(By.xpath("//input[@name='displayName']")).sendKeys(displayname);
			driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
			driver.findElement(By.xpath("//label[text()='Ledger Account']/following-sibling::div//i")).click();
			driver.findElement(By.xpath("//li[text()='"+ledgerAccount+"']")).click();
			Utility.clickThroughAction(driver, driver.findElement(By.xpath("//label[text()='Flat/Percent']/following-sibling::div//i")));
			List<WebElement> l1 = driver.findElements(By.xpath("//div[@id='eef3bda8-2c51-4ed1-c3b6-51e7c206395a']/ul/li"));
			for(WebElement e: l1) {
				if(e.getAttribute("textContent").equalsIgnoreCase(USDorPercentage)) {
					driver.findElement(By.xpath("//div[@id='eef3bda8-2c51-4ed1-c3b6-51e7c206395a']/ul/li[text()='"+USDorPercentage+"']")).click();
					break;
				}
			}
			driver.findElement(By.xpath("//input[@name='amount']")).sendKeys(amount);
			driver.findElement(By.xpath("//span[text()='Exclude when tax exempt']/../span/input[@type='checkbox']")).click();
			driver.findElement(By.xpath("//button[text()='Add ledger accounts']")).click();
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath("//label[text()='Select ledger account(s)']/..//div[text()='--Select--']")));
			//driver.findElement(By.xpath("//label[text()='Select ledger account(s)']/..//div[text()='--Select--']")).click();
			Utility.clickThroughAction(driver, driver.findElement(By.xpath("//li[@label='"+addLedgerAccount+"']")));
			Wait.wait2Second();		
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath("//button/span[text()='Add']")));
			Wait.wait2Second();
			Utility.clickThroughAction(driver, driver.findElement(By.xpath("//button/span[text()=' Save ']")));
		}
		
		//Fees

		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <checkIfFeesRowExists> Description: <This
		 * method checks if Fees Row exists in Taxes and Fees> Input parameters: <WebDriver driver>
		 * Return value: <boolean> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public boolean checkIfFeesRowExists(WebDriver driver) {
			int count = 0;
			List<WebElement> l = driver.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr"));
			for (int i = 1; i <= l.size(); i++) {
				String s = driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']/tr["+i+"]/td[2]"))
						.getAttribute("textContent");
				if (s.equalsIgnoreCase("Fee")) {
					count = count + 1;
				}
			}
			if (count >= 1) {
				return true;
			} else {
				return false;
			}
		}

		
		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <getFeeAmountFromFeeLedgerAccount> Description: <This
		 * method gets Fee Amount from Fee Ledger Account> Input parameters: <WebDriver driver>
		 * Return value: <String> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public String getFeeAmountFromFeeLedgerAccount(WebDriver driver) {
			List<WebElement> l = driver.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr"));
			for (int i = 1; i <=l.size(); i++) {
				String s = driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']/tr["+i+"]/td[2]"))
						.getAttribute("textContent");
				if (s.equalsIgnoreCase("Fee")) {
					driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']/tr["+i+"]/td[6]/div/button[contains(@class,'edit')]")).click();
					break;	
				}
			}
			String amount = driver.findElement(By.xpath("//input[@name='amount']")).getAttribute("value");
			String caption = driver.findElement(By.xpath("//label[text()='Flat/Percent of Room Charge']/following-sibling::div//div[@class='ant-select-selection-selected-value']")).getAttribute("textContent");
			String perValue = driver.findElement(By.xpath("(//div[text()='--Select--']/following-sibling::div)[3]")).getAttribute("textContent");
			String FeeAmount = amount +"|"+ caption +"|"+ perValue;
			return FeeAmount;
		}	
		
		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <getFeeLedgerAccountSelectedOption> Description: <This
		 * method gets selected Ledger Account> Input parameters: <WebDriver driver>
		 * Return value: <String> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public String getFeeLedgerAccountSelectedOption(WebDriver driver) {
			String selectedLedgerAccount = driver.findElement(By.xpath("//label[text()='Ledger Account']/following-sibling::div//div[@class='ant-select-selection-selected-value']")).getAttribute("textContent");
			return selectedLedgerAccount;
		}	
		
		/*
		 * #############################################################################
		 * #############################################################################
		 * ################ Method Name: <createFeeItem> Description: <This
		 * method creates Tax Item> Input parameters: <WebDriver driver>
		 * Return value: <void> Created By: <Naveen Kadthala> Created On: <09/23/2020>
		 * 
		 * #############################################################################
		 * #############################################################################
		 * ################
		 */
		public void createFeeItem(WebDriver driver, String feename, String displayname, String description, String USDorPercentage, String amount, String perstaynight ) {
			Utility.clickThroughAction(driver, driver.findElement(By.xpath("//span[text()='Create ']")));
			driver.findElement(By.xpath("//a[text()='Fee']")).click();
			driver.findElement(By.xpath("//input[@name='name']")).sendKeys(feename);
			driver.findElement(By.xpath("//input[@name='displayName']")).sendKeys(displayname);
			driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
			driver.findElement(By.xpath("//label[text()='Ledger Account']/following-sibling::div//i")).click();
			driver.findElement(By.xpath("//li[text()='Fee Adjustment']")).click();
			Utility.clickThroughAction(driver, driver.findElement(By.xpath("//label[text()='Flat/Percent of Room Charge']/following-sibling::div//i")));
			List<WebElement> l1 = driver.findElements(By.xpath("//div[@id='2caf2a77-481b-410c-81ac-3e2e0f1989a8']/ul/li"));
			for(WebElement e: l1) {
				if(e.getAttribute("textContent").equalsIgnoreCase(USDorPercentage)) {
					driver.findElement(By.xpath("//div[@id='2caf2a77-481b-410c-81ac-3e2e0f1989a8']/ul/li[text()='"+USDorPercentage+"']")).click();
					break;
				}
			}
			driver.findElement(By.xpath("//input[@name='amount']")).sendKeys(amount);

			Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//label[text()='Flat/Percent of Room Charge']/../../../..//i[@class='anticon anticon-down ant-select-arrow-icon'])[2]")));
			List<WebElement> l2 = driver.findElements(By.xpath("//div[@id='97dc590f-7116-47aa-a2ef-8db8d660a6c8']/ul/li"));
			for(WebElement e: l2) {
				if(e.getAttribute("textContent").equalsIgnoreCase(perstaynight)) {
					driver.findElement(By.xpath("//div[@id='97dc590f-7116-47aa-a2ef-8db8d660a6c8']/ul/li[text()='"+perstaynight+"']")).click();
					break;
				}
			}
			Utility.clickThroughAction(driver, driver.findElement(By.xpath("//button/span[text()=' Save ']")));
		}
	
		
		public double getFolioAmountExcludingCurrency(WebDriver driver, String amount) {
			
			double money;
			
			//money = Integer.parseInt(amount.substring(2).replace(".00", ""));
			money = Double.parseDouble(amount.substring(2));
			
			return money;
			
		}
		
		// This method is to validate "No Report Data Available" message when there is no data or Running Report without selecting any Inputs
		public void validateNoReportData(WebDriver driver, ArrayList<String> test_steps)
				throws InterruptedException, ParseException {

			clickReturnToDefault(driver, test_steps);
			clickOnRunReport(driver);

			String toast = "//div[contains(text(),'No Report Data Available')]";

			try {
				Wait.WaitForElement(driver, toast);
				reportLogger.info("Sccess: validated 'No Report Data Available' toas message");
				test_steps.add("Sccess: validated 'No Report Data Available' toas message");

			} catch (Exception e) {
				reportLogger.info("Failed: failed to validate 'No Report Data Available' toas message");
				test_steps.add("Failed: failed to validate 'No Report Data Available' toas message");
			}
		}
		
		// This method is to validate "No Report Data Available" message when there is no data or Running Report without selecting any Inputs
		public boolean checkNoReportDataAvailable(WebDriver driver, ArrayList<String> test_steps)
				throws InterruptedException, ParseException {
			boolean flag = false;
			String toast = "//div[contains(text(),'No Report Data Available')]";

			try {
				//Wait.WaitForElement(driver, toast);
				Wait.explicit_wait_10sec(driver.findElement(By.xpath(toast)), driver);
				reportLogger.info("Sccess: validated 'No Report Data Available' toas message");
				test_steps.add("Sccess: validated 'No Report Data Available' toas message");
				flag = true;

			} catch (Exception e) {

			}
			return flag;
		}
		
		
		//This method is to read the values from excel
		public HashMap<String, String> getLedgerInputsAndValues(WebDriver driver, String Ledger, String Value) {
            HashMap<String, String> selectinputs = new HashMap<String, String>();
            for(int i=0;i<Ledger.split("[|]").length;i++) {
                selectinputs.put(Ledger.split("[|]")[i], Value.split("[|]")[i]);
            }           
            return selectinputs;
        }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void validateLedgerReportSummaryView(WebDriver driver, HashMap<String, String> beforeLedgerCategoryDetails, HashMap<String, String> beforeDetailsOfAllLedgerCategories, 
				HashMap<String, String> afterLedgerCategoryDetails, HashMap<String, String> afterDetailsOfAllLedgerCategories, HashMap<String, String> ledgerAccounts, 
				HashMap<String, Double> ledgerAmounts, HashMap<String, String> folioItemValues, int numberOfRooms, ArrayList<String> test_steps)
				throws InterruptedException, ParseException {
			
			Set<String> ledgers = ledgerAccounts.keySet();
			
			for (String ledger : ledgers) {
				
					//Summary view
					if (beforeLedgerCategoryDetails.containsKey(ledger)) {
						double totalAmount = Double.parseDouble(afterLedgerCategoryDetails.get(ledger).substring(1))
								- Double.parseDouble(beforeLedgerCategoryDetails.get(ledger).substring(1));
						reportLogger.info(ledger+": " + totalAmount);
						
						if (totalAmount == ledgerAmounts.get(ledger)) {
							reportLogger.info(ledger+" amount validated successfully in Summary View");
							test_steps.add(ledger+" amount validated successfully in Summary View");
						}else {
							reportLogger.info("Failed - "+ledger+" amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - "+ledger+" amount in Summary View validation failed. Expected: "+ledgerAmounts.get(ledger)+" But found: "+totalAmount);
						}
						
					} else {
						double totalAmount = Double.parseDouble(afterLedgerCategoryDetails.get(ledger).substring(1));
						reportLogger.info(ledger+": " + totalAmount);
						
						if (totalAmount == ledgerAmounts.get(ledger)) {
							reportLogger.info(ledger+" amount validated successfully in Summary View");
							test_steps.add(ledger+" amount validated successfully in Summary View");
						}else {
							reportLogger.info("Failed - "+ledger+" amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - "+ledger+" amount in Summary View validation failed. Expected: "+ledgerAmounts.get(ledger)+" But found: "+totalAmount);
						}
						
					}

					String[] strLedger = ledgerAccounts.get(ledger).split(",");

					if (strLedger.length == 1) {
						if (beforeDetailsOfAllLedgerCategories.containsKey(ledgerAccounts.get(ledger))) {
							double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(ledgerAccounts.get(ledger)).substring(1))
									- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(ledgerAccounts.get(ledger)).substring(1));
							reportLogger.info(ledgerAccounts.get(ledger)+" - " + ledgerAccounts.get(ledger) + ": " + amount);
							
							if (amount == (Double.parseDouble(folioItemValues.get(ledgerAccounts.get(ledger)))*numberOfRooms)) {
								reportLogger.info(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
								test_steps.add(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
							}else {
								reportLogger.info("Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed. Expected: "+(Double.parseDouble(folioItemValues.get(ledgerAccounts.get(ledger)))*numberOfRooms)+" But found: "+amount);
							}
							
						} else {
							double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(ledgerAccounts.get(ledger)).substring(1));
							reportLogger.info(ledger+" - " + ledgerAccounts.get(ledger) + ": " + amount);
							
							if (amount == (Double.parseDouble(folioItemValues.get(ledgerAccounts.get(ledger)))*numberOfRooms)) {
								reportLogger.info(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
								test_steps.add(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
							}else {
								reportLogger.info("Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed. Expected: "+(Double.parseDouble(folioItemValues.get(ledgerAccounts.get(ledger)))*numberOfRooms)+" But found: "+amount);
							}
							
						}
					} else {
						for (int i = 0; i < strLedger.length; i++) {
							if (beforeDetailsOfAllLedgerCategories.containsKey(strLedger[i])) {
								double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strLedger[i]).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(strLedger[i]).substring(1));
								reportLogger.info("Incidentals - " + strLedger[i] + ": " + amount);
								
								if (amount == (Double.parseDouble(folioItemValues.get(strLedger[i]))*numberOfRooms)) {
									reportLogger.info(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
									test_steps.add(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
								}else {
									reportLogger.info("Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed. Expected: "+(Double.parseDouble(folioItemValues.get(strLedger[i]))*numberOfRooms)+" But found: "+amount);
								}
								
							} else {
								double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strLedger[i]).substring(1));
								reportLogger.info(ledger+" - " + strLedger[i] + ": " + amount);
								
								if (amount == (Double.parseDouble(folioItemValues.get(strLedger[i]))*numberOfRooms)) {
									reportLogger.info(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
									test_steps.add(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
								}else {
									reportLogger.info("Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed. Expected: "+(Double.parseDouble(folioItemValues.get(strLedger[i]))*numberOfRooms)+" But found: "+amount);
								}
								
							}
						}
					}

				
				
			}
				
		}
		
		
		public void validateLedgerReportDetailedView(WebDriver driver, HashMap<String, String> ledgerAccounts, 
				HashMap<String, String> folioItemValues, int numberOfRooms,
				ArrayList<String> reservationNumbers, ArrayList<String> guestNames, ArrayList<String> arrivalDates,
				HashMap<String, String> itemDescription, double roomChargeAmount, String currencySymbal, ArrayList<String> test_steps)
				throws InterruptedException, ParseException {
			
			Set<String> ledgers = ledgerAccounts.keySet();
			HashMap<String,ArrayList<String>> TransactionDetails = new HashMap<>();
			
			for (String ledger : ledgers) {

					TransactionDetails.clear();
					String[] allLedgers = ledgerAccounts.get(ledger).split(",");

					if (allLedgers.length == 1) {
						TransactionDetails = getTransactionDetailsList(driver, ledgerAccounts.get(ledger), test_steps);
						reportLogger.info(ledgerAccounts.get(ledger)+" - Transaction Details: "+TransactionDetails);
						
						if (TransactionDetails.get("Reservation #").containsAll(reservationNumbers)) {
							reportLogger.info("Reservation number "+reservationNumbers+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("Reservation number "+reservationNumbers+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}else {
							reportLogger.info("Failed, Reservation number "+reservationNumbers+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("AssertionError - Failed, Reservation number "+reservationNumbers+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}
						
						if (TransactionDetails.get("Guest/Account Name").containsAll(guestNames)) {
							reportLogger.info("Guest Name "+guestNames+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("Guest Name "+guestNames+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}else {
							reportLogger.info("Failed, Guest Name "+guestNames+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("AssertionError - Failed, Guest Name "+guestNames+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}
						
						if (TransactionDetails.get("Arrival Date").containsAll(arrivalDates)) {
							reportLogger.info("Arrival Date "+arrivalDates+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("Arrival Date "+arrivalDates+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}else {
							reportLogger.info("Failed, Arrival Date "+arrivalDates+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("AssertionError - Failed, Arrival Date "+arrivalDates+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}
						
						if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
							reportLogger.info("Date "+Utility.getCurrentDate("MMM dd,yyyy")+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("Date "+Utility.getCurrentDate("MMM dd,yyyy")+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}else {
							reportLogger.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}
						
						if (TransactionDetails.get("Item Description").contains(itemDescription.get(ledgerAccounts.get(ledger)))) {
							reportLogger.info("Item Description "+itemDescription.get(ledgerAccounts.get(ledger))+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("Item Description "+itemDescription.get(ledgerAccounts.get(ledger))+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}else {
							reportLogger.info("Failed, Item Description "+itemDescription.get(ledgerAccounts.get(ledger))+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(ledgerAccounts.get(ledger))+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
						}
						
						if (ledgerAccounts.get(ledger).equalsIgnoreCase("Room Charge")) {
							if (TransactionDetails.get("Amount").contains(currencySymbal+roomChargeAmount+"0")) {
								reportLogger.info("Amount "+roomChargeAmount+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
								test_steps.add("Amount "+roomChargeAmount+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							}else {
								reportLogger.info("Failed, Amount "+currencySymbal+roomChargeAmount+"0"+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
								test_steps.add("AssertionError - Failed, Amount "+currencySymbal+roomChargeAmount+"0"+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							}
						}else {
							if (TransactionDetails.get("Amount").contains(currencySymbal+folioItemValues.get(ledgerAccounts.get(ledger)))) {
								reportLogger.info("Amount "+folioItemValues.get(ledgerAccounts.get(ledger))+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
								test_steps.add("Amount "+folioItemValues.get(ledgerAccounts.get(ledger))+" is available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							}else {
								reportLogger.info("Failed, Amount "+currencySymbal+folioItemValues.get(ledgerAccounts.get(ledger))+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
								test_steps.add("AssertionError - Failed, Amount "+currencySymbal+folioItemValues.get(ledgerAccounts.get(ledger))+" is not available under "+ledgerAccounts.get(ledger)+" in Detailed View");
							}
						}
						
					} else {
						for (int i = 0; i < allLedgers.length; i++) {
							
							TransactionDetails.clear();
							TransactionDetails = getTransactionDetailsList(driver, allLedgers[i], test_steps);
							
							if (TransactionDetails.get("Reservation #").containsAll(reservationNumbers)) {
								reportLogger.info("Reservation number "+reservationNumbers+" is available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("Reservation number "+reservationNumbers+" is available under "+allLedgers[i]+" in Detailed View");
							}else {
								reportLogger.info("Failed, Reservation number "+reservationNumbers+" is not available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("AssertionError - Failed, Reservation number "+reservationNumbers+" is not available under "+allLedgers[i]+" in Detailed View");
							}
							
							if (TransactionDetails.get("Guest/Account Name").containsAll(guestNames)) {
								reportLogger.info("Guest Name "+guestNames+" is available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("Guest Name "+guestNames+" is available under "+allLedgers[i]+" in Detailed View");
							}else {
								reportLogger.info("Failed, Guest Name "+guestNames+" is not available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("AssertionError - Failed, Guest Name "+guestNames+" is not available under "+allLedgers[i]+" in Detailed View");
							}
							
							if (TransactionDetails.get("Arrival Date").containsAll(arrivalDates)) {
								reportLogger.info("Arrival Date "+arrivalDates+" is available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("Arrival Date "+arrivalDates+" is available under "+allLedgers[i]+" in Detailed View");
							}else {
								reportLogger.info("Failed, Arrival Date "+arrivalDates+" is not available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("AssertionError - Failed, Arrival Date "+arrivalDates+" is not available under "+allLedgers[i]+" in Detailed View");
							}
							
							if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
								reportLogger.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+allLedgers[i]+" in Detailed View");
							}else {
								reportLogger.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+allLedgers[i]+" in Detailed View");
							}
							
							if (TransactionDetails.get("Item Description").contains(itemDescription.get(allLedgers[i]))) {
								reportLogger.info("Item Description "+itemDescription.get(allLedgers[i])+" is available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("Item Description "+itemDescription.get(allLedgers[i])+" is available under "+allLedgers[i]+" in Detailed View");
							}else {
								reportLogger.info("Failed, Item Description "+itemDescription.get(allLedgers[i])+" is not available under "+allLedgers[i]+" in Detailed View");
								test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(allLedgers[i])+" is not available under "+allLedgers[i]+" in Detailed View");
							}
							
							if (allLedgers[i].equalsIgnoreCase("Room Charge")) {
								if (TransactionDetails.get("Amount").contains(currencySymbal+roomChargeAmount+"0")) {
									reportLogger.info("Amount "+roomChargeAmount+" is available under "+allLedgers[i]+" in Detailed View");
									test_steps.add("Amount "+roomChargeAmount+" is available under "+allLedgers[i]+" in Detailed View");
								}else {
									reportLogger.info("Failed, Amount "+currencySymbal+roomChargeAmount+"0"+" is not available under "+allLedgers[i]+" in Detailed View");
									test_steps.add("AssertionError - Failed, Amount "+currencySymbal+roomChargeAmount+"0"+" is not available under "+allLedgers[i]+" in Detailed View");
								}
							}else {
								if (TransactionDetails.get("Amount").contains(currencySymbal+folioItemValues.get(allLedgers[i]))) {
									reportLogger.info("Amount "+folioItemValues.get(allLedgers[i])+" is available under "+allLedgers[i]+" in Detailed View");
									test_steps.add("Amount "+folioItemValues.get(allLedgers[i])+" is available under "+allLedgers[i]+" in Detailed View");
								}else {
									reportLogger.info("Failed, Amount "+currencySymbal+folioItemValues.get(allLedgers[i])+" is not available under "+allLedgers[i]+" in Detailed View");
									test_steps.add("AssertionError - Failed, Amount "+currencySymbal+folioItemValues.get(allLedgers[i])+" is not available under "+allLedgers[i]+" in Detailed View");
								}
							}

							
						}
					}

				
				
				
				
			}
			

			
		}
		
		
		
		
		//This method is for Negative testing
		public void validateLedgerReportSummaryViewNegativeTesting(WebDriver driver, HashMap<String, String> beforeLedgerCategoryDetails, HashMap<String, String> beforeDetailsOfAllLedgerCategories, 
				HashMap<String, String> afterLedgerCategoryDetails, HashMap<String, String> afterDetailsOfAllLedgerCategories, HashMap<String, String> ledgerAccounts, 
				HashMap<String, Double> ledgerAmounts, HashMap<String, String> folioItemValues, int numberOfRooms, ArrayList<String> test_steps)
				throws InterruptedException, ParseException {
			
			Set<String> ledgers = ledgerAccounts.keySet();
			
			for (String ledger : ledgers) {
				
					//Summary view
					if (beforeLedgerCategoryDetails.containsKey(ledger)) {
						double totalAmount = Double.parseDouble(afterLedgerCategoryDetails.get(ledger).substring(1))
								- Double.parseDouble(beforeLedgerCategoryDetails.get(ledger).substring(1));
						reportLogger.info(ledger+": " + totalAmount);
						
						if (totalAmount != ledgerAmounts.get(ledger)) {
							reportLogger.info(ledger+" amount validated successfully in Summary View");
							test_steps.add(ledger+" amount validated successfully in Summary View");
						}else {
							reportLogger.info("Failed - "+ledger+" amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - "+ledger+" amount in Summary View validation failed");
						}
						
					} else {
						double totalAmount = Double.parseDouble(afterLedgerCategoryDetails.get(ledger).substring(1));
						reportLogger.info(ledger+": " + totalAmount);
						
						if (totalAmount != ledgerAmounts.get(ledger)) {
							reportLogger.info(ledger+" amount validated successfully in Summary View");
							test_steps.add(ledger+" amount validated successfully in Summary View");
						}else {
							reportLogger.info("Failed - "+ledger+" amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - "+ledger+" amount in Summary View validation failed");
						}
						
					}

					String[] strLedger = ledgerAccounts.get(ledger).split(",");

					if (strLedger.length == 1) {
						if (beforeDetailsOfAllLedgerCategories.containsKey(ledgerAccounts.get(ledger))) {
							double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(ledgerAccounts.get(ledger)).substring(1))
									- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(ledgerAccounts.get(ledger)).substring(1));
							reportLogger.info(ledgerAccounts.get(ledger)+" - " + ledgerAccounts.get(ledger) + ": " + amount);
							
							if (amount != (Double.parseDouble(folioItemValues.get(ledgerAccounts.get(ledger)))*numberOfRooms)) {
								reportLogger.info(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
								test_steps.add(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
							}else {
								reportLogger.info("Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed");
							}
							
						} else {
							double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(ledgerAccounts.get(ledger)).substring(1));
							reportLogger.info(ledger+" - " + ledgerAccounts.get(ledger) + ": " + amount);
							
							if (amount != (Double.parseDouble(folioItemValues.get(ledgerAccounts.get(ledger)))*numberOfRooms)) {
								reportLogger.info(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
								test_steps.add(ledger+" - "+ledgerAccounts.get(ledger)+" amount validated successfully in Summary View");
							}else {
								reportLogger.info("Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - "+ledger+" - "+ledgerAccounts.get(ledger)+" amount in Summary View validation failed.");
							}
							
						}
					} else {
						for (int i = 0; i < strLedger.length; i++) {
							if (beforeDetailsOfAllLedgerCategories.containsKey(strLedger[i])) {
								double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strLedger[i]).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(strLedger[i]).substring(1));
								reportLogger.info("Incidentals - " + strLedger[i] + ": " + amount);
								
								if (amount != (Double.parseDouble(folioItemValues.get(strLedger[i]))*numberOfRooms)) {
									reportLogger.info(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
									test_steps.add(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
								}else {
									reportLogger.info("Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed");
								}
								
							} else {
								double amount = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strLedger[i]).substring(1));
								reportLogger.info(ledger+" - " + strLedger[i] + ": " + amount);
								
								if (amount != (Double.parseDouble(folioItemValues.get(strLedger[i]))*numberOfRooms)) {
									reportLogger.info(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
									test_steps.add(ledger+" - "+strLedger[i]+" amount validated successfully in Summary View");
								}else {
									reportLogger.info("Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - "+ledger+" - "+strLedger[i]+" amount in Summary View validation failed");
								}
								
							}
						}
					}

				
				
			}
				
		}
		
		
		
		
	

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################ Method Name: <validateGeneratedOnValue> Description: <This
	 * method validates value of Generated On in Report generated> Input parameters:
	 * <WebDriver driver, ArrayList<String> test_steps> Return value: <void> Created
	 * By: <Naveen Kadthala> Created On: <09/15/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public void validateGeneratedOnValue(WebDriver driver, ArrayList<String> test_steps) {
		try {
			Elements_Reports res = new Elements_Reports(driver);

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");
			LocalDateTime now = LocalDateTime.now();

			String expValue = dtf.format(now);
			String actValue = res.GRGeneratedOnValue.getText().trim();

			System.out.println("expValue="+expValue);
			System.out.println("actValue="+actValue);
			
			if (expValue.equals(actValue)) {
				reportLogger.info("Success - Generated On value validation");
				test_steps.add("Success - Generated On value validation");
			} else {
				reportLogger.info("FAIL - Generated On value validation");
				test_steps.add("AssertionError - FAIL - Generated On value validation-<Br>");
			}
		} catch (Exception e) {
			reportLogger.info("FAIL - Generated On value validation");
			test_steps.add("AssertionError - FAIL - Generated On value validation-<Br>" + e.toString());
		} catch (Error e) {
			reportLogger.info("FAIL - Generated On value validation");
			test_steps.add("AssertionError - FAIL - Generated On value validation-<Br>" + e.toString());
		}
	}
}
