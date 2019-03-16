package com.dereekb.gae.model.geo.place;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Type that provides information about a {@link GeoPlace}.
 *
 * @author dereekb
 */
public interface GeoPlaceInfoType {

	/**
	 * @return Returns the key to the {@link GeoPlace}.
	 */
	public ModelKey getGeoPlaceKey();

}
