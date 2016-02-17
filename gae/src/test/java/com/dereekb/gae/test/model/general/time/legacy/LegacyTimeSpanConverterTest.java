package com.dereekb.gae.test.model.general.time.legacy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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
		Assert.assertNotNull(aWeek);
		Assert.assertTrue(aSpan.compareTo(aWeek.getTimeSpan()) == 0);

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
		Assert.assertNotNull(week);

		Assert.assertTrue(week.contains(this.converter.convertToWeekTime(a)));
		Assert.assertTrue(week.contains(this.converter.convertToWeekTime(b)));
	}

}
