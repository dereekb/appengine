package com.dereekb.gae.model.general.geo;

/**
 * Mutable {@link Point}.
 * 
 * @author dereekb
 *
 */
public interface MutablePoint extends Point {

	public void setLatitude(double latitude);

	public void setLongitude(double longitude);

}
