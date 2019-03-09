package com.dereekb.gae.model.general.time.generation;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.utilities.misc.random.IntegerGenerator;

/**
 * {@link Generator} for {@link TimeImpl}.
 *
 * @author dereekb
 *
 */
public class TimeGenerator extends AbstractGenerator<Time> {

	public static final TimeGenerator GENERATOR = new TimeGenerator();

	private static final Generator<Integer> HOURS_GENERATOR = new IntegerGenerator(0, 24);
	private static final Generator<Integer> MINUTES_GENERATOR = new IntegerGenerator(0, 60);

	@Override
	public Time generate(GeneratorArg arg) {
		int hour = HOURS_GENERATOR.generate(arg);
		int minutes = MINUTES_GENERATOR.generate(arg);

		TimeImpl time = new TimeImpl(hour, minutes);
		return time;
	}

}
