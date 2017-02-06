package com.dereekb.gae.utilities.misc.reader;

import java.util.Date;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;


/**
 * Utility for reading {@link Long} times and converting them to {@link Date},
 * and vice versa.
 *
 * @author dereekb
 *
 */
public class DateLongConverter {

	public static final DateLongConverter CONVERTER = new DateLongConverter();

	public Date safeConvert(Long time) {
		Date date = null;

		if (time != null) {
			date = new Date(time);
		}

		return date;
	}

	public Date convert(Long time) throws ConversionFailureException {

		if (time == null) {
			throw new ConversionFailureException();
		}

		return new Date(time);
	}

	public Long safeConvert(Date date) {
		Long time = null;

		if (date != null) {
			time = date.getTime();
		}

		return time;
	}

	public Long convert(Date date) throws ConversionFailureException {

		if (date == null) {
			throw new ConversionFailureException();
		}

		return date.getTime();
	}

}
