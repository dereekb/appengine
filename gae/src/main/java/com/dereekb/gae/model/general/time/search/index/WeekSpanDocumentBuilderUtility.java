package com.dereekb.gae.model.general.time.search.index;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.time.WeekSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.search.impl.WeekTimeSearchTagsConverterImpl;
import com.dereekb.gae.model.general.time.util.impl.WeekTimeConverterImpl;
import com.dereekb.gae.utilities.misc.SubFieldNameFormatter;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.appengine.api.search.Document.Builder;

/**
 * Used for embedding {@link WeekSpan} info into a search document.
 *
 * @author dereekb
 *
 */
public class WeekSpanDocumentBuilderUtility {

	public static final WeekSpanDocumentBuilderUtility UTILITY = new WeekSpanDocumentBuilderUtility();

	public static final String DEFAULT_FIELD_NAME = "times";

	public static final String HOURS_FIELD = "hours";

	private String hoursSplitter = " ";

	public String getHoursSplitter() {
		return this.hoursSplitter;
	}

	public void setHoursSplitter(String hoursSplitter) {
		this.hoursSplitter = hoursSplitter;
	}

	// MARK: Make
	public Instance make() {
		return new Instance();
	};

	public Instance makeWithEncodedTimes(Iterable<Integer> encodedTimes) {
		List<WeekTime> weekTimes = null;

		if (encodedTimes != null) {
			weekTimes = WeekTimeConverterImpl.CONVERTER.weekTimesFromNumbers(encodedTimes);
		}

		return this.make(weekTimes);
	};

	public Instance make(List<WeekTime> weekTimes) {
		return new Instance(weekTimes);
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

		public String hoursField() {
			return this.format(HOURS_FIELD);
		}

	}

	public class Instance {

		private FieldFormatter formatter = new FieldFormatter();

		private String hours = null;

		private Instance() {}

		private Instance(List<WeekTime> weekTimes) {
			this.setWeekTimes(weekTimes);
		}

		public void setWeekTimes(List<WeekTime> weekTimes) {
			if (weekTimes != null && weekTimes.size() > 0) {
				Set<String> tags = WeekTimeSearchTagsConverterImpl.CONVERTER.buildTags(weekTimes);
				Joiner joiner = Joiner.on(WeekSpanDocumentBuilderUtility.this.hoursSplitter).skipNulls();

				this.hours = joiner.join(tags);
			} else {
				this.hours = null;
			}
		}

		public FieldFormatter getFieldNamer() {
			return this.formatter;
		}

		public void setFieldFormat(String format) {
			this.formatter.setFieldFormat(format);
		}

		// MARK: Attach
		public void attach(Builder builder) {

			// Times
			String hoursName = this.formatter.hoursField();
			SearchDocumentBuilderUtility.addText(hoursName, this.hours, builder);

		}

	}

}
