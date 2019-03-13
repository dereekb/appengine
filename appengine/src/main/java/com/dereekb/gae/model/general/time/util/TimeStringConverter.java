package com.dereekb.gae.model.general.time.util;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;

/**
 * Converts {@link Time} to {@link String} representations and vice-versa.
 *
 * @author dereekb
 *
 */
public interface TimeStringConverter {

	public String convertToString(Time time);

	public String convertToString(TimeSpan timeSpan);

	public String convertToString(Day day);

	public String convertToString(DayTimeSpanPair pair);

	public Time timeFromString(String timeString);

}
