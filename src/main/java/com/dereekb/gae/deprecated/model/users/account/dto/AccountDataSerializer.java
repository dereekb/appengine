package com.thevisitcompany.gae.deprecated.model.users.account.dto;

import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.mod.data.SerializerPair;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionDelegate;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionFunction;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;

public class AccountDataSerializer
        implements ModelConversionDelegate<Account, AccountData> {

	@Override
	public Account convertDataToObject(AccountData data) {

		Account object = new Account(data.getId());

		object.setName(data.getName());

		List<Long> viewerKeys = data.getViewers();
		Set<Key<Login>> viewers = Login.createKeySet(Login.class, viewerKeys);
		object.setViewers(viewers);

		List<Long> memberKeys = data.getViewers();
		Set<Key<Login>> members = Login.createKeySet(Login.class, memberKeys);
		object.setMembers(members);

		List<Long> ownerKeys = data.getViewers();
		Set<Key<Login>> owners = Login.createKeySet(Login.class, ownerKeys);
		object.setOwners(owners);

		return object;
	}

	@ModelConversionFunction(isDefault = true, value = ModelConversionDelegate.DEFAULT_DATA_NAME)
	public boolean convertToArchive(SerializerPair<Account, AccountData> pair) {

		Account object = (Account) pair.getSource();
		AccountData archive = new AccountData();

		archive.setId(object.getId());
		archive.setName(object.getName());

		Set<Key<Login>> owners = object.getViewers();
		List<Long> ownerKeys = Account.readKeyIdentifiers(owners);
		archive.setOwners(ownerKeys);

		pair.setResult(archive);
		return true;
	}

	@ModelConversionFunction(value = "full")
	public boolean convertToBackupArchive(SerializerPair<Account, AccountData> pair) {

		Account object = (Account) pair.getSource();
		AccountData archive = new AccountData();

		archive.setId(object.getId());
		archive.setName(object.getName());

		Set<Key<Login>> viewers = object.getViewers();
		List<Long> viewerKeys = Account.readKeyIdentifiers(viewers);
		archive.setViewers(viewerKeys);

		Set<Key<Login>> members = object.getViewers();
		List<Long> memberKeys = Account.readKeyIdentifiers(members);
		archive.setMembers(memberKeys);

		Set<Key<Login>> owners = object.getViewers();
		List<Long> ownerKeys = Account.readKeyIdentifiers(owners);
		archive.setOwners(ownerKeys);

		pair.setResult(archive);
		return true;
	}

}
