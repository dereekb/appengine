package com.dereekb.gae.utilities.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Staticly used class for retrieving annotations from methods. If the super class has annotations on overridden methods, this will look for them.
 * 
 * Methods must be public or otherwise are not visible.
 * @author dereekb
 *
 */
public class AnnotationsRetriever {

	public static <A extends Annotation> A getAnnotation(Class<A> annotationClass, Method method) {
		A annotation = method.getAnnotation(annotationClass);

		if (annotation == null) {
			annotation = getOverriddenAnnotation(annotationClass, method);
		}

		return annotation;
	}

	private static <A extends Annotation> A getOverriddenAnnotation(Class<A> annotationClass, Method method) {

		final String name = method.getName();
		final Class<?> methodClass = method.getDeclaringClass();
		final Class<?>[] params = method.getParameterTypes();

		A annotation = null;
		final Class<?> superclass = methodClass.getSuperclass();
		
		if (superclass != null) {
			annotation = getOverriddenAnnotationFrom(annotationClass, superclass, name, params);
		}
		
		return annotation;
	}

	private static <A extends Annotation> A getOverriddenAnnotationFrom(Class<A> annotationClass, Class<?> searchClass, String name, Class<?>[] params) {
		A annotation = null;
		
		try {
			final Method method = searchClass.getMethod(name, params);
			annotation = method.getAnnotation(annotationClass);
			
			if (annotation != null) {
				return annotation;
			} else {
				annotation = getOverriddenAnnotation(annotationClass, method);
			}
			
		} catch (final NoSuchMethodException e) {
			return null;
		}
		
		return annotation;
	}
	
}
