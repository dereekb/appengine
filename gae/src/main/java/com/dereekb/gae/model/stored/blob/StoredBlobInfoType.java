package com.dereekb.gae.model.stored.blob;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * A type that contains more information about a {@link StoredBlob}.
 *
 * @author dereekb
 *
 */
public interface StoredBlobInfoType {

	/**
	 * @return Returns the key to the {@link StoredBlob}.
	 */
	public ModelKey getStoredBlobKey();

}
