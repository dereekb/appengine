package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.deprecated.search.UniqueSearchModel;

/**
 * Used for building an {@link IndexingDocument} for the input model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexingDocumentBuilder<T extends UniqueSearchModel> {

	/**
	 *
	 * @param model
	 *            input model. Never {@code null}.
	 * @return {@link IndexingDocument} for the input model. Never {@code null}.
	 */
	public IndexingDocument<T> buildSearchDocument(T model);

}
