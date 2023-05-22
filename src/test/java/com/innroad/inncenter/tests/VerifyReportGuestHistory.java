
package com.innroad.inncenter.tests;

import com.innroad.inncenter.testcore.TestCore;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DocumnetTemplates;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyReportGuestHistory extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	public static String timeZone = null;
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>(); 

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyGuestHistoryAccount(String TestCaseID,String propertyName, String phonenumber, String email, String city,
			String state, String cardNumber, String paymentMethod, String pageType, String functionName,
			String scenario) throws ParseException {
		if(Utility.isAllDigit(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("5");
			comments.add("Failed");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
				comments.add("Failed");
			}
		}
		if (!scenario.equalsIgnoreCase("GS/GR")) {
			test_name = "VerifyReport" + " " + scenario + " In " + pageType;
		} else if (functionName.equalsIgnoreCase("Guest Statement")) {
			test_name = "VerifyReport" + " " + functionName + " In " + pageType;
		} else if (functionName.equalsIgnoreCase("Guest Registration")) {
			test_name = "VerifyReport" + " " + functionName + " In " + pageType;

		}

		test_description = "Verify Guest History Account Report<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1984' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1984</a>";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Navigation nav = new Navigation();
		GuestHistory guestHistory = new GuestHistory();
		CPReservationPage cpRes = new CPReservationPage();
		Admin admin = new Admin();
		Reports report = new Reports();
		Account createCA = new Account();
		DocumnetTemplates documentTemplate = new DocumnetTemplates();
		String guestFirstName = "gFirst" +  Utility.generateRandomStringWithGivenLength(3);
		String guestLastName = "gLast" +  Utility.generateRandomStringWithGivenLength(3);
		String billingNotes = "adding primary card";
		String marketSegment = "Internet";
		String referral = "Other";
		String salutation = "Mr.";
		String accountNo = "";
		String confirmationNum = "";
		String postalcode = Utility.fourDigitgenerateRandomString();
		String expDate = Utility.getFutureMonthAndYearForMasterCard();
		String documentTemplateName = functionName + "" + "Testing";
		String templateDescription = functionName + " " + "TestDescription";
		String address = "testAddress";
		String reportType = functionName;
		String fileName = null;
		String lines[] = null;
		String reportId = "Object1";
		String reportSource = "data";
		String headingText = "TestingTemplateContent";
		String contentFields = "GuestFirstName /GuestLastName/ConfirmationNumber";
		HashMap<String, String> map = new HashMap<String, String>();
		String mailingListHeading = "Reservation Mailing List";
		String listReportHeading = "Reservation List With Total Revenue";
		String exportInto = "Export Report To Excel";
		String expectedFileName = "";
		String accountName = "";
		String accounttype = "Corporate/Member Accounts";
		String adminName = "";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			test_steps.add("=================GET THE PROPERTY TIMEZONE ======================");
			app_logs.info("================= GET THE PROPERTY TIMEZONE ======================");
			nav.Setup(driver);
			nav.Properties(driver);
			nav.openProperty(driver, test_steps, propertyName);
			nav.clickPropertyOptions(driver, test_steps);
			timeZone = nav.get_Property_TimeZone(driver);
			test_steps.add("Property TimeZone: " + timeZone);
			nav.admin(driver);
			nav.clickonClientinfo(driver);
			adminName = admin.getAdminName(driver);
			nav.Reservation_Backward(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (pageType.equalsIgnoreCase("Guest History")) {
				// Create Guest Account test_steps.

				test_steps.add("=================CREATE GUEST HISTORY ACCOUNT ======================");
				app_logs.info("=================CREATE GUEST HISTORY ACCOUNT======================");
				nav.GuestHistory(driver);
				guestHistory.clickGuestHistoryNewAccount(driver);
				test_steps.add("Open new guest history");
				boolean flag = guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
						test_steps);
				while (!flag) {
					if (!flag) {
						guestHistory.closeAccount(driver);
						test_steps.add("====== CLOSE OLDER TAB AND OPEN AGAIN NEW ACCOUNT TAB =======");
						app_logs.info("====== CLOSE OLDER TAB AND OPEN AGAIN NEW ACCOUNT TAB ======");
						guestHistory.clickGuestHistoryNewAccount(driver);
						test_steps.add("Open new guest history");
						flag = guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
								test_steps);
					}
				}
				guestHistory.accountAttributes(driver, marketSegment, referral, test_steps);
				accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);

				System.out.println(accountNo);
				guestHistory.mailinginfo(driver, guestFirstName, guestLastName, phonenumber, phonenumber, address,
						address, address, email, city, state, postalcode, test_steps);

				map.put("GuestFirstName", guestFirstName);
				map.put("GuestLastName ", guestLastName);

				guestHistory.billinginfo(driver, paymentMethod, cardNumber, expDate, billingNotes, test_steps);
				guestHistory.clickSave(driver);
				test_steps.add("Successfully created Guest Account");

				test_steps.add("================= CREATE RESERVATION FROM ACCOUNT NEW BUTTON ======================");
				guestHistory.newReservation(driver);
				test_steps.add("Click new reservation button on Guest History Account page");
				cpRes.clickOnFindRooms(driver, test_steps);
				cpRes.selectRoomClass(driver, test_steps);
				cpRes.clickNext(driver, test_steps);
				cpRes.clickBookNow(driver, test_steps);
				confirmationNum = cpRes.get_ReservationConfirmationNumber(driver, test_steps);
				map.put("ConfirmationNumber", confirmationNum);
				cpRes.clickCloseReservationSavePopup(driver, test_steps);
				cpRes.close_FirstOpenedReservation(driver, test_steps);
			} else if (pageType.trim().equalsIgnoreCase("Account")) {
				test_steps.add(
						"=================CREATE ACCOUNT TYPE ======================: " + accounttype.toUpperCase());
				app_logs.info(
						"=================CREATE ACCOUNT TYPE ======================: " + accounttype.toUpperCase());
				nav.Accounts(driver);
				test_steps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");
				accountName = guestFirstName;

				createCA.ClickNewAccountbutton(driver, accounttype);
				createCA.AccountDetails(driver, accounttype, accountName);
				createCA.AccountAttributes(driver, test, marketSegment, referral, test_steps);
				map.put("GuestFirstName", guestFirstName);
				map.put("GuestLastName ", guestLastName);
				createCA.Mailinginfo(driver, test, guestFirstName, guestLastName, phonenumber, phonenumber, address,
						address, address, email, city, state, postalcode, test_steps);
				createCA.Billinginfo(driver, test, test_steps);
				// createCA.Cp_AccAddNotes(driver, Subject, Description, NoteType);
				createCA.AccountSave(driver, test, accountName, test_steps);
				test_steps.add("Successfully created Guest Account");
				test_steps.add("================= CREATE RESERVATION FROM ACCOUNT NEW BUTTON ======================"
						+ accounttype.toUpperCase());
				createCA.CP_NewReservationButton(driver, test);
				test_steps.add("Click new reservation button on Guest History Account page");
				cpRes.clickOnFindRooms(driver, test_steps);
				cpRes.selectRoomClass(driver, test_steps);
				cpRes.clickNext(driver, test_steps);
				cpRes.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName);
				cpRes.clickBookNow(driver, test_steps);
				confirmationNum = cpRes.get_ReservationConfirmationNumber(driver, test_steps);
				map.put("ConfirmationNumber", confirmationNum);
				cpRes.clickCloseReservationSavePopup(driver, test_steps);
				cpRes.close_FirstOpenedReservation(driver, test_steps);

			}
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (scenario.equalsIgnoreCase("GS/GR")) {
				test_steps.add("=================== CREATE NEW DOCUMENT TEMPLATE ======================");
				app_logs.info("=================== CREATE NEW DOCUMENT TEMPLATE ======================");

				nav.setup(driver);
				nav.navigateDocumentTemplate(driver);
				documentTemplate.deleteAllDocuments(driver);
				documentTemplate.clickNewDocument(driver, test_steps);
				documentTemplate.enterDocumnetName(driver, documentTemplateName);
				documentTemplate.enterDocumnetDescription(driver, templateDescription, test_steps);

				documentTemplate.select_DefaultTemplate(driver, test_steps);

				test_steps.add("=================== ASSOCIATING SOURCES ======================");
				app_logs.info("=================== ASSOCIATING SOURCES ======================");
				documentTemplate.associateSources(driver, test_steps);

				test_steps.add("=================== ASSOCIATING PROPERTIES ======================");
				app_logs.info("=================== ASSOCIATING PROPERTIES ======================");
				documentTemplate.associateProperties(driver, propertyName, test_steps);

				test_steps.add("=================== ASSOCIATING FUNCTIONS ======================");
				app_logs.info("=================== ASSOCIATING FUNCTIONS ======================");
				if (functionName.equalsIgnoreCase("Guest Registration")) {
					documentTemplate.associateFunction(driver, functionName + " " + "Form", test_steps);
				} else {
					documentTemplate.associateFunction(driver, functionName, test_steps);
				}

				test_steps.add("=================== ADDING CONTENT ======================");
				app_logs.info("=================== ADDING CONTENT ======================");
				documentTemplate.click_Content(driver, test_steps);

				documentTemplate.enterHeadingInContentEditor(driver, headingText);

				String[] contentFieldArray = contentFields.split("\\|");
				for (String field : contentFieldArray) {
					if (!field.equals("")) {
						documentTemplate.enter_SpecificContentFields(driver, test_steps, field);
					}
				}
				documentTemplate.saveDocument(driver, test_steps);
				//comments.add("Created Custom Document Template: "+ documentTemplateName);
				nav.reservation(driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Document Template", testName, "Document Template", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Document Template", testName, "Document Template", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (pageType.equalsIgnoreCase("Guest History")) {
				test_steps
						.add("=================== NAVIGATE TO GUEST HISTORY AND SEARCH ACCOUNT ======================");
				app_logs.info(
						"=================== NAVIGATE TO GUEST HISTORY AND SEARCH ACCOUNT  ======================");
				nav.GuestHistory(driver);
				guestHistory.searchWithFirstName(driver, guestFirstName, test_steps);
				guestHistory.OpenSearchedGuestHistroyAccount(driver);
				test_steps.add("Opened searched account");
				test_steps.add("=================== SEARCH CREATED RESEVATION UNDER ACCOUNT ======================");
				app_logs.info("=================== SEARCH CREATED RESEVATION UNDER ACCOUNT  ======================");
				guestHistory.OpenReservtionTab_GuestHistoryAccount(driver);
				test_steps.add("Navigate to Reservation Tab under Account");
				guestHistory.searchReservation(driver, confirmationNum);
				test_steps.add("Search account: " + confirmationNum);
				guestHistory.clickOnPrintIcon(driver, test_steps);
			} else if (pageType.trim().equalsIgnoreCase("Account")) {
				nav.Accounts(driver);
				test_steps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");
				createCA.searchForAnAccountAndOpen(driver, test_steps, accountName, accounttype);
				createCA.clickreservationTab(driver, test_steps);
				createCA.clickOnPrintIcon(driver, test_steps);
			}

			test_steps.add(
					"===================== DOWNLOAD <b>" + reportType.toUpperCase() + "</b> =====================");
			app_logs.info(
					"===================== DOWNLOAD <b>" + reportType.toUpperCase() + "</b> =====================");

			cpRes.selectReportTypeButton(driver, reportType, "radio", true, test_steps);
			if (!scenario.equalsIgnoreCase("ExcelList Report")) {
				cpRes.clickPrintReservationReports(driver);
				test_steps.add("Click Print Reservation Report");
				app_logs.info("Click Print Reservation Report");
				cpRes.waitTillNumberOfTabsExist(driver, 2);
				test_steps.add("New tab opened");
				app_logs.info("New tab opened");
				Utility.switchTab(driver, 1);
			} else {

				String adminame = adminName.replace(" ", "_");
				expectedFileName = adminame + "_" + listReportHeading.replace(" ", "") + "_"
/*=======
				//expectedFileName = "Automation_Reports_Client" + "_" + listReportHeading.replace(" ", "") + "_"
				//		+ Utility.getCurrentDate("yyyyMMdd", timeZone);
				expectedFileName = "Automation_QA_Test_Client_GS" + "_" + listReportHeading.replace(" ", "") + "_"
>>>>>>> develop*/
						+ Utility.getCurrentDate("yyyyMMdd", timeZone);
				guestHistory.clickExportButton(driver, exportInto, test_steps);

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.open('');");
				cpRes.waitTillNumberOfTabsExist(driver, 2);
				Utility.switchTab(driver, 1);
				test_steps.add("VERIFYING REPORT NOT DOWNLOADING TWICE ISSUE NG-4769 FOR FUNCTION: "
						+ reportType.toUpperCase());
				ArrayList<String> fileNames = Utility.download_statusWithCount(driver);
				for (String filesName : fileNames) {
					Assert.assertTrue(filesName.contains(expectedFileName),
							"Failed : '" + reportType + "' File Name missmatched");
				}
				test_steps.add("Verified Reports Name: " + fileNames);
				app_logs.info("Verified Report Name: " + fileNames);
				
				try {
					Assert.assertEquals(fileNames.size(), 1);
					test_steps.add("Success - Verifyied downloaded files count" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-4769'>" + "Click here to open JIRA: NG-4769</a>");
					test_steps.add("Verifyied downloaded files count is:" + fileNames.size());
					statusCode.set(0,"1");
					comments.set(0,"Excel List report downloaded successfully "+expectedFileName);
				} catch (Exception e) {
					test_steps.add("FAIL - Sort Report By drop down options validation" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-338'>"
							+ "Click here to open JIRA: RPT-338</a>"+e.toString());
					
				} catch (Error e) {
					test_steps.add("FAIL - Sort Report By drop down options validation" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-338'>"
							+ "Click here to open JIRA: RPT-338</a>"+e.toString());
				}

				Utility.closeTabsExcept(driver, 1);
				test_steps.add("Close report's tab and switch to first tab");
				app_logs.info("Close report's tab and switch to first tab");
			}
			if (!scenario.equalsIgnoreCase("ExcelList Report")) {
				if (cpRes.reportDisplayed(driver, reportId, test_steps)) {
					fileName = cpRes.downloadReport(driver, reportSource, reportId);
					test_steps.add("Download Report");
					app_logs.info("Download Report");
					test_steps.add("=====================VERIFY <b>" + reportType.toUpperCase()
							+ "</b> REPORT NAME =====================");
					app_logs.info("===================== VERIFY <b>" + reportType.toUpperCase()
							+ "</b> REPORT NAME =====================");
					test_steps.add("Actual File Name '" + fileName + "'");
					app_logs.info("Actual File Name '" + fileName + "'");
					if (scenario.equalsIgnoreCase("GS/GR")) {
						
							String expectedfilename = Utility.getCurrentDate("yyyyMMdd", timeZone);
							String foundName = fileName.substring(0, 8);
							try {
								Assert.assertEquals(foundName, expectedfilename, "Failed to verify");
								test_steps.add("Sucess - verify file name" + "<br>"
										+ "<a href='https://innroad.atlassian.net/browse/NG-11636'>" + "Click here to open JIRA: NG-11636</a>");
								app_logs.info("Verified Report Name");
								statusCode.set(0,"1");
								comments.set(0,"Created successfully custom report get downloaded: "+expectedfilename);
								statusCode.set(1,"1");
								comments.set(1,"Created successfully custom report get downloaded: "+expectedfilename);
							} catch (Exception e) {
								test_steps.add("FAIL - File name missmacthed" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-11636'>"
							+ "Click here to open JIRA: NG-11636</a>"+e.toString());
							} catch(Error e) {
								test_steps.add("FAIL - File name missmacthed" + "<br>"
										+ "<a href='https://innroad.atlassian.net/browse/NG-11636'>"
										+ "Click here to open JIRA: NG-11636</a>"+e.toString());
							
								statusCode.set(0,"1");
								comments.set(0,"Due to existing issue NG-11636 system generated report get's downloaded and not the custom one");
								statusCode.set(1,"1");
								comments.set(1,"Due to existing issue NG-11636 system generated report get's downloaded and not the custom one");
					
							}
					
					} else if ((scenario.equalsIgnoreCase("List Report"))) {
						String expectedfilename = listReportHeading.replace(" ", "") + "_"
								+ Utility.getCurrentDate("yyyyMMdd", timeZone);
						try {
							Assert.assertTrue(fileName.contains(expectedfilename),
									"Failed : '" + reportType + "' File Name missmatched");
							test_steps.add("Sucess - verify file name" + "<br>"
									+ "<a href='https://innroad.atlassian.net/browse/NG-11639'>" + "Click here to open JIRA: NG-11639</a>");
							app_logs.info("Verified Report Name");
							statusCode.set(0,"1");
							comments.set(0,"Report get downloaded: "+expectedfilename);
							
						} catch (Exception e) {
							test_steps.add("FAIL - File name missmatched" + "<br>"
									+ "<a href='https://innroad.atlassian.net/browse/NG-11639'>"
									+ "Click here to open JIRA: NG-11639</a>"+e.toString());
							statusCode.set(0,"5");
							comments.set(0,"Report not get downloaded: "+expectedfilename);
						} catch(Error e) {
							test_steps.add("FAIL - File name missmatched" + "<br>"
									+ "<a href='https://innroad.atlassian.net/browse/NG-11639'>"
									+ "Click here to open JIRA: NG-11639</a>"+e.toString());
							statusCode.set(0,"5");
							comments.set(0,"Report not get downloaded: "+expectedfilename);
						}
					} else if ((scenario.equalsIgnoreCase("Mailing Details"))) {
						String expectedfilename = mailingListHeading.replace(" ", "") + "_"
								+ Utility.getCurrentDate("yyyyMMdd", timeZone);
						try {
							Assert.assertTrue(fileName.contains(expectedfilename),
									"Failed : '" + reportType + "' File Name missmatched");
							test_steps.add("Sucess - verify file name" + "<br>"
									+ "<a href='https://innroad.atlassian.net/browse/NG-11639'>" + "Click here to open JIRA: NG-11639</a>");
							
							app_logs.info("Verified Report Name");
							statusCode.set(0,"1");
							comments.set(0,"Verify the contents in generated report with heading: ");
							
							
						} catch (Exception e) {
							test_steps.add("FAIL - File name missmacthed" + "<br>"
									+ "<a href='https://innroad.atlassian.net/browse/NG-11639'>"
									+ "Click here to open JIRA: NG-11639</a>"+e.toString());
						} catch(Error e) {
							test_steps.add("FAIL - File name missmacthed" + "<br>"
									+ "<a href='https://innroad.atlassian.net/browse/NG-11639'>"
									+ "Click here to open JIRA: NG-11639</a>"+e.toString());
							statusCode.set(0,"1");
							comments.set(0,"Name missmatched: "+expectedfilename);
						}
					}

					lines = Utility.checkPDFArray(fileName);
					System.out.println("line size : " + lines.length);
					if (scenario.equalsIgnoreCase("GS/GR")) {
						test_steps.add("=================== VERIFY GS/GR REPORT ======================");
						app_logs.info("=================== VERIFY GS/GR REPORT ======================");
						map.put("Heading", headingText);
						report.verifyReportContent(driver, lines, map, test_steps);
						test_steps.add("Failed - Content verified" + "<br>"
								+ "<a href='https://innroad.atlassian.net/browse/NG-6044'>" + "Click here to open JIRA: NG-6044</a>");
						//test_steps.add("Verify the issue NG-6044 is working fine");
					} else if (scenario.equalsIgnoreCase("List Report")) {
						test_steps.add("=================== VERIFY LIST REPORT ======================");
						app_logs.info("=================== VERIFY LIST REPORT ======================");
						map.put("listReportHeading", listReportHeading);
						report.verifyReportContent(driver, lines, map, test_steps);
						//test_steps.add("Verify the issues NG-11639, NG-11636 are working fine");
						
						test_steps.add("Success - Content verified" + "<br>"
								+ "<a href='https://innroad.atlassian.net/browse/NG-11639'>" + "Click here to open JIRA: NG-11639</a>"
										+ "<a href='https://innroad.atlassian.net/browse/NG-11636'>"
								+"Click here to open JIRA: NG-11636</a>");
						
						//comments.add("File content verified successfully: ");
					} else if (scenario.equalsIgnoreCase("Mailing Details")) {
						test_steps.add("=================== VERIFY MAILING REPORT ======================");
						app_logs.info("=================== VERIFY MAILING REPORT ======================");
						map.put("mailingListHeading", mailingListHeading);
						report.verifyReportContent(driver, lines, map, test_steps);
						//test_steps.add("Verify the issues NG-8820,NG-3394 are working fine");
						test_steps.add("Success - Content verified" + "<br>"
								+ "<a href='https://innroad.atlassian.net/browse/NG-8820'>" + "Click here to open JIRA: NG-8820</a>"
										+ "<a href='https://innroad.atlassian.net/browse/NG-3394'>"
								+"Click here to open JIRA: NG-3394</a>");
						
					}

					Utility.closeTabsExcept(driver, 1);
					test_steps.add("Close report's tab and switch to first tab");
					app_logs.info("Close report's tab and switch to first tab");

				} else {
					test_steps.add("Failed: <b>" + reportType.toUpperCase() + "</b> Report is not displaying");
				}
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			
			driver.manage().deleteAllCookies();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Report", testName, "Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Report", testName, "Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyReportGuestHistory", envLoginExcel);
	}

	@AfterMethod(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
