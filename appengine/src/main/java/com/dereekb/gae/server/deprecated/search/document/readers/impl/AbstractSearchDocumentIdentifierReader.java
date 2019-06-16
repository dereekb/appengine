package com.dereekb.gae.server.search.document.readers.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.initializer.impl.ModelStagedDocumentBuilderInitializer;
import com.dereekb.gae.server.deprecated.search.document.readers.SearchDocumentIdentifierReader;
import com.google.appengine.api.search.Document;

public abstract class AbstractSearchDocumentIdentifierReader<K>
        implements SearchDocumentIdentifierReader<K> {

	private final String field;

	public AbstractSearchDocumentIdentifierReader() {
		this(ModelStagedDocumentBuilderInitializer.MODEL_KEY_FIELD_KEY);
	}

	public AbstractSearchDocumentIdentifierReader(String field) {
		this.field = field;
	}

	protected abstract K convertIdentifierAtom(String atom);

	@Override
	public K readIdentifier(Document document) {
		String value = document.getOnlyField(this.field).getAtom();
		K identifier = this.convertIdentifierAtom(value);
		return identifier;
	}

	@Override
	public List<K> readIdentifiers(Iterable<? extends Document> results) {
		List<K> identifiers = new ArrayList<K>();

		for (Document result : results) {
			K identifier = this.readIdentifier(result);
			identifiers.add(identifier);
		}

		return identifiers;
	}

}
