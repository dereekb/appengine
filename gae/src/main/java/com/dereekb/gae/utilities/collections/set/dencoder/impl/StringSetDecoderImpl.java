package com.dereekb.gae.utilities.collections.set.dencoder.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.set.dencoder.StringSetDecoder;

/**
 * Used for decoding using a map.
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 */
public class StringSetDecoderImpl<T>
        implements StringSetDecoder<T> {

	public static final String DEFAULT_DECODER = ",";

	private Map<String, T> map;

	private String decoder = DEFAULT_DECODER;

	public StringSetDecoderImpl(Map<String, T> map) throws IllegalArgumentException {
		this.setMap(map);
	}

	public Map<String, T> getMap() {
		return this.map;
	}

	public void setMap(Map<String, T> map) throws IllegalArgumentException {
		if (map == null) {
			throw new IllegalArgumentException();
		}

		this.map = map;
	}

	// MARK: Set Decoder
	@Override
	public Set<T> decode(String encodedValues) {
		String[] decoded = encodedValues.split(this.decoder);
		List<String> encodedValuesList = ListUtility.toList(decoded);
		return this.decode(encodedValuesList);
	}

	@Override
	public Set<T> decode(Iterable<? extends String> encodedValues) {
		Set<T> values = new HashSet<T>();

		for (String encodedValue : encodedValues) {
			T decoded = this.map.get(encodedValue);

			if (decoded != null) {
				values.add(decoded);
			}
		}

		return values;
	}

	@Override
	public String toString() {
		return "SetDecoderImpl [map=" + this.map + ", decoder=" + this.decoder + "]";
	}

}
