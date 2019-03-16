package com.dereekb.gae.model.general.geo.generation;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.dereekb.gae.utilities.misc.random.DoubleGenerator;

/**
 *
 * @author dereekb
 *
 */
public class PointGenerator extends AbstractGenerator<Point> {

	private static final DoubleGenerator LATITUDE_GENERATOR = new DoubleGenerator(-90.0, 90.0);
	private static final DoubleGenerator LONGITUDE_GENERATOR = new DoubleGenerator(-180.0, 180.0);

	/**
	 * {@link PointGenerator} singleton.
	 */
	public static final PointGenerator GENERATOR = new PointGenerator();

	public PointGenerator() {}

	@Override
	public Point generate(GeneratorArg arg) {
		PointImpl point = new PointImpl();

		point.setLatitude(LATITUDE_GENERATOR.generate(arg));
		point.setLongitude(LONGITUDE_GENERATOR.generate(arg));

		return point;
	}

	@Override
	public String toString() {
		return "PointGenerator []";
	}

}
