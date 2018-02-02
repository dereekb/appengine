package com.dereekb.gae.extras.gen.utility.spring;

/**
 * Builder for an XML list.
 *
 * @author dereekb
 *
 * @param <T>
 *            return type
 */
public interface SpringBeansXMLListBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	/**
	 * Only allowed on root-level list beans.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown if not a root-level bean.
	 */
	public SpringBeansXMLListBuilder<T> id(String beanId) throws UnsupportedOperationException;

	public SpringBeansXMLListBuilder<T> type(Class<T> type);

	public SpringBeansXMLListBuilder<T> ref(String ref);

	public SpringBeansXMLListBuilder<T> value(String ref);

	// TODO: Bean, etc.

}
