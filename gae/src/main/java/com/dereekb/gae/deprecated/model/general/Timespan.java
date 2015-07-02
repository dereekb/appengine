package com.thevisitcompany.gae.deprecated.model.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thevisitcompany.gae.model.general.time.Day;
import com.thevisitcompany.gae.utilities.misc.ByteAccessor;

/**
 * Represents a timespan from one time to another time.
 *
 * @author dereekb
 */
@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Timespan {

	public static final Integer MIN_DAYS_VALUE = 0;
	public static final Integer MAX_DAYS_VALUE = 255; // 0xFF
	public static final Integer MAX_24_HOUR_TIME = 1440; // 0xFFF

	/**
	 * Weekdays stored in as a single integer/unsigned byte and decoded using bit-shifting.
	 *
	 * Timespans with 0 days are invalid.
	 */
	@Min(1)
	@Max(255)
	private int days;

	@Min(0)
	@Max(1440)
	private int from;

	@Min(0)
	@Max(1440)
	private int to;

	public Timespan() {
		this.days = 0;
		this.from = 0;
		this.to = 0;
	};

	public Timespan(int days, int from, int to) {
		this.setDaysByte(days);
		this.from = from;
		this.to = to;
	};

	public Timespan(Set<String> daySet, String from, String to) {

		if (daySet != null) {
			this.setDayWithStrings(daySet);
		}

	};

	public int getFrom() {
		return this.from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	@JsonIgnore
	public String fromTimeString() {
		return Timespan.hoursToString(this.from);
	}

	@JsonIgnore
	public void setFromTime(String fromTime) {
		this.from = stringToMinutes(fromTime);
	}

	public int getTo() {
		return this.to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	@JsonIgnore
	public String toTimeString() {
		return Timespan.hoursToString(this.to);
	}

	@JsonIgnore
	public void setToTime(String toTime) {
		this.to = stringToMinutes(toTime);
	}

	@JsonInclude
	public int getDaysByte() {
		return this.days;
	}

	public void setDaysByte(int days) {
		this.days = ((days > MAX_DAYS_VALUE) ? MAX_DAYS_VALUE : (days >= MIN_DAYS_VALUE) ? days : MIN_DAYS_VALUE);
	}

	public void toggleDay(Day day,
	                      boolean on) {
		ByteAccessor accessor = new ByteAccessor(this.days);
		accessor.setByte(on, day.bit);
		this.days = accessor.getValue();
	}

	public boolean containsDay(Day day) {
		ByteAccessor accessor = new ByteAccessor(this.days);
		return accessor.readByte(day.bit);
	}

	@JsonIgnore
	public List<Day> getDays() {
		List<Day> days = new ArrayList<Day>(7);
		ByteAccessor accessor = new ByteAccessor();

		if (accessor.readByte(Day.Monday.bit)) {
			days.add(Day.Monday);
		}

		if (accessor.readByte(Day.Tuesday.bit)) {
			days.add(Day.Tuesday);
		}

		if (accessor.readByte(Day.Wednesday.bit)) {
			days.add(Day.Wednesday);
		}

		if (accessor.readByte(Day.Thursday.bit)) {
			days.add(Day.Thursday);
		}

		if (accessor.readByte(Day.Friday.bit)) {
			days.add(Day.Friday);
		}

		if (accessor.readByte(Day.Saturday.bit)) {
			days.add(Day.Saturday);
		}

		if (accessor.readByte(Day.Sunday.bit)) {
			days.add(Day.Sunday);
		}

		return days;
	}

	@JsonIgnore
	public void setDays(Collection<Day> days) throws IllegalArgumentException {

		if (days == null) {
			throw new IllegalArgumentException("Cannot pass a null Days collection.");
		}

		Set<Day> daysSet = new HashSet<Day>(days);
		ByteAccessor accessor = new ByteAccessor();

		if (daysSet.contains(Day.Monday)) {
			accessor.setByte(true, Day.Monday.bit);
		}

		if (daysSet.contains(Day.Tuesday)) {
			accessor.readByte(Day.Tuesday.bit);
		}

		if (daysSet.contains(Day.Wednesday)) {
			accessor.readByte(Day.Wednesday.bit);
		}

		if (daysSet.contains(Day.Thursday)) {
			accessor.readByte(Day.Thursday.bit);
		}

		if (daysSet.contains(Day.Friday)) {
			accessor.readByte(Day.Friday.bit);
		}

		if (daysSet.contains(Day.Saturday)) {
			accessor.readByte(Day.Saturday.bit);
		}

		if (daysSet.contains(Day.Sunday)) {
			accessor.readByte(Day.Sunday.bit);
		}

	}

	public void setDayWithStrings(Collection<String> days) throws IllegalArgumentException {

		if (days == null) {
			throw new IllegalArgumentException("Cannot pass a null Days collection.");
		}

		ByteAccessor accessor = new ByteAccessor();
		Set<String> daySet = new HashSet<String>(days);

		if (daySet.contains("Monday")) {
			accessor.setByte(true, Day.Monday.bit);
		}

		if (daySet.contains("Tuesday")) {
			accessor.setByte(true, Day.Tuesday.bit);
		}

		if (daySet.contains("Wednesday")) {
			accessor.setByte(true, Day.Wednesday.bit);
		}

		if (daySet.contains("Thursday")) {
			accessor.setByte(true, Day.Thursday.bit);
		}

		if (daySet.contains("Friday")) {
			accessor.setByte(true, Day.Friday.bit);
		}

		if (daySet.contains("Saturday")) {
			accessor.setByte(true, Day.Saturday.bit);
		}

		if (daySet.contains("Sunday")) {
			accessor.setByte(true, Day.Sunday.bit);
		}

		this.days = accessor.getValue();
	}

	// Static Functions

	// Converts from integer of minutes to time in hours.
	public static String hoursToString(int minutes) {
		Integer hours = (int) Math.floor((minutes % 2400) / 60);
		Integer remainder = minutes % 60;

		String remainderString = remainder.toString();

		if (remainderString.length() == 1) {
			remainderString += "0";
		}

		// log.info("Minutes: " + minutes);
		// log.info("Timespan: " + hours);

		boolean AM = true;

		if (hours >= 12) {
			AM = false; // We're in the PM
			hours %= 12; // Timespan = Timespan % 12;
		}

		if (hours == 0) {
			hours = 12;
		}

		if (AM) {
			return "" + hours + ":" + remainderString + "AM";
		} else {
			return "" + hours + ":" + remainderString + "PM";
		}
	}

	// Converts from integer of minutes to time in hours.
	public static int stringToMinutes(String Time) {
		// Convert 12:00PM to 12*60;
		// Convert 1200 to 12*60;
		// Convert 2400 to 0;
		// Convert 10 to 10*60;
		// Convert 10PM to 22*60;
		// Convert 10:30PM to 22*60 + 30;
		// Convert null strings to 12:00AM, or 0 Minutes;
		// Convert 12 PM to 12*60.
		// Convert "34pm54546" to 10PM
		boolean AM = true;

		// Find if AM or PM or am or pm
		Time = Time.toLowerCase();
		Time = Time.replaceAll("\\s", ""); // Remove spaces.
		if (Time.contains("m")) { // Contains an AM or PM
			AM = Time.contains("a"); // Contains am, or ma, or a.m., etc. Is the morning.

			// Attempt to cut string into two parts: 12:00, AM
			if (AM) {
				Time = Time.split("a")[0]; // Split at the A,a.
			} else {
				Time = Time.split("p")[0]; // Split at the P,p.
			}
		}

		// Remove ":" if it exists
		Time = Time.replace(":", "");

		if (Time.isEmpty()) {
			return AM ? 0 : 720; // If AM, return 0. If PM, return 12:00
		} else if (Time.length() > 2) {
			// If 3-4 elements:
			// Floor of 1200/100 + PM*12
			// remainder of 1200%60 for minutes.
			Integer value = new Integer(Time); // Turn "1200" into 1200;
			int Timespan = (value / 100);
			int Minutes = (value - (Timespan * 100)); // Remaining

			Timespan = Timespan % 24; // Done so we don't have a ton of extra minutes.

			if (Timespan > 12) {
				AM = false; // We're in the PM
				Timespan %= 12; // Timespan = Timespan % 12;
			} else if (Timespan == 12) {
				Timespan = 0; // 12AM is 0 hours
			}

			return (((Timespan + (AM ? 0 : 12)) * 60) + Minutes) % MAX_24_HOUR_TIME; // Max of 24 hours

		} else {
			// If 1-2 elements:
			// Value is most between 1-12.
			Integer value = new Integer(Time); // Turn "1200" into 1200;
			int Timespan = value; // Value cannot be bigger than 23, as there is no 24th hour.

			if (Timespan > 12) {
				AM = false; // We're in the PM
				Timespan %= 12; // Timespan = Timespan % 12;
			} else if ((Timespan == 12) && AM) {
				Timespan = 0; // 12AM is 0 hours
			}

			return ((Timespan + (AM ? 0 : 12)) * 60) % 1440;
		}
	}

}
