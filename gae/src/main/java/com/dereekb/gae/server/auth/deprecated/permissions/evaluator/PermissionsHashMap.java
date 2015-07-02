package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class PermissionsHashMap{

	private final Map<String, PermissionsHandler> handlers = new HashMap<String, PermissionsHandler>();

	public PermissionsHashMap() {}
	
	public void addHandlers(Collection<PermissionsHandler> handlers) {
		for (PermissionsHandler handler : handlers) {
			this.addHandler(handler);
		}
	}
	
	public void addHandler(PermissionsHandler handler) {
		String key = handler.getResponseKey();
		PermissionsHandler replacedHandler = this.handlers.put(key, handler);
		
		if (replacedHandler != null) {
			throw new RuntimeException("PermissionsHandler '" + handler + "' replaced previous handler '" + replacedHandler + "' with the key '" + key + "'.");
		}
	}
	
	public PermissionsHandler getHandler(PermissionsEvent event) {
		String key = event.getRequestHandlerName();
		PermissionsHandler handler = null;
		
		if (key != null) {
			handler = this.handlers.get(key);
		}
		
		return handler;
	}
	
	public Map<String, PermissionsHandler> getHandlers() {
		return handlers;
	}

}
