package com.dereekb.gae.model.stored.blob.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDocumentBuilderStep;
import com.dereekb.gae.model.stored.blob.search.document.query.StoredBlobSearchBuilder.StoredBlobSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilderSource;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;
import com.dereekb.gae.utilities.collections.map.MapReader;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link StoredBlobSearch} elements.
 *
 * @author dereekb
 *
 */
public class StoredBlobSearchBuilder
        implements Factory<StoredBlobSearch> {

	public static final String DEFAULT_ID_FIELD = ModelDocumentBuilderUtility.ID_FIELD;
	public static final String DEFAULT_DATE_FIELD = ModelDocumentBuilderUtility.DATE_FIELD;
	public static final String DEFAULT_TYPE_FIELD = StoredBlobDocumentBuilderStep.TYPE_FIELD;
	public static final String DEFAULT_NAME_FIELD = StoredBlobDocumentBuilderStep.NAME_FIELD;
	public static final String DEFAULT_ENDING_FIELD = StoredBlobDocumentBuilderStep.ENDING_FIELD;

	private String prefix = StoredBlobDocumentBuilderStep.DERIVATIVE_PREFIX;

	private String idField = DEFAULT_ID_FIELD;
	private String dateField = DEFAULT_DATE_FIELD;
	private String typeField = DEFAULT_TYPE_FIELD;
	private String nameField = DEFAULT_NAME_FIELD;
	private String endingField = DEFAULT_ENDING_FIELD;

	public StoredBlobSearchBuilder() {}

	public StoredBlobSearchBuilder(String prefix) {
		this.prefix = prefix;
	}

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
		StoredBlobSearch search = new StoredBlobSearch(parameters);

		if (search.hasValues() == false) {
			search = null;
		}

		return search;
	}

	public void applyParameters(StoredBlobSearch search,
	                            Map<String, String> parameters) {
		MapReader<String> reader = new MapReader<String>(parameters, this.getFormat());

		if (reader.containsKey(this.idField)) {
			Long id = new Long(reader.get(this.idField));
			search.setId(id);
		}

		search.setEnding(reader.get(this.endingField));
		search.setDate(DateSearch.fromString(reader.get(this.dateField)));
	}

	public String getFormat() {
		return this.prefix + "%s";
	}

	// MAKE: Make
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

		String type = search.getType();
		if (type != null) {
			String typeName = String.format(format, this.typeField);
			builder = builder.and(new AtomField(typeName, type));
		}

		String name = search.getName();
		if (name != null) {
			String nameName = String.format(format, this.nameField);
			builder = builder.and(new AtomField(nameName, name));
		}

		return builder;
	}

	@Override
	public StoredBlobSearch make() throws FactoryMakeFailureException {
		return new StoredBlobSearch();
	}

	public class StoredBlobSearch
	        implements ExpressionBuilderSource {

		// Both
		private DateSearch date;

		// Derivative
		private Long id;
		private String ending;

		// StoredBlob
		private String name;
		private String type;

		private StoredBlobSearch() {}

		public StoredBlobSearch(Map<String, String> parameters) {
			this.applyParameters(parameters);
		}

		public void applyParameters(Map<String, String> parameters) {
			StoredBlobSearchBuilder.this.applyParameters(this, parameters);
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

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean hasValues() {
			return (this.date != null || this.ending != null || this.id != null || this.name != null || this.type != null);
		}

		@Override
		public ExpressionBuilder makeExpression() {
			return StoredBlobSearchBuilder.this.make(this);
		}

		@Override
		public String toString() {
			return "StoredBlobSearch [date=" + this.date + ", id=" + this.id + ", ending=" + this.ending + ", name="
			        + this.name + ", type=" + this.type + "]";
		}

	}

}
