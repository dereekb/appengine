package com.dereekb.gae.server.auth.model.pointer.taskqueue;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.AbstractKeyQueryIterateTaskRequestBuilder;
import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.AbstractSingleKeyQueryIterateTaskRequestBuilder;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;

/**
 * {@link AbstractKeyQueryIterateTaskRequestBuilder} implementation used to create
 * a {@link LoginPointerQuery} for referenced logins.
 * 
 * @author dereekb
 *
 */
public class LoginPointerLoginQueryTaskRequestBuilder extends AbstractSingleKeyQueryIterateTaskRequestBuilder<Login> {

	public LoginPointerLoginQueryTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(baseRequest);
	}

	// MARK: AbstractQueryIterateTaskRequestBuilder
	@Override
	protected LoginPointerQuery getParametersForModelKey(ModelKey modelKey) {
		LoginPointerQuery query = new LoginPointerQuery();

		query.setLogin(modelKey);

		return query;
	}

}
