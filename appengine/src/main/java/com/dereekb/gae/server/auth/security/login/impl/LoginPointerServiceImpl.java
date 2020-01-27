package com.dereekb.gae.server.auth.security.login.impl;

import java.util.List;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQueryInitializer.ObjectifyLoginPointerQuery;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.exception.LoginDisabledException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link LoginPointerService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginPointerServiceImpl
        implements LoginPointerService {

	private ObjectifyRegistry<LoginPointer> registry;
	private TaskRequestSender<LoginPointer> reviewTask;

	public LoginPointerServiceImpl(ObjectifyRegistry<LoginPointer> registry, TaskRequestSender<LoginPointer> reviewTask)
	        throws IllegalArgumentException {
		this.setRegistry(registry);
		this.setReviewTask(reviewTask);
	}

	public ObjectifyRegistry<LoginPointer> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<LoginPointer> registry) {
		if (registry == null) {
			throw new IllegalArgumentException("registry cannot be null.");
		}

		this.registry = registry;
	}

	public TaskRequestSender<LoginPointer> getReviewTask() {
		return this.reviewTask;
	}

	public void setReviewTask(TaskRequestSender<LoginPointer> reviewTask) {
		this.reviewTask = reviewTask;
	}

	// MARK: LoginPointerService
	@Override
	public LoginPointer getLoginPointer(ModelKey key) throws LoginDisabledException {
		LoginPointer pointer = this.registry.get(key);

		if (pointer != null && pointer.getDisabled() == true) {
			throw new LoginDisabledException(pointer);
		}

		return pointer;
	}

	@Override
	public LoginPointer getOrCreateLoginPointer(ModelKey key,
	                                            LoginPointer template)
	        throws LoginDisabledException {
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
	public LoginPointer createLoginPointer(ModelKey key,
	                                       LoginPointer template)
	        throws LoginExistsException {
		boolean exists = this.registry.exists(key);

		if (exists) {
			throw new LoginExistsException(key.getName());
		}

		if (template == null) {
			template = new LoginPointer();
		}

		template.setModelKey(key);
		this.registry.store(template);

		if (this.reviewTask != null) {
			this.reviewTask.sendTask(template);
		}

		return template;
	}

	@Override
	public List<LoginPointer> findWithEmail(String email) throws LoginDisabledException, IllegalArgumentException {

		if (StringUtility.isEmptyString(email)) {
			throw new IllegalArgumentException("Email cannot be null or empty.");
		}

		ObjectifyLoginPointerQuery query = new ObjectifyLoginPointerQuery();
		query.setEmail(email);

		ObjectifyQueryRequestBuilder<LoginPointer> builder = this.registry.makeQuery();

		query.configure(builder);

		return builder.buildExecutableQuery().queryModels();
	}

	@Override
	public LoginPointer findWithEmail(LoginPointerType type,
	                                  String email)
	        throws LoginDisabledException,
	            IllegalArgumentException {

		if (type == null || StringUtility.isEmptyString(email)) {
			throw new IllegalArgumentException("Both type and email cannot be null.");
		}

		ObjectifyLoginPointerQuery query = new ObjectifyLoginPointerQuery();
		query.setEmail(email);
		query.setType(type);

		ObjectifyQueryRequestBuilder<LoginPointer> builder = this.registry.makeQuery();

		query.configure(builder);

		List<LoginPointer> pointers = builder.buildExecutableQuery().queryModels();

		if (pointers.isEmpty()) {
			return null;
		} else {
			LoginPointer pointer = pointers.get(0);

			if (pointer.getDisabled()) {
				throw new LoginDisabledException(pointer);
			} else {
				return pointer;
			}
		}
	}

	@Override
	public LoginPointer changeVerified(ModelKey key,
	                                   boolean verified)
	        throws UnavailableModelException {
		LoginPointer pointer = this.registry.get(key);

		if (pointer == null) {
			throw new UnavailableModelException(key);
		}

		pointer.setVerified(verified);
		this.registry.updateAsync(pointer);

		return pointer;
	}

	@Override
	public String toString() {
		return "LoginPointerServiceImpl [registry=" + this.registry + ", reviewTask=" + this.reviewTask + "]";
	}

}