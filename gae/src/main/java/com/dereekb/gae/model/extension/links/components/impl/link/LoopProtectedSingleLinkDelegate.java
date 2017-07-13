package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.Set;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.parent.objectify.ObjectifyChild;
import com.dereekb.gae.utilities.collections.parent.objectify.ObjectifyParentReader;

/**
 * {@link SingleLinkDelegate} wrapper that protects a {@link ObjectifyChild}
 * primary from a loop generated.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public class LoopProtectedSingleLinkDelegate<T extends ObjectifyChild<T>>
        implements SingleLinkDelegate {

	private ModelKey primary;
	private ObjectifyParentReader<T> parentReader;
	private SingleLinkDelegate delegate;

	public LoopProtectedSingleLinkDelegate(ModelKey primary,
	        ObjectifyParentReader<T> parentReader,
	        SingleLinkDelegate delegate) {
		this.primary = primary;
		this.parentReader = parentReader;
		this.delegate = delegate;
	}

	// MARK: SingleLinkDelegate
	@Override
	public ModelKey getKey() {
		return this.delegate.getKey();
	}

	@Override
	public void setKey(ModelKey key) throws RelationChangeException {
		if (key != null) {
			try {
				Set<ModelKey> parentKeys = this.parentReader.getParentKeys(key);

				if (parentKeys.contains(this.primary.getModelKey())) {
					throw new RelationChangeException(key, null,
					        "Cannot link as it would create a relation loop.");
				}
			} catch (UnavailableModelException e) {
				throw new RelationChangeException(key, null, "Could not load primary model to check loop.");
			}
		}

		this.delegate.setKey(key);
	}

	@Override
	public String toString() {
		return "LoopProtectedSingleLinkDelegate [primary=" + this.primary + ", parentReader=" + this.parentReader
		        + ", delegate=" + this.delegate + "]";
	}

}
