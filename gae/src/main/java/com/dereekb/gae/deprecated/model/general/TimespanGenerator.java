package com.thevisitcompany.gae.deprecated.model.general;

import com.thevisitcompany.gae.model.extension.generation.AbstractGenerator;

public class TimespanGenerator extends AbstractGenerator<Timespan> {

	@Override
	public Timespan generate() {
		Integer value = this.randomPositiveInt();

		byte days = (byte) this.random.nextInt(Byte.MAX_VALUE);
		int from = (int) (value % 720);
		int to = (int) ((value % 720) + 720);

		Timespan timespan = new Timespan(days, from, to);
		return timespan;
	}
}
