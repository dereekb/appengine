package com.dereekb.gae.model.extension.search.document.search.utility;

import com.google.appengine.api.search.ScoredDocument;

/**
 * Used for reading identifiers from a {@link ScoredDocument}.
 *
 * @author dereekb
 */
public interface ScoredDocumentKeyReader<T> {

	public T readKeyFromDocument(ScoredDocument document);

}
