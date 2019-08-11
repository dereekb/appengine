package com.dereekb.gae.server.search.iterator;

import java.util.List;

import com.google.appengine.api.search.Document;

/**
 * A batch of documents.
 *
 * @author dereekb
 *
 */
public interface DocumentIteratorBatch {

	public List<String> getIdentifiers();

	public List<Document> getDocuments();

	public Integer getBatchSize();

	public Document getLastDocument();

	public String getLastIdentifier();

}
