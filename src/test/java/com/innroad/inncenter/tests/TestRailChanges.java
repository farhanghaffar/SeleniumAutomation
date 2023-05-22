package com.innroad.inncenter.tests;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import org.testng.annotations.Test;


import com.innroad.inncenter.utils.APIClient;
import com.innroad.inncenter.utils.APIException;




public class TestRailChanges {


	private static String getAuthorization(String user, String password)
	{
		try 
		{
			return new String(Base64.getEncoder().encode((user + ":" + password).getBytes()));
		}
		catch (IllegalArgumentException e)
		{
			// Not thrown
		}
		
		return "";
	}
	@Test
	public void sendPost() throws Exception {
     
		URL url = new URL("https://innroad.testrail.io/index.php?/api/v2/add_case/59407");
		System.out.println("URL : '"+url + "'");
		// Create the connection object and set the required HTTP method
		// (GET/POST) and headers (content type and basic auth).
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		String auth = getAuthorization("gangotri.sikheria@innroad.com", "Vagmi@14");
		conn.addRequestProperty("Authorization", "Basic " + auth);
		conn.setRequestMethod("POST");
		conn.addRequestProperty("Content-Type", "application/json");
		//conn.setRequestProperty("Content-Type", "application/json; utf-8");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		ArrayList<Object> sectionid= new ArrayList<Object> ();
		sectionid.add(5);
		//sectionid.add(2);
		JSONObject json = new JSONObject();
		json.put("section_id", "59407");
		json.put("title", "Test Automation BY Gangotri");
		json.put("refs", "RPT-468");
		json.put("template_id", "Test Case (Steps)");
		json.put("custom_configuration", sectionid);
		
		String jsonInputString = json.toString();
		System.out.println(jsonInputString);
		
		
		try(OutputStream os = conn.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    os.write(input, 0, input.length);			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(conn.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    System.out.println("Response Is---"+response.toString());
				}catch(Exception e) {
					e.printStackTrace();
				}
    }
	
	/*public void testRailComponentAndtestTypeChange(ArrayList<String> caseId,
			Object[] sectionid, ArrayList<String> typeid) {
		
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		try {
			JSONArray ja = new JSONArray();
			ja.put(3);
			ja.put(4);
			APIClient client = null;
		client = new APIClient("https://innroad.testrail.io");
		client.setUser("gangotri.sikheria@innroad.com");
		client.setPassword("Vagmi@14");
					data.put("custom_testtype", ja);
					//data.put("section_id", "59407");
					data.put("title", "Test Automation BY Gangotri");
					data.put("refs", "RPT-468");
					data.put("custom_reviewed", "3");
					data.put("custom_configuration", ja);
					data.put("custom_automation", "4");
					data.put("custom_component", ja);
				try {
					client.sendPost("add_case/" + caseId.get(0), data);
					Object response=client.sendGet("add_case/" + caseId.get(0));
					System.out.println("Response- " +response.toString());
					} catch (IOException | APIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCaseChange() {
		ArrayList<String> caseId= new ArrayList<String> ();
		Object [] sectionid= {"1","2"};
		ArrayList<String> typeid= new ArrayList<String> ();
		
		caseId.add("59407");
//		sectionid.add("1");
//		sectionid.add("2");
		
		//typeid.add("8");
		testRailComponentAndtestTypeChange(caseId,sectionid,typeid);
		//app_logs.info("Updated Successfully");
		
	}
*/
}
