package com.dereekb.gae.utilities.security.pem.impl;

import java.io.IOException;
import java.io.Reader;
import java.security.PrivateKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import com.dereekb.gae.utilities.security.pem.PrivateKeyProvider;

/**
 * {@link PrivateKeyProvider} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractPrivateKeyProvider
        implements PrivateKeyProvider {

	private transient PrivateKey privateKey;

	public AbstractPrivateKeyProvider() {
		super();
	}

	// MARK: PrivateKeyProvider
	@Override
	public PrivateKey getPrivateKey() throws RuntimeException {
		if (this.privateKey == null) {
			this.privateKey = this.readPrivateKey();
		}

		return this.privateKey;
	}

	private final PrivateKey readPrivateKey() throws RuntimeException {
		Reader reader;

		try {
			reader = this.getPrivateKeyReader();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}

		return readPrivateKey(reader);
	}

	protected abstract Reader getPrivateKeyReader() throws IOException, RuntimeException;

	public static PrivateKey readPrivateKey(Reader reader) throws RuntimeException {
		try {
			PEMParser pemParser = new PEMParser(reader);

			PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
			pemParser.close();

			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			PrivateKey pKey = converter.getPrivateKey(object);

			return pKey;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
