package com.dereekb.gae.extras.gen.utility.spring.security.impl;

import com.dereekb.gae.extras.gen.utility.spring.security.RoleConfig;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * Abstract {@link RoleConfig} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractRoleConfigImpl
        implements RoleConfig {

	private boolean not = false;
	private String accessFunction;

	public AbstractRoleConfigImpl(String accessFunction) {
		this(false, accessFunction);
	}

	public AbstractRoleConfigImpl(boolean not, String accessFunction) {
		super();
		this.setNot(not);
		this.setAccessFunction(accessFunction);
	}

	public boolean isNot() {
		return this.not;
	}

	public void setNot(boolean not) {
		this.not = not;
	}

	public String getAccessFunction() {
		return this.accessFunction;
	}

	public void setAccessFunction(String accessFunction) {
		if (accessFunction == null) {
			throw new IllegalArgumentException("accessFunction cannot be null.");
		}

		this.accessFunction = accessFunction;
	}

	// MARK: RoleConfig
	@Override
	public String getAccess() {
		Iterable<String> args = this.getArguments();
		args = StringUtility.wrapValues(args, "'");
		return this.accessFunction + "(" + StringUtility.joinValues(args) + ")";
	}

	protected abstract Iterable<String> getArguments();

	@Override
	public String toString() {
		return "AbstractRoleConfigImpl [not=" + this.not + ", accessFunction=" + this.accessFunction + "]";
	}

}
