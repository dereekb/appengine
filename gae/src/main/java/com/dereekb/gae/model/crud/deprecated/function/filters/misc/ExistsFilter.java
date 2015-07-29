package com.dereekb.gae.model.crud.deprecated.function.filters.misc;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionObjectFilter;

/**
 * Filter that checks whether or not a set of items related to the input objects
 * exist.
 *
 * Related elements are retrieved using a {@link ExistsFilterDelegate} and a
 * {@link Getter} is used to check for their existence.
 *
 * @author dereekb
 */
public class ExistsFilter<W extends StagedFunctionObject<Object>> extends AbstractStagedFunctionObjectFilter<Object, W> {

	private Getter<?> getter;
	private ExistsFilterDelegate<W> delegate;

	@Override
	public FilterResults<W> filterFunctionObjects(StagedFunctionStage stage,
	                                              Iterable<W> objects) {

		FilterResults<W> results = new FilterResults<W>();
		HashMapWithList<ModelKey, W> mapping = new HashMapWithList<ModelKey, W>();

		for (W object : objects) {
			Collection<ModelKey> key = this.delegate.getKeysForExistCheck(object);
			mapping.addAll(key, object);
		}

		Set<ModelKey> keys = mapping.getKeySet();
		Set<ModelKey> existing = this.getter.exists(keys);

		for (ModelKey key : existing) {
			List<W> pass = mapping.getElements(key);
			results.addAll(FilterResult.PASS, pass);
			mapping.remove(key);
		}

		Set<W> fail = mapping.getAllElements();
		results.addAll(FilterResult.FAIL, fail);
		return results;
	}

	@Override
	public FilterResult filterObject(W object) {
		Collection<ModelKey> keys = this.delegate.getKeysForExistCheck(object);
		boolean exists = this.getter.allExist(keys);
		return FilterResult.withBoolean(exists);
	}

	public Getter<?> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<?> getter) {
		this.getter = getter;
	}

	public ExistsFilterDelegate<W> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ExistsFilterDelegate<W> delegate) {
		this.delegate = delegate;
	}

}
