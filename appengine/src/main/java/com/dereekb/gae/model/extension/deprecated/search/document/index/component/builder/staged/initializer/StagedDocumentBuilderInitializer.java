package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.initializer;

import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Used by {@link StagedDocumentBuilder} to initialize the document by creating
 * a new {@link Document.Builder} instance.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface StagedDocumentBuilderInitializer<T extends UniqueSearchModel> {

	public Document.Builder newBuilder(T model);

}
