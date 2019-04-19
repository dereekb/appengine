package com.dereekb.gae.web.api.server.initialize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.initialize.ServerInitializeService;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.server.schedule.exception.UnavailableSchedulerTaskException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Service initialization controller. Provides a single GET function for easy
 * initialization.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/server")
public class ApiInitializeServerController {

	private ServerInitializeService initializeService;

	public ApiInitializeServerController(ServerInitializeService initializeService) {
		this.setInitializeService(initializeService);
	}

	public ServerInitializeService getInitializeService() {
		return this.initializeService;
	}

	public void setInitializeService(ServerInitializeService initializeService) {
		if (initializeService == null) {
			throw new IllegalArgumentException("initializeService cannot be null.");
		}

		this.initializeService = initializeService;
	}

	// MARK: Initialize
	@ResponseBody
	@RequestMapping(value = "/initialize", method = RequestMethod.GET, produces = "application/json")
	public ApiResponse initialize() throws UnavailableSchedulerTaskException {
		ApiResponse response = null;

		try {
			try {
				this.initializeService.initializeServer();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			response = new ApiResponseImpl(true);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "ApiInitializeServerController [initializeService=" + this.initializeService + "]";
	}

}
