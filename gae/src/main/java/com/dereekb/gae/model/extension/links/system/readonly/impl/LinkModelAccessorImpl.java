package com.dereekb.gae.model.extension.links.system.readonly.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.system.components.LinkModel;
import com.dereekb.gae.model.extension.links.system.components.LinkModelBuilder;
import com.dereekb.gae.model.extension.links.system.components.impl.AbstractTypedLinkSystemComponent;
import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModelAccessor} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModelAccessorImpl<T extends UniqueModel> extends AbstractTypedLinkSystemComponent
        implements LinkModelAccessor {

	private ReadService<T> readService;
	private LinkModelBuilder<T> linkBuilder;

	public LinkModelAccessorImpl(String linkModelType,
	        ReadService<T> readService,
	        LinkModelBuilder<T> linkBuilder) {
		super(linkModelType);
		this.setReadService(readService);
		this.setLinkBuilder(linkBuilder);
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		if (readService == null) {
			throw new IllegalArgumentException("readService cannot be null.");
		}

		this.readService = readService;
	}

	public LinkModelBuilder<T> getLinkBuilder() {
		return this.linkBuilder;
	}

	public void setLinkBuilder(LinkModelBuilder<T> linkBuilder) {
		if (linkBuilder == null) {
			throw new IllegalArgumentException("linkBuilder cannot be null.");
		}

		this.linkBuilder = linkBuilder;
	}

	// MARK: ReadOnlyLinkModelAccessor
	@Override
	public ReadResponse<LinkModel> readLinkModels(ReadRequest request) throws AtomicOperationException {
		ReadResponse<T> modelReadResponse = this.readService.read(request);
		return this.wrapReadResponse(modelReadResponse);
	}

	protected ReadResponse<LinkModel> wrapReadResponse(ReadResponse<T> readResponse) {
		return new LinkModelReadResponse(readResponse);
	}

	private class LinkModelReadResponse
	        implements ReadResponse<LinkModel> {

		private final ReadResponse<T> readResponse;

		private Collection<LinkModel> linkModelsCache;

		public LinkModelReadResponse(ReadResponse<T> readResponse) {
			this.readResponse = readResponse;
		}

		@Override
		public Collection<ModelKey> getFiltered() {
			return this.readResponse.getFiltered();
		}

		@Override
		public Collection<ModelKey> getUnavailable() {
			return this.readResponse.getUnavailable();
		}

		@Override
		public Collection<ModelKey> getFailed() {
			return this.readResponse.getFailed();
		}

		@Override
		public Collection<LinkModel> getModels() {
			if (this.linkModelsCache == null) {
				this.linkModelsCache = this.makeLinkModels();
			}

			return this.linkModelsCache;
		}

		private Collection<LinkModel> makeLinkModels() {
			return null;
		}

	}

}
