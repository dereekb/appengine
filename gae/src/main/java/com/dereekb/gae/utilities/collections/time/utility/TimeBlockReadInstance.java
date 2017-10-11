package com.dereekb.gae.utilities.collections.time.utility;

import com.dereekb.gae.utilities.collections.time.TimeBlock;

/**
 * Wraps a {@link TimeBlock} and provides utility functions.
 * 
 * @author dereekb
 *
 */
public interface TimeBlockReadInstance
        extends TimeBlockContainer {

	/**
	 * The amount of time encapsulated within the {@link TimeBlock}.
	 * 
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getTotalTime();

}
