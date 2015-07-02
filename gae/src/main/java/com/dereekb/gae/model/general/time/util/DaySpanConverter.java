package com.dereekb.gae.model.general.time.util;

import com.dereekb.gae.model.general.time.DaySpan;

/**
 * Encodes a {@link DaySpan} to a number.
 *
 * @author dereekb
 *
 */
public interface DaySpanConverter {

	public Integer daysToNumber(DaySpan span) throws IllegalArgumentException;

	public DaySpan daysFromNumber(Integer number) throws IllegalArgumentException;

}
