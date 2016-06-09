package com.dereekb.gae.model.general.time.search;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.impl.HourImpl;

public class WeekTimeSearchTag {

	public static final Integer DAY_SIZE = 32;

	private final Day day;
	private final Hour hour;

	public WeekTimeSearchTag(Day day, Hour hour) {
		this.day = day;
		this.hour = hour;
	}

	public Day getDay() {
		return this.day;
	}

	public Hour getHour() {
		return this.hour;
	}

	public String buildTag() {
		return buildTag(this.day, this.hour);
	}

	// MARK: Conversions
	public static final String buildTag(Day day,
	                                    Hour hour) {
		int dayNumber = day.getBit() * DAY_SIZE;
		return buildTag(dayNumber, hour.getDayHour());
	}

	public static final String buildTag(int dayNumber,
	                                    int hourNumber) {
		Integer tagBit = dayNumber + hourNumber;
		return Integer.toHexString(tagBit);
	}

	public static final WeekTimeSearchTag decodeTag(String hexTag) {
		int tagBit = Integer.parseInt(hexTag, 16);

		int hourNumber = tagBit % DAY_SIZE;
		int dayNumber = (tagBit - hourNumber) / DAY_SIZE;

		Day day = Day.dayForBit(dayNumber);
		Hour hour = new HourImpl(hourNumber);

		return new WeekTimeSearchTag(day, hour);
	}


}
