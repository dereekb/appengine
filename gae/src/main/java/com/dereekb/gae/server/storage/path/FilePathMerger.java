package com.dereekb.gae.server.storage.path;

import com.dereekb.gae.server.storage.file.Storable;
import com.dereekb.gae.server.storage.folder.StorableFolder;

/**
 * Interface for generating a concrete path {@link String} for a
 * {@link StorableFolder} and {@link Storable} object.
 *
 * @author dereekb
 */
public interface FilePathMerger {

	public String makePath(StorableFolder folder,
	                       Storable storable);

}
