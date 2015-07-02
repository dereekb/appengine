package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.authentication.LoginAuthentication;

/**
 * Permissions event that wraps the login, object, and request.
 * 
 * 
 * @author dereekb
 *
 */
public final class PermissionsEvent {

	private static final Integer SPLIT_MAXIMUM = 3;
	private static final String SPLIT_REGEX = "\\.";
	private static final Integer REQUEST_HANDLER_NAME_INDEX = 0;
	private static final Integer REQUEST_FUNCTION_INDEX = 1;
	private static final Integer REQUEST_PARAMETERS_INDEX = 2;
	
	private final String request;
	private final Object object;
	private final LoginAuthentication authentication;
	
	private String[] splitRequest = null;
	
	public PermissionsEvent(LoginAuthentication authentication, Object object, String request) {
		this.authentication = authentication;
		this.object = object;
		this.request = request;
	}

	public String getRequest() {
		return this.request;
	}

	public Object getObject() {
		return this.object;
	}

	public LoginAuthentication getAuthentication() {
		return this.authentication;
	}
	
	public Login getLogin() {
		Login login = null;
		
		Object principal = this.authentication.getPrincipal();
		
		if (principal != null) {
			login = (Login) principal;
		}
		
		return login;
	}
	
	/**
	 * Returns the first split of the string.
	 * @return
	 */
	public String getRequestHandlerName() {
		String[] splitRequest = this.getSplitRequest();
		String requestHandlerName = splitRequest[REQUEST_HANDLER_NAME_INDEX];
		return requestHandlerName;
	}

	public boolean hasFunctionName() {
		String[] splitRequest = this.getSplitRequest();
		return (splitRequest.length > 1);
	}
	
	public String getRequestFunctionName() {
		String[] splitRequest = this.getSplitRequest();
		String requestFunctionName = splitRequest[REQUEST_FUNCTION_INDEX];
		return requestFunctionName;
	}
	
	public boolean hasParameters() {
		String[] splitRequest = this.getSplitRequest();
		return (splitRequest.length > 2);
	}

	public String getRequestParameters() {
		String[] splitRequest = this.getSplitRequest();
		String requestParameters = splitRequest[REQUEST_PARAMETERS_INDEX];
		return requestParameters;
	}
	
	private String[] getSplitRequest() {
		String[] splitRequest = this.splitRequest;
		
		if (splitRequest == null) {
			splitRequest = this.request.split(SPLIT_REGEX, SPLIT_MAXIMUM);
			this.splitRequest = splitRequest;
		}
		
		return splitRequest;
	}
	
}
