package com.thevisitcompany.gae.deprecated.model.users.account.support;

import com.thevisitcompany.gae.deprecated.model.users.account.Account;


/**
 * Delegate used for adding or removing items from an account using an observer.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            Item Type
 */
public interface AccountItemChangeDelegate<T> {

	/**
	 * Links the given item to the account.
	 * 
	 * @param account
	 * @param item
	 * @return True if the item is linked to the account.
	 */
	public boolean changeAccountItem(Account account,
	                                 AccountItemChange change,
	                                 T item);
	
}
