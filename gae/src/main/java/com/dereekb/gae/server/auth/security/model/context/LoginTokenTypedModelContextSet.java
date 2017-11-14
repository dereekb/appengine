package com.dereekb.gae.server.auth.security.model.context;

import java.util.List;

import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * Simple accessor for {@link LoginTokenModelContext} values.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenTypedModelContextSet extends TypedModel {

	/**
	 * Returns all {@link LoginTokenModelContext} values.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<LoginTokenModelContext> getContexts();

}
