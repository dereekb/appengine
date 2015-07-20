package com.dereekb.gae.model.extension.search.document.index.service.impl;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Implementation of {@link DocumentIndexService}.
 *
 * @author dereekb
 *
 */
public class DocumentIndexServiceImpl<T extends UniqueSearchModel>
        implements DocumentIndexService<T> {

	@Override
	public boolean indexChange(Iterable<T> models,
	                           IndexAction action) throws AtomicOperationException {
		// TODO Auto-generated method stub
		return false;
	}

}
