package com.dereekb.gae.model.general.time.util;

import com.dereekb.gae.model.general.time.Time;

/**
 * Converts {@link Time} to numeric {@link Integer} representations.
 *
 * @author dereekb
 *
 */
public interface TimeValueConverter {

	public Integer timeToNumber(Time time);

	public Time timeFromNumber(Integer timeNumber);

}
