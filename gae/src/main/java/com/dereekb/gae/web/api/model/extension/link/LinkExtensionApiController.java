package com.dereekb.gae.web.api.model.extension.link;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;
import com.dereekb.gae.model.extension.links.service.LinkServiceResponse;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeSetException;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceRequestImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.WrappedApiUnprocessableEntityException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeConverterImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Controller for accessing a {@link LinkService}.
 *
 * @author dereekb
 *
 */
@RestController
public class LinkExtensionApiController {

	private LinkService service;
	private ApiLinkChangeConverter converter;

	/**
	 * Convenience constructor that uses the passed
	 * {@link TypeModelKeyConverter} to create a
	 * {@link ApiLinkChangeConverterImpl} instance for the {@link #converter}.
	 *
	 * @param indexService
	 * @param keyTypeConverter
	 */
	public LinkExtensionApiController(LinkService service, TypeModelKeyConverter keyTypeConverter) {
		this(service, new ApiLinkChangeConverterImpl(keyTypeConverter));
	}

	public LinkExtensionApiController(LinkService service, ApiLinkChangeConverter converter) {
		this.setService(service);
		this.setConverter(converter);
	}

	public LinkService getService() {
		return this.service;
	}

	public void setService(LinkService service) {
		if (service == null) {
			throw new IllegalArgumentException("service cannot be null.");
		}

		this.service = service;
	}

	public ApiLinkChangeConverter getConverter() {
		return this.converter;
	}

	public void setConverter(ApiLinkChangeConverter converter) {
		if (converter == null) {
			throw new IllegalArgumentException("converter cannot be null.");
		}

		this.converter = converter;
	}

	// MARK: API
	@ResponseBody
	@RequestMapping(value = "/{type}/link", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public final ApiResponse link(@PathVariable("type") String primaryType,
	                              @Valid @RequestBody ApiLinkChangeRequest request) {
		ApiResponseImpl response = null;

		try {
			boolean atomic = request.isAtomic();

			List<ApiLinkChangeImpl> submittedChanges = request.getData();
			List<LinkSystemChange> changes = this.converter.convert(primaryType, submittedChanges);

			LinkServiceRequest linkServiceRequest = new LinkServiceRequestImpl(changes, atomic);
			LinkServiceResponse linkServiceResponse = this.service.updateLinks(linkServiceRequest);

			response = new ApiResponseImpl(true);
			this.addMissingKeysToResponse(primaryType, linkServiceResponse, response);
		} catch (LinkSystemChangeSetException e) {
			throw new WrappedApiUnprocessableEntityException(e);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	private void addMissingKeysToResponse(String primaryType,
	                                      LinkServiceResponse linkServiceResponse,
	                                      ApiResponseImpl apiResponse) {
		HashMapWithSet<String, ModelKey> missingKeys = linkServiceResponse.getMissingPrimaryKeysSet();
		Set<ModelKey> missingPrimaryKeys = missingKeys.get(primaryType);

		if (missingPrimaryKeys != null && missingPrimaryKeys.isEmpty() == false) {
			ApiResponseErrorImpl missingKeysError = MissingRequiredResourceException
			        .tryMakeApiErrorForModelKeys(missingPrimaryKeys, "Unavailable to change links.");
			apiResponse.addError(missingKeysError);
		}
	}

	@Override
	public String toString() {
		return "LinkExtensionApiController [indexService=" + this.service + ", converter=" + this.converter + "]";
	}

}
