package com.thevisitcompany.gae.deprecated.model.users.user.support.functions.observers;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.users.user.User;
import com.thevisitcompany.gae.deprecated.model.users.user.UserItemChange;
import com.thevisitcompany.gae.deprecated.model.users.user.UserItemChangeDelegate;
import com.thevisitcompany.gae.deprecated.model.users.user.support.UserService;
import com.thevisitcompany.gae.deprecated.model.users.user.support.UserServiceDependent;
import com.thevisitcompany.gae.utilities.function.staged.StagedFunction;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Observer that when called will iterate through the items and perform an user change of some type.
 * 
 * Requires that a change and delegate be specified.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            Model Type
 */
public class UserItemChangeObserver<T>
        implements StagedFunctionObserver<T>, UserServiceDependent {

	private UserItemChange change;
	private UserItemChangeDelegate<T> delegate;
	private UserService accountService;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {

		if (change == null) {
			throw new NullPointerException();
		}

		User account = this.accountService.getUser();
		List<T> items = handler.getFunctionObjects();

		for (T item : items) {
			delegate.changeUserItem(account, change, item);
		}

		this.accountService.saveUser(account);
	}

	public UserItemChange getChange() {
		return change;
	}

	public void setChange(UserItemChange change) {
		this.change = change;
	}

	@Override
	public void setUserService(UserService service) {
		this.accountService = service;
	}

}
