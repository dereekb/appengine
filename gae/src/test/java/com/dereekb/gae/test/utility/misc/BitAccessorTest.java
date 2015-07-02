package com.dereekb.gae.test.utility.misc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.dereekb.gae.utilities.misc.BitAccessor;

/**
 * {@link BitAccessor} tests.
 *
 * @author dereekb
 *
 */
@RunWith(JUnit4.class)
public class BitAccessorTest {

	@Test
	public void testIndexReadWrite() {
		Long value = 0L;
		Integer index = 0;

		BitAccessor accessor = new BitAccessor(value);
		accessor.writeBit(true, index);
		Assert.assertTrue(accessor.readBit(index));

		accessor.writeBit(false, index);
		Assert.assertFalse(accessor.readBit(index));
	}

	@Test
	public void testIndexLogicShifting() {
		Long value = 0L;
		Integer index = 0;
		Integer indexLeftShift = 5;
		Integer indexRightShift = 3;

		BitAccessor accessor = new BitAccessor(value);
		accessor.writeBit(true, index);
		accessor.shiftLeft(indexLeftShift);

		Assert.assertTrue(accessor.readBit(index + indexLeftShift));

		accessor.shiftRight(indexRightShift);
		Assert.assertFalse(accessor.readBit(index + indexLeftShift));
		Assert.assertTrue(accessor.readBit(index + indexLeftShift - indexRightShift));
	}

	@Test
	public void testByteValueReading() {
		Long value = BitAccessor.BYTE_MASK;
		Integer index = 0;
		Integer shiftLeft = 1;

		BitAccessor accessor = new BitAccessor(value);
		Long readValue = accessor.readByteValue(index);

		Assert.assertTrue(readValue.equals(value));

		accessor.shiftLeft(BitAccessor.BITS_IN_BYTE * shiftLeft);
		Long focusedReadValue = accessor.focusByteValue(shiftLeft);

		// Still equivalent since we just focused on it.
		Assert.assertTrue(focusedReadValue.equals(value));

		// Add another byte to it.
		accessor = accessor.or(value);

		// TODO: Continue..?
	}

	@Test
	public void testMaskMaking() {
		Long leftBitMask = BitAccessor.makeLeftBitMask(3);
		Assert.assertTrue(leftBitMask == 0xfffffffffffffff8L);

		Long leftOctalMask = BitAccessor.makeLeftHexMask(3);
		Assert.assertTrue(leftOctalMask == 0xfffffffffffff000L);

		Long leftByteMask = BitAccessor.makeLeftByteMask(3);
		Assert.assertTrue(leftByteMask == 0xffffffffff000000L);

		Long rightBitMask = BitAccessor.makeRightBitMask(3);
		Assert.assertTrue(rightBitMask == 7L); // 0111

		Long rightOctalMask = BitAccessor.makeRightHexMask(3);
		Assert.assertTrue(rightOctalMask == 0xfffL);

		Long rightByteMask = BitAccessor.makeRightByteMask(3);
		Assert.assertTrue(rightByteMask == 0xffffffL);
	}

	@Test
	public void testRawValueWriting() {
		Long initialValue = 0L;
		Long value = BitAccessor.BYTE_MASK;
		Integer index = 0;

		BitAccessor accessor = new BitAccessor(initialValue);

		for (int i = 0; i < BitAccessor.BYTES_IN_LONG; i += 1) {
			accessor.writeValue(value, i);
		}

		Assert.assertTrue(accessor.focusByteValue(index) == BitAccessor.BYTE_MASK);
		Assert.assertTrue(accessor.getValue() == 0xffffffffffffffffL);
	}

	@Test
	public void testRawValueReading() {
		Long initialValue = BitAccessor.ALL_ONE_BITS;
		BitAccessor accessor = new BitAccessor(initialValue);

		Long mask = 0xFFFF0000FFFF0000L;
		Long expected = 0xFFFF0000FFFFL;

		Long value = accessor.focusValue(mask, 4);

		//System.out.println("Value: " + value + " " + Long.toHexString(value));
		//System.out.println("Expected: " + expected + " " + Long.toHexString(expected));

		Assert.assertTrue(value.equals(expected));
	}

	@Test
	public void testIndexWriteRange() {
		Long value = 0L;
		BitAccessor accessor = new BitAccessor(value);

		try {
			for (int i = 0; i < BitAccessor.MAX_BIT_INDEX; i += 1) {
				accessor.writeBit(true, i);
				Assert.assertTrue(accessor.readBit(i));
			}
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
