package com.dereekb.gae.test.model.general.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.HourImpl;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;

/**
 * Used for testing the {@link Compare} interface for time related elements.
 *
 * @author dereekb
 *
 */
public class TimeComparisonTests {

	@Test
	public void testTimeComparisons() {
		Time a = TimeImpl.midnight();
		Time b = TimeImpl.noon();

		int abComparison = a.compareTo(b);
		Assert.assertTrue(abComparison < 0); // A is before/less than B

		int aaComparison = a.compareTo(a);
		Assert.assertTrue(aaComparison == 0); // A is the same as A.

		int baComparison = b.compareTo(a);
		Assert.assertTrue(baComparison > 0); // B is after/greater than B.
	}

	@Test
	public void testHourComparisons() {
		Hour a = HourImpl.midnight();
		Hour b = HourImpl.noon();

		int abComparison = a.compareTo(b);
		Assert.assertTrue(abComparison < 0); // A is before/less than B

		int aaComparison = a.compareTo(a);
		Assert.assertTrue(aaComparison == 0); // A is the same as A.

		int baComparison = b.compareTo(a);
		Assert.assertTrue(baComparison > 0); // B is after/greater than B.
	}

	@Test
	public void testTimeSpanSameStartTimeComparison() {
		Time timeA = TimeImpl.midnight();
		Time timeB = TimeImpl.noon();
		Time timeC = new TimeImpl(2, 30, TimeAmPm.PM);

		TimeSpan a = new TimeSpanImpl(timeA, timeB);
		TimeSpan b = new TimeSpanImpl(timeA, timeC);

		int abComparison = a.compareTo(b);
		Assert.assertTrue(abComparison < 0); // A is before/less than B.

		int aaComparison = a.compareTo(a);
		Assert.assertTrue(aaComparison == 0); // A is the same as A.

		int baComparison = b.compareTo(a);
		Assert.assertTrue(baComparison > 0); // B is after/greater than B.
	}

	@Test
	public void testTimeSpanDifferentStartTimeComparison() {
		Time timeA = TimeImpl.midnight();
		Time timeB = new TimeImpl(1, 30, TimeAmPm.AM);
		Time timeC = new TimeImpl(2, 30, TimeAmPm.PM);

		TimeSpan a = new TimeSpanImpl(timeA, timeC);
		TimeSpan b = new TimeSpanImpl(timeB, timeC);

		int abComparison = a.compareTo(b);
		Assert.assertTrue(abComparison < 0); // A is before/less than B.

		int aaComparison = a.compareTo(a);
		Assert.assertTrue(aaComparison == 0); // A is the same as A.

		int baComparison = b.compareTo(a);
		Assert.assertTrue(baComparison > 0); // B is after/greater than B.
	}

}
