package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link LoginPointerService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginPointerServiceImpl
        implements LoginPointerService {

	private GetterSetter<LoginPointer> getterSetter;
	private TaskRequestSender<LoginPointer> reviewTask;

	public LoginPointerServiceImpl(GetterSetter<LoginPointer> getterSetter,
	        TaskRequestSender<LoginPointer> reviewTask) throws IllegalArgumentException {
		this.setGetterSetter(getterSetter);
		this.setReviewTask(reviewTask);
	}

	public GetterSetter<LoginPointer> getGetterSetter() {
		return this.getterSetter;
	}

	public void setGetterSetter(GetterSetter<LoginPointer> getterSetter) throws IllegalArgumentException {
		if (getterSetter == null) {
			throw new IllegalArgumentException("Encoder cannot be null.");
		}

		this.getterSetter = getterSetter;
	}

	public TaskRequestSender<LoginPointer> getReviewTask() {
		return this.reviewTask;
	}

	public void setReviewTask(TaskRequestSender<LoginPointer> reviewTask) {
		this.reviewTask = reviewTask;
	}

	// MARK: LoginPointerService
	@Override
	public LoginPointer getLoginPointer(ModelKey key) {
		return this.getLoginPointer(key);
	}

	@Override
	public LoginPointer createLoginPointer(ModelKey key,
	                                       LoginPointer template)
	        throws LoginExistsException {
		boolean exists = this.getterSetter.exists(key);

		if (exists) {
			throw new LoginExistsException(key.getName());
		}

		if (template == null) {
			template = new LoginPointer();
		}

		template.setModelKey(key);
		this.getterSetter.save(template, false);

		if (this.reviewTask != null) {
			this.reviewTask.sendTask(template);
		}

		return template;
	}

	@Override
	public LoginPointer getOrCreateLoginPointer(ModelKey key,
	                                            LoginPointer template) {
		LoginPointer pointer = this.getLoginPointer(key);

		if (pointer == null) {
			try {
				pointer = this.createLoginPointer(key, template);
			} catch (LoginExistsException e) {
				throw new IllegalStateException("The pointer somehow existed but was not returned.", e);
			}
		}

		return pointer;
	}

	@Override
	public String toString() {
		return "LoginPointerServiceImpl [getterSetter=" + this.getterSetter + ", reviewTask=" + this.reviewTask + "]";
	}

}
