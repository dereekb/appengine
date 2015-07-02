package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delegate for {@link LinkImpl}.
 *
 * @author dereekb
 *
 */
public interface LinkImplDelegate {

	public List<ModelKey> keys();

	public void remove(ModelKey key);

	public void add(ModelKey key);

	public void clear();

}
