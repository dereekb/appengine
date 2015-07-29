package com.dereekb.gae.model.crud.extension.delete;

import com.dereekb.gae.model.crud.function.delegate.DeleteFunctionDelegate;
import com.dereekb.gae.model.crud.function.exception.CancelDeleteException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.builder.TaskRequestSender;
import com.dereekb.gae.server.taskqueue.exception.SubmitTaskException;

/**
 * {@link DeleteFunctionDelegate} that uses a {@link TaskRequestSender} to
 * submit a task to delete the elements.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public class ScheduledDeleteFunctionDelegate<T extends UniqueModel>
        implements DeleteFunctionDelegate<T> {

	private TaskRequestSender<T> deleteRequestSender;

	public ScheduledDeleteFunctionDelegate(TaskRequestSender<T> deleteRequestSender) {
		this.deleteRequestSender = deleteRequestSender;
	}

	public TaskRequestSender<T> getDeleteRequestSender() {
		return this.deleteRequestSender;
	}

	public void setDeleteRequestSender(TaskRequestSender<T> deleteRequestSender) {
		this.deleteRequestSender = deleteRequestSender;
	}

	@Override
	public void deleteObjects(Iterable<T> objects) throws CancelDeleteException {
		try {
			this.deleteRequestSender.sendTasks(objects);
		} catch (SubmitTaskException e) {
			throw new CancelDeleteException(objects, e);
		}
	}

	@Override
	public String toString() {
		return "ScheduledDeleteFunctionDelegate [deleteRequestSender=" + this.deleteRequestSender + "]";
	}

}
