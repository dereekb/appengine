package com.dereekb.gae.model.general.time.search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.util.impl.WeekTimeConverterImpl;

/**
 * {@link WeekTimeSearchTagsConverter} implementation.
 *
 * @author dereekb
 *
 */
public class WeekTimeSearchTagsConverterImpl
        implements WeekTimeSearchTagsConverter {

	public static final WeekTimeSearchTagsConverterImpl CONVERTER = new WeekTimeSearchTagsConverterImpl();
	public static final Integer DEFAULT_THRESHOLD = 30;

	public Set<String> buildTagsForEncoded(List<Integer> weekTimeIntegers) {
		Set<String> tags = new HashSet<String>();

		for (Integer weekTimeInteger : weekTimeIntegers) {
			Set<String> timeTags = this.buildTagsForEncoded(weekTimeInteger);
			tags.addAll(timeTags);
		}

		return tags;
	}

	public Set<String> buildTagsForEncoded(Integer weekTimeInteger) {
		WeekTime weekTime = WeekTimeConverterImpl.CONVERTER.weekTimeFromNumber(weekTimeInteger);
		return this.buildTags(weekTime);
	}

	@Override
	public Set<String> buildTags(WeekSpan weekSpan) {
		List<DayTimeSpanPair> pairs = weekSpan.toDayTimeSpanPairs();
		return this.buildTags(pairs);
	}

	@Override
	public Set<String> buildTags(WeekTime weekTime) {
		List<DayTimeSpanPair> pairs = weekTime.toDayTimeSpanPairs();
		return this.buildTags(pairs);
	}

	public Set<String> buildTags(Iterable<DayTimeSpanPair> pairs) {
		Set<String> tags = new HashSet<String>();

		for (DayTimeSpanPair pair : pairs) {
			Set<String> pairTags = this.buildTags(pair);
			tags.addAll(pairTags);
		}

		return tags;
	}

	@Override
    public Set<String> buildTags(DayTimeSpanPair pair) {
		return this.buildTags(pair.getDay(), pair.getTimeSpan());
    }

	@Override
	public Set<String> buildTags(Day day,
	                             TimeSpan timeSpan) {
		Set<String> tags = new HashSet<String>();

		List<Integer> hours = TimeSpanImpl.getMilitaryHours(timeSpan, DEFAULT_THRESHOLD);
		int dayNumber = day.getBit() * 32;

		for (Integer hour : hours) {
			Integer tagBit = dayNumber + hour;
			String tagHex = Integer.toHexString(tagBit);
			tags.add(tagHex);
		}

		return tags;
	}

	public String buildTag(Day day,
	                       Hour hour) {
		int dayNumber = day.getBit() * 32;
		Integer tagBit = dayNumber + hour.getDayHour();
		return Integer.toHexString(tagBit);
	}

}
