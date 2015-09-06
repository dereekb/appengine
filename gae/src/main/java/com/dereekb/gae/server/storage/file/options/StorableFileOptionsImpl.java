package com.dereekb.gae.server.storage.file.options;

/**
 * {@link StorableFileOptions} implementation.
 *
 * @author dereekb
 *
 */
public class StorableFileOptionsImpl
        implements StorableFileOptions {

	private StorableFileVisibility visibility;

	private StorableFileCacheOptions cacheOptions;

	public StorableFileOptionsImpl() {}

	public StorableFileOptionsImpl(StorableFileVisibility visibility, StorableFileCacheOptions cacheOptions) {
		this.visibility = visibility;
		this.cacheOptions = cacheOptions;
	}

	@Override
    public StorableFileVisibility getVisibility() {
		return this.visibility;
	}

	public void setVisibility(StorableFileVisibility visibility) {
		this.visibility = visibility;
	}


	@Override
	public StorableFileCacheOptions getCacheOptions() {
		return this.cacheOptions;
	}

	public void setCacheOptions(StorableFileCacheOptions cacheOptions) {
		this.cacheOptions = cacheOptions;
	}

	/**
	 * Combines a pair of options.
	 *
	 * @param defaultOptions
	 * @return Returns a new instance with the options combined.
	 */
	@Deprecated
	public StorableFileOptionsImpl combine(StorableFileOptionsImpl options) {
		StorableFileOptionsImpl combination = new StorableFileOptionsImpl();

		this.visibility = (this.visibility != null) ? this.visibility : options.visibility;
		this.cacheOptions = (this.cacheOptions != null) ? this.cacheOptions : options.cacheOptions;

		return combination;
	}

	@Override
	public String toString() {
		return "StorableFileOptionsImpl [visibility=" + this.visibility + ", cacheOptions=" + this.cacheOptions + "]";
	}

}
