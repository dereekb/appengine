package com.dereekb.gae.utilities.security.pem.impl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.dereekb.gae.utilities.security.pem.PrivateKeyProvider;

/**
 * {@link PrivateKeyProvider} that reads a PEM from a file.
 *
 * @author dereekb
 *
 */
public class FilePrivateKeyProviderImpl extends AbstractPrivateKeyProvider {

	private String filePath;

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

	// MARK: AbstractPrivateKeyProvider
	@Override
	protected Reader getPrivateKeyReader() throws IOException, RuntimeException {
		return new FileReader(this.filePath);
	}

	@Override
	public String toString() {
		return "PrivateKeyProviderImpl [filePath=" + this.filePath + "]";
	}

}
