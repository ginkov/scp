package com.jiayun.scp.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.format.Formatter;

public class LongToDateString implements Formatter<Long>{
	
	private DateFormat df;

	public LongToDateString() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public LongToDateString(String pattern) {
		df = new SimpleDateFormat(pattern);
	}

	@Override
	public String print(Long timestampMillis, Locale locale) {
		Date d = new Date(timestampMillis);
		return df.format(d);
	}

	@Override
	public Long parse(String dateString, Locale locate) throws ParseException {
		return df.parse(dateString).getTime();
	}
	

}
