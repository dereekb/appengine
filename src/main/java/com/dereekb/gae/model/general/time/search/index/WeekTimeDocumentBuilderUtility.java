package com.dereekb.gae.model.general.time.search.index;

import java.util.Set;

import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.util.impl.TimeValueConverterImpl;
import com.dereekb.gae.model.general.time.util.impl.WeekTimeConverterImpl;
import com.dereekb.gae.utilities.misc.SubFieldNameFormatter;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.appengine.api.search.Document.Builder;

/**
 * Used for embedding {@link WeekTime} info into a search document.
 *
 * @author dereekb
 *
 */
public class WeekTimeDocumentBuilderUtility {

	public static final WeekTimeDocumentBuilderUtility UTILITY = new WeekTimeDocumentBuilderUtility();

	public static final String DEFAULT_FIELD_NAME = "week";

	public static final String DAYS_FIELD = "days";
	public static final String START_FIELD = "start";
	public static final String END_FIELD = "end";

	private String daysSplitter = " ";

	public String getDaysSplitter() {
		return this.daysSplitter;
	}

	public void setDaysSplitter(String daysSplitter) {
		this.daysSplitter = daysSplitter;
	}

	// MARK: Make
	public Instance make() {
		return new Instance();
	};

	public Instance make(Integer encodedHours) {
		WeekTime weekTime = null;

		if (encodedHours != null) {
			weekTime = WeekTimeConverterImpl.CONVERTER.weekTimeFromNumber(encodedHours);
		}

		return this.make(weekTime);
	};

	public Instance make(WeekTime weekTime) {
		return new Instance(weekTime);
	};

	// TODO: Add Extended class with more information such as an open all day
	// boolean.

	public static class FieldFormatter extends SubFieldNameFormatter {

		public FieldFormatter() {
			this(DEFAULT_FIELD_NAME);
		}

		public FieldFormatter(String field) {
			super(field);
		}

		public String daysField() {
			return this.format(DAYS_FIELD);
		}

		public String startField() {
			return this.format(START_FIELD);
		}

		public String endField() {
			return this.format(END_FIELD);
		}

	}

	public class Instance {

		private FieldFormatter formatter = new FieldFormatter();

		private String days = null;
		private Integer start = null;
		private Integer end = null;

		private Instance() {}

		private Instance(WeekTime weekTime) {
			this.setWeekTime(weekTime);
		}

		public void setWeekTime(WeekTime weekTime) {
			if (weekTime != null) {
				DaySpan daySpan = weekTime.getDaySpan();
				Set<Day> days = daySpan.getDays();

				this.days = this.makeDaysString(days);

				// Time
				TimeSpan timeSpan = weekTime.getTimeSpan();
				Time start = timeSpan.getStartTime();
				Time end = timeSpan.getEndTime();

				this.start = TimeValueConverterImpl.CONVERTER.timeToNumber(start);
				this.end = TimeValueConverterImpl.CONVERTER.timeToNumber(end);
			} else {
				this.days = null;
				this.start = null;
				this.end = null;
			}
		}

		private String makeDaysString(Set<Day> days) {
			Joiner joiner = Joiner.on(WeekTimeDocumentBuilderUtility.this.daysSplitter).skipNulls();
			Set<String> strings = Day.getKeyStrings(days);
			return joiner.join(strings);
		}

		public FieldFormatter getFieldNamer() {
			return this.formatter;
		}

		public void setFieldFormat(String format) {
			this.formatter.setFieldFormat(format);
		}

		// MARK: Attach
		public void attach(Builder builder) {

			// Days
			String daysName = this.formatter.daysField();
			SearchDocumentBuilderUtility.addText(daysName, this.days, builder);

			// Times
			String startName = this.formatter.startField();
			SearchDocumentBuilderUtility.addAtom(startName, this.start, builder);

			String endName = this.formatter.endField();
			SearchDocumentBuilderUtility.addAtom(endName, this.end, builder);

		}

	}

	@Override
	public String toString() {
		return "WeekTimeDocumentBuilderUtility [daysSplitter=" + this.daysSplitter + "]";
	}

}
