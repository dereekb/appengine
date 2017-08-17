package com.dereekb.gae.utilities.misc.success;

/**
 * Model that has a success flag.
 * 
 * @author dereekb
 *
 */
public interface SuccessModel {

	/**
	 * Whether or not this model was successful.
	 * 
	 * @return {@code true} if successful.
	 */
	public boolean isSuccessful();

}
