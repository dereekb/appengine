package com.dereekb.gae.server.storage.gcs.blobstore.path;

import com.dereekb.gae.server.storage.file.Storable;
import com.dereekb.gae.server.storage.file.StorableFile;
import com.dereekb.gae.server.storage.path.RelativeFilePathResolver;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Default implementation of {@link BlobstoreRelativeFilePathResolver} that uses
 * a {@link RelativeFilePathResolver} and {@link BlobstorePathConverter} to
 * generate {@link BlobKey} instances for input.
 *
 * @author dereekb
 * @param <T>
 */
public final class DefaultBlobstoreRelativeFilePathResolver<T>
        implements BlobstoreRelativeFilePathResolver<T> {

	private final RelativeFilePathResolver<T> resolver;
	private final BlobstorePathConverter converter;

	public DefaultBlobstoreRelativeFilePathResolver(RelativeFilePathResolver<T> resolver,
	        BlobstorePathConverter converter) {
		this.resolver = resolver;
		this.converter = converter;
	}

	public RelativeFilePathResolver<T> getResolver() {
		return this.resolver;
	}

	public BlobstorePathConverter getConverter() {
		return this.converter;
	}

	@Override
	public BlobKey blobKeyForFile(T relative,
	                              Storable reference) {

		StorableFile file = this.resolver.pathForStorable(relative, reference);
		BlobKey blobKey = this.converter.blobKeyForFile(file);
		return blobKey;
	}

}
