package com.dereekb.gae.test.utility.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.time.impl.JavaTimeConverter;

public class JavaTimeConverterTest {

	@Test
	public void testToFromConversion() {
		Date now = new Date();

		String nowString = JavaTimeConverter.INSTANT_CONVERTER.convertToString(now);
		Date nowConvert = JavaTimeConverter.INSTANT_CONVERTER.convertFromString(nowString);

		assertTrue(now.equals(nowConvert));
	}

	@Test
	public void testToFromInstantConversionWithTimezoneString() {
		String timeWithTimeZone = "2011-12-03T10:15:30Z";

		Date dateConvert = JavaTimeConverter.INSTANT_CONVERTER.convertFromString(timeWithTimeZone);
		String dateString = JavaTimeConverter.INSTANT_CONVERTER.convertToString(dateConvert);

		assertTrue(dateString.equals(timeWithTimeZone));
	}

	@Test
	public void testFromConversionWithLocalTimezoneInfo() {
		String timeWithTimeZone = "2019-05-22T00:19:50.345-05:00";

		Date dateConvert = JavaTimeConverter.SAFE_SINGLETON.convertFromString(timeWithTimeZone);
		assertTrue(dateConvert != null);

		// assertTrue(dateString.equals(timeWithTimeZone));
	}

}
