package com.dereekb.gae.server.taskqueue.scheduler.utility.converter;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;

/**
 * Used for converting {@link TaskRequest} values to a specified type for
 * submitting requests.
 *
 * @author dereekb
 *
 */
public interface TaskRequestConverter<T>
        extends DirectionalConverter<TaskRequest, T>, SingleDirectionalConverter<TaskRequest, T> {

	public TaskRequestReader makeReader(TaskRequest request);

}
