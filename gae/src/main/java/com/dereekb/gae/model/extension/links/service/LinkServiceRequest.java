package com.dereekb.gae.model.extension.links.service;

import java.util.List;

import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;

/**
 * Request for {@link LinkService}.
 *
 * @author dereekb
 */
public interface LinkServiceRequest
        extends AtomicTaskConfig {

	/**
	 * Returns the list of link changes to make.
	 *
	 * @return list of link changes to make. Never {@code null}.
	 */
	public List<LinkSystemChange> getLinkChanges();

}
