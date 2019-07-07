package com.dereekb.gae.extras.gen.app.config.app.model.shared.filter;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.SingleObjectFilter;

/**
 * {@link LocalModelConfiguration} filter.
 *
 * @author dereekb
 *
 */
@SuppressWarnings("unchecked")
public class NotInternalModelConfigurationFilter<T extends AppModelConfiguration>
        implements SingleObjectFilter<T> {

	@SuppressWarnings("rawtypes")
	private static final NotInternalModelConfigurationFilter SINGLETON = new NotInternalModelConfigurationFilter();

	public static <T extends AppModelConfiguration> NotInternalModelConfigurationFilter<T> make() {
		return SINGLETON;
	}

	// MARK: SingleObjectFilter
	@Override
	public FilterResult filterObject(AppModelConfiguration object) {
		return FilterResult.valueOf(object.isInternalModelOnly() == false);
	}

}
