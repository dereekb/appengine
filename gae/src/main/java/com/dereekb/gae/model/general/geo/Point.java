package com.dereekb.gae.model.general.geo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
public final class Point {
	
	public static final Point ORIGIN = new Point();

	@NotNull
	@Min(-90)
	@Max(90)
	private double latitude;

	@NotNull
	@Min(-180)
	@Max(180)
	private double longitude;

	public Point() {
		this.latitude = 0;
		this.longitude = 0;
	}

	public Point(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Point(Point location) {
		this(location.getLatitude(), location.getLongitude());
	}

	public static Point fromGeoPt(GeoPt pt) {
		if (pt != null) {
			return new Point(pt.getLatitude(), pt.getLongitude());
		} else {
			return null;
		}
	}

	public static GeoPt makeGeoPt(Point point) {
		if (point != null) {
			return point.toGeoPt();
		} else {
			return null;
		}
	}

	public static boolean isOrigin(Point point) {
		boolean isOrigin = true;
		
		if (point != null) {
			isOrigin = point.isDefault();
		}
		
		return isOrigin;
	}
	
	public GeoPt toGeoPt() {
		Double lat = this.getLatitude();
		Double lon = this.getLongitude();
		return new GeoPt(lat.floatValue(), lon.floatValue());
	}

	/**
	 * Returns True
	 *
	 * @param location
	 * @return
	 */
	public boolean locationsMatch(Point location) {
		return ((this.latitude == location.latitude) && (this.longitude == location.longitude));
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@JsonIgnore
	public boolean isDefault() {
		return (this.latitude == 0 && this.longitude == 0);
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
		Point other = (Point) obj;
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