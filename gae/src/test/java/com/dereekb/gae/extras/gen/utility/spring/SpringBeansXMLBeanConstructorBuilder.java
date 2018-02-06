package com.dereekb.gae.extras.gen.utility.spring;

import com.jamesmurty.utils.XMLBuilder2;

/**
 * Constructor for a {@link SpringbeansXMLBean}.
 *
 * @author dereekb
 *
 * @param <T>
 *            up type
 */
public interface SpringBeansXMLBeanConstructorBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	public XMLBuilder2 nextArgBuilder();

	public SpringBeansXMLBeanConstructorBuilder<T> ref(String ref);

	public SpringBeansXMLBeanConstructorBuilder<T> value(String value);

	public SpringBeansXMLMapBuilder<SpringBeansXMLBeanConstructorBuilder<T>> map();

	public SpringBeansXMLListBuilder<SpringBeansXMLBeanConstructorBuilder<T>> list();

}
