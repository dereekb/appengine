package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.server.search.service.request.DocumentPutRequestModel;

/**
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexingDocument<T extends UniqueSearchModel>
        extends DocumentPutRequestModel {

	/**
	 * Returns the model.
	 *
	 * @return Model associated with this document. Never {@code null}.
	 */
	public T getDocumentModel();

}
