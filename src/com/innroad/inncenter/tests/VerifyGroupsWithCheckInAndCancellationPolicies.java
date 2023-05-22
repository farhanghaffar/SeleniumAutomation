package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
=======
>>>>>>> develop

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
<<<<<<< HEAD
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
=======
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Rate;
>>>>>>> develop
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationFolio;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;

public class VerifyGroupsWithCheckInAndCancellationPolicies extends TestCore {

	private WebDriver driver = null;

<<<<<<< HEAD
	

=======
>>>>>>> develop
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
<<<<<<< HEAD
=======
	ArrayList<String> checkInDates = new ArrayList<>();
    ArrayList<String> checkOutDates = new ArrayList<>();
>>>>>>> develop

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	public void login(String testName) {

		try {
			if (!Utility.insertTestName.containsKey(testName)) {

				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_Group(driver);
<<<<<<< HEAD
			login_CP(driver);
=======
>>>>>>> develop
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@Test(dataProvider = "getData", groups = { "groups" })
	public void verifyGroupsWithCheckInAndCancellationPolicies(String url, String clientcode, String username,
			String password, String RoomClassAbb, String RoomClassName, String maxAdults, String maxPersons,
			String roomQuantity, String RateName, String bedsCount, String BaseAmount, String AddtionalAdult,
			String AdditionalChild, String AssociateSession, String RatePolicy, String RateDescription,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String ChargeRoutingAllItem, String ChargeRoutingRoomChargeOnly, String BlockName, String RoomPerNight,
			String CHKINPolicyName, String CHKINPolicytype, String CHKINChargestype, String CHKINNumber,
			String PolicyText, String PolicyDesc, String CancellationPolicyName, String CancellationPolicyType,
			String CancellationAttribute1, String CancellationAttribute1_Value, String CancellationAttribute2,
			String CancellationAtttribute2_Value, String RackRate, String PaymentMethod, String CardNumber,
<<<<<<< HEAD
			String ExpiryDate, String arriveDate, String departDate, String ratePlanName, String channelName) throws InterruptedException, IOException {
=======
			String ExpiryDate,String checkInDate, String checkOutDate,String adults, String children, String rateplan, String Salutation, 
			String NameOnCard, String PaymentType) throws InterruptedException, IOException {
>>>>>>> develop

		String testName = "VerifyGroupsWithCheckInAndCancellationPolicies";
		String test_description = "Verify Groups With Check In And Cancellation Policies.<br>";
		// + "<a href='https://innroad.testrail.io/index.php?/cases/view/554181'
		// target='_blank'>"
		// + "Click here to open TestRail: C554181</a><br/>"
		// + "Verify account folio voiding a payment for a reservation line
		// item.<br>"
		// + "<a href='https://innroad.testrail.io/index.php?/cases/view/554182'
		// target='_blank'>"
		// + "Click here to open TestRail: C554182</a><br/>"
		// + "Verify account folio voiding a line item after completing the
		// payment .<br>"
		// + "<a href='https://innroad.testrail.io/index.php?/cases/view/554183'
		// target='_blank'>"
		// + "Click here to open TestRail: C554183</a><br/>"
		// + "Verify account folio voiding a reservation line item after
		// completing the payment.<br>"
		// + "<a href='https://innroad.testrail.io/index.php?/cases/view/554184'
		// target='_blank'>"
		// + "Click here to open TestRail: C554184</a><br/>";

		String test_catagory = "Groups";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		login(testName);

		Navigation nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		AdvGroups advGrp = new AdvGroups();
		RoomClass room_class = new RoomClass();
<<<<<<< HEAD
		CPReservationPage reservationPage= new CPReservationPage();
		Rate rate = new Rate();
		Policies Create_New_Policy = new Policies();
		ReservationFolio resFolio = new ReservationFolio();
		NewRoomClassesV2 newRoomClassV2= new NewRoomClassesV2();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		RatesGrid rateGrid = new RatesGrid();
		String defaultRatePlan=null,cancellationPolicyApplied=null, checkinPolicyApplied=null ;
		HashMap<String,String> rates= new HashMap<String, String>();
		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> seasonCancellationPolicy = new ArrayList<String>();	
		ArrayList<String> seasonCheckInPolicy = new ArrayList<String>();	
		ArrayList<HashMap<String, String>> getcancellationPoliciesData= new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> getcheckinPoliciesData= new ArrayList<HashMap<String, String>>();
		Policies policies = new Policies(); 
		HashMap<String, String> checkinPoliciesData= new HashMap<String, String>();
		String[] roomClassArray = null;

		try {

			if (!(Utility.validateInput(arriveDate))&&!(Utility.validateInput(departDate))){
				if (AccountFirstName.split("\\|").length>1) {
					for (int i = 0; i < AccountFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3),
								ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
					}
				}else
				{
					checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3),
							ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
				}
			}
			
			if (AccountFirstName.split("\\|").length>1) {
				arriveDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				departDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			}else {
				arriveDate = checkInDates.get(0);
				departDate = checkOutDates.get(0);
			}
			app_logs.info(arriveDate);
			app_logs.info(departDate);	
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to CheckINOut Date", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to CheckINOut Date", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
	/*	try {
			nav.inventory(driver);
			nav.ratesGrid(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanName);	
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.clickEditIcon(driver, test_steps);
			rateGrid.verifyRatePlaninEditMode(driver, test_steps, ratePlanName);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInDates.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.clickSeasonPolicies(driver, test_steps);
			test_steps.add("==================Get Policy from  Season==================");
			ArrayList<String> cancellation= new ArrayList<String>();
			ArrayList<String> checkin= new ArrayList<String>();
			
			roomClassArray = RoomClassName.split("\\|");
			app_logs.info(roomClassArray[0]);
			app_logs.info(roomClassArray[1]);
			
			if (AccountFirstName.split("\\|").length>1) {
				for(int i=0;i<roomClassArray.length;i++) {
					cancellation.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),roomClassArray[i],test_steps));
				checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),roomClassArray[i],test_steps));											
			}
			

		}else {
			cancellation.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),roomClassArray[0],test_steps));
			checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),roomClassArray[0],test_steps));					
		}
			for (String str : cancellation) {
				if (str != null) {
					seasonCancellationPolicy.add(str);
				}
			}
			for (String str : checkin) {
				if (str != null) {
					seasonCheckInPolicy.add(str);
				}
			}
			app_logs.info(seasonCancellationPolicy);
			app_logs.info(seasonCheckInPolicy);		
			
			if(!Utility.isEmptyStringArrayList(seasonCancellationPolicy)){
				test_steps.add("No Deposit Policy Associated with Season");
			}
			if(!Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
				test_steps.add("No Check-In Policy Associated with Season");
			}
			nightlyRate.closeSeason(driver, test_steps);
			rateGrid.closeRatePlan(driver, ratePlanName,test_steps );	
			nightlyRate.clickYesButton(driver, test_steps);
			
			/*
			 * defaultRatePlan= rateGrid.getDefaultRatePlan(driver, test_steps);
			 * app_logs.info(defaultRatePlan); rateGrid.expandRoomClass(driver,
			 * RoomClassName); rates=rateGrid.getRatesOfChannel(driver, arriveDate,
			 * departDate, RoomClassName, channelName); app_logs.info(rates);
			 * nav.navReservationFromRateGrid(driver);
			 */
		/*				}
		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Get Rate from Rate Grid", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Get Rate from Rate Grid", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try{
			if(Utility.isEmptyStringArrayList(seasonCancellationPolicy)){
				test_steps.add("=========Get  Data from Deposit Policy if policy exist on Season level=========");
				nav.clickPoliciesAfterRateGridTab(driver,test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);	
				if (AccountFirstName.split("\\|").length>1) {
					for(String str: seasonCancellationPolicy) {
						getcancellationPoliciesData.add(policies.getpoliciesData(driver, test_steps, ratesConfig.getProperty("depositPolicyText"), str));							
					}
				}else {
					getcancellationPoliciesData.add(policies.getpoliciesData(driver, test_steps, ratesConfig.getProperty("depositPolicyText"), seasonCancellationPolicy.get(0)));}
			}
			app_logs.info(getcancellationPoliciesData);
			if(Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
				test_steps.add("=========Get  Data from Check-In Policy if policy exist on Season level=========");
				nav.clickPoliciesAfterRateGridTab(driver,test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);		
				HashMap<String, String> valueHashmap= new HashMap<String, String>();
				if (AccountFirstName.split("\\|").length>1) {
					for(int i=0;i<seasonCheckInPolicy.size();i++) {
						getcheckinPoliciesData.add(policies.getpoliciesData(driver, test_steps, ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(i)));
						valueHashmap.put(seasonCheckInPolicy.get(i), getcheckinPoliciesData.get(i).get("AttrValue"));								
					}
					String  value=null;
					value=Utility.maxUsingCollectionsMax(valueHashmap);
					checkinPolicyApplied=Utility.getKeyOfValue(valueHashmap, value);
					checkinPoliciesData.put("AttrValue", value);
					app_logs.info(checkinPoliciesData);
				}else {
					checkinPoliciesData=policies.getpoliciesData(driver, test_steps, ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(0));
					checkinPolicyApplied=seasonCheckInPolicy.get(0);
				
				}
			}	
			app_logs.info(getcheckinPoliciesData);
		}catch (Exception e) {
			Utility.catchException(driver, e, "get value from  Policies", "Policies", "Policies", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "get value from Policies", "Policies", "Policies", testName,
					test_description, test_catagory, test_steps);

		}

*/		
		//// Room Class
	/*	try {
=======
		Rate rate = new Rate();
		Policies Create_New_Policy = new Policies();
		ReservationFolio resFolio = new ReservationFolio();
		CPReservationPage reservationPage = new CPReservationPage();
		
		try {
		    if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkOutDate))){

                if (AccountFirstName.split("\\|").length>1) {

                    for (int i = 0; i < AccountFirstName.split("\\|").length; i++) {

                        checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));

                        checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),

                                ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));

                    }

                }else

                {

                    checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));

                    checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),

                            ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));

                }

            }


            if (AccountFirstName.split("\\|").length>1) {

                checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);

                checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);

            }else {

                checkInDate = checkInDates.get(0);

                checkOutDate = checkOutDates.get(0);

            }


           app_logs.info(checkInDate);

            app_logs.info(checkOutDate);
		}catch(Exception e)
		{
			
		}

		//// Room Class
		try {
>>>>>>> develop
			nav.Setup(driver);
			nav.RoomClass(driver);
			RoomClassName = (RoomClassName + Utility.getTimeStamp()).replaceAll("_", "");
			RoomClassAbb = (RoomClassAbb + Utility.getTimeStamp()).replaceAll("_", "");
<<<<<<< HEAD
			
			newRoomClassV2.createRoomClassV2(driver, RoomClassName, RoomClassAbb, maxAdults, maxPersons, roomQuantity, test, test_steps);
			
			
				test_steps.add("Sccessfully Created New RoomClass " + RoomClassName + " Abb : " + RoomClassAbb);
=======

			app_logs.info("try");
			nav.NewRoomClass1(driver);

			getTest_Steps.clear();
			room_class.roomClassInfoNewPage(driver, RoomClassName, RoomClassAbb, bedsCount, maxAdults, maxPersons, roomQuantity, test);
			/*
			 * getTest_Steps = room_class.CreateRoomClass(driver, RoomClassName,
			 * RoomClassAbb, bedsCount, maxAdults, maxPersons, roomQuantity, test,
			 * getTest_Steps);
			 */
			test_steps.addAll(getTest_Steps);

			test_steps.add("Sccessfully Created New RoomClass " + RoomClassName + " Abb : " + RoomClassAbb);
>>>>>>> develop
			app_logs.info("Sccessfully Created New RoomClass" + RoomClassName + " Abb : " + RoomClassAbb);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Rate and Attach RoomClass
		try {

			nav.Inventory(driver);
			nav.Rates1(driver);
			RateName = (RateName + Utility.getTimeStamp()).replaceAll("_", "");
			String DisplayName = RateName;

			getTest_Steps = rate.CreateRate_Season(driver, RateName, maxAdults, maxPersons, BaseAmount, AddtionalAdult,
					AdditionalChild, DisplayName, RatePolicy, RateDescription, RoomClassName, AssociateSession,
					"Rack Rate", getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Enter all require details and save");
			app_logs.info("Enter all require details and save");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
<<<<<<< HEAD
		
		*/
		try {
			nav.inventory(driver);
			test_steps.add("Navigated to Inventory");
			app_logs.info("Navigated to Inventory");
			nav.policies(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 5);	
			/*
			 * Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
			 * Navigate.Menuitem_Policy_Text.click();
			 * Wait.explicit_wait_xpath(OR.Policy_Button, driver);
			 * test_steps.add("Navigated to Policies");
			 * app_logs.info("Navigated to Policies");
			 * 
			 */		
			
			
			
=======
		try {
			nav.Inventory(driver);
			test_steps.add("Navigated to Inventory");
			app_logs.info("Navigated to Inventory");
			Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
			Navigate.Menuitem_Policy_Text.click();
			Wait.explicit_wait_xpath(OR.Policy_Button, driver);
			test_steps.add("Navigated to Policies");
			app_logs.info("Navigated to Policies");
>>>>>>> develop
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to navigate to Policies page", testName, "PoliciesNavigation", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to navigate to Policies page", testName, "PoliciesNavigation", driver);

			} else {
				Assert.assertTrue(false);
			}
		}
		// Create Check-in Policy

		try {
<<<<<<< HEAD
			
			test_steps.add("========== Creating new policy for account ==========");
=======
>>>>>>> develop
			CHKINPolicyName = (CHKINPolicyName + Utility.getTimeStamp()).replaceAll("_", "");
			getTest_Steps.clear();
			getTest_Steps = Create_New_Policy.NewPolicybutton(driver, CHKINPolicytype, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			Create_New_Policy.Enter_Policy_Name(driver, CHKINPolicyName);
			Create_New_Policy.Enter_Checkin_Policy_Attributes(driver, CHKINChargestype, CHKINNumber);
			Create_New_Policy.Enter_Policy_Desc(driver, PolicyText, PolicyDesc);
			Create_New_Policy.Associate_Sources(driver);
			Create_New_Policy.AssociateSingle_Seasons(driver, AssociateSession);
			Create_New_Policy.AssociateSingle_RoomClass(driver, RoomClassName);
			Create_New_Policy.AssociateSingle_RatePlan(driver, RackRate);
			Create_New_Policy.Save_Policy(driver);
			Create_New_Policy.Close_Policy_Tab(driver);
			test_steps.add(CHKINPolicytype + "Policy '" + CHKINPolicyName + "' Created successfully");
			app_logs.info(CHKINPolicytype + "Policy '" + CHKINPolicyName + "' Created successfully");
			Create_New_Policy.Verify_Policy(driver, CHKINPolicyName);

			test_steps.add("Successfully Verify Policy " + CHKINPolicyName);
			app_logs.info("Successfully Verify Policy " + CHKINPolicyName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to save the Deposit policy", testName, "CreateDepositPolicy", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to save the  Deposit policy", testName, "CreateDepositPolicy", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		// Cancellation Policy
		try {
<<<<<<< HEAD
			
			  CancellationPolicyName = (CancellationPolicyName +
			  Utility.getTimeStamp()).replaceAll("_", ""); getTest_Steps.clear();
			  getTest_Steps = Create_New_Policy.NewPolicybutton(driver,
			  CancellationPolicyType, getTest_Steps); test_steps.addAll(getTest_Steps);
			  test_steps.add("Navigated to new Policy by clicking on new Policy button");
			  app_logs.info("Navigated to new Policy by clicking on new Policy button");
			  Create_New_Policy.Enter_Policy_Name(driver, CancellationPolicyName);
			  getTest_Steps.clear(); getTest_Steps =
			  Create_New_Policy.Cancellation_policy_Attributes(driver,
			  CancellationAttribute1, CancellationAttribute1_Value, CancellationAttribute2,
			  CancellationAtttribute2_Value, getTest_Steps);
			  test_steps.addAll(getTest_Steps); Create_New_Policy.Enter_Policy_Desc(driver,
			  PolicyText, PolicyDesc); Create_New_Policy.Associate_Sources(driver);
			  Create_New_Policy.AssociateSingle_Seasons(driver, AssociateSession);
			  Create_New_Policy.AssociateSingle_RoomClass(driver, RoomClassName);
			  Create_New_Policy.AssociateSingle_RatePlan(driver, RackRate);
			  Create_New_Policy.Save_Policy(driver);
			  Create_New_Policy.Close_Policy_Tab(driver);
			  test_steps.add("Successfully Create Policy " + CancellationPolicyName);
			  app_logs.info(" Successfully Create Policy " + CancellationPolicyName);
			  Create_New_Policy.Verify_Policy(driver, CancellationPolicyName);
			  test_steps.add("Successfully Verify Policy " + CancellationPolicyName);
			  app_logs.info(" Successfully Verify Policy " + CancellationPolicyName);
			 

=======
			CancellationPolicyName = (CancellationPolicyName + Utility.getTimeStamp()).replaceAll("_", "");
			getTest_Steps.clear();
			getTest_Steps = Create_New_Policy.NewPolicybutton(driver, CancellationPolicyType, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Navigated to new Policy by clicking on new Policy button");
			app_logs.info("Navigated to new Policy by clicking on new Policy button");
			Create_New_Policy.Enter_Policy_Name(driver, CancellationPolicyName);
			getTest_Steps.clear();
			getTest_Steps = Create_New_Policy.Cancellation_policy_Attributes(driver, CancellationAttribute1,
					CancellationAttribute1_Value, CancellationAttribute2, CancellationAtttribute2_Value, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			Create_New_Policy.Enter_Policy_Desc(driver, PolicyText, PolicyDesc);
			Create_New_Policy.Associate_Sources(driver);
			Create_New_Policy.AssociateSingle_Seasons(driver, AssociateSession);
			Create_New_Policy.AssociateSingle_RoomClass(driver, RoomClassName);
			Create_New_Policy.AssociateSingle_RatePlan(driver, RackRate);
			Create_New_Policy.Save_Policy(driver);
			Create_New_Policy.Close_Policy_Tab(driver);
			test_steps.add("Successfully Create Policy " + CancellationPolicyName);
			app_logs.info(" Successfully Create Policy " + CancellationPolicyName);
			Create_New_Policy.Verify_Policy(driver, CancellationPolicyName);
			test_steps.add("Successfully Verify Policy " + CancellationPolicyName);
			app_logs.info(" Successfully Verify Policy " + CancellationPolicyName);
>>>>>>> develop
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to save the Cancellation policy", testName, "CreateCancellationPolicy",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to save the Cancellation policy", testName, "CreateCancellationPolicy",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		}
		// Navigate to Groups
		try {
<<<<<<< HEAD
			//nav.Reservation(driver);
			reservationPage.navigateToReservationPage(driver);
=======
			nav.Reservation(driver);
>>>>>>> develop
			nav.Groups(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Groups
		String AccountNo = "0";
		try {
			AccountName = AccountName + Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			group.type_GroupName(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			AccountNo = Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			getTest_Steps = group.enterAccountNo(driver, AccountNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.type_MailingAddrtess(driver, test, AccountFirstName, AccountLastName, Phonenumber, Address1, city,
					State, Country, Postalcode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create RoomBlock

		try {
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			getTest_Steps.clear();
<<<<<<< HEAD
		//	getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight, RoomClassName);
			getTest_Steps = group.createNewBlock(driver, BlockName, arriveDate, departDate, ratePlanName, RoomPerNight, RoomClassName);
=======
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight, RoomClassName);
>>>>>>> develop
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);

			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			test_steps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, RoomPerNight, "Failed Room Blocked Not Matched");

			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			test_steps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, RoomPerNight, "Failed Room Blocked Not Matched");

			String expectedRevenueDetail = group.getExpectedRevenue_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			test_steps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

			String expectedRevenueInfo = group.getExpectedRevenue_GroupInfo(driver);
			Utility.app_logs.info("Before Group Info Expected Revenue  : " + expectedRevenueInfo);
			test_steps.add("Before Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);

			String blocked = group.getBlocked(driver, RoomClassName);
			assertEquals(Integer.parseInt(blocked), Integer.parseInt(RoomPerNight), "Failed Room Blocked Not Matched");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Folio Tab
		try {
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.selectCheckInPolicy(driver, CHKINPolicyName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.selectCancellationPolicy(driver, CancellationPolicyName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyCheckInPolicy(driver, CHKINPolicyName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyCancellationPolicy(driver, CancellationPolicyName);
			test_steps.addAll(getTest_Steps);

			Utility.app_logs.info("Issue with setting Group Cancellation policy");
			test_steps.add("Issue with setting Group Cancellation policy"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-4249' target='_blank'>"
					+ "Verified : NG-4249</a><br/>");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select policy", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select policy", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String beforePickupValue = null;
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {
<<<<<<< HEAD
			getTest_Steps.clear();
			res.clickGuestInfo_1(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.contactInformation(driver, "", AccountFirstName, AccountLastName, Address1, Address1, "", "", city,
					Country, State, Postalcode, Phonenumber, "", "", "", "", "", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.billingInformation(driver, PaymentMethod, CardNumber, ExpiryDate, "Billing Notes");

			getTest_Steps.clear();
			res.RoomAssignedReservation(driver, true, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
=======
			/*
			 * getTest_Steps.clear(); res.clickGuestInfo_1(driver, getTest_Steps);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear(); getTest_Steps =
			 * res.verifyAccountAttached(driver, AccountName);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear();
			 * res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear();
			 * res.contactInformation(driver, "", AccountFirstName, AccountLastName,
			 * Address1, Address1, "", "", city, Country, State, Postalcode, Phonenumber,
			 * "", "", "", "", "", getTest_Steps); test_steps.addAll(getTest_Steps);
			 * 
			 * res.billingInformation(driver, PaymentMethod, CardNumber, ExpiryDate,
			 * "Billing Notes");
			 * 
			 * getTest_Steps.clear(); res.RoomAssignedReservation(driver, true,
			 * getTest_Steps); test_steps.addAll(getTest_Steps);
			 * 
			 * getTest_Steps.clear();
			 */
			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();

            reservationPage.select_CheckInDate(driver, test_steps, checkInDate);

            reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);

            reservationPage.enter_Adults(driver, test_steps, adults);

            reservationPage.enter_Children(driver, test_steps, children);

            reservationPage.select_Rateplan(driver, test_steps, rateplan,"");

            reservationPage.clickOnFindRooms(driver, test_steps);

            reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes","");

            reservationPage.clickNext(driver, test_steps);

            reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));

            reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);

            reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);

            reservationPage.clickBookNow(driver, test_steps);

            reservationPage.get_ReservationConfirmationNumber(driver, test_steps);

            reservationPage.get_ReservationStatus(driver, test_steps);

            reservationPage.clickCloseReservationSavePopup(driver, test_steps);

           test_steps.add("Successfully Associated Account to  Reservation");

            app_logs.info("Successfully Associated Account to Reservation");
            reservationPage.getRoomNo_ResDetail(driver);
>>>>>>> develop
			res.SaveRes_Updated(driver);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			String folioBalance = res.GetFolioBalance(driver) + "";
			test_steps.add("Folio Balance : " + folioBalance);

			getTest_Steps.clear();
			getTest_Steps = res.checkInBtnClick(driver);
			test_steps.addAll(getTest_Steps);

			// Wait.WaitForReservationLoading(driver);

			getTest_Steps.clear();
			getTest_Steps = res.enterPaymentAmount(driver, folioBalance);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyPayAmountAuthType(driver, folioBalance, "Authorization Only");
			test_steps.addAll(getTest_Steps);

			Utility.app_logs.info("Check in Policy- Authorize");
			test_steps.add("Check in Policy- Authorize"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-6097' target='_blank'>"
					+ "Verified : NG-6097</a><br/>");

			getTest_Steps.clear();
			getTest_Steps = resFolio.clickOnProcessBtn(driver);
			test_steps.addAll(getTest_Steps);

			res.SaveRes_Updated(driver);

			test_steps.add("Reservation Save Button Clicked");
			app_logs.info("Reservation Save Button Clicked");

			res.CloseOpenedReservation(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {

<<<<<<< HEAD
			nav.Reservation(driver);
			test_steps.add("Navigated to Reservation");
			app_logs.info("Navigated to Reservation");
			res.clickNewReservationButton(driver, getTest_Steps);
			test_steps.add("New Reservation Button Clicked");
			app_logs.info("New Reservation Button Clicked");

			getTest_Steps.clear();
			res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.contactInformation(driver, "", AccountFirstName, AccountLastName, Address1, Address1, "", "", city,
					Country, State, Postalcode, Phonenumber, "", "", "", "", "", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.billingInformation(driver, PaymentMethod, CardNumber, ExpiryDate, "Billing Notes");
			test_steps.add("Entered Billing Information");
			getTest_Steps.clear();
			res.RoomAssignedReservation(driver, test, RoomClassName, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
=======
			/*
			 * nav.Reservation(driver); test_steps.add("Navigated to Reservation");
			 * app_logs.info("Navigated to Reservation");
			 * res.clickNewReservationButton(driver, getTest_Steps);
			 * test_steps.add("New Reservation Button Clicked");
			 * app_logs.info("New Reservation Button Clicked");
			 * 
			 * getTest_Steps.clear(); res.marketingInfo(driver, test, MargetSegment,
			 * Referral, getTest_Steps); test_steps.addAll(getTest_Steps);
			 * getTest_Steps.clear(); res.contactInformation(driver, "", AccountFirstName,
			 * AccountLastName, Address1, Address1, "", "", city, Country, State,
			 * Postalcode, Phonenumber, "", "", "", "", "", getTest_Steps);
			 * test_steps.addAll(getTest_Steps);
			 * 
			 * res.billingInformation(driver, PaymentMethod, CardNumber, ExpiryDate,
			 * "Billing Notes"); test_steps.add("Entered Billing Information");
			 * getTest_Steps.clear(); res.RoomAssignedReservation(driver, test,
			 * RoomClassName, getTest_Steps); test_steps.addAll(getTest_Steps);
			 * 
			 * getTest_Steps.clear();
			 */
			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();

            reservationPage.select_CheckInDate(driver, test_steps, checkInDate);

            reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);

            reservationPage.enter_Adults(driver, test_steps, adults);

            reservationPage.enter_Children(driver, test_steps, children);

            reservationPage.select_Rateplan(driver, test_steps, rateplan,"");

            reservationPage.clickOnFindRooms(driver, test_steps);

            reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes","");

            reservationPage.clickNext(driver, test_steps);

            reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));

            reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);

            reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);

            reservationPage.clickBookNow(driver, test_steps);

            reservationPage.get_ReservationConfirmationNumber(driver, test_steps);

            reservationPage.get_ReservationStatus(driver, test_steps);

            reservationPage.clickCloseReservationSavePopup(driver, test_steps);

           test_steps.add("Successfully Associated Account to  Reservation");

            app_logs.info("Successfully Associated Account to Reservation");
            reservationPage.getRoomNo_ResDetail(driver);
>>>>>>> develop
			res.SaveRes_Updated(driver);
			test_steps.add("Reservation Save Button Clicked");
			app_logs.info("Reservation Save Button Clicked");
			test_steps.addAll(getTest_Steps);

			res.ClickFolioTabs(driver);
			
			res.ClickFolioOption(driver);
			
			getTest_Steps.clear();
			getTest_Steps = resFolio.discardAllCancelPolicies(driver);
			test_steps.addAll(getTest_Steps);
			
			res.SaveRes_Updated(driver);
			
<<<<<<< HEAD
			Wait.WaitForReservationLoading(driver);
=======
			Wait.waitForCPReservationLoading(driver);
>>>>>>> develop
			res.clickGuestInfo_1(driver);

			getTest_Steps.clear();
			getTest_Steps = res.Associate_GroupAccount(driver, AccountName);
			test_steps.addAll(getTest_Steps);
			
//			res.SaveAfter_Reservation(driver);
//			test_steps.add("Reservation Save Button Clicked");
//			app_logs.info("Reservation Save Button Clicked");

			getTest_Steps.clear();
			getTest_Steps = res.verifyResPolicyDialog(driver);
			test_steps.addAll(getTest_Steps);

			Utility.app_logs.info("Unable to associate account for client that has no policies - Code Fix");
			test_steps.add("Unable to associate account for client that has no policies - Code Fix"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-6553' target='_blank'>"
					+ "Verified : NG-6553</a><br/>");

			res.SaveRes_Updated(driver);
			test_steps.add("Reservation Save Button Clicked");
			app_logs.info("Reservation Save Button Clicked");

			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			nav.Reservation(driver);
			nav.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete rate
		try {
			try {
				nav.Inventory(driver);
				nav.Rates1(driver);
				rate.SearchRate(driver, RateName, false);

				test_steps.add("New Rate has been Searched successfully");
				app_logs.info("New Rate has been Searched successfully");

				rate.delete_rate_1(driver, RateName);

				test_steps.add("New Rate has been Deleted successfully");
				app_logs.info("New Rate has been Deleted successfully");
			} catch (Exception e) {
				System.out.println("Tried To Delete Rate");
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Room Class
		try {
			try {
				nav.Setup(driver);
				nav.RoomClass(driver);

				try {
					room_class.SearchRoomClass(driver, RoomClassName, false);
					room_class.Delete_RoomClass(driver, RoomClassName);
				} catch (Exception b) {
					room_class.SelectItemsPerPage(driver);
					room_class.Search_Delete_RoomClass(driver, RoomClassName);
				}

				test_steps.add("Delete room class successfully");
				app_logs.info("Delete room class successfully");
			} catch (Exception e) {
				System.out.println("Tried to Delete RoomClass");
			}
			
			try {
				nav.Inventory(driver);
				Policies policy = new Policies();
				test_steps.add("Navigated  Inventory");
				app_logs.info("Navigated  Inventory");
				Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
				Navigate.Menuitem_Policy_Text.click();
				test_steps.add("Navigated  Policy");
				app_logs.info("Navigated  Policy");
				policy.Delete_Policy(driver, CHKINPolicyName);
				policy.Delete_Policy(driver, CancellationPolicyName);
				test_steps.add("Successfully Delete All Created Policies ");
				app_logs.info(" Successfully Delete All Created Policies ");
			} catch (Exception e) {
				System.out.println("Tried to Delete Policy");
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyGrpWithChkInAndCnclPolici", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
