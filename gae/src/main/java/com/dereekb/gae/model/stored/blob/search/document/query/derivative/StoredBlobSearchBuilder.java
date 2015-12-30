package com.dereekb.gae.model.stored.blob.search.document.query.derivative;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDocumentBuilderStep;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilderSource;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;

public class StoredBlobSearchBuilder {

	public static final String DEFAULT_ID_FIELD = ModelDocumentBuilderUtility.ID_FIELD;
	public static final String DEFAULT_ENDING_FIELD = StoredBlobDocumentBuilderStep.ENDING_FIELD;
	public static final String DEFAULT_DATE_FIELD = ModelDocumentBuilderUtility.DATE_FIELD;

	private String prefix = StoredBlobDerivativeDocumentBuilderStep.DEFAULT_PREFIX;

	private String idField = DEFAULT_ID_FIELD;
	private String endingField = DEFAULT_ENDING_FIELD;
	private String dateField = DEFAULT_DATE_FIELD;

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getIdField() {
		return this.idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getEndingField() {
		return this.endingField;
	}

	public void setEndingField(String endingField) {
		this.endingField = endingField;
	}

	public String getDateField() {
		return this.dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public StoredBlobSearch make(Map<String, String> parameters) {
		StoredBlobSearch search = new StoredBlobSearch(this);

		if (parameters.containsKey(this.idField)) {
			Long id = new Long(parameters.get(this.idField));
			search.setId(id);
		}

		search.setEnding(parameters.get(this.endingField));
		search.setDate(DateSearch.fromString(parameters.get(this.dateField)));

		if (search.hasValues() == false) {
			search = null;
		}

		return search;
	}

	public String getFormat() {
		return this.prefix + "%s";
	}

	public ExpressionBuilder make(StoredBlobSearch search) {
		return this.make(search, this.getFormat());
	}

	public ExpressionBuilder make(StoredBlobSearch search,
	                              String format) {
		ExpressionBuilder builder = new ExpressionStart();

		Long id = search.getId();
		if (id != null) {
			String idName = String.format(format, this.idField);
			builder = builder.and(new AtomField(idName, id));
		}

		String ending = search.getEnding();
		if (ending != null) {
			String endingName = String.format(format, this.endingField);
			builder = builder.and(new AtomField(endingName, ending));
		}

		DateSearch date = search.getDate();
		if (date != null) {
			String dateFormat = String.format(format, this.dateField);
			builder = builder.and(date.make(dateFormat));
		}

		return builder;
	}

	/**
	 * Search component for derivative fields.
	 *
	 * @author dereekb
	 * @see {@link StoredBlobDerivativeDocumentBuilderStep}
	 */
	public final class StoredBlobSearch
	        implements ExpressionBuilderSource {

		private Long id;
		private String ending;
		private DateSearch date;

		private final StoredBlobSearchBuilder builder;

		private StoredBlobSearch(StoredBlobSearchBuilder builder) {
			if (builder == null) {
				throw new IllegalArgumentException("Builder is required.");
			}

			this.builder = builder;
		}

		public Long getId() {
			return this.id;
		}

		private void setId(Long id) {
			this.id = id;
		}

		public String getEnding() {
			return this.ending;
		}

		private void setEnding(String ending) {
			this.ending = ending;
		}

		public DateSearch getDate() {
			return this.date;
		}

		private void setDate(DateSearch date) {
			this.date = date;
		}

		public boolean hasValues() {
			return (this.date != null || this.ending != null || this.id != null);
		}

		@Override
		public ExpressionBuilder makeExpression() {
			return this.builder.make(this);
		}

	}

}
