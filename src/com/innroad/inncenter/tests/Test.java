package com.innroad.inncenter.tests;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.innroad.inncenter.utils.Utility;

public class Test {

	public static String generateRandomNumberWithGivenNumberOfDigits(int number) {
		String randomNumber = "1234567890";
		StringBuilder sb = new StringBuilder(number);
		for (int i = 0; i < number; i++) {
			int index = (int) (randomNumber.length() * Math.random());
			sb.append(randomNumber.charAt(index));
		}
		return sb.toString();
	}
	
	public static double round(double value, int digits) {
	    double scale = Math.pow(10, digits);
	    return Math.round(value * scale) / scale;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Calendar monthToDate = Calendar.getInstance();
		monthToDate.add(Calendar.MONTH, +1);
		
		System.out.println("Month: "+monthToDate.get(Calendar.MONTH));
		String month = Integer.toString(monthToDate.get(Calendar.MONTH));
		System.out.println(month);
		System.out.println("Month: "+new DateFormatSymbols().getMonths()[monthToDate.get(Calendar.MONTH)-1].substring(0, 3));
		
		
		System.out.println(round(20.0, 3));
		
		DecimalFormat dc = new DecimalFormat("#.##");
		
		double d = 2.00;
		
		System.out.println("Double: "+dc.format(d));
		
		int a = 2;
		double b = 5.6;
		
		System.out.println("Double int : "+b*a);
		
		
		
		SimpleDateFormat f = new SimpleDateFormat("MMM dd,yyyy");
		
		String s = "$500.00";
		
		String ss = s.substring(1);
		System.out.println(ss);
		System.out.println(ss.replaceAll(".0", ""));
		System.out.println(ss.replace(".00", ""));
		
		String date = "25/05/2020";
		
		System.out.println(Utility.parseDate(date, "dd/MM/yyyy", "MMM dd,yyyy"));
		
		
		System.out.println("Date: "+f.format(new Date(new SimpleDateFormat("MM/dd/yyyy").format(new Date(date)))));
		
		
		System.out.println(Utility.getCurrentDate("MMM dd,yyyy"));
		
		
		String number = generateRandomNumberWithGivenNumberOfDigits(9);
		System.out.println(number);
		
		
		
		
		
		
		
		
		
		
		
		
//		guestFirstName = guestFirstName + "|" + Utility.generateRandomString();
//		guestLastName = guestLastName + "|" + Utility.generateRandomString();
//		Adults = Adults + "|" + Adults;
//		Children = Children + "|" + Children;
//		Rateplan = Rateplan + "|" + Rateplan;
//		RoomClass = RoomClass + "|" + RoomClass;
//		salutation = salutation + "|" + salutation;
//		phoneNumber = phoneNumber + "|" + phoneNumber;
//		alternativePhone = alternativePhone + "|" + alternativePhone;
//		email = email + "|" + email;
		
//		app_logs.info(guestFirstName);
//		app_logs.info(guestLastName);
//		app_logs.info(Adults);
//		app_logs.info(Children);
//		app_logs.info(Rateplan);
//		app_logs.info(RoomClass);
//		app_logs.info(salutation);
		

		
		
		
		
		
		
		

	}

}
