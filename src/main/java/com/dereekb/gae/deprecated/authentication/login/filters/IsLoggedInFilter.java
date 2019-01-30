package com.thevisitcompany.gae.deprecated.authentication.login.filters;

import com.thevisitcompany.gae.deprecated.authentication.login.exceptions.NoLoginException;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSource;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.utilities.filters.Filter;
import com.thevisitcompany.gae.utilities.filters.FilterDelegate;
import com.thevisitcompany.gae.utilities.filters.FilterResult;
import com.thevisitcompany.gae.utilities.filters.FilterResults;

/**
 * Basic filter that checks whether or not this
 * 
 * @author dereekb
 * 
 * @param <T>
 */
public class IsLoggedInFilter<T>
        implements LoginSourceDependent, Filter<T> {

	protected LoginSource loginDelegate;

	@Override
	public void setLoginSource(LoginSource delegate) {
		if (delegate == null) {
			throw new NullPointerException();
		}

		this.loginDelegate = delegate;
	}

	private Login getLogin() {
		return this.loginDelegate.getLogin();
	}

	private void checkLoginState() throws NoLoginException {
		if (this.getLogin() == null) {
			throw new NoLoginException();
		}
	}

	@Override
	public FilterResult filterObject(T objecte) throws NoLoginException {
		this.checkLoginState();
		return FilterResult.PASS;
	}

	@Override
	public FilterResults<T> filterObjects(Iterable<T> objects) throws NoLoginException {
		this.checkLoginState();
		return new FilterResults<T>(objects);
	}

	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(Iterable<W> sources,
	                                                      FilterDelegate<T, W> delegate) throws NoLoginException {

		this.checkLoginState();
		return new FilterResults<W>(sources);
	}

}
