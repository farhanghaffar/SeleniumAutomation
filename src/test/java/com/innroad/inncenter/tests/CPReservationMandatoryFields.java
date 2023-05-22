package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CPReservationMandatoryFields extends TestCore{

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	Navigation navigation = new Navigation();	
	String testName = this.getClass().getSimpleName().trim();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
		
	}
	
	@Test(groups = "CP")
	public void cPReservationMandatoryFields() throws ParseException {
		
		String testCaseID="848365|848366";
		if(Utility.getResultForCase(driver, testCaseID)) {
		//test_name="Validation message for Guest information section during edit reservation.";
		test_description = "Validation message for Guest information section during edit reservation.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848365' target='_blank'>"
				+ "Click here to open Test Case: 848365</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848366' target='_blank'>"
				+ "Click here to open Test Case: 848366</a>";
		//testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String checkInDate=null, checkOutDate=null;
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		CPReservationPage cpRes = new CPReservationPage();
		Utility.initializeTestCase("848365|848366", Utility.testId, Utility.statusCode, Utility.comments, "");
		// Login
				try {
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						//Utility.reTry.replace(testName, 1);
						Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
					}

					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					checkInDate = checkInDates.get(0);
					checkOutDate = checkOutDates.get(0);
					app_logs.info(checkInDate);
					app_logs.info(checkOutDate);
					driver = getDriver();
					login_CP(driver);
					testSteps.add("Logged into the application");
					app_logs.info("Logged into the application");					
				} catch (Exception e) {
					Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, testSteps);
				}
				
				try {					
					testSteps.add("<b>======Validation message for Guest information section during create reservation.======</b>");					
					cpRes.click_NewReservation(driver, testSteps);
					cpRes.select_CheckInDate(driver, testSteps, checkInDate);
					cpRes.select_CheckoutDate(driver, testSteps, checkOutDate);
					cpRes.clickOnFindRooms(driver, testSteps);
					cpRes.selectRoomClass(driver, testSteps);
					cpRes.clickNext(driver, testSteps);
					cpRes.verifySpinerLoading(driver);
					cpRes.verifyCPMandaoryField(driver, testSteps, true);					
					/*testSteps.add(
							"Validation message for Guest information section during create reservation."
									+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848365' target='_blank'>"
									+ "Click here to open TestRail: 848365</a><br>");			
					  Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Validation message for Guest information section during create reservation."); 
					  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);						
					  testSteps.add(
								"Validation message for Guest information section during create reservation."
										+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848366' target='_blank'>"
										+ "Click here to open TestRail: 848366</a><br>");			
					Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Validation message for Guest information section during create reservation."); 
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);									*/  
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify mandatory fields");
					}
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Mandatory field", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Mandatory field", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
