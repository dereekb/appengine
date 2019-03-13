package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.AbstractMutableLinkData;

/**
 * Optional assertion delegate used by {@link AbstractMutableLinkData} to check a model/link can be modified.
 * 
 * @author dereekb
 *
 * @param <T> model type
 */
public interface MutableLinkDataAssertionDelegate<T> {

	/**
	 * Asserts that the model change is allowed.
	 * 
	 * @param model Model. Never {@code null}.
	 * @param change {@link MutableLinkChange}. Never {@code null}.
	 * 
	 * @throws MutableLinkChangeException thrown if the change should not be allowed for any reason.
	 */
	public void assertChangeIsAllowed(T model, MutableLinkChange change) throws MutableLinkChangeException;
	
}
