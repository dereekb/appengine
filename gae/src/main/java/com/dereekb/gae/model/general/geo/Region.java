package com.dereekb.gae.model.general.geo;

import java.util.List;

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
	private List<Point> points;

	public Region() {}

	public Region(List<Point> points) {
		this.points = points;
	}

	public List<Point> getPoints() {
		return this.points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public boolean isEmpty() {
		return (this.points == null || this.points.isEmpty());
	}

}
