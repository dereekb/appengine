package com.dereekb.gae.extras.gen.utility.spring;

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

	public SpringBeansXMLBeanConstructorBuilder<T> ref(String ref);

	public SpringBeansXMLBeanConstructorBuilder<T> value(String value);

}
