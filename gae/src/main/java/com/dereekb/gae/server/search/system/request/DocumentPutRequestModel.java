package com.dereekb.gae.server.search.system.request;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Model used by {@link DocumentPutRequest}.
 * <p>
 * When the request is completed, it will be used as a delegate to bind the
 * saved document's identifier back to the model.
 *
 * @author dereekb
 *
 */
public interface DocumentPutRequestModel
        extends UniqueSearchModel {

	/**
	 * @return {@link Document} to put into the index.
	 */
	public Document getDocument();

}
