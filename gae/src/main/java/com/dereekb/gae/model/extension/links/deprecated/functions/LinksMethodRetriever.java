package com.dereekb.gae.model.extension.links.deprecated.functions;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.utilities.AnnotationsRetriever;
import com.dereekb.gae.utilities.cache.CachePair;

/**
 * Support class that looks up methods in a class that are annotated with an {@link LinksMethod} annotation.
 * 
 * @author dereekb
 */
@Deprecated
public class LinksMethodRetriever {

	private static final class LinksHandlerCache extends CachePair<Method, String> {

		public LinksHandlerCache(String key, Method cachedValue) {
			super(key, cachedValue);
		}
	}

	private Class<?> delegateClass;
	private Map<LinksAction, LinksHandlerCache> linkHandlerCache = new HashMap<LinksAction, LinksHandlerCache>();

	public LinksMethodRetriever() {}

	public LinksMethodRetriever(Class<?> delegateClass) throws NullPointerException {
		this.setDelegateClass(delegateClass);
	}

	/**
	 * Looks up the method annotated with a {@link LinksMethod} in the delegate class.
	 * 
	 * @param name
	 * @param action
	 * @return Method with the responding annotation, or null if none was found.
	 */
	public Method retrieveMethod(String name,
	                             LinksAction action) {
		Method method = this.checkCacheValue(name, action);

		if (method == null) {
			method = retrieveMethodForType(this.delegateClass, action, name);
			this.addHandleCacheToMap(name, method, action);
		}

		return method;
	}

	private void addHandleCacheToMap(String name,
	                                 Method method,
	                                 LinksAction action) {
		LinksHandlerCache cache = new LinksHandlerCache(name, method);
		this.linkHandlerCache.put(action, cache);
	}

	private Method checkCacheValue(String name,
	                               LinksAction action) {
		Method method = null;
		LinksHandlerCache handlerCache = this.linkHandlerCache.get(action);

		if (handlerCache != null) {
			String key = handlerCache.getKey();
			if (key.equals(name)) {
				method = handlerCache.getCachedValue();
			}
		}

		return method;
	}

	private static Method retrieveMethodForType(Class<?> handlerClass,
	                                            LinksAction action,
	                                            String name) {
		Method[] methods = handlerClass.getMethods();
		Method functionMethod = null;

		for (Method method : methods) {
			LinksMethod annotation = AnnotationsRetriever.getAnnotation(LinksMethod.class, method);

			if (annotation != null) {
				if (annotation.action().equals(action)) {
					if (annotation.value().equals(name)) {
						functionMethod = method;
						break;
					}
				}
			}
		}

		return functionMethod;
	}

	public Class<?> getDelegateClass() {
		return delegateClass;
	}

	public void setDelegateClass(Class<?> delegateClass) throws NullPointerException {
		if (delegateClass == null) {
			throw new NullPointerException("Attempted to set delegate class to null.");
		}

		if (delegateClass.equals(this.delegateClass) == false) {
			this.linkHandlerCache.clear();
		}

		this.delegateClass = delegateClass;
	}

}
