package com.dereekb.gae.model.extension.generation;

import java.util.List;

import com.dereekb.gae.model.extension.generation.impl.GeneratorArgImpl;

public class GeneratorUtility {

	public static <T> List<T> generate(int count,
	                                   Generator<T> generator) {
		return generator.generate(count, new GeneratorArgImpl());
	}

}
