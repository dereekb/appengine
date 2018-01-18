package com.dereekb.gae.server.auth.security.model.query.task;

import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.server.datastore.objectify.query.impl.TaskedObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Abstract security task that restricts a query to the user's scope depending
 * on the role the current security context has.
 * <p>
 * Administrator roles are unaffected. Non-user roles are rejected.
 * <p>
 * Generally used with a
 * {@link TaskedObjectifyQueryRequestLimitedBuilderInitializer}.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractSecurityModelQueryTaskOverride<D extends LoginTokenUserDetails<?>, Q>
        implements Task<Q> {

	@Override
	public void doTask(Q input) throws FailedTaskException {
		try {
			D details = this.tryLoadSecurityDetails();

			switch (details.getUserType()) {
				case ADMINISTRATOR:
					// Do nothing to modify the input query.
					break;
				case USER:
					this.tryUpdateQueryForUser(input, details);
					break;
				default:
					this.tryUpdateQueryForDefaultType(input, details);
					break;
			}
		} catch (NoSecurityContextException e) {
			throw new FailedTaskException("No security context is available.", e);
		} catch (InvalidAttributeException e) {


			throw new FailedTaskException("Illegal query argument.", e);
		} catch (NoModelKeyException e) {
			throw new FailedTaskException("One or more required security keys were unavailable.", e);
		}
	}

	// MARK: Internal
	protected abstract D tryLoadSecurityDetails() throws NoSecurityContextException;

	protected abstract void tryUpdateQueryForUser(Q input,
	                                              D details)
	        throws InvalidAttributeException,
	            NoModelKeyException,
	            FailedTaskException;

	protected void tryUpdateQueryForDefaultType(Q input,
	                                            D details)
	        throws InvalidAttributeException,
	            NoModelKeyException,
	            FailedTaskException {
		this.throwFailedTaskForUnauthorizedUser();
	}

	protected void throwFailedTaskForUnauthorizedUser() throws FailedTaskException {
		throw new FailedTaskException("This security user type is not allowed to query.");
	}

	@Override
	public String toString() {
		return "SecurityOverrideStudentOwnedModelQueryTask []";
	}

}
