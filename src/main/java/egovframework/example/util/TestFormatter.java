package egovframework.example.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

public class TestFormatter implements Formatter<Date> {

	private String format;
	
	@Override
	public String print(Date object, Locale locale) {
		System.out.println("======================>TestFormatter print");
		return getDateFormat(locale).format(object);
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		System.out.println("======================>TestFormatter parse");
		return getDateFormat(locale).parse(text);
	}

	private DateFormat getDateFormat(Locale locale) {
		if(StringUtils.hasText(this.format)) {
			return new SimpleDateFormat(this.format, locale);
		}else {
			return SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, locale);
		}
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
}
