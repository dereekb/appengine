package com.dereekb.gae.utilities.data;


public enum NumberRepresentation {

	BINARY(2),

	OCTARY(8),

	DECIMAL(10),

	HEXADECIMAL(16);

	public final int radix;

	private NumberRepresentation(int radix) {
		this.radix = radix;
	}

	public int getRadix() {
		return this.radix;
	}

}
