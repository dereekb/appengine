package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.server.search.system.SearchDocumentReadSystem;
import com.dereekb.gae.server.search.system.request.DocumentIdentifierRequest;
import com.dereekb.gae.server.search.system.request.impl.DocumentIdentifierRequestImpl;
import com.dereekb.gae.server.search.system.response.SearchDocumentReadResponse;


public abstract class SearchableTaskQueueEditControllerEntryTest<T extends SearchableUniqueModel> extends TaskQueueEditControllerEntryTest<T> {

	private String searchIndex;

	@Autowired
	private SearchDocumentReadSystem system;

	public String getSearchIndex() {
		return this.searchIndex;
	}

	public void setSearchIndex(String searchIndex) {
		this.searchIndex = searchIndex;
	}

	public SearchDocumentReadSystem getSystem() {
		return this.system;
	}

	public void setSystem(SearchDocumentReadSystem system) {
		this.system = system;
	}

	@Override
	protected boolean isProperlyInitialized(T model) {
		boolean hasSearchId = (model.getSearchIdentifier() != null);
		return hasSearchId;
	}

	@Override
	protected boolean isProperlyDeleted(T model) {
		String searchId = model.getSearchIdentifier();
		DocumentIdentifierRequest docRequest = new DocumentIdentifierRequestImpl(this.searchIndex, searchId);
		SearchDocumentReadResponse docResponse = this.system.readDocuments(docRequest);
		boolean docDeleted = (docResponse.getFirstDocument() == null);
		return docDeleted;
	}

}
