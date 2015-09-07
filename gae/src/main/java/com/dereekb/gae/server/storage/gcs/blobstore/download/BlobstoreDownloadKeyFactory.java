package com.dereekb.gae.server.storage.gcs.blobstore.download;

import com.dereekb.gae.server.storage.download.StoredFileDownloadKeyService;
import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.services.blobstore.object.path.BlobstoreRelativeFilePathResolver;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Factory that implements the {@link StoredFileDownloadKeyService} interface,
 * and generates key strings for BlobKeys.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public final class BlobstoreDownloadKeyFactory<T>
        implements StoredFileDownloadKeyService<T> {

	private final BlobstoreRelativeFilePathResolver<T> resolver;

	public BlobstoreDownloadKeyFactory(BlobstoreRelativeFilePathResolver<T> resolver) {
		super();
		this.resolver = resolver;
	}

	public BlobstoreRelativeFilePathResolver<T> getResolver() {
		return this.resolver;
	}

	@Override
	public String makeDownloadKey(T source,
	                              Storable item) {
		BlobKey key = this.resolver.blobKeyForFile(source, item);
		String keyString = key.getKeyString();
		return keyString;
	}

}
