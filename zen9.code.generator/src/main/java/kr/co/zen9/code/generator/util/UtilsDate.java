package kr.co.zen9.code.generator.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kr.co.zen9.code.generator.exception.DateUtilsException;


public class UtilsDate {
	
	public static final String DEFAULT_DATE_PATTERN = "YYYY-MM-dd";
	public static final String DEFAULT_DATETIME_PATTERN = "YYYY-MM-dd hh:mm:ss";
	public static final String DEFAULT_DATETIME_MILI_PATTERN = "YYYY-MM-dd hh:mm:ss:SSS";

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }
    
    public static Date getDate(java.sql.Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
    
    public static String today() {
    	return today(DEFAULT_DATE_PATTERN);
    }
    
    public static String today(String format) {
        return formatter(format, getDate());
    }
    
    public static String formatter(Date date) {
        return formatter(DEFAULT_DATE_PATTERN, date);
    }
    
    public static String formatter(String format,Date date) {
    	
    	String value = null;
    	
    	try {
    		
        	if( date == null) {
        		throw new DateUtilsException("The date value is null.");
        	}			
        	
        	SimpleDateFormat sdf = new SimpleDateFormat(format);
        	value = sdf.format(date);
        	
    	} catch (Exception e) {
			e.printStackTrace();
		}
        return value;
    }
    
	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM uuuu").withLocale(Locale.getDefault());

		System.out.println(dtf.toString());
	}

}
