package com.dereekb.gae.model.extension.links.components.impl.link;

import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link SingleLink} extension that allows setting the {@link ModelKey} without
 * having to clear the link first.
 * <p>
 * Useful in cases where the link is only used as a reference by the model
 * itself and sudden changes without first unlinking are considered an
 * unnecessary precaution.
 * 
 * @author dereekb
 *
 */
@Deprecated
public class UnrestrictedSingleLink extends SingleLink {

	public UnrestrictedSingleLink(SingleLinkDelegate delegate) {
		super(delegate);
	}

	// MARK: Override
	/**
	 * Sets the new value, regardless if it is currently set or not.
	 */
	@Override
	public boolean add(ModelKey key) throws RelationChangeException {
		boolean redundant = false;
		SingleLinkDelegate delegate = this.getDelegate();
		ModelKey currentKey = delegate.getKey();

		if (currentKey == null || currentKey.equals(key) == false) {
			delegate.setKey(key);
		} else {
			redundant = true;
		}

		return redundant;
	}

}
