package com.dereekb.gae.server.auth.model.login.misc.filter;

import com.dereekb.gae.server.auth.model.login.misc.LoginOwned;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.filter.AbstractModelKeyFilter;

/**
 * {@link Filter} for {@link LoginOwned} types.
 * 
 * @author dereekb
 *
 */
public class LoginOwnedFilter extends AbstractModelKeyFilter<LoginOwned> {

	public LoginOwnedFilter(ModelKey modelKey) {
		super(modelKey);
	}

	@Override
	protected ModelKey readModelKey(LoginOwned model) {
		return model.getLoginOwnerKey();
	}

}
