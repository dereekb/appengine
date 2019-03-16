package com.dereekb.gae.server.auth.model.login.misc.owned.filter;

import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwnedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.filter.AbstractModelKeyFilter;

/**
 * {@link Filter} for {@link LoginOwnedModel} types.
 * 
 * @author dereekb
 *
 */
public class LoginOwnedFilter extends AbstractModelKeyFilter<LoginOwnedModel> {

	public LoginOwnedFilter(ModelKey modelKey) {
		super(modelKey);
	}

	@Override
	protected ModelKey readModelKey(LoginOwnedModel model) {
		return model.getLoginOwnerKey();
	}

}
