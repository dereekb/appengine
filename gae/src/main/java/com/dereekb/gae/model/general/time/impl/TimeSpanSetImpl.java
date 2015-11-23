package com.dereekb.gae.model.general.time.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.TimeSpanSet;
import com.dereekb.gae.model.general.time.util.TimeSpanSimplifier;
import com.dereekb.gae.model.general.time.util.exception.MergeTimeSpanException;
import com.dereekb.gae.model.general.time.util.impl.TimeSpanSimplifierImpl;

/**
 * {@link TimeSpanSet} implementation.
 *
 * @author dereekb
 *
 */
public class TimeSpanSetImpl
        implements TimeSpanSet {

	private static final TimeSpanSimplifier TIMESPAN_SIMPLIFIER = new TimeSpanSimplifierImpl();

	private List<TimeSpan> timeSpans = new ArrayList<TimeSpan>();

	public List<TimeSpan> getTimeSpans() {
		return this.timeSpans;
	}

	@Override
	public void add(TimeSpan input) {
		input = new TimeSpanImpl(input);

		if (this.timeSpans.isEmpty()) {
			this.timeSpans.add(input);
		} else {
			this.timeSpans = this.insert(input);
		}
	}

	private List<TimeSpan> insert(TimeSpan input) {
		List<TimeSpan> timeSpans = new ArrayList<TimeSpan>();
		TimeSpan current = input;

		for (TimeSpan span : this.timeSpans) {
			try {
				current = TIMESPAN_SIMPLIFIER.merge(span, current);
			} catch (MergeTimeSpanException e) {
				timeSpans.add(span);
			}
		}

		timeSpans.add(current);
		Collections.sort(timeSpans);
		return timeSpans;
	}

	@Override
	public void remove(TimeSpan input) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(Time time) {
		boolean contained = false;

		for (TimeSpan span : this.timeSpans) {
			if (TIMESPAN_SIMPLIFIER.isContained(span, time)) {
				contained = true;
				break;
			}
		}

		return contained;
	}

	@Override
	public boolean contains(TimeSpan timeSpan) {
		boolean contained = false;

		for (TimeSpan span : this.timeSpans) {
			if (TIMESPAN_SIMPLIFIER.isContained(span, timeSpan)) {
				contained = true;
				break;
			}
		}

		return contained;
	}

	@Override
	public void clear() {
		this.timeSpans.clear();
	}

}
