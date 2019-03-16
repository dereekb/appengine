package com.dereekb.gae.client.api.service.request.impl;

import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;

/**
 * {@link ClientRequestUrl} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientRequestUrlImpl
        implements ClientRequestUrl {

	private SimplePath path;

	public ClientRequestUrlImpl(String path) {
		this(new SimplePathImpl(path));
	}

	public ClientRequestUrlImpl(SimplePath path) {
		this.setPath(path);
	}

	public SimplePath getPath() {
		return this.path;
	}

	public void setPath(SimplePath path) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null.");
		}

		this.path = path;
	}

	// MARK: ClientRequestUrl
	@Override
	public SimplePath getRelativeUrlPath() {
		return this.path;
	}

	@Override
	public String toString() {
		return "ClientRequestUrlImpl [path=" + this.path + "]";
	}

}
