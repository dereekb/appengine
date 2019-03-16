package com.dereekb.gae.server.search.system.iterator;

import java.util.List;

import com.google.appengine.api.search.Document;

public interface DocumentIteratorBatch {

	public List<String> getIdentifiers();

	public List<Document> getDocuments();

	public Integer getBatchSize();

	public Document getLastDocument();

	public String getLastIdentifier();

}
