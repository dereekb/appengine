package com.dereekb.gae.test.model.general.time;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.util.impl.TimeSpanSimplifierImpl;

public class TimeSpanSimplifierImplTest {

	private TimeSpanSimplifierImpl impl = new TimeSpanSimplifierImpl();

	@Test
	public void testCanMerge() {

		assertTrue(this.impl.canMerge(TimeSpanImpl.allDay(), TimeSpanImpl.fromMidnight(TimeImpl.noon())));

	}

}
