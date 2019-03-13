package com.dereekb.gae.server.datastore.models.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link UniqueModel}.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractUniqueModel
        implements UniqueModel {

	// MARK: UniqueModel
	@Override
	public ModelKey keyValue() {
		return this.getModelKey();
	}

}
