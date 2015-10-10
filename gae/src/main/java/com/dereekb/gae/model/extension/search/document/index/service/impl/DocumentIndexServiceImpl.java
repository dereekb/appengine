package com.dereekb.gae.model.extension.search.document.index.service.impl;

import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.search.document.index.task.DocumentIndexPairsTask;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Implementation of {@link DocumentIndexService}.
 *
 * @author dereekb
 *
 */
public class DocumentIndexServiceImpl<T extends UniqueSearchModel>
        implements DocumentIndexService<T> {

	private DocumentIndexPairsTask<T> indexTask;

	@Override
	public boolean indexChange(Iterable<T> models,
	                           IndexAction action) throws AtomicOperationException {

		List<IndexPair<T>> pairs = IndexPair.makePairs(models, action);
		boolean success;

		try {
			this.indexTask.doTask(pairs);
			success = true;
		} catch (FailedTaskException e) {
			success = false;
		}

		return success;
	}

}
