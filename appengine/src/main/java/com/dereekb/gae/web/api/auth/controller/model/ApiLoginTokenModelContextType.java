package com.dereekb.gae.web.api.auth.controller.model;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiLoginTokenModelContextRequest;

/**
 * {@link ApiLoginTokenModelContextRequest} type.
 * 
 * @author dereekb
 *
 */
public interface ApiLoginTokenModelContextType
        extends TypedModel {

	/**
	 * Returns the keys set.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getKeys();

}
