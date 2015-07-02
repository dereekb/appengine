package com.dereekb.gae.utilities;

import java.lang.reflect.Method;

/**
 * Retrieves a method with a given name from the target class.
 * @author dereekb
 *
 */
public class MethodRetriever {

	public static Method findMethod(String name, Class<?> type) {
		Method method = findMethod(name, type, true);
		return method;
	}
	
	/**
	 * Returns a method with the given name. 
	 * @param name
	 * @param type
	 * @param enforceType Whether or not to return immediately when the method's type matches the class type.
	 * @return
	 */
	public static Method findMethod(String name, Class<?> type, boolean enforceType) {
		Method foundMethod = null;
		Method[] methods = type.getMethods();
		
		for (Method method : methods) {
			String methodName = method.getName();
			
			if (name.equals(methodName)) {
				foundMethod = method;
				
				if (enforceType) {
					if (type.equals(method.getDeclaringClass())) {
						break;
					}
				}
			}
		}
		
		return foundMethod;
	}
	
}
