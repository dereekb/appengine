package com.dereekb.gae.server.storage.file;

/**
 * Generic file options.
 * 
 * @author dereekb
 */
public class StorageFileOptions {

	public static enum StorageFileVisibility {

		/**
		 * Visible and downloadable to anyone.
		 */
		PUBLIC,

		/**
		 * Only accessible by normal means.
		 */
		PRIVATE

	}

	/**
	 * How visible to make the file.
	 */
	private StorageFileVisibility visibility;

	/**
	 * Whether or not to allow client-side caching of value.
	 */
	private Boolean cache;

	/**
	 * Time to cache the object for. In seconds.
	 */
	private Integer cacheTime;

	public StorageFileVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(StorageFileVisibility visibility) {
		this.visibility = visibility;
	}

	public Boolean getCache() {
		return cache;
	}

	public void setCache(Boolean cache) {
		this.cache = cache;
	}

	public Integer getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(Integer cacheTime) {
		this.cacheTime = cacheTime;
	}

	/**
	 * Combines a pair of options.
	 * 
	 * @param defaultOptions
	 * @return Returns a new instance with the options combined.
	 */
	public StorageFileOptions combine(StorageFileOptions options) {
		StorageFileOptions combination = new StorageFileOptions();

		visibility = (this.visibility != null) ? this.visibility : options.visibility;
		cache = (this.cache != null) ? this.cache : options.cache;
		cacheTime = (this.cacheTime != null) ? this.cacheTime : options.cacheTime;

		combination.setVisibility(visibility);
		combination.setCache(cache);
		combination.setCacheTime(cacheTime);

		return combination;
	}

}
