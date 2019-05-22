package com.dereekb.gae.test.utility.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.time.impl.ThreeTenIsoTimeConverter;

@Deprecated
public class ThreeTenIsoTimeConverterTest {

	@Test
	public void testToFromConversion() {
		Date now = new Date();

		String nowString = ThreeTenIsoTimeConverter.INSTANT_CONVERTER.convertToString(now);
		Date nowConvert = ThreeTenIsoTimeConverter.INSTANT_CONVERTER.convertFromString(nowString);

		assertTrue(now.equals(nowConvert));
	}

	@Test
	public void testToFromInstantConversionWithTimezoneString() {
		String timeWithTimeZone = "2011-12-03T10:15:30Z";

		Date dateConvert = ThreeTenIsoTimeConverter.INSTANT_CONVERTER.convertFromString(timeWithTimeZone);
		String dateString = ThreeTenIsoTimeConverter.INSTANT_CONVERTER.convertToString(dateConvert);

		assertTrue(dateString.equals(timeWithTimeZone));
	}

	@Test
	public void testToFromConversionWithTimezoneString() {
		String timeWithTimeZone = "2011-12-03T10:15:30.324";

		Date dateConvert = ThreeTenIsoTimeConverter.LOCAL_TIME_CONVERTER.convertFromString(timeWithTimeZone);
		String dateString = ThreeTenIsoTimeConverter.LOCAL_TIME_CONVERTER.convertToString(dateConvert);

		assertTrue(dateString.equals(timeWithTimeZone));
	}

	@Test
	public void testFromConversionWithLocalTimezoneInfo() {
		String timeWithTimeZone = "2019-05-22T00:19:50.345-05:00";

		Date dateConvert = ThreeTenIsoTimeConverter.SAFE_SINGLETON.convertFromString(timeWithTimeZone);
		assertTrue(dateConvert != null);

		// assertTrue(dateString.equals(timeWithTimeZone));
	}

}
