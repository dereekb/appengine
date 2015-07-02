package com.dereekb.gae.model.extension.request.key;

/**
 * Used to generate a String identifier for the input object.
 *
 * Generated identifiers are unique for each input object. Per the
 * {@link Request} type, there should only be one request, per type, max. This
 * should be taken into account when generating identifiers.
 *
 * @author dereekb
 * @param <T>
 */
public interface RequestKeyBuilder<T> {

	public String requestKeyForObject(T object);

}
