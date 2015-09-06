package com.dereekb.gae.server.storage.gcs.files;

import com.dereekb.gae.server.storage.file.StorableContent;
import com.dereekb.gae.server.storage.file.options.StorableFileOptionsImpl;
import com.dereekb.gae.server.storage.file.options.StorableFileVisibility;
import com.dereekb.gae.server.storage.gcs.GcsStorageFileSaveRequest;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;

/**
 * Creates an {@link GcsStorageFileSaveRequest} from this request.
 * 
 * @author dereekb
 */
public class GcsStorageFileRequestBuilder {

	private static final String PUBLIC_VISIBILITY = "public-read";
	private static final String PRIVATE_VISIBILITY = "private";
	private static final String ALLOW_CACHE_STRING_FORMAT = "public, max-age=%d";
	private static final String DISALLOW_CACHE_STRING_FORMAT = "private, max-age=0, no-transform";

	private StorableFileOptionsImpl defaultOptions;
	private Integer defaultCacheTime = 86400; // 1 day in seconds

	/**
	 * Whether or not to combine the {@link StorableFileOptionsImpl} from the object and the default options.
	 */
	private boolean combineOptions = false;

	public GcsStorageFileSaveRequest createSaveRequest(String bucket,
	                                                   StorableContent file) {
		GcsStorageFileSaveRequest request = new GcsStorageFileSaveRequest(bucket, file);

		GcsFileOptions options = this.buildOptions(file);
		request.setOptions(options);

		return request;
	}

	public GcsFileOptions buildOptions(StorableContent file) {
		GcsFileOptions.Builder builder = new GcsFileOptions.Builder();
		StorableFileOptionsImpl fileOptions = this.getOptionsForFile(file);

		String mimeType = file.getContentType();
		builder = builder.mimeType(mimeType);

		if (fileOptions != null) {
			builder = this.appendOptionsToBuilder(fileOptions, builder);
		}

		GcsFileOptions options = builder.build();
		return options;
	}

	private StorableFileOptionsImpl getOptionsForFile(StorableContent file) {
		StorableFileOptionsImpl options = file.getOptions();

		if (options == null) {
			options = this.defaultOptions;
		} else {
			if (this.combineOptions) {
				options = options.combine(this.defaultOptions);
			}
		}

		return options;
	}

	private GcsFileOptions.Builder appendOptionsToBuilder(StorableFileOptionsImpl options,
	                                                      GcsFileOptions.Builder builder) {
		StorableFileVisibility visibility = options.getVisibility();
		if (visibility != null) {
			builder = builder.acl(this.stringForVisibility(visibility));
		}

		Boolean cache = options.getCache();
		if (cache != null) {
			builder = builder.cacheControl(this.stringForCache(options));
		}

		return builder;
	}

	private String stringForVisibility(StorableFileVisibility visibility) {
		String string = null;

		switch (visibility) {
			case SYSTEM:
				string = PRIVATE_VISIBILITY;
				break;
			case PUBLIC:
				string = PUBLIC_VISIBILITY;
				break;
		}

		return string;
	}

	private String stringForCache(StorableFileOptionsImpl options) {
		String cacheString = null;
		boolean cache = options.getCache();
		Integer time = options.getCacheTime();

		if (time == null) {
			time = this.defaultCacheTime;
		}

		if (cache) {
			cacheString = String.format(ALLOW_CACHE_STRING_FORMAT, time);
		} else {
			cacheString = DISALLOW_CACHE_STRING_FORMAT;
		}

		return cacheString;
	}

	public StorableFileOptionsImpl getDefaultOptions() {
		return this.defaultOptions;
	}

	public void setDefaultOptions(StorableFileOptionsImpl defaultOptions) {
		this.defaultOptions = defaultOptions;

		if (defaultOptions == null) {
			this.combineOptions = false;
		}
	}

	public Integer getDefaultCacheTime() {
		return this.defaultCacheTime;
	}

	public void setDefaultCacheTime(Integer defaultCacheTime) throws NullPointerException {
		if (defaultCacheTime == null) {
			throw new NullPointerException("Cannot set default cache time to null.");
		}

		this.defaultCacheTime = defaultCacheTime;
	}

	public boolean isCombineOptions() {
		return this.combineOptions;
	}

	public void setCombineOptions(boolean combineOptions) throws IllegalArgumentException {
		if (combineOptions == true && this.defaultOptions == null) {
			throw new IllegalArgumentException("Cannot combine options while default options is null.");
		}

		this.combineOptions = combineOptions;
	}

}
