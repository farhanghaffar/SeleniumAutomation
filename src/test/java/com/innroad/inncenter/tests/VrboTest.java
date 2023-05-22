package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.endpoints.VrboEndPoints;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

import io.restassured.response.Response;

public class VrboTest extends TestCore{
	
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	private WebDriver driver = null;
	VrboObjects vrboObject= new VrboObjects();
	
	HashMap<String, String> data= new HashMap<String, String>();
	
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}


	@Test
	public void verifyAdvertiserIndex() {
		String	testName=null;
		try {
		testName = "Verify Vrbo Client Level";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if (!Utility.insertTestName.containsKey(testName)) {
			Utility.insertTestName.put(testName, testName);
			Utility.reTry.put(testName, 0);

		} else {
			Utility.reTry.replace(testName, 1);
		}
		driver = getDriver();
		data=vrboObject.getVrboStatusCode(vrvoToken);
		testSteps.add(data.get("APIURL"));
		vrboObject.veifyVrboClientStatusCode(Integer.parseInt(data.get("StatusCode")), testSteps);
		
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		
	}catch (Exception e) {
		
		  Utility.catchExceptionOTA( e, test_description, test_catagory,
		  "Verify Vrbo Client Level", testName, test_description, test_catagory,
		  testSteps);
		 
		
	} catch (Error e) {
		
		  Utility.catchErrorOTA(e, test_description, test_catagory,
		  "Verify Vrbo Client Level", testName, test_description, test_catagory,
		  testSteps);
		 
		
	}
		
	
}
	
	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyClientLevel(String clientId) {
		String	testName=null;
		data.clear();
		try {
			testName = "Verify Vrbo Client Level Listing";
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			app_logs.info(vrvoToken);
			data=	vrboObject.getVrboClientStatusCode(clientId, vrvoToken);
			testSteps.add(data.get("APIURL"));
			vrboObject.veifyVrboClientStatusCode(Integer.parseInt(data.get("StatusCode")), testSteps);
			
		}catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Verify Vrbo Client Level", testName,
					test_description, test_catagory, testSteps);
			
		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Verify Vrbo Client Level", testName,
					test_description, test_catagory, testSteps);
			
		}
		
	}



	
	@Test(dataProvider = "getData2", groups = "Vrbo")
	public void verifyUnitAvailabilityContent(String propertyId, String roomClassId, String ratePlanId) {
/*
		HashMap<String, String> jsonMap = new HashMap<>();
		jsonMap.put("propertyId", propertyId);
		Response response = null;
		String getUnitAvailabilityContentApi = VrboEndPoints.generateVrboUnitAvailabilityContentEndPoint(propertyId, roomClassId, ratePlanId);
		String endPointName = "unitAvailabilityContent";
		app_logs.info("Endpoint Name: " + endPointName);
		app_logs.info("unitAvailabilityContent : " + getUnitAvailabilityContentApi );
		
		try {
			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, 403, testSteps);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Failed to verify unit availability content status code", testName,
					test_description, test_catagory, testSteps);		
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Failed to verify unit availability content status code", testName,
					test_description, test_catagory, testSteps);			
		}

		try {
			response = vrboObject.getApiResponse(getUnitAvailabilityContentApi);
			testSteps.add(response.asString());
	
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Failed to verify unit availability content response", testName,
					test_description, test_catagory, testSteps);		
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Failed to verify unit availability content response", testName,
					test_description, test_catagory, testSteps);			
		}

		try {
			JSONObject jObject = vrboObject.generateJsonObject(jsonMap);
			vrboObject.verifyPostRequest(getUnitAvailabilityContentApi, jObject, testSteps);
	
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Failed to verify unit availability content post request", testName,
					test_description, test_catagory, testSteps);		
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Failed to verify unit availability content post request", testName,
					test_description, test_catagory, testSteps);			
		}

		try {
			vrboObject.verifyDeleteRequest(getUnitAvailabilityContentApi, testSteps);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Failed to verify unit availability content delete request", testName,
					test_description, test_catagory, testSteps);		
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Failed to verify unit availability content delete request", testName,
					test_description, test_catagory, testSteps);			
		}
				
		
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Failed to add steps into report", testName,
					test_description, test_catagory, testSteps);
			
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Failed to add steps into report", testName,
					test_description, test_catagory, testSteps);
			
		}
*/		
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VrboTest", otaexcel);
	}
	
	@DataProvider
	public Object[][] getData2() {
		// return test data from the sheet name provided
		return Utility.getData("vrboUnitAvailabilityContent", otaexcel);
	}


}
