package com.dereekb.gae.server.event.event.service.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.impl.BasicEventImpl;
import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.server.event.event.service.exception.EventServiceException;

/**
 * {@link EventServiceImpl} extension that sets the scope of input events.
 *
 * @author dereekb
 *
 */
public class AppEventServiceImpl extends EventServiceImpl {

	private AppInfo appInfo;

	public AppEventServiceImpl(AppInfo appInfo) {
		this(appInfo, Collections.emptyList());
	}

	public AppEventServiceImpl(AppInfo appInfo, List<EventServiceListener> listeners) {
		super(listeners);
		this.setAppInfo(appInfo);
	}

	public AppInfo getAppInfo() {
		return this.appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		if (appInfo == null) {
			throw new IllegalArgumentException("appInfo cannot be null.");
		}

		this.appInfo = appInfo;
	}

	// MARK: EventService
	@Override
	public void submitEvent(Event event) throws EventServiceException {
		this.tryUpdateScopeOnEvent(event);
		super.submitEvent(event);
	}

	private void tryUpdateScopeOnEvent(Event event) {
		this.tryUpdateScopeOnEvent(event);
		if (event.getScope() == null && BasicEventImpl.class.isAssignableFrom(event.getClass())) {
			String scope = this.appInfo.getAppServiceVersionInfo().getAppService();
			((BasicEventImpl) event).setScope(scope);
		}
	}

	@Override
	public String toString() {
		return "AppEventServiceImpl [appInfo=" + this.appInfo + ", getListeners()=" + this.getListeners() + "]";
	}

}
