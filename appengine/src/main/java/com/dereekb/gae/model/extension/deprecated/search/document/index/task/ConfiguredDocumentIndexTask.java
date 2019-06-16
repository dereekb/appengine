package com.dereekb.gae.model.extension.search.document.index.task;

import com.dereekb.gae.model.extension.deprecated.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.deprecated.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} implementation that performs an {@link IndexAction} on
 * the input models using a {@link DocumentIndexService}.
 *
 * @author dereekb
 *
 * @param <T>
 *            input model
 */
public class ConfiguredDocumentIndexTask<T extends UniqueSearchModel>
        implements IterableTask<T> {

	private IndexAction action;
	private DocumentIndexService<T> indexService;

	public ConfiguredDocumentIndexTask() {}

	public ConfiguredDocumentIndexTask(IndexAction action, DocumentIndexService<T> indexService) {
		this.action = action;
		this.indexService = indexService;
	}

	public DocumentIndexService<T> getIndexTask() {
		return this.indexService;
	}

	public void setIndexTask(DocumentIndexService<T> indexService) {
		this.indexService = indexService;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		this.indexService.indexChange(input, this.action);
	}

	@Override
	public String toString() {
		return "ConfiguredDocumentIndexTask [action=" + this.action + ", indexService=" + this.indexService + "]";
	}

}
