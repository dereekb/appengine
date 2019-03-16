package com.thevisitcompany.gae.deprecated.model.users.login.functions.filters;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.authentication.login.support.AbstractLoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.model.crud.function.filters.CanCreateFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanDeleteFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanReadFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanUpdateFilterDelegate;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.PermissionsReader;
import com.thevisitcompany.visit.models.locations.permissions.LocationsPermissionHandler;

public class LoginChangesFilterDelegate extends AbstractLoginSourceDependent
        implements CanUpdateFilterDelegate<Login>, CanCreateFilterDelegate<Login>, CanDeleteFilterDelegate<Login>,
        CanReadFilterDelegate<Login> {

	private static final String CREATE_ALL_STRING = LocationsPermissionHandler.permissionsRequestString("login.create");

	private static final String READ_ALL_STRING = LocationsPermissionHandler.permissionsRequestString("login.read.*");

	private static final String UPDATE_ALL_STRING = LocationsPermissionHandler
	        .permissionsRequestString("login.update.*");

	private static final String DELETE_ALL_STRING = LocationsPermissionHandler
	        .permissionsRequestString("login.delete.*");

	@Override
	public boolean canRead(Login object) {
		boolean canRead = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canRead = reader.hasPermission(READ_ALL_STRING);

		if (canRead == false) {
			Key<Login> loginKey = login.getKey();
			canRead = loginKey.equals(object.getKey());
		}

		return canRead;
	}

	@Override
	public boolean canDelete(Login object) {
		boolean canDelete = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canDelete = reader.hasPermission(DELETE_ALL_STRING);

		return canDelete;
	}

	@Override
	public boolean canCreate(Login object) {
		boolean canCreate = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canCreate = reader.hasPermission(CREATE_ALL_STRING);

		if (canCreate == false) {
			Key<Login> loginKey = login.getKey();
			canCreate = loginKey.equals(object.getKey());
		}

		return true;
	}

	@Override
	public boolean canUpdate(Login object) {
		boolean canUpdate = false;

		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);
		canUpdate = reader.hasPermission(UPDATE_ALL_STRING);

		if (canUpdate == false) {
			Key<Login> loginKey = login.getKey();
			canUpdate = loginKey.equals(object.getKey());
		}

		return canUpdate;
	}

}
