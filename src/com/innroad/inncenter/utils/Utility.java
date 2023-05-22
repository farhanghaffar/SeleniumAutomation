package com.innroad.inncenter.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpUtils;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;
import org.apache.poi.hssf.record.formula.SheetNameFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.Keys;

import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Admin;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.tests.Uri;
import com.innroad.inncenter.tests.var;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Admin;
import com.innroad.inncenter.webelements.WebElementsLogin;
import com.relevantcodes.extentreports.LogStatus;

public class Utility extends TestCore {

	public static String Account_Number = "";
	public static int BeforeSuite = 0;
	public static int count = 1;
	public static int reset_count = 0;
	public static HashMap<String, Integer> reTry = new HashMap<String, Integer>();
	public static HashMap<String, String> insertTestName = new HashMap<String, String>();
	public static int roomIndex = 1;
	public static int roomIndexEnd = 9;
	public static ArrayList<String> login = new ArrayList<>();
	public static ArrayList<String> login_GS = new ArrayList<>();
	public static ArrayList<String> login_CP = new ArrayList<>();
	public static ArrayList<String> loginWPI = new ArrayList<>();
	public static ArrayList<String> loginRateV2 = new ArrayList<>();
	public static ArrayList<String> loginReportsV2 = new ArrayList<>();
	public static boolean toggle = false;
	public static String RoomNo = "";
	public static String userId = null;
	public static String rateplanName = null;
	public static int row = 0;
	public static ArrayList<String> login_Group = new ArrayList<>();

	public static ArrayList<WebDriver> drivers = new ArrayList<>();
	public static HashMap<String, WebDriver> AddDriver = new HashMap<String, WebDriver>();
	public static boolean isBlackFirst = false;
	public static String envURL = "";
	public static boolean isPdfReading = false;

	public static ArrayList<String> ClassesName = new ArrayList<>();
	public static int totalRoomClasses = 0;
	public static String SeasonName = "All Year Season";
	public static String RoomClassName = "";
	public static String RoomClassabv = "";
	public static String ReservationNo = "";
	public static File fileName = null;
	public static String guestFirstName = null;
	public static String guestLastName = null;
	public static ArrayList<String> LineItemIconSource = new ArrayList<>();
	public static String ReportsProperty = "";
	public static String ReportsProperty2 = "";
	public static String ReportsProperty3 = "";
	public static String UserName = "";
	public static String WestPointInnProperty = "";
	public static String clientCurrency = "";
	public static String clientCurrencySymbol = "";

	public static String DELIM = "";
	public static String DELIMS = "\\|";
	public static String SEASONDELIM = "";

	public static boolean isExecutable(String test_name, excel_reader excel) {

		String sheetName = "testcases";
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {

			if (excel.getCellData(sheetName, "test_name", rowNum).equals(test_name)) {
				if (excel.getCellData(sheetName, "runmode", rowNum).equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public static void getLogin(excel_reader excel, String sheetName) {
		// ArrayList<String> getLogin = new ArrayList();
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			login.add(excel.getCellData(sheetName, "URL", rowNum));
			login.add(excel.getCellData(sheetName, "ClientId", rowNum));
			login.add(excel.getCellData(sheetName, "UserName", rowNum));
			login.add(excel.getCellData(sheetName, "Password", rowNum));
		}
	}

	public static void getLogin_GS(excel_reader excel, String sheetName) {
		// ArrayList<String> getLogin = new ArrayList();
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			login_GS.add(excel.getCellData(sheetName, "URL", rowNum));
			login_GS.add(excel.getCellData(sheetName, "ClientId", rowNum));
			login_GS.add(excel.getCellData(sheetName, "UserName", rowNum));
			login_GS.add(excel.getCellData(sheetName, "Password", rowNum));
		}
	}
	
	public static void getGroup_Login(excel_reader excel, String sheetName) {

		// ArrayList<String> getLogin = new ArrayList();

		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			login_Group.add(excel.getCellData(sheetName, "URL", rowNum));
			login_Group.add(excel.getCellData(sheetName, "ClientId", rowNum));
			login_Group.add(excel.getCellData(sheetName, "UserName", rowNum));
			login_Group.add(excel.getCellData(sheetName, "Password", rowNum));
		}

	}

	public static void getLogin_CP(excel_reader excel, String sheetName) {
		// ArrayList<String> getLogin = new ArrayList();
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			login_CP.add(excel.getCellData(sheetName, "URL", rowNum));
			login_CP.add(excel.getCellData(sheetName, "ClientId", rowNum));
			login_CP.add(excel.getCellData(sheetName, "UserName", rowNum));
			login_CP.add(excel.getCellData(sheetName, "Password", rowNum));
		}
	}

