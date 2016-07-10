package com.dereekb.gae.web.api.model.extension.link;

import java.util.List;
import java.util.Set;

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
import com.dereekb.gae.model.extension.links.service.LinkServiceResponse;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangesException;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceRequestImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeConverterImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeRequest;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
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

	public LinkExtensionApiController(LinkService service, ApiLinkChangeConverter converter) {
		this.service = service;
		this.converter = converter;
	}

	/**
	 * Convenience constructor that uses the passed
	 * {@link TypeModelKeyConverter} to create a
	 * {@link ApiLinkChangeConverterImpl} instance for the {@link #converter}.
	 *
	 * @param indexService
	 * @param keyTypeConverter
	 */
	public LinkExtensionApiController(LinkService service, TypeModelKeyConverter keyTypeConverter) {
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
	public final ApiResponseImpl link(@PathVariable("type") String primaryType,
	                                  @Valid @RequestBody ApiLinkChangeRequest request) {
		ApiResponseImpl response = null;

		try {
			boolean atomic = request.isAtomic();
			List<ApiLinkChange> submittedChanges = request.getData();
			List<LinkSystemChange> changes = this.converter.convert(primaryType, submittedChanges);

			LinkServiceRequest linkServiceRequest = new LinkServiceRequestImpl(changes, atomic);
			LinkServiceResponse linkServiceResponse = this.service.updateLinks(linkServiceRequest);

			response = new ApiResponseImpl(true);
			this.addMissingKeysToResponse(linkServiceResponse, response);
		} catch (LinkSystemChangesException e) {
			throw e;
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	private void addMissingKeysToResponse(LinkServiceResponse linkServiceResponse,
	                                      ApiResponseImpl response) {
		HashMapWithSet<String, ModelKey> missingKeys = linkServiceResponse.getMissingKeys();
		HashMapWithList<String, String> missingKeysMap = new HashMapWithList<String, String>();
		Set<String> missingTypes = missingKeys.keySet();

		if (missingTypes.isEmpty() == false) {
			for (String type : missingTypes) {
				Set<ModelKey> keys = missingKeys.get(type);
				List<String> stringKeys = ModelKey.readStringKeys(keys);
				missingKeysMap.addAll(type, stringKeys);
			}

			ApiResponseDataImpl responseData = new ApiResponseDataImpl();
			responseData.setData(missingKeysMap.getRawMap());
			responseData.setType("MissingKeys");

			response.addIncluded(responseData);
		}
	}

	@Override
	public String toString() {
		return "LinkExtensionApiController [indexService=" + this.service + ", converter=" + this.converter + "]";
	}

}
