package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Request for the {@link LinksService}.
 * 
 * @author dereekb
 *
 * @see LinksServiceModelRequest
 * @see LinksServiceKeyRequest
 */
public interface LinksServiceRequest {

	public LinksAction getAction();

	public String getType();

	public Collection<ModelKey> getLinkKeys();

}
