package com.dereekb.gae.server.auth.model.pointer.misc.owned.query;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Query for an {@link LoginPointer}-related model that can query by
 * loginPointer.
 *
 * @author dereekb
 *
 */
public interface LoginPointerOwnedQuery {

	/**
	 * Returns the loginPointer parameter, if set.
	 *
	 * @return {@link ModelKeyQueryFieldParameter}, or {@code null} if not set.
	 */
	public ModelKeyQueryFieldParameter getLoginPointer();

}
