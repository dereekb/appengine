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

	// MARK: Comparisons
	public static boolean dateIsAfterDate(Date a,
	                                      Date b) {
		return dateIsAfterDate(a, b, null);
	}

	public static boolean dateIsAfterDate(Date a,
	                                      Date b,
	                                      Long precision) {
		return precisionCompare(a, b, -precision) > 0;
	}

	public static boolean dateIsEqualOrAfterDate(Date a,
	                                             Date b) {
		return dateIsEqualOrAfterDate(a, b, null);
	}

	public static boolean dateIsEqualOrAfterDate(Date a,
	                                             Date b,
	                                             Long precision) {
		return precisionCompare(a, b, -precision) >= 0;
	}

	public static int precisionCompare(Date a,
	                                   Date b,
	                                   Long precisionDelta) {
		if (precisionDelta != null) {
			a = new Date(a.getTime() + precisionDelta);
		}

		return a.compareTo(b);
	}

	public static Date roundDateDownToSecond(Date issued) {
		Long time = issued.getTime();
		time = time / 1000;
		return new Date(time * 1000);
	}

}
