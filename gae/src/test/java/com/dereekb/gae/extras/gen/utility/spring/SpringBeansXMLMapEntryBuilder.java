package com.dereekb.gae.extras.gen.utility.spring;

/**
 * {@link SpringBeansXMLBuilderEntity} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            parent type
 */
public interface SpringBeansXMLMapEntryBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	public static final String ELEMENT = "entry";

	public static final String KEY_ATTRIBUTE = "key";

	public static final String KEY_REF_ATTRIBUTE = "key-ref";
	public static final String VALUE_REF_ATTRIBUTE = "value-ref";

	public SpringBeansXMLMapEntryBuilder<T> key(String key);

	public SpringBeansXMLMapEntryBuilder<T> keyRef(String beanRef);
	public SpringBeansXMLMapEntryBuilder<T> valueRef(String beanRef);


}
