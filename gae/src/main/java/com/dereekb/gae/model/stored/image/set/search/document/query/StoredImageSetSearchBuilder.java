package com.dereekb.gae.model.stored.image.set.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchBuilderImpl;
import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchImpl;
import com.dereekb.gae.model.stored.image.set.search.document.index.StoredImageSetDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.set.search.document.query.StoredImageSetSearchBuilder.StoredImageSetSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.utilities.collections.map.StringMapReader;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link StoredImageSetSearch} elements.
 *
 * @author dereekb
 *
 */
public class StoredImageSetSearchBuilder extends AbstractSearchBuilderImpl<StoredImageSetSearch> {

	public static final String DEFAULT_LABEL_FIELD = StoredImageSetDocumentBuilderStep.LABEL_FIELD;
	public static final String DEFAULT_DETAIL_FIELD = StoredImageSetDocumentBuilderStep.DETAIL_FIELD;
	public static final String DEFAULT_TAGS_FIELD = StoredImageSetDocumentBuilderStep.TAGS_FIELD;

	private String labelField = DEFAULT_LABEL_FIELD;
	private String detailField = DEFAULT_DETAIL_FIELD;
	private String tagsField = DEFAULT_TAGS_FIELD;

	public StoredImageSetSearchBuilder() {
		super(StoredImageSetDocumentBuilderStep.DERIVATIVE_PREFIX);
	}

	public StoredImageSetSearchBuilder(String prefix) {
		super(prefix);
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

	// MARK: Search
	@Override
	public StoredImageSetSearch make() throws FactoryMakeFailureException {
		return new StoredImageSetSearch();
	}

	@Override
	public StoredImageSetSearch makeNewSearch(Map<String, String> parameters) {
		return new StoredImageSetSearch(parameters);
	}

	@Override
	protected void applyParametersToSearch(StoredImageSetSearch search,
	                                       StringMapReader reader) {

		search.setLabel(reader.get(this.labelField));
		search.setDetail(reader.get(this.detailField));
		search.setTags(reader.get(this.tagsField));
	}

	// MAKE: Make
	@Override
	public ExpressionBuilder buildExpression(StoredImageSetSearch search,
	                                         String format,
	                                         ExpressionBuilder builder) {

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

	public class StoredImageSetSearch extends AbstractSearchImpl {

		private String label;
		private String detail;
		private String tags;

		private StoredImageSetSearch() {}

		public StoredImageSetSearch(Map<String, String> parameters) {
			this.applyParameters(parameters);
		}

		@Override
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

		@Override
		public ExpressionBuilder makeExpression() {
			return StoredImageSetSearchBuilder.this.make(this);
		}

		@Override
		public String toString() {
			return "StoredImageSetSearch [label=" + this.label + ", detail=" + this.detail + ", tags=" + this.tags
			        + "]";
		}

	}

}
