package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.NewLoginGenerator;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * {@link LoginRegisterServiceImpl} with a custom filter for
 * {@link LoginPointer} types.
 *
 * @author dereekb
 *
 */
public class FilteredLoginRegisterServiceImpl extends LoginRegisterServiceImpl {

	private Filter<LoginPointer> filter;

	public FilteredLoginRegisterServiceImpl(NewLoginGenerator loginGenerator,
	        GetterSetter<Login> loginGetterSetter,
	        GetterSetter<LoginPointer> loginPointerGetterSetter,
	        Filter<LoginPointer> filter) throws IllegalArgumentException {
		super(loginGenerator, loginGetterSetter, loginPointerGetterSetter);
		this.setFilter(filter);
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
			throw new LoginRegistrationRejectedException();
		} else {
			return super.register(pointer);
		}
	}

	@Override
	public String toString() {
		return "FilteredLoginRegisterServiceImpl [filter=" + this.filter + ", toString()=" + super.toString() + "]";
	}

}
