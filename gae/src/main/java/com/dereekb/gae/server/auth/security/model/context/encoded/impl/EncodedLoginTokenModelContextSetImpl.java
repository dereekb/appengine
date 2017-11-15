package com.dereekb.gae.server.auth.security.model.context.encoded.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.exception.UnavailableEncodedModelContextException;

/**
 * {@link EncodedLoginTokenModelContextSet} implementation.
 * 
 * @author dereekb
 *
 */
public class EncodedLoginTokenModelContextSetImpl
        implements EncodedLoginTokenModelContextSet {

	private Map<Integer, String> map;

	public EncodedLoginTokenModelContextSetImpl() {
		this(new HashMap<Integer, String>());
	}

	public EncodedLoginTokenModelContextSetImpl(Map<Integer, String> map) {
		super();
		this.setMap(map);
	}

	public Map<Integer, String> getMap() {
		return this.map;
	}

	public void setMap(Map<Integer, String> map) {
		if (map == null) {
			throw new IllegalArgumentException("map cannot be null.");
		}

		this.map = map;
	}

	// MARK: EncodedLoginTokenModelContextSet
	@Override
	public Set<Integer> getEncodedModelContextTypes() {
		return this.map.keySet();
	}

	@Override
	public String getEncodedModelTypeContext(Integer encodedType) throws UnavailableEncodedModelContextException {
		String context = this.map.get(encodedType);

		if (context == null) {
			throw new UnavailableEncodedModelContextException();
		}

		return context;
	}

	@Override
	public String toString() {
		return "EncodedLoginTokenModelContextSetImpl [map=" + this.map + "]";
	}

}
