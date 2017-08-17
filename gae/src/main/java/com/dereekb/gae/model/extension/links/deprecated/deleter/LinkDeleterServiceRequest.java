package com.dereekb.gae.model.extension.links.deleter;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by {@link LinkDeleterService} instances to process a delete request.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface LinkDeleterServiceRequest {

	/**
	 * Returns the model type.
	 *
	 * @return Model type. Never {@code null}.
	 */
	public String getModelType();

	/**
	 * @return Returns the keys of models to unlink. Never {@code null}.
	 */
	public Collection<ModelKey> getTargetKeys();

}
