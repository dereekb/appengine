package com.dereekb.gae.utilities.misc;

/**
 * Accessor that toggles the bit values of an number.
 *
 * @author dereekb
 * @Deprecated Rename and call it BitAccessor.
 */
@Deprecated
public class ByteAccessor {

	int value;

	public ByteAccessor() {
		this.value = 0;
	}

	public ByteAccessor(int daysByte) {
		this.value = daysByte;
	}

	public void setByte(boolean on,
	                    int index) {
		if (on) {
			this.value |= (1 << index); // set a bit to 1
		} else {
			this.value &= ~(1 << index); // set a bit to 0
		}
	}

	public boolean readByte(int index) {
		int result = (this.value & (1 << index)); // read
		return (result != 0);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("ByteAccessor: Value = {");

		for (int i = 7; i >= 0; i--) {
			boolean value = this.readByte(i);
			string.append(((value) ? "1" : "0"));
		}

		string.append("}");
		return string.toString();
	}
}