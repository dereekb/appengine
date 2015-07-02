package com.thevisitcompany.gae.server.auth.deprecated.permissions.filters;

import com.thevisitcompany.gae.server.auth.deprecated.permissions.PermissionsReader;
import com.thevisitcompany.gae.server.auth.model.login.Login;
import com.thevisitcompany.gae.utilities.filters.AbstractFilter.PreFilterResults;

/**
 * A generic permissions filter that uses a permission string.
 *
 * @author dereekb
 */
public abstract class GenericPermissionsFilter<T> extends AbstractLoginDependentFunctionHandlerFilter<T> {

	private String preFilterPermissionPassString = "*";

	public GenericPermissionsFilter() {}

	public GenericPermissionsFilter(String preFilterPermissionPassString) {
		super();
		this.preFilterPermissionPassString = preFilterPermissionPassString;
	}

	public PreFilterResults preFilterCheck(Iterable<T> objects) {
		Login login = this.getLogin();
		PermissionsReader reader = PermissionsReader.reader(login);

		boolean canAccessAll = reader.hasPermission(this.preFilterPermissionPassString);

		PreFilterResults results = (canAccessAll) ? PreFilterResults.PASS_ALL : PreFilterResults.CONTINUE;
		return results;
	}

	/**
	 * String used for the pre filter permissions pass.
	 *
	 * If the login passes this, then they are granted permission to all objects.
	 */
	public final String getPreFilterPermissionPassString() {
		return this.preFilterPermissionPassString;
	}

	public void setPreFilterPermissionPassString(String preFilterPermissionPassString) {
		this.preFilterPermissionPassString = preFilterPermissionPassString;
	}

}
