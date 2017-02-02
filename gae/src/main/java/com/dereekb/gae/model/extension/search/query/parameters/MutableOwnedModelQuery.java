package com.dereekb.gae.model.extension.search.query.parameters;

import com.dereekb.gae.utilities.query.builder.parameters.impl.StringQueryFieldParameter;

public interface MutableOwnedModelQuery {

	public void setOwnerId(String ownerIdParameter);

	public void setOwnerId(StringQueryFieldParameter ownerId);

}
