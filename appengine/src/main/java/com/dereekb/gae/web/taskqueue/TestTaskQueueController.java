package com.dereekb.gae.web.taskqueue;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Controller use for testing to see if the taskqueue is accessible.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/taskqueue")
public class TestTaskQueueController {

	@ResponseBody
	@RequestMapping(path = "/test", method = { RequestMethod.GET, RequestMethod.PUT }, produces = "application/json")
	public final ApiResponse getStatus() throws ApiCaughtRuntimeException {
		ApiResponseImpl response = new ApiResponseImpl();

		ApiResponseData data = new ApiResponseDataImpl("Success", "Success");
		response.setData(data);

		return response;
	}

}
