package com.dereekb.gae.model.extension.taskqueue.scheduler.builder;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link TaskRequestSender} extension that also allows using
 * {@link ModelKeyListAccessor} values.
 *
 * @author dereekb
 */
public interface ListAccessorTaskRequestSender<T extends UniqueModel> {

	/**
	 * Creates and submits tasks for the input.
	 * 
	 * @param accessor
	 *            {@link ModelKeyListAccessor<T>}. Never {@code null}.
	 * @throws SubmitTaskException
	 */
	public void sendTask(ModelKeyListAccessor<T> accessor) throws SubmitTaskException;

}
