package com.dereekb.gae.utilities.time;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtility {

	public static final String MIN_DATE_KEY = "min";
	public static final String MAX_DATE_KEY = "max";
	public static final String EPOCH_KEY = "epoch";
	public static final String NOW_KEY = "now";

	public static final Long TIME_IN_MINUTE = 1000 * 60L;
	public static final Long TIME_IN_HOUR = TIME_IN_MINUTE * 60L;
	public static final Long TIME_IN_DAY = TIME_IN_HOUR * 24;
	
	private static final Map<Long, String> DATE_KEY_VALUE_MAP = new HashMap<Long, String>();

	static {
		DATE_KEY_VALUE_MAP.put(Long.MIN_VALUE, MIN_DATE_KEY);
		DATE_KEY_VALUE_MAP.put(Long.MAX_VALUE, MAX_DATE_KEY);
		DATE_KEY_VALUE_MAP.put(0L, EPOCH_KEY);
	}

	public static String getKeyForDate(Date date) {
		Long dateValue = (date != null) ? date.getTime() : null;
		return DATE_KEY_VALUE_MAP.get(dateValue);
	}

	public static Date getDateForKey(String key) {
		Date date = null;

		if (key != null && key.length() < 6) {
			switch (key) {
				case EPOCH_KEY:
					date = new Date(0L);
					break;
				case NOW_KEY:
					date = new Date();
					break;
				case MIN_DATE_KEY:
					date = new Date(Long.MIN_VALUE);
					break;
				case MAX_DATE_KEY:
					date = new Date(Long.MAX_VALUE);
					break;
			}
		}

		return date;
	}

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

	/**
	 * Returns true if the specified amount of time has passed since the input
	 * date.
	 * 
	 * @param origin
	 *            {@link Date}. Never {@code null}.
	 * @param time
	 *            {@link Long}. Never {@code null}.
	 * @return {@code true} if the specified amount of time has passed.
	 */
	public static boolean timeHasPassed(Date origin,
	                                    Long time) {
		return timeHasPassed(new Date(), origin, time);
	}

	public static boolean timeHasPassed(Date now,
	                                    Date origin,
	                                    Long time) {
		Date reference = new Date(origin.getTime() + time);
		return dateIsEqualOrAfterDate(now, reference);
	}

	public static boolean dateIsAfterDate(Date a,
	                                      Date b) {
		return dateIsAfterDate(a, b, null);
	}

	public static boolean dateIsAfterDate(Date a,
	                                      Date b,
	                                      Long precision) {
		if (precision == null) {
			precision = 0L;
		}

		return precisionCompare(a, b, -precision) > 0;
	}

	public static boolean dateIsEqualOrAfterDate(Date a,
	                                             Date b) {
		return dateIsEqualOrAfterDate(a, b, null);
	}

	public static boolean dateIsEqualOrAfterDate(Date a,
	                                             Date b,
	                                             Long precision) {
		if (precision == null) {
			precision = 0L;
		}

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

	public static Long timeInDays(Integer days) {
		return timeInDays(days.longValue());
	}
	
	public static Long timeInDays(Long days) {
		return TIME_IN_DAY * days;
	}

}
