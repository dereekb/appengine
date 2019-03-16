package com.dereekb.gae.model.general.time.generation;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;

/**
 * {@link Generator} for {@link WeekTime}.
 *
 * @author dereekb
 *
 */
public class WeekTimeGenerator extends AbstractGenerator<WeekTime> {

	public static final WeekTimeGenerator GENERATOR = new WeekTimeGenerator();

	private Generator<DaySpan> daySpanGenerator = DaySpanGenerator.GENERATOR;
	private Generator<TimeSpan> timeSpanGenerator = TimeSpanGenerator.GENERATOR;

	public Generator<DaySpan> getDaySpanGenerator() {
		return this.daySpanGenerator;
	}

	public void setDaySpanGenerator(Generator<DaySpan> daySpanGenerator) {
		this.daySpanGenerator = daySpanGenerator;
	}

	public Generator<TimeSpan> getTimeSpanGenerator() {
		return this.timeSpanGenerator;
	}

	public void setTimeSpanGenerator(Generator<TimeSpan> timeSpanGenerator) {
		this.timeSpanGenerator = timeSpanGenerator;
	}

	@Override
	public WeekTime generate(GeneratorArg arg) {
		DaySpan daySpan = this.daySpanGenerator.generate(arg);
		TimeSpan timeSpan = this.timeSpanGenerator.generate(arg);
		return new WeekTimeImpl(daySpan, timeSpan);
	}

}
