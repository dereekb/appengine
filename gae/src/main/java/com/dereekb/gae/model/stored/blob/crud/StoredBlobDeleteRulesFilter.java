package com.dereekb.gae.model.stored.blob.crud;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.utilities.filters.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResult;

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

	@Override
	public FilterResult filterObject(StoredBlob object) {
		Descriptor descriptor = object.getDescriptor();

		boolean descriptorIsNotSet = (descriptor == null);

		return FilterResult.withBoolean(descriptorIsNotSet);
	}

}
