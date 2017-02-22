package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.dereekb.gae.server.auth.security.authentication.LoginAuthentication;
import com.dereekb.gae.utilities.java.AnnotationsRetriever;

/**
 * Entry-point for Visit Permissions Evaluations. 
 * 
 * All hasPermission(...) requests through Spring Security will come through here.
 * 
 * @author dereekb
 *
 */
public final class PermissionsEvaluator implements PermissionEvaluator {

	private final PermissionsHashMap handlerMap = new PermissionsHashMap();
	private final static String PREFIX_APPEND_FORMAT = "%s.%s";
	
	public PermissionsEvaluator(Collection<PermissionsHandler> handlers) {
		this.handlerMap.addHandlers(handlers);
	}
	
	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetObject, Object permission) throws NoPermissionHandlerFoundException {

		boolean hasPermission = false;
		
		if (authentication != null && (permission instanceof String)) {
			
			String permissionRequest = (String) permission;
			
			if (targetObject != null) {
				permissionRequest = this.searchAndAppendPrefix(targetObject, permissionRequest);
			}
			
			LoginAuthentication loginAuthentication = (LoginAuthentication) authentication;
			PermissionsEvent event = new PermissionsEvent(loginAuthentication, targetObject, permissionRequest);
			
			PermissionsHandler handler = this.handlerMap.getHandler(event);
			
			if (handler != null) {
				hasPermission = this.hasPermission(handler, event);
			} else {
				throw new NoPermissionHandlerFoundException(permissionRequest);
			}
		}
		
		return hasPermission;
	}

	private String searchAndAppendPrefix(Object targetObject, String inputPermissionRequest) {
	
		String permissionRequest = inputPermissionRequest;
		Class<?> targetClass = targetObject.getClass();
		PermissionsPrefix prefix = targetClass.getAnnotation(PermissionsPrefix.class);
		
		if (prefix != null) {
			boolean inputRequestHasLength = (inputPermissionRequest.isEmpty() == false);
			String value = prefix.value();
			permissionRequest = (inputRequestHasLength) ? (String.format(PREFIX_APPEND_FORMAT, value, inputPermissionRequest)) : (value);
		}
		
		return permissionRequest;
	}
	
	private boolean hasPermission(PermissionsHandler handler, PermissionsEvent event) {
		boolean hasPermission = false;
		
		try {
			String functionId = event.getRequestFunctionName();
			Method eventHandlerMethod = findHandlerEventMethod(functionId, handler);
			
			try {
				Boolean allow = (Boolean) eventHandlerMethod.invoke(handler, event);
				hasPermission = allow;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			
		} catch (NoPermissionHandlerFunctionFoundException e) {
			hasPermission = handler.hasPermission(event);
		}
		
		return hasPermission;
	}
	
	private static Method findHandlerEventMethod(String function, PermissionsHandler handler) throws NoPermissionHandlerFunctionFoundException{

		Class<?> handlerClass = handler.getClass();
		Method[] methods = handlerClass.getMethods();
		Method functionMethod = null;
		
		for (Method method : methods) {
			PermissionEventHandlerFunction annotation = AnnotationsRetriever.getAnnotation(PermissionEventHandlerFunction.class, method);
			
			if (annotation != null) {
				String[] annotationFunctions = annotation.value();
				
				for (String annotationFunction : annotationFunctions) {
					
					if (function.equals(annotationFunction)) {
						functionMethod = method;
						break;
					}
				}	
			}
		}
		
		if (functionMethod == null) {
			throw new NoPermissionHandlerFunctionFoundException(function, handler);
		}
		
		return functionMethod;
	}
	
	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		throw new RuntimeException("Permission type is not supported.");
	}

}
