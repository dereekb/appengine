package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 *
 * @author dereekb
 *
 * @see {@link LinksServiceModelRequest}
 * @see {@link LinksServiceKeyRequest}
 */
public interface LinksServiceRequest {

	public LinksAction getAction();

	public String getType();

	public Collection<ModelKey> getLinkKeys();

}
