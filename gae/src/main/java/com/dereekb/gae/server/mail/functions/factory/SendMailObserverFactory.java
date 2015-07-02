package com.dereekb.gae.server.mail.functions.factory;

import com.dereekb.gae.server.mail.MailManager;
import com.dereekb.gae.server.mail.functions.observer.SendMailObserver;
import com.dereekb.gae.server.mail.functions.observer.SendMailObserverDelegate;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.observer.AbstractStagedFunctionObserverFactory;

public class SendMailObserverFactory<T> extends AbstractStagedFunctionObserverFactory<SendMailObserver<T>, T> {

	private SendMailObserverDelegate<T> delegate;
	private MailManager manager;

	public SendMailObserverFactory() {
		super(StagedFunctionStage.FINISHED);
	}

	@Override
	public SendMailObserver<T> generateObserver() {
		SendMailObserver<T> observer = new SendMailObserver<T>();

		if (manager != null) {
			observer.setManager(manager);
		}

		if (delegate != null) {
			observer.setDelegate(delegate);
		}

		return observer;
	}

	public SendMailObserverDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(SendMailObserverDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public MailManager getManager() {
		return manager;
	}

	public void setManager(MailManager manager) {
		this.manager = manager;
	}

}
