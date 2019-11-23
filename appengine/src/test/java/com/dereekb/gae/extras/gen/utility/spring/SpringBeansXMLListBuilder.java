package com.dereekb.gae.extras.gen.utility.spring;

import java.util.List;

/**
 * Builder for an XML list.
 *
 * @author dereekb
 *
 * @param <T>
 *            return type
 */
public interface SpringBeansXMLListBuilder<T>
        extends SpringBeansXMLUtilBeanBuilderEntity<T> {

	public static final String ROOT_LIST_ELEMENT = "util:list";
	public static final String LIST_ELEMENT = "list";

	public static final String LIST_CLASS_ATTRIBUTE = "list-class";
	public static final String VALUE_CLASS_ATTRIBUTE = "value-type";

	/**
	 * Only allowed on root-level list beans.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown if not a root-level bean.
	 */
	public SpringBeansXMLListBuilder<T> id(String beanId) throws UnsupportedOperationException;

	public SpringBeansXMLListBuilder<T> type(Class<T> type);

	public SpringBeansXMLListBuilder<T> ref(String ref);

	public SpringBeansXMLListBuilder<T> refs(List<String> refs);

	public SpringBeansXMLListBuilder<T> value(String ref);

	public SpringBeansXMLListBuilder<T> values(String... values);

	public SpringBeansXMLListBuilder<T> values(Iterable<String> values);

	public SpringBeansXMLBeanBuilder<SpringBeansXMLListBuilder<T>> bean();

}
