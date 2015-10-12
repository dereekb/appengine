package com.dereekb.gae.server.search.service.request;

import java.util.Collection;

/**
 * Document request for reading multiple documents by their identifier.
 *
 * @author dereekb
 *
 */
public interface DocumentIdentifierRequest
        extends SearchDocumentRequest {

	/**
	 * Identifiers corresponding to the documents to read.
	 *
	 * @return {@link Collection} of document identifiers to read. Never
	 *         {@code null}.
	 */
	public Collection<String> getDocumentIdentifiers();

}
