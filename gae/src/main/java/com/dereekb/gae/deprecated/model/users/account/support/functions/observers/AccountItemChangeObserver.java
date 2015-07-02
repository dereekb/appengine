package com.thevisitcompany.gae.deprecated.model.users.account.support.functions.observers;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountItemChange;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountItemChangeDelegate;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountService;
import com.thevisitcompany.gae.deprecated.model.users.account.support.AccountServiceDependent;
import com.thevisitcompany.gae.utilities.function.staged.StagedFunction;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Observer that when called will iterate through the items and perform an account change of some type.
 * 
 * Requires that a change and delegate be specified.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            Model Type
 */
public class AccountItemChangeObserver<T>
        implements StagedFunctionObserver<T>, AccountServiceDependent {

	private AccountItemChange change;
	private AccountItemChangeDelegate<T> delegate;
	private AccountService accountService;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {

		if (this.change == null) {
			throw new NullPointerException();
		}

		Account account = this.accountService.getAccount();
		List<T> items = handler.getFunctionObjects();

		for (T item : items) {
			this.delegate.changeAccountItem(account, this.change, item);
		}

		this.accountService.saveAccount(account);
	}

	public AccountItemChange getChange() {
		return this.change;
	}

	public void setChange(AccountItemChange change) {
		this.change = change;
	}

	@Override
	public void setAccountService(AccountService service) {
		this.accountService = service;
	}

}
