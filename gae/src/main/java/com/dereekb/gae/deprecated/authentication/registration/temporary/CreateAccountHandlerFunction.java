package com.thevisitcompany.gae.deprecated.authentication.registration.temporary;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.authentication.registration.RegistrationHandlerFunction;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.account.AccountRegistry;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;

public class CreateAccountHandlerFunction
        implements RegistrationHandlerFunction {

	private final AccountRegistry registry = new AccountRegistry();

	@Override
	public void modifyRegisteredLogin(Login login) {
		Account account = new Account();
		account.setName("Default Account");
		Key<Login> loginKey = login.getKey();

		account.getOwners().add(loginKey);
		registry.save(account, false);

		Key<Account> newAccount = account.getKey();
		login.getAccounts().add(newAccount);
	}

}
