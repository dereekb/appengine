package com.dereekb.gae.model.extension.links.deprecated.functions;

import java.util.Collection;
import java.util.Iterator;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.filter.FallableStagedFunctionObject;

/**
 * Links pair used by a {@link LinksFunction} instance.
 *
 * @author dereekb
 *
 * @param <T>
 *            Target type that extends {@link UniqueModel}.
 * @see {@link LinksHandler}
 */
public class LinksPair<T extends UniqueModel> extends HandlerPair<Collection<ModelKey>, T>
        implements FallableStagedFunctionObject<T> {

	private boolean successful = false;
	private final String name;
	private final LinksAction operation;

	/**
	 * {@link LinksPair} constructor.
	 *
	 * @param target
	 * @param keys
	 * @param name Name of the link type. Corresponds to the function annotated with {@link LinksMethod}
	 * @param operation {@link LinksAction} to perform.
	 * @throws NullPointerException Thrown if the target or keys collection is null.
	 * @throws IllegalArgumentException Thrown if the keys collection is empty.
	 */
	public LinksPair(T target, Collection<ModelKey> keys, String name, LinksAction operation)
	        throws NullPointerException, IllegalArgumentException {
		super(keys, target);

		if (target == null) {
			throw new NullPointerException("Target is null.");
		}

		if (operation != LinksAction.UNLINK) {
			if (keys == null) {
				throw new NullPointerException("Keys collection is null.");
			} else if (keys.isEmpty()) {
				throw new IllegalArgumentException("Keys collection is empty.");
			}
		}

		if (name == null) {
			throw new NullPointerException("No link name/function specified.");
		}

		if (operation == null) {
			throw new NullPointerException("No operation specified.");
		}

		this.name = name;
		this.operation = operation;
	}

	public String getName() {
		return this.name;
	}

	public T getTarget() {
		return this.object;
	}

	public LinksAction getOperation() {
		return this.operation;
	}

	public ModelKey getFirstLink() {
		Iterator<ModelKey> iterator = this.key.iterator();
		return iterator.next();
	}

	public Collection<ModelKey> getLinks() {
		return this.key;
	}

	@Override
	public T getFunctionObject(StagedFunctionStage stage) {
		return this.object;
	}

	@Override
	public boolean hasFailed() {
		return !this.successful;
	}

	public boolean isSuccessful() {
		return this.successful;
	}

	public void setSuccessful(boolean success) {
		this.successful = success;
	}

}
