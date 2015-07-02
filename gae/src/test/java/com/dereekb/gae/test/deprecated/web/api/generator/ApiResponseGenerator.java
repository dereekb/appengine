package com.thevisitcompany.gae.test.deprecated.web.api.generator;

import java.util.ArrayList;
import java.util.List;

import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;
import com.thevisitcompany.gae.deprecated.web.response.models.ApiIdentifierResponse;
import com.thevisitcompany.gae.deprecated.web.response.models.ApiModelResponse;
import com.thevisitcompany.gae.model.extension.generation.AbstractGenerator;

public class ApiResponseGenerator<T extends KeyedModel<Long>, A> extends AbstractGenerator<ApiResponse> {

	private Boolean defaultSuccessValue = true;
	private Boolean generateErrors = false;

	private Boolean generateIdentifiers = true;
	private Integer maxIdentifiersGenerated = 14;

	public ApiModelResponse<T, Long> generateModelResponse(List<T> models) {

		ApiModelResponse<T, Long> response = new ApiModelResponse<T, Long>();
		response.setModels(models);
		response.setSuccess(defaultSuccessValue);

		return response;
	}

	@Override
	public ApiResponse generate() {

		ApiIdentifierResponse<Long> response = new ApiIdentifierResponse<Long>();
		response.setSuccess(defaultSuccessValue);

		if (this.generateIdentifiers) {
			List<Long> keys = new ArrayList<Long>();

			for (int i = 0; i < this.maxIdentifiersGenerated; i += 1) {
				keys.add(this.randomPositiveLong());
			}

			response.setIdentifiers(keys);
		}

		if (this.generateErrors) {
			response.putError(ApiResponse.ERROR_REASON_KEY, "generated");
		}

		return response;
	}

}
