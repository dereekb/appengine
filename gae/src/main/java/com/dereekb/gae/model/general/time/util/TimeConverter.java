package com.dereekb.gae.model.general.time.util;

import com.dereekb.gae.model.general.time.Time;

/**
 * Converts {@link Time} to string representations.
 *
 * @author dereekb
 *
 */
public interface TimeConverter {

	public String timeToString(Time time);

	public Time timeFromString(String timeString);

}
