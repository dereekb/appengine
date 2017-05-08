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
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeConverterImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeRequest;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeResponse;

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
	public final ApiLinkChangeResponse link(@PathVariable("type") String primaryType,
	                                        @Valid @RequestBody ApiLinkChangeRequest request) {
		ApiLinkChangeResponse response = null;

		try {
			boolean atomic = request.isAtomic();

			List<ApiLinkChangeImpl> submittedChanges = request.getData();
			List<LinkSystemChange> changes = this.converter.convert(primaryType, submittedChanges);

			LinkServiceRequest linkServiceRequest = new LinkServiceRequestImpl(changes, atomic);
			LinkServiceResponse linkServiceResponse = this.service.updateLinks(linkServiceRequest);

			response = new ApiLinkChangeResponse(true);
			this.addMissingKeysToResponse(linkServiceResponse, response);
		} catch (LinkSystemChangeSetException e) {
			throw e;
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	private void addMissingKeysToResponse(LinkServiceResponse linkServiceResponse,
	                                      ApiLinkChangeResponse response) {
		HashMapWithSet<String, ModelKey> missingKeys = linkServiceResponse.getMissingKeys();
		HashMapWithList<String, String> missingKeysMap = new HashMapWithList<>();
		Set<String> missingTypes = missingKeys.keySet();

		if (missingTypes.isEmpty() == false) {
			for (String type : missingTypes) {
				Set<ModelKey> keys = missingKeys.get(type);
				List<String> stringKeys = ModelKey.readStringKeys(keys);
				missingKeysMap.addAll(type, stringKeys);
			}

			response.setMissingLinkKeys(missingKeysMap.getRawMap());
		}
	}

	@Override
	public String toString() {
		return "LinkExtensionApiController [indexService=" + this.service + ", converter=" + this.converter + "]";
	}

}
