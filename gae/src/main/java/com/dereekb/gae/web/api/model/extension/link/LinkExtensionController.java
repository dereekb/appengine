package com.dereekb.gae.web.api.model.extension.link;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;
import com.dereekb.gae.model.extension.links.service.impl.LinkSystemChangesException;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceRequestImpl;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.request.ApiRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Controller for accessing a {@link LinkService}.
 *
 * @author dereekb
 *
 */
@Controller
public class LinkExtensionController {

	private LinkService service;
	private ApiLinkChangeConverter converter;

	public LinkExtensionController(LinkService service, ApiLinkChangeConverter converter) {
		this.service = service;
		this.converter = converter;
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

			response = new ApiResponse(true);

		} catch (LinkSystemChangesException e) {
			throw e;
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	public static class ApiLinkChange {

		/**
		 * Action to perform.
		 */
		@NotNull
		private String action;

		/**
		 * Keys to change.
		 */
		@NotNull
		private String primaryKey;

		/**
		 * The name of the link to change.
		 */
		@NotNull
		private String linkName;

		/**
		 * Keys of the target model.
		 *
		 * A max of 50 keys are allowed to be changed at one time.
		 */
		@NotEmpty
		@Max(50)
		private List<String> targetKeys;

		public String getAction() {
			return this.action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getPrimaryKey() {
			return this.primaryKey;
		}

		public void setPrimaryKey(String primaryKey) {
			this.primaryKey = primaryKey;
		}

		public String getLinkName() {
			return this.linkName;
		}

		public void setLinkName(String linkName) {
			this.linkName = linkName;
		}

		public List<String> getTargetKeys() {
			return this.targetKeys;
		}

		public void setTargetKeys(List<String> targetKeys) {
			this.targetKeys = targetKeys;
		}

	}

}
