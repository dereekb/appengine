package com.thevisitcompany.gae.deprecated.model.users.account.support.functions.filters;

import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSource;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountSource;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountSourceDependent;
import com.thevisitcompany.gae.deprecated.model.users.account.utility.AccountLevel;
import com.thevisitcompany.gae.deprecated.model.users.account.utility.AccountReader;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.utilities.filters.FilterResult;
import com.thevisitcompany.gae.utilities.filters.FilterResults;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilter;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilterDelegate;

public class HasAccountLevelFilter<T>
        implements StagedFunctionFilter<T>, AccountSourceDependent, LoginSourceDependent {

	private LoginSource loginSource;
	private AccountSource accountSource;
	private AccountLevel accountLevel;

	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(StagedFunctionStage stage,
	                                                      Iterable<W> sources,
	                                                      StagedFunctionFilterDelegate<T, W> delegate) {
		boolean hasLevel = this.hasAccountLevel();

		FilterResult result = FilterResult.withBoolean(hasLevel);
		FilterResults<W> results = new FilterResults<W>(result, sources);
		return results;
	}

	private boolean hasAccountLevel() {
		Account account = accountSource.getAccount();
		Login login = loginSource.getLogin();

		AccountReader reader = new AccountReader(account);
		boolean hasLevel = reader.loginHasLevel(accountLevel, login);
		return hasLevel;
	}

	@Override
	public void setAccountSource(AccountSource source) {
		this.accountSource = source;
	}

	@Override
	public void setLoginSource(LoginSource delegate) {
		this.loginSource = delegate;
	}

}
