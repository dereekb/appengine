package com.thevisitcompany.gae.deprecated.authentication.login.functions.filters;

import com.thevisitcompany.gae.deprecated.authentication.login.exceptions.NoLoginException;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginDependent;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.utilities.filters.Filter;
import com.thevisitcompany.gae.utilities.filters.FilterDelegate;
import com.thevisitcompany.gae.utilities.filters.FilterResult;
import com.thevisitcompany.gae.utilities.filters.FilterResults;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilter;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilterDelegate;

/**
 * Filter that throws a NoLoginException exception if it doesn't have a login set.
 * 
 * @author dereekb
 */
public class HasLoginFilter<T>
        implements LoginDependent, Filter<T>, StagedFunctionFilter<T> {

	protected Login login;

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	private void checkLoginState() throws NoLoginException {
		if (this.login == null) {
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

	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(StagedFunctionStage stage,
	                                                      Iterable<W> sources,
	                                                      StagedFunctionFilterDelegate<T, W> delegate) {
		return this.filterObjectsWithDelegate(sources, delegate);
	}

}
