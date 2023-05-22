package com.innroad.inncenter.tests;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class CreateSecurityRequestBody {
	
	public static Logger securityLogger = Logger.getLogger("CreateSecurityRequestBody");
	JSONObject json = new JSONObject();
	public String loginJson(String clientID, String userName, String password) {
		String loginRequestBody=null;
		json.put("Clientcode", clientID);
		json.put("LoginID", userName);
		json.put("UserPassword", password);
		securityLogger.info(json.toString());
		loginRequestBody=json.toString();		
		return loginRequestBody;		
	}

	
	public String entitlementAndSecurityJson(String iRegistrationNo,String transactionId) {
		String entitlementAndSecurityRequestBody=null;
		json.put("iRegistrationNo", iRegistrationNo);
		json.put("Entity", "");
		json.put("TransactionId", transactionId);
		securityLogger.info(json.toString());
		entitlementAndSecurityRequestBody=json.toString();
		return entitlementAndSecurityRequestBody;		
	}
	
	
	
	public  String createPaymentMethodJson(String folioId, String paymentMethodType,String referenceTypeId,String billingAddressJsonObj,String ledgerAccountId,
			String NameonCard, String expiryDate,
			String cardNumnber, String lastFourDigit) throws Exception { 
		String paymentMethod=null;
		 JSONObject authorJsonObj=new JSONObject(); 
		 List<String> recordSource =
				    Arrays.asList(billingAddressJsonObj);
				List<JSONObject> records =
				    recordSource.stream().map(JSONObject::new).collect(Collectors.toList());
		 authorJsonObj.put("billingAddress",records.get(0));
		authorJsonObj.put("folioId",Integer.parseInt(folioId));
		authorJsonObj.put("paymentMethodType",Integer.parseInt(paymentMethodType));
		authorJsonObj.put("referenceTypeId",Integer.parseInt(referenceTypeId));	
		JSONObject creditCardJsonObj=new JSONObject();  	
		creditCardJsonObj.put("expiryDate",expiryDate);
		creditCardJsonObj.put("id",0);
		creditCardJsonObj.put("lastFourDigits",lastFourDigit);
		creditCardJsonObj.put("ledgerAccountId",Integer.parseInt(ledgerAccountId));
		creditCardJsonObj.put("name",NameonCard);
		creditCardJsonObj.put("number",cardNumnber);
		authorJsonObj.put("creditCard",creditCardJsonObj ); 
		paymentMethod=authorJsonObj.toString();
		securityLogger.info(paymentMethod);
		return paymentMethod; 
		} 
		 
	
}
