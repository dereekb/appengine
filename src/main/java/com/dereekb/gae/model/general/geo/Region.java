package com.dereekb.gae.model.general.geo;

import java.util.List;

import javax.validation.Valid;

import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Contains a set of {@link Points} that make up a region.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Region {

	/**
	 * List of points that make up this region.
	 */
	@Valid
	private List<PointImpl> points;

	public Region() {}

	public Region(List<PointImpl> points) {
		this.points = points;
	}

	public List<PointImpl> getPoints() {
		return this.points;
	}

	public void setPoints(List<PointImpl> points) {
		this.points = points;
	}

	public boolean isEmpty() {
		return (this.points == null || this.points.isEmpty());
	}

}
