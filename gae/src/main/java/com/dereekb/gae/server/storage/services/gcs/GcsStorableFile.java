package com.dereekb.gae.server.storage.services.gcs;

import com.google.appengine.tools.cloudstorage.GcsFilename;

/**
 * Storable file that contains a {@link GcsFilename}.
 * 
 * @author dereekb
 *
 */
public interface GcsStorableFile {

	public GcsFilename getGcsFilename();

}
