package com.dereekb.gae.extras.gen.utility.spring;

/**
 * {@link SpringBeansXMLBuilder} entity
 *
 * @author dereekb
 *
 * @param <T>
 *            parent type
 */
public interface SpringBeansXMLBuilderEntity<T>
        extends XMLBuilderObject {

	/**
	 * Completes the entity and goes up to the parent.
	 *
	 * @return Model. Never {@code null}.
	 */
	public T up();

	/**
	 * Completes the entity and goes back to the root builder.
	 *
	 * @return {@link SpringBeansXMLBuilder}. Never {@code null}.
	 */
	public SpringBeansXMLBuilder done();

}
