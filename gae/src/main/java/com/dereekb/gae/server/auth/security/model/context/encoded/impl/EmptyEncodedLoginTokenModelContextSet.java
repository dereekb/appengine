package com.dereekb.gae.server.auth.security.model.context.encoded.impl;

import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.exception.UnavailableEncodedModelContextException;

/**
 * {@link EncodedLoginTokenModelContextSet} that is alway empty.
 * 
 * @author dereekb
 *
 */
public class EmptyEncodedLoginTokenModelContextSet
        implements EncodedLoginTokenModelContextSet {

	public static final EmptyEncodedLoginTokenModelContextSet SINGLETON = new EmptyEncodedLoginTokenModelContextSet();

	private EmptyEncodedLoginTokenModelContextSet() {
		super();
	}

	public static EmptyEncodedLoginTokenModelContextSet make() {
		return SINGLETON;
	}

	// MARK: EncodedLoginTokenModelContextSet
	@Override
	public Set<Integer> getEncodedModelContextTypes() {
		return Collections.emptySet();
	}

	@Override
	public String getEncodedModelTypeContext(Integer encodedType) throws UnavailableEncodedModelContextException {
		throw new UnavailableEncodedModelContextException();
	}

	@Override
	public String toString() {
		return "EmptyEncodedLoginTokenModelContextSet []";
	}

}
