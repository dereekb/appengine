package com.dereekb.gae.server.storage.object.file.options.impl;

import com.dereekb.gae.server.storage.object.file.options.StorableFileCacheOptions;


/**
 * {@link StorableFileCacheOptions} implementation.
 *
 * @author dereekb
 *
 */
public class StorableFileCacheOptionsImpl
        implements StorableFileCacheOptions {

	private boolean isCacheable;
	private Long maxCacheTime;

	public StorableFileCacheOptionsImpl() {}

	public StorableFileCacheOptionsImpl(boolean isCacheable, Long maxCacheTime) {
		this.isCacheable = isCacheable;
		this.maxCacheTime = maxCacheTime;
	}

	@Override
	public boolean isCacheable() {
		return this.isCacheable;
	}

	public void setCacheable(boolean isCacheable) {
		this.isCacheable = isCacheable;
	}

	@Override
	public Long getMaxCacheTime() {
		return this.maxCacheTime;
	}

	public void setMaxCacheTime(Long maxCacheTime) {
		this.maxCacheTime = maxCacheTime;
	}

	@Override
	public String toString() {
		return "StorableFileCacheOptionsImpl [isCacheable=" + this.isCacheable + ", maxCacheTime=" + this.maxCacheTime
		        + "]";
	}

}
