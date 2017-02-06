package com.dereekb.gae.model.general.time.util.impl;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.util.TimeStringConverter;

/**
 * {@link TimeStringConverter} implementation.
 *
 * @author dereekb
 *
 */
public class TimeStringConverterImpl
        implements TimeStringConverter {

	public final static String DEFAULT_TIME_FORMAT = "%s:%02d%s";
	public final static String DEFAULT_TIME_SPAN_FORMAT = "%s - %s";
	public final static String DEFAULT_DAY_TIME_PAIR_FORMAT = "%s";
	public final static String TIME_REGEX = "^(([a-zA-Z])+)$|^((((\\d){1,2})?)((((:)?(\\d){2}))?)(((\\s)?([a-zA-Z])+))?)$";

	private boolean useMilitaryTime = false;
	private String timeFormat = DEFAULT_TIME_FORMAT;
	private String timeSpanFormat = DEFAULT_TIME_SPAN_FORMAT;

	private boolean useDayAbbreviation = false;
	private String dayTimePairFormat = DEFAULT_DAY_TIME_PAIR_FORMAT;

	private String amString = "AM";
	private String pmString = "PM";

	public TimeStringConverterImpl() {};

	public String getTimeFormat() {
		return this.timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getTimeSpanFormat() {
		return this.timeSpanFormat;
	}

	public void setTimeSpanFormat(String timeSpanFormat) {
		this.timeSpanFormat = timeSpanFormat;
	}
	public boolean isUseMilitaryTime() {
		return this.useMilitaryTime;
	}

	public void setUseMilitaryTime(boolean useMilitaryTime) {
		this.useMilitaryTime = useMilitaryTime;
	}

	public boolean isUseDayAbbreviation() {
		return this.useDayAbbreviation;
	}

	public void setUseDayAbbreviation(boolean useDayAbbreviation) {
		this.useDayAbbreviation = useDayAbbreviation;
	}

	public String getDayTimePairFormat() {
		return this.dayTimePairFormat;
	}

	public void setDayTimePairFormat(String dayTimePairFormat) {
		this.dayTimePairFormat = dayTimePairFormat;
	}

	public String getAmString() {
		return this.amString;
	}

	public void setAmString(String amString) {
		this.amString = amString;
	}

	public String getPmString() {
		return this.pmString;
	}

	public void setPmString(String pmString) {
		this.pmString = pmString;
	}

	// TimeStringConverter
	public String convertToString(TimeAmPm amPm) {
		String string;

		switch (amPm) {
			case AM:
				string = this.amString;
				break;
			case PM:
				string = this.pmString;
				break;
			default:
				throw new RuntimeException("Unsupported TimeAmPm.");
		}

		return string;
	}

	@Override
	public String convertToString(Time time) {
		Hour hour = time.getHour();

		String amPmString;
		Integer hourNumber;
		Integer minuteNumber = time.getMinutes();

		if (this.useMilitaryTime) {
			hourNumber = hour.getDayHour();
			amPmString = "";
		} else {
			hourNumber = hour.getVisualHour();
			amPmString = this.convertToString(hour.getAmPm());
		}

		return String.format(this.timeFormat, hourNumber, minuteNumber, amPmString);
	}

	@Override
	public String convertToString(TimeSpan timeSpan) {
		String start = this.convertToString(timeSpan.getStartTime());
		String end = this.convertToString(timeSpan.getEndTime());
		return String.format(this.timeSpanFormat, start, end);
	}

	@Override
	public String convertToString(Day day) {
		String string;

		if (this.useDayAbbreviation) {
			string = day.getAbbreviation();
		} else {
			string = day.getName();
		}

		return string;
	}

	@Override
	public String convertToString(DayTimeSpanPair pair) {
		String timeSpanString = this.convertToString(pair.getTimeSpan());
		String dayString = this.convertToString(pair.getDay());
		return String.format(this.dayTimePairFormat, timeSpanString, dayString);
	}

	@Override
	public Time timeFromString(String timeString) {
		return null;
	}

}
