package com.innroad.inncenter.testcore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.utils.APIClient;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.utils.XmlUtility;
import com.innroad.inncenter.utils.excel_reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestCore {

	public static String PROJECT_ID;
	public static Long TestPlan_id;
	public static Long suite_id;
	public static String TestRail_AssignToID ;

	public static String testRailUserName;
	public static String testRailPassword;
	public static String vrboToken;
	public static int statusCode200;
	public static int statusCode403;
	public static int statusCode404;
	public static Properties vrboProperties = new Properties();

	public static String vrvoToken;
	public static String vrvoRateToken;
	public static String BookingRequestToken;
	public static String vrvoBookingUpdateToken;
	public static String airbnbHostUrl;
	public static String airbnbEmail;
	public static String airbnbPassword;
	public static Properties vrvoproperties = new Properties();
	public static Properties securityProperties = new Properties();

	public static String propertyTimeZone;
	public static String propertyCurrency;
	public static String propertyDateFormat;
	public static String missingMandatoryFieldsValidationMessage;

	//public static  String propertyCurrency="$";

	public static APIClient client = null;
	public static String propertyFirstDayOfWeek;


	/*@AfterClass(alwaysRun = true)
	public void closeDriver2() throws MalformedURLException, IOException, APIException {
		getDriver().close();
		System.out.println("After Class 2");
	}
*/


	protected String DBenv = null;
	// Setting path variables
	public static String extentReportsPath = System.getProperty("user.dir") + File.separator + "extent-reports";
	public static String testDataPath = System.getProperty("user.dir") + File.separator + "test-data";

	public static String configFilesPath = System.getProperty("user.dir") + File.separator + "config-files";
	public static String picFilePath = System.getProperty("user.dir") + File.separator + "resources";
	public static String schemaFilesPath = System.getProperty("user.dir") + File.separator + "schema-files";
	public static String imagesFilesPath = System.getProperty("user.dir") + File.separator + "Images";

	// This is used to store the email attachments
	public static String mailAttachmentsPath = System.getProperty("user.dir") + File.separator + "externalFiles"
			+ File.separator + "downloadFiles" + File.separator;

	public static final ExtentReports extent = new ExtentReports(extentReportsPath + File.separator
			+ "CentralParkSanitySuite_Execution_Report_" + Utility.getTimeStamp() + ".html", false);	
	
	public static ExtentTest test;

	public static ThreadLocal<RemoteWebDriver> dr234 = new ThreadLocal<RemoteWebDriver>();

	// This captures all the failed and skipped test scripts
	public static List<String> failedSkippedTestsList = new ArrayList<String>();

	public static boolean isSuiteCreated = false;

	// This flag is used to generate the report for failed scripts execution
	public static String currentSuiteFile = "";

	public WebDriver getDriver() {

		return dr234.get();
	}

	public void setWebDriver(RemoteWebDriver driver) {

		dr234.set(driver);
	}

	public static Properties config = new Properties();
	public static Properties ratesConfig = new Properties();
	// public static WebDriver driver = null;
	public static excel_reader envLoginExcel = null;
	public static excel_reader HS_EXCEL = null;
	public static excel_reader BEExcel = null;
	public static excel_reader SNExcel = null;
	public static excel_reader SanityExcel = null;
	public static excel_reader otaexcel = null;
	public static excel_reader airbnbexcel = null;
	public static excel_reader excelReservationV2 = null;	
	public static excel_reader excel = null;
	public static excel_reader gsexcel = null;
	public static excel_reader excel_Swarna = null;
	public static excel_reader securityexcel = null;
	public static excel_reader reportV2excel = null;	
	//Excel reader added by Riddhi
	public static excel_reader excelRiddhi = null;
	public static Logger app_logs = Logger.getLogger("Testcore");
	public static Properties testRailConfig = new Properties();

	// It is used to store jenkins build parameter
	public static String envURL;

	public static String targetBrowser;
	// public static monitoringMail mail = new monitoringMail();

	@BeforeSuite(alwaysRun = true)
	@Parameters("env")
	public void init(@Optional("https://app.qainnroad.com") String env, ITestContext ctx)
			throws IOException, APIException {

		FileInputStream fis1 = new FileInputStream(configFilesPath + File.separator + "TestRailConfig.properties");
		testRailConfig.load(fis1);
		PROJECT_ID = testRailConfig.getProperty("TestRail_ProjectId").trim();
		String testRunId = testRailConfig.getProperty("TestRail_TestRunId").trim();
		suite_id = Long.parseLong(testRunId);
		testRailUserName = testRailConfig.getProperty("TestRail_Username").trim();
		testRailPassword = testRailConfig.getProperty("TestRail_Password").trim();
		TestRail_AssignToID = testRailConfig.getProperty("TestRail_AssignToID").trim();
		System.out.println("TestRail_AssignToID: " + TestRail_AssignToID);
		System.out.println("BeforeSuite :  " + Utility.BeforeSuite);
		FileInputStream fis2 = new FileInputStream(configFilesPath + File.separator + "vrbo.properties");
		FileInputStream fis3 = new FileInputStream(configFilesPath + File.separator + "vrbo.properties");
		vrvoproperties.load(fis2);
		vrboProperties.load(fis3);
		vrboToken = vrvoproperties.getProperty("AuthToken");

		statusCode200 = Integer.parseInt(vrboProperties.getProperty("statusCode200"));
		statusCode403 = Integer.parseInt(vrboProperties.getProperty("statusCode403"));
		statusCode404 = Integer.parseInt(vrboProperties.getProperty("statusCode404"));

		vrvoToken = vrvoproperties.getProperty("AuthToken");
		vrvoRateToken = vrvoproperties.getProperty("RateAuthToken");
		BookingRequestToken = vrvoproperties.getProperty("BookingRequestToken");
		vrvoBookingUpdateToken = vrvoproperties.getProperty("BookingUpdateToken");

		FileInputStream securityproperty = new FileInputStream(configFilesPath + File.separator + "security.properties");
		securityProperties.load(securityproperty);
		// vrvoToken = vrvoproperties.getProperty("AuthToken");
		if (Utility.BeforeSuite == 0) {

			// This statement will move old extent reports present in the
			// 'extent-reports' directory to 'extent-reports\archived' directory
			Utility.archiveExtentReports(extentReportsPath);
			// This statement will move old sax api files present in the
			// 'sax-files' directory to 'sax-files\archived' directory
			// Utility.archiveSaxFiles(saxFilePath);
			// Checks and creates the directory where attached files will be
			// stored
			EmailUtils.setTargetDirectory(mailAttachmentsPath);

			extent.loadConfig(new File("extent-config.xml"));
		}
		Utility.BeforeSuite++;
		System.out.println("after if BeforeSuite :  " + Utility.BeforeSuite);
		envURL = env.replaceAll("\"", "");
		Utility.envURL = env;

		System.out.println("##################################################################################");
		System.out.println("EXECUTING Automation CENTRALPARK-SANITY SUITE ON: " + envURL + " - ENVIRONMENT.");
		System.out.println("##################################################################################");

		extent.addSystemInfo("Selenium-WebDriver", "3.141.59");

		// String envName = envURL.substring(envURL.indexOf('/') + 2,
		// envURL.indexOf("com") + 3).toUpperCase().trim();

		// Load the configuration file
		FileInputStream fis = new FileInputStream(configFilesPath + File.separator + "config");
		config.load(fis);

		// Load the Rate V2 configuration file
		fis = new FileInputStream(configFilesPath + File.separator + "ratesv2.properties");
		ratesConfig.load(fis);

		// Writing environment url to configuration file to use while re-running failed
		// test scripts
		
		String envName = null;

		try {

			envName = envURL.substring(envURL.indexOf('/') + 2, envURL.indexOf("com") + 3).toUpperCase().trim();

		} catch (Exception e) {

			envName = "DEV";

		}
		
		

		extent.addSystemInfo("Env-URL", envURL);
		PropertyConfigurator.configure("log4j.properties");

		// Load the Excel file
		excel = new excel_reader(testDataPath + File.separator + "FormSubmitting.xlsx");
		
	}

	public void HS_login(WebDriver driver, String envURL, ArrayList<String> loginCreds) throws InterruptedException {
		// Login to InnCenter for GS
		Login login = new Login();
		login.login(driver, envURL, loginCreds.get(1), loginCreds.get(2), loginCreds.get(3));

	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeClass() throws MalformedURLException, ParseException {

		RemoteWebDriver driver = null;
		targetBrowser = config.getProperty("browser").toUpperCase();
		DesiredCapabilities dc = null;

// 		The below code for docker execution

		String host = "localhost";
		dc = DesiredCapabilities.chrome();

		if (System.getProperty("BROWSER") != null && System.getProperty("BROWSER").equalsIgnoreCase("firefox")) {
			dc = DesiredCapabilities.firefox();
		}

		if (System.getProperty("HUB_HOST") != null) {
			host = System.getProperty("HUB_HOST");
		}

		String completeUrl = "http://" + host + ":4444/wd/hub";

		switch (targetBrowser) {

		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", ".\\Drivers\\geckodriver.exe");

			FirefoxOptions fo = new FirefoxOptions();
			fo.setLogLevel(FirefoxDriverLogLevel.INFO);

			// preference added for downloading pdf instead view in browser pdf
			// viewer

			fo.addPreference("browser.download.dir", System.getProperty("user.dir") + File.separator + "externalFiles"
					+ File.separator + "downloadFiles" + File.separator);
			fo.addPreference("browser.download.useDownloadDir", true);
			fo.addPreference("browser.download.folderList", 2);
			fo.addPreference("browser.download.manager.showWhenStarting", false);
			fo.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
			fo.addPreference("pdfjs.disabled", true);
			fo.addPreference("browser.link.open_newwindow", 1);
			fo.addPreference("marionette", false);

			// Use this to disable Acrobat plugin for previewing PDFs in Firefox
			// (if you have Adobe reader installed on your computer)
			// fo.addPreference("plugin.scan.Acrobat", "99.0");
			// fo.addPreference("plugin.scan.plid.all", false);
			// FirefoxProfile profile= new FirefoxProfile();
			// profile.setPreference( "layout.css.devPixelsPerPx", "0.9" );
			// fo.setProfile(profile);
			driver = new FirefoxDriver(fo);
			setWebDriver(driver);
			break;

		case "CHROME":
			System.setProperty("webdriver.chrome.silentOutput", "true");

			if (currentSuiteFile.equals("tapechart.xml")) {
				System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver2.36\\chromedriver.exe");

			} else {
				System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
			}

			// Setting new download directory path
			Map<String, Object> prefs = new HashMap<String, Object>();

			// Use File.separator as it will work on any OS
			prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "externalFiles"
					+ File.separator + "downloadFiles");
			prefs.put("plugins.always_open_pdf_externally", true);
			prefs.put("download.extensions_to_open", "applications/pdf");

			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			chromeOptions.setExperimentalOption("prefs", prefs);
			// chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			// chromeOptions.addArguments("--disable-gpu");
			// chromeOptions.addArguments("disable-infobars");

			//chromeOptions.addArguments("--disable-popup-blocking");
			//chromeOptions.addArguments("user-data-dir=D:\\Python\\GetStartedPython\\chrome-dir-Facebook");
			LoggingPreferences logs = new LoggingPreferences();
			// logs.enable(LogType.BROWSER, Level.ALL);
			// logs.enable(LogType.CLIENT, Level.ALL);
			// logs.enable(LogType.DRIVER, Level.ALL);
			// logs.enable(LogType.PERFORMANCE, Level.ALL);
			// logs.enable(LogType.PROFILER, Level.ALL);
			// logs.enable(LogType.SERVER, Level.ALL);
			//dc = DesiredCapabilities.chrome();
			//dc.setCapability(CapabilityType.LOGGING_PREFS, logs);
			//dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			//dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			// use for docker
			 //driver = new RemoteWebDriver(new URL(completeUrl), dc);

			// use for stand-alone execution
			driver = new ChromeDriver(chromeOptions);

			// use for docker execution

	//		 driver = new RemoteWebDriver(new URL(completeUrl), dc);

			setWebDriver(driver);

			app_logs.info(targetBrowser + " driver is initialized");
			getDriver().manage().window().maximize();
			app_logs.info("Browser window is maximized");

			// Setting default web driver timeout to 30 SECONDS
			getDriver().manage().timeouts().implicitlyWait(30L, TimeUnit.SECONDS);
			break;

		case "IE":

			System.setProperty("webdriver.ie.driver", ".\\Drivers\\IEDriverServer.exe");

			InternetExplorerOptions ieOptions = new InternetExplorerOptions();

			ieOptions.setCapability("nativeEvents", false);
			ieOptions.setCapability("unexpectedAlertBehaviour", "accept");
			ieOptions.setCapability("ignoreProtectedModeSettings", true);
			ieOptions.setCapability("disable-popup-blocking", true);
			ieOptions.setCapability("enablePersistentHover", true);
			ieOptions.setCapability("ignoreZoomSetting", true);

			driver = new InternetExplorerDriver(ieOptions);
			setWebDriver(driver);
			break;

		case "OPERA":
			// In Progress
			System.setProperty("webdriver.opera.driver", ".\\Drivers\\operadriver.exe");
			OperaOptions operaOptions = new OperaOptions();
			driver = new OperaDriver(operaOptions);
			setWebDriver(driver);
			break;

		case "OTA":
			break;
		}
		

	}

	
	
	public void login_GS(WebDriver driver) throws InterruptedException {
		// Login to InnCenter for GS

		Login login = new Login();
		login.login(driver, envURL, Utility.login_GS.get(1), Utility.login_GS.get(2), Utility.login_GS.get(3));

	}

	public static void login_NONGS(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.login.get(1), Utility.login.get(2), Utility.login.get(3));
	}

	public static void login_CP(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.login_CP.get(1), Utility.login_CP.get(2), Utility.login_CP.get(3));
	}
	
	public static void login_Autoota(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.login_Autoota.get(1), Utility.login_Autoota.get(2), Utility.login_Autoota.get(3));
	}

	public static void login_CP(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, IOException {
		login_CP(driver);
		test_steps.add("Logged into inncenter");
	}
	
	public static void login_Autoota(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, IOException {
		login_Autoota(driver);
		test_steps.add("Logged into inncenter");
	}

	public static void login_Group(WebDriver driver) throws InterruptedException, IOException {

		// Group Login to InnCenter for NON GS
		Login login = new Login();
		System.out.println(Utility.login_Group.get(1));
		login.login(driver, envURL, Utility.login_Group.get(1), Utility.login_Group.get(2), Utility.login_Group.get(3));

	}
	
	

	@AfterSuite(alwaysRun = true)
	public void closeDriverInstance() {

		// sending mail
		// testUtil.zip(System.getProperty("user.dir")+"\\screenshot");
		// mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to,"Test
		// Report", "Please find the attached report",TestConfig.reportPath
		// "Reports.zip");

		app_logs.info("before end suite BeforeSuite :  " + Utility.BeforeSuite);
try {
	    Utility.BeforeSuite = 1;
		if (Utility.BeforeSuite == 1) {
			//extent.addSystemInfo("Client", Utility.USED_CLIENT);
			extent.addSystemInfo("Target-Browser", targetBrowser);
			extent.flush();
			Utility.BeforeSuite = 0;

		}

		/*
		 * // Below code is not required, it is used for debugging purpose
		 * 
		 * try { EmailUtils.sendEmailWithAttachment(reportFile); } catch (Exception e1)
		 * { e1.printStackTrace(); }
		 */

		// This will generate the xml suite for failed and skipped tests
		 /*isSuiteCreated =
		 XmlUtility.generateXmlSuiteForFailedSkippedTests("CentralParkSanity-Failed.xml",failedSkippedTestsList);
*/
		// Rerun the failed and skipped tests again
		try {

			if (isSuiteCreated) {
				app_logs.info("Executing batch file");
				String command = "cmd.exe /C start " + System.getProperty("user.dir") + File.separator
						+ "CentralParkSanity-Failed.bat";
				Runtime.getRuntime().exec(command);
			} else {
				app_logs.info("Suite file is not created hence skipping the batch file execution for failed tests");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		// This block of code will run the failed / skipped tests
		try {

			if (currentSuiteFile.equals("CentralParkSanity-Failed.xml")) {

				extent.addSystemInfo("Client", Utility.login.get(1));
				extent.addSystemInfo("Target-Browser", targetBrowser);
				extent.flush();
			}

			// EmailUtils.sendEmailWithAttachment(reportFile);

		} catch (Exception e1) {

			e1.printStackTrace();

		}
}catch (Exception e) {
	e.printStackTrace();
}
		app_logs.info("after end suite BeforeSuite :  " + Utility.BeforeSuite);
	}

	public static void login_CP(WebDriver driver, String user) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.login_CP.get(1), user, Utility.login_CP.get(3));
	}

	public static void logout(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.logout(driver);
		;
	}

	public static void loginWPI(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.loginWPI.get(1), Utility.loginWPI.get(2), Utility.loginWPI.get(3));
	}

	public static void loginAutoReportsV2(WebDriver driver) throws InterruptedException, IOException {
		Login login = new Login();
		login.login(driver, envURL, Utility.loginAutoReportsV2.get(1),
				Utility.loginAutoReportsV2.get(2), Utility.loginAutoReportsV2.get(3));
	}
	
	public static void loginRateV2(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.loginRateV2.get(1), Utility.loginRateV2.get(2), Utility.loginRateV2.get(3));
	}
	
	public static void loginClinent3281(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.loginClient3281.get(1), Utility.loginClient3281.get(2), Utility.loginClient3281.get(3));
	}
	public static void loginAutoReportsV2(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, IOException {
		loginAutoReportsV2(driver);
		test_steps.add("Logged in with client : <b>" + Utility.loginReportsV2ReservationV2.get(1) + "</b>");
	}


	// Login method for ReportsV2
	public static void loginReportsV2(WebDriver driver) throws InterruptedException, IOException {
		Login login = new Login();
		login.login(driver, envURL, Utility.loginReportsV2.get(1), Utility.loginReportsV2.get(2),
				Utility.loginReportsV2.get(3));
	}

	// Login method for ReportsV2 with ReservationV2
	public static void loginReportsV2ReservationV2(WebDriver driver) throws InterruptedException, IOException {
		Login login = new Login();
		login.login(driver, envURL, Utility.loginReportsV2ReservationV2.get(1),
				Utility.loginReportsV2ReservationV2.get(2), Utility.loginReportsV2ReservationV2.get(3));
	}

	// Login method for ReportsV2 with ReservationV2
	public static void loginReportsV2ReservationV2(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, IOException {
		loginReportsV2ReservationV2(driver);
		test_steps.add("Logged in with client : <b>" + Utility.loginReportsV2ReservationV2.get(1) + "</b>");
	}

	// Login method with super user
	public static void loginSuperUser(WebDriver driver) throws InterruptedException, IOException {
		Login login = new Login();
		login.login(driver, envURL, Utility.loginSuperuser.get(1), Utility.loginSuperuser.get(2),
				Utility.loginSuperuser.get(3));
	}

	public static void loginOTA(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.loginOTA.get(1), Utility.loginOTA.get(2), Utility.loginOTA.get(3));
	}

	public static void loginAirbnb(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.loginAirbnb.get(1), Utility.loginAirbnb.get(2), Utility.loginAirbnb.get(3));
	}

	public static void loginVrbo(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.loginVrbo.get(1), Utility.loginVrbo.get(2), Utility.loginVrbo.get(3));
	}

	public static void printString(String value) {
		System.out.println(value);
	}
	
	public static void loginHostAirbnb(WebDriver driver) throws InterruptedException {
		Login login = new Login();
		login.loginAirbnbHost(driver,airbnbHostUrl, airbnbEmail, airbnbPassword);
	}

	public static void loginNightAudit(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		System.out.println( Utility.loginNightAudit.get(1) + " : " +  Utility.loginNightAudit.get(2) + " : " +  Utility.loginNightAudit.get(3));
		Login login = new Login();		
		login.login(driver, envURL, Utility.loginNightAudit.get(1), Utility.loginNightAudit.get(2), Utility.loginNightAudit.get(3));
		
	}
	
	
	public static void loginResV2(WebDriver driver) throws InterruptedException, IOException {
		// Login to InnCenter for NON GS
		Login login = new Login();
		login.login(driver, envURL, Utility.loginResV2.get(1), Utility.loginResV2.get(2), Utility.loginResV2.get(3));
	}
}

