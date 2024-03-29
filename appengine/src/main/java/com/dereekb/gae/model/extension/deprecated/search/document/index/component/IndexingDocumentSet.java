package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.deprecated.search.UniqueSearchModel;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentPutRequest;
import com.dereekb.gae.server.deprecated.search.system.request.impl.DocumentPutRequestImpl;

/**
 * Extension of {@link DocumentPutRequestImpl} that includes and updates models
 * when the request is processed.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexingDocumentSet<T extends UniqueSearchModel>
        extends DocumentPutRequest {

}
