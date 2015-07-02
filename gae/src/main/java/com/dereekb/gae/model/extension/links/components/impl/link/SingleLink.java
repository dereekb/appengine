package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkImplDelegate} implementation for links with a single value.
 *
 * @author dereekb
 *
 */
public class SingleLink
        implements LinkImplDelegate {

	private final SingleLinkDelegate delegate;

	public SingleLink(SingleLinkDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public List<ModelKey> keys() {
		ModelKey key = this.delegate.getKey();
		List<ModelKey> keys;

		if (key == null) {
			keys = new ArrayList<ModelKey>();
			keys.add(key);
		} else {
			keys = Collections.emptyList();
		}

		return keys;
	}

	@Override
	public void add(ModelKey key) {
		this.delegate.setKey(key);
	}

	@Override
	public void remove(ModelKey key) {
		ModelKey currentKey = this.delegate.getKey();

		if (currentKey == null || currentKey.equals(key)) {
			this.clear();
		}
	}

	@Override
	public void clear() {
		this.delegate.setKey(null);
	}

}
