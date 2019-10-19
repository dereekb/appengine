package com.dereekb.gae.server.search.response.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.search.response.SearchServiceReadResponse;
import com.google.appengine.api.search.Document;

/**
 * {@link SearchServiceReadResponse} implementation.
 *
 * @author dereekb
 *
 */
public class SearchServiceReadResponseImpl
        implements SearchServiceReadResponse {

	private List<Document> documents;
	private List<String> missingDocuments;

	public SearchServiceReadResponseImpl(List<Document> documents, List<String> missingDocuments) {
		this.setDocuments(documents);
		this.setMissingDocuments(missingDocuments);
	}

	public static SearchServiceReadResponseImpl responseForDocument(String identifier,
	                                                                 Document document) {
		List<Document> documents;
		List<String> missingDocuments;

		if (document == null) {
			documents = null;
			missingDocuments = new ArrayList<String>(1);
			missingDocuments.add(identifier);
		} else {
			documents = new ArrayList<Document>(1);
			documents.add(document);
			missingDocuments = null;
		}

		return new SearchServiceReadResponseImpl(documents, missingDocuments);
	}

	@Override
	public Document getFirstDocument() {
		Document document;

		try {
			document = this.documents.get(0);
		} catch (IndexOutOfBoundsException e) {
			document = null;
		}

		return document;
	}

	@Override
	public List<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(List<Document> documents) {
		if (documents == null) {
			documents = Collections.emptyList();
		}

		this.documents = documents;
	}

	@Override
	public List<String> getMissingDocuments() {
		return this.missingDocuments;
	}

	public void setMissingDocuments(List<String> missingDocuments) {
		if (missingDocuments == null) {
			missingDocuments = Collections.emptyList();
		}

		this.missingDocuments = missingDocuments;
	}

	@Override
	public String toString() {
		return "SearchDocumentReadResponseImpl [documents=" + this.documents + ", missingDocuments="
		        + this.missingDocuments + "]";
	}

}
