package com.thevisitcompany.gae.deprecated.model.users.user.functions.filters;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.authentication.login.support.AbstractLoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.user.User;
import com.thevisitcompany.gae.model.crud.function.filters.CanCreateFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanDeleteFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanReadFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanUpdateFilterDelegate;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.PermissionsReader;
import com.thevisitcompany.visit.models.locations.permissions.LocationsPermissionHandler;

public class UserChangesFilterDelegate extends AbstractLoginSourceDependent
        implements CanUpdateFilterDelegate<User>, CanCreateFilterDelegate<User>, CanDeleteFilterDelegate<User>,
        CanReadFilterDelegate<User> {

	private static final String CREATE_ALL_STRING = LocationsPermissionHandler.permissionsRequestString("user.create");

	private static final String READ_ALL_STRING = LocationsPermissionHandler.permissionsRequestString("user.read.*");

	private static final String UPDATE_ALL_STRING = LocationsPermissionHandler
	        .permissionsRequestString("user.update.*");

	private static final String DELETE_ALL_STRING = LocationsPermissionHandler
	        .permissionsRequestString("user.delete.*");

	@Override
	public boolean canCreate(User object) {
		Login login = this.getLogin();
		boolean canCreate = (login.getUser() == null);
		return canCreate;
	}

	@Override
	public boolean canRead(User object) {
		boolean canRead = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canRead = reader.hasPermission(READ_ALL_STRING);

		if (canRead == false) {
			canRead = this.isUser(login);
		}

		return canRead;
	}

	@Override
	public boolean canDelete(User object) {
		boolean canDelete = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canDelete = reader.hasPermission(DELETE_ALL_STRING);

		if (canDelete == false) {
			canDelete = this.isUser(login);
		}
		return canDelete;
	}

	@Override
	public boolean canUpdate(User object) {
		boolean canUpdate = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canUpdate = reader.hasPermission(UPDATE_ALL_STRING);

		if (canUpdate == false) {
			canUpdate = this.isUser(login);
		}

		return canUpdate;
	}

	private boolean isUser(Login login) {
		Key<User> userKey = login.getUser();
		boolean isUser = userKey.equals(userKey);
		return isUser;
	}

}
