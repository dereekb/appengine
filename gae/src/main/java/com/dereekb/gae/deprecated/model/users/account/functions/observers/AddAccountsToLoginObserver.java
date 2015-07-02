package com.thevisitcompany.gae.deprecated.model.users.account.functions.observers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.authentication.login.support.AbstractLoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.functions.LinksPair;
import com.thevisitcompany.gae.model.extension.links.service.OpenLinksService;
import com.thevisitcompany.gae.server.datastore.Setter;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyCrudService;
import com.thevisitcompany.gae.utilities.collections.map.HashMapWithList;
import com.thevisitcompany.gae.utilities.function.staged.StagedFunction;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Observer used after account creation that adds the created accounts to their designated owners.
 * 
 * Logins that cannot be retrieved are automatically tied to the current login.
 * 
 * Both accounts and logins will be saved at this step.
 * 
 * @author dereekb
 */
public class AddAccountsToLoginObserver extends AbstractLoginSourceDependent
        implements StagedFunctionObserver<Account> {

	private class AccountsPairingHelper {

		private Login login = null;
		private Key<Login> loginKey = null;
		private final List<Account> accountsList;
		private HashMapWithList<Key<Login>, Account> accountSet;

		public AccountsPairingHelper(List<Account> accountsList) {
			this.accountsList = accountsList;
			this.initalizeLogin();
		}

		private void initalizeLogin() {
			this.login = getLogin();
			this.loginKey = this.login.getKey();
		}

		public boolean run() {
			buildAccountSet();

			Collection<Key<Login>> otherLoginKeys = this.getOtherLoginKeys();
			List<Login> readLogins = this.readLogins(otherLoginKeys);

			List<LinksPair<Login, Long>> linksPairs = new ArrayList<LinksPair<Login, Long>>();
			HashMapWithList<Login, Account> pairedSet = this.buildPairedSet(readLogins, linksPairs);

			List<Login> savedLogins = this.saveLinks(linksPairs);

			// Clear those that were successfully save.
			pairedSet.removeAll(savedLogins);

			// Save the remaining/failed to login.
			return this.saveRemainingToLogin(pairedSet);
		}

		private void buildAccountSet() {
			HashMapWithList<Key<Login>, Account> accountSet = new HashMapWithList<Key<Login>, Account>();

			// Get all the owners we need and pair they with the account set.
			for (Account account : this.accountsList) {
				Set<Key<Login>> owners = account.getOwners();

				// If there are no owners, use the current login.
				if (owners.isEmpty()) {
					owners.add(loginKey);
				}

				accountSet.addAll(owners, account);
			}

			this.accountSet = accountSet;
		}

		private Collection<Key<Login>> getOtherLoginKeys() {
			Set<Key<Login>> otherLogins = this.accountSet.getKeySet();
			otherLogins.remove(loginKey);
			return otherLogins;
		}

		private List<Login> readLogins(Collection<Key<Login>> keys) {
			List<Login> readLogins = null;

			try {
				// Read the logins from the service. Some may not exist.
				readLogins = loginService.readWithKeys(keys);
			} catch (UnavailableObjectsException e) {
				readLogins = new ArrayList<Login>();
			}

			return readLogins;
		}

		private HashMapWithList<Login, Account> buildPairedSet(List<Login> logins,
		                                                       List<LinksPair<Login, Long>> linksPairs) {
			HashMapWithList<Login, Account> pairedSet = new HashMapWithList<Login, Account>();

			for (Login login : logins) {
				Key<Login> readLoginKey = login.getKey();
				List<Account> accounts = accountSet.getObjects(readLoginKey);
				List<Long> accountIdentifiers = new ArrayList<Long>();

				for (Account account : accounts) {
					Set<Key<Login>> owners = account.getOwners();
					owners.add(readLoginKey);
					accountIdentifiers.add(account.getId());
					pairedSet.add(login, account);
				}

				LinksPair<Login, Long> linksPair = new LinksPair<Login, Long>(login, accountIdentifiers, "account",
				        LinksAction.LINK);
				linksPairs.add(linksPair);
			}

			return pairedSet;
		}

		private List<Login> saveLinks(Collection<LinksPair<Login, Long>> pairs) {
			List<Login> successfulSaves = null;

			try {
				successfulSaves = loginLinksService.linksChange(pairs);
			} catch (Exception e) {
				successfulSaves = Collections.emptyList();
			}

			return successfulSaves;
		}

		private boolean saveRemainingToLogin(HashMapWithList<Login, Account> pairedSet) {

			boolean success = true;

			if (pairedSet.isEmpty() == false) {
				Set<Long> remainingAccounts = new HashSet<Long>();

				for (Login pairedLogin : pairedSet.getKeySet()) {
					Key<Login> pairedLoginKey = pairedLogin.getKey();
					List<Account> accounts = pairedSet.getObjects(pairedLogin);

					for (Account account : accounts) {
						Set<Key<Login>> accountOwners = account.getOwners();
						accountOwners.remove(pairedLoginKey);
						accountOwners.add(loginKey);
						remainingAccounts.add(account.getId());
					}
				}

				LinksPair<Login, Long> linksPair = new LinksPair<Login, Long>(login, remainingAccounts, "account",
				        LinksAction.LINK);
				Collection<LinksPair<Login, Long>> loginPairCollection = new ArrayList<LinksPair<Login, Long>>();
				loginPairCollection.add(linksPair);
				loginLinksService.linksChange(loginPairCollection);
			}

			return success;
		}

	}

	private Setter<Account> accountSetter;
	private ObjectifyCrudService<Login, Long> loginService;
	private OpenLinksService<Login, Long> loginLinksService;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<Account, ?> handler) {

		List<Account> accountsList = handler.getFunctionObjects();
		AccountsPairingHelper helper = new AccountsPairingHelper(accountsList);
		helper.run();

		accountSetter.save(accountsList, true);
	}
}
