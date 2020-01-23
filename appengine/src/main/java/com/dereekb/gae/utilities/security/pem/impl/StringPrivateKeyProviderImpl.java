package com.dereekb.gae.utilities.security.pem.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.dereekb.gae.utilities.security.pem.PrivateKeyProvider;

/**
 * {@link PrivateKeyProvider} that reads a private key from a string.
 *
 * @author dereekb
 *
 */
public class StringPrivateKeyProviderImpl extends AbstractPrivateKeyProvider {

	private String privateKeyString;

	public StringPrivateKeyProviderImpl(String privateKeyString) {
		super();
		this.setPrivateKeyString(privateKeyString);
	}

	public String getPrivateKeyString() {
		return this.privateKeyString;
	}

	public void setPrivateKeyString(String privateKeyString) {
		if (privateKeyString == null) {
			throw new IllegalArgumentException("privateKeyString cannot be null.");
		}

		this.privateKeyString = privateKeyString;
	}

	// MARK: AbstractPrivateKeyProvider
	@Override
	protected Reader getPrivateKeyReader() throws IOException, RuntimeException {
		return new StringReader(this.privateKeyString);
	}

	@Override
	public String toString() {
		return "StringPrivateKeyProviderImpl []";
	}

}
