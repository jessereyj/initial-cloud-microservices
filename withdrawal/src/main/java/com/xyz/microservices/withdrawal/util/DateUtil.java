package com.xyz.microservices.withdrawal.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	public static final String DATETIME_FORMAT = "MM/dd/yyyy hh:mm:ss";
	
	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static String getCurrentDate() {
		Date currentDate = new Date();
		DateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
		return formatter.format(currentDate);
	}
	
	public static long getDays(Date previousDate) {
		long diffInMillies = Math.abs(new Date().getTime() - previousDate.getTime());
	    return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
	
	public static Date getStringToUtilDate(String currentDate, String format) {
		try {
			return new SimpleDateFormat(format).parse(currentDate);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
