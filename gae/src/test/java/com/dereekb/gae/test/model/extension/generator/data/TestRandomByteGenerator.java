package com.dereekb.gae.test.model.extension.generator.data;

import java.util.Random;

public class TestRandomByteGenerator
        implements TestByteDataGenerator {

	private Random random = new Random();
	private Integer size = 0xF00;

	@Override
	public byte[] generateBytes() {
		byte[] array = new byte[size];
		random.nextBytes(array);
		return array;
	}

}
