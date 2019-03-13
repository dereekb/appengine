package com.thevisitcompany.gae.deprecated.model.users.login;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;


/**
 * Settings for a given login.
 * @author dereekb
 */

public class LoginSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Account to interact with automatically.
	 */
	private Key<Account> defaultAccount;
	
	/**
	 * Whether or not items created by the login should be followed automatically.
	 */
	private boolean autoFavoriteCreatedItems = true;
	
	public LoginSettings() {}

	public Key<Account> getDefaultAccount() {
		return defaultAccount;
	}

	public void setDefaultAccount(Key<Account> defaultAccount) {
		this.defaultAccount = defaultAccount;
	}

	public boolean isAutoFavoriteCreatedItems() {
		return autoFavoriteCreatedItems;
	}

	public void setAutoFavoriteCreatedItems(boolean autoFavoriteCreatedItems) {
		this.autoFavoriteCreatedItems = autoFavoriteCreatedItems;
	}

}
