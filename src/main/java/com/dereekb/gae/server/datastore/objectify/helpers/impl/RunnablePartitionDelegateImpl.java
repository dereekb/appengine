package com.dereekb.gae.server.datastore.objectify.helpers.impl;

import com.dereekb.gae.server.datastore.objectify.helpers.PartitionDelegate;
import com.googlecode.objectify.Work;

/**
 * Abstract {@link PartitionDelegate} implementation that implements
 * PartitionDelegate, but has no output type.
 *
 * @author dereekb
 *
 * @param <P>
 *            input type
 */
public abstract class RunnablePartitionDelegateImpl<P>
        implements PartitionDelegate<P, Void> {

	@Override
	public Work<Void> makeWorkForInput(final Iterable<P> input) {
		final RunnablePartitionDelegateImpl<P> instance = this;

		return new Work<Void>() {

			@Override
			public Void run() {
				instance.run(input);
				return null;
			}

		};
	}

	/**
	 * Runs with the input.
	 *
	 * @param input
	 *            {@link Iterable}.
	 *
	 * @return {@link Runnable}. Never {@code null}.
	 */
	public abstract void run(Iterable<P> input);

}
