package com.dereekb.gae.server.auth.model.login.crud;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.utilities.filters.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Filter that returns {@link FilterResult#PASS} for deletable {@link Login}.
 * <p>
 * Login values are not deletable if they have any pointers still linked, and if
 * their roles are not empty.
 *
 * @author dereekb
 *
 */
public class LoginDeleteRulesFilter extends AbstractFilter<Login> {

	@Override
	public FilterResult filterObject(Login object) {
		return FilterResult.withBoolean(object.getPointers().isEmpty() && object.getRoles().isEmpty());
	}

}
