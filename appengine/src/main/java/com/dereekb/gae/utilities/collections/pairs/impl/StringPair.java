package com.dereekb.gae.utilities.collections.pairs.impl;

/**
 * {@link HandlerPair} with a {@link String} as the key and value.
 *  
 * @author dereekb
 *
 */
public class StringPair extends HandlerPair<String, String> {

	public StringPair(String key, String object) {
		super(key, object);
	}

	@Override
	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.object;
	}

}
