package com.dereekb.gae.utilities.misc.bit.impl;

import com.dereekb.gae.utilities.misc.bit.BitContainer;
import com.dereekb.gae.utilities.misc.bit.BitFocus;
import com.dereekb.gae.utilities.misc.bit.BitMasker;
import com.dereekb.gae.utilities.misc.bit.BitString;

/**
 * {@link BitContainer} implementation for {@link Long}.
 *
 * @author dereekb
 *
 */
public class LongBitContainer
        implements BitContainer<Long>, BitMasker<Long>, BitFocus<Long>, BitString {

	public static final Long ALL_ONE_BITS = (-1L);
	public static final Long ALL_ZERO_BITS = 0L;

	public static final Integer BITS_IN_LONG = 64;

	public static final Long INTEGER_BIT_MASK = 0xFFFFFFFFL;

	private Long value;

	public LongBitContainer() {
		this.value = 0L;
	}

	public LongBitContainer(Long value) {
		this.setValue(value);
	}

	public LongBitContainer(Integer bits) {
		this.setIntegerBits(bits);
	}

	// MARK: BitContainer
	public void clear() {
		this.setValue(0L);
	}

	@Override
	public Integer getBitLength() {
		return BITS_IN_LONG;
	}

	@Override
	public boolean getBit(int index) throws IndexOutOfBoundsException {
		long result = this.readBitValue(index);
		return (result != 0);
	}

	@Override
	public void setBit(boolean on,
	                   int index) throws IndexOutOfBoundsException {
		if (on) {
			this.value |= (1 << index);
		} else {
			this.value &= ~(1 << index);
		}
	}

	@Override
	public Long getValue() {
		return this.value;
	}

	@Override
	public void setValue(Long value) throws IllegalArgumentException {
		if (value == null) {
			value = 0L;
		}

		this.value = value;
	}

	// MARK: Bit Logic
	@Override
	public void bitShiftLeft(int bits) {
		this.value = this.value << bits;
	}

	@Override
	public void bitShiftRight(int bits) {
		this.value = this.value >> bits;
	}

	@Override
	public Long and(Long value) {
		return this.value & value;
	}

	@Override
	public Long or(Long value) {
		return this.value | value;
	}

	@Override
	public Long xor(Long value) {
		return this.value ^ value;
	}

	@Override
	public Long not(Long value) {
		return ~this.value;
	}

	@Override
    public void applyAnd(Long value) {
		this.value = this.and(value);
    }

	@Override
	public void applyOr(Long value) {
		this.value = this.or(value);
	}

	@Override
	public void applyXor(Long value) {
		this.value = this.xor(value);
	}

	@Override
	public void applyNot(Long value) {
		this.value = this.not(value);
	}

	// MARK: Mask
	@Override
	public Long makeMask(int start,
	                     int end) throws IndexOutOfBoundsException {
		int length = end - start;
		return (~(ALL_ONE_BITS << length) << start);
	}

	@Override
	public Long makeLeftMask(int index) throws IndexOutOfBoundsException {
		return (ALL_ONE_BITS << index);
	}

	@Override
	public Long makeRightMask(int index) throws IndexOutOfBoundsException {
		return ~this.makeLeftMask(index);
	}

	// MARK: Bit String
	@Override
	public String getBitString() {
		return Long.toBinaryString(this.value);
	}

	public String getBitString(int bits) {
		StringBuilder string = new StringBuilder();

		for (int i = bits; i >= 0; i--) {
			boolean value = this.getBit(i);
			string.append(((value) ? "1" : "0"));
		}

		return string.toString();
	}

	@Override
	public String getHexString() {
		return Long.toHexString(this.value);
	}

	// MARK: Bit Focus
	@Override
	public Long focusBits(int start,
	                      int end) {
		long mask = this.makeMask(start, end);
		return this.focusBitsWithMask(mask, start);
	}

	@Override
	public Long focusBitsWithMask(Long mask,
	                              int start) {
		long value = this.value & mask;
		return (value >> start);
	}

	// MARK: Internal
	public Long readBitValue(int index) {
		Long value = (this.value & (1 << index)); // read
		return value;
	}

	public Integer getIntegerBits() {
		return this.value.intValue();
	}

	public void setIntegerBits(Integer value) {
		if (value == null) {
			this.value = 0L;
		} else {
			this.value = bitsFromInteger(value);
		}
	}

	public void setHexBits(String hex) throws NumberFormatException {
		if (hex == null) {
			this.value = 0L;
		} else {
			this.value = Long.parseLong(hex, 16);
		}
	}

	// MARK: Other
	public static Long bitsFromInteger(Integer value) {
		return value.longValue() & INTEGER_BIT_MASK;
	}

	@Override
	public String toString() {
		return "LongBitContainer [value=" + this.value + "]";
	}

}
