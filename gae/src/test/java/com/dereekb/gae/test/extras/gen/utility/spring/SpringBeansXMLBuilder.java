package com.dereekb.gae.test.extras.gen.utility.spring;

import com.jamesmurty.utils.XMLBuilder2;

/**
 * {@link XMLBuilder2} wrapper for building Spring configurations.
 *
 * @author dereekb
 *
 */
public interface SpringBeansXMLBuilder
        extends SpringBeansXMLObject {

	/**
	 * Start a new bean element.
	 *
	 * @param id
	 * @return {@link SpringBeansXMLBeanBuilder}. Never {@code null}.
	 */
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> bean(String id);

}
