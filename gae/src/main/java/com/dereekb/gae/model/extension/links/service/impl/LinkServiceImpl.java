package com.dereekb.gae.model.extension.links.service.impl;

import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.service.LinkChange;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;

/**
 * {@link LinkService} implementation.
 *
 * @author dereekb
 *
 */
public class LinkServiceImpl
        implements LinkService {

	private LinkSystem system;

	public LinkServiceImpl(LinkSystem system) {
		this.system = system;
	}

	public LinkSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkSystem system) {
		this.system = system;
	}

	@Override
	public void updateLinks(LinkServiceRequest request) throws LinkChangesException, AtomicOperationException {
		List<LinkChange> changes = request.getLinkChanges();
		LinkChangesRunner runner = new LinkChangesRunner(this.system);

		try {
			runner.runChanges(changes);
		} catch (Exception e) {
			throw new AtomicOperationException(e);
		}

		List<LinkChangeException> failures = runner.getFailures();

		if (failures.isEmpty() == false) {
			runner.saveChanges();
		} else {
			throw new LinkChangesException(failures);
		}
	}

}
