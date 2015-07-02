package com.thevisitcompany.gae.deprecated.model.users.account.support.functions.filters;

import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountSource;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountSourceDependent;
import com.thevisitcompany.gae.utilities.filters.FilterResult;
import com.thevisitcompany.gae.utilities.filters.FilterResults;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilter;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilterDelegate;

public class HasAccountFilter<T>
        implements StagedFunctionFilter<T>, AccountSourceDependent {

	private AccountSource accountSource;

	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(StagedFunctionStage stage,
	                                                      Iterable<W> sources,
	                                                      StagedFunctionFilterDelegate<T, W> delegate) {

		boolean hasAccount = this.hasAccount();
		FilterResult result = FilterResult.withBoolean(hasAccount);
		FilterResults<W> results = new FilterResults<W>(result, sources);
		return results;
	}

	private boolean hasAccount() {
		Account account = accountSource.getAccount();
		return (account != null);
	}

	@Override
	public void setAccountSource(AccountSource source) {
		this.accountSource = source;
	}

}
