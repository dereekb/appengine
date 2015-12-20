package com.dereekb.gae.model.extension.search.document.search.utility.deprecated;

import com.google.appengine.api.search.ScoredDocument;

/**
 * Used for reading identifiers from a {@link ScoredDocument}.
 *
 * @author dereekb
 */
@Deprecated
public interface ScoredDocumentKeyReader<T> {

	public T readKeyFromDocument(ScoredDocument document);

}
