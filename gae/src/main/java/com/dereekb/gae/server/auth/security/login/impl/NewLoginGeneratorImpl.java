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

	private Storer<Login> loginSaver;
	private TaskRequestSender<Login> reviewTask;
	private NewLoginGeneratorDelegate delegate = this;

	public NewLoginGeneratorImpl(Storer<Login> loginSaver, TaskRequestSender<Login> reviewTask) {
		this(loginSaver, reviewTask, null);
	}

	public NewLoginGeneratorImpl(Storer<Login> loginSaver,
	        TaskRequestSender<Login> reviewTask,
	        NewLoginGeneratorDelegate delegate) {
		this.setLoginSaver(loginSaver);
		this.setReviewTask(reviewTask);
		this.setDelegate(delegate);
	}

	public Storer<Login> getLoginSaver() {
		return this.loginSaver;
	}

	public void setLoginSaver(Storer<Login> loginSaver) throws IllegalArgumentException {
		if (loginSaver == null) {
			throw new IllegalArgumentException("LoginSaver cannot be null.");
		}

		this.loginSaver = loginSaver;
	}

	public TaskRequestSender<Login> getReviewTask() {
		return this.reviewTask;
	}

	public void setReviewTask(TaskRequestSender<Login> reviewTask) throws IllegalArgumentException {
		if (reviewTask == null) {
			throw new IllegalArgumentException("ReviewTask cannot be null.");
		}

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
		this.loginSaver.store(login);

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
		return "NewLoginGeneratorImpl [loginSaver=" + this.loginSaver + ", reviewTask=" + this.reviewTask
		        + ", delegate=" + this.delegate + "]";
	}

}
