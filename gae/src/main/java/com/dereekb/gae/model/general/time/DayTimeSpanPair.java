package com.dereekb.gae.model.general.time;


/**
 * Contains a {@link Day} and a {@link TimeSpan} paired together.
 *
 * @author dereekb
 *
 */
public interface DayTimeSpanPair {

	public Day getDay();

	public TimeSpan getTimeSpan();

	public boolean isNow();

}
