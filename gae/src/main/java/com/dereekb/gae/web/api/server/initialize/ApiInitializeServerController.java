package com.dereekb.gae.web.api.server.initialize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.server.schedule.exception.UnavailableSchedulerTaskException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Service initialization controller. Provides a single GET function for easy
 * initialization.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/service")
public class ApiInitializeServerController {

	private ApiInitializeServerControllerDelegate delegate;

	public ApiInitializeServerController(ApiInitializeServerControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public ApiInitializeServerControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ApiInitializeServerControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Initialize
	@ResponseBody
	@RequestMapping(value = "/initialize", method = RequestMethod.GET, produces = "application/json")
	public ApiResponse initialize() throws UnavailableSchedulerTaskException {
		ApiResponse response = null;

		try {
			response = this.delegate.initialize();
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

}
