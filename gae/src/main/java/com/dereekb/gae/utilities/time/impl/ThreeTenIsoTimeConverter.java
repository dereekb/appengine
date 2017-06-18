package com.dereekb.gae.utilities.time.impl;

import java.util.Date;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import com.dereekb.gae.utilities.time.IsoTimeConverter;

public final class ThreeTenIsoTimeConverter {

	public static final IsoTimeConverter INSTANT_CONVERTER = new InstantConverter();
	public static final IsoTimeConverter LOCAL_TIME_CONVERTER = new LocalDateTimeConverter();

	public static final IsoTimeConverter SINGLETON = INSTANT_CONVERTER;

	private static class InstantConverter
	        implements IsoTimeConverter {

		private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_INSTANT;

		@Override
		public Date convertFromString(String isoString) throws IllegalArgumentException {

			if (isoString == null) {
				throw new IllegalArgumentException("Time cannot be null.");
			}
			Instant instant = null;

			try {
				instant = Instant.from(timeFormatter.parse(isoString));
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(e);
			}

			return DateTimeUtils.toDate(instant);
		}

		@Override
		public String convertToString(Date date) {

			if (date == null) {
				throw new IllegalArgumentException("Date cannot be null.");
			}

			Instant instant = DateTimeUtils.toInstant(date);
			return timeFormatter.format(instant);
		}

	}

	/**
	 * @deprecated Is locked to the UTC timezone only, compared to
	 *             {@link InstantConverter}.
	 * 
	 * @author dereekb
	 *
	 */
	@Deprecated
	public static final class LocalDateTimeConverter
	        implements IsoTimeConverter {

		private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;

		@Override
		public Date convertFromString(String isoString) throws IllegalArgumentException {

			if (isoString == null) {
				throw new IllegalArgumentException("Time cannot be null.");
			}

			LocalDateTime localDateTime = null;

			try {
				localDateTime = LocalDateTime.from(timeFormatter.parse(isoString));
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(e);
			}

			Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
			return DateTimeUtils.toDate(instant);
		}

		@Override
		public String convertToString(Date date) {

			if (date == null) {
				throw new IllegalArgumentException("Date cannot be null.");
			}

			Instant instant = DateTimeUtils.toInstant(date);
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
			return timeFormatter.format(localDateTime);
		}

	}

}
