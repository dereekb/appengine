package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.NewLoginGenerator;
import com.dereekb.gae.server.auth.security.login.NewLoginGeneratorDelegate;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link NewLoginGenerator} implementation.
 *
 * @author dereekb
 *
 */
public class NewLoginGeneratorImpl
        implements NewLoginGenerator, NewLoginGeneratorDelegate {

	private Setter<Login> loginSetter;
	private TaskRequestSender<Login> reviewTask;
	private NewLoginGeneratorDelegate delegate = this;

	public NewLoginGeneratorImpl(Setter<Login> loginSetter, TaskRequestSender<Login> reviewTask) {
		this(loginSetter, reviewTask, null);
	}

	public NewLoginGeneratorImpl(Setter<Login> loginSetter,
	        TaskRequestSender<Login> reviewTask,
	        NewLoginGeneratorDelegate delegate) {
		this.setLoginSetter(loginSetter);
		this.setReviewTask(reviewTask);
		this.setDelegate(delegate);
	}

	public Setter<Login> getLoginSetter() {
		return this.loginSetter;
	}

	public void setLoginSetter(Setter<Login> loginSetter) {
		this.loginSetter = loginSetter;
	}

	public TaskRequestSender<Login> getReviewTask() {
		return this.reviewTask;
	}

	public void setReviewTask(TaskRequestSender<Login> reviewTask) {
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
		this.loginSetter.save(login, false);

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
		return "NewLoginGeneratorImpl [loginSetter=" + this.loginSetter + ", reviewTask=" + this.reviewTask
		        + ", delegate=" + this.delegate + "]";
	}

}
