package com.dereekb.gae.model.extension.search.document.index.component.builder;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Used for generating documents for objects that implement the
 * {@link UniqueSearchModel} interface.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface SearchDocumentBuilder<T extends UniqueSearchModel> {

	/**
	 * Creates a new {@link Document} for the passed model.
	 */
	public Document buildSearchDocument(T model);

}
