package com.dereekb.gae.server.search.request;

import com.dereekb.gae.utilities.collections.pairs.impl.ResultsPair;
import com.google.appengine.api.search.Document;

/**
 * Defines a pair to process a document put with.
 *
 * @author dereekb
 *
 */
public class SearchServiceIndexRequestPair extends ResultsPair<Document, String> {

	public SearchServiceIndexRequestPair(Document source) {
		super(source);
	}

	public Document getDocument() {
		return this.key;
	}

	public String getDocumentKey() {
		return this.object;
	}

}
