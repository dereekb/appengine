package com.dereekb.gae.server.storage.services.blobstore.object.path.impl;

import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.object.path.RelativeFilePathResolver;
import com.dereekb.gae.server.storage.services.blobstore.object.path.BlobKeyResolver;
import com.dereekb.gae.server.storage.services.blobstore.object.path.BlobstoreRelativeFilePathResolver;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Default implementation of {@link BlobstoreRelativeFilePathResolver} that uses
 * a {@link RelativeFilePathResolver} and {@link BlobKeyResolver} to
 * generate {@link BlobKey} instances for input.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public class BlobstoreRelativeFilePathResolverImpl<T>
        implements BlobstoreRelativeFilePathResolver<T> {

	private BlobKeyResolver converter;
	private RelativeFilePathResolver<T> resolver;

	public BlobstoreRelativeFilePathResolverImpl(BlobKeyResolver converter, RelativeFilePathResolver<T> resolver) {
		this.converter = converter;
		this.resolver = resolver;
	}

	public BlobKeyResolver getConverter() {
		return this.converter;
	}

	public void setConverter(BlobKeyResolver converter) {
		this.converter = converter;
	}

	public RelativeFilePathResolver<T> getResolver() {
		return this.resolver;
	}

	public void setResolver(RelativeFilePathResolver<T> resolver) {
		this.resolver = resolver;
	}

	// MARK: BlobstoreRelativeFilePathResolver
	@Override
	public BlobKey blobKeyForFile(T relative,
	                              Storable reference) {

		StorableFile file = this.resolver.pathForStorable(relative, reference);
		BlobKey blobKey = this.converter.blobKeyForFile(file);
		return blobKey;
	}

	@Override
	public String toString() {
		return "BlobstoreRelativeFilePathResolverImpl [resolver=" + this.resolver + ", converter=" + this.converter
		        + "]";
	}

}
