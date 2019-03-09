package com.dereekb.gae.model.general.time.generation;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.util.WeekTimeConverter;
import com.dereekb.gae.model.general.time.util.impl.WeekTimeConverterImpl;

/**
 * {@link Generator} for an encoded {@link WeekTime}.
 *
 * @author dereekb
 *
 */
public class EncodedWeekTimeGenerator extends AbstractGenerator<Integer> {

	public static final EncodedWeekTimeGenerator GENERATOR = new EncodedWeekTimeGenerator();

	private static final WeekTimeConverter WEEK_TIME_CONVERTER = new WeekTimeConverterImpl();

	private Generator<WeekTime> weekTimeGenerator = WeekTimeGenerator.GENERATOR;

	public Generator<WeekTime> getWeekTimeGenerator() {
		return this.weekTimeGenerator;
	}

	public void setWeekTimeGenerator(Generator<WeekTime> weekTimeGenerator) {
		this.weekTimeGenerator = weekTimeGenerator;
	}

	@Override
	public Integer generate(GeneratorArg arg) {
		WeekTime weekTime = this.weekTimeGenerator.generate(arg);
		Integer time = WEEK_TIME_CONVERTER.weekTimeToNumber(weekTime);
		return time;
	}

}
