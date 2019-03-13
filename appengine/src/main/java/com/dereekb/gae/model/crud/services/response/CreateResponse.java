package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.response.pair.InvalidCreateTemplatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Create response for a {@link CreateService}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see CreateRequest
 */
public interface CreateResponse<T extends UniqueModel>
        extends SimpleCreateResponse<T> {

	/**
	 * Returns a collection of keyed failures for templates, keyed by the index
	 * in the input.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	@Override
	public Collection<? extends InvalidCreateTemplatePair<T>> getInvalidTemplates();

}
