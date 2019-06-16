package com.dereekb.gae.server.storage.object.path;

import com.dereekb.gae.server.deprecated.storage.object.file.Storable;
import com.dereekb.gae.server.deprecated.storage.object.folder.StorableFolder;

/**
 * Interface for generating a concrete path {@link String} for a
 * {@link StorableFolder} and {@link Storable} object.
 *
 * @author dereekb
 */
@Deprecated
public interface FilePathMerger {

	public String makePath(StorableFolder folder,
	                       Storable storable);

}
