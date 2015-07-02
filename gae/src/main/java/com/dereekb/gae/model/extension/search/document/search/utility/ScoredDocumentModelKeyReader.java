package com.dereekb.gae.model.extension.search.document.search.utility;

import com.dereekb.gae.model.extension.search.document.index.component.builder.ModelStagedDocumentBuilderInit;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Implementation of {@link ScoredDocumentKeyReader} that retrieves
 * {@link ModelKey} keys.
 *
 * @author dereekb
 */
@Deprecated
public class ScoredDocumentModelKeyReader
        implements ScoredDocumentKeyReader<ModelKey> {

	private final String fieldKey;
	private final ModelKeyType keyType;

	public ScoredDocumentModelKeyReader() {
		this(ModelStagedDocumentBuilderInit.MODEL_KEY_FIELD_KEY, ModelKeyType.NUMBER);
	}

	public ScoredDocumentModelKeyReader(String fieldKey, ModelKeyType keyType) {
		this.fieldKey = fieldKey;
		this.keyType = keyType;
	}

	@Override
	public ModelKey readKeyFromDocument(ScoredDocument document) {
		Field field = document.getOnlyField(this.fieldKey);
		String value = field.getAtom();
		ModelKey key;

		switch (this.keyType) {
			case NUMBER:
				try {
					Long number = new Long(value);
					key = new ModelKey(number);
					break;
				} catch (NumberFormatException e) {

				}
			case NAME:
			default:
				key = new ModelKey(value);
		}

		return key;
	}

	public String getFieldKey() {
		return this.fieldKey;
	}

	public ModelKeyType getKeyType() {
		return this.keyType;
	}

}
