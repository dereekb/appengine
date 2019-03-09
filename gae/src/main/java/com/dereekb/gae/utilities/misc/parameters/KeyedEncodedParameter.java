package com.dereekb.gae.utilities.misc.parameters;

import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * {@link EncodedParameter} implementation that is also keyed by a string
 * value.
 * 
 * @author dereekb
 *
 */
public interface KeyedEncodedParameter
        extends EncodedParameter, AlwaysKeyed<String> {

	/**
	 * Returns a key for this parameter.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getParameterKey();

}
