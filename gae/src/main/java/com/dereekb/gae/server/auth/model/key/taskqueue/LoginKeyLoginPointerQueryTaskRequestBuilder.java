package com.dereekb.gae.server.auth.model.key.taskqueue;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.AbstractQueryIterateTaskRequestBuilder;
import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.AbstractSingleQueryIterateTaskRequestBuilder;
import com.dereekb.gae.server.auth.model.key.search.query.LoginKeyQuery;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;

/**
 * {@link AbstractQueryIterateTaskRequestBuilder} implementation used to create
 * a {@link LoginKeyQuery} for referenced LoginPointer values.
 * 
 * @author dereekb
 *
 */
public class LoginKeyLoginPointerQueryTaskRequestBuilder extends AbstractSingleQueryIterateTaskRequestBuilder<LoginPointer> {

	public LoginKeyLoginPointerQueryTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(baseRequest);
	}

	// MARK: AbstractQueryIterateTaskRequestBuilder
	@Override
	protected LoginKeyQuery getParametersForModelKey(ModelKey modelKey) {
		LoginKeyQuery query = new LoginKeyQuery();

		query.setLoginPointer(modelKey);

		return query;
	}

}