	public static void getLoginWPI(excel_reader excel, String sheetName) {
		// ArrayList<String> getLogin = new ArrayList();
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			loginWPI.add(excel.getCellData(sheetName, "URL", rowNum));
			loginWPI.add(excel.getCellData(sheetName, "ClientId", rowNum));
			loginWPI.add(excel.getCellData(sheetName, "UserName", rowNum));
			loginWPI.add(excel.getCellData(sheetName, "Password", rowNum));
		}
	}

	public static void getLoginRateV2(excel_reader excel, String sheetName) {
		// ArrayList<String> getLogin = new ArrayList();
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			loginRateV2.add(excel.getCellData(sheetName, "URL", rowNum));
			loginRateV2.add(excel.getCellData(sheetName, "ClientId", rowNum));
			loginRateV2.add(excel.getCellData(sheetName, "UserName", rowNum));
			loginRateV2.add(excel.getCellData(sheetName, "Password", rowNum));
		}
	}
	
	//This method is to get data for ReporstVV2 login
	public static void getLoginReportsV2(excel_reader excel, String sheetName) {
		// ArrayList<String> getLogin = new ArrayList();
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			loginReportsV2.add(excel.getCellData(sheetName, "URL", rowNum));
			loginReportsV2.add(excel.getCellData(sheetName, "ClientId", rowNum));
			loginReportsV2.add(excel.getCellData(sheetName, "UserName", rowNum));
			loginReportsV2.add(excel.getCellData(sheetName, "Password", rowNum));
		}
	}
	
	
	public static void getGroup_Login(excel_reader excel, String sheetName) {

		 // ArrayList<String> getLogin = new ArrayList();

		 for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
		login_Group.add(excel.getCellData(sheetName, "URL", rowNum));
		login_Group.add(excel.getCellData(sheetName, "ClientId", rowNum));
		login_Group.add(excel.getCellData(sheetName, "UserName", rowNum));
		login_Group.add(excel.getCellData(sheetName, "Password", rowNum));
		}

		 }

	// TestNG Parameterization
	public static Object[][] getData(String sheetName, excel_reader excel) {
		// return test data;
		// read test data from xls

		/*
		 * System.out.println("Sheet name: "+sheetName);
		 * System.out.println("excel: "+excel);
		 */

		int rows = excel.getRowCount(sheetName) - 1;
		if (rows <= 0) {
			Object[][] testData = new Object[1][0];
			return testData;

		}
		rows = excel.getRowCount(sheetName); // 3
		int cols = excel.getColumnCount(sheetName);
		Object data[][] = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {

			for (int colNum = 0; colNum < cols; colNum++) {
				// System.out.println(rowNum-2 + " data "+ sheetName +", "+
				// colNum+", "+ rowNum);
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}

		return data;

	}

	// ***Read Data From Excel Using Map****/

	public static Object[][] getDataMap(String sheetName, excel_reader excel) {
		return excel.getMapData(sheetName);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: customDateTimeFormat() ' Description: This Utility method
	 * returns a current time stamp which is used to generate the unique extent
	 * report file for each run ' Input parameters: NA ' Return value:
	 * Time-stamp in String representation ' Created By: Surender Avula ' '
	 * Created On: 06/05/2017 (MM/DD/YYYY) ' Modified By | Description of
	 * Modification:
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static String getTimeStamp() {
		return new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()).replaceAll("[-: ]", "_");
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: archiveExtentReports() ' Description: This Utility method
	 * will back up the old reports present in the 'reports' directory to
	 * 'reports\old' directory. ' Input parameters: path ' Return value: NA ' '
	 * Created By: Surender Avula ' Created On: 06/06/2017 (MM/DD/YYYY) '
	 * Modified By | Description of Modification:
	 * ------------------------------------------ 07/04/2017:Surender Avula:
	 * 1.Renamed method name from backUpReportFiles() 2.Add path argument to
	 * method. 3.Modified implementation such that checks for the path argument
	 * value, root directory and archived (sub-directory) in project working
	 * directly and report accordingly. 4.Added comments for better readability
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static void archiveExtentReports(String path) {

		// verify that path should not be empty
		if (path != "") {

			// file object creation for 'archived' folder present inside
			// extent-reports
			String archiveFolderName = "archived";
			File archiveFolder = new File(path + "\\" + archiveFolderName);

			try {

				// file object creation for extent-reports folder which is root
				// directory for entent reports
				File reportsRootDir = new File(path);

				// verifying root dir (extent-reports) is present in project
				// working directory or not
				if (reportsRootDir.exists()) {

					// get all the files and sub-directory names
					String[] dirListNames = reportsRootDir.list();

					// check with 'archived' sub-directory is present or not in
					// root dir.
					if (!Arrays.asList(dirListNames).contains(archiveFolderName)) {
						try {
							archiveFolder.mkdir(); // create 'archived' folder
							// if not alread present
						} catch (Exception e) {
							System.out.println("failed to created archive folder , " + e.getMessage());
						}
					}

					// move all the existing (if any) reports to archived folder
					for (String name : dirListNames) {
						File reportFile = new File(reportsRootDir, name);
						if (reportFile.isFile()) {
							reportFile
									.renameTo(new File(archiveFolder.getAbsolutePath() + "\\" + reportFile.getName()));
						}
					}
				} else {
					System.out.println(reportsRootDir.getName()
							+ " folder is not present in project working directory, it should be created by extent-reports library "
							+ "hence please verify extent-reports statements in testCore.java file.");
				}

			} catch (NullPointerException npe) {
				System.out.println(npe.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {
			System.out.println("Path argument is empty, please pass valid path value.");
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: captureScreenShot(String, WebDriver) ' Description: This
	 * Utility method returns screenshot file path ' Input parameters: String,
	 * WebDriver ' Return value: String ' Created By: Surender Avula ' Created
	 * On: 06/04/2018 (MM/DD/YYYY) ' Modified By | Description of Modification:
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static String captureScreenShot(String name, WebDriver driver) {
		String screenShotPath = System.getProperty("user.dir") + "\\screenshots\\" + name + ".png";
		TakesScreenshot screenShot = (TakesScreenshot) driver;
		File srcFile = screenShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(screenShotPath);
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenShotPath;
	}

	public static void ScrollToElement(WebElement element, WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		// Wait.wait2Second();
	}

	public static void ScrollToElement_roomClass(WebElement element, WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

	}

	public static void ScrollToEnd(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void ScrollToUp(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	public static void ScrollToViewElementINMiddle(WebDriver driver, WebElement ele) throws InterruptedException {
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, ele);
	}

	public static String getScreenhot(WebDriver driver) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/screenshots/" + getTimeStamp() + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static boolean return_element_status_after_explicit_wait(String Element, WebDriver driver) {

		boolean element_status;
		try {
			Wait.explicit_wait_xpath(Element, driver);
			element_status = true;
		} catch (Exception e)

		{
			element_status = false;
		}
		return element_status;
	}

	public static void updateReport(Exception e, String logMessage, String testName, String pageElement,
			WebDriver driver) {
		System.out.println(driver);

		String strException = e.toString();

		try {
			if (strException.contains("NoSuchElementException")) {

				test.log(LogStatus.FAIL,
						logMessage + "," + " <br> <b> Exception Details: </b>"
								+ strException.substring(0, strException.indexOf("}") + 1)
								+ "<br> <b> Attaching screenshot below : </b>"
								+ test.addScreenCapture(Utility.captureScreenShot(
										testName + "_" + pageElement + "_" + Utility.getTimeStamp(), driver)));

			} else if (strException.contains("TimeoutException")) {

				test.log(LogStatus.FAIL,
						logMessage + "," + " <br> <b> Exception Details: </b>"
								+ strException.substring(0, strException.indexOf("Build"))
								+ "<br> <b> Attaching screenshot below : </b>"
								+ test.addScreenCapture(Utility.captureScreenShot(
										testName + "_" + pageElement + "_" + Utility.getTimeStamp(), driver)));
			} else if (strException.contains("StaleElementReferenceException")) {

				test.log(LogStatus.FAIL,
						logMessage + "," + " <br> <b> Exception Details: </b>"
								+ strException.substring(0, strException.indexOf("(") + 1)
								+ "<br> <b> Attaching screenshot below : </b>"
								+ test.addScreenCapture(Utility.captureScreenShot(
										testName + "_" + pageElement + "_" + Utility.getTimeStamp(), driver)));
			} else {

				test.log(LogStatus.FAIL,
						logMessage + "," + " <br> <b> Exception Details: </b>" + strException
								+ "<br> <b> Attaching screenshot below : </b>"
								+ test.addScreenCapture(Utility.captureScreenShot(
										testName + "_" + pageElement + "_" + Utility.getTimeStamp(), driver)));
			}
		} catch (Exception ex) {
			test.log(LogStatus.FAIL, logMessage + "," + " <br> <b> Exception Details: </b>"
					+ strException.substring(0, strException.indexOf("at") + 1)
					+ "<br> <b> Attaching screenshot below : </b>" + test.addScreenCapture(Utility
							.captureScreenShot(testName + "_" + pageElement + "_" + Utility.getTimeStamp(), driver)));
		}
		app_logs.error(logMessage + "\n");

		e.printStackTrace();

		try {
			Thread.sleep(500);
		} catch (InterruptedException ie) {

		}

		throw new SkipException(e.getMessage());

	}

	public static void updateReport(Error e, String logMessage, String testName, String pageElement, WebDriver driver) {

		String strError = e.toString();

		if (strError.contains("AssertionError")) {

			test.log(LogStatus.FAIL, logMessage + "," + " <br> <b> Exception Details: </b>" + strError
					+ "<br> <b> Attaching screenshot below : </b>" + test.addScreenCapture(Utility
							.captureScreenShot(testName + "_" + pageElement + "_" + Utility.getTimeStamp(), driver)));

		}

		app_logs.error(logMessage + "\n");

		e.printStackTrace();

		try {
			Thread.sleep(500);
		} catch (InterruptedException ie) {

		}

		throw new SkipException(e.getMessage());

	}

	public static WebElement ElementFinderUntillNotShow(By locator, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(locator);
			element.getTagName();
			return element;
		} catch (StaleElementReferenceException e) {
			System.out.println("in wait");
			return ElementFinderUntillNotShow(locator, driver);
		}
	}

	public static void AddTest_IntoReport(String TestName, String TestDescription, String TestCatagory,
			ArrayList<String> test_steps) {

		test = extent.startTest(TestName, TestDescription).assignCategory(TestCatagory).assignCategory("Regression");
		for (int i = 0; i < test_steps.size(); i++) {

			if (test_steps.get(i).contains("Failed") || test_steps.get(i).contains("Assertion")) {
				test.log(LogStatus.FAIL, test_steps.get(i));
			} else {

				test.log(LogStatus.PASS, test_steps.get(i));
			}
		}
		test_steps.clear();

	}

	public static void AddTest_IntoReport(ArrayList<String> TestName, ArrayList<String> TestDescription,
			ArrayList<String> TestCatagory, ArrayList<String> test_steps) {

		System.out.println("Size : " + TestName.size());
		int Count = 0;

		for (int i = 0; i < test_steps.size(); i++) {

			if (test_steps.get(i).equalsIgnoreCase("Break") || i == 0) {

				test = extent.startTest(TestName.get(Count), TestDescription.get(Count))
						.assignCategory(TestCatagory.get(Count)).assignCategory("Regression");
				Count++;

				if (!test_steps.get(i).equals("Break")) {
					test.log(LogStatus.PASS, test_steps.get(i));
				}

			}

			else {
				test.log(LogStatus.PASS, test_steps.get(i));
			}

		}
		test_steps.clear();

	}

	public static String GenerateRandomNumber() {
		String randomNumber = "1234567890";
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			int index = (int) (randomNumber.length() * Math.random());
			sb.append(randomNumber.charAt(index));
		}
		return sb.toString();
	}

	public static String GenerateRandomString() {
		Random rand = new Random();

		// Generate random integers in range 0 to 999
		int rand_num = rand.nextInt(999);

		return String.valueOf(rand_num);
	}

	public static ChromeOptions ChromOption() {
		System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);
		// logs.enable(LogType.CLIENT, Level.ALL);
		logs.enable(LogType.DRIVER, Level.ALL);
		logs.enable(LogType.PERFORMANCE, Level.ALL);
		// logs.enable(LogType.PROFILER, Level.ALL);
		// logs.enable(LogType.SERVER, Level.ALL);
		DesiredCapabilities dc = DesiredCapabilities.chrome();
		dc.setCapability(CapabilityType.LOGGING_PREFS, logs);
		return options;
	}

	public static String GenerateRandomString15Digit() {
		Random rand = new Random();
		long first14 = (long) (Math.random() * 100000000000000L);
		// Generate random integers in range 0 to 9
		int rand_num = rand.nextInt(10);
		String RandomNumber1 = String.valueOf(first14);
		String RandomNumber2 = String.valueOf(rand_num);
		RandomNumber2 = RandomNumber1 + RandomNumber2;
		return RandomNumber2;
	}

	public static String generateRandomString() {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < 10; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());
			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();

	}

	public static String generateRandomStringWithGivenLength(int length) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < length; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());
			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();

	}

	public static String get_Year(String date) {

		Integer[] data = new Integer[3];

		StringTokenizer st = new StringTokenizer(date, "/");
		int i = 0;
		while (st.hasMoreElements()) {
			data[i] = Integer.parseInt(st.nextToken());
			i++;
		}
		return "" + data[2];
	}

	public static String get_MonthYear(String date) {
		String month = null;

		Integer[] data = new Integer[3];

		StringTokenizer st = new StringTokenizer(date, "/");
		int i = 0;
		while (st.hasMoreElements()) {
			data[i] = Integer.parseInt(st.nextToken());
			i++;
		}
		if (data[1] == 01 || data[1] == 1) {
			month = "January";
		}

		if (data[1] == 02 || data[1] == 2) {
			month = "February";
		}

		if (data[1] == 03 || data[1] == 3) {
			month = "March";
		}

		if (data[1] == 04 || data[1] == 4) {
			month = "April";
		}

		if (data[1] == 05 || data[1] == 5) {
			month = "May";
		}

		if (data[1] == 06 || data[1] == 6) {
			month = "June";
		}

		if (data[1] == 07 || data[1] == 7) {
			month = "July";
		}

		if (data[1] == 8) {
			month = "August";
		}

		if (data[1] == 9) {
			month = "September";
		}

		if (data[1] == 10) {
			month = "October";
		}

		if (data[1] == 11) {
			month = "November";
		}

		if (data[1] == 12) {
			month = "December";
		}

		month = month + " " + data[2];
		return month;

	}

	public static String get_Month(String date) {
		String month = null;

		Integer[] data = new Integer[3];

		StringTokenizer st = new StringTokenizer(date, "/");
		int i = 0;
		while (st.hasMoreElements()) {
			data[i] = Integer.parseInt(st.nextToken());
			i++;
		}
		if (data[1] == 01 || data[1] == 1) {
			month = "Jan";
		}

		if (data[1] == 02 || data[1] == 2) {
			month = "Feb";
		}

		if (data[1] == 03 || data[1] == 3) {
			month = "Mar";
		}

		if (data[1] == 04 || data[1] == 4) {
			month = "Apr";
		}

		if (data[1] == 05 || data[1] == 5) {
			month = "May";
		}

		if (data[1] == 06 || data[1] == 6) {
			month = "Jun";
		}

		if (data[1] == 07 || data[1] == 7) {
			month = "Jul";
		}

		if (data[1] == 8) {
			month = "Aug";
		}

		if (data[1] == 9) {
			month = "Sep";
		}

		if (data[1] == 10) {
			month = "Oct";
		}

		if (data[1] == 11) {
			month = "Nov";
		}

		if (data[1] == 12) {
			month = "Dec";
		}

		return month;

	}

	public static String getDay(String date) {
		String day = null;

		Integer[] data = new Integer[3];

		StringTokenizer st = new StringTokenizer(date, "/");
		int i = 0;
		while (st.hasMoreElements()) {
			data[i] = Integer.parseInt(st.nextToken());
			i++;
		}

		day = data[0].toString();

		if (data[0] <= 9) {

			day = day.replace("0", "");
		}
		day = day.trim();
		return day;

	}

	public static String getYear(String date) {
		String day = null;

		Integer[] data = new Integer[3];

		StringTokenizer st = new StringTokenizer(date, "/");
		int i = 0;
		while (st.hasMoreElements()) {
			data[i] = Integer.parseInt(st.nextToken());
			i++;
		}

		day = data[2].toString();

		day = day.trim();
		return day;

	}

	public static File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	public static boolean compareImage(File fileA, File fileB) {
		try {
			// take buffer data from botm image files //

			BufferedImage biA = ImageIO.read(fileA);
			DataBuffer dbA = biA.getData().getDataBuffer();
			int sizeA = dbA.getSize();
			BufferedImage biB = ImageIO.read(fileB);
			DataBuffer dbB = biB.getData().getDataBuffer();
			int sizeB = dbB.getSize();
			// compare data-buffer objects //
			if (sizeA == sizeB) {
				for (int i = 0; i < sizeA; i++) {
					if (dbA.getElem(i) != dbB.getElem(i)) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to compare image files ...");
			return false;
		}
	}

	public static String checkPDF(String fileName) throws IOException {
		// File getFile =
		// Utility.getLatestFilefromDir(System.getProperty("user.dir") +
		// File.separator + "externalFiles"
		// + File.separator + "downloadFiles" + File.separator);

		File getFile = new File(System.getProperty("user.dir") + File.separator + "externalFiles" + File.separator
				+ "downloadFiles" + File.separator + fileName);
		System.out.println(getFile.getAbsolutePath());
		System.out.println(getFile.getName());
		PDDocument document = PDDocument.load(getFile);
		document.getClass();
		String pdfFileInText = null;
		if (!document.isEncrypted()) {
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			PDFTextStripper tStripper = new PDFTextStripper();
			pdfFileInText = tStripper.getText(document);
			document.close();

			if (getFile.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}
		}
		return pdfFileInText;
	}

	public static void deleteFile(File file) {

		try {
			if (file.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static WebElement expandRootElement(WebDriver driver, WebElement element) {
		WebElement ele = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot",
				element);
		return ele;
	}

	public static String download_status(WebDriver driver) throws InterruptedException {

		driver.get("chrome://downloads/");
		Wait.wait3Second();
		WebElement root1 = driver.findElement(By.tagName("downloads-manager"));

		// Get shadow root element
		WebElement shadowRoot1 = expandRootElement(driver, root1);

		WebElement root2 = shadowRoot1.findElement(By.cssSelector("downloads-item"));
		WebElement shadowRoot2 = expandRootElement(driver, root2);

		WebElement anc = shadowRoot2.findElement(By.cssSelector("#file-link"));
		WebElement name = shadowRoot2.findElement(By.cssSelector("#name"));
		WebElement root3 = shadowRoot2.findElement(By.cssSelector("#description"));
		String fileName = name.getText();
		System.out.println(fileName);
		System.out.println(anc.getText());
		String fileName2 = anc.getText();
		int couter = 0;

		while (!root3.getText().isEmpty() && couter < 10) {
			System.out.println(root3.getText());
			couter++;
			Wait.wait2Second();
		}

		if (root3.getText().isEmpty()) {
			System.out.println("File downloaded check it");
		} else {
			System.out.println("download fail");
			assertFalse(true, "Failed: download Failed");
		}
		driver.navigate().back();
		if (fileName.isEmpty()) {
			return fileName2;
		} else {
			return fileName;
		}
	}

	public static String GettingDate(WebDriver driver) {
		java.util.Date d = new java.util.Date();
		String onlyDate = d.toString().substring(8, 10);
		// app_logs.info("Today Date : "+onlyDate);
		return onlyDate;
	}

	public static String GeetingNextWeek(WebDriver driver) {

		Calendar calendar = Calendar.getInstance();
		app_logs.info("The current date is : " + calendar.getTime());
		calendar.add(Calendar.DATE, +7);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String nextWeek = dateFormat.format(calendar.getTime());
		app_logs.info("After Convert 7 days later: " + nextWeek);
		String onlyDate = nextWeek.toString().substring(8, 10);
		app_logs.info("OnlyDate " + onlyDate);
		return onlyDate;

	}

	public static String GettingWeekDay(WebDriver driver) {
		Date date = new Date();
		String onlyDay = date.toString().substring(0, 3);
		app_logs.info("Today Date : " + onlyDay);
		return onlyDay;
	}

	public static String GettingNextMonday(WebDriver driver) {

		Calendar calendar = Calendar.getInstance();

		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int days = Calendar.SUNDAY - weekday;
		int count = days;
		if (days < 0) {
			// this will usually be the case since Calendar.SUNDAY is the
			// smallest
			days += 7;
		}
		calendar.add(Calendar.DAY_OF_YEAR, days - count);
		Date date = calendar.getTime();
		app_logs.info("Next Monday Date : " + date);
		String onlyDate = date.toString().substring(8, 10);
		return onlyDate;
	}

	public static String convertDollarToNormalAmount(WebDriver driver, String Amount) {

		// String finalString = String.valueOf(Amount).split("\\.")[0];
		// String number = finalString.replaceAll("[^0-9]", "");
		// System.out.println("After Converting the amount is: " + number);
		if (Amount.contains("$")) {
			Amount = Amount.replace("$", "");
		}
		if (Amount.contains("(")) {
			Amount = Amount.replace("(", "");
		}
		if (Amount.contains(")")) {
			Amount = Amount.replace(")", "");
		}
		System.out.println("After Converting the amount is: " + Amount);
		Amount = Amount.trim();
		return Amount;
	}

	public static String RemoveDollarandSpaces(WebDriver driver, String Amount) {

		Utility.app_logs.info("Amount :  " + Amount);
		String number = Amount.replace("$", "");
		Utility.app_logs.info("Remove Dollar :  " + number);
		number = number.replace(" ", "");
		Utility.app_logs.info("Remove Space :  " + number);
		number = number.trim();
		return number;
	}

	public static String getDatePast_FutureDate(int Days) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));

		c.add(Calendar.DATE, Days);
		String getDate = dateFormat.format(c.getTime());
		return getDate;
	}

	public static boolean isElementPresent(WebDriver driver, By locatorKey) {
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		try {
			driver.findElement(locatorKey);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}

	}

	public static String getCurrentDate(String formate) {
		DateFormat requiredDateFormat = new SimpleDateFormat(formate);
		requiredDateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		String date = requiredDateFormat.format(new Date());
		System.out.println(date);
		return date;
	}
	public static String dayNameWithDateIncrement(String startDate, int noOfDays) throws ParseException
	{
		String dateWithDay="";	
		for(int i=0;i<=noOfDays-1;i++)
			{	
				String dt = startDate; 
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(dt));
				c.add(Calendar.DATE, i);  
				dt = sdf.format(c.getTime());
				Date date=sdf.parse(dt);
				DateFormat format2=new SimpleDateFormat("EE");
				String finalDay=format2.format(date);		
				System.out.println(dt+"_"+finalDay);
				dateWithDay=dt+"_"+finalDay;
			}	
		return dateWithDay;
	}																								

	public static String GetNextDate(int Day, String formateStr) {
		SimpleDateFormat format = new SimpleDateFormat(formateStr);
		format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		final Date date = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, Day);
		return format.format(calendar.getTime());
	}

	public static void ScrollToElement_NoWait(WebElement element, WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

	}

	/*
	 * #########################################################################
	 * *########################################################################
	 * #*########################**'
	 * 
	 * Method Name:
	 * 
	 * GetCurrentWeekRange() ' Description: This Utility method will give us the
	 * date range of the current month. The month starts from the 1st and ends
	 * on 28/29 or 30 or 31 ' Input parameters: ' Return value: string ' '
	 * Created By: Aqsa ' Created On: 04/16/2020 (MM/DD/YYYY) '
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static String GetCurrentMonthDateRange() {
		Calendar c = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("d MMM,yyyy");
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = 1;
		c.set(year, month, day);
		int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String StartDay = dateFormat.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
		System.out.println("First Day of month: " + StartDay);
		String EndDay = dateFormat.format(c.getTime());
		System.out.println("Last Day of month: " + EndDay);
		return StartDay + ";" + EndDay;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: GetCurrentWeekRange() ' Description: This Utility method
	 * will give us the date range of the current week. The week starts from the
	 * SUNDAY and ends on Saturday ' Input parameters: ' Return value: string '
	 * ' Created By: Aqsa ' Created On: 04/16/2020 (MM/DD/YYYY) '
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String GetCurrentWeekDateRange() {
		DateFormat dateFormat = new SimpleDateFormat("d MMM,yyyy");

		Calendar calendar = Calendar.getInstance();
		String SundayDate = "";
		String NextSaturday = "";
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {

			calendar.add(Calendar.DATE, -1);
			SundayDate = dateFormat.format(calendar.getTime());

		}

		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			// calculate how much to add

			int days = (Calendar.MONDAY - (calendar.get(Calendar.DAY_OF_WEEK)) + 5) % 7;
			calendar.add(Calendar.DAY_OF_YEAR, days);
			NextSaturday = dateFormat.format(calendar.getTime());

		}

		return SundayDate + ";" + NextSaturday;
	}

	public static String parseDate(String date, String inFormate, String outFormate) {
		String result = null;
		try {
			Date date1 = new SimpleDateFormat(inFormate).parse(date);
			DateFormat requiredDateFormat = new SimpleDateFormat(outFormate);
			// requiredDateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
			result = requiredDateFormat.format(date1);
			app_logs.info(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static void logoutToInnCenter(WebDriver driver, ArrayList<String> test_steps)
			throws ParseException, InterruptedException, IOException {
		Elements_Admin admin = new Elements_Admin(driver);
		WebElementsLogin login = new WebElementsLogin(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", admin.UserIcon);
		js.executeScript("arguments[0].click();", admin.UserIcon);
		app_logs.info("User Click");
		// Wait.explicit_wait_visibilityof_webelement_120(admin.LogoutBtn,
		// driver);

		Wait.WaitForElement(driver, OR_Admin.LogoutBtn);
		app_logs.info("Wait for Element");
		js.executeScript("arguments[0].scrollIntoView();", admin.LogoutBtn);
		js.executeScript("arguments[0].click();", admin.LogoutBtn);
		test_steps.add("Logged out to the innCenter");
		app_logs.info("Logged out to the innCenter");
		Wait.explicit_wait_visibilityof_webelement_120(login.clientCode, driver);

	}

	/*
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: GetFutureDateAndYear() ' Description: This Utility method
	 * will give us the date of the future date and year.
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static String getFutureMonthAndYearForMasterCard(WebDriver driver, ArrayList<String> test_steps)
			throws ParseException {
		String DateYear = null;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat date = new SimpleDateFormat("dd");
		DateFormat dateFormatYear = new SimpleDateFormat("yy");
		Date today = new Date();
		Date currentDate = dateFormat.parse(dateFormat.format(today));
		int Date = currentDate.getMonth() + 3;
		String Isdate = String.valueOf(Date);
		Date isDate = date.parse(Isdate);
		Isdate = date.format(isDate);
		app_logs.info("Future Month   : " + Isdate);
		int Year = currentDate.getYear() + 1900;
		Year = Year + 3;
		String IsYear = String.valueOf(Year);
		Date year = dateFormatYear.parse(IsYear);
		IsYear = dateFormatYear.format(year);
		app_logs.info("CurrentYear Plus Three : " + IsYear);
		DateYear = Isdate + "/" + IsYear;
		app_logs.info("ExpiryMonthANDYear : " + DateYear);
		return DateYear;
	}

	public static String getFutureMonthAndYearForMasterCard() throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 3);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
		String returnMonthAndYear = sdf.format(cal.getTime());
		return returnMonthAndYear;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: GetDate() ' Description: This Utility method will give us
	 * the date only from Comple Date like - from dd/MM/YYYY give only dd .
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String GetDate(WebDriver driver, ArrayList<String> test_steps, String date, String format,
			String ChangedFormat) throws ParseException {
		String Date = null;
		DateFormat currentFormate = new SimpleDateFormat(format);
		DateFormat changedFormat = new SimpleDateFormat(ChangedFormat);
		Date convertDatefromString = currentFormate.parse(date);
		int getDateOnly = convertDatefromString.getDate();
		String convertDateIntoString = String.valueOf(getDateOnly);
		Date isDate = changedFormat.parse(convertDateIntoString);
		Date = changedFormat.format(isDate);
		app_logs.info("CurrentDate Is : " + Date);
		return Date;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: GetDayOfDate() ' Description: This Utility method will
	 * give us the day from the date like - from dd/MM/YYYY- Give Monday,
	 * Tuesday etc.. .
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String GetDayOfDate(WebDriver driver, ArrayList<String> test_steps, String date, String format,
			String changedFormats) throws ParseException {
		String Day = null;

		DateFormat currentFormate = new SimpleDateFormat(format);
		Date convertDatefromString = currentFormate.parse(date);
		int getDayOnly = convertDatefromString.getDate();
		int getMOnth = convertDatefromString.getMonth() + 1;
		int getYear = convertDatefromString.getYear() + 1900;

		Day = java.time.format.DateTimeFormatter.ofPattern(changedFormats)
				.format(LocalDate.of(getYear, getMOnth, getDayOnly));
		app_logs.info("CurrentDay Of Date Is : " + Day);
		return Day;

	}

	public static void ScrollToTillEndOfPage(WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Wait.wait2Second();
	}

	public static boolean equalLists(List<String> one, List<String> two) {
		if (one == null && two == null) {
			return true;
		}

		if ((one == null && two != null) || one != null && two == null || one.size() != two.size()) {
			return false;
		}

		// to avoid messing the order of the lists we will use a copy
		// as noted in comments by A. R. S.
		one = new ArrayList<String>(one);
		two = new ArrayList<String>(two);

		Collections.sort(one);
		Collections.sort(two);
		return one.equals(two);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: generateRandomStringWithoutNumbers ' Description: Getting
	 * 6 digit random string ' Input parameters: ' Return value: String '
	 * Created By: Naveen Banda ' Created On: 13-04-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String generateRandomStringWithoutNumbers() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: validateString ' Description: Validating provided string
	 * is not null and not empty ' Input parameters: String ' Return value:
	 * boolean ' Created By: Naveen Banda ' Created On: 04-04-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static boolean validateString(String str) throws ParseException {
		boolean validate = false;
		try {
			if (!str.isEmpty() && str != null && str != "") {
				validate = true;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		return validate;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: validateInput ' Description: Validating input string is
	 * not null and not empty ' Input parameters: String ' Return value: boolean
	 * ' Created By: Naveen Banda ' Created On: 04-04-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static boolean validateInput(String str) throws Exception {
		boolean validate = false;
		if (!str.isEmpty() && str != null && str != "" && str != "NA" && str != "na") {
			String currentDay = Utility.getCurrentDate("dd/MM/yyyy");
			if (checkFirstDateIsGreaterOrEqualDayToSecond(str, currentDay)) {
				validate = true;					
			}
		}
		return validate;
	}
	
	
	/*public static boolean validateInput1(String str) throws Exception {
		boolean validate = false;
		if (!str.isEmpty() && str != null && str != "" && str != "NA" && str != "na") {
		validate = true;					
		
		}
		return validate;
	}*/
	


	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: validateString ' Description: Validating provided string
	 * is not null and not empty ' Input parameters: String ' Return value:
	 * boolean ' Created By: Naveen Banda ' Created On: 04-04-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String getCardNumberHidden(String cardNum) throws ParseException {
		String returnCardNum;
		returnCardNum = "XXXX" + cardNum.substring(cardNum.length() - 4);
		return returnCardNum;
	}

	public static void verifyText(String actual, String exp, String message, ArrayList<String> test_steps, Logger log) {
		assertEquals(actual, exp, "Failed: To verify " + message);
		test_steps.add("Verified " + message + " : <b>" + actual + "</b>");
		log.info("Verified " + message + " :" + actual);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: differenceBetweenDates ' Description: To get difference
	 * between two dates ' Input parameters: String, String ' Return value:
	 * String ' Created By: Naveen Banda ' Created On: 04-04-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String differenceBetweenDates(String checkIn, String checkOut) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date firstDate = sdf.parse(checkIn);
		Date secondDate = sdf.parse(checkOut);

		long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		String diffDays = (Float.toString(diff)).replace(".0", "");
		return diffDays;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: splitInputData ' Description: Splits input data based on
	 * '|' char and return it in an arraylist ' Input parameters: String '
	 * Return value: ArrayList ' Created By: Naveen Banda ' Created On:
	 * 04-04-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static ArrayList<String> splitInputData(String input) throws ParseException {
		ArrayList<String> splitData = new ArrayList<>();
		String size[] = input.split("\\|");
		for (String string : size) {
			splitData.add(string);
		}
		return splitData;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: splitInputData ' Description: Splits input data based on
	 * '|' char and return it in an arraylist ' Input parameters: String '
	 * Return value: ArrayList ' Created By: Naveen Banda ' Created On:
	 * 04-04-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String addInputDataToStringWithSeperator(ArrayList<String> input) throws ParseException {
		int size = input.size();
		String str = "";
		for (int i = 0; i < size; i++) {
			str = str + "" + input.get(i);
			if (i < size - 1) {
				str = str + "|";
			}
		}
		return str;

	}

	public static String GenerateRandomNumber(int maxNumber) {
		Random rand = new Random();

		// Generate random integers in range 0 to 999
		int rand_num = rand.nextInt(maxNumber);

		return String.valueOf(rand_num);
	}

	public static String fourDigitgenerateRandomString() {

		// chose a Character random from this String
		String AlphaNumericString = "0123456789";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < 4; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());
			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();

	}

	public static boolean isElementDisplayed(WebDriver driver, By locatorKey) {
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		try {
			boolean value = driver.findElement(locatorKey).isDisplayed();
			return value;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}

	}

	public static String getNextDate(int Day, String formateStr) {
		SimpleDateFormat format = new SimpleDateFormat(formateStr);
		format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		final Date date = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, Day);
		return format.format(calendar.getTime());
	}

	public static int getStayDays(String checkinDay, String checkoutDay) {
		int checkin = Integer.parseInt(checkinDay.split("/")[0]);
		int checkout = Integer.parseInt(checkoutDay.split("/")[0]);

		int StayDays = checkout - checkin;

		return StayDays;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getLineItemIconSource> ' Description: <This method will
	 * get the line item source/icon from the excel sheet> ' Input parameters:
	 * <excel_reader, String> ' Return value: <void> ' Created By: <Adhnan
	 * Ghaffar> ' Created On: <06/02/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static void getLineItemIconSource(excel_reader excel, String sheetName) {
		for (int rowNum = 2; rowNum <= excel.getRowCount(sheetName); rowNum++) {
			LineItemIconSource.add(excel.getCellData(sheetName, "Authorized", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "AuthIconSource", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "Pending", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "PendingIconSource", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "Posted", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "PostedIconSource", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "Refund", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "RefundIconSource", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "Cancel", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "CanclIconSource", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "PaymentFailed", rowNum));
			LineItemIconSource.add(excel.getCellData(sheetName, "FailedIconSource", rowNum));
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: checkPDF_Array ' Description: Read pdf file and save in
	 * array ' Input parameters: String ' Return value: String Array ' Created
	 * By: Adnan Ghaffar ' Created On: 11-05-2020
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static String[] checkPDF_Array(String fileName) throws IOException {
		// File getFile =
		// Utility.getLatestFilefromDir(System.getProperty("user.dir") +
		// File.separator + "externalFiles"
		// + File.separator + "downloadFiles" + File.separator);

		File getFile = new File(System.getProperty("user.dir") + File.separator + "externalFiles" + File.separator
				+ "downloadFiles" + File.separator + fileName);
		System.out.println(getFile.getAbsolutePath());
		System.out.println(getFile.getName());
		PDDocument document = PDDocument.load(getFile);
		document.getClass();
		String pdfFileInText = null;
		String[] lines = null;
		if (!document.isEncrypted()) {
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			PDFTextStripper tStripper = new PDFTextStripper();
			pdfFileInText = tStripper.getText(document);
			lines = pdfFileInText.split("\\r?\\n");
			System.out.println("lines : " + lines.length);
			System.out.println("report end");
			document.close();
			if (getFile.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}
		}
		return lines;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: getTimeStampTest Description: get time and date from client
	 * machine Input parameters: Return value: String Created By: Adnan Ghaffar
	 * Created On: 09-06-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */

	public static String getTimeStampTest() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("E MMM  d,yyyy hh:mm a");
		String currentDate = date.format(cal.getTime());
		return currentDate;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <removeDollarBracketsAndSpaces> ' Description: <This
	 * method will remove dollar, brackets and spaces characters from the
	 * string> ' Input parameters: <String> ' Return value: <String> ' Created
	 * By: <Adhnan Ghaffar> ' Created On: <06/02/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String removeDollarBracketsAndSpaces(String Amount) {

		if (Amount.contains("$")) {
			Amount = Amount.replace("$", "");
		}
		if (Amount.contains("(")) {
			Amount = Amount.replace("(", "");
		}
		if (Amount.contains(")")) {
			Amount = Amount.replace(")", "");
		}
		System.out.println("After Converting the amount is: " + Amount);
		return Amount.trim();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <checkPDFArray> ' Description: <This method will get file
	 * data and delete the file> ' Input parameters: <String> ' Return value:
	 * <String[]> ' Created By: <Adhnan Ghaffar> ' Created On: <06/02/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String[] checkPDFArray(String fileName) throws IOException {

		File getFile = new File(System.getProperty("user.dir") + File.separator + "externalFiles" + File.separator
				+ "downloadFiles" + File.separator + fileName);
		System.out.println(getFile.getAbsolutePath());
		System.out.println(getFile.getName());
		PDDocument document = PDDocument.load(getFile);
		document.getClass();
		String pdfFileInText = null;
		String[] lines = null;
		if (!document.isEncrypted()) {
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			PDFTextStripper tStripper = new PDFTextStripper();
			pdfFileInText = tStripper.getText(document);
			lines = pdfFileInText.split("\\r?\\n");
			System.out.println("lines : " + lines.length);
			System.out.println("report end");
			document.close();
			if (getFile.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}
		}
		return lines;
	}

	public static String generateRandomNumber() {
		Random rand = new Random();

		// Generate random integers in range 0 to 999
		int rand_num = rand.nextInt(10000);

		return String.valueOf(rand_num);
	}
	
	public static String generateRandomNumberWithGivenNumberOfDigits(int number) {
		String randomNumber = "123456789";
		StringBuilder sb = new StringBuilder(number);
		for (int i = 0; i < number; i++) {
			int index = (int) (randomNumber.length() * Math.random());
			sb.append(randomNumber.charAt(index));
		}
		return sb.toString();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: clickFolioDetails ' Description: <This method will return
	 * the current Date in given timeZone and format.> ' Input
	 * parameters:(String, String) ' Return value: String ' Created By: <Adhnan
	 * Ghaffar> ' Created On: <06/12/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String getCurrentDate(String formate, String timeZone) {
		DateFormat requiredDateFormat = new SimpleDateFormat(formate);
		requiredDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		String date = requiredDateFormat.format(new Date());
		app_logs.info(date);
		return date;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: getNextDate ' Description: <This method will return the
	 * given timeZone Date ingiven format after adding "Day" in it. > ' Input
	 * parameters:(int, String, String) ' Return value: String ' Created By:
	 * <Adhnan Ghaffar> ' Created On: <06/12/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String getNextDate(int Day, String formateStr, String timeZone) {
		SimpleDateFormat format = new SimpleDateFormat(formateStr);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		final Date date = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, Day);
		return format.format(calendar.getTime());
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: closeTabsExcept ' Description: <This method will close all
	 * other tab except first 'count' number of tabs.> ' Input
	 * parameters:(WebDriver, int) ' Return value: void ' Created By: <Adhnan
	 * Ghaffar> ' Created On: <06/12/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static void closeTabsExcept(WebDriver driver, int count) {
		ArrayList<String> allTabs = new ArrayList<>(driver.getWindowHandles());
		System.out.println("ALl Tabs:" + allTabs.size());
		while (allTabs.size() != count) {
			Utility.switchTab(driver, allTabs.get(count));
			driver.close();
			System.out.println("Close tab");
			Utility.switchTab(driver, allTabs.get(count - 1));
			allTabs = new ArrayList<>(driver.getWindowHandles());
			System.out.println("ALl Tabs:" + allTabs.size());
		}

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: switchTab ' Description: <This method will switch Tab> '
	 * Input parameters:(WebDriver, int) ' Return value: void ' Created By:
	 * <Adhnan Ghaffar> ' Created On: <06/12/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static void switchTab(WebDriver driver, int index) throws InterruptedException {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(index));

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: switchTab ' Description: <This method will switch Tab> '
	 * Input parameters:(WebDriver, String) ' Return value: void ' Created By:
	 * <Adhnan Ghaffar> ' Created On: <06/12/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static void switchTab(WebDriver driver, String tab) {
		if (!(tab.isEmpty()) && tab != null) {
			driver.switchTo().window(tab);
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: addDate ' Description: <This method will return the given
	 * Date in given format after adding "Day" in it. > ' Input parameters:(int,
	 * String, String, String) ' Return value: String ' Created By: <Adhnan
	 * Ghaffar> ' Created On: <07//2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String addDate(int Day, String inputFormat, String inputdate, String outputFormat, String timeZone)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(inputFormat);
		SimpleDateFormat outformat = new SimpleDateFormat(outputFormat);
		//format.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date();
		date = format.parse(inputdate);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, Day);
		return outformat.format(calendar.getTime());
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: catchError and catchException ' Description: <This method
	 * Handeling Catch Error and Exception> ' Input parameters:(WebDriver,
	 * String and Error and Exception) ' Return value: void ' Created By:
	 * <Gangotri Sikheria> ' Created On: <06/25/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static void catchError(WebDriver driver, Error e, String desc, String category, String module,
			String testName, String testDescription, String testCatagory, ArrayList<String> testSteps) {
		if (reTry.get(testName) == count) {
			RetryFailedTestCases.count = reset_count;
			AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	public static void catchException(WebDriver driver, Exception e, String desc, String category, String module,
			String testName, String testDescription, String testCatagory, ArrayList<String> testSteps) {
		if (reTry.get(testName) == count) {
			RetryFailedTestCases.count = reset_count;
			AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getCustomDate> ' Description: < Get Custom Date by
	 * passing no of days that you want to add or reduce, for addition : pass
	 * integer +1 or 1 and pass -1 to reduce day from passed date, Note: this
	 * method will not consider desired date in total days count e.g :
	 * desiredDate = 20 Feb, 2020 and daysToAddOrReduce = 2 so it will return 22
	 * Feb 2020 > ' Input parameters: < String desiredDate, String inFormate,
	 * String outFormate,int daysToAddOrReduce > ' Return value: <String> '
	 * Created By: <Muhammad Haider> ' Created On: <MM/DD/YYYY> <07/01/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String getCustomDate(String desiredDate, String inFormate, String outFormate, int daysToAddOrReduce) {

		String result = null;
		app_logs.info("Recevied Date : " + desiredDate);
		app_logs.info("Days To Add Or Reduce : " + daysToAddOrReduce);
		try {

			SimpleDateFormat format = new SimpleDateFormat(inFormate);
			// format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
			final Date date1 = new SimpleDateFormat(inFormate).parse(desiredDate);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			calendar.add(Calendar.DAY_OF_YEAR, daysToAddOrReduce);
			app_logs.info(format.format(calendar.getTime()));
			DateFormat requiredDateFormat = new SimpleDateFormat(outFormate);
			// requiredDateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));

			result = requiredDateFormat.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		app_logs.info("Generated Date : " + result);
		return result;
		// Just uncomment below lines and test it once.
		// }
		// public static void main(String[] args) {
		// System.out.println(getCustomDate("20 Feb, 2020", "dd MMM, yyyy", "dd
		// MMM, yyyy", 2));
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ######################## <<<<<<< HEAD
	 *
	 * ' Method Name: <clickThroughJavaScript> ' ' Description: < Click Element
	 * Through JS > ' Input parameters: < WebDriver driver,WebElement ele > ' '
	 * Return value: <void> ' Created By: <Muhammad Haider> ' Created On:
	 * <MM/DD/YYYY> <06/30/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static void clickThroughJavaScript(WebDriver driver, WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", ele);
	}

	/*
	 * <<<<<<< HEAD
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * =======
	 * #########################################################################
	 * #########################################################################
	 * ######################## ======= >>>>>>> develop >>>>>>> develop
	 * 
	 * ' Method Name: <convertTokenToArrayList> ' Description: <This method will
	 * convert Token To ArrayList where deleim you have pass based on you script
	 * > ' Input parameters: < String tokens, String delim > ' Return value:
	 * <ArrayList<String>> ' Created By: <Muhammad Haider> ' Created On:
	 * <MM/DD/YYYY> <07/07/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static ArrayList<String> convertTokenToArrayList(String tokens, String delim) {
		ArrayList<String> finalList = new ArrayList<String>();

		StringTokenizer token = new StringTokenizer(tokens, delim);
		while (token.hasMoreTokens()) {
			finalList.add(token.nextToken());
		}
		// app_logs.info(finalList.size());
		return finalList;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <convertTokenToArrayList> ' Description: <This method will
	 * convert ArrayList To Token where deleim you have pass based on you script
	 * > ' Input parameters: < ArrayList<String> list, String delim > ' Return
	 * value: <String> ' Created By: <Muhammad Haider> ' Created On:
	 * <MM/DD/YYYY> <07/07/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String convertArrayListToToken(ArrayList<String> list, String delim) {
		String finalString = "";

		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				finalString += delim;
			}
			finalString += list.get(i);
		}
		app_logs.info(finalString);
		return finalString;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clearValue> ' Description: <This method will clear feild
	 * value when element.clear() is not working> ' Input parameters: <
	 * (WebDriver driver, WebElement ele> ' Return value: <void> ' Created By:
	 * <Muhammad Haider> ' Created On: <MM/DD/YYYY> <07/07/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static void clearValue(WebDriver driver, WebElement ele) {
		if (!ele.getAttribute("value").isEmpty() || !ele.getAttribute("value").equals("")
				|| ele.getAttribute("value").length() != 0) {
			int size = ele.getAttribute("value").length();
			ele.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			// for (int i = 1; i <= size; i++) {
			ele.sendKeys(Keys.BACK_SPACE);
			// }
		}
	}

//	 public static void main(String[] args) throws ParseException {
//		 DecimalFormat df = new DecimalFormat("0.00");
//			
//			
//			System.out.println(df.format(Float.parseFloat("73.366")));
////	 System.out.println(differenceBetweenDates("23/09/2020", "02/10/2020"));
//	 }

	public static void hoverOnElement(WebDriver driver, WebElement ele) {
		Actions actions = new Actions(driver);
		actions.moveToElement(ele).perform();
	}

	public static void clickThroughAction(WebDriver driver, WebElement ele) {
		Actions actions = new Actions(driver);
		actions.moveToElement(ele).click().perform();
	}

	public static void mouseOverElement(WebDriver driver, WebElement webElement) {
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(((Locatable) webElement).getCoordinates());
	}

	public static String increaseDateAsPerGivenDate(String format, String date, int no) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(formatter.parse(date));
		calendar.add(Calendar.DATE, no); // number of days to add
		String getDate = formatter.format(calendar.getTime());
		return getDate;
	}

	public static String convertDateFormattoString(String pattern, Date date) throws ParseException {
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat(pattern);
		dateString = sdfr.format(date);
		return dateString;

	}

	public static Date convertStringtoDateFormat(String format, String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date getDate = formatter.parse(date);
		return getDate;

	}

	public static String getDateOnlyFromCompleteDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayNumInt = calendar.get(Calendar.DATE);
		String dayOnly = String.valueOf(dayNumInt);
		app_logs.info(dayOnly);
		return dayOnly;
	}

	public static List<Date> getDateRangeBetweenfromAndToDate(Date fromDate, Date toDate) {
		List<Date> dates = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.DATE, 0);
		dates.add(cal.getTime());
		while (cal.getTime().before(toDate)) {
			cal.add(Calendar.DATE, 1);
			dates.add(cal.getTime());
		}
		return dates;
	}

	public static void logoutToRateV2(WebDriver driver, ArrayList<String> test_steps)
			throws ParseException, InterruptedException, IOException {
		Elements_Admin admin = new Elements_Admin(driver);
		WebElementsLogin login = new WebElementsLogin(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", admin.rateV2UserIcon);
		js.executeScript("arguments[0].click();", admin.rateV2UserIcon);
		app_logs.info("User Click");
		Wait.WaitForElement(driver, OR_Admin.rateV2LogOut);
		app_logs.info("Wait for Element");
		js.executeScript("arguments[0].scrollIntoView();", admin.rateV2LogOut);
		js.executeScript("arguments[0].click();", admin.rateV2LogOut);
		test_steps.add("Logged out to the RateV2");
		app_logs.info("Logged out to the RateV2");
		Wait.explicit_wait_visibilityof_webelement_120(login.clientCode, driver);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <combine> ' Description: <This method will conbine two
	 * data provider and get data from two different sheet> ' Input parameters:
	 * < (Object[][] a1 and Object[][] a2> ' Return value: <void> ' Created By:
	 * <Gangotri Sikheria> ' Created On: <MM/DD/YYYY> <21/07/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static Object[][] combine(Object[][] a1, Object[][] a2) {
		List<Object[]> objectCodesList = new LinkedList<Object[]>();
		for (Object[] o : a1) {
			for (Object[] o2 : a2) {
				objectCodesList.add(concatAll(o, o2));
			}
		}
		return objectCodesList.toArray(new Object[0][0]);
	}

	@SafeVarargs
	private static <T> T[] concatAll(T[] first, T[]... rest) {
		// calculate the total length of the final object array after the concat
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		// copy the first array to result array and then copy each array
		// completely to result
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}

		return result;
	}

	public static ArrayList<String> getDiffrenceOfLists(ArrayList<String> mainList, ArrayList<String> secondList) {
		ArrayList<String> tempAll = mainList;
		ArrayList<String> tempSelected = secondList;

		tempAll.removeAll(tempSelected);
		System.out.println(tempAll);
		return tempAll;
	}

	public static String convertBooleanToString(boolean acctual) {

		if (acctual) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public static Boolean convertStringToBoolean(String acctual) {

		if (acctual.equalsIgnoreCase("Yes")) {
			return true;
		} else if (acctual.equalsIgnoreCase("No")) {
			return false;
		} else {
			throw new EmptyStackException();
		}
	}

	public static String getDateFromCurrentDate(int days) {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = new Date();
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		// manipulate date
		c.add(Calendar.DATE, days); // same with c.add(Calendar.DAY_OF_MONTH,
									// 1);
		// convert calendar to date
		Date currentDatePlusOne = c.getTime();
		System.out.println(dateFormat.format(currentDatePlusOne));

		return dateFormat.format(currentDatePlusOne);

	}

	private ArrayList<ColorName> initColorList() {
		ArrayList<ColorName> colorList = new ArrayList<ColorName>();
		colorList.add(new ColorName("AliceBlue", 0xF0, 0xF8, 0xFF));
		colorList.add(new ColorName("AntiqueWhite", 0xFA, 0xEB, 0xD7));
		colorList.add(new ColorName("Aqua", 0x00, 0xFF, 0xFF));
		colorList.add(new ColorName("Aquamarine", 0x7F, 0xFF, 0xD4));
		colorList.add(new ColorName("Azure", 0xF0, 0xFF, 0xFF));
		colorList.add(new ColorName("Beige", 0xF5, 0xF5, 0xDC));
		colorList.add(new ColorName("Bisque", 0xFF, 0xE4, 0xC4));
		colorList.add(new ColorName("Black", 0x00, 0x00, 0x00));
		colorList.add(new ColorName("BlanchedAlmond", 0xFF, 0xEB, 0xCD));
		colorList.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
		colorList.add(new ColorName("BlueViolet", 0x8A, 0x2B, 0xE2));
		colorList.add(new ColorName("Brown", 0xA5, 0x2A, 0x2A));
		colorList.add(new ColorName("BurlyWood", 0xDE, 0xB8, 0x87));
		colorList.add(new ColorName("CadetBlue", 0x5F, 0x9E, 0xA0));
		colorList.add(new ColorName("Chartreuse", 0x7F, 0xFF, 0x00));
		colorList.add(new ColorName("Chocolate", 0xD2, 0x69, 0x1E));
		colorList.add(new ColorName("Coral", 0xFF, 0x7F, 0x50));
		colorList.add(new ColorName("CornflowerBlue", 0x64, 0x95, 0xED));
		colorList.add(new ColorName("Cornsilk", 0xFF, 0xF8, 0xDC));
		colorList.add(new ColorName("Crimson", 0xDC, 0x14, 0x3C));
		colorList.add(new ColorName("Cyan", 0x00, 0xFF, 0xFF));
		colorList.add(new ColorName("DarkBlue", 0x00, 0x00, 0x8B));
		colorList.add(new ColorName("DarkCyan", 0x00, 0x8B, 0x8B));
		colorList.add(new ColorName("DarkGoldenRod", 0xB8, 0x86, 0x0B));
		colorList.add(new ColorName("DarkGray", 0xA9, 0xA9, 0xA9));
		colorList.add(new ColorName("DarkGreen", 0x00, 0x64, 0x00));
		colorList.add(new ColorName("DarkKhaki", 0xBD, 0xB7, 0x6B));
		colorList.add(new ColorName("DarkMagenta", 0x8B, 0x00, 0x8B));
		colorList.add(new ColorName("DarkOliveGreen", 0x55, 0x6B, 0x2F));
		colorList.add(new ColorName("DarkOrange", 0xFF, 0x8C, 0x00));
		colorList.add(new ColorName("DarkOrchid", 0x99, 0x32, 0xCC));
		colorList.add(new ColorName("DarkRed", 0x8B, 0x00, 0x00));
		colorList.add(new ColorName("DarkSalmon", 0xE9, 0x96, 0x7A));
		colorList.add(new ColorName("DarkSeaGreen", 0x8F, 0xBC, 0x8F));
		colorList.add(new ColorName("DarkSlateBlue", 0x48, 0x3D, 0x8B));
		colorList.add(new ColorName("DarkSlateGray", 0x2F, 0x4F, 0x4F));
		colorList.add(new ColorName("DarkTurquoise", 0x00, 0xCE, 0xD1));
		colorList.add(new ColorName("DarkViolet", 0x94, 0x00, 0xD3));
		colorList.add(new ColorName("DeepPink", 0xFF, 0x14, 0x93));
		colorList.add(new ColorName("DeepSkyBlue", 0x00, 0xBF, 0xFF));
		colorList.add(new ColorName("DimGray", 0x69, 0x69, 0x69));
		colorList.add(new ColorName("DodgerBlue", 0x1E, 0x90, 0xFF));
		colorList.add(new ColorName("FireBrick", 0xB2, 0x22, 0x22));
		colorList.add(new ColorName("FloralWhite", 0xFF, 0xFA, 0xF0));
		colorList.add(new ColorName("ForestGreen", 0x22, 0x8B, 0x22));
		colorList.add(new ColorName("Fuchsia", 0xFF, 0x00, 0xFF));
		colorList.add(new ColorName("Gainsboro", 0xDC, 0xDC, 0xDC));
		colorList.add(new ColorName("GhostWhite", 0xF8, 0xF8, 0xFF));
		colorList.add(new ColorName("Gold", 0xFF, 0xD7, 0x00));
		colorList.add(new ColorName("GoldenRod", 0xDA, 0xA5, 0x20));
		colorList.add(new ColorName("Gray", 0x80, 0x80, 0x80));
		colorList.add(new ColorName("Green", 0x00, 0x80, 0x00));
		colorList.add(new ColorName("GreenYellow", 0xAD, 0xFF, 0x2F));
		colorList.add(new ColorName("HoneyDew", 0xF0, 0xFF, 0xF0));
		colorList.add(new ColorName("HotPink", 0xFF, 0x69, 0xB4));
		colorList.add(new ColorName("IndianRed", 0xCD, 0x5C, 0x5C));
		colorList.add(new ColorName("Indigo", 0x4B, 0x00, 0x82));
		colorList.add(new ColorName("Ivory", 0xFF, 0xFF, 0xF0));
		colorList.add(new ColorName("Khaki", 0xF0, 0xE6, 0x8C));
		colorList.add(new ColorName("Lavender", 0xE6, 0xE6, 0xFA));
		colorList.add(new ColorName("LavenderBlush", 0xFF, 0xF0, 0xF5));
		colorList.add(new ColorName("LawnGreen", 0x7C, 0xFC, 0x00));
		colorList.add(new ColorName("LemonChiffon", 0xFF, 0xFA, 0xCD));
		colorList.add(new ColorName("LightBlue", 0xAD, 0xD8, 0xE6));
		colorList.add(new ColorName("LightCoral", 0xF0, 0x80, 0x80));
		colorList.add(new ColorName("LightCyan", 0xE0, 0xFF, 0xFF));
		colorList.add(new ColorName("LightGoldenRodYellow", 0xFA, 0xFA, 0xD2));
		colorList.add(new ColorName("LightGray", 0xD3, 0xD3, 0xD3));
		colorList.add(new ColorName("LightGreen", 0x90, 0xEE, 0x90));
		colorList.add(new ColorName("LightPink", 0xFF, 0xB6, 0xC1));
		colorList.add(new ColorName("LightSalmon", 0xFF, 0xA0, 0x7A));
		colorList.add(new ColorName("LightSeaGreen", 0x20, 0xB2, 0xAA));
		colorList.add(new ColorName("LightSkyBlue", 0x87, 0xCE, 0xFA));
		colorList.add(new ColorName("LightSlateGray", 0x77, 0x88, 0x99));
		colorList.add(new ColorName("LightSteelBlue", 0xB0, 0xC4, 0xDE));
		colorList.add(new ColorName("LightYellow", 0xFF, 0xFF, 0xE0));
		colorList.add(new ColorName("Lime", 0x00, 0xFF, 0x00));
		colorList.add(new ColorName("LimeGreen", 0x32, 0xCD, 0x32));
		colorList.add(new ColorName("Linen", 0xFA, 0xF0, 0xE6));
		colorList.add(new ColorName("Magenta", 0xFF, 0x00, 0xFF));
		colorList.add(new ColorName("Maroon", 0x80, 0x00, 0x00));
		colorList.add(new ColorName("MediumAquaMarine", 0x66, 0xCD, 0xAA));
		colorList.add(new ColorName("MediumBlue", 0x00, 0x00, 0xCD));
		colorList.add(new ColorName("MediumOrchid", 0xBA, 0x55, 0xD3));
		colorList.add(new ColorName("MediumPurple", 0x93, 0x70, 0xDB));
		colorList.add(new ColorName("MediumSeaGreen", 0x3C, 0xB3, 0x71));
		colorList.add(new ColorName("MediumSlateBlue", 0x7B, 0x68, 0xEE));
		colorList.add(new ColorName("MediumSpringGreen", 0x00, 0xFA, 0x9A));
		colorList.add(new ColorName("MediumTurquoise", 0x48, 0xD1, 0xCC));
		colorList.add(new ColorName("MediumVioletRed", 0xC7, 0x15, 0x85));
		colorList.add(new ColorName("MidnightBlue", 0x19, 0x19, 0x70));
		colorList.add(new ColorName("MintCream", 0xF5, 0xFF, 0xFA));
		colorList.add(new ColorName("MistyRose", 0xFF, 0xE4, 0xE1));
		colorList.add(new ColorName("Moccasin", 0xFF, 0xE4, 0xB5));
		colorList.add(new ColorName("NavajoWhite", 0xFF, 0xDE, 0xAD));
		colorList.add(new ColorName("Navy", 0x00, 0x00, 0x80));
		colorList.add(new ColorName("OldLace", 0xFD, 0xF5, 0xE6));
		colorList.add(new ColorName("Olive", 0x80, 0x80, 0x00));
		colorList.add(new ColorName("OliveDrab", 0x6B, 0x8E, 0x23));
		colorList.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
		colorList.add(new ColorName("OrangeRed", 0xFF, 0x45, 0x00));
		colorList.add(new ColorName("Orchid", 0xDA, 0x70, 0xD6));
		colorList.add(new ColorName("PaleGoldenRod", 0xEE, 0xE8, 0xAA));
		colorList.add(new ColorName("PaleGreen", 0x98, 0xFB, 0x98));
		colorList.add(new ColorName("PaleTurquoise", 0xAF, 0xEE, 0xEE));
		colorList.add(new ColorName("PaleVioletRed", 0xDB, 0x70, 0x93));
		colorList.add(new ColorName("PapayaWhip", 0xFF, 0xEF, 0xD5));
		colorList.add(new ColorName("PeachPuff", 0xFF, 0xDA, 0xB9));
		colorList.add(new ColorName("Peru", 0xCD, 0x85, 0x3F));
		colorList.add(new ColorName("Pink", 0xFF, 0xC0, 0xCB));
		colorList.add(new ColorName("Plum", 0xDD, 0xA0, 0xDD));
		colorList.add(new ColorName("PowderBlue", 0xB0, 0xE0, 0xE6));
		colorList.add(new ColorName("Purple", 0x80, 0x00, 0x80));
		colorList.add(new ColorName("Red", 0xFF, 0x00, 0x00));
		colorList.add(new ColorName("RosyBrown", 0xBC, 0x8F, 0x8F));
		colorList.add(new ColorName("RoyalBlue", 0x41, 0x69, 0xE1));
		colorList.add(new ColorName("SaddleBrown", 0x8B, 0x45, 0x13));
		colorList.add(new ColorName("Salmon", 0xFA, 0x80, 0x72));
		colorList.add(new ColorName("SandyBrown", 0xF4, 0xA4, 0x60));
		colorList.add(new ColorName("SeaGreen", 0x2E, 0x8B, 0x57));
		colorList.add(new ColorName("SeaShell", 0xFF, 0xF5, 0xEE));
		colorList.add(new ColorName("Sienna", 0xA0, 0x52, 0x2D));
		colorList.add(new ColorName("Silver", 0xC0, 0xC0, 0xC0));
		colorList.add(new ColorName("SkyBlue", 0x87, 0xCE, 0xEB));
		colorList.add(new ColorName("SlateBlue", 0x6A, 0x5A, 0xCD));
		colorList.add(new ColorName("SlateGray", 0x70, 0x80, 0x90));
		colorList.add(new ColorName("Snow", 0xFF, 0xFA, 0xFA));
		colorList.add(new ColorName("SpringGreen", 0x00, 0xFF, 0x7F));
		colorList.add(new ColorName("SteelBlue", 0x46, 0x82, 0xB4));
		colorList.add(new ColorName("Tan", 0xD2, 0xB4, 0x8C));
		colorList.add(new ColorName("Teal", 0x00, 0x80, 0x80));
		colorList.add(new ColorName("Thistle", 0xD8, 0xBF, 0xD8));
		colorList.add(new ColorName("Tomato", 0xFF, 0x63, 0x47));
		colorList.add(new ColorName("Turquoise", 0x40, 0xE0, 0xD0));
		colorList.add(new ColorName("Violet", 0xEE, 0x82, 0xEE));
		colorList.add(new ColorName("Wheat", 0xF5, 0xDE, 0xB3));
		colorList.add(new ColorName("White", 0xFF, 0xFF, 0xFF));
		colorList.add(new ColorName("WhiteSmoke", 0xF5, 0xF5, 0xF5));
		colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
		colorList.add(new ColorName("YellowGreen", 0x9A, 0xCD, 0x32));
		return colorList;
	}

	public String getColorNameFromRgb(int r, int g, int b) {
		ArrayList<ColorName> colorList = initColorList();
		ColorName closestMatch = null;
		int minMSE = Integer.MAX_VALUE;
		int mse;
		for (ColorName c : colorList) {
			mse = c.computeMSE(r, g, b);
			if (mse < minMSE) {
				minMSE = mse;
				closestMatch = c;
			}
		}

		if (closestMatch != null) {
			return closestMatch.getName();
		} else {
			return "No matched color name.";
		}
	}

	public String getColorNameFromHex(int hexColor) {
		int r = (hexColor & 0xFF0000) >> 16;
		int g = (hexColor & 0xFF00) >> 8;
		int b = (hexColor & 0xFF);
		return getColorNameFromRgb(r, g, b);
	}

	public int colorToHex(Color c) {
		return Integer.decode("0x" + Integer.toHexString(c.getRGB()).substring(2));
	}

	public String getColorNameFromColor(Color color) {
		return getColorNameFromRgb(color.getRed(), color.getGreen(), color.getBlue());
	}

	public class ColorName {
		public int r, g, b;
		public String name;

		public ColorName(String name, int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
			this.name = name;
		}

		public int computeMSE(int pixR, int pixG, int pixB) {
			return (int) (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b) * (pixB - b)) / 3);
		}

		public int getR() {
			return r;
		}

		public int getG() {
			return g;
		}

		public int getB() {
			return b;
		}

		public String getName() {
			return name;
		}
	}

	public static int getNumberofDays(String startDate, String endDate) throws Exception {
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		Date dateObj1 = format1.parse(startDate);
		Date dateObj2 = format1.parse(endDate);
		// getTime() returns the number of milliseconds since January 1, 1970,
		// 00:00:00 GMT represented by this Date object
		long diff = dateObj2.getTime() - dateObj1.getTime();
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		System.out.println("difference between days: " + diffDays);
		return diffDays;

    }

	public static ArrayList<String> getAllDatesBetweenCheckInOutDates(String startDate, String endDate) throws ParseException {
		LocalDate start = LocalDate.parse(parseDate(startDate, "dd/MM/yyyy", "yyyy-MM-dd"));
		String endDate2 = addDays(endDate, -1);
		LocalDate end = LocalDate.parse(parseDate(endDate2, "dd/MM/yyyy", "yyyy-MM-dd"));
		ArrayList<String> allDays = new ArrayList<>();
		while (!start.isAfter(end)) {
		    String date = start.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		    allDays.add(date);
		    start = start.plusDays(1);
		}	
		return allDays;
	}

	public static String addDays(String date, int days) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(date));
		cal.add(Calendar.DAY_OF_MONTH, days);
		String returnDate = sdf.format(cal.getTime());
		return returnDate;
	}
	
	public static boolean checkFirstDateIsGreaterOrEqualDayToSecond(String date1, String date2) throws Exception {
		int count = date1.split("\\|").length;
		ArrayList<String> dates1 = splitInputData(date1);
		ArrayList<String> dates2 = splitInputData(date2);
		if (dates2.size()!=dates1.size()) {
			dates2.clear();
			for (int i = 0; i < dates1.size(); i++) {
				dates2.add(date2.split("\\|")[0]);
			}
		}
		boolean returnDate = false;
		for (int i = 0; i < count; i++) {
			Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(dates1.get(i));
			Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse(dates2.get(i));
			if (d1.after(d2) || d1.equals(d2)) {
				returnDate = true;					
			}			
		}
		return returnDate;
	}

	public static boolean checkFirstDateIsGreaterDayToSecond(String date1, String date2) throws Exception {
		Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
		Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse(date2);
		boolean returnDate = false;
		if (d1.after(d2)) {
			returnDate = true;					
		}
		return returnDate;
	}
	
	public static String getPercentage(String percentage,String amount) {
		return (Float.parseFloat(amount)*(Float.parseFloat(percentage.replace("%", "").trim())/100))+"";
	}

	public static boolean isGivenDatePassed(String checkin, String timeZone) {
		boolean isPast = false;
		String currentDay = Utility.getNextDate(0, "dd/M/yyyy", timeZone);
		String today = parseDate(currentDay, "dd/MM/yyyy","yyyy/MM/dd");
		
		String givenDay = parseDate(checkin, "dd/MM/yyyy","yyyy/MM/dd");
		
		
		Date date1  = new Date(givenDay);

		Date date2 = new Date(today);
		if(date1.before(date2)) {
			isPast = true;
			 System.out.println("Date already passed");
			}else {
			System.out.println("Date already not passed");		        	
				}
		return isPast;
		
	}

	public static boolean isElementPresent(WebDriver driver, By locatorKey, int index) {
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		try {
			driver.findElements(locatorKey).get(index);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}

	}

	
	public static String getDatePast_FutureDate(String date, int Days) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", date);
		//Date date = new Date();
		//String DF = dateFormat.format(date);
		String DF = dateFormat.format(fromDate);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));

		c.add(Calendar.DATE, Days);
		String getDate = dateFormat.format(c.getTime());
		return getDate;
	}

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
	
	 public static int getTotalOccurancesOfSubstring(String target, String subStringToFind) 
	    {
		 int index = target.indexOf(subStringToFind);
		 int count = 0;
		   
		 while(index >= 0) {
		     index = target.indexOf(subStringToFind, index+1);
		     count++;
		 }
		 Pattern pattern = Pattern.compile("(" + subStringToFind + ")(.*?)", Pattern.UNICODE_CASE);
		 Matcher matcher = pattern.matcher(target);

		   /* int pos = 0;
		    while (matcher.find())
		    {
		        count++;
		        System.out.println("found: " + count + " : "
		                + matcher.start() + " - " + matcher.end());
		    }*/
		 System.out.println(count);
	        return count;
	 }
	 
	 /*
		 * #########################################################################
		 * #########################################################################
		 * ########################
		 *
		 * ' Method Name: addDate ' Description: <This method will return the given
		 * Date in given format after adding "months" in it. > ' Input parameters:(int,
		 * String, String, String) ' Return value: String ' Created By: <Adhnan
		 * Ghaffar> ' Created On: <08/14/2020>
		 * #########################################################################
		 * #########################################################################
		 * ########################
		 */
		public static String addMonth(int month, String inputFormat, String inputdate, String outputFormat, String timeZone)
				throws ParseException {
			SimpleDateFormat format = new SimpleDateFormat(inputFormat);
			SimpleDateFormat outformat = new SimpleDateFormat(outputFormat);
			format.setTimeZone(TimeZone.getTimeZone(timeZone));
			Date date = new Date();
			date = format.parse(inputdate);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, month);
			return outformat.format(calendar.getTime());
		}


		 /*
			 * #########################################################################
			 * #########################################################################
			 * ########################
			 *
			 * ' Method Name: compareDates ' Description: <This method will return difference of given dates. > 
			 * 'Input parameters:(String, String, String) 
			 * ' Return value: String 
			 * ' Created By: <Adhnan Ghaffar> 
			 * ' Created On: <08/17/2020>
			 * #########################################################################
			 * #########################################################################
			 * ########################
			 */
		public static int compareDates(String date1, String date2, String dateFormat) throws ParseException {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			Date parsedDate1 = format.parse(date1);
			Date parsedDate2 = format.parse(date2);
			app_logs.info("Parsed date 1 : " + parsedDate1);
			app_logs.info("Parsed date 2 : " + parsedDate2);
			app_logs.info(" parsedDate1.compareTo(parsedDate2) : "+ parsedDate1.compareTo(parsedDate2));
			return  parsedDate1.compareTo(parsedDate2);
		}





	public static String convertDecimalFormat(String amount)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		amount=df.format(Float.parseFloat(amount));
		return amount;
		
	}
	
	
	public static boolean isEmptyStringArray(String [] array){
		 for(int i=0; i<array.length; i++){ 
		  if(array[i]!=null){
		   return false;
		  }
		  }
		  return true;
	}
	
	public static boolean isEmptyStringArrayList(ArrayList<String> array) {
        if(!array.isEmpty())
        {
        	return true;
        }
        return false;
		}
	
	
	
	 
	public static String getDayFromDate(String inputdate, String dateFormat, String timeZone) throws ParseException{
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			format.setTimeZone(TimeZone.getTimeZone(timeZone));
			Date date = new Date();
			date = format.parse(inputdate);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
		 
		      System.out.println(calendar.getTime().toString());
		      int day = calendar.get(Calendar.DAY_OF_WEEK);
		      System.out.println("Day: " + day);
		      String dayInString = "";
		      if(day == 1){
		    	  dayInString = "Mon";
		      }else if( day  == 2){
		    	  dayInString = "Tue";
		      }
		      else if( day  == 3){
		    	  dayInString = "Wed";
		      }else if( day  == 4){
		    	  dayInString = "Thu";
		      }else if( day  == 5){
		    	  dayInString = "Fri";
		      }else if( day  == 6){
		    	  dayInString = "Sat";
		      }else if( day  == 7){
		    	  dayInString = "Sun";
		      }
		      System.out.println(dayInString);
	      return dayInString;
	     
	      
	}
		

		public static void selectDateFromDatePicker(WebDriver driver, String desiredDate, String formate) {
			app_logs.info("==========SELECT DATE FROM DATE PICKER==========");
			String selectedMonthYearPath = "//div[@class='DayPicker-Caption']/div";
			String nextMonthBtnPath = "//button[contains(@class,'FloatRight')]";
			String previousMonthBtnPath = "//button[contains(@class,'FloatLeft')]";

			String selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
			app_logs.info("Found Mounth Year : " + selectedMonthYearTxt);
			String foundYear = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
			app_logs.info("Parsed Year : " + foundYear);
			String foundMonth = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
			app_logs.info("Parsed Month : " + foundMonth);

			app_logs.info("Desired Date : " + desiredDate);
			String desiredDay = parseDate(desiredDate, formate, "dd");
			app_logs.info("Parsed Desired Day : " + desiredDay);
			String desiredMonth = parseDate(desiredDate, formate, "MM");
			app_logs.info("Parsed Desired Month : " + desiredMonth);
			String desiredYear = parseDate(desiredDate, formate, "yyyy");
			app_logs.info("Parsed Desired Year : " + desiredYear);

			app_logs.info("===========CHECKING YEAR CONDITION==========");
			if (!foundYear.equals(desiredYear)) {
				int foundYearIntParssed = Integer.parseInt(foundYear);
				int desiredYearIntParssed = Integer.parseInt(desiredYear);

				if (foundYearIntParssed < desiredYearIntParssed) {
					app_logs.info("Found Year : " + foundYearIntParssed + " is Less than " + "Desired Year : "
							+ desiredYearIntParssed);
					while (foundYearIntParssed != desiredYearIntParssed) {
						clickThroughJavaScript(driver, driver.findElement(By.xpath(nextMonthBtnPath)));
						app_logs.info("NEXT ARROW CLICKED FOR YEAR ");
						selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
						foundYear = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
						foundMonth = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
						foundYearIntParssed = Integer.parseInt(foundYear);
						app_logs.info("NEW FOUND YEAR : " + foundYearIntParssed);
					}
				} else if (foundYearIntParssed > desiredYearIntParssed) {
					app_logs.info("Found Year : " + foundYearIntParssed + " is Greater than " + "Desired Year : "
							+ desiredYearIntParssed);
					while (foundYearIntParssed != desiredYearIntParssed) {
						clickThroughJavaScript(driver, driver.findElement(By.xpath(previousMonthBtnPath)));
						app_logs.info("PREVIOUS ARROW CLICKED FOR YEAR ");
						selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
						foundYear = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
						foundMonth = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
						foundYearIntParssed = Integer.parseInt(foundYear);
						app_logs.info("NEW FOUND YEAR : " + foundYearIntParssed);
					}
				}
			}

			app_logs.info("===========CHECKING MONTH CONDITION==========");

			if (!foundMonth.equals(desiredMonth)) {
				int foundMonthIntParssed = Integer.parseInt(foundMonth);
				int desiredMonthIntParssed = Integer.parseInt(desiredMonth);

				if (foundMonthIntParssed < desiredMonthIntParssed) {
					app_logs.info("Found Month : " + foundMonthIntParssed + " is Less than " + "Desired Month : "
							+ desiredMonthIntParssed);
					while (foundMonthIntParssed != desiredMonthIntParssed) {
						clickThroughJavaScript(driver, driver.findElement(By.xpath(nextMonthBtnPath)));
						app_logs.info("NEXT ARROW CLICKED FOR Month ");
						selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
						foundYear = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
						foundMonth = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
						foundMonthIntParssed = Integer.parseInt(foundMonth);
						app_logs.info("NEW FOUND MONTH : " + foundMonthIntParssed);
					}
				} else if (foundMonthIntParssed > desiredMonthIntParssed) {
					app_logs.info("Found Month : " + foundMonthIntParssed + " is Greater than " + "Desired Month : "
							+ desiredMonthIntParssed);
					while (foundMonthIntParssed != desiredMonthIntParssed) {
						clickThroughJavaScript(driver, driver.findElement(By.xpath(previousMonthBtnPath)));
						app_logs.info("PREVIOUS ARROW CLICKED FOR Month ");
						selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
						foundYear = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
						foundMonth = parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
						foundMonthIntParssed = Integer.parseInt(foundMonth);
						app_logs.info("NEW FOUND MONTH : " + foundMonthIntParssed);
					}
				}
			}

			app_logs.info("===========SELECTING DESIRED DAY==========");
			// div[@aria-label='Fri May 08 2020']

			driver.findElement(
					By.xpath("//div[@aria-label='" + Utility.parseDate(desiredDate, formate, "EE MMM dd yyyy") + "']"))
					.click();

			app_logs.info("==========DATE SELECTED FROM DATE PICKER==========");
		}
		
		
		public static ArrayList<String> checkDayAndReturnDates(String startDate,String endDate, String formate, String isMon,String isTue, String isWed, String isThru, String isFri, String isSat, String isSun) throws Exception {
			ArrayList<String> returnDate = new ArrayList<String>();
			
			int days = Utility.getNumberofDays(Utility.parseDate(startDate, formate, "dd/MM/yyyy"), Utility.parseDate(endDate, formate, "dd/MM/yyyy"));
			String newDate = startDate;
			for(int i=0; i<days; i++) {
				newDate = Utility.getCustomDate(startDate, formate, formate, i);
				String day = Utility.parseDate(newDate, formate, "E");
				
				if (day.equalsIgnoreCase("Mon")) {
					if (isMon.equalsIgnoreCase("yes")) {
						returnDate.add(newDate);
					}
				} else if (day.equalsIgnoreCase("Tue")) {
					if (isTue.equalsIgnoreCase("yes")) {
						returnDate.add(newDate);
					}
				} else if (day.equalsIgnoreCase("wed")) {
					if (isWed.equalsIgnoreCase("yes")) {
						returnDate.add(newDate);
					}
				} else if (day.equalsIgnoreCase("thu")) {
					if (isThru.equalsIgnoreCase("yes")) {
						returnDate.add(newDate);
					}
				} else if (day.equalsIgnoreCase("fri")) {
					if (isFri.equalsIgnoreCase("yes")) {
						returnDate.add(newDate);
					}
				} else if (day.equalsIgnoreCase("sat")) {
					if (isSat.equalsIgnoreCase("yes")) {
						returnDate.add(newDate);
					}
				} else if (day.equalsIgnoreCase("sun")) {
					if (isSun.equalsIgnoreCase("yes")) {
						returnDate.add(newDate);
					}
				}
				
			}
			return returnDate;

		}

		
		public static <K, V extends Comparable<V>> V maxUsingCollectionsMax(Map<K, V> map) {
		    Entry<K, V> maxEntry = Collections.max(map.entrySet(), new Comparator<Entry<K, V>>() {
		        public int compare(Entry<K, V> e1, Entry<K, V> e2) {
		            return e1.getValue()
		                .compareTo(e2.getValue());
		        }
		    });
		    return maxEntry.getValue();
		}
		
		
		
		
		
		public static <K, V extends Comparable<V>> K maxUsingIteration(Map<K, V> map) {
		    Map.Entry<K, V> maxEntry = null;
		    for (Map.Entry<K, V> entry : map.entrySet()) {
		        if (maxEntry == null || entry.getValue()
		            .compareTo(maxEntry.getValue()) > 0) {
		            maxEntry = entry;
		        }
		    }
		    return maxEntry.getKey();
		}
		
		
		public static boolean isAttribtuePresent(WebElement element, String attribute) { 
		    Boolean result = false; 
			  try { 
		       element.getAttribute(attribute).equals("true"); 
		             result = true; 
		        
		    } catch (Exception e) {  
		       app_logs.info(e.toString()); 
		    } 
		    return result; 
		}
		
		
		
		public static HashMap<String,String> getExcelData(String filePath,String SheetName) throws IOException{
			HashMap<String,String> data = new HashMap<String,String>();
			FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet st = wb.getSheet(SheetName);
			XSSFRow row = st.getRow(0);
			XSSFRow row1 = st.getRow(1);
			int lastColumn=row.getLastCellNum();
			XSSFCell cell;
			XSSFCell cell1;
			String header="";
			String value="";
			for(int i=0;i<lastColumn;i++) {
				cell=row.getCell(i);
				cell1=row1.getCell(i);
				header=cell.getStringCellValue();
				value=cell1.getStringCellValue();
				data.put(header, value)	;
			}
			return data;
		}
		

		public static <K, V> String getKeyOfValue(HashMap<String, String> map, String valueInMap) {
			String key=null;
			for (Entry<String, String> entry : map.entrySet()) {  // Itrate through hashmap
				String x=entry.getValue();
			      if (entry.getValue().toString().toLowerCase().trim().equalsIgnoreCase(valueInMap.toLowerCase().trim())) {
			    	  key=  entry.getKey().toString();     // Print the key with max value
			      app_logs.info(key);
			      }
			    }
			return key;
		}

		
		
		public  static String addTeststepsBlue(String input) {
			return "<p style=\"color:#0D204F\";><b>"+input+"</b></p>";
		}
		public  static String addTeststepsGreen(String input) {
			return "<p style=\"color:#168A0A\";><b>"+input+"</b></p>";
		}
		public  static String addTeststepsRed(String input) {
			return "<p style=\"color:#F3071C\";><b>"+input+"</b></p>";
		}
		

		public static String formatDecimal(String amount) {
			 DecimalFormat df = new DecimalFormat("0.00");
			
				return df.format(Float.parseFloat(amount));
		}
		
		public static void testAddMissingDates() throws ParseException
		{
		    List<Date> listOfDates = new ArrayList<Date>();
		  
		    listOfDates.add(convertStringtoDateFormat("dd/MM/yyyy", "20/09/2020"));
		    listOfDates.add(convertStringtoDateFormat("dd/MM/yyyy", "21/09/2020"));
		    listOfDates.add(convertStringtoDateFormat("dd/MM/yyyy", "25/09/2020"));
		    listOfDates.add(convertStringtoDateFormat("dd/MM/yyyy", "28/09/2020"));
		    listOfDates.add(convertStringtoDateFormat("dd/MM/yyyy", "30/09/2020"));

		    Collections.sort(listOfDates);

		    List<Date> resultingDates = generateDateListBetween(listOfDates.get(0), listOfDates.get(listOfDates.size()-1));

		    //Remove all dates in listOfDates
		    resultingDates.removeAll(listOfDates);

		    for(Date date : resultingDates)
		    {
		        System.out.println(date);
		    }       
		}

		private static List<Date> generateDateListBetween(Date startDate, Date endDate)
		{
		    //Flip the input if necessary, to prevent infinite loop
		    if(startDate.after(endDate))
		    {
		        Date temp = startDate;
		        startDate = endDate;
		        endDate = temp;
		    }

		    List<Date> resultList = new ArrayList<Date>();
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(startDate);

		    do
		    {
		        resultList.add(cal.getTime());
		        cal.roll(Calendar.DAY_OF_MONTH, true);  //Roll one day forwards         
		    }
		    while(cal.getTime().before(endDate));

		    return resultList;
		}
		
		 public static void main(String[] args) throws Exception {
//			 testAddMissingDates();
//			 System.out.println(Utility.getAllDatesBetweenCheckInOutDates("16/09/2020", "20/09/2020"));
			 
//			 int i = 0 ;
//			 
//			 System.out.println(i++);
			 System.out.println(Utility.getAllDatesBetweenTwoDates("18/10/2020", "18/10/2020"));
			 
		 }
		 
		 public static String mapToToken(String delim, HashMap<Integer, String> map) {
			 String finalString = "";

				for (int i = 0; i < map.size(); i++) {
					if (i != 0) {
						finalString += delim;
					}
					finalString += parseDate(map.get(i), "MM/dd/yyyy", "dd/MM/yyyy");
				}
				app_logs.info(finalString);
				return finalString;
		 }
		
		 /*
			 * #########################################################################
			 * #########################################################################
			 * ######################## 
			 *
			 * ' Method Name: <isSelectedWithBefore> ' ' Description: < Click Element
			 * Through JS > ' Input parameters: < WebDriver driver,WebElement ele > ' '
			 * Return value: <void> ' Created By: <Muhammad Haider> ' Created On:
			 * <MM/DD/YYYY> <06/30/2020>
			 * 
			 * #########################################################################
			 * #########################################################################
			 * ########################
			 */
			public static boolean isSelectedWithBefore(WebDriver driver, WebElement ele) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				String day = jse.executeScript(
						"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
						ele).toString();
				if (!day.contains("none")) {
					return true;
				}else {
					return false;
				}
			}
			
			public static ArrayList<String> getAllDatesBetweenTwoDates(String startDate, String endDate) throws ParseException {
				LocalDate start = LocalDate.parse(parseDate(startDate, "dd/MM/yyyy", "yyyy-MM-dd"));
				LocalDate end = LocalDate.parse(parseDate(endDate, "dd/MM/yyyy", "yyyy-MM-dd"));
				ArrayList<String> allDays = new ArrayList<>();
				while (!start.isAfter(end)) {
					String date = start.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					allDays.add(date);
					start = start.plusDays(1);
				}
				return allDays;

			}
			public static Map<String, String> parseQuerystring(String queryString) 
			{
				Map<String, String> map = new HashMap<String, String>();
		        if ((queryString == null) || (queryString.equals(""))) {
		            return map;
		        }
		        String[] params = queryString.split("&");
		        for (String param : params) {
		            try {
		                String[] keyValuePair = param.split("=", 2);
		                String name = URLDecoder.decode(keyValuePair[0], "UTF-8");
		                if (name == "") {
		                    continue;
		                }
		                String value = keyValuePair.length > 1 ? URLDecoder.decode(
		                        keyValuePair[1], "UTF-8") : "";
		                map.put(name, value);
		            } catch (UnsupportedEncodingException e) {
		                // ignore this parameter if it can't be decoded
		            }
		        }
		        return map.get("AccountId");
		       // return map;
			}
			
}
