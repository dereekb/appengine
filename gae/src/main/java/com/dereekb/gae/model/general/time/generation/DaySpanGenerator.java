package com.dereekb.gae.model.general.time.generation;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.impl.DaySpanBitImpl;

/**
 * {@link Generator} for {@link DaySpan}.
 *
 * @author dereekb
 *
 */
public class DaySpanGenerator extends AbstractGenerator<DaySpan> {

	public static final DaySpanGenerator GENERATOR = new DaySpanGenerator();

	@Override
	public DaySpan generate(GeneratorArg arg) {
		DaySpanBitImpl daySpan = new DaySpanBitImpl();

		Integer days = arg.getGeneratorRandom().nextInt(0b11111110);
		daySpan.setIntegerNumber(days);

		return daySpan;
	}

}
