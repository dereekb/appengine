package com.dereekb.gae.model.extension.search.document.search.utility.impl;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.initializer.impl.ModelStagedDocumentBuilderInitializer;
import com.dereekb.gae.model.extension.search.document.search.utility.ScoredDocumentKeyReader;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.ScoredDocument;

/**
 * {@link ScoredDocumentKeyReader} implementation.
 *
 * @author dereekb
 *
 */
public class ScoredDocumentKeyReaderImpl
        implements ScoredDocumentKeyReader {

	private static final String DEFAULT_ID_FIELD = ModelStagedDocumentBuilderInitializer.MODEL_KEY_FIELD_KEY;

	private String idField;
	private StringModelKeyConverter converter;

	public ScoredDocumentKeyReaderImpl(StringModelKeyConverter converter) {
		this(DEFAULT_ID_FIELD, converter);
	}

	public ScoredDocumentKeyReaderImpl(String idField, StringModelKeyConverter converter) {
		this.idField = idField;
		this.converter = converter;
	}

	public String getIdField() {
		return this.idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public StringModelKeyConverter getConverter() {
		return this.converter;
	}

	public void setConverter(StringModelKeyConverter converter) {
		this.converter = converter;
	}

	// MARK: ScoredDocumentKeyReader
	@Override
	public ModelKey keyFromDocument(ScoredDocument document) {
		Field field = document.getOnlyField(this.idField);
		String value = field.getAtom();
		return this.converter.convertSingle(value);
	}

	@Override
	public String toString() {
		return "ScoredDocumentKeyReaderImpl [idField=" + this.idField + ", converter=" + this.converter + "]";
	}

}
