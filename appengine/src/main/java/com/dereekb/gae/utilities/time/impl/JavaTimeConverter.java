package com.dereekb.gae.utilities.time.impl;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.dereekb.gae.utilities.time.IsoTimeConverter;
import com.dereekb.gae.utilities.time.SafeIsoTimeConverter;

public final class JavaTimeConverter {

	public static final IsoTimeConverter INSTANT_CONVERTER = new InstantConverter();

	public static final IsoTimeConverter SINGLETON = INSTANT_CONVERTER;
	public static final SafeIsoTimeConverter SAFE_SINGLETON = new SafeIsoTimeConverterImpl(SINGLETON);

	private static class InstantConverter
	        implements IsoTimeConverter {

		private static final DateTimeFormatter fromStringTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_INSTANT;

		@Override
		public Date convertFromString(String isoString) throws IllegalArgumentException {

			if (isoString == null) {
				throw new IllegalArgumentException("Time cannot be null.");
			}
			Instant instant = null;

			try {
				instant = Instant.from(fromStringTimeFormatter.parse(isoString));
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(e);
			}

			return Date.from(instant);
		}

		@Override
		public String convertToString(Date date) {

			if (date == null) {
				throw new IllegalArgumentException("Date cannot be null.");
			}

			Instant instant = date.toInstant();
			return timeFormatter.format(instant);
		}

	}

}
