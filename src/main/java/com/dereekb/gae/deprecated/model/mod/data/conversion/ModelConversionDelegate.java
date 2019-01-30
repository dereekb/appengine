package com.thevisitcompany.gae.deprecated.model.mod.data.conversion;

/**
 * Delegate for converting between object types.
 *
 * Conversion from objects to data are actually annotated with a {@link ModelConversionFunction} annotation.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <D>
 */
@Deprecated
public interface ModelConversionDelegate<T, D> {

	public static String DEFAULT_DATA_NAME = "data";

	public T convertDataToObject(D data);

}
