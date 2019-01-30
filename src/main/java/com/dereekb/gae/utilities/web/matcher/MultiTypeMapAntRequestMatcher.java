package com.dereekb.gae.utilities.web.matcher;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapAndSet;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link MultiTypeAntRequestMatcher} extension that uses a
 * {@link HashMapWithSet} to compare all variables to matchable values.
 * 
 * Example usage:
 * 
 * The URL <i>/{x}/{y}/{z}/foo</i> is checked against set values in the map
 * corresponding to keys "x", "y", and "z".
 * 
 * @author dereekb
 *
 */
public class MultiTypeMapAntRequestMatcher extends MultiTypeAntRequestMatcher {

	private HashMapWithSet<String, String> typesMap;

	public MultiTypeMapAntRequestMatcher(String pattern,
	        Map<? extends String, ? extends Collection<? extends String>> typesMap) {
		this(pattern, typesMap, DEFAULT_CASE_SENSITIVE);
	}

	public MultiTypeMapAntRequestMatcher(String pattern,
	        Map<? extends String, ? extends Collection<? extends String>> typesMap,
	        boolean caseSensitive) {
		super(pattern, caseSensitive);
		this.setTypesMap(typesMap);
	}

	public HashMapWithSet<String, String> getTypesMap() {
		return this.typesMap;
	}

	public void setTypesMap(Map<? extends String, ? extends Collection<? extends String>> typesMap) {
		if (typesMap == null) {
			throw new IllegalArgumentException("typesMap cannot be null.");
		}

		if (this.caseSensitive) {
			this.typesMap = new HashMapWithSet<String, String>(typesMap);
		} else {
			this.typesMap = new CaseInsensitiveMapAndSet(typesMap);
		}
	}

	// MARK: Override
	@Override
	protected boolean matchesVariables(Map<String, String> pathVariables) {

		for (Entry<String, Set<String>> entry : this.typesMap.entrySet()) {
			String variable = entry.getKey();
			String value = pathVariables.get(variable);

			if (entry.getValue().contains(value) == false) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "MultiTypeMapAntRequestMatcher [typesMap=" + this.typesMap + ", caseSensitive=" + this.caseSensitive
		        + ", getPattern()=" + this.getPattern() + "]";
	}

}
