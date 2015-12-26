package com.dereekb.gae.model.extension.read.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Exception for when a requested types is unavailable.
 *
 * @author dereekb
 */
public class UnavailableTypesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected final Set<String> types;

	public UnavailableTypesException(String type) {
		this(SingleItem.withValue(type));
	}

	public UnavailableTypesException(Collection<String> types) {
		this.types = new HashSet<String>(types);
	}

	public Set<String> getTypes() {
		return this.types;
	}

	@Override
	public String toString() {
		return "UnavailableTypesException [types=" + this.types + "]";
	}

}
