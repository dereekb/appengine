package com.dereekb.gae.utilities.collections.tree.map.builder;

import java.util.List;

import com.google.common.base.Joiner;

/**
 * Basic value that is read in from a {@link MapTreeBuilder}.
 * 
 * @author dereekb
 */
public class MapTreeValue {

	private final String value;
	private final List<String> parameters;

	public MapTreeValue(String value) throws NullPointerException {
		this(value, null);
	}

	public MapTreeValue(String value, List<String> parameters) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("Value cannot be null.");
		}

		this.value = value;
		this.parameters = parameters;
	}

	public static MapTreeValue withValue(String value) {
		return new MapTreeValue(value);
	}

	public String getValue() {
		return value;
	}

	public boolean hasParameters() {
		return (this.parameters != null);
	}

	public List<String> getParameters() {
		return parameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapTreeValue other = (MapTreeValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return value + "(" + this.commaSeparatedParameters() + ")";
	}

	private String commaSeparatedParameters() {
		String parameters = "";

		if (this.parameters != null) {
			Joiner joiner = Joiner.on("\",\"").skipNulls();
			parameters = "\"" + joiner.join(this.parameters) + "\"";
		}

		return parameters;
	}

}
