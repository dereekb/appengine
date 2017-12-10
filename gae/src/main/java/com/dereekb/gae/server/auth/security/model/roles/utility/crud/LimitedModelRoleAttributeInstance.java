package com.dereekb.gae.server.auth.security.model.roles.utility.crud;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public interface LimitedModelRoleAttributeInstance {

	public void assertExists() throws InvalidAttributeException;

	public void assertHasRole(ModelRole role) throws InvalidAttributeException;

}