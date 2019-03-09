package com.dereekb.gae.model.extension.links.descriptor.filter;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.UniqueDescribedModel;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * Filters values that have or do not have a {@link Descriptor} set.
 *
 * @author dereekb
 *
 */
public class UniqueDescribedModelFilter extends AbstractFilter<UniqueDescribedModel> {

	private boolean hasDescriptor;

	public UniqueDescribedModelFilter() {
		this(true);
	}

	public UniqueDescribedModelFilter(boolean hasDescriptor) {
		this.hasDescriptor = hasDescriptor;
	}

	public boolean getHasDescriptor() {
		return this.hasDescriptor;
	}

	public void setHasDescriptor(boolean hasDescriptor) {
		this.hasDescriptor = hasDescriptor;
	}

	@Override
	public FilterResult filterObject(UniqueDescribedModel object) {
		boolean result = this.matchesRule(object);
		return FilterResult.withBoolean(result);
	}

	public boolean matchesRule(UniqueDescribedModel object) {
		Descriptor descriptor = object.getDescriptor();
		boolean hasDescriptor = (descriptor != null);
		return (hasDescriptor == this.hasDescriptor);
	}

}
