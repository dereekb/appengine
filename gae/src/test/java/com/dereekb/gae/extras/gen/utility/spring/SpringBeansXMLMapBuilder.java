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
	public static final String VALUE_TYPE_ATTRIBUTE = "value-type";

	/**
	 * Only allowed on root-level list beans.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown if not a root-level bean.
	 */
	public SpringBeansXMLMapBuilder<T> id(String beanId) throws UnsupportedOperationException;

	public SpringBeansXMLMapBuilder<T> keyType(Class<?> type);

	public SpringBeansXMLMapBuilder<T> valueType(Class<?> type);

	public SpringBeansXMLMapEntryBuilder<SpringBeansXMLMapBuilder<T>> entry(String key);

	public SpringBeansXMLMapEntryBuilder<SpringBeansXMLMapBuilder<T>> entry(String key,
	                                                                        boolean ref);

	// Utility
	public SpringBeansXMLMapBuilder<T> keyValueRefEntry(String key,
	                                                    String valueRef);

	public SpringBeansXMLMapBuilder<T> keyRefValueRefEntry(String keyRef,
	                                                       String valueRef);

}
