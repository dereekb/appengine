package com.dereekb.gae.model.extension.search.document.index.taskqueue;

import java.util.List;

import com.dereekb.gae.model.extension.taskqueue.api.CustomTask;
import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskInfo;
import com.dereekb.gae.server.search.DocumentSearchController;
import com.dereekb.gae.server.search.DocumentSearchController.DocumentSearchIndexIterator;

/**
 * {@link CustomTask} task for clearing a search index.
 *
 * Deletes are processed asynchronously by default.
 *
 * TODO: Task does not scale. Update to allow for striding the entire index.
 *
 * TODO: Update to implement {@link IterateModelsSubtask<T>}
 *
 * @deprecated TODO: Add to new Search Task Queue Controller
 * @author dereekb
 */
@Deprecated
public class ClearIndexTaskQueueTask
        implements CustomTask {

	private String index;
	private boolean asyncDelete = true;

	@Override
	public void doTask(CustomTaskInfo request) {
		DocumentSearchIndexIterator iterator = DocumentSearchController.makeIndexIterator(this.index);

		while (iterator.hasReachedEnd() == false) {
			List<String> identifiers = iterator.getNextDocumentsIdentifiers();
			DocumentSearchController.delete(identifiers, this.index, this.asyncDelete);
		}
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public boolean isAsyncDelete() {
		return this.asyncDelete;
	}

	public void setAsyncDelete(boolean asyncDelete) {
		this.asyncDelete = asyncDelete;
	}

}
