package com.dereekb.gae.utilities.misc.keyed;

/**
 * Item that has a code integer. Generally used to index enum values.
 * 
 * @author dereekb
 *
 */
public interface IndexCoded {

	/**
	 * Returns the code index.
	 * 
	 * @return {@link Integer}. Never {@code null}.
	 */
	public Integer getCode();

}
