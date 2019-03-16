package com.dereekb.gae.server.auth.security.model.query;

import com.dereekb.gae.utilities.query.builder.parameters.impl.StringQueryFieldParameter;

public interface MutableOwnedModelQuery {

	public void setOwnerId(String ownerIdParameter) throws IllegalArgumentException;

	public void setOwnerId(StringQueryFieldParameter ownerId) throws IllegalArgumentException;

	public void setEqualsOwnerId(String ownerId) throws IllegalArgumentException;

}
