package com.dereekb.gae.model.general.time;

import java.util.List;

/**
 * An object that can be represented as a set of {@link DayTimeSpanPair}
 * objects.
 *
 * @author dereekb
 *
 */
public interface DayTimeSpanPairSetConvertable {

	/**
	 * Gets a list of all {@link DayTimeSpanPair} values within the
	 * {@link WeekSpan}.
	 *
	 * @return {@link List} of {@link DayTimeSpanPair} values. Never
	 *         {@code null}.
	 */
	public List<DayTimeSpanPair> toDayTimeSpanPairs();

}
