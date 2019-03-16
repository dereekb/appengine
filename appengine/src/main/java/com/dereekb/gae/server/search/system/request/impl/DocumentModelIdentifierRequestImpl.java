package com.dereekb.gae.server.search.system.request.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.server.search.system.request.DocumentIdentifierRequest;

/**
 * {@link DocumentIdentifierRequest} implementation that uses a collection of
 * {@link UniqueSearchModel} instances.
 *
 * @author dereekb
 *
 */
public class DocumentModelIdentifierRequestImpl extends SearchDocumentRequestImpl
        implements DocumentIdentifierRequest {

	private Collection<? extends UniqueSearchModel> searchModels;

	public DocumentModelIdentifierRequestImpl(String indexName,
 Collection<? extends UniqueSearchModel> searchModels)
	        throws IllegalArgumentException {
		super(indexName);
		this.setSearchModels(searchModels);
	}

	@Override
	public Collection<String> getDocumentIdentifiers() {
		List<String> identifiers = new ArrayList<String>();

		for (UniqueSearchModel model : this.searchModels) {
			String searchIdentifier = model.getSearchIdentifier();

			if (searchIdentifier != null) {
				identifiers.add(searchIdentifier);
			}
		}

		return identifiers;
	}

	public Collection<? extends UniqueSearchModel> getSearchModels() {
		return this.searchModels;
	}

	public void setSearchModels(Collection<? extends UniqueSearchModel> searchModels) {
		if (searchModels == null) {
			throw new IllegalArgumentException("Search models cannot be null.");
		}

		this.searchModels = searchModels;
	}

}
