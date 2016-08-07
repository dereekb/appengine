package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.NewLoginGenerator;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * {@link LoginRegisterServiceImpl} with a custom filter.
 *
 * @author dereekb
 *
 */
public class FilteredLoginRegisterServiceImpl extends LoginRegisterServiceImpl {

	private Filter<LoginPointer> filter;

	public FilteredLoginRegisterServiceImpl(NewLoginGenerator loginGenerator, LinkService linkService) {
		super(loginGenerator, linkService);
	}

	public Filter<LoginPointer> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<LoginPointer> filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Filter cannot be null.");
		}

		this.filter = filter;
	}

	// MARK: LoginRegisterService
	@Override
	public Login register(LoginPointer pointer) throws LoginExistsException, LoginRegistrationRejectedException {
		if (this.filter.filterObject(pointer) != FilterResult.PASS) {
			return super.register(pointer);
		} else {
			throw new LoginRegistrationRejectedException();
		}
	}

	@Override
	public String toString() {
		return "FilteredLoginRegisterServiceImpl [filter=" + this.filter + ", toString()=" + super.toString() + "]";
	}

}
