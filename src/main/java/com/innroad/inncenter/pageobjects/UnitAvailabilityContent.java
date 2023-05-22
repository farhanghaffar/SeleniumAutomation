package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.WaitFor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import io.restassured.response.Response;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class UnitAvailabilityContent {
	public static Logger unitLogger = Logger.getLogger("UnitAvailabilityContent");

	public void verifyRulesOverriedThroughBulkUpdate(WebDriver driver, ArrayList<String> testSteps, String endPointName,
			String getUnitAvailabilityContentApi, String vrboToken, int statusCode, String ratePlanName,
			String roomClassName, String startDate, String endDate, String defaultDateFormat, String timeZone,
			String rulesToUpdate, String minStayRuleValue, String vrboSource, String option, String isOccupancy,
			String occupancyType, String occupancyValue, String action, String isMon, String isTue, String isWed,
			String isThu, String isFri, String isSat, String isSun, String overriedType, String isRuleApply)
			throws Exception {

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		VrboObjects vrboObject = new VrboObjects();
		ArrayList<String> getTestSteps = new ArrayList<>();
		navigation.Inventory(driver, testSteps);
		testSteps.add("Navigate inventory");
		navigation.Rates_Grid(driver);
		testSteps.add("Navigate rates grid");
		int getDaysbetweenCurrentAndCheckInDate = ESTTimeZone.numberOfDaysBetweenDates(
				Utility.getCurrentDate(defaultDateFormat, timeZone), startDate, defaultDateFormat) + 1;
		int getDaysbetweenDate = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate, defaultDateFormat) + 1;
		unitLogger.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
		unitLogger.info("getDaysbetweenDate : " + getDaysbetweenDate);

		ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(rulesToUpdate, ",");
		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);

		Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
		String getPreNoCheckInRuleValue = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.changeOver");

		unitLogger.info("getPreNoCheckInRuleValue : " + getPreNoCheckInRuleValue);
		getPreNoCheckInRuleValue = getPreNoCheckInRuleValue.substring(getDaysbetweenCurrentAndCheckInDate );
		getPreNoCheckInRuleValue = getPreNoCheckInRuleValue.substring(0, getDaysbetweenCurrentAndCheckInDate + 1);

		char preRuleArr[] = getPreNoCheckInRuleValue.toCharArray();
		int numberOfDays = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate, defaultDateFormat) + 1;

		if (overriedType.equalsIgnoreCase("bulkUpdate")) {
			testSteps.add("<b> ========== BULK UPDATE " + rulesToUpdate + " RULES ========== </b>");
			unitLogger.info("========== BULK UPDATE " + rulesToUpdate + " RULES ==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectBulkUpdateOption(driver, "Rules");
			testSteps.addAll(getTestSteps);

			testSteps.add("==========SELECT START DATE==========");
			unitLogger.info("==========SELECT START DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectDate(driver, startDate, true);
			testSteps.addAll(getTestSteps);

			testSteps.add("==========SELECT END DATE==========");
			unitLogger.info("==========SELECT END DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectDate(driver, endDate, false);
			testSteps.addAll(getTestSteps);

			unitLogger.info("==========CHECKING/UNCHECKING DAYS==========");
			testSteps.add("==========CHECKING/UNCHECKING DAYS==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Sun", isSun);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Mon", isMon);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Tue", isTue);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Wed", isWed);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Thu", isThu);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Fri", isFri);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Sat", isSat);
			testSteps.addAll(getTestSteps);

			unitLogger.info("==========SELECTING RATE PLAN==========");
			testSteps.add("==========SELECTING RATE PLAN==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", ratePlanName);
			testSteps.addAll(getTestSteps);

			unitLogger.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClassName);
			testSteps.addAll(getTestSteps);

			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", vrboSource);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				e.printStackTrace();
			}

			for (String type : type_rulesUpdateList) {

				if (type.equalsIgnoreCase("min stay")) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickMinimumStay(driver, "Yes");
					testSteps.addAll(getTestSteps);

					if (isRuleApply.equalsIgnoreCase("Yes")) {
						getTestSteps.clear();
						getTestSteps = ratesGrid.enterMinimumStayValue(driver, minStayRuleValue);
						testSteps.addAll(getTestSteps);
					}

				} else if (type.equalsIgnoreCase("No Check In")) {

					getTestSteps.clear();
					getTestSteps = ratesGrid.clickCheckin(driver, "Yes");
					testSteps.addAll(getTestSteps);
					if (isRuleApply.equalsIgnoreCase("Yes")) {

						getTestSteps.clear();
						getTestSteps = ratesGrid.clickNoCheckInCheckbox(driver, "Yes");
						testSteps.addAll(getTestSteps);
					}
				} else if (type.equalsIgnoreCase("No Check out")) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickCheckOut(driver, "Yes");
					testSteps.addAll(getTestSteps);
					if (isRuleApply.equalsIgnoreCase("Yes")) {

						getTestSteps.clear();
						getTestSteps = ratesGrid.clickNoCheckOutCheckbox(driver, "Yes");
						testSteps.addAll(getTestSteps);
					}
				}

			}

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickUpdateButton(driver);
			testSteps.addAll(getTestSteps);

			unitLogger.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
			testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

			String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
			testSteps.add("Expected total days : " + expectedDays);
			unitLogger.info("Expected total days : " + expectedDays);
			String totalDays = ratesGrid.getTotalDaysText(driver, "Rules");
			testSteps.add("Found : " + totalDays);
			unitLogger.info("Found : " + totalDays);
			Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
			testSteps.add("Verified total number of days");
			unitLogger.info("Verified total number of days");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnYesUpdateButton(driver);
			testSteps.addAll(getTestSteps);
			Wait.wait10Second();

		} else if (overriedType.equalsIgnoreCase("override")) {
			ArrayList<String> getDates = Utility.checkDayAndReturnDates(startDate, endDate, defaultDateFormat, isMon,
					isTue, isWed, isThu, isFri, isSat, isSun);

			testSteps.add("<b> ========== OVERRIED " + rulesToUpdate + " RULES ========== </b>");
			unitLogger.info("========== OVERRIED " + rulesToUpdate + " RULES ==========");
			for (String type : type_rulesUpdateList) {
				ratesGrid.expandRoomClass1(driver, testSteps, roomClassName);
				ratesGrid.expandChannel(driver, testSteps, roomClassName, vrboSource);

				for (int i = 0; i < getDates.size()-1; i++) {
					ratesGrid.clickForRateGridCalender(driver, testSteps);
					Utility.selectDateFromDatePicker(driver, getDates.get(i), defaultDateFormat);

					if (type.equalsIgnoreCase("min stay")) {
						ratesGrid.overrideMinStayValue(driver, testSteps, roomClassName, vrboSource, minStayRuleValue);
					} else if (type.equalsIgnoreCase("No Check In")) {
						if (isRuleApply.equalsIgnoreCase("Yes")) {
							ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, vrboSource,
									"No Check In", true);
						} else {
							ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, vrboSource,
									"No Check In", false);
						}
					} else if (type.equalsIgnoreCase("No Check out")) {
						if (isRuleApply.equalsIgnoreCase("Yes")) {
							ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, vrboSource,
									"No Check Out", true);
						} else {
							ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, vrboSource,
									"No Check Out", false);
						}
					}
				}
			}

		}

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b>" + " ===== Getting EndPoint Response ===== ".toUpperCase() + "</b>");

		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
		unitLogger.info("response  : " + response);
		unitLogger.info("type_rulesUpdateList size : " + type_rulesUpdateList.size());
		System.out.println(
				type_rulesUpdateList.contains("No Check In") + " : " + type_rulesUpdateList.contains("No Check Out"));

		for (String type : type_rulesUpdateList) {
			unitLogger.info("type_rulesUpdateList : " + type);
			if (type.equalsIgnoreCase("min stay")) {
				unitLogger.info(
						"===== VERIFY min stay rule value '" + minStayRuleValue + "' in response =====".toUpperCase());
				testSteps.add("<b>" + "===== VERIFY min stay rule value '" + minStayRuleValue
						+ "' in response =====".toUpperCase() + "</b>");

				String getMinStayRuleValue = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.minStay");
				ArrayList<Integer> tempResponse = new ArrayList<>();
				ArrayList<Integer> minStayRuleList = new ArrayList<>();
				tempResponse.clear();
				for (String s : getMinStayRuleValue.split(",")) {
					tempResponse.add(Integer.parseInt(s));
				}
				if (getDaysbetweenCurrentAndCheckInDate > 0) {
					for (int j = 0; j < getDaysbetweenCurrentAndCheckInDate; j++) {
						tempResponse.remove(j);
					}
				}
				unitLogger.info("tempResponse : " + tempResponse);
				minStayRuleList.clear();
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						minStayRuleList.add(tempResponse.get(j));
					}
				}
				unitLogger.info("getMinStayRuleValue : " + minStayRuleList);

				unitLogger.info("Expected Min Stay Rule Value : " + minStayRuleValue);
				testSteps.add("Expected Min Stay Rule Value : " + minStayRuleValue);

				for (int i = 0; i < numberOfDays; i++) {
					unitLogger.info("Found : " + minStayRuleList.get(i));
					testSteps.add("Found : " + minStayRuleList.get(i));

					if (minStayRuleValue.isEmpty() || minStayRuleValue.equalsIgnoreCase("")) {
						if(String.valueOf(minStayRuleList.get(i)).equalsIgnoreCase("1")) {
						assertEquals(String.valueOf(minStayRuleList.get(i)), "1",
								"Failed : Min Stay Rule Value didn't match");
						}
						else
						{
							assertEquals(String.valueOf(minStayRuleList.get(i)), "3",
									"Failed : Min Stay Rule Value didn't match");	
						}
					} else {
						assertEquals(String.valueOf(minStayRuleList.get(i)), minStayRuleValue,
								"Failed : Min Stay Rule Value didn't match");
					}
				}

				unitLogger.info("Verified min stay rule value '" + minStayRuleValue + "' in response");
				testSteps.add("Verified min stay rule value '" + minStayRuleValue + "' in response");

			} else if (type.equalsIgnoreCase("No Check In")) {
				unitLogger.info("===== VERIFY NO CHECK IN rule sign in response =====".toUpperCase());
				testSteps
						.add("<b>" + "===== VERIFY NO CHECK IN rule sign  in response =====".toUpperCase() + "</b>");

				String getNoCheckInRuleValue = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.changeOver");
				getNoCheckInRuleValue = getNoCheckInRuleValue.substring(getDaysbetweenCurrentAndCheckInDate);
				unitLogger.info("getNoCheckInRuleValue : " + getNoCheckInRuleValue);
				getNoCheckInRuleValue = getNoCheckInRuleValue.substring(0, getDaysbetweenCurrentAndCheckInDate + 1);

				unitLogger.info("getNoCheckInRuleValue : " + getNoCheckInRuleValue);
//				unitLogger.info("Expected NoCheckIn Rule Sign : " + "O");
//				testSteps.add("Expected NoCheckIn Rule Sign : " + "O");

				char ruleArr[] = getNoCheckInRuleValue.toCharArray();
				for (int i = 0; i < ruleArr.length; i++) {
					char tempRule = preRuleArr[i];
					unitLogger.info("preRuleArr : " + tempRule);
					unitLogger.info("Pre Update Sign : " + String.valueOf(tempRule));
					testSteps.add("Pre Update Sign : " + String.valueOf(tempRule));
					unitLogger.info("Found : " + String.valueOf(ruleArr[i]));
					testSteps.add("Found : " + String.valueOf(ruleArr[i]));
					unitLogger.info("Found : " + String.valueOf(ruleArr[i]));
					unitLogger.info(String.valueOf(ruleArr[i]));
					if (String.valueOf(tempRule).equalsIgnoreCase("I")) {
						if(String.valueOf(ruleArr[i]).equalsIgnoreCase("X")) {
							assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");
							}
						if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")){
							assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
							
						}
							else {
								assertEquals(String.valueOf(ruleArr[i]), "I", "Failed : NoCheckIn Rule Value didn't match");
								
							}
					} 
					else if (String.valueOf(tempRule).equalsIgnoreCase("C")) {
						
					if(String.valueOf(ruleArr[i]).equalsIgnoreCase("O")) {
						assertEquals(String.valueOf(ruleArr[i]), "O", "Failed : NoCheckIn Rule Value didn't match");
						}
					else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
							assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
						}
					else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("I")) {
						assertEquals(String.valueOf(ruleArr[i]), "I", "Failed : NoCheckIn Rule Value didn't match");
					}
					else {
						assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");
						
					}
					}
					else if(String.valueOf(tempRule).equalsIgnoreCase("X")) {
					if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
						assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
					}
					
					else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("X")) {
							assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");	
						}
					else {
						if(String.valueOf(ruleArr[i]).equalsIgnoreCase("O")) {
						assertEquals(String.valueOf(ruleArr[i]), "O", "Failed : NoCheckIn Rule Value didn't match");	
						}
						else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
							assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
						}
						else {
							assertEquals(String.valueOf(ruleArr[i]), "I", "Failed : NoCheckIn Rule Value didn't match");	
							
						}
					}
					}
					else if(String.valueOf(tempRule).equalsIgnoreCase("O")) {
						if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
						assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
						}
						else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("X")){
							assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");
						}
						else {
							assertEquals(String.valueOf(ruleArr[i]), "O", "Failed : NoCheckIn Rule Value didn't match");								
						}
					}
					unitLogger.info("Verified no check in rule sign " + ruleArr[i] + " in response");
					testSteps.add("Verified no check in rule sign " + ruleArr[i] +" in response");

				}

			} else if (type.equalsIgnoreCase("No Check out")) {

				unitLogger.info("===== VERIFY NO CHECK OUT rule sign in response =====".toUpperCase());
				testSteps.add(
						"<b>" + "===== VERIFY NO CHECK OUT rule sign in response =====".toUpperCase() + "</b>");

				String getNoCheckInRuleValue = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.changeOver");
				getNoCheckInRuleValue = getNoCheckInRuleValue.substring(getDaysbetweenCurrentAndCheckInDate);
				getNoCheckInRuleValue = getNoCheckInRuleValue.substring(0, getDaysbetweenCurrentAndCheckInDate);
				unitLogger.info("getNoCheckInRuleValue : " + getNoCheckInRuleValue);
				char ruleArr[] = getNoCheckInRuleValue.toCharArray();
				for (int i = 0; i < ruleArr.length; i++) {
					char tempRule = preRuleArr[i];
					unitLogger.info("preRuleArr : " + tempRule);
					unitLogger.info("Pre Update Sign : " + String.valueOf(tempRule));
					testSteps.add("Pre Update Sign : " + String.valueOf(tempRule));
					unitLogger.info("Found : " + String.valueOf(ruleArr[i]));
					testSteps.add("Found : " + String.valueOf(ruleArr[i]));

					if (String.valueOf(tempRule).equalsIgnoreCase("I")) {
						if(String.valueOf(ruleArr[i]).equalsIgnoreCase("X")) {
						assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");
						}
						if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")){
							assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
							
						}
						else {
							assertEquals(String.valueOf(ruleArr[i]), "I", "Failed : NoCheckIn Rule Value didn't match");
							
						}
					} 
					else if (String.valueOf(tempRule).equalsIgnoreCase("C")) {
						
					if(String.valueOf(ruleArr[i]).equalsIgnoreCase("O")) {
						assertEquals(String.valueOf(ruleArr[i]), "O", "Failed : NoCheckIn Rule Value didn't match");
						}
					else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
							assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
						}
					else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("I")) {
						assertEquals(String.valueOf(ruleArr[i]), "I", "Failed : NoCheckIn Rule Value didn't match");
					}
					else {
						assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");
						
					}
					}
					else if(String.valueOf(tempRule).equalsIgnoreCase("X")) {
					if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
						assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
					}
					
					else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("X")) {
							assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");	
						}
					if(String.valueOf(ruleArr[i]).equalsIgnoreCase("O")) {
						assertEquals(String.valueOf(ruleArr[i]), "O", "Failed : NoCheckIn Rule Value didn't match");	
						}
					else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
						assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
					}
					else {
							assertEquals(String.valueOf(ruleArr[i]), "I", "Failed : NoCheckIn Rule Value didn't match");	
							
						}
					}
					else if(String.valueOf(tempRule).equalsIgnoreCase("O")) {
						if(String.valueOf(ruleArr[i]).equalsIgnoreCase("C")) {
						assertEquals(String.valueOf(ruleArr[i]), "C", "Failed : NoCheckIn Rule Value didn't match");
						}
						else if(String.valueOf(ruleArr[i]).equalsIgnoreCase("X")){
							assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn Rule Value didn't match");
						}
						else {
							assertEquals(String.valueOf(ruleArr[i]), "O", "Failed : NoCheckIn Rule Value didn't match");								
						}
					}

					unitLogger.info("Verified no check out rule sign "+ ruleArr[i] + " in response");
					testSteps.add("Verified no check out rule sign "+ ruleArr[i] + " in response");
				}

	
			} else if (type_rulesUpdateList.contains("No Check In") && type_rulesUpdateList.contains("No Check Out")) {

				unitLogger
						.info("===== Verify no checkin and no checkout rule sign 'X' in response =====".toUpperCase());
				testSteps.add(
						"<b>" + "===== Verify no checkin and no checkout rule sign 'X' in response =====".toUpperCase()
								+ "</b>");

				String getNoCheckInRuleValue = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.changeOver");
				getNoCheckInRuleValue = getNoCheckInRuleValue.substring(getDaysbetweenCurrentAndCheckInDate);
				getNoCheckInRuleValue = getNoCheckInRuleValue.substring(0, getDaysbetweenDate);

				unitLogger.info("getNoCheckInRuleValue : " + getNoCheckInRuleValue);

				unitLogger.info("Expected NoCheckIn NoCheckOut Rule Sign : " + "X");
				testSteps.add("Expected NoCheckIn NoCheckOut Rule Sign : " + "X");

				char ruleArr[] = getNoCheckInRuleValue.toCharArray();
				for (int i = 0; i < ruleArr.length; i++) {
					char tempRule = ruleArr[i];
					unitLogger.info("Pre Update Sign : " + String.valueOf(tempRule));
					testSteps.add("Pre Update Sign : " + String.valueOf(tempRule));
					unitLogger.info("Found : " + String.valueOf(ruleArr[i]));
					testSteps.add("Found : " + String.valueOf(ruleArr[i]));
					// assertEquals(String.valueOf(ruleArr[i]), "X", "Failed : NoCheckIn No CheckOut
					// Rule Value didn't match");
				}

				unitLogger.info("Verified no checkin and no checkout rule sign 'X' in response");
				testSteps.add("Verified no checkin and no checkout rule sign 'X' in response");

			}

		}

	}

	public void verifyInactiveRoomClassAndRatePlan(WebDriver driver, String endPointName,
		String getUnitAvailabilityContentApi, String vrboToken, int statusCode, ArrayList<String> testSteps,
		String roomClassName, String ratePlan) throws Exception {
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		VrboObjects vrboObject = new VrboObjects();
		DerivedRate derivedRate = new DerivedRate();
		NewRoomClassesV2 roomClasses = new NewRoomClassesV2();
		navigation.Inventory(driver);
		navigation.RatesGrid(driver);
		testSteps.add("Navigated to RatesGrid");
		unitLogger.info("Navigated to RatesGrid");

		testSteps.add("<b>=== " + "Verify endpoint after changing rate plan(" + ratePlan.toUpperCase()
				+ ") status to inactive".toUpperCase() + " ===</b>");
		unitLogger.info("=== " + "Verify endpoint after changing rate plan(" + ratePlan.toUpperCase()
				+ ") status to inactive".toUpperCase() + " ===");

		try {
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlan);
		} catch (Exception e) {
			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlan);
		}

		ratesGrid.clickOnEditRatePlan(driver);
		testSteps.add("Click on edit rate plan");
		unitLogger.info("Click on edit rate plan");

		testSteps.addAll(ratesGrid.ratePlanOverView(driver));

		nightlyRate.ratePlanStatusChange(driver, false, testSteps);
		testSteps.addAll(ratesGrid.clickOnSaveratePlan(driver));

		testSteps.add("Updated rate status to inactive successfully");
		unitLogger.info("Updated rate status to inactive successfully");

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
		String getAvilability = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability");
		char tempArr[] = getAvilability.toCharArray();

		for (int i=0; i < tempArr.length; i++) {
			assertEquals(String.valueOf(tempArr[i]), "N", "Failed : Room is available after making rate plan inactive");
		}

		testSteps.add("Verified availablity sign in endpoint is 'N' after making rate plan inactive");
		unitLogger.info("Verified availablity sign in endpoint is 'N' after making rate plan inactive");

		testSteps.add("Update rate status to active");
		unitLogger.info("Update rate status to active");

		nightlyRate.ratePlanStatusChange(driver, true, testSteps);
		testSteps.addAll(ratesGrid.clickOnSaveratePlan(driver));
		derivedRate.closeOpenedRatePlanTab(driver, testSteps);
		try {
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			ratesGrid.clickOnSaveratePlan(driver);
			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
		} catch (Exception f) {

		}
		try {
			navigation.Rates_Grid(driver);
		} catch (Exception e) {
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			ratesGrid.clickOnSaveratePlan(driver);
			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
		}

		navigation.reservationBackward3(driver);
		testSteps.add("Navigate to Reservations");
		unitLogger.info("Navigate to Reservations");
		
	  testSteps.add("<b>=== " + "Verify endpoint when room class(" +
	  ratePlan.toUpperCase() + ") inactive".toUpperCase() + " ===</b>");
	  unitLogger.info("=== " + "Verify endpoint when room class(" +
	  ratePlan.toUpperCase() + ") inactive".toUpperCase() + " ===");
	  vrboObject.verifyVrboApiStatusCode(endPointName,
	  getUnitAvailabilityContentApi, vrboToken, statusCode, testSteps);
	 
	  response = vrboObject.getApiResponse(getUnitAvailabilityContentApi,
	  vrboToken); getAvilability = vrboObject.getAttributesValue(response,
	  "unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability");
	  
	  for(char c : getAvilability.toCharArray()) { 
		  assertEquals(c, "N", "Failed : Room is available after making rate plan inactive");
		  }
	  
	  testSteps.add("Verified availability sign in endpoint is 'N' when room class inactive"); 
	  unitLogger.info("Verified availability sign in endpoint is 'N' when room class inactive");

		/*
		 * testSteps.add("<b>=== " + "Verify endpoint after rate plan(" +
		 * ratePlan.toUpperCase() + ") deletion".toUpperCase() + " ===</b>");
		 * unitLogger.info("=== " + "Verify endpoint after rate plan(" +
		 * ratePlan.toUpperCase() + ") deletion".toUpperCase() + " ===");
		 * 
		 * navigation.RatesGrid(driver); testSteps.add("Navigated to RatesGrid");
		 * unitLogger.info("Navigated to RatesGrid");
		 * 
		 * try { ratesGrid.clickRatePlanArrow(driver, testSteps);
		 * ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlan); }
		 * catch (Exception e) { driver.navigate().refresh();
		 * ratesGrid.clickRatePlanArrow(driver, testSteps);
		 * ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlan); }
		 * nightlyRate.deleteNightlyRatePlan(driver, ratePlan, "Delete", testSteps);
		 * 
		 * testSteps.add( "Successfully verified deleted rate plan data in endpoint");
		 * unitLogger.info( "Successfully verified deleted rate plan data in endpoint");
		 * 
		 * vrboObject.verifyVrboApiStatusCode(endPointName,
		 * getUnitAvailabilityContentApi, vrboToken, statusCode, testSteps);
		 * 
		 * response = vrboObject.getApiResponse(getUnitAvailabilityContentApi,
		 * vrboToken); getAvilability = vrboObject.getAttributesValue(response,
		 * "unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability"
		 * );
		 * 
		 * for(char c : getAvilability.toCharArray()) { assertEquals(c, "N",
		 * "Failed : Room is available after making rate plan inactive"); }
		 * 
		 * testSteps.
		 * add("Verified availability sign in endpoint is 'N' after making rate plan inactive"
		 * ); unitLogger.
		 * info("Verified availability sign in endpoint is 'N' after making rate plan inactive"
		 * );
		 */
	}
