package com.innroad.inncenter.tests;
import com.innroad.inncenter.testcore.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.ListManagement;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.webelements.Elements_ReservationV2;

public class VerifyEditPaymentMethodForReservation extends TestCore
{
	public WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	String modifyReferralField = "";
	String modifyMarketSegment = "";
	
	Navigation navigation = new Navigation();
	ReservationV2 resV2 = new ReservationV2();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	ListManagement listManagement = new ListManagement();
	ReservationV2 res = new ReservationV2();
	Policies pol = new Policies();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Elements_NewRoomClassPage roomClassEle = new Elements_NewRoomClassPage(driver);
	NewRoomClassesV2 roomClassObj = new NewRoomClassesV2();
	Elements_ReservationV2 eleResv2 = new Elements_ReservationV2(driver);
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() 
	{
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if(!Utility.isExecutable(test_name, excelRiddhi)) throw new
			SkipException("Skiping the test - " + test_name);
	}
	 
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the Sheet Name provided
		return Utility.getData("VerifyEditPaymentMethodForReser", excelRiddhi);
	}
	
	private void getMapData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}
	
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	
	@Test(dataProvider = "getData")
	public void verifyEditPaymentMethodForReservation(String TestCaseId, String TestCaseName, String RatePlanName,String marketSegment, 
			String Referral, String modifyPaymentMethod) throws Exception 
	{

		Utility.initializeTestCase(TestCaseId, caseId, statusCode, comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		
		try 
		{
			if (!Utility.insertTestName.containsKey(test_name)) 
			{
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else 
			{
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(excelRiddhi, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} 
		catch (Exception e) 
		{
			if (Utility.reTry.get(test_name) == Utility.count) 
			{
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} 
			else 
			{
				Assert.assertTrue(false);
			}

		} 
		catch (Error e) 
		{
			if (Utility.reTry.get(test_name) == Utility.count) 
			{
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		String channelName = "innCenter";
		System.out.println("channelName : " + channelName);
		System.out.println(System.getProperty("user.dir"));
		HashMap<String, String> ratePlanData = null;
		HashMap<String, String> roomClassData = null;
		
		try 
		{
			roomClassData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNewRoomClass");
			ratePlanData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNightlyRatePlanV2");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Get Room Class Fields from the excel sheet via roomclassData, and assign them to local variables
		String roomClassName = roomClassData.get("roomClassName");
		String roomClassAB = roomClassData.get("roomClassAB");
		String policy = roomClassData.get("policy");
		String sortOrder = roomClassData.get("sortOrder");
		String maxAdults = roomClassData.get("maxAdults");
		String maxPerson = roomClassData.get("maxPerson");
		String details = roomClassData.get("details");
		String roomQuant = roomClassData.get("roomQuant");
		String roomName = roomClassData.get("roomName");
		String roomSortNo = roomClassData.get("roomSortNo");
		String statinId = roomClassData.get("statinId");
		String zone = roomClassData.get("zone");

		//Get Rate Plan Fields From excel sheet via Rateplandata arraylist and assign them to local variables
		//String ratePlanName = ratePlanData.get("ratePlanName");
		String FolioDisplayName = ratePlanData.get("FolioDisplayName");
		String Description = ratePlanData.get("Description");
		String Channels = ratePlanData.get("Channels");
		String RoomClasses = roomClassName;
		String isRatePlanRestrictionReq = ratePlanData.get("roomClassName");
		String RestrictionType = ratePlanData.get("RestrictionType");
		String isMinStay = ratePlanData.get("isMinStay");
		String MinNights = ratePlanData.get("MinNights");
		String isMaxStay = ratePlanData.get("isMaxStay");
		String MaxNights = ratePlanData.get("MaxNights");
		String isMoreThanDaysReq = ratePlanData.get("isMoreThanDaysReq");
		String MoreThanDaysCount = ratePlanData.get("MoreThanDaysCount");
		String isWithInDaysReq = ratePlanData.get("isWithInDaysReq");
		String WithInDaysCount = ratePlanData.get("WithInDaysCount");
		String PromoCode = ratePlanData.get("PromoCode");
		String isPolicesReq = ratePlanData.get("isPolicesReq");
		String PoliciesType = ratePlanData.get("PoliciesType");
		String PoliciesName = ratePlanData.get("PoliciesName");
		String SeasonName = ratePlanData.get("SeasonName");
		String SeasonStartDate = Utility.getCurrentDate("dd/MM/yyyy");// ratePlanData.get("SeasonStartDate");
		String SeasonEndDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 60);// ratePlanData.get("SeasonEndDate");
		String isMonDay = ratePlanData.get("isMonDay");
		String isTueDay = ratePlanData.get("isTueDay");
		String isWednesDay = ratePlanData.get("isWednesDay");
		String isThursDay = ratePlanData.get("isThursDay");
		String isFriday = ratePlanData.get("isFriday");
		String isSaturDay = ratePlanData.get("isSaturDay");
		String isSunDay = ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String RatePerNight = ratePlanData.get("RatePerNight");
		String MaxAdults = ratePlanData.get("MaxAdults");
		String MaxPerson = ratePlanData.get("MaxPerson");
		String AdditionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSerasonLevelRules = ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass = ratePlanData.get("isAssignRulesByRoomClass");
		String SeasonRuleSpecificRoomClasses = ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String SeasonRuleType = ratePlanData.get("SeasonRuleType");
		String SeasonRuleMinStayValue = ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday = ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday = ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday = ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday = ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday = ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday = ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday = ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies = ratePlanData.get("isSeasonPolicies");
		String SeasonPolicyType = ratePlanData.get("SeasonPolicyType");
		String SeasonPolicyValues = ratePlanData.get("SeasonPolicyValues");
		String MaxPersons = ratePlanData.get("MaxPersons");
		ArrayList<String> availableReferrals = new ArrayList<String>();
		ArrayList<String> availableMarketSegments = new ArrayList<String>();
		
		try 
		  { 
			  	navigation.ReservationV2_Backward(driver);
				
			  	app_logs.info("==========Navigate to Setup==========");
			  	test_steps.add("==========Navigate to Setup=========="); 
			  	
			  	navigation.Setup(driver);
			  	
			  	app_logs.info("==========Navigate to ListManagement==========");
			  	test_steps.add("==========Navigate to ListManagement==========");
			  
			  	navigation.ListManagemnet(driver);
			  	
			  	app_logs.info("======Get All available System Referrals=======");
				test_steps.add("======Get All available System Referrals=======");
				//Get list of all available "Referrals" (System+Custom Referrals)
				availableReferrals   = listManagement.getActiveMarketSegmentsNames(driver);
				
				if(availableReferrals.size()>0)
				{ 
					Referral = availableReferrals.get(0).trim();
				}
				else
				{
					listManagement.SelectFilter(driver, "Referral", test_steps);
					if(!listManagement.searchListManagementItemByName(driver,Referral, "Referral", test_steps)) 
					{
						listManagement.NewItemCreation(driver, Referral,Referral, test_steps); 
						listManagement.SaveButtonClick(driver,test_steps); 
					}
				}
			  	
			  	//Navigate to List Management -> Market Segment HyperLink
			  	listManagement.ClickOnListManagementSubSection("Market Segment",driver,test_steps);
			  	app_logs.info("======Get All avalable System Market Segments=======");
				test_steps.add("======Get All avalable System Market Segments=======");
				
				//Get list of all available "Market Segment" (System+Custom Market Segments)
				
				availableMarketSegments = listManagement.getActiveMarketSegmentsNames(driver);

				if(availableMarketSegments.size() > 0)
				{
					marketSegment = availableMarketSegments.get(0).trim();
				}
				else
				{
					listManagement.SelectFilter(driver,"Market Segment", test_steps);
					if(!listManagement.searchListManagementItemByName(driver,marketSegment, "Market Segment", test_steps)) 
					{
						listManagement.NewItemCreation(driver, marketSegment,marketSegment, test_steps); 
						listManagement.SaveButtonClick(driver,test_steps); 
					}
				}
				
				navigation.Reservation_Backward(driver);
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
			} 
			catch (Error e) 
			{
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
		String salutation = "Ms.";
		String guestFirstName = "Riddhi";
		String guestLastName = "S_"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		String phoneNumber = "9876543444";
		String altenativePhone = "9876543211";
		String email = "innroadautomation@innroad.com";
		String account = "";
		String accountType = "Corporate/Member Accounts";
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
		//String referral = "Walk In";
		//String marketSegment = "GDS";
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
		
		String CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
		String CheckOutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 2);
		String adult = "1";
		String children = "1";
		int noOfPaymentMethods = modifyPaymentMethod.split("\\|").length;
		
		HashMap<String, String> data = null;
		
		try 
		{
			navigation.ReservationV2_Backward(driver);
			String reservationNo;
			
			data = res.createReservationwithACF(driver, test_steps, "From Reservations page",
			  CheckInDate, CheckOutDate, adult, children, RatePlanName, "", roomClassName,
			  roomClassAB, salutation, guestFirstName, guestLastName, phoneNumber,
			  altenativePhone, email, address1, address2, address3, city, country, state,
			  postalCode, isGuesProfile, marketSegment, Referral, PaymentMethod,
			  CardNumber, NameOnCard, ExpiryDate, additionalGuests, roomNumber, quote,
			  noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
			  taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
			  accountFirstName, accountLastName, true, "TAX_IT",false,false,false,false);			 			 
		}
		catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try
		{
			String ReservationNumber = data.get("ReservationNumber");
			resV2.closeAllOpenedReservations(driver);
			
			//Navigate to Dashboard >> Search by reservation no >> Open existing reservation
			resV2.basicSearch_WithReservationNo(driver, ReservationNumber, true);
			
			
			if(noOfPaymentMethods > 1)
			{
				for(int i=0;i<noOfPaymentMethods;i++)
				{
					app_logs.info("======= Modifying Reservation with Payment Method =======" + modifyPaymentMethod.split("\\|")[i]);
					test_steps.add("======Modifying Reservation with Payment Method =======" + modifyPaymentMethod.split("\\|")[i]);
					
					if(modifyPaymentMethod.split("\\|")[i].equals("Cash"))
					{
						modifyPaymentInfo(driver, modifyPaymentMethod.split("\\|")[i], CardNumber, NameOnCard, ExpiryDate, accountName);
					}
					else if(modifyPaymentMethod.split("\\|")[i].equals("Visa"))
					{
						modifyPaymentInfo(driver, modifyPaymentMethod.split("\\|")[i], "4242424242424242", "Riddhi-automated-card-name", "10/32", accountName);
					}
					else if(modifyPaymentMethod.split("\\|")[i].equals("MC"))
					{
						modifyPaymentInfo(driver, modifyPaymentMethod.split("\\|")[i], "5454545454545454", "Riddhi-automated-card-name", "10/32", accountName);
					}		
				}	
			}
			else
			{
				modifyPaymentInfo(driver, "Cash", CardNumber, NameOnCard, ExpiryDate, accountName);
			}
			
			
			resV2.clickOnEditPaymentInfoIcon(driver,test_steps);
			resV2.modifyBillingAddressDetailsinPaymentInfo(driver, test_steps, address1, address2, address3, city,  country, state, postalCode);
			
			//click on Save Payment Info Button
			resV2.clickOnSavePaymentInfo(driver, test_steps);
			
			//Close All Opened Reservation
			//resV2.closeAllOpenedReservations(driver);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) 
			{
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} 
			else 
			{
				Assert.assertTrue(false);
			}
		} 
		catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
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
		} catch (Exception e) 
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
	
	public void modifyPaymentInfo(WebDriver driver, String modifyPaymentMethod, String CardNumber, String NameOnCard, 
			String ExpiryDate, String accountName) throws InterruptedException, IOException, Exception
	{
		ReservationV2 resV2 = new ReservationV2();
		
		resV2.clickOnEditPaymentInfoIcon(driver,test_steps);
		
		//modify Payment Method to Cash
		resV2.modifyPaymentInfo(driver, modifyPaymentMethod, CardNumber, NameOnCard, ExpiryDate, accountName, test_steps);
				
		//click on Save Payment Info Button
		resV2.clickOnSavePaymentInfo(driver, test_steps);
	}
	
	//@AfterClass(alwaysRun=true)
	public void closeDriver() 
	{
		driver.quit();
	}
}
