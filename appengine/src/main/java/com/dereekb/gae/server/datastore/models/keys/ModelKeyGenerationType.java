package com.dereekb.gae.server.datastore.models.keys;

import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * Model Key generation type.
 *
 * @author dereekb
 *
 */
public enum ModelKeyGenerationType implements IndexCoded {

	AUTOMATIC(0),

	/**
	 * Name not related to another model.
	 */
	UNIQUE_NAME(1),

	/**
	 * Related to another model, and is self-generating.
	 */
	RELATED_NAME(2);

	public final int code;

	private ModelKeyGenerationType(int code) {
		this.code = code;
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

}
