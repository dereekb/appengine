package com.dereekb.gae.model.extension.search.document.index.component.builder.impl;

import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.component.builder.StagedDocumentBuilderInit;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * Implementation of {@link StagedDocumentBuilderInit} that takes in a
 * {@link SearchableUniqueModel} and builds a {@link Document.Builder} instance
 * set with the model's document ID, identifier, and type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class ModelStagedDocumentBuilderInit<T extends SearchableUniqueModel>
        implements StagedDocumentBuilderInit<T> {

	/**
	 * Default model key Document field identifier.
	 */
	public static final String MODEL_KEY_FIELD_KEY = "M_ID";

	/**
	 * Default model key Type field identifier.
	 */
	public static final String MODEL_TYPE_FIELD_KEY = "M_TYPE";

	private final String type;

	public ModelStagedDocumentBuilderInit(Class<T> type) {
		this(type.getSimpleName());
	}

	public ModelStagedDocumentBuilderInit(String type) throws IllegalArgumentException {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Type cannot be null or empty.");
		}

		this.type = type;
	}

	public String getType() {
		return this.type;
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
		String key = model.getModelKey().toString();
		Field.Builder keyField = SearchDocumentBuilderUtility.atomField(MODEL_KEY_FIELD_KEY, key);
		builder.addField(keyField);

		// Type Field
		Field.Builder typeField = SearchDocumentBuilderUtility.atomField(MODEL_TYPE_FIELD_KEY, this.type);
		builder.addField(typeField);

		return builder;
	}

}
