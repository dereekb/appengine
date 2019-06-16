package com.dereekb.gae.model.extension.search.document.search.task;

import com.dereekb.gae.model.extension.deprecated.search.document.search.SearchPair;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} for performing a search using {@link SearchPair} instances.
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public class DocumentSearchPairsTask<Q>
        implements IterableTask<SearchPair<Q>> {

	@Override
	public void doTask(Iterable<SearchPair<Q>> input) throws FailedTaskException {
		// TODO Auto-generated method stub

	}

}
