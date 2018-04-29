package com.dereekb.gae.server.app.model.hook.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.model.crud.task.impl.delegate.impl.AbstractChainedUpdateTaskDelegate;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.security.AppModelRole;
import com.dereekb.gae.server.app.model.app.shared.crud.AppRelatedModelAttributeUtility;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.auth.security.model.roles.utility.crud.ModelRoleAttributeInstance;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.googlecode.objectify.Key;

/**
 * {@link AppHookAttributeUpdater} extension used specifically for the
 * create-case.
 *
 * @author dereekb
 *
 */
public class AppHookCreateAttributeUpdater extends AbstractChainedUpdateTaskDelegate<AppHook> {

	public static final String GROUP_REQUIRED_CODE = "GROUP_REQUIRED";
	public static final String EVENT_REQUIRED_CODE = "EVENT_REQUIRED";
	public static final String PATH_REQUIRED_CODE = "PATH_REQUIRED";

	private AppRelatedModelAttributeUtility appAttributeUtility;

	public AppHookCreateAttributeUpdater(UpdateTaskDelegate<AppHook> updateTaskDelegate,
	        AppRelatedModelAttributeUtility appAttributeUtility) {
		super(updateTaskDelegate);
		this.appAttributeUtility = appAttributeUtility;
	}

	public AppRelatedModelAttributeUtility getAppAttributeUtility() {
		return this.appAttributeUtility;
	}

	public void setAppAttributeUtility(AppRelatedModelAttributeUtility appAttributeUtility) {
		if (appAttributeUtility == null) {
			throw new IllegalArgumentException("appAttributeUtility cannot be null.");
		}

		this.appAttributeUtility = appAttributeUtility;
	}

	// MARK: UpdateTaskDelegate
	@Override
	protected void chainUpdateTarget(AppHook target,
	                                 AppHook template)
	        throws InvalidAttributeException {

		// Set App
		Key<App> appKey = this.loadAppKeyFromTemplate(template);
		target.setApp(appKey);

		// Group
		String group = template.getGroup();

		if (group != null) {
			target.setGroup(group);
		} else if (StringUtility.isEmptyString(group)) {
			throw new InvalidAttributeException("group", null, "Group cannot be empty.", GROUP_REQUIRED_CODE);
		}

		// Event
		String event = template.getEvent();

		if (event != null) {
			target.setEvent(event);
		} else if (StringUtility.isEmptyString(event)) {
			throw new InvalidAttributeException("event", null, "Event cannot be empty.", EVENT_REQUIRED_CODE);
		}

		// Path
		String path = template.getPath();

		if (path != null) {
			target.setPath(path);
		} else if (StringUtility.isEmptyString(path)) {
			throw new InvalidAttributeException("path", null, "Path cannot be empty.", PATH_REQUIRED_CODE);
		}

	}

	public Key<App> loadAppKeyFromTemplate(AppHook template) throws InvalidAttributeException {
		ModelRoleAttributeInstance<App> instance = this.appAttributeUtility.makeInstanceWithTemplate(template);

		// User must have manage hooks role.
		instance.assertHasRole(AppModelRole.MANAGE_HOOKS);

		return instance.getKey();
	}

}
