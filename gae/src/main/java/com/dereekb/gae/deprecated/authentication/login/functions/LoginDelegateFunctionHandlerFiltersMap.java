package com.thevisitcompany.gae.deprecated.authentication.login.functions;

import java.util.HashSet;
import java.util.Set;

import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginDependent;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSource;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSourceDependent;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSourceService;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionObject;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilter;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFiltersMap;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionObjectFilter;

/**
 * Filters Map that watches to see which filters that are added are dependent on a login filter, then uses it.
 * 
 * @author dereekb
 * 
 * @param <T>
 * @param <W>
 */
public class LoginDelegateFunctionHandlerFiltersMap<T, W extends StagedFunctionObject<T>> extends StagedFunctionFiltersMap<T, W>
        implements LoginSourceService, LoginSourceDependent {

	private LoginSource loginSource;

	private Set<LoginDependent> loginDependentFilters = new HashSet<LoginDependent>();

	public void prepareFilters() {
		Login login = this.loginSource.getLogin();

		for (LoginDependent dependentFilter : this.loginDependentFilters) {
			dependentFilter.setLogin(login);
		}
	}

	@Override
	public void add(StagedFunctionStage stage,
	                StagedFunctionFilter<T> filter) {

		if (filter instanceof LoginDependent) {
			LoginDependent dependent = (LoginDependent) filter;
			loginDependentFilters.add(dependent);
		}

		super.add(stage, filter);
	}

	public void add(StagedFunctionStage stage,
	                StagedFunctionObjectFilter<T, W> filter) {

		if (filter instanceof LoginDependent) {
			LoginDependent dependent = (LoginDependent) filter;
			loginDependentFilters.add(dependent);
		}

		super.add(stage, filter);
	}

	@Override
	public void remove(StagedFunctionStage stage,
	                   StagedFunctionFilter<T> filter) {
		loginDependentFilters.remove(filter);
		super.remove(stage, filter);
	}

	public void remove(StagedFunctionStage stage,
	                   StagedFunctionObjectFilter<T, W> filter) {
		loginDependentFilters.remove(filter);
		super.remove(stage, filter);
	}

	public void clearNormalFilters() {
		for (StagedFunctionFilter<T> filter : this.normal) {
			loginDependentFilters.remove(filter);
		}

		super.clearNormalFilters();
	}

	public void clearObjectFilters() {
		for (StagedFunctionObjectFilter<T, W> filter : this.objectDependent) {
			loginDependentFilters.remove(filter);
		}

		super.clearObjectFilters();
	}

	@Override
	public LoginSource getLoginDelegate() {
		return this.loginSource;
	}

	@Override
	public void setLoginSource(LoginSource loginSource) {
		this.loginSource = loginSource;
	}

}
