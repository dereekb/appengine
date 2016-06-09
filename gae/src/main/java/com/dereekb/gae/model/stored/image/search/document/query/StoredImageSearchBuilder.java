package com.dereekb.gae.model.stored.image.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchBuilderImpl;
import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchImpl;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.model.stored.blob.search.document.query.StoredBlobSearchBuilder;
import com.dereekb.gae.model.stored.blob.search.document.query.StoredBlobSearchBuilder.StoredBlobSearch;
import com.dereekb.gae.model.stored.image.search.document.index.StoredImageDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.search.document.query.StoredImageSearchBuilder.StoredImageSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.utilities.collections.map.StringMapReader;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link StoredImageSearch} elements.
 *
 * @author dereekb
 *
 */
public class StoredImageSearchBuilder extends AbstractSearchBuilderImpl<StoredImageSearch> {

	public static final String DEFAULT_TYPE_FIELD = StoredImageDocumentBuilderStep.TYPE_FIELD;
	public static final String DEFAULT_NAME_FIELD = StoredImageDocumentBuilderStep.NAME_FIELD;
	public static final String DEFAULT_TAGS_FIELD = StoredImageDocumentBuilderStep.TAGS_FIELD;
	public static final String DEFAULT_SUMMARY_FIELD = StoredImageDocumentBuilderStep.SUMMARY_FIELD;

	private String nameField = DEFAULT_NAME_FIELD;
	private String tagsField = DEFAULT_TAGS_FIELD;
	private String typeField = DEFAULT_TYPE_FIELD;
	private String summaryField = DEFAULT_SUMMARY_FIELD;

	private GeoPlaceSearchBuilder geoPlaceSearchBuilder = new GeoPlaceSearchBuilder();
	private StoredBlobSearchBuilder storedBlobSearchBuilder = new StoredBlobSearchBuilder();

	public StoredImageSearchBuilder() {
		super(StoredImageDocumentBuilderStep.DERIVATIVE_PREFIX);
	}

	public StoredImageSearchBuilder(String prefix) {
		super(prefix);
	}

	public String getNameField() {
		return this.nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public String getTagsField() {
		return this.tagsField;
	}

	public void setTagsField(String tagsField) {
		this.tagsField = tagsField;
	}

	public String getTypeField() {
		return this.typeField;
	}

	public void setTypeField(String typeField) {
		this.typeField = typeField;
	}

	public String getSummaryField() {
		return this.summaryField;
	}

	public void setSummaryField(String summaryField) {
		this.summaryField = summaryField;
	}

	// MARK: Search
	@Override
	public StoredImageSearch make() throws FactoryMakeFailureException {
		return new StoredImageSearch();
	}

	@Override
	public StoredImageSearch makeNewSearch(Map<String, String> parameters) {
		return new StoredImageSearch(parameters);
	}

	@Override
	protected void applyParametersToSearch(StoredImageSearch search,
	                                       StringMapReader reader) {

		search.setName(reader.get(this.nameField));
		search.setType(reader.get(this.typeField));
		search.setTags(reader.get(this.tagsField));
		search.setSummary(reader.get(this.summaryField));

		search.geoPlaceSearch.applyParameters(reader.getMap());
		search.storedBlobSearch.applyParameters(reader.getMap());
	}

	// MAKE: Make
	@Override
	public ExpressionBuilder buildExpression(StoredImageSearch search,
	                                         String format,
	                                         ExpressionBuilder builder) {

		String name = search.getName();
		if (name != null) {
			String nameName = String.format(format, this.nameField);
			builder = builder.and(new AtomField(nameName, name));
		}

		String tags = search.getTags();
		if (tags != null) {
			String tagsName = String.format(format, this.tagsField);
			builder = builder.and(new AtomField(tagsName, tags));
		}

		String type = search.getType();
		if (type != null) {
			String typeName = String.format(format, this.typeField);
			builder = builder.and(new AtomField(typeName, type));
		}

		String summary = search.getSummary();
		if (summary != null) {
			String summaryName = String.format(format, this.summaryField);
			builder = builder.and(new AtomField(summaryName, summary));
		}

		builder.and(search.geoPlaceSearch.makeExpression());
		builder.and(search.storedBlobSearch.makeExpression());

		return builder;
	}

	public class StoredImageSearch extends AbstractSearchImpl {

		private String name;
		private String summary;
		private String tags;
		private String type;

		private final GeoPlaceSearch geoPlaceSearch = StoredImageSearchBuilder.this.geoPlaceSearchBuilder.make();
		private final StoredBlobSearch storedBlobSearch = StoredImageSearchBuilder.this.storedBlobSearchBuilder.make();

		private StoredImageSearch() {}

		public StoredImageSearch(Map<String, String> parameters) {
			this.applyParameters(parameters);
		}

		@Override
        public void applyParameters(Map<String, String> parameters) {
			StoredImageSearchBuilder.this.applyParameters(this, parameters);
		}

        public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSummary() {
			return this.summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getTags() {
			return this.tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public GeoPlaceSearch getGeoPlaceSearch() {
			return this.geoPlaceSearch;
		}

		public StoredBlobSearch getStoredBlobSearch() {
			return this.storedBlobSearch;
		}

		@Override
		public ExpressionBuilder makeExpression() {
			return StoredImageSearchBuilder.this.make(this);
		}

	}

}
