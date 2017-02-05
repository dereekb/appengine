package com.dereekb.gae.utilities.collections.set;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Case insensitive {@link String} {@link Set} implemented with {@link TreeSet}.
 *
 * @author dereekb
 *
 */
public class CaseInsensitiveSet extends TreeSet<String> {

	private static final long serialVersionUID = 1L;

	public CaseInsensitiveSet() {
		super(String.CASE_INSENSITIVE_ORDER);
	}

	public CaseInsensitiveSet(Collection<String> s) {
		this();

		if (s != null) {
			this.addAll(s);
		}
	}

	@Override
	public String toString() {
		return "CaseInsensitiveSet [toString()=" + super.toString() + "]";
	}

}
