package com.dereekb.gae.test.utility.misc.range;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.misc.range.ComparableRange;

public class ComparableRangeTest {

	@Test
	public void testRange() {
		Integer min = 0, max = 20, def = 10;
		ComparableRange<Integer> range = new ComparableRange<Integer>();

		range.setMinValue(min);
		range.setDefaultValue(def);
		range.setMaxValue(max);

		assertTrue(range.getLimitedValue(100) == max);
		assertTrue(range.getLimitedValue(-100) == min);
		assertTrue(range.getLimitedValue(null) == def);

		range.setDefaultValue(null);
		assertNull(range.getLimitedValue(null));

		range.setMaxValue(null);
		assertTrue(range.getLimitedValue(100) == 100);
		assertTrue(range.getLimitedValue(-100) == min);

		range.setMinValue(null);
		assertTrue(range.getLimitedValue(-100) == -100);
	}

}
