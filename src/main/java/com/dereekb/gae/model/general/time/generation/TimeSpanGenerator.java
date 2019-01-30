package com.dereekb.gae.model.general.time.generation;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;

/**
 * {@link Generator} for {@link TimeSpan}.
 *
 * @author dereekb
 *
 */
public class TimeSpanGenerator extends AbstractGenerator<TimeSpan> {

	public static final TimeSpanGenerator GENERATOR = new TimeSpanGenerator();

	private Generator<Time> timeGenerator = TimeGenerator.GENERATOR;

	public Generator<Time> getTimeGenerator() {
		return this.timeGenerator;
	}

	public void setTimeGenerator(Generator<Time> timeGenerator) {
		this.timeGenerator = timeGenerator;
	}

	@Override
	public TimeSpan generate(GeneratorArg arg) {
		Time timeA = this.timeGenerator.generate(arg);
		Time timeB = this.timeGenerator.generate(arg);

		TimeSpanImpl timeSpan = new TimeSpanImpl(timeA, timeB);
		return timeSpan;
	}

}
