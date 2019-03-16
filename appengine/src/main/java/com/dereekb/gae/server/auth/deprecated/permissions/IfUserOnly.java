package com.dereekb.gae.server.auth.deprecated.permissions;

import java.util.Set;

import com.dereekb.gae.server.auth.deprecated.permissions.roles.RoleType;
import com.googlecode.objectify.condition.ValueIf;

public class IfUserOnly extends ValueIf<Permissions> {

	@Override
	public boolean matchesValue(Permissions value) {
		Set<RoleType> roleTypes = value.getRoleTypes();
		return (roleTypes.size() == 1 && (roleTypes.contains(RoleType.USER) || roleTypes.contains(RoleType.PUBLIC)));
	}
	
}
