package com.dereekb.gae.server.search.service.request;

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
public interface DocumentPutRequestModel {

	/**
	 * @return {@link Document} to put into the index.
	 */
	public Document getDocument();

	/**
	 * @return {@link String} of the current document identifier. Return
	 *         {@code null} if the document currently has no
	 *         document/identifier.
	 */
	public String getSearchDocumentIdentifier();

	/**
	 * Called to link the saved identifier back to the request model.
	 *
	 * @param document
	 *            identifier. Never {@code null}.
	 */
	public void setSearchDocumentIdentifier(String identifier);

}
