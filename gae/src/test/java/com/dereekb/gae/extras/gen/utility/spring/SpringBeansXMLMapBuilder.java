package com.dereekb.gae.extras.gen.utility.spring;

/**
 * Builder for an XML list.
 *
 * @author dereekb
 *
 * @param <T>
 *            return type
 */
public interface SpringBeansXMLMapBuilder<T>
        extends SpringBeansXMLUtilBeanBuilderEntity<T> {

	public static final String ROOT_LIST_ELEMENT = "util:map";
	public static final String LIST_ELEMENT = "map";

	public static final String KEY_TYPE_ATTRIBUTE = "key-type";

	/**
	 * Only allowed on root-level list beans.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown if not a root-level bean.
	 */
	public SpringBeansXMLMapBuilder<T> id(String beanId) throws UnsupportedOperationException;

	public SpringBeansXMLMapBuilder<T> keyType(Class<?> type);

	public SpringBeansXMLMapEntryBuilder<SpringBeansXMLMapBuilder<T>> entry(String key);

	// Utility
	public SpringBeansXMLMapBuilder<T> keyValueRefEntry(String key,
	                                                    String valueRef);

}
