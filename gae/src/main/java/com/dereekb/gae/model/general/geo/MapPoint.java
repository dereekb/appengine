package com.dereekb.gae.model.general.geo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A map point that contains a {@link PointImpl}, {@link Address}, and {@link Zoom}
 * value.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class MapPoint {

	public static final Integer MIN_ZOOM = 0;
	public static final Integer MAX_ZOOM = 21;

	private static final Integer DEFAULT_ZOOM = 3;

	@NotNull
	private PointImpl point;

	private Address address;

	@Min(0)
	@Max(21)
	private int zoom;

	public MapPoint() {
		this.point = new PointImpl();
	}

	public MapPoint(PointImpl point) {
		this(point, DEFAULT_ZOOM);
	}

	public MapPoint(PointImpl point, int zoom) {
		this.point = point;
		this.zoom = zoom;
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point point) {
		this.point = new PointImpl(point);
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getZoom() {
		return this.zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}
