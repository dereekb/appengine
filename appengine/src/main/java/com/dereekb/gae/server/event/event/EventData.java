package com.dereekb.gae.server.event.event;

import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Arbitrary data associated with an {@link Event}.
 * <p>
 * Is keyed by a unique type that helps {@link EventServiceListener}
 * implementations properly react to the data type.
 * <p>
 * General use cases will test and cast to a more specific event
 * data interface.
 *
 * @author dereekb
 *
 */
public interface EventData
        extends AlwaysKeyed<String> {

	/**
	 * Returns the unique data type key.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEventDataType();

	/**
	 * {@inheritDoc}
	 * <p>
	 * Returns the same value as {@link #getEventDataType()}.
	 */
	@Override
	public String keyValue();

}
