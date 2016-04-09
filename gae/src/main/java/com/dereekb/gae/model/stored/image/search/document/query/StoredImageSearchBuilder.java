package com.dereekb.gae.model.stored.image.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.model.stored.blob.search.document.query.StoredBlobSearchBuilder;
import com.dereekb.gae.model.stored.blob.search.document.query.StoredBlobSearchBuilder.StoredBlobSearch;
import com.dereekb.gae.model.stored.image.search.document.index.StoredImageDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.search.document.query.StoredImageSearchBuilder.StoredImageSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilderSource;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;
import com.dereekb.gae.utilities.collections.map.MapReader;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link StoredImageSearch} elements.
 *
 * @author dereekb
 *
 */
public class StoredImageSearchBuilder
        implements Factory<StoredImageSearch> {

	public static final String DEFAULT_TYPE_FIELD = StoredImageDocumentBuilderStep.TYPE_FIELD;
	public static final String DEFAULT_NAME_FIELD = StoredImageDocumentBuilderStep.NAME_FIELD;
	public static final String DEFAULT_TAGS_FIELD = StoredImageDocumentBuilderStep.TAGS_FIELD;
	public static final String DEFAULT_SUMMARY_FIELD = StoredImageDocumentBuilderStep.SUMMARY_FIELD;

	private String prefix = "";

	private String nameField = DEFAULT_NAME_FIELD;
	private String tagsField = DEFAULT_TAGS_FIELD;
	private String typeField = DEFAULT_TYPE_FIELD;
	private String summaryField = DEFAULT_SUMMARY_FIELD;

	private GeoPlaceSearchBuilder geoPlaceSearchBuilder = new GeoPlaceSearchBuilder();
	private StoredBlobSearchBuilder storedBlobSearchBuilder = new StoredBlobSearchBuilder();

	public StoredImageSearchBuilder() {}

	public StoredImageSearchBuilder(String prefix) {
		this.setPrefix(prefix);
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	public StoredImageSearch make(Map<String, String> parameters) {
		StoredImageSearch search = new StoredImageSearch(parameters);

		if (search.hasValues() == false) {
			search = null;
		}

		return search;
	}

	public void applyParameters(StoredImageSearch search,
	                            Map<String, String> parameters) {
		MapReader<String> reader = new MapReader<String>(parameters, this.getFormat());

		search.setName(reader.get(this.nameField));
		search.setType(reader.get(this.typeField));
		search.setTags(reader.get(this.tagsField));
		search.setSummary(reader.get(this.summaryField));

		search.geoPlaceSearch.applyParameters(parameters);
		search.storedBlobSearch.applyParameters(parameters);
	}

	public String getFormat() {
		return this.prefix + "%s";
	}

	public ExpressionBuilder make(StoredImageSearch search) {
		return this.make(search, this.getFormat());
	}

	public ExpressionBuilder make(StoredImageSearch search,
	                              String format) {
		ExpressionBuilder builder = new ExpressionStart();

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

	@Override
	public StoredImageSearch make() throws FactoryMakeFailureException {
		return new StoredImageSearch();
	}

	public class StoredImageSearch
	        implements ExpressionBuilderSource {

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

		public boolean hasValues() {
			return (this.name != null || this.summary != null || this.tags != null || this.type != null
			        || this.geoPlaceSearch.hasValues() || this.storedBlobSearch.hasValues());
		}

		@Override
		public ExpressionBuilder makeExpression() {
			return StoredImageSearchBuilder.this.make(this);
		}

	}

}
