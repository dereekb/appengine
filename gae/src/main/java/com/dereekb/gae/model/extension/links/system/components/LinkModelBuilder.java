package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used for building a {@link LinkModel} for the input model.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LinkModelBuilder<T extends UniqueModel> {

	/**
	 * Creates a {@link LinkModel} for the input model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link LinkModel}. Never {@code null}.
	 */
	public LinkModel makeLinkModel(T model);

}
