package com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.SecuredTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * Used for converting {@link TaskRequest} values to {@link TaskOptions} for
 * submitting requests.
 *
 * @author dereekb
 *
 */
public interface AppEngineTaskRequestConverter
        extends DirectionalConverter<SecuredTaskRequest, TaskOptions>, SingleDirectionalConverter<SecuredTaskRequest, TaskOptions> {

	public TaskRequestReader makeReader(SecuredTaskRequest request);

}
