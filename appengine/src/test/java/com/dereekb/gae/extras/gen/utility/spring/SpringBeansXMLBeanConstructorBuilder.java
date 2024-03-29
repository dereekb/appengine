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

	public SpringBeansXMLBeanConstructorBuilder<T> value(Enum<?> value);

	public SpringBeansXMLBeanConstructorBuilder<T> nullArg();

	public SpringBeansXMLBeanConstructorBuilder<T> enumBean(Enum<?> value);

	public SpringBeansXMLBeanBuilder<SpringBeansXMLBeanConstructorBuilder<T>> bean();

	public SpringBeansXMLMapBuilder<SpringBeansXMLBeanConstructorBuilder<T>> map();

	public SpringBeansXMLListBuilder<SpringBeansXMLBeanConstructorBuilder<T>> list();

	public SpringBeansXMLArrayBuilder<SpringBeansXMLBeanConstructorBuilder<T>> array();

}
