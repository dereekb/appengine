package com.dereekb.gae.model.general.time.search.query;

import com.dereekb.gae.model.deprecated.general.time.search.index.WeekSpanDocumentBuilderUtility;
import com.dereekb.gae.model.deprecated.general.time.search.index.WeekSpanDocumentBuilderUtility.FieldFormatter;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.impl.field.TextField;

/**
 * Used for searching for WeekSpan hour hex tags.
 *
 * @author dereekb
 *
 * @see WeekSpanDocumentBuilderUtility
 */
public class WeekSpanSearch {

	private String hourHexTags;

	public WeekSpanSearch() {}

	public WeekSpanSearch(String hourHexTags) {
		this.setHourHexTags(hourHexTags);
	}

	public String getHourHexTags() {
		return this.hourHexTags;
	}

	public void setHourHexTags(String hourHexTags) {
		this.hourHexTags = hourHexTags;
	}

	/**
	 * Creates a {@link WeekSpanSearch} from the input string.
	 * <p>
	 * Format: TAGS(String)
	 *
	 * @param searchString
	 * @return {@link WeekSpanSearch} or {@code null} if nothing is input.
	 * @throws IllegalArgumentException
	 */
	public static WeekSpanSearch fromString(String searchString) throws IllegalArgumentException {
		WeekSpanSearch search = null;

		if (searchString != null && searchString.isEmpty() == false) {
			try {
				search = new WeekSpanSearch(searchString);
			} catch (Exception e) {
				throw new IllegalArgumentException("Could not create time span search.", e);
			}
		}

		return search;
	}

	public ExpressionBuilder make(String field) {
		FieldFormatter formatter = new FieldFormatter(field);
		ExpressionBuilder builder = null;

		if (this.hourHexTags != null) {
			builder = new TextField(formatter.hoursField(), this.hourHexTags);
		}

		return builder;
	}

	@Override
	public String toString() {
		return "WeekSpanSearch [hourHexTags=" + this.hourHexTags + "]";
	}

}
