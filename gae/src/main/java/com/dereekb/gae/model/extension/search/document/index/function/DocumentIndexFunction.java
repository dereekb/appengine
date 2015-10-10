package com.dereekb.gae.model.extension.search.document.index.function;

import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.model.extension.search.document.index.task.DocumentIndexPairsTask;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;
import com.dereekb.gae.utilities.task.function.StagedFunctionTask;

/**
 * Staged function used for indexing elements using {@link IndexPair} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @deprecated use {@link StagedFunctionTask} and factory instead. See
 *             {@link DocumentIndexPairsTask}.
 */
@Deprecated
public final class DocumentIndexFunction<T extends UniqueSearchModel> extends FilteredStagedFunction<T, IndexPair<T>> {

	private final DocumentIndexPairsTask<T> task;

	public DocumentIndexFunction(DocumentIndexPairsTask<T> task) {
		this.task = task;
	}

	public DocumentIndexPairsTask<T> getTask() {
		return this.task;
	}

	@Override
	protected void doFunction() {
		Iterable<IndexPair<T>> pairs = this.getWorkingObjects();
		this.task.doTask(pairs);
	}

}
