package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.search.DocumentChangeSet;
import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Wraps a collection of {@link IndexingDocument} instances with the name of the
 * index.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link UniqueSearchModel} model.
 */
public interface IndexingDocumentSet<T extends UniqueSearchModel>
        extends DocumentChangeSet {

}
