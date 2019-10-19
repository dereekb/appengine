package com.dereekb.gae.extras.gen.utility.spring;

/**
 * Property for a {@link SpringbeansXMLBean}.
 *
 * @author dereekb
 *
 * @param <T>
 *            up type
 */
public interface SpringBeansXMLBeanPropertyBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	public SpringBeansXMLBeanPropertyBuilder<T> name(String name);

	public SpringBeansXMLBeanPropertyBuilder<T> value(String value);

	public SpringBeansXMLBeanPropertyBuilder<T> value(Enum<?> value);

	public SpringBeansXMLBeanPropertyBuilder<T> ref(String ref);

	/**
	 * Build a new bean.
	 */
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBeanPropertyBuilder<T>> bean();

}
