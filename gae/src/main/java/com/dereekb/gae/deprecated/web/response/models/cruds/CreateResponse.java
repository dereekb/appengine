package com.thevisitcompany.gae.deprecated.web.response.models.cruds;

import java.util.List;

import com.thevisitcompany.gae.deprecated.web.response.models.ApiModelResponse;

public class CreateResponse<T, K> extends ApiModelResponse<T, K> {

	public CreateResponse() {
		super();
	}

	public CreateResponse(T model) {
		super(model);
	}

	public CreateResponse(List<T> models) {
		super(models);
	}

}
