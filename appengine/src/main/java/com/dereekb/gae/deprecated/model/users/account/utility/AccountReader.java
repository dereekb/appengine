package com.thevisitcompany.gae.deprecated.model.users.account.utility;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.account.AccountSettings;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.visit.models.locations.place.Place;

/**
 * Helps read accounts by comparing logins to accounts.
 * 
 * @author dereekb
 * 
 */
public class AccountReader {

	// TODO Note: Could improve performance marginally by caching set of all logins for account.

	private Account account;

	public AccountReader() {}

	public AccountReader(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public AccountLevel getAccountLevel(Login login) {
		AccountLevel userLevel = AccountLevel.NONE;

		Key<Account> accountKey = account.getKey();
		Set<Key<Account>> accounts = login.getAccounts();

		if (accounts.contains(accountKey)) {
			userLevel = this.retrieveUserLevelFromAccount(login);
		}

		return userLevel;
	}

	/**
	 * Differs from getUserLevel() by not checking whether or not the login is in this account.
	 * 
	 * @param login
	 * @param account
	 * @return
	 */
	private AccountLevel retrieveUserLevelFromAccount(Login login) {
		AccountLevel userLevel = AccountLevel.NONE;
		Key<Login> loginKey = login.getKey();

		Set<Key<Login>> owners = account.getOwners();
		Set<Key<Login>> members = account.getMembers();
		Set<Key<Login>> viewers = account.getViewers();

		if (owners.contains(loginKey)) {
			userLevel = AccountLevel.OWNER;
		} else if (members.contains(loginKey)) {
			userLevel = AccountLevel.MEMBER;
		} else if (viewers.contains(loginKey)) {
			userLevel = AccountLevel.VIEWER;
		}

		return userLevel;
	}

	public Set<Key<Login>> getAllLogins() {
		Set<Key<Login>> owners = account.getOwners();
		Set<Key<Login>> members = account.getMembers();
		Set<Key<Login>> viewers = account.getViewers();

		Set<Key<Login>> logins = new HashSet<Key<Login>>();
		logins.addAll(owners);
		logins.addAll(members);
		logins.addAll(viewers);

		return logins;
	}

	public Integer getLoginCount() {
		Set<Key<Login>> owners = account.getOwners();
		Set<Key<Login>> members = account.getMembers();
		Set<Key<Login>> viewers = account.getViewers();

		Integer count = owners.size() + members.size() + viewers.size();
		return count;
	}

	/**
	 * Whether or not the given login is a viewer/member/owner of this account.
	 * 
	 * @param login
	 * @return True if the user is referenced in the account.
	 */
	public boolean isInAccount(Login login) {
		AccountLevel accountLevel = this.getAccountLevel(login);
		boolean isInAccount = (accountLevel != AccountLevel.NONE);
		return isInAccount;
	}

	/**
	 * Returns true if the given login is the only login remaining.
	 * 
	 * @param login
	 * @param account
	 * @return
	 */
	public boolean isOnlyUserInAccount(Login login) {
		boolean isOnlyUser = false;

		Key<Login> loginKey = login.getKey();
		Integer count = this.getLoginCount();

		if (count == 1) {
			Set<Key<Login>> logins = this.getAllLogins();
			isOnlyUser = logins.contains(loginKey);
		}

		return isOnlyUser;
	}

	/**
	 * Whether or not any objects (not members) are referenced in this account.
	 * 
	 * @return Returns true if no objects are in the account.
	 */
	public boolean accountIsEmpty() {
		Set<Key<Place>> places = account.getPlaces();
		return places.isEmpty();
	}

	/**
	 * Checks whether or not the given login is a member or owner of the current account;
	 * 
	 * @param login
	 * @return True if the login is an owner or member.
	 */
	public boolean isMember(Login login) {
		AccountLevel accountLevel = this.getAccountLevel(login);
		boolean isMember = false;

		switch (accountLevel) {
			case MEMBER:
			case OWNER:
				isMember = true;
				break;
			default:
				isMember = false;
				break;
		}

		return isMember;
	}

	public boolean canCreateItem(Login login) {
		AccountLevel accountLevel = this.getAccountLevel(login);
		boolean canCreate = false;

		switch (accountLevel) {
			case MEMBER:
				AccountSettings settings = this.account.getSettings();
				boolean membersCanCreate = settings.getMembersCanCreate();
				canCreate = membersCanCreate;
				break;
			case OWNER:
				canCreate = true;
				break;
			default:
				canCreate = false;
				break;
		}

		return canCreate;
	}

	/**
	 * Checks whether or not the login has the specified account level in the account.
	 * 
	 * @param accountLevel
	 * @param login
	 * @return True if the login has the given account level.
	 */
	public boolean loginHasLevel(AccountLevel accountLevel,
	                             Login login) {
		AccountLevel level = this.getAccountLevel(login);
		return level.has(accountLevel);
	}

	public boolean hasPermission(AccountPermission permission,
	                             Login login) {

		// TODO: Update AccountReader hasPermission... and account reader in general to better support permissions.

		boolean hasPermission = true;

		switch (permission) {
			case ADD_ITEM:
				hasPermission = this.canCreateItem(login);
				break;
			case ADD_MEMBER:
				break;
			case REMOVE_ITEM:
				break;
			case REMOVE_MEMBER:
				break;
			case UPDATE_ACCOUNT:
				break;
			case VIEW_ITEM:
				break;
			case VIEW_MEMBERS:
				break;
			default:
				break;
		}

		return hasPermission;
	}

	public boolean canDeleteAccount(Login login) {
		boolean canChange = this.accountIsEmpty() && this.isOnlyUserInAccount(login);
		return canChange;
	}
}
