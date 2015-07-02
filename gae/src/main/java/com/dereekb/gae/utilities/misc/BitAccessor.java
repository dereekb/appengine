package com.dereekb.gae.utilities.misc;

/**
 * Used for accessing and changing bits in a {@link long} value.
 *
 * @author dereekb
 *
 */
public final class BitAccessor {

	/**
	 * Used for masking a specific byte.
	 */
	public static final Long BYTE_MASK = 0xFFL;

	public static final Integer BITS_IN_BYTE = 8;
	public static final Integer BYTES_IN_LONG = 8;
	public static final Integer MAX_BIT_INDEX = BYTES_IN_LONG * BITS_IN_BYTE;

	private Long value;

	public BitAccessor() {
		this.value = 0L;
	}

	public BitAccessor(Integer value) {
		this.setValue(value);
	}

	public BitAccessor(Long value) {
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

	public void setValue(Integer value) {
		if (value == null) {
			this.value = 0L;
		} else {
			this.value = value.longValue();
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
		BitAccessor accessor = new BitAccessor(value);
		accessor.shiftBytesLeft(index);

		this.value = this.orValue(accessor.value);
	}

	// MARK: Byte Reading
	/**
	 * Reads a byte from the index 0 to 7.
	 *
	 * @param index
	 *            Byte index (0-7) to check.
	 * @return value of the read byte.
	 */
	public long readByteValue(int index) throws IndexOutOfBoundsException {
		long mask = (BYTE_MASK << BITS_IN_BYTE * index);
		return this.andValue(mask);
	}

	/**
	 * Focuses on the byte value at the specified index.
	 *
	 * @param index
	 *            Byte index (0-7) to check.
	 * @return Value ranging from 0 to 0xFF.
	 * @throws IndexOutOfBoundsException
	 */
	public long focusByteValue(int index) throws IndexOutOfBoundsException {
		long value = this.readByteValue(index);
		return (value >> (BITS_IN_BYTE * index));
	}

	// MARK: Shifting
	public void shiftLeft() {
		this.shiftLeft(1);
	}

	public void shiftLeft(int count) {
		this.value = this.value << count;
	}

	public void shiftBytesLeft() {
		this.shiftLeft(BITS_IN_BYTE);
	}

	public void shiftBytesLeft(int count) {
		this.shiftLeft(BITS_IN_BYTE * count);
	}

	public void shiftRight() {
		this.shiftRight(1);
	}

	public void shiftRight(int count) {
		this.value = this.value >> count;
	}

	public void shiftBytesRight() {
		this.shiftRight(BITS_IN_BYTE);
	}

	public void shiftBytesRight(int count) {
		this.shiftRight(BITS_IN_BYTE * count);
	}

	//MARK: Logic
	public long andValue(long value) {
		return this.value & value;
	}

	public BitAccessor and(long value) {
		return new BitAccessor(this.andValue(value));
	}

	public long orValue(long value) {
		return this.value | value;
	}

	public BitAccessor or(long value) {
		return new BitAccessor(this.orValue(value));
	}

	public long xorValue(long value) {
		return this.value ^ value;
	}

	public BitAccessor xor(long value) {
		return new BitAccessor(this.xorValue(value));
	}

	public long notValue(long value) {
		return ~this.value;
	}

	public BitAccessor not(long value) {
		return new BitAccessor(this.notValue(value));
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
		return "BitAccessor [value=" + this.value + "]";
	}

}
