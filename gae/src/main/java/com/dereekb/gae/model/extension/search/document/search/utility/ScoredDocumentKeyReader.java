package com.dereekb.gae.model.extension.search.document.search.utility;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Used for reading a {@link ModelKey} from a {@link ScoredDocument}.
 * 
 * @author dereekb
 *
 */
public interface ScoredDocumentKeyReader {

	public ModelKey keyFromDocument(ScoredDocument document);

}
