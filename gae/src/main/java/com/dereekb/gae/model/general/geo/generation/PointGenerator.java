package com.dereekb.gae.model.general.geo.generation;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.utilities.misc.random.DoubleGenerator;

/**
 *
 * @author dereekb
 *
 */
public class PointGenerator extends AbstractGenerator<Point> {

	private static final DoubleGenerator LATITUDE_GENERATOR = new DoubleGenerator(-90.0, 90.0);
	private static final DoubleGenerator LONGITUDE_GENERATOR = new DoubleGenerator(-180.0, 180.0);

	public PointGenerator() {}

	@Override
	public Point generate(Long seed) {
		Point point = new Point();

		point.setLatitude(LATITUDE_GENERATOR.generate(seed));
		point.setLongitude(LONGITUDE_GENERATOR.generate(seed));

		return point;
	}

	@Override
	public String toString() {
		return "PointGenerator []";
	}

}
