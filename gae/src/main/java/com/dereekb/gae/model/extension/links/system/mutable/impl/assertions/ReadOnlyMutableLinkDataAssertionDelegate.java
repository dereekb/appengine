package com.dereekb.gae.model.extension.links.system.mutable.impl.assertions;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkDataAssertionDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.ReadOnlyLinkChangeException;

/**
 * {@link MutableLinkDataAssertionDelegate} implementation for read-only links.
 * 
 * 
 * @author dereekb
 *
 */
public class ReadOnlyMutableLinkDataAssertionDelegate<T> implements MutableLinkDataAssertionDelegate<T> {

	public static final ReadOnlyMutableLinkDataAssertionDelegate<Object> SINGLETON = new ReadOnlyMutableLinkDataAssertionDelegate<Object>();

	@SuppressWarnings("unchecked")
	public static <T> ReadOnlyMutableLinkDataAssertionDelegate<T> make() {
		return (ReadOnlyMutableLinkDataAssertionDelegate<T>) SINGLETON;
	}

	// MARK: MutableLinkDataAssertionDelegate
	@Override
	public void assertChangeIsAllowed(T model,
	                                  MutableLinkChange change)
	        throws MutableLinkChangeException {
		throw new ReadOnlyLinkChangeException(change);
	}

	@Override
	public String toString() {
		return "ReadOnlyMutableLinkDataAssertionDelegate []";
	}

}
