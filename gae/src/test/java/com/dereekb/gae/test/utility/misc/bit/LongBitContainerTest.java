package com.dereekb.gae.test.utility.misc.bit;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

public class LongBitContainerTest {

	@Test
	public void testIndexReadWrite() {
		Long value = 0L;
		Integer index = 0;

		LongBitContainer container = new LongBitContainer(value);
		container.setBit(true, index);
		Assert.assertTrue(container.getBit(index));

		container.setBit(false, index);
		Assert.assertFalse(container.getBit(index));
	}

	@Test
	public void testIndexLogicShifting() {
		Long value = 0L;
		Integer index = 0;
		Integer indexLeftShift = 5;
		Integer indexRightShift = 3;

		LongBitContainer container = new LongBitContainer(value);
		container.setBit(true, index);
		container.bitShiftLeft(indexLeftShift);

		Assert.assertTrue(container.getBit(index + indexLeftShift));

		container.bitShiftRight(indexRightShift);
		Assert.assertFalse(container.getBit(index + indexLeftShift));
		Assert.assertTrue(container.getBit(index + indexLeftShift - indexRightShift));
	}

	@Test
	public void testMaskMaking() {
		LongBitContainer container = new LongBitContainer();

		Assert.assertTrue(container.makeMask(2, 5).equals(0b11100L));
		Assert.assertTrue(container.makeMask(4, 8).equals(0b11110000L));

		Assert.assertTrue(container.makeLeftMask(4).equals(0xFFFFFFFFFFFFFFF0L));
		Assert.assertTrue(container.makeRightMask(4).equals(0b00001111L));

		System.out.println("Test: " + Long.toHexString(new Integer(-1).longValue()));

	}

}
