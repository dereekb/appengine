package com.dereekb.gae.model.general.time.util.impl;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.Time;
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

	public final static String DEFAULT_TIME_FORMAT = "%s%s";
	public final static String DEFAULT_TIME_SPAN_FORMAT = "%s - %s";
	public final static String DEFAULT_DAY_TIME_PAIR_FORMAT = "%s";
	public final static String TIME_REGEX = "^(([a-zA-Z])+)$|^((((\\d){1,2})?)((((:)?(\\d){2}))?)(((\\s)?([a-zA-Z])+))?)$";

	private boolean useMilitaryTime = false;
	private String timeFormat = DEFAULT_TIME_FORMAT;
	private String timeSpanFormat = DEFAULT_TIME_SPAN_FORMAT;

	private boolean useDayAbbreviation = false;
	private String dayTimePairFormat = DEFAULT_DAY_TIME_PAIR_FORMAT;

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

	// TimeStringConverter
	@Override
	public String convertToString(Time time) {
		Hour hour = time.getHour();
		Integer hourNumber;
		Integer minuteNumber = time.getMinutes();

		if (this.useMilitaryTime) {
			hourNumber = hour.getDayHour();
		} else {
			hourNumber = hour.getVisualHour();
		}

		return String.format(this.timeFormat, hourNumber, minuteNumber);
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
		// TODO Auto-generated method stub
		return null;
	}

}
