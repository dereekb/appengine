package com.thevisitcompany.gae.deprecated.model.users.account.functions.filters;

import com.thevisitcompany.gae.deprecated.authentication.login.support.AbstractLoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.account.utility.AccountPermission;
import com.thevisitcompany.gae.deprecated.model.users.account.utility.AccountReader;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.model.crud.function.filters.CanCreateFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanDeleteFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanReadFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanUpdateFilterDelegate;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.PermissionsReader;
import com.thevisitcompany.visit.models.locations.permissions.LocationsPermissionHandler;

public class AccountChangesFilterDelegate extends AbstractLoginSourceDependent
        implements CanUpdateFilterDelegate<Account>, CanCreateFilterDelegate<Account>,
        CanDeleteFilterDelegate<Account>, CanReadFilterDelegate<Account> {

	private static final String READ_ALL_STRING = LocationsPermissionHandler.permissionsRequestString("account.read.*");

	private static final String UPDATE_ALL_STRING = LocationsPermissionHandler
	        .permissionsRequestString("account.update.*");

	private static final String DELETE_ALL_STRING = LocationsPermissionHandler
	        .permissionsRequestString("account.delete.*");

	@Override
	public boolean canCreate(Account object) {
		return true;
	}

	@Override
	public boolean canRead(Account object) {
		boolean canRead = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canRead = reader.hasPermission(READ_ALL_STRING);

		if (canRead == false) {
			AccountReader accountReader = new AccountReader(object);
			canRead = accountReader.hasPermission(AccountPermission.VIEW_ACCOUNT, login);
		}

		return canRead;
	}

	@Override
	public boolean canUpdate(Account object) {
		boolean canUpdate = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canUpdate = reader.hasPermission(UPDATE_ALL_STRING);

		if (canUpdate == false) {
			AccountReader accountReader = new AccountReader(object);
			canUpdate = accountReader.hasPermission(AccountPermission.UPDATE_ACCOUNT, login);
		}

		return canUpdate;
	}

	@Override
	public boolean canDelete(Account object) {
		boolean canDelete = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canDelete = reader.hasPermission(DELETE_ALL_STRING);

		if (canDelete == false) {
			AccountReader accountReader = new AccountReader(object);
			canDelete = accountReader.canDeleteAccount(login);
		}

		return canDelete;
	}

}
