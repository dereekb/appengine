package com.dereekb.gae.model.general.time.util.impl;

import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.util.TimeValueConverter;

/**
 * {@link TimeValueConverter} implementation.
 *
 * @author dereekb
 *
 */
public class TimeValueConverterImpl
        implements TimeValueConverter {

	public static final TimeValueConverterImpl CONVERTER = new TimeValueConverterImpl();

	// MARK: Number
	@Override
	public Integer timeToNumber(Time time) {
		Hour hour = time.getHour();
		Integer hours = hour.getDayHour();
		Integer minutes = time.getMinutes();
		return ((hours * Time.MINUTES_IN_HOUR) + minutes);
	}

	@Override
	public Time timeFromNumber(Integer timeNumber) {
		timeNumber = timeNumber % Time.MAX_TIME;
		Double hours = Math.floor(timeNumber.doubleValue() / Time.MINUTES_IN_HOUR.doubleValue());
		Integer minutes = timeNumber % Time.MINUTES_IN_HOUR;
		TimeImpl time = new TimeImpl(hours.intValue(), minutes);
		return time;
	}

}
