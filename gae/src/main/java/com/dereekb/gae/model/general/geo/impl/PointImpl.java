package com.dereekb.gae.model.general.geo.impl;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.dereekb.gae.model.general.geo.MutablePoint;
import com.dereekb.gae.model.general.geo.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.appengine.api.datastore.GeoPt;

/**
 * Represents a geographical point in the world.
 *
 * Also acts as it's own DTO.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class PointImpl
        implements MutablePoint {

	public static final Point ORIGIN = new PointImpl();

	@NotNull
	@Min(-90)
	@Max(90)
	private double latitude;

	@NotNull
	@Min(-180)
	@Max(180)
	private double longitude;

	public PointImpl() {
		this.latitude = 0;
		this.longitude = 0;
	}

	public PointImpl(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public PointImpl(Point location) {
		this(location.getLatitude(), location.getLongitude());
	}

	public static PointImpl fromGeoPt(GeoPt pt) {
		if (pt != null) {
			return new PointImpl(pt.getLatitude(), pt.getLongitude());
		} else {
			return null;
		}
	}

	public static boolean isOrigin(Point point) {
		boolean isOrigin = true;

		if (point != null) {
			isOrigin = (point.getLatitude() == 0 && point.getLongitude() == 0);
		}

		return isOrigin;
	}

	public static GeoPt makeGeoPt(Point point) {
		if (point != null) {
			Double lat = point.getLatitude();
			Double lon = point.getLongitude();
			return new GeoPt(lat.floatValue(), lon.floatValue());
		} else {
			return null;
		}
	}

	public GeoPt toGeoPt() {
		return PointImpl.makeGeoPt(this);
	}

	@JsonIgnore
	public boolean isDefault() {
		return PointImpl.isOrigin(this);
	}

	/**
	 * Returns True
	 *
	 * @param location
	 * @return
	 */
	public boolean locationsMatch(PointImpl location) {
		return ((this.latitude == location.latitude) && (this.longitude == location.longitude));
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		PointImpl other = (PointImpl) obj;
		if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
			return false;
		}
		if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Point [latitude=" + this.latitude + ", longitude=" + this.longitude + "]";
	}

}