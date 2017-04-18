package com.dereekb.gae.server.datastore.configuration;

/**
 * Mutable {@link GetterConfiguration}.
 * 
 * @author dereekb
 *
 */
public interface MutableGetterConfiguration
        extends GetterConfiguration {

	public void setAllowCache(boolean cache);

}
