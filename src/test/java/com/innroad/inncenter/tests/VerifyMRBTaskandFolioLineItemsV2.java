/*
 * Added By Riddhi
 * '907847|907906|908054|907865|907994
 * 
 */
package com.innroad.inncenter.tests;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.elasticsearch.cluster.ClusterState.Custom;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.HistoryInfo;
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.ReservationV2Search;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;


public class VerifyMRBTaskandFolioLineItemsV2 extends TestCore
{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	Folio oldFolioObj = new Folio();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account accObj = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 resV2 = new ReservationV2();
	ReservationV2Search resSearch = new ReservationV2Search();
	Properties properties = new Properties();
	RoomClass roomClass = new RoomClass();
	Reports report = new Reports();
	Rate rate1 = new Rate();
	Policies policies = new Policies();
	MerchantServices msObj = new MerchantServices();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	CPReservationPage resV1 = new CPReservationPage();
	DerivedRate derivedRate = new DerivedRate();
	RatePackage ratePackage = new RatePackage();
	FolioNew folioObj = new FolioNew();
	
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	ArrayList<String> sessionEndDate = new ArrayList<>();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	ArrayList<String> roomClassesNames = new ArrayList<>();
	ArrayList<String> roomClassfinalRoomNos= new ArrayList<String>();
	
	String checkInDate="", checkOutDate = "", guestFirstName="", guestLastName="", seasonStartDate="",seasonEndDate="",policyDesc ="";
	String  propertyName = null, roomClassAbbs = null, productName = null,calculatedAmountofProductForThisReservation = "",
			roomClassNameWithoutNum = null, roomClassAvvWithoutNum = null, rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservationNo = null, balance = null,policyname=null,policyNameWithoutNum=null,
			tripTaxes = null, tripTotal = null, depositAmount = null, date=null,expPolicyFee="", roomChargeAmount="", gcNumber =null;
	String source = "innCenter";
	public void checkRunMode() 
	{
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if(!Utility.isExecutable(test_name, excelRiddhi)) throw new
			SkipException("Skiping the test - " + test_name);
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyMRBTaskandFolioLineV2", excelRiddhi);
	}
	
