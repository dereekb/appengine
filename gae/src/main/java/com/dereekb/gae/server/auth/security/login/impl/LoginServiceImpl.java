package com.dereekb.gae.server.auth.security.login.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.exception.LinkException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.LoginService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * Abstract {@link LoginService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginServiceImpl
        implements LoginService {

	private String format;
	private GetterSetter<LoginPointer> getterSetter;
	private TaskRequestSender<LoginPointer> reviewTask;
	private LoginRegisterService registerService;

	public LoginServiceImpl(String format, GetterSetter<LoginPointer> getterSetter) throws IllegalArgumentException {
		this(format, getterSetter, null);
	}

	public LoginServiceImpl(String format, GetterSetter<LoginPointer> getterSetter, LoginRegisterService registerService)
	        throws IllegalArgumentException {
		this(format, getterSetter, null, registerService);
	}

	public LoginServiceImpl(String format,
	        GetterSetter<LoginPointer> getterSetter,
	        TaskRequestSender<LoginPointer> reviewTask,
	        LoginRegisterService registerService) throws IllegalArgumentException {
		this.setFormat(format);
		this.setGetterSetter(getterSetter);
		this.setReviewTask(reviewTask);
		this.setRegisterService(registerService);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
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

	public LoginRegisterService getRegisterService() {
		return this.registerService;
	}

	public void setRegisterService(LoginRegisterService registerService) {
		this.registerService = registerService;
	}

	// MARK: LoginService
	@Override
	public LoginPointer getLogin(String username) throws LoginUnavailableException {
		LoginPointer pointer = this.loadLogin(username);

		if (pointer == null) {
			throw new LoginUnavailableException(username);
		}

		return pointer;
	}

	protected LoginPointer createLogin(String username,
	                                   LoginPointer template) throws LoginExistsException {
		ModelKey key = this.getLoginPointerKey(username);
		boolean exists = this.getterSetter.exists(key);

		if (exists) {
			throw new LoginExistsException(username);
		}

		template.setModelKey(key);
		this.getterSetter.save(template, false);

		if (this.reviewTask != null) {
			this.reviewTask.sendTask(template);
		}

		return template;
	}

	protected LoginPointer loadLogin(String username) {
		ModelKey key = this.getLoginPointerKey(username);
		return this.getterSetter.get(key);
	}

	protected ModelKey getLoginPointerKey(String username) {
		String id = this.getLoginIdForUsername(username);
		return new ModelKey(id);
	}

	protected String getLoginIdForUsername(String username) {
		return String.format(this.format, username.toLowerCase());
	}

	// MARK: LoginRegisterService
	@Override
	public Login register(LoginPointer pointer) throws LoginExistsException {
		return this.registerService.register(pointer);
	}

	@Override
	public void registerLogins(ModelKey loginKey,
	                           Set<String> loginPointers) throws LinkException {
		this.registerService.registerLogins(loginKey, loginPointers);
	}

}
