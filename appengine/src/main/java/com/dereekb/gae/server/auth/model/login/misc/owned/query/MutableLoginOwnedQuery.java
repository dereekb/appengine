package com.dereekb.gae.server.auth.model.login.misc.owned.query;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Mutable {@link LoginOwnedQuery}.
 *
 * @author dereekb
 *
 */
public interface MutableLoginOwnedQuery
        extends LoginOwnedQuery {

	public void setLogin(ModelKey login);

	public void setLogin(ModelKeyQueryFieldParameter login);

	public void setLogin(String login);

}
