package com.thevisitcompany.gae.deprecated.model.users.account;

import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModelRegistry;

public class AccountRegistry extends ObjectifyModelRegistry<Account> {

	public static AccountRegistry getRegistry() {
		return new AccountRegistry();
	}
	
	public AccountRegistry() {
		super(Account.class);
	}

}
