package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.initializer.impl;

import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.initializer.StagedDocumentBuilderInitializer;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * Implementation of {@link StagedDocumentBuilderInitializer} that takes in a
 * {@link SearchableUniqueModel} and builds a {@link Document.Builder} instance
 * set with the model's document ID, identifier, and type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelStagedDocumentBuilderInitializer<T extends SearchableUniqueModel>
        implements StagedDocumentBuilderInitializer<T> {

	/**
	 * Default model key Document field identifier.
	 */
	public static final String MODEL_KEY_FIELD_KEY = "M_ID";

	/**
	 * Default model key Type field identifier.
	 */
	public static final String MODEL_TYPE_FIELD_KEY = "M_TYPE";

	private String type;

	public ModelStagedDocumentBuilderInitializer(Class<T> type) {
		this(type.getSimpleName());
	}

	public ModelStagedDocumentBuilderInitializer(String type) throws IllegalArgumentException {
		this.setType(type);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Type cannot be null or empty.");
		}

		this.type = type;
	}

	@Override
	public Builder newBuilder(T model) {
		Document.Builder builder = Document.newBuilder();

		// Search Id
		String searchIdentifier = model.getSearchIdentifier();

		if (searchIdentifier != null) {
			builder.setId(searchIdentifier);
		}

		// Key Field
		String key = model.getModelKey().keyAsString();
		Field.Builder keyField = SearchDocumentBuilderUtility.atomField(MODEL_KEY_FIELD_KEY, key);
		builder.addField(keyField);

		// Type Field
		Field.Builder typeField = SearchDocumentBuilderUtility.atomField(MODEL_TYPE_FIELD_KEY, this.type);
		builder.addField(typeField);

		return builder;
	}

	@Override
	public String toString() {
		return "ModelStagedDocumentBuilderInitializer [type=" + this.type + "]";
	}

}
