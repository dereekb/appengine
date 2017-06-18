package com.dereekb.gae.model.extension.taskqueue.scheduler.builder;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;

/**
 * Special {@link TaskRequestBuilder} that builds requests using a
 * {@link ModelKeyListAccessor<T>}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ListAccessorTaskRequestBuilder<T extends UniqueModel> {

	/**
	 * Builds a list of {@link MutableTaskRequest} instances.
	 * 
	 * @param input
	 *            {@link ModelKeyListAccessor}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<MutableTaskRequest> buildRequests(ModelKeyListAccessor<T> input);

}
