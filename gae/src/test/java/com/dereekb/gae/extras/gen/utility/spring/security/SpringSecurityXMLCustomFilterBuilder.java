package com.dereekb.gae.extras.gen.utility.spring.security;

import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilderEntity;

/**
 * Custom filter for a {@link SpringSecurityXMLHttpBeanBuilder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            up type
 */
public interface SpringSecurityXMLCustomFilterBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	public SpringSecurityXMLCustomFilterBuilder<T> ref(String string);

	public SpringSecurityXMLCustomFilterBuilder<T> position(String position);

}
