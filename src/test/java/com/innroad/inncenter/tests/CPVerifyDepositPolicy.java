package com.innroad.inncenter.tests;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyDepositPolicy extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	String abbri = null, seasonStartDate = null, seasonEndDate = null, confirmationNo = null, getAmount = null,
			roomClassNames = null;
	NightlyRate nightlyRate = new NightlyRate();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	Policies policies = new Policies();
	Folio folio = new Folio();
	String testName = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyDepositPolicy(String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity, String depositPolicyName, String typeOfPolicy, String policyValue, String chargesTypes,
			String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String noOfPolicy) throws ParseException {
		String getcardNo = null, roomCharge = null, highestPolicyName = null, highestPolicyValue = null,highestPolicyType = null;
		if (noOfPolicy.equalsIgnoreCase("Single")) {
			test_name = "CPVerifyDepositSinglePolicy";
		} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
			test_name = "CPVerifyDepositMultiplePolicy"; 
		}
		test_description = "Verify CheckIn Policies <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825295' target='_blank'>"
				+ "Click here to open TestRail: 825295</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("825295", Utility.testId, Utility.statusCode,
				Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		
		ArrayList<String> roomNumber = new ArrayList<String>();
		List<String> roomNumbers = new ArrayList<String>();
		List<String> roomCharges = new ArrayList<String>();
		RatesGrid rateGrid = new RatesGrid();
		HashMap<String, ArrayList<String>> polictiesNames = new HashMap<String, ArrayList<String>>();
		HashMap<String, String> getCheckinCheckoutDate = new HashMap<String, String>();
		ArrayList<String> policiesNames = new ArrayList<String>();
		ArrayList<String> policiesAttrsValue = new ArrayList<String>();
		ArrayList<String> policiesTypes = new ArrayList<String>();
		ArrayList<String> roomClassesNames = new ArrayList<String>();
		ArrayList<String> roomClassOneRoomNo= new ArrayList<String>();
		ArrayList<String> roomClassTwoRoomNo= new ArrayList<String>();
		ArrayList<String> roomClassfinalRoomNos= new ArrayList<String>();
		ArrayList<String> roomsCharge = new ArrayList<>();
		// Login
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
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						Utility.reTry.replace(testName, 1);
					}
					datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
					app_logs.info(datesRangeList);
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
				// Create Room Class
				try {
					testSteps.add("<b>======Start Creating New Room Class======</b>");
					navigation.clickSetup(driver);
					navigation.RoomClass(driver);
					testSteps.add("Navigated to Room Class");
					if (noOfPolicy.equalsIgnoreCase("Single")) {
						roomClassNames = roomClassName + Utility.fourDigitgenerateRandomString();
						newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults,
								maxPersopns, roomQuantity);
						newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
						testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
						app_logs.info("Room Class Created: " + roomClassNames);
					} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
						ArrayList<String> roomClassAre = Utility.splitInputData(roomClassName);
						ArrayList<String> rooms= new ArrayList<String> ();
						for (int i = 0; i < roomClassAre.size(); i++) {
							roomClassNames = roomClassAre.get(i) + Utility.threeDigitgenerateRandomString();
							 rooms=newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults,
									maxPersopns, roomQuantity);
							newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
							roomClassesNames.add(roomClassNames);
							roomClassfinalRoomNos.addAll(rooms);
						}
						for(int i=0;i<4;i++) {
						roomClassOneRoomNo.add(roomClassfinalRoomNos.get(i));
						}
						for(int i=4;i<8;i++) {
							roomClassTwoRoomNo.add(roomClassfinalRoomNos.get(i));
						}
						app_logs.info(roomClassOneRoomNo);
						app_logs.info(roomClassTwoRoomNo);								
						roomClassNames = roomClassesNames.get(0) + "|" + roomClassesNames.get(1);
						testSteps.add("Room Class Created: <b>" + roomClassesNames + "</>");
						app_logs.info("Room Class Created: " + roomClassesNames);
						app_logs.info("Room Class Created: " + roomClassNames);
					}

				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
							test_catagory, testSteps);
				}
				try {
					HashMap<String, String> policiesHashmap = new HashMap<String, String>();
					HashMap<String, String> policiesHashmapForType = new HashMap<String, String>();
					testSteps.add("<b>======Start Creating Policy ======</b>");
					navigation.inventoryFromRoomClass(driver, testSteps);
					navigation.policies(driver, testSteps);
					Wait.waitUntilPageLoadNotCompleted(driver, 5);
					if (noOfPolicy.equalsIgnoreCase("Single")) {
						depositPolicyName = depositPolicyName + Utility.generateRandomStringWithoutNumbers();
						polictiesNames = policies.createPolicies(driver, testSteps, "", "", "Deposit", "", "",
								depositPolicyName, "", typeOfPolicy, policyValue, chargesTypes, "", "", "No", "");
					} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
						ArrayList<String> policiesAre = Utility.splitInputData(depositPolicyName);
						ArrayList<String> policiesPercentageAre = Utility.splitInputData(policyValue);
						ArrayList<String> policiesType = Utility.splitInputData(typeOfPolicy);

						for (int i = 0; i < policiesAre.size(); i++) {
							String policy = policiesAre.get(i) + Utility.generateRandomStringWithoutNumbers();
							String percentageOfPolicy = policiesPercentageAre.get(i);
							policiesNames.add(policy);
							policiesAttrsValue.add(percentageOfPolicy);	
							policiesTypes.add(policiesType.get(i));
							polictiesNames = policies.createPolicies(driver, testSteps, "", "", "Deposit", "", policy, "", "",
									policiesType.get(i), percentageOfPolicy, chargesTypes, "", "", "No", "");
							policiesHashmap.put(policy, percentageOfPolicy);
							policiesHashmapForType.put(policiesType.get(i), percentageOfPolicy);
							}
						depositPolicyName = policiesNames.get(0) + "|" + policiesNames.get(1);
						}
					app_logs.info(polictiesNames);
					app_logs.info(policiesNames);
					app_logs.info(policiesAttrsValue);
					app_logs.info(policiesHashmap);
					app_logs.info(depositPolicyName);

				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
							test_catagory, testSteps);
				}
				// Create Season
				try {
					testSteps.add("<b>======Start Updating Rate Plan ======</b>");
					if (noOfPolicy.equalsIgnoreCase("Single")) {
						nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName, datesRangeList,
								seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", false);
					} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
						ArrayList<String> rateplanes = Utility.splitInputData(ratePlanName);
						nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplanes.get(0), datesRangeList,
								seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", false);
					}
					nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList.get(0));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					nightlyRate.clickSeasonPolicies(driver, testSteps);
					if (noOfPolicy.equalsIgnoreCase("Single")) {
						nightlyRate.selectPolicy(driver, "Deposit", depositPolicyName, true, testSteps);
					} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
								ArrayList<String> roomClassList = Utility.splitInputData(roomClassNames);
						ArrayList<String> policiesList = Utility.splitInputData(depositPolicyName);
						HashMap<String, String> policiesListOne = new HashMap<String, String>();
						HashMap<String, String> policiesListTwo = new HashMap<String, String>();
						HashMap<String, HashMap<String, String>> roomClassPolicy = new HashMap<String, HashMap<String, String>>();
						policiesListOne.put("Deposit", policiesList.get(0));
						policiesListTwo.put("Deposit", policiesList.get(1));
						roomClassPolicy.put(roomClassList.get(0), policiesListOne);
						roomClassPolicy.put(roomClassList.get(1), policiesListTwo);
						nightlyRate.selectPolicyAsPerRoomClass(driver, roomClassNames, roomClassPolicy, testSteps);
					
					}
					nightlyRate.clickSaveSason(driver, testSteps);
					nightlyRate.clickSaveRatePlanButton(driver, testSteps);
					rateGrid.closeRatePlan(driver, testSteps, ratePlanName);
					Wait.waitUntilPageLoadNotCompleted(driver, 40);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				}
				// Create Reservation
				try {
					testSteps.add("<b>======Start Creating Reservation ======</b>");
					if (noOfPolicy.equalsIgnoreCase("Single")) {
						guestFirstName = guestFirstName + Utility.threeDigitgenerateRandomString();
						guestLastName = guestLastName + Utility.threeDigitgenerateRandomString();
						confirmationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, adults, children,
								ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber,
								nameOnCard, marketSegment, referral, roomClassNames, true, false);
						testSteps.add("Reservation Created: <b>" + confirmationNo + "</>");
						app_logs.info("Reservation Created: " + confirmationNo);
						app_logs.info(roomNumber);
					} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
						confirmationNo = reservation.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, adults,
								children, ratePlanName, roomClassNames, salutation, guestFirstName, guestLastName, "No",
								roomNumber, paymentType, cardNumber, nameOnCard, referral, false, testSteps);
					}
					roomNumbers = reservation.getStayInfoRoomNo(driver, testSteps);
					app_logs.info(roomNumbers);
					getCheckinCheckoutDate = reservation.getStayInfoCheckInCheckOutDate(driver);
					app_logs.info(getCheckinCheckoutDate);
					roomCharges = reservation.getStayInfoRoomCharges(driver, testSteps);
					roomCharge = roomCharges.get(0).replace("$", "");
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				}	
			
				try {
					
					String depositAmount = null;
					HashMap<String, String> getRoomCharge = new HashMap<String, String>();
					reservation.click_Folio(driver, testSteps);
					reservation.includeTaxesinLineItems(driver, testSteps, true);
					if (noOfPolicy.equalsIgnoreCase("Single")) {
						getRoomCharge = reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDate,
								checkOutDate);
						for (Map.Entry<String, String> entry : getRoomCharge.entrySet()) {
							roomsCharge.add(entry.getValue());
						}
					} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
						for (int i = 0; i < roomClassesNames.size(); i++) {
							reservation.click_FolioDetail_DropDownBox(driver, testSteps);
							reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassesNames.get(i),
									roomNumbers.get(i));											
							getRoomCharge = reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps,
									checkInDate, checkOutDate);
							for (Map.Entry<String, String> entry : getRoomCharge.entrySet()) {
								roomsCharge.add(entry.getValue());
							}
							app_logs.info(roomsCharge);
						}
						reservation.click_FolioDetail_DropDownBox(driver, testSteps);
						reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassesNames.get(0), roomNumbers.get(0));
						}

				if (noOfPolicy.equalsIgnoreCase("Multiple")) {
					HashMap<String, String> getdepositAmount = new HashMap<String,String>();
						for(int i=0;i<policiesNames.size();i++) {
							String size= String.valueOf(roomClassName.split("\\|").length);
							getdepositAmount.put(policiesNames.get(i), reservation.calculationOfDepositAmountToBePaidForRateV2(roomsCharge, policiesTypes.get(i), policiesAttrsValue.get(i),size));
						}
						ArrayList<Double> dbr= new ArrayList<Double>();
						for (Map.Entry<String, String> entry : getdepositAmount.entrySet()) {
							dbr.add(Double.valueOf(entry.getValue()));
						}
						DecimalFormat df = new DecimalFormat("0.00");
						df.setMaximumFractionDigits(2);
						depositAmount=df.format(Collections.max(dbr));
						app_logs.info(depositAmount);
						highestPolicyName=Utility.getKeyfromHashmap(getdepositAmount, depositAmount);
						app_logs.info(highestPolicyName);
					}	else if (noOfPolicy.equalsIgnoreCase("Single")){
						depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(getRoomCharge, typeOfPolicy, policyValue);
					}
				reservation.click_DeatilsTab(driver, testSteps);
					testSteps.add("<b>======Verify Policy on Detail Page ======</b>");
					if (noOfPolicy.equalsIgnoreCase("Single")) {
						reservation.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, depositPolicyName, "");
					} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
						reservation.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, highestPolicyName, "");
					}
					
					reservation.click_Folio(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					testSteps.add("<b>======Deposit Policy Amount verification======</b>");
					app_logs.info("======Deposit Policy Amount verification======");
						getcardNo = Utility.getCardNumberHidden(cardNumber);
					String description = getcardNo;
					reservation.verifyFolioLineItem(driver, checkInDate, paymentType, description, depositAmount, testSteps);
					reservation.verify_FolioPayment(driver, testSteps, depositAmount);
					String paymentMethod= ""+paymentType+" "+getcardNo+" ("+Utility.expiryDate+")";
					testSteps.add("<b>======Start Verifying History======</b>");
					// Click History Tab
					reservation.click_History(driver, testSteps);
					reservation.verifyHistoryWithCapturedPayment(driver, testSteps,
							Utility.convertDecimalFormat(depositAmount), paymentMethod, "Deposit");
					if (noOfPolicy.equalsIgnoreCase("Multiple")) {
							testSteps.add("Verify Deposit policy calculation for MRB when each room has different Policy Attributes"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/825295' target='_blank'>"
								+ "Click here to open TestRail: 825295</a><br>");
						Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Deposit policy calculation for MRB when each room has different Policy Attributes");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
								Utility.comments.get(0), TestCore.TestRail_AssignToID);
						}
					
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Checkin policy verification folio line item", "Reservation",
							"Reservation", testName, test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Checkin policy verification folio line item", "Reservation", "Reservation",
							testName, test_description, test_catagory, testSteps);
				}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CPVerifyDepositPolicy", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
