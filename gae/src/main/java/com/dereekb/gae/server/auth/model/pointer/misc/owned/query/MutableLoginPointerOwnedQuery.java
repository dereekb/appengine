package com.dereekb.gae.server.auth.model.pointer.misc.owned.query;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Mutable {@link LoginPointerOwnedQuery}.
 *
 * @author dereekb
 *
 */
public interface MutableLoginPointerOwnedQuery
        extends LoginPointerOwnedQuery {

	public void setLoginPointer(ModelKey loginPointer);

	public void setLoginPointer(ModelKeyQueryFieldParameter loginPointer);

	public void setLoginPointer(String loginPointer);

}
