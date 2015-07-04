package com.dereekb.gae.utilities.misc;

import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * Used for accessing and changing bits in a {@link long} value.
 *
 * @author dereekb
 * @deprecated Replaced by {@link LongBitContainer}
 */
@Deprecated
public final class LongBitAccessor {

	/**
	 * Used for masking a specific byte.
	 */
	public static final Long BYTE_MASK = 0xFFL;
	public static final Long ALL_ONE_BITS = (-1L);
	public static final Long ALL_ZERO_BITS = 0L;

	public static final Integer BITS_IN_HEX = 4; // 0xF = 1 half-byte
	public static final Integer BITS_IN_BYTE = 8; // 0xFF = 1 Byte
	public static final Integer BYTES_IN_LONG = 8;
	public static final Integer MAX_BIT_INDEX = BYTES_IN_LONG * BITS_IN_BYTE;

	private Long value;

	public LongBitAccessor() {
		this.value = 0L;
	}

	public LongBitAccessor(Integer value) {
		this.setValue(value);
	}

	public LongBitAccessor(Long value) {
		this.setValue(value);
	}

	public Long getValue() {
		return this.value;
	}

	public Integer getIntegerValue() {
		return this.value.intValue();
	}

	public void setValue(Long value) {
		if (value == null) {
			value = 0L;
		}

		this.value = value;
	}

	public void setValue(String hex) throws NumberFormatException {
		if (hex == null) {
			this.value = 0L;
		} else {
			this.value = Long.parseLong(hex, 16);
		}
	}

	/**
	 * Sets the value to match the passed {@link Integer} value.
	 *
	 * @param value
	 * @see #setBits(Integer) if you want to just set the bit ordering.
	 */
	public void setValue(Integer value) {
		if (value == null) {
			this.value = 0L;
		} else {
			this.value = value.longValue();
		}
	}

	/**
	 * Sets the value using bits from the passed integer.
	 *
	 * @param value
	 */
	public void setBits(Integer value) {
		if (value == null) {
			this.value = 0L;
		} else {
			this.value = value.longValue() & 0xFFFFFFFFL;
		}
	}

	// MARK: Functions
	public boolean readBit(int index) {
		long result = this.readBitValue(index);
		return (result != 0);
	}

	public long readBitValue(int index) {
		long value = (this.value & (1 << index)); // read
		return value;
	}

	public void writeBit(boolean on,
	                     int index) throws IndexOutOfBoundsException {
		if (on) {
			/*
			 * Does an OR with this value and a new value with all values set to
			 * 0 except for the one at the specified index.
			 */
			this.value |= (1 << index); // set a bit to 1
		} else {
			/*
			 * Does an AND with this value with a new value with all values set
			 * to 1 except for the index.
			 */
			this.value &= ~(1 << index); // set a bit to 0
		}
	}

	/**
	 * Similar to {@link #or(long)}, except shifts the input value over to the
	 * target index before applying. This is useful for merging multiple small
	 * values together without having to perform shifting ahead of time.
	 *
	 * @param value
	 *            Value to write with.
	 * @param index
	 *            Byte index from 0-7.
	 * @throws IndexOutOfBoundsException
	 */
	public void writeValue(long value,
	                       int index) throws IndexOutOfBoundsException {
		LongBitAccessor accessor = new LongBitAccessor(value);
		accessor.shiftBytesLeft(index);

		// TODO: This won't work for cases where the "starting" value is already
		// 1. It just performs an OR, instead of writing a blank slate.

		this.value = this.orValue(accessor.value);
	}

	// MARK: Byte Reading
	/**
	 * Reads a byte from the index 0 to 7.
	 *
	 * @param byteIndex
	 *            Byte index (0-7) to check.
	 * @return value of the read byte.
	 */
	public long readByteValue(int byteIndex) throws IndexOutOfBoundsException {
		long mask = (BYTE_MASK << (BITS_IN_BYTE * byteIndex));
		return this.andValue(mask);
	}

	/**
	 * Focuses on the byte value at the specified index.
	 *
	 * @param byteIndex
	 *            Byte index (0-7) to check.
	 * @return Value ranging from 0 to 0xFF.
	 * @throws IndexOutOfBoundsException
	 */
	public long focusByteValue(int byteIndex) throws IndexOutOfBoundsException {
		long value = this.readByteValue(byteIndex);
		long focus = (value >> (BITS_IN_BYTE * byteIndex));
		return focus & makeRightByteMask(1);
	}

