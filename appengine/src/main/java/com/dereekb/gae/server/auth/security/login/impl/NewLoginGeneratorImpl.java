package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.NewLoginGenerator;
import com.dereekb.gae.server.auth.security.login.NewLoginGeneratorDelegate;
import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link NewLoginGenerator} implementation.
 *
 * @author dereekb
 *
 */
public class NewLoginGeneratorImpl
        implements NewLoginGenerator, NewLoginGeneratorDelegate {

	private Storer<Login> loginStorer;
	private TaskRequestSender<Login> reviewTask;

	private NewLoginGeneratorDelegate delegate = this;

	public NewLoginGeneratorImpl(Storer<Login> loginStorer, TaskRequestSender<Login> reviewTask) {
		this(loginStorer, reviewTask, null);
	}

	public NewLoginGeneratorImpl(Storer<Login> loginStorer,
	        TaskRequestSender<Login> reviewTask,
	        NewLoginGeneratorDelegate delegate) {
		this.setLoginStorer(loginStorer);
		this.setReviewTask(reviewTask);
		this.setDelegate(delegate);
	}

	public Storer<Login> getLoginStorer() {
		return this.loginStorer;
	}

	public void setLoginStorer(Storer<Login> loginStorer) throws IllegalArgumentException {
		if (loginStorer == null) {
			throw new IllegalArgumentException("LoginStorer cannot be null.");
		}

		this.loginStorer = loginStorer;
	}

	public TaskRequestSender<Login> getReviewTask() {
		return this.reviewTask;
	}

	public void setReviewTask(TaskRequestSender<Login> reviewTask) throws IllegalArgumentException {
		this.reviewTask = reviewTask;
	}

	public NewLoginGeneratorDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(NewLoginGeneratorDelegate delegate) {
		if (delegate == null) {
			delegate = this;
		}

		this.delegate = delegate;
	}

	// MARK: NewLoginGenerator
	@Override
	public Login makeLogin(LoginPointer pointer) {
		Login login = this.delegate.makeNewLogin(pointer);
		this.loginStorer.store(login);

		if (this.reviewTask != null) {
			this.reviewTask.sendTask(login);
		}

		return login;
	}

	// MARK: NewLoginGeneratorDelegate
	@Override
	public Login makeNewLogin(LoginPointer pointer) {
		return new Login();
	}

	@Override
	public String toString() {
		return "NewLoginGeneratorImpl [loginStorer=" + this.loginStorer + ", reviewTask=" + this.reviewTask
		        + ", delegate=" + this.delegate + "]";
	}

}
