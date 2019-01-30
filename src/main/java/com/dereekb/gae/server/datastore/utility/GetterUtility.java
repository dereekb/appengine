package com.dereekb.gae.server.datastore.utility;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.MapUtility;

/**
 * Utility used with a {@link Getter} instance.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class GetterUtility<T extends UniqueModel> {

	public Getter<T> getter;

	public GetterUtility(Getter<T> getter) {
		this.setGetter(getter);
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	// MARK: Utility
	public List<T> filterExisting(Iterable<T> entities) {
		Map<ModelKey, T> map = ModelKey.makeModelKeyMap(entities);
		Set<ModelKey> existing = this.getter.getExisting(map.keySet());
		return MapUtility.getValuesForKeys(map, existing);

	}

}
