package com.dereekb.gae.utilities.time.model;

import java.util.Date;

/**
 * Model with a mutable date.
 * 
 * @author dereekb
 *
 */
public interface MutableDatedModel
        extends DatedModel {

	/**
	 * Sets the model's date.
	 * 
	 * @param date
	 *            {@link Date}. {@code null} is allowed.
	 * @throws IllegalArgumentException
	 *             thrown if the input date value is not allowed.
	 */
	public void setDate(Date date) throws IllegalArgumentException;

}
