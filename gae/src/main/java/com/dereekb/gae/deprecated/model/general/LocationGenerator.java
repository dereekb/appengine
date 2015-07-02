package com.thevisitcompany.gae.deprecated.model.general;

import com.thevisitcompany.gae.model.extension.generation.AbstractGenerator;
import com.thevisitcompany.gae.model.general.geo.Point;
import com.thevisitcompany.gae.utilities.misc.range.IntegerRange;

/**
 * Implements the {@link Generator} interface for generating {@link Point}.
 * 
 * @author dereekb
 */
public class LocationGenerator extends AbstractGenerator<Point> {

	private IntegerRange zoomRange;

	@Override
	public Point generate() {
		Double latitude = ((this.random.nextDouble() * 180) - 90);
		Double longitude = ((this.random.nextDouble() * 360) - 180);
		Integer zoom = this.random.nextInt(21);

		if (this.zoomRange != null) {
			zoom = this.zoomRange.getLimitedValue(zoom);
		}

		Long identifier = this.randomPositiveLong();

		Point location = new Point(latitude, longitude, "Location: " + identifier, "tag");
		location.setZoom(zoom);
		return location;
	}

	public IntegerRange getZoomRange() {
		return zoomRange;
	}

	public void setZoomRange(IntegerRange zoomRange) {
		this.zoomRange = zoomRange;
	}

}
