package com.dereekb.gae.server.auth.security.role;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.utilities.collections.map.MapReader;

/**
 * Default implementation of {@link LoginRoleReader}.
 *
 * @author dereekb
 */
public class LoginRoleReaderImpl
        implements LoginRoleReader {

	private Map<Integer, ? extends Role> rolesMap;

	public LoginRoleReaderImpl(Map<Integer, ? extends Role> rolesMap) {
		this.rolesMap = rolesMap;
	}

	public Map<Integer, ? extends Role> getRolesMap() {
		return this.rolesMap;
	}

	public void setRolesMap(Map<Integer, ? extends Role> rolesMap) {
		this.rolesMap = rolesMap;
	}

	@Override
	public List<? extends Role> rolesForLogin(Login login) {
		Set<Integer> roleIds = login.getRoles();
		List<? extends Role> roles = MapReader.quickRead(this.rolesMap, roleIds);
		return roles;
	}

}
