package com.dereekb.gae.extras.gen.utility.spring;

/**
 * Utility bean entity.
 *
 * @author dereekb
 *
 * @param <T>
 *            entity type
 */
public abstract interface SpringBeansXMLUtilBeanBuilderEntity<T>
        extends SpringBeansXMLBuilderEntity<T> {

	public boolean isRootBean();

}
