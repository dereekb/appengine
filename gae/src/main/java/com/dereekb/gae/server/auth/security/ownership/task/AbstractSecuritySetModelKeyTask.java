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
	private boolean alwaysSkipAdmin = true;

	public AbstractSecuritySetModelKeyTask() {
		this(false, true);
	}

	public AbstractSecuritySetModelKeyTask(boolean failOnNoSecurity) {
		this(failOnNoSecurity, true);
	}

	public AbstractSecuritySetModelKeyTask(boolean failOnNoSecurity, boolean alwaysSkipAdmin) {
		super();
		this.setFailOnNoSecurity(failOnNoSecurity);
		this.setAlwaysSkipAdmin(alwaysSkipAdmin);
	}

	public boolean isFailOnNoSecurity() {
		return this.failOnNoSecurity;
	}

	public void setFailOnNoSecurity(boolean failOnNoSecurity) {
		this.failOnNoSecurity = failOnNoSecurity;
	}
	
	public boolean isAlwaysSkipAdmin() {
		return this.alwaysSkipAdmin;
	}

	public void setAlwaysSkipAdmin(boolean alwaysSkipAdmin) {
		this.alwaysSkipAdmin = alwaysSkipAdmin;
	}

	// MARK: IterableTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		if (this.trySkipAdmin()) {
			return;
		}
		
		ModelKey securityKey = this.getSecurityModelKey();

		if (securityKey != null) {
			IterableTask<T> task = this.makeSecurityTask(securityKey);
			task.doTask(input);
		}
	}

	private boolean trySkipAdmin() {
		if (this.alwaysSkipAdmin) {
			try {
				D details = this.getUserDetails();
				return details.isAdministrator();
			} catch (NoSecurityContextException e) {
				this.tryFailOnNoSecurity();
			}
		}
		
		return false;
	}

	// MARK: Internal
	private ModelKey getSecurityModelKey() throws UnsupportedOperationException {
		ModelKey modelKey = null;

		try {
			D details = this.getUserDetails();
			modelKey = this.getSecurityModelKeyFromDetails(details);
		} catch (NoSecurityContextException e) {
			this.tryFailOnNoSecurity();
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

	private void tryFailOnNoSecurity() {
		if (this.failOnNoSecurity) {
			throw new UnsupportedOperationException();
		}
	}
	
	protected abstract D getUserDetails();

	protected abstract ModelKey getSecurityKey(D details) throws NoModelKeyException;

	protected abstract IterableTask<T> makeSecurityTask(ModelKey securityKey);

}
