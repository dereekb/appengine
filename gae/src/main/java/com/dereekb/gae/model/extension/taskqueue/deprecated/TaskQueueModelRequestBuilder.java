package com.dereekb.gae.model.extension.taskqueue.deprecated;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequest;
import com.dereekb.gae.server.taskqueue.functions.observer.TaskQueueObjectRequestBuilder;

@Deprecated
public interface TaskQueueModelRequestBuilder<T>
        extends TaskQueueObjectRequestBuilder<T> {

	public Collection<TaskQueuePushRequest> buildRequests(Collection<ModelKey> keys);

}
