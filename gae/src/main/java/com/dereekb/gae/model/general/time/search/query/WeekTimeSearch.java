package com.dereekb.gae.model.general.time.search.query;

import com.dereekb.gae.model.general.time.search.index.WeekTimeDocumentBuilderUtility;
import com.dereekb.gae.model.general.time.search.index.WeekTimeDocumentBuilderUtility.FieldFormatter;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.TextField;

/**
 * Used for searching for WeekTime values that match.
 *
 * Can search between an hour range and/or specify a single day.
 *
 * @author dereekb
 *
 * @see {@link WeekTimeDocumentBuilderUtility}
 */
public class WeekTimeSearch {

	private static final String SPLITTER = ",";

	private String day;
	private Integer start;
	private Integer end;

	public WeekTimeSearch(String day) {
		this(day, null, null);
	}

	public WeekTimeSearch(Integer start) {
		this(start, null);
	}

	public WeekTimeSearch(Integer start, Integer end) {
		this(null, start, end);
	}

	public WeekTimeSearch(String day, Integer start, Integer end) {
		this.setDay(day);
		this.setStart(start);
		this.setEnd(end);
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getStart() {
		return this.start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return this.end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	/**
	 * Creates a {@link WeekTimeSearch} from the input string.
	 * <p>
	 * Format: OPEN?(Integer), CLOSE?(Integer), DAY?(String)
	 *
	 * @param searchString
	 * @return {@link WeekTimeSearch} or {@code null} if nothing is input.
	 * @throws IllegalArgumentException
	 */
	public static WeekTimeSearch fromString(String searchString) throws IllegalArgumentException {
		WeekTimeSearch search = null;

		if (searchString != null && searchString.isEmpty() == false) {
			try {
				String[] split = searchString.split(SPLITTER);

				String day = null;
				Integer open = null;
				Integer close = null;

				switch (split.length) {
					default:
					case 3: // Open/Close/Day
						open = new Integer(split[0]);
						close = new Integer(split[1]);
						day = split[2];
						break;
					case 2: // Open/Close or Open/Day
						open = new Integer(split[0]);
						ParsedTimeString arg2 = ParsedTimeString.parse(split[1]);
						close = arg2.time;
						day = arg2.day;
						break;
					case 1: // Open or Day
						ParsedTimeString arg = ParsedTimeString.parse(split[0]);
						open = arg.time;
						day = arg.day;
						break;
				}

				search = new WeekTimeSearch(day, open, close);
			} catch (Exception e) {
				throw new IllegalArgumentException("Could not create time search.", e);
			}
		}

		return search;
	}

	private static class ParsedTimeString {

		private String day;
		private Integer time;

		private static ParsedTimeString parse(String time) {
			ParsedTimeString parsed = new ParsedTimeString();

			try {
				parsed.time = new Integer(time);
			} catch (NumberFormatException e) {
				parsed.day = time;
			}

			return parsed;
		}

	}

	public ExpressionBuilder make(String field) {
		FieldFormatter formatter = new FieldFormatter(field);

		ExpressionBuilder day = null;
		ExpressionBuilder builder;

		if (this.day != null) {
			day = new TextField(formatter.daysField(), this.day);
		}

		if (this.start != null) {
			builder = new AtomField(formatter.startField(), this.start);

			if (this.end != null) {
				builder = new AtomField(formatter.endField(), this.end);
			}

			if (day != null) {
				builder = builder.and(day);
			}
		} else {
			builder = day;
		}

		return builder;
	}

	@Override
	public String toString() {
		return "WeekTimeSearch [day=" + this.day + ", start=" + this.start + ", end=" + this.end + "]";
	}

}
