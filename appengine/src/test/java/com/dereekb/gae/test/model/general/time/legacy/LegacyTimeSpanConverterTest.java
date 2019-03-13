package com.dereekb.gae.test.model.general.time.legacy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.legacy.LegacyTimeSpan;
import com.dereekb.gae.model.general.time.impl.legacy.LegacyTimeSpanConverterImpl;


public class LegacyTimeSpanConverterTest {

	private LegacyTimeSpanConverterImpl converter = new LegacyTimeSpanConverterImpl();

	@Test
	public void testTimeSpanConverter() {

		// 0AM to 12PM
		LegacyTimeSpan a = new LegacyTimeSpan(6, 0, (Time.MAX_TIME / 2));
		TimeSpan aSpan = TimeSpanImpl.fromMidnight(TimeImpl.noon());

		WeekTime aWeek = this.converter.convertToWeekTime(a);
		assertNotNull(aWeek);
		assertTrue(aSpan.compareTo(aWeek.getTimeSpan()) == 0);

		// Test Days
		// Monday, Tuesday, Wednesday, Thursday, Sunday
		LegacyTimeSpan daysTest = new LegacyTimeSpan(158, 0, (Time.MAX_TIME / 2));
		WeekTime daysWeek = this.converter.convertToWeekTime(daysTest);
		DaySpan daySpan = daysWeek.getDaySpan();
		Set<Day> days = daySpan.getDays();

		Day[] expectedDaysArray = new Day[] { Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.SUNDAY };
		assertTrue(days.containsAll(Arrays.asList(expectedDaysArray)));

	}

	@Test
	public void testMultipleTimeSpanConverter() {

		// 0AM to 12PM
		LegacyTimeSpan a = new LegacyTimeSpan(63, (Time.MAX_TIME - 400), Time.MAX_TIME);
		LegacyTimeSpan b = new LegacyTimeSpan(255, 0, (Time.MAX_TIME / 2));

		List<LegacyTimeSpan> timeSpans = new ArrayList<LegacyTimeSpan>();
		timeSpans.add(a);
		timeSpans.add(b);

		WeekSpan week = this.converter.convertToWeekSpan(timeSpans);
		assertNotNull(week);

		assertTrue(week.contains(this.converter.convertToWeekTime(a)));
		assertTrue(week.contains(this.converter.convertToWeekTime(b)));
	}

	/**
	 * Is not working properly, or expectedly.
	 *
	 * http://thevisitapp.com/api/places/read?identifiers=461001
	 *
	 * "timespans":[{"daysByte":158,"from":1020,"to":1260},{"daysByte":96,"from"
	 * :1020,"to":1320}]
	 */
	@Test
	public void testCaseA() {

		List<LegacyTimeSpan> timespans = new ArrayList<LegacyTimeSpan>();
		Integer openTime = 1020;

		timespans.add(new LegacyTimeSpan(158, openTime, 1260));
		timespans.add(new LegacyTimeSpan(96, openTime, 1320));

		WeekSpan week = this.converter.convertToWeekSpan(timespans);
		List<DayTimeSpanPair> dayTimeSpanPairs = week.toDayTimeSpanPairs();

		assertNotNull(dayTimeSpanPairs);
		assertTrue(dayTimeSpanPairs.size() == 7); // Every day of the
														 // week.

		/*
		for (DayTimeSpanPair pair : dayTimeSpanPairs) {
			assertTrue(pair.getTimeSpan().getStartTime() == new TimeImpl(openTime));
		}
		*/

	}

}
