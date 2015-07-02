package com.thevisitcompany.gae.deprecated.model.mod.data.conversion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.data.DataMethodHandlerCache;
import com.thevisitcompany.gae.deprecated.model.mod.data.SerializerPair;
import com.thevisitcompany.gae.utilities.AnnotationsRetriever;

/**
 * Converts a model to it's model data (Data Transfer Object) representation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <D>
 *            Model's DTO Type
 */
@Deprecated
public class ModelConverter<T, D> {

	private class ModelDataConverterCache extends DataMethodHandlerCache<ModelConversionDelegate<T, D>> {

		public ModelDataConverterCache(ModelConversionDelegate<T, D> serializer) {
			super(serializer);
		}
	}

	private ModelDataConverterCache serializerCache;
	private ModelConversionDelegate<T, D> converter;

	public ModelConverter() {};

	public ModelConverter(ModelConversionDelegate<T, D> converter) throws NullPointerException {
		this.setConverter(converter);
	};

	public T convertToObject(D data) {
		ModelConversionDelegate<T, D> handler = this.retrieveHandler(data);
		T result = handler.convertDataToObject(data);
		return result;
	}

	public D convertToData(T object) throws DataConversionFailureException {
		return this.convertToData(object, null);
	}

	public D convertToData(T object,
	                       String name) throws DataConversionFailureException {
		D data = null;

		ModelConversionDelegate<T, D> handler = this.retrieveHandler(object);
		Method conversionMethod = this.retrieveConversionMethod(handler, name);

		SerializerPair<T, D> pair = new SerializerPair<T, D>(object);
		Boolean success = false;

		try {
			success = (Boolean) conversionMethod.invoke(handler, pair);

			if (success) {
				data = pair.getResult();
			} else {
				throw new DataConversionFailureException(pair);
			}
		} catch (InvocationTargetException e) {
			Throwable cause = e.getTargetException();
			throw new DataConversionFailureException(pair, cause);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new DataConversionFailureException(pair, e);
		}

		return data;
	}

	public List<T> convertToObjects(Iterable<D> modelData) {
		List<T> results = new ArrayList<T>();

		for (D data : modelData) {
			T result = this.convertToObject(data);
			results.add(result);
		}

		return results;
	}

	public List<D> convertToDataModels(Collection<T> objects) throws DataConversionFailureException {
		return this.convertToDataModels(objects, null);
	}

	public List<D> convertToDataModels(Collection<T> objects,
	                                   String name) throws DataConversionFailureException {
		List<D> results = new ArrayList<D>();

		for (T object : objects) {
			D result = this.convertToData(object, name);
			results.add(result);
		}

		return results;
	}

	private ModelConversionDelegate<T, D> retrieveHandler(Object object) {
		return this.converter;
	}

	private Method retrieveConversionMethod(ModelConversionDelegate<T, D> handler,
	                                        String function) {
		Method functionMethod = null;

		if (this.serializerCache.matchesFunction(function)) {
			functionMethod = this.serializerCache.getCurrentMethod();
		} else {
			if (function != null) {
				functionMethod = retrieveNamedConversionMethod(handler, function);
			} else {
				functionMethod = retrieveDefaultConversionMethod(handler);
			}
		}

		if (functionMethod != null) {
			ModelConversionFunction annotation = AnnotationsRetriever.getAnnotation(ModelConversionFunction.class,
			        functionMethod);
			boolean isDefault = annotation.isDefault();
			this.serializerCache.setCurrentMethod(functionMethod, isDefault, function);
		}

		return functionMethod;
	}

	private static <T, D> Method retrieveNamedConversionMethod(ModelConversionDelegate<T, D> handler,
	                                                           String function) {

		Class<?> handlerClass = handler.getClass();
		Method[] methods = handlerClass.getMethods();

		Method functionMethod = null;

		for (Method method : methods) {
			ModelConversionFunction annotation = AnnotationsRetriever.getAnnotation(ModelConversionFunction.class,
			        method);

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
			throw new RuntimeException("No function found to in handler '" + handler + " to respond to function '"
			        + function + "'.");
		}

		return functionMethod;
	}

	private static <T, D> Method retrieveDefaultConversionMethod(ModelConversionDelegate<T, D> handler) {

		Class<?> handlerClass = handler.getClass();
		Method[] methods = handlerClass.getMethods();
		Method defaultMethod = null;

		for (Method method : methods) {
			ModelConversionFunction annotation = AnnotationsRetriever.getAnnotation(ModelConversionFunction.class,
			        method);

			if (annotation != null) {
				if (annotation.isDefault()) {
					defaultMethod = method;
					break;
				}
			}
		}

		if (defaultMethod == null) {
			throw new RuntimeException("No default function found to in handler '" + handler + "'.");
		}

		return defaultMethod;
	}

	public ModelConversionDelegate<T, D> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelConversionDelegate<T, D> converter) {
		this.converter = converter;
		this.serializerCache = new ModelDataConverterCache(converter);
	}

}
