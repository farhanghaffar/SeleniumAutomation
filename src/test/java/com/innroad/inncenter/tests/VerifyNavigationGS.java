package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyNavigationGS extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	String testName = null,property=null;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(groups = "GuestServices")
	public void verifyNavigationGS() throws ParseException {		
		String testCaseID="852584|848760";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "User should be able to navigate to other Inncenter pages from Guest Services after selecting a property from Property Selector";
		test_description = "GS-Room Maintenance-Making a Room Out Of Order And Verify TapeChart , RoomStatus and Inventory<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/852584' target='_blank'>"
				+ "Click here to open TestRail: 852584</a><br>";				
		test_catagory = "GS Navigation";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("852584|848760", Utility.testId, Utility.statusCode,Utility.comments,"");
		Navigation navigation = new Navigation();
		ReservationHomePage homePage = new ReservationHomePage();
		RoomStatus roomstatus = new RoomStatus();
		String propertyName= "Payments Property-2";
		// Login
				try {
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						//Utility.reTry.replace(testName, 1);
						Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
					}							
					driver = getDriver();
					//login_GS(driver);
					loginWPI(driver);
					test_steps.add("Logged into the application");
					app_logs.info("Logged into the application");			
			} catch (Exception e) {
				Utility.catchException(driver, e, "verify  Login", "Login", "Login", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "verify  Login", "Login", "Login", testName, test_description,
						test_catagory, test_steps);
			}
				
				try {
					test_steps.add("<b>======Navigation after Guest Services======</b>");
					test_steps.add("<b>===== Getting Property =====</b>");
					app_logs.info("===== Getting Property=====");
					property=homePage.getReportsProperty(driver, test_steps);
					test_steps.add("<b>Property Name : </b>" + property);
					app_logs.info("Property Name : " + property);
					navigation.navigateGuestservice(driver);					
					test_steps.add("Navigate to Guest Services");
					app_logs.info("Navigate to Guest Services");
					
					test_steps.add("<b>===== Selecting Property "+propertyName+"=====</b>");
					app_logs.info("===== Selecting Property "+propertyName+"=====");
					navigation.selectPropertyAfterGuestServices(driver, propertyName);
					Wait.waitforPageLoad(50, driver);
					test_steps.add("<b>Select Property  : </b>" + propertyName);
					app_logs.info("Select Property  : " + propertyName);
					
					navigation.navigateToReservationFromGuestServices(driver, test_steps);
					navigation.navigateGuestservice(driver);					
					test_steps.add("Navigate to Guest Services");
					app_logs.info("Navigate to Guest Services");
					roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
					roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);			
					navigation.navigateToReservationFromGuestServices(driver, test_steps);
					test_steps.add("<b>===== Selecting Property "+property+"=====</b>");
					app_logs.info("===== Selecting Property "+property+"=====");
					navigation.selectProperty(driver, property);
					Wait.waitforPageLoad(50, driver);
					test_steps.add("<b>Select Property  : </b>" + property);
					app_logs.info("Select Property  : " + property);
				/*	Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "verify navigation to change proeprty for guest services");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			*/
					
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Guest History");
					}
					Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
							Utility.comments, TestCore.TestRail_AssignToID);
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						
				}catch (Exception e) {
					Utility.catchException(driver, e, "verify  Navigation", "Navigation", "Navigation", testName,
							test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "verify  Navigation", "Navigation", "Navigation", testName, test_description,
							test_catagory, test_steps);
				}
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		
	}


}


