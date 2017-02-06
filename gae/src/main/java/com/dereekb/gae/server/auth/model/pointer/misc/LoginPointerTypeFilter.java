package com.dereekb.gae.server.auth.model.pointer.misc;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * {@link Filter} implementation that filters {@link LoginPointer} instances by
 * their {@link LoginPointerType} values.
 * 
 * @author dereekb
 *
 */
public class LoginPointerTypeFilter extends AbstractFilter<LoginPointer> {

	private Integer type;

	public LoginPointerTypeFilter(LoginPointerType type) throws IllegalArgumentException {
		this.setType(type);
	}

	public LoginPointerType getType() {
		return LoginPointerType.valueOf(this.type);
	}

	public void setType(LoginPointerType type) throws IllegalArgumentException {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}

		this.type = type.getId();
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(LoginPointer object) {
		return FilterResult.valueOf(object.getTypeId().equals(this.type));
	}

}
