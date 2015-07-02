package com.thevisitcompany.gae.deprecated.authentication.login.functions.factory;

import com.thevisitcompany.gae.deprecated.authentication.login.functions.LoginDelegateFunctionHandlerFiltersMap;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSource;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionObject;
import com.thevisitcompany.gae.utilities.function.staged.factory.filter.StagedFunctionFilterMapFactory;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFiltersMap;

public class LoginFunctionHandlerFiltersMapFactory<T, W extends StagedFunctionObject<T>>
        implements StagedFunctionFilterMapFactory<T, W> {

	private LoginSource source;

	@Override
	public StagedFunctionFiltersMap<T, W> makefiltersMap() {
		LoginDelegateFunctionHandlerFiltersMap<T, W> filtersMap = new LoginDelegateFunctionHandlerFiltersMap<T, W>();

		if (source != null) {
			filtersMap.setLoginSource(source);
		}

		return filtersMap;
	}

	public LoginSource getSource() {
		return source;
	}

	public void setSource(LoginSource source) {
		this.source = source;
	}

}
