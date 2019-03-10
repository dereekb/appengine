package com.dereekb.gae.test.utility.misc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.misc.LongBitAccessor;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * {@link LongBitAccessor} tests.
 *
 * @author dereekb
 *
 * @deprecated The LongBitAccessor has been replaced by {@link LongBitContainer}.
 */
@Deprecated
public class BitAccessorTest {

	@Test
	public void testIndexReadWrite() {
		Long value = 0L;
		Integer index = 0;

		LongBitAccessor accessor = new LongBitAccessor(value);
		accessor.writeBit(true, index);
		assertTrue(accessor.readBit(index));

		accessor.writeBit(false, index);
		assertFalse(accessor.readBit(index));
	}

	@Test
	public void testIndexLogicShifting() {
		Long value = 0L;
		Integer index = 0;
		Integer indexLeftShift = 5;
		Integer indexRightShift = 3;

		LongBitAccessor accessor = new LongBitAccessor(value);
		accessor.writeBit(true, index);
		accessor.shiftLeft(indexLeftShift);

		assertTrue(accessor.readBit(index + indexLeftShift));

		accessor.shiftRight(indexRightShift);
		assertFalse(accessor.readBit(index + indexLeftShift));
		assertTrue(accessor.readBit(index + indexLeftShift - indexRightShift));
	}

	@Test
	public void testByteValueReading() {
		Long value = LongBitAccessor.BYTE_MASK;
		Integer index = 0;
		Integer shiftLeft = 1;

		LongBitAccessor accessor = new LongBitAccessor(value);
		Long readValue = accessor.readByteValue(index);

		assertTrue(readValue.equals(value));

		accessor.shiftLeft(LongBitAccessor.BITS_IN_BYTE * shiftLeft);
		Long focusedReadValue = accessor.focusByteValue(shiftLeft);

		// Still equivalent since we just focused on it.
		assertTrue(focusedReadValue.equals(value));

		// Add another byte to it.
		accessor = accessor.or(value);

	}

	@Test
	public void testMaskMaking() {
		Long leftBitMask = LongBitAccessor.makeLeftBitMask(3);
		assertTrue(leftBitMask == 0xfffffffffffffff8L);

		Long leftOctalMask = LongBitAccessor.makeLeftHexMask(3);
		assertTrue(leftOctalMask == 0xfffffffffffff000L);

		Long leftByteMask = LongBitAccessor.makeLeftByteMask(3);
		assertTrue(leftByteMask == 0xffffffffff000000L);

		Long rightBitMask = LongBitAccessor.makeRightBitMask(3);
		assertTrue(rightBitMask == 7L); // 0111

		Long rightOctalMask = LongBitAccessor.makeRightHexMask(3);
		assertTrue(rightOctalMask == 0xfffL);

		Long rightByteMask = LongBitAccessor.makeRightByteMask(3);
		assertTrue(rightByteMask == 0xffffffL);
	}

	@Test
	public void testRawValueWriting() {
		Long initialValue = 0L;
		Long value = LongBitAccessor.BYTE_MASK;
		Integer index = 0;

		LongBitAccessor accessor = new LongBitAccessor(initialValue);

		for (int i = 0; i < LongBitAccessor.BYTES_IN_LONG; i += 1) {
			accessor.writeValue(value, i);
		}

		assertTrue(accessor.focusByteValue(index) == LongBitAccessor.BYTE_MASK);
		assertTrue(accessor.getValue() == 0xffffffffffffffffL);
	}

	@Test
	public void testFocusValue() {
		Long initialValue = LongBitAccessor.ALL_ONE_BITS;
		LongBitAccessor accessor = new LongBitAccessor(initialValue);

		Long mask = 0xFFFF0000FFFF0000L;
		Long expected = 0xFFFF0000FFFFL;

		Long value = accessor.focusValue(mask, 4);

		//System.out.println("Value: " + value + " " + Long.toHexString(value));
		//System.out.println("Expected: " + expected + " " + Long.toHexString(expected));

		assertTrue(value.equals(expected));
	}

	@Test
	public void testFocusSmallValue() {
		Long initialValue = 0xD01111L;
		LongBitAccessor accessor = new LongBitAccessor(initialValue);

		Long mask = 0xFF0000L;
		Long expected = 0xD0L;

		Long value = accessor.focusValue(mask, 4);

		/*
		System.out.println("Value: " + value + " " + Long.toHexString(value));
		System.out.println("Expected: " + expected + " " + Long.toHexString(expected));
		 */

		assertTrue(value.equals(expected));
	}

	@Test
	public void testIndexWriteRange() {
		Long value = 0L;
		LongBitAccessor accessor = new LongBitAccessor(value);

		try {
			for (int i = 0; i < LongBitAccessor.MAX_BIT_INDEX; i += 1) {
				accessor.writeBit(true, i);
				assertTrue(accessor.readBit(i));
			}
		} catch (Exception e) {
			fail();
		}
	}

	// Bit Conversion Tests
	// @Test
	public void testIntegerValueBitConversion() {

		Integer initialValue = 0xFFFF555F;
		Long expected = 0x00000000FFFF555FL;

		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putInt(initialValue);
		buffer.putInt(0);
		buffer.rewind();
		Long longValue = buffer.getLong();
		longValue = longValue >> 32;
		longValue = longValue & 0xFFFFFFFFL;

		System.out.println(Integer.toHexString(initialValue));
		System.out.println(Long.toHexString(expected));
		System.out.println(Long.toHexString(longValue));

		assertTrue(expected.equals(longValue));

	}

	// @Test
	public void testToIntegerValueBitConversion() {

		Integer initialValue = 0xF123F555;
		Long expected = 0x00000000F123F555L;

		Long longValue = initialValue.longValue();
		longValue = longValue & 0xFFFFFFFFL;

		System.out.println(Integer.toHexString(initialValue));
		System.out.println(Long.toHexString(expected));
		System.out.println(Long.toHexString(longValue));

		assertTrue(expected.equals(longValue));

	}

	@Test
	public void testFromIntegerValueBitConversion() {

		Long iValue = 0x0000000054321FFFL;
		Integer expectedValue = 0x54321FFF;

		Integer intValue = iValue.intValue();
		// longValue = longValue & 0xFFFFFFFFL;

		System.out.println(Long.toHexString(iValue));
		System.out.println(Integer.toHexString(expectedValue));
		System.out.println(Integer.toHexString(intValue));

		assertTrue(expectedValue.equals(intValue));

	}
}
