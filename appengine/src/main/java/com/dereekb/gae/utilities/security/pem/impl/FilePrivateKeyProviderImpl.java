package com.dereekb.gae.utilities.security.pem.impl;

import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import com.dereekb.gae.utilities.security.pem.PrivateKeyProvider;

/**
 * {@link PrivateKeyProvider} that reads a PEM from a file.
 *
 * @author dereekb
 *
 */
public class FilePrivateKeyProviderImpl
        implements PrivateKeyProvider {

	private String filePath;

	private transient PrivateKey privateKey;

	public FilePrivateKeyProviderImpl(String absoluteFilePath) {
		super();
		this.setFilePath(absoluteFilePath);
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		if (filePath == null) {
			throw new IllegalArgumentException("filePath cannot be null.");
		}

		this.filePath = filePath;
	}

	// MARK: PrivateKeyProvider
	@Override
	public PrivateKey getPrivateKey() throws RuntimeException {
		if (this.privateKey == null) {
			this.privateKey = this.readPrivateKey();
		}

		return this.privateKey;
	}

	private PrivateKey readPrivateKey() throws RuntimeException {
		try {
			PEMParser pemParser = new PEMParser(new FileReader(this.filePath));

			PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
			pemParser.close();

			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			PrivateKey pKey = converter.getPrivateKey(object);

			return pKey;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return "PrivateKeyProviderImpl [filePath=" + this.filePath + "]";
	}

}
