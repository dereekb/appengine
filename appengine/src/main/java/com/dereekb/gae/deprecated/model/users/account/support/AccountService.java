package com.thevisitcompany.gae.deprecated.model.users.account.support;

import com.thevisitcompany.gae.deprecated.model.users.account.Account;

/**
 * Basic service for retrieving and saving an account.
 * @author dereekb
 *
 */
public interface AccountService extends AccountSource {

	public Account getAccount();
	
	public void saveAccount(Account account);
	
}