	private void getMapData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}
	@Test(dataProvider = "getData")	
	public void verifyMRBTaskandFolioLineItemsV2(String TestCaseId, String TestCaseName, 
			String ratePlanName, String roomClassName, String isTask, String TaskCategory,
			String TaskType, String TaskDetails, String TaskRemarks, String TaskDueon, String TaskAssignee,
			String TaskStatus, String updatedTaskStatus, 
			String category, String perUnit, String adjustLineItemCategory, String adjustLineCategoryAmount) throws Exception
	{
		Utility.initializeTestCase(TestCaseId, caseId, statusCode, comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";
		// String test_name = test_name;
		String productDetailsDELIM = Utility.DELIM + Utility.DELIM;
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		ratePlanNames = Utility.splitInputData(ratePlanName);
		roomClassesNames = Utility.splitInputData(roomClassName);
		try {
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			if (guestFirstName.split("\\|").length > 1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			} else {
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
			}
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(excelRiddhi, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			propertyName = properties.getProperty(driver, test_steps);			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Login to Reservation V2", 
					"Failed to Login to Reservation V2", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);		}
		 catch (Error e) {
			 Utility.catchError(driver, e, "Failed to Login to Reservation V2", 
						"Failed to Login to Reservation V2", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
		 }
	
		String channelName = "innCenter";
		System.out.println("channelName : " + channelName);
		System.out.println(System.getProperty("user.dir"));
		HashMap<String, String> ratePlanData = null;
		HashMap<String, String> roomClassData = null;
		HashMap<String, String> packageData = null;
		try 
		{
			roomClassData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNewRoomClass");
			ratePlanData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNewRoomClass");		;			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//Get Room Class Data From Excel
		String roomClass = roomClassData.get("roomClassName");
		String roomClassAB = roomClassData.get("roomClassAB");
		String policy = roomClassData.get("policy");
		String sortOrder = roomClassData.get("sortOrder");
		String maxAdult = roomClassData.get("maxAdults");
		String maxPerson = roomClassData.get("maxPerson");
		String details = roomClassData.get("details");
		String roomQuant = roomClassData.get("roomQuant");
		String roomName = roomClassData.get("roomName");
		String roomSortNo = roomClassData.get("roomSortNo");
		String statinId = roomClassData.get("statinId");
		String zone = roomClassData.get("zone");
		
		String salutation = "Ms.";
		String guestFirstName = "MRB_Task|MRB_Task";
		String guestLastName = Utility.generateRandomNumberWithGivenNumberOfDigits(4)+"|"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		String phoneNumber = "9876543444";
		String altenativePhone = "9876543211";
		String email = "innroadautomation@innroad.com";
		String account = "";
		String accountType = "";
		String address1 = "Address1 - Lane1";
		String address2 = "Lane2";
		String address3 = "Lane3";
		String city = "New York";
		String country = "United States";
		String state = "New York";
		String postalCode = "5432";
		boolean isGuesProfile = false;
		String PaymentMethod = "";
		String CardNumber = "";
		String NameOnCard = "";
		String ExpiryDate = "";
		String referral = "Walk In";
		String marketSegment = "GDS";
		int additionalGuests = 0;
		String roomNumber = "";
		boolean quote = false;
		String noteType = "";
		String noteSubject = "";
		String noteDescription = "";
		String taskCategory = "";
		String taskType = "";
		String taskDetails = "";
		String taskRemarks = "";
		String taskDueOn = "";
		String taskAssignee = "";
		String taskStatus = "";
		String accountName = "";
		String accountFirstName = "";
		String accountLastName = "";
		boolean isTaxExempt = true;
		String taxExemptID = "TAX_ITAUTO";
		
		String CheckInDate = Utility.getCurrentDate("dd/MM/yyyy")+"|"+Utility.getCurrentDate("dd/MM/yyyy");
		String CheckOutDate = Utility.getCustomDate(seasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 2)+"|"+Utility.getCustomDate(seasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 2);
		String adult = "1"+"|2";
		String children = "1"+"|1";
		String RatePlanName4MRB = ratePlanName + "|" + ratePlanName;
		String roomClassName4MRB = roomClassName +"|" + roomClassName;
		HashMap<String, String> data = null;		
		ArrayList<StayInfo> stayInfo = new ArrayList<StayInfo>();
		String roomNo = "";
		//Create MRB Reservation
		try
		{
			//navigation.ReservationV2_Backward(driver);
			data = resV2.createReservationwithACF(driver, test_steps, "From Reservations page",
					CheckInDate, CheckOutDate, adult, children, RatePlanName4MRB, "", roomClassName4MRB,
				  roomClassAB, salutation, guestFirstName, guestLastName, phoneNumber,
				  altenativePhone, email, address1, address2, address3, city, country, state,
				  postalCode, isGuesProfile, marketSegment, referral, PaymentMethod,
				  CardNumber, NameOnCard, ExpiryDate, additionalGuests, roomNumber, quote,
				  noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
				  taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
				  accountFirstName, accountLastName, true, "TAX_IT",false,false,false,false);
			reservationNo = data.get("ReservationNumber");
			test_steps.add("========================MRB Reservation is created Successfully - Reservation Number : ========================" + data.get("ReservationNumber"));
			app_logs.info("========================MRB Reservation is created Successfully - Reservation Number : ========================" + data.get("ReservationNumber"));
			resV2.closeAllOpenedReservations(driver);
			//String mrbReservationNumber = data.get("ReservationNumber");
			//resV2.basicSearch_WithReservationNo(driver, "20323342", true);
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
					"Failed to create Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
				"Failed to Create Package Rate Plan", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Add Task For MRB Reservation for specific room & Verify it - 907847
		try
		{	
			test_steps.add("========================Adding Tasks to MRB Reservation for Individual Rooms========================");
			app_logs.info("========================Adding Tasks to MRB Reservation for Individual Rooms ========================");
			resV2.basicSearch_WithReservationNo(driver, reservationNo, true);
			stayInfo = resV2.getStayInfoDetailMRB(driver);
			Wait.wait25Second();
			for(int i=0;i<2;i++)
			{
				roomNo = stayInfo.get(i).getSI_ROOM_NUMBER();
				String selectedRoom = roomClassName + ": "+ roomNo;
				test_steps.add("Selected Room : "+selectedRoom);
				app_logs.info("Selected Room : "+selectedRoom);
				resV2.AddTaskForRooms(driver, test_steps, isTask, TaskCategory, TaskType, TaskDetails, TaskRemarks, TaskDueon, 
						TaskAssignee, TaskStatus, true, selectedRoom);				
				String roomAbbnRoomNo = roomClassAB+": "+roomNo;
				test_steps.add("Room Abb + Room No - "+roomAbbnRoomNo);
				app_logs.info("Room Abb + Room No -"+roomAbbnRoomNo);				
				resV2.verifyRoomForCreatedTask(driver, roomAbbnRoomNo, test_steps);
				resV2.verifyCreatedTask(driver, TaskType, TaskDetails, TaskStatus, test_steps);					
				resV2.switchHistoryTab(driver, test_steps);
				resV2.enterTextToSearchHistory(driver, "Added task for "+TaskType+", Due On", 
						true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
				driver.navigate().refresh();
				Wait.wait15Second();
				resV2.switchDetailTab(driver, test_steps);
				Wait.wait25Second();
			}	
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Add & Verify Task FOr MRB", 
					"Failed to Add & Verify Task FOr MRB", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Add & Verify Task FOr MRB", 
				"Failed to Verify Add & Verify Task FOr MRB", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Verify History Logs for Updating any tasks - 907906
		try
		{
			test_steps.add("========================Verify History Logs after updating tasks========================");
			app_logs.info("========================Verify History Logs after updating tasks========================");			
			resV2.editTask(driver, test_steps);
			resV2.updateTask(driver, 1, "Done");
			driver.navigate().refresh();
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Update & Verify Task", 
					"Failed to Update & Verify Task", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Update & Verify Task", 
				"Failed to Verify Add & Verify Task FOr MRB", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Navigate to Secondary Folio & Add FOlio Line Items - 908054
		try
		{			
			test_steps.add("===============Navigating to Secondary Folio, Add and Verify Line Items=================");
			app_logs.info("===============Navigating to Secondary Folio, Add and Verify Line Items=================");
			resV2.click_Folio(driver, test_steps);			
			roomNo = stayInfo.get(1).getSI_ROOM_NUMBER();
			String folioName="Guest Folio For "+roomClassAB + " : "+roomNo;
			app_logs.info("FolioName : " + folioName);
			folioObj.moveToFolio(driver, test_steps, folioName);
			folioObj.addFolioLineItem(driver, test_steps, category, perUnit);
			String amount = Utility.convertDecimalFormat(perUnit);
			amount = "$" + amount + "";
			folioObj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), category, amount, "", "", "");					
			resV2.switchHistoryTab(driver, test_steps);
			//Created Parking item Nightly Parking for Guest Folio			
			resV2.enterTextToSearchHistory(driver, "Created "+category+" item "+category+" for Guest Folio", 
				true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Add & Verify Folio Line Items in Secondary Folio", 
					"Failed to Add & Verify Folio Line Items in Secondary Folio", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Add & Verify Folio Line Items in Secondary Folio", 
				"Failed to Add & Verify Folio Line Items in Secondary Folio", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Adjust Line Item and Verify
		try
		{
			test_steps.add("===============Add Folio Line Items & Adjsuting Line Items=================");
			app_logs.info("===============Add Folio Line Items & Adjusting Line Items=================");
			driver.navigate().refresh();
			resV2.click_Folio(driver, test_steps);			
			folioObj.addFolioLineItem(driver, test_steps, adjustLineItemCategory, adjustLineCategoryAmount);
			test_steps.add("===============Adjsuting Line Items=================");
			app_logs.info("===============Adjusting Line Items=================");
			String firstAmountofCategory = Utility.convertDecimalFormat(perUnit);
			firstAmountofCategory = "$" + firstAmountofCategory+"";
			folioObj.clickFolioDesc(driver, "Pending", adjustLineItemCategory, test_steps);			
			folioObj.adjustFolioLineItems(driver, test_steps, adjustLineItemCategory, adjustLineCategoryAmount);
			double adjustAmount = Double.parseDouble(adjustLineCategoryAmount) * 2;
			String adjustAmnt = Double.toString(adjustAmount);
			String amount = Utility.convertDecimalFormat(adjustAmnt);
			amount = "$" + amount + "";
			folioObj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), adjustLineItemCategory, adjustAmnt, "", "", "");
			resV2.switchHistoryTab(driver, test_steps);
			String historyText = "Changed "+adjustLineItemCategory+" item "+adjustLineItemCategory+" cost "+firstAmountofCategory+" to "+adjustAmnt;
			resV2.enterTextToSearchHistory(driver, historyText, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);						
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Adjust Folio Line Items", 
					"Failed to Adjust Folio Line Items", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Adjust Folio Line Items", 
				"Failed to Adjust Folio Line Items", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Update Test Rail
		try 
		{
			ArrayList<String> list = Utility.convertTokenToArrayList(TestCaseId, "\\|");
			caseId = new ArrayList<String>();
			statusCode = new ArrayList<String>();
			comments = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				caseId.add(list.get(i));
				statusCode.add("1");
				comments.add("PASS : " + this.getClass().getSimpleName().trim());
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
					TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		driver.quit();
	}
}
