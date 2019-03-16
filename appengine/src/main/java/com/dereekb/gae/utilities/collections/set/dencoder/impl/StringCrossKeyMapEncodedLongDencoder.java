package com.dereekb.gae.utilities.collections.set.dencoder.impl;

import java.util.Map;

/**
 * {@link CrossKeyMapEncodedLongDencoder} for {@link String} keys.
 * 
 * @author dereekb
 *
 */
public class StringCrossKeyMapEncodedLongDencoder extends CrossKeyMapEncodedLongDencoder<String> {

	public StringCrossKeyMapEncodedLongDencoder(Map<Integer, String> map) throws IllegalArgumentException {
		super(map);
	}

}
