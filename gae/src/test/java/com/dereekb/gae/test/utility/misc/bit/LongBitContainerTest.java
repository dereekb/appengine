package com.dereekb.gae.test.utility.misc.bit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.misc.bit.BitIndex;
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

	@Test
	public void testFocusingOnBits() {
		LongBitContainer container = new LongBitContainer(0b111000);

		Long focusedBits = container.focusBits(3, 6);
		Assert.assertTrue(focusedBits == 0b111);
	}

	@Test
	public void testFocusingOnBitsWithMask() {
		LongBitContainer container = new LongBitContainer(0xFF00);
		Long mask = 0x0F00L;
		Integer start = BitIndex.hexIndex(2); // F00

		Long focusedBits = container.focusBitsWithMask(mask, start);
		Assert.assertTrue(focusedBits == 0xF);
	}

	@Test
	public void testActiveBits() {
		LongBitContainer container = new LongBitContainer(1L);
		Long value = container.getValue();

		Assert.assertTrue(value == 1L);
		Assert.assertTrue(container.getBit(0));
		Assert.assertFalse(container.getBit(32));
		Assert.assertFalse(container.getBit(63));

		List<Byte> activeIndexes = container.getAllActiveIndexes();
		Assert.assertTrue(activeIndexes.size() == 1);

		Byte b = 0;
		Assert.assertTrue(activeIndexes.contains(b));
	}

	@Test
	public void testOutOfRangeRead() {
		LongBitContainer container = new LongBitContainer(1L);

		try {
			container.getBit(LongBitContainer.MAX_INDEX + 1);
			Assert.fail();
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			container.getBit(LongBitContainer.MIN_INDEX - 1);
			Assert.fail();
		} catch (IndexOutOfBoundsException e) {

		}
	}

}
