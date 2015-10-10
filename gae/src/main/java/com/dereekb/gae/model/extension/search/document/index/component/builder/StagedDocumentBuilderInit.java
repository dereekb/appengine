package com.dereekb.gae.model.extension.search.document.index.component.builder;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.StagedDocumentBuilder;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Used by {@link StagedDocumentBuilder} to initialize the document.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface StagedDocumentBuilderInit<T extends UniqueSearchModel> {

	public Document.Builder newBuilder(T model);

}
