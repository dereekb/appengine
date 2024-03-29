package com.dereekb.gae.model.extension.links.service.impl;

import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.extension.links.deprecated.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;
import com.dereekb.gae.model.extension.links.service.LinkServiceResponse;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeException;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeSetException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithSet;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link LinkService} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LinkServiceLinkSystemImpl
        implements LinkService {

	private LinkSystem system;

	public LinkServiceLinkSystemImpl(LinkSystem system) {
		this.system = system;
	}

	public LinkSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkSystem system) {
		this.system = system;
	}

	// MARK: LinkService
	@Override
	public LinkServiceImplResponse updateLinks(LinkServiceRequest request)
	        throws LinkSystemChangeSetException,
	            AtomicOperationException {
		List<LinkSystemChange> changes = request.getLinkChanges();
		LinkServiceLinkSystemChangesRunner runner = new LinkServiceLinkSystemChangesRunner(this.system);

		try {
			runner.runChanges(changes);
		} catch (Exception e) {
			throw new AtomicOperationException(e);
		}

		List<LinkSystemChangeException> failures = runner.getFailures();
		boolean hasMissingKeys = runner.hasMissingKeys();

		if (failures.isEmpty()) {
			if (hasMissingKeys && request.isAtomic()) {
				HashMapWithSet<String, ModelKey> missing = runner.getMissingPrimaryKeys();
				throw new AtomicOperationException(missing.valuesSet(), AtomicOperationExceptionReason.UNAVAILABLE);
			} else {
				runner.saveChanges();
			}
		} else {
			throw new LinkSystemChangeSetException(failures);
		}

		return new LinkServiceImplResponse(runner);
	}

	public static class LinkServiceImplResponse
	        implements LinkServiceResponse {

		private final LinkServiceLinkSystemChangesRunner runner;

		public LinkServiceImplResponse(LinkServiceLinkSystemChangesRunner runner) {
			this.runner = runner;
		}

		// MARK: LinkServiceResponse
		@Override
		public CaseInsensitiveMapWithSet<ModelKey> getMissingPrimaryKeysSet() {
			return this.runner.getMissingPrimaryKeys();
		}

	}

}
