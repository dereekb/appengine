package com.dereekb.gae.web.api.model.extension.search;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Represents a request made to the {@link SearchExtensionApiController} to update
 * the index and search models.
 *
 * @author dereekb
 *
 */
public interface ApiSearchUpdateRequest {

	/**
	 * Returns the target type.
	 *
	 * @return target type. Never {@code null}.
	 */
	public String getUpdateTargetType();

	/**
	 * Returns the target keys. Values are string-encoded {@link ModelKey}
	 * values.
	 *
	 * @return {@link List} of target keys. Never {@code null} and shouldn't be
	 *         empty.
	 */
	public List<String> getUpdateTargetKeys();

}
