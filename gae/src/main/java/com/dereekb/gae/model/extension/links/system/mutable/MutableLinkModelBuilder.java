package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used for building {@link MutableLinkModel} instances.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface MutableLinkModelBuilder<T extends UniqueModel> {

	/**
	 * Creates a {@link MutableLinkModel} for the input model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link MutableLinkModel}. Never {@code null}.
	 */
	public MutableLinkModel makeMutableLinkModel(T model);

}
