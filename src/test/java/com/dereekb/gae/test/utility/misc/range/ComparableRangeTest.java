package com.dereekb.gae.test.utility.misc.range;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.misc.range.ComparableRange;

public class ComparableRangeTest {

	@Test
	public void testRange() {
		Integer min = 0, max = 20, def = 10;
		ComparableRange<Integer> range = new ComparableRange<Integer>();

		range.setMinValue(min);
		range.setDefaultValue(def);
		range.setMaxValue(max);

		Assert.assertTrue(range.getLimitedValue(100) == max);
		Assert.assertTrue(range.getLimitedValue(-100) == min);
		Assert.assertTrue(range.getLimitedValue(null) == def);

		range.setDefaultValue(null);
		Assert.assertNull(range.getLimitedValue(null));

		range.setMaxValue(null);
		Assert.assertTrue(range.getLimitedValue(100) == 100);
		Assert.assertTrue(range.getLimitedValue(-100) == min);

		range.setMinValue(null);
		Assert.assertTrue(range.getLimitedValue(-100) == -100);
	}

}
