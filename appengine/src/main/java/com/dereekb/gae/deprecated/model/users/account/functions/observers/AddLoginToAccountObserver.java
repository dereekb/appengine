package com.thevisitcompany.gae.deprecated.model.users.account.functions.observers;

import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.authentication.login.support.AbstractLoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.utilities.function.staged.StagedFunction;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Observer that adds the login to accounts that are to be created and have no login specified.
 * 
 * @author dereekb
 * 
 */
public class AddLoginToAccountObserver extends AbstractLoginSourceDependent
        implements StagedFunctionObserver<Account> {

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<Account, ?> handler) {

		Login login = this.getLogin();
		Key<Login> loginKey = login.getKey();
		List<Account> accountsList = handler.getFunctionObjects();

		for (Account account : accountsList) {
			Set<Key<Login>> owners = account.getOwners();

			if (owners.isEmpty()) {
				owners.add(loginKey);
			}
		}
	}

}