	/**
	 * Focuses on the value specified by the mask, and shifted by the byte
	 * index.
	 *
	 * For example, if the mask 0xFFFF0000 is applied with a shift of 4, then
	 * the values at 0xXXXX will be returned as such.
	 *
	 * @param mask
	 *            Value mask to apply to the value.
	 * @param hexIndex
	 *            The 'start' hex index. Will shift the read value to start at
	 *            this index.
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public long focusValue(long mask,
	                       int hexIndex) throws IndexOutOfBoundsException {
		LongBitAccessor and = this.and(mask);
		and.shiftHexRight(hexIndex);
		// and.maskHexRight(hexIndex); //TODO: Change to independent function.
		long focusMask = makeRightHexMask(MAX_BIT_INDEX - hexIndex);
		return and.getValue() & focusMask;
	}

	// MARK: Shifting
	public void shiftLeft() {
		this.shiftLeft(1);
	}

	/**
	 * Shifts the bits left by the given count.
	 *
	 * Bits shifted right are set to 0.
	 *
	 * @param count
	 */
	public void shiftLeft(int count) {
		this.value = this.value << count;
	}

	public void shiftBytesLeft() {
		this.shiftLeft(BITS_IN_BYTE);
	}

	public void shiftHexLeft(int hexCount) {
		this.shiftLeft(BITS_IN_HEX * hexCount);
	}

	public void shiftBytesLeft(int byteCount) {
		this.shiftLeft(BITS_IN_BYTE * byteCount);
	}

	public void shiftRight() {
		this.shiftRight(1);
	}

	/**
	 * Shifts the bits right by the given count.
	 *
	 * Bits shifted right are set to 1.
	 *
	 * @param count
	 */
	public void shiftRight(int count) {
		this.value = this.value >> count;
	}

	public void shiftBytesRight() {
		this.shiftRight(BITS_IN_BYTE);
	}

	public void shiftHexRight(int hexCount) {
		this.shiftRight(BITS_IN_HEX * hexCount);
	}

	public void shiftBytesRight(int byteCount) {
		this.shiftRight(BITS_IN_BYTE * byteCount);
	}

	// MARK: Masking
	public void addIntegerMask() {

	}

	/**
	 * Creates a new mask with all bits after (to the left) the index in the ON
	 * position.
	 *
	 * Example: makeLeftBitMask(3) -> 11111000
	 *
	 * @param index
	 */
	public static long makeLeftBitMask(int index) {
		return (ALL_ONE_BITS << index);
	}

	/**
	 * Creates a new mask with all bits after (to the left) the hex index in the
	 * ON position.
	 *
	 * Example: makeLeftHexMask(3) -> fffff000
	 *
	 * @param hexIndex
	 */
	public static long makeLeftHexMask(int hexIndex) {
		return makeLeftBitMask(hexIndex * 4);
	}

	/**
	 * Creates a new mask with all bits after (to the left) the byte index in
	 * the ON position.
	 *
	 * Example: makeLeftByteMask(3) -> ff000000
	 *
	 * @param byteIndex
	 */
	public static long makeLeftByteMask(int byteIndex) {
		return makeLeftBitMask(byteIndex * BITS_IN_BYTE);
	}

	/**
	 * Creates a new mask with all bits after (to the left) the index in the ON
	 * position.
	 *
	 * Example: makeRightBitMask(3) -> 111
	 *
	 * @param index
	 */
	public static long makeRightBitMask(int index) {
		return ~makeLeftBitMask(index);
	}

	/**
	 * Creates a new mask with all bits after (to the left) the hex index in the
	 * ON position.
	 *
	 * Example: makeRightHexMask(3) -> fff
	 *
	 * @param hexIndex
	 */
	public static long makeRightHexMask(int hexIndex) {
		return makeRightBitMask(hexIndex * BITS_IN_HEX);
	}

	/**
	 * Creates a new mask with all bits after (to the left) the byte index in
	 * the ON position.
	 *
	 * Example: makeRightMask(3) -> ffffff
	 *
	 * @param byteIndex
	 */
	public static long makeRightByteMask(int byteIndex) {
		return makeRightBitMask(byteIndex * BITS_IN_BYTE);
	}

	//MARK: Logic
	public long andValue(long value) {
		return this.value & value;
	}

	public LongBitAccessor and(long value) {
		return new LongBitAccessor(this.andValue(value));
	}

	public long orValue(long value) {
		return this.value | value;
	}

	public LongBitAccessor or(long value) {
		return new LongBitAccessor(this.orValue(value));
	}

	public long xorValue(long value) {
		return this.value ^ value;
	}

	public LongBitAccessor xor(long value) {
		return new LongBitAccessor(this.xorValue(value));
	}

	public long notValue(long value) {
		return ~this.value;
	}

	public LongBitAccessor not(long value) {
		return new LongBitAccessor(this.notValue(value));
	}

	// MARK: Strings
	public String toBitString() {
		return this.toBitString(BYTES_IN_LONG);
	}

	public String toBitString(int bytes) {
		StringBuilder string = new StringBuilder();

		for (int i = bytes * BITS_IN_BYTE; i >= 0; i--) {
			boolean value = this.readBit(i);
			string.append(((value) ? "1" : "0"));
		}

		return string.toString();
	}

	public String toHexString() {
		return Long.toHexString(this.value);
	}

	@Override
	public String toString() {
		return "LongBitAccessor [value=" + this.value + "]";
	}

}
