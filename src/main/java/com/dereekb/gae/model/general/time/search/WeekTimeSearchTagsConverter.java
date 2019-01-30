package com.dereekb.gae.model.general.time.search;

import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekSpan;
import com.dereekb.gae.model.general.time.WeekTime;


public interface WeekTimeSearchTagsConverter {

	public Set<String> buildTags(WeekSpan weekSpan);

	public Set<String> buildTags(WeekTime weekTime);

	public Set<String> buildTags(DayTimeSpanPair pair);

	public Set<String> buildTags(Day day,
	                             TimeSpan timeSpan);

}
