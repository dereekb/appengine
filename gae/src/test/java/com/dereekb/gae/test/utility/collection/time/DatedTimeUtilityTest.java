package com.dereekb.gae.test.utility.collection.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.time.utility.TimeBlockRounding;

/**
 * Used for testing dated time utilities.
 * 
 * @author dereekb
 *
 */
public class DatedTimeUtilityTest {

	/**
	 * Tests rounding block values.
	 */
	@Test
	public void testBlockRounding() {

		Long time = 1000L;
		Long timeInPeriod = 500L;

		// Time Period with even floor/ceil values
		Integer ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		Integer floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(2));
		Assert.assertTrue(floorRound.equals(ceilRound));

		// Time Period with Different floor/ceil values
		timeInPeriod = 300L;

		ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(4));
		Assert.assertTrue(floorRound.equals(3));

		// Time Period equal to time
		timeInPeriod = time;

		ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(1));
		Assert.assertTrue(floorRound.equals(ceilRound));

		// Time Period with greatly larger timeInPeriod
		timeInPeriod = time * 100000;

		ceilRound = TimeBlockRounding.CEIL.roundBlockSize(time, timeInPeriod);
		floorRound = TimeBlockRounding.FLOOR.roundBlockSize(time, timeInPeriod);

		Assert.assertTrue(ceilRound.equals(1));
		Assert.assertTrue(floorRound.equals(0));
	}

}
