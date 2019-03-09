package com.dereekb.gae.model.geo.place.crud;

import com.dereekb.gae.model.extension.links.descriptor.filter.UniqueDescribedModelFilter;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * Filter that returns {@link FilterResult#PASS} for deletable {@link GeoPlace}.
 * <p>
 * GeoPlace values are not deletable if they are linked to a descriptor.
 *
 * @author dereekb
 *
 */
public class GeoPlaceDeleteRulesFilter extends AbstractFilter<GeoPlace> {

	private static final UniqueDescribedModelFilter DESCRIBED_FILTER = new UniqueDescribedModelFilter(false);

	@Override
	public FilterResult filterObject(GeoPlace object) {
		return DESCRIBED_FILTER.filterObject(object);
	}

}
