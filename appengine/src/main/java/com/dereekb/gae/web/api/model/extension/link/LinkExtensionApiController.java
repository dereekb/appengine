package com.dereekb.gae.web.api.model.extension.link;

import java.util.ArrayList;
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
import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeSetException;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceRequestImpl;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemRequestImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.WrappedApiUnprocessableEntityException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeRequest;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
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

	public LinkExtensionApiController(LinkService service) {
		this.setService(service);
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

	// MARK: API
	@ResponseBody
	@RequestMapping(value = "/{type}/link", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public final ApiResponse link(@PathVariable("type") String primaryType,
	                              @Valid @RequestBody ApiLinkChangeRequest request) {
		ApiResponseImpl response = null;

		try {
			boolean atomic = request.isAtomic();

			List<ApiLinkChangeImpl> submittedChanges = request.getData();
			List<LinkModificationSystemRequest> changes = this.convert(primaryType, submittedChanges);

			LinkServiceRequest linkServiceRequest = new LinkServiceRequestImpl(changes, atomic);
			LinkServiceResponse linkServiceResponse = this.service.updateLinks(linkServiceRequest);

			response = new ApiResponseImpl(true);
			
			ApiLinkChangeResponseData data = ApiLinkChangeResponseData.makeWithResponse(linkServiceResponse);
			response.setData(data);
			
			this.addErrorsToResponse(primaryType, linkServiceResponse, response);
		} catch (LinkServiceChangeSetException e) {
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

	private void addErrorsToResponse(String primaryType,
	                                 LinkServiceResponse linkServiceResponse,
	                                 ApiResponseImpl response) {
		LinkServiceChangeSetException errorsSet = linkServiceResponse.getErrorsSet();
		
		if (errorsSet.hasErrors()) {
			ApiResponseError responseError = errorsSet.asResponseError();
			response.addError(responseError);
		}
	}

	private List<LinkModificationSystemRequest> convert(String linkModelType,
	                                                    List<ApiLinkChangeImpl> inputChanges) {
		List<LinkModificationSystemRequest> changes = new ArrayList<LinkModificationSystemRequest>();

		Integer index = 0;
		
		for (ApiLinkChange inputChange : inputChanges) {
			
			String id = inputChange.getId();
			
			if (id == null) {
				id = index.toString();
			}
			
			String linkName = inputChange.getLinkName();
			String primaryKey = inputChange.getPrimaryKey();
			
			String actionString = inputChange.getAction();
			MutableLinkChangeType linkChangeType = MutableLinkChangeType.fromString(actionString);
			
			Set<String> targetStringKeys = inputChange.getTargetKeys();
			
			LinkModificationSystemRequestImpl change = new LinkModificationSystemRequestImpl(linkModelType, primaryKey, linkName, linkChangeType, targetStringKeys);
			change.setRequestKey(id);
			
			changes.add(change);
			
			index += 1;
		}

		return changes;
	}

	@Override
	public String toString() {
		return "LinkExtensionApiController [indexService=" + this.service + "]";
	}

}
