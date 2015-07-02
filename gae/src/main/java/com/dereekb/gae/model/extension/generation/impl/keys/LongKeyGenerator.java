package com.dereekb.gae.model.extension.generation.impl.keys;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;

/**
 * Generates random Long keys.
 * 
 * @author dereekb
 * 
 */
public class LongKeyGenerator extends AbstractGenerator<Long> {

	@Override
	public Long generate() {
		return this.randomPositiveLong();
	}

}
