package com.dereekb.gae.server.auth.deprecated.permissions;

import com.googlecode.objectify.condition.ValueIf;

public class IfNoRoles extends ValueIf<Permissions> {

	@Override
	public boolean matchesValue(Permissions value) {
		return (value.hasRoles() == false);
	}
	
}