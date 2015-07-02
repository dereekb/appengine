package com.thevisitcompany.gae.deprecated.model.users.account.functions;

import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.model.crud.function.delegate.CreateFunctionDelegate;
import com.thevisitcompany.gae.model.crud.function.delegate.UpdateFunctionDelegate;

public class AccountFunctionsDelegate
        implements CreateFunctionDelegate<Account>, UpdateFunctionDelegate<Account> {

	@Override
	public Account create(Account source) {
		Account account = new Account();
		account.setName(source.getName());
		return account;
	}

	@Override
	public boolean update(Account source,
	                      Account context) {

		String name = source.getName();
		context.setName(name);

		return true;
	}

}
