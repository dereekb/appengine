package com.dereekb.gae.server.auth.security.ownership.task;

import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Abstract {@link IterableTask} for security. Generally used with a
 * {@link AbstractSetModelKeyTask}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractSecuritySetModelKeyTask<T, D extends LoginTokenUserDetails<? extends LoginToken>>
        implements IterableTask<T> {

	private boolean failOnNoSecurity = false;

	public AbstractSecuritySetModelKeyTask() {
		super();
	}

	public AbstractSecuritySetModelKeyTask(boolean failOnNoSecurity) {
		super();
		this.setFailOnNoSecurity(failOnNoSecurity);
	}

	public boolean isFailOnNoSecurity() {
		return this.failOnNoSecurity;
	}

	public void setFailOnNoSecurity(boolean failOnNoSecurity) {
		this.failOnNoSecurity = failOnNoSecurity;
	}

	// MARK: IterableTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		ModelKey securityKey = this.getSecurityModelKey();

		if (securityKey != null) {
			IterableTask<T> task = this.makeSecurityTask(securityKey);
			task.doTask(input);
		}
	}

	// MARK: Internal
	private ModelKey getSecurityModelKey() throws UnsupportedOperationException {
		ModelKey modelKey = null;

		try {
			D details = this.getUserDetails();
			modelKey = this.getSecurityModelKeyFromDetails(details);
		} catch (NoSecurityContextException e) {
			if (this.failOnNoSecurity) {
				throw new UnsupportedOperationException();
			}
		}

		return modelKey;
	}

	protected ModelKey getSecurityModelKeyFromDetails(D details) throws UnsupportedOperationException {
		try {
			return this.getSecurityKey(details);
		} catch (NoModelKeyException e) {
			if (details.isAdministrator() == false) {
				throw new UnsupportedOperationException("Required owner was unavailable with current security.");
			}
		}

		return null;
	}

	protected abstract D getUserDetails();

	protected abstract ModelKey getSecurityKey(D details) throws NoModelKeyException;

	protected abstract IterableTask<T> makeSecurityTask(ModelKey securityKey);

}
