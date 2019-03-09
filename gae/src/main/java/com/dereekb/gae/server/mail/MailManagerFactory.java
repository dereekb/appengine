package com.dereekb.gae.server.mail;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * Factory for creating {@link MailManager} instances.
 * 
 * @author dereekb
 */
public class MailManagerFactory
        implements Factory<MailManager> {

	private MailSource source;
	private MailManagerDelegate delegate;

	@Override
	public MailManager make() throws FactoryMakeFailureException {
		MailManager manager = new MailManager();

		if (source != null) {
			manager.setSource(source);
		}

		if (delegate != null) {
			manager.setDelegate(delegate);
		}

		return manager;
	}

	public MailSource getSource() {
		return source;
	}

	public void setSource(MailSource source) {
		this.source = source;
	}

	public MailManagerDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(MailManagerDelegate delegate) {
		this.delegate = delegate;
	}

}
