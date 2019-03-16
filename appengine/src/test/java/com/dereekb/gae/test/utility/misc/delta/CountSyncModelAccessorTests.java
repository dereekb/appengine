package com.dereekb.gae.test.utility.misc.delta;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.misc.delta.CountSyncModelAccessor;
import com.dereekb.gae.utilities.misc.delta.MutableCountSyncModel;
import com.dereekb.gae.utilities.misc.delta.impl.LongCountSyncModelAccessor;

/**
 * Tests {@link CountSyncModelAccessor} implementations.
 * 
 * @author dereekb
 *
 */
public class CountSyncModelAccessorTests {

	@Test
	public void testLongAccessorImplementation() {
		TestDelta event = new TestDelta();	// Already in system.

		CountSyncModelAccessor<TestDelta, Long> accessor = event.getCountAccessor();
		accessor.clearDelta();

		assertTrue(event.getDelta() == null);
		assertTrue(event.getCount() == 1);

		accessor.setCount(5L);
		assertTrue(event.getDelta() == 4);
		assertTrue(event.getCount() == 5);

		accessor.setCount(2L);
		// Should be 1 from previous delta.
		assertTrue(event.getDelta() == 1);
		assertTrue(event.getCount() == 2);

		accessor.setCount(-10L);
		assertTrue(event.getDelta() == -11);
		assertTrue(event.getCount() == -10);

		// Simulate save. Lock in the new value.
		accessor.clearDelta();
		accessor.setCount(1L);
		assertTrue(event.getDelta() == 11);
		assertTrue(event.getCount() == 1);

		accessor.setCount(null);
		assertTrue(event.getCount() == 1);
		assertTrue(event.getDelta() == 11);
	}

	public static class TestDelta
	        implements MutableCountSyncModel<TestDelta, Long> {

		private Long count = 1L;
		private Long delta = 1L;

		@Override
		public Long getCount() {
			return this.count;
		}

		public void setCount(Long count) {
			this.count = count;
		}

		@Override
		public Long getDelta() {
			return this.delta;
		}

		public void setDelta(Long delta) {
			this.delta = delta;
		}

		// MARK: MutableCountSyncModel
		@Override
		public CountSyncModelAccessor<TestDelta, Long> getCountAccessor() {
			return LongCountSyncModelAccessor.make(this);
		}

		@Override
		public void setRawCount(Long count) {
			this.count = count;
		}

		@Override
		public void setRawDelta(Long delta) {
			this.delta = delta;
		}

	}

}
