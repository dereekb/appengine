package com.dereekb.gae.utilities.time;

import java.util.Date;

public class DateUtility {

	public static Date getDateIn(Long milliseconds) {
		return getDateIn(System.currentTimeMillis(), milliseconds);
	}

	public static Date getDateIn(Date start,
	                             Long milliseconds) {
		return getDateIn(start.getTime(), milliseconds);
	}

	public static Date getDateIn(Long start,
	                             Long milliseconds) {
		return new Date(start + milliseconds);
	}

}
