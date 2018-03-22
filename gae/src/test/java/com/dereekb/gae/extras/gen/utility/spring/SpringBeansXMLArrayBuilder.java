package com.dereekb.gae.extras.gen.utility.spring;

/**
 * Builder for an XML array.
 *
 * @author dereekb
 *
 * @param <T>
 *            return type
 *
 * @see SpringBeansXMLListBuilder
 */
public interface SpringBeansXMLArrayBuilder<T>
        extends SpringBeansXMLUtilBeanBuilderEntity<T> {

	public static final String ROOT_ARRAY_ELEMENT = "util:array";
	public static final String ARRAY_ELEMENT = "array";

	public static final String VALUE_CLASS_ATTRIBUTE = "value-type";

	/**
	 * Only allowed on root-level list beans.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown if not a root-level bean.
	 */
	public SpringBeansXMLArrayBuilder<T> id(String beanId) throws UnsupportedOperationException;

	public SpringBeansXMLArrayBuilder<T> type(Class<T> type);

	public SpringBeansXMLArrayBuilder<T> ref(String ref);

	public SpringBeansXMLArrayBuilder<T> value(String ref);

	public SpringBeansXMLBeanBuilder<SpringBeansXMLArrayBuilder<T>> bean();

}
