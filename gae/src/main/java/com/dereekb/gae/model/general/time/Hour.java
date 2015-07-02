package com.dereekb.gae.model.general.time;

/**
 * Represents the hours in a day.
 *
 * @author dereekb
 *
 */
public interface Hour {

	/**
	 * @return {@link TimeAmPm} for the hour. Never null.
	 */
	public TimeAmPm getAmPm();

	/**
	 * Returns the hour for this time. Ranges from 1-12.
	 *
	 * @return {@link Integer} between 1-12.
	 */
	public Integer getHour();

}
