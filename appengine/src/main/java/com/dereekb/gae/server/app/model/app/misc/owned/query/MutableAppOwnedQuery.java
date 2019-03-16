package com.dereekb.gae.server.app.model.app.misc.owned.query;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Mutable {@link AppOwnedQuery}.
 *
 * @author dereekb
 *
 */
public interface MutableAppOwnedQuery
        extends AppOwnedQuery {

	public void setApp(ModelKey app);

	public void setApp(ModelKeyQueryFieldParameter app);

	public void setApp(String app);

}
