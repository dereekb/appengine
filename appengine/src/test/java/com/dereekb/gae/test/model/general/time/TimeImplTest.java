package com.dereekb.gae.test.model.general.time;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.impl.TimeImpl;


public class TimeImplTest {

	@Test
	public void testGetHoursBetweenTwoTimesA() {
		Time a = new TimeImpl(0, 0);
		Time b = new TimeImpl(6, 0);

		List<Integer> hours = TimeImpl.getMilitaryHoursBetween(a, b);
		assertTrue(hours.size() == 6); // Should be 0,1,2,3,4,5
	}

	@Test
	public void testGetHoursBetweenTwoTimesWithMinutes() {
		Time a = new TimeImpl(0, 15);
		Time b = new TimeImpl(6, 45);

		List<Integer> hours = TimeImpl.getMilitaryHoursBetween(a, b);
		assertTrue(hours.size() == 6); // Should be 0,1,2,3,4,5
	}

	@Test
	public void testGetHoursBetweenTwoTimesWithOffsetHours() {
		Time a = new TimeImpl(2, 15);
		Time b = new TimeImpl(8, 45);

		List<Integer> hours = TimeImpl.getMilitaryHoursBetween(a, b);
		assertTrue(hours.size() == 6); // Should be 2,3,4,5,6,7

		assertTrue(hours.contains(2));
		assertTrue(hours.contains(7));
	}

}
