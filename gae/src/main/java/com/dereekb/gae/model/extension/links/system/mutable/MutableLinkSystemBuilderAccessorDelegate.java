package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkSystemBuilderImpl;

/**
 * Delegate for {@link MutableLinkSystemBuilderImpl}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface MutableLinkSystemBuilderAccessorDelegate<T>
        extends MutableLinkAccessorFactory<T>, TypedLinkSystemComponent {

	/**
	 * Returns the read service.
	 * 
	 * @return {@code ReadService}. Never {@code null}.
	 */
	public ReadService<T> getReadService();

}
