package com.dereekb.gae.utilities.collections.map;

import java.util.Map;

/**
 * Extension of {@link MapReader} for Strings only.
 * <p>
 * Allows reading additional values and types and automatic conversions.
 *
 * @author dereekb
 *
 */
public class StringMapReader extends MapReader<String> {

	public StringMapReader(Map<String, String> map) throws IllegalArgumentException {
		super(map);
	}

	public StringMapReader(Map<String, String> map, String keyFormat) throws IllegalArgumentException {
		super(map, keyFormat);
	}

	public Integer getInteger(String key) {
		String value = this.get(key);
		Integer integer = null;

		if (value != null) {
			try {
				integer = new Integer(value);
			} catch (Exception e) {

			}
		}

		return integer;
	}

	public Long getLong(String key) {
		String value = this.get(key);
		Long longValue = null;

		if (value != null) {
			try {
				longValue = Long.valueOf(value);
			} catch (Exception e) {

			}
		}

		return longValue;
	}

	public Boolean getBoolean(String key) {
		String value = this.get(key);
		Boolean boolValue = null;

		if (value != null) {
			boolValue = new Boolean(value);
		}

		return boolValue;
	}

}
