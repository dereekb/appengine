package com.dereekb.gae.utilities.collections.pairs;

public class StringPair extends HandlerPair<String, String> {

	public StringPair(String key, String object) {
		super(key, object);
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.object;
	}

}
