package com.dereekb.gae.web.api.model.extension.link.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.service.LinkServiceResponse;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemResult;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.utilities.collections.pairs.SuccessPair;
import com.dereekb.gae.utilities.collections.pairs.impl.SuccessResultsPair;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ApiResponseData} that contains successful and missing keys.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class ApiLinkChangeResponseData
        implements ApiResponseData {

	public static final String DATA_TYPE = "LINK_RESPONSE_DATA";

	private Set<String> successful;
	private Set<String> failed;

	public ApiLinkChangeResponseData(Set<String> successful, Set<String> failed) {
		this.setSuccessful(successful);
		this.setFailed(failed);
	}

	public static ApiLinkChangeResponseData makeWithResponse(LinkServiceResponse response) {

		List<SuccessPair<LinkModificationSystemResult>> pairs = response.getSuccessResults();

		HashMapWithSet<Boolean, LinkModificationSystemResult> successMap = SuccessResultsPair.keysResultsMap(pairs);

		Set<LinkModificationSystemResult> success = successMap.get(true);
		Set<LinkModificationSystemResult> failed = successMap.get(false);

		Set<String> successfulKeys = ModelKey.readStringKeysSet(success);
		Set<String> failedKeys = ModelKey.readStringKeysSet(failed);

		// TODO: Build the link change results per modified key and set as the
		// data.
		// success.iterator().next().getPrimaryResult().getLinkChangeResult();

		return new ApiLinkChangeResponseData(successfulKeys, failedKeys);
	}

	public Set<String> getSuccessful() {
		return this.successful;
	}

	public void setSuccessful(Set<String> successful) {
		this.successful = successful;
	}

	public Set<String> getFailed() {
		return this.failed;
	}

	public void setFailed(Set<String> failed) {
		this.failed = failed;
	}

	// MARK: ApiResponseData
	@JsonIgnore
	@Override
	public String getResponseDataType() {
		return DATA_TYPE;
	}

	@JsonIgnore
	@Override
	public Object getResponseData() {
		return this;
	}

	@Override
	public String toString() {
		return "ApiLinkChangeResponseData [successful=" + this.successful + ", failed=" + this.failed + "]";
	}

}
