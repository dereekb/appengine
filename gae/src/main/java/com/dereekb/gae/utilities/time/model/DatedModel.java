package com.dereekb.gae.utilities.time.model;

import java.util.Date;

/**
 * Interface for models that have a primary date value affixed.
 * 
 * @author dereekb
 *
 */
public interface DatedModel {

	/**
	 * Returns the model's date.
	 * 
	 * @return {@link Date}. Never {@code null}.
	 */
	public Date getDate();

}
