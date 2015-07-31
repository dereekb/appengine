package com.dereekb.gae.web.api.model.extension.link;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceRequestImpl;
import com.dereekb.gae.model.extension.links.service.impl.LinkSystemChangesException;
import com.dereekb.gae.server.datastore.models.keys.conversion.ModelKeyTypeConverter;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeConverterImpl;
import com.dereekb.gae.web.api.shared.request.ApiRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Controller for accessing a {@link LinkService}.
 *
 * @author dereekb
 *
 */
@RestController
public class LinkApiExtensionController {

	private LinkService service;
	private ApiLinkChangeConverter converter;

	public LinkApiExtensionController(LinkService service, ApiLinkChangeConverter converter) {
		this.service = service;
		this.converter = converter;
	}

	/**
	 * Convenience constructor that uses the passed
	 * {@link ModelKeyTypeConverter} to create a
	 * {@link ApiLinkChangeConverterImpl} instance for the {@link #converter}.
	 *
	 * @param service
	 * @param keyTypeConverter
	 */
	public LinkApiExtensionController(LinkService service, ModelKeyTypeConverter keyTypeConverter) {
		this.service = service;
		this.converter = new ApiLinkChangeConverterImpl(keyTypeConverter);
	}

	public LinkService getService() {
		return this.service;
	}

	public void setService(LinkService service) {
		this.service = service;
	}

	public ApiLinkChangeConverter getConverter() {
		return this.converter;
	}

	public void setConverter(ApiLinkChangeConverter converter) {
		this.converter = converter;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'link')")
	@RequestMapping(value = "/links/{type}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public final ApiResponse link(@PathVariable("type") String primaryType,
	                              @Valid @RequestBody ApiRequest<List<ApiLinkChange>> request) {
		ApiResponse response = null;

		try {
			List<ApiLinkChange> submittedChanges = request.getData();
			List<LinkSystemChange> changes = this.converter.convert(primaryType, submittedChanges);

			LinkServiceRequest linkServiceRequest = new LinkServiceRequestImpl(changes);
			this.service.updateLinks(linkServiceRequest);

			response = new ApiResponseImpl(true);
		} catch (LinkSystemChangesException e) {
			throw e;
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "LinkApiExtensionController [service=" + this.service + ", converter=" + this.converter + "]";
	}

}
