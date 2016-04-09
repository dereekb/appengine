package com.dereekb.gae.model.stored.image.set.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.stored.image.set.search.document.index.StoredImageSetDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.set.search.document.query.StoredImageSetSearchBuilder.StoredImageSetSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilderSource;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;
import com.dereekb.gae.utilities.collections.map.MapReader;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link StoredImageSetSearch} elements.
 *
 * @author dereekb
 *
 */
public class StoredImageSetSearchBuilder
        implements Factory<StoredImageSetSearch> {

	public static final String DEFAULT_LABEL_FIELD = StoredImageSetDocumentBuilderStep.LABEL_FIELD;
	public static final String DEFAULT_DETAIL_FIELD = StoredImageSetDocumentBuilderStep.DETAIL_FIELD;
	public static final String DEFAULT_TAGS_FIELD = StoredImageSetDocumentBuilderStep.TAGS_FIELD;

	private String prefix = "";

	private String labelField = DEFAULT_LABEL_FIELD;
	private String detailField = DEFAULT_DETAIL_FIELD;
	private String tagsField = DEFAULT_TAGS_FIELD;

	public StoredImageSetSearchBuilder() {}

	public StoredImageSetSearchBuilder(String prefix) {
		this.setPrefix(prefix);
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

    public String getLabelField() {
		return this.labelField;
	}

	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}

	public String getDetailField() {
		return this.detailField;
	}

	public void setDetailField(String detailField) {
		this.detailField = detailField;
	}

	public String getTagsField() {
		return this.tagsField;
	}

	public void setTagsField(String tagsField) {
		this.tagsField = tagsField;
	}

	public StoredImageSetSearch make(Map<String, String> parameters) {
		StoredImageSetSearch search = new StoredImageSetSearch(parameters);

		if (search.hasValues() == false) {
			search = null;
		}

		return search;
	}

	public void applyParameters(StoredImageSetSearch search,
	                            Map<String, String> parameters) {
		MapReader<String> reader = new MapReader<String>(parameters, this.getFormat());

		search.setLabel(reader.get(this.labelField));
		search.setDetail(reader.get(this.detailField));
		search.setTags(reader.get(this.tagsField));
	}

	public String getFormat() {
		return this.prefix + "%s";
	}

	public ExpressionBuilder make(StoredImageSetSearch search) {
		return this.make(search, this.getFormat());
	}

	public ExpressionBuilder make(StoredImageSetSearch search,
	                              String format) {
		ExpressionBuilder builder = new ExpressionStart();

		String label = search.getLabel();
		if (label != null) {
			String labelName = String.format(format, this.labelField);
			builder = builder.and(new AtomField(labelName, label));
		}

		String detail = search.getDetail();
		if (detail != null) {
			String detailName = String.format(format, this.detailField);
			builder = builder.and(new AtomField(detailName, detail));
		}

		String tags = search.getTags();
		if (tags != null) {
			String tagsName = String.format(format, this.tagsField);
			builder = builder.and(new AtomField(tagsName, tags));
		}

		return builder;
	}

	@Override
	public StoredImageSetSearch make() throws FactoryMakeFailureException {
		return new StoredImageSetSearch();
	}

	public class StoredImageSetSearch
	        implements ExpressionBuilderSource {

		private String label;
		private String detail;
		private String tags;

		private StoredImageSetSearch() {}

		public StoredImageSetSearch(Map<String, String> parameters) {
			this.applyParameters(parameters);
		}

		public void applyParameters(Map<String, String> parameters) {
			StoredImageSetSearchBuilder.this.applyParameters(this, parameters);
		}

		public String getLabel() {
			return this.label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getDetail() {
			return this.detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public String getTags() {
			return this.tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public boolean hasValues() {
			return (this.label != null || this.detail != null || this.tags != null);
		}

		@Override
		public ExpressionBuilder makeExpression() {
			return StoredImageSetSearchBuilder.this.make(this);
		}

	}

}
