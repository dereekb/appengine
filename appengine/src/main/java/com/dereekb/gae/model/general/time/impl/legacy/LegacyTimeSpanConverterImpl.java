package com.dereekb.gae.model.general.time.impl.legacy;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.DaySpanBitImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;
import com.dereekb.gae.model.general.time.util.TimeValueConverter;
import com.dereekb.gae.model.general.time.util.impl.TimeValueConverterImpl;

/**
 * Used for converting {@link LegacyTimeSpan} instances to non-legacy
 * components.
 *
 * @author dereekb
 *
 */
public class LegacyTimeSpanConverterImpl {

	private static final TimeValueConverter DEFAULT_CONVERTER = new TimeValueConverterImpl();

	private TimeValueConverter converter;

	public LegacyTimeSpanConverterImpl() {
		this.converter = DEFAULT_CONVERTER;
	}

	public LegacyTimeSpanConverterImpl(TimeValueConverter converter) {
		this.converter = converter;
	}

	public TimeValueConverter getConverter() {
		return this.converter;
	}

	public void setConverter(TimeValueConverter converter) {
		this.converter = converter;
	}

	// MARK: Converter
	public WeekSpan convertToWeekSpan(Iterable<LegacyTimeSpan> timeSpans) {
		WeekSpanImpl weekSpan = new WeekSpanImpl();

		List<WeekTime> weekTimes = this.convertToWeekTimes(timeSpans);
		weekSpan.addAll(weekTimes);

		return weekSpan;
	}

	public List<WeekTime> convertToWeekTimes(Iterable<LegacyTimeSpan> timeSpans) {
		List<WeekTime> times = new ArrayList<WeekTime>();

		for (LegacyTimeSpan timeSpan : timeSpans) {
			WeekTime time = this.convertToWeekTime(timeSpan);
			times.add(time);
		}

		return times;
	}

	public WeekTime convertToWeekTime(LegacyTimeSpan legacyTimeSpan) {
		DaySpan daySpan = new DaySpanBitImpl(legacyTimeSpan.getDaysByte());

		Time start = this.converter.timeFromNumber(legacyTimeSpan.getFrom());
		Time end = this.converter.timeFromNumber(legacyTimeSpan.getTo());
		TimeSpan timeSpan = new TimeSpanImpl(start, end);

		WeekTimeImpl weekTime = new WeekTimeImpl(daySpan, timeSpan);
		return weekTime;
	}

	@Override
	public String toString() {
		return "LegacyTimeSpanConverterImpl []";
	}

}
