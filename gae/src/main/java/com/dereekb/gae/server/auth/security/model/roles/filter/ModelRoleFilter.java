package com.dereekb.gae.server.auth.security.model.roles.filter;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * {@link Filter} for models. Uses a {@link ModelRoleSetContextBuilder} to build
 * the context.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleFilter<T extends UniqueModel> extends AbstractFilter<T> {

	private final ModelRoleSetContextBuilder<T> loader;
	private transient ModelRoleSetContainerFilter filter;

	public ModelRoleFilter(ModelRole role, ModelRoleSetContextBuilder<T> loader) {
		if (loader == null) {
			throw new IllegalArgumentException("loader cannot be null");
		}

		this.loader = loader;
		this.filter = new ModelRoleSetContainerFilter(role);
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(T object) {
		ModelRoleSetContext<T> roleSet = this.loader.loadContext(object);
		return this.filter.filterObject(roleSet);
	}

	@Override
	public String toString() {
		return "ModelRoleFilter [loader=" + this.loader + ", filter=" + this.filter + "]";
	}

}
