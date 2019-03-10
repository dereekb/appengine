package com.dereekb.gae.test.utility.misc.bit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.misc.bit.BitIndex;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

public class LongBitContainerTest {

	@Test
	public void testIndexReadWrite() {
		Long value = 0L;
		Integer index = 0;

		LongBitContainer container = new LongBitContainer(value);
		container.setBit(true, index);
		assertTrue(container.getBit(index));

		container.setBit(false, index);
		assertFalse(container.getBit(index));
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

		assertTrue(container.getBit(index + indexLeftShift));

		container.bitShiftRight(indexRightShift);
		assertFalse(container.getBit(index + indexLeftShift));
		assertTrue(container.getBit(index + indexLeftShift - indexRightShift));
	}

	@Test
	public void testMaskMaking() {
		LongBitContainer container = new LongBitContainer();

		assertTrue(container.makeMask(2, 5).equals(0b11100L));
		assertTrue(container.makeMask(4, 8).equals(0b11110000L));

		assertTrue(container.makeLeftMask(4).equals(0xFFFFFFFFFFFFFFF0L));
		assertTrue(container.makeRightMask(4).equals(0b00001111L));

		System.out.println("Test: " + Long.toHexString(new Integer(-1).longValue()));
	}

	@Test
	public void testFocusingOnBits() {
		LongBitContainer container = new LongBitContainer(0b111000);

		Long focusedBits = container.focusBits(3, 6);
		assertTrue(focusedBits == 0b111);
	}

	@Test
	public void testFocusingOnBitsWithMask() {
		LongBitContainer container = new LongBitContainer(0xFF00);
		Long mask = 0x0F00L;
		Integer start = BitIndex.hexIndex(2); // F00

		Long focusedBits = container.focusBitsWithMask(mask, start);
		assertTrue(focusedBits == 0xF);
	}

	@Test
	public void testActiveBits() {
		LongBitContainer container = new LongBitContainer(1L);
		Long value = container.getValue();

		assertTrue(value == 1L);
		assertTrue(container.getBit(0));
		assertFalse(container.getBit(32));
		assertFalse(container.getBit(63));

		List<Byte> activeIndexes = container.getAllActiveIndexes();
		assertTrue(activeIndexes.size() == 1);

		Byte b = 0;
		assertTrue(activeIndexes.contains(b));
	}

	@Test
	public void testOutOfRangeRead() {
		LongBitContainer container = new LongBitContainer(1L);

		try {
			container.getBit(LongBitContainer.MAX_INDEX + 1);
			fail();
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			container.getBit(LongBitContainer.MIN_INDEX - 1);
			fail();
		} catch (IndexOutOfBoundsException e) {

		}
	}

}
