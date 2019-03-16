package com.dereekb.gae.model.general.time.util;

import com.dereekb.gae.model.general.time.WeekTime;

/**
 * Used for converting {@link WeekTime} values to a {@link Integer}
 * representation.
 *
 * @author dereekb
 */
public interface WeekTimeConverter {

	public Integer weekTimeToNumber(WeekTime weekTime);

	public WeekTime weekTimeFromNumber(Integer number);

}
