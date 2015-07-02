package com.thevisitcompany.gae.deprecated.model.users.login.utility;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.login.LoginSettings;
import com.thevisitcompany.gae.model.objectify.generator.ObjectifyModelGenerator;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.Permissions;

public class LoginGenerator extends ObjectifyModelGenerator<Login> {

	@Override
	public Login generateModel(Long identifier) {
		Login login = new Login(identifier);

		login.setEmail("email@test.com");
		login.setActive(true);

		LoginSettings settings = new LoginSettings();
		login.setSettings(settings);

		Permissions permissions = new Permissions();
		login.setPermissions(permissions);

		Set<Key<Account>> accounts = new HashSet<Key<Account>>();
		accounts.add(Key.create(Account.class, 1));
		login.setAccounts(accounts);

		return login;
	}

}