public void verifyRoomAvaibiltyForMRB(WebDriver driver,ArrayList<String> testSteps,String checkInArr[],String defaultDateFormat,String timeZone
		,String vrboToken,String endPointName,String getUnitAvailabilityContentApi,String checkOutArr[] 
		, int statusCode200,int getDaysbetweenCurrentAndCheckInDate,
		
		ArrayList<String> checkInDatesList,ArrayList<String> checkOutDatesList,HashMap<Integer, ArrayList<Integer>> mapBeforeRes,int getChangeInCount,boolean isCheckOut ) throws Exception {
		VrboObjects vrboObject = new VrboObjects();
		HashMap<Integer, ArrayList<Integer>> mapAfterRes = new HashMap<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<Integer> availabilityCountList=new ArrayList();
		for (int i = 0; i < checkInArr.length; i++) {
			
			try {
				int getDaysBetweenCurrentAndCheckIn = Utility.getNumberofDays(
						Utility.getCurrentDate(defaultDateFormat, timeZone), checkInArr[i], defaultDateFormat);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int getDaysBetweenDates = Utility.getNumberofDays(checkInArr[i], checkOutArr[i], defaultDateFormat);

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps);
			Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			unitLogger.info(
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
							+ resp);
			ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
			String mrbArr[] = resp.split(",");
			unitLogger.info("getAvailabilityCount : " + mrbArr.length);
			for (int j = 0; j < mrbArr.length; j++) {
				if (j >= getDaysbetweenCurrentAndCheckInDate) {
					getAvailabilityCount.add(Integer.parseInt(mrbArr[j]));
				}
			}
			unitLogger.info("getAvailabilityCount : " + getAvailabilityCount);
			availabilityCountList.clear();
			if (getDaysBetweenDates > 0) {
				for (int j = 0; j < getDaysBetweenDates; j++) {
					availabilityCountList.add(getAvailabilityCount.get(j));
				}
				
			}
			unitLogger.info("availabilityCountBeforeResListFirstDateRange : " + availabilityCountList);

			mapAfterRes.put(i, availabilityCountList);
			
			unitLogger.info(availabilityCountList);

		}
		
   if(getChangeInCount!=0) {
		unitLogger.info("mapAfterRes : " + mapAfterRes);
		unitLogger.info("mapBeforeRes : " + mapBeforeRes);
		unitLogger.info("===== Verifying Availability Count Decreased =====".toUpperCase());
		testSteps.add("<b> ===== Verifying Availability Count Decreased ===== </b>".toUpperCase());
		unitLogger.info(checkInDatesList.size());
		for (int i = 0; i < checkInDatesList.size(); i++) {
			unitLogger.info(
					checkInDatesList.get(i) + " : " + checkOutDatesList.contains(checkInDatesList.get(i)));
			ArrayList<Integer> listBeforeRes = mapBeforeRes.get(i);
			ArrayList<Integer> listAfterRes = mapAfterRes.get(i);
			int occurrences = Collections.frequency(checkInDatesList, checkInDatesList.get(i));
			unitLogger.info("occurrences : " + occurrences);
			testSteps.add("occurrences : " + occurrences);
			unitLogger.info("Date at index '" + i + "'" + checkInDatesList.get(i));
			testSteps.add("Date at index '" + i + "'" + checkInDatesList.get(i));
			
			for (int j = 0; j < listBeforeRes.size(); j++) {
				unitLogger.info("Expected count for '" + checkInDatesList.get(i) + "' : " + listBeforeRes.get(i));
				unitLogger.info("Expected count for '" + checkInDatesList.get(i) + "' : " + listBeforeRes.get(j));
				testSteps.add("Expected count for '" + checkInDatesList.get(i) + "' : " + listBeforeRes.get(j));
				unitLogger.info("Found at date index '" + j + "' : " + listAfterRes.get(j));
				testSteps.add("Found at date index '" + j + "' : " + listAfterRes.get(j));
				Integer IncreaseInAfter=listAfterRes.get(j)+getChangeInCount;
				unitLogger.info(IncreaseInAfter);
//				assertEquals(listBeforeRes.get(j), IncreaseInAfter,
//						"Failed availability didn't decreased at point");
				
				}
		
			unitLogger.info("Verified Availability Count Decreased");
		testSteps.add("Verified Availability Count Decreased");

		}
}
else
{
	unitLogger.info("mapAfterRes : " + mapAfterRes);
	unitLogger.info("===== Verifying Availability Count Increase =====".toUpperCase());
	testSteps.add("<b> ===== Verifying Availability Count Increase===== </b>".toUpperCase());
	for (int i = 0; i < checkInDatesList.size(); i++) {
		unitLogger.info(
				checkInDatesList.get(i) + " : " + checkOutDatesList.contains(checkInDatesList.get(i)));
		ArrayList<Integer> listBeforeRes = mapBeforeRes.get(i);
		ArrayList<Integer> listAfterRes = mapAfterRes.get(i);
		int occurrences = Collections.frequency(checkInDatesList, checkInDatesList.get(i));
		unitLogger.info("occurrences : " + occurrences);
		testSteps.add("occurrences : " + occurrences);
		unitLogger.info("Date at index '" + i + "'" + checkInDatesList.get(i));
		testSteps.add("Date at index '" + i + "'" + checkInDatesList.get(i));
		
		for (int j = 0; j < listBeforeRes.size(); j++) {
			
			
			if(isCheckOut) {
				Integer IncreaseInAfter=listAfterRes.get(j)+getChangeInCount;
				
				testSteps.add("Expected count for '" + checkInDatesList.get(i) + "' : " + listBeforeRes.get(j));
				unitLogger.info("Found at date index '" + j + "' : " + IncreaseInAfter);
				testSteps.add("Found at date index '" + j + "' : " + IncreaseInAfter);
			}
			else {
				unitLogger.info("Expected count for '" + checkInDatesList.get(i) + "' : " + listBeforeRes.get(i));
				unitLogger.info("Expected count for '" + checkInDatesList.get(i) + "' : " + listBeforeRes.get(j));
				testSteps.add("Expected count for '" + checkInDatesList.get(i) + "' : " + listBeforeRes.get(j));
				unitLogger.info("Found at date index '" + j + "' : " + listAfterRes.get(j));
				testSteps.add("Found at date index '" + j + "' : " + listAfterRes.get(j));
//			assertEquals(listBeforeRes.get(j), listAfterRes.get(j),
//					"Failed availability didn't decreased at point");
		}
		}
	
		unitLogger.info("Verified Availability Count Increase");
	testSteps.add("Verified Availability Count Increase");

	}
	}

	
}
	
	public void verifyRoomAvailability(WebDriver driver, String endPointName, String getUnitAvailabilityContentApi,
			String vrboToken, int statusCode, ArrayList<String> testSteps, ArrayList<String> getDates,
			String defaultDateFormat, String dateFormatForRateGrid, String timeZone, String roomClassName,
			String ratePlanName, ArrayList<Integer> beforeReservedCountList,
			ArrayList<Integer> beforeAvailableRoomsCountList, ArrayList<Integer> availabilityCountBeforeResList,
			int getDaysbetweenCurrentAndCheckInDate, int getDaysbetweenDate, int changeInCount)
			throws InterruptedException, ParseException {

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		VrboObjects vrboObject = new VrboObjects();
		ArrayList<String> getTestSteps = new ArrayList<>();
		String afterReservedCount = "";
		String afterAvailableRooms = "";

		unitLogger.info("========== VERIFY IN AVAILABILITY TAB ==========".toUpperCase());
		testSteps.add("========== VERIFY IN AVAILABILITY TAB ==========".toUpperCase());

//		navigation.Inventory(driver);
//		testSteps.add("Navigate Inventory");
//		unitLogger.info("Navigate Inventory");
//
//		navigation.Rates_Grid(driver);
//		testSteps.add("Navigate Rates Grid");
//		unitLogger.info("Navigate Rates Grid");
//		try {
//			ratesGrid.clickOnAvailability(driver);
//		} catch (Exception f) {
//
//			navigation.Rates_Grid(driver);
//			ratesGrid.clickOnAvailability(driver);
//		}
//		testSteps.add("BeforeReservedCountList : " + beforeReservedCountList);
//		testSteps.add("beforeAvailableRoomsCountList : " + beforeAvailableRoomsCountList);
//
//		for (int i = 0; i < getDates.size() -1; i++) {
//			String calendarTodayDate = ESTTimeZone.reformatDate(getDates.get(i), defaultDateFormat,
//					dateFormatForRateGrid);
//
//			ratesGrid.clickCalendar(driver);
//			
//			ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
//
//			getTestSteps.clear();
//			ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone,
//					getTestSteps);
//			
//			int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);
//
//			getTestSteps.clear();
//			ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
//
//			int tempReserved = beforeReservedCountList.get(i);
//			tempReserved = tempReserved + changeInCount;
//			testSteps.add("Before Reserved Count : " + tempReserved);
//			unitLogger.info("Before Reserved Count : " + tempReserved);
//			afterReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
//			testSteps.add("After Reserved Count : " + afterReservedCount);
//			unitLogger.info("After Reserved Count : " + afterReservedCount);
//
//			// assertEquals(afterReservedCount, Integer.toString(tempReserved),
//			// "Failed : Reserved count is not increased after creating reservation");
//
//			tempReserved = beforeAvailableRoomsCountList.get(i);
//			if (tempReserved != 0) {
//				tempReserved = tempReserved - changeInCount;
//			}
//			testSteps.add("Before Available Count : " + tempReserved);
//			unitLogger.info("Before Available Count : " + tempReserved);
//			afterAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
//			testSteps.add("After Available Rooms : " + afterAvailableRooms);
//			unitLogger.info("After Available Rooms : " + afterAvailableRooms);
//
//			// assertEquals(afterAvailableRooms, Integer.toString(tempReserved),
//			// "Failed : Available Rooms is not decreased after creating reservation");
//			ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);
//
//		}
//		if (changeInCount > 0) {
//			unitLogger.info("Verified availability count decreased for specific date range in rategrid");
//			testSteps.add("Verified availability count decreased for specific date range in rategrid");
//		} else {
//			unitLogger.info("Verified availability count increased for specific date range in rategrid");
//			testSteps.add("Verified availability count increased for specific date range in rategrid");
//
//		}

		ArrayList<Integer> availabilityCountAfterResList = new ArrayList<>();

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		String resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);
		ArrayList<Integer> tempResponse = new ArrayList<>();
		tempResponse.clear();
		String tempArr[] = resp.split(",");
		unitLogger.info("tempResponse : " + tempArr.length);
		for (int i=0; i < tempArr.length; i++) {
			if (i >= getDaysbetweenCurrentAndCheckInDate) { 
				tempResponse.add(Integer.parseInt(tempArr[i]));
			}
		}
		/*
		 * if (getDaysbetweenCurrentAndCheckInDate > 0) { for (int j = 0; j <
		 * getDaysbetweenCurrentAndCheckInDate; j++) { tempResponse.remove(j); } }
		 */
		unitLogger.info("tempResponse : " + tempResponse);
		availabilityCountAfterResList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				availabilityCountAfterResList.add(tempResponse.get(j));
			}
		}

		if (changeInCount > 0) {
			unitLogger.info(
					"===== Verify availability count decreased for specific daterange in response =====".toUpperCase());
			testSteps.add("<b>"
					+ "===== VERIFY availability count decreased for specific daterange in response =====".toUpperCase()
					+ "</b>");
		} else {
			unitLogger.info(
					"===== Verify availability count increased for specific daterange in response =====".toUpperCase());
			testSteps.add("<b>"
					+ "===== VERIFY availability count increased for specific daterange in response =====".toUpperCase()
					+ "</b>");

		}
		unitLogger.info("availabilityCountBeforeResList : " + availabilityCountBeforeResList);
		unitLogger.info("availabilityCountAfterResList : " + availabilityCountAfterResList);

		for (int i = 0; i < availabilityCountBeforeResList.size(); i++) {
			int tempTwo = availabilityCountAfterResList.get(i);
			int tempOne = availabilityCountBeforeResList.get(i);
			if (tempOne != 0) {
				tempOne = tempOne - changeInCount;
			}
			unitLogger.info("Previous Room availability count : " + tempOne);
			testSteps.add("Previous Room availability count : " + tempOne);
			unitLogger.info("Current Room availbility count : " + tempTwo);
			testSteps.add("Current Room availbility count : " + tempTwo);
			// assertEquals(tempTwo, tempOne, "Failed : Availability count in response
			// didn't decrease");

		}
		if (changeInCount > 0) {
			unitLogger.info("Verified availability count decreased for specific daterange available in response");
			testSteps.add("Verified availability count decreased for specific daterange available in response");

		} else {
			unitLogger.info("Verified availability count increased for specific daterange available in response");
			testSteps.add("Verified availability count increased for specific daterange available in response");

		}

		try {
			navigation.reservationBackward3(driver);
			unitLogger.info("Navigate to Reservations");
			
		}catch (Exception e) {
			unitLogger.info(e.toString());
		}

	}

	public void verifyRoomMaintenanceForCurrentDate(WebDriver driver, String endPointName,
			String getUnitAvailabilityContentApi, String vrboToken, int statusCode, String subject, String timeZone,
			String roomNumber, String roomClassName, String detail, ArrayList<String> testSteps) throws Exception {
		RoomMaintenance rm = new RoomMaintenance();
		Navigation navigation = new Navigation();
		VrboObjects vrboObject = new VrboObjects();
		String dateFormat = "dd/MM/yyyy";
		String startDate = Utility.getCurrentDate(dateFormat, timeZone);
		String endDate = Utility.getNextDate(1, dateFormat, timeZone);
		int getDays = Utility.getNumberofDays(startDate, endDate, dateFormat);
		unitLogger.info(
				"===== Verified availability count reduce for start date '" + startDate + "' and end date start date '"
						+ endDate + "' in response after making room out of order =====".toUpperCase());
		testSteps.add("<b>" + "===== Verified availability count reduce for start date '" + startDate
				+ "' and end date start date '" + endDate
				+ "' in response after making room out of order =====".toUpperCase() + "</b>");

		unitLogger.info("===== Getting EndPoint Response BEFORE MAKING ROOM OUT OF ORDER =====".toUpperCase());
		testSteps.add("===== Getting EndPoint Response BEFORE MAKING ROOM OUT OF ORDER ===== ".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		String resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);
		int availabilityCountBeforeRes = Integer.parseInt(resp.substring(0, 1));

		unitLogger.info("Room availability count Before making out of order: " + availabilityCountBeforeRes);
		testSteps.add("Previous Room availability count Before making out of order : " + availabilityCountBeforeRes);

		navigation.navigateGuestservice(driver);
		testSteps.add("Clicked guest services");
		unitLogger.info("Clicked guest services");

		navigation.RoomMaintenance(driver);
		testSteps.add("Clicked room maintenance");
		unitLogger.info("Clicked room maintenance");

		rm.createOutOfOrderRoomItem(driver, startDate, endDate, dateFormat, subject, roomNumber, roomClassName,
				testSteps, detail);

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);
		unitLogger.info("getDays : " + getDays);
		int availabilityCountAfterRes = Integer.parseInt(resp.substring(0, getDays));
		unitLogger.info("availabilityCountBeforeRes : " + availabilityCountAfterRes);

		unitLogger.info("===== VERIFY availability count decreased '" + availabilityCountAfterRes
				+ "' in response after making room out of order =====".toUpperCase());
		testSteps.add("<b> ===== VERIFY availability count decreased '" + availabilityCountAfterRes
				+ "' in response after making room out of order ===== </b>".toUpperCase());

		unitLogger.info("Previous Room availability count : " + availabilityCountBeforeRes);
		testSteps.add("Previous Room availability count : " + availabilityCountBeforeRes);
		unitLogger.info("Current Room availbility count : " + availabilityCountAfterRes);
		testSteps.add("Current Room availbility count : " + availabilityCountAfterRes);

		assertEquals(availabilityCountBeforeRes - 1, availabilityCountAfterRes,
				"Failed : Availability count in response didn't decrease");
		unitLogger.info("Verified availability count decreased available in response");
		testSteps.add("Verified availability count decreased available in response");

		endDate = Utility.getNextDate(2, dateFormat, timeZone);
		unitLogger.info(
				"===== Verified availability count reduce for start date '" + startDate + "' and end date start date '"
						+ endDate + "' in response after extending room out of order end date =====".toUpperCase());
		testSteps.add("<b>" + "===== Verified availability count reduce for start date '" + startDate
				+ "' and end date start date '" + endDate
				+ "' in response after extending room out of order end date =====".toUpperCase() + "</b>");
		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		String availabilityCountAfterResStr = resp;

		Wait.waitforPageLoad(30, driver);
		getDays = Utility.getNumberofDays(startDate, endDate, dateFormat);

		rm.clickOnExistingRoomMaintenance(driver, subject, testSteps);
		rm.updateDate(driver, startDate, endDate, dateFormat, testSteps);

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);
		unitLogger.info("getDays : " + getDays);
		unitLogger.info("resp : " + resp);

		unitLogger.info(
				"===== VERIFY availability count in response after extending room out of order date to one day====="
						.toUpperCase());
		testSteps.add(
				"<b> ===== VERIFY availability count in response after extending room out of order date to one day ===== </b>"
						.toUpperCase());
		String[] availabilityArr = resp.split(",");
		String[] beforeAvailabilityArr = availabilityCountAfterResStr.split(",");

		availabilityCountBeforeRes = Integer.parseInt(beforeAvailabilityArr[1]);
		unitLogger.info("Previous Room availbility count : " + availabilityCountBeforeRes);
		testSteps.add("Previous Room availbility count : " + availabilityCountBeforeRes);

		int availabilityCountAfterExtendDate = Integer.parseInt(availabilityArr[1]);
		unitLogger.info("Current Room availbility count : " + availabilityCountAfterExtendDate);
		testSteps.add("Current Room availbility count : " + availabilityCountAfterExtendDate);
		assertEquals(availabilityCountAfterExtendDate, availabilityCountBeforeRes - 1,
				"Failed : Availability count in response didn't decrease");

		unitLogger.info("Verified availability count for start date '" + startDate + "' and end date start date '"
				+ endDate + "' in response after extending room out of order date to one day");
		testSteps.add("Verified availability count for start date '" + startDate + "' and end date start date '"
				+ endDate + "' in response after extending room out of order date to one day");

		endDate = Utility.getNextDate(1, dateFormat, timeZone);
		unitLogger
				.info("===== Verifying availability count for start date '" + startDate + "' and end date start date '"
						+ endDate + "' in response reducing room out of order end date=====".toUpperCase());
		testSteps.add("<b>" + "===== Verifying availability count for start date '" + startDate
				+ "' and end date start date '" + endDate
				+ "' in response reducing room out of order end date=====".toUpperCase() + "</b>");

		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		availabilityCountAfterResStr = resp;

		rm.clickOnExistingRoomMaintenance(driver, subject, testSteps);
		rm.updateDate(driver, startDate, endDate, dateFormat, testSteps);

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);
		unitLogger.info("getDays : " + getDays);
		unitLogger.info("availabilityCountBeforeRes : " + resp);

		unitLogger.info(
				"===== VERIFY availability count in end point after reducing room out of order date to one day====="
						.toUpperCase());
		testSteps.add(
				"<b> ===== VERIFY availability count in end point after reducing room out of order date to one day ===== </b>"
						.toUpperCase());

		availabilityArr = resp.split(",");
		beforeAvailabilityArr = availabilityCountAfterResStr.split(",");
		availabilityCountBeforeRes = Integer.parseInt(beforeAvailabilityArr[1]);
		unitLogger.info("Previous Room availbility count : " + availabilityCountBeforeRes);
		testSteps.add("Previous Room availbility count : " + availabilityCountBeforeRes);
		int availabilityCountAfterReduceDate = Integer.parseInt(availabilityArr[1]);
		unitLogger.info("Current Room availbility count : " + availabilityCountAfterReduceDate);
		testSteps.add("Current Room availbility count : " + availabilityCountAfterReduceDate);
		assertEquals(availabilityCountAfterReduceDate, availabilityCountBeforeRes + 1,
				"Failed : Availability count in response didn't decrease");

		unitLogger.info("Verified availability count in end point after reducing room out of order date to one day");
		testSteps.add("Verified availability count in end point after reducing room out of order date to one day");

		rm.DeleteRoomOutOfOrder(driver, subject);
	}

	public void verifyRoomMaintenance(WebDriver driver, String endPointName, String getUnitAvailabilityContentApi,
			String vrboToken, int statusCode, String subject, String startDate, String endDate,
			String defaultDateFormat, String timeZone, String roomNumber, String roomClassName, String detail,
			ArrayList<String> testSteps) throws Exception {
		RoomMaintenance rm = new RoomMaintenance();
		Navigation navigation = new Navigation();
		VrboObjects vrboObject = new VrboObjects();
		ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();
		ArrayList<Integer> availabilityCountAfterResList = new ArrayList<>();
		int getDaysbetweenCurrentAndCheckInDate = ESTTimeZone.numberOfDaysBetweenDates(
				Utility.getCurrentDate(defaultDateFormat, timeZone), startDate, defaultDateFormat) + 1;
		int getDaysbetweenDate = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate, defaultDateFormat) + 1;
		unitLogger.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
		unitLogger.info("getDaysbetweenDate : " + getDaysbetweenDate);

		unitLogger.info(
				"===== Verified availability count reduce for start date '" + startDate + "' and end date start date '"
						+ endDate + "' in response after making room out of order =====".toUpperCase());
		testSteps.add("<b>" + "===== Verified availability count reduce for start date '" + startDate
				+ "' and end date start date '" + endDate
				+ "' in response after making room out of order =====".toUpperCase() + "</b>");

		unitLogger.info("===== Getting EndPoint Response BEFORE MAKING ROOM OUT OF ORDER =====".toUpperCase());
		testSteps.add("===== Getting EndPoint Response BEFORE MAKING ROOM OUT OF ORDER ===== ".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		String resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);
		ArrayList<Integer> tempResponse = new ArrayList<>();
		tempResponse.clear();
		for (String s : resp.split(",")) {
			tempResponse.add(Integer.parseInt(s));
		}
		if (getDaysbetweenCurrentAndCheckInDate > 0) {
			for (int j = 0; j < getDaysbetweenCurrentAndCheckInDate; j++) {
				tempResponse.remove(j);
			}
		}
		unitLogger.info("tempResponse : " + tempResponse);
		availabilityCountBeforeResList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				availabilityCountBeforeResList.add(tempResponse.get(j));
			}
		}
		unitLogger.info("Room availability count before making out of order for specific date range : "
				+ availabilityCountBeforeResList);
		testSteps.add("Previous Room availability count before making out of order for specific date range : "
				+ availabilityCountBeforeResList);

		navigation.navigateGuestservice(driver);
		testSteps.add("Clicked guest services");
		unitLogger.info("Clicked guest services");

		navigation.RoomMaintenance(driver);
		testSteps.add("Clicked room maintenance");
		unitLogger.info("Clicked room maintenance");

		rm.createOutOfOrderRoomItem(driver, startDate, endDate, defaultDateFormat, subject, roomNumber, roomClassName,
				testSteps, detail);

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);
		tempResponse.clear();
		for (String s : resp.split(",")) {
			tempResponse.add(Integer.parseInt(s));
		}
		if (getDaysbetweenCurrentAndCheckInDate > 0) {
			for (int j = 0; j < getDaysbetweenCurrentAndCheckInDate; j++) {
				tempResponse.remove(j);
			}
		}
		unitLogger.info("tempResponse : " + tempResponse);
		availabilityCountAfterResList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				availabilityCountAfterResList.add(tempResponse.get(j));
			}
		}

		unitLogger.info("===== VERIFY availability count decreased in response after making room out of order ====="
				.toUpperCase());
		testSteps.add(
				"<b> ===== VERIFY availability count decreased in response after making room out of order ===== </b>"
						.toUpperCase());

		for (int i = 0; i < availabilityCountBeforeResList.size(); i++) {
			int tempOne = availabilityCountBeforeResList.get(i);
			int tempTwo = availabilityCountAfterResList.get(i);
			if (tempOne != 0) {
				tempOne = tempOne - 1;
			}
			unitLogger.info("Previous Room availability count : " + tempOne);
			testSteps.add("Previous Room availability count : " + tempOne);
			unitLogger.info("Current Room availbility count : " + tempTwo);
			testSteps.add("Current Room availbility count : " + tempTwo);
			assertEquals(tempTwo, tempOne, "Failed : Availability count in response didn't decrease");

		}
		unitLogger.info("Verified availability count decreased to zero available in response");
		testSteps.add("Verified availability count decreased to zero available in response");
		// ------- EXTENDING ROOM OUT OF DATE -------//

		String nextDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(endDate, defaultDateFormat, defaultDateFormat, 1,
				timeZone);
		getDaysbetweenCurrentAndCheckInDate = ESTTimeZone.numberOfDaysBetweenDates(
				Utility.getCurrentDate(defaultDateFormat, timeZone), startDate, defaultDateFormat) + 1;
		getDaysbetweenDate = ESTTimeZone.numberOfDaysBetweenDates(startDate, nextDate, defaultDateFormat) + 1;
		unitLogger.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate
				+ " getDaysbetweenDate : " + getDaysbetweenDate);

		unitLogger.info("===== Verified availability count reduce for start date '" + startDate + "' and end date '"
				+ nextDate + "' in response after extending room out of order end date to one day =====".toUpperCase());
		testSteps.add("<b>" + "===== Verified availability count reduce for start date '" + startDate
				+ "' and end date '" + nextDate
				+ "' in response after extending room out of order end date to one day =====".toUpperCase() + "</b>");

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");

		tempResponse.clear();
		for (String s : resp.split(",")) {
			tempResponse.add(Integer.parseInt(s));
		}
		if (getDaysbetweenCurrentAndCheckInDate > 0) {
			for (int j = 0; j < getDaysbetweenCurrentAndCheckInDate; j++) {
				tempResponse.remove(j);
			}
		}
		unitLogger.info("tempResponse : " + tempResponse);
		availabilityCountBeforeResList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				availabilityCountBeforeResList.add(tempResponse.get(j));
			}
		}

		Wait.waitforPageLoad(30, driver);
		rm.clickOnExistingRoomMaintenance(driver, subject, testSteps);
		rm.updateDate(driver, startDate, endDate, defaultDateFormat, testSteps);

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);

		tempResponse.clear();
		for (String s : resp.split(",")) {
			tempResponse.add(Integer.parseInt(s));
		}
		if (getDaysbetweenCurrentAndCheckInDate > 0) {
			for (int j = 0; j < getDaysbetweenCurrentAndCheckInDate; j++) {
				tempResponse.remove(j);
			}
		}
		unitLogger.info("tempResponse : " + tempResponse);
		availabilityCountAfterResList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				availabilityCountAfterResList.add(tempResponse.get(j));
			}
		}

		unitLogger.info(
				"===== VERIFY availability count in response after extending room out of order date to one day====="
						.toUpperCase());
		testSteps.add(
				"<b> ===== VERIFY availability count in response after extending room out of order date to one day ===== </b>"
						.toUpperCase());
		for (int i = 0; i < availabilityCountBeforeResList.size(); i++) {
			int tempOne = availabilityCountBeforeResList.get(i);
			int tempTwo = availabilityCountAfterResList.get(i);
			if (tempOne != 0) {
				tempOne = tempOne - 1;
			}
			unitLogger.info("Previous Room availability count : " + tempOne);
			testSteps.add("Previous Room availability count : " + tempOne);
			unitLogger.info("Current Room availbility count : " + tempTwo);
			testSteps.add("Current Room availbility count : " + tempTwo);
			assertEquals(tempTwo, tempOne,
					"Failed : Availability count in response didn't decrease after extending out of order date");
		}

		unitLogger.info("Verified availability count for start date '" + startDate + "' and end date '" + nextDate
				+ "' in response after extending room out of order date to one day");
		testSteps.add("Verified availability count for start date '" + startDate + "' and end date '" + nextDate
				+ "' in response after extending room out of order date to one day");

		// ------- REDUCING ROOM OUT OF ORDER DATE -------//

		unitLogger.info("===== Verifying availability count for start date '" + startDate + "' and end date '" + endDate
				+ "' in response reducing room out of order to ".toUpperCase() + endDate + " =====");
		testSteps.add("<b>" + "===== Verifying availability count for start date '" + startDate + "' and end date '"
				+ endDate + "' in response reducing room out of order to ".toUpperCase() + endDate + " =====</b>");

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");

		tempResponse.clear();
		for (String s : resp.split(",")) {
			tempResponse.add(Integer.parseInt(s));
		}
		if (getDaysbetweenCurrentAndCheckInDate > 0) {
			for (int j = 0; j < getDaysbetweenCurrentAndCheckInDate; j++) {
				tempResponse.remove(j);
			}
		}
		unitLogger.info("tempResponse : " + tempResponse);
		availabilityCountBeforeResList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				availabilityCountBeforeResList.add(tempResponse.get(j));
			}
		}

		rm.clickOnExistingRoomMaintenance(driver, subject, testSteps);
		rm.updateDate(driver, startDate, endDate, defaultDateFormat, testSteps);

		unitLogger.info("===== Getting EndPoint Response =====".toUpperCase());
		testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
		resp = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
		unitLogger.info(
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : " + resp);

		tempResponse.clear();
		for (String s : resp.split(",")) {
			tempResponse.add(Integer.parseInt(s));
		}
		if (getDaysbetweenCurrentAndCheckInDate > 0) {
			for (int j = 0; j < getDaysbetweenCurrentAndCheckInDate; j++) {
				tempResponse.remove(j);
			}
		}
		unitLogger.info("tempResponse : " + tempResponse);
		availabilityCountAfterResList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				availabilityCountAfterResList.add(tempResponse.get(j));
			}
		}

		unitLogger.info(
				"===== VERIFY availability count in end point after reducing room out of order date to ".toUpperCase()
						+ endDate + " =====");
		testSteps.add("<b>" + " ===== VERIFY availability count in end point after reducing room out of order date to "
				.toUpperCase() + endDate + " =====</b>");

		for (int i = 0; i < availabilityCountBeforeResList.size(); i++) {
			int tempOne = availabilityCountBeforeResList.get(i);
			int tempTwo = availabilityCountAfterResList.get(i);

			if (i == availabilityCountBeforeResList.size() - 1) {
				unitLogger.info("Previous Room availability count : " + (tempOne + 1));
				testSteps.add("Previous Room availability count : " + (tempOne + 1));
				unitLogger.info("Current Room availbility count : " + tempTwo);
				testSteps.add("Current Room availbility count : " + tempTwo);

				assertEquals(tempTwo, tempOne + 1,
						"Failed : Availability count in response didn't reduce after recuding room out of order date");
			} else {
				if (tempOne != 0) {
					tempOne = tempOne - 1;
				}
				unitLogger.info("Previous Room availability count : " + tempOne);
				testSteps.add("Previous Room availability count : " + tempOne);
				unitLogger.info("Current Room availbility count : " + tempTwo);
				testSteps.add("Current Room availbility count : " + tempTwo);
				assertEquals(tempTwo, tempOne,
						"Failed : Availability count in response didn't reduce after recuding room out of order date");
			}

		}

		unitLogger.info("Verified availability count in end point after reducing room out of order date to " + endDate);
		testSteps.add("Verified availability count in end point after reducing room out of order date to " + endDate);

		rm.DeleteRoomOutOfOrder(driver, subject);
	}

	public void changeRoomClass(WebDriver driver, ArrayList<String> testSteps, String reservationNumber,
			String roomClassName, String reservationType, String account, String isAssign)
			throws InterruptedException, ParseException {

		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();

		ArrayList<String> getTestSteps = new ArrayList<>();
		reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
		testSteps.add("Searched and opened reservation with number : " + reservationNumber);
		unitLogger.info("Searched and opened reservation with number : " + reservationNumber);

		unitLogger.info("===== " + "Changing room class with 'No Rate Change' checked for reservation to '"
				+ roomClassName + "'".toUpperCase() + " =====");
		testSteps.add("===== " + "Changing room class with 'No Rate Change' checked for reservation to '"
				+ roomClassName + "'".toUpperCase() + " =====");

		if (reservationType.equalsIgnoreCase("mrb")) {

			getTestSteps.clear();
			getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
			testSteps.addAll(getTestSteps);

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, isAssign, "");

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
			testSteps.addAll(getTestSteps);

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, isAssign, "");

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
			testSteps.addAll(getTestSteps);

		} else {
			reservationPage.ClickEditStayInfo(driver, testSteps);
			reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
			testSteps.addAll(reservationPage.clickFindRooms(driver));
			if (reservationType.equalsIgnoreCase("Account")) {
				reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "Yes", account);
			} else {
				reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "Yes", "");
			}

			testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));
		}

		reservationPage.closeReservationTab(driver);
		testSteps.add("Closed opened reservation");
		unitLogger.info("Closed opened reservation");

	}

	public void verifyMinStayRuleRatePlan(WebDriver driver, String endPointName, String getUnitAvailabilityContentApi,
			String vrboToken, int statusCode, ArrayList<String> testSteps, String ratePlan, String roomClassName,
			int ratePlanLevelRuleValue, int seasonLevelRuleValue, String checkInDate, String checkOutDate, String defaultDateFormat, String timeZone, Boolean isRestrictionApplied,
			Boolean isMinStay, String seasonIsAssignRulesByRoomClass) throws Exception {
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		VrboObjects vrboObject = new VrboObjects();

		String seasonName = "seasonName" + Utility.GenerateRandomString();
		String isSerasonLevelRules = "Yes";
		String isAssignRulesByRoomClass = "Yes";
		String restrictionType = "Length of stay";

		if(seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			isSerasonLevelRules = "No";
			isAssignRulesByRoomClass = "No";
				
		}
		String seasonStartDate = Utility.getCurrentDate("dd/M/yyyy", timeZone);
		String seasonEndDate = Utility.getNextDate(2, "dd/M/yyyy", timeZone);

		seasonStartDate = ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/M/yyyy");
		seasonEndDate = ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/M/yyyy");
		navigation.Inventory(driver, testSteps);
		testSteps.add("Navigate inventory");
		navigation.Rates_Grid(driver);
		testSteps.add("Navigate rates grid");
		int getDaysbetweenCurrentAndCheckInDate = ESTTimeZone.numberOfDaysBetweenDates(
				Utility.getCurrentDate("dd/M/yyyy", timeZone), seasonStartDate, "dd/M/yyyy");
		int getDaysbetweenDate = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate, "dd/M/yyyy");
		unitLogger.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
		unitLogger.info("getDaysbetweenDate : " + getDaysbetweenDate);
		if (isMinStay == true &&  isRestrictionApplied == true && seasonIsAssignRulesByRoomClass.equalsIgnoreCase("Yes")) {
			testSteps.add("<b>=== " + "Verify season level rule value applied in endpoint after changing rate plan("
					+ ratePlan.toUpperCase() + ") level and season level min stay rule value".toUpperCase()
					+ " ===</b>");
			unitLogger.info("=== " + "Verify season level rule value applied in endpoint after changing rate plan("
					+ ratePlan.toUpperCase() + ") level and season level min stay rule value".toUpperCase() + " ===");
		}else if(isMinStay == true &&  isRestrictionApplied == true && seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			testSteps.add("<b>=== " + "Verify rate level rule value applied in endpoint after changing rate plan("
					+ ratePlan.toUpperCase() + ") level and season level min stay rule value".toUpperCase()
					+ " ===</b>");
			unitLogger.info("=== " + "Verify rate level rule value applied in endpoint after changing rate plan("
					+ ratePlan.toUpperCase() + ") level and season level min stay rule value".toUpperCase() + " ===");
			
		}else if(isMinStay == false &&  isRestrictionApplied == false && seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			testSteps.add("<b>=== " + "Verifyno rule value applied in endpoint after changing rate plan("
					+ ratePlan.toUpperCase() + ") level and season level min stay rule value".toUpperCase()
					+ " ===</b>");
			unitLogger.info("=== " + "Verify no rule value applied in endpoint after changing rate plan("
					+ ratePlan.toUpperCase() + ") level and season level min stay rule value".toUpperCase() + " ===");
			
			
		}

		try {
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlan);
		} catch (Exception e) {
			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlan);
		}

		ratesGrid.clickOnEditRatePlan(driver);
		testSteps.add("Click on edit rate plan");
		unitLogger.info("Click on edit rate plan");

		testSteps.addAll(ratesGrid.ratePlanOverView(driver));
		nightlyRate.switchRestrictionAndPoliciesTab(driver, testSteps);

		testSteps.add("=================== RESTRICTION UPDATE ======================");
		unitLogger.info("===================  RESTRICTION UPDATE ======================");
		nightlyRate.selectRestrictionTypes(driver, restrictionType, isRestrictionApplied, testSteps);
		nightlyRate.lengthOfStay(driver, restrictionType, isMinStay, Integer.toString(ratePlanLevelRuleValue), false, "",
				testSteps);

		testSteps.add("=================== UPDATE SEASON(" + seasonName + ") ======================");
		unitLogger.info("=================== UPDATE SEASON(" + seasonName + ") ======================");
		ratesGrid.clickOnSeasonTab(driver);
		nightlyRate.selectSeasonDates(driver, testSteps, seasonStartDate, seasonEndDate);
		nightlyRate.enterSeasonName(driver, testSteps, seasonName);

		nightlyRate.selectSeasonDays(driver, testSteps, "Yes", "Yes", "Yes", "Yes", "Yes", "Yes", "Yes");
		try {
			nightlyRate.clickReplaceSeason(driver, testSteps);
		} catch (Exception e) {

		}
		nightlyRate.clickCreateSeason(driver, testSteps);
		nightlyRate.selectSeasonColor(driver, testSteps);
		ratesGrid.enterRoomClassRates(driver, roomClassName, "200", "", "", "", "", "", testSteps);
		nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);
		nightlyRate.clickAssignRulesByRoomClass(driver, testSteps, seasonIsAssignRulesByRoomClass);
		nightlyRate.enterSeasonLevelRule(driver, testSteps, isSerasonLevelRules, isAssignRulesByRoomClass,
				roomClassName, "Min Nights", Integer.toString(seasonLevelRuleValue), "Yes", "Yes", "Yes", "Yes", "Yes",
				"Yes", "Yes");

		nightlyRate.clickSaveSason(driver, testSteps);
		testSteps.addAll(ratesGrid.clickOnSaveratePlan(driver));
		testSteps.add("Updated rate plan rules successfully");
		unitLogger.info("Updated rate plan rules successfully");

		if ((isMinStay == true &&  isRestrictionApplied == true
				&& seasonIsAssignRulesByRoomClass.equalsIgnoreCase("Yes"))
				|| (isMinStay == false &&  isRestrictionApplied == false
						&& seasonIsAssignRulesByRoomClass.equalsIgnoreCase("Yes"))) {
			unitLogger.info(" ======Verifying season level min stay rule value '"
					+ Integer.toString(seasonLevelRuleValue) + "' applied in response ======".toUpperCase());
			testSteps.add("<b>" + " ======Verifying season level min stay rule value '"
					+ Integer.toString(seasonLevelRuleValue) + "' applied in response ======".toUpperCase() + "</b>");
		}
		else if(isMinStay == true &&  isRestrictionApplied == true &&
				seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			unitLogger.info(" ======Verifying rate plan level min stay rule value '"
					+ Integer.toString(ratePlanLevelRuleValue) + "' applied in response ======".toUpperCase());
			testSteps.add("<b>" + " ======Verifying rate plan level min stay rule value '"
					+ Integer.toString(ratePlanLevelRuleValue) + "' applied in response ======".toUpperCase() + "</b>");
		}
		else if(isMinStay == false &&  isRestrictionApplied == false &&
				seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			unitLogger.info("====== Verifying min stay rule value is '1' after unchecking rateplan level and season level restriction in response");
			testSteps.add("<b>" + " ======Verifying min stay rule value is '1' after unchecking rateplan level and season level restriction in response ======".toUpperCase() + "</b>");
		}

		vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode,
				testSteps);
		Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

		String getMinStayRuleValue = vrboObject.getAttributesValue(response,
				"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.minStay");
		unitLogger.info("getMinStayRuleValue : " + getMinStayRuleValue);
		
		ArrayList<Integer> tempResponse = new ArrayList<>();
		ArrayList<Integer> minStayRuleList = new ArrayList<>();
		tempResponse.clear();
		String tempArr[] = getMinStayRuleValue.split(",");
		unitLogger.info("tempResponse : " + tempArr.length);
		for (int i=0; i < tempArr.length; i++) {
			if (i >= getDaysbetweenCurrentAndCheckInDate) { 
				tempResponse.add(Integer.parseInt(tempArr[i]));
			}
		}
		unitLogger.info("tempResponse : " + tempResponse.size());
		unitLogger.info("tempResponse : " + tempResponse);
		/*
		 * for (int j = 0; j < 4; j++) { tempResponse.remove(j); tempResponse.
		 * unitLogger.info("tempResponse : " + j + " : " + tempResponse);
		 * unitLogger.info("tempResponse : " + tempResponse.size()); }
		 */
		//}
		unitLogger.info("tempResponse : " + tempResponse);
		minStayRuleList.clear();
		if (getDaysbetweenDate > 0) {
			for (int j = 0; j < getDaysbetweenDate; j++) {
				minStayRuleList.add(tempResponse.get(j));
			}
		}
		unitLogger.info("getMinStayRuleValue : " + minStayRuleList);

		if(seasonIsAssignRulesByRoomClass.equalsIgnoreCase("Yes")) {
			unitLogger.info("Expected Min Stay Rule Value : " + seasonLevelRuleValue);
			testSteps.add("Expected Min Stay Rule Value : " + seasonLevelRuleValue);

			for (int i = 0; i < getDaysbetweenDate; i++) {
				unitLogger.info("Found : " + minStayRuleList.get(i));
				testSteps.add("Found : " + minStayRuleList.get(i));

				if (Integer.toString(seasonLevelRuleValue).isEmpty()
						|| Integer.toString(seasonLevelRuleValue).equalsIgnoreCase("")) {

				//	assertEquals(String.valueOf(minStayRuleList.get(i)), "1",
				//			"Failed : Min Stay Rule Value didn't match");
				} else {
				//	assertEquals(String.valueOf(minStayRuleList.get(i)), Integer.toString(seasonLevelRuleValue),
				//			"Failed : Min Stay Rule Value didn't match");
				}
			}
		}else if(isMinStay == true &&  isRestrictionApplied == true &&
				seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			unitLogger.info("Expected Min Stay Rule Value : " + ratePlanLevelRuleValue);
			testSteps.add("Expected Min Stay Rule Value : " + ratePlanLevelRuleValue);

			for (int i = 0; i < getDaysbetweenDate; i++) {
				unitLogger.info("Found : " + minStayRuleList.get(i));
				testSteps.add("Found : " + minStayRuleList.get(i));

				if (Integer.toString(ratePlanLevelRuleValue).isEmpty()
						|| Integer.toString(ratePlanLevelRuleValue).equalsIgnoreCase("")) {

				//	assertEquals("1", String.valueOf(minStayRuleList.get(i)),
				//			"Failed : Min Stay Rule Value didn't match");
				} else {
				//	assertEquals(Integer.toString(ratePlanLevelRuleValue),String.valueOf(minStayRuleList.get(i)), 
				//			"Failed : Min Stay Rule Value didn't match");
				}
			}

		}
		else if(isMinStay == false &&  isRestrictionApplied == false &&
				seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			unitLogger.info("Expected Min Stay Rule Value : " + "1");
			testSteps.add("Expected Min Stay Rule Value : " + "1");

			for (int i = 0; i < getDaysbetweenDate; i++) {
				unitLogger.info("Found : " + minStayRuleList.get(i));
				testSteps.add("Found : " + minStayRuleList.get(i));
			//	assertEquals(String.valueOf(minStayRuleList.get(i)), "1",
			//			"Failed : Min Stay Rule Value didn't match");
			}		
		}

		if ((isMinStay == true &&  isRestrictionApplied == true && 
				seasonIsAssignRulesByRoomClass.equalsIgnoreCase("Yes"))
				|| (isMinStay == false &&  isRestrictionApplied == false && 
						seasonIsAssignRulesByRoomClass.equalsIgnoreCase("Yes"))) {
			unitLogger.info("Verified season level min stay rule value '" + Integer.toString(seasonLevelRuleValue)
					+ "' applied in response");
			testSteps.add("Verified season level min stay rule value '" + Integer.toString(seasonLevelRuleValue)
					+ "' applied in response");
		}
		else if(isMinStay == true &&  isRestrictionApplied == true &&
				seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			unitLogger.info("Verified rateplan level min stay rule value '" + Integer.toString(ratePlanLevelRuleValue)
			+ "' applied in response");
			testSteps.add("Verified rateplan level min stay rule value '" + Integer.toString(ratePlanLevelRuleValue)
			+ "' applied in response");

		}
		else if(isMinStay == false &&  isRestrictionApplied == false &&
				seasonIsAssignRulesByRoomClass.equalsIgnoreCase("No")) {
			unitLogger.info("Verified min stay rule value is '1' after unchecking rateplan level and season level restriction in response");
			testSteps.add("Verified min stay rule value is '1' after unchecking rateplan level and season level restriction in response");
		}
		
		navigation.reservationBackward3(driver);
		unitLogger.info("Navigate to Reservations");
	
	}

}
