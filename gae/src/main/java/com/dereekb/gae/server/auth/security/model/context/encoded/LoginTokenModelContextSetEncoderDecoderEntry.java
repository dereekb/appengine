package com.dereekb.gae.server.auth.security.model.context.encoded;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderImpl;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * {@link LoginTokenModelContextSetEncoderDecoderImpl} delegate.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextSetEncoderDecoderEntry
        extends IndexCoded, TypedModel {

	/**
	 * Encodes the set to a string.
	 * 
	 * @param typedSet
	 *            {@link LoginTokenTypedModelContextSet}. Never {@code null}.
	 * @return {@link String}.
	 */
	public String encode(LoginTokenTypedModelContextSet typedSet);

	/**
	 * Decodes the input to a list of contexts.
	 * 
	 * @param encodedContext
	 *            {@link String}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<LoginTokenModelContext> decode(String encodedContext);

}
