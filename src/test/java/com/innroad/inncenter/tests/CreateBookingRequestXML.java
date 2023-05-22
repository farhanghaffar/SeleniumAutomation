package com.innroad.inncenter.tests;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.innroad.inncenter.utils.Utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateBookingRequestXML {

	public File createBookingRequest(String testCaseName, String version, String advertiserIds, String classId,
			String channels, String messages, String titles, String firstNames, String lastNames, String email,
			String phoneNo, String address1, String address2, String address3, String address4, String countryName,
			String adults, String childs, String startDate, String endDates, String originationDate, String currency,
			String changeName, String dueDates, String paymentType, String postalCodes, String cvvs, String expiryDate,
			String nameONCard, String cardNo, String cardCodes, String cardTypes, String schemaFilesPath,
			String IsTaxAdd, String TaxNameInXml, String TaxAmountInXml, String setTaxRate, String isExtraAdultInXml,
			String extraAdultAmount, String isExtraChildInXml, String extraChildAmount, String isFeeAdd,
			String feeNameInXml, String feeAmounts, String setFeeRate, String taxExternalId, String feeExternalId,
			String roomchargeAmount, String paymentSheduleItemAmount, String masterListingChannel,
			String clientIPAddress, String trackingUuid, String numberToken) {
		File xmlDataInFile = null;
		ArrayList<String> taxesName = new ArrayList<String>();
		ArrayList<String> taxesAmount = new ArrayList<String>();
		ArrayList<String> taxesExternal = new ArrayList<String>();
		ArrayList<String> feeName = new ArrayList<String>();
		ArrayList<String> feeAmount = new ArrayList<String>();
		ArrayList<String> feeExternal = new ArrayList<String>();

		ArrayList<String> setTaxRates = new ArrayList<String>();
		ArrayList<String> setFeeRates = new ArrayList<String>();
		if (IsTaxAdd.equalsIgnoreCase("Yes")) {

			taxesName = Utility.convertTokenToArrayList(TaxNameInXml, Utility.DELIMS);
			taxesAmount = Utility.convertTokenToArrayList(TaxAmountInXml, Utility.DELIMS);
			taxesExternal = Utility.convertTokenToArrayList(taxExternalId, Utility.DELIMS);
			setTaxRates = Utility.convertTokenToArrayList(setTaxRate, Utility.DELIMS);
		}
		if (isFeeAdd.equalsIgnoreCase("Yes")) {
			feeName = Utility.convertTokenToArrayList(feeNameInXml, Utility.DELIMS);
			feeAmount = Utility.convertTokenToArrayList(feeAmounts, Utility.DELIMS);
			feeExternal = Utility.convertTokenToArrayList(feeExternalId, Utility.DELIMS);
			setFeeRates = Utility.convertTokenToArrayList(setFeeRate, Utility.DELIMS);
		}
		HashMap<String, String> feeType = new HashMap<String, String>();
		feeType.put("tax", "TAX");
		feeType.put("child", "RENTAL");
		feeType.put("adult", "RENTAL");
		feeType.put("fee", "MISC");

		HashMap<String, String> productId = new HashMap<String, String>();
		productId.put("TAX", "TAX");
		productId.put("RENTAL", "RENT");
		productId.put("MISC", "PROPERTY-FEE");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element bookingRequest = doc.createElement("bookingRequest");
			doc.appendChild(bookingRequest);

			Element documentVersion = doc.createElement("documentVersion");
			documentVersion.appendChild(doc.createTextNode(version));
			bookingRequest.appendChild(documentVersion);

			// supercars element
			Element bookingRequestDetails = doc.createElement("bookingRequestDetails");
			bookingRequest.appendChild(bookingRequestDetails);

			Element advertiserId = doc.createElement("advertiserAssignedId");
			advertiserId.appendChild(doc.createTextNode(advertiserIds));
			bookingRequestDetails.appendChild(advertiserId);

			Element externalID = doc.createElement("listingExternalId");
			externalID.appendChild(doc.createTextNode(classId));
			bookingRequestDetails.appendChild(externalID);

			Element unitexternalID = doc.createElement("unitExternalId");
			unitexternalID.appendChild(doc.createTextNode(classId));
			bookingRequestDetails.appendChild(unitexternalID);

			Element inquiryId = doc.createElement("inquiryId");
			inquiryId.appendChild(doc.createTextNode("dummy"));
			bookingRequestDetails.appendChild(inquiryId);

			Element propertyUrl = doc.createElement("propertyUrl");
			propertyUrl.appendChild(doc.createTextNode("dummy"));
			bookingRequestDetails.appendChild(propertyUrl);

			Element channel = doc.createElement("listingChannel");
			channel.appendChild(doc.createTextNode(channels));
			bookingRequestDetails.appendChild(channel);

			Element masterListingChannels = doc.createElement("masterListingChannel");
			masterListingChannels.appendChild(doc.createTextNode(masterListingChannel));
			bookingRequestDetails.appendChild(masterListingChannels);

			Element clientIPAddresss = doc.createElement("clientIPAddress");
			clientIPAddresss.appendChild(doc.createTextNode(clientIPAddress));
			bookingRequestDetails.appendChild(clientIPAddresss);

			Element message = doc.createElement("message");
			message.appendChild(doc.createTextNode(messages));
			bookingRequestDetails.appendChild(message);

			Element inquirer = doc.createElement("inquirer");
			bookingRequestDetails.appendChild(inquirer);

			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(titles));
			inquirer.appendChild(title);

			Element firstName = doc.createElement("firstName");
			firstName.appendChild(doc.createTextNode(firstNames));
			inquirer.appendChild(firstName);

			Element lastName = doc.createElement("lastName");
			lastName.appendChild(doc.createTextNode(lastNames));
			inquirer.appendChild(lastName);

			Element emailAddress = doc.createElement("emailAddress");
			emailAddress.appendChild(doc.createTextNode(email));
			inquirer.appendChild(emailAddress);

			Element phoneNumber = doc.createElement("phoneNumber");
			phoneNumber.appendChild(doc.createTextNode(phoneNo));
			inquirer.appendChild(phoneNumber);

			Element address = doc.createElement("address");
			inquirer.appendChild(address);
			Attr rel = doc.createAttribute("rel");
			rel.setValue("BILLING");
			address.setAttributeNode(rel);

			Element addressLine1 = doc.createElement("addressLine1");
			addressLine1.appendChild(doc.createTextNode(address1));
			address.appendChild(addressLine1);

			Element addressLine2 = doc.createElement("addressLine2");
			addressLine2.appendChild(doc.createTextNode(address2));
			address.appendChild(addressLine2);

			Element addressLine3 = doc.createElement("addressLine3");
			addressLine3.appendChild(doc.createTextNode(address3));
			address.appendChild(addressLine3);

			Element addressLine4 = doc.createElement("addressLine4");
			addressLine4.appendChild(doc.createTextNode(address4));
			address.appendChild(addressLine4);

			Element country = doc.createElement("country");
			country.appendChild(doc.createTextNode(countryName));
			address.appendChild(country);

			Element commission = doc.createElement("commission");
			bookingRequestDetails.appendChild(commission);

			Element reservation = doc.createElement("reservation");
			bookingRequestDetails.appendChild(reservation);

			Element adult = doc.createElement("numberOfAdults");
			adult.appendChild(doc.createTextNode(adults));
			reservation.appendChild(adult);

			Element child = doc.createElement("numberOfChildren");
			child.appendChild(doc.createTextNode(childs));
			reservation.appendChild(child);

			Element pets = doc.createElement("numberOfPets");
			pets.appendChild(doc.createTextNode(childs));
			reservation.appendChild(pets);

			Element reservationDates = doc.createElement("reservationDates");
			reservation.appendChild(reservationDates);

			Element beginDate = doc.createElement("beginDate");
			beginDate.appendChild(doc.createTextNode(startDate));
			reservationDates.appendChild(beginDate);

			Element endDate = doc.createElement("endDate");
			endDate.appendChild(doc.createTextNode(endDates));
			reservationDates.appendChild(endDate);

			Element reservationOriginationDate = doc.createElement("reservationOriginationDate");
			reservationOriginationDate.appendChild(doc.createTextNode(originationDate));
			reservation.appendChild(reservationOriginationDate);

			Element orderItemList = doc.createElement("orderItemList");
			bookingRequestDetails.appendChild(orderItemList);

			Element orderItem = doc.createElement("orderItem");
			orderItemList.appendChild(orderItem);

			// Default one which we uses for room charge
			Element externalId = doc.createElement("externalId");
			externalId.appendChild(doc.createTextNode("1234"));
			orderItem.appendChild(externalId);

			Element feeTypE = doc.createElement("feeType");
			feeTypE.appendChild(doc.createTextNode("RENTAL"));
			orderItem.appendChild(feeTypE);

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(changeName));
			orderItem.appendChild(name);

			Element preTaxAmount = doc.createElement("preTaxAmount");
			Attr tax = doc.createAttribute("currency");
			tax.setValue(currency);
			preTaxAmount.setAttributeNode(tax);
			preTaxAmount.appendChild(doc.createTextNode(roomchargeAmount));
			orderItem.appendChild(preTaxAmount);

			Element productid = doc.createElement("productId");
			productid.appendChild(doc.createTextNode("RENT"));
			orderItem.appendChild(productid);

			Element totalAmount = doc.createElement("totalAmount");
			Attr tAmount = doc.createAttribute("currency");
			tAmount.setValue(currency);
			totalAmount.setAttributeNode(tAmount);
			totalAmount.appendChild(doc.createTextNode(roomchargeAmount));
			orderItem.appendChild(totalAmount);
			if (IsTaxAdd.equalsIgnoreCase("Yes")) {
				for (int i = 0; i < taxesName.size(); i++) {
					Element orderItem1 = null;
					orderItem1 = doc.createElement("orderItem");
					orderItemList.appendChild(orderItem1);

					Element externalId1 = doc.createElement("externalId");
					externalId1.appendChild(doc.createTextNode(taxesExternal.get(i)));
					orderItem1.appendChild(externalId1);

					Element feeType1 = doc.createElement("feeType");
					feeType1.appendChild(doc.createTextNode(feeType.get("tax")));
					orderItem1.appendChild(feeType1);

					Element name1 = doc.createElement("name");
					name1.appendChild(doc.createTextNode(taxesName.get(i)));
					orderItem1.appendChild(name1);

					Element preTaxAmount1 = doc.createElement("preTaxAmount");
					Attr tax1 = doc.createAttribute("currency");
					tax1.setValue(currency);
					preTaxAmount1.setAttributeNode(tax1);
					preTaxAmount1.appendChild(doc.createTextNode(taxesAmount.get(i)));
					orderItem1.appendChild(preTaxAmount1);

					Element productId1 = doc.createElement("productId");
					productId1.appendChild(doc.createTextNode(productId.get(feeType.get("tax"))));
					orderItem1.appendChild(productId1);

					if (!setTaxRates.get(i).equalsIgnoreCase("No")) {
						Element taxRate1 = doc.createElement("taxRate");
						taxRate1.appendChild(doc.createTextNode(setTaxRates.get(i)));
						orderItem1.appendChild(taxRate1);
					}

					Element totalAmount1 = doc.createElement("totalAmount");
					Attr tAmount1 = doc.createAttribute("currency");
					tAmount1.setValue(currency);
					totalAmount1.setAttributeNode(tAmount1);
					totalAmount1.appendChild(doc.createTextNode(taxesAmount.get(i)));
					orderItem1.appendChild(totalAmount1);
				}

			}
			if (isFeeAdd.equalsIgnoreCase("Yes")) {
				for (int i = 0; i < feeName.size(); i++) {
					Element orderItem2 = null;
					orderItem2 = doc.createElement("orderItem");
					orderItemList.appendChild(orderItem2);

					Element externalId1 = doc.createElement("externalId");
					externalId1.appendChild(doc.createTextNode(feeExternal.get(i)));
					orderItem2.appendChild(externalId1);

					Element feeType1 = doc.createElement("feeType");
					feeType1.appendChild(doc.createTextNode(feeType.get("fee")));
					orderItem2.appendChild(feeType1);

					Element name1 = doc.createElement("name");
					name1.appendChild(doc.createTextNode(feeName.get(i)));
					orderItem2.appendChild(name1);

					Element preTaxAmount1 = doc.createElement("preTaxAmount");
					Attr fee = doc.createAttribute("currency");
					fee.setValue(currency);
					preTaxAmount1.setAttributeNode(fee);
					preTaxAmount1.appendChild(doc.createTextNode(feeAmount.get(i)));
					orderItem2.appendChild(preTaxAmount1);

					Element productId1 = doc.createElement("productId");
					productId1.appendChild(doc.createTextNode(productId.get(feeType.get("fee"))));
					orderItem2.appendChild(productId1);

					if (!setFeeRates.get(i).equalsIgnoreCase("No")) {
						Element feeRate1 = doc.createElement("taxRate");
						feeRate1.appendChild(doc.createTextNode(setFeeRates.get(i)));
						orderItem2.appendChild(feeRate1);
					}

					Element totalAmount1 = doc.createElement("totalAmount");
					Attr tAmount1 = doc.createAttribute("currency");
					tAmount1.setValue(currency);
					totalAmount1.setAttributeNode(tAmount1);
					totalAmount1.appendChild(doc.createTextNode(feeAmount.get(i)));
					orderItem2.appendChild(totalAmount1);
				}
			}
			if (isExtraAdultInXml.equalsIgnoreCase("Yes")) {
				Element orderItem3 = null;
				orderItem3 = doc.createElement("orderItem");
				orderItemList.appendChild(orderItem3);

				Element feeType1 = doc.createElement("feeType");
				feeType1.appendChild(doc.createTextNode(feeType.get("adult")));
				orderItem3.appendChild(feeType1);

				Element name1 = doc.createElement("name");
				name1.appendChild(doc.createTextNode("OBR_ADULT_FEE"));
				orderItem3.appendChild(name1);

				Element preTaxAmount1 = doc.createElement("preTaxAmount");
				Attr fee = doc.createAttribute("currency");
				fee.setValue(currency);
				preTaxAmount1.setAttributeNode(fee);
				preTaxAmount1.appendChild(doc.createTextNode(extraAdultAmount));
				orderItem3.appendChild(preTaxAmount1);

				Element productId1 = doc.createElement("productId");
				productId1.appendChild(doc.createTextNode(productId.get(feeType.get("adult"))));
				orderItem3.appendChild(productId1);

				Element totalAmount1 = doc.createElement("totalAmount");
				Attr tAmount1 = doc.createAttribute("currency");
				tAmount1.setValue(currency);
				totalAmount1.setAttributeNode(tAmount1);
				totalAmount1.appendChild(doc.createTextNode(extraAdultAmount));
				orderItem3.appendChild(totalAmount1);
			}
			if (isExtraChildInXml.equalsIgnoreCase("Yes")) {
				Element orderItem3 = null;
				orderItem3 = doc.createElement("orderItem");
				orderItemList.appendChild(orderItem3);

				Element feeType1 = doc.createElement("feeType");
				feeType1.appendChild(doc.createTextNode(feeType.get("child")));
				orderItem3.appendChild(feeType1);

				Element name1 = doc.createElement("name");
				name1.appendChild(doc.createTextNode("OBR_CHILD_FEE"));
				orderItem3.appendChild(name1);

				Element preTaxAmount1 = doc.createElement("preTaxAmount");
				Attr fee = doc.createAttribute("currency");
				fee.setValue(currency);
				preTaxAmount1.setAttributeNode(fee);
				preTaxAmount1.appendChild(doc.createTextNode(extraChildAmount));
				orderItem3.appendChild(preTaxAmount1);

				Element productId1 = doc.createElement("productId");
				productId1.appendChild(doc.createTextNode(productId.get(feeType.get("child"))));
				orderItem3.appendChild(productId1);

				Element totalAmount1 = doc.createElement("totalAmount");
				Attr tAmount1 = doc.createAttribute("currency");
				tAmount1.setValue(currency);
				totalAmount1.setAttributeNode(tAmount1);
				totalAmount1.appendChild(doc.createTextNode(extraChildAmount));
				orderItem3.appendChild(totalAmount1);
			}
			Element paymentScheduleItemList = doc.createElement("paymentScheduleItemList");
			bookingRequestDetails.appendChild(paymentScheduleItemList);

			Element paymentScheduleItem = doc.createElement("paymentScheduleItem");
			paymentScheduleItemList.appendChild(paymentScheduleItem);

			Element amounT = doc.createElement("amount");
			Attr attr1 = doc.createAttribute("currency");
			attr1.setValue(currency);
			amounT.setAttributeNode(attr1);
			amounT.appendChild(doc.createTextNode(paymentSheduleItemAmount));
			paymentScheduleItem.appendChild(amounT);

			Element dueDate = doc.createElement("dueDate");
			dueDate.appendChild(doc.createTextNode(dueDates));
			paymentScheduleItem.appendChild(dueDate);

			Element paymentForm = doc.createElement("paymentForm");
			bookingRequestDetails.appendChild(paymentForm);

			Element paymentCard = doc.createElement("paymentCard");
			paymentForm.appendChild(paymentCard);

			Element paymentFormType = doc.createElement("paymentFormType");
			paymentFormType.appendChild(doc.createTextNode(paymentType));
			paymentCard.appendChild(paymentFormType);

			Element billingAddress = doc.createElement("billingAddress");
			Attr rel1 = doc.createAttribute("rel");
			rel1.setValue("BILLING");
			billingAddress.setAttributeNode(rel1);
			paymentCard.appendChild(billingAddress);

			Element addressLine11 = doc.createElement("addressLine1");
			addressLine11.appendChild(doc.createTextNode(address1));
			billingAddress.appendChild(addressLine11);

			Element addressLine31 = doc.createElement("addressLine3");
			addressLine31.appendChild(doc.createTextNode(address3));
			billingAddress.appendChild(addressLine31);

			Element addressLine41 = doc.createElement("addressLine4");
			addressLine41.appendChild(doc.createTextNode(address4));
			billingAddress.appendChild(addressLine41);

			Element country1 = doc.createElement("country");
			country1.appendChild(doc.createTextNode(countryName));
			billingAddress.appendChild(country1);

			Element postalCode = doc.createElement("postalCode");
			postalCode.appendChild(doc.createTextNode(postalCodes));
			billingAddress.appendChild(postalCode);

			Element cvv = doc.createElement("cvv");
			cvv.appendChild(doc.createTextNode(cvvs));
			paymentCard.appendChild(cvv);

			Element expiration = doc.createElement("expiration");
			expiration.appendChild(doc.createTextNode(expiryDate));
			paymentCard.appendChild(expiration);

			Element nameOnCard = doc.createElement("nameOnCard");
			nameOnCard.appendChild(doc.createTextNode(nameONCard));
			paymentCard.appendChild(nameOnCard);

			Element number = doc.createElement("number");
			number.appendChild(doc.createTextNode(cardNo));
			paymentCard.appendChild(number);

			Element numberTokens = doc.createElement("numberToken");
			numberTokens.appendChild(doc.createTextNode(numberToken));
			paymentCard.appendChild(numberTokens);

			Element paymentCardDescriptor = doc.createElement("paymentCardDescriptor");
			paymentCard.appendChild(paymentCardDescriptor);

			Element paymentFormType1 = doc.createElement("paymentFormType");
			paymentFormType1.appendChild(doc.createTextNode(paymentType));
			paymentCardDescriptor.appendChild(paymentFormType1);

			Element cardCode = doc.createElement("cardCode");
			cardCode.appendChild(doc.createTextNode(cardCodes));
			paymentCardDescriptor.appendChild(cardCode);

			Element cardType = doc.createElement("cardType");
			cardType.appendChild(doc.createTextNode(cardTypes));
			paymentCardDescriptor.appendChild(cardType);

			Element trackingUuids = doc.createElement("trackingUuid");
			trackingUuids.appendChild(doc.createTextNode(trackingUuid));
			bookingRequestDetails.appendChild(trackingUuids);

			Element travelerSources = doc.createElement("travelerSource");
			travelerSources.appendChild(doc.createTextNode(masterListingChannel));
			bookingRequestDetails.appendChild(travelerSources);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			xmlDataInFile = new File(schemaFilesPath + File.separator + "bookingRequest_" + Utility.getTimeStamp() + "_"
					+ testCaseName.replaceAll("\\s", "") + ".xml");
			StreamResult result = new StreamResult(xmlDataInFile);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlDataInFile;

	}

	public File getResponseXmlFile(String xml, String schemaFilesPath, String testCaseName, String responseFileName) {
		File xmlDataInFile = null;

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
			Document doc = dBuilder.newDocument();
			doc = dBuilder.parse(inputStream);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			xmlDataInFile = new File(schemaFilesPath + File.separator + responseFileName+"_"+Utility.getTimeStamp()+"_"+testCaseName.replaceAll("\\s", "")+".xml");

			StreamResult result = new StreamResult(xmlDataInFile);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlDataInFile;
	}

	public String getFileContent(File xmlFile) throws IOException {
		Reader fileReader = new FileReader(xmlFile);
		BufferedReader bufReader = new BufferedReader(fileReader);

		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();
		while (line != null) {
			sb.append(line).append("\r\n");
			line = bufReader.readLine();
		}
		String xml2String = sb.toString();
		System.out.println("XML to String using BufferedReader : ");
		System.out.println(xml2String);
		bufReader.close();
		return xml2String;

	}

	public String getValueOfXmlTags(String xml, String tagName)
			throws IOException, ParserConfigurationException, SAXException {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource src = new InputSource();
		src.setCharacterStream(new StringReader(xml));

		Document doc = builder.parse(src);
		String value = doc.getElementsByTagName(tagName).item(0).getTextContent();
		return value;

	}

	public File createBasicBookingRequest(String testCaseName,String advertiserIds, String classId, String channels, String messages,
			String titles, String firstNames, String lastNames, String email, String phoneNo, String address1,
			String address3, String address4, String countryName, String adults, String childs, String startDate,
			String endDates, String originationDate, String externalIds, String feeTypes, String changeName,
			String amounts, String productID, String dueDates, String paymentType, String postalCodes, String cvvs,
			String expiryDate, String nameONCard, String cardNo, String cardCodes, String cardTypes,
			String schemaFilesPath, String currency) {
		File xmlDataInFile = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element bookingRequest = doc.createElement("bookingRequest");
			doc.appendChild(bookingRequest);

			Element documentVersion = doc.createElement("documentVersion");
			documentVersion.appendChild(doc.createTextNode("1.3"));
			bookingRequest.appendChild(documentVersion);

			// supercars element
			Element bookingRequestDetails = doc.createElement("bookingRequestDetails");
			bookingRequest.appendChild(bookingRequestDetails);

			Element advertiserId = doc.createElement("advertiserAssignedId");
			advertiserId.appendChild(doc.createTextNode(advertiserIds));
			bookingRequestDetails.appendChild(advertiserId);

			Element externalID = doc.createElement("listingExternalId");
			externalID.appendChild(doc.createTextNode(classId));
			bookingRequestDetails.appendChild(externalID);

			Element unitexternalID = doc.createElement("unitExternalId");
			unitexternalID.appendChild(doc.createTextNode(classId));
			bookingRequestDetails.appendChild(unitexternalID);

			Element inquiryId = doc.createElement("inquiryId");
			inquiryId.appendChild(doc.createTextNode("dummy"));
			bookingRequestDetails.appendChild(inquiryId);

			Element propertyUrl = doc.createElement("propertyUrl");
			propertyUrl.appendChild(doc.createTextNode("dummy"));
			bookingRequestDetails.appendChild(propertyUrl);

			Element channel = doc.createElement("listingChannel");
			channel.appendChild(doc.createTextNode(channels));
			bookingRequestDetails.appendChild(channel);

			Element message = doc.createElement("message");
			message.appendChild(doc.createTextNode(messages));
			bookingRequestDetails.appendChild(message);

			Element inquirer = doc.createElement("inquirer");
			bookingRequestDetails.appendChild(inquirer);

			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(titles));
			inquirer.appendChild(title);

			Element firstName = doc.createElement("firstName");
			firstName.appendChild(doc.createTextNode(firstNames));
			inquirer.appendChild(firstName);

			Element lastName = doc.createElement("lastName");
			lastName.appendChild(doc.createTextNode(lastNames));
			inquirer.appendChild(lastName);

			Element emailAddress = doc.createElement("emailAddress");
			emailAddress.appendChild(doc.createTextNode(email));
			inquirer.appendChild(emailAddress);

			Element phoneNumber = doc.createElement("phoneNumber");
			phoneNumber.appendChild(doc.createTextNode(phoneNo));
			inquirer.appendChild(phoneNumber);

			Element address = doc.createElement("address");
			inquirer.appendChild(address);
			Attr rel = doc.createAttribute("rel");
			rel.setValue("BILLING");
			address.setAttributeNode(rel);

			Element addressLine1 = doc.createElement("addressLine1");
			addressLine1.appendChild(doc.createTextNode(address1));
			address.appendChild(addressLine1);

			Element addressLine3 = doc.createElement("addressLine3");
			addressLine3.appendChild(doc.createTextNode(address3));
			address.appendChild(addressLine3);

			Element addressLine4 = doc.createElement("addressLine4");
			addressLine4.appendChild(doc.createTextNode(address4));
			address.appendChild(addressLine4);

			Element country = doc.createElement("country");
			country.appendChild(doc.createTextNode(countryName));
			address.appendChild(country);

			Element commission = doc.createElement("commission");
			bookingRequestDetails.appendChild(commission);

			Element reservation = doc.createElement("reservation");
			bookingRequestDetails.appendChild(reservation);

			Element adult = doc.createElement("numberOfAdults");
			adult.appendChild(doc.createTextNode(adults));
			reservation.appendChild(adult);

			Element child = doc.createElement("numberOfChildren");
			child.appendChild(doc.createTextNode(childs));
			reservation.appendChild(child);

			Element pets = doc.createElement("numberOfPets");
			pets.appendChild(doc.createTextNode(childs));
			reservation.appendChild(pets);

			Element reservationDates = doc.createElement("reservationDates");
			reservation.appendChild(reservationDates);

			Element beginDate = doc.createElement("beginDate");
			beginDate.appendChild(doc.createTextNode(startDate));
			reservationDates.appendChild(beginDate);

			Element endDate = doc.createElement("endDate");
			endDate.appendChild(doc.createTextNode(endDates));
			reservationDates.appendChild(endDate);

			Element reservationOriginationDate = doc.createElement("reservationOriginationDate");
			reservationOriginationDate.appendChild(doc.createTextNode(originationDate));
			reservation.appendChild(reservationOriginationDate);

			Element orderItemList = doc.createElement("orderItemList");
			bookingRequestDetails.appendChild(orderItemList);

			Element orderItem = doc.createElement("orderItem");
			orderItemList.appendChild(orderItem);

			Element externalId = doc.createElement("externalId");
			externalId.appendChild(doc.createTextNode(externalIds));
			orderItem.appendChild(externalId);

			Element feeType = doc.createElement("feeType");
			feeType.appendChild(doc.createTextNode(feeTypes));
			orderItem.appendChild(feeType);

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(changeName));
			orderItem.appendChild(name);

			Element preTaxAmount = doc.createElement("preTaxAmount");
			Attr tax = doc.createAttribute("currency");
			tax.setValue(currency);
			preTaxAmount.setAttributeNode(tax);
			preTaxAmount.appendChild(doc.createTextNode(amounts));
			orderItem.appendChild(preTaxAmount);

			Element productId = doc.createElement("productId");
			productId.appendChild(doc.createTextNode(productID));
			orderItem.appendChild(productId);

			Element totalAmount = doc.createElement("totalAmount");
			Attr tAmount = doc.createAttribute("currency");
			tAmount.setValue(currency);
			totalAmount.setAttributeNode(tAmount);
			totalAmount.appendChild(doc.createTextNode(amounts));
			orderItem.appendChild(totalAmount);
	
			Element paymentScheduleItemList = doc.createElement("paymentScheduleItemList");
			bookingRequestDetails.appendChild(paymentScheduleItemList);

			Element paymentScheduleItem = doc.createElement("paymentScheduleItem");
			paymentScheduleItemList.appendChild(paymentScheduleItem);

			Element amount = doc.createElement("amount");
			Attr attr1 = doc.createAttribute("currency");
			attr1.setValue(currency);
			amount.setAttributeNode(attr1);
			amount.appendChild(doc.createTextNode(amounts));
			paymentScheduleItem.appendChild(amount);

			Element dueDate = doc.createElement("dueDate");
			dueDate.appendChild(doc.createTextNode(dueDates));
			paymentScheduleItem.appendChild(dueDate);

			Element paymentForm = doc.createElement("paymentForm");
			bookingRequestDetails.appendChild(paymentForm);

			Element paymentCard = doc.createElement("paymentCard");
			paymentForm.appendChild(paymentCard);

			Element paymentFormType = doc.createElement("paymentFormType");
			paymentFormType.appendChild(doc.createTextNode(paymentType));
			paymentCard.appendChild(paymentFormType);

			Element billingAddress = doc.createElement("billingAddress");
			Attr rel1 = doc.createAttribute("rel");
			rel1.setValue("BILLING");
			billingAddress.setAttributeNode(rel1);
			paymentCard.appendChild(billingAddress);

			Element addressLine11 = doc.createElement("addressLine1");
			addressLine11.appendChild(doc.createTextNode(address1));
			billingAddress.appendChild(addressLine11);

			Element addressLine31 = doc.createElement("addressLine3");
			addressLine31.appendChild(doc.createTextNode(address3));
			billingAddress.appendChild(addressLine31);

			Element addressLine41 = doc.createElement("addressLine4");
			addressLine41.appendChild(doc.createTextNode(address4));
			billingAddress.appendChild(addressLine41);

			Element country1 = doc.createElement("country");
			country1.appendChild(doc.createTextNode(countryName));
			billingAddress.appendChild(country1);

			Element postalCode = doc.createElement("postalCode");
			postalCode.appendChild(doc.createTextNode(postalCodes));
			billingAddress.appendChild(postalCode);

			Element cvv = doc.createElement("cvv");
			cvv.appendChild(doc.createTextNode(cvvs));
			paymentCard.appendChild(cvv);

			Element expiration = doc.createElement("expiration");
			expiration.appendChild(doc.createTextNode(expiryDate));
			paymentCard.appendChild(expiration);

			Element nameOnCard = doc.createElement("nameOnCard");
			nameOnCard.appendChild(doc.createTextNode(nameONCard));
			paymentCard.appendChild(nameOnCard);

			Element number = doc.createElement("number");
			number.appendChild(doc.createTextNode(cardNo));
			paymentCard.appendChild(number);

			Element paymentCardDescriptor = doc.createElement("paymentCardDescriptor");
			paymentCard.appendChild(paymentCardDescriptor);

			Element paymentFormType1 = doc.createElement("paymentFormType");
			paymentFormType1.appendChild(doc.createTextNode(paymentType));
			paymentCardDescriptor.appendChild(paymentFormType1);

			Element cardCode = doc.createElement("cardCode");
			cardCode.appendChild(doc.createTextNode(cardCodes));
			paymentCardDescriptor.appendChild(cardCode);

			Element cardType = doc.createElement("cardType");
			cardType.appendChild(doc.createTextNode(cardTypes));
			paymentCardDescriptor.appendChild(cardType);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			xmlDataInFile = new File(schemaFilesPath + File.separator + "bookingRequest_"+Utility.getTimeStamp()+"_"+testCaseName.replaceAll("\\s", "")+".xml");
			StreamResult result = new StreamResult(xmlDataInFile);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlDataInFile;

	}

	public File createBookingUpdateIndexRequest(String advertiserIds, String schemaFilesPath, String startDates,
			String endDates) {
		File xmlDataInFile = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element bookingRequest = doc.createElement("bookingContentIndexRequest");
			doc.appendChild(bookingRequest);

			Element advertiserId = doc.createElement("advertiser");
			bookingRequest.appendChild(advertiserId);

			Element assignedid = doc.createElement("assignedId");
			assignedid.appendChild(doc.createTextNode(advertiserIds));
			advertiserId.appendChild(assignedid);

			Element startDate = doc.createElement("startDate");
			startDate.appendChild(doc.createTextNode(startDates));
			bookingRequest.appendChild(startDate);

			Element endDate = doc.createElement("endDate");
			endDate.appendChild(doc.createTextNode(endDates));
			bookingRequest.appendChild(endDate);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			xmlDataInFile = new File(schemaFilesPath + File.separator + "BUSContentIndex.xml");
			StreamResult result = new StreamResult(xmlDataInFile);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlDataInFile;

	}

}
