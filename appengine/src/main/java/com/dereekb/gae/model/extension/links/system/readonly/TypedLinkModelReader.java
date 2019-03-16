package com.dereekb.gae.model.extension.links.system.readonly;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.components.LinkModel;

/**
 * Used for creating a {@link LinkModel} from input models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface TypedLinkModelReader<T> {

	/**
	 * Makes a link model for the input model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link LinkModel}. Never {@code null}.
	 */
	public LinkModel makeLinkModel(T model);

	/**
	 * Loads links for the requested models.
	 * 
	 * @param models
	 *            {@link Collection}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<? extends LinkModel> makeLinkModels(Collection<T> models);

}
