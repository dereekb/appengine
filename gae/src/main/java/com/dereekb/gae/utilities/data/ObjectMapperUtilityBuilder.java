package com.dereekb.gae.utilities.data;

/**
 * {@link ObjectMapperUtility} builder.
 *
 * @author dereekb
 *
 */
public interface ObjectMapperUtilityBuilder {

	/**
	 * Creates a new utility that is null safe.
	 *
	 * @return {@link ObjectMapperUtility}. Never {@code null}.
	 */
	public ObjectMapperUtilityBuilder nullSafe();

	/**
	 * Creates a new utility with the null safety set.
	 *
	 * @return {@link ObjectMapperUtility}. Never {@code null}.
	 */
	public ObjectMapperUtilityBuilder nullSafe(boolean safe);

	/**
	 * Creates a new ObjectMapperUtility.
	 *
	 * @return {@link ObjectMapperUtility}. Never {@code null}.
	 */
	public ObjectMapperUtility make();

}
