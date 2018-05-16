package com.dereekb.gae.extras.gen.app.config.project.app.configurer.shared.remote;

import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl.AbstractRemoteServiceSpringContextConfigurer;

/**
 * Abstract class that filters based on the {@link AppSpringContextType}.
 *
 * @author dereekb
 *
 * @see AbstractRemoteServiceSpringContextConfigurer
 */
public class AbstractAppSpringContextTypeFilteredConfigurer {

	private AppSpringContextType springContext;

	public AbstractAppSpringContextTypeFilteredConfigurer() {
		this(AppSpringContextType.TASKQUEUE);
	}

	public AbstractAppSpringContextTypeFilteredConfigurer(AppSpringContextType springContext) {
		this.setSpringContext(springContext);
	}

	public AppSpringContextType getSpringContext() {
		return this.springContext;
	}

	public void setSpringContext(AppSpringContextType springContext) {
		if (springContext == null) {
			throw new IllegalArgumentException("springContext cannot be null.");
		}

		this.springContext = springContext;
	}

	// MARK: Utility
	public boolean matchesSpringContext(AppSpringContextType springContext) {
		return this.springContext == springContext;
	}

}
