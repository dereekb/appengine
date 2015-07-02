package com.dereekb.gae.model.extension.links.components.impl.link;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;


public interface SingleLinkDelegate {

	public ModelKey getKey();

	public void setKey(ModelKey key);

}
