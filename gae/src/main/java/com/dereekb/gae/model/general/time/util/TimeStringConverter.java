package com.dereekb.gae.model.general.time.util;

import com.dereekb.gae.model.general.time.Time;

/**
 * Converts {@link Time} to {@link String} representations and vice-versa.
 *
 * @author dereekb
 *
 */
public interface TimeStringConverter {

	public String timeToString(Time time);

	public Time timeFromString(String timeString);

}
