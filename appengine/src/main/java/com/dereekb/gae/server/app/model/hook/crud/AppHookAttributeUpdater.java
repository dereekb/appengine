package com.dereekb.gae.server.app.model.hook.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link UpdateTaskDelegate} implementation for {@link AppHook}.
 *
 * @author dereekb
 *
 */
public class AppHookAttributeUpdater
        implements UpdateTaskDelegate<AppHook> {

	// MARK: UpdateTaskDelegate
	@Override
	public void updateTarget(AppHook target,
	                         AppHook template)
	        throws InvalidAttributeException {

		// Enabled
		Boolean enabled = template.getEnabled();

		if (enabled != null && enabled != target.getEnabled()) {
			target.setEnabled(enabled);
		}

	}

}
