package com.innroad.inncenter.utils;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.poi.hssf.record.StyleRecord;

import com.innroad.inncenter.pageobjects.CPReservationPage;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class ESTTimeZone {

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <CompareDate> ' Description: <this method compare two
	 * dates. like if you wanna compare past date with today then u have to send
	 * past date in parameter > ' Input parameters: < pass date as string in
	 * method> ' Return value: <boolean> ' Created By: <Farhan Ghaffar> '
	 * Created On: <MM/DD/YYYY>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static boolean CompareDate(String getDate) throws ParseException {
		boolean isLess = false;
		String[] str = getDate.split(" ");
		System.out.println(str.length);
		String day = str[0];
		String month = str[1];
		String year = str[3];
		System.out.println("day : " + day);
		System.out.println("day : " + month);
		System.out.println("day : " + year);
		str = str[2].split(",");

		getDate = day + ", " + str[0] + " " + month + " " + year;

		System.out.println(getDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date d1 = dateFormat.parse(getDate);
		Date date = new Date();
		String TodayDate = dateFormat.format(date);

		Date d2 = dateFormat.parse(TodayDate);

		if (d1.compareTo(d2) > 0) {
			// When Date d1 > Date d2
			isLess = false;
		}

		else if (d1.compareTo(d2) < 0) {

			// When Date d1 < Date d2
			System.out.println("Date1 is before Date2");
			isLess = true;
		}

		else if (d1.compareTo(d2) == 0) {

			// When Date d1 = Date d2
			System.out.println("Date1 is equal to Date2");
			isLess = true;
			;
		}

		return isLess;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getDateforAccountBalance> ' Description: < Get date
	 * format for account balance report > ' Input parameters: <pass day as
	 * integer value and then will return base on day like pass 0 as day then it
	 * will return today date> ' Return value: <String> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <MM/DD/YYYY>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String getDateforAccountBalance(int days) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));

		c.add(Calendar.DATE, days);
		String arrivalDate = dateFormat.format(c.getTime());

		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date1 = format.parse(arrivalDate);
		System.out.println(date1);
		String d = date1.toString();

		String[] split_String = d.split(" ");
		String weakday = "";
		switch (split_String[0]) {
		case "Mon":
			weakday = "Monday";
			break;
		case "Tue":
			weakday = "Tuesday";
			break;
		case "Wed":
			weakday = "Wednesday";
			break;
		case "Thu":
			weakday = "Thursday";
			break;
		case "Fri":
			weakday = "Friday";
			break;
		case "Sat":
			weakday = "Saturday";
			break;
		case "Sun":
			weakday = "Sunday";
			break;
		}

		// start month
		String month = "";
		switch (split_String[1]) {
		case "Jan":
			month = "January";
			break;
		case "Feb":
			month = "February";
			break;
		case "Mar":
			month = "March";
			break;
		case "Apr":
			month = "April";
			break;
		case "May":
			month = "May";
			break;
		case "Jun":
			month = "June";
			break;
		case "Jul":
			month = "July";
			break;
		case "Aug":
			month = "August";
			break;
		case "Sep":
			month = "September";
			break;
		case "Oct":
			month = "October";
			break;
		case "Nov":
			month = "November";
			break;
		case "Dec":
			month = "December";
			break;

		}

		String get_date = split_String[2];
		if (get_date.charAt(0) == '0') {
			get_date = String.valueOf(get_date.charAt(1));
		}

		String getDate = weakday + ", " + month + " " + get_date + ", " + split_String[split_String.length - 1];

		return getDate;

	}

	public static String DateFormateForLineItem(int days) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();

		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.DATE, days);
		String selectDate = dateFormat.format(c.getTime());
		return selectDate;
	}

	public static String DateFormateForLineItemWithFormate(int days,String formate) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(formate);
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();

		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.DATE, days);
		String selectDate = dateFormat.format(c.getTime());
		return selectDate;
	}

	
	public static String DateFormat(int days) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();

		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.DATE, days);
		String selectDate = dateFormat.format(c.getTime());
		return selectDate;
	}
	
	public static String DateFormateForLineItem(int days, String format) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();

		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.DATE, days);
		String selectDate = dateFormat.format(c.getTime());
		return selectDate;
	}

	public static String GetDate(String Time_Zone, String Format) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(Format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(Time_Zone));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		String arrivalDate = dateFormat.format(c.getTime());

		System.out.println("date : " + arrivalDate);
		return arrivalDate;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getDateforAccountBalance> ' Description: < Get date
	 * format for folio line item > ' Input parameters: <pass day as integer
	 * value and then will return base on day like pass 0 as day then it will
	 * return today date> ' Return value: <String> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <MM/DD/YYYY>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String getDateforLineItem(int days, boolean LineItemDate) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));

		c.add(Calendar.DATE, days);
		String arrivalDate = dateFormat.format(c.getTime());

		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date1 = format.parse(arrivalDate);
		System.out.println(date1);
		String d = date1.toString();

		String[] split_String = d.split(" ");
		String weakday = "";
		switch (split_String[0]) {
		case "Mon":
			weakday = "Mon";
			break;
		case "Tue":
			weakday = "Tue";
			break;
		case "Wed":
			weakday = "Wed";
			break;
		case "Thu":
			weakday = "Thu";
			break;
		case "Fri":
			weakday = "Fri";
			break;
		case "Sat":
			weakday = "Sat";
			break;
		case "Sun":
			weakday = "Sun";
			break;
		}

		// start month
		String month = "";
		switch (split_String[1]) {
		case "Jan":
			month = "Jan";
			break;
		case "Feb":
			month = "Feb";
			break;
		case "Mar":
			month = "Mar";
			break;
		case "Apr":
			month = "Apr";
			break;
		case "May":
			month = "May";
			break;
		case "Jun":
			month = "Jun";
			break;
		case "Jul":
			month = "Jul";
			break;
		case "Aug":
			month = "Aug";
			break;
		case "Sep":
			month = "Sep";
			break;
		case "Oct":
			month = "Oct";
			break;
		case "Nov":
			month = "Nov";
			break;
		case "Dec":
			month = "Dec";
			break;

		}

		String get_date = split_String[2];
		/*
		 * if (get_date.charAt(0) == '0') { get_date =
		 * String.valueOf(get_date.charAt(1)); }
		 */

		String getDate = "";
		if (LineItemDate) {
			getDate = weakday + " " + month + " " + get_date + ", " + split_String[split_String.length - 1];

		} else {
			getDate = weakday + ", " + get_date + "-" + month + "-" + split_String[split_String.length - 1];
		}

		return getDate;

	}

	public static String getDateIncidental(int days, boolean LineItemDate) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));

		c.add(Calendar.DATE, days);
		String arrivalDate = dateFormat.format(c.getTime());

		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date1 = format.parse(arrivalDate);
		System.out.println(date1);
		String d = date1.toString();

		String[] split_String = d.split(" ");
		String weakday = "";
		switch (split_String[0]) {
		case "Mon":
			weakday = "Mon";
			break;
		case "Tue":
			weakday = "Tue";
			break;
		case "Wed":
			weakday = "Wed";
			break;
		case "Thu":
			weakday = "Thu";
			break;
		case "Fri":
			weakday = "Fri";
			break;
		case "Sat":
			weakday = "Sat";
			break;
		case "Sun":
			weakday = "Sun";
			break;
		}

		// start month
		String month = "";
		switch (split_String[1]) {
		case "Jan":
			month = "Jan";
			break;
		case "Feb":
			month = "Feb";
			break;
		case "Mar":
			month = "Mar";
			break;
		case "Apr":
			month = "Apr";
			break;
		case "May":
			month = "May";
			break;
		case "Jun":
			month = "Jun";
			break;
		case "Jul":
			month = "Jul";
			break;
		case "Aug":
			month = "Aug";
			break;
		case "Sep":
			month = "Sep";
			break;
		case "Oct":
			month = "Oct";
			break;
		case "Nov":
			month = "Nov";
			break;
		case "Dec":
			month = "Dec";
			break;

		}

		String get_date = split_String[2];
		/*
		 * if (get_date.charAt(0) == '0') { get_date =
		 * String.valueOf(get_date.charAt(1)); }
		 */

		String getDate = weakday + " " + month + " " + get_date + " ," + split_String[split_String.length - 1];

		return getDate;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getComingSunday> ' Description: <This static method will
	 * provide coming Sunday date provided that Days will be provided in
	 * multiples of seven.> ' Input parameters: <int Days> ' Return value:
	 * <String> ' Created By: <Jamal Nasir> ' Created On: <05/06/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static String getComingSunday(int Days) throws ParseException {
		if (Days < 7) {
			Days = 7;
		}
		DateFormat Format = new SimpleDateFormat("MM/dd/yyyy");
		Format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		String DF = Format.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(Format.parse(DF));

		int weekday = c.get(Calendar.DAY_OF_WEEK);
		int days = Calendar.SUNDAY - weekday;
		if (days <= 0) {
			// this will usually be the case since Calendar.SUNDAY is the
			// smallest
			days += Days;
		}
		c.add(Calendar.DAY_OF_YEAR, days);
		String arrivalDate = Format.format(c.getTime());

		Date date1 = Format.parse(arrivalDate);
		System.out.println(date1);
		String selectDate = Format.format(c.getTime());

		return selectDate;

	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <reformatDate> ' Description: <This static method will
	 * take date and its format. then it changes date to required format.> '
	 * Input parameters: <String DateToFormat, String PreFormat, String
	 * PostFormat> ' Return value: <String> ' Created By: <Jamal Nasir> '
	 * Created On: <04/30/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public static String reformatDate(String DateToFormat, String preFormat, String postFormat) throws ParseException {
		DateFormat srcDf = new SimpleDateFormat(preFormat);

		// parse the date string into Date object
		Date date = srcDf.parse(DateToFormat);

		DateFormat destDf = new SimpleDateFormat(postFormat);

		// format the date into another format
		DateToFormat = destDf.format(date);

		return DateToFormat;
	}

	public static String SubString(String StringToBeParse, int StartingPoint, int EndPoint) {
		System.out.println(StringToBeParse.length());
		StringToBeParse = StringToBeParse.substring(StartingPoint, EndPoint);
		System.out.println("first part: " + StringToBeParse);
		return StringToBeParse;
		// String secondpart = getStr.substring(10, getStr.length());
		// System.out.println("second part: "+secondpart);
	}

	public static String parseDate(String date, String inFormate, String outFormate) {
		String result = null;
		try {
			Date date1 = new SimpleDateFormat(inFormate).parse(date);
			DateFormat requiredDateFormat = new SimpleDateFormat(outFormate);
			// requiredDateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
			result = requiredDateFormat.format(date1);
			System.out.println(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <DateFormateForCheckInCheckOut> ' Description: <This
	 * method will return Date for MR Reservations CheckIn and CheckOut> ' Input
	 * parameters: <int days> ' Return value: <String> ' Created By: <Jamal
	 * Nasir> ' Created On: <05/18/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String DateFormateForCheckInCheckOut(int days) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.DATE, days);
		String selectDate = dateFormat.format(c.getTime());
		return selectDate;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getDateWithTime> ' Description: <This method will return
	 * Date with Time> ' Input parameters: <int Minute> ' Return value: <String>
	 * ' Created By: <Adnan Ghaffar> ' Created On: <05/18/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public static String getDateWithTime(int minutes) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("E MMM d,yyyy hh:mm a");
		//dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));

		c.add(Calendar.MINUTE, minutes);
		String arrivalDate = dateFormat.format(c.getTime());
		return arrivalDate;
	}

	public static int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static int numberOfDaysBetweenDates(String startDate, String endDate) throws ParseException {
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date date = sdf.parse(startDate);
		cal1.setTime(date);
		date = sdf.parse(endDate);
		cal2.setTime(date);

		System.out.println("Days= " + daysBetween(cal1.getTime(), cal2.getTime()));
		return daysBetween(cal1.getTime(), cal2.getTime());
	}

	public static String getDateonBasedOfDate(String formate, String date, int day, String timeZone)
			throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(formate);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(date));
		c.add(Calendar.DATE, day);
		String getDate = dateFormat.format(c.getTime());
		//System.out.println(getDate);
		return getDate;
	}

	public static String getDayBasedOnDate(String formate, String date, String timeZone, String dayFormot)
			throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(formate);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date getDate = dateFormat.parse(date);
		DateFormat dayformat = new SimpleDateFormat(dayFormot);
		String finalDay = dayformat.format(getDate);
		System.out.println(finalDay);
		return finalDay;
	}

	public static String getDateBaseOnDate(String date, String dateFormat, String expectedFormat)
			throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat(dateFormat);
		Date getDate = format1.parse(date);
		// for date in calendar search
		DateFormat format2 = new SimpleDateFormat(expectedFormat);
		return format2.format(getDate);

	}

	
	public static boolean CompareDates(String getDate,String formate,String timeZone) throws ParseException {
		boolean isLess = false;

		SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date d1 = dateFormat.parse(getDate);
		Date date = new Date();
		String TodayDate = dateFormat.format(date);

		Date d2 = dateFormat.parse(TodayDate);

		if (d1.compareTo(d2) > 0) {
			// When Date d1 > Date d2
			isLess = false;
		}

		else if (d1.compareTo(d2) < 0) {

			// When Date d1 < Date d2
			System.out.println("Date1 is before Date2");
			isLess = true;
		}

		else if (d1.compareTo(d2) == 0) {

			// When Date d1 = Date d2
			System.out.println("Date1 is equal to Date2");
			isLess = true;
		}

		return isLess;
		
	}
	
	public static String getDateFormatBasedOnClientDateFormartType(String clientDateFormatType) throws ParseException {
		String dateFormat = "";
		

		if (clientDateFormatType.equalsIgnoreCase("USA")) {
			dateFormat = "MM/dd/yyyy";

		} else if (clientDateFormatType.equalsIgnoreCase("International")) {
			dateFormat = "dd/MM/yyyy";
		} else {
			dateFormat = "This case isn't handled, Please handle it first in Utility";
		}
		return dateFormat;
	}
	public static String getNextDateBaseOnPreviouseDate(String date,String firstDateFormate,String expectedFormate,int day,String timeZone) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(firstDateFormate);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(date));
		c.add(Calendar.DATE, day);
		String getDate = dateFormat.format(c.getTime());
		DateFormat expecetdDateFormat = new SimpleDateFormat(expectedFormate);
		Date date1 = dateFormat.parse(getDate);

		getDate = expecetdDateFormat.format(date1);
		System.out.println(getDate);

		return getDate;
	}
	

	public static int getMonthFromString(String month) {
		int monthValue = 1;
		switch (month) {
			case "January":
				monthValue = 1;
				break;
			case "February":
				monthValue = 2;
				break;
			case "March":
				monthValue = 3;
				break;
			case "April":
				monthValue = 4;
				break;
			case "May":
				monthValue = 5;
				break;
			case "June":
				monthValue = 6;
				break;
			case "July":
				monthValue = 7;
				break;
			case "August":
				monthValue = 8;
				break;
			case "September":
				monthValue = 9;
				break;
			case "October":
				monthValue = 10;
				break;
			case "November":
				monthValue = 11;
				break;
			case "December":
				monthValue = 12;
				break;
		}

		return monthValue;
	}
		
	public static int numberOfDaysBetweenDates(String startDate, String endDate,String dateFormat) throws ParseException {
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Date date = sdf.parse(startDate);
		cal1.setTime(date);
		date = sdf.parse(endDate);
		cal2.setTime(date);

		System.out.println("Days= " + daysBetween(cal1.getTime(), cal2.getTime()));
		return daysBetween(cal1.getTime(), cal2.getTime());
	}

	public static String getMonthFromCurrentMonth(String inputdate, String inputFormat, int monthToGet) throws ParseException{
		Calendar calendar = GregorianCalendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy");
		String reformatDate = ESTTimeZone.reformatDate(inputdate, inputFormat, "MMMM yyyy");
    	Date currentMonth = df.parse(reformatDate);
    	calendar.setTime(currentMonth);
    	// current month
    	String currentMonthAsSting = df.format(calendar.getTime());
    	System.out.println("currentMonthAsSting : " + currentMonthAsSting);
    	    
    	// Add next month
    	calendar.add(Calendar.MONTH, monthToGet);
	   	String nextMonthAsString = df.format(calendar.getTime());
	   	System.out.println("nextMonthAsString : " + nextMonthAsString);
    	return nextMonthAsString;
	     			      
	}
	
	public static String setDatesAsPerTimeZone(String inputDate, String inputDateFormat, String requiredTimeZone ) {
		String requiredDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat);			
			Calendar calendar = new GregorianCalendar();
			Date today = new Date();
			String systemDateToday = dateFormat.format(calendar.getTime());
			String diff = Utility.differenceBetweenDates(inputDate, systemDateToday);
			dateFormat.setTimeZone(TimeZone.getTimeZone(requiredTimeZone));
			calendar.add(Calendar.DATE, Integer.parseInt(diff));
	        requiredDate = dateFormat.format(today);
	        calendar.setTime(dateFormat.parse(requiredDate));
	        calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(diff));
	        requiredDate = dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			return null;
		}
		return requiredDate;
		
	}
	public static String getDateWithTime(String timeZone,String dateFormate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(dateFormate);;
				//new SimpleDateFormat("E MMM d,yyyy hh:mm a");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		//c.add(Calendar.MINUTE, minutes);
		String arrivalDate = dateFormat.format(c.getTime());
		return arrivalDate;
	}
	
	public static void main(String[] arg) throws Exception {
		System.out.println(getDateWithTime("US/Eastern","MM/dd/yyyy hh:mm aa"));
	}

	public static String DateFormateForLineItem(int days, String format,String timeZone) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date();
		String DF = dateFormat.format(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.DATE, days);
		c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String selectDate = dateFormat.format(c.getTime());
		return selectDate;
	}

	public static String getLastMonth(String format,String timeZone,int month) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date();
		String DF = dateFormat.format(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.MONTH, month);
		String getMonth = dateFormat.format(c.getTime());
		return getMonth;
		
	}
	
	public static int getDaysInYearTillToday(String format,String timeZone) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar c = Calendar.getInstance();
		int days = c.get(Calendar.DAY_OF_YEAR);
		return days;
	}
	
	public static String getLastYear(String format,String timeZone) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date();
		String DF = dateFormat.format(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.YEAR, -1);
		String getMonth = dateFormat.format(c.getTime());
		return getMonth;
		
	}
	
	public static int getDaysInYear(String expectedDayFor,String format,String timeZone) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = dateFormat.parse(expectedDayFor);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int days = c.getActualMaximum(Calendar.DAY_OF_YEAR);
		return days;
	}
	
	public static int getDaysInYearAndMonth(String expectedDayFor,String format,String timeZone) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = dateFormat.parse(expectedDayFor);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
}
