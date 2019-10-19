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

	/**
	 * Returns a {@link Date} based on the input key. The keys are specified as
	 * static variables on the {@link DateUtility}.
	 *
	 * @param key
	 *            {@link String}.
	 * @return {@link Date}, or {@code null} if none found for that key or if
	 *         the input was {@code null}.
	 */
	public static Date getDateForKey(String key) {
		Date date = null;

		if (key != null) {
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

	public static Date getDateInDays(Long days) {
		return getDateIn(System.currentTimeMillis(), DateUtility.timeInDays(days));
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

	public static Double dateToSecondsDouble(Date date) {
		Long time = date.getTime();
		time = time / 1000;				// Round Value To Seconds
		return time.doubleValue();
	}

	public static Date secondsDoubleToDate(Double seconds) {
		Long time = seconds.longValue();
		time = time * 1000;				// Convert back to timestamp
		return new Date(time);
	}

	public static Double dateToMinutesDouble(Date date) {
		return dateToRoundedTimeDouble(date, DateUtility.TIME_IN_MINUTE);
	}

	public static Date minutesDoubleToDate(Double minutes) {
		return roundedTimeDoubleToDate(minutes, DateUtility.TIME_IN_MINUTE);
	}

	public static Double dateToRoundedTimeDouble(Date date, Long roundedTime) {
		Long time = date.getTime();
		time = time / roundedTime;				// Round Value To Minutes
		return time.doubleValue();
	}

	public static Date roundedTimeDoubleToDate(Double seconds, Long roundedTime) {
		Long time = seconds.longValue();
		time = time * roundedTime;				// Convert back to timestamp
		return new Date(time);
	}

	public static Long timeInDays(Integer days) {
		return timeInDays(days.longValue());
	}

	public static Long timeInDays(long days) {
		return TIME_IN_DAY * days;
	}

	public static Long timeInHours(Integer hours) {
		return timeInHours(hours.longValue());
	}

	public static Long timeInHours(long hours) {
		return TIME_IN_HOUR * hours;
	}

	public static Long timeInMinutes(Integer minutes) {
		return timeInMinutes(minutes.longValue());
	}

	public static Long timeInMinutes(long minutes) {
		return TIME_IN_MINUTE * minutes;
	}

	public static boolean dateIsInTheFutureAtleast(Date date,
	                                               long minimumScheduleTime) {
		return dateIsInTheFutureAtleast(date, minimumScheduleTime, 0L);
	}

	public static boolean dateIsInTheFutureAtleast(Date date,
	                                               long minimumScheduleTime,
	                                               long leeway) {
		Long futureTime = Math.min(0L, minimumScheduleTime - leeway);
		Date futureDate = DateUtility.getDateIn(futureTime);
		return futureDate.after(date);
	}

	/**
	 * Returns the time difference between now and the current date.
	 *
	 * @param date
	 *            {@link Date}. Never {@code null}.
	 * @return {@link Long} time difference. Never {@code null}.
	 */
	public static Long getDifference(Date date) {
		return getDifference(date, new Date());
	}

	public static Long getDifference(Date start,
	                                 Date end) {
		return end.getTime() - start.getTime();
	}

}
