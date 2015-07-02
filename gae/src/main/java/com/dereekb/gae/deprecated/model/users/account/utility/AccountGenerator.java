package com.thevisitcompany.gae.deprecated.model.users.account.utility;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.model.objectify.generator.ObjectifyModelGenerator;

public class AccountGenerator extends ObjectifyModelGenerator<Account> {

	@Override
	public Account generateModel(Long identifier) {
		Account account = new Account(identifier);

		account.setName("Account Name");

		Set<Key<Login>> owners = new HashSet<Key<Login>>();
		owners.add(Key.create(Login.class, 1));
		account.setOwners(owners);

		Set<Key<Login>> members = new HashSet<Key<Login>>();
		members.add(Key.create(Login.class, 12));
		members.add(Key.create(Login.class, 13));
		account.setMembers(members);

		Set<Key<Login>> viewers = new HashSet<Key<Login>>();
		viewers.add(Key.create(Login.class, 111));
		viewers.add(Key.create(Login.class, 112));
		viewers.add(Key.create(Login.class, 113));
		viewers.add(Key.create(Login.class, 114));
		account.setViewers(viewers);

		return account;
	}

}
