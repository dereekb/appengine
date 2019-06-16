package com.dereekb.gae.server.storage.services.gcs.impl;

import com.dereekb.gae.server.deprecated.storage.object.file.StorableContent;
import com.dereekb.gae.server.deprecated.storage.object.file.options.StorableFileCacheOptions;
import com.dereekb.gae.server.deprecated.storage.object.file.options.StorableFileOptions;
import com.dereekb.gae.server.deprecated.storage.object.file.options.StorableFileVisibility;
import com.dereekb.gae.server.deprecated.storage.services.gcs.object.request.GcsStorableFileSaveRequest;
import com.dereekb.gae.server.deprecated.storage.services.gcs.object.request.GcsStorableFileSaveRequestBuilder;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFileOptions.Builder;

/**
 * {@link GcsStorableFileSaveRequest} implementation.
 *
 * @author dereekb
 *
 */
public class GcsStorableFileSaveRequestBuilderImpl
        implements GcsStorableFileSaveRequestBuilder {

	private static final String PUBLIC_VISIBILITY = "public-read";
	private static final String SYSTEM_VISIBILITY = "private";
	private static final String ALLOW_CACHE_STRING_FORMAT = "public, max-age=%d";
	private static final String DISALLOW_CACHE_STRING_FORMAT = "private, max-age=0, no-transform";

	private StorableFileOptions defaultOptions;

	public GcsStorableFileSaveRequestBuilderImpl() {}

	public GcsStorableFileSaveRequestBuilderImpl(StorableFileOptions defaultOptions) {
		this.defaultOptions = defaultOptions;
	}

	public StorableFileOptions getDefaultOptions() {
		return this.defaultOptions;
	}

	public void setDefaultOptions(StorableFileOptions defaultOptions) {
		this.defaultOptions = defaultOptions;
	}

	@Override
	public GcsStorableFileSaveRequest buildRequest(String gcsBucket,
	                                               StorableContent content) {
		GcsFileOptions options = this.buildOptions(content);
		GcsStorableFileSaveRequestImpl request = new GcsStorableFileSaveRequestImpl(gcsBucket, content, options);
		return request;
	}

	// MARK: Options
	public GcsFileOptions buildOptions(StorableContent file) {
		GcsFileOptions.Builder builder = new GcsFileOptions.Builder();
		StorableFileOptions fileOptions = this.getOptionsForFile(file);

		// Append MIME-Type
		String mimeType = file.getContentType();
		builder = builder.mimeType(mimeType);

		// Append remaining options
		if (fileOptions != null) {
			builder = this.appendOptionsToBuilder(fileOptions, builder);
		}

		GcsFileOptions options = builder.build();
		return options;
	}

	private StorableFileOptions getOptionsForFile(StorableContent file) {
		StorableFileOptions options = file.getOptions();

		if (options == null) {
			options = this.defaultOptions;
		}

		return options;
	}

	private Builder appendOptionsToBuilder(StorableFileOptions fileOptions,
	                                       Builder builder) {

		StorableFileVisibility visibility = fileOptions.getVisibility();
		builder = this.appendVisibilityOptionsToBuilder(visibility, builder);

		StorableFileCacheOptions cacheOptions = fileOptions.getCacheOptions();
		builder = this.appendCacheOptionsToBuilder(cacheOptions, builder);

		return builder;
	}

	// MARK: Visibility
	private Builder appendVisibilityOptionsToBuilder(StorableFileVisibility visibility,
	                                                 Builder builder) {

		if (visibility != null) {
			builder = builder.acl(this.stringForVisibility(visibility));
		}

		return builder;
	}

	private String stringForVisibility(StorableFileVisibility visibility) {
		String string = null;

		switch (visibility) {
			case SYSTEM:
				string = SYSTEM_VISIBILITY;
				break;
			case PUBLIC:
				string = PUBLIC_VISIBILITY;
				break;
		}

		return string;
	}

	// MARK: Cache Options
	private Builder appendCacheOptionsToBuilder(StorableFileCacheOptions cacheOptions,
	                                            Builder builder) {

		if (cacheOptions != null) {
			String cacheString = this.stringForCacheOptions(cacheOptions);
			builder = builder.cacheControl(cacheString);
		}

		return builder;
	}

	private String stringForCacheOptions(StorableFileCacheOptions cacheOptions) {
		String cacheString = null;

		boolean cache = cacheOptions.isCacheable();
		Long time = cacheOptions.getMaxCacheTime();

		if (cache) {
			cacheString = String.format(ALLOW_CACHE_STRING_FORMAT, time);
		} else {
			cacheString = DISALLOW_CACHE_STRING_FORMAT;
		}

		return cacheString;
	}

	@Override
	public String toString() {
		return "GcsStorableFileSaveRequestBuilderImpl [defaultOptions=" + this.defaultOptions + "]";
	}

}
