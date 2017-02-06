package com.dereekb.gae.server.mail.functions.observer;

import java.util.List;

import com.dereekb.gae.server.mail.MailManager;
import com.dereekb.gae.server.mail.MailRequest;
import com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObserver;

public class SendMailObserver<T>
        implements StagedFunctionObserver<T> {

	private SendMailObserverDelegate<T> delegate;
	private MailManager manager;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {
		List<T> objects = handler.getFunctionObjects();
		MailRequest request = delegate.createMailRequestForObjects(objects);
		manager.sendMail(request);
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
