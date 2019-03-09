package com.dereekb.gae.model.stored.blob.crud;

import com.dereekb.gae.model.extension.links.descriptor.filter.UniqueDescribedModelFilter;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * Filter that returns {@link FilterResult#PASS} for deletable
 * {@link StoredBlob}.
 * <p>
 * StoredBlob values are not deletable if they are linked to a descriptor.
 *
 * @author dereekb
 *
 */
public class StoredBlobDeleteRulesFilter extends AbstractFilter<StoredBlob> {

	private static final UniqueDescribedModelFilter DESCRIBED_FILTER = new UniqueDescribedModelFilter(false);

	@Override
	public FilterResult filterObject(StoredBlob object) {
		return DESCRIBED_FILTER.filterObject(object);
	}

}
