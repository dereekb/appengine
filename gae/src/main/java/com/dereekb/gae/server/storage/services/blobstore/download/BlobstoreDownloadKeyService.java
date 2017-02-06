package com.dereekb.gae.server.storage.services.blobstore.download;

import com.dereekb.gae.server.storage.download.DownloadKeyService;
import com.dereekb.gae.server.storage.exception.MissingFileException;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.services.blobstore.object.path.BlobKeyResolver;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * {@link DownloadKeyService} implementation that generates a blobstore key for
 * the input file.
 *
 * @author dereekb
 *
 */
public class BlobstoreDownloadKeyService
        implements DownloadKeyService {

	protected BlobKeyResolver resolver;

	public BlobstoreDownloadKeyService(BlobKeyResolver resolver) {
		this.resolver = resolver;
	}

	public BlobKeyResolver getResolver() {
		return this.resolver;
	}

	public void setResolver(BlobKeyResolver resolver) {
		this.resolver = resolver;
	}

	// MARK: DownloadKeyService
	@Override
	public String makeDownloadKey(StorableFile file) throws MissingFileException {
		BlobKey key = this.resolver.blobKeyForFile(file);
		return key.getKeyString();
	}

	@Override
	public String toString() {
		return "BlobstoreDownloadKeyService [resolver=" + this.resolver + "]";
	}

}
