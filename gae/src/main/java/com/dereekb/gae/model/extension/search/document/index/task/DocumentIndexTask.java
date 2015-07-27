package com.dereekb.gae.model.extension.search.document.index.task;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} implementation that wraps a {@link DocumentIndexPairTask}, and
 * builds {@link IndexPair} values for the input before calling the task.
 *
 * @author dereekb
 *
 */
public class DocumentIndexTask<T extends UniqueSearchModel>
        implements IterableTask<T> {

	private IndexAction action;
	private DocumentIndexPairsTask<T> indexTask;

	public DocumentIndexTask() {}

	public DocumentIndexTask(IndexAction action, DocumentIndexPairsTask<T> indexTask) {
		this.action = action;
		this.indexTask = indexTask;
	}

	public DocumentIndexPairsTask<T> getIndexTask() {
		return this.indexTask;
	}

	public void setIndexTask(DocumentIndexPairsTask<T> indexTask) {
		this.indexTask = indexTask;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		List<IndexPair<T>> pairs = IndexPair.makePairs(input, this.action);
		this.indexTask.doTask(pairs);
	}

	@Override
	public String toString() {
		return "DocumentIndexTask [action=" + this.action + ", indexTask=" + this.indexTask + "]";
	}

}
