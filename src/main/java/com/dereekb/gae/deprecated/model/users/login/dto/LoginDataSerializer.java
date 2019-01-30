package com.thevisitcompany.gae.deprecated.model.users.login.dto;

import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.mod.data.SerializerPair;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionDelegate;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionFunction;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.user.User;

public class LoginDataSerializer
        implements ModelConversionDelegate<Login, LoginData> {

	@Override
	public Login convertDataToObject(LoginData data) {

		Login object = new Login(data.getId());

		object.setEmail(data.getEmail());

		Long userKey = data.getUser();
		Key<User> user = Key.create(User.class, userKey);
		object.setUser(user);

		List<Long> accountKeys = data.getAccounts();
		Set<Key<Account>> accounts = Account.createKeySet(Account.class, accountKeys);
		object.setAccounts(accounts);

		object.setSettings(data.getSettings());
		object.setActive(data.getIsActive());

		return object;
	}

	@ModelConversionFunction(isDefault = true, value = ModelConversionDelegate.DEFAULT_DATA_NAME)
	public boolean convertToArchive(SerializerPair<Login, LoginData> pair) {
		Login object = (Login) pair.getSource();
		LoginData archive = new LoginData();

		archive.setId(object.getId());
		archive.setEmail(object.getEmail());
		archive.setIsActive(object.isActive());

		pair.setResult(archive);
		return true;
	}

	@ModelConversionFunction(value = "full")
	public boolean convertToFullArchive(SerializerPair<Login, LoginData> pair) {

		Login object = (Login) pair.getSource();
		LoginData archive = new LoginData();

		archive.setId(object.getId());
		archive.setEmail(object.getEmail());
		archive.setSettings(object.getSettings());

		Set<Key<Account>> accounts = object.getAccounts();
		List<Long> accountsKeys = Account.readKeyIdentifiers(accounts);
		archive.setAccounts(accountsKeys);

		archive.setIsActive(object.isActive());

		pair.setResult(archive);
		return true;
	}

}
